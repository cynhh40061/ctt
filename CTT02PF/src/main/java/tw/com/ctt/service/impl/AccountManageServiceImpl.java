package tw.com.ctt.service.impl;

import static tw.com.ctt.constant.AccountManagerConstant.*;
import static tw.com.ctt.constant.LogToDBConstant.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.action.BaseAction;
import tw.com.ctt.dao.IAccountManageDao;
import tw.com.ctt.model.MemBean;
import tw.com.ctt.model.MgrBean;
import tw.com.ctt.model.SetGameServerBean;
import tw.com.ctt.service.IAccountManageService;
import tw.com.ctt.util.CommandUtil;
import tw.com.ctt.util.ShowLog;

public class AccountManageServiceImpl extends BaseService implements IAccountManageService {

	private static final long serialVersionUID = 5855274351289897836L;
	private static final Logger LOG = LogManager.getLogger(AccountManageServiceImpl.class.getName());

	public AccountManageServiceImpl() {

	}

	public AccountManageServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public int findLevel(long userId) {
		return ((IAccountManageDao) dao).findLevel(userId);
	}

	@Override
	public Map<String, String> findTypeTotal(long userId, int status) {
		return ((IAccountManageDao) dao).findTypeTotal(userId, status);
	}

	@Override
	public List<Map<String, Object>> getUpAccountName(long accId, int accLevelType) {

		return ((IAccountManageDao) dao).getUpAccountName(accId, accLevelType);
	}

	@Override
	public int checkAccName(String tableName, String accName) {
		return ((IAccountManageDao) dao).checkAccName(tableName, accName);
	}

