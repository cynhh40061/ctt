package tw.com.ctt.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import static tw.com.ctt.constant.CommandConstant.*;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.impl.BaseDao;
import tw.com.ctt.service.impl.BaseService;
import tw.com.ctt.util.AESUtil;
import tw.com.ctt.util.ShowLog;

// Extend HttpServlet class
@WebServlet(name = "Default", urlPatterns = { "/Default" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class BaseAction extends HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 7018888313281618940L;
	private static final Logger LOG = LogManager.getLogger(BaseAction.class.getName());
	public final String META_PROTERTIES = "meta";
	public final String LANG_TW = "zh_TW";
	public final String LANG_CN = "zh_CN";
	public final String LANG_US = "en_US";
	public final String ACTION_METHOD = "theActionMethod";// Action!Method
	public final String FORM_METHOD = "theFormMethod";// get, post
	protected boolean isDMI = false;
	public final String FLD_PWD = "pwd";
	/* no need to check anything */
	public String[] loginURL = {};
	/* only for check */
	public String[] loginCheckURL = {};
	/* check tokenId but no update tokenId */
	public String[] loginCheckNoUpdateURL = {};
	/* must be logined, but not need to check auth */
	public String[] extraURL = {};
	/* must be logined, and need to check auth */
	public String[] authURL = {};

	public static final String KEY = "cttcttcttquanto!";
	public ServletConfig config;
	public long ACC_ID;
	public String IP;

	public BaseAction() {
		super();
		initURLs();
	}

	public String[] StringArrayAdd(String[] a, String[] b) {
		if (a == null) {
			a = new String[] {};
		}
		if (b == null) {
			b = new String[] {};
		}
		Set<String> dataSet = new LinkedHashSet<String>();
		Collections.addAll(dataSet, a);
		Collections.addAll(dataSet, b);
		return dataSet.toArray(new String[dataSet.size()]);
	}

	/**
	 * show error log
	 * 
	 * @param log
	 * @param e
	 */
	public void err(Logger log, Exception e) {
		log.error(e.getMessage());
		StringBuilder errSB = new StringBuilder();
		for (StackTraceElement ste : e.getStackTrace()) {
			errSB.append("\n" + ste.toString());
			ste = null;
		}
		log.error(errSB.toString() + "\n");
		errSB.setLength(0);
		errSB = null;
	}

	/**
	 * check HTTP method, if is get method, then forward error page
	 * 
	 * @param req
	 * @param res
	 */
	public void checkGET(HttpServletRequest req, HttpServletResponse res) {
		if ("get".equals(req.getAttribute(FORM_METHOD))) {
			LOG.error("checkGET() is get! Not allow!!!");
			try {
				req.getRequestDispatcher("404.xhtml").forward(req, res);
			} catch (ServletException e) {
				LOG.info("ServletException, " + e.getMessage());
				err(LOG, e);
			} catch (IOException e) {
				LOG.info("IOException, " + e.getMessage());
				err(LOG, e);
			}
		}
	}

	/**
	 * default method for no DMI
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void main(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// interFace default method
		try {
			req.getRequestDispatcher("404.xhtml").forward(req, res);
		} catch (ServletException e) {
			LOG.info("ServletException, " + e.getMessage());
			err(LOG, e);
		} catch (IOException e) {
			LOG.info("IOException, " + e.getMessage());
			err(LOG, e);
		}
	}

	/**
	 * init request & response setting
	 * 
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void initREQ(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		req.getSession().setAttribute("IP", getIpAddr(req));
		res.setContentType("text/html;charset=UTF-8");
		LOG.debug("\n" + req.getRequestURI() + "\t#" + req.getMethod());
		LOG.debug("\n" + req.getContentType());
		if (req.getParameterMap().size() > 0) {
			String param = "";
			for (String str : req.getParameterMap().keySet()) {
				param += ";" + str + ":" + req.getParameter(str);
			}
			LOG.debug("\n" + param.substring(1, param.length() < 200 ? param.length() : 200));
			param = "";
			param = null;
		}
		BufferedReader br = null;
		StringBuilder sb = null;
		String s1 = null;
		try {
			br = req.getReader();
			sb = new StringBuilder();
			s1 = null;
			while ((s1 = br.readLine()) != null && s1.length() != 0) {
				sb.append(s1);
			}
			if (sb.length() > 0) {
				String[] tmpList = sb.toString().split("&");
				for (String str : tmpList) {
					if (str.indexOf("=") != -1) {
						try {
							String[] tmpList1 = str.split("=");
							String tt = "";
							if (tmpList1.length == 2 && tmpList1[1] != null && tmpList1[1].trim().length() > 0) {
								tt = java.net.URLDecoder.decode(tmpList1[1].replaceAll("%20", "+"), "UTF-8").trim();
							}
							LOG.debug(tmpList1[0] + ":\t" + tt);
							req.setAttribute(tmpList1[0], tt);
							tmpList1 = null;
							tt = "";
							tt = null;
						} catch (Exception e) {
							LOG.debug("java.net.URLDecoder.decode (right of =) is ERROR!!!\n" + str);
						}
					}
				}
				tmpList = null;
			}
		} catch (Exception e) {
			LOG.debug("\ninitREQ_Exception=" + e.getMessage());
			err(LOG, e);
		} finally {
			br = null;
			sb.setLength(0);
			sb = null;
			s1 = "";
			s1 = null;
		}
	}

	/**
	 * url: action!method => call action.method (Dynamic method Invoc)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		initREQ(req, res);
		req.setAttribute(FORM_METHOD, "get");
		doDispatch(req, res);
		return;
	}

	/**
	 * url: action!method => call action.method (Dynamic method Invoc)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		initREQ(req, res);
		req.setAttribute(FORM_METHOD, "post");
		doDispatch(req, res);
		return;
	}

	/**
	 * url: action!method => call action.method (Dynamic method Invoc)
	 */
	public void doDispatch(HttpServletRequest req, HttpServletResponse res) {
		isDMI = false;
		if (req.getAttribute(ACTION_METHOD) != null) {
			try {
				Method[] methods = this.getClass().getDeclaredMethods();
				for (Method method : methods) {
					if (method.getName().equals(req.getAttribute(ACTION_METHOD).toString()) && method.getParameterTypes().length == 2
							&& method.getParameters()[0].getType() == HttpServletRequest.class
							&& method.getParameters()[1].getType() == HttpServletResponse.class) {
						isDMI = true;
						method.invoke(this, req, res);
						return;
						// break;
					}
				}
				isDMI = false;
				main(req, res);
				return;
			} catch (SecurityException e) {
				LOG.info("SecurityException, " + e.getMessage());
				err(LOG, e);
			} catch (IllegalAccessException e) {
				LOG.info("IllegalAccessException, " + e.getMessage());
				err(LOG, e);
			} catch (IllegalArgumentException e) {
				LOG.info("IllegalArgumentException, " + e.getMessage());
				err(LOG, e);
			} catch (InvocationTargetException e) {
				LOG.info("InvocationTargetException, " + e.getMessage());
				err(LOG, e);
			} catch (Exception e) {
				LOG.info("Exception, " + e.getMessage());
				err(LOG, e);
			}
		} else {
			isDMI = false;
			try {
				main(req, res);
			} catch (Exception e) {
				LOG.info("Exception, " + e.getMessage());
				err(LOG, e);
			}
			return;
		}
	}

	public boolean checkLoginSec(HttpServletRequest req, HttpServletResponse res) {
		boolean isLogin = false;
		long userId = 0;
		String tokenId = null;
		BaseService service = null;
		BaseDao dao = null;

		if (CommandConstant.CHECK_URL) {
			userId = req.getSession().getAttribute("accId") != null ? Long.parseLong(req.getSession().getAttribute("accId").toString()) : 0;
			tokenId = req.getParameter("tokenId") != null ? req.getParameter("tokenId") : "";
		} else {
			userId = req.getParameter("accId") != null ? Long.parseLong(req.getParameter("accId").toString()) : 0;
			tokenId = req.getParameter("tokenId") != null ? req.getParameter("tokenId") : "";
			if (userId == 0 || tokenId.isEmpty() || tokenId.trim().length() == 0) {
				userId = req.getParameter("accIdGame") != null ? Long.parseLong(req.getParameter("accIdGame").toString()) : 0;
				tokenId = req.getParameter("tokenIdGame") != null ? req.getParameter("tokenIdGame") : "";
			}
		}

		if (userId > 0 && !tokenId.isEmpty() && tokenId.trim().length() > 0) {
			service = new BaseService();
			dao = new BaseDao();
			service.setDao(dao);
			if (service.checkManagerTokenTimeOut(tokenId, userId) || service.checkMemberTokenTimeOut(tokenId, userId)) {
				isLogin = true;
			}

		} else {
			LOG.debug("accId is null and tokenId is null ");
		}

		if (dao != null) {
			dao.close();
			dao = null;
		}
		if (service != null) {
			service.close();
			service = null;
		}

		LOG.debug("isLogin" + isLogin);

		return isLogin;
	}

	/**
	 * URL force update CommandConstant.BLACK_WHITE_LIST
	 */
	public void checkServerIPs(HttpServletRequest req, HttpServletResponse res) {
		LOG.info("BaseAction.checkServerIPs start");
		BaseDao dao = null;
		boolean todo = false;
		Map<String, Object> tmpMap = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			tmpMap = new HashMap<String, Object>();
			dao = new BaseDao();
			todo = dao.checkServerIPs();
			tmpMap.put("result", todo ? "1" : "0");
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				res.setContentType("application/json;charset=UTF-8");
				responseJSONObject = new JSONObject(tmpMap);
				out = res.getWriter();
				out.println(responseJSONObject);
				// out.flush();
				LOG.debug(responseJSONObject.toString());
			}
		} catch (Exception e) {
			LOG.error("BaseAction.checkServerIPs.Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (dao != null) {
				dao.close();
				dao = null;
			}
			todo = false;
			tmpMap = null;
			responseJSONObject = null;
			out = null;
		}
		return;
	}

	@Override
	public void init() throws ServletException {
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		this.init();
	}

	public Map<String, String> checkLoginMember(long accId, String tokenId) {
		Map<String, String> isLogin = null;
		BaseService service = null;
		BaseDao dao = null;
		if (accId > 0 && tokenId != null && !"".equals(tokenId)) {
			service = new BaseService();
			dao = new BaseDao();
			service.setDao(dao);
			isLogin = service.checkMemberTokenTimeOut2(tokenId, accId);
		}
		if (dao != null) {
			dao.close();
			dao = null;
		}
		if (service != null) {
			service.close();
			service = null;
		}
		return isLogin;
	}

	@Override
	public void destroy() {
		System.gc();
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		HttpServletRequest request;
		HttpServletResponse response;
		if (!(req instanceof HttpServletRequest && res instanceof HttpServletResponse)) {
			throw new ServletException("non-HTTP request or response");
		}
		request = (HttpServletRequest) req;
		response = (HttpServletResponse) res;

		CommandConstant.SERVER_NAME = (CommandConstant.SERVER_NAME == null || CommandConstant.SERVER_NAME.isEmpty()
				|| CommandConstant.SERVER_NAME.trim().length() == 0) ? "" + req.getLocalName() : CommandConstant.SERVER_NAME;

		CommandConstant.SERVER_PORT = (CommandConstant.SERVER_PORT == null || CommandConstant.SERVER_PORT.isEmpty()
				|| CommandConstant.SERVER_PORT.trim().length() == 0) ? "" + req.getLocalPort() : CommandConstant.SERVER_PORT;

		CommandConstant.SERVER_IP = (CommandConstant.SERVER_IP == null || CommandConstant.SERVER_IP.isEmpty()
				|| CommandConstant.SERVER_IP.trim().length() == 0) ? "" + req.getLocalAddr() : CommandConstant.SERVER_IP;

		CommandConstant.SERVER_CONTEXT = (CommandConstant.SERVER_CONTEXT == null || CommandConstant.SERVER_CONTEXT.isEmpty()
				|| CommandConstant.SERVER_CONTEXT.trim().length() == 0) ? "" + req.getServletContext() : CommandConstant.SERVER_CONTEXT;

		long accId = request.getSession().getAttribute("accId") != null && !"".equals(request.getSession().getAttribute("accId").toString())
				? accIdToLong(request.getSession().getAttribute("accId").toString())
				: 0;
		String actionUrl = (!"".equals(request.getContextPath()) ? request.getRequestURI().split(request.getContextPath())[1]
				: request.getRequestURI())
				+ (request.getAttribute(ACTION_METHOD) != null && !"".equals(request.getAttribute(ACTION_METHOD))
						? "!" + request.getAttribute(ACTION_METHOD)
						: "");

		LOG.debug(actionUrl);

		if (CommandConstant.CHECK_URL) {
			BaseService service = new BaseService();
			BaseDao dao = new BaseDao();
			service.setDao(dao);
			Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();

			if (LOGIN_CHECK_URL.contains(actionUrl)) {
				service(request, response);
				return;
			} else if (ALLOW_NO_LOGIN_URLS.contains(actionUrl)) {
				service(request, response);
				return;
			} else if (EXTRA_URL.contains(actionUrl)) {
				if (!checkLoginSec(request, response)) {
					tmpMap.put("tokenId", "fail");
					responseJSON(response, tmpMap);
					return;
				}
				service(request, response);
				return;
			} else if (AUTH_URL.contains(actionUrl)) {
				if (!checkLoginSec(request, response)) {
					tmpMap.put("tokenId", "fail");
					responseJSON(response, tmpMap);
					return;
				}
				if (!checkAuth(accId, actionUrl)) {
					tmpMap.put("auth", "fail");
					responseJSON(response, tmpMap);
					return;
				}
				service(request, response);
				return;
			} else {
				tmpMap.put("url", "fail");
				responseJSON(response, tmpMap);
				return;
			}
		} else {
			service(request, response);
			return;
		}
	}

	private void responseJSON(HttpServletResponse response, Map<String, Object> map) {
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			response.setContentType("application/json;charset=UTF-8");
			responseJSONObject = new JSONObject(map);
			out = response.getWriter();
			out.println(responseJSONObject);
		} catch (IOException e) {
			e.printStackTrace();
			err(LOG, e);
		} finally {
			if (map != null) {
				map.clear();
				map = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
		return;
	}

	/**
	 * Common method used to strip unwanted characters from form input strings
	 * 
	 * @param inputStr
	 * @return Returns the cleaned string
	 */
	protected String escapeFormInput(String inputStr) {
		return StringEscapeUtils.escapeHtml4(inputStr);
	}

	/**
	 * to trim() input string
	 * 
	 * @param inputStr
	 * @return
	 */
	protected String trimString(String inputStr) {
		if (inputStr != null) {
			return inputStr.trim();
		} else {
			return null;
		}
	}

	/**
	 * check inputStr is null or empty or ""
	 * 
	 * @param inputStr
	 * @return
	 */
	public static boolean checkStringEmpty(String inputStr) {
		if (inputStr == null || inputStr.isEmpty() || "".equals(inputStr.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check inputList is null or empty or size=0
	 * 
	 * @param inputList
	 * @return
	 */
	public static boolean checkListEmpty(@SuppressWarnings("rawtypes") List inputList) {
		if (inputList == null || inputList.isEmpty() || inputList.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check inputMap is null or empty or size=0
	 * 
	 * @param inputMap
	 * @return
	 */
	public static boolean checkMapEmpty(@SuppressWarnings("rawtypes") Map inputMap) {
		if (inputMap == null || inputMap.isEmpty() || inputMap.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check inputArray is null or empty(length=0)
	 * 
	 * @param inputArray
	 * @return
	 */
	public static boolean checkArrayEmpty(String[] inputArray) {
		if (inputArray == null || inputArray.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * i18n
	 * 
	 * @param key
	 * @param local
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected String getMessageByBundle(String key, Locale local) throws UnsupportedEncodingException {
		ResourceBundle rs = ResourceBundle.getBundle(META_PROTERTIES, local);
		return rs.getString(key);
		// return new String(rs.getString(key).getBytes("ISO-8859-1"), "UTF-8");
	}

	/**
	 * i18n zh_TW, zh_CN, en_US;this.LANG_TW,this.LANG_CN,this.LANG_US
	 * 
	 * @param key
	 * @param lang
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected String getMessageByBundle(String key, String lang) throws UnsupportedEncodingException {
		Locale locale = new Locale(lang.split("_")[0], lang.split("_")[1]);
		return getMessageByBundle(key, locale);
	}

	/**
	 * public boolean checkAuth(long accId, String url, PortionAuthServiceImpl
	 * service, int acc_level_type) { boolean result =
	 * service.getAuthFunction(accId, url, acc_level_type); return result; }
	 */
	public boolean checkAuth(long accId, String url) {
		BaseService service = new BaseService();
		BaseDao dao = new BaseDao();
		service.setDao(dao);
		boolean todo = false;

		if (CommandConstant.CHECK_URL) {
			todo = service.getAuthFunction(accId, url);
		} else {
			todo = true;
		}

		if (dao != null) {
			dao.close();
			dao = null;
		}
		if (service != null) {
			service.close();
			service = null;
		}
		return todo;

	}

	public String getIpAddr(HttpServletRequest req) {
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多級代理，那麽取第一個ip為客戶ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}

	@SuppressWarnings("static-access")
	public Map<String, Object> getRequestData(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		Enumeration<String> params = req.getParameterNames();
		StringBuilder sb = new StringBuilder();
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			sb.append("\n" + paramName + " = " + req.getParameter(paramName));
			if (paramName.equals(FLD_PWD)) {
				String encrypted = AESUtil.encrypt_CBC(req.getParameter(paramName).trim(), KEY);
				map.put(paramName, encrypted);
			} else {
				if (null == req.getParameter(paramName) || req.getParameter(paramName).length() == 0
						|| "".equals(req.getParameter(paramName).trim())) {
					map.put(paramName, "");
				} else {
					map.put(paramName, req.getParameter(paramName).trim());
				}
			}
			paramName = "";
			paramName = null;
		}
		params = null;
		this.LOG.debug(sb.toString());
		sb.setLength(0);
		sb = null;
		LOG.debug("getRequestData_map===" + map.toString());
		return map;
	}

	public static String clearNum(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			str = str.trim();
			String tmpStr = "";
			Pattern pattern = Pattern.compile("^\\d");
			for (int i = 0; i < str.length(); i++) {
				String tmpC = str.substring(i, i + 1);
				if (pattern.matcher(tmpC).find()) {
					tmpStr += tmpC;
				}
				tmpC = null;
				tmpC = "";
			}
			str = tmpStr;
			tmpStr = "";
			tmpStr = null;
			pattern = null;
			return str;
		}
	}

	public static String checkDateTimeFormat(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			str = str.trim().replaceAll("-", "/");
			Pattern pattern = Pattern.compile(
					"^(?:(?!0000)[0-9]{4}/(?:(?:0[1-9]|1[0-2])/(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])/(?:29|30)|(?:0[13578]|1[02])/31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)/02/29)[ ]([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
			if (pattern.matcher(str).find()) {
				return str;
			} else {
				return "";
			}
		}
	}

	public static String checkDateFormat(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			str = str.trim().replaceAll("-", "/");
			Pattern pattern = Pattern.compile(
					"^(?:(?!0000)[0-9]{4}/(?:(?:0[1-9]|1[0-2])/(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])/(?:29|30)|(?:0[13578]|1[02])/31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)/02/29)$");
			if (pattern.matcher(str).find()) {
				return str;
			} else {
				return "";
			}
		}
	}

	public static String checkAccount(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			str = str.trim();
			Pattern pattern = Pattern.compile("^[a-z][a-z0-9]*$");
			if (pattern.matcher(str).find()) {
				return str;
			} else {
				return "";
			}
		}
	}

	public static String checkPWD(String str) {
		String PWD = str.trim();
		if (PWD == null || PWD.trim().length() == 0) {
			return "";
		} else {
			Pattern pattern = Pattern.compile("^[\\x21-\\x7E]+$");
			if (pattern.matcher(PWD).find()) {
				return PWD;
			} else {
				return "";
			}
		}
	}

	public static String checkNameOut(String str) {
		String name = str.trim();
		if (name == null || name.trim().length() == 0) {
			return "";
		} else {
			Pattern pattern = Pattern.compile("[^\\x00-\\x2C\\x2E-\\x2F\\x3A-\\x40\\x5B-\\x5E\\x60\\x7B-\\x7F]");
			if (pattern.matcher(name).find()) {
				return name;
			} else {
				return "";
			}
		}
	}

	public static String checkNumber(String str) {
		String name = str.trim();
		if (name == null || name.trim().length() == 0) {
			return "";
		} else {
			Pattern pattern = Pattern.compile("[^0-9]");
			if (pattern.matcher(name).find()) {
				return name;
			} else {
				return "";
			}
		}
	}

	public static String checkENandNumOnly(String str) {
		str = str.trim();
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			Pattern pattern = Pattern.compile("^[\\d|a-zA-Z]+$");
			if (pattern.matcher(str).find()) {
				return str;
			} else {
				return "";
			}
		}
	}

	public long accIdToLong(String str) {
		String tmpStr = "";
		if (str != null && str.length() > 0) {
			tmpStr = clearNum(str);
			if (tmpStr.length() > 0 && tmpStr != null && !"".equals(tmpStr)) {
				return Long.parseLong(tmpStr);
			}
		}
		return 0;
	}

	public static List<String> checkMapKey(String strs[], Map<String, Object> map) {
		List<String> chklist = null;
		if ((strs != null && strs.length > 0) && (map != null && map.size() > 0)) {
			chklist = new ArrayList<String>();
			for (String str : strs) {
				if (map.keySet().contains(str)) {
					try {
						if (map.get(str) == null || "".equals(map.get(str).toString().trim())) {
							chklist.add(str);
						}
					} catch (Exception e) {
						LOG.debug("\ncheckMapKey_Exception=" + e.getMessage());
						ShowLog.err(LOG, e);
						chklist.add(str);
					}
				} else {
					LOG.debug("\ncheckMapKey: key not find! " + str);
					chklist.add(str);
				}
				str = "";
				str = null;
			}
		} else {
			LOG.debug("\ncheckMapKey: strs is null || map is null!");
		}
		return chklist;
	}

	@SuppressWarnings("unused")
	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		LOG.debug("Part Header = {0}", partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

	protected boolean constainArray(String[] list, String key) {
		if (list == null || list.length == 0 || key.isEmpty() || key.trim().length() == 0) {
			return false;
		}
		return ((new ArrayList<String>(Arrays.asList(list))).contains(key));
	}

	public void initURLs() {
		ALLOW_NO_LOGIN_URLS = initConstant(ALLOW_NO_LOGIN_URLS, loginURL);
		LOGIN_CHECK_URL = initConstant(LOGIN_CHECK_URL, loginCheckURL);
		LOGIN_CHECK_NO_UPDATE_URL = initConstant(LOGIN_CHECK_NO_UPDATE_URL, loginCheckNoUpdateURL);
		EXTRA_URL = initConstant(EXTRA_URL, extraURL);
		AUTH_URL = initConstant(AUTH_URL, authURL);
	}

	private static CopyOnWriteArraySet<String> initConstant(CopyOnWriteArraySet<String> set, String[] array) {
		if (set == null) {
			set = new CopyOnWriteArraySet<String>();
		}
		if (array != null && array.length > 0) {
			set.addAll(new CopyOnWriteArraySet<String>(Arrays.asList(array)));
		}
		return set;
	}

	/**
	 * URL force update CommandConstant.BLACK_WHITE_LIST
	 */
	public void checkIPs(HttpServletRequest req, HttpServletResponse res) {
		LOG.info("BaseAction.checkIPs start");
		BaseDao dao = null;
		boolean todo = false;
		Map<String, Object> tmpMap = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			tmpMap = new HashMap<String, Object>();
			dao = new BaseDao();

			todo = dao.checkBlackWhiteIPs();

			tmpMap.put("result", todo ? "1" : "0");
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				res.setContentType("application/json;charset=UTF-8");
				responseJSONObject = new JSONObject(tmpMap);
				out = res.getWriter();
				out.println(responseJSONObject);
				// out.flush();
				LOG.debug(responseJSONObject.toString());
			}
		} catch (Exception e) {
			LOG.error("BaseAction.checkIPs.Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (dao != null) {
				dao.close();
				dao = null;
			}
			todo = false;
			tmpMap = null;
			responseJSONObject = null;
			out = null;
		}
		return;
	}

	/**
	 * encode string to URL code
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeURL(String str) {
		if (str == null || str.isEmpty() || str.trim().length() == 0) {
			return "";
		}
		try {
			return java.net.URLEncoder.encode(str, "UTF-8").trim().replaceAll("+", "%20");
		} catch (UnsupportedEncodingException e) {
			LOG.debug("encodeURL ERROR:\n" + str);
			ShowLog.err(LOG, e);
		}
		return "";
	}

	/**
	 * decode URL code to string
	 * 
	 * @param str
	 * @return
	 */
	public static String decodeURL(String str) {
		if (str == null || str.isEmpty() || str.trim().length() == 0) {
			return "";
		}
		try {
			return java.net.URLDecoder.decode(str.replaceAll("%20", "+"), "UTF-8").trim();
		} catch (UnsupportedEncodingException e) {
			LOG.debug("encodeURL ERROR:\n" + str);
			ShowLog.err(LOG, e);
		}
		return "";
	}

	/**
	 * get value for request.getParameter or request.getAttribute
	 * 
	 * @param req
	 * @param name
	 * @return
	 */
	public String getReqValue(HttpServletRequest req, String name) {
		if (req == null || name == null || name.isEmpty() || name.trim().length() == 0) {
			return "";
		}
		try {
			if (req.getParameterMap().keySet().contains(name)) {
				return req.getParameter(name);
			}
			for (Enumeration<String> en = req.getAttributeNames(); en.hasMoreElements();) {
				String attribute = en.nextElement();
				if (attribute.equals(name)) {
					return "" + req.getAttribute(name).toString();
				}
			}
		} catch (Exception e) {
			LOG.debug("getReqValue ERROR:\t" + name);
			err(LOG, e);
		}
		return "";
	}

	/* 取得瀏覽器及系統資訊 */
	public void checkBrowser(HttpServletRequest req) {
		String browserDetails = req.getHeader("User-Agent");
		String userAgent = browserDetails;
		String user = userAgent.toLowerCase();
		String os = "";
		String browser = "";
		LOG.info("User Agent for the request is===>" + browserDetails);
		// =================OS=======================
		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "Windows";
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "Mac";
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix";
		} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "Android";
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		// ===============Browser===========================
		if (user.contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version")) {
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera"))
				browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			else if (user.contains("opr"))
				browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
		} else if (user.contains("chrome")) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1) || (user.indexOf("mozilla/4.7") != -1)
				|| (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
			// browser=(userAgent.substring(userAgent.indexOf("MSIE")).split("
			// ")[0]).replace("/", "-");
			browser = "Netscape-?";

		} else if (user.contains("firefox")) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("rv")) {
			browser = "IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
		} else {
			browser = "UnKnown, More-Info: " + userAgent;
		}
		LOG.info("Operating System======>" + os);
		LOG.info("Browser Name==========>" + browser);
	}
}
