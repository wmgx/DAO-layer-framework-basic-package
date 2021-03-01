package top.wmgx.dao;


import top.wmgx.utils.MapUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class MapperUtils {
	public static <T> List<T> query(Class<T> cla ,Connection conn,String sql,Object ...params){

		List<T> list = new ArrayList<T>();
	
		List<Map<String,Object>> res =SqlUtils.query(conn, sql, params);
		res.stream().forEach(map->{
			list.add(MapUtils.mapToBean(cla, map));
		});
			
		return list;
	}
	public static <T> List<T> query(Class<T> cla ,String sql,Object ...params){

		List<T> list = new ArrayList<T>();
	
		List<Map<String,Object>> res =SqlUtils.query(sql, params);
		res.stream().forEach(map->{
			list.add(MapUtils.mapToBean(cla, map));
		});
			
		return list;
	}
	public static int update(Connection conn,String sql , Object ...params) {
	
		int res =0;
		res =SqlUtils.update(conn, sql, params);
		
		return res;
	}
	
	public static int update(String sql , Object ...params) {
		
		Connection conn = ConnectionUtils.getConnction();
		int res =0;
		res =SqlUtils.update(conn, sql, params);
		ConnectionUtils.closeConnection(conn);
		return res;
	}


}
