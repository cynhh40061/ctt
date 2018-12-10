package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
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

import tw.com.ctt.dao.impl.QueryAndCanclebetOrderDaoImpl;
import tw.com.ctt.service.impl.QueryAndCanclebetOrderServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "BetOrderQuery", urlPatterns = { "/BetOrderQuery" })
public class QueryAndCanclebetOrderAction extends BaseAction {

	private static final long serialVersionUID = -5390951620012326545L;
	private static final Logger LOG = LogManager.getLogger(QueryAndCanclebetOrderAction.class.getName());

	public String[] loginCheckURL = { "/BetOrderQuery!queryOrders" };
	public String[] loginCheckNoUpdateURL = {};
	public String[] extraURL = {};
	public String[] authURL = {};

	public QueryAndCanclebetOrderAction() {
		super();
		super.loginCheckURL = loginCheckURL;
		super.loginCheckNoUpdateURL = loginCheckNoUpdateURL;
		super.extraURL = extraURL;
		super.authURL = authURL;
		initURLs();
	}

	public boolean checkLogin() {
		return true;
	}

	public void queryOrders(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("queryOrders");
		Map<String, Object> tmpMap = null;
		long accId;
		QueryAndCanclebetOrderServiceImpl service = null;
		QueryAndCanclebetOrderDaoImpl dao = null;
		int type = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new QueryAndCanclebetOrderServiceImpl();
			dao = new QueryAndCanclebetOrderDaoImpl();
			service.setDao(dao);

			accId = req.getParameter("accId") != null ? Integer.parseInt(req.getParameter("accId").toString()) : 0;
			type = req.getParameter("type") != null ? Integer.parseInt(req.getParameter("type").toString()) : 0;

			if (type == 1) {// mid
				tmpMap.put("BetOrderInfo", service.searchMidBetOrder(accId));
				tmpMap.put("type", 1);
			} else if (type == 2) {// main
				tmpMap.put("BetOrderInfo", service.searchMainBetOrder(accId));
				tmpMap.put("type", 2);
			}
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));
			tmpMap.put("tokenId", "success");
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
