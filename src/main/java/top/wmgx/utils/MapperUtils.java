package top.wmgx.utils;


import top.wmgx.dao.ConnectionUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MapperUtils {
	public static <T> List<T> query(Class<T> cla ,String sql,Object ...params){
		Connection conn = ConnectionUtils.getConnction();
		List<T> list = new ArrayList<T>();
		try {
			List<Map<String,Object>> res =SqlUtils.query(conn, sql, params);
			res.stream().forEach(map->{
				list.add(MapUtils.mapToBean(cla, map));
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionUtils.closeConnection(conn);
		}
		return list;
	}
	
	public static int update(String sql , Object ...params) {
		Connection conn =ConnectionUtils.getConnction();
		int res =0;
		try {
			res =SqlUtils.update(conn, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionUtils.closeConnection(conn);
		}
		return res;
	}
	
	

}
