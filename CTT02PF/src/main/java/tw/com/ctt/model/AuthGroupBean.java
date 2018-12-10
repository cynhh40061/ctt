package tw.com.ctt.model;

import java.io.Serializable;

public class AuthGroupBean implements Serializable {
	private static final long serialVersionUID = 4836805943141655017L;
	private int authId;
	private String authName;
	private String authRemark;
	private int authLevelType;
	private String url;
	private int checked;
	private int adminAuth;

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

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
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

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	public int getAdminAuth() {
		return adminAuth;
	}

	public void setAdminAuth(int adminAuth) {
		this.adminAuth = adminAuth;
	}
}
