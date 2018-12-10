package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.util.ShowLog;

/**
 * Using this action to queryKjTimeStatus it include the period, time and kj
 * data.
 * 
 * @author Quanto
 *
 */
@WebServlet(name = "KjInfo", urlPatterns = { "/KjInfo" })
public class QueryKjInfoAction extends BaseAction {

	private static final long serialVersionUID = -5390951620012326545L;
	private static final Logger LOG = LogManager.getLogger(QueryKjInfoAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = { "/KjInfo!queryKjTimeStatus" };
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = {};
	private String[] authURL = {};

	public QueryKjInfoAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("QueryKjInfoAction start");
	}

	public boolean checkLogin() {
		return true;
	}

	/**
	 * queryKjHistory to get the kj date and perion_num include 10 periods of kj
	 * date and all future period of each lottery.
	 * 
	 * @throws IOException
	 */
	public void queryKjTimeStatus(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("queryKjTimeStatus");
		Map<String, Object> tmpMap = null;
		String tokenId = null;
		long accId = 0;
		long lastUpdateTimeFromWeb = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null && req.getParameter("LUT") != null) {
				tokenId = req.getParameter("tokenId").toString();
				accId = accIdToLong(req.getParameter("accId").toString());
				lastUpdateTimeFromWeb = accIdToLong(req.getParameter("LUT").toString());
			} else if (req.getAttribute("tokenId") != null && req.getAttribute("accId") != null && req.getAttribute("LUT") != null) {
				tokenId = req.getAttribute("tokenId").toString();
				accId = accIdToLong(req.getAttribute("accId").toString());
				lastUpdateTimeFromWeb = accIdToLong(req.getAttribute("LUT").toString());
			} else {
				tmpMap.put("tokenId", "fail");
			}
			if (tokenId != null && accId != 0) {
				Map<String, String> accData = checkLoginMember(accId, tokenId);
				if ("0".equals(accData.get("tokenIdCheck"))) {
					tmpMap.put("tokenId", "fail");
				} else {
					if (lastUpdateTimeFromWeb != CommandConstant.DB_LAST_UPDATE_TIME_OF_KJ) {
						tmpMap.putAll(accData);
						tmpMap.put("KjTimeStatus", CommandConstant.KJ_TIME_AND_STATUS);
					}
					tmpMap.put("tokenId", "success");
					tmpMap.put("LUT", CommandConstant.DB_LAST_UPDATE_TIME_OF_KJ);
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
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
	}
}
