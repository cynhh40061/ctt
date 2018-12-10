package tw.com.ctt.constant;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandConstant {
	public static String SERVER_NAME = null;
	public static String SERVER_IP = null;
	public static String SERVER_PORT = null;
	public static String SERVER_CONTEXT = null;
	public static CopyOnWriteArraySet<String> BLACK_LIST = null;
	public static CopyOnWriteArraySet<String> WHITE_LIST = null;
	public static long LAST_UPDATE_BLACK_WHITE_TIME = 0; // from DB UNIX_TIMESTAMP()
	public static long CHECK_BLACK_WHITE_TIME = 0; // server new Date().getTime(); over 60mins will be checked
	public static List<List<String>> SERVERS_IP_LIST;
	public static long LAST_UPDATE_BET_RATIO_TIME = 0; // from DB UNIX_TIMESTAMP()
	public static long LAST_UPDATE_SERVERS_IP_TIME = 0; // from DB UNIX_TIMESTAMP()
	public static long CHECK_SERVERS_IP_TIME = 0; // server new Date().getTime(); over 10mins will be checked
	public static CopyOnWriteArraySet<String> ALLOW_NO_LOGIN_URLS = null; // no need to login URLs
	/* not run server list */
	public static List<String> NOT_RUN_LIST = null;
	/* no need to check anything */
	public static CopyOnWriteArraySet<String> LOGIN_URL = null;
	/* only for check */
	public static CopyOnWriteArraySet<String> LOGIN_CHECK_URL = null;
	/* check tokenId but no update tokenId */
	public static CopyOnWriteArraySet<String> LOGIN_CHECK_NO_UPDATE_URL = null;
	/* must be logined, but not need to check auth */
	public static CopyOnWriteArraySet<String> EXTRA_URL = null;
	/* must be logined, and need to check auth */
	public static CopyOnWriteArraySet<String> AUTH_URL = null;

	public static Map<String, Object> CURRENT_BET_RATIO = null;
	public static Map<String, Object> CURRENT_BET_RATIO_LF = null; // (baseline)
	public static Map<String, Object> SUB_ORDER_MAPPING_INFO = null;
	public static Map<String, Object> SUB_ORDER_NO_OF_BET_INFO = null;
	public static Map<String, Object> ALL_LOTTERY_INFO = null;
	public static Map<String, Object> ALL_LOTTERY_TITLE_INFO = null;
	public static Map<String, Object> KJ_TIME_AND_STATUS = null;

	public static Map<String, Object> KJ_HISTORY_TIME_AND_STATUS = null;

	public static long CHECK_UPDATE_ALL_LOTTERY_TIME = 0;
	public static long DB_LAST_UPDATE_TIME_OF_ALL_LOTTERY_INFO = 0;

	public static long CHECK_UPDATE_KJ_TIME = 0;
	public static long DB_LAST_UPDATE_TIME_OF_KJ = 0;

	public static long CHECK_UPDATE_BASELINE_TIME = 0;
	public static long DB_LAST_UPDATE_TIME_OF_BASELINE = 0;

	public static long CHECK_UPDATE_BASELINE_TIME_LF = 0;
	public static long DB_LAST_UPDATE_TIME_OF_BASELINE_LF = 0;

	public static Map<String, Object> KJ_TIME_INFO;

	public static boolean CHECK_URL = false;

	public static final int URL_TYPE_LOGIN_URL = 1;
	public static final int URL_TYPE_LOGIN_CHECK_URL = 2;
	public static final int URL_TYPE_LOGIN_CHECK_NO_UPDATE_URL = 3;
	public static final int URL_TYPE_EXTRA_URL = 4;
	public static final int URL_TYPE_AUTH_URL = 5;
}
