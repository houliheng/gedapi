package com.resoft.accounting.fdfs;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import com.resoft.accounting.applyChangeBankcard.entity.AccVideoPath;
import com.resoft.accounting.fdfs.util.FdfsUtils;
import com.resoft.accounting.fdfs.util.FileUtils;
import com.resoft.accounting.fdfs.util.JdbcUtils;
import com.resoft.accounting.fdfs.util.StringUtils;

public class FileUploadThread extends Constant implements Runnable{
	
	private final File rootDir;
	private final String applyNo;
	private final String uploadUser;
	private final boolean deleteFileAfterUploadSucceed;
	private final String registerDate;

	public FileUploadThread(File rootDir, String applyNo, String uploadUser, boolean deleteFileAfterUploadSucceed,String registerDate) {
		this.rootDir = rootDir;
		this.applyNo = applyNo;
		this.uploadUser = uploadUser;
		this.deleteFileAfterUploadSucceed = deleteFileAfterUploadSucceed;
		this.registerDate=registerDate;
	}

	public void run() {
		List<AccVideoPath> records = new ArrayList<AccVideoPath>();
		TreeMap<String, TreeSet<String>> fileMap = FileUtils.listFile(rootDir);
		try {
			for (Entry<String, TreeSet<String>> e: fileMap.entrySet()) {
				if (e.getValue() == null) {
					AccVideoPath record = new AccVideoPath();
					record.setRegisterDate(registerDate);
					record.setApplyNo(applyNo);
					record.setFileDir(e.getKey());
					record.setCreateBy1(uploadUser);
					record.setCreateDate1(StringUtils.getCurrentTimeString());
					records.add(record);
				} else {
					for (String fileName: e.getValue()) {
						if (fileName.startsWith("suolue__")) {
							continue;
						}
						String fileStoragePath = FdfsUtils.upload(new File(rootDir, e.getKey() + fileName));
						String thumbnailStoragePath = null;
						if (e.getValue().contains("suolue__" + fileName)) {
							thumbnailStoragePath = FdfsUtils.upload(new File(rootDir, e.getKey() + "suolue__" + fileName));
						}
						AccVideoPath record = new AccVideoPath();
						record.setRegisterDate(registerDate);
						record.setApplyNo(applyNo);
						record.setFileDir(e.getKey());
						record.setFileName(fileName);
						record.setFileStoragePath(fileStoragePath);
						record.setThumbnailStoragePath(thumbnailStoragePath);
						record.setCreateBy1(uploadUser);
						record.setCreateDate1(StringUtils.getCurrentTimeString());
						records.add(record);
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error("", e);
		}
		try {
			JdbcUtils.insertRecords(records);
		} catch (SQLException e) {
			LOGGER.error("", e);
		}
		if (deleteFileAfterUploadSucceed) {
			FileUtils.delete(rootDir);
		}
		
	}
	
}
