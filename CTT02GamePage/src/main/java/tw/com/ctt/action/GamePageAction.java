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

@WebServlet(name = "GamePage", urlPatterns = { "/GamePage" })

/**
 * <h1>GamePageAction</h1> The action has three major function. 1. set/get user
 * info of players. 2. check account is alive or not by tokenId. 3. get the game
 * page for i-frame.
 * 
 * Important: because the game page doesnt connect to websocket server if player
 * on the "main page" or the "select role page" so we need to create this action
 * to handle some simple request like choice a role, query basic info of account
 * etc...
 * 
 * but if the player select a room to play, those info will not access by this
 * action, the websocket server will handle all of those infos.
 *
 * @author Quanto Lin
 * @version 1.0
 * @since 2018-08-31
 */
public class GamePageAction extends BaseAction {

	private static final long serialVersionUID = 1135893114919005938L;
	private static final Logger LOG = LogManager.getLogger(GamePageAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = { "/GamePage", "/GamePage!setInfo", "/GamePage!getInfo", "/GamePage!checkAccTokenTimeOut" };
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = {};
	private String[] authURL = {};

	public GamePageAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("GamePageAction start");
	}

	public boolean checkLogin() {
		return true;
	}

	/**
	 * main function to service the request without any directed function name. and
	 * it will response the game page html file.
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void main(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			String tmpHtml = "";
			String accIdInPost = req.getParameter("accIdGame");
			String tokenIdInPost = req.getParameter("tokenIdGame");
			if (checkLoginSec(req, res)) {
				PrintWriter out = res.getWriter();
				if (true) {
					String filename = "/WEB-INF/Game/index2.html";
					ServletContext context = req.getServletContext();
					InputStream is = context.getResourceAsStream(filename);
					BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
					String sCurrentLine;
					while ((sCurrentLine = br.readLine()) != null) {
						tmpHtml += "\n" + sCurrentLine;
					}
				} else {
					tmpHtml = tw.com.ctt.util.Configuration.GAME_PAGE_HTML;
				}
				tmpHtml = tmpHtml.replace("$1", accIdInPost);
				tmpHtml = tmpHtml.replace("$2", tokenIdInPost);
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

	/**
	 * when player select a role, game page will sent a request to setInfo, and will
	 * update the role info of player.
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void setInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {
		GameDaoImpl dao = null;
		GameServiceImpl service = null;
		Map<String, Object> tmpMap = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			if (req.getParameter("accId") != null && req.getParameter("tokenId") != null && req.getParameter("role") != null) {
				long accIdInPost = Long.parseLong(req.getParameter("accId"));
				String tokenIdInPost = req.getParameter("tokenId");
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

	/**
	 * when player in main page, game page need to query player's basic info by this
	 * function.
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
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

	/**
	 * check tokenId is available or not.
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */

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
