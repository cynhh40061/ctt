package tw.com.ctt.server;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.GameDaoImpl;
import tw.com.ctt.model.CommissionRawBean;
import tw.com.ctt.model.GamePuncheRecordsBean;
import tw.com.ctt.model.PlayerInfo;
import tw.com.ctt.model.RoomInfo;
import tw.com.ctt.model.WriteInfo;
import tw.com.ctt.service.impl.GameServiceImpl;
import tw.com.ctt.util.GameDefaultCmds;

public class GameServerThread extends Thread {

	private static final Logger LOG = LogManager.getLogger(GameServerThread.class.getName());

	List<GameServerWSClient> wsList;
	String myId;
	String bet;
	String maxPlayer;
	String gameTimesType;

	int waitVideoTimeOut = 4;
	int accountLiveTimeout = 30;
	int playerLiveTimeout = 5;
	int waitContinueTimeOut = 10;
	int waitPuncheTimeOut = 10;
	int autoPuncheTimeStamp = 7;
	int commission = 0;
	long gcTimer = 0;

	BigDecimal commissionRatio;

	static final int EXECUTE_PLAYER_LIST = 1;
	static final int EXECUTE_GAME_ROOM_LIST = 2;
	private int gameIdSerialization = 0;
	private int eachThread = 2;
	private int maxTimesPlayWithSamePlayer = 1;
	private long lastSerializationNumberDate = 0L;

	private Boolean isGameServerProcessShutDown = false;
	private Boolean isGameServerReadyShutDown = false;
	private Date calToShutDown = null;
	private Date calToShutDownByCmd = null;

	List<String> wsURLs;
	List<Long> playerList;
	ConcurrentHashMap<Long, PlayerInfo> playerMap;
	List<RoomInfo> roomList;
	List<WriteInfo> gameMessageQueue;
	List<String> cacheMessageQueue;
	GameServiceImpl GameDaoService;

	private boolean isStartGame = false;

