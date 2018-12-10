package tw.com.ctt.action;

import static tw.com.ctt.constant.AccountMemberConstant.CHECK_LENGTH_20;
import static tw.com.ctt.constant.AccountMemberConstant.MAX_AMOUNT;
import static tw.com.ctt.constant.AccountMemberConstant.MIN_AMOUNT;
import static tw.com.ctt.constant.GameConstant.ALL_GAME;
import static tw.com.ctt.constant.GameConstant.PUNCH_GAME;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.AccountMemberDaoImpl;
import tw.com.ctt.service.impl.AccountMemberServiceImpl;
import tw.com.ctt.util.AESUtil;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "AccountMember", urlPatterns = { "/AccountMember" })
public class AccountMemberAction extends BaseAction {

	private static final long serialVersionUID = -5390951620012326545L;
	private static final Logger LOG = LogManager.getLogger(AccountMemberAction.class.getName());

	public AccountMemberAction() {
	}

	public boolean checkLogin() {
		return true;
	}

	public void main(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			req.getRequestDispatcher("WEB-INF/Account/index.html").forward(req, res);
		} catch (ServletException e) {
			LOG.info("ServletException, " + e.getMessage());
			err(LOG, e);
		} catch (IOException e) {
			LOG.info("IOException, " + e.getMessage());
			err(LOG, e);
		}
	}

	public void getMemAccData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		String tokenId;
		long accId;
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				accId = accIdToLong(req.getParameter("accId").toString());
				tokenId = req.getParameter("tokenId").toString();
				service = new AccountMemberServiceImpl(accId, getIpAddr(req));
				dao = new AccountMemberDaoImpl();
				service.setDao(dao);
				tmpMap = service.getMemAccData(tokenId, accId);
			} else {

				tmpMap.put("tokenId", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
	}

	public void updatePwd(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		String tokenId;
		String newPwd;
		String oldPwd;
		long accId;
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;
		String ip = getIpAddr(req);
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {

				if (checkPWD(req.getParameter("newPwd").toString()) != null && checkPWD(req.getParameter("oldPwd").toString()) != null
						&& !"".equals(checkPWD(req.getParameter("newPwd").toString()))
						&& !"".equals(checkPWD(req.getParameter("oldPwd").toString()))) {
					tokenId = req.getParameter("tokenId").toString();
					newPwd = AESUtil.encrypt_CBC(checkPWD(req.getParameter("newPwd").toString()), KEY);
					oldPwd = AESUtil.encrypt_CBC(checkPWD(req.getParameter("oldPwd").toString()), KEY);

					accId = accIdToLong(req.getParameter("accId").toString());

					if (!checkLoginSec(req, res)) {
						tmpMap.put("tokenId", "fail");
					} else {
						service = new AccountMemberServiceImpl(accId, getIpAddr(req));
						dao = new AccountMemberDaoImpl();
						service.setDao(dao);
						tmpMap = service.updatePwd(accId, tokenId, oldPwd, newPwd, ip);

						tmpMap.put("tokenId", "success");
					}
				} else {
					tmpMap.put("isSuccess", false);
				}

			} else {
				tmpMap.put("tokenId", "fail");
			}

		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
	}

	public void updateWithdrawPwd(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		long accId;
		String withdrawPwd;
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;

		String ip = getIpAddr(req);
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				if (checkPWD(req.getParameter("withdrawPwd").toString()) != null
						&& !"".equals(checkPWD(req.getParameter("withdrawPwd").toString()))) {
					accId = accIdToLong(req.getParameter("accId").toString());
					withdrawPwd = AESUtil.encrypt_CBC(checkPWD(req.getParameter("withdrawPwd").toString()), KEY);

					if (!checkLoginSec(req, res)) {
						tmpMap.put("tokenId", "fail");
					} else {
						service = new AccountMemberServiceImpl(accId, getIpAddr(req));
						dao = new AccountMemberDaoImpl();
						service.setDao(dao);

						tmpMap = service.updateWithdrawPwd(accId, withdrawPwd, ip);
						tmpMap.put("tokenId", "success");
					}
				} else {
					tmpMap.put("isSuccess", false);
				}
			} else {
				tmpMap.put("tokenId", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
	}

	public void addRechargeOrder(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("addRechargeOrder");
		Map<String, Object> tmpMap = null;
		Map<String, Object> map = null;
		long accId;
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;

		int rechargeTypeBank = 1;
		int statusOne = 1;
		int currencyRMB = 0;
		int normalRechargeOrder = 0;

		boolean todo = false;
		String[] strKey = { "accName", "bankAccName", "bank", "bankAcc", "bankSid", "note", "bankDateTime" };
		String[] bigdecKey = { "amount" };
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				map = getRequestData(req, res);
				accId = accIdToLong(req.getParameter("accId").toString());
				if (!checkLoginSec(req, res)) {
					tmpMap.put("tokenId", "fail");
				} else {
					service = new AccountMemberServiceImpl(accId, getIpAddr(req));
					dao = new AccountMemberDaoImpl();
					service.setDao(dao);
					if (!"".equals(map.get("accName").toString()) && !"".equals(map.get("amount").toString())
							&& !"".equals(map.get("note").toString()) && !"".equals(map.get("bankSid").toString())
							&& !"".equals(map.get("bankAccName").toString()) && !"".equals(map.get("bankAcc").toString())
							&& !"".equals(map.get("bank").toString()) && !"".equals(map.get("bankDateTime").toString())) {
						for (int i = 0; i < strKey.length; i++) {
							if (map.keySet().contains(strKey[i])) {
								if (!"".equals(map.get(strKey[i]))) {
									try {
										map.put(strKey[i], map.get(strKey[i]).toString());
									} catch (Exception e) {
										LOG.debug(e.getMessage());
									}
								} else {
									map.put(strKey[i], "");
								}
							} else {
								map.put(strKey[i], "");
							}
						}
						for (int i = 0; i < bigdecKey.length; i++) {
							if (map.keySet().contains(bigdecKey[i])) {
								if (!"".equals(map.get(bigdecKey[i]))) {
									try {
										map.put(bigdecKey[i], new BigDecimal(map.get(bigdecKey[i]).toString()));
									} catch (Exception e) {
										map.put(bigdecKey[i], BigDecimal.ZERO);
									}
								} else {
									map.put(bigdecKey[i], BigDecimal.ZERO);
								}
							} else {
								map.put(bigdecKey[i], BigDecimal.ZERO);
							}
						}
						if (((BigDecimal) map.get("amount")).signum() > 0)
							if ((((BigDecimal) map.get("amount"))).compareTo(new BigDecimal(MAX_AMOUNT)) < 1)
								if (!"".equals(checkENandNumOnly((String) map.get("bankSid"))))
									if (!"".equals(checkAccount((String) map.get("accName"))))

										if (!"".equals(checkENandNumOnly((String) map.get("note"))))
											if (!"".equals(checkNameOut((String) map.get("bankAccName"))))
												if (!"".equals(clearNum((String) map.get("bankAcc"))))
													if (!"".equals(checkNameOut((String) map.get("bank"))))
														if (!"".equals(checkDateTimeFormat((String) map.get("bankDateTime")))) {

															todo = service.addRechargeOrder(accId, checkAccount((String) map.get("accName")),
																	checkNameOut((String) map.get("bankAccName")),
																	checkNameOut((String) map.get("bank")), clearNum((String) map.get("bankAcc")),
																	checkENandNumOnly((String) map.get("bankSid")), rechargeTypeBank, statusOne,
																	(BigDecimal) map.get("amount"), currencyRMB,
																	checkENandNumOnly((String) map.get("note")), normalRechargeOrder,
																	checkDateTimeFormat((String) map.get("bankDateTime")));
														}
					}

					if (todo) {
						tmpMap.put("message", "success");
					} else {
						tmpMap.put("message", "fail");
					}
					tmpMap.put("tokenId", "success");
				}
			} else {
				tmpMap.put("tokenId", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
			rechargeTypeBank = 0;
			statusOne = 0;
			currencyRMB = 0;
			normalRechargeOrder = 0;
		}
	}

	public void addWithdrawalOrder(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("addWithdrawalOrder");
		Map<String, Object> tmpMap = null;
		Map<String, Object> map = null;
		long accId;
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;

		boolean todo = false;
		BigDecimal withdrawMoney = null;
		String newOrderWithdrawPwd = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				map = getRequestData(req, res);
				accId = accIdToLong(req.getParameter("accId").toString());
				if (!checkLoginSec(req, res)) {
					tmpMap.put("tokenId", "fail");
				} else {
					service = new AccountMemberServiceImpl(accId, getIpAddr(req));
					dao = new AccountMemberDaoImpl();
					service.setDao(dao);
					if (!"".equals(map.get("accName").toString()) && !"".equals(map.get("withdrawalAmount").toString())
							&& Integer.parseInt("" + map.get("bankInfoList")) > 0 && !"".equals(checkPWD(map.get("orderWithdrawPwd").toString()))) {
						if (map.containsKey("withdrawalAmount")) {
							withdrawMoney = new BigDecimal("" + map.get("withdrawalAmount"));
						}
						newOrderWithdrawPwd = AESUtil.encrypt_CBC(checkPWD(map.get("orderWithdrawPwd").toString()), KEY);

						if (withdrawMoney.compareTo(new BigDecimal(MIN_AMOUNT)) >= 0 && withdrawMoney.compareTo(new BigDecimal(MAX_AMOUNT)) <= 0
								&& !"".equals(checkAccount((String) map.get("accName")))
								&& !"".equals(checkPWD(map.get("orderWithdrawPwd").toString()))) {
							todo = service.addWithdrawalOrder(accId, checkAccount((String) map.get("accName")), withdrawMoney,
									Integer.parseInt("" + map.get("bankInfoList")), newOrderWithdrawPwd);
						}
					}
					LOG.debug("addWithdrawalOrder_todo===============" + todo);
					if (todo) {
						tmpMap.put("message", "success");
					} else {
						int cou = service.getWithdrawPwdFailed(accId);
						tmpMap.put("message", "fail");
						tmpMap.put("failCount", cou);
					}
					tmpMap.put("tokenId", "success");
				}
			} else {
				tmpMap.put("tokenId", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
	}

	public void getAccDetails(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		long accId;
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				accId = accIdToLong(req.getParameter("accId").toString());
				if (!checkLoginSec(req, res)) {
					tmpMap.put("tokenId", "fail");
				} else {
					service = new AccountMemberServiceImpl(accId, getIpAddr(req));
					dao = new AccountMemberDaoImpl();
					service.setDao(dao);

					tmpMap = service.getAccDetails(accId);
					tmpMap.put("tokenId", "success");
				}

			} else {
				tmpMap.put("tokenId", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
	}

	public void updateAccDetails(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		String memberRealName = null;
		String phoneNumber = null;
		String qqAcc = null;
		String wechatAcc = null;
		String nickname = null;
		long accId;
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;

		String ip = getIpAddr(req);
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				accId = accIdToLong(req.getParameter("accId").toString());
				memberRealName = checkNameOut(req.getParameter("memberRealName") != null ? req.getParameter("memberRealName").toString() : "");
				phoneNumber = clearNum(req.getParameter("phoneNumber") != null ? clearNum(req.getParameter("phoneNumber").toString()) : "");
				qqAcc = checkENandNumOnly(req.getParameter("qqAcc") != null ? req.getParameter("qqAcc").toString() : "");
				wechatAcc = checkENandNumOnly(req.getParameter("wechatAcc") != null ? req.getParameter("wechatAcc").toString() : "");
				nickname = checkNameOut(req.getParameter("nickname") != null ? req.getParameter("nickname").toString() : "");

				if (!checkLoginSec(req, res)) {
					tmpMap.put("tokenId", "fail");
				} else {
					service = new AccountMemberServiceImpl(accId, getIpAddr(req));
					dao = new AccountMemberDaoImpl();
					service.setDao(dao);
					if (!"".equals(memberRealName) && (!"".equals(qqAcc) || !"".equals(wechatAcc)) && !"".equals(nickname)) {
						tmpMap.put("isSuccess", service.updateAccDetails(accId, memberRealName, phoneNumber, qqAcc, wechatAcc, nickname, ip));
					}
					tmpMap.put("tokenId", "success");
				}

			} else {
				tmpMap.put("tokenId", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;

			memberRealName = null;
			phoneNumber = null;
			qqAcc = null;
			wechatAcc = null;
			nickname = null;
		}
	}

	public void addBankCard(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("addBankCard");
		Map<String, Object> tmpMap = null;
		Map<String, Object> map = null;
		long accId;
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;

		boolean todo = false;
		String bank = null;
		String bankAcc = null;
		String bankAccName = null;
		String bankCardBranches = null;
		String area = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				map = getRequestData(req, res);
				accId = accIdToLong(req.getParameter("accId").toString());
				if (!checkLoginSec(req, res)) {
					tmpMap.put("tokenId", "fail");
				} else {
					bank = checkNameOut((String) map.get("bank") == null ? "" : (String) map.get("bank"));
					bankAcc = clearNum((String) map.get("bankAcc") == null ? "" : (String) map.get("bankAcc"));
					bankAccName = checkNameOut((String) map.get("bankAccName") == null ? "" : (String) map.get("bankAccName"));
					bankCardBranches = checkNameOut((String) map.get("bankCardBranches") == null ? "" : (String) map.get("bankCardBranches"));
					area = checkNameOut((String) map.get("area") == null ? "" : (String) map.get("area"));
					service = new AccountMemberServiceImpl(accId, getIpAddr(req));
					dao = new AccountMemberDaoImpl();
					service.setDao(dao);
					if (accId > 0 && !"".equals(bank) && !"".equals(bankAcc) && !"".equals(bankAccName) && !"".equals(bankCardBranches)
							&& !"".equals(area) && bank.length() <= CHECK_LENGTH_20 && bankAcc.length() <= CHECK_LENGTH_20
							&& bankAccName.length() <= CHECK_LENGTH_20 && bankCardBranches.length() <= CHECK_LENGTH_20
							&& area.length() <= CHECK_LENGTH_20) {
						todo = service.addBankCard(accId, bank, bankAcc, bankAccName, bankCardBranches, area);
					}
					tmpMap.put("isSuccess", todo);
					tmpMap.put("tokenId", "success");
				}
			} else {
				tmpMap.put("tokenId", "fail");
			}
		} catch (Exception e) {
			tmpMap.put("tokenId", "fail");
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
			todo = false;
			bank = null;
			bankAcc = null;
			bankAccName = null;
			bankCardBranches = null;
			area = null;
			accId = 0;
		}
	}

	public void getBankCardInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("getBankCardInfo");
		Map<String, Object> tmpMap = null;
		long accId;
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				accId = accIdToLong(req.getParameter("accId").toString());
				if (!checkLoginSec(req, res)) {
					tmpMap.put("tokenId", "fail");
				} else {
					service = new AccountMemberServiceImpl(accId, getIpAddr(req));
					dao = new AccountMemberDaoImpl();
					service.setDao(dao);
					if (accId > 0) {
						tmpMap.put("bankCardInfo", service.getBankCardInfo(accId));
					}
					tmpMap.put("tokenId", "success");
				}
			} else {
				tmpMap.put("tokenId", "fail");
			}
		} catch (Exception e) {
			tmpMap.put("tokenId", "fail");
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
			accId = 0;
		}
	}

	public void getPlatformCashBc(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		int platformId = 0;
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			if (req.getParameter("platformId") != null) {
				platformId = Integer.parseInt(req.getParameter("platformId").toString());

				service = new AccountMemberServiceImpl();
				dao = new AccountMemberDaoImpl();
				service.setDao(dao);
				tmpMap = service.getPlatformCashBc(platformId);
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;

		}
	}

	public void checkMemberAcc(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		String accName = "";
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("accName") != null) {
				accName = req.getParameter("accName").toString();

				service = new AccountMemberServiceImpl();
				dao = new AccountMemberDaoImpl();
				service.setDao(dao);
				tmpMap.put("isCheckMemberAcc", service.checkMemberAcc(accName));

			} else {
				tmpMap.put("isCheckMemberAcc", false);
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;

		}
	}

	public void addCashMemAcc(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("addCashMemAcc");
		Map<String, Object> tmpMap = null;
		String accName = "";
		String pwd = "";
		int platformId = 0;
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;

		String ip = getIpAddr(req);
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("accName") != null && req.getParameter("pwd") != null && req.getParameter("platformId") != null
					&& !"".equals(checkPWD(req.getParameter("pwd").toString()))) {
				accName = checkAccount(req.getParameter("accName").toString() == null ? "" : req.getParameter("accName").toString());
				pwd = AESUtil.encrypt_CBC(checkPWD(req.getParameter("pwd").toString() == null ? "" : req.getParameter("pwd").toString()), KEY);
				platformId = Integer.parseInt(req.getParameter("platformId").toString());

				service = new AccountMemberServiceImpl();
				dao = new AccountMemberDaoImpl();
				service.setDao(dao);
				if (!"".equals(accName) && !"".equals(pwd)) {
					tmpMap.put("isInsertAccMem", service.addCashMemAcc(platformId, accName, pwd, ip));
				}
			} else {
				tmpMap.put("isInsertAccMem", false);
			}

		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;

		}
	}

	public void getCashierInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("getCashierInfo");
		Map<String, Object> tmpMap = null;
		long accId;
		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;

		try {
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				tmpMap = new ConcurrentHashMap<String, Object>();
				accId = accIdToLong(req.getParameter("accId").toString());
				if (!checkLoginSec(req, res)) {
					tmpMap.put("tokenId", "fail");
				} else {
					service = new AccountMemberServiceImpl(accId, getIpAddr(req));
					dao = new AccountMemberDaoImpl();
					service.setDao(dao);
					tmpMap = service.getCashierInfo(Integer.parseInt(req.getParameter("cashierId").toString()));
					tmpMap.put("tokenId", "success");
				}
			}
		} catch (Exception e) {
			tmpMap.put("tokenId", "fail");
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
			accId = 0;
		}
	}

	public void searchRecords(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;

		AccountMemberServiceImpl service = null;
		AccountMemberDaoImpl dao = null;

		String[] check = { "gameName", "accName", "startTime", "endTime" };
		int chkMapCount = 0;
		Map<String, Object> map = null;

		int firstCount = 0;
		int count = 0;
		int page = 0;
		int nextPage = 0;
		int totalCount = 0;
		int totalPage = 0;
		String startTime = null;
		String endTime = null;
		SimpleDateFormat sdFormat = null;
		Map<String, Object> totleMap = null;
		long accId = 0;

		try {
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				tmpMap = new ConcurrentHashMap<String, Object>();
				accId = accIdToLong(req.getParameter("accId").toString());
				if (!checkLoginSec(req, res)) {
					tmpMap.put("tokenId", "fail");
				} else {
					tmpMap.put("tokenId", "success");
					sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					service = new AccountMemberServiceImpl(accId, getIpAddr(req));
					dao = new AccountMemberDaoImpl();
					service.setDao(dao);

					page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
					nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
					count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;
					startTime = req.getParameter("startTime") != null ? req.getParameter("startTime").toString() : null;
					endTime = req.getParameter("endTime") != null ? req.getParameter("endTime").toString() : null;

					map = getRequestData(req, res);
					totleMap = new ConcurrentHashMap<String, Object>();

					for (int c = 0; c < check.length; c++) {
						if (map.containsKey(check[c])) {
							chkMapCount++;
						}
					}

					if (chkMapCount == 4) {
						if (Integer.parseInt(map.get("gameName").toString()) == ALL_GAME) {

						}
						if (Integer.parseInt(map.get("gameName").toString()) == PUNCH_GAME) {
							totleMap = service.searchRecordsTotleCount(startTime, endTime);
							if (totleMap != null && !"".equals(totleMap.toString())) {
								totalCount = Integer.parseInt(totleMap.get("count").toString());
								tmpMap.put("gameName", "三國猜拳王");
								tmpMap.put("totleBet", totleMap.get("totleBet"));
								tmpMap.put("totleWinGoal", totleMap.get("totleWinGoal"));
								tmpMap.put("totleNetAmount", totleMap.get("totleNetAmount"));
							}
						}
					}
					LOG.debug("totalCount=" + totalCount);
					if (totalCount > 0) {
						totalPage = (int) Math.ceil((totalCount * 1.0) / (count * 1.0));
					} else {
						totalPage = 1;
					}

					if (nextPage == 0) {
						if (totalPage >= page && page > 0) {
							nextPage = page;
							firstCount = count * (page - 1);
						} else if (totalPage < page) {
							nextPage = totalPage;
							firstCount = totalCount - (count * (page - 1));
						} else if (page <= 0) {
							nextPage = 1;
							firstCount = 0;
						}
					} else {
						if (totalPage >= nextPage && nextPage > 0) {
							nextPage = nextPage;
							firstCount = count * (nextPage - 1);
						} else if (totalPage < nextPage) {
							nextPage = totalPage;
							firstCount = totalCount - (count * (nextPage - 1));
						} else if (nextPage <= 0) {
							nextPage = 1;
							firstCount = 0;
						}
					}
					LOG.debug("totalPage=" + totalPage);
					tmpMap.put("gameRecordsLastPage", totalPage);
					tmpMap.put("gameRecordsPage", nextPage);
					if (chkMapCount == 4) {
						if (Integer.parseInt(map.get("gameName").toString()) == ALL_GAME) {

						}
						if (Integer.parseInt(map.get("gameName").toString()) == PUNCH_GAME) {
							tmpMap.put("punchGameRecords",
									service.searchRecords(map.get("accName").toString(), startTime, endTime, firstCount, count));
						}
					}
					tmpMap.put("searchTime", sdFormat.format(new Date()));
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;

			firstCount = 0;
			count = 0;
			page = 0;
			nextPage = 0;
			totalCount = 0;
			totalPage = 0;

			check = null;
			chkMapCount = 0;
			if (map != null) {
				map.clear();
				map = null;
			}
		}
		return;
	}
}
