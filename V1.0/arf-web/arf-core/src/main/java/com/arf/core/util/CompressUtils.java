/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.util.Assert;

/**
 * Utils - 压缩/解压缩
 * 
 * @author arf
 * @version 4.0
 */
public final class CompressUtils {

	/**
	 * 不可实例化
	 */
	private CompressUtils() {
	}

	/**
	 * ZIP压缩
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 */
	public static void zip(File srcFile, File destFile) {
		Assert.notNull(srcFile);
		Assert.state(srcFile.exists());
		Assert.notNull(destFile);
		if (destFile.exists()) {
			Assert.state(destFile.isFile());
		}

		ArchiveOutputStream archiveOutputStream = null;
		try {
			destFile.getParentFile().mkdirs();
			archiveOutputStream = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, new BufferedOutputStream(new FileOutputStream(destFile)));
			zip(archiveOutputStream, srcFile, "");
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (ArchiveException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(archiveOutputStream);
		}
	}

	/**
	 * ZIP解压缩
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destDir
	 *            目标目录
	 */
	public static void unZip(File srcFile, File destDir) {
		Assert.notNull(srcFile);
		Assert.state(srcFile.exists());
		Assert.state(srcFile.isFile());
		Assert.notNull(destDir);
		if (destDir.exists()) {
			Assert.state(destDir.isDirectory());
		}

		ArchiveInputStream archiveInputStream = null;
		try {
			destDir.mkdirs();
			archiveInputStream = new ArchiveStreamFactory().createArchiveInputStream(new BufferedInputStream(new FileInputStream(srcFile)));
			ZipArchiveEntry zipArchiveEntry = null;
			while ((zipArchiveEntry = ((ZipArchiveInputStream) archiveInputStream).getNextZipEntry()) != null) {
				if (zipArchiveEntry.isDirectory()) {
					new File(destDir, zipArchiveEntry.getName()).mkdirs();
				} else {
					OutputStream outputStream = null;
					try {
						outputStream = new BufferedOutputStream(new FileOutputStream(new File(destDir, zipArchiveEntry.getName())));
						IOUtils.copy(archiveInputStream, outputStream);
						outputStream.flush();
					} finally {
						IOUtils.closeQuietly(outputStream);
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (ArchiveException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(archiveInputStream);
		}
	}

	/**
	 * ZIP压缩
	 * 
	 * @param archiveOutputStream
	 *            ArchiveOutputStream
	 * @param file
	 *            文件
	 * @param base
	 *            基本路径
	 */
	private static void zip(ArchiveOutputStream archiveOutputStream, File file, String base) {
		Assert.notNull(archiveOutputStream);
		Assert.notNull(file);
		Assert.state(file.exists());
		Assert.notNull(base);

		try {
			String entryName = base + file.getName();
			ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, entryName);
			archiveOutputStream.putArchiveEntry(zipArchiveEntry);
			if (file.isFile()) {
				InputStream inputStream = null;
				try {
					inputStream = new BufferedInputStream(new FileInputStream(file));
					IOUtils.copy(inputStream, archiveOutputStream);
					archiveOutputStream.closeArchiveEntry();
				} finally {
					IOUtils.closeQuietly(inputStream);
				}
			} else {
				archiveOutputStream.closeArchiveEntry();
				File[] children = file.listFiles();
				if (children != null) {
					for (File child : children) {
						zip(archiveOutputStream, child, entryName + "/");
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}