package top.wmgx.utils;

/**
 * 字符串工具
 * 
 * @author 11092
 *
 */
public class StringUtils {

	/**
	 * 将字符串的首个字母转小写
	 * @param s 要转换的字符串
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
	public static boolean isNullOrEmpty(String s) {
		if(s == null)
			return true;
		if("".equals(s.trim())){
			return true;
		}
		return false;
	}
	
	public static boolean isAllNumber(String s) {
		for(char c: s.toCharArray())
			if(c<'0' || c>'9')
				return false;
		return true;
	}
}
