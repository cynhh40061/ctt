package tw.com.ctt.model;

import java.io.Serializable;

public class LotterySettingBean implements Serializable{
	private static final long serialVersionUID = -977959167785372598L;
	private int id;
	private int issue;
	private long kjTime;
	private long stopBettingTime;
	private long giveUpKjTime;
	private long platformKjTime;
	private long jumpOffTime;
	private long createWeekTime;
	private long startBettingWeekTime;
	private long startBettingTime;
	private int createPeriodWeekType;
	private long createPeriodTime;
	
	
	public LotterySettingBean() {
		this.id=0;
		this.issue=0;
		this.kjTime=0;
		this.stopBettingTime=0;
		this.giveUpKjTime=0;
		this.platformKjTime=0;
		this.jumpOffTime=0;
		this.createWeekTime=0;
		this.startBettingWeekTime=0;
		this.startBettingTime=0;
		this.createPeriodWeekType=0;
		this.createPeriodTime=0;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getIssue() {
		return issue;
	}


	public void setIssue(int issue) {
		this.issue = issue;
	}


	public long getKjTime() {
		return kjTime;
	}


	public void setKjTime(long kjTime) {
		this.kjTime = kjTime;
	}


	public long getStopBettingTime() {
		return stopBettingTime;
	}


	public void setStopBettingTime(long stopBettingTime) {
		this.stopBettingTime = stopBettingTime;
	}


	public long getGiveUpKjTime() {
		return giveUpKjTime;
	}


	public void setGiveUpKjTime(long giveUpKjTime) {
		this.giveUpKjTime = giveUpKjTime;
	}


	public long getPlatformKjTime() {
		return platformKjTime;
	}


	public void setPlatformKjTime(long platformKjTime) {
		this.platformKjTime = platformKjTime;
	}


	public long getJumpOffTime() {
		return jumpOffTime;
	}


	public void setJumpOffTime(long jumpOffTime) {
		this.jumpOffTime = jumpOffTime;
	}

	public long getCreateWeekTime() {
		return createWeekTime;
	}


	public void setCreateWeekTime(long createWeekTime) {
		this.createWeekTime = createWeekTime;
	}


	public long getStartBettingWeekTime() {
		return startBettingWeekTime;
	}


	public void setStartBettingWeekTime(long startBettingWeekTime) {
		this.startBettingWeekTime = startBettingWeekTime;
	}


	public long getStartBettingTime() {
		return startBettingTime;
	}


	public void setStartBettingTime(long startBettingTime) {
		this.startBettingTime = startBettingTime;
	}


	public int getCreatePeriodWeekType() {
		return createPeriodWeekType;
	}


	public void setCreatePeriodWeekType(int createPeriodWeekType) {
		this.createPeriodWeekType = createPeriodWeekType;
	}


	public long getCreatePeriodTime() {
		return createPeriodTime;
	}


	public void setCreatePeriodTime(long createPeriodTime) {
		this.createPeriodTime = createPeriodTime;
	}
	
	
}
