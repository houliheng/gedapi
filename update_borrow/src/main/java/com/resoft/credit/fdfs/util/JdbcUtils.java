package com.resoft.credit.fdfs.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.resoft.credit.applyRegister.entity.CreVideoPath;
import com.resoft.credit.fdfs.Constant;

public class JdbcUtils extends Constant {

	public static void insertRecords(List<CreVideoPath> records,String taskDefKey) throws SQLException {
		if (records == null || records.isEmpty()) {
			return;
		}
		if (dbConnection != null || dataSource != null) {
			insertRecordsByJdbc(records,taskDefKey);
		} else if (daoService != null) {
			for (CreVideoPath r : records) {
				daoService.saveCreVideoPath(r,taskDefKey);
			}
		}
	}
	
	public static void insertRecord(CreVideoPath record,String taskDefKey) throws SQLException {
		if (record == null) {
			return;
		}
		if (dbConnection != null || dataSource != null) {
			List<CreVideoPath> records = new ArrayList<CreVideoPath>();
			records.add(record);
			insertRecordsByJdbc(records,taskDefKey);
		} else if (daoService != null) {
			daoService.saveCreVideoPath(record,taskDefKey);
		}
	}
	
	public static void insertRecordsByJdbc(List<CreVideoPath> records,String taskDefKey) throws SQLException {
		if (records == null || records.isEmpty()) {
			return;
		}
		PreparedStatement ps = null;
		try {
			String sql = getInsertRecordsSql();
			Connection conn = getConnection();
			ps = conn.prepareStatement(sql);
			for (CreVideoPath r : records) {
				ps.setString(1, r.getRegisterDate());
				ps.setString(2, r.getApplyNo());
				ps.setString(3, r.getFileDir());
				ps.setString(4, r.getFileName());
				ps.setString(5, r.getFileStoragePath());
				ps.setString(6, r.getThumbnailStoragePath());
				ps.setString(7, taskDefKey);
				ps.setString(8, r.getCreateBy1());
				ps.setString(9, r.getCreateDate1());
				ps.addBatch();
			}
			ps.execute();
			conn.commit();
		} finally {
			closeDbSource(ps);
		}
	}
	
	private static String getInsertRecordsSql() {
		StringBuffer sb = new StringBuffer("insert into ").append(dbTableName).append('(');
		sb.append(dbColNameRegisterDate).append(',');
		sb.append(dbColNameApplyNo).append(',');
		sb.append(dbColNameFileDir).append(',');
		sb.append(dbColNameFileName).append(',');
		sb.append(dbColNameFileStoragePath).append(',');
		sb.append(dbColNameThumbnailStoragePath).append(',');
		sb.append(dbColNameUploadUser).append(',');
		sb.append(dbColNameUploadTime).append(')');
		sb.append(" values (cast(? AS DATE),?,?,?,?,?,?,?,cast(? AS DATETIME))");
		return sb.toString();
	}
	
	private static synchronized Connection getConnection() throws SQLException {
		if (dbConnection != null) {
			return dbConnection;
		} else if (dataSource != null) {
			dbConnection = dataSource.getConnection();
			return dbConnection;
		} else {
			return null;
		}
	}
	
	public static void closeDbSource(Object obj) throws SQLException {
		if (obj != null) {
			if (obj instanceof Connection) {
				((Connection) obj).close();
			} else if (obj instanceof Statement) {
				((Statement) obj).close();
			} else if (obj instanceof PreparedStatement) {
				((PreparedStatement) obj).close();
			} else if (obj instanceof ResultSet) {
				((ResultSet) obj).close();
			} else {
				throw new IllegalArgumentException("Unsupportable type: " + obj.getClass().getCanonicalName());
			}
		}
	}
	
}
