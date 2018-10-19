package com.thinkgem.jeesite.common.persistence.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 数据库分页插件，只拦截查询语句.
 * 
 * @author poplar.yfyang / thinkgem
 * @version 2013-8-28
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class PaginationInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		final Object[] args = invocation.getArgs();
		// 当前环境 MappedStatement,BoundSql及sql获取
		final MappedStatement mappedStatement = (MappedStatement) args[0];

		// //拦截需要分页的SQL
		Object parameter = args[1];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Object parameterObject = boundSql.getParameterObject();

		// 获取分页参数对象
		Page<Object> page = null;
		if (parameterObject != null) {
			page = convertParameter(parameterObject, page);
		}

		// 如果设置了分页对象，则进行分页
		if (page != null && page.getPageSize() != -1) {

			if (StringUtils.isBlank(boundSql.getSql())) {
				return null;
			}
			String originalSql = boundSql.getSql().trim();

			// 分页查询 本地化对象 修改数据库注意修改实现
			String pageSql = SQLHelper.generatePageSql(originalSql, page, DIALECT);
			if (page.isDefaultCount()) {
				page.setCount(SQLHelper.getCount(pageSql, null, mappedStatement, parameterObject, boundSql, log, page, DIALECT));
			}
			page.initialize();
			// if (log.isDebugEnabled()) {
			// log.debug("PAGE SQL:" + StringUtils.replace(pageSql, "\n", ""));
			// }
			args[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
			args[1] = SQLHelper.setPageParameter((MappedStatement) args[0], args[1], page, DIALECT);
			BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), pageSql, getPageParameterMapping(mappedStatement.getConfiguration(), boundSql), boundSql.getParameterObject());
			// 解决Mybatis 分页foreach 参数失效 start
			if (Reflections.getFieldValue(boundSql, "metaParameters") != null) {
				MetaObject mo = (MetaObject) Reflections.getFieldValue(boundSql, "metaParameters");
				Reflections.setFieldValue(newBoundSql, "metaParameters", mo);
			}
			// 解决Mybatis 分页foreach 参数失效 end
			MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
			args[0] = newMs;
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		super.initProperties(properties);
	}

	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties() != null) {
			for (String keyProperty : ms.getKeyProperties()) {
				builder.keyProperty(keyProperty);
			}
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		return builder.build();
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}
}
