package tw.com.ctt.service.impl;

import java.math.BigDecimal;

import static tw.com.ctt.constant.AccountMemberConstant.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static tw.com.ctt.constant.LogToDBConstant.*;

import tw.com.ctt.action.AccountMemberAction;
import tw.com.ctt.dao.IAccountMemberDao;
import tw.com.ctt.model.MemBean;
import tw.com.ctt.service.IAccountMemberService;
import tw.com.ctt.util.CommandUtil;
import tw.com.ctt.util.ShowLog;

public class AccountMemberServiceImpl extends BaseService implements IAccountMemberService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7588251442765528152L;
	private static final Logger LOG = LogManager.getLogger(AccountMemberAction.class.getName());
	private static long USERID;
	private static String USERIP;

	public AccountMemberServiceImpl() {
		this.USERID = 0;
		this.USERIP = "";
	}

	public AccountMemberServiceImpl(long userId, String userIp) {
		this.USERID = userId;
		this.USERIP = userIp;
	}

	public AccountMemberServiceImpl(String userIp) {
		this.USERIP = userIp;
	}

	@Override
	public Map<String, Object> getMemAccData(String tokenId, long accId) {
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		boolean boole = false;
		boole = dao.checkTokenTimeOut("ctt_manager.ctt_member_token", tokenId, accId);
		if (boole) {
			map = ((IAccountMemberDao) dao).getMemAccData(tokenId, accId);
			map.put("tokenId", "success");
		} else {
			map.put("tokenId", "fail");
		}
		return map;
	}

	@Override
	public Map<String, Object> updatePwd(long accId, String tokenId, String oldPwd, String newPwd, String ip) {
		Map<String, Object> map = null;
		boolean boole = false;
		try {
			map = new ConcurrentHashMap<String, Object>();

			boole = ((IAccountMemberDao) dao).updatePwd(accId, oldPwd, newPwd);
			if (boole) {
				boole = dao.setLogToDBNoCommit("ctt_manager.ctt_member_update_log", USERID, USERID, NO_ID, OPS_TYPE_MEMBER, ACC_TYPE_MEMBER,
						LOG_ACTION_SET_PASSWORD, LOG_NO_DATA, USERIP);
			}
			if (boole) {
				boole = dao.colosMemTokenId(accId, tokenId);
			}
		} catch (Exception e) {
			boole = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (boole) {
					boole = dao.commitDB();
				} else {
					boole = dao.rollBackDB();
				}
			} catch (SQLException e) {
				boole = false;
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			map.put("isSuccess", boole);
			boole = false;
		}
		return map;
	}

	@Override
	public Map<String, Object> updateWithdrawPwd(long accId, String withdrawPwd, String ip) {
		Map<String, Object> map = null;
		boolean boole = false;
		try {
			map = new ConcurrentHashMap<String, Object>();
			boole = ((IAccountMemberDao) dao).updateWithdrawPwd(accId, withdrawPwd);

			if (boole) {
				boole = dao.setLogToDBNoCommit("ctt_manager.ctt_member_update_log", USERID, USERID, NO_ID, OPS_TYPE_MEMBER, ACC_TYPE_MEMBER,
						LOG_ACTION_SET_WITHDRAWAL_PASSWORD, LOG_NO_DATA, USERIP);
			}
		} catch (Exception e) {
			boole = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (boole) {
					boole = dao.commitDB();
				} else {
					boole = dao.rollBackDB();
				}
			} catch (SQLException e) {
				boole = false;
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			map.put("isSuccess", boole);
			boole = false;
		}
		return map;
	}

	@Override
	public boolean addRechargeOrder(long accId, String accName, String bankAccName, String bank, String bankAcc, String bankSid, int orderType,
			int status, BigDecimal amount, int currency, String postscript, int backOrderStatus, String bankDateTime) {
		boolean todo = false;
		long orderId = 0;
		int memtype = 0;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		try {
			logDataMap = new ConcurrentHashMap<String, Object>();

			memtype = ((IAccountMemberDao) dao).checkMemType(accId);
			if (memtype == GRNERAL_MEM) {
				orderId = ((IAccountMemberDao) dao).insertRecharge(accId, accName, bankAccName, bank, bankAcc, bankSid, orderType, status, amount,
						currency, postscript, backOrderStatus, bankDateTime);
			}
			if (orderId > 0) {
				logDataMap.put("accId", accId);
				logDataMap.put("accName", accName);
				logDataMap.put("bankAccName", bankAccName);
				logDataMap.put("bank", bank);
				logDataMap.put("bankAcc", bankAcc);
				logDataMap.put("bankSid", bankSid);
				logDataMap.put("orderType", orderType);
				logDataMap.put("status", status);
				logDataMap.put("amount", amount);
				logDataMap.put("currency", currency);
				logDataMap.put("postscript", postscript);
				logDataMap.put("backOrderStatus", backOrderStatus);
				logDataMap.put("bankDateTime", bankDateTime);
				responseJSONObject = new JSONObject(logDataMap);
				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_data_ops_log", USERID, USERID, orderId, OPS_TYPE_MEMBER, ACC_TYPE_MEMBER,
						LOG_ACTION_ADD_RECHARGE_ORDER, responseJSONObject.toString(), USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (memtype == GRNERAL_MEM) {
					if (todo) {
						todo = dao.commitDB();
					} else {
						todo = dao.rollBackDB();
					}
				}
			} catch (SQLException e) {
				todo = false;
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			orderId = 0;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return todo;
	}

	@Override
	public boolean addWithdrawalOrder(long accId, String accName, BigDecimal withdrawalAmount, int bankInfoList, String orderWithdrawPwd) {
		boolean todo = false;
		Map<String, Object> map = null;
		long orderId = 0;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		try {
			int memtype = ((IAccountMemberDao) dao).checkMemType(accId);
			map = ((IAccountMemberDao) dao).getMemAccInfo(accId);
			logDataMap = new ConcurrentHashMap<String, Object>();

			if (withdrawalAmount.compareTo(((BigDecimal) map.get("withdrawAmount"))) <= 0) {
				if (memtype == GRNERAL_MEM) {
					map = ((IAccountMemberDao) dao).addWithdrawalOrderGetBankCardInfo(accId, orderWithdrawPwd, bankInfoList);
					if (map.containsKey("accId")) {
						orderId = ((IAccountMemberDao) dao).insertWithdrawal(accId, accName, (String) map.get("bankAccName"),
								(String) map.get("bank"), (String) map.get("bankAcc"), 1, withdrawalAmount, 0);
					} else {
						LOG.debug("map no accId");
					}
				}
				if (orderId > 0) {
					todo = ((IAccountMemberDao) dao).charge(accId, withdrawalAmount);
				}
			}
			if (todo) {
				logDataMap.put("accId", accId);
				logDataMap.put("accName", accName);
				logDataMap.put("withdrawalAmount", withdrawalAmount);

				logDataMap.put("bankAccName", (String) map.get("bankAccName"));
				logDataMap.put("bank", (String) map.get("bank"));
				logDataMap.put("bankAcc", (String) map.get("bankAcc"));
				logDataMap.put("status", 1);
				logDataMap.put("currency", 0);
				responseJSONObject = new JSONObject(logDataMap);
				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_data_ops_log", USERID, USERID, orderId, OPS_TYPE_MEMBER, ACC_TYPE_MEMBER,
						LOG_ACTION_ADD_WITHDRAWAL_ORDER, responseJSONObject.toString(), USERIP);
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
					if (!map.containsKey("accId")) {
						((IAccountMemberDao) dao).updateWithdrawPwdFailed(accId);
					}
				}
			} catch (Exception err) {
				todo = false;
				LOG.debug(err.getMessage());
				ShowLog.err(LOG, err);
			}
			orderId = 0;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
			if (map != null) {
				map.clear();
				map = null;
			}
		}
		return todo;
	}

	@Override
	public Map<String, Object> getAccDetails(long accId) {
		return ((IAccountMemberDao) dao).getAccDetails(accId);
	}

	@Override
	public boolean updateAccDetails(long accId, String memberRealName, String phoneNumber, String qqAcc, String wechatAcc, String nickname,
			String ip) {
		boolean todo = true;
		Map<String, Map<String, String>> logDataMap = null;
		JSONObject responseJSONObject = null;
		MemBean oldBean = null;
		MemBean newBean = null;
		try {
			oldBean = ((IAccountMemberDao) dao).getAccDetailsForLog(USERID);
			newBean = (MemBean) CommandUtil.sliceBean(oldBean);
			if (!CommandUtil.equalsString(oldBean.getMemberRealName(), memberRealName) || !CommandUtil.equalsString(oldBean.getQqAcc(), qqAcc)
					|| !CommandUtil.equalsString(oldBean.getWechatAcc(), wechatAcc)
					|| !CommandUtil.compareInt(oldBean.getPhoneNumber(), phoneNumber)) {
				todo = ((IAccountMemberDao) dao).updateAccDetails(accId, memberRealName, phoneNumber, qqAcc, wechatAcc);
			}
			if (todo && !CommandUtil.equalsString(oldBean.getNickname(), nickname)) {
				todo = ((IAccountMemberDao) dao).updateAccNickname(accId, nickname);
			}
			if (todo) {
				newBean.setMemberRealName(memberRealName);
				newBean.setPhoneNumber(phoneNumber);
				newBean.setQqAcc(qqAcc);
				newBean.setWechatAcc(wechatAcc);
				newBean.setNickname(nickname);
				logDataMap = CommandUtil.compareBeanDiff(oldBean, newBean);
				responseJSONObject = new JSONObject(logDataMap);
				if (!CommandUtil.equalsString(oldBean.getMemberRealName(), memberRealName) || !CommandUtil.equalsString(oldBean.getQqAcc(), qqAcc)
						|| !CommandUtil.equalsString(oldBean.getWechatAcc(), wechatAcc)
						|| !CommandUtil.compareInt(oldBean.getPhoneNumber(), phoneNumber)
						|| !CommandUtil.compareInt(oldBean.getNickname(), nickname)) {
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_member_update_log", USERID, USERID, NO_ID, OPS_TYPE_MEMBER, ACC_TYPE_MEMBER,
							LOG_ACTION_SET_MEM_ACC, responseJSONObject.toString(), USERIP);
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
				todo = false;
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
	public boolean addBankCard(long accId, String bank, String bankAcc, String bankAccName, String bankCardBranches, String area) {
		boolean todo = false;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		try {
			logDataMap = new ConcurrentHashMap<String, Object>();

			todo = ((IAccountMemberDao) dao).addBankCard(accId, bank, bankAcc, bankAccName, bankCardBranches, area);
			if (todo) {
				logDataMap.put("accId", accId);
				logDataMap.put("bank", bank);
				logDataMap.put("bankAcc", bankAcc);
				logDataMap.put("bankAccName", bankAccName);
				logDataMap.put("bankCardBranches", bankCardBranches);
				logDataMap.put("area", area);
				responseJSONObject = new JSONObject(logDataMap);
				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_member_update_log", USERID, accId, NO_ID, OPS_TYPE_MEMBER, ACC_TYPE_MEMBER,
						LOG_ACTION_ADD_BANK_CARD, responseJSONObject.toString(), USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		}
		if (!todo) {
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
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return todo;
	}

	@Override
	public List<Map<String, Object>> getBankCardInfo(long accId) {
		return ((IAccountMemberDao) dao).getBankCardInfo(accId);
	}

	@Override
	public Map<String, Object> getPlatformCashBc(int platformId) {
		return ((IAccountMemberDao) dao).getPlatformCashBc(platformId);
	}

	@Override
	public boolean checkMemberAcc(String accName) {
		boolean todo = false;
		Map<String, Object> cashBCMap = null;
		try {
			todo = ((IAccountMemberDao) dao).checkMemberAcc(accName);
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (cashBCMap != null) {
				cashBCMap.clear();
				cashBCMap = null;
			}
		}
		return todo;
	}

	@Override
	public boolean addCashMemAcc(int platformId, String accName, String pwd, String ip) {
		boolean todo = false;
		Map<String, Object> cashBCMap = null;
		long accId = 0;
		IAccountMemberDao addDao = null;
		Map<String, Object> logDataMap = null;
		JSONObject responseJSONObject = null;
		try {
			addDao = (IAccountMemberDao) dao;
			logDataMap = new ConcurrentHashMap<String, Object>();
			cashBCMap = addDao.getPlatformCashBc(platformId);
			// String insertAccName = cashBCMap.get("cashBcName").toString()+accName;
			accId = addDao.addMember(accName, pwd ,Integer.parseInt(cashBCMap.get("handicap").toString()));
			if (accId > 0) {
				todo = addDao.addMemberLevel(accId, Long.parseLong(cashBCMap.get("cashBcId").toString()));
				if (todo) {
					todo = addDao.addCreate(accId, ip);
				}
				if (todo) {
					todo = addDao.addLastLogin(accId);
				}
				if (todo) {
					todo = addDao.addLastUpdate(accId, ip);
				}
//				if (todo) {
//					for (int i = 1; i <= 5; i++) {
//						if (todo) {
//							todo = addDao.addMemberRatio(accId, i, Byte.parseByte("0"));
//						}
//					}
//				}
//				if (todo) {
//					todo = addDao.MemberRatioTotal(accId);
//				}
				
				
				for (int i = 0; i < GAME_TYPE_ARR.length && todo == true; i++) {
					todo = addDao.addMemberRatio(accId, GAME_TYPE_ARR[i], Byte.parseByte("0"));

					if (todo) {
						List<Integer> ration = addDao.selectGameRatioMem(Long.parseLong(cashBCMap.get("cashBcId").toString()),	Byte.parseByte("0"), GAME_TYPE_ARR[i]);
						todo = addDao.memberRatioTotal(accId, GAME_TYPE_ARR[i], ration);
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
					logDataMap.put("memberType", GRNERAL_MEM);
					logDataMap.put("platformId", platformId);
					logDataMap.put("accName", accName);
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_member_update_log", accId, accId, NO_ID, OPS_TYPE_MEMBER, OPS_TYPE_MEMBER,
							LOG_ACTION_ADD_ACC, responseJSONObject.toString(), USERIP);
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
				todo = false;
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			if (cashBCMap != null) {
				cashBCMap.clear();
				cashBCMap = null;
			}
			if (addDao != null) {
				addDao = null;
			}
			accId = 0;
		}
		return todo;
	}
	

	@Override
	public Map<String, Object> getCashierInfo(int cashierId) {
		return ((IAccountMemberDao) dao).getCashierInfo(cashierId);
	}

	@Override
	public int getWithdrawPwdFailed(long accId) {
		return ((IAccountMemberDao) dao).getWithdrawPwdFailed(accId);
	}
	
	@Override
	public Map<String, Object> searchRecordsTotleCount(String startTime, String endTime) {
		return ((IAccountMemberDao) dao).searchRecordsTotle(USERID, startTime, endTime);
	}
	
	@Override
	public List<Map<String, Object>> searchRecords(String accName, String startTime, String endTime, int firstCount, int count) {
		return ((IAccountMemberDao) dao).searchRecords(USERID, accName, startTime, endTime, firstCount, count);
	}
}
