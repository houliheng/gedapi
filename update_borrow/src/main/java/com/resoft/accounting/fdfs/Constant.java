package com.resoft.accounting.fdfs;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.resoft.accounting.applyChangeBankcard.service.AccVieoPathService;

public class Constant {
	
	protected static final Logger LOGGER = Logger.getLogger(Constant.class);
	
	protected static String fdfsConfFile = "/fdfs_client.conf";
	
	protected static String dbTableOwner = "acc";
	protected static String dbTableName = "";
	protected static String dbColNameRegisterDate = "register_date";
	protected static String dbColNameApplyNo = "apply_no";
	protected static String dbColNameFileDir = "file_dir";
	protected static String dbColNameFileName = "file_name";
	protected static String dbColNameFileStoragePath = "file_storage_path";
	protected static String dbColNameThumbnailStoragePath = "thumbnail_storage_path";
	protected static String dbColNameUploadUser = "create_by";
	protected static String dbColNameUploadTime = "create_date";
	
	protected static int dateStringStartIndexInJinjianNo = 5;
	protected static int dateStringLength = 6;
	
	protected static String encodingInZip = "gbk";

	protected static AccVieoPathService daoService;
	protected static DataSource dataSource;
	protected static Connection dbConnection;
	
	protected static int threadPoolSize = 10;
	
	protected static int fdfsConnectionPoolSize = 10;

	/** 日期字符串在进件号中的起始位置。准确的说，是位于日期字符串前面的字符个数。 */
	public static void setDateStringStartIndexInJinjianNo(
			int dateStringStartIndexInJinjianNo) {
		Constant.dateStringStartIndexInJinjianNo = dateStringStartIndexInJinjianNo;
	}
	
	public static void setDbSource(AccVieoPathService daoService) {
		Constant.daoService = daoService;
	}

	public static void setDbSource(DataSource dataSource) {
		Constant.dataSource = dataSource;
	}

	public static void setDbSource(Connection dbConnection) {
		Constant.dbConnection = dbConnection;
	}

	/**
	 * 设置插件初始化线程池的模式。默认值为10
	 * <br> -1  : 使用 CachedThreadPool，不限制线程数量
	 * <br>  0  : 以同步方式执行线程中的代码
	 * <br>  1  : 使用 SingleThreadExecutor，同时只启用一个线程
	 * <br>  1+ : 大于1时，使用 FixedThreadPool，最大线程数为设定的数值
	 */
	public static void setThreadPoolSize(int threadPoolSize) {
		Constant.threadPoolSize = threadPoolSize;
	}

	/** 初始化与fdfs集群的连接池大小，推荐不小于threadPoolSize的值。默认值为10 */
	public static void setFdfsConnectionPoolSize(int fdfsConnectionPoolSize) {
		Constant.fdfsConnectionPoolSize = fdfsConnectionPoolSize;
	}
	
}
