package top.wmgx.utils;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlUtils {
	public static Boolean isShowSql = Boolean
			.parseBoolean(PropertiesUtils.getPropertiesItem("dataSource", "isShowSql"));

	private static void setParams(PreparedStatement pres, Object... params) throws Exception {
		ParameterMetaData pmd;
		try {
			pmd = pres.getParameterMetaData();
			if (pmd.getParameterCount() != params.length) {
				throw new Exception("参数个数与目标参数个数匹配");
			}
			for (int i = 1; i <= pmd.getParameterCount(); i++) { // 依次添加参数
				pres.setObject(i, params[i - 1]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static List<Map<String, Object>> query(Connection conn, String sql, Object... params) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		PreparedStatement pres =null;
		ResultSet res=null;
		try {

			pres= conn.prepareStatement(sql);
			try {
				setParams(pres, params);
			} catch (Exception e) {
				throw e;
			}

			res = pres.executeQuery();
			ResultSetMetaData resm = res.getMetaData();
			
			int colCount = resm.getColumnCount();
			while (res.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= colCount; i++) {
					String s = resm.getColumnTypeName(i);
					if ("DATA".equals(s) || "DATATIME".equals(s)) {
						map.put(resm.getColumnName(i), res.getDate(i).toLocalDate());
						continue;
					}
					if ("TIMESTAMP".equals(s) || "DATATIME".equals(s)) {
						map.put(resm.getColumnName(i), res.getTimestamp(i).toLocalDateTime());
						continue;
					}
					map.put(resm.getColumnName(i), res.getObject(i));
				}
				result.add(map);
			}
			if(isShowSql) {
				LogUtils.logInfo("SQL 语句："+pres.toString().substring(pres.toString().indexOf(" ")+1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeRes(res);
			closePre(pres);
		}

		return result;

	}

	public static Integer update(Connection conn, String sql, Object... params) throws Exception {
		PreparedStatement pres  = null;
		try {

			pres= conn.prepareStatement(sql);
			try {
				setParams(pres, params);
			} catch (Exception e) {
				throw e;
			}

			if(isShowSql) {
				LogUtils.logInfo("SQL 语句："+pres.toString().substring(pres.toString().indexOf(" ")+1));
			}
			return pres.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}finally{
			closePre(pres);
		}

	}
	public static void closeRes(ResultSet res) {
		if(res!=null)
			try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public static void closePre(PreparedStatement pre) {
		if(pre!=null)
			try {
				pre.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
