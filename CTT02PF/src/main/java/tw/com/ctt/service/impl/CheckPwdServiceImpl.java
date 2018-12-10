package tw.com.ctt.service.impl;

import static tw.com.ctt.constant.LogToDBConstant.LOG_ACTION_LOGIN;
import static tw.com.ctt.constant.LogToDBConstant.LOG_ACTION_LOGOUT;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.impl.CheckPwdDaoImpl;
import tw.com.ctt.service.ICheckPwdService;
import static tw.com.ctt.constant.LogToDBConstant.*;

public class CheckPwdServiceImpl extends BaseService implements ICheckPwdService {

	private static final long serialVersionUID = 5855274351289897836L;
	private static final Logger LOG = LogManager.getLogger(CheckPwdServiceImpl.class.getName());

	public CheckPwdServiceImpl() {

	}

	public CheckPwdServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public Map<String, String> checkPwd(String userName, String pwd) {
		return ((CheckPwdDaoImpl) dao).checkPwd(userName, pwd);
	}

	@Override
	public List<Map<String, Object>> getAuth(String accId) {
		return ((CheckPwdDaoImpl) dao).getAuth(accId);
	}

	@Override
	public boolean updateMemberLastLogin(int userId, String ip) {
		return ((CheckPwdDaoImpl) dao).updateLastLogin("ctt_manager.ctt_member_last_login", userId, ip);
	}

	@Override
	public boolean updateManagerLastLogin(int userId, String ip) {
		return ((CheckPwdDaoImpl) dao).updateLastLogin("ctt_manager.ctt_manager_last_login", userId, ip);
	}

	@Override
	public boolean login(Map<String, Object> result, String userName, String pwd, String userIp) {
		String tokenId = "";
		boolean bool = true;

		Map<String, String> tmpMap = ((CheckPwdDaoImpl) dao).checkPwd(userName, pwd);
		result.put("basic", tmpMap);
		if (tmpMap.get("result") == "true" && !"".equals(tmpMap.get("acc_level_type"))) {
			result.put("auth", ((CheckPwdDaoImpl) dao).getAuth(tmpMap.get("acc_id")));
			tokenId = dao.getNewTokenId("ctt_manager.ctt_manager_token", Long.parseLong(tmpMap.get("acc_id").toString()));
			if (tokenId != null && !"".equals(tokenId)) {
				result.put("tokenId", tokenId);
			} else {
				bool = false;
			}
			if (bool) {
				bool = ((CheckPwdDaoImpl) dao).updateLastLogin("ctt_manager.ctt_manager_last_login", Integer.parseInt(tmpMap.get("acc_id")), userIp);
			}
			if (bool) {
				bool = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_login_logout_log", Long.parseLong(tmpMap.get("acc_id").toString()),
						Long.parseLong(tmpMap.get("acc_id").toString()), 0, OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_LOGIN, "tokenId:" + tokenId, userIp);
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
			return bool;
		} else {
			bool = false;
		}
		return bool;

	}

	@Override
	public boolean logout(long accId, String userName, String tokenId, String userIp) {
		boolean bool = true;
		if (bool) {
			bool = dao.colosMagTokenId(accId, tokenId);
		}
		if (bool) {
			bool = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_login_logout_log", accId, accId, 0, OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_LOGOUT, "", userIp);
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

		return bool;
	}
}
