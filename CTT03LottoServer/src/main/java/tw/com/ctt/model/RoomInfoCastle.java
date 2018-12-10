package tw.com.ctt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.com.ctt.util.GameDefaultCmds;

public class RoomInfoCastle extends RoomInfo{
	
	public RoomInfoCastle() {
	}
	
	public void setGameState(int gameState) {
		this.gameState = gameState;
		if(this.gameState == RoomInfoCastle.STATUS_WAIT_START_GAP) {
			this.setGameStateChangeTime(System.currentTimeMillis() + waitGap * 1000 + 999);
		}else if(this.gameState == RoomInfoCastle.STATUS_WAIT_START_ROUND_GAP) {
			this.setGameStateChangeTime(System.currentTimeMillis() + waitGap * 1000 + 999);
		}else if(this.gameState == RoomInfoCastle.STATUS_WAIT_ROUND_GAP) {
			this.setGameStateChangeTime(System.currentTimeMillis() + waitGap * 1000 + 999);
		}else if(this.gameState == RoomInfoCastle.STATUS_WAIT_PUNCHE) {
			this.setGameStateChangeTime(System.currentTimeMillis() + waitPuncheTimeOut * 1000 + 999);
//		}else if(this.gameState == RoomInfo.STATUS_WAIT_CONTINUE) {
//			this.setGameStateChangeTime(System.currentTimeMillis() + this.waitContinueTimeOut * 1000 + 999);
		}else if(this.gameState == RoomInfoCastle.STATUS_WAIT_PLAY_VIDEO) {
			this.setGameStateChangeTime(System.currentTimeMillis() + waitVideoTimeOut * 1000 + 999);
		}
	}
	
	public Map<Long, Integer> getResultCount(){
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		for(int i = 0; i < playerList.size();i++) {
			result.put(playerList.get(i).getAccId(), playerList.get(i).getWinTimes());
		}
		return result;
	}	
	
	public Map<String, Long> getFinalResult(){
		Map<String, Long> result = new HashMap<String, Long>();
		result.put(ROOM_WINNER, NO_WINNER);
		result.put(ROOM_LOSER, NO_WINNER);
		if(playerList.size() == 2) {
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
		if(playerList.size() == 2) {
			int winner = this.whichWin(playerList.get(0).getPlayerPunches(), playerList.get(1).getPlayerPunches());
			if(winner == 0) {
			}else if(winner == 1){
				 playerList.get(0).setWinTimes( playerList.get(0).getWinTimes()+1);
			}else {
				playerList.get(1).setWinTimes( playerList.get(1).getWinTimes()+1);
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
}
