package tw.com.ctt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CommissionRawBean implements Serializable {

	private static final long serialVersionUID = -1296936076581015076L;
	
	private long gameId;
	private long accId;
	private int accLevelType;
	private BigDecimal money;
	private String createTime;
	public long getGameId() {
		return gameId;
	}
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	public long getAccId() {
		return accId;
	}
	public void setAccId(long accId) {
		this.accId = accId;
	}
	public int getAccLevelType() {
		return accLevelType;
	}
	public void setAccLevelType(int accLevelType) {
		this.accLevelType = accLevelType;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "CommissionRawBean [gameId=" + gameId + ", accId=" + accId + ", accLevelType=" + accLevelType
				+ ", money=" + money + ", createTime=" + createTime + "]";
	}
	
	
	

}
