package top.wmgx.utils.proxy;

import top.wmgx.utils.ClassUtils;
import top.wmgx.utils.MaxSizeHashMap;
import top.wmgx.utils.PropertiesUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Properties;



public class ClassUtilsProxy {
	private static MaxSizeHashMap<Class, List<Field>> fields;

	private static MaxSizeHashMap<Class, List<Method>> methods;

	private static MaxSizeHashMap<Class, List<Method>> setMethods;

	private static MaxSizeHashMap<Class, Map<String, Method>> setMethodsWithNumber;
	static {
		Properties pro = PropertiesUtils.getProperties("Cache");
		fields = new MaxSizeHashMap<Class, List<Field>>(Integer.valueOf(pro.getProperty("fieldsNumber")));
		methods = new MaxSizeHashMap<Class, List<Method>>(Integer.valueOf(pro.getProperty("methodsNumber")));
		setMethods = new MaxSizeHashMap<Class, List<Method>>(Integer.valueOf(pro.getProperty("setMethodsNumber")));
		setMethodsWithNumber = new MaxSizeHashMap<Class,  Map<String, Method>>(Integer.valueOf(pro.getProperty("setMethodsWithFieldNameNumber")));
	}
	
	public static <T> List<Field> getFields(Class<T> cla) {
		if(fields.containsKey(cla)) {
			return fields.get(cla);
		}
		fields.put(cla, ClassUtils.getFields(cla));
		return fields.get(cla);
	}
	
	public static<T> Map<String,Method> getAllSetMethodsWithFieldName(Class<T> cla){
		if(setMethodsWithNumber.containsKey(cla)) {
			return setMethodsWithNumber.get(cla);
		}
		setMethodsWithNumber.put(cla,ClassUtils.getAllSetMethodsWithFieldName(cla));
		return setMethodsWithNumber.get(cla);
	}
	
	public static<T> List<Method> getAllSetMethods(Class<T> cla){
		if(setMethods.containsKey(cla)) {
			return setMethods.get(cla);
		}
		setMethods.put(cla,ClassUtils.getAllSetMethods(cla));
		return setMethods.get(cla);
	}
	public static<T> List<Method> getAllMethods(Class<T> cla){
		if(methods.containsKey(cla)) {
			return methods.get(cla);
		}
		methods.put(cla,ClassUtils.getAllMethods(cla));
		return methods.get(cla);
	}
	public static <T> T newInstance(Class<T> cla) {
		return ClassUtils.newInstance(cla);
	}
}
