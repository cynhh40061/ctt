package tw.com.ctt.model;

public class LotteryTimeSetBean implements java.io.Serializable {

	private static final long serialVersionUID = -8178131518786829645L;
	
	private int type;
	private int id;
	private int issue;
	private long kjTime;
	private long platformKjTime;
	private long stopBettingTime;
	private long giveUpKjTime;
	private int createPeriodWeekType;
	private int createWeekType;
	private long jumpOffTime;
	
	private int startBettingWeekType;
	private long createPeriodTime;
	private long startBettingTime;
	
	public LotteryTimeSetBean() {
		this.type = 0;
		this.id = 0;
		this.issue = 0;
		this.kjTime = 0;
		this.platformKjTime = 0;
		this.stopBettingTime = 0;
		this.giveUpKjTime = 0;
		this.createPeriodWeekType = 0;
		this.createWeekType = 0;
		this.jumpOffTime = 0;
		
		this.startBettingWeekType = 0;
		this.createPeriodTime = 0;
		this.startBettingTime = 0;
	}
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public long getPlatformKjTime() {
		return platformKjTime;
	}
	public void setPlatformKjTime(long platformKjTime) {
		this.platformKjTime = platformKjTime;
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
	public int getCreatePeriodWeekType() {
		return createPeriodWeekType;
	}
	public void setCreatePeriodWeekType(int createPeriodWeekType) {
		this.createPeriodWeekType = createPeriodWeekType;
	}
	public int getCreateWeekType() {
		return createWeekType;
	}
	public void setCreateWeekType(int createWeekType) {
		this.createWeekType = createWeekType;
	}
	public long getJumpOffTime() {
		return jumpOffTime;
	}
	public void setJumpOffTime(long jumpOffTime) {
		this.jumpOffTime = jumpOffTime;
	}
	public int getStartBettingWeekType() {
		return startBettingWeekType;
	}
	public void setStartBettingWeekType(int startBettingWeekType) {
		this.startBettingWeekType = startBettingWeekType;
	}
	public long getCreatePeriodTime() {
		return createPeriodTime;
	}
	public void setCreatePeriodTime(long createPeriodTime) {
		this.createPeriodTime = createPeriodTime;
	}
	public long getStartBettingTime() {
		return startBettingTime;
	}
	public void setStartBettingTime(long startBettingTime) {
		this.startBettingTime = startBettingTime;
	}


	@Override
	public String toString() {
		return "LotteryTimeSetBean [type=" + type + ", id=" + id + ", issue=" + issue + ", kjTime=" + kjTime
				+ ", platformKjTime=" + platformKjTime + ", stopBettingTime=" + stopBettingTime + ", giveUpKjTime="
				+ giveUpKjTime + ", createPeriodWeekType=" + createPeriodWeekType + ", createWeekType=" + createWeekType
				+ ", jumpOffTime=" + jumpOffTime + ", startBettingWeekType=" + startBettingWeekType
				+ ", createPeriodTime=" + createPeriodTime + ", startBettingTime=" + startBettingTime + "]";
	}
	
	
	
}
