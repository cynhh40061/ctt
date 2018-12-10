package tw.com.ctt.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
// import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.impl.BaseDao;
import tw.com.ctt.dao.impl.BaselineInfoDaoImpl;
import tw.com.ctt.dao.impl.KjInfoDaoImpl;
import tw.com.ctt.dao.impl.OrderInfoOfBettingDaoImpl;
import tw.com.ctt.service.impl.BaselineInfoServiceImpl;
import tw.com.ctt.service.impl.KjInfoServiceImpl;
import tw.com.ctt.service.impl.OrderInfoOfBettingServiceImpl;

public class CheckLoginFilter implements Filter {

	private String exts = "xhtml,php";

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// System.out.println("CheckLoginFilter");
		HttpServletRequest requ = (HttpServletRequest) req;
//		HttpSession s = requ.getSession();
		String uri = requ.getRequestURI();
		String ext = uri.indexOf(".") == -1 ? "" : uri.substring(uri.lastIndexOf(".") + 1);
		String[] tmps = exts.split(",");
		boolean todo = false;
		if (!uri.endsWith("/") && !"".equals(ext)) {
			for (int i = 0; i < tmps.length; i++) {
				if (tmps[i].trim().equalsIgnoreCase(ext.trim())) {
					todo = true;
					break;
				}
			}
		} else if ("".equals(ext)) {
			todo = true;
		}

		// over 60 mins not update SERVERS_IP_LIST, will be called checkServerIPs
		if (new Date().getTime() - CommandConstant.CHECK_SERVERS_IP_TIME > 60 * 60 * 1000) {
			BaseDao dao = new BaseDao();
			dao.checkServerIPs();
			dao.close();
			dao = null;
		}
		// over 10 mins not update BLACK_WHITE_LIST, will be called checkBlackWhiteIPs
		if (new Date().getTime() - CommandConstant.CHECK_BLACK_WHITE_TIME > 60 * 60 * 1000) {
			BaseDao dao = new BaseDao();
			dao.checkBlackWhiteIPs();
			dao.close();
			dao = null;
		}
		// check remoteAddr in WHITE_LIST or BLACK_LIST
		boolean allow = false;
		if (CommandConstant.WHITE_LIST != null && !CommandConstant.WHITE_LIST.isEmpty() && CommandConstant.WHITE_LIST.size() > 0) {
			String remoteAddr = req.getRemoteAddr();
			if (remoteAddr == null || remoteAddr.isEmpty() || remoteAddr.split("\\.").length != 4) {
				req.getRequestDispatcher("/building.xhtml").forward(req, resp);
				return;
			}
			if (CommandConstant.WHITE_LIST.contains(remoteAddr)
					|| CommandConstant.WHITE_LIST.contains(remoteAddr.substring(0, remoteAddr.lastIndexOf(".")))) {
				// allow
				allow = true;
			}
			remoteAddr = "";
			remoteAddr = null;
		}
		if (!allow && CommandConstant.BLACK_LIST != null && !CommandConstant.BLACK_LIST.isEmpty() && CommandConstant.BLACK_LIST.size() > 0) {
			String remoteAddr = req.getRemoteAddr();
			if (remoteAddr == null || remoteAddr.isEmpty() || remoteAddr.split("\\.").length != 4) {
				req.getRequestDispatcher("/building.xhtml").forward(req, resp);
				return;
			}
			if (CommandConstant.BLACK_LIST.contains(remoteAddr)
					|| CommandConstant.BLACK_LIST.contains(remoteAddr.substring(0, remoteAddr.lastIndexOf(".")))) {
				req.getRequestDispatcher("/building.xhtml").forward(req, resp);
				return;
			}
			remoteAddr = "";
			remoteAddr = null;
			allow = true;
		}
		allow = false;
		
