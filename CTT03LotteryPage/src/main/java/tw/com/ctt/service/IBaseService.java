package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

import tw.com.ctt.dao.IBaseDao;

public interface IBaseService {

	public void setDao(IBaseDao dao);

	public void close();

	public boolean checkMemberTokenTimeOut(String tokenId, long accId);

	public boolean checkMemberTokenTimeOutNoUpdate(long accId, String tokenId);

	public String getManagerTokenId(long accId);

	public String getMemberTokenId(long accId);

	public boolean checkManagerTokenTimeOut(String tokenId, long accId);

	public Map<String, String> checkMemberTokenTimeOut2(String tokenId, long accId);

	public boolean getAuthFunction(long accId, String url);

	public boolean setActionLogToDBNoCommit(String tableName, long opsAccId, String actionUrl, int opsType, int urlType, String detail, String ip);

	public void callServer(String url, List<String> notRunList);
}