package com.resoft.credit.fdfs.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.resoft.credit.fdfs.Constant;

public class ZipUtils extends Constant {
	
	public static void unzip(String zipPath, String dest) throws IOException{
		unzip(zipPath, new File(dest));
	}
	
	public static void unzip(String zipPath, File dest) throws IOException{
		unzip(new ZipFile(zipPath, encodingInZip), dest);
	}
	
	private static void unzip(ZipFile zipFile, File dest) throws IOException{
		FileUtils.forceMkdir(dest);
		//先创建全部路径
		Enumeration<?> emu = zipFile.getEntries();
		while (emu.hasMoreElements()) {
			ZipEntry e = (ZipEntry) emu.nextElement();
			if(e.getName().endsWith("/")
					|| e.getName().endsWith("\\")){
				File file = new File(dest, e.getName());
				FileUtils.forceMkdir(file);
			}
		}
		//再解压文件
		emu = zipFile.getEntries();
		while (emu.hasMoreElements()) {
			ZipEntry e = (ZipEntry) emu.nextElement();
			if(!e.isDirectory()){
				File file = new File(dest, e.getName());
				BufferedInputStream bis = null;
				BufferedOutputStream bos = null;
				try {
					bis = new BufferedInputStream(zipFile.getInputStream(e));
					bos = new BufferedOutputStream(new FileOutputStream(file), 100*1024);
					int count;
					byte[] b = new byte[100*1024];
					while ((count = bis.read(b, 0, 100*1024)) != -1) {
						bos.write(b, 0, count);
					}
				} finally {
					FileUtils.closeIOSource(bos);
					FileUtils.closeIOSource(bis);
				}
			}
		}
		zipFile.close();
	}

}
