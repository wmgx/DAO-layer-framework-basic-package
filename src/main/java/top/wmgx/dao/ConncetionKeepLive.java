package top.wmgx.dao;
/**
 * 数据库连接心跳检测
 * @author wmgx
 *
 */
public class ConncetionKeepLive implements  Runnable {
	/**
	 * 间隔时间
	 */
	private Integer interval=6000;
	/**
	 * 
	 * @param interval 触发间隔时间
	 */
	public ConncetionKeepLive(Integer interval) {
		this.interval = interval;
	}
	public void run() {
		while(true) {
		try {
			Thread.sleep(interval);
			ConnectionUtils.activeConnections();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
		
	}
}



