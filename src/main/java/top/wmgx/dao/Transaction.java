package top.wmgx.dao;

import top.wmgx.utils.LogUtils;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Transaction {
	private Connection connection;

	public Transaction() {
		this(ConnectionUtils.getConnction());
		LogUtils.logInfo(this.toString() + "事务建立");

	}

	public Transaction(Connection connection) {

		try {
			this.connection = connection;
			this.connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询封装成对应的类返回
	 * 
	 * @param <T>    要封装的类
	 * @param cla    要封装的类
	 * @param sql    sql语句
	 * @param params 参数
	 * @return
	 */
	public <T> List<T> query(Class<T> cla, String sql, Object... params) {
		return MapperUtils.query(cla, connection, sql, params);
	}

	/**
	 * 查询，以map的形式返回
	 * 
	 * @param sql    sql语句
	 * @param params 参数
	 * @return
	 */
	public List<Map<String, Object>> query(String sql, Object... params) {
		try {
			return SqlUtils.query(connection, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 返回刚刚插入的自动id是多少，仅在上一条是insert的时候有效
	 * 
	 * @return
	 */
	public int getInsertedAutoId() {
		return ((BigInteger) this.query("select  @@identity").get(0).get("@@identity")).intValue();
	}

	/**
	 * 更新
	 * 
	 * @param sql    sql语句
	 * @param params 参数
	 * @return
	 */
	public int update(String sql, Object... params) {
		return MapperUtils.update(connection, sql, params);
	}

	/**
	 * 提交
	 */
	public void commit() {
		try {
			this.connection.commit();
			System.gc();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 回滚
	 */
	public void rollback() {
		try {
			this.connection.rollback();
			System.gc();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		LogUtils.logInfo(this.toString() + "事务释放");
		this.connection.setAutoCommit(true);	
		ConnectionUtils.closeConnection(this.connection);
		super.finalize();
	}

}
