package tw.com.ctt.action;

import static tw.com.ctt.constant.LogToDBConstant.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.MoneyDaoImpl;
import tw.com.ctt.dao.impl.OrderDaoImpl;
import tw.com.ctt.service.impl.MoneyServiceImpl;
import tw.com.ctt.service.impl.OrderServiceImpl;
import tw.com.ctt.util.ShowLog;
import static tw.com.ctt.constant.OrderConstant.*;

@WebServlet(name = "Order", urlPatterns = { "/Order" })
public class OrderAction extends BaseAction {

	private static final long serialVersionUID = -1800888787815816193L;
	private static final Logger LOG = LogManager.getLogger(OrderAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/Order!checkMemberAccName", "/Order!addOrder", "/Order!searchOrderRechargeCarryOutData",
			"/Order!searchOrderRechargeUndoneData", "/Order!searchOrderWithdrawalCarryOutData", "/Order!searchOrderWithdrawalUndoneData",
			"/Order!getOrderRechargeUndoneData", "/Order!getOrderRechargeCarryOutData", "/Order!getOrderWithdrawalUndoneData",
			"/Order!getOrderWithdrawalCarryOutData", "/Order!getOrderReviewData", "/Order!getAuditRechargeOrderData",
			"/Order!getAuditWithdrawalOrderData", "/Order!checkRechargeCarryOutOrderData", "/Order!checkWithdrawalCarryOutOrderData",
			"/Order!checkOrderReview", "/Order!audit" };
	private String[] authURL = { "/Order!mainRechargeCarrOut", "/Order!mainWithdrawalCarryOut", "/Order!mainRechargeUndone",
			"/Order!mainWithdrawalUndone", "/Order!mainReview" };

	public OrderAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("OrderAction start");
	}

	public void mainRechargeCarrOut(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("mainRechargeCarrOut.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkRechargeCarrOut(){\n");
			sb.append("    if(typeof Order_RechargeCarrOut==='undefined' || typeof Order_RechargeCarrOut != 'function' ){\n");
			sb.append("        checkJS('Order_RechargeCarrOut', 'AccountManage/Order.js');\n");
			sb.append("    } else {\n");
			sb.append("        Order_RechargeCarrOut();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkRechargeCarrOut();\n");
			// clean extraJS
			sb.append("if(document.getElementById('extraJS') != null){\n");
			sb.append("    document.getElementById('extraJS').innerHTML = '';\n");
			sb.append("}\n");
			tmpMap.put("extraJS", sb.toString());
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				responseJSONObject = new JSONObject(tmpMap);
				out = res.getWriter();
				out.println(responseJSONObject);
				out.flush();
			}
		} catch (Exception e) {
			LOG.error("mainRechargeCarrOut.main.Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			tmpMap = null;
			responseJSONObject = null;
			out = null;
		}
		return;
	}

	public void mainWithdrawalCarryOut(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("mainWithdrawalCarryOut.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkWithdrawalCarryOut(){\n");
			sb.append("    if(typeof Order_WithdrawalCarryOut==='undefined' || typeof Order_WithdrawalCarryOut != 'function' ){\n");
			sb.append("        checkJS('Order_WithdrawalCarryOut', 'AccountManage/Order.js');\n");
			sb.append("    } else {\n");
			sb.append("        Order_WithdrawalCarryOut();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkWithdrawalCarryOut();\n");
			// clean extraJS
			sb.append("if(document.getElementById('extraJS') != null){\n");
			sb.append("    document.getElementById('extraJS').innerHTML = '';\n");
			sb.append("}\n");
			tmpMap.put("extraJS", sb.toString());
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				responseJSONObject = new JSONObject(tmpMap);
				out = res.getWriter();
				out.println(responseJSONObject);
				out.flush();
			}
		} catch (Exception e) {
			LOG.error("mainWithdrawalCarryOut.main.Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			tmpMap = null;
			responseJSONObject = null;
			out = null;
		}
		return;
	}

	public void mainRechargeUndone(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("mainRechargeUndone.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkRechargeUndone(){\n");
			sb.append("    if(typeof Order_RechargeUndone==='undefined' || typeof Order_RechargeUndone != 'function' ){\n");
			sb.append("        checkJS('Order_RechargeUndone', 'AccountManage/Order.js');\n");
			sb.append("    } else {\n");
			sb.append("        Order_RechargeUndone();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkRechargeUndone();\n");
			// clean extraJS
			sb.append("if(document.getElementById('extraJS') != null){\n");
			sb.append("    document.getElementById('extraJS').innerHTML = '';\n");
			sb.append("}\n");
			tmpMap.put("extraJS", sb.toString());
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				responseJSONObject = new JSONObject(tmpMap);
				out = res.getWriter();
				out.println(responseJSONObject);
				out.flush();
			}
		} catch (Exception e) {
			LOG.error("mainRechargeUndone.main.Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			tmpMap = null;
			responseJSONObject = null;
			out = null;
		}
		return;
	}

	public void mainWithdrawalUndone(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("mainRechargeUndone.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkWithdrawalUndone(){\n");
			sb.append("    if(typeof Order_WithdrawalUndone==='undefined' || typeof Order_WithdrawalUndone != 'function' ){\n");
			sb.append("        checkJS('Order_WithdrawalUndone', 'AccountManage/Order.js');\n");
			sb.append("    } else {\n");
			sb.append("        Order_WithdrawalUndone();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkWithdrawalUndone();\n");
			// clean extraJS
			sb.append("if(document.getElementById('extraJS') != null){\n");
			sb.append("    document.getElementById('extraJS').innerHTML = '';\n");
			sb.append("}\n");
			tmpMap.put("extraJS", sb.toString());
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				responseJSONObject = new JSONObject(tmpMap);
				out = res.getWriter();
				out.println(responseJSONObject);
				out.flush();
			}
		} catch (Exception e) {
			LOG.error("mainRechargeUndone.main.Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			tmpMap = null;
			responseJSONObject = null;
			out = null;
		}
		return;
	}

	public void mainReview(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("mainReview.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkReview(){\n");
			sb.append("    if(typeof Order_Review==='undefined' || typeof Order_Review != 'function' ){\n");
			sb.append("        checkJS('Order_Review', 'AccountManage/Order.js');\n");
			sb.append("    } else {\n");
			sb.append("        Order_Review();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkReview();\n");
			// clean extraJS
			sb.append("if(document.getElementById('extraJS') != null){\n");
			sb.append("    document.getElementById('extraJS').innerHTML = '';\n");
			sb.append("}\n");
			tmpMap.put("extraJS", sb.toString());
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				responseJSONObject = new JSONObject(tmpMap);
				out = res.getWriter();
				out.println(responseJSONObject);
				out.flush();
			}
		} catch (Exception e) {
			LOG.error("mainReview.main.Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			tmpMap = null;
			responseJSONObject = null;
			out = null;
		}
		return;
	}

	public void checkMemberAccName(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		boolean exist = false;
		Map<String, Object> tmpMap = null;
		OrderDaoImpl dao = null;
		OrderServiceImpl service = null;
		String accName = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			dao = new OrderDaoImpl();
			service = new OrderServiceImpl(userId, userIp);
			service.setDao(dao);
			accName = req.getParameter("acc_name");

			if (accName.length() >= 4 && accName.length() <= 20) {
				exist = service.checkMemberAccName(accName);
			}
			if (exist) {
				tmpMap.put("result", 1);
			} else {
				tmpMap.put("result", 0);
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

			userId = 0;
			userIp = null;
			exist = false;
			accName = null;
		}
		return;
	}

	public void addOrder(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		OrderDaoImpl dao = null;
		OrderServiceImpl service = null;
		Map<String, Object> map = null;
		Map<String, Object> tmpMap = null;
		boolean orderId = false;
		String[] strKey = { "member_acc_name", "bank_acc_name", "bank", "bank_acc", "bank_sid", "bank_deposit_datetime", "note", "remark" };
		String[] bigdecKey = { "amount", "bank_check_amount" };

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			dao = new OrderDaoImpl();
			service = new OrderServiceImpl(userId, userIp);
			service.setDao(dao);
			map = getRequestData(req, res);
			LOG.debug(map);
			if (!"".equals(map.get("member_acc_name").toString()) && !"".equals(map.get("amount").toString())
					&& !"".equals(map.get("remark").toString())) {
				for (int i = 0; i < strKey.length; i++) {
					if (map.keySet().contains(strKey[i])) {
						if (!"".equals(map.get(strKey[i]))) {
							try {
								map.put(strKey[i], map.get(strKey[i]).toString());
							} catch (Exception e) {
								LOG.info("Exception, " + e.getMessage());
								ShowLog.err(LOG, e);
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
				if (((BigDecimal) map.get("amount")).signum() > 0 && (((BigDecimal) map.get("amount"))).compareTo(new BigDecimal(MAX_AMOUNT)) < 1) {
					map.put("bank_acc", clearNum(map.get("bank_acc").toString()));
					map.put("bank_deposit_datetime", checkDateTimeFormat(map.get("bank_deposit_datetime").toString()));

					orderId = service.addRechargeOrder((String) map.get("member_acc_name"), (String) map.get("bank_acc_name"),
							(String) map.get("bank"), (String) map.get("bank_acc"), (String) map.get("bank_sid"),
							(BigDecimal) map.get("bank_check_amount"), (String) map.get("bank_deposit_datetime"), 1, 1,
							(BigDecimal) map.get("amount"), 0, (String) map.get("note"), 1, (String) map.get("remark"));
				}
			}

			if (orderId) {
				tmpMap.put("message", "success");
				tmpMap.put("orderId", orderId);
			} else {
				tmpMap.put("message", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);
			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);
			out.flush();
			out.close();
			out = null;
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (map != null) {
				map.clear();
				map = null;
			}
			userId = 0;
			userIp = null;
			orderId = false;
		}
		return;
	}

	public void searchOrderRechargeCarryOutData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("searchOrderRechargeCarryOutData");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;

		String accName = null;
		String audiAccName = null;
		String firstDatetime = null;
		String lastDatetime = null;
		Date firstDate = null;
		Date lastDate = null;
		Date nowDate = null;
		Date date = null;
		int status = 0;
		int aes = 1;
		int sort = 0;
		int firstCount = 0;
		int count = 0;
		int page = 0;
		int nextPage = 0;
		int totalCount = 0;
		int totalPage = 0;
		SimpleDateFormat formatter = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);

			accName = checkAccount(req.getParameter("accName") != null ? req.getParameter("accName").toString() : "");
			audiAccName = checkAccount(req.getParameter("audiAccName") != null ? req.getParameter("audiAccName").toString() : "");
			firstDatetime = checkDateTimeFormat(req.getParameter("firstDatetime").toString());
			lastDatetime = checkDateTimeFormat(req.getParameter("lastDatetime").toString());
			status = req.getParameter("status") != null ? Integer.parseInt(req.getParameter("status").toString()) : 0;
			aes = req.getParameter("aes") != null ? Integer.parseInt(req.getParameter("aes").toString()) : 1;
			sort = req.getParameter("sort") != null ? Integer.parseInt(req.getParameter("sort").toString()) : 0;
			page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
			nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
			count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;
			LOG.debug("accName--" + accName + "\naudiAccName--" + audiAccName);

			if ("".equals(firstDatetime) && !"".equals(lastDatetime)) {
				firstDatetime = lastDatetime;
			}
			if (!"".equals(firstDatetime) && "".equals(lastDatetime)) {
				lastDatetime = firstDatetime;
			}

			if (!"".equals(firstDatetime) && !"".equals(lastDatetime)) {
				formatter = new SimpleDateFormat("yyyy/MM/dd");
				firstDate = new Date(firstDatetime);
				lastDate = new Date(lastDatetime);
				nowDate = new Date();
				date = new Date(nowDate.getTime() - (long) 30 * 24 * 60 * 60 * 1000);

				if (firstDate.getTime() > lastDate.getTime()) {
					String strDate = lastDatetime;
					lastDatetime = firstDatetime;
					firstDatetime = strDate;
				}
				if (date.getTime() > firstDate.getTime() || firstDate.getTime() > nowDate.getTime()) {
					firstDatetime = formatter.format(date);
				} else {
					firstDatetime = formatter.format(firstDate);
				}
				if (date.getTime() > lastDate.getTime() || lastDate.getTime() > nowDate.getTime()) {
					lastDatetime = formatter.format(nowDate);
				} else {
					lastDatetime = formatter.format(lastDate);
				}

				LOG.debug("firstDate" + firstDatetime);
				LOG.debug("lastDate" + lastDatetime);
			}

			totalCount = service.getOrderRechargeCarryOutCountTotal(status, accName, audiAccName, firstDatetime, lastDatetime);
			LOG.debug(totalCount);
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
			LOG.debug(totalPage);
			tmpMap.put("orderMaxPage", totalPage);
			tmpMap.put("page", nextPage);
			tmpMap.put("RechargeCarryOut",
					service.searchOrderRechargeCarryOutData(status, accName, audiAccName, firstDatetime, lastDatetime, aes, sort, firstCount, count));
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));

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

			userId = 0;
			userIp = null;

			accName = null;
			audiAccName = null;
			if (firstDate != null) {
				firstDate.clone();
				firstDate = null;
			}

			if (lastDate != null) {
				lastDate.clone();
				lastDate = null;
			}

			if (nowDate != null) {
				nowDate.clone();
				nowDate = null;
			}

			if (date != null) {
				date.clone();
				date = null;
			}

			status = 0;
			aes = 0;
			sort = 0;
			firstCount = 0;
			count = 0;
			page = 0;
			nextPage = 0;
			totalCount = 0;
			totalPage = 0;
		}
		return;
	}

	public void searchOrderRechargeUndoneData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("searchOrderRechargeUndoneData");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;

		String accName = null;
		String audiAccName = null;
		String firstDatetime = null;
		String lastDatetime = null;
		int status = 0;
		Date firstDate = null;
		Date lastDate = null;
		Date nowDate = null;
		int aes = 1;
		int sort = 0;
		int firstCount = 0;
		int count = 0;
		int page = 0;
		int nextPage = 0;
		int totalCount = 0;
		int totalPage = 0;
		SimpleDateFormat formatter = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);

			accName = checkAccount(req.getParameter("accName") != null ? req.getParameter("accName").toString() : "");
			audiAccName = checkAccount(req.getParameter("audiAccName") != null ? req.getParameter("audiAccName").toString() : "");
			firstDatetime = checkDateTimeFormat(req.getParameter("firstDatetime").toString());
			lastDatetime = checkDateTimeFormat(req.getParameter("lastDatetime").toString());
			status = req.getParameter("status") != null ? Integer.parseInt(req.getParameter("status").toString()) : 0;
			aes = req.getParameter("aes") != null ? Integer.parseInt(req.getParameter("aes").toString()) : 1;
			sort = req.getParameter("sort") != null ? Integer.parseInt(req.getParameter("sort").toString()) : 0;
			page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
			nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
			count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;

			LOG.debug("accName--" + accName + "\naudiAccName--" + audiAccName);

			if ("".equals(firstDatetime) && !"".equals(lastDatetime)) {
				firstDatetime = lastDatetime;
			}
			if (!"".equals(firstDatetime) && "".equals(lastDatetime)) {
				lastDatetime = firstDatetime;
			}

			if (!"".equals(firstDatetime) && !"".equals(lastDatetime)) {
				formatter = new SimpleDateFormat("yyyy/MM/dd");
				firstDate = new Date(firstDatetime);
				lastDate = new Date(lastDatetime);
				nowDate = new Date();

				if (firstDate.getTime() > lastDate.getTime()) {
					String strDate = lastDatetime;
					lastDatetime = firstDatetime;
					firstDatetime = strDate;
				}
				if (firstDate.getTime() > nowDate.getTime()) {
					firstDatetime = formatter.format(nowDate);
				} else {
					firstDatetime = formatter.format(firstDate);
				}
				if (lastDate.getTime() > nowDate.getTime()) {
					lastDatetime = formatter.format(nowDate);
				} else {
					lastDatetime = formatter.format(lastDate);
				}

				LOG.debug("firstDate" + firstDatetime);
				LOG.debug("lastDate" + lastDatetime);
			}

			totalCount = service.getOrderRechargeUndoneCountTotal(status, accName, audiAccName, firstDatetime, lastDatetime);
			LOG.debug(totalCount);
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
			LOG.debug(totalPage);
			tmpMap.put("page", nextPage);
			tmpMap.put("orderMaxPage", totalPage);
			tmpMap.put("RechargeUndone",
					service.searchOrderRechargeUndoneData(status, accName, audiAccName, firstDatetime, lastDatetime, aes, sort, firstCount, count));
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));

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

			userId = 0;
			userIp = null;

			accName = null;
			audiAccName = null;
			status = 0;
		}
		return;
	}

	public void searchOrderWithdrawalCarryOutData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("searchOrderWithdrawalCarryOutData");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;

		String accName = null;
		String audiAccName = null;
		String firstDatetime = null;
		String lastDatetime = null;
		Date firstDate = null;
		Date lastDate = null;
		Date nowDate = null;
		Date date = null;
		SimpleDateFormat formatter = null;
		int status = 0;
		int aes = 1;
		int sort = 0;
		int firstCount = 0;
		int count = 0;
		int page = 0;
		int nextPage = 0;
		int totalCount = 0;
		int totalPage = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);

			accName = checkAccount(req.getParameter("accName") != null ? req.getParameter("accName").toString() : "");
			audiAccName = checkAccount(req.getParameter("audiAccName") != null ? req.getParameter("audiAccName").toString() : "");
			firstDatetime = checkDateTimeFormat(req.getParameter("firstDatetime").toString());
			lastDatetime = checkDateTimeFormat(req.getParameter("lastDatetime").toString());
			status = req.getParameter("status") != null ? Integer.parseInt(req.getParameter("status").toString()) : 0;
			aes = req.getParameter("aes") != null ? Integer.parseInt(req.getParameter("aes").toString()) : 1;
			sort = req.getParameter("sort") != null ? Integer.parseInt(req.getParameter("sort").toString()) : 0;
			page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
			nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
			count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;

			LOG.debug("accName--" + accName + "\naudiAccName--" + audiAccName);

			if ("".equals(firstDatetime) && !"".equals(lastDatetime)) {
				firstDatetime = lastDatetime;
			}
			if (!"".equals(firstDatetime) && "".equals(lastDatetime)) {
				lastDatetime = firstDatetime;
			}

			if (!"".equals(firstDatetime) && !"".equals(lastDatetime)) {
				formatter = new SimpleDateFormat("yyyy/MM/dd");
				firstDate = new Date(firstDatetime);
				lastDate = new Date(lastDatetime);
				nowDate = new Date();
				date = new Date(nowDate.getTime() - (long) 30 * 24 * 60 * 60 * 1000);

				if (firstDate.getTime() > lastDate.getTime()) {
					String strDate = lastDatetime;
					lastDatetime = firstDatetime;
					firstDatetime = strDate;
				}

				if (date.getTime() > firstDate.getTime() || firstDate.getTime() > nowDate.getTime()) {
					firstDatetime = formatter.format(date);
				} else {
					firstDatetime = formatter.format(firstDate);
				}
				if (date.getTime() > lastDate.getTime() || lastDate.getTime() > nowDate.getTime()) {
					lastDatetime = formatter.format(nowDate);
				} else {
					lastDatetime = formatter.format(lastDate);
				}

				LOG.debug("firstDate" + firstDatetime);
				LOG.debug("lastDate" + lastDatetime);

			}

			totalCount = service.getOrderWithdrawalCarryOutCountTotal(status, accName, audiAccName, firstDatetime, lastDatetime);
			LOG.debug(totalCount);
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
			LOG.debug(totalPage);
			tmpMap.put("orderMaxPage", totalPage);
			tmpMap.put("page", nextPage);
			tmpMap.put("WithdrawalCarryOut", service.searchOrderWithdrawalCarryOutData(status, accName, audiAccName, firstDatetime, lastDatetime, aes,
					sort, firstCount, count));
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));

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

			userId = 0;
			userIp = null;

			accName = null;
			audiAccName = null;

			status = 0;
		}
		return;
	}

	public void searchOrderWithdrawalUndoneData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("searchOrderWithdrawalUndoneData");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;

		String accName = null;
		String audiAccName = null;
		String firstDatetime = null;
		String lastDatetime = null;
		Date firstDate = null;
		Date lastDate = null;
		Date nowDate = null;
		int status = 0;
		int aes = 1;
		int sort = 0;
		int firstCount = 0;
		int count = 0;
		int page = 0;
		int nextPage = 0;
		int totalCount = 0;
		int totalPage = 0;
		SimpleDateFormat formatter = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);

			accName = checkAccount(req.getParameter("accName") != null ? req.getParameter("accName").toString() : "");
			audiAccName = checkAccount(req.getParameter("audiAccName") != null ? req.getParameter("audiAccName").toString() : "");
			firstDatetime = checkDateTimeFormat(req.getParameter("firstDatetime").toString());
			lastDatetime = checkDateTimeFormat(req.getParameter("lastDatetime").toString());
			status = req.getParameter("status") != null ? Integer.parseInt(req.getParameter("status").toString()) : 0;
			aes = req.getParameter("aes") != null ? Integer.parseInt(req.getParameter("aes").toString()) : 1;
			sort = req.getParameter("sort") != null ? Integer.parseInt(req.getParameter("sort").toString()) : 0;
			page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
			nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
			count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;

			LOG.debug("accName--" + accName + "\naudiAccName--" + audiAccName);

			if ("".equals(firstDatetime) && !"".equals(lastDatetime)) {
				firstDatetime = lastDatetime;
			}
			if (!"".equals(firstDatetime) && "".equals(lastDatetime)) {
				lastDatetime = firstDatetime;
			}

			if (!"".equals(firstDatetime) && !"".equals(lastDatetime)) {
				formatter = new SimpleDateFormat("yyyy/MM/dd");
				firstDate = new Date(firstDatetime);
				lastDate = new Date(lastDatetime);
				nowDate = new Date();

				if (firstDate.getTime() > lastDate.getTime()) {
					String strDate = lastDatetime;
					lastDatetime = firstDatetime;
					firstDatetime = strDate;
				}
				if (firstDate.getTime() > nowDate.getTime()) {
					firstDatetime = formatter.format(nowDate);
				} else {
					firstDatetime = formatter.format(firstDate);
				}
				if (lastDate.getTime() > nowDate.getTime()) {
					lastDatetime = formatter.format(nowDate);
				} else {
					lastDatetime = formatter.format(lastDate);
				}

				LOG.debug("firstDate" + firstDatetime);
				LOG.debug("lastDate" + lastDatetime);
			}

			totalCount = service.getOrderWithdrawalUndoneCountTotal(status, accName, audiAccName, firstDatetime, lastDatetime);
			LOG.debug(totalCount);

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
			LOG.debug(totalPage);
			tmpMap.put("page", nextPage);
			tmpMap.put("orderMaxPage", totalPage);
			tmpMap.put("WithdrawalUndone",
					service.searchOrderWithdrawalUndoneData(status, accName, audiAccName, firstDatetime, lastDatetime, aes, sort, firstCount, count));
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));

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

			userId = 0;
			userIp = null;

			accName = null;
			audiAccName = null;

			status = 0;
		}
		return;
	}

	public void getOrderReviewData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;
		int aes = 1;
		int sort = 0;
		int totalCount = 0;
		int totalPage = 0;
		int count = 0;
		int nextPage = 0;
		int page = 0;
		int firstCount = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			aes = req.getParameter("aes") != null ? Integer.parseInt(req.getParameter("aes").toString()) : 1;
			sort = req.getParameter("sort") != null ? Integer.parseInt(req.getParameter("sort").toString()) : 0;
			page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
			nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
			count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);

			totalCount = service.getOrderReviewCountTotal();
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
			LOG.debug(totalPage);

			tmpMap.put("orderMaxPage", totalPage);
			tmpMap.put("page", nextPage);
			tmpMap.put("Review", service.getOrderReviewData(aes, sort, firstCount, count));
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));
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

			userId = 0;
			userIp = null;
		}
		return;
	}

	public void getOrderWithdrawalUndoneData(HttpServletRequest req, HttpServletResponse res) throws IOException {

		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;

		int totalCount = 0;
		int totalPage = 0;
		int count = 0;
		int nextPage = 0;
		int page = 0;
		int firstCount = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
			nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
			count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);

			totalCount = service.getOrderWithdrawalUndoneCountTotal(0, "", "", "", "");
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
			LOG.debug(totalPage);

			tmpMap.put("orderMaxPage", totalPage);
			tmpMap.put("page", nextPage);
			tmpMap.put("WithdrawalUndone", service.getOrderWithdrawalUndoneData(firstCount, count));
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));
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

			userId = 0;
			userIp = null;
		}
		return;
	}

	public void getOrderWithdrawalCarryOutData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;

		int totalCount = 0;
		int totalPage = 0;
		int count = 0;
		int nextPage = 0;
		int page = 0;
		int firstCount = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
			nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
			count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);

			totalCount = service.getOrderWithdrawalCarryOutCountTotal(0, "", "", "", "");

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
			LOG.debug(totalPage);

			tmpMap.put("orderMaxPage", totalPage);
			tmpMap.put("page", nextPage);
			tmpMap.put("WithdrawalCarryOut", service.getOrderWithdrawalCarryOutData(firstCount, count));
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));
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

			userId = 0;
			userIp = null;
		}
		return;
	}

	public void getOrderRechargeUndoneData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;

		int totalCount = 0;
		int totalPage = 0;
		int count = 0;
		int nextPage = 0;
		int page = 0;
		int firstCount = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
			nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
			count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);

			totalCount = service.getOrderRechargeUndoneCountTotal(0, "", "", "", "");

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
			LOG.debug(totalPage);

			tmpMap.put("orderMaxPage", totalPage);
			tmpMap.put("page", nextPage);
			tmpMap.put("RechargeUndone", service.getOrderRechargeUndoneData(firstCount, count));
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));
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

			userId = 0;
			userIp = null;
		}
		return;
	}

	public void getOrderRechargeCarryOutData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;

		int totalCount = 0;
		int totalPage = 0;
		int count = 0;
		int nextPage = 0;
		int page = 0;
		int firstCount = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
			nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
			count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);

			totalCount = service.getOrderRechargeCarryOutCountTotal(0, "", "", "", "");

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
			LOG.debug(totalPage);

			tmpMap.put("orderMaxPage", totalPage);
			tmpMap.put("page", nextPage);
			tmpMap.put("RechargeCarryOut", service.getOrderRechargeCarryOutData(firstCount, count));
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));
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

			userId = 0;
			userIp = null;
		}
		return;
	}

	public void checkOrderReview(HttpServletRequest req, HttpServletResponse res) throws IOException {

		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;
		long orderId = 0;
		int orderType = 0;
		boolean auditPass = false;
		Map<String, Object> getOrderInfo = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);
			orderId = accIdToLong("" + req.getParameter("orderId"));
			orderType = Integer.parseInt("" + req.getParameter("orderType"));

			if (orderId > 0 && userId > 0) {
				getOrderInfo = service.checkOrderReview(orderId);
				auditPass = service.getOrderAudit(Long.parseLong("" + getOrderInfo.get("accId")), orderId, orderType);
				if (auditPass) {
					getOrderInfo = service.checkOrderReview(orderId);
				}
				tmpMap.put("reviewData", getOrderInfo);
			}
			if (auditPass) {
				tmpMap.put("message", "success");
			} else {
				tmpMap.put("message", "fail");
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

			userId = 0;
			userIp = null;
			orderId = 0;
		}
		return;
	}

	public void getAuditRechargeOrderData(HttpServletRequest req, HttpServletResponse res) throws IOException {

		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;
		long orderId = 0;
		int orderType = 0;
		int status = 0;
		boolean auditPass = false;
		Map<String, Object> getOrderInfo = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);
			orderId = accIdToLong("" + req.getParameter("orderId"));
			orderType = Integer.parseInt("" + req.getParameter("orderType"));
			status = Integer.parseInt("" + req.getParameter("status"));

			if (orderId > 0 && userId > 0 && status > 0) {
				getOrderInfo = service.getAuditRechargeOrderData(orderId, orderType);
				auditPass = service.getOrderAudit(Long.parseLong("" + getOrderInfo.get("accId")), orderId, orderType);
				if (auditPass) {
					getOrderInfo = service.getAuditRechargeOrderData(orderId, orderType);
				}
				tmpMap.put("data", getOrderInfo);
			}

			if (auditPass) {
				tmpMap.put("message", "success");
			} else {
				tmpMap.put("message", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("message", "fail");
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
		return;
	}

	public void getAuditWithdrawalOrderData(HttpServletRequest req, HttpServletResponse res) throws IOException {

		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;
		int status = 0;
		long orderId = 0;
		int orderType = 0;
		boolean auditPass = false;
		Map<String, Object> getOrderInfo = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);
			status = Integer.parseInt("" + req.getParameter("status"));
			orderId = accIdToLong("" + req.getParameter("orderId"));
			orderType = Integer.parseInt("" + req.getParameter("orderType"));

			if (orderId > 0 && userId > 0 && status > 0) {
				getOrderInfo = service.getAuditWithdrawalOrderData(orderId, orderType);
				auditPass = service.getOrderAudit(Long.parseLong("" + getOrderInfo.get("accId")), orderId, orderType);
				if (auditPass) {
					getOrderInfo = service.getAuditWithdrawalOrderData(orderId, orderType);
				}
				tmpMap.put("data", getOrderInfo);
			}
			if (auditPass) {
				tmpMap.put("message", "success");
			} else {
				tmpMap.put("message", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("message", "fail");
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

			userId = 0;
			userIp = null;
			status = 0;
			orderId = 0;
			orderType = 0;
			auditPass = false;
		}
		return;
	}

	public void checkRechargeCarryOutOrderData(HttpServletRequest req, HttpServletResponse res) throws IOException {

		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;
		long orderId = 0;
		int orderType = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);
			orderId = accIdToLong("" + req.getParameter("orderId"));
			orderType = Integer.parseInt("" + req.getParameter("orderType"));

			tmpMap.put("data", service.checkRechargeCarryOutOrder(orderId, orderType));
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("message", "fail");
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

			userId = 0;
			userIp = null;
			orderId = 0;
			orderType = 0;
		}
		return;
	}

	public void checkWithdrawalCarryOutOrderData(HttpServletRequest req, HttpServletResponse res) throws IOException {

		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;
		long orderId = 0;
		int orderType = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);
			orderId = accIdToLong("" + req.getParameter("orderId"));
			orderType = Integer.parseInt("" + req.getParameter("orderType"));

			tmpMap.put("data", service.checkWithdrawalCarryOutOrder(orderId, orderType));
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("message", "fail");
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

			userId = 0;
			userIp = null;
			orderId = 0;
			orderType = 0;
		}
		return;
	}

	public void audit(HttpServletRequest req, HttpServletResponse res) throws IOException {

		long memberAccId = 0;
		BigDecimal orderAmount = null;
		long userId = 0;
		String userIp = null;
		int status = 0;
		long orderId = 0;
		int orderType = 0;
		boolean allow = false;
		Map<String, Object> tmpMap = null;
		String bankSid = null;
		BigDecimal bankCheckAmount = null;
		String bankDepositTime = null;
		String remark = null;
		OrderServiceImpl service = null;
		OrderDaoImpl dao = null;
		MoneyServiceImpl service2 = null;
		MoneyDaoImpl dao2 = null;
		boolean auditPass = false;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			memberAccId = accIdToLong("" + req.getParameter("memberAccId"));
			orderAmount = new BigDecimal("" + req.getParameter("orderAmount"));
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			status = Integer.parseInt("" + req.getParameter("status"));
			orderId = accIdToLong("" + req.getParameter("orderId"));
			orderType = Integer.parseInt("" + req.getParameter("orderType"));
			allow = Boolean.parseBoolean("" + req.getParameter("success"));
			bankSid = "" + req.getParameter("bankSid");
			bankCheckAmount = new BigDecimal("" + req.getParameter("bankCheckAmount"));
			bankDepositTime = checkDateTimeFormat("" + req.getParameter("bankDepositTime"));
			remark = "" + req.getParameter("remark");
			service = new OrderServiceImpl(userId, userIp);
			dao = new OrderDaoImpl();
			service.setDao(dao);
			service2 = new MoneyServiceImpl(userId, userIp);
			dao2 = new MoneyDaoImpl();
			service2.setDao(dao2);

			if (orderId > 0 && status > 1 && ((orderType == ORDER_WITHDRAWAL && bankCheckAmount.compareTo(new BigDecimal(MAX_AMOUNT)) < 1)
					|| orderType == ORDER_RECHARGE)) {
				auditPass = service.audit(memberAccId, status, orderId, orderType, allow, remark, bankSid, bankCheckAmount, bankDepositTime);
			}
			if (auditPass && (orderType == ORDER_RECHARGE && status == ORDER_STATUS_4_2ND_AUDITING && allow)) {
				service2.memOrderDeposit(memberAccId, orderAmount, LOG_ACTION_RECHARGE_ORDER_AUDIT_SUCCESS_ADD);
			} else if (auditPass && (orderType == ORDER_WITHDRAWAL && !allow)) {
				service2.memOrderDeposit(memberAccId, orderAmount, LOG_ACTION_WITHDRAWAL_ORDER_AUDIT_FAIL_ADD);// 提款失敗加回
			}

			if (auditPass) {
				tmpMap.put("message", "success");
			} else {
				tmpMap.put("message", "fail");
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
			if (dao2 != null) {
				dao2.close();
				dao2 = null;
			}
			if (service2 != null) {
				service2.close();
				service2 = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
			memberAccId = 0;
			orderAmount = null;
			userId = 0;
			userIp = null;
			status = 0;
			orderId = 0;
			orderType = 0;
			allow = false;
			auditPass = false;
			bankSid = null;
			bankCheckAmount = null;
			bankDepositTime = null;
			remark = null;
		}
		return;
	}
}
