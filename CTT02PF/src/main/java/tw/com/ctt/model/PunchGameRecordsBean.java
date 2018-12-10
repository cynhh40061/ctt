package tw.com.ctt.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PunchGameRecordsBean implements Serializable {
	private static final long serialVersionUID = -7208401469273318826L;
	private long gameId;
	private int serverId;
	private long accId;
	private String accName;
	private String startTime;
	private String endTime;
	private BigDecimal bet;
	private BigDecimal accStartBalance;
	private BigDecimal accWinGoal;
	private String gameProcess;
	private int gameType;
	private boolean result;

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public long getAccId() {
		return accId;
	}

	public void setAccId(long accId) {
		this.accId = accId;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getBet() {
		return bet;
	}

	public void setBet(BigDecimal bet) {
		this.bet = bet;
	}

	public BigDecimal getAccStartBalance() {
		return accStartBalance;
	}

	public void setAccStartBalance(BigDecimal accStartBalance) {
		this.accStartBalance = accStartBalance;
	}

	public BigDecimal getAccWinGoal() {
		return accWinGoal;
	}

	public void setAccWinGoal(BigDecimal accWinGoal) {
		this.accWinGoal = accWinGoal;
	}

	public String getGameProcess() {
		return gameProcess;
	}

	public void setGameProcess(String gameProcess) {
		this.gameProcess = gameProcess;
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

}
