package com.resoft.accounting.fdfs;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.resoft.accounting.applyChangeBankcard.entity.AccVideoPath;
import com.resoft.accounting.fdfs.util.FdfsUtils;
import com.resoft.accounting.fdfs.util.JdbcUtils;
import com.resoft.accounting.fdfs.util.StringUtils;
import com.resoft.accounting.fdfs.util.FileUtils;
import com.resoft.accounting.fdfs.util.ZipUtils;

public class Manager extends Constant {

	public static void uploadDir(File rootDir, String applyNo, String uploadUser, boolean deleteFileAfterUploadSucceed, String registerDate) throws IOException {
		if (!rootDir.isDirectory()) {
			throw new IOException(rootDir.getAbsolutePath() + " is not a directory.");
		}
		FileUploadThread task = new FileUploadThread(rootDir, applyNo, uploadUser, deleteFileAfterUploadSucceed, registerDate);
		if (threadPool == null) {
			task.run();
		} else {
			threadPool.execute(task);
		}
	}

	public static void uploadZip(File zipFile, String applyNo, String uploadUser, boolean deleteFileAfterUploadSucceed, String registerDate) throws IOException {
		String tmpDir = System.getProperty("java.io.tmpdir");
		File rootDir = new File(tmpDir, String.valueOf(System.currentTimeMillis()));
		ZipUtils.unzip(zipFile.getAbsolutePath(), rootDir);
		uploadDir(rootDir, applyNo, uploadUser, true, registerDate);
		if (deleteFileAfterUploadSucceed) {
			FileUtils.delete(zipFile);
		}
	}

	public static void uploadFile(File f, String fileName, File suolue, String subPath, String applyNo, String uploadUser, boolean deleteFileAfterUploadSucceed) throws IOException {
		String fileStoragePath = FdfsUtils.upload(f);
		String thumbnailStoragePath = null;
		if (suolue != null) {
			thumbnailStoragePath = FdfsUtils.upload(suolue);
		}
		String registerDate = StringUtils.getDateStringFromApplyNo(applyNo);
		AccVideoPath record = new AccVideoPath();
		record.setRegisterDate(registerDate);
		record.setApplyNo(applyNo);
		record.setFileDir(subPath);
		record.setFileName(fileName);
		record.setFileStoragePath(fileStoragePath);
		record.setThumbnailStoragePath(thumbnailStoragePath);
		record.setCreateBy1(uploadUser);
		record.setCreateDate1(StringUtils.getCurrentTimeString());
		try {
			JdbcUtils.insertRecord(record);
		} catch (SQLException e) {
			LOGGER.error("", e);
		}
	}

	public static byte[] download(String storagePath) throws Exception {
		return FdfsUtils.download(storagePath);
	}

	public static boolean downloadToLocalPath(String storagePath, String targetLocalPath) throws Exception {
		return FdfsUtils.download(storagePath, targetLocalPath);
	}

	private static ExecutorService threadPool;

	public static void startThreadPool() {
		startThreadPool(threadPoolSize);
	}

	public static void startThreadPool(int threadPoolSize) {
		if (threadPool != null) {
			throw new RuntimeException("thread pool has already bean initialized.");
		}
		if (threadPoolSize == -1) {
			threadPool = Executors.newCachedThreadPool();
		} else if (threadPoolSize == 0) {
			threadPool = null;
		} else if (threadPoolSize == 1) {
			threadPool = Executors.newSingleThreadExecutor();
		} else if (threadPoolSize > 1) {
			threadPool = Executors.newFixedThreadPool(threadPoolSize);
		} else {
			throw new IllegalArgumentException("illegal thread pool size: " + threadPoolSize);
		}
	}

}
