package com.redshark.common.page.dialect;

import com.redshark.common.page.util.SQLHelper;


/**
 * 
 * mysql的sql处理包,目前只支持分页操作的差异处理
 * @author jianyue.yan
 * @see
 * @version 1.0
 *
 */
public class MySqlDialect extends Dialect{
	
	protected static final String SQL_END_DELIMITER = ";";
	
/*	public String getLimitString(String sql, boolean hasOffset) 
	{
		return getLimitString(sql,-1,-1);
	}*/

	
	/**
	 * 得到分页的SQL
	 * @param offset 	偏移量
	 * @param limit		位置
	 * @return	分页SQL
	 */
	public String getLimitString(String sql, int offset, int limit) {
		String newsql = SQLHelper.getLineSql(sql);
		
		String returnSql =  newsql +" limit "+ offset +" ,"+ limit;
		
		return returnSql;
	}

	public boolean supportsLimit() {
		return true;
	}
	
}
