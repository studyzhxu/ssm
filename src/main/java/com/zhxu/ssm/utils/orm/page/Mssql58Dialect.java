package com.zhxu.ssm.utils.orm.page;

public class Mssql58Dialect extends Dialect {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mybatis.extend.interceptor.IDialect#getLimitString(java.lang.String,
	 * int, int)
	 */
	@Override
	public String getLimitString(String sql, int offset, int limit) {
		sql = sql.trim().toLowerCase();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);

		if (sql.indexOf("distinct") > 0) {
			String order = "";
			pagingSelect.append("select * from (select row_number()over(order by tempcolumn)temprownumber,* ")
					.append(" from (select top ").append(offset + limit).append(" tempcolumn=0,* from ( ");

			// 暂不支持多排序结果合并查询
			// 判断是否order....结尾, 分页中排序需要拆分sql
			if (sql.indexOf("order by") > 0) {
				order = sql.substring(sql.lastIndexOf("order by"));
				if (order.indexOf(".") > 0) {
					order = "order by d" + order.substring(order.indexOf("."));
				}
				sql = sql.substring(0, sql.lastIndexOf("order by"));
			}

			pagingSelect.append(sql);
			pagingSelect.append(" ) d ").append(order).append(" ) t )tt where temprownumber>").append(offset);
		} else {
			pagingSelect.append("select * from (select row_number()over(order by tempcolumn)temprownumber,* ")
					.append(" from (select top ").append(offset + limit).append(" tempcolumn=0, ");

			sql = sql.substring(sql.indexOf("select") + 6);

			pagingSelect.append(sql);
			pagingSelect.append(" ) t )tt where temprownumber>").append(offset);
		}
		return pagingSelect.toString();
	}
}