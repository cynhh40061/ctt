package tw.com.ctt.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IAuthDao;
import tw.com.ctt.dao.impl.BaseDao;
import tw.com.ctt.service.IAuthService;

public class AuthServiceImpl extends BaseService implements IAuthService {

	private static final long serialVersionUID = -5375529628938987603L;
	private static final Logger LOG = LogManager.getLogger(AuthServiceImpl.class.getName());

	public AuthServiceImpl() {

	}

	public AuthServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public List<Map<String, Object>> getAuthGroup(long accId, int accLevelType) {
		return ((IAuthDao) dao).getAuthGroup(accId, accLevelType);
	}

	@Override
	public List<Map<String, Object>> getAuthList() {
		return ((IAuthDao) dao).getAuthList();
	}

	@Override
	public List<Map<String, Object>> getGroupAuthList(int groupId) {
		return ((IAuthDao) dao).getGroupAuthList(groupId);
	}

	@Override
	public List<Map<String, Object>> getUpLevelAuth() {
		return ((IAuthDao) dao).getUpLevelAuth();
	}

	@Override
	public int addAuth(int authLevelType, int level1, int level2, String authName, String authRemark, String url, int adminAuth) {
		int cou = 0;
		try {
			cou = ((IAuthDao) dao).addAuth(authLevelType, level1, level2, authName, authRemark, url, adminAuth);
			if (cou > 0) {
				// todo = setLogToDB
			}
			if (cou > 0) {
				((BaseDao) (IAuthDao) dao).commitDB();
			} else {
				((BaseDao) (IAuthDao) dao).rollBackDB();
			}
		} catch (SQLException e) {
			LOG.debug(e);
			return 0;
		} finally {
		}
		return cou;
	}

	@Override
	public boolean updateAuthGroup(int groupId, String[] authList) {
		boolean todo = false;
		try {
			todo = ((IAuthDao) dao).updateAuthGroup(groupId, authList);
			if (todo) {
				// todo = setLogToDB
			}
			if (todo) {
				todo = ((BaseDao) (IAuthDao) dao).commitDB();
			} else {
				todo = ((BaseDao) (IAuthDao) dao).rollBackDB();
			}
		} catch (SQLException e) {
			LOG.debug(e);
			return false;
		} finally {
		}
		return todo;
	}

	@Override
	public boolean createAuthGroup(String groupName, String[] authList) {
		boolean todo = false;
		try {
			todo = ((IAuthDao) dao).createAuthGroup(groupName, authList);
			if (todo) {
				// todo = setLogToDB
			}
			if (todo) {
				todo = ((BaseDao) (IAuthDao) dao).commitDB();
			} else {
				todo = ((BaseDao) (IAuthDao) dao).rollBackDB();
			}
		} catch (SQLException e) {
			LOG.debug(e);
			return false;
		} finally {
		}
		return todo;
	}

	@Override
	public boolean checkAuthGroupName(String groupName) {
		int cou = ((IAuthDao) dao).checkAuthGroupName(groupName.replaceAll("\\s+", ""));
		if (cou == 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<Map<String, Object>> portopnGroupToAccLevel(int accLevelType) {
		return ((IAuthDao) dao).portopnGroupToAccLevel(accLevelType);
	}

	@Override
	public boolean updateLevelTypeGroup(int levelType, String[] groupIdList) {
		boolean todo = false;
		try {
			todo = ((IAuthDao) dao).deleteLevelTypeGroup(levelType);
			if(todo) {
				todo = ((IAuthDao) dao).updateLevelTypeGroup(levelType, groupIdList);
			}
			
		} catch (Exception e) {
			LOG.debug(e.getMessage());
			return false;
		} finally {
			if (todo) {
				try {
					todo = dao.commitDB();
				} catch (SQLException e) {
					LOG.debug(e);
					try {
						todo = dao.rollBackDB();
					} catch (SQLException e1) {
						LOG.debug(e1.getMessage());
					}
				}
			} else {
				try {
					todo = dao.rollBackDB();
				} catch (SQLException e) {
					LOG.debug(e.getMessage());
				}
			}
			
		}
		return todo;
	}

	@Override
	public boolean checkAuthRemarkText(String AuthRemarkText) {
		int cou = ((IAuthDao) dao).checkAuthRemarkText(AuthRemarkText.replaceAll("\\s+", ""));
		if (cou == 0) {
			return true;
		} else {
			return false;
		}
	}
}
