package tw.com.ctt.action;

import java.io.BufferedReader;
//Import required java libraries
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.BaseDao;
import tw.com.ctt.dao.impl.GameDaoImpl;
import tw.com.ctt.service.impl.BaseService;
import tw.com.ctt.service.impl.GameServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "LotteryPage", urlPatterns = { "/LotteryPage" })
// Extend HttpServlet class & Implements Servlet
public class GamePageAction extends BaseAction {

	private static final long serialVersionUID = 1135893114919005938L;
	private static final Logger LOG = LogManager.getLogger(GamePageAction.class.getName());

	public boolean checkLogin() {
		return true;
	}

	public void main(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			String tmpHtml = "";
			String accNameInPost = req.getParameter("accNameGame");
			String accIdInPost = req.getParameter("accIdGame");
			String tokenIdInPost = req.getParameter("tokenIdGame");
			String lotteryPageLevel1 = req.getParameter("lotteryPageLevel1");
			String lotteryPageLevel2 = req.getParameter("lotteryPageLevel2");
			if (true) {// checkLoginSec(Long.parseLong(accIdInPost),tokenIdInPost)) {
				PrintWriter out = res.getWriter();
				if (false) {
					String filename = "/WEB-INF/lottery01.html";
					ServletContext context = getServletContext();
					InputStream is = context.getResourceAsStream(filename);
					BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
					String sCurrentLine;
					while ((sCurrentLine = br.readLine()) != null) {
						tmpHtml += "\n" + sCurrentLine;
					}
				} else {
					tmpHtml = tw.com.ctt.util.Configuration.GAME_PAGE_HTML;
				}
				tmpHtml = tmpHtml.replace("$$$1", accIdInPost);
				tmpHtml = tmpHtml.replace("$$$2", accNameInPost);
				tmpHtml = tmpHtml.replace("$$$3", tokenIdInPost);
				tmpHtml = tmpHtml.replace("$$$4", lotteryPageLevel1);
				tmpHtml = tmpHtml.replace("$$$5", lotteryPageLevel2);
				out.println(tmpHtml);
				out.flush();
				out.close();
				out = null;
			}
		} catch (IOException e) {
			LOG.info("IOException, " + e.getMessage());
			err(LOG, e);
		}
	}

	public void setInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {
		GameDaoImpl dao = null;
		GameServiceImpl service = null;
		Map<String, Object> tmpMap = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			if (req.getParameter("accId") != null && req.getParameter("tokenId") != null && req.getParameter("role") != null) {
				long accIdInPost = Long.parseLong(req.getParameter("accId"));
				String role = req.getParameter("role");
				if (checkLoginSec(req, res)) {
					dao = new GameDaoImpl();
					service = new GameServiceImpl();
					service.setDao(dao);
					if (service.updateRolePuncheGameAccInfo(accIdInPost, role)) {
						tmpMap.put("message", "success");
					} else {
						tmpMap.put("message", "fail");
					}
					tmpMap.put("tokenId", "success");
				} else {
					tmpMap.put("tokenId", "fail");
				}
			} else {
				tmpMap.put("tokenId", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			PrintWriter out = res.getWriter();
			JSONObject responseJSONObject = new JSONObject(tmpMap);
			out.println(responseJSONObject);
			responseJSONObject = null;
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
		}
	}

	public void getInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {

		GameDaoImpl dao = null;
		GameServiceImpl service = null;
		Map<String, Object> tmpMap = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			if (req.getParameter("accId") != null && req.getParameter("tokenId") != null) {
				long accIdInPost = Long.parseLong(req.getParameter("accId"));
				String tokenIdInPost = req.getParameter("tokenId");
				dao = new GameDaoImpl();
				service = new GameServiceImpl();
				service.setDao(dao);
				if (service.checkMemberTokenTimeOutNoUpdate(accIdInPost, tokenIdInPost)) {
					List<Map<String, Object>> tmpList = null;
					tmpList = service.getPlayer(accIdInPost);
					if (tmpList.size() == 1) {

						BigDecimal balance = new BigDecimal("0");
						balance = (BigDecimal) tmpList.get(0).get("balance");

						tmpMap.put("accName", tmpList.get(0).get("accName"));
						tmpMap.put("balance", "" + balance);
					}

					tmpList = service.getPlayerRole(accIdInPost);
					if (tmpList.size() == 1) {
						tmpMap.put("role", tmpList.get(0).get("role"));
					}

					tmpMap.put("tokenId", "success");
				} else {
					tmpMap.put("tokenId", "fail");
				}
			} else {
				tmpMap.put("tokenId", "fail");
			}

		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			err(LOG, e);
		} finally {
			PrintWriter out = res.getWriter();
			JSONObject responseJSONObject = new JSONObject(tmpMap);
			out.println(responseJSONObject);
			responseJSONObject = null;
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
		}
	}

	public void checkAccTokenTimeOut(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		BaseService service = null;
		BaseDao dao = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("accId") != null && req.getParameter("tokenId") != null && !"".equals(req.getParameter("accId").toString())
					&& !"".equals(req.getParameter("tokenId").toString())) {
				long accId = Long.parseLong(req.getParameter("accId").toString());
				String tokenId = req.getParameter("tokenId");
				service = new BaseService();
				dao = new BaseDao();
				service.setDao(dao);
				if (service.checkMemberTokenTimeOut(tokenId, accId)) {
					tmpMap.put("tokenId", "success");
				} else {
					tmpMap.put("tokenId", "fail");
				}
			} else {
				tmpMap.put("tokenId", "fail");
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			err(LOG, e);
		} finally {
			PrintWriter out = res.getWriter();
			JSONObject responseJSONObject = new JSONObject(tmpMap);
			out.println(responseJSONObject);
			responseJSONObject = null;
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
		}

	}

}
