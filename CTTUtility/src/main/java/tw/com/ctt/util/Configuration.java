package tw.com.ctt.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Configuration {

	public static String READ_DB_USER_NAME;

	public static String READ_DB_PASSWORD;

	public static String READ_DB_URL;

	public static String READ_DB_IP;

	public static String READ_DB_DRIVER;

	public static String READ_DATASOURCE;

	public static Integer READ_DB_MIN_CONNECTIONS = 1;

	public static Integer READ_DB_MAX_CONNECTIONS = 15;

	public static String WRITE_DB_USER_NAME;

	public static String WRITE_DB_PASSWORD;

	public static String WRITE_DB_IP;

	public static String WRITE_DB_URL;

	public static String WRITE_DB_DRIVER;

	public static String WRITE_DATASOURCE;

	public static String GAME_PAGE_HTML;

	public static Integer WRITE_DB_MIN_CONNECTIONS = 1;

	public static Integer WRITE_DB_MAX_CONNECTIONS = 5;

	public static Integer MAX_WORK_CMD = 1;

	public static Integer MAX_WORK_THREAD = 1;

	public static String AD_IMG_SRC;

	public static String BLACK_LIST;

	public static String WHITE_LIST;

	public static String NOT_RUN_LIST;

	public static Boolean CHECK_URL = false;

	private static boolean initialized = false;

	public static Configuration configuration = new Configuration();

	public Configuration() {
		if (!initialized || !Configuration.checkInit()) {
			init();
		}
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		// Configuration configuration = new Configuration();
		Configuration configuration = Configuration.getInstance();
		System.out.println("Configuration   1:" + configuration.configuration.READ_DB_DRIVER);
		// configuration.configuration = configuration.getInstance();
		System.out.println("check init():" + configuration.checkInit());
	}

	public static Configuration getInstance() {
		if (!initialized || configuration == null || !Configuration.checkInit()) {
			configuration = new Configuration();
		}
		return configuration;
	}

	private static boolean checkInit() {
		Field[] field = Configuration.class.getDeclaredFields();
		for (int i = 0; i < field.length; i++) {
			try {
				String[] ll = new String[] { "READ_DATASOURCE", "WRITE_DATASOURCE", "GAME_PAGE_HTML", "configuration" };
				List<String> list = new ArrayList<String>(Arrays.asList(ll));
				// System.out.println(list.toString());
				if (!list.contains(field[i].getName().toString())) {
					if (field[i].get(configuration) == null) {
						System.out.println(field[i].getName() + "\t" + field[i].get(configuration));
						return false;
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	private void init() {
		Properties props = new Properties();
		InputStream inputStream = null;
		String username = "";
		String passwd = "";
		String url = "";
		String ip = "";
		String driver = "";
		String datasource = "";
		int minConn = 0;
		int maxConn = 0;
		String imgSrc = "";
		String blackList = "";
		String whiteList = "";
		String notRunList = "";
		Boolean checkUrl = false;
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream("baseconfig.properties");
			props.load(inputStream);

			username = props.get("READ_DB_USER_NAME") == null ? "" : (String) props.get("READ_DB_USER_NAME");
			passwd = props.get("READ_DB_PASSWORD") == null ? "" : (String) props.get("READ_DB_PASSWORD");
			url = props.get("READ_DB_URL") == null ? "" : (String) props.get("READ_DB_URL");
			ip = props.get("READ_DB_IP") == null ? "" : (String) props.get("READ_DB_IP");
			driver = props.get("READ_DB_DRIVER") == null ? "" : (String) props.get("READ_DB_DRIVER");
			datasource = props.get("READ_DATASOURCE") == null ? "" : (String) props.get("READ_DATASOURCE");
			minConn = props.get("READ_DB_MIN_CONNECTIONS") == null ? 0
					: Integer.parseInt((String) props.get("READ_DB_MIN_CONNECTIONS"));
			maxConn = props.get("READ_DB_PASSWORD") == null ? 0
					: Integer.parseInt((String) props.get("READ_DB_MAX_CONNECTIONS"));
			imgSrc = props.get("AD_IMG_SRC") == null ? "" : (String) props.get("AD_IMG_SRC");
			blackList = props.get("BLACK_LIST") == null ? "" : (String) props.get("BLACK_LIST");
			whiteList = props.get("WHITE_LIST") == null ? "" : (String) props.get("WHITE_LIST");
			notRunList = props.get("NOT_RUN_LIST") == null ? "" : (String) props.get("NOT_RUN_LIST");
			checkUrl = props.get("CHECK_URL") == null ? false : Boolean.parseBoolean((String) props.get("CHECK_URL"));

			if (username != null && !username.isEmpty() && username.trim().length() > 0) {
				READ_DB_USER_NAME = username;
			} else {
				System.out.println("READ_DB_USER_NAME is null !!");
				destroy();
				return;
			}
			if (passwd != null && !passwd.isEmpty() && passwd.trim().length() > 0) {
				READ_DB_PASSWORD = passwd;
			} else {
				System.out.println("READ_DB_PASSWORD is null !!");
				destroy();
				return;
			}
			if (url != null && !url.isEmpty() && url.trim().length() > 0) {
				READ_DB_URL = url;
			} else {
				System.out.println("READ_DB_URL is null !!");
				destroy();
				return;
			}
			if (ip != null && !ip.isEmpty() && ip.trim().length() > 0) {
				READ_DB_IP = ip;
			} else {
				System.out.println("READ_DB_IP is null !!");
				destroy();
				return;
			}
			if (driver != null && !driver.isEmpty() && driver.trim().length() > 0) {
				READ_DB_DRIVER = driver;
			} else {
				System.out.println("READ_DB_DRIVER is null !!");
				destroy();
				return;
			}
			if (datasource != null && !datasource.isEmpty() && datasource.trim().length() > 0) {
				READ_DATASOURCE = datasource;
			}
			if (minConn > 0) {
				READ_DB_MIN_CONNECTIONS = minConn;
			} else {
				System.out.println("READ_DB_MIN_CONNECTIONS is null !!");
				destroy();
				return;
			}
			if (maxConn > 0) {
				READ_DB_MAX_CONNECTIONS = maxConn;
			} else {
				System.out.println("READ_DB_MAX_CONNECTIONS is null !!");
				destroy();
				return;
			}

			username = props.get("WRITE_DB_USER_NAME") == null ? "" : (String) props.get("WRITE_DB_USER_NAME");
			passwd = props.get("WRITE_DB_PASSWORD") == null ? "" : (String) props.get("WRITE_DB_PASSWORD");
			url = props.get("WRITE_DB_URL") == null ? "" : (String) props.get("WRITE_DB_URL");
			ip = props.get("WRITE_DB_IP") == null ? "" : (String) props.get("WRITE_DB_IP");
			driver = props.get("WRITE_DB_DRIVER") == null ? "" : (String) props.get("WRITE_DB_DRIVER");
			datasource = props.get("WRITE_DATASOURCE") == null ? "" : (String) props.get("WRITE_DATASOURCE");
			minConn = props.get("WRITE_DB_MIN_CONNECTIONS") == null ? 0
					: Integer.parseInt((String) props.get("WRITE_DB_MIN_CONNECTIONS"));
			maxConn = props.get("WRITE_DB_MAX_CONNECTIONS") == null ? 0
					: Integer.parseInt((String) props.get("WRITE_DB_MAX_CONNECTIONS"));

			if (username != null && !username.isEmpty() && username.trim().length() > 0) {
				WRITE_DB_USER_NAME = username;
			} else {
				System.out.println("WRITE_DB_USER_NAME is null !!");
				destroy();
				return;
			}
			if (passwd != null && !passwd.isEmpty() && passwd.trim().length() > 0) {
				WRITE_DB_PASSWORD = passwd;
			} else {
				System.out.println("WRITE_DB_PASSWORD is null !!");
				destroy();
				return;
			}
			if (url != null && !url.isEmpty() && url.trim().length() > 0) {
				WRITE_DB_URL = url;
			} else {
				System.out.println("WRITE_DB_URL is null !!");
				destroy();
				return;
			}
			if (ip != null && !ip.isEmpty() && ip.trim().length() > 0) {
				WRITE_DB_IP = ip;
			} else {
				System.out.println("WRITE_DB_IP is null !!");
				destroy();
				return;
			}
			if (driver != null && !driver.isEmpty() && driver.trim().length() > 0) {
				WRITE_DB_DRIVER = driver;
			} else {
				System.out.println("WRITE_DB_DRIVER is null !!");
				destroy();
				return;
			}
			if (datasource != null && !datasource.isEmpty() && datasource.trim().length() > 0) {
				WRITE_DATASOURCE = datasource;
			}
			if (minConn > 0) {
				WRITE_DB_MIN_CONNECTIONS = minConn;
			} else {
				System.out.println("WRITE_DB_MIN_CONNECTIONS is null !!");
				destroy();
				return;
			}
			if (maxConn > 0) {
				WRITE_DB_MAX_CONNECTIONS = maxConn;
			} else {
				System.out.println("WRITE_DB_MAX_CONNECTIONS is null !!");
				destroy();
				return;
			}
			if (Integer.parseInt((String) props.get("MAX_WORK_CMD")) > 0) {
				MAX_WORK_CMD = Integer.parseInt((String) props.get("MAX_WORK_CMD"));
			} else {
				System.out.println("MAX_WORK_CMD is null !!");
				destroy();
				return;
			}
			if (Integer.parseInt((String) props.get("MAX_WORK_THREAD")) > 0) {
				MAX_WORK_THREAD = Integer.parseInt((String) props.get("MAX_WORK_THREAD"));
			} else {
				System.out.println("MAX_WORK_THREAD is null !!");
				destroy();
				return;
			}

			if (imgSrc != null && !imgSrc.isEmpty() && imgSrc.trim().length() > 0) {
				AD_IMG_SRC = imgSrc;
			} else {
				System.out.println("AD_IMG_SRC is null !!");
				destroy();
				return;
			}
			if (blackList != null && !blackList.isEmpty() && blackList.trim().length() > 0) {
				BLACK_LIST = blackList;
			} else {
				System.out.println("BLACK_LIST is null !!");
				destroy();
				return;
			}
			if (whiteList != null && !whiteList.isEmpty() && whiteList.trim().length() > 0) {
				WHITE_LIST = whiteList;
			} else {
				System.out.println("WHITE_LIST is null !!");
				destroy();
				return;
			}
			if (notRunList != null) {
				NOT_RUN_LIST = notRunList;
			}
			if (checkUrl != null) {
				CHECK_URL = checkUrl;
			}

			initialized = checkInit();
		} catch (Exception e) {
			System.err.println("Configuration_init_Exception=" + e.getMessage());
			e.printStackTrace();
		}
	}

	protected static void destroy() {
		System.out.println("destroy");
		READ_DB_USER_NAME = "";
		READ_DB_USER_NAME = null;
		READ_DB_PASSWORD = "";
		READ_DB_PASSWORD = null;
		READ_DB_URL = "";
		READ_DB_URL = null;
		READ_DB_IP = "";
		READ_DB_IP = null;
		READ_DB_DRIVER = "";
		READ_DB_DRIVER = null;
		READ_DATASOURCE = "";
		READ_DATASOURCE = null;
		READ_DB_MIN_CONNECTIONS = 0;
		READ_DB_MAX_CONNECTIONS = 0;
		WRITE_DB_USER_NAME = "";
		WRITE_DB_USER_NAME = null;
		WRITE_DB_PASSWORD = "";
		WRITE_DB_PASSWORD = null;
		WRITE_DB_IP = "";
		WRITE_DB_IP = null;
		WRITE_DB_URL = "";
		WRITE_DB_URL = null;
		WRITE_DB_DRIVER = "";
		WRITE_DB_DRIVER = null;
		WRITE_DATASOURCE = "";
		WRITE_DATASOURCE = null;
		GAME_PAGE_HTML = "";
		GAME_PAGE_HTML = null;
		WRITE_DB_MIN_CONNECTIONS = 0;
		WRITE_DB_MAX_CONNECTIONS = 0;
		MAX_WORK_CMD = 0;
		MAX_WORK_THREAD = 0;

		configuration = null;

		System.gc();
	}

	protected void finalize() throws Throwable {
		destroy();
		super.finalize();
	}
}
