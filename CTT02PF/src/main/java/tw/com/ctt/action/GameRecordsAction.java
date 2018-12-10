package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.GameRecordsDaoImpl;
import tw.com.ctt.service.impl.GameRecordsServiceImpl;
import tw.com.ctt.util.ShowLog;

import static tw.com.ctt.constant.GameConstant.*;

@WebServlet(name = "GameRecords", urlPatterns = { "/GameRecords" })
public class GameRecordsAction extends BaseAction {

	private static final long serialVersionUID = -6584467566969675929L;
	private static final Logger LOG = LogManager.getLogger(GameRecordsAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/GameRecords!searchRecords" };
	private String[] authURL = { "/GameRecords" };

	public GameRecordsAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("GameRecordsAction start");
	}

	public boolean checkLogin() {
		return true;
	}

	public void main(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("AuthGroupAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_GR_checkGameRecordsAction(){\n");
			sb.append("    if(typeof GameRecords==='undefined' || typeof GameRecords != 'function' ){\n");
			sb.append("        checkJS('GameRecords', 'AccountManage/GameRecords.js');\n");
			sb.append("    } else {\n");
			sb.append("        GameRecords();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_GR_checkGameRecordsAction();\n");
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
			LOG.error("AuthGroupAction.main.Exception, " + e.getMessage());
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

	public void searchRecords(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;

		GameRecordsServiceImpl service = null;
		GameRecordsDaoImpl dao = null;

		String[] check = { "gameName", "gameId", "accName", "startTime", "endTime", "gameType" };
		List<String> chkList = null;
		Map<String, Object> map = null;

		int firstCount = 0;
		int count = 0;
		int page = 0;
		int nextPage = 0;
		int totalCount = 0;
		int totalPage = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			service = new GameRecordsServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
			dao = new GameRecordsDaoImpl();
			service.setDao(dao);

			page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
			nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
			count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;

			map = getRequestData(req, res);
			chkList = checkMapKey(check, map);
			if (chkList != null && chkList.size() > 0) {
				if (Integer.parseInt(map.get("gameName").toString()) == ALL_GAME) {
					totalCount = service.searchRecordsTotleCount(Long.parseLong(map.get("gameId").toString()), map.get("accName").toString(),
							map.get("startTime").toString(), map.get("endTime").toString(), Integer.parseInt(map.get("gameType").toString()));
				}
				if (Integer.parseInt(map.get("gameName").toString()) == PUNCH_GAME) {
					totalCount = service.searchRecordsTotleCount(Long.parseLong(map.get("gameId").toString()), map.get("accName").toString(),
							map.get("startTime").toString(), map.get("endTime").toString(), Integer.parseInt(map.get("gameType").toString()));
				}
			}
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
			tmpMap.put("gameRecordsLastPage", totalPage);
			tmpMap.put("gameRecordsPage", nextPage);
			if (chkList != null && chkList.size() > 0) {
				if (Integer.parseInt(map.get("gameName").toString()) == ALL_GAME) {
					tmpMap.put("punchGameRecords",
							service.searchRecords(Long.parseLong(map.get("gameId").toString()), map.get("accName").toString(),
									map.get("startTime").toString(), map.get("endTime").toString(), Integer.parseInt(map.get("gameType").toString()),
									firstCount, count));
				}
				if (Integer.parseInt(map.get("gameName").toString()) == PUNCH_GAME) {
					tmpMap.put("punchGameRecords",
							service.searchRecords(Long.parseLong(map.get("gameId").toString()), map.get("accName").toString(),
									map.get("startTime").toString(), map.get("endTime").toString(), Integer.parseInt(map.get("gameType").toString()),
									firstCount, count));
				}
			}
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

			firstCount = 0;
			count = 0;
			page = 0;
			nextPage = 0;
			totalCount = 0;
			totalPage = 0;

			check = null;
			if (chkList != null) {
				chkList.clear();
				chkList = null;
			}
			if (map != null) {
				map.clear();
				map = null;
			}
		}
		return;
	}
}
