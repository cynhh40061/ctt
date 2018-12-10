package tw.com.ctt.service.impl;

import static tw.com.ctt.constant.LogToDBConstant.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.action.AccountMemberAction;
import tw.com.ctt.dao.ILoginDao;
import tw.com.ctt.service.ILoginService;

public class LoginServiceImpl extends BaseService implements ILoginService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1221777920357800837L;
	private static final Logger LOG = LogManager.getLogger(AccountMemberAction.class.getName());

	@Override
	public Map<String, Object> login(String accName, String pwd, String ip, int platformId) {
		Map<String, Object> map = null;
		Map<String, Object> mapLog = null;
		JSONObject jsonLog = null;
		boolean bool = true;
		boolean ispwdWithdrawFailed = false;
		boolean isLoginFailed = false;
		boolean isLogin = false;
		long accId = 0;
		String tokenId = "";

		try {
			map = new ConcurrentHashMap<String, Object>();
			mapLog = new ConcurrentHashMap<String, Object>();

			map = ((ILoginDao) dao).checkPwd(accName, pwd, platformId);

			if (map.containsKey("accId")) {
				accId = Long.parseLong(map.get("accId").toString());
			}

			if (map.containsKey("isPwdWithdrawFailed")) {
				if (Integer.parseInt(map.get("isPwdWithdrawFailed").toString()) == 1) {
					ispwdWithdrawFailed = true;
				}

			}
			if (map.containsKey("isLoginFailed")) {
				if (Integer.parseInt(map.get("isLoginFailed").toString()) == 1) {
					isLoginFailed = true;
				}
			}
			if (map.containsKey("isLogin")) {
				if (Integer.parseInt(map.get("isLogin").toString()) == 1) {
					isLogin = true;
				}
			}

			if (bool && isLogin && ispwdWithdrawFailed && isLoginFailed) {
				LOG.debug("ctt_member_token");
				tokenId = dao.getNewTokenId("ctt_manager.ctt_member_token", accId);

				map.put("tokenId", tokenId);
			}
			if (bool && !"".equals(tokenId) && tokenId != null && isLogin && ispwdWithdrawFailed && isLoginFailed) {
				bool = ((ILoginDao) dao).updateLastLogin("ctt_manager.ctt_member_last_login", accId, ip);
			}

			if (bool && ispwdWithdrawFailed) {
				bool = ((ILoginDao) dao).addLoginFailCount(accId, platformId, isLogin);
			}

			mapLog.putAll(map);

			jsonLog = new JSONObject(mapLog);

			if (bool && accId > 0) {
				bool = dao.setLogToDBNoCommit("ctt_manager.ctt_member_login_logout_log", accId, accId, NO_ID, OPS_TYPE_MEMBER, ACC_TYPE_MEMBER, LOG_ACTION_LOGIN, jsonLog.toString(), ip);
				
			}
			if (bool) {
				try {
					bool = dao.commitDB();
				} catch (SQLException e) {
					bool = false;
					LOG.debug(e.getMessage());
				}
			} else {
				try {
					bool = dao.rollBackDB();
				} catch (SQLException e) {
					bool = false;
					LOG.debug(e.getMessage());
				}
			}
			map.put("isSuccess", bool);

		} catch (Exception e) {
			LOG.debug(e.getMessage());
		} finally {
			if (mapLog != null) {
				mapLog.clear();
				mapLog = null;
			}
			if (jsonLog != null) {
				jsonLog = null;
			}

		}

		return map;
	}

	@Override
	public boolean logout(String tokenId, long accId, String ip) {
		boolean bool = true;
		Map<String, Object> mapLog = null;
		JSONObject jsonLog = null;
		try {
			mapLog = new ConcurrentHashMap<String, Object>();
			mapLog.put("tokenId", tokenId);
			jsonLog = new JSONObject(mapLog);

			if (bool) {
				bool = dao.colosMemTokenId(accId, tokenId);
			}
			if (bool) {
				bool = dao.setLogToDBNoCommit("ctt_manager.ctt_member_login_logout_log", accId, accId, NO_ID, OPS_TYPE_MEMBER, ACC_TYPE_MEMBER, LOG_ACTION_LOGOUT, jsonLog.toString(), ip);
			}
			if (bool) {
				try {
					bool = dao.commitDB();
				} catch (SQLException e) {
					bool = false;
					LOG.debug(e.getMessage());
				}
			} else {
				try {
					bool = dao.rollBackDB();
				} catch (SQLException e) {
					bool = false;
					LOG.debug(e.getMessage());
				}
			}
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		} finally {
			if (mapLog != null) {
				mapLog.clear();
				mapLog = null;
			}
			if (jsonLog != null) {
				jsonLog = null;
			}
		}
		return bool;
	}

	@Override
	public List<Map<String, Object>> getPlatformAd(int platformId) {
		return ((ILoginDao) dao).getPlatformAd(platformId);
	}
}
