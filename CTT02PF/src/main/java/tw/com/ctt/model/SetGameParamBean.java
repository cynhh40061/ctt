package tw.com.ctt.model;

public class SetGameParamBean implements java.io.Serializable {

	private static final long serialVersionUID = -8178131518786829645L;

	private int sid;
	private String gameServerId;
	private String platformId;

	private int gameTimesType;
	private int bet;
	private int gameTimeOut;
	private int waitContinueTimeOut;
	private int commission;
	private int continueTimesType;

	private int nextGameTimesType;
	private int nextBet;
	private int nextGameTimeOut;
	private int nextWaitContinueTimeOut;
	private int nextCommission;
	private int nextContinueTimesType;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getGameServerId() {
		return gameServerId;
	}

	public void setGameServerId(String gameServerId) {
		this.gameServerId = gameServerId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public int getGameTimesType() {
		return gameTimesType;
	}

	public void setGameTimesType(int gameTimesType) {
		this.gameTimesType = gameTimesType;
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public int getGameTimeOut() {
		return gameTimeOut;
	}

	public void setGameTimeOut(int gameTimeOut) {
		this.gameTimeOut = gameTimeOut;
	}

	public int getWaitContinueTimeOut() {
		return waitContinueTimeOut;
	}

	public void setWaitContinueTimeOut(int waitContinueTimeOut) {
		this.waitContinueTimeOut = waitContinueTimeOut;
	}

	public int getCommission() {
		return commission;
	}

	public void setCommission(int commission) {
		this.commission = commission;
	}

	public int getContinueTimesType() {
		return continueTimesType;
	}

	public void setContinueTimesType(int continueTimesType) {
		this.continueTimesType = continueTimesType;
	}

	public int getNextGameTimesType() {
		return nextGameTimesType;
	}

	public void setNextGameTimesType(int nextGameTimesType) {
		this.nextGameTimesType = nextGameTimesType;
	}

	public int getNextBet() {
		return nextBet;
	}

	public void setNextBet(int nextBet) {
		this.nextBet = nextBet;
	}

	public int getNextGameTimeOut() {
		return nextGameTimeOut;
	}

	public void setNextGameTimeOut(int nextGameTimeOut) {
		this.nextGameTimeOut = nextGameTimeOut;
	}

	public int getNextWaitContinueTimeOut() {
		return nextWaitContinueTimeOut;
	}

	public void setNextWaitContinueTimeOut(int nextWaitContinueTimeOut) {
		this.nextWaitContinueTimeOut = nextWaitContinueTimeOut;
	}

	public int getNextCommission() {
		return nextCommission;
	}

	public void setNextCommission(int nextCommission) {
		this.nextCommission = nextCommission;
	}

	public int getNextContinueTimesType() {
		return nextContinueTimesType;
	}

	public void setNextContinueTimesType(int nextContinueTimesType) {
		this.nextContinueTimesType = nextContinueTimesType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
