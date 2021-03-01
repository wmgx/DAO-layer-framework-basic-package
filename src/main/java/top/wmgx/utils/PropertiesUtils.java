package top.wmgx.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * 配置文件工具类
 * @author 11092
 *
 */
public class PropertiesUtils {
	/**
	 * 存储已经读取的配置文件
	 */
	private static HashMap<String, Properties> properties = new HashMap<String, Properties>();
	/**
	 * 通过指定文件名，获取文件中的全部配置项
	 * @param fileName
	 * @return
	 */
	public static Properties getProperties(String fileName) {
		if(properties.containsKey(fileName))
			return properties.get(fileName);
		File f= new File(PropertiesUtils.class.getClassLoader().getResource(fileName+".properties").getPath());
    	Properties pro= new Properties();
    	try {
			pro.load(new BufferedInputStream(new FileInputStream(f)));
			properties.put(fileName, pro);
			return pro;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 通过配置文件名，和配置项关键字，返回值
	 * @param fileName 文件名
	 * @param key 关键字
	 * @return 值
	 */
	public static String getPropertiesItem(String fileName,String key) {
		Properties pro = getProperties(fileName);
		if(pro!=null)
			return pro.getProperty(key);
		return "";
	}
}
