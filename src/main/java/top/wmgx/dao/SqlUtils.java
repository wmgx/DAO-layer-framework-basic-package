package top.wmgx.dao;

import top.wmgx.utils.LogUtils;
import top.wmgx.utils.PropertiesUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 包含执行sql的功能（查询和更新）
 * 
 * @author wmgx
 *
 */
public class SqlUtils {
	/**
	 * 是否显示日志，从配置文件读取
	 */
	public static Boolean isShowSql = Boolean
			.parseBoolean(PropertiesUtils.getPropertiesItem("dataSource", "isShowSql"));

	/**
	 * 设置参数
	 * 
	 * @param pres   PreparedStatement 对象
	 * @param params 参数们
	 * @throws Exception 参数个数和目标参数不匹配
	 */
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

	/**
	 * 
	 * @param conn   数据库连接
	 * @param sql    要执行的sql语句
	 * @param params 参数
	 * @return 返回一个Map<字段名,字段值>的列表
	 * @throws Exception
	 */
	public static List<Map<String, Object>> query(Connection conn, String sql, Object... params) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		PreparedStatement pres = null;
		ResultSet res = null;
		try {

			pres = conn.prepareStatement(sql);
			try {
				setParams(pres, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (isShowSql) {
				LogUtils.logInfo("SQL 语句：" + pres.toString().substring(pres.toString().indexOf(" ") + 1));
			}
			res = pres.executeQuery();
			ResultSetMetaData resm = res.getMetaData();

			int colCount = resm.getColumnCount();
			while (res.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= colCount; i++) {
					String s = resm.getColumnTypeName(i);
					Object o = res.getObject(i);
					if ("DECIMAL".equals(s)) {
						if(o!=null)
							map.put(resm.getColumnName(i), ((BigDecimal) o).doubleValue());
						else
							map.put(resm.getColumnName(i), 0.0);
						continue;
					}
					if(o == null) {
						map.put(resm.getColumnName(i), o);
						continue;
					}
					if ("DECIMAL".equals(s)) {
						map.put(resm.getColumnName(i), ((BigDecimal) o).doubleValue());
						continue;
					}
					if ("DATE".equals(s)) {
						map.put(resm.getColumnName(i), ((Date) o).toLocalDate());
						continue;
					}
					if ("TIMESTAMP".equals(s) || "DATETIME".equals(s)) {
						map.put(resm.getColumnName(i), ((Timestamp)o).toLocalDateTime());
						continue;
					}
					map.put(resm.getColumnName(i), o);
				}
				result.add(map);
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeRes(res);
			closePre(pres);
		}

		return result;

	}



	/**
	 * 执行更新语句，并且返回
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */

	public static Integer update(Connection conn, String sql, Object... params) {
		PreparedStatement pres = null;
		try {

			pres = conn.prepareStatement(sql);
			try {
				setParams(pres, params);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (isShowSql) {
				LogUtils.logInfo("SQL 语句：" + pres.toString().substring(pres.toString().indexOf(" ") + 1));
			}
			return pres.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			closePre(pres);
		}

	}

	/**
	 * 查询，以map的形式返回
	 * 
	 * @param sql    sql语句
	 * @param params 参数
	 * @return
	 */
	public static List<Map<String, Object>> query(String sql, Object... params) {
		Connection connection = ConnectionUtils.getConnction();

		List<Map<String, Object>> res = query(connection, sql, params);

		ConnectionUtils.closeConnection(connection);
		return res;

	}

	public static void closeRes(ResultSet res) {
		if (res != null)
			try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void closePre(PreparedStatement pre) {
		if (pre != null)
			try {
				pre.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
