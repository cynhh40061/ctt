package tw.com.ctt.model;

import java.io.Serializable;
import java.util.Date;

public class LotteryNumBean implements Serializable{
	private static final long serialVersionUID = 148847247329200445L;
	private int id;
	private long periodNum;
	private String data;
	private Date date;
	private Date kjTime;
	private Date actualKjTime;
	private Date apiKjTime;
	
	private Date giveUpKjTime;
	
	private String lotteryDate;
	private String kjDateTime;
	private String actualKjDateTime;
	
	private Date stopBettingTime;
	
	private Date startBettingTime;
	
	private Date expectedStartBettingTime;
	
	private int indexOfToday;
	
	private Date jumpOffTime;
	private int zodiacType;
	
	



	public int getZodiacType() {
		return zodiacType;
	}

	public void setZodiacType(int zodiacType) {
		this.zodiacType = zodiacType;
	}
	public int getIndexOfToday() {
		return indexOfToday;
	}

	public void setIndexOfToday(int indexOfToday) {
		this.indexOfToday = indexOfToday;
	}

	public Date getStartBettingTime() {
		return startBettingTime;
	}

	public void setStartBettingTime(Date startBettingTime) {
		this.startBettingTime = startBettingTime;
	}

	public Date getExpectedStartBettingTime() {
		return expectedStartBettingTime;
	}

	public void setExpectedStartBettingTime(Date expectedStartBettingTime) {
		this.expectedStartBettingTime = expectedStartBettingTime;
	}

	public Date getStopBettingTime() {
		return stopBettingTime;
	}

	public void setStopBettingTime(Date stopBettingTime) {
		this.stopBettingTime = stopBettingTime;
	}

	public Date getGiveUpKjTime() {
		return giveUpKjTime;
	}

	public void setGiveUpKjTime(Date giveUpKjTime) {
		this.giveUpKjTime = giveUpKjTime;
	}

	public String getLotteryDate() {
		return lotteryDate;
	}

	public void setLotteryDate(String lotteryDate) {
		this.lotteryDate = lotteryDate;
	}

	public String getKjDateTime() {
		return kjDateTime;
	}

	public void setKjDateTime(String kjDateTime) {
		this.kjDateTime = kjDateTime;
	}

	public String getActualKjDateTime() {
		return actualKjDateTime;
	}

	public void setActualKjDateTime(String actualKjDateTime) {
		this.actualKjDateTime = actualKjDateTime;
	}

	public LotteryNumBean() {
		this.id = 0;
		this.periodNum = 0;
		this.data = "";
		this.date = null;
		this.kjTime = null;
		this.actualKjTime = null;
		this.apiKjTime = null;
	}


	public long getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(long periodNum) {
		this.periodNum = periodNum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getApiKjTime() {
		return apiKjTime;
	}

	public void setApiKjTime(Date apiKjTime) {
		this.apiKjTime = apiKjTime;
	}

	public Date getActualKjTime() {
		return actualKjTime;
	}

	public void setActualKjTime(Date actualKjTime) {
		this.actualKjTime = actualKjTime;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getKjTime() {
		return kjTime;
	}

	public void setKjTime(Date kjTime) {
		this.kjTime = kjTime;
	}

	public Date getJumpOffTime() {
		return jumpOffTime;
	}

	public void setJumpOffTime(Date jumpOffTime) {
		this.jumpOffTime = jumpOffTime;
	}
	
	
	
	
}
