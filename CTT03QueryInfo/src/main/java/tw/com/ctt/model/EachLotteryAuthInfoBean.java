package tw.com.ctt.model;

import java.io.Serializable;

public class EachLotteryAuthInfoBean implements Serializable {
	private static final long serialVersionUID = 3157478025557305706L;
	private int authId;
	private String lotteryTitle;
	private int authLevelType;
	private String lotteryRule;
	public int getAuthId() {
		return authId;
	}
	public void setAuthId(int authId) {
		this.authId = authId;
	}
	public String getLotteryTitle() {
		return lotteryTitle;
	}
	public void setLotteryTitle(String lotteryTitle) {
		this.lotteryTitle = lotteryTitle;
	}
	public int getAuthLevelType() {
		return authLevelType;
	}
	public void setAuthLevelType(int authLevelType) {
		this.authLevelType = authLevelType;
	}
	public String getLotteryRule() {
		return lotteryRule;
	}
	public void setLotteryRule(String lotteryRule) {
		this.lotteryRule = lotteryRule;
	}
	
}
