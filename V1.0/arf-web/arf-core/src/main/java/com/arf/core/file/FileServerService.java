package com.arf.core.file;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface FileServerService {
	
	 /**
     * 上传文件到文件服务器
     * @param file
     * @param fileType
     * @param childDir
     * @param filename 要保存到FTP上的文件名,如果为空字符串则随机UUID
     * @param listener
     * @return
     */
	public String upload2ServerWithName(MultipartFile file,FileType fileType,String childDir,String filename,UploadListener listener);
    
    /**
     * 将文件上传到文件服务器
     * @param file
     * @param fileType
     * @param childDir
     * @param listener
     * @return 上传成功会返回子路径
     * @throws Exception
     */
    public String upload2Server(MultipartFile file,FileType fileType,String childDir,UploadListener listener);
    
	/**
	 将文件上传到文件服务器
	 * @param file
	 * @param fileType
	 * @param childDir
	 * @param filename
	 * @param listener
	 * @return 上传成功会返回子路径
	 */
	public String upload2Server(File file,FileType fileType,String childDir,String filename,UploadListener listener);
	
	public interface UploadListener{
		void before();
		void sucess(String path);
		void failed();
	}
}
