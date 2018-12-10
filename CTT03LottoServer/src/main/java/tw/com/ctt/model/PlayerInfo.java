package tw.com.ctt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class PlayerInfo implements Serializable{
	private static final long serialVersionUID = 8433871948208435164L;
	private int accessWsIndex;
	private String accessFrom;
	private long accId;
	private String accountName;
	private long keepAlive;
	private int state;
	private int inGameState;
	private String playerPunches;
	private int keepPlayState;
	private int wantToLeaveState;
	private BigDecimal balance;
	
	private List<Map<String , Object>> ratioOfPlayer;

	
//	public static final int ACC_BALANCE_IN_GAME_NO  = 0;
//	public static final int ACC_BALANCE_IN_GAME_YES = 1;
	
	public static final int WANT_TO_LEAVE_NONE = 0;
	public static final int WANT_TO_LEAVE_YES = 1;
	
	public static final int CONTINUE_STATUS_NONE = 0;
	public static final int CONTINUE_STATUS_YES = 1;
	public static final int CONTINUE_STATUS_NO = 2;
	public static final int CONTINUE_STATUS_YES_BUT_OTHER_OPPONENT = 3;
	
	public static final int STATUS_OFFLINE = 0;	
	public static final int STATUS_ONLINE_IN_LOBBY = 1;
	public static final int STATUS_ROLE_OK = 2;
	public static final int STATUS_WAIT_ROOM = 3;
	public static final int STATUS_IN_ROOM_WAIT_START = 4;
	public static final int STATUS_WAIT_PUNCHE = 5;
	public static final int STATUS_ALREADY_PUNCHE = 6;
	public static final int STATUS_WAIT_CONTINUE = 7;
	public static final int STATUS_ALREADY_CONTINUE = 8;
	public static final int STATUS_NO_MONEY = 9;
	
	public static final int WANT_TO_ROBOT_STOP = 0;
	public static final int WANT_TO_ROBOT_START = 1;

	public static final int STATUS_IN_GAME = 1;
	public static final int STATUS_NOT_IN_GAME = 0;
	
	public static final int NO_ROOM_INDEX = -1;
	
	private int waitContinueTimeOut = 10;	
	private long stateChangeTime;
	private int broadCastTimerValue = 0;
	private int robotStatus = WANT_TO_ROBOT_STOP;
	private String playRole = "";
	
	private int roomIndex = NO_ROOM_INDEX;

	public void close() {
		inGameState = STATUS_NOT_IN_GAME;
		accessWsIndex = 0;
		accessFrom = "";
		accessFrom = null;
		accId = 0;
		accountName = "";
		accountName = null;
		keepAlive = 0;
		state = 0;
		playerPunches = "";
		playerPunches = null;
		keepPlayState = 0;
		wantToLeaveState = 0;
		balance = BigDecimal.ZERO;
		balance = null;
		waitContinueTimeOut = 0;
		stateChangeTime = 0;
		broadCastTimerValue = 0;
		robotStatus = 0;
		playRole = "";
		playRole = null;
		roomIndex = 0;
		ratioOfPlayer = null;
	}
	
	public List<Map<String, Object>> getRatioOfPlayer() {
		return ratioOfPlayer;
	}

	public void setRatioOfPlayer(List<Map<String, Object>> ratioOfPlayer) {
		this.ratioOfPlayer = ratioOfPlayer;
	}

	public int getRoomIndex() {
		return roomIndex;
	}

	public void setRoomIndex(int roomIndex) {
		this.roomIndex = roomIndex;
	}

	public int getInGameState() {
		return inGameState;
	}

	public void setInGameState(int inGameState) {
		this.inGameState = inGameState;
	}

	public String getPlayRole() {
		return playRole;
	}

	public void setPlayRole(String playRole) {
		this.playRole = playRole;
	}

	public int getRobotStatus() {
		return robotStatus;
	}

	public void setRobotStatus(int robotStatus) {
		this.robotStatus = robotStatus;
	}

	public int getWaitContinueTimeOut() {
		return waitContinueTimeOut;
	}
	
	public void setWaitContinueTimeOut(int waitContinueTimeOut) {
		this.waitContinueTimeOut = waitContinueTimeOut;
	}
	
	public int getWantToLeaveState() {
		return wantToLeaveState;
	}
	public void setWantToLeaveState(int wantToLeaveState) {
		this.wantToLeaveState = wantToLeaveState;
	}
	public int getAccessWsIndex() {
		return accessWsIndex;
	}
	public void setAccessWsIndex(int accessWsIndex) {
		this.accessWsIndex = accessWsIndex;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public long getAccId() {
		return accId;
	}
	public void setAccId(long accId) {
		this.accId = accId;
	}
	public String getAccessFrom() {
		return accessFrom;
	}
	public void setAccessFrom(String accessFrom) {
		this.accessFrom = accessFrom;
	}
	public int getKeepPlayState() {
		return keepPlayState;
	}
	public void setKeepPlayState(int keepPlayState) {
		this.keepPlayState = keepPlayState;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
		if(this.state == PlayerInfo.STATUS_WAIT_CONTINUE) {
			this.setStateChangeTime(System.currentTimeMillis() + this.waitContinueTimeOut * 1000 + 999);
		}
	}
	public String getPlayerPunches() {
		return playerPunches;
	}
	public void setPlayerPunches(String playerPunches) {
		this.playerPunches = playerPunches;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public long getKeepAlive() {
		return keepAlive;
	}
	public void setKeepAlive(long keepAlive) {
		this.keepAlive = keepAlive;
	}	
	
	public long getStateChangeTime() {
		return stateChangeTime;
	}
	
	public void setStateChangeTime(long stateChangeTime) {
		this.stateChangeTime = stateChangeTime;
	}

	
	public boolean isTimeOut() {
		return (this.getStateChangeTime() < System.currentTimeMillis());
	}
	
	public boolean isTimerChanged() {
		boolean broadCastTimerChanged = false;
		if(this.broadCastTimerValue != this.timeToTimeOutInSec()) {
			this.broadCastTimerValue = this.timeToTimeOutInSec();
			broadCastTimerChanged = true;
		}
		return broadCastTimerChanged;
	}
	
	public int timeToTimeOutInSec() {
		if(!this.isTimeOut()) {
			return (int) ((this.getStateChangeTime() - System.currentTimeMillis())/1000);
		}
		return 0;
	}
	
	public int getTimerValue() {
		return this.broadCastTimerValue;
	}
}
