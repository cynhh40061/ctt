package tw.com.ctt.action;

import javax.servlet.annotation.WebServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "UpdateInfo", urlPatterns = { "/UpdateInfo" })
public class UpdateInfoAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2498128181441837157L;
	private static final Logger LOG = LogManager.getLogger(UpdateInfoAction.class.getName());

	public String[] loginCheckURL = { "/UpdateInfo!checkIPs" };
	public String[] loginCheckNoUpdateURL = {};
	public String[] extraURL = {};
	public String[] authURL = {};

	public UpdateInfoAction() {
		super();
		super.loginCheckURL = loginCheckURL;
		super.loginCheckNoUpdateURL = loginCheckNoUpdateURL;
		super.extraURL = extraURL;
		super.authURL = authURL;
		initURLs();
		LOG.info("UpdateInfoAction start");
	}
}
