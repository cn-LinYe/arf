package com.arf.core.file;

import java.io.File;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

@Component("fileStoreService")
public class FileStoreService implements ServletContextAware {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String WEB_APPS_DIR = "webapps";

	/**
	 * 文件存储位置
	 */
	@Value("${file.store.dir}")
	private String storeDir;

	@SuppressWarnings("unused")
	private ServletContext servletContext;
	
	private String storeRealPath;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		// 处理路径
		if(StringUtils.isNotBlank(storeDir)){
			if("\\".equals(File.separator)){ //WIN
				storeDir = storeDir.replace("/", "\\");
			}else{
				storeDir = storeDir.replace("\\", "/");
			}
			if(storeDir.startsWith(File.separator)){
				storeDir = storeDir.substring(1);
			}
			if(storeDir.endsWith(File.separator)){
				storeDir = storeDir.substring(0,storeDir.length() - 1);
			}
			// 获取tomcat下webapps路径
			String catalinaHome = System.getProperty("catalina.home");
			storeRealPath = catalinaHome + File.separator + WEB_APPS_DIR + File.separator
					+ storeDir + File.separator;
		}
	}

	/**
	 * 文件存储
	 * @param type
	 * @param file
	 * @return 返回文件存储后的相对路径
	 * @throws Exception
	 */
	public String storeFile(FileType type,File file) throws Exception {
		String paths[] = storeFiles(type,file);
		if(paths != null && paths.length == 1){
			return paths[0];
		}else{
			return null;
		}
	}
	
	public String storeMultipartFile(FileType type,MultipartFile file) throws Exception{
		String paths[] = storeMultipartFiles(type,file);
		if(paths != null && paths.length == 1){
			return paths[0];
		}else{
			return null;
		}
	}
	public String[] storeMultipartFiles(FileType type,MultipartFile ...files) throws Exception{
		String paths[] = new String[files.length];
		File tmps[] = new File[files.length];
		int index = 0;
		try {
			for (MultipartFile file : files) {
				String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				logger.info(file.getOriginalFilename()+"--extension--->"+"."+extension);
				File newFile = File.createTempFile(UUID.randomUUID().toString(), "."+extension);
				file.transferTo(newFile);
				paths[index] = storeFile(type, newFile);
				tmps[index] = newFile;
				index++;
			} 
		} finally {
			for(File f : tmps){
				try{
					f.delete();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return paths;
	}
	
	/**
	 * 文件存储 批量
	 * @param type
	 * @param files
	 * @return 返回文件存储后的相对路径
	 * @throws Exception
	 */
	public String[] storeFiles(FileType type,File ...files) throws Exception {
		if(StringUtils.isBlank(storeDir)){
			throw new Exception("没有在配置文件中配置文件存储目录file.store.dir");
		}
		if(files == null || files.length == 0){
			return null;
		}
		int length = files.length;
		String path = storeRealPath + type.toString();
		File DIR = new File(path);
		if(!DIR.exists()){
			mkDir(DIR);
		}
		String[] filePaths = new String[length];
		for(int i=0;i<length;i++){
			File file = files[i];
			//生成文件名
			String fileName = UUID.randomUUID().toString();
			String extension = FilenameUtils.getExtension(file.getName());
			String filePath = path + File.separator + fileName + "." + extension;
			File f = new File(filePath);
			FileCopyUtils.copy(file, f);
			filePaths[i] = (storeDir + File.separator + type.toString() + File.separator + fileName + "." + extension).replace("\\", "/");
		}
		return filePaths;
	}
	
	public static void mkDir(File file) {
		if (file.getParentFile().exists()) {
			file.mkdir();
		} else {
			mkDir(file.getParentFile());
			file.mkdir();
		}
	}
}
