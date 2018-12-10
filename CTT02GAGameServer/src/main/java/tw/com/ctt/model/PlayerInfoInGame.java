package tw.com.ctt.model;

public class PlayerInfoInGame {
	private long accId;
	private String accountName;
	private String playerPunches;
	private int keepPlayState;
	private int winTimes;
	
	public int getWinTimes() {
		return winTimes;
	}
	public void setWinTimes(int winTimes) {
		this.winTimes = winTimes;
	}
	public long getAccId() {
		return accId;
	}
	public void setAccId(long accId) {
		this.accId = accId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPlayerPunches() {
		return playerPunches;
	}
	public void setPlayerPunches(String playerPunches) {
		this.playerPunches = playerPunches;
	}
	public int getKeepPlayState() {
		return keepPlayState;
	}
	public void setKeepPlayState(int keepPlayState) {
		this.keepPlayState = keepPlayState;
	}
}
