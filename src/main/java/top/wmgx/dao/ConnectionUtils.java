package top.wmgx.dao;

import top.wmgx.utils.LogUtils;
import top.wmgx.utils.PropertiesUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;


import java.sql.Statement;


/**
 * 数据库连接的工具类，获取数据库连接
 * @author wmgx
 *
 */
public class ConnectionUtils {
	/**
	 * 数据库连接
	 */
	private static String dataBaseurl;
	/**
	 * 数据库Driver
	 */
	private static String driver;
	/**
	 * 用户名
	 */
	private static String password;
	/**
	 * 密码
	 */
	private static String user;
	/**
	 * 最大连接数
	 */
	private static Integer count;
	/**
	 * 激活的sql语句
	 */
	private static String validationQuery = "SELECT 1 FROM DUAL";
	/**
	 * 存储连接
	 */
	private static List<Connection> connections = new LinkedList<Connection>();
	/**
	 * 激活时间间隔
	 */
	private static Integer interval =6000;
	static {

		LogUtils.logInfo("ConnectionUtils开始配置");
		// 从配置文件读取配置参数
		Properties pro = PropertiesUtils.getProperties("dataSource");
		dataBaseurl = (String) pro.get("dataBaseurl");
		driver = (String) pro.get("driver");
		password = (String) pro.get("password");
		user = (String) pro.get("user");
		count = Integer.parseInt((String)pro.get("count"));
		validationQuery = (String) pro.get("validationQuery");
		interval = Integer.parseInt((String)pro.get("interval"));
		try {
			Class.forName(driver);
			// 加载指定个数的连接
			for (int i = 0; i < count; i++) {
				Connection conn = DriverManager.getConnection(dataBaseurl, user, password);
				connections.add(conn);
			}
			new Thread(new ConncetionKeepLive(interval)).start();
			
		} catch (SQLException|ClassNotFoundException e) {
			e.printStackTrace();
		}

		LogUtils.logInfo("ConnectionUtils配置完成");
	}
	/**
	 * 连接保活
	 */
	public static synchronized void activeConnections() {
		LogUtils.logInfo("链接保活开始");
		for (int i = 0; i < connections.size(); i++) {
			Connection  conn = connections.get(i);
			try {
				Statement st  = conn.createStatement();
				st.execute(validationQuery);
				st.close();
			} catch (SQLException e) {
				LogUtils.logError("发现死链接，正在获取新的链接");
				try {
					connections.set(i, DriverManager.getConnection(dataBaseurl, user, password));
					LogUtils.logError("获取连接成功");
				} catch (SQLException e1) {
					LogUtils.logError("发现死链接，获取新链接失败，请检查网络");
				}
			}
		}
		LogUtils.logInfo("链接保活结束");
		
	}
	/**
	 * 获取一个可用的数据库连接
	 * @return  数据库连接
	 */
	public static synchronized Connection getConnction() {
		// 当连接池中还有连接
		if(connections.size()!=0) {
			Connection conn = connections.get(0);
			Statement st;
			try {
				st = conn.createStatement();
				st.execute(validationQuery);
				st.close();
			} catch (SQLException e) {
				LogUtils.logError("发现死链接，自动获取新链接");
				try {
					return DriverManager.getConnection(dataBaseurl, user, password);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			connections.remove(0);
			return conn;
		}
		// 重新创建一个连接返回
		try {
			return DriverManager.getConnection(dataBaseurl, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 关闭连接（返回连接池）
	 * @param conn 要关闭的连接
	 */
	public static synchronized void closeConnection(Connection conn) {
		if(connections.size()==count) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		connections.add(conn);
	}
	@Override
	protected void finalize() throws Throwable {
		for (int i = 0; i < connections.size(); i++) {
			connections.get(i).close();
		}
		super.finalize();
	}
	
	
}
