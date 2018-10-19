package com.thinkgem.jeesite.common.persistence.dialect.db;

import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.dialect.Dialect;
import com.thinkgem.jeesite.common.persistence.interceptor.SQLHelper;
import com.thinkgem.jeesite.common.utils.Constants;

/**
 * Mysql方言的实现
 *
 * @author poplar.yfyang
 * @version 1.0 2010-10-10 下午12:31
 * @since JDK 1.5
 */
public class MySQLDialect implements Dialect {

    @Override
    public String getLimitString(String sql, int offset, int limit) {
    	if(sql.indexOf("LIMITSQL") > 0){
    		sql = sql.replace("LIMITSQL", " limit "+ offset + "," + limit * 50);
    	}
		return getLimitString(sql);
    }

    public boolean supportsLimit() {
        return true;
    }

    public String getLimitString(String sql) {
        StringBuffer sqlBuffer = new StringBuffer(sql.length() + 14);
        sqlBuffer.append(sql);
        sqlBuffer.append(" limit ?,?");
        return sqlBuffer.toString();
    }
    
	@Override
	public Map<Object, Object> setPageParameter(MappedStatement ms, Object parameterObject, BoundSql boundSql, Page<Object> page) {
		Map<Object, Object> paramMap = SQLHelper.setPageParameter(ms, parameterObject, boundSql, page);
		if(boundSql.getSql().indexOf("LIMITSQL") < 0){
			paramMap.put(Constants.PAGEPARAMETER_FIRST, page.getStartRow());
		}else {
			paramMap.put(Constants.PAGEPARAMETER_FIRST, 0);
		}
		paramMap.put(Constants.PAGEPARAMETER_SECOND, page.getPageSize());
        return paramMap;
	}

}
