package tw.com.ctt.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class GamePuncheRecordsBean implements Serializable {

	private static final long serialVersionUID = 7368691759829494793L;
	
	
	private long gameId;
	@Override
	public String toString() {
		return "GamePuncheRecordsBean [gameId=" + gameId + ", p1AccId=" + p1AccId + ", p2AccId=" + p2AccId
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", bet=" + bet + ", p1AccStartBalance="
				+ p1AccStartBalance + ", p1AccWinGoal=" + p1AccWinGoal + ", p2AccStartBalance=" + p2AccStartBalance
				+ ", p2AccWinGoal=" + p2AccWinGoal + ", gameProcess=" + gameProcess + ", gameType=" + gameType
				+ ", result=" + result + "]";
	}
	private long p1AccId;
	private long p2AccId;
	private String startTime;
	private String endTime;
	private int bet;
	private BigDecimal p1AccStartBalance;
	private BigDecimal p1AccWinGoal;
	private BigDecimal p2AccStartBalance;
	private BigDecimal p2AccWinGoal;
	private String gameProcess;
	private int gameType;
	private boolean result;
	private int serverId ;
	
	public void close() {
		this.gameId  = 0;
		this.p1AccId = 0;
		this.p2AccId = 0;
		this.startTime = null;
		this.endTime = null;
		this.bet = 0;
		this.p1AccStartBalance = null;
		this.p1AccWinGoal = null;
		this.p2AccStartBalance = null;
		this.p2AccWinGoal = null;
		this.gameProcess = null;
		this.gameType = 0;
		this.result = false;
		this.serverId = 0;
	}
	
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public long getGameId() {
		return gameId;
	}
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	public long getP1AccId() {
		return p1AccId;
	}
	public void setP1AccId(long p1AccId) {
		this.p1AccId = p1AccId;
	}
	public long getP2AccId() {
		return p2AccId;
	}
	public void setP2AccId(long p2AccId) {
		this.p2AccId = p2AccId;
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

	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	public BigDecimal getP1AccStartBalance() {
		return p1AccStartBalance;
	}
	public void setP1AccStartBalance(BigDecimal p1AccStartBalance) {
		this.p1AccStartBalance = p1AccStartBalance;
	}
	public BigDecimal getP1AccWinGoal() {
		return p1AccWinGoal;
	}
	public void setP1AccWinGoal(BigDecimal p1AccWinGoal) {
		this.p1AccWinGoal = p1AccWinGoal;
	}
	public BigDecimal getP2AccStartBalance() {
		return p2AccStartBalance;
	}
	public void setP2AccStartBalance(BigDecimal p2AccStartBalance) {
		this.p2AccStartBalance = p2AccStartBalance;
	}
	public BigDecimal getP2AccWinGoal() {
		return p2AccWinGoal;
	}
	public void setP2AccWinGoal(BigDecimal p2AccWinGoal) {
		this.p2AccWinGoal = p2AccWinGoal;
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
