package com.resoft.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtils {
	private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	public static void zipFile(File inputFile, ZipOutputStream zos) {
		if (inputFile.exists()) {
			if (inputFile.isFile()) {
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(inputFile);
					bis = new BufferedInputStream(fis);
					ZipEntry entry = new ZipEntry(inputFile.getName());
					zos.putNextEntry(entry);
					int count;
					byte[] buffer = new byte[100 * 1024];
					while ((count = bis.read(buffer, 0, 100 * 1024)) != -1) {
						zos.write(buffer, 0, count);
					}

				} catch (Exception e) {
					logger.error("压缩异常", e);
				} finally {
					try {
						bis.close();
						fis.close();
					} catch (IOException e) {
						logger.error("关闭流异常！", e);
					}
					inputFile.delete();
				}
			} else {
				File[] files = inputFile.listFiles();
				for (File file : files) {
					zipFile(file, zos);
					file.delete();
				}
			}
		}
	}

	public static void downloadZip(File file, HttpServletResponse response) {
		InputStream fis = null;
		OutputStream os = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[100 * 1024];
			os = response.getOutputStream();
			response.setContentType("application/octet-stream");
			response.setHeader("content-disposition", "attachment;filename=" + file.getName());
			int len = 0;
			// 循环将输入流中的内容读取到缓冲区中
			while ((len = fis.read(buffer, 0, 100 * 1024)) != -1) {
				os.write(buffer, 0, len);
			}
			os.flush();
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
				logger.error("关闭流报错", e);
			}
		}

	}

	public static File fileToZip(String filePath) {
		File target = null;
		File source = new File(filePath);
		if (source.exists()) {
			String zipName = source.getName() + ".zip";
			target = new File(source.getParent(), zipName);
			if (target.exists()) {
				target.delete();
			}
			FileOutputStream fos = null;
			ZipOutputStream zos = null;
			try {
				fos = new FileOutputStream(target);
				zos = new ZipOutputStream(new BufferedOutputStream(fos));
				addEntry("", source, zos);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (zos != null) {
						zos.flush();
						zos.close();
					}
					if (fos != null) {
						fos.flush();
						fos.close();
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return target;
	}

	/**
	 * 扫描添加文件Entry
	 * 
	 * @param base
	 *            基础路径
	 * @param source
	 *            源文件
	 * @param zos
	 *            zip输出流
	 * @throws IOException
	 */
	public static void addEntry(String base, File source, ZipOutputStream zos) throws IOException {
		// 按目录分级，形如：/aaa/bbb.txt
		String entry = base + source.getName();
		if (source.isDirectory()) {
			for (File file : source.listFiles()) {
				// 递归列出目录下的所有文件，添加文件Entry
				addEntry(entry + "/", file, zos);
			}
		} else {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				byte[] buffer = new byte[1024 * 10];
				fis = new FileInputStream(source);
				bis = new BufferedInputStream(fis, buffer.length);
				int read = 0;
				zos.putNextEntry(new ZipEntry(entry));
				zos.setEncoding("GBK");
				while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
					zos.write(buffer, 0, read);
				}
				zos.closeEntry();
			} finally {
				if (bis != null) {
					bis.close();
				}
				if (fis != null) {
					fis.close();
				}
			}
		}
	}

	public static void deleteFile(File source) {
		if (!source.exists()) {
			return;
		}
		if (source.isFile()) {
			source.delete();
			return;
		}
		File[] files = source.listFiles();
		for (File file : files) {
			deleteFile(file);
		}
		source.delete();
	}

	public static void main(String[] args) {
		fileToZip("C:/tmp/GJD00170301162119AAA");
		deleteFile(new File("C:/tmp/GJD00170301162119AAA"));
	}
}
