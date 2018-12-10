package tw.com.ctt.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.impl.BaseDao;
import tw.com.ctt.dao.impl.BlackWhiteDaoImpl;
import tw.com.ctt.model.BlackWhiteBean;
import tw.com.ctt.service.impl.BaseService;
import tw.com.ctt.service.impl.BlackWhiteServiceImpl;
import tw.com.ctt.util.CommandUtil;
import tw.com.ctt.util.ShowLog;

/**
 * <p>
 * created date:2018/06/05 11:00<br>
 * modify date:2018/06/05 11:00<br>
 * BlackWhiteAction for manage black white list
 * 
 * @author paul <br>
 * @version 0.0.1 <br>
 */
@WebServlet(name = "BlackWhite", urlPatterns = { "/BlackWhite" })
public class BlackWhiteAction extends BaseAction {

	private static final long serialVersionUID = -3581809015529885702L;
	private static final Logger LOG = LogManager.getLogger(BlackWhiteAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/BlackWhite!showAll", "/BlackWhite!show", "/BlackWhite!update" };
	private String[] authURL = { "/BlackWhite" };

	public BlackWhiteAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("BlackWhiteAction start");
	}

	/**
	 * default run method
	 */
	public void main(HttpServletRequest req, HttpServletResponse res) {
		LOG.debug("BlackWhiteAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new HashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_BW_checkBlackWhite(){\n");
			sb.append("    if(typeof blackWhite==='undefined' || typeof blackWhite != 'function' ){\n");
			sb.append("        checkJS('blackWhite', 'ctt/blackWhite.js');\n");
			sb.append("    } else {\n");
			sb.append("        blackWhite();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_BW_checkBlackWhite();\n");
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
			LOG.error("BlackWhiteAction.main.Exception, " + e.getMessage());
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

	// /*
	// * (non-Javadoc)
	// *
	// * @see tw.com.ctt.action.BaseAction#checkLogin()
	// */
	// public boolean checkLogin() {

	// LOG.info(req.getLocalAddr());// 127.0.0.1
	// LOG.info(req.getLocalName());// 127.0.0.1
	// LOG.info(req.getContextPath());// CTT02PF
	// LOG.info(req.getRequestURI());// CTT02PF/BlackWhite
	// LOG.info(req.getRequestURL());// http://127.0.0.1:8080/CTT02PF/BlackWhite
	// LOG.info(req.getAttribute(ACTION_METHOD));// checkIPs
	// LOG.info(req.getRequestURI().split(req.getContextPath())[1]
	// + (req.getAttribute(ACTION_METHOD) != null &&
	// !"".equals(req.getAttribute(ACTION_METHOD)) ? "!" +
	// req.getAttribute(ACTION_METHOD)
	// : ""));// /BlackWhite!showAll
	// LOG.info(!"".equals(req.getContextPath()) ?
	// req.getRequestURI().split(req.getContextPath())[1]
	// + (req.getAttribute(ACTION_METHOD) != null &&
	// !"".equals(req.getAttribute(ACTION_METHOD)) ? "!" +
	// req.getAttribute(ACTION_METHOD)
	// : "")
	// : req.getRequestURI() + (req.getAttribute(ACTION_METHOD) != null &&
	// !"".equals(req.getAttribute(ACTION_METHOD))
	// ? "!" + req.getAttribute(ACTION_METHOD)
	// : ""));
	// LOG.info((!"".equals(req.getContextPath()) ?
	// req.getRequestURI().split(req.getContextPath())[1] : req.getRequestURI())
	// + (req.getAttribute(ACTION_METHOD) != null &&
	// !"".equals(req.getAttribute(ACTION_METHOD)) ? "!" +
	// req.getAttribute(ACTION_METHOD)
	// : ""));
	// return true;
	// }

	// /**
	// * URL force update CommandConstant.BLACK_WHITE_LIST
	// */
	// public void checkIPs() {
	// LOG.info("BlackWhiteAction.checkIPs start");
	// BaseDao dao = null;
	// boolean todo = false;
	// Map<String, Object> tmpMap = null;
	// JSONObject responseJSONObject = null;
	// PrintWriter out = null;
	// try {
	// tmpMap = new HashMap<String, Object>();
	// dao = new BaseDao();
	// todo = dao.checkBlackWhiteIPs();
	// tmpMap.put("result", todo ? "1" : "0");
	// if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
	// res.setContentType("application/json;charset=UTF-8");
	// responseJSONObject = new JSONObject(tmpMap);
	// out = res.getWriter();
	// out.println(responseJSONObject);
	// // out.flush();
	// LOG.debug(responseJSONObject.toString());
	// }
	// } catch (Exception e) {
	// LOG.error("BlackWhiteAction.checkIPs.Exception, " + e.getMessage());
	// ShowLog.err(LOG, e);
	// } finally {
	// if (dao != null) {
	// dao.close();
	// dao = null;
	// }
	// todo = false;
	// tmpMap = null;
	// responseJSONObject = null;
	// out = null;
	// }
	// return;
	// }

	/**
	 * get all black white list
	 */
	public void showAll(HttpServletRequest req, HttpServletResponse res) {
		LOG.info("BlackWhiteAction.showAll start");
		BlackWhiteDaoImpl dao = null;
		BlackWhiteServiceImpl service = null;
		List<Object> tmpList = null;
		Map<String, Object> tmpMap = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			dao = new BlackWhiteDaoImpl();
			service = new BlackWhiteServiceImpl();
			service.setDao(dao);
			tmpList = service.getAllList();
			tmpMap = new HashMap<String, Object>();
			int i = 1;
			for (Object o : tmpList) {
				BlackWhiteBean bean = (BlackWhiteBean) o;
				Map<String, Object> tmo = CommandUtil.getMap(bean);
				if (tmo != null && !tmo.isEmpty() && tmo.size() > 0) {
					Map<String, String> tmn = new HashMap<String, String>();
					for (String key : tmo.keySet()) {
						tmn.put(key, tmo.get(key).toString());
					}
					tmpMap.put("" + i, tmn);
					tmn = null;
				} else {
					tmpMap.put("" + i, null);
				}
				i++;
				tmo = null;
				bean = null;
				o = null;
			}
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				tmpMap.put("result", "1");
			} else {
				tmpMap.put("result", "0");
			}
			i = 0;
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				res.setContentType("application/json;charset=UTF-8");
				responseJSONObject = new JSONObject(tmpMap);
				out = res.getWriter();
				out.println(responseJSONObject);
				out.flush();
			}
		} catch (Exception e) {
			LOG.error("BlackWhiteAction.showAll.Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			tmpList = null;
			tmpMap = null;
			responseJSONObject = null;
			out = null;
		}
		return;
	}

	/**
	 * get black white list by (name or ip)
	 */
	public void show(HttpServletRequest req, HttpServletResponse res) {
		BlackWhiteDaoImpl dao = null;
		BlackWhiteServiceImpl service = null;
		List<Object> tmpList = null;
		Map<String, Object> tmpMap = null;
		Map<String, Object> data = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			data = this.getRequestData(req, res);
			String name = data.containsKey("name") ? data.get("name").toString() : "";
			String ip = data.containsKey("ip") ? data.get("ip").toString() : "";
			dao = new BlackWhiteDaoImpl();
			service = new BlackWhiteServiceImpl();
			service.setDao(dao);
			tmpList = service.getList(name, ip);
			tmpMap = new HashMap<String, Object>();
			int i = 1;
			for (Object o : tmpList) {
				BlackWhiteBean bean = (BlackWhiteBean) o;
				Map<String, Object> tmo = CommandUtil.getMap(bean);
				if (tmo != null && !tmo.isEmpty() && tmo.size() > 0) {
					Map<String, String> tmn = new HashMap<String, String>();
					for (String key : tmo.keySet()) {
						tmn.put(key, tmo.get(key).toString());
					}
					tmpMap.put("" + i, tmn);
					tmn = null;
				} else {
					tmpMap.put("" + i, null);
				}
				i++;
				tmo = null;
				bean = null;
				o = null;
			}
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				tmpMap.put("result", "1");
			} else {
				tmpMap.put("result", "0");
			}
			i = 0;
			name = "";
			name = null;
			ip = "";
			ip = null;
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				res.setContentType("application/json;charset=UTF-8");
				responseJSONObject = new JSONObject(tmpMap);
				out = res.getWriter();
				out.println(responseJSONObject);
				out.flush();
			}
		} catch (Exception e) {
			LOG.error("BlackWhiteAction.show.Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			tmpList = null;
			tmpMap = null;
			data = null;
			responseJSONObject = null;
			out = null;
		}
		return;
	}

	/**
	 * for update black white list
	 */
	public void update(HttpServletRequest req, HttpServletResponse res) {
		BlackWhiteDaoImpl dao = null;
		BlackWhiteServiceImpl service = null;
		Map<String, Object> tmpMap = null;
		Map<String, Object> data = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			int cou = 0;
			data = this.getRequestData(req, res);
			String mode = data.containsKey("mode") ? data.get("mode").toString() : "";
			String name = data.containsKey("name") ? data.get("name").toString() : "";
			String ip = data.containsKey("ip") ? data.get("ip").toString() : "";
			if (("1".equals(mode) || "2".equals(mode) && (!"".equals(name) && !"".equals(ip))) || "3".equals(mode)) {
				String text = data.containsKey("text") ? data.get("text").toString() : "";
				String type = data.containsKey("type") ? data.get("type").toString() : "";
				String area = data.containsKey("area") ? data.get("area").toString() : "";
				String id = data.containsKey("id") ? data.get("id").toString() : "";
				dao = new BlackWhiteDaoImpl();
				service = new BlackWhiteServiceImpl();
				service.setDao(dao);
				BlackWhiteBean bean = new BlackWhiteBean();
				bean.setName(name);
				String[] ll = ip.split("\\.");
				if (ll.length == 4) {
					bean.setIp1(Integer.parseInt(ll[0]));
					bean.setIp2(Integer.parseInt(ll[1]));
					bean.setIp3(Integer.parseInt(ll[2]));
					bean.setIp4(Integer.parseInt(ll[3]));
				}
				ll = null;
				if (!"".equals(text)) {
					bean.setText(text);
				}
				if (!"".equals(type)) {
					bean.setType("1".equals(type) ? true : false);
				}
				if (!"".equals(area)) {
					bean.setArea(area);
				}

				if ("1".equals(mode)) {
					cou = service.insertRow(bean);
				} else if ("2".equals(mode) && !"".equals(id)) {
					bean.setId(Integer.parseInt(id));
					cou = service.updateRow(bean);
				} else if ("3".equals(mode) && !"".equals(id)) {
					bean.setId(Integer.parseInt(id));
					cou = service.deleteRow(Integer.parseInt(id));
				} else {
					LOG.debug("BlackWhiteAction.update not update!");
				}
				text = "";
				text = null;
				type = "";
				type = null;
				area = "";
				area = null;
				id = "";
				id = null;
				// for update black white list (CommandConstant.BLACK_WHITE_LIST)
				dao.checkBlackWhiteIPs();
			}
			tmpMap = new HashMap<String, Object>();
			if (cou > 0) {
				tmpMap.put("result", "1");
				if (dao == null) {
					dao = new BlackWhiteDaoImpl();
				}
				if (CommandConstant.SERVER_IP == null) {
					CommandConstant.SERVER_IP = java.net.InetAddress.getLocalHost().getHostAddress();
				}

				// if (CommandConstant.SERVERS_IP_LIST != null &&
				// !CommandConstant.SERVERS_IP_LIST.isEmpty()
				// && CommandConstant.SERVERS_IP_LIST.size() > 0) {
				// for (String str : CommandConstant.SERVERS_IP_LIST) {
				// if (CommandConstant.SERVER_IP.equals(str)) {
				// str = "127.0.0.1";
				// } else {
				// dao.touchURL("http://" + str + ":" + CommandConstant.SERVER_PORT
				// + ("".equals(CommandConstant.SERVER_CONTEXT) ? "/" :
				// CommandConstant.SERVER_CONTEXT)
				// + "/BlackWhite!checkIPs.php?date=" + new Date().getTime());
				// }
				// }
				// }

				BaseService baseService = new BaseService();
				BaseDao baseDao = new BaseDao();
				baseService.setDao(baseDao);

				baseService.callServer("Default!checkIPs", CommandConstant.NOT_RUN_LIST);

			} else {
				tmpMap.put("result", "0");
			}
			mode = "";
			mode = null;
			name = "";
			name = null;
			ip = "";
			ip = null;
			cou = 0;
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				res.setContentType("application/json;charset=UTF-8");
				responseJSONObject = new JSONObject(tmpMap);
				out = res.getWriter();
				out.println(responseJSONObject);
				// out.flush();
			}
		} catch (Exception e) {
			LOG.error("BlackWhiteAction.update.Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			tmpMap = null;
			data = null;
			responseJSONObject = null;
			out = null;
		}
		return;
	}

}
