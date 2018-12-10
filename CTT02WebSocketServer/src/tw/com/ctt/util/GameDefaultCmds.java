package tw.com.ctt.util;

public class GameDefaultCmds {
	
	public static final String  SET_ACC_ID = "101";
	public static final String  SET_ACCID_OK = "102";
	
	public static final String  GET_SERVER_LIST = "103";
	public static final String  SERVER_LIST = "104";
	
	public static final String  GET_LAST_SERVER = "105";
	public static final String  LAST_SERVER = "106";
	
	public static final String  JOIN = "107";
	public static final String  CONNECT_SUCCESS = "108";
	public static final String  RECONNECT_SUCCESS = "110";
	public static final String  IN_LOBBY = "112";
	public static final String  SERVER_FULL = "114";
	
	public static final String  MATCH = "115";
	public static final String  WAIT_ROOM = "116";
	public static final String  INTO_ROOM = "118";
	
	public static final String  LEAVE = "119";
	public static final String  GET_LEAVE_ROOM = "120";
	
	
	public static final String  PUNCHE = "121";
	public static final String  GET_PUNCHE = "122";
	public static final String  PAPER = "123";
	public static final String  SCISSOR = "125";
	public static final String  ROCK = "127";
	
	public static final String  AUTO_PUNCHE = "128";
	
	public static final String  KEEP_PLAY = "129";
	public static final String GET_KEEP_PLAY = "130";
	
	public static final String  CMD_NO_WINNER = "132";
	public static final String  WINNER = "134";
	public static final String  LOSER = "136";	
	public static final String  FINAL_WINNER = "138";
	public static final String  FINAL_LOSER = "140";
	
	public static final String  WAIT_CONTINUE = "142";
	public static final String  WAIT_VIDEO = "144";
	public static final String  WAIT_PUNCHE = "146";
	public static final String  GAME_START = "148";
	public static final String  ROUND = "150";
	public static final String  REAL_PLAYTIMES = "152";
	public static final String  MONEY = "154";
	
	public static final String  PUNCHE_TIMEOUT = "156";
	public static final String  CONTINUE_TIMEOUT = "158";
	public static final String  VIDEO_TIMEOUT = "160";
	public static final String  CONNECT_FAIL = "162";
	
	public static final String  HEART_BEAT = "163";
	
	public static final String  GAME_OVER = "164";
	public static final String  NO_MONEY = "166";
	public static final String  DISCONNECT_GAME_SERVER = "168";
	
	public static final String  LEAVE_GAME = "169";
	public static final String  GET_LEAVE_GAME = "170";
	
	public static final String  START_ROBOT = "171";
	public static final String  GET_START_ROBOT = "172";
	
	public static final String  STOP_ROBOT = "173";
	public static final String  GET_STOP_ROBOT = "174";
	
	public static final String  NAME = "176";
	public static final String  OPPONENT_NAME = "178";
	public static final String  OPPONENT_MONEY = "180";
	
	/*
	 * Comm between Game server and websocket server
	 */
	
	public static final String  ADD_CLIENT = "001";
	public static final String  REMOVE_CLIENT = "002";
	public static final String  REGIST_GAME_SERVER_ID = "003";
	public static final String  REGIST_GAME_SERVER_BET = "004";
	public static final String  REGIST_GAME_SERVER_GAME_TYPE = "005";
	public static final String  REGIST_GAME_SERVER_MAX_PLAYER = "006";
	public static final String  NORMAL_CMD = "007";

	/*
	 * cmd about money
	 */
	public static final String  DEPOSIT = "008";
	public static final String  WITHDRAW = "009";
	

	/*
	 * Id header for regist to websocket server.
	 */
	public static final String  CTT_GAME_SERVER = "37718s";
	public static final String  CTT_GAME_CLIENT = "client";
	
}
