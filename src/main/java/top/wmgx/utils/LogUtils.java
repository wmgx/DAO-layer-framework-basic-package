package top.wmgx.utils;

public class LogUtils {
	private static int debugInfoLevel = Integer.parseInt(PropertiesUtils.getPropertiesItem("config", "debugInfoLevel"));

	public static void logDebug(String s) {
		if (debugInfoLevel <= 1) {
			System.out.println(TimeUtils.getNow() + "\t" + s);
		}
	}

	public static void logInfo(String s) {
		if (debugInfoLevel <= 2) {
			System.out.println(TimeUtils.getNow() + "\t" + s);
		}
	}

	public static void logWarn(String s) {
		if (debugInfoLevel <= 3) {
			System.out.println(TimeUtils.getNow() + "\t" + s);
		}
	}

	public static void logError(String s) {
		if (debugInfoLevel <= 4) {
			System.out.println(TimeUtils.getNow() + "\t" + s);
		}
	}
}
