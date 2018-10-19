package com.resoft.accounting.util;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.DruidDataSource;
import com.resoft.accounting.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;

@Service("taskTimer")
@Transactional
public class TimerUtils{

	private DruidDataSource dataSource = SpringContextHolder.getBean("accDataSource");
	
	/**
	 * 调用跑批存储
	 */
	public void callAccounting(){
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			CallableStatement callableStatement = connection.prepareCall("{call SP_RUN_ACC_OVERDUE('" + DateUtils.formatDate(new Date(), "yyyy-MM-dd") + "')}");
			callableStatement.execute();
		} catch (SQLException e) {
			System.out.println(e); 
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println(e); 
			}
		}
	}
	
}
