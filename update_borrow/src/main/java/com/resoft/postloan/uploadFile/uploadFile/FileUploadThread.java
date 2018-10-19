package com.resoft.postloan.uploadFile.uploadFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import com.resoft.postloan.plVideoPath.entity.PLVideoPath;
import com.resoft.postloan.uploadFile.util.FdfsUtils;
import com.resoft.postloan.uploadFile.util.FileUtils;
import com.resoft.postloan.uploadFile.util.JdbcUtils;
import com.resoft.postloan.uploadFile.util.StringUtils;


public class FileUploadThread extends Constant implements Runnable{
	
	private final File rootDir;
	private final String applyNo;
	private final String uploadUser;
	private final boolean deleteFileAfterUploadSucceed;
	private final String registerDate;
	private final String taskDefKey;

	public FileUploadThread(String taskDefKey,File rootDir, String applyNo, String uploadUser, boolean deleteFileAfterUploadSucceed,String registerDate) {
		this.rootDir = rootDir;
		this.taskDefKey=taskDefKey;
		this.applyNo = applyNo;
		this.uploadUser = uploadUser;
		this.deleteFileAfterUploadSucceed = deleteFileAfterUploadSucceed;
		this.registerDate=registerDate;
	}

	public void run() {
		List<PLVideoPath> records = new ArrayList<PLVideoPath>();
		TreeMap<String, TreeSet<String>> fileMap = FileUtils.listFile(rootDir);
		try {
			for (Entry<String, TreeSet<String>> e: fileMap.entrySet()) {
				if (e.getValue() == null) {
					PLVideoPath record = new PLVideoPath();
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
						String upFileName=fileName.substring(0,fileName.length()-3)+"JPG";
						String downFileName=fileName.substring(0,fileName.length()-3)+"jpg";
						if (e.getValue().contains("suolue__" + upFileName)) {
							thumbnailStoragePath = FdfsUtils.upload(new File(rootDir, e.getKey() + "suolue__" + upFileName));
						}else if(e.getValue().contains("suolue__" + downFileName)){
							thumbnailStoragePath = FdfsUtils.upload(new File(rootDir, e.getKey() + "suolue__" + downFileName));
						}
						PLVideoPath record = new PLVideoPath();
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
			JdbcUtils.insertRecords(records,taskDefKey);
		} catch (SQLException e) {
			LOGGER.error("", e);
		}
		if (deleteFileAfterUploadSucceed) {
			FileUtils.delete(rootDir);
		}
		
	}
	
}
