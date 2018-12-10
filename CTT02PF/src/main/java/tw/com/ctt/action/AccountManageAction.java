package tw.com.ctt.action;

//Import required java libraries
import java.io.IOException;
import static tw.com.ctt.constant.AccountManagerConstant.*;
import static tw.com.ctt.constant.LogToDBConstant.*;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.AccountManageDaoImpl;
import tw.com.ctt.dao.impl.MoneyDaoImpl;
import tw.com.ctt.model.ManagerAcciunyInfos;
import tw.com.ctt.service.impl.AccountManageServiceImpl;
import tw.com.ctt.service.impl.MoneyServiceImpl;
import tw.com.ctt.util.AESUtil;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "AccountManage", urlPatterns = { "/AccountManage" })
// Extend HttpServlet class & Implements Servlet
public class AccountManageAction extends BaseAction {

	private static final long serialVersionUID = 1135893114919005938L;
	private static final Logger LOG = LogManager.getLogger(AccountManageAction.class.getName());

	private static final int BANK_DATA_FIRST = 1;
	private static final int BANK_DATA_SECOND = 2;

	private String[] loginURL = {};
	private String[] loginCheckURL = { "/AccountManage!checkAccName", "/AccountManage!getLowerLevelAccTotal" };
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/AccountManage!ajax", "/AccountManage!showAddAccount", "/AccountManage!add", "/AccountManage!getSeetingAccData",
			"/AccountManage!seetingAccData", "/AccountManage!updateMemberWithdrawPwd", "/AccountManage!updateMemberPwd",
			"/AccountManage!updateAccStatus", "/AccountManage!getRandomPassword", "/AccountManage!showLog", "/AccountManage!getAccDetails",
			"/AccountManage!updateBankData", "/AccountManage!addGameServer", "/AccountManage!disableBankCard", "/AccountManage!getGameServerData",
			"/AccountManage!setGameServerData", "/AccountManage!showOrderLog", "/AccountManage!showGameSetLog" };
	private String[] authURL = { "/AccountManage!ajax.php" };

	public AccountManageAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("AccountManageAction start");
	}

	public boolean checkLogin() {
		return true;
	}

	// public void doGet(HttpServletRequest request, HttpServletResponse response)
	// throws ServletException, IOException {
	// // this.REQ = request;
	// // this.RES = response;
	// req.setAttribute(FORM_METHOD, "get");
	// LOG.debug("\n" + req.getRequestURI() + "\t#doGet");
	// req.getRequestDispatcher("404.html").forward(request, response);
	// return;
	// }

	public void ajax(HttpServletRequest req, HttpServletResponse res) throws IOException {

		int totalCount = 0;
		int totalPage = 0;
		int firstCount = 0;
		List<List<String>> tmpListList = null;
		List<String> tmpList = null;
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;
		// printRequest(REQ);

		String init = escapeFormInput(req.getParameter("init"));
		int sort = req.getParameter("sort") == null ? 0 : Integer.parseInt(req.getParameter("sort"));
		int asc = req.getParameter("asc") == null ? 0 : Integer.parseInt(req.getParameter("asc"));
		int status = req.getParameter("status") == null ? 1 : Integer.parseInt(req.getParameter("status"));
		int type = req.getParameter("type") == null ? 0 : Integer.parseInt(req.getParameter("type"));
		long userId = accIdToLong("" + req.getSession().getAttribute("accId"));
		String userIp = getIpAddr(req);
		long accId = req.getParameter("accId") == null ? 0 : accIdToLong(req.getParameter("accId"));
		long upAccId = req.getParameter("upAccId") == null ? 0 : accIdToLong(req.getParameter("upAccId"));
		int accLevel = req.getParameter("accLevel") == null ? 0 : Integer.parseInt(req.getParameter("accLevel"));
		String accName = checkAccount(req.getParameter("accName") == null ? "" : req.getParameter("accName").toString());
		int memberType = req.getParameter("memberType") == null ? 1 : Integer.parseInt(req.getParameter("memberType"));
		int count = req.getParameter("count") == null ? 25 : Integer.parseInt(req.getParameter("count"));
		int page = req.getParameter("page") == null ? 0 : Integer.parseInt(req.getParameter("page"));
		int nextPage = req.getParameter("nextPage") == null ? 0 : Integer.parseInt(req.getParameter("nextPage"));
		boolean checkStatus = req.getParameter("checkStatus") == null ? false : Boolean.parseBoolean(req.getParameter("checkStatus"));

		if (!checkStringEmpty(init)) {
			tmpMap = new ConcurrentHashMap<String, Object>();
			tmpListList = new ArrayList<List<String>>();
			tmpList = new ArrayList<String>();
			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);
			List list = null;
			if (type > 0) {
				if (type == ACC_LEVEL_COM) {

					LOG.debug("\n--service.searchCOM(" + userId + ", " + status + ", " + sort + ", " + asc + ")");

					list = service.searchCOM(userId, accId, status, sort, asc);

					tmpList.add("<a onclick=\"sort(2," + type + ");\">帳號<i class=\"icon-arrow-down\" id = \"iconArrow2\"></i></a>");
					tmpList.add("<a onclick=\"sort(1," + type + ");\">名稱<i class=\"icon-arrow-down\" id = \"iconArrow1\"></i></a>");
					tmpList.add("<a onclick=\"sort(3," + type + ");\">新增日期<i class=\"icon-arrow-down\" id = \"iconArrow3\"></i></a>");
					tmpList.add("權限");
					tmpList.add("<a onclick=\"sort(4," + type + ");\">IP<i class=\"icon-arrow-down\" id = \"iconArrow4\"></i></a>");
					tmpList.add("功能");
				} else if (type >= ACC_LEVEL_SC && type <= ACC_LEVEL_AG10) {

					LOG.debug("\n--service.searchMAG(" + type + ", " + userId + ", " + upAccId + ", " + accId + "," + status + ", " + sort + ", "
							+ asc + "," + checkStatus + ")");

					list = service.searchMAG(type, userId, upAccId, accId, status, sort, asc, checkStatus);

					if (type > ACC_LEVEL_SC) {
						tmpList.add("上線結構");
					}
					tmpList.add("在線");
					tmpList.add("<a onclick=\"sort(1," + type + ");\">名稱<i class=\"icon-arrow-down\" id = \"iconArrow1\"></i></a>");
					tmpList.add("<a onclick=\"sort(2," + type + ");\">帳號<i class=\"icon-arrow-down\" id = \"iconArrow2\"></i></a>");

					switch (type) {
					case ACC_LEVEL_SC:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_BC]);
					case ACC_LEVEL_BC:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_CO]);
					case ACC_LEVEL_CO:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_SA]);
					case ACC_LEVEL_SA:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_AG]);
					case ACC_LEVEL_AG:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_AG1]);
					case ACC_LEVEL_AG1:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_AG2]);
					case ACC_LEVEL_AG2:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_AG3]);
					case ACC_LEVEL_AG3:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_AG4]);
					case ACC_LEVEL_AG4:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_AG5]);
					case ACC_LEVEL_AG5:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_AG5]);
					case ACC_LEVEL_AG6:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_AG7]);
					case ACC_LEVEL_AG7:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_AG8]);
					case ACC_LEVEL_AG8:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_AG9]);
					case ACC_LEVEL_AG9:
						tmpList.add(ACC_LEVEL_NICKNAME_ARR[ACC_LEVEL_AG10]);
					case ACC_LEVEL_AG10:
						tmpList.add(ACC_LEVEL_NICKNAME_MEM);
					}
					tmpList.add("成數");
					tmpList.add("功能");
					tmpList.add("<a onclick=\"sort(3," + type + ");\">新增日期<i class=\"icon-arrow-down\" id = \"iconArrow3\"></i></a>");
					tmpList.add("<a onclick=\"sort(5," + type + ");\">最後登入時間<i class=\"icon-arrow-down\" id = \"iconArrow5\"></i></a>");
					tmpList.add("<a onclick=\"sort(4," + type + ");\">IP<i class=\"icon-arrow-down\" id = \"iconArrow4\"></i></a>");

				} else if (type == ACC_LEVEL_MEM) {
					LOG.debug("\n--service.findMEM(" + userId + ", " + accId + ", " + status + ", " + sort + ", " + asc + ")");

					totalCount = service.searchTotalCountMEM(accLevel, userId, upAccId, accName, memberType, status, checkStatus);

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

					list = service.searchMEM(accLevel, userId, upAccId, accName, memberType, status, sort, asc, checkStatus, firstCount, count);

					tmpList.add("上線結構");
					tmpList.add("在線");
					tmpList.add("會員狀態");
					tmpList.add("<a onclick=\"sort(1," + type + ");\">名稱<i class=\"icon-arrow-down\" id = \"iconArrow1\"></i></a>");
					tmpList.add("<a onclick=\"sort(2," + type + ");\">帳號<i class=\"icon-arrow-down\" id = \"iconArrow2\"></i></a>");
					tmpList.add("成數");
					tmpList.add("功能");
					tmpList.add("<a onclick=\"sort(3," + type + ");\">新增日期<i class=\"icon-arrow-down\" id = \"iconArrow3\" ></i></a>");
					tmpList.add("<a onclick=\"sort(5," + type + ");\">最後登入時間<i class=\"icon-arrow-down\" id = \"iconArrow5\" ></i></a>");
					tmpList.add("<a onclick=\"sort(4," + type + ");\">IP<i class=\"icon-arrow-down\" id = \"iconArrow4\"></i></a>");
				}

				if (tmpList != null && tmpList.size() > 0) {
					if (type == ACC_LEVEL_MEM) {
						tmpMap.put("th", tmpList);
						tmpMap.put("nowPage", nextPage);
						tmpMap.put("maxPage", totalPage);
					} else {
						tmpMap.put("th", tmpList);
					}

				}
			}

			int lev = service.findLevel(userId);
			Map<String, String> tmpMap2 = (Map<String, String>) service.findTypeTotal(userId, status);
			List<String> tmpList1 = new ArrayList<String>();
			List<String> tmpList2 = new ArrayList<String>();
			List<String> tmpList3 = new ArrayList<String>();

			if (lev < ACC_LEVEL_COM) {
				tmpList1.add(ACC_LEVEL_COM_NICKNAME);
				if (tmpMap2.containsKey("com")) {
					tmpList2.add(tmpMap2.get("com"));
				} else {
					tmpList2.add("0");
				}

				tmpList3.add("" + ACC_LEVEL_COM);
			}
			if (lev < ACC_LEVEL_SC) {
				tmpList1.add(ACC_LEVEL_SC_NICKNAME);
				if (tmpMap2.containsKey("sc")) {
					tmpList2.add(tmpMap2.get("sc"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_SC);
			}
			if (lev < ACC_LEVEL_BC) {
				tmpList1.add(ACC_LEVEL_BC_NICKNAME);
				if (tmpMap2.containsKey("bc")) {
					tmpList2.add(tmpMap2.get("bc"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_BC);
			}
			if (lev < ACC_LEVEL_CO) {
				tmpList1.add(ACC_LEVEL_CO_NICKNAME);
				if (tmpMap2.containsKey("co")) {
					tmpList2.add(tmpMap2.get("co"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_CO);
			}
			if (lev < ACC_LEVEL_SA) {
				tmpList1.add(ACC_LEVEL_SA_NICKNAME);
				if (tmpMap2.containsKey("sa")) {
					tmpList2.add(tmpMap2.get("sa"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_SA);
			}
			if (lev < ACC_LEVEL_AG) {
				tmpList1.add(ACC_LEVEL_AG_NICKNAME);
				if (tmpMap2.containsKey("ag")) {
					tmpList2.add(tmpMap2.get("ag"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_AG);
			}
			if (lev < ACC_LEVEL_AG1) {
				tmpList1.add(ACC_LEVEL_AG1_NICKNAME);
				if (tmpMap2.containsKey("ag1")) {
					tmpList2.add(tmpMap2.get("ag1"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_AG1);
			}
			if (lev < ACC_LEVEL_AG2) {
				tmpList1.add(ACC_LEVEL_AG2_NICKNAME);
				if (tmpMap2.containsKey("ag2")) {
					tmpList2.add(tmpMap2.get("ag2"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_AG2);
			}
			if (lev < ACC_LEVEL_AG3) {
				tmpList1.add(ACC_LEVEL_AG3_NICKNAME);
				if (tmpMap2.containsKey("ag3")) {
					tmpList2.add(tmpMap2.get("ag3"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_AG3);
			}
			if (lev < ACC_LEVEL_AG4) {
				tmpList1.add(ACC_LEVEL_AG4_NICKNAME);
				if (tmpMap2.containsKey("ag4")) {
					tmpList2.add(tmpMap2.get("ag4"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_AG4);
			}
			if (lev < ACC_LEVEL_AG5) {
				tmpList1.add(ACC_LEVEL_AG5_NICKNAME);
				if (tmpMap2.containsKey("ag5")) {
					tmpList2.add(tmpMap2.get("ag5"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_AG5);
			}
			if (lev < ACC_LEVEL_AG6) {
				tmpList1.add(ACC_LEVEL_AG6_NICKNAME);
				if (tmpMap2.containsKey("ag6")) {
					tmpList2.add(tmpMap2.get("ag6"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_AG6);
			}
			if (lev < ACC_LEVEL_AG7) {
				tmpList1.add(ACC_LEVEL_AG7_NICKNAME);
				if (tmpMap2.containsKey("ag7")) {
					tmpList2.add(tmpMap2.get("ag7"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_AG7);
			}
			if (lev < ACC_LEVEL_AG8) {
				tmpList1.add(ACC_LEVEL_AG8_NICKNAME);
				if (tmpMap2.containsKey("ag8")) {
					tmpList2.add(tmpMap2.get("ag8"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_AG8);
			}
			if (lev < ACC_LEVEL_AG9) {
				tmpList1.add(ACC_LEVEL_AG9_NICKNAME);
				if (tmpMap2.containsKey("ag9")) {
					tmpList2.add(tmpMap2.get("ag9"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_AG9);
			}
			if (lev < ACC_LEVEL_AG10) {
				tmpList1.add(ACC_LEVEL_AG10_NICKNAME);
				if (tmpMap2.containsKey("ag10")) {
					tmpList2.add(tmpMap2.get("ag10"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_AG10);
			}
			if (lev < ACC_LEVEL_MEM) {
				tmpList1.add(ACC_LEVEL_MEM_NICKNAME);
				if (tmpMap2.containsKey("mem")) {
					tmpList2.add(tmpMap2.get("mem"));
				} else {
					tmpList2.add("0");
				}
				tmpList3.add("" + ACC_LEVEL_MEM);
			}

			tmpMap.put("typeTitle", tmpList1);
			tmpMap.put("typeRows", tmpList2);
			tmpMap.put("typeLevel", tmpList3);
			if (tmpList1.size() == 0 || tmpList2.size() == 0 || tmpList3.size() == 0) {
			}

			Map<String, Object> upMap = new ConcurrentHashMap<String, Object>();
			Map<String, Object> ratioMap = new ConcurrentHashMap<String, Object>();

			if (!this.checkListEmpty(list)) {
				for (Object obj : list) {

					ManagerAcciunyInfos tmpMap1 = (ManagerAcciunyInfos) obj;
					tmpList = new ArrayList<String>();

					Map<String, String> upMapInfo = new ConcurrentHashMap<String, String>();
					Map<String, String> ratioMapInfo = new ConcurrentHashMap<String, String>();

					upMapInfo.put("scId", "" + tmpMap1.getScId());
					upMapInfo.put("scAccName", tmpMap1.getScAccName());
					upMapInfo.put("scNickname", tmpMap1.getScNickname());
					upMapInfo.put("scAccStatus", "" + tmpMap1.getScAccStatus());
					upMapInfo.put("bcId", "" + tmpMap1.getBcId());
					upMapInfo.put("bcAccName", tmpMap1.getBcAccName());
					upMapInfo.put("bcNickname", tmpMap1.getBcNickname());
					upMapInfo.put("bcAccStatus", "" + tmpMap1.getBcAccStatus());
					upMapInfo.put("coId", "" + tmpMap1.getCoId());
					upMapInfo.put("coAccName", tmpMap1.getCoAccName());
					upMapInfo.put("coNickname", tmpMap1.getCoNickname());
					upMapInfo.put("coAccStatus", "" + tmpMap1.getCoAccStatus());
					upMapInfo.put("saId", "" + tmpMap1.getSaId());
					upMapInfo.put("saAccName", tmpMap1.getSaAccName());
					upMapInfo.put("saNickname", tmpMap1.getSaNickname());
					upMapInfo.put("saAccStatus", "" + tmpMap1.getSaAccStatus());
					upMapInfo.put("agId", "" + tmpMap1.getAgId());
					upMapInfo.put("agAccName", tmpMap1.getAgAccName());
					upMapInfo.put("agNickname", tmpMap1.getAgNickname());
					upMapInfo.put("agAccStatus", "" + tmpMap1.getAgAccStatus());

					upMapInfo.put("ag1Id", "" + tmpMap1.getAg1Id());
					upMapInfo.put("ag1AccName", tmpMap1.getAg1AccName());
					upMapInfo.put("ag1Nickname", tmpMap1.getAg1Nickname());
					upMapInfo.put("ag1AccStatus", "" + tmpMap1.getAg1AccStatus());
					upMapInfo.put("ag2Id", "" + tmpMap1.getAg2Id());
					upMapInfo.put("ag2AccName", tmpMap1.getAg2AccName());
					upMapInfo.put("ag2Nickname", tmpMap1.getAg2Nickname());
					upMapInfo.put("ag2AccStatus", "" + tmpMap1.getAg2AccStatus());
					upMapInfo.put("ag3Id", "" + tmpMap1.getAg3Id());
					upMapInfo.put("ag3AccName", tmpMap1.getAg3AccName());
					upMapInfo.put("ag3Nickname", tmpMap1.getAg3Nickname());
					upMapInfo.put("ag3AccStatus", "" + tmpMap1.getAg3AccStatus());
					upMapInfo.put("ag4Id", "" + tmpMap1.getAg4Id());
					upMapInfo.put("ag4AccName", tmpMap1.getAg4AccName());
					upMapInfo.put("ag4Nickname", tmpMap1.getAg4Nickname());
					upMapInfo.put("ag4AccStatus", "" + tmpMap1.getAg4AccStatus());
					upMapInfo.put("ag5Id", "" + tmpMap1.getAg5Id());
					upMapInfo.put("ag5AccName", tmpMap1.getAg5AccName());
					upMapInfo.put("ag5Nickname", tmpMap1.getAg5Nickname());
					upMapInfo.put("ag5AccStatus", "" + tmpMap1.getAg5AccStatus());
					upMapInfo.put("ag6Id", "" + tmpMap1.getAg6Id());
					upMapInfo.put("ag6AccName", tmpMap1.getAg6AccName());
					upMapInfo.put("ag6Nickname", tmpMap1.getAg6Nickname());
					upMapInfo.put("ag6AccStatus", "" + tmpMap1.getAg6AccStatus());
					upMapInfo.put("ag7Id", "" + tmpMap1.getAg7Id());
					upMapInfo.put("ag7AccName", tmpMap1.getAg7AccName());
					upMapInfo.put("ag7Nickname", tmpMap1.getAg7Nickname());
					upMapInfo.put("ag7AccStatus", "" + tmpMap1.getAg7AccStatus());
					upMapInfo.put("ag8Id", "" + tmpMap1.getAg8Id());
					upMapInfo.put("ag8AccName", tmpMap1.getAg8AccName());
					upMapInfo.put("ag8Nickname", tmpMap1.getAg8Nickname());
					upMapInfo.put("ag8AccStatus", "" + tmpMap1.getAg8AccStatus());
					upMapInfo.put("ag9Id", "" + tmpMap1.getAg9Id());
					upMapInfo.put("ag9AccName", tmpMap1.getAg9AccName());
					upMapInfo.put("ag9Nickname", tmpMap1.getAg9Nickname());
					upMapInfo.put("ag9AccStatus", "" + tmpMap1.getAg9AccStatus());
					upMapInfo.put("ag10Id", "" + tmpMap1.getAg10Id());
					upMapInfo.put("ag10AccName", tmpMap1.getAg10AccName());
					upMapInfo.put("ag10Nickname", tmpMap1.getAg10Nickname());
					upMapInfo.put("ag10AccStatus", "" + tmpMap1.getAg10AccStatus());

					ratioMapInfo.put("g1Com", "" + tmpMap1.getG1Com());
					ratioMapInfo.put("g1Sc", "" + tmpMap1.getG1Sc());
					ratioMapInfo.put("g1Bc", "" + tmpMap1.getG1Bc());
					ratioMapInfo.put("g1Co", "" + tmpMap1.getG1Co());
					ratioMapInfo.put("g1Sa", "" + tmpMap1.getG1Sa());
					ratioMapInfo.put("g1Ag", "" + tmpMap1.getG1Ag());
					ratioMapInfo.put("g1Ag1", "" + tmpMap1.getG1Ag1());
					ratioMapInfo.put("g1Ag2", "" + tmpMap1.getG1Ag2());
					ratioMapInfo.put("g1Ag3", "" + tmpMap1.getG1Ag3());
					ratioMapInfo.put("g1Ag4", "" + tmpMap1.getG1Ag4());
					ratioMapInfo.put("g1Ag5", "" + tmpMap1.getG1Ag5());
					ratioMapInfo.put("g1Ag6", "" + tmpMap1.getG1Ag6());
					ratioMapInfo.put("g1Ag7", "" + tmpMap1.getG1Ag7());
					ratioMapInfo.put("g1Ag8", "" + tmpMap1.getG1Ag8());
					ratioMapInfo.put("g1Ag9", "" + tmpMap1.getG1Ag9());
					ratioMapInfo.put("g1Ag10", "" + tmpMap1.getG1Ag10());

					ratioMapInfo.put("g2Com", "" + tmpMap1.getG2Com());
					ratioMapInfo.put("g2Sc", "" + tmpMap1.getG2Sc());
					ratioMapInfo.put("g2Bc", "" + tmpMap1.getG2Bc());
					ratioMapInfo.put("g2Co", "" + tmpMap1.getG2Co());
					ratioMapInfo.put("g2Sa", "" + tmpMap1.getG2Sa());
					ratioMapInfo.put("g2Ag", "" + tmpMap1.getG2Ag());
					ratioMapInfo.put("g2Ag1", "" + tmpMap1.getG2Ag1());
					ratioMapInfo.put("g2Ag2", "" + tmpMap1.getG2Ag2());
					ratioMapInfo.put("g2Ag3", "" + tmpMap1.getG2Ag3());
					ratioMapInfo.put("g2Ag4", "" + tmpMap1.getG2Ag4());
					ratioMapInfo.put("g2Ag5", "" + tmpMap1.getG2Ag5());
					ratioMapInfo.put("g2Ag6", "" + tmpMap1.getG2Ag6());
					ratioMapInfo.put("g2Ag7", "" + tmpMap1.getG2Ag7());
					ratioMapInfo.put("g2Ag8", "" + tmpMap1.getG2Ag8());
					ratioMapInfo.put("g2Ag9", "" + tmpMap1.getG2Ag9());
					ratioMapInfo.put("g2Ag10", "" + tmpMap1.getG2Ag10());

					ratioMapInfo.put("g3Com", "" + tmpMap1.getG3Com());
					ratioMapInfo.put("g3Sc", "" + tmpMap1.getG3Sc());
					ratioMapInfo.put("g3Bc", "" + tmpMap1.getG3Bc());
					ratioMapInfo.put("g3Co", "" + tmpMap1.getG3Co());
					ratioMapInfo.put("g3Sa", "" + tmpMap1.getG3Sa());
					ratioMapInfo.put("g3Ag", "" + tmpMap1.getG3Ag());

					ratioMapInfo.put("g3Ag1", "" + tmpMap1.getG3Ag1());
					ratioMapInfo.put("g3Ag2", "" + tmpMap1.getG3Ag2());
					ratioMapInfo.put("g3Ag3", "" + tmpMap1.getG3Ag3());
					ratioMapInfo.put("g3Ag4", "" + tmpMap1.getG3Ag4());
					ratioMapInfo.put("g3Ag5", "" + tmpMap1.getG3Ag5());
					ratioMapInfo.put("g3Ag6", "" + tmpMap1.getG3Ag6());
					ratioMapInfo.put("g3Ag7", "" + tmpMap1.getG3Ag7());
					ratioMapInfo.put("g3Ag8", "" + tmpMap1.getG3Ag8());
					ratioMapInfo.put("g3Ag9", "" + tmpMap1.getG3Ag9());
					ratioMapInfo.put("g3Ag10", "" + tmpMap1.getG3Ag10());

					ratioMapInfo.put("g4Com", "" + tmpMap1.getG4Com());
					ratioMapInfo.put("g4Sc", "" + tmpMap1.getG4Sc());
					ratioMapInfo.put("g4Bc", "" + tmpMap1.getG4Bc());
					ratioMapInfo.put("g4Co", "" + tmpMap1.getG4Co());
					ratioMapInfo.put("g4Sa", "" + tmpMap1.getG4Sa());
					ratioMapInfo.put("g4Ag", "" + tmpMap1.getG4Ag());

					ratioMapInfo.put("g4Ag1", "" + tmpMap1.getG4Ag1());
					ratioMapInfo.put("g4Ag2", "" + tmpMap1.getG4Ag2());
					ratioMapInfo.put("g4Ag3", "" + tmpMap1.getG4Ag3());
					ratioMapInfo.put("g4Ag4", "" + tmpMap1.getG4Ag4());
					ratioMapInfo.put("g4Ag5", "" + tmpMap1.getG4Ag5());
					ratioMapInfo.put("g4Ag6", "" + tmpMap1.getG4Ag6());
					ratioMapInfo.put("g4Ag7", "" + tmpMap1.getG4Ag7());
					ratioMapInfo.put("g4Ag8", "" + tmpMap1.getG4Ag8());
					ratioMapInfo.put("g4Ag9", "" + tmpMap1.getG4Ag9());
					ratioMapInfo.put("g4Ag10", "" + tmpMap1.getG4Ag10());

					ratioMapInfo.put("g5Com", "" + tmpMap1.getG5Com());
					ratioMapInfo.put("g5Sc", "" + tmpMap1.getG5Sc());
					ratioMapInfo.put("g5Bc", "" + tmpMap1.getG5Bc());
					ratioMapInfo.put("g5Co", "" + tmpMap1.getG5Co());
					ratioMapInfo.put("g5Sa", "" + tmpMap1.getG5Sa());
					ratioMapInfo.put("g5Ag", "" + tmpMap1.getG5Ag());

					ratioMapInfo.put("g5Ag1", "" + tmpMap1.getG5Ag1());
					ratioMapInfo.put("g5Ag2", "" + tmpMap1.getG5Ag2());
					ratioMapInfo.put("g5Ag3", "" + tmpMap1.getG5Ag3());
					ratioMapInfo.put("g5Ag4", "" + tmpMap1.getG5Ag4());
					ratioMapInfo.put("g5Ag5", "" + tmpMap1.getG5Ag5());
					ratioMapInfo.put("g5Ag6", "" + tmpMap1.getG5Ag6());
					ratioMapInfo.put("g5Ag7", "" + tmpMap1.getG5Ag7());
					ratioMapInfo.put("g5Ag8", "" + tmpMap1.getG5Ag8());
					ratioMapInfo.put("g5Ag9", "" + tmpMap1.getG5Ag9());
					ratioMapInfo.put("g5Ag10", "" + tmpMap1.getG5Ag10());

					ratioMapInfo.put("nextG1Com", "" + tmpMap1.getNextG1Com());
					ratioMapInfo.put("nextG1Sc", "" + tmpMap1.getNextG1Sc());
					ratioMapInfo.put("nextG1Bc", "" + tmpMap1.getNextG1Bc());
					ratioMapInfo.put("nextG1Co", "" + tmpMap1.getNextG1Co());
					ratioMapInfo.put("nextG1Sa", "" + tmpMap1.getNextG1Sa());
					ratioMapInfo.put("nextG1Ag", "" + tmpMap1.getNextG1Ag());

					ratioMapInfo.put("nextG1Ag1", "" + tmpMap1.getNextG1Ag1());
					ratioMapInfo.put("nextG1Ag2", "" + tmpMap1.getNextG1Ag2());
					ratioMapInfo.put("nextG1Ag3", "" + tmpMap1.getNextG1Ag3());
					ratioMapInfo.put("nextG1Ag4", "" + tmpMap1.getNextG1Ag4());
					ratioMapInfo.put("nextG1Ag5", "" + tmpMap1.getNextG1Ag5());
					ratioMapInfo.put("nextG1Ag6", "" + tmpMap1.getNextG1Ag6());
					ratioMapInfo.put("nextG1Ag7", "" + tmpMap1.getNextG1Ag7());
					ratioMapInfo.put("nextG1Ag8", "" + tmpMap1.getNextG1Ag8());
					ratioMapInfo.put("nextG1Ag9", "" + tmpMap1.getNextG1Ag9());
					ratioMapInfo.put("nextG1Ag10", "" + tmpMap1.getNextG1Ag10());

					ratioMapInfo.put("nextG2Com", "" + tmpMap1.getNextG2Com());
					ratioMapInfo.put("nextG2Sc", "" + tmpMap1.getNextG2Sc());
					ratioMapInfo.put("nextG2Bc", "" + tmpMap1.getNextG2Bc());
					ratioMapInfo.put("nextG2Co", "" + tmpMap1.getNextG2Co());
					ratioMapInfo.put("nextG2Sa", "" + tmpMap1.getNextG2Sa());
					ratioMapInfo.put("nextG2Ag", "" + tmpMap1.getNextG2Ag());

					ratioMapInfo.put("nextG2Ag1", "" + tmpMap1.getNextG2Ag1());
					ratioMapInfo.put("nextG2Ag2", "" + tmpMap1.getNextG2Ag2());
					ratioMapInfo.put("nextG2Ag3", "" + tmpMap1.getNextG2Ag3());
					ratioMapInfo.put("nextG2Ag4", "" + tmpMap1.getNextG2Ag4());
					ratioMapInfo.put("nextG2Ag5", "" + tmpMap1.getNextG2Ag5());
					ratioMapInfo.put("nextG2Ag6", "" + tmpMap1.getNextG2Ag6());
					ratioMapInfo.put("nextG2Ag7", "" + tmpMap1.getNextG2Ag7());
					ratioMapInfo.put("nextG2Ag8", "" + tmpMap1.getNextG2Ag8());
					ratioMapInfo.put("nextG2Ag9", "" + tmpMap1.getNextG2Ag9());
					ratioMapInfo.put("nextG2Ag10", "" + tmpMap1.getNextG2Ag10());

					ratioMapInfo.put("nextG3Com", "" + tmpMap1.getNextG3Com());
					ratioMapInfo.put("nextG3Sc", "" + tmpMap1.getNextG3Sc());
					ratioMapInfo.put("nextG3Bc", "" + tmpMap1.getNextG3Bc());
					ratioMapInfo.put("nextG3Co", "" + tmpMap1.getNextG3Co());
					ratioMapInfo.put("nextG3Sa", "" + tmpMap1.getNextG3Sa());
					ratioMapInfo.put("nextG3Ag", "" + tmpMap1.getNextG3Ag());

					ratioMapInfo.put("nextG3Ag1", "" + tmpMap1.getNextG3Ag1());
					ratioMapInfo.put("nextG3Ag2", "" + tmpMap1.getNextG3Ag2());
					ratioMapInfo.put("nextG3Ag3", "" + tmpMap1.getNextG3Ag3());
					ratioMapInfo.put("nextG3Ag4", "" + tmpMap1.getNextG3Ag4());
					ratioMapInfo.put("nextG3Ag5", "" + tmpMap1.getNextG3Ag5());
					ratioMapInfo.put("nextG3Ag6", "" + tmpMap1.getNextG3Ag6());
					ratioMapInfo.put("nextG3Ag7", "" + tmpMap1.getNextG3Ag7());
					ratioMapInfo.put("nextG3Ag8", "" + tmpMap1.getNextG3Ag8());
					ratioMapInfo.put("nextG3Ag9", "" + tmpMap1.getNextG3Ag9());
					ratioMapInfo.put("nextG3Ag10", "" + tmpMap1.getNextG3Ag10());

					ratioMapInfo.put("nextG4Com", "" + tmpMap1.getNextG4Com());
					ratioMapInfo.put("nextG4Sc", "" + tmpMap1.getNextG4Sc());
					ratioMapInfo.put("nextG4Bc", "" + tmpMap1.getNextG4Bc());
					ratioMapInfo.put("nextG4Co", "" + tmpMap1.getNextG4Co());
					ratioMapInfo.put("nextG4Sa", "" + tmpMap1.getNextG4Sa());
					ratioMapInfo.put("nextG4Ag", "" + tmpMap1.getNextG4Ag());

					ratioMapInfo.put("nextG4Ag1", "" + tmpMap1.getNextG4Ag1());
					ratioMapInfo.put("nextG4Ag2", "" + tmpMap1.getNextG4Ag2());
					ratioMapInfo.put("nextG4Ag3", "" + tmpMap1.getNextG4Ag3());
					ratioMapInfo.put("nextG4Ag4", "" + tmpMap1.getNextG4Ag4());
					ratioMapInfo.put("nextG4Ag5", "" + tmpMap1.getNextG4Ag5());
					ratioMapInfo.put("nextG4Ag6", "" + tmpMap1.getNextG4Ag6());
					ratioMapInfo.put("nextG4Ag7", "" + tmpMap1.getNextG4Ag7());
					ratioMapInfo.put("nextG4Ag8", "" + tmpMap1.getNextG4Ag8());
					ratioMapInfo.put("nextG4Ag9", "" + tmpMap1.getNextG4Ag9());
					ratioMapInfo.put("nextG4Ag10", "" + tmpMap1.getNextG4Ag10());

					ratioMapInfo.put("nextG5Com", "" + tmpMap1.getNextG5Com());
					ratioMapInfo.put("nextG5Sc", "" + tmpMap1.getNextG5Sc());
					ratioMapInfo.put("nextG5Bc", "" + tmpMap1.getNextG5Bc());
					ratioMapInfo.put("nextG5Co", "" + tmpMap1.getNextG5Co());
					ratioMapInfo.put("nextG5Sa", "" + tmpMap1.getNextG5Sa());
					ratioMapInfo.put("nextG5Ag", "" + tmpMap1.getNextG5Ag());

					ratioMapInfo.put("nextG5Ag1", "" + tmpMap1.getNextG5Ag1());
					ratioMapInfo.put("nextG5Ag2", "" + tmpMap1.getNextG5Ag2());
					ratioMapInfo.put("nextG5Ag3", "" + tmpMap1.getNextG5Ag3());
					ratioMapInfo.put("nextG5Ag4", "" + tmpMap1.getNextG5Ag4());
					ratioMapInfo.put("nextG5Ag5", "" + tmpMap1.getNextG5Ag5());
					ratioMapInfo.put("nextG5Ag6", "" + tmpMap1.getNextG5Ag6());
					ratioMapInfo.put("nextG5Ag7", "" + tmpMap1.getNextG5Ag7());
					ratioMapInfo.put("nextG5Ag8", "" + tmpMap1.getNextG5Ag8());
					ratioMapInfo.put("nextG5Ag9", "" + tmpMap1.getNextG5Ag9());
					ratioMapInfo.put("nextG5Ag10", "" + tmpMap1.getNextG5Ag10());

					upMap.put("" + tmpMap1.getAccId(), upMapInfo);
					ratioMap.put("" + tmpMap1.getAccId(), ratioMapInfo);

					if (type > ACC_LEVEL_SC && type <= ACC_LEVEL_MEM) {
						tmpList.add("<a onclick=\"javascript:getUpper(" + tmpMap1.getAccId() + "," + status + ");\" class=\"btn-link\"></a>");
					}
					if (type > ACC_LEVEL_COM) {
						tmpList.add(tmpMap1.getOnlines() == null ? "N" : tmpMap1.getOnlines());
					}
					if (type == ACC_LEVEL_MEM) {
						tmpList.add(tmpMap1.getAccType());
					}
					if (type == ACC_LEVEL_COM) {
						tmpList.add(tmpMap1.getAccName());
						tmpList.add(tmpMap1.getNickname());
					} else {
						tmpList.add(tmpMap1.getNickname());
						if (type == ACC_LEVEL_MEM) {
							tmpList.add("<a href=javascript:getAccDetails(" + tmpMap1.getAccId() + ")>" + tmpMap1.getAccName() + "</a>");
						} else {
							tmpList.add(tmpMap1.getAccName());
						}

					}
					if (type > ACC_LEVEL_COM && type < ACC_LEVEL_MEM) {
						int[] getMag = { tmpMap1.getBc(), tmpMap1.getCo(), tmpMap1.getSa(), tmpMap1.getAg(), tmpMap1.getAg1(), tmpMap1.getAg2(),
								tmpMap1.getAg3(), tmpMap1.getAg4(), tmpMap1.getAg5(), tmpMap1.getAg6(), tmpMap1.getAg7(), tmpMap1.getAg8(),
								tmpMap1.getAg9(), tmpMap1.getAg10() };

						for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
							if (type <= ACC_LEVEL_MAG[i]) {
								if ((i + 1) == ACC_LEVEL_MAG.length) {
									if (tmpMap1.getMem() > 0) {
										tmpList.add("<a onclick=\"searchAccLowerAccData(" + ACC_LEVEL_MEM + ", " + tmpMap1.getAccId() + "," + status
												+ ");\">" + tmpMap1.getMem() + "</a>");
									} else {
										tmpList.add("0");
									}
								} else {
									if (getMag[i] > 0) {
										tmpList.add("<a onclick=\"searchAccLowerAccData(" + ACC_LEVEL_MAG[i + 1] + ", " + tmpMap1.getAccId() + ","
												+ status + ");\">" + getMag[i] + "</a>");
									} else {
										tmpList.add("0");
									}
								}
							}
						}
					}

					if (type == ACC_LEVEL_COM) {
						tmpList.add(tmpMap1.getCreateTime().toString());
						tmpList.add(tmpMap1.getGroupText() == null ? "" : tmpMap1.getGroupText());
					} else {

						tmpList.add("<button onclick=\"javascript:onClickShowRatio(" + type + ", " + tmpMap1.getAccId() + ",'" + tmpMap1.getAccName()
								+ "');\">查詢</button>");
						if (status == STATUS_ENABLED) {
							tmpList.add("<a href=\"javascript:onClickSetting(" + type + ", " + tmpMap1.getAccId()
									+ ");\" class=\"icon-function\"><img src='img/icon_setting.svg'>設定</a>"
									+ "<a href=\"javascript:onClickChangeStatus(" + type + ", " + status + "," + tmpMap1.getAccId() + ",'"
									+ tmpMap1.getAccName() + "');\" class=\"icon-function\"><img src=\"img/icon_check.svg\">啟用</a>");
						} else if (status == STATUS_NOLOGIN) {
							tmpList.add("<a href=\"javascript:onClickSetting(" + type + ", " + tmpMap1.getAccId()
									+ ");\" class=\"icon-function\"><img src='img/icon_setting.svg'>設定</a>"
									+ "<a href=\"javascript:onClickChangeStatus(" + type + ", " + status + "," + tmpMap1.getAccId() + ",'"
									+ tmpMap1.getAccName() + "');\" class=\"icon-function\"><img src=\"img/icon_no_entry.svg\">禁止登入</a>");
						} else if (status == STATUS_DISABLED) {
							LOG.debug(tmpMap1.isUpdateStatus());
							if (tmpMap1.isUpdateStatus()) {
								tmpList.add("<a href=\"javascript:onClickSetting(" + type + ", " + tmpMap1.getAccId()
										+ ");\" class=\"icon-function\"><img src='img/icon_setting.svg'>設定</a>"
										+ "<a href=\"javascript:onClickChangeStatus(" + type + ", " + status + "," + tmpMap1.getAccId() + ",'"
										+ tmpMap1.getAccName() + "');\" class=\"icon-function\"><img src=\"img/icon_forbidden.svg\">停用</a>");
							} else {
								tmpList.add("<a href=\"javascript:onClickSetting(" + type + ", " + tmpMap1.getAccId()
										+ ");\" class=\"icon-function\"><img src='img/icon_setting.svg'>設定</a>"
										+ "<a href= \"javascript:void(0);\" class=\"icon-function\"><img src=\"img/icon_forbidden.svg\">停用</a>");
							}
						} else {
							tmpList.add("<a href=\"javascript:onClickSetting(" + type + ", " + tmpMap1.getAccId()
									+ ");\" class=\"icon-function\"><img src='img/icon_setting.svg'>設定</a>"
									+ "<a href= \"javascript:void(0);\" class=\"icon-function\"><img src=\"img/icon_delete.svg\">刪除</a>");
						}

						tmpList.add(tmpMap1.getCreateTime().toString());
						tmpList.add(tmpMap1.getLastLoginTime().toString());

					}

					tmpList.add(tmpMap1.getIp());

					if (type == ACC_LEVEL_COM) {

						if (status == STATUS_ENABLED) {
							tmpList.add("<a href=\"javascript:onClickSetting(" + type + ", " + tmpMap1.getAccId()
									+ ");\" class=\"icon-function\"><img src='img/icon_setting.svg'>設定</a>"
									+ "<a href=\"javascript:onClickChangeStatus(" + type + ", " + status + "," + tmpMap1.getAccId() + ",'"
									+ tmpMap1.getAccName() + "');\" class=\"icon-function\"><img src=\"img/icon_check.svg\">啟用</a>");
						} else if (status == STATUS_NOLOGIN) {
							tmpList.add("<a href=\"javascript:onClickSetting(" + type + ", " + tmpMap1.getAccId()
									+ ");\" class=\"icon-function\"><img src='img/icon_setting.svg'>設定</a>"
									+ "<a href=\"javascript:onClickChangeStatus(" + type + ", " + status + "," + tmpMap1.getAccId() + ",'"
									+ tmpMap1.getAccName() + "');\" class=\"icon-function\"><img src=\"img/icon_no_entry.svg\">禁止登入</a>");
						} else if (status == STATUS_DISABLED) {
							LOG.debug(tmpMap1.isUpdateStatus());
							if (tmpMap1.isUpdateStatus()) {
								tmpList.add("<a href=\"javascript:onClickSetting(" + type + ", " + tmpMap1.getAccId()
										+ ");\" class=\"icon-function\"><img src='img/icon_setting.svg'>設定</a>"
										+ "<a href=\"javascript:onClickChangeStatus(" + type + ", " + status + "," + tmpMap1.getAccId() + ",'"
										+ tmpMap1.getAccName() + "');\" class=\"icon-function\"><img src=\"img/icon_forbidden.svg\">停用</a>");
							} else {
								tmpList.add("<a href=\"javascript:onClickSetting(" + type + ", " + tmpMap1.getAccId()
										+ ");\" class=\"icon-function\"><img src='img/icon_setting.svg'>設定</a>"
										+ "<a href= \"javascript:void(0);\" class=\"icon-function\"><img src=\"img/icon_forbidden.svg\">停用</a>");
							}
						} else {
							tmpList.add("<a href=\"javascript:onClickSetting(" + type + ", " + tmpMap1.getAccId()
									+ ");\" class=\"icon-function\"><img src='img/icon_setting.svg'>設定</a>"
									+ "<a href= \"javascript:void(0);\" class=\"icon-function\"><img src=\"img/icon_delete.svg\">刪除</a>");
						}

					}
					tmpListList.add(tmpList);
					tmpList = new ArrayList<String>();
					obj = null;
				}
				tmpMap.put("td", tmpListList);
				tmpMap.put("up", upMap);
				tmpMap.put("ratio", ratioMap);
			}
			tmpMap.put("serchAccData", service.getAllAccData(userId));

			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));

			JSONObject responseJSONObject = new JSONObject(tmpMap);
			res.setContentType("application/json;charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpList != null) {
				tmpList.clear();
				tmpList = null;
			}
			if (tmpListList != null) {
				tmpListList.clear();
				tmpListList = null;
			}
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
			return;

		} else {

		}
		return;
	}

	// private void printRequest(HttpServletRequest httpRequest) {
	// LOG.debug(" \n\n Headers");
	//
	// Enumeration<String> headerNames = httpRequest.getHeaderNames();
	// while (headerNames.hasMoreElements()) {
	// String headerName = headerNames.nextElement();
	// LOG.debug(headerName + " = " + httpRequest.getHeader(headerName));
	// }
	//
	// LOG.debug("\n\nParameters");
	//
	// Enumeration<String> params = httpRequest.getParameterNames();
	// while (params.hasMoreElements()) {
	// String paramName = params.nextElement();
	// LOG.debug(paramName + " = " + httpRequest.getParameter(paramName));
	// }
	//
	// LOG.debug("\n\n Row data");
	// LOG.debug(extractPostRequestBody(httpRequest));
	//
	// LOG.debug("\n");
	// LOG.debug("getRemoteAddr = " + httpRequest.getRemoteAddr());
	// LOG.debug("\n");
	//
	// }

	@SuppressWarnings("resource")
	static String extractPostRequestBody(HttpServletRequest request) {
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			Scanner s = null;
			try {
				s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return s.hasNext() ? s.next() : "";
		}
		return "";
	}

	public void showAddAccount(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
		Map<String, Object> tmpMap = null;
		long userId = 0;
		Map<String, Object> map = null;
		String userIp = null;

		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;

		int selectedSecMenu = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			map = new ConcurrentHashMap<String, Object>();
			userIp = getIpAddr(req);

			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);

			selectedSecMenu = Integer.parseInt("" + req.getParameter("selectedSecMenu"));

			if (selectedSecMenu > ACC_LEVEL_COM) {
				List<Map<String, Object>> upAccountListMap = service.getUpAccountName(userId, selectedSecMenu);
				for (int i = 0; i < upAccountListMap.size(); i++) {
					Map<String, Object> mapStr = new ConcurrentHashMap<String, Object>();
					List<String> currentRatioList = new ArrayList<String>();

					mapStr.put("accName", upAccountListMap.get(i).get("accName").toString());
					mapStr.put("balance", "" + upAccountListMap.get(i).get("balance").toString());
					mapStr.put("fullRatio", "" + upAccountListMap.get(i).get("fullRatio"));
					mapStr.put("accLevelType", "" + upAccountListMap.get(i).get("accLevelType"));
					mapStr.put("handicap", "" + upAccountListMap.get(i).get("handicap"));

					currentRatioList.add("" + upAccountListMap.get(i).get("g1CurrentRatio"));
					currentRatioList.add("" + upAccountListMap.get(i).get("g2CurrentRatio"));
					currentRatioList.add("" + upAccountListMap.get(i).get("g3CurrentRatio"));
					currentRatioList.add("" + upAccountListMap.get(i).get("g4CurrentRatio"));
					currentRatioList.add("" + upAccountListMap.get(i).get("g5CurrentRatio"));
					mapStr.put("currentRatio", currentRatioList);
					map.put("" + upAccountListMap.get(i).get("accId"), mapStr);

				}
				tmpMap.put("managerRatio", map);
			}
			if (selectedSecMenu <= ACC_LEVEL_AG10) {

				List<Map<String, Object>> createAuthMap = service.createAuth(selectedSecMenu + 1, userId);
				if (createAuthMap != null) {
					tmpMap.put("createauth", createAuthMap);
				}
			}

		} catch (Exception e) {
			LOG.info("catch:" + e.getMessage());
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
			if (map != null) {
				map.clear();
				map = null;
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
			selectedSecMenu = 0;
		}
		return;
	}

	public void add(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		Map<String, Object> tmpMap = null;
		Map<String, Object> map = null;
		String userIp = null;

		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;

		MoneyServiceImpl service2 = null;
		MoneyDaoImpl dao2 = null;

		int accLevelType = 0;

		BigDecimal depositMoney = BigDecimal.ZERO;
		long accId = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong(req.getSession().getAttribute("accId").toString());
			map = getRequestAccountData(req);

			userIp = getIpAddr(req);

			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);

			service2 = new MoneyServiceImpl(userId, userIp);
			dao2 = new MoneyDaoImpl();
			service2.setDao(dao2);

			accLevelType = Integer.parseInt("" + map.get("accLevelType"));
			map.put("userAccId", userId);
			map.put("ip", userIp);

			if (map.containsKey("depositMoney")) {
				if (!"".equals(map.get("depositMoney").toString()) && map.get("depositMoney") != null) {
					depositMoney = new BigDecimal("" + map.get("depositMoney"));
				} else {
					depositMoney = BigDecimal.ZERO;
				}
			}

			if (accLevelType == ACC_LEVEL_MEM) {
				accId = service.addMemData(map);
				if (accId > 0) {
					if (depositMoney.compareTo(BigDecimal.ZERO) == 1 && Integer.parseInt(map.get("memberType").toString()) == DIRECTLY_UNDER_MEM) {
						service2.Deposit(accId, accIdToLong(map.get("upAccId").toString()), depositMoney, "ctt_manager.ctt_member_acc",
								LOG_ACTION_ADD_ACC_DEPOSIT_ADD);
					}
				}
			} else if (accLevelType == ACC_LEVEL_COM) {
				accId = service.addComData(map);
			} else if (accLevelType == ACC_LEVEL_SC) {
				accId = service.addScData(map);
				if (accId > 0) {
					if (depositMoney.compareTo(BigDecimal.ZERO) == 1) {
						service2.scDeposit(accId, depositMoney, LOG_ACTION_ADD_ACC_DEPOSIT_ADD);
					}
				}
			} else if (accLevelType >= ACC_LEVEL_BC && accLevelType < ACC_LEVEL_MEM) {
				accId = service.addMgrData(map);
				if (accId > 0) {
					if (depositMoney.compareTo(BigDecimal.ZERO) == 1) {
						service2.Deposit(accId, accIdToLong(map.get("upAccId").toString()), depositMoney, "ctt_manager.ctt_manager_acc",
								LOG_ACTION_ADD_ACC_DEPOSIT_ADD);
					}
				}
			}

			if (accId == 0) {
				tmpMap.put("message", "duplicate");
			} else if (accId > 0) {
				tmpMap.put("message", "success");
			} else if (accId == -1) {
				tmpMap.put("message", "fail");
			}
		} catch (Exception e) {
			LOG.info("catch:" + e.getMessage());
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
			if (map != null) {
				map.clear();
				map = null;
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

			userId = 0;
			userIp = null;
			accLevelType = 0;
			depositMoney = null;
			accId = 0;
		}
		return;
	}

	public void checkAccName(HttpServletRequest req, HttpServletResponse res) throws IOException {

		long userId = 0;
		String userIp = null;
		int result = 0;
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;
		int acc_level_type = 0;
		try {
			userId = accIdToLong(req.getSession().getAttribute("accId").toString());
			userIp = getIpAddr(req);
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);
			if (acc_level_type < ACC_LEVEL_MEM) {
				result = service.checkAccName("ctt_manager.ctt_manager_acc", "" + req.getParameter("acc_name").trim());
			} else if (acc_level_type == ACC_LEVEL_MEM) {
				result = service.checkAccName("ctt_manager.ctt_member_acc", "" + req.getParameter("acc_name").trim());
			}
			if (result == 0) {
				tmpMap.put("result", 1);
			} else {
				tmpMap.put("result", 0);
			}
		} catch (Exception e) {
			LOG.info("catch:" + e.getMessage());
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
			result = 0;
			acc_level_type = 0;
		}
		return;
	}

	private Map<String, Object> getRequestAccountData(HttpServletRequest httpRequest) {
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		Enumeration<String> params = httpRequest.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			LOG.debug(paramName + " = " + httpRequest.getParameter(paramName));
			if (paramName.equals("pwd")) {
				String pwd = checkPWD(httpRequest.getParameter(paramName).toString().trim());
				pwd = AESUtil.encrypt_CBC(pwd, KEY);
				map.put(paramName, pwd);
			} else if (paramName.equals("nickname")) {
				String nickname = checkNameOut(httpRequest.getParameter(paramName).toString().trim());
				map.put(paramName, nickname);
			} else {
				map.put(paramName, httpRequest.getParameter(paramName).trim());
			}
		}
		return map;
	}

	public void getSeetingAccData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;

		Map<String, Object> tmpMap = null;
		Map<String, Object> map = null;
		String userIp = null;

		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;

		int accLevelType = 0;
		int accId = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			map = getRequestAccountData(req);
			userIp = "" + map.get("ip");

			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);

			accLevelType = Integer.parseInt("" + req.getParameter("accLevelType"));
			accId = Integer.parseInt("" + req.getParameter("accId"));

			if (accLevelType == ACC_LEVEL_COM) {
				try {
					tmpMap.put("createauth", service.createAuth(accLevelType, userId));
					tmpMap.put("accData", service.getSeetingComData(accId));

				} catch (SQLException e) {
					LOG.debug(e.getMessage());
				}
			} else if (accLevelType == ACC_LEVEL_SC) {
				try {
					tmpMap.put("createauth", service.createAuth(accLevelType, userId));
					tmpMap.put("accData", service.getSeetingScData(accId));
				} catch (SQLException e) {
					LOG.debug(e.getMessage());
				}
			} else if (accLevelType > ACC_LEVEL_SC && accLevelType < ACC_LEVEL_MEM) {
				try {
					tmpMap.put("createauth", service.createAuth(accLevelType, userId));
					tmpMap.put("accData", service.getSeetingManagerData(accId));
				} catch (SQLException e) {
				}

			} else if (accLevelType == ACC_LEVEL_MEM) {
				try {
					tmpMap.put("accData", service.getSeetingMemberData(accId));
				} catch (SQLException e) {
					LOG.info("Exception:" + e.getMessage());
					ShowLog.err(LOG, e);
				}
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
			if (map != null) {
				map.clear();
				map = null;
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
			accLevelType = 0;
			accId = 0;
		}
		return;
	}

	public void seetingAccData(HttpServletRequest req, HttpServletResponse res) throws IOException {

		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		Map<String, Object> map = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;

		MoneyServiceImpl service2 = null;
		MoneyDaoImpl dao2 = null;

		int accLevelType = 0;
		long accId = 0;
		BigDecimal depositMoney = BigDecimal.ZERO;
		BigDecimal withdrawalMoney = BigDecimal.ZERO;

		Map<String, Object> checkAccRatioMap = null;

		boolean success = false;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			checkAccRatioMap = new ConcurrentHashMap<String, Object>();
			map = getRequestAccountData(req);
			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);

			if (map.containsKey("accLevelType")) {
				accLevelType = Integer.parseInt("" + map.get("accLevelType"));
			}
			if (map.containsKey("accId")) {
				accId = accIdToLong("" + map.get("accId"));
			}
			if (map.containsKey("depositMoney")) {
				depositMoney = new BigDecimal("" + map.get("depositMoney"));
			}
			if (map.containsKey("withdrawalMoney")) {
				withdrawalMoney = new BigDecimal("" + map.get("withdrawalMoney"));
			}

			map.put("updateAccId", userId);
			map.put("ip", userIp);

			if (depositMoney.compareTo(BigDecimal.ZERO) == 1 || withdrawalMoney.compareTo(BigDecimal.ZERO) == 1) {
				service2 = new MoneyServiceImpl(userId, userIp);
				dao2 = new MoneyDaoImpl();
				service2.setDao(dao2);
			}

			if (accLevelType < ACC_LEVEL_MEM && accLevelType >= ACC_LEVEL_SC) {
				checkAccRatioMap = service.checkAccRatio(accId, Integer.parseInt("" + map.get("currentRatioG" + LOTTERY)),
						Integer.parseInt("" + map.get("currentRatioG" + LIVEVIDEO)), Integer.parseInt("" + map.get("currentRatioG" + MOVEMENT)),
						Integer.parseInt("" + map.get("currentRatioG" + VIDEOGAME)), Integer.parseInt("" + map.get("currentRatioG" + GAME)));
				success = Boolean.parseBoolean(checkAccRatioMap.get("isSuccess").toString());
			} else {
				success = true;
			}
			if (success) {
				if (accLevelType == ACC_LEVEL_COM) {
					success = service.updateCom(map);
				} else {
					if (accLevelType == ACC_LEVEL_SC) {
						success = service.updateMgr(map);
						if (success) {
							if (depositMoney.compareTo(BigDecimal.ZERO) == 1) {
								service2.scDeposit(accId, depositMoney, LOG_ACTION_SET_ACC_DEPOSIT_ADD);
							} else if (withdrawalMoney.compareTo(BigDecimal.ZERO) == 1) {
								service2.withdrawal(accId, withdrawalMoney, "ctt_manager.ctt_manager_acc", LOG_ACTION_SET_ACC_WITHDRAWAL_DEBIT);
							}
						}
					} else if (accLevelType == ACC_LEVEL_MEM) {
						success = service.updateMem(map);
						if (success) {
							if (depositMoney.compareTo(BigDecimal.ZERO) == 1) {
								service2.Deposit(accId, Integer.parseInt("" + map.get("upAccId")), depositMoney, "ctt_manager.ctt_member_acc",
										LOG_ACTION_SET_ACC_DEPOSIT_ADD);
							} else if (withdrawalMoney.compareTo(BigDecimal.ZERO) == 1) {
								service2.withdrawal(accId, withdrawalMoney, "ctt_manager.ctt_member_acc", LOG_ACTION_SET_ACC_WITHDRAWAL_DEBIT);
							}
						}
					} else if (accLevelType > ACC_LEVEL_SC && accLevelType < ACC_LEVEL_MEM) {
						success = service.updateMgr(map);

						if (success) {
							if (depositMoney.compareTo(BigDecimal.ZERO) == 1) {
								service2.Deposit(accId, Integer.parseInt("" + map.get("upAccId")), depositMoney, "ctt_manager.ctt_manager_acc",
										LOG_ACTION_SET_ACC_DEPOSIT_ADD);
							} else if (withdrawalMoney.compareTo(BigDecimal.ZERO) == 1) {
								service2.withdrawal(accId, withdrawalMoney, "ctt_manager.ctt_manager_acc", LOG_ACTION_SET_ACC_WITHDRAWAL_DEBIT);
							}
						}
					}

				}
				if (success) {
					tmpMap.put("message", "success");
				} else {
					tmpMap.put("overAccList", checkAccRatioMap.get("checkRatio"));
					tmpMap.put("message", "fail");
				}

				LOG.debug("seetimgAccData Action =>" + tmpMap.toString());
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("message", "fail");
		} finally {
			LOG.debug(tmpMap.toString());

			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (map != null) {
				map.clear();
				map = null;
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
			if (checkAccRatioMap != null) {
				checkAccRatioMap.clear();
				checkAccRatioMap = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;

			userId = 0;
			userIp = null;
			accLevelType = 0;
			accId = 0;
			depositMoney = null;
			withdrawalMoney = null;
			success = false;
		}
		return;
	}

	public void updateMemberWithdrawPwd(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		boolean toole = false;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;
		long accId = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);
			accId = accIdToLong(req.getParameter("accId"));

			toole = service.updateMemberWithdrawPwd(accId);

			if (toole) {
				tmpMap.put("message", "success");
			} else {
				tmpMap.put("message", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
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
			toole = false;
			accId = 0;
		}
		return;
	}

	public void updateMemberPwd(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		boolean toole = false;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = new AccountManageServiceImpl();
		long accId = 0;
		String pwd = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);
			accId = accIdToLong(req.getParameter("accId"));
			pwd = checkPWD(getRandomPassword());

			toole = service.updateMemberPwd(accId, AESUtil.encrypt_CBC(pwd, KEY));
			if (toole) {
				tmpMap.put("pwd", pwd);
				tmpMap.put("message", "success");
			} else {
				tmpMap.put("pwd", "");
				tmpMap.put("message", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
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
			toole = false;
			accId = 0;
			pwd = null;
		}
		return;
	}

	public void updateAccStatus(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;

		long accId = 0;
		int status = 0;
		int accLevelType = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);

			accId = accIdToLong("" + req.getParameter("accId"));
			status = Integer.parseInt("" + req.getParameter("status"));
			accLevelType = Integer.parseInt("" + req.getParameter("accLevelType"));

			tmpMap.put("accStatusOpsType", service.updateAccStatus(accId, status, accLevelType));
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
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
			accId = 0;
			status = 0;
			accLevelType = 0;
		}
		return;
	}

	private String getRandomPassword() {
		int z;
		StringBuilder sb = new StringBuilder();
		int i;
		for (i = 0; i < 10; i++) {
			z = (int) ((Math.random() * 7) % 3);

			if (z == 1) { // 放數字
				sb.append((int) ((Math.random() * 10) + 48));
			} else if (z == 2) { // 放大寫英文
				sb.append((char) (((Math.random() * 26) + 65)));
			} else {// 放小寫英文
				sb.append(((char) ((Math.random() * 26) + 97)));
			}
		}
		return sb.toString().toUpperCase();
	}

	public void showLog(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;

		int totalCount = 0;
		int totalPage = 0;
		int firstCount = 0;
		int count = req.getParameter("count") == null ? 0 : Integer.parseInt(req.getParameter("count"));
		int page = req.getParameter("page") == null ? 0 : Integer.parseInt(req.getParameter("page"));
		int nextPage = req.getParameter("nextPage") == null ? 0 : Integer.parseInt(req.getParameter("nextPage"));

		LOG.debug("page==============" + page);
		LOG.debug("nextPage==============" + nextPage);
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);

			totalCount = service.getLogTotalCount(userId, accIdToLong("" + req.getParameter("updateAccId")),
					Integer.parseInt("" + req.getParameter("actionUpperId")), Integer.parseInt("" + req.getParameter("accLevel")));

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
			tmpMap.put("showOpsLog",
					service.showLog(userId, accIdToLong("" + req.getParameter("updateAccId")),
							Integer.parseInt("" + req.getParameter("actionUpperId")), Integer.parseInt("" + req.getParameter("accLevel")), firstCount,
							count));

			tmpMap.put("logMaxPage", totalPage);
			tmpMap.put("logPage", nextPage);
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

			userId = 0;
			userIp = null;
		}
		return;
	}

	public void getLowerLevelAccTotal(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("getLowerLevelAccTotal");
		String userIp = null;
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;
		String tokenId = "";
		long accId = 0;
		try {
			userIp = getIpAddr(req);
			if (req.getParameter("tokenId") != null && req.getSession().getAttribute("accId") != null) {
				tmpMap = new ConcurrentHashMap<String, Object>();
				accId = accIdToLong(req.getSession().getAttribute("accId").toString());
				tokenId = req.getParameter("tokenId").toString();
				dao = new AccountManageDaoImpl();
				service = new AccountManageServiceImpl(accId, userIp);
				service.setDao(dao);
				tmpMap = service.getLowerLevelAccTotal(accId, tokenId);
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
		return;
	}

	public void PWDChange(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		Map<String, Object> map = null;
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;
		boolean success = false;
		String oldPwd = null;
		String newPwd = null;
		String checkNewPwd = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);
			map = getRequestAccountData(req);
			if (map.containsKey("oldPassWord") && map.containsKey("newPassWord") && map.containsKey("checkNewPassWord")) {

				oldPwd = checkPWD("" + map.get("oldPassWord"));
				newPwd = checkPWD("" + map.get("newPassWord"));
				checkNewPwd = checkPWD("" + map.get("checkNewPassWord"));

				LOG.debug("oldPwd--" + oldPwd + "newPwd--" + newPwd + "checkNewPwd--" + checkNewPwd);

				if (!"".equals(oldPwd) && !"".equals(newPwd) && !"".equals(checkNewPwd)) {
					if (newPwd.equals(checkNewPwd)) {
						success = service.PWDChange(AESUtil.encrypt_CBC(oldPwd, KEY), AESUtil.encrypt_CBC(newPwd, KEY));
						LOG.debug("success--" + success);
					}
				}
			}
			if (success) {
				tmpMap.put("message", "success");
			} else {
				tmpMap.put("message", "fail");
			}
			LOG.debug(tmpMap.toString());
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

			userId = 0;
			userIp = null;
		}
		return;
	}

	public void getAccDetails(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;
		long accId = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				accId = accIdToLong(req.getParameter("accId").toString());

				dao = new AccountManageDaoImpl();
				service = new AccountManageServiceImpl();
				service.setDao(dao);
				tmpMap.putAll(service.getAccDetails(accId));
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
		return;
	}

	public void updateBankData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;
		String url = "";
		String bank = "";
		String bankAcc = "";
		String bankAccName = "";
		String bankCardBranches = "";
		String area = "";
		long accId = 0;
		int bankType = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null && req.getParameter("banktype") != null
					&& req.getParameter("bank") != null && req.getParameter("bankAcc") != null && req.getParameter("bankAccName") != null
					&& req.getParameter("bankCardBranches") != null && req.getParameter("area") != null && req.getParameter("url") != null) {

				url = req.getParameter("url").toString();
				accId = accIdToLong(req.getParameter("accId").toString());
				bankType = Integer.parseInt(req.getParameter("banktype").toString());
				bank = checkNameOut(req.getParameter("bank").toString());
				bankAcc = clearNum(req.getParameter("bankAcc").toString());
				bankAccName = checkNameOut(req.getParameter("bankAccName").toString());
				bankCardBranches = checkNameOut(req.getParameter("bankCardBranches").toString());
				area = checkNameOut(req.getParameter("area").toString());

				if (checkAuth(accId, url)) {
					dao = new AccountManageDaoImpl();
					service = new AccountManageServiceImpl();
					service.setDao(dao);
					if (bankType == BANK_DATA_FIRST) {
						tmpMap.put("isSuccess", service.updateBankDefaultData(accId, bank, bankAcc, bankAccName, bankCardBranches, area));
					} else if (bankType == BANK_DATA_SECOND) {
						tmpMap.put("isSuccess", service.updateBankNoDefaultData(accId, bank, bankAcc, bankAccName, bankCardBranches, area));
					}
				}
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
		return;
	}

	public void addGameServer(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;
		Map<String, Object> map = null;
		Map<String, Object> tmpMap = null;
		long sid = 0;
		List<String> chkList = null;

		String[] check = { "gameServerId", "bet", "maxPlayer", "gameTimesType", "commission", "thread", "beatsTimeOut", "gameTimeOut",
				"autoTimeStamp", "waitContinueTimeOut", "continueTimesType", "serviceTime", "serverStatus" };
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = Long.parseLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);
			map = getRequestData(req, res);
			chkList = checkMapKey(check, map);
			if (chkList != null && chkList.size() > 0) {
				sid = service.insertGameServer("" + map.get("gameServerId"), Integer.parseInt("" + map.get("bet")),
						Integer.parseInt("" + map.get("maxPlayer")), Integer.parseInt("" + map.get("gameTimesType")),
						Integer.parseInt("" + map.get("commission")), Integer.parseInt("" + map.get("thread")),
						Integer.parseInt("" + map.get("beatsTimeOut")), Integer.parseInt("" + map.get("gameTimeOut")),
						Integer.parseInt("" + map.get("autoTimeStamp")), Integer.parseInt("" + map.get("waitContinueTimeOut")),
						Integer.parseInt("" + map.get("continueTimesType")), "" + map.get("serviceTime"),
						Integer.parseInt("" + map.get("serverStatus")));
			} else {
				sid = 0;
				LOG.debug("addGameServer_map_fail===" + chkList.toString());
			}

			if (sid > 0) {
				tmpMap.put("message", "success");
				tmpMap.put("orderId", sid);
			} else {
				tmpMap.put("message", "fail");
				tmpMap.put("chkList", chkList);// 前台show錯誤key
			}
		} catch (Exception e) {
			tmpMap.put("message", "fail");
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
			sid = 0;
		}
		return;
	}

	public void disableBankCard(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;
		String url = "";
		int bankCardId = 0;
		long accId = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null && req.getParameter("url") != null
					&& req.getParameter("bankCardId") != null) {
				url = req.getParameter("url").toString();
				accId = accIdToLong(req.getParameter("accId").toString());
				bankCardId = Integer.parseInt(req.getParameter("bankCardId").toString());

				if (checkAuth(accId, url)) {
					dao = new AccountManageDaoImpl();
					service = new AccountManageServiceImpl(ACC_ID, IP);
					service.setDao(dao);
					boolean todo = service.disableBankCard(bankCardId, accId);
					tmpMap.put("isSuccess", todo);
				}
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
		return;
	}

	public void getGameServerData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(accIdToLong(req.getSession().getAttribute("accId").toString()), getIpAddr(req));
			service.setDao(dao);
			tmpMap.put("GameServerData", service.getGameServerData());
			LOG.debug(tmpMap.toString());
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
		return;
	}

	public void setGameServerData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;
		Map<String, Object> map = null;
		String[] check = { "sid", "gameServerId", "nextBet", "nextMaxPlayer", "nextGameTimesType", "nextCommission", "nextThread", "nextBeatsTimeOut",
				"nextGameTimeOut", "nextAutoTimeStamp", "nextWaitContinueTimeOut", "nextContinueTimesType", "nextServiceTime", "nextServerStatus" };
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			map = getRequestData(req, res);
			LOG.debug(map.toString());
			if (req.getParameter("tokenId") != null) {
				if (checkMapKey(check, map) != null && checkMapKey(check, map).size() == 0) {
					dao = new AccountManageDaoImpl();
					service = new AccountManageServiceImpl(Long.parseLong(req.getSession().getAttribute("accId").toString()), getIpAddr(req));
					service.setDao(dao);

					boolean todo = service.setGameServerData(Integer.parseInt("" + map.get("sid")), "" + map.get("gameServerId"),
							Integer.parseInt("" + map.get("nextBet")), Integer.parseInt("" + map.get("nextMaxPlayer")),
							Integer.parseInt("" + map.get("nextGameTimesType")), Integer.parseInt("" + map.get("nextCommission")),
							Integer.parseInt("" + map.get("nextThread")), Integer.parseInt("" + map.get("nextBeatsTimeOut")),
							Integer.parseInt("" + map.get("nextGameTimeOut")), Integer.parseInt("" + map.get("nextAutoTimeStamp")),
							Integer.parseInt("" + map.get("nextWaitContinueTimeOut")), Integer.parseInt("" + map.get("nextContinueTimesType")),
							"" + map.get("nextServiceTime"), Integer.parseInt("" + map.get("nextServerStatus")));

					if (todo) {
						tmpMap.put("isSuccess", todo);
					} else {
						tmpMap.put("isSuccess", false);
					}
				} else {
					tmpMap.put("isSuccess", false);
					tmpMap.put("message", checkMapKey(check, map));
				}
			}
		} catch (Exception e) {
			tmpMap.put("isSuccess", false);
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
			if (map != null) {
				map.clear();
				map = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
			check = null;
		}
		return;
	}

	public void showOrderLog(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;

		int totalCount = 0;
		int totalPage = 0;
		int firstCount = 0;
		int count = req.getParameter("count") == null ? 0 : Integer.parseInt(req.getParameter("count"));
		int page = req.getParameter("page") == null ? 0 : Integer.parseInt(req.getParameter("page"));
		int nextPage = req.getParameter("nextPage") == null ? 0 : Integer.parseInt(req.getParameter("nextPage"));
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);

			totalCount = service.getOrderLogTotalCount(Integer.parseInt("" + req.getParameter("upperAction")));

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
			tmpMap.put("showOrderLog", service.showOrderLog(Integer.parseInt("" + req.getParameter("upperAction")), firstCount, count));
			tmpMap.put("logMaxPage", totalPage);
			tmpMap.put("logPage", nextPage);
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

			userId = 0;
			userIp = null;
		}
		return;
	}

	public void showGameSetLog(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		AccountManageDaoImpl dao = null;
		AccountManageServiceImpl service = null;

		int totalCount = 0;
		int totalPage = 0;
		int firstCount = 0;
		int count = req.getParameter("count") == null ? 0 : Integer.parseInt(req.getParameter("count"));
		int page = req.getParameter("page") == null ? 0 : Integer.parseInt(req.getParameter("page"));
		int nextPage = req.getParameter("nextPage") == null ? 0 : Integer.parseInt(req.getParameter("nextPage"));
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			dao = new AccountManageDaoImpl();
			service = new AccountManageServiceImpl(userId, userIp);
			service.setDao(dao);

			totalCount = service.getOrderLogTotalCount(Integer.parseInt("" + req.getParameter("upperAction")));

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
			tmpMap.put("showGameSetLog", service.showGameSetLog(Integer.parseInt("" + req.getParameter("upperAction")), firstCount, count));
			tmpMap.put("logMaxPage", totalPage);
			tmpMap.put("logPage", nextPage);
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

			userId = 0;
			userIp = null;
		}
		return;
	}
}
