package tw.com.ctt.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.impl.BaseDao;
import tw.com.ctt.dao.impl.GameDaoImpl;
import tw.com.ctt.service.impl.GameServiceImpl;
import tw.com.ctt.util.DataSource;
import tw.com.ctt.util.RepayingMoney;
import tw.com.ctt.util.ShowLog;

public class GameServer {

	private static final Logger LOG = LogManager.getLogger(GameServer.class.getName());

	private static final String WS_IP = "127.0.0.1:8080";

	public static void main(String[] args) {
		GameServer gs = new GameServer();
		gs.contextInitialized();

		getGameServerParam();

	}

	public void contextInitialized() {
		String notRunList = null;
		BaseDao dao = null;
		try {
			// need to check url
			CommandConstant.CHECK_URL = tw.com.ctt.util.Configuration.CHECK_URL == null ? false
					: tw.com.ctt.util.Configuration.CHECK_URL;

			// get not run list
			notRunList = tw.com.ctt.util.Configuration.NOT_RUN_LIST == null ? ""
					: tw.com.ctt.util.Configuration.NOT_RUN_LIST;

			if (notRunList != null && !notRunList.isEmpty() && notRunList.trim().length() > 0) {
				String[] notRunLists = notRunList.split(",");
				CommandConstant.NOT_RUN_LIST = new ArrayList<String>(Arrays.asList(notRunLists));
			} else {
				CommandConstant.NOT_RUN_LIST = new ArrayList<String>();
			}

			CommandConstant.SERVER_NAME = java.net.InetAddress.getLocalHost().getHostName() == null ? ""
					: java.net.InetAddress.getLocalHost().getHostName();
			CommandConstant.SERVER_IP = java.net.InetAddress.getLocalHost().getHostAddress() == null ? ""
					: java.net.InetAddress.getLocalHost().getHostAddress();

			// initial BLACK_WHITE_LIST
			dao = new BaseDao();
			String[] ip = tw.com.ctt.util.Configuration.BLACK_LIST.split(",");
			CommandConstant.BLACK_LIST = new CopyOnWriteArraySet<String>(Arrays.asList(ip));
			ip = null;
			ip = tw.com.ctt.util.Configuration.WHITE_LIST.split(",");
			CommandConstant.WHITE_LIST = new CopyOnWriteArraySet<String>(Arrays.asList(ip));

			// for update black white list (CommandConstant.BLACK_WHITE_LIST)
			dao.checkBlackWhiteIPs();

			DataSource.returnReadConnection(null);
			DataSource.returnWriteConnection(null);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			notRunList = "";
			notRunList = null;
			if (dao != null) {
				dao.close();
				dao = null;
			}
		}
	}

	public static void getGameServerParam() {
		GameDaoImpl dao = null;
		GameServiceImpl service = null;
		List<Map<String, Object>> mapList = null;
		Map<String, Object> map = null;
		List<String> websocketURLs = null;
		List<GameServerThread> theardList = null;
		RepayingMoney repayingMoney = null;
		try {
			websocketURLs = new CopyOnWriteArrayList<String>();
			websocketURLs.add("ws://" + WS_IP + "/CTT02WebSocketServer/gameWS?");
			mapList = new ArrayList<Map<String, Object>>();
			map = new ConcurrentHashMap<String, Object>();
			service = new GameServiceImpl();
			dao = new GameDaoImpl();
			service.setDao(dao);
			mapList = service.getGameServerParam();
			String[] checkKey = { "gameServerId", "bet", "maxPlayer", "gameTimesType", "commission", "thread",
					"beatsTimeOut", "playerBeatsTimeOut", "gameTimeOut", "autoTimeStamp", "waitContinueTimeOut",
					"continueTimesType", "serviceTime", "serverStatus" };
			theardList = new ArrayList<GameServerThread>();
			repayingMoney = new RepayingMoney();
			for (int i = 0; i < mapList.size(); i++) {
				map = mapList.get(i);
				if (checkMapKey(checkKey, map) != null && checkMapKey(checkKey, map).size() == 0) {
					repayingMoney.RepayingMoneyService(map.get("gameServerId").toString());

					GameServerThread thread = new GameServerThread("" + map.get("gameServerId"), "" + map.get("bet"),
							"" + map.get("maxPlayer"), "" + map.get("gameTimesType"), "" + map.get("commission"),
							"" + map.get("thread"), "" + map.get("beatsTimeOut"), "" + map.get("gameTimeOut"),
							"" + map.get("autoTimeStamp"), "" + map.get("waitContinueTimeOut"), websocketURLs,
							"" + map.get("continueTimesType"), "" + map.get("serviceTime"),
							"" + map.get("playerBeatsTimeOut"));
					theardList.add(thread);
					theardList.get(i).start();
				} else {
					LOG.debug("getGameServerParam_check_map_fail===" + checkMapKey(checkKey, map).toString());
				}
			}

		} catch (Exception error) {
			LOG.debug(error.getMessage());
			ShowLog.err(LOG, error);
		} finally {
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			if (mapList != null) {
				mapList.clear();
				mapList = null;
			}
			for (int i = 0; i < theardList.size(); i++) {
				try {
					theardList.get(i).join();
				} catch (InterruptedException e) {
					LOG.debug(e.getMessage());
					ShowLog.err(LOG, e);
				}
			}
			if (repayingMoney != null) {
				repayingMoney.closeRepayingMoney();
				repayingMoney = null;
			}
		}
		return;
	}

	public static List<String> checkMapKey(String strs[], Map<String, Object> map) {
		List<String> chklist = null;
		if ((strs != null && strs.length > 0) && (map != null && map.size() > 0)) {
			chklist = new ArrayList<String>();
			for (String str : strs) {
				if (map.keySet().contains(str)) {
					try {
						if (map.get(str) == null || "".equals(map.get(str).toString().trim())) {
							chklist.add(str);
						}
					} catch (Exception e) {
						LOG.debug("\ncheckMapKey_Exception=" + e.getMessage());
						ShowLog.err(LOG, e);
						chklist.add(str);
					}
				} else {
					LOG.debug("\ncheckMapKey: key not find! " + str);
					chklist.add(str);
				}
				str = "";
				str = null;
			}
		} else {
			LOG.debug("\ncheckMapKey: strs is null || map is null!");
		}
		return chklist;
	}
}