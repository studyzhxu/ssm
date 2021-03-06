package com.zhxu.ssm.utils.orm.page;

public abstract class Dialect {

	public static enum Type {
		MYSQL, ORACLE, DB2, MSSQL58, MSSQL12
	}

	public abstract String getLimitString(String sql, int skipResults, int maxResults);

}