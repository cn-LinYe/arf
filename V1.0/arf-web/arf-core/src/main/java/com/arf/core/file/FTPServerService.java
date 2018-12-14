package com.arf.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

//@Component("fileServerService")
@Deprecated //FTP文件服务器已迁往阿里云OSS 2017-05-16 12:10 by caolibao
public class FTPServerService implements FileServerService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 文件服务器URL地址
	 */
	@Value("${file.ftp.host}")
	private String host;
	
	@Value("${file.ftp.userName}")
	private String userName;
	
	@Value("${file.ftp.password}")
	private String password;
	
	@Value("${file.ftp.port}")
	private Integer port;
	
	@Value("${file.ftp.dir}")
	private String ftpDir; 
	
	@Value("${file.store.dir}")
	private String storeDir;
	
    /**  
     *   
     * @param username 用户名  
     * @param password 密码  
     * @return  
     * @throws Exception  
     */    
    private  FTPClient connect(){
    	FTPClient ftp;
    	
    	if(StringUtils.isBlank(host)){
    		return null;
    	}
    	ftp = new FTPClient();
        ftp.setDataTimeout(3 * 60 * 1000);       //设置传输超时时间为60秒 
        ftp.setConnectTimeout(60 * 1000);       //连接超时为60秒
        
        int reply;      
        try {
        	if(port == null || port <= 0){
        		port = 21;
        	}
			ftp.connect(host,port);
			boolean logined = ftp.login(userName,password);
			if(!logined){
				logger.error("连接FTP服务器使用的用户名/密码错误,无法登陆FTP,userName="+userName+",password="+password+",host="+host+":"+port);
			}
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {      
			    ftp.disconnect();      
			    return null;      
			}
			if(StringUtils.isNotBlank(ftpDir)){
				ftp.changeWorkingDirectory(ftpDir);
			}
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();//设置FTP被动传输模式
			return ftp;      
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    /**
     * 上传文件到文件服务器
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
			logger.error("上传文件到ftp出现异常",e);
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
     * 将文件上传到文件服务器
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
			logger.error("上传文件到ftp出现异常",e);
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
	 将文件上传到文件服务器
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
		
		FTPClient ftp = connect();
		if(null == ftp){
			return null;
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
		String remoteFileName = dir + filename;
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			if(StringUtils.isNotBlank(dir) && !this.mkdirs(dir,ftp)){
				return null;
			}
			boolean stored = ftp.storeFile(remoteFileName, fileInputStream);
			if(stored){
				String ftpFilePath = "arf.uploads" + "/" + remoteFileName;
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
			logger.error("上传文件到ftp出现异常",e);
		}finally {
			try {
				if(ftp != null && ftp.isConnected()){
					ftp.disconnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	
	private boolean mkdirs(String pathname,FTPClient ftpClient){
		if(StringUtils.isBlank(pathname)){
			return false;
		}
		if(ftpClient == null){
			return false;
		}
		pathname = pathname.replace("\\", "/");
		String[] dirs = pathname.split("/");
		String path = "";
		for(String dir : dirs){
			if(StringUtils.isBlank(dir)){
				continue;
			}
			path += dir + "/";
			try {
				ftpClient.mkd(path);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
	