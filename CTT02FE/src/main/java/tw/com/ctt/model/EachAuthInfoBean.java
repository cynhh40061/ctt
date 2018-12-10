package tw.com.ctt.model;

import java.io.Serializable;

public class EachAuthInfoBean implements Serializable {
	private static final long serialVersionUID = 3157478025557305706L;
	private int authId;
	public int getAuthId() {
		return authId;
	}
	public void setAuthId(int authId) {
		this.authId = authId;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public int getAuthLevelType() {
		return authLevelType;
	}
	public void setAuthLevelType(int authLevelType) {
		this.authLevelType = authLevelType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String authName;
	private int authLevelType;
	private String url;
}
