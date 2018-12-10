package tw.com.ctt.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.action.BaseAction;
import tw.com.ctt.dao.ISetGameDao;
import tw.com.ctt.service.ISetGameService;
import tw.com.ctt.util.ShowLog;

public class SetGameServiceImpl extends BaseService implements ISetGameService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8484139785250900080L;
	private static final Logger LOG = LogManager.getLogger(SetGameServiceImpl.class.getName());

	public SetGameServiceImpl() {

	}

	public SetGameServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public List<Map<String, Object>> getGameParamData() {
		return ((ISetGameDao) dao).getGameParamData(USERID);
	}
	@Override
	public boolean updateGameParamData(String updateObj) {
		JSONObject jsonMap = null;
		boolean todo = false;
		String chk [] = {"sid","bet","gameTimesType","gameTimeOut","waitContinueTimeOut","commission"}; 
		try {
			jsonMap = new JSONObject(updateObj);
			for(int i = 0;i < jsonMap.length();i++) {
				JSONObject data = new JSONObject(updateObj);
				data = (JSONObject) jsonMap.get(""+i);//遊戲參數JSON從0開始
				for(int a = 0;a < chk.length;a++) {
					if(data.keySet().contains(chk[a])) {
						data.put(chk[a], data.getInt(chk[a]));
					}else {
						data.put(chk[a], "");
					}
				}
				todo = ((ISetGameDao) dao).updateGameParamData(data.getInt("sid"), data.getInt("bet"), data.getInt("gameTimesType"), data.getInt("gameTimeOut"), data.getInt("waitContinueTimeOut"), data.getInt("commission"), data.getInt("continueTimesType"),USERID);
			}
		}catch(Exception e) {
			todo = false;
			LOG.info("updateGameParamData Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		}finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				todo = false;
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return todo;
	}
}