	/**
	 * 建構式，帶入此thread 的 ID
	 * 
	 * @param id
	 *            辨別每個thread用的
	 */
	public GameServerThread(String id, String bet, String maxPlayer, String gameTimesType, String commission, String roomInEachThread,
			String accountLiveTimeout, String waitPuncheTimeOut, String autoPuncheTimeStamp, String waitContinueTimeOut, List<String> URLs,
			String continueType, String deadTime, String playerLiveTimeout) {
		this.wsURLs = URLs;
		this.myId = id;
		this.bet = bet;
		this.maxPlayer = maxPlayer;
		this.gameTimesType = gameTimesType;
		this.eachThread = Integer.parseInt(roomInEachThread);
		this.maxTimesPlayWithSamePlayer = Integer.parseInt(continueType);
		this.accountLiveTimeout = Integer.parseInt(accountLiveTimeout);
		this.waitContinueTimeOut = Integer.parseInt(waitContinueTimeOut);
		this.waitPuncheTimeOut = Integer.parseInt(waitPuncheTimeOut);
		this.autoPuncheTimeStamp = Integer.parseInt(autoPuncheTimeStamp);
		this.playerLiveTimeout = Integer.parseInt(playerLiveTimeout);

		this.commission = Integer.parseInt(commission);
		this.commissionRatio = new BigDecimal(this.commission).divide(new BigDecimal("100"));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.calToShutDown = sdf.parse(deadTime);
			this.calToShutDownByCmd = sdf.parse(deadTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		GameDaoImpl dao = new GameDaoImpl();
		GameDaoService = new GameServiceImpl();
		GameDaoService.setDao(dao);

		gameIdSerialization = GameDaoService.getPuncheGameLastSerialization(new Date(), Integer.parseInt(this.myId));

		// playerList = new CopyOnWriteArrayList<Integer>();
		playerMap = new ConcurrentHashMap<>();
		gameMessageQueue = new CopyOnWriteArrayList<WriteInfo>();
		cacheMessageQueue = new CopyOnWriteArrayList<String>();
		roomList = new CopyOnWriteArrayList<RoomInfo>();
	}

	/**
	 * GameMsgQueue 的內容是與遊戲相關的訊息，將會往ws server傳遞，並由其轉拋到指定ws client.
	 * 
	 * @return
	 */
	private int getGameMsgQueueSize() {
		return gameMessageQueue.size();
	}

	/**
	 * GameMsgQueue 的內容是與遊戲相關的訊息，將會往ws server傳遞，並由其轉拋到指定ws client.
	 * 
	 * @return 回傳 json
	 */
	private Map<String, Object> popGameMsgQueue() {
		Map<String, Object> result = new HashMap<String, Object>();
		WriteInfo w = gameMessageQueue.remove(0);
		Map<String, String> m = new HashMap<String, String>();
		m.put("to", w.getReceiver());
		m.put("msg", w.getMessage());
		m.put("cmd", w.getCommand());
		JSONObject responseJSONObject = new JSONObject(m);
		result.put("msg", responseJSONObject.toString());
		result.put("ws", w.getWeServerIndex());
		return result;
	}

	/**
	 * GameMsgQueue 的內容是與遊戲相關的訊息，將會往ws server傳遞，並由其轉拋到指定ws client.
	 * 
	 * @param receiver
	 *            傳遞的目標 ws client
	 * @param message
	 *            傳遞的內容
	 */
	private void insertGameMsgQueue(String receiver, String message, String command, int wsServerIndex) {
		WriteInfo w = new WriteInfo();
		w.setWeServerIndex(wsServerIndex);
		w.setReceiver(receiver);
		w.setMessage(message);
		w.setCommand(command);
		gameMessageQueue.add(w);
	}

	private void insertGameMsgQueueAllWS(String receiver, String message, String command) {
		for (int i = 0; i < wsList.size(); i++) {
			insertGameMsgQueue(receiver, message, command, i);
		}
	}

	/**
	 * 延長該玩家的過期時間
	 * 
	 * @param p
	 *            某個登入遊戲的玩家
	 */
	private void updateKeepAlive(PlayerInfo p) {
		long now = System.currentTimeMillis() + playerLiveTimeout * 1000;
		p.setKeepAlive(now);
	}

	/**
	 * 更新該玩家的金額 將所有金額取出(DB應該剩下0元)
	 * 
	 * @param p
	 */
	private void withdrawPlayerBalanceFromDB(PlayerInfo p) {
		this.isStartGame = true;
		p.setBalance(GameDaoService.withdrawAllMoneyToGame(p.getAccId(), this.myId , this.bet));
		// updateTmpMoneyTable(p);
	}

	private void updateTmpMoneyTable(PlayerInfo p) {
		GameDaoService.updateMoneyPuncheGameAccInfo(p.getAccId(), p.getBalance(), this.myId);
	}

	/**
	 * 更新該玩家的金額 將所剩餘額存回去(如果DB內有的話。會先加上去)
	 * 
	 * @param p
	 */
	private void depositPlayerBalanceFromDB(PlayerInfo p) {
		this.isStartGame = false;
		GameDaoService.depositAllMoneyFromGame(p.getAccId(), p.getBalance(), this.myId);
		BigDecimal balance = new BigDecimal("0");
		p.setBalance(balance);
		// updateTmpMoneyTable(p);
	}

	private boolean checkServerTimeOut() {
		if (isGameServerProcessShutDown) {
			return true;
		} else {
			if (new Date().after(this.calToShutDownByCmd)) {
				isGameServerProcessShutDown = true;
				return true;
			}
			return false;
		}
	}

	private boolean isServerReadyShutDown() {
		if (isGameServerReadyShutDown) {
			return true;
		} else {
			if (playerMap.isEmpty()) {
				boolean isRoomEmpty = true;
				for (int i = 0; i < roomList.size(); i++) {
					if (roomList.get(i).getPlayerIdList().size() != 0) {
						isRoomEmpty = isRoomEmpty & false;
					}
				}
				if (isRoomEmpty) {
					if (gameMessageQueue.size() == 0) {
						isGameServerReadyShutDown = true;
						return true;
					}
				}
			}
			return false;
		}
	}

	private void modifyShutDownTimeNow(String operator, long accId, int wsIndex) {
		Date tmpDate = new Date();
		if (tmpDate.before(this.calToShutDown)) {
			this.calToShutDownByCmd = tmpDate;
			insertGameMsgQueue(operator, generateGameCmd(GameDefaultCmds.GET_SHUTDOWN_TIME_OK, ""), GameDefaultCmds.NORMAL_CMD, wsIndex);
		} else {
			insertGameMsgQueue(operator, generateGameCmd(GameDefaultCmds.GET_SHUTDOWN_TIME_FAIL, "" + this.calToShutDown), GameDefaultCmds.NORMAL_CMD,
					wsIndex);
		}
	}

	private void modifyShutDownTime(String operator, long accId, int wsIndex, String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date tmpDate = null;
		try {
			tmpDate = sdf.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (tmpDate.before(this.calToShutDown)) {
			this.calToShutDownByCmd = tmpDate;
			insertGameMsgQueue(operator, generateGameCmd(GameDefaultCmds.GET_SHUTDOWN_TIME_OK, ""), GameDefaultCmds.NORMAL_CMD, wsIndex);
		} else {
			insertGameMsgQueue(operator, generateGameCmd(GameDefaultCmds.GET_SHUTDOWN_TIME_FAIL, "" + this.calToShutDown), GameDefaultCmds.NORMAL_CMD,
					wsIndex);
		}
	}

	/**
	 * 當有玩家下達 'join' command 到遊戲大廳，要將其帳號納入控管
	 * 
	 * @param operator
	 *            操作者的ws client
	 * @param accId
	 *            要被加入的 accId
	 */
	private void connectLobby(String operator, long accId, int wsIndex) {
		LOG.debug("connectLobbyconnectLobbyconnectLobbyconnectLobbyconnectLobbyconnectLobbyconnectLobbyconnectLobbyconnectLobbyconnectLobby");
		if (!checkServerTimeOut()) {
			PlayerInfo tmpPlayer = getPlayerInfoByAccId(accId);
			if (tmpPlayer != null) {
				insertGameMsgQueueAllWS(tmpPlayer.getAccessFrom(), "", GameDefaultCmds.REMOVE_CLIENT);
				tmpPlayer.setAccessFrom(operator);
				tmpPlayer.setAccessWsIndex(wsIndex);
				insertGameMsgQueueAllWS(tmpPlayer.getAccessFrom(), "", GameDefaultCmds.ADD_CLIENT);
				handleLeaveGame(accId);
				insertGameMsgQueue(tmpPlayer.getAccessFrom(), generateGameCmd(GameDefaultCmds.RECONNECT_SUCCESS, ""), GameDefaultCmds.NORMAL_CMD,
						tmpPlayer.getAccessWsIndex());
				insertGameMsgQueue(tmpPlayer.getAccessFrom(), generateGameCmd(GameDefaultCmds.MONEY, "" + tmpPlayer.getBalance()),
						GameDefaultCmds.NORMAL_CMD, tmpPlayer.getAccessWsIndex());
				insertGameMsgQueue(tmpPlayer.getAccessFrom(), generateGameCmd(GameDefaultCmds.NAME, tmpPlayer.getAccountName()),
						GameDefaultCmds.NORMAL_CMD, tmpPlayer.getAccessWsIndex());

				//updateTmpMoneyTable(tmpPlayer);

				int tmpRoomIndex = tmpPlayer.getRoomIndex();
				if (tmpRoomIndex != PlayerInfo.NO_ROOM_INDEX) {
					RoomInfo tmpRoom = roomList.get(tmpRoomIndex);
					List<Long> ll = tmpRoom.getPlayerIdList();
					showBasicRoomInfo(tmpPlayer, tmpRoom);
					showBasicInfoOfList(ll);
				}
			} else {
				List<Map<String, Object>> tmpList = null;
				tmpList = GameDaoService.getPlayer(accId);
				List<Map<String, Object>> ratioOfPlayer = GameDaoService.getMemberUpperAccRatio(accId, 5);
				if (tmpList.size() == 1) {
					PlayerInfo p = new PlayerInfo();
					p.setWaitContinueTimeOut(this.waitContinueTimeOut);
					p.setAccessFrom(operator);
					updateKeepAlive(p);
					p.setAccId(accId);
					p.setRatioOfPlayer(ratioOfPlayer);
					p.setInGameState(PlayerInfo.STATUS_NOT_IN_ROOM);
					p.setRobotStatus(PlayerInfo.WANT_TO_ROBOT_STOP);
					p.setAccountName("" + tmpList.get(0).get("accName"));
					p.setPlayerPunches("");
					p.setKeepPlayState(PlayerInfo.CONTINUE_STATUS_NONE);
					p.setWantToLeaveState(PlayerInfo.WANT_TO_LEAVE_NONE);
					p.setState(PlayerInfo.STATUS_ONLINE_IN_LOBBY);
					p.setAccessWsIndex(wsIndex);
					withdrawPlayerBalanceFromDB(p);

					if (p.getBalance().compareTo(new BigDecimal(this.bet)) != -1) {
						GameDaoService.addAccNowGameData(accId, Integer.parseInt(this.myId), "CTT", "三國猜拳王");
						playerMap.put(accId, p);
						insertGameMsgQueueAllWS(p.getAccessFrom(), "", GameDefaultCmds.ADD_CLIENT);
						insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.CONNECT_SUCCESS, ""), GameDefaultCmds.NORMAL_CMD,
								p.getAccessWsIndex());
						insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.MONEY, "" + p.getBalance()), GameDefaultCmds.NORMAL_CMD,
								p.getAccessWsIndex());
						insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.NAME, p.getAccountName()), GameDefaultCmds.NORMAL_CMD,
								p.getAccessWsIndex());
					} else {
						depositPlayerBalanceFromDB(p);
						insertGameMsgQueue(operator, generateGameCmd(GameDefaultCmds.NO_MONEY, "" + p.getBalance()), GameDefaultCmds.NORMAL_CMD,
								wsIndex);
					}
				} else {
					insertGameMsgQueue(operator, generateGameCmd(GameDefaultCmds.CONNECT_FAIL, "INVALID ACCOUNT"), GameDefaultCmds.NORMAL_CMD,
							wsIndex);
				}
			}
		} else {
			insertGameMsgQueue(operator, generateGameCmd(GameDefaultCmds.CONNECT_FAIL, "SEVER OFF"), GameDefaultCmds.NORMAL_CMD, wsIndex);
		}
	}

	/**
	 * 移除某個玩家，並一起移除在ws server註冊的該帳號
	 * 
	 * @param index
	 *            playerList的index
	 */
	private void disconnectLobby(PlayerInfo p) {
		p.setState(PlayerInfo.STATUS_OFFLINE);
		depositPlayerBalanceFromDB(p);
		insertGameMsgQueueAllWS(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.DISCONNECT_GAME_SERVER, ""), GameDefaultCmds.REMOVE_CLIENT);
	}

	/**
	 * 移除玩家
	 * 
	 * @param p
	 */
	private void removePlayerWhenOffLine(PlayerInfo p) {
		if (p.getState() == PlayerInfo.STATUS_OFFLINE) {
			GameDaoService.removeAccNowGameData(p.getAccId(), Integer.parseInt(this.myId));
			playerMap.remove(p.getAccId());
			p.close();
			p = null;
		}
	}

	/**
	 * 當有玩家下達 'match' command 到遊戲大廳，要幫她找到一個遊戲室，或建立一個給他
	 * 
	 * @param operator
	 *            操作者的ws client (用來回應)
	 * @param accId
	 *            要加入遊戲室的玩家帳號
	 */
	private void matchRoom(long accId) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		if (!checkServerTimeOut()) {
			if (p != null && p.getState() == PlayerInfo.STATUS_ROLE_OK) {
				if (checkPlayerEnoughMoney(p)) {
					p.setState(PlayerInfo.STATUS_WAIT_ROOM);
					insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.WAIT_ROOM, ""), GameDefaultCmds.NORMAL_CMD,
							p.getAccessWsIndex());
				} else {
					insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.NO_MONEY, "" + p.getBalance()), GameDefaultCmds.NORMAL_CMD,
							p.getAccessWsIndex());
				}
			}
		} else {
			insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.CONNECT_FAIL, "SEVER OFF"), GameDefaultCmds.NORMAL_CMD,
					p.getAccessWsIndex());
		}
	}

	/**
	 * 玩家下達leave game, 系統嘗試離開
	 * 
	 * @param accId
	 */
	private void handleLeaveGame(long accId) {
		handleleaveRoom(accId);
		leaveGame(accId);
	}

	/**
	 * 若狀態允許, 將玩家移除
	 * 
	 * @param accId
	 */
	private void leaveGame(long accId) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		if (p != null) {
			if (!isPlayerStateInRoom(p)) {
				p.setWantToLeaveState(PlayerInfo.WANT_TO_LEAVE_YES);
				insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.GET_LEAVE_GAME, "BYE"), GameDefaultCmds.NORMAL_CMD,
						p.getAccessWsIndex());
			}
		}
	}

	/**
	 * 收到開啟託管
	 * 
	 * @param accId
	 */
	private void handleStartRobot(long accId) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		if (p != null) {
			p.setRobotStatus(PlayerInfo.WANT_TO_ROBOT_START);
			insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.GET_START_ROBOT, ""), GameDefaultCmds.NORMAL_CMD,
					p.getAccessWsIndex());
		}
	}

	/**
	 * 收到取消託管
	 * 
	 * @param accId
	 */
	private void handleStopRobot(long accId) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		if (p != null) {
			p.setRobotStatus(PlayerInfo.WANT_TO_ROBOT_STOP);
			insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.GET_STOP_ROBOT, ""), GameDefaultCmds.NORMAL_CMD,
					p.getAccessWsIndex());
		}
	}

	/**
	 * 當有玩家下達 'leave' command 到遊戲大廳，如果還在等。要幫他離開
	 * 
	 * @param operator
	 *            操作者的ws client (用來回應)
	 * @param accId
	 *            要離開戲室的玩家帳號
	 */
	private void handleleaveRoom(long accId) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		if (p != null) {
			insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.GET_LEAVE_ROOM, ""), GameDefaultCmds.NORMAL_CMD,
					p.getAccessWsIndex());
			if (p.getState() == PlayerInfo.STATUS_WAIT_ROOM) {
				playerLeaveRoomAndInitParams(accId);
			} else if (p.getInGameState() == PlayerInfo.STATUS_IN_ROOM) {
				int tmpRoomIndex = p.getRoomIndex();
				if (tmpRoomIndex != PlayerInfo.NO_ROOM_INDEX) {
					removePlayerFromRoomIfNotStart(accId, tmpRoomIndex);
				}
			}
		}
	}

	/**
	 * 當有玩家下達 'punche' command 到遊戲大廳，如果此時正在等待出拳，則接收
	 * 
	 * @param operator
	 *            操作者的ws client (用來回應)
	 * @param accId
	 *            出拳的玩家帳號
	 * @param Punche
	 *            出拳內容
	 */
	private void handleGetPunche(long accId, String Punche) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		if (p != null && p.getState() == PlayerInfo.STATUS_WAIT_PUNCHE) {
			p.setPlayerPunches(Punche);
			p.setState(PlayerInfo.STATUS_ALREADY_PUNCHE);
			insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.GET_PUNCHE, Punche), GameDefaultCmds.NORMAL_CMD,
					p.getAccessWsIndex());
		}
	}

	private void handleGetRole(long accId, String playRole) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		if (p != null && p.getState() == PlayerInfo.STATUS_ONLINE_IN_LOBBY) {
			p.setPlayRole(playRole);
			p.setState(PlayerInfo.STATUS_ROLE_OK);
			insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.GET_ROLE, ""), GameDefaultCmds.NORMAL_CMD, p.getAccessWsIndex());
		}
	}

	private void handlePing(String operator, int wsIndex, String data) {
		if (data.equals(this.myId)) {
			insertGameMsgQueue(operator, generateGameCmd(GameDefaultCmds.PONG, ""), GameDefaultCmds.NORMAL_CMD, wsIndex);
		}
	}

	/**
	 * 當有玩家下達 'keepplay' command 到遊戲大廳，如果此時正在等待是否繼續，則接收
	 * 
	 * @param operator
	 *            操作者的ws client (用來回應)
	 * @param accId
	 *            玩家帳號
	 * @param state
	 *            是否繼續 1 繼續 2不繼續
	 */
	private void handlekeepPlay(long accId, String state) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		if (p != null && p.getState() == PlayerInfo.STATUS_WAIT_CONTINUE) {
			p.setKeepPlayState(Integer.parseInt(state));
			p.setState(PlayerInfo.STATUS_ALREADY_CONTINUE);
			insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.GET_KEEP_PLAY, state), GameDefaultCmds.NORMAL_CMD,
					p.getAccessWsIndex());
		}
	}

	/**
	 * 網頁端在join下達之後，會在5秒左右發一次心跳，當心跳間格超過設定的20秒會將玩家踢掉
	 * 
	 * @param operator
	 *            操作者的ws client (用來回應)
	 * @param accId
	 *            玩家帳號
	 */
	private void handleHeartbeat(long accId) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		// LOG.debug("HEART : " + p.getAccessFrom()+" "+p.getAccId());
		if (p != null) {
			updateKeepAlive(p);
		}
	}

	/**
	 * 更新一整個List 的玩家金額，並將每個玩家自身金額送出給該玩家
	 * 
	 * @param players
	 *            玩家列表 (通常來至於 room 物件)
	 */
	private void showBasicInfoOfList(List<Long> playersAccId) {
		if (playersAccId.size() == 2) {
			PlayerInfo acc1 = getPlayerInfoByAccId(playersAccId.get(0));
			PlayerInfo acc2 = getPlayerInfoByAccId(playersAccId.get(1));
			showBasicInfoOfSelfAndOpponent(acc1, acc2);
			showBasicInfoOfSelfAndOpponent(acc2, acc1);
		}
	}
	
	private void showBasicRoomInfo(PlayerInfo myAcc,RoomInfo r) {
		Map<String, Long> result = r.getFinalResult();
		if (result.get(RoomInfo.ROOM_WINNER) != RoomInfo.NO_WINNER) {
			BigDecimal commissionMoney = new BigDecimal(this.bet).multiply(commissionRatio);
			BigDecimal tmpMoney = new BigDecimal(this.bet).subtract(commissionMoney);
			if(myAcc.getAccId() == result.get(RoomInfo.ROOM_WINNER)) {
				insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.ADD_MONEY, "" + tmpMoney), GameDefaultCmds.NORMAL_CMD,
						myAcc.getAccessWsIndex());
			}else if(myAcc.getAccId() == result.get(RoomInfo.ROOM_LOSER)) {
				insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.SUB_MONEY, "" + tmpMoney), GameDefaultCmds.NORMAL_CMD,
						myAcc.getAccessWsIndex());
			}
		}
	}

	private void showBasicInfoOfSelfAndOpponent(PlayerInfo myAcc, PlayerInfo oppAcc) {
		if(myAcc.getState() ==PlayerInfo.STATUS_ALREADY_PUNCHE ) {
			if(!"null".equals(myAcc.getPlayerPunches()) || !"".equals(myAcc.getPlayerPunches())) {
				insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.GET_PUNCHE, myAcc.getPlayerPunches()), GameDefaultCmds.NORMAL_CMD,
						myAcc.getAccessWsIndex());
			}
		}
		if(myAcc.getRobotStatus()==PlayerInfo.WANT_TO_ROBOT_START) {
			insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.GET_START_ROBOT, ""), GameDefaultCmds.NORMAL_CMD,
					myAcc.getAccessWsIndex());
			showAutoPuncheRobotStatus(myAcc, oppAcc, true);
		}else {
			insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.GET_STOP_ROBOT, ""), GameDefaultCmds.NORMAL_CMD,
					myAcc.getAccessWsIndex());
			showAutoPuncheRobotStatus(myAcc, oppAcc, false);
		}
		insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.MONEY, "" + myAcc.getBalance()), GameDefaultCmds.NORMAL_CMD,
				myAcc.getAccessWsIndex());
		insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.NAME, myAcc.getAccountName()), GameDefaultCmds.NORMAL_CMD,
				myAcc.getAccessWsIndex());
		insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.OPPONENT_MONEY, "" + oppAcc.getBalance()),
				GameDefaultCmds.NORMAL_CMD, myAcc.getAccessWsIndex());
		insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.OPPONENT_NAME, hiddenSomeTextOfString(oppAcc.getAccountName())),
				GameDefaultCmds.NORMAL_CMD, myAcc.getAccessWsIndex());
		insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.OPPONENT_ROLE, oppAcc.getPlayRole()), GameDefaultCmds.NORMAL_CMD,
				myAcc.getAccessWsIndex());
		insertGameMsgQueue(myAcc.getAccessFrom(),
				generateGameCmd(GameDefaultCmds.MAX_LIVE_POINT, "" + (Integer.parseInt(this.gameTimesType) / 2 + 1)), GameDefaultCmds.NORMAL_CMD,
				myAcc.getAccessWsIndex());
	}

	private String hiddenSomeTextOfString(String s) {
		return "***" + s.substring(s.length() - 4, s.length());
	}

	/**
	 * 廣播給List 內的所有玩家
	 * 
	 * @param players
	 *            玩家列表 (通常來至於 room 物件)
	 * @param msg
	 *            要廣播的訊息
	 */
	private void brocastInList(List<Long> playersAccId, String msg) {
		for (int j = 0; j < playersAccId.size(); j++) {
			PlayerInfo p = getPlayerInfoByAccId(playersAccId.get(j));
			if (p != null) {
				insertGameMsgQueue(p.getAccessFrom(), msg, GameDefaultCmds.NORMAL_CMD, p.getAccessWsIndex());
			}
		}
	}

	/**
	 * 檢查JSON是否合法
	 * 
	 * @param text
	 *            要測試的內容
	 * @return 是否為一個JSON string
	 */
	public boolean isJSONValid(String text) {
		try {
			new JSONObject(text);
		} catch (JSONException ex) {
			try {
				new JSONArray(text);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 檢查一個玩家是否在線上
	 * 
	 * @param p
	 *            該玩家
	 * @return 是否在線上
	 */
	private boolean checkPlayerOnlineByTime(PlayerInfo p) {
		if (p != null) {
			if (p.getState() == PlayerInfo.STATUS_OFFLINE) {
				return false;
			} else {
				if ((System.currentTimeMillis() > p.getKeepAlive()) || (checkServerTimeOut())) {
					p.setState(PlayerInfo.STATUS_OFFLINE);
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean checkPlayerOnlineByTimeNotChangeState(PlayerInfo p) {
		if (p != null) {
			if (p.getState() == PlayerInfo.STATUS_OFFLINE) {
				return false;
			} else {
				if ((System.currentTimeMillis() > p.getKeepAlive()) || (checkServerTimeOut())) {
					return false;
				}
				return true;
			}
		}
		return false;
	}

	private boolean checkPlayerWantToLeaveState(PlayerInfo p) {
		if (p != null) {
			if (p.getWantToLeaveState() == PlayerInfo.WANT_TO_LEAVE_YES) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 將一個玩家與遊戲室關聯
	 * 
	 * @param accName
	 *            玩家名稱。用來顯示
	 * @param playerListId
	 *            玩家在此thread被放在playerList中的index
	 */
	private void insertPlayerToRoom(PlayerInfo p) {
		String accName = p.getAccountName();
		long accId = p.getAccId();
		boolean hasEmptyRoom = false;
		boolean entered = false;
		int roomIndex = 0;
		for (int i = 0; i < roomList.size(); i++) {
			if (roomList.get(i).getGameState() == RoomInfo.STATUS_ONE_PLAYER && accId != roomList.get(i).getPlayerIdList().get(0)) {
				roomList.get(i).addPlayer(accName, accId);

				long now = System.currentTimeMillis() + accountLiveTimeout * 1000;
				long nowPlayer = System.currentTimeMillis() + playerLiveTimeout * 1000;
				
				roomList.get(i).setKeepAlive(now);
				roomList.get(i).setKeepAlivePlayer(nowPlayer);

				hasEmptyRoom = true;
				entered = true;
				brocastInList(roomList.get(i).getPlayerIdList(), generateGameCmd(GameDefaultCmds.INTO_ROOM, ""));
				roomIndex = i;
				break;
			}
		}
		if (!entered) {
			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getGameState() == RoomInfo.STATUS_EMPTY) {
					roomList.get(i).addPlayer(accName, accId);

					long now = System.currentTimeMillis() + accountLiveTimeout * 1000;
					long nowPlayer = System.currentTimeMillis() + playerLiveTimeout * 1000;
					
					roomList.get(i).setKeepAlive(now);
					roomList.get(i).setKeepAlivePlayer(nowPlayer);

					hasEmptyRoom = true;
					brocastInList(roomList.get(i).getPlayerIdList(), generateGameCmd(GameDefaultCmds.INTO_ROOM, ""));
					roomIndex = i;
					break;
				}
			}
		}
		if (!hasEmptyRoom) {
			RoomInfo r = new RoomInfo();
			r.setWaitContinueTimeOut(this.waitContinueTimeOut);
			r.setWaitPuncheTimeOut(this.waitPuncheTimeOut);
			r.setWaitVideoTimeOut(this.waitVideoTimeOut);
			r.setGameTimes(Integer.parseInt(gameTimesType));
			r.setGameState(RoomInfo.STATUS_EMPTY);
			r.setPlayTimes(0);
			r.addPlayer(accName, accId);
			long now = System.currentTimeMillis() + accountLiveTimeout * 1000;
			long nowPlayer = System.currentTimeMillis() + playerLiveTimeout * 1000;
			r.setKeepAlive(now);
			r.setKeepAlivePlayer(nowPlayer);
			roomList.add(r);
			roomIndex = roomList.size() - 1;
			brocastInList(r.getPlayerIdList(), generateGameCmd(GameDefaultCmds.INTO_ROOM, ""));
		}
		p.setRoomIndex(roomIndex);
	}

	/**
	 * 將指定的玩家從還沒開始的遊戲中移除
	 * 
	 * @param accId
	 */
	private void removePlayerFromRoomIfNotStart(long accId, int roomIndex) {
		RoomInfo r = roomList.get(roomIndex);
		if (r.getGameState() == RoomInfo.STATUS_ONE_PLAYER && accId == r.getPlayerIdList().get(0)) {
			List<Long> allPlayer = r.getPlayerIdList();
			for (int j = 0; j < allPlayer.size(); j++) {
				r.removePlayer(allPlayer.get(j));
				playerLeaveRoomAndInitParams(allPlayer.get(j));
			}
			r.setGameState(RoomInfo.STATUS_EMPTY);
			r.setPlayTimes(0);
		}
	}

	/**
	 * 利用accId取得playerList的item
	 * 
	 * @param accId
	 * 
	 */
	private PlayerInfo getPlayerInfoByAccId(long accId) {
		if (playerMap.containsKey(accId)) {
			return playerMap.get(accId);
		} else {
			return null;
		}
	}

	/**
	 * 取得下一個room所使用的ID
	 * 
	 * @return
	 */
	private long getGameId() {
		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		long nowDate = 0;
		nowDate += (calendar.get(Calendar.YEAR)) * 10000;
		nowDate += (calendar.get(Calendar.MONTH) + 1) * 100;
		nowDate += calendar.get(Calendar.DAY_OF_MONTH);

		if (lastSerializationNumberDate != nowDate) {
			lastSerializationNumberDate = nowDate;
			if (gameIdSerialization == 0) {
				gameIdSerialization = 1;
			} else {
				gameIdSerialization = gameIdSerialization + 1;
			}
		} else {
			gameIdSerialization = gameIdSerialization + 1;
		}
		return lastSerializationNumberDate * 10000000000L + Integer.parseInt(this.myId) * 1000000 + gameIdSerialization;
	}

	/**
	 * 確認玩家是否在線上，沒有則移除，如果還在遊戲內，則先不將此玩家砍掉
	 */
	private void checkPlayerOnlineState(PlayerInfo p) {
		if (!isPlayerStateInRoom(p)) {
			if (!checkPlayerOnlineByTime(p)) {
				handleleaveRoom(p.getAccId());
				disconnectLobby(p);
			}
		}
	}

	private void checkPlayerWantToLeave(PlayerInfo p) {
		if (!isPlayerStateInRoom(p)) {
			if (checkPlayerWantToLeaveState(p)) {
				disconnectLobby(p);
			}
		}
	}

	/**
	 * 確認玩家狀態是否在遊戲內
	 * 
	 * @param p
	 * @return
	 */
	private boolean isPlayerStateInRoom(PlayerInfo p) {
		if (p.getInGameState() == PlayerInfo.STATUS_IN_ROOM) {
			return true;
		}
		return false;
	}

	/**
	 * 將選擇要進行遊戲的玩家分配至遊戲室
	 */
	private void distribuePlayerToRoom(PlayerInfo p) {
		if (p.getState() == PlayerInfo.STATUS_WAIT_ROOM) {
			p.setRobotStatus(PlayerInfo.WANT_TO_ROBOT_STOP);
			p.setState(PlayerInfo.STATUS_IN_ROOM_WAIT_START);
			p.setInGameState(PlayerInfo.STATUS_IN_ROOM);
			insertPlayerToRoom(p);
		}
	}

	private void checkPlayerWaitContinue(PlayerInfo p) {
		if (p.getState() == PlayerInfo.STATUS_WAIT_CONTINUE) {
			if (!checkPlayerEnoughMoney(p)) {
				insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.NO_MONEY, "" + p.getBalance()), GameDefaultCmds.NORMAL_CMD,
						p.getAccessWsIndex());
				p.setKeepPlayState(PlayerInfo.CONTINUE_STATUS_NO);
				p.setState(PlayerInfo.STATUS_ALREADY_CONTINUE);
			} else {
				if (p.isTimeOut()) {
					p.setKeepPlayState(PlayerInfo.CONTINUE_STATUS_NO);
					p.setState(PlayerInfo.STATUS_ALREADY_CONTINUE);
				} else {
					if (p.isTimerChanged()) {
						insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.WAIT_CONTINUE, "" + p.getTimerValue()),
								GameDefaultCmds.NORMAL_CMD, p.getAccessWsIndex());
					}
				}
			}
		}
	}

	/**
	 * 確認指定的玩家accId是否為某個狀態
	 * 
	 * @param accId
	 * @param state
	 * @return
	 */
	private boolean checkPlayerState(long accId, int state) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		if (p != null) {
			return (p.getState() == state);
		}
		return false;
	}

	private boolean checkPlayerEnoughMoney(PlayerInfo p) {
		if (p != null) {
			int result = p.getBalance().compareTo(new BigDecimal(this.bet));
			if (result >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 當玩家離開遊戲室，初始化其playerList內參數
	 * 
	 * @param accId
	 */
	private void playerLeaveRoomAndInitParams(long accId) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		if (p != null) {
			insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.IN_LOBBY, ""), GameDefaultCmds.NORMAL_CMD, p.getAccessWsIndex());
			p.setPlayerPunches("null");
			p.setKeepPlayState(PlayerInfo.CONTINUE_STATUS_NONE);
			p.setWantToLeaveState(PlayerInfo.WANT_TO_LEAVE_NONE);
			p.setInGameState(PlayerInfo.STATUS_NOT_IN_ROOM);
			p.setState(PlayerInfo.STATUS_ROLE_OK);

		}
	}

	/**
	 * 確認其他ws 傳來的訊息是否已經註冊(經由join command 可以註冊)
	 * 
	 * @param operator
	 * @param accId
	 * @return
	 */
	private boolean checkIfWebsocketIdRegisted(String operator, long accId) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		if (p != null) {
			if (operator.equals(p.getAccessFrom())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 處理所有game command
	 * 
	 * @param message
	 *            JSON格式的Command
	 */
	private void handleGameCmd(String message, int wsIndex) {
		// LOG.debug(wsIndex + " " + this.myId + " " + message);
		if (isJSONValid(message)) {
			JSONObject jsonObj = new JSONObject(message);
			String accessFrom = "";
			String accessCmd = "";
			long accId = 0;
			String tokenId = "";
			String data = "";
			try {
				accessFrom = jsonObj.getString("From");
				accessCmd = jsonObj.getString("Cmd");
				accId = jsonObj.getLong("AccId");
				tokenId = jsonObj.getString("TokenId");
				data = jsonObj.getString("Data");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (GameDaoService.checkMemberTokenTimeOut(tokenId, accId)) {
				if (GameDefaultCmds.SET_SHUTDOWN_TIME.equals(accessCmd)) {
					modifyShutDownTime(accessFrom, accId, wsIndex, data);
				} else if (GameDefaultCmds.SET_SHUTDOWN_TIME_NOW.equals(accessCmd)) {
					modifyShutDownTimeNow(accessFrom, accId, wsIndex);
				} else if (GameDefaultCmds.JOIN.equals(accessCmd)) {
					connectLobby(accessFrom, accId, wsIndex);
				} else if (checkIfWebsocketIdRegisted(accessFrom, accId)) {
					if (GameDefaultCmds.HEART_BEAT.equals(accessCmd)) {
						handleHeartbeat(accId);
					} else if (GameDefaultCmds.MATCH.equals(accessCmd)) {
						matchRoom(accId);
					} else if (GameDefaultCmds.PUNCHE.equals(accessCmd)) {
						handleGetPunche(accId, data);
					} else if (GameDefaultCmds.KEEP_PLAY.equals(accessCmd)) {
						handlekeepPlay(accId, data);
					} else if (GameDefaultCmds.LEAVE.equals(accessCmd)) {
						handleleaveRoom(accId);
					} else if (GameDefaultCmds.LEAVE_GAME.equals(accessCmd)) {
						handleLeaveGame(accId);
					} else if (GameDefaultCmds.START_ROBOT.equals(accessCmd)) {
						handleStartRobot(accId);
					} else if (GameDefaultCmds.STOP_ROBOT.equals(accessCmd)) {
						handleStopRobot(accId);
					} else if (GameDefaultCmds.SET_ROLE.equals(accessCmd)) {
						handleGetRole(accId, data);
					} else if (GameDefaultCmds.PING.equals(accessCmd)) {
						handlePing(accessFrom, wsIndex, data);
					}
				}
			} else {
				insertGameMsgQueue(accessFrom, generateGameCmd(GameDefaultCmds.CONNECT_FAIL, "ACCOUNT TIMEOUT"), GameDefaultCmds.NORMAL_CMD, wsIndex);
			}

		} else {
			LOG.debug("Not JSON");
		}
	}

	/**
	 * 取款
	 * 
	 * @param operator
	 * @param accId
	 * @param money
	 */
	private void withdraw(PlayerInfo p, BigDecimal money) {
		BigDecimal tmpBalance = p.getBalance().subtract(money);
		p.setBalance(tmpBalance);
		updateTmpMoneyTable(p);
	}

	/**
	 * 存款
	 * 
	 * @param operator
	 * @param accId
	 * @param money
	 */
	private void deposit(PlayerInfo p, BigDecimal money) {
		BigDecimal tmpBalance = money.add(p.getBalance());
		p.setBalance(tmpBalance);
		updateTmpMoneyTable(p);
	}

	/**
	 * 設定遊戲室內的所有玩家到指定狀態，並初始化遊戲室內玩家參數
	 * 
	 * @param r
	 * @param playerState
	 */
	private void changePlayerStateAndInitParamsInGame(RoomInfo r, int playerState) {
		List<Long> Ids = r.getPlayerIdList();
		for (int i = 0; i < Ids.size(); i++) {
			PlayerInfo p = getPlayerInfoByAccId(Ids.get(i));
			p.setState(playerState);
			r.modifyPlayerPunche(Ids.get(i), "null");
			r.modifyPlayerKeepPlayState(Ids.get(i), 0);
		}
	}

	/**
	 * 更新遊戲室內玩家的出拳狀態
	 * 
	 * @param r
	 */
	private void getPunchesOfRoom(RoomInfo r) {
		List<Long> Ids = r.getNoPuncheList();
		for (int i = 0; i < Ids.size(); i++) {
			if (checkPlayerState(Ids.get(i), PlayerInfo.STATUS_ALREADY_PUNCHE)) {
				getPunche(Ids.get(i), r);
			} else if ((r.getTimerValue() <= this.autoPuncheTimeStamp)
					&& (getPlayerInfoByAccId(Ids.get(i)).getRobotStatus() == PlayerInfo.WANT_TO_ROBOT_START)) {
				autoPunche(Ids.get(i), r);
			}
		}
	}

	/**
	 * 確認該遊戲室是否要開始遊戲
	 * 
	 * @param r
	 */
	private void checkReadyToStartGame(RoomInfo r) {
		if (r.getGameState() == RoomInfo.STATUS_TWO_PLAYER) {
			r.initPlayers();
			r.setPlayTimes(r.getPlayTimes() + 1);
			r.setGameId(getGameId());
			r.setGameState(RoomInfo.STATUS_WAIT_START_GAP);
			r.setRealGameTimes(0);
			showWinTimesOfMap(r.getResultCount());
			showBasicInfoOfList(r.getPlayerIdList());
		}
	}

	private void checkWaitRoomTooLong(RoomInfo r) {
		if (r.getGameState() == RoomInfo.STATUS_ONE_PLAYER) {
			List<Long> tmpList = r.getPlayerIdList();
			if (System.currentTimeMillis() > r.getKeepAlive()) {
				for (int i = 0; i < tmpList.size(); i++) {
					handleLeaveGame(tmpList.get(i));
				}
			}
			else {
				for (int i = 0; i < tmpList.size(); i++) {
					PlayerInfo p = getPlayerInfoByAccId(tmpList.get(i));
					if (!checkPlayerOnlineByTimeNotChangeState(p)) {
						if (System.currentTimeMillis() > r.getKeepAlivePlayer()) {
							if(p.getAccId() > 0) {
								handleLeaveGame(p.getAccId());
							}
						}
					}
				}
			}
			
		}
	}

	/**
	 * 正常取得玩家出拳資訊
	 * 
	 * @param accId
	 *            玩家id
	 * @param r
	 *            遊戲室
	 */
	private void getPunche(long accId, RoomInfo r) {
		PlayerInfo p = getPlayerInfoByAccId(accId);
		r.modifyPlayerPunche(accId, p.getPlayerPunches());
		if (p.getRobotStatus() == PlayerInfo.WANT_TO_ROBOT_START) {
			handleStopRobot(accId);
		}
		List<Long> tmpList = r.getPlayerIdList();
		if (tmpList.size() == 2) {
			if (tmpList.get(0) == accId) {
				showAutoPuncheRobotStatus(p, getPlayerInfoByAccId(tmpList.get(1)), false);
			} else {
				showAutoPuncheRobotStatus(p, getPlayerInfoByAccId(tmpList.get(0)), false);
			}
		}
	}

	/**
	 * 自動出拳
	 * 
	 * @param accId
	 *            玩家id
	 * @param r
	 *            遊戲室
	 */
	private void autoPunche(long accId, RoomInfo r) {
		int randomValue = (int) (Math.random() * 3 + 1);
		String tmpPunche = "";
		if (randomValue == 1) {
			tmpPunche = GameDefaultCmds.PAPER;
		} else if (randomValue == 2) {
			tmpPunche = GameDefaultCmds.SCISSOR;
		} else {
			tmpPunche = GameDefaultCmds.ROCK;
		}
		PlayerInfo p = getPlayerInfoByAccId(accId);
		insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.AUTO_PUNCHE, tmpPunche), GameDefaultCmds.NORMAL_CMD,
				p.getAccessWsIndex());
		r.modifyPlayerPunche(accId, tmpPunche);
		List<Long> tmpList = r.getPlayerIdList();
		if (tmpList.size() == 2) {
			if (tmpList.get(0) == accId) {
				showAutoPuncheRobotStatus(p, getPlayerInfoByAccId(tmpList.get(1)), true);
			} else {
				showAutoPuncheRobotStatus(p, getPlayerInfoByAccId(tmpList.get(0)), true);
			}
		}
	}

	/**
	 * 告訴對手此玩家是否電腦託管中
	 * 
	 * @param myAcc
	 *            此玩家
	 * @param oppAcc
	 *            對手
	 * @param status
	 *            電腦託管狀態
	 */
	private void showAutoPuncheRobotStatus(PlayerInfo myAcc, PlayerInfo oppAcc, boolean status) {
		if (status) {
			insertGameMsgQueue(oppAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.OPPONENT_GET_START_ROBOT, ""), GameDefaultCmds.NORMAL_CMD,
					oppAcc.getAccessWsIndex());
		} else {
			insertGameMsgQueue(oppAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.OPPONENT_GET_STOP_ROBOT, ""), GameDefaultCmds.NORMAL_CMD,
					oppAcc.getAccessWsIndex());
		}

	}

	/**
	 * 確認是否等待遊戲開始後的等待時間
	 * 
	 * @param r
	 */
	private void checkGameWaitGameStartGap(RoomInfo r) {
		if (r.getGameState() == RoomInfo.STATUS_WAIT_START_GAP) {
			if (r.isTimeOut()) {
				r.setGameState(RoomInfo.STATUS_WAIT_START_ROUND_GAP);
			} else {
				if (r.isTimerChanged()) {
					List<Long> allPlayer = r.getPlayerIdList();
					brocastInList(allPlayer, generateGameCmd(GameDefaultCmds.GAME_START, "" + r.getTimerValue()));
				}
			}
		}
	}

	/**
	 * 確認是否等待round後的等待時間
	 * 
	 * @param r
	 */
	private void checkGameWaitGameStartRoundGap(RoomInfo r) {
		if (r.getGameState() == RoomInfo.STATUS_WAIT_START_ROUND_GAP) {
			if (r.isTimeOut()) {
				List<Long> allPlayer = r.getPlayerIdList();
				brocastInList(allPlayer, generateGameCmd(GameDefaultCmds.REAL_PLAYTIMES, "" + r.getRealGameTimes()));
				changePlayerStateAndInitParamsInGame(r, PlayerInfo.STATUS_WAIT_PUNCHE);
				r.setGameState(RoomInfo.STATUS_WAIT_PUNCHE);
			} else {
				if (r.isTimerChanged()) {
					Map<Long, Integer> tmpResult = r.getResultCount();
					List<Long> l = new ArrayList<Long>(tmpResult.keySet());
					HashMap<String, String> m = new HashMap<String, String>();
					m.put("Count", "" + (1 + tmpResult.get(l.get(0)) + tmpResult.get(l.get(1))));
					m.put("Timer", "" + r.getTimerValue());
					JSONObject responseJSONObject = new JSONObject(m);
					brocastInList(r.getPlayerIdList(), generateGameCmd(GameDefaultCmds.ROUND, responseJSONObject.toString()));
				}
			}
		}
	}

	/**
	 * 將結果送到client, 會把自己的出拳帶在前面.
	 * 
	 * @param r
	 */
	private void showVideoInfoOfRoom(RoomInfo r) {
		int time = r.getTimerValue();
		if (time >= this.waitVideoTimeOut - 1) {
			Map<Long, String> punches = r.getPlayerPunche();
			Map<String, Long> result = r.getResultIdList();
			List<Long> players = r.getPlayerIdList();
			if (result.get(RoomInfo.ROOM_WINNER) != result.get(RoomInfo.ROOM_LOSER)) {
				PlayerInfo pWinner = getPlayerInfoByAccId(result.get(RoomInfo.ROOM_WINNER));
				PlayerInfo pLoser = getPlayerInfoByAccId(result.get(RoomInfo.ROOM_LOSER));
				HashMap<String, String> m = new HashMap<String, String>();
				m.put("videoSide", "0");
				m.put("puncheSelf", punches.get(pWinner.getAccId()));
				m.put("puncheOpp", punches.get(pLoser.getAccId()));
				m.put("RoleOpp", pLoser.getPlayRole());
				m.put("RolePlayer", pWinner.getPlayRole());
				m.put("Timer", "" + time);
				JSONObject responseJSONObject = new JSONObject(m);
				if (time == this.waitVideoTimeOut) {
					insertGameMsgQueue(pWinner.getAccessFrom(), generateGameCmd(GameDefaultCmds.PUNCHE_INFO, responseJSONObject.toString()),
							GameDefaultCmds.NORMAL_CMD, pWinner.getAccessWsIndex());
				} else {
					insertGameMsgQueue(pWinner.getAccessFrom(), generateGameCmd(GameDefaultCmds.VIDEO_INFO, responseJSONObject.toString()),
							GameDefaultCmds.NORMAL_CMD, pWinner.getAccessWsIndex());
				}

				m = new HashMap<String, String>();
				m.put("videoSide", "1");
				m.put("puncheSelf", punches.get(pLoser.getAccId()));
				m.put("puncheOpp", punches.get(pWinner.getAccId()));
				m.put("RoleOpp", pWinner.getPlayRole());
				m.put("Timer", "" + time);
				responseJSONObject = new JSONObject(m);
				if (time == this.waitVideoTimeOut) {
					insertGameMsgQueue(pLoser.getAccessFrom(), generateGameCmd(GameDefaultCmds.PUNCHE_INFO, responseJSONObject.toString()),
							GameDefaultCmds.NORMAL_CMD, pLoser.getAccessWsIndex());
				} else {
					insertGameMsgQueue(pLoser.getAccessFrom(), generateGameCmd(GameDefaultCmds.VIDEO_INFO, responseJSONObject.toString()),
							GameDefaultCmds.NORMAL_CMD, pLoser.getAccessWsIndex());
				}

			} else if (players.size() == 2) {
				PlayerInfo p1 = getPlayerInfoByAccId(players.get(0));
				PlayerInfo p2 = getPlayerInfoByAccId(players.get(1));

				HashMap<String, String> m = new HashMap<String, String>();
				m.put("videoSide", "2");
				m.put("puncheSelf", punches.get(p1.getAccId()));
				m.put("puncheOpp", punches.get(p2.getAccId()));
				m.put("RoleOpp", p2.getPlayRole());
				m.put("Timer", "" + time);
				JSONObject responseJSONObject = new JSONObject(m);
				if (time == this.waitVideoTimeOut) {
					insertGameMsgQueue(p1.getAccessFrom(), generateGameCmd(GameDefaultCmds.PUNCHE_INFO, responseJSONObject.toString()),
							GameDefaultCmds.NORMAL_CMD, p1.getAccessWsIndex());
				} else {
					insertGameMsgQueue(p1.getAccessFrom(), generateGameCmd(GameDefaultCmds.VIDEO_INFO, responseJSONObject.toString()),
							GameDefaultCmds.NORMAL_CMD, p1.getAccessWsIndex());
				}

				m = new HashMap<String, String>();
				m.put("videoSide", "2");
				m.put("puncheSelf", punches.get(p2.getAccId()));
				m.put("puncheOpp", punches.get(p1.getAccId()));
				m.put("RoleOpp", p1.getPlayRole());
				m.put("Timer", "" + time);
				responseJSONObject = new JSONObject(m);
				if (time == this.waitVideoTimeOut) {
					insertGameMsgQueue(p2.getAccessFrom(), generateGameCmd(GameDefaultCmds.PUNCHE_INFO, responseJSONObject.toString()),
							GameDefaultCmds.NORMAL_CMD, p2.getAccessWsIndex());
				} else {
					insertGameMsgQueue(p2.getAccessFrom(), generateGameCmd(GameDefaultCmds.VIDEO_INFO, responseJSONObject.toString()),
							GameDefaultCmds.NORMAL_CMD, p2.getAccessWsIndex());
				}
			}
		}
	}

	/**
	 * 確認是否遊戲室正在等待玩家出拳
	 * 
	 * @param r
	 */
	private void checkGameWaitPunche(RoomInfo r) {
		if (r.getGameState() == RoomInfo.STATUS_WAIT_PUNCHE) {
			if (r.isTimeOut() || r.getHasPuncheList().size() == 2) {
				List<Long> allPlayer = r.getPlayerIdList();
				brocastInList(allPlayer, generateGameCmd(GameDefaultCmds.PUNCHE_TIMEOUT, "0"));
				List<Long> noPunches = r.getNoPuncheList();
				for (int i = 0; i < noPunches.size(); i++) {
					handleStartRobot(noPunches.get(i));
					autoPunche(noPunches.get(i), r);
				}
				boolean showAni = true;
				r.updateResult();
				Map<String, Long> result = r.getResultIdList();
				if (result.get(RoomInfo.ROOM_WINNER) != result.get(RoomInfo.ROOM_LOSER)) {
					PlayerInfo p = getPlayerInfoByAccId(result.get(RoomInfo.ROOM_WINNER));
					insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.WINNER, ""), GameDefaultCmds.NORMAL_CMD,
							p.getAccessWsIndex());
					p = getPlayerInfoByAccId(result.get(RoomInfo.ROOM_LOSER));
					insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.LOSER, ""), GameDefaultCmds.NORMAL_CMD,
							p.getAccessWsIndex());
				} else {
					showAni = false;
					brocastInList(allPlayer, generateGameCmd(GameDefaultCmds.CMD_NO_WINNER, ""));
				}
				result = r.getFinalResult();
				if (result.get(RoomInfo.ROOM_WINNER) != RoomInfo.NO_WINNER) {
					PlayerInfo p = getPlayerInfoByAccId(result.get(RoomInfo.ROOM_WINNER));
					GamePuncheRecordsBean gamePuncheRecordsBean = new GamePuncheRecordsBean();
					BigDecimal commissionMoney = new BigDecimal(this.bet).multiply(commissionRatio);
					BigDecimal tmpMoney = new BigDecimal(this.bet).subtract(commissionMoney);
					Date gameEndTime = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					List<Map<String, Object>> tmpRatio = p.getRatioOfPlayer();

					List<CommissionRawBean> listOfCommissions = new ArrayList<CommissionRawBean>();

					for (int i = 0; i < tmpRatio.size(); i++) {
						CommissionRawBean commissionRawBean = new CommissionRawBean();
						Map<String, Object> tmpManager = tmpRatio.get(i);
						BigDecimal ratio = new BigDecimal("" + tmpManager.get("ratio")).divide(new BigDecimal("100"));

						commissionRawBean.setAccId(Long.parseLong("" + tmpManager.get("accId")));
						commissionRawBean.setAccLevelType(Integer.parseInt("" + tmpManager.get("accLevelType")));
						commissionRawBean.setCreateTime(sdf.format(r.getGameStartTime()));
						commissionRawBean.setGameId(r.getGameId());
						commissionRawBean.setMoney(commissionMoney.multiply(ratio));
						listOfCommissions.add(commissionRawBean);
					}

					if (p.getAccId() == allPlayer.get(0)) {
						gamePuncheRecordsBean.setResult(false);
						gamePuncheRecordsBean.setP1AccStartBalance(p.getBalance());
						gamePuncheRecordsBean.setP1AccWinGoal(tmpMoney);
					} else {
						gamePuncheRecordsBean.setResult(true);
						gamePuncheRecordsBean.setP2AccStartBalance(p.getBalance());
						gamePuncheRecordsBean.setP2AccWinGoal(tmpMoney);
					}
					deposit(p, tmpMoney);
					insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.ADD_MONEY, "" + tmpMoney), GameDefaultCmds.NORMAL_CMD,
							p.getAccessWsIndex());
					p = getPlayerInfoByAccId(result.get(RoomInfo.ROOM_LOSER));
					tmpMoney = new BigDecimal(this.bet);

					if (p.getAccId() == allPlayer.get(0)) {
						gamePuncheRecordsBean.setP1AccStartBalance(p.getBalance());
						gamePuncheRecordsBean.setP1AccWinGoal(tmpMoney.negate());
					} else {
						gamePuncheRecordsBean.setP2AccStartBalance(p.getBalance());
						gamePuncheRecordsBean.setP2AccWinGoal(tmpMoney.negate());
					}
					withdraw(p, tmpMoney);
					insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.SUB_MONEY, "" + tmpMoney), GameDefaultCmds.NORMAL_CMD,
							p.getAccessWsIndex());

					Map<String, Object> map = new ConcurrentHashMap<String, Object>();
					map.put("gameProcess", r.getGameProcessListMap());
					map.put("gameTimes", r.getRealGameTimes());

					JSONObject jsonGameProcess = new JSONObject(map);
					gamePuncheRecordsBean.setServerId(Integer.parseInt(this.myId));
					gamePuncheRecordsBean.setBet(Integer.parseInt(this.bet));
					gamePuncheRecordsBean.setEndTime(sdf.format(gameEndTime));
					gamePuncheRecordsBean.setGameId(r.getGameId());
					gamePuncheRecordsBean.setGameProcess(jsonGameProcess.toString());
					gamePuncheRecordsBean.setGameType(Integer.parseInt(this.gameTimesType));
					gamePuncheRecordsBean.setP1AccId(allPlayer.get(0));
					gamePuncheRecordsBean.setP2AccId(allPlayer.get(1));
					gamePuncheRecordsBean.setStartTime(sdf.format(r.getGameStartTime()));

					GameDaoService.insertGamePuncheRecords(gamePuncheRecordsBean, listOfCommissions);

				}
				showWinTimesOfMap(r.getResultCount());

				
				
				if (showAni) {
					r.setGameState(RoomInfo.STATUS_WAIT_PLAY_VIDEO);
				} else {
					r.setGameState(RoomInfo.STATUS_WAIT_PLAY_VIDEO,result.get(RoomInfo.ROOM_WINNER));
				}

			} else {
				if (r.isTimerChanged()) {
					List<Long> allPlayer = r.getPlayerIdList();
					getPunchesOfRoom(r);
					brocastInList(allPlayer, generateGameCmd(GameDefaultCmds.WAIT_PUNCHE, "" + r.getTimerValue()));
				}
			}
		}
	}

	/**
	 * 合成遊戲command的地方
	 * 
	 * @param cmd
	 * @param data
	 * @return json String
	 */
	private String generateGameCmd(String cmd, String data) {
		HashMap<String, String> m = new HashMap<String, String>();
		m.put("Cmd", cmd);
		m.put("Data", data);
		JSONObject responseJSONObject = new JSONObject(m);
		return responseJSONObject.toString();
	}

	/**
	 * 確認是否在等待前端播放動畫
	 * 
	 * @param r
	 */
	private void checkGameWaitPlayVideo(RoomInfo r) {
		if (r.getGameState() == RoomInfo.STATUS_WAIT_PLAY_VIDEO) {
			List<Long> allPlayer = r.getPlayerIdList();
			if (r.isTimeOut()) {
				brocastInList(allPlayer, generateGameCmd(GameDefaultCmds.VIDEO_TIMEOUT, "0"));
				Map<String, Long> result = r.getFinalResult();
				// LOG.debug(result.get(RoomInfo.ROOM_WINNER) + " " +
				// RoomInfo.NO_WINNER);
				if (result.get(RoomInfo.ROOM_WINNER) != RoomInfo.NO_WINNER) {
					brocastInList(allPlayer, generateGameCmd(GameDefaultCmds.GAME_OVER, "1"));
					r.setGameState(RoomInfo.STATUS_WAIT_CONTINUE);
					changePlayerStateAndInitParamsInGame(r, PlayerInfo.STATUS_WAIT_CONTINUE);
				} else {
					brocastInList(allPlayer, generateGameCmd(GameDefaultCmds.GAME_OVER, "0"));
					brocastInList(allPlayer, generateGameCmd(GameDefaultCmds.REAL_PLAYTIMES, "" + r.getRealGameTimes()));
					r.setGameState(RoomInfo.STATUS_WAIT_ROUND_GAP);
				}
			} else {
				if (r.isTimerChanged()) {
					showVideoInfoOfRoom(r);
				}
			}
		}
	}

	/**
	 * 確認是否等待每round之間的等待時間
	 * 
	 * @param r
	 */
	private void checkGameWaitGameRoundGap(RoomInfo r) {
		if (r.getGameState() == RoomInfo.STATUS_WAIT_ROUND_GAP) {
			if (r.isTimeOut()) {
				// showWinTimesOfMap(r.getResultCount());
				r.setGameState(RoomInfo.STATUS_WAIT_PUNCHE);
				changePlayerStateAndInitParamsInGame(r, PlayerInfo.STATUS_WAIT_PUNCHE);
			} else {
				if (r.isTimerChanged()) {
					Map<Long, Integer> tmpResult = r.getResultCount();
					List<Long> l = new ArrayList<Long>(tmpResult.keySet());

					HashMap<String, String> m = new HashMap<String, String>();
					m.put("Count", "" + (1 + tmpResult.get(l.get(0)) + tmpResult.get(l.get(1))));
					m.put("Timer", "" + r.getTimerValue());
					JSONObject responseJSONObject = new JSONObject(m);
					brocastInList(r.getPlayerIdList(), generateGameCmd(GameDefaultCmds.ROUND, responseJSONObject.toString()));
				}
			}
		}
	}

	/**
	 * 將目前贏的次數送到client
	 * 
	 * @param tmpResult
	 */
	private void showWinTimesOfMap(Map<Long, Integer> tmpResult) {
		List<Long> l = new ArrayList<Long>(tmpResult.keySet());
		if (l.size() == 2) {
			PlayerInfo acc1 = getPlayerInfoByAccId(l.get(0));
			PlayerInfo acc2 = getPlayerInfoByAccId(l.get(1));
			showWinTimesInfoOfSelfAndOpponent(acc1, acc2, tmpResult.get(l.get(0)), tmpResult.get(l.get(1)));
			showWinTimesInfoOfSelfAndOpponent(acc2, acc1, tmpResult.get(l.get(1)), tmpResult.get(l.get(0)));
		}
	}

	/**
	 * 將目前贏的次數送到client, 會根據玩家_對手 的方式排序
	 * 
	 * @param myAcc
	 * @param oppAcc
	 * @param myTimes
	 * @param oppTimes
	 */
	private void showWinTimesInfoOfSelfAndOpponent(PlayerInfo myAcc, PlayerInfo oppAcc, int myTimes, int oppTimes) {
		insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.MONEY, "" + myAcc.getBalance()), GameDefaultCmds.NORMAL_CMD,
				myAcc.getAccessWsIndex());
		insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.WIN_TIMES, "" + myTimes), GameDefaultCmds.NORMAL_CMD,
				myAcc.getAccessWsIndex());
		insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.OPPONENT_MONEY, "" + oppAcc.getBalance()),
				GameDefaultCmds.NORMAL_CMD, myAcc.getAccessWsIndex());
		insertGameMsgQueue(myAcc.getAccessFrom(), generateGameCmd(GameDefaultCmds.OPPONENT_WIN_TIMES, "" + oppTimes), GameDefaultCmds.NORMAL_CMD,
				myAcc.getAccessWsIndex());
		insertGameMsgQueue(myAcc.getAccessFrom(),
				generateGameCmd(GameDefaultCmds.MAX_LIVE_POINT, "" + (Integer.parseInt(this.gameTimesType) / 2 + 1)), GameDefaultCmds.NORMAL_CMD,
				myAcc.getAccessWsIndex());
	}

	/**
	 * 遊戲室等待玩家確認是否繼續進行遊戲
	 * 
	 * @param r
	 */
	private void checkGameWaitKeepPlay(RoomInfo r) {
		if (r.getGameState() == RoomInfo.STATUS_WAIT_CONTINUE) {
			List<Long> ll = r.getPlayerIdList();
			for (int i = 0; i < ll.size(); i++) {
				PlayerInfo p = playerMap.get(ll.get(i));

				if (checkServerTimeOut()) {
					handlekeepPlay(p.getAccId(), "" + PlayerInfo.CONTINUE_STATUS_NO);
				}

				r.modifyPlayerKeepPlayState(p.getAccId(), p.getKeepPlayState());
				if (p.getState() == PlayerInfo.STATUS_ALREADY_CONTINUE) {
					if (p.getKeepPlayState() == PlayerInfo.CONTINUE_STATUS_YES) {
						List<Long> l = r.getNoKeepPlayer();
						if ((ll.size() == 2) && (l.size() == 0) && (r.getPlayTimes() < maxTimesPlayWithSamePlayer)) {
						} else {
							r.removePlayer(p.getAccId());
							p.setState(PlayerInfo.STATUS_ROLE_OK);
							p.setKeepPlayState(PlayerInfo.CONTINUE_STATUS_NONE);
							p.setPlayerPunches("null");
							p.setRobotStatus(PlayerInfo.WANT_TO_ROBOT_STOP);
							insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.IN_LOBBY, ""), GameDefaultCmds.NORMAL_CMD,
									p.getAccessWsIndex());
							matchRoom(p.getAccId());
						}
					} else if (p.getKeepPlayState() == PlayerInfo.CONTINUE_STATUS_NO) {
						r.removePlayer(p.getAccId());
						disconnectLobby(p);
						boolean cleanRoom = false;
						List<Long> l = r.getKeepPlayer();
						for (int j = 0; j < l.size(); j++) {
							PlayerInfo p2 = playerMap.get(l.get(j));
							p2.setState(PlayerInfo.STATUS_ROLE_OK);
							p2.setKeepPlayState(PlayerInfo.CONTINUE_STATUS_NONE);
							p2.setPlayerPunches("null");
							p2.setRobotStatus(PlayerInfo.WANT_TO_ROBOT_STOP);
							r.removePlayer(p2.getAccId());
							insertGameMsgQueue(p2.getAccessFrom(), generateGameCmd(GameDefaultCmds.IN_LOBBY, ""), GameDefaultCmds.NORMAL_CMD,
									p2.getAccessWsIndex());
							matchRoom(p2.getAccId());
							cleanRoom = true;
						}
						if (cleanRoom) {
							break;
						}
					} else if (p.getKeepPlayState() == PlayerInfo.CONTINUE_STATUS_YES_BUT_OTHER_OPPONENT) {
						r.removePlayer(p.getAccId());
						p.setState(PlayerInfo.STATUS_ROLE_OK);
						p.setKeepPlayState(PlayerInfo.CONTINUE_STATUS_NONE);
						p.setPlayerPunches("null");
						p.setRobotStatus(PlayerInfo.WANT_TO_ROBOT_STOP);
						insertGameMsgQueue(p.getAccessFrom(), generateGameCmd(GameDefaultCmds.IN_LOBBY, ""), GameDefaultCmds.NORMAL_CMD,
								p.getAccessWsIndex());
						matchRoom(p.getAccId());
					}
				}
			}
			ll = r.getKeepPlayer();
			if (ll.size() == 2) {
				for (int i = 0; i < ll.size(); i++) {
					PlayerInfo p = playerMap.get(ll.get(i));
					p.setKeepPlayState(PlayerInfo.CONTINUE_STATUS_NONE);
					p.setPlayerPunches("null");
					p.setRobotStatus(PlayerInfo.WANT_TO_ROBOT_STOP);
				}
				brocastInList(r.getPlayerIdList(), generateGameCmd(GameDefaultCmds.CONTINUE_TIMEOUT, "0"));
				r.setGameState(RoomInfo.STATUS_TWO_PLAYER);
			}
			if (r.getPlayerIdList().size() == 0) {
				r.setPlayTimes(0);
				r.setGameState(RoomInfo.STATUS_EMPTY);
			}
		}
	}

	/**
	 * 主要工作function
	 */
	public void run() {
		try {
			wsList = new CopyOnWriteArrayList<GameServerWSClient>();
			for (int urlIndex = 0; urlIndex < this.wsURLs.size(); urlIndex++) {
				final GameServerWSClient gameEndPoint = new GameServerWSClient(
						new URI(this.wsURLs.get(urlIndex) + GameDefaultCmds.CTT_GAME_SERVER + this.myId + System.currentTimeMillis()), urlIndex);
				gameEndPoint.addMessageHandler(new GameServerWSClient.MessageHandler() {
					public void handleMessage(String message, int wsIndex) {
						handleGameCmd(message, wsIndex);
					}
				});
				gameEndPoint.connect();
				wsList.add(gameEndPoint);
			}

			insertGameMsgQueueAllWS("", this.myId, GameDefaultCmds.REGIST_GAME_SERVER_ID);
			insertGameMsgQueueAllWS("", this.bet, GameDefaultCmds.REGIST_GAME_SERVER_BET);
			insertGameMsgQueueAllWS("", this.maxPlayer, GameDefaultCmds.REGIST_GAME_SERVER_MAX_PLAYER);
			insertGameMsgQueueAllWS("", this.gameTimesType, GameDefaultCmds.REGIST_GAME_SERVER_GAME_TYPE);
			Service serviceForPlayerAndRooms = new Service();
			while (true) {
				playerList = null;
				for (int roomIndex = 0; roomIndex < roomList.size(); roomIndex += this.eachThread) {
					int tmpLastIndex = roomIndex + this.eachThread;
					if (tmpLastIndex > roomList.size()) {
						tmpLastIndex = roomList.size();
					}
					Request request = new Request(roomIndex, tmpLastIndex, null, EXECUTE_GAME_ROOM_LIST);
					serviceForPlayerAndRooms.accept(request);
				}
				while (true) {
					Thread.sleep(1);
					if (serviceForPlayerAndRooms.pool.isAllIdle()) {
						break;
					}
				}

				playerList = new CopyOnWriteArrayList<Long>(playerMap.keySet());
				int totalPlayerNum = playerList.size();
				for (int roomIndex = 0; roomIndex < totalPlayerNum; roomIndex += this.eachThread) {
					int tmpLastIndex = roomIndex + this.eachThread;
					if (tmpLastIndex > totalPlayerNum) {
						tmpLastIndex = totalPlayerNum;
					}
					List<Long> subList = playerList.subList(roomIndex, tmpLastIndex);
					Request request = new Request(0, 0, subList, EXECUTE_PLAYER_LIST);
					serviceForPlayerAndRooms.accept(request);
				}
				while (true) {
					Thread.sleep(1);
					if (serviceForPlayerAndRooms.pool.isAllIdle()) {
						break;
					}
				}

				int msgQSize = getGameMsgQueueSize();
				if (msgQSize > 0) {
					Map<String, Object> m = popGameMsgQueue();
					if ((Integer) m.get("ws") < wsList.size()) {
						if (wsList.get((Integer) m.get("ws")).isSuccess()) {
							wsList.get((Integer) m.get("ws")).sendMessage((String) m.get("msg"));
						}
					}
					m = null;
				}
				gcTimer++;
				if (gcTimer > 60000) {
					gcTimer = 0;
					System.gc();
				}

				if (checkServerTimeOut() && isServerReadyShutDown()) {
					LOG.debug("Server Shut Down " + this.myId);
					for (int i = 0; i < wsList.size(); i++) {
						wsList.get(i).closeSession();
					}
					break;
				}
				Thread.sleep(1);
			}
		} catch (URISyntaxException ex) {
			System.err.println("URISyntaxException exception: " + ex.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class Request {
		int roomIndex;
		int tmpLastIndex;
		int action;
		List<Long> players;

		Request(int roomIndex, int tmpLastIndex, List<Long> players, int action) {
			this.roomIndex = roomIndex;
			this.tmpLastIndex = tmpLastIndex;
			this.action = action;
			this.players = players;
		}

		public Map<String, Object> execute() {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("fromIndex", this.roomIndex);
			data.put("toIndex", this.tmpLastIndex);
			data.put("action", this.action);
			data.put("ids", this.players);
			return data;
		}
	}

	class Service {
		private WorkerThreadPool pool = new WorkerThreadPool();

		void accept(Request request) {
			pool.service(request);
		}
	}

	class WorkerThreadPool {
		private List<ExecuteRoomThread> workerThreads;

		WorkerThreadPool() {
			workerThreads = new ArrayList<ExecuteRoomThread>();
		}

		synchronized void service(Request request) {
			boolean idleNotFound = true;
			for (ExecuteRoomThread workerThread : workerThreads) {
				if (workerThread.isIdle()) {
					workerThread.setRequest(request);
					idleNotFound = false;
					break;
				}
			}
			if (idleNotFound) {
				ExecuteRoomThread workerThread = createWorkerThread();
				workerThread.setRequest(request);
				workerThreads.add(workerThread);
			}
		}

		synchronized void cleanIdle() {
			for (ExecuteRoomThread workerThread : workerThreads) {
				if (workerThread.isIdle()) {
					workerThreads.remove(workerThread);
					workerThread.terminate();
				}
			}
		}

		synchronized boolean isAllIdle() {
			for (ExecuteRoomThread workerThread : workerThreads) {
				if (!workerThread.isIdle()) {
					return false;
				}
			}
			return true;
		}

		private ExecuteRoomThread createWorkerThread() {
			ExecuteRoomThread workerThread = new ExecuteRoomThread(playerList, roomList, gameMessageQueue, bet);
			workerThread.start();
			try {
				Thread.sleep(10); // 給點時間進入 Runnable
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return workerThread;
		}
	}

	public class ExecuteRoomThread extends Thread {
		List<RoomInfo> roomList;
		List<Long> idList;
		int fromIndex;
		int toIndex;
		int action;
		String bet;
		List<WriteInfo> gameMessageQueue;
		private Request request;
		private boolean isContinue = true;

		boolean isIdle() {
			return request == null;
		}

		void setRequest(Request request) {
			synchronized (this) {
				if (isIdle()) {
					this.request = request;
					notify();
				}
			}
		}

		void terminate() {
			isContinue = false;
			setRequest(null);
		}

		public void run() {
			while (isContinue) {
				synchronized (this) {
					if (request != null) {
						Map<String, Object> exeData = request.execute();
						this.fromIndex = (int) exeData.get("fromIndex");
						this.toIndex = (int) exeData.get("toIndex");
						this.action = (int) exeData.get("action");
						this.idList = (List<Long>) exeData.get("ids");
						if (action == EXECUTE_PLAYER_LIST) {
							handleAllPlayer();
						} else if (action == EXECUTE_GAME_ROOM_LIST) {
							handleAllGameRoom();
						}
						request = null;
					} else {
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		// public ExecuteRoomThread(List<Integer> idList, ConcurrentMap<Integer,
		// PlayerInfo> playerMap,
		// List<RoomInfo> roomList, List<WriteInfo> gameMessageQueue, List<String>
		// cacheMessageQueue, String bet) {
		public ExecuteRoomThread(List<Long> idList, List<RoomInfo> roomList, List<WriteInfo> gameMessageQueue, String bet) {
			this.bet = bet;
			this.idList = idList;
			this.roomList = roomList;
			this.gameMessageQueue = gameMessageQueue;
			// this.cacheMessageQueue = cacheMessageQueue;
			// this.playerMap = playerMap;
		}

		private void handleAllPlayer() {
			for (int i = 0; i < idList.size(); i++) {
				PlayerInfo p = playerMap.get(idList.get(i));
				checkPlayerOnlineState(p);
				distribuePlayerToRoom(p);
				checkPlayerWaitContinue(p);
				checkPlayerWantToLeave(p);
				removePlayerWhenOffLine(p);
			}
		}

		private void handleAllGameRoom() {
			for (int i = fromIndex; i < toIndex; i++) {
				RoomInfo r = roomList.get(i);
				checkWaitRoomTooLong(r);
				checkReadyToStartGame(r);
				checkGameWaitGameStartGap(r);
				checkGameWaitGameStartRoundGap(r);
				checkGameWaitPunche(r);
				checkGameWaitPlayVideo(r);
				checkGameWaitGameRoundGap(r);
				checkGameWaitKeepPlay(r);
			}
		}
	}
}
