package top.wmgx.utils;

import top.wmgx.utils.proxy.ClassUtilsProxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Map 操作的工具类
 * @author wmgx
 *
 */
public class MapUtils {
	/**
	 * Map 转成指定类型的Bean
	 * @param <T> 指定类型
	 * @param cla 指定类型的Class
	 * @param map 转换的Map
	 * @return
	 */
	public static <T> T mapToBean(Class<T> cla, Map<String, Object> map) {
		T bean = ClassUtilsProxy.newInstance(cla);
		Map<String, Method> methos = ClassUtils.getAllSetMethodsWithFieldName(cla);
		Map<String, String> fieldMap = new HashMap<String, String>();
		try {
			// 获取 fieldMap 字段 
			Field field = cla.getDeclaredField("fieldMap");
			field.setAccessible(true);
			fieldMap = (Map<String, String>) field.get(bean);
			
		} catch (Exception e) {
			fieldMap = new HashMap<String, String>();
		}
		// 以模型作为标准
		for (String key:fieldMap.keySet()){
			if (map.containsKey(key)){
				// 更换 映射字段的key
				map.put(fieldMap.get(key),map.get(key));
				map.remove(key);
			}
		}
		methos.keySet().forEach(key->{
			if (map.containsKey(key)){
				try {
					methos.get(key).invoke(bean, map.get(key));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		});
		return bean;

		// 以结果作为标准；
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		list.add(fieldMap);
////		for(String key : map.keySet()) {
//		map.keySet().stream().forEach(key -> {
//			if (list.get(0).containsKey(key)) {
//				try {
//					if(methos.containsKey(list.get(0).get(key)))
//						methos.get(list.get(0).get(key)).invoke(bean, map.get(key));
//				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//					e.printStackTrace();
//				}
//			} else {
//				try {
//					if (methos.containsKey(key)) {
////						System.out.println(key+" "+map.get(key));
//						methos.get(key).invoke(bean, map.get(key));
//					}
//				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//					e.printStackTrace();
//				}
//			}
////		}
//		});

	}
	
	/**
	 * 主要是为了创建字段映射方便
	 * @param strings
	 * @return
	 */
	public static Map<Object,Object> createStringMap(Object ...objs){
		Map<Object,Object> res = new HashMap<Object, Object>();
		for(int i =0;i<objs.length;i++) {
			res.put(objs[i], objs[i+1]);
			i++;
		}
		return res;
	} 
}
