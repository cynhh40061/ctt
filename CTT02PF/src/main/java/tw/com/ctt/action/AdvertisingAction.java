package tw.com.ctt.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.AdvertisingDaoImpl;
import tw.com.ctt.service.impl.AdvertisingServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "Advertising", urlPatterns = { "/Advertising" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class AdvertisingAction extends BaseAction {

	private static final long serialVersionUID = 3942899878349157608L;
	private static final Logger LOG = LogManager.getLogger(AdvertisingAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/Advertising!getAllPlatformId", "/Advertising!getAdData", "/Advertising!addAdData", "/Advertising!updateAdData",
			"/Advertising!deleteAdData", "/Advertising!checkAdPosition" };
	private String[] authURL = {};

	public AdvertisingAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("AdvertisingAction start");
	}

	public void main(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("AuthGroupAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial AdvertisingAction.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AD_checkAd(){\n");
			sb.append("    if(typeof authGroup==='undefined' || typeof authGroup != 'function' ){\n");
			sb.append("        checkJS('ad', 'AccountManage/ad.js');\n");
			sb.append("        ad();\n");
			sb.append("    } else {\n");
			sb.append("        ad();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AD_checkAd();\n");
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

	public void getAllPlatformId(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		AdvertisingServiceImpl service = null;
		AdvertisingDaoImpl dao = null;
		long accId = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new AdvertisingServiceImpl();
			dao = new AdvertisingDaoImpl();
			service.setDao(dao);
			accId = accIdToLong(req.getSession().getAttribute("accId").toString());

			tmpMap.put("platformData", service.getAllPlatformId(accId));
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

	public void getAdData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		AdvertisingServiceImpl service = null;
		AdvertisingDaoImpl dao = null;
		int platformId = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("platformId") != null && !"".equals(req.getParameter("platformId").toString())) {
				service = new AdvertisingServiceImpl();
				dao = new AdvertisingDaoImpl();
				service.setDao(dao);
				platformId = Integer.parseInt(req.getParameter("platformId").toString());

				tmpMap.put("adData", service.getAdData(platformId));
				LOG.debug(tmpMap.toString());
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

	public void addAdData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		AdvertisingServiceImpl service = null;
		AdvertisingDaoImpl dao = null;
		int platformId = 0;
		String adPosition = "";
		String imgText = "";
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("platformId") != null && !"".equals(req.getParameter("platformId").toString())
					&& req.getParameter("adPosition") != null && !"".equals(req.getParameter("adPosition").toString())
					&& req.getParameter("imgText") != null && !"".equals(req.getParameter("imgText").toString())) {
				service = new AdvertisingServiceImpl();
				dao = new AdvertisingDaoImpl();
				service.setDao(dao);
				platformId = Integer.parseInt(req.getParameter("platformId").toString());
				adPosition = req.getParameter("adPosition").toString();
				imgText = req.getParameter("imgText").toString();

				tmpMap.put("isSuccess", service.addAdData(platformId));

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

	public void updateAdData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		AdvertisingServiceImpl service = null;
		AdvertisingDaoImpl dao = null;
		int platformId = 0;
		String adPosition = "";
		String imgURL = "";
		String imgSRC = "";
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("platformId") != null && !"".equals(req.getParameter("platformId").toString()) && req.getParameter("imgSrc") != null
					&& !"".equals(req.getParameter("imgSrc").toString()) && req.getParameter("adPosition") != null
					&& !"".equals(req.getParameter("adPosition").toString()) && req.getParameter("imgUrl") != null) {
				service = new AdvertisingServiceImpl();
				dao = new AdvertisingDaoImpl();
				service.setDao(dao);
				platformId = Integer.parseInt(req.getParameter("platformId").toString());
				adPosition = req.getParameter("adPosition").toString();
				imgURL = req.getParameter("imgUrl").toString();
				imgSRC = req.getParameter("imgSrc").toString();

				tmpMap.put("isSuccess", service.updateAdData(platformId, adPosition, imgURL, imgSRC));

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

	public void deleteAdData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		AdvertisingServiceImpl service = null;
		AdvertisingDaoImpl dao = null;
		int platformId = 0;
		String adPosition = "";
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("platformId") != null && !"".equals(req.getParameter("platformId").toString())
					&& req.getParameter("adPosition") != null && !"".equals(req.getParameter("adPosition").toString())) {
				service = new AdvertisingServiceImpl();
				dao = new AdvertisingDaoImpl();
				service.setDao(dao);
				platformId = Integer.parseInt(req.getParameter("platformId").toString());
				adPosition = req.getParameter("adPosition").toString();

				tmpMap.put("isSuccess", service.deleteAdData(platformId, adPosition));

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

	public void checkAdPosition(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		AdvertisingServiceImpl service = null;
		AdvertisingDaoImpl dao = null;
		int platformId = 0;
		String adPosition = "";
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("platformId") != null && !"".equals(req.getParameter("platformId").toString())
					&& req.getParameter("adPosition") != null && !"".equals(req.getParameter("adPosition").toString())) {
				service = new AdvertisingServiceImpl();
				dao = new AdvertisingDaoImpl();
				service.setDao(dao);
				platformId = Integer.parseInt(req.getParameter("platformId").toString());
				adPosition = req.getParameter("adPosition").toString();
				boolean todo = service.checkAdPosition(platformId, adPosition);
				LOG.debug(todo);
				tmpMap.put("isCheckAdPosition", todo);

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

	public void getIframe() throws IOException {
		File img = null;
		try {
			// processRequest(REQ, RES);
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {

			img = null;
		}
		return;
	}

}