		// over 1 min not update ALL_LOTTERY_INFO, will be called
		// getAllLotteryLastUpdateTime
		if (new Date().getTime() - CommandConstant.CHECK_UPDATE_ALL_LOTTERY_TIME > 60 * 60 * 1000) {
			CommandConstant.CHECK_UPDATE_ALL_LOTTERY_TIME = new Date().getTime();
			OrderInfoOfBettingServiceImpl service = null;
			OrderInfoOfBettingDaoImpl dao = null;
			try {
				service = new OrderInfoOfBettingServiceImpl();
				dao = new OrderInfoOfBettingDaoImpl();
				service.setDao(dao);
				long t1 = service.getAllLotteryLastUpdateTime();				
				if (CommandConstant.DB_LAST_UPDATE_TIME_OF_ALL_LOTTERY_INFO < t1) {
					CommandConstant.DB_LAST_UPDATE_TIME_OF_ALL_LOTTERY_INFO = t1;
					CommandConstant.ALL_LOTTERY_INFO = service.getAllLottery();
				}
			} catch (Exception e) {
			} finally {
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

		// over 10 sec not update KJ_TIME_AND_STATUS, will be called getKjLastUpdateTime
		if (new Date().getTime() - CommandConstant.CHECK_UPDATE_KJ_TIME > 60 *60 * 1000) {
			KjInfoServiceImpl service = null;
			KjInfoDaoImpl dao = null;
			try {
				service = new KjInfoServiceImpl();
				dao = new KjInfoDaoImpl();
				service.setDao(dao);
				long t1 = service.getKjLastUpdateTime();
				CommandConstant.CHECK_UPDATE_KJ_TIME = new Date().getTime();
				if (CommandConstant.DB_LAST_UPDATE_TIME_OF_KJ < t1) {
					CommandConstant.DB_LAST_UPDATE_TIME_OF_KJ = t1;
					CommandConstant.KJ_TIME_INFO = service.getAllKjTimeStatus();
				}
			} catch (Exception e) {
			} finally {
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

		// over 15 sec not update CURRENT_BET_RATIO, will be called
		// getBaselineLastUpdateTime
		if (new Date().getTime() - CommandConstant.CHECK_UPDATE_BASELINE_TIME > 60 *60 * 1000) {
			CommandConstant.CHECK_UPDATE_BASELINE_TIME = new Date().getTime();
			BaselineInfoServiceImpl service = null;
			BaselineInfoDaoImpl dao = null;
			try {
				service = new BaselineInfoServiceImpl();
				dao = new BaselineInfoDaoImpl();
				service.setDao(dao);
				long t1 = service.getBaselineLastUpdateTime();				
				if (CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE < t1) {
					CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE = t1;
					CommandConstant.CURRENT_BET_RATIO = service.getBaseline();
				}
			} catch (Exception e) {
			} finally {
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
		
		if (new Date().getTime() - CommandConstant.CHECK_UPDATE_BASELINE_TIME_LF > 15 * 1000) {
			BaselineInfoServiceImpl service = null;
			BaselineInfoDaoImpl dao = null;
			try {
				service = new BaselineInfoServiceImpl();
				dao = new BaselineInfoDaoImpl();
				service.setDao(dao);
				long t1 = service.getBaselineLastUpdateTimeLF();
				CommandConstant.CHECK_UPDATE_BASELINE_TIME_LF = new Date().getTime();
				if (CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE_LF < t1) {
					CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE_LF = t1;
					CommandConstant.CURRENT_BET_RATIO_LF = service.getBaselineLF();
				}
			} catch (Exception e) {
			} finally {
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

		// over 60 mins not update KJ_TIME_AND_STATUS, will be called
		// getKjLastUpdateTime
		if (new Date().getTime() - CommandConstant.CHECK_UPDATE_SUB_ORDER_TIME > 60 * 60 * 1000) {
			CommandConstant.CHECK_UPDATE_SUB_ORDER_TIME = new Date().getTime();
			OrderInfoOfBettingServiceImpl service = null;
			OrderInfoOfBettingDaoImpl dao = null;
			try {
				service = new OrderInfoOfBettingServiceImpl();
				dao = new OrderInfoOfBettingDaoImpl();
				service.setDao(dao);
				CommandConstant.NO_OF_BET_INFO_FOR_SUB_ORDER = service.getSubOrderInfoNoOfBet();
				CommandConstant.BASIC_INFO_OF_SUB_ORDER = service.getSubOrderInfo();
				CommandConstant.ALL_HANDICAP_INFO = service.getAllHandicap();				
			} catch (Exception e) {
			} finally {
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

		if (uri.substring(uri.lastIndexOf(".") + 1).split(".").equals("Login")) {
			todo = false;
		}
		chain.doFilter(req, resp);
		return;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String encodingParam = filterConfig.getInitParameter("exts");
		if (encodingParam != null) {
			exts = encodingParam;
		}
	}

	public void destroy() {
		// nothing todo
	}

}
