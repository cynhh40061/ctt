package tw.com.ctt.action;

import javax.servlet.http.HttpServlet;

public class BaseActionParent extends HttpServlet {

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

	private void BaseActionParent() {

	}
}
