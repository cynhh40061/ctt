package tw.com.ctt.filter;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.impl.BaseDao;
import tw.com.ctt.util.DataSource;

public class ContextInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Connection connR = null;
		Connection connW = null;
		String notRunList = null;
		BaseDao dao = null;
		try {
			// need to check url
			CommandConstant.CHECK_URL = tw.com.ctt.util.Configuration.CHECK_URL;

			// get not run list
			notRunList = tw.com.ctt.util.Configuration.NOT_RUN_LIST == null ? "" : tw.com.ctt.util.Configuration.NOT_RUN_LIST;
			if (notRunList != null && !notRunList.isEmpty() && notRunList.trim().length() > 0) {
				String[] notRunLists = notRunList.split(",");
				CommandConstant.NOT_RUN_LIST = new ArrayList<String>(Arrays.asList(notRunLists));
			} else {
				CommandConstant.NOT_RUN_LIST = new ArrayList<String>();
			}

			CommandConstant.SERVER_NAME = java.net.InetAddress.getLocalHost().getHostName() == null ? ""
					: java.net.InetAddress.getLocalHost().getHostName();
			CommandConstant.SERVER_IP = java.net.InetAddress.getLocalHost().getHostAddress() == null ? ""
					: java.net.InetAddress.getLocalHost().getHostAddress();
			CommandConstant.SERVER_CONTEXT = sce.getServletContext().getContextPath() == null ? "" : sce.getServletContext().getContextPath();
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			Set<ObjectName> objs = mbs.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
			for (ObjectName obj : objs) {
				String port = obj.getKeyProperty("port");
				if (port != null && !port.isEmpty() && !"".equals(port.trim())) {
					CommandConstant.SERVER_PORT = port.toString();
					port = "";
					port = null;
					break;
				}
			}
			
			// for update servers ip list (CommandConstant.SERVERS_IP_LIST)
			dao = new BaseDao();
			dao.initServerIPs();
			
			// initial BLACK_WHITE_LIST
			String[] ip = tw.com.ctt.util.Configuration.BLACK_LIST.split(",");
			CommandConstant.BLACK_LIST = new CopyOnWriteArraySet<String>(Arrays.asList(ip));
			ip = null;
			ip = tw.com.ctt.util.Configuration.WHITE_LIST.split(",");
			CommandConstant.WHITE_LIST = new CopyOnWriteArraySet<String>(Arrays.asList(ip));
			
			// for update black white list (CommandConstant.BLACK_WHITE_LIST)
			dao.checkBlackWhiteIPs();
			
			// call order date update proce
			dao.updateOrderIdDate();

			connR = DataSource.getReadConnection();
			DataSource.returnReadConnection(connR);
			connW = DataSource.getWriteConnection();
			DataSource.returnWriteConnection(connW);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			notRunList = "";
			notRunList = null;
			if (dao != null) {
				dao.close();
				dao = null;
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

}