	@Override
	public long addComData(Map<String, Object> map) {
		long accId = 0;
		boolean todo = false;
		IAccountManageDao addDao = null;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		try {
			addDao = (IAccountManageDao) dao;
			logDataMap = new ConcurrentHashMap<String, Object>();
			accId = addDao.addCom("" + map.get("accName"), "" + map.get("pwd"), "" + map.get("nickname"),
					Integer.parseInt("" + map.get("authGroup")));
			if (accId > 0) {
				todo = addDao.addComScLevel(accId, Integer.parseInt("" + map.get("accLevelType")));
				if (todo) {
					todo = addDao.addCreate("ctt_manager.ctt_manager_create", accId, 0, "" + map.get("ip"));
				}
				if (todo) {
					todo = addDao.addLastLogin("ctt_manager.ctt_manager_last_login", accId, "0.0.0.0");
				}
				if (todo) {
					todo = addDao.addLastUpdate("ctt_manager.ctt_manager_last_update", accId, USERID, "" + map.get("ip"));
				}
				if (todo) {
					todo = dao.addToken("ctt_manager.ctt_manager_token", accId);
				}
				if (todo) {
					logDataMap.put("accName", "" + map.get("accName"));
					logDataMap.put("nickname", "" + map.get("nickname"));
					logDataMap.put("authGroup", Integer.parseInt("" + map.get("authGroup")));
					logDataMap.put("accLevelType", Integer.parseInt("" + map.get("accLevelType")));
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, accId, 0, OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_ADD_ACC,
							responseJSONObject.toString(), USERIP);
				}
			}
		} catch (SQLException e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
					if (accId > 0) {
						accId = -1;
					}
				}
			} catch (SQLException e) {
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			todo = false;
			addDao = null;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return accId;
	}

	@Override
	public long addScData(Map<String, Object> map) {
		long accId = 0;
		long cashBcId = 0;
		long trialPlayBcId = 0;
		boolean todo = true;
		IAccountManageDao addDao = null;
		List<Integer> currentRatio = null;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		try {
			addDao = (IAccountManageDao) dao;
			currentRatio = new ArrayList<Integer>();
			logDataMap = new ConcurrentHashMap<String, Object>();

			accId = addDao.addManager("" + map.get("accName"), "" + map.get("pwd"), "" + map.get("nickname"),
					Integer.parseInt("" + map.get("authGroup")), Integer.parseInt("" + map.get("fullRatio")) , Integer.parseInt("" + map.get("handicap")));
			if (accId > 0) {
				todo = addDao.addComScLevel(accId, Integer.parseInt("" + map.get("accLevelType")));
				if (todo) {
					todo = addDao.addCreate("ctt_manager.ctt_manager_create", accId, 0, "" + map.get("ip"));
				}
				if (todo) {
					todo = addDao.addLastLogin("ctt_manager.ctt_manager_last_login", accId, "0.0.0.0");
				}
				if (todo) {
					todo = addDao.addLastUpdate("ctt_manager.ctt_manager_last_update", accId, USERID, "" + map.get("ip"));
				}

				for (int i = 0; i < GAME_TYPE_ARR.length && todo == true; i++) {
					currentRatio.add(Integer.parseInt("" + map.get("currentRatioG" + GAME_TYPE_ARR[i])));
					if (todo) {
						todo = addDao.addManagerRatio(accId, GAME_TYPE_ARR[i], 0, 100,
								Integer.parseInt("" + map.get("currentRatioG" + GAME_TYPE_ARR[i])));
					}
					if (todo) {
						logDataMap.put("maxRatioG" + GAME_TYPE_ARR[i], 100);
						logDataMap.put("minRatioG" + GAME_TYPE_ARR[i], 0);
						logDataMap.put("currentRatioG" + i, Integer.parseInt("" + map.get("currentRatioG" + GAME_TYPE_ARR[i])));
					}
				}
				if (todo) {
					todo = dao.addToken("ctt_manager.ctt_manager_token", accId);
				}
				if (todo) {
					logDataMap.put("accName", "" + map.get("accName"));
					logDataMap.put("nickname", "" + map.get("nickname"));
					logDataMap.put("authGroup", Integer.parseInt("" + map.get("authGroup")));
					logDataMap.put("fullRatio", Integer.parseInt("" + map.get("fullRatio")));
					logDataMap.put("accLevelType", Integer.parseInt("" + map.get("accLevelType")));

					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, accId, 0, OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_ADD_ACC,
							responseJSONObject.toString(), USERIP);
				}
			} else {
				todo = false;
			}

			if (todo) {
				cashBcId = addManagerBC(addDao, accId, map.get("accName") + "bc0", "" + map.get("pwd"), "CashBc",
						Integer.parseInt("" + map.get("authGroup")), Integer.parseInt("" + map.get("fullRatio")), currentRatio,Integer.parseInt("" + map.get("handicap")));
			}
			if (todo) {
				trialPlayBcId = addManagerBC(addDao, accId, map.get("accName") + "bc1", "" + map.get("pwd"), "TrialPlayBc",
						Integer.parseInt("" + map.get("authGroup")), Integer.parseInt("" + map.get("fullRatio")), currentRatio,Integer.parseInt("" + map.get("handicap")));
			}

			if (cashBcId > 0 && trialPlayBcId > 0) {
				todo = addDao.addPlatform(accId, cashBcId, trialPlayBcId);
			} else {
				todo = false;
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
					if (accId > 0) {
						accId = -1;
					}
				}
			} catch (SQLException e) {
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			todo = true;
			addDao = null;
			cashBcId = 0;
			trialPlayBcId = 0;
			todo = true;
			addDao = null;
			currentRatio = null;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}

		return accId;
	}

	private long addManagerBC(IAccountManageDao addDao, long upAccId, String accName, String Pwd, String nickname, int authGroup, int fullRatio,
			List<Integer> currentRatio , int handicap) {
		long accId = 0;
		boolean todo = true;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;

		try {
			logDataMap = new ConcurrentHashMap<String, Object>();
			accId = addDao.addManager(accName, Pwd, nickname, authGroup, 0 ,handicap);
			if (accId > 0) {
				todo = addDao.addLevel(accId, upAccId);
				if (todo) {
					todo = addDao.addCreate("ctt_manager.ctt_manager_create", accId, 0, USERIP);
				}
				if (todo) {
					todo = addDao.addLastLogin("ctt_manager.ctt_manager_last_login", accId, "0.0.0.0");
				}
				if (todo) {
					todo = addDao.addLastUpdate("ctt_manager.ctt_manager_last_update", accId, USERID, USERIP);
				}
				for (int i = 0; i < GAME_TYPE_ARR.length && todo == true; i++) {
					if (fullRatio == FULL_RATIO_ENABLED) {
						todo = addDao.addManagerRatio(accId, GAME_TYPE_ARR[i], 0, currentRatio.get(i), 0);
						logDataMap.put("maxRatioG" + GAME_TYPE_ARR[i], 0);
						logDataMap.put("minRatioG" + GAME_TYPE_ARR[i], 0);
						logDataMap.put("currentRatioG" + GAME_TYPE_ARR[i], currentRatio.get(i));
					} else {
						todo = addDao.addManagerRatio(accId, GAME_TYPE_ARR[i], 0, 0, 0);
						logDataMap.put("maxRatioG" + GAME_TYPE_ARR[i], 0);
						logDataMap.put("minRatioG" + GAME_TYPE_ARR[i], 0);
						logDataMap.put("currentRatioG" + GAME_TYPE_ARR[i], 0);
					}
				}
				if (todo) {
					todo = dao.addToken("ctt_manager.ctt_manager_token", accId);
				}
				if (todo) {
					logDataMap.put("accName", accName);
					logDataMap.put("nickname", nickname);
					logDataMap.put("authGroup", authGroup);
					logDataMap.put("fullRatio", fullRatio);
					logDataMap.put("accLevelType", 3);

					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, accId, 0, OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_ADD_ACC,
							responseJSONObject.toString(), USERIP);
				}
			} else {
				todo = false;
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
					if (accId > 0) {
						accId = -1;
					}
				}
			} catch (SQLException e) {
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			todo = false;
			addDao = null;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return accId;
	}

	@Override
	public long addMgrData(Map<String, Object> map) {
		long accId = 0;
		boolean todo = false;
		IAccountManageDao addDao = null;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		try {
			addDao = (IAccountManageDao) dao;
			logDataMap = new ConcurrentHashMap<String, Object>();
			accId = addDao.addManager("" + map.get("accName"), "" + map.get("pwd"), "" + map.get("nickname"),
					Integer.parseInt("" + map.get("authGroup")), Integer.parseInt("" + map.get("fullRatio")),Integer.parseInt("" + map.get("handicap")));
			if (accId > 0) {
				todo = addDao.addLevel(accId, Long.parseLong("" + map.get("upAccId")));
				if (todo) {
					todo = addDao.addCreate("ctt_manager.ctt_manager_create", accId, 0, "" + map.get("ip"));
				}
				if (todo) {
					todo = addDao.addLastLogin("ctt_manager.ctt_manager_last_login", accId, "0.0.0.0");
				}
				if (todo) {
					todo = addDao.addLastUpdate("ctt_manager.ctt_manager_last_update", accId, USERID, "" + map.get("ip"));
				}
				for (int i = 0; i < GAME_TYPE_ARR.length && todo == true; i++) {
					if (map.containsKey("minRatioG" + GAME_TYPE_ARR[i]) && map.containsKey("maxRatioG" + GAME_TYPE_ARR[i])
							&& map.containsKey("currentRatioG" + GAME_TYPE_ARR[i])) {
						todo = addDao.addManagerRatio(accId, GAME_TYPE_ARR[i], Integer.parseInt("" + map.get("minRatioG" + GAME_TYPE_ARR[i])),
								Integer.parseInt("" + map.get("maxRatioG" + GAME_TYPE_ARR[i])),
								Integer.parseInt("" + map.get("currentRatioG" + GAME_TYPE_ARR[i])));
						if (todo) {
							logDataMap.put("maxRatioG" + GAME_TYPE_ARR[i], Integer.parseInt("" + map.get("maxRatioG" + GAME_TYPE_ARR[i])));
							logDataMap.put("minRatioG" + GAME_TYPE_ARR[i], Integer.parseInt("" + map.get("minRatioG" + GAME_TYPE_ARR[i])));
							logDataMap.put("currentRatioG" + GAME_TYPE_ARR[i], Integer.parseInt("" + map.get("currentRatioG" + GAME_TYPE_ARR[i])));
						}
					}
				}
				if (todo) {
					todo = dao.addToken("ctt_manager.ctt_manager_token", accId);
				}
				if (todo) {
					logDataMap.put("accName", "" + map.get("accName"));
					logDataMap.put("nickname", "" + map.get("nickname"));
					logDataMap.put("authGroup", Integer.parseInt("" + map.get("authGroup")));
					logDataMap.put("fullRatio", Integer.parseInt("" + map.get("fullRatio")));
					logDataMap.put("accLevelType", Integer.parseInt("" + map.get("accLevelType")));
					responseJSONObject = new JSONObject(logDataMap);

					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, accId, 0, OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_ADD_ACC,
							responseJSONObject.toString(), USERIP);
				}
			} else {
				todo = false;
			}
		} catch (Exception e1) {
			if (accId > 0) {
				accId = -1;
			}
			LOG.debug(e1.getMessage());
			ShowLog.err(LOG, e1);
		} finally {
			try {
				if (todo) {
					dao.commitDB();
				} else {
					dao.rollBackDB();
					if (accId > 0) {
						accId = -1;
					}
				}
			} catch (Exception e) {
				if (accId > 0) {
					accId = -1;
				}
			}
			todo = false;
			addDao = null;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return accId;
	}

	@Override
	public long addMemData(Map<String, Object> map) {
		long accId = 0;
		boolean todo = true;
		IAccountManageDao addDao = null;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		try {
			addDao = (IAccountManageDao) dao;
			logDataMap = new ConcurrentHashMap<String, Object>();
			accId = addDao.addMember("" + map.get("accName"), "" + map.get("pwd"), "" + map.get("nickname"),
					Integer.parseInt("" + map.get("memberType")),Integer.parseInt("" + map.get("handicap")));
			if (accId > 0) {
				todo = addDao.addMemberLevel(accId, Integer.parseInt("" + map.get("upAccId")));
				if (todo) {
					todo = addDao.addCreate("ctt_manager.ctt_member_create", accId, 0, "" + map.get("ip"));
				}
				if (todo) {
					todo = addDao.addLastLogin("ctt_manager.ctt_member_last_login", accId, "0.0.0.0");
				}
				if (todo) {
					todo = addDao.addLastUpdate("ctt_manager.ctt_member_last_update", accId, USERID, "" + map.get("ip"));
				}
				for (int i = 0; i < GAME_TYPE_ARR.length && todo == true; i++) {
					todo = addDao.addMemberRatio(accId, GAME_TYPE_ARR[i], Integer.parseInt("" + map.get("minRatioG" + GAME_TYPE_ARR[i])));

					if (todo) {
						List<Integer> ration = addDao.selectGameRatioMem(Integer.parseInt("" + map.get("upAccId")),
								Integer.parseInt("" + map.get("minRatioG" + GAME_TYPE_ARR[i])), GAME_TYPE_ARR[i]);
						todo = addDao.memberRatioTotal(accId, GAME_TYPE_ARR[i], ration);
					}
					if (todo) {
						logDataMap.put("minRatioG" + GAME_TYPE_ARR[i], Integer.parseInt("" + map.get("minRatioG" + GAME_TYPE_ARR[i])));
					}
				}
				if (todo) {
					todo = addDao.addAccDeails(accId);
				}
				if (todo) {
					todo = addDao.addPunchGameAccInfo(accId);
				}

				if (todo) {
					todo = dao.addToken("ctt_manager.ctt_member_token", accId);
				}
				if (todo) {
					logDataMap.put("accName", "" + map.get("accName"));
					logDataMap.put("nickname", "" + map.get("nickname"));
					logDataMap.put("memberType", Integer.parseInt("" + map.get("memberType")));
					logDataMap.put("accLevelType", Integer.parseInt("" + map.get("accLevelType")));
					responseJSONObject = new JSONObject(logDataMap);

					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_member_update_log", USERID, accId, 0, OPS_TYPE_MANAGER, ACC_TYPE_MEMBER, LOG_ACTION_ADD_ACC,
							responseJSONObject.toString(), USERIP);
				}
			} else {
				todo = false;
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					dao.commitDB();
				} else {
					dao.rollBackDB();
					if (accId > 0) {
						accId = -1;
					}
				}
			} catch (Exception e) {
				accId = 0;
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			todo = false;
			addDao = null;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return accId;
	}

	@Override
	public List<Map<String, Object>> createAuth(int accLevelType, long accId) throws SQLException {
		return ((IAccountManageDao) dao).createAuth(accLevelType, accId);
	}

	@Override
	public MgrBean getSeetingComData(long accId) {
		try {
			return ((IAccountManageDao) dao).getSeetingComData(accId);
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public MgrBean getSeetingScData(long accId) {
		MgrBean bean = null;
		try {
			bean = ((IAccountManageDao) dao).getSeetingMagData(accId);
			bean.setLowerLevelAccBalance(((IAccountManageDao) dao).getLowerLevelAccBalance(accId, 1));
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		return bean;
	}

	@Override
	public Map<String, Object> getSeetingManagerData(long accId) {
		Map<String, Object> map = null;
		try {
			map = new ConcurrentHashMap<String, Object>();
			MgrBean bean = ((IAccountManageDao) dao).getSeetingMagData(accId);
			bean.setLowerLevelAccBalance(((IAccountManageDao) dao).getLowerLevelAccBalance(accId, 1));
			map.put("accData", bean);
			map.put("upAccData", ((IAccountManageDao) dao).getSeetingMagUpData(accId));

		} catch (Exception e) {
			map = null;
			LOG.debug(e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> getSeetingMemberData(long accId) throws SQLException {
		Map<String, Object> map = null;
		try {
			map = new ConcurrentHashMap<String, Object>();
			map.put("accData", ((IAccountManageDao) dao).getSeetingMemData(accId));
			map.put("upAccData", ((IAccountManageDao) dao).getSeetingMemUpData(accId));
		} catch (Exception e) {
			ShowLog.err(LOG, e);
			map = null;
		}
		return map;
	}

	@Override
	public boolean updateMem(Map<String, Object> map) {
		boolean todo = true;
		IAccountManageDao addDao = null;
		Map<String, Map<String, String>> logDataMap = null;
		JSONObject responseJSONObject = null;
		MemBean oldBean = null;
		MemBean newBean = null;
		
		boolean isUpdate = false;
		try {
			addDao = (IAccountManageDao) dao;

			oldBean = ((IAccountManageDao) dao).getSeetingMemData(Long.parseLong("" + map.get("accId")));
			if (oldBean.getNickname() == null) {
				oldBean.setNickname("");
			}
			newBean = (MemBean) CommandUtil.sliceBean(oldBean);
			if (!CommandUtil.equalsString(oldBean.getNickname(), map.get("nickname"))
					|| !CommandUtil.equalsString(oldBean.getPwd(), map.get("pwd"))
					|| !CommandUtil.equalsString(oldBean.getHandicap(), map.get("handicap"))) {
				todo = addDao.updateMemAcc(Long.parseLong("" + map.get("accId")), "" + map.get("pwd"), "" + map.get("nickname"), Integer.parseInt("" + map.get("handicap")));
			}
			if (todo) {
				todo = addDao.updateLastUpdate(Long.parseLong("" + map.get("accId")), Long.parseLong("" + map.get("updateAccId")), "" + map.get("ip"),
						"ctt_manager.ctt_member_last_update");
			}
			if (todo && map.keySet().contains("nickname") && !"".equals(map.get("nickname"))
					&& !CommandUtil.equalsString(oldBean.getNickname(), map.get("nickname"))) {
				newBean.setNickname("" + map.get("nickname"));
				logDataMap = CommandUtil.compareBeanDiff(oldBean, newBean);
				responseJSONObject = new JSONObject(logDataMap);
				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_member_update_log", USERID, Long.parseLong("" + map.get("accId")), 0, OPS_TYPE_MANAGER,
						ACC_TYPE_MEMBER, LOG_ACTION_SET_NICKNAME, responseJSONObject.toString(), USERIP);
			}
			for (int i = 0; i < GAME_TYPE_ARR.length && todo == true; i++) {
				if (todo) {
					if (!CommandUtil.compareInt(map.get("minRatioG" + GAME_TYPE_ARR[i]),
							oldBean.getClass().getMethod("getG" + GAME_TYPE_ARR[i] + "MinRatioNext").invoke(oldBean, null))) {
						todo = addDao.updateMemRatio(Long.parseLong("" + map.get("accId")), GAME_TYPE_ARR[i],
								Integer.parseInt("" + map.get("minRatioG" + GAME_TYPE_ARR[i])));
						if (todo) {
							oldBean.getClass().getMethod("setG" + GAME_TYPE_ARR[i] + "MinRatioNext", int.class).invoke(newBean,
									Integer.parseInt("" + map.get("minRatioG" + GAME_TYPE_ARR[i])));
						}
						
						isUpdate =true;
					}
				}
			}
			if(isUpdate == true) {
				for (int i = 0; i < GAME_TYPE_ARR.length && todo == true; i++) {
					List<Integer> ration = addDao.selectGameRatioUpdateMem(Long.parseLong("" + map.get("accId")), GAME_TYPE_ARR[i],
							Integer.parseInt("" + map.get("minRatioG" + GAME_TYPE_ARR[i])));
					todo = addDao.updateMemberRatioTotal(Long.parseLong("" + map.get("accId")), GAME_TYPE_ARR[i], ration);
				}

			}
			
			if (todo) {
				logDataMap = CommandUtil.compareBeanDiff(oldBean, newBean);
				responseJSONObject = new JSONObject(logDataMap);
				if (!BaseAction.checkMapEmpty(logDataMap)) {
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_member_update_log", USERID, Long.parseLong("" + map.get("accId")), 0,
							OPS_TYPE_MANAGER, 2, LOG_ACTION_SET_RATIO, responseJSONObject.toString(), USERIP);
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (Exception e) {
				todo = false;
				LOG.debug("updateMgr_Exception=" + e.getMessage());
				ShowLog.err(LOG, e);
			}
			addDao = null;
			responseJSONObject = null;
			oldBean = null;
			newBean = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return todo;
	}

	@Override
	public boolean updateCom(Map<String, Object> map) {
		boolean todo = true;
		String lastUpdatetable = "ctt_manager.ctt_manager_last_update";
		Map<String, Map<String, String>> logDataMap = null;
		JSONObject responseJSONObject = null;
		MgrBean oldBean = null;
		MgrBean newBean = null;
		try {
			oldBean = ((IAccountManageDao) dao).getSeetingComData(Long.parseLong("" + map.get("accId")));
			if (oldBean.getNickname() == null) {
				oldBean.setNickname("");
			} else if (oldBean.getPwd() == null) {
				oldBean.setPwd("");
			}
			newBean = (MgrBean) CommandUtil.sliceBean(oldBean);
			if (!CommandUtil.equalsString(oldBean.getPwd(), map.get("pwd")) || !CommandUtil.equalsString(oldBean.getNickname(), map.get("nickname"))
					|| !CommandUtil.compareInt(oldBean.getAuthGroup(), map.get("authGroup"))) {
				todo = ((IAccountManageDao) dao).updateMgrAcc(Long.parseLong("" + map.get("accId")), "" + map.get("pwd"), "" + map.get("nickname"),
						Integer.parseInt("" + map.get("authGroup")), -1 , 0);
			}
			if (todo) {
				todo = ((IAccountManageDao) dao).updateLastUpdate(Long.parseLong("" + map.get("accId")), Long.parseLong("" + map.get("updateAccId")),
						"" + map.get("ip"), lastUpdatetable);
			}
			if (todo) {
				if (map.keySet().contains("nickname") && !"".equals(map.get("nickname"))
						&& !CommandUtil.equalsString(oldBean.getNickname(), map.get("nickname"))) {
					newBean.setNickname("" + map.get("nickname"));
					logDataMap = CommandUtil.compareBeanDiff(oldBean, newBean);
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, Long.parseLong("" + map.get("accId")), 0,
							OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_SET_NICKNAME, responseJSONObject.toString(), USERIP);
				}
				if (map.keySet().contains("pwd") && !"".equals(map.get("pwd")) && !CommandUtil.equalsString(oldBean.getPwd(), map.get("pwd"))) {
					newBean.setPwd("" + map.get("pwd"));
					logDataMap = CommandUtil.compareBeanDiff(oldBean, newBean);
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, Long.parseLong("" + map.get("accId")), 0,
							OPS_TYPE_MANAGER, 1, LOG_ACTION_SET_PASSWORD, LOG_NO_DATA, USERIP);
				}
				if (map.keySet().contains("authGroup") && !"".equals(map.get("authGroup"))
						&& !CommandUtil.compareInt(oldBean.getAuthGroup(), map.get("authGroup"))) {
					newBean.setPwd("" + map.get("authGroup"));
					logDataMap = CommandUtil.compareBeanDiff(oldBean, newBean);
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, Long.parseLong("" + map.get("accId")), 0,
							OPS_TYPE_MANAGER, 1, LOG_ACTION_SET_PASSWORD, LOG_NO_DATA, USERIP);
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					dao.commitDB();
				} else {
					dao.rollBackDB();
				}
			} catch (Exception e) {
				todo = false;
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			lastUpdatetable = "";
			lastUpdatetable = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
			responseJSONObject = null;
			oldBean = null;
			newBean = null;
		}
		return todo;
	}

	@Override
	public boolean updateMgr(Map<String, Object> map) {
		boolean todo = true;
		IAccountManageDao addDao = null;
		List<Map<String, Object>> accIdMapList = null;
		Map<String, Map<String, String>> logDataMap = null;
		JSONObject responseJSONObject = null;
		MgrBean oldBean = null;
		MgrBean newBean = null;
		try {
			boolean isFullRaio = Integer.parseInt("" + map.get("fullRatio")) == FULL_RATIO_ENABLED;

			addDao = (IAccountManageDao) dao;
			accIdMapList = new ArrayList<Map<String, Object>>();
			oldBean = ((IAccountManageDao) dao).getSeetingMagData(Long.parseLong("" + map.get("accId")));
			if (oldBean.getNickname() == null) {
				oldBean.setNickname("");
			} else if (oldBean.getPwd() == null) {
				oldBean.setPwd("");
			}
			newBean = (MgrBean) CommandUtil.sliceBean(oldBean);
			if (!CommandUtil.equalsString(oldBean.getPwd(), map.get("pwd")) || !CommandUtil.equalsString(oldBean.getNickname(), map.get("nickname"))
					|| !CommandUtil.compareInt(oldBean.getAuthGroup(), map.get("authGroup"))
					|| !CommandUtil.compareInt(oldBean.getFullRatio(), map.get("fullRatio"))
					|| !CommandUtil.compareInt(oldBean.getHandicap(), map.get("handicap"))) {
				
				todo = addDao.updateMgrAcc(Long.parseLong("" + map.get("accId")), "" + map.get("pwd"), "" + map.get("nickname"),
						Integer.parseInt("" + map.get("authGroup")), Integer.parseInt("" + map.get("fullRatio")), Integer.parseInt("" + map.get("handicap")));
				
				if(!CommandUtil.compareInt(oldBean.getHandicap(), map.get("handicap"))) {
					if(todo) {
						todo = addDao.updateMagHandicap(Long.parseLong("" + map.get("accId")) , Integer.parseInt("" + map.get("handicap")));
					}
					if(todo) {
						todo = addDao.updateMemHandicap(Long.parseLong("" + map.get("accId")) , Integer.parseInt("" + map.get("handicap")));
					}
				}
			}
			boolean isUpdate = false;
			for (int i = 0; i < GAME_TYPE_ARR.length && todo == true; i++) {
				if (!CommandUtil.compareInt(map.get("minRatioG" + GAME_TYPE_ARR[i]), oldBean.getClass().getMethod("getG" + GAME_TYPE_ARR[i] + "MinRatioNext").invoke(oldBean, null))
						|| !CommandUtil.compareInt(map.get("maxRatioG" + GAME_TYPE_ARR[i]), oldBean.getClass().getMethod("getG" + GAME_TYPE_ARR[i] + "MaxRatioNext").invoke(oldBean, null))
						|| !CommandUtil.compareInt(map.get("currentRatioG" + GAME_TYPE_ARR[i]), oldBean.getClass().getMethod("getG" + GAME_TYPE_ARR[i] + "CurrentMaxRatioNext").invoke(oldBean, null))) {
					todo = addDao.updateMgrRatio(Long.parseLong("" + map.get("accId")), GAME_TYPE_ARR[i],
							Integer.parseInt("" + map.get("minRatioG" + GAME_TYPE_ARR[i])),
							Integer.parseInt("" + map.get("maxRatioG" + GAME_TYPE_ARR[i])),
							Integer.parseInt("" + map.get("currentRatioG" + GAME_TYPE_ARR[i])));
					if (todo) {
						oldBean.getClass().getMethod("setG" + (i + 1) + "MaxRatioNext", int.class).invoke(newBean,
								Integer.parseInt("" + map.get("maxRatioG" + GAME_TYPE_ARR[i])));
						oldBean.getClass().getMethod("setG" + (i + 1) + "MinRatioNext", int.class).invoke(newBean,
								Integer.parseInt("" + map.get("minRatioG" + GAME_TYPE_ARR[i])));
						oldBean.getClass().getMethod("setG" + (i + 1) + "CurrentMaxRatioNext", int.class).invoke(newBean,
								Integer.parseInt("" + map.get("currentRatioG" + GAME_TYPE_ARR[i])));
						
						isUpdate = true;
					}
				}
				if (isFullRaio == true) {
					if (todo) {
						todo = addDao.updateLowerLevelMagRatio(Long.parseLong("" + map.get("accId")),
								Integer.parseInt("" + map.get("currentRatioG" + GAME_TYPE_ARR[i])), GAME_TYPE_ARR[i]);
					}
					if (todo) {
						todo = addDao.updateLowerLevelMemRatio(Long.parseLong("" + map.get("accId")),
								Integer.parseInt("" + map.get("currentRatioG" + GAME_TYPE_ARR[i])), GAME_TYPE_ARR[i]);
					}
				}
			}
			if(isUpdate == true) {
				List<Long> listLong = addDao.findTotalRatioAccId(Long.parseLong("" + map.get("accId")));
				for (int k = 0; k < listLong.size(); k++) {
					for (int i = 0; i < GAME_TYPE_ARR.length && todo == true; i++) {
						List<Integer> ration = addDao.selectGameRatio(listLong.get(k), GAME_TYPE_ARR[i], Long.parseLong("" + map.get("accId")),
								Integer.parseInt("" + map.get("minRatioG" + GAME_TYPE_ARR[i])),
								Integer.parseInt("" + map.get("maxRatioG" + GAME_TYPE_ARR[i])),
								Integer.parseInt("" + map.get("currentRatioG" + GAME_TYPE_ARR[i])), isFullRaio);
						todo = addDao.updateMemberRatioTotal(listLong.get(k), GAME_TYPE_ARR[i], ration);
					}
				}
			}
			if (todo) {
				todo = addDao.updateLastUpdate(Long.parseLong("" + map.get("accId")), Long.parseLong("" + map.get("updateAccId")), "" + map.get("ip"),
						"ctt_manager.ctt_manager_last_update");
			}
			if (todo) {
				if (map.keySet().contains("nickname") && !"".equals(map.get("nickname"))
						&& !CommandUtil.equalsString(oldBean.getNickname(), map.get("nickname"))) {
					newBean.setNickname("" + map.get("nickname"));
					logDataMap = CommandUtil.compareBeanDiff(oldBean, newBean);
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, Long.parseLong("" + map.get("accId")), 0,
							OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_SET_NICKNAME, responseJSONObject.toString(), USERIP);
				}
				if (map.keySet().contains("pwd") && !"".equals(map.get("pwd")) && !CommandUtil.equalsString(oldBean.getPwd(), map.get("pwd"))) {
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, Long.parseLong("" + map.get("accId")), 0,
							OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_SET_PASSWORD, LOG_NO_DATA, USERIP);
				}
				if (map.keySet().contains("authGroup") && !"".equals(map.get("authGroup"))
						&& !CommandUtil.compareInt(oldBean.getAuthGroup(), map.get("authGroup"))) {
					newBean.setPwd("" + map.get("authGroup"));
					logDataMap = CommandUtil.compareBeanDiff(oldBean, newBean);
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, Long.parseLong("" + map.get("accId")), 0,
							OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_SET_PASSWORD, LOG_NO_DATA, USERIP);
				}
				if (map.keySet().contains("fullRatio") && !"".equals(map.get("fullRatio"))
						&& !CommandUtil.compareInt(oldBean.getNextFullRatio(), map.get("fullRatio"))) {
					newBean.setNextFullRatio(Integer.parseInt("" + map.get("fullRatio")));
					logDataMap = CommandUtil.compareBeanDiff(oldBean, newBean);
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, Long.parseLong("" + map.get("accId")), 0,
							OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_SET_FULL_RATIO, responseJSONObject.toString(), USERIP);
				}
				logDataMap = CommandUtil.compareBeanDiff(oldBean, newBean);
				if (logDataMap != null && (logDataMap.containsKey("g1MinRatioNext") || logDataMap.containsKey("g2MinRatioNext")
						|| logDataMap.containsKey("g3MinRatioNext") || logDataMap.containsKey("g4MinRatioNext")
						|| logDataMap.containsKey("g5MinRatioNext") || logDataMap.containsKey("g1MaxRatioNext")
						|| logDataMap.containsKey("g2MaxRatioNext") || logDataMap.containsKey("g3MaxRatioNext")
						|| logDataMap.containsKey("g4MaxRatioNext") || logDataMap.containsKey("g5MaxRatioNext")
						|| logDataMap.containsKey("g1CurrentMaxRatioNext") || logDataMap.containsKey("g2CurrentMaxRatioNext")
						|| logDataMap.containsKey("g3CurrentMaxRatioNext") || logDataMap.containsKey("g4CurrentMaxRatioNext")
						|| logDataMap.containsKey("g5CurrentMaxRatioNext"))) {
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, Long.parseLong("" + map.get("accId")), 0,
							OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_SET_RATIO, responseJSONObject.toString(), USERIP);
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("updateMgr_Exception=" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (Exception e) {
				todo = false;
				LOG.debug("updateMgr_Exception=" + e.getMessage());
				ShowLog.err(LOG, e);
			}
			addDao = null;
			responseJSONObject = null;
			oldBean = null;
			newBean = null;
			if (accIdMapList != null) {
				accIdMapList.clear();
				accIdMapList = null;
			}
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return todo;
	}

	@Override
	public boolean updateMemberWithdrawPwd(long accId) {
		boolean todo = false;
		try {
			todo = ((IAccountManageDao) dao).updateMemberWithdrawPwd(accId);
			if (todo) {
				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_member_update_log", USERID, accId, 0, OPS_TYPE_MANAGER, ACC_TYPE_MEMBER,
						LOG_ACTION_SET_WITHDRAWAL_PASSWORD, LOG_NO_DATA, USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("updateMemberWithdrawPwd_Exception=" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				todo = false;
				LOG.debug("updateMemberWithdrawPwd_Exception=" + e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return todo;
	}

	@Override
	public boolean updateMemberPwd(long accId, String pwd) {
		boolean todo = false;
		try {
			todo = ((IAccountManageDao) dao).updateMemberPwd(accId, pwd);
			if (todo) {
				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_member_update_log", USERID, accId, 0, OPS_TYPE_MANAGER, ACC_TYPE_MEMBER, LOG_ACTION_SET_PASSWORD,
						LOG_NO_DATA, USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("updateMemberPwd_Exception=" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				todo = false;
				LOG.debug("updateMemberPwd_SQLException=" + e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return todo;
	}

	@Override
	public int updateAccStatus(long accId, int status, int accLevelType) {
		boolean todo = false;
		String tableName = null;
		int accType = 0;
		int accStatusOpsType = 0;
		try {
			if (accLevelType >= ACC_LEVEL_COM && accLevelType < ACC_LEVEL_MEM) {
				tableName = "manager";
				accType = ACC_TYPE_MANAGER;
			} else if (accLevelType == ACC_LEVEL_MEM) {
				tableName = "member";
				accType = ACC_TYPE_MEMBER;
			}
			if (status == STATUS_ENABLED) {
				todo = ((IAccountManageDao) dao).updateStatusEnable(accId, accLevelType);
				if (todo) {
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_" + tableName + "_update_log", USERID, accId, 0, OPS_TYPE_MANAGER, accType,
							LOG_ACTION_ENABLE_ACC, "", USERIP);
				}
				if (todo) {
					accStatusOpsType = STATUS_ENABLED;
				} else {
					accStatusOpsType = STATUS_ENABLED_UPDATE_FAIL;
				}
			} else if (status == STATUS_NOLOGIN) {
				todo = ((IAccountManageDao) dao).updateStatusCantLogin(accId, accLevelType);
				if (todo) {
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_" + tableName + "_update_log", USERID, accId, 0, OPS_TYPE_MANAGER, accType,
							LOG_ACTION_DO_NOT_LOGIN_ACC, "", USERIP);
				}
				if (todo) {
					accStatusOpsType = STATUS_NOLOGIN;
				} else {
					accStatusOpsType = STATUS_NOLOGIN_UPDATE_FAIL;
				}
			} else if (status == STATUS_DISABLED) {
				todo = ((IAccountManageDao) dao).updateStatusDisable(accId, accLevelType);
				if (todo) {
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_" + tableName + "_update_log", USERID, accId, 0, OPS_TYPE_MANAGER, accType,
							LOG_ACTION_DISABLE_ACC, "", USERIP);
				}
				if (todo) {
					accStatusOpsType = STATUS_DISABLED;
				} else {
					accStatusOpsType = STATUS_DISABLED_UPDATE_FAIL;
				}
			} else if (status == STATUS_DELETE) {
				todo = ((IAccountManageDao) dao).updateStatusDelete(accId, accLevelType);
				if (todo) {
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_" + tableName + "_update_log", USERID, accId, 0, OPS_TYPE_MANAGER, accType,
							LOG_ACTION_DELETE_ACC, "", USERIP);
				}
				if (todo) {
					accStatusOpsType = STATUS_DELETE;
				} else {
					accStatusOpsType = STATUS_DELETE_UPDATE_FAIL;
				}
			}
		} catch (Exception e) {
			accStatusOpsType = 0;
			LOG.debug("updateAccStatus_Exception=" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				todo = false;
				LOG.debug("updateAccStatus_SQLException=" + e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return accStatusOpsType;
	}

	@Override
	public Map<String, Object> getAllAccData(long accId) {
		return ((IAccountManageDao) dao).getAllAccData(accId);
	}

	@Override
	public Map<String, Object> checkAccRatio(long accId, int g1CurrentRatio, int g2CurrentRatio, int g3CurrentRatio, int g4CurrentRatio,
			int g5CurrentRatio) {
		System.out.print("checkAccRatio");
		boolean tool = true;
		String[] overRatio = null;
		String[] overRatioMem = null;
		Map<String, Object> result = null;
		Map<String, Object> incompatibleGameMap = null;

		List<Map<String, Object>> overRatioAcc = ((IAccountManageDao) dao).checkAccRatio(accId, g1CurrentRatio, g2CurrentRatio, g3CurrentRatio,
				g4CurrentRatio, g5CurrentRatio);
		List<Map<String, Object>> overRatioMemAcc = ((IAccountManageDao) dao).checkMemRatio(accId, g1CurrentRatio, g2CurrentRatio, g3CurrentRatio,
				g4CurrentRatio, g5CurrentRatio);
		Map<String, Object> map = null;

		String scStr = "";
		String bcStr = "";
		String coStr = "";
		String saStr = "";
		String agStr = "";
		String ag1Str = "";
		String ag2Str = "";
		String ag3Str = "";
		String ag4Str = "";
		String ag5Str = "";
		String ag6Str = "";
		String ag7Str = "";
		String ag8Str = "";
		String ag9Str = "";
		String ag10Str = "";
		String memStr = "";

		try {
			result = new ConcurrentHashMap<String, Object>();
			incompatibleGameMap = new ConcurrentHashMap<String, Object>();
			for (int i = 0; i < overRatioAcc.size(); i++) {
				for (int j = 0; j < GAME_TYPE_ARR.length; j++) {
					map = new ConcurrentHashMap<String, Object>();
					scStr = "";
					bcStr = "";
					coStr = "";
					saStr = "";
					agStr = "";
					ag1Str = "";
					ag2Str = "";
					ag3Str = "";
					ag4Str = "";
					ag5Str = "";
					ag6Str = "";
					ag7Str = "";
					ag8Str = "";
					ag9Str = "";
					ag10Str = "";
					memStr = "";

					if (overRatioAcc.get(i).get("g" + GAME_TYPE_ARR[j] + "OverAcc") != null) {
						overRatio = overRatioAcc.get(i).get("g" + GAME_TYPE_ARR[j] + "OverAcc").toString().split(",");
					} else {
						overRatio = new String[0];
					}

					if (overRatioMemAcc.get(i).get("g" + GAME_TYPE_ARR[j] + "OverAcc") != null) {
						overRatioMem = overRatioMemAcc.get(i).get("g" + GAME_TYPE_ARR[j] + "OverAcc").toString().split(",");
					} else {
						overRatioMem = new String[0];
					}

					for (int k = 0; k < overRatio.length; k++) {
						if (overRatio[k] != null && !"".equals(overRatio[k])) {
							String str = "";
							if (k == overRatio.length - 1) {
								str = overRatio[k].substring(1, overRatio[k].length());
							} else {
								str = overRatio[k].substring(1, overRatio[k].length()) + ",";
							}
							switch (Integer.parseInt(overRatio[k].substring(0, 1))) {
							case ACC_LEVEL_SC:
								if (scStr.length() > 200) {
									if (scStr.indexOf(".") > 0) {
										scStr += ".....";
									}
								} else {
									scStr += str;
								}

								break;
							case ACC_LEVEL_BC:
								if (bcStr.length() > 200) {
									if (bcStr.indexOf(".") > 0) {
										bcStr += ".....";
									}
								} else {
									bcStr += str;
								}

								break;
							case ACC_LEVEL_CO:
								if (coStr.length() > 200) {
									if (coStr.indexOf(".") > 0) {
										coStr += "...";
									}

								} else {
									coStr += str;
								}

								break;
							case ACC_LEVEL_SA:
								if (saStr.length() > 200) {
									if (saStr.indexOf(".") > 0) {
										saStr += ".....";
									}
								} else {
									saStr += str;
								}

								break;
							case ACC_LEVEL_AG:
								if (agStr.length() > 200) {
									if (agStr.indexOf(".") > 0) {
										agStr += ".....";
									}
								} else {
									agStr += str;
								}

								break;
							case ACC_LEVEL_AG1:
								if (ag1Str.length() > 200) {
									if (ag1Str.indexOf(".") > 0) {
										ag1Str += ".....";
									}
								} else {
									ag1Str += str;
								}

								break;
							case ACC_LEVEL_AG2:
								if (ag2Str.length() > 200) {
									if (ag2Str.indexOf(".") > 0) {
										ag2Str += ".....";
									}
								} else {
									ag2Str += str;
								}

								break;
							case ACC_LEVEL_AG3:
								if (ag3Str.length() > 200) {
									if (ag3Str.indexOf(".") > 0) {
										ag3Str += ".....";
									}
								} else {
									ag3Str += str;
								}

								break;
							case ACC_LEVEL_AG4:
								if (ag4Str.length() > 200) {
									if (ag4Str.indexOf(".") > 0) {
										ag4Str += ".....";
									}
								} else {
									ag4Str += str;
								}

								break;
							case ACC_LEVEL_AG5:
								if (ag5Str.length() > 200) {
									if (ag5Str.indexOf(".") > 0) {
										ag5Str += ".....";
									}
								} else {
									ag5Str += str;
								}

								break;
							case ACC_LEVEL_AG6:
								if (ag6Str.length() > 200) {
									if (ag6Str.indexOf(".") > 0) {
										ag6Str += ".....";
									}
								} else {
									ag6Str += str;
								}

								break;
							case ACC_LEVEL_AG7:
								if (ag7Str.length() > 200) {
									if (ag7Str.indexOf(".") > 0) {
										ag7Str += ".....";
									}
								} else {
									ag7Str += str;
								}

								break;
							case ACC_LEVEL_AG8:
								if (ag8Str.length() > 200) {
									if (ag8Str.indexOf(".") > 0) {
										ag8Str += ".....";
									}
								} else {
									ag8Str += str;
								}

								break;
							case ACC_LEVEL_AG9:
								if (ag9Str.length() > 200) {
									if (ag9Str.indexOf(".") > 0) {
										ag9Str += ".....";
									}
								} else {
									ag9Str += str;
								}

								break;
							case ACC_LEVEL_AG10:
								if (ag10Str.length() > 200) {
									if (ag10Str.indexOf(".") > 0) {
										ag10Str += ".....";
									}
								} else {
									ag10Str += str;
								}
								break;
							}
						}
					}

					for (int k = 0; k < overRatioMem.length; k++) {
						if (overRatioMem[k] != null && !"".equals(overRatioMem[k])) {
							if (memStr.length() > 200) {
								memStr += ".....";
								break;
							} else {
								if (k == overRatioMem.length - 1) {
									memStr += overRatioMem[k].substring(3, overRatioMem[k].length());
								} else {
									memStr += overRatioMem[k].substring(3, overRatioMem[k].length()) + ",";
								}
							}

						}
					}
					if (memStr.length() > 0 || scStr.length() > 0 || bcStr.length() > 0 || coStr.length() > 0 || saStr.length() > 0
							|| agStr.length() > 0 || ag1Str.length() > 0 || ag2Str.length() > 0 || ag3Str.length() > 0 || ag4Str.length() > 0
							|| ag5Str.length() > 0 || ag6Str.length() > 0 || ag7Str.length() > 0 || ag8Str.length() > 0 || ag9Str.length() > 0
							|| ag10Str.length() > 0) {
						map.put("sc", scStr);
						map.put("bc", bcStr);
						map.put("co", coStr);
						map.put("sa", saStr);
						map.put("ag", agStr);
						map.put("ag1", ag1Str);
						map.put("ag2", ag2Str);
						map.put("ag3", ag3Str);
						map.put("ag4", ag4Str);
						map.put("ag5", ag5Str);
						map.put("ag6", ag6Str);
						map.put("ag7", ag7Str);
						map.put("ag8", ag8Str);
						map.put("ag9", ag9Str);
						map.put("ag10", ag10Str);
						map.put("mem", memStr);

						incompatibleGameMap.put("" + GAME_TYPE_ARR[j], map);

						if (tool) {
							tool = false;
						}
					}

				}
			}

			LOG.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + incompatibleGameMap);

			result.put("isSuccess", tool);
			result.put("checkRatio", incompatibleGameMap);

			LOG.debug("checkRatio Service =>" + result.toString());
		} catch (Exception e) {
			tool = false;
			System.err.println(e.getMessage());
		} finally {
			scStr = null;
			bcStr = null;
			coStr = null;
			saStr = null;
			agStr = null;
			memStr = null;
			if (overRatioAcc != null) {
				overRatioAcc.clear();
				overRatioAcc = null;
			}
			if (overRatio != null) {
				overRatio.clone();
				overRatio = null;
			}
			if (overRatioMem != null) {
				overRatioMem.clone();
				overRatioMem = null;
			}

		}
		return result;
	}

	@Override
	public Map<String, Object> getLowerLevelAccTotal(long accId, String tokenId) {
		boolean boole = false;
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		boole = dao.checkTokenTimeOut("ctt_manager.ctt_manager_token", tokenId, accId);
		if (boole) {
			map = ((IAccountManageDao) dao).getLowerLevelAccTotal(accId);
			map.put("tokenId", "success");
		} else {
			map.put("tokenId", "fail");
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> showLog(long opsAccId, long updateAccId, int actionUpperId, int accLevel, int firstCount, int count) {
		return ((IAccountManageDao) dao).showLog(opsAccId, updateAccId, actionUpperId, accLevel, firstCount, count);
	}

	@Override
	public boolean PWDChange(String oldPassWord, String newPassWord) {
		boolean todo = false;
		try {
			todo = ((IAccountManageDao) dao).PWDChange(USERID, oldPassWord, newPassWord);
			if (todo) {
				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_update_log", USERID, USERID, 0, OPS_TYPE_MANAGER, ACC_TYPE_MANAGER, LOG_ACTION_SET_PASSWORD,
						"", USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("showLog_Exception=" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				todo = false;
				LOG.debug("showLog_SQLException=" + e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return todo;
	}

	@Override
	public int getLogTotalCount(long opsAccId, long updateAccId, int actionUpperId, int accLevel) {
		return ((IAccountManageDao) dao).getLogTotalCount(opsAccId, updateAccId, actionUpperId, accLevel);
	}

	@Override
	public Map<String, Object> getAccDetails(long accId) {
		Map<String, Object> map = ((IAccountManageDao) dao).getAccDetails(accId);
		map.putAll(((IAccountManageDao) dao).getBankCardData(accId));
		return map;
	}

	@Override
	public boolean updateBankDefaultData(long accId, String bank, String bankAcc, String bankAccName, String bankCardBranches, String area) {
		return ((IAccountManageDao) dao).updateBankDefaultData(accId, bank, bankAcc, bankAccName, bankCardBranches, area);
	}

	@Override
	public boolean updateBankNoDefaultData(long accId, String bank, String bankAcc, String bankAccName, String bankCardBranches, String area) {
		return ((IAccountManageDao) dao).updateBankNoDefaultData(accId, bank, bankAcc, bankAccName, bankCardBranches, area);
	}

	@Override
	public long insertGameServer(String gameServerId, int bet, int maxPlayer, int gameTimesType, int commission, int thread, int beatsTimeOut,
			int gameTimeOut, int autoTimeStamp, int waitContinueTimeOut, int continueTimesType, String serviceTime, int serverStatus) {
		boolean todo = false;
		long sid = 0;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		try {
			sid = ((IAccountManageDao) dao).insertGameServer(gameServerId, bet, maxPlayer, gameTimesType, commission, thread, beatsTimeOut,
					gameTimeOut, autoTimeStamp, waitContinueTimeOut, continueTimesType, serviceTime, serverStatus);
			if (sid > 0) {
				logDataMap = new ConcurrentHashMap<String, Object>();
				logDataMap.put("sid", sid);
				logDataMap.put("gameServerId", gameServerId);
				logDataMap.put("bet", bet);
				logDataMap.put("maxPlayer", maxPlayer);
				logDataMap.put("gameTimesType", gameTimesType);
				logDataMap.put("commission", commission);
				logDataMap.put("thread", thread);
				logDataMap.put("beatsTimeOut", beatsTimeOut);
				logDataMap.put("gameTimeOut", gameTimeOut);
				logDataMap.put("autoTimeStamp", autoTimeStamp);
				logDataMap.put("waitContinueTimeOut", waitContinueTimeOut);
				logDataMap.put("continueTimesType", continueTimesType);
				logDataMap.put("serviceTime", serviceTime);
				logDataMap.put("serverStatus", serverStatus);
				responseJSONObject = new JSONObject(logDataMap);
				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_data_ops_log", USERID, sid, sid, OPS_TYPE_MANAGER, 0, LOG_ACTION_ADD_GAME_SERVER,
						responseJSONObject.toString(), USERIP);
			}
		} catch (Exception e) {
			sid = 0;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo && sid > 0) {
					todo = dao.commitDB();
				} else {
					sid = 0;
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				sid = 0;
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			todo = false;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return sid;
	}

	@Override
	public boolean disableBankCard(int bankCardId, long accId) {
		Map<String, Object> mapLog = null;
		JSONObject jsonLog = null;
		boolean todo = false;
		try {
			mapLog = new ConcurrentHashMap<String, Object>();
			mapLog.put("bankCardId", bankCardId);
			jsonLog = new JSONObject(mapLog);

			todo = ((IAccountManageDao) dao).disableBankCard(bankCardId, accId);

			if (todo) {
				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_member_update_log", USERID, accId, 0, OPS_TYPE_MANAGER, ACC_TYPE_MEMBER, LOG_ACTION_SET_DETAIL,
						jsonLog.toString(), USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
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
			if (mapLog != null) {
				mapLog.clear();
				mapLog = null;
			}
			if (jsonLog != null) {
				jsonLog = null;
			}
		}
		return todo;
	}

	@Override
	public Map<String, Object> getGameServerData() {
		return ((IAccountManageDao) dao).getGameServerData();
	}

	@Override
	public boolean setGameServerData(int sid, String gameServerId, int nextBet, int nextMaxPlayer, int nextGameTimesType, int nextCommission,
			int nextThread, int nextBeatsTimeOut, int nextGameTimeOut, int nextAutoTimeStamp, int nextWaitContinueTimeOut, int nextContinueTimesType,
			String nextServiceTime, int nextServerStatus) {
		boolean todo = false;
		JSONObject responseJSONObject = null;
		Map<String, Map<String, String>> logDataMap = null;
		SetGameServerBean oldBean = null;
		SetGameServerBean newBean = null;
		try {
			oldBean = ((IAccountManageDao) dao).getGameServerDataForLog(sid);
			newBean = (SetGameServerBean) CommandUtil.sliceBean(oldBean);
			todo = ((IAccountManageDao) dao).updateGameServer(sid, gameServerId, nextBet, nextMaxPlayer, nextGameTimesType, nextCommission,
					nextThread, nextBeatsTimeOut, nextGameTimeOut, nextAutoTimeStamp, nextWaitContinueTimeOut, nextContinueTimesType, nextServiceTime,
					nextServerStatus);
			if (todo) {
				newBean.setGameServerId(gameServerId);
				newBean.setNextBet(nextBet);
				newBean.setNextMaxPlayer(nextMaxPlayer);
				newBean.setNextGameTimesType(nextGameTimesType);
				newBean.setNextCommission(nextCommission);
				newBean.setNextThread(nextThread);
				newBean.setNextBeatsTimeOut(nextBeatsTimeOut);
				newBean.setNextGameTimeOut(nextGameTimeOut);
				newBean.setNextAutoTimeStamp(nextAutoTimeStamp);
				newBean.setNextWaitContinueTimeOut(nextWaitContinueTimeOut);
				newBean.setNextContinueTimesType(nextContinueTimesType);
				newBean.setNextServiceTime(nextServiceTime);
				newBean.setNextServerStatus(nextServerStatus);
				logDataMap = CommandUtil.compareBeanDiff(oldBean, newBean);
				if (!BaseAction.checkMapEmpty(logDataMap)) {
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_data_ops_log", USERID, sid, sid, OPS_TYPE_MANAGER, 0, LOG_ACTION_SET_GAME_SERVER,
							responseJSONObject.toString(), USERIP);
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			oldBean = null;
			newBean = null;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return todo;
	}

	@Override
	public int getOrderLogTotalCount(int upperAction) {
		return ((IAccountManageDao) dao).getOrderLogTotalCount(upperAction);
	}

	@Override
	public List<Map<String, Object>> showOrderLog(int upperAction, int firstCount, int count) {
		return ((IAccountManageDao) dao).showOrderLog(upperAction, firstCount, count);
	}

	@Override
	public int getGameSetLogTotalCount(int upperAction) {
		return ((IAccountManageDao) dao).getGameSetLogTotalCount(upperAction);
	}

	@Override
	public List<Map<String, Object>> showGameSetLog(int upperAction, int firstCount, int count) {
		return ((IAccountManageDao) dao).showGameSetLog(upperAction, firstCount, count);
	}

	@Override
	public List<?> searchCOM(long userId, long accId, int status, int sort, int asc) {
		return ((IAccountManageDao) dao).searchCOM(userId, accId, status, sort, asc);
	}

	@Override
	public List<?> searchMAG(int levelType, long userId, long upAccId, long accId, int status, int sort, int asc, boolean checkStatus) {
		return ((IAccountManageDao) dao).searchMAG(levelType, userId, upAccId, accId, status, sort, asc, checkStatus);
	}

	@Override
	public List<?> searchMEM(int searchLevel, long userId, long upAccId, String accName, int memType, int status, int sort, int asc,
			boolean checkStatus, int firstCount, int count) {
		return ((IAccountManageDao) dao).searchMEM(searchLevel, userId, upAccId, accName, memType, status, sort, asc, checkStatus, firstCount, count);
	}

	@Override
	public int searchTotalCountMEM(int searchLevel, long userId, long upAccId, String accName, int memType, int status, boolean checkStatus) {
		return ((IAccountManageDao) dao).searchTotalCountMEM(searchLevel, userId, upAccId, accName, memType, status, checkStatus);
	}

}
