package tw.com.ctt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.util.GameDefaultCmds;

public class RoomInfo {
	private long keepAlive;
	private long keepAlivePlayer;
	protected List<PlayerInfoInGame> playerList;
	protected long gameId;	
	protected int gameState;
	protected int gameTimes;
	protected int realGameTimes = 0;
	protected long gameStateChangeTime;
	
	protected int waitPuncheTimeOut = 10;
	protected int waitContinueTimeOut = 10;
	protected int waitVideoTimeOut = 2;
	protected int waitGap = 1;
	protected int noWinnerTimeOut = 2;
	
	protected int broadCastTimerValue = 0;
	protected int playTimes = 0;
	
	protected Date gameStartTime = null;
	
	protected List<Map<String,Object>> gameProcessListMap = null;
	
	public static final long NO_WINNER = 0L;
	public static final String  ROOM_WINNER = "301";
	public static final String  ROOM_LOSER = "303";
	
	public static final int STATUS_EMPTY = 0;
	public static final int STATUS_ONE_PLAYER = 1;
	public static final int STATUS_TWO_PLAYER = 2;
	public static final int STATUS_WAIT_START_GAP = 3;
	public static final int STATUS_WAIT_START_ROUND_GAP = 4;
	public static final int STATUS_WAIT_PUNCHE = 5;
	public static final int STATUS_WAIT_PLAY_VIDEO = 6;
	public static final int STATUS_WAIT_ROUND_GAP = 7;
	public static final int STATUS_WAIT_CONTINUE = 8;
	
	
	private static final Logger LOG = LogManager.getLogger(RoomInfo.class.getName());

	
	public long getKeepAlivePlayer() {
		return keepAlivePlayer;
	}

	public void setKeepAlivePlayer(long keepAlivePlayer) {
		this.keepAlivePlayer = keepAlivePlayer;
	}

	public long getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(long keepAlive) {
		this.keepAlive = keepAlive;
	}

	public Date getGameStartTime() {
		return gameStartTime;
	}

	public void setGameStartTime(Date gameStartTime) {
		this.gameStartTime = gameStartTime;
	}

	public int getPlayTimes() {
		return playTimes;
	}

	public void setPlayTimes(int playTimes) {
		this.playTimes = playTimes;
	}
	
	public int getWaitPuncheTimeOut() {
		return waitPuncheTimeOut;
	}

	public void setWaitPuncheTimeOut(int waitPuncheTimeOut) {
		this.waitPuncheTimeOut = waitPuncheTimeOut;
	}

	public int getWaitContinueTimeOut() {
		return waitContinueTimeOut;
	}

	public void setWaitContinueTimeOut(int waitContinueTimeOut) {
		this.waitContinueTimeOut = waitContinueTimeOut;
	}

	public int getWaitVideoTimeOut() {
		return waitVideoTimeOut;
	}

	public void setWaitVideoTimeOut(int waitVideoTimeOut) {
		this.waitVideoTimeOut = waitVideoTimeOut;
	}

	public int getRealGameTimes() {
		return realGameTimes;
	}

	public void setRealGameTimes(int realGameTimes) {
		this.realGameTimes = realGameTimes;
	}

	public int getGameTimes() {
		return gameTimes;
	}

	public void setGameTimes(int gameTimes) {
		this.gameTimes = gameTimes;
	}
	
	public long getGameStateChangeTime() {
		return gameStateChangeTime;
	}

	public void setGameStateChangeTime(long gameStateChangeTime) {
		this.gameStateChangeTime = gameStateChangeTime;
	}

	public RoomInfo() {
		playerList = Collections.synchronizedList(new ArrayList<PlayerInfoInGame>());
		gameProcessListMap = new ArrayList<Map<String,Object>>();
	}
	
	public int getGameState() {
		return gameState;
	}
	
