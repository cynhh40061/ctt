package tw.com.ctt.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IBaseDao;
import tw.com.ctt.model.ServerInfo;
import tw.com.ctt.service.IBaseService;
import tw.com.ctt.util.ShowLog;

public class BaseService implements Serializable, IBaseService {

	private static final long serialVersionUID = -7507696189865417120L;
	private static final Logger LOG = LogManager.getLogger(BaseService.class.getName());
	protected IBaseDao dao;
	public static long USERID = 0;
	public static String USERIP = "";

	public BaseService() {
		this.USERID = 0;
		this.USERIP = "";
	}

	public BaseService(long userid, String userip) {
		this.USERID = userid;
		this.USERIP = userip;
	}

	@Override
	public void setDao(IBaseDao dao) {
		this.dao = dao;
	}

	@Override
	public void close() {
		if (this.dao != null) {
			this.dao.close();
		}
	}

	@Override
	public String getManagerTokenId(long accId) {
		return dao.getNewTokenId("ctt_manager.ctt_manager_token", accId);
	}

	@Override
	public String getMemberTokenId(long accId) {
		return dao.getNewTokenId("ctt_manager.ctt_member_token", accId);
	}

	@Override
	public boolean checkManagerTokenTimeOut(String tokenId, long accId) {
		boolean todo = false;
		todo = dao.checkTokenTimeOut("ctt_manager.ctt_manager_token", tokenId, accId);
		if (todo) {
			todo = dao.updateTokenTime("ctt_manager.ctt_manager_token", tokenId, accId);
		}
		return todo;
	}

	@Override
	public boolean checkMemberTokenTimeOut(String tokenId, long accId) {
		boolean todo = false;
		todo = dao.checkTokenTimeOut("ctt_manager.ctt_member_token", tokenId, accId);
		if (todo) {
			todo = dao.updateTokenTime("ctt_manager.ctt_member_token", tokenId, accId);
		}
		return todo;
	}

	@Override
	public boolean checkMemberTokenTimeOutNoUpdate(long accId, String tokenId) {
		boolean todo = false;
		todo = dao.checkTokenTimeOut("ctt_manager.ctt_member_token", tokenId, accId);
		return todo;
	}

	@Override
	public Map<String, String> checkMemberTokenTimeOut2(String tokenId, long accId) {
		boolean todo = false;
		Map<String, String> tmpResult = dao.checkTokenTimeOutMember("ctt_manager.ctt_member_token", tokenId, accId);
		todo = "1".equals(tmpResult.get("tokenIdCheck")) ? true : false;
		if (todo) {
			todo = dao.updateTokenTime("ctt_manager.ctt_member_token", tokenId, accId);
		}
		return tmpResult;
	}

	@Override
	public boolean getAuthFunction(long accId, String url) {
		return dao.getAuthFunction(accId, url);
	}

	@Override
	public boolean setActionLogToDBNoCommit(String tableName, long opsAccId, String actionUrl, int opsType, int urlType,
			String detail, String ip) {
		boolean todo = false;
		try {
			// if() {
			//
			// }else if() {
			//
			// }
			todo = dao.setActionLogToDBNoCommit(tableName, opsAccId, actionUrl, opsType, urlType, detail, ip);
		} catch (Exception e) {
			todo = false;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {

		}
		return todo;
	}

	@Override
	public void callServer(String url, List<String> notRunList) {
		dao.callServer(url, notRunList);
	}

	@Override
	public List<ServerInfo> getServerInfo(String context, Boolean status) {
		return dao.getServerInfo(context, status);
	}
}
