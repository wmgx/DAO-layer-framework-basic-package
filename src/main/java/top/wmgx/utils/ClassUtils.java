package top.wmgx.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * 类操作工具类
 * @author wmgx
 *
 */
public class ClassUtils {
	/**
	 * 获取指定类的所有字段
	 * @param <T> T
	 * @param cla 指定Class
	 * @return
	 */
	public static <T> List<Field> getFields(Class<T> cla) {
		List<Field> list  = new ArrayList<Field>();
		Class c = cla;
		while(c!=Object.class) {
			for(Field f: c.getDeclaredFields()) {
				list.add(f);
			}
			c=c.getSuperclass();
		}
		return list;
		
	}
	/**
	 * 获取指定 类的所有set方法，并且在map中以其属性名作为其key索引
	 * @param <T>
	 * @param cla
	 * @return
	 */
	public static<T> Map<String,Method> getAllSetMethodsWithFieldName(Class<T> cla){
		 Map<String,Method> map = new HashMap<String, Method>();
		 getAllSetMethods(cla).stream().forEach(c->{
			 map.put(StringUtils.toLowerCaseFirstOne(c.getName().substring(3)), c);
		 });
		 return map;
	}
	/**
	 * 获取所有set方法
	 * @param <T>
	 * @param cla
	 * @return
	 */
	public static<T> List<Method> getAllSetMethods(Class<T> cla){

		
		return getAllMethods(cla).stream().filter(c->{
			return c.getName().startsWith("set");
		}).collect(Collectors.toList());
	}
	
	/**
	 * 获取所有方法
	 * @param <T>
	 * @param cla
	 * @return
	 */
	public static<T> List<Method> getAllMethods(Class<T> cla){
		List<Method> list  = new ArrayList<Method>();
		Class c = cla;
		while(c!=Object.class) {
			for(Method f: c.getDeclaredMethods()) {
				list.add(f);
			}
			c= c.getSuperclass();
		}
		return list;
		
	}
	/**
	 * 新建一个实例
	 * @param <T>
	 * @param cla
	 * @return
	 */
	public static <T> T newInstance(Class<T> cla) {
		try {
			return cla.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