	public boolean isTimeOut() {
		return (this.getGameStateChangeTime() < System.currentTimeMillis());
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
			return (int) ((this.getGameStateChangeTime() - System.currentTimeMillis())/1000);
		}
		return 0;
	}
	
	public int getTimerValue() {
		return this.broadCastTimerValue;
	}

	public void setGameState(int gameState, long playPuncheResultState) {
		this.gameState = gameState;
		if(this.gameState == RoomInfo.STATUS_WAIT_PLAY_VIDEO) {
			if(playPuncheResultState == NO_WINNER) {
				this.setGameStateChangeTime(System.currentTimeMillis() + noWinnerTimeOut * 1000 + 999);
			}
			else {
				this.setGameStateChangeTime(System.currentTimeMillis() + waitVideoTimeOut * 1000 + 999);
			}
		}
	}
	
	
	public void setGameState(int gameState) {
		this.gameState = gameState;
		if(this.gameState == RoomInfo.STATUS_WAIT_START_GAP) {
			this.setGameStartTime(new Date());
			this.cleanGameProcessListMap();
			this.setGameStateChangeTime(System.currentTimeMillis() + waitGap * 1000 + 999);
		}else if(this.gameState == RoomInfo.STATUS_WAIT_START_ROUND_GAP) {
			this.setGameStateChangeTime(System.currentTimeMillis() + waitGap * 1000 + 999);
		}else if(this.gameState == RoomInfo.STATUS_WAIT_ROUND_GAP) {
			this.setGameStateChangeTime(System.currentTimeMillis() + waitGap * 1000 + 999);
		}else if(this.gameState == RoomInfo.STATUS_WAIT_PUNCHE) {
			this.setGameStateChangeTime(System.currentTimeMillis() + waitPuncheTimeOut * 1000 + 999);
//		}else if(this.gameState == RoomInfo.STATUS_WAIT_CONTINUE) {
//			this.setGameStateChangeTime(System.currentTimeMillis() + this.waitContinueTimeOut * 1000 + 999);
		}else if(this.gameState == RoomInfo.STATUS_WAIT_PLAY_VIDEO) {
			this.setGameStateChangeTime(System.currentTimeMillis() + waitVideoTimeOut * 1000 + 999);
		}
	}

	public void addPlayer(String name, long accId) {
		if(playerList.size() < 2) {
			PlayerInfoInGame p = new PlayerInfoInGame();
			p.setAccId(accId);
			p.setAccountName(name);
			p.setKeepPlayState(0);
			p.setPlayerPunches("none");
			playerList.add(p);
			
			if(playerList.size() == 1) {
				this.setGameState(RoomInfo.STATUS_ONE_PLAYER);
			}else if(playerList.size() == 2) {
				this.setGameState(RoomInfo.STATUS_TWO_PLAYER);
			}
		}
	}
	
	protected int whichWin(String s1, String s2) {
		if(GameDefaultCmds.PAPER.equals(s1) && GameDefaultCmds.ROCK.equals(s2)) {
			return 1;
		}
		if(GameDefaultCmds.SCISSOR.equals(s1) && GameDefaultCmds.PAPER.equals(s2)) {
			return 1;
		}
		if(GameDefaultCmds.ROCK.equals(s1) && GameDefaultCmds.SCISSOR.equals(s2)) {
			return 1;
		}		
		if(GameDefaultCmds.PAPER.equals(s2) && GameDefaultCmds.ROCK.equals(s1)) {
			return 2;
		}
		if(GameDefaultCmds.SCISSOR.equals(s2) && GameDefaultCmds.PAPER.equals(s1)) {
			return 2;
		}
		if(GameDefaultCmds.ROCK.equals(s2) && GameDefaultCmds.SCISSOR.equals(s1)) {
			return 2;
		}
		return 0;
	}
	
	public Map<Long, Integer> getResultCount(){
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		for(int i = 0; i < playerList.size();i++) {
			result.put(playerList.get(i).getAccId(), playerList.get(i).getWinTimes());
		}
		return result;
	}
	
	public void addGameTimes(){
		this.setRealGameTimes( this.getRealGameTimes() + 1);
	}
	
	public Map<String, Long> getFinalResult(){
		Map<String, Long> result = new HashMap<String, Long>();
		result.put(ROOM_WINNER, NO_WINNER);
		result.put(ROOM_LOSER, NO_WINNER);
		if(playerList.size() == 2) {
//			LOG.debug(playerList.get(0).getWinTimes()  +"   "+ playerList.get(1).getWinTimes() +"   "+this.getGameTimes());
			if((playerList.get(0).getWinTimes() > this.getGameTimes()/2) ||( playerList.get(1).getWinTimes() > this.getGameTimes()/2)) {
				if(playerList.get(0).getWinTimes() > playerList.get(1).getWinTimes()) {
					result.put(ROOM_WINNER, playerList.get(0).getAccId());
					result.put(ROOM_LOSER, playerList.get(1).getAccId());
				}else {
					result.put(ROOM_WINNER, playerList.get(1).getAccId());
					result.put(ROOM_LOSER, playerList.get(0).getAccId());
				}					
			}
		}
		return result;
	}
	
	public void updateResult() {
		addGameTimes();
		if(playerList.size() == 2) {
			Map<String , Object> gameProcessMap = new HashMap<String, Object>();
			PlayerInfoInGame p1 = playerList.get(0);
			PlayerInfoInGame p2 = playerList.get(1);	
			int winner = this.whichWin(p1.getPlayerPunches(), p2.getPlayerPunches());
			
//			gameProcessMap.put("p1", p1.getPlayerPunches());
//			gameProcessMap.put("p2", p2.getPlayerPunches());
			
			if(winner == 0) {
				gameProcessMap.clear();
			}else if(winner == 1){
				p1.setWinTimes(p1.getWinTimes()+1);
				gameProcessMap.put("winId", p1.getAccId());
				gameProcessMap.put("winName", p1.getAccountName());
				gameProcessMap.put("p1", p1.getPlayerPunches());
				gameProcessMap.put("p2", p2.getPlayerPunches());
				addGameProcessListMap(gameProcessMap);
			}else {
				p2.setWinTimes(p2.getWinTimes()+1);
				gameProcessMap.put("winId", p2.getAccId());
				gameProcessMap.put("winName", p2.getAccountName());
				gameProcessMap.put("p1", p2.getPlayerPunches());
				gameProcessMap.put("p2", p1.getPlayerPunches());
				addGameProcessListMap(gameProcessMap);
			}

		}
	}
	
	public void initPlayers() {
		for(int i = 0 ; i< playerList.size(); i++) {
			PlayerInfoInGame p = playerList.get(i);
			p.setKeepPlayState(0);
			p.setPlayerPunches("null");
			p.setWinTimes(0);			
		}	
	}
	
	public Map<String, Long> getResultIdList() {		
		Map<String, Long> result = new HashMap<String, Long>();
		if(playerList.size() == 2) {
			int winner = this.whichWin(playerList.get(0).getPlayerPunches(), playerList.get(1).getPlayerPunches());
			if(winner == 0) {
				result.put(ROOM_WINNER, NO_WINNER);
				result.put(ROOM_LOSER, NO_WINNER);
			}else if(winner == 1){
				result.put(ROOM_WINNER, playerList.get(0).getAccId());
				result.put(ROOM_LOSER, playerList.get(1).getAccId());
			}else {
				result.put(ROOM_WINNER, playerList.get(1).getAccId());
				result.put(ROOM_LOSER, playerList.get(0).getAccId());
			}
		}else if(playerList.size() == 1) {
			result.put(ROOM_WINNER, NO_WINNER);
			result.put(ROOM_LOSER, NO_WINNER);
		}else {
			result.put(ROOM_WINNER, NO_WINNER);
			result.put(ROOM_LOSER, NO_WINNER);
		}
		return result;
	}
	
	public void modifyPlayerPunche(long accId, String punche) {
		for(int i = 0;i < playerList.size();i++) {
			if(accId == playerList.get(i).getAccId()) {
				playerList.get(i).setPlayerPunches(punche);
			}
		}
	}
	
	public Map<Long, String> getPlayerPunche(){
			Map<Long, String> result = new HashMap<Long, String>();
			for(int i = 0; i < playerList.size();i++) {
				result.put(playerList.get(i).getAccId(), playerList.get(i).getPlayerPunches());
			}
			return result;
		}
	
	public List<Long> getHasPuncheList() {
		List<Long> result = new ArrayList<Long>();
		for(int i = 0;i < playerList.size();i++) {
			if(!"null".equals(playerList.get(i).getPlayerPunches())) {
				result.add(playerList.get(i).getAccId());
			}
		}
		return result;
	}
	
	public List<Long> getNoPuncheList() {
		List<Long> result = new ArrayList<Long>();
		for(int i = 0;i < playerList.size();i++) {
			if("null".equals(playerList.get(i).getPlayerPunches())) {
				result.add(playerList.get(i).getAccId());
			}
		}
		return result;
	}
	
	public void modifyPlayerKeepPlayState(long accId, int state) {
		for(int i = 0;i < playerList.size();i++) {
			if(accId == playerList.get(i).getAccId()) {
				playerList.get(i).setKeepPlayState(state);
			}
		}
	}
	
	public List<Long> getKeepPlayer() {
		List<Long> result = new ArrayList<Long>();
		for(int i = 0;i < playerList.size();i++) {
			if(playerList.get(i).getKeepPlayState() == PlayerInfo.CONTINUE_STATUS_YES) {
				result.add(playerList.get(i).getAccId());
			}
		}
		return result;
	}
	
	public List<Long> getNoKeepAndNoActionPlayer() {
		List<Long> result = new ArrayList<Long>();
		for(int i = 0;i < playerList.size();i++) {
			if(playerList.get(i).getKeepPlayState() != PlayerInfo.CONTINUE_STATUS_YES) {
				result.add(playerList.get(i).getAccId());
			}
		}
		return result;
	}
	
	public List<Long> getNoKeepPlayer() {
		List<Long> result = new ArrayList<Long>();
		for(int i = 0;i < playerList.size();i++) {
			if(playerList.get(i).getKeepPlayState() == PlayerInfo.CONTINUE_STATUS_NO) {
				result.add(playerList.get(i).getAccId());
			}
		}
		return result;
	}
	
	public List<String> getPlayerNameList() {
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < playerList.size(); i++) {
			result.add(playerList.get(i).getAccountName());
		}
		return result;
	}
	
	public List<Long> getPlayerIdList() {
		List<Long> result = new ArrayList<Long>();
		for(int i = 0; i < playerList.size(); i++) {
			result.add(playerList.get(i).getAccId());
		}
		return result;
	}
	
	public void removePlayer(long accId) {
		for(int i = 0;i < playerList.size();i++) {
			if(accId  == playerList.get(i).getAccId()) {
				playerList.remove(i);
			}
		}
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
		LOG.debug("Game Id : "+ this.gameId);
	}

	public List<Map<String, Object>> getGameProcessListMap() {
		return gameProcessListMap;
	}

	public void setGameProcessListMap(List<Map<String, Object>> gameProcessListMap) {
		this.gameProcessListMap = gameProcessListMap;
	}
	public void addGameProcessListMap(Map<String, Object> map) {
		this.gameProcessListMap.add(map);
	}
	
	public void cleanGameProcessListMap() {
		this.gameProcessListMap.clear();
	}
	
	
}
