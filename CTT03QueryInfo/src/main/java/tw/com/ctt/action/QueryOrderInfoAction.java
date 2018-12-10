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

import tw.com.ctt.dao.impl.BetOrderDaoImpl;
import tw.com.ctt.service.impl.BetOrderServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "OrderInfo", urlPatterns = { "/OrderInfo" })
public class QueryOrderInfoAction extends BaseAction {

	private static final long serialVersionUID = -5390951620012326545L;
	private static final Logger LOG = LogManager.getLogger(QueryOrderInfoAction.class.getName());

	private String[] loginURL = {};
	public String[] loginCheckURL = { "/OrderInfo!queryOrders" };
	public String[] loginCheckNoUpdateURL = {};
	public String[] extraURL = {};
	public String[] authURL = {};

	public QueryOrderInfoAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("QueryOrderInfoAction start");
	}

	public boolean checkLogin() {
		return true;
	}

	public void queryOrders(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("queryOrders");
		Map<String, Object> tmpMap = null;
		long accId;
		BetOrderServiceImpl service = null;
		BetOrderDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				accId = accIdToLong(req.getParameter("accId").toString());
				service = new BetOrderServiceImpl(accId, getIpAddr(req));
				dao = new BetOrderDaoImpl();
				service.setDao(dao);
				tmpMap.put("BetOrderInfo", service.getBetOrderInfo(accId));
				tmpMap.put("tokenId", "success");
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
}
