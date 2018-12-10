package tw.com.ctt.model;

import java.io.Serializable;
import java.util.Date;

public class LotteryAPIOpencaiBean implements Serializable{
	
	private static final long serialVersionUID = 2664853052108895507L;
	private String lotteryType;
	private String issue;
	private String data;
	private Date apiKjTime;
	
	public String getLotteryType() {
		return lotteryType.toLowerCase();
	}
	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Date getApiKjTime() {
		return apiKjTime;
	}
	public void setApiKjTime(Date apiKjTime) {
		this.apiKjTime = apiKjTime;
	}
	@Override
	public String toString() {
		return "LotteryAPIOpencaiBean [lotteryType=" + lotteryType + ", issue=" + issue + ", data=" + data
				+ ", apiKjTime=" + apiKjTime + "]";
	}
	
	

	
	
}
