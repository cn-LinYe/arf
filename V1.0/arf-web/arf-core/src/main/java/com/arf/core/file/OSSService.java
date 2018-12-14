package com.arf.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;

@Component("fileServerService") //原有FTPServerService.java迁往OSS 2017-05-16 12:11 by caolibao
public class OSSService implements FileServerService{
	
	// endpoint以深圳为例，其它region请按实际情况填写
	private final static String End_Point = "http://oss-cn-shenzhen.aliyuncs.com";
	// accessKey请登录https://ak-console.aliyun.com/#/查看
	private final static String Access_Key_Id = "LTAItxf60wkOBq2k";
	private final static String Access_Key_Secret = "zfUME921MGH9UGotmbYWxMkCG77dhE";
	private final static String Bucket = "anxindian";
	
	// 创建OSSClient实例
	private final static OSSClient ossClient = new OSSClient(End_Point, Access_Key_Id, Access_Key_Secret);
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${file.store.dir}")
	private String fileStoreDir;
	
	
	/**
     * 上传文件到文件oss服务器
     * @param file
     * @param fileType
     * @param childDir
     * @param filename 要保存到FTP上的文件名,如果为空字符串则随机UUID
     * @param listener
     * @return
     */
	@Override
    public String upload2ServerWithName(MultipartFile file,FileType fileType,String childDir,String filename,UploadListener listener){
    	String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    	File newFile = null;
    	try{
    		newFile = File.createTempFile(UUID.randomUUID().toString(), "."+extension);
			file.transferTo(newFile);
			return upload2Server(newFile,fileType,childDir,filename,listener);
		}catch(Exception e){
			logger.error("上传文件到oss出现异常",e);
			return null;
		}finally {
			try{
				if(newFile != null)
					newFile.delete();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
    }
    
    /**
     * 将文件上传到文件oss服务器
     * @param file
     * @param fileType
     * @param childDir
     * @param listener
     * @return 上传成功会返回子路径
     * @throws Exception
     */
    @Override
    public String upload2Server(MultipartFile file,FileType fileType,String childDir,UploadListener listener){
    	String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    	File newFile = null;
    	try{
    		newFile = File.createTempFile(UUID.randomUUID().toString(), "."+extension);
			file.transferTo(newFile);
			return upload2Server(newFile,fileType,childDir,null,listener);
		}catch(Exception e){
			logger.error("上传文件到oss出现异常",e);
			return null;
		}finally {
			try{
				if(newFile != null)
					newFile.delete();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
    
	/**
	 将文件上传到文件oss服务器
	 * @param file
	 * @param fileType
	 * @param childDir
	 * @param filename
	 * @param listener
	 * @return 上传成功会返回子路径
	 */
    @Override
	public String upload2Server(File file,FileType fileType,String childDir,String filename,UploadListener listener){
		if(listener != null){
			listener.before();
		}
		
		if(!file.isFile()){
			return null;
		}
		String dir =  "";
		if(fileType != null){
			dir = fileType.toString() + "/"; // eg./test/doc/
		}
		//判断childDir是否存在
		if(!StringUtils.isBlank(childDir)){
			if(childDir.startsWith("/")){
				childDir = childDir.substring(1);
			}
			if(childDir.endsWith("/")){
				childDir = childDir.substring(0,childDir.length() - 1);
			}
			dir = dir + childDir + "/";// eg./test/doc/childDir/
		}
		if(dir.startsWith("/")){
			dir = dir.substring(1);
		}
		
		if(StringUtils.isBlank(filename)){
			String extension = FilenameUtils.getExtension(file.getName());
			if(extension == null){
				extension = "";
			}else{
				extension = "." + extension;
			}
			filename = getFileUUID() + extension;
		}
		String remoteFileName = fileStoreDir + "/" +  dir + filename;
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			remoteFileName = remoteFileName.replaceAll("/+", "/");
			PutObjectResult pr = ossClient.putObject(Bucket, remoteFileName, fileInputStream);
			if(pr != null){
				String ftpFilePath = remoteFileName;
				if(listener != null){
					listener.sucess(ftpFilePath);
				}
				return ftpFilePath;
			}else{
				if(listener != null){
					listener.failed();
				}
				return null;
			}
			
		} catch (Exception e) {
			if(listener != null){
				listener.failed();
			}
			logger.error("上传文件到oss出现异常",e);
		}finally {
			try{
				if(fileInputStream != null){
					fileInputStream.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 以一个文件生成UUID
	 * @param fileName
	 * @return
	 */
	private static String getFileUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
