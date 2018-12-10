package tw.com.ctt.model;

import java.io.Serializable;

public class LotteryWorkBean implements Serializable{
	
	private static final long serialVersionUID = -4555820817062761029L;
	private int id;
	private String type;
	private String issueFormat;
	private int zodiacType;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIssueFormat() {
		return issueFormat;
	}
	public void setIssueFormat(String issueFormat) {
		this.issueFormat = issueFormat;
	}
	public int getZodiacType() {
		return zodiacType;
	}
	public void setZodiacType(int zodiacType) {
		this.zodiacType = zodiacType;
	}
	
	
}
