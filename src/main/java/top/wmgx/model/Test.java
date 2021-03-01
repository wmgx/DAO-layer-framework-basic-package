package top.wmgx.model;

import java.util.HashMap;
import java.util.Map;

public class Test {
	// 当字段名和属性名不一致的时候，用这个做映射，字段名在前，属性名在后
	private static Map<String,String> fieldMap = new HashMap<String, String>(); ;
	
	private Integer rolesId;
	private String rolesName;
	private Integer borrowdNumber;
	private Integer borowedduration;
	private Integer postponeTimes;
	private Integer postponduration;
	static {
		fieldMap.put("borrowedNumber","borrowdNumber");
	}
	public Integer getRolesId() {
		return rolesId;
	}
	public void setRolesId(Integer rolesId) {
		this.rolesId = rolesId;
	}
	public String getRolesName() {
		return rolesName;
	}
	public void setRolesName(String rolesName) {
		this.rolesName = rolesName;
	}
	public Integer getBorrowdNumber() {
		return borrowdNumber;
	}
	public void setBorrowdNumber(Integer borrowdNumber) {
		this.borrowdNumber = borrowdNumber;
	}
	public Integer getBorowedduration() {
		return borowedduration;
	}
	public void setBorowedduration(Integer borowedduration) {
		this.borowedduration = borowedduration;
	}
	public Integer getPostponeTimes() {
		return postponeTimes;
	}
	public void setPostponeTimes(Integer postponeTimes) {
		this.postponeTimes = postponeTimes;
	}
	public Integer getPostponduration() {
		return postponduration;
	}
	public void setPostponduration(Integer postponduration) {
		this.postponduration = postponduration;
	}
	@Override
	public String toString() {
		return "Test [rolesId=" + rolesId + ", rolesName=" + rolesName + ", borrowdNumber=" + borrowdNumber
				+ ", borowedduration=" + borowedduration + ", postponeTimes=" + postponeTimes + ", postponduration="
				+ postponduration + "]";
	}
	
	
}
