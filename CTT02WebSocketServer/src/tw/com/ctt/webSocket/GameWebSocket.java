package tw.com.ctt.webSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tw.com.ctt.util.GameDefaultCmds;

@ServerEndpoint("/gameWS")
public class GameWebSocket {

	private Session session;
	private Set<String> addressSet;
	private String gameServerId = "";
	private String gameClientAccId = "";
	private String gameBet = "";
	private int maxPlayer = 0;
	private String gameType = "";
		
	@OnOpen
	public void onOpen(Session session) throws Exception {
		this.session = session;
		this.addressSet = new HashSet<String>();
		GameWebSocketMapUtil.put(session.getQueryString(), this);
	}

	@OnClose
	public void onClose() throws Exception {
		System.out.println("CLOSE:"+session.getQueryString());
		GameWebSocketMapUtil.remove(session.getQueryString());
	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		try {
			if (session.getQueryString().indexOf(GameDefaultCmds.CTT_GAME_CLIENT) == 0) {				
				if (isJSONValid(message)) {
					JSONObject jsonObj = new JSONObject(message);
					String cmd = "" + jsonObj.getString("Cmd");
					String data = "" + jsonObj.getString("Data");			
					
					if (cmd.equals(GameDefaultCmds.GET_SERVER_LIST)) {
						List<Map<String, String>> serverLists = new ArrayList<Map<String, String>>();
						Map<String, String> tmpMap;						
						Set<String> keys = GameWebSocketMapUtil.getKeys();
						for (String key : keys) {							
							if (key.indexOf(GameDefaultCmds.CTT_GAME_SERVER) == 0) {								
								GameWebSocket myWebSocket = ((GameWebSocket) GameWebSocketMapUtil.get(key));
								if (myWebSocket != null) {
									tmpMap = new HashMap<String, String>();
									tmpMap.put("name", myWebSocket.gameServerId);
									tmpMap.put("max", ""+myWebSocket.maxPlayer);
									tmpMap.put("current", ""+myWebSocket.addressSet.size());
									tmpMap.put("bet", myWebSocket.gameBet);
									tmpMap.put("type", myWebSocket.gameType);
									serverLists.add(tmpMap);
								}
							}
						}						
						JSONObject responseJSONObject = new JSONObject();	
						responseJSONObject.put("Meg", keys);
						responseJSONObject.put("Cmd", GameDefaultCmds.SERVER_LIST);
						responseJSONObject.put("Data", serverLists);
						this.sendMessage(responseJSONObject.toString());
						
					}else if (cmd.equals(GameDefaultCmds.GET_LAST_SERVER)) {	
						String gameServerId = "NONE";	
						if(GameWebSocketAccIdMapUtil.getKeys().contains(data)) {
							if(!GameWebSocketAccIdMapUtil.get(data).isEmpty()) {
								gameServerId = GameWebSocketAccIdMapUtil.get(data).get("serverId");	
							}							
						}																
						JSONObject responseJSONObject = new JSONObject();	
						responseJSONObject.put("Cmd", GameDefaultCmds.LAST_SERVER);
						responseJSONObject.put("Data", gameServerId);
						this.sendMessage(responseJSONObject.toString());
					}else if (cmd.equals(GameDefaultCmds.SET_ACC_ID)) {	
						this.gameClientAccId = data;											
						JSONObject responseJSONObject = new JSONObject();	
						responseJSONObject.put("Cmd", GameDefaultCmds.SET_ACCID_OK);
						responseJSONObject.put("Data", gameClientAccId);
						this.sendMessage(responseJSONObject.toString());
					}
					else if (cmd.equals(GameDefaultCmds.JOIN)){
						Set<String> keys = GameWebSocketMapUtil.getKeys();
						for (String key : keys) {
							if (key.indexOf(GameDefaultCmds.CTT_GAME_SERVER) == 0) {
								GameWebSocket myWebSocket = ((GameWebSocket) GameWebSocketMapUtil.get(key));
								if (myWebSocket != null) {
									if(data.equals(myWebSocket.gameServerId)) {
										if (myWebSocket.addressSet.size() < myWebSocket.maxPlayer) {
											synchronized (myWebSocket) {
												myWebSocket.sendMessage(message);
											}
										}else {
											JSONObject responseJSONObject = new JSONObject();	
											responseJSONObject.put("Cmd", GameDefaultCmds.SERVER_FULL);
											responseJSONObject.put("Data", "FULL");
											this.sendMessage(responseJSONObject.toString());
										}
										break;
									}
									
								}
							}
						}
					}else if (this.addressSet.size() != 0) {
						for (Iterator<String> iterator = this.addressSet.iterator(); iterator.hasNext();) {
							String s = iterator.next();
							GameWebSocket myWebSocket = ((GameWebSocket) GameWebSocketMapUtil.get(s));
							if (myWebSocket != null) {
								synchronized (myWebSocket) {
									myWebSocket.sendMessage(message);
								}

							} else {
								iterator.remove();
							}
						}
					}
				}				
			} else if (session.getQueryString().indexOf(GameDefaultCmds.CTT_GAME_SERVER) == 0) {
//				System.out.println(session.getQueryString() + " : "+message);
				if (isJSONValid(message)) {
					JSONObject jsonObj = new JSONObject(message);
					String msgAddress = "" + jsonObj.getString("to");
					String msg = "" + jsonObj.getString("msg");
					String cmd = "" + jsonObj.getString("cmd");
					
					if (cmd.equals(GameDefaultCmds.ADD_CLIENT)) {
						GameWebSocket gameClient = ((GameWebSocket) GameWebSocketMapUtil.get(msgAddress));
						if (gameClient != null) {
							synchronized (gameClient) {
								Map<String, String> m = new ConcurrentHashMap<String, String>();
								m.put("serverId", this.gameServerId);
								m.put("clientWSId", gameClient.session.getQueryString());
								GameWebSocketAccIdMapUtil.put(gameClient.gameClientAccId, m);								
								gameClient.addressSet.add(this.session.getQueryString());
							}
						}
						this.addressSet.add(msgAddress);
					} else if (cmd.equals(GameDefaultCmds.REMOVE_CLIENT)) {
						for (Iterator<String> iterator = this.addressSet.iterator(); iterator.hasNext();) {
							String s = iterator.next();
							if (msgAddress.equals(s)) {
								GameWebSocket gameClient = ((GameWebSocket) GameWebSocketMapUtil.get(msgAddress));
								if (gameClient != null) {
									synchronized (gameClient) {
										gameClient.sendMessage(msg);										
										if( gameClient.session.getQueryString().equals(GameWebSocketAccIdMapUtil.get(gameClient.gameClientAccId).get("clientWSId"))) {
											GameWebSocketAccIdMapUtil.remove(gameClient.gameClientAccId);											
											System.out.println("REMOVE_CLIENT" + GameWebSocketAccIdMapUtil.getValues().toString());
										}
										gameClient.addressSet.clear();										
									}
								}
								else {
									List<String>l = new ArrayList<String>(GameWebSocketAccIdMapUtil.getKeys());
									for(int i = 0;i < l.size(); i++) {										
										if(msgAddress.equals(GameWebSocketAccIdMapUtil.get(l.get(i)).get("clientWSId"))) {
											GameWebSocketAccIdMapUtil.remove(l.get(i));			
										}
									}
								}
								iterator.remove();
							}
						}						
					}else if (cmd.equals(GameDefaultCmds.REGIST_GAME_SERVER_ID)) {
						this.gameServerId = msg;				
					} else if (cmd.equals(GameDefaultCmds.REGIST_GAME_SERVER_BET)) {
						this.gameBet = msg;						
					} else if (cmd.equals(GameDefaultCmds.REGIST_GAME_SERVER_GAME_TYPE)) {
						this.gameType = msg;						
					} else if (cmd.equals(GameDefaultCmds.REGIST_GAME_SERVER_MAX_PLAYER)) {
						this.maxPlayer = Integer.parseInt(msg);						
					} else if (cmd.equals(GameDefaultCmds.NORMAL_CMD)) {
						GameWebSocket myWebSocket = ((GameWebSocket) GameWebSocketMapUtil.get(msgAddress));
						if (myWebSocket != null) {
							synchronized (myWebSocket) {
								myWebSocket.sendMessage(msg);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			try {
				new JSONArray(test);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

	@OnError
	public void onError(Session session, Throwable error) {
//		System.out.println("�ڥ���   "+error.toString());
//		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		try {
			if (this.session.isOpen()) {
				this.session.getBasicRemote().sendText(message, true);
			}
		} catch (Exception e) {
			if (this.session != null) {
				System.out.println(this.session.isOpen());
			} else {
				System.out.println("Session NULL");
			}
			// e.printStackTrace();
		}
	}

	public void sendMessageAll(String message) throws IOException {
		for (GameWebSocket myWebSocket : GameWebSocketMapUtil.getValues()) {
			myWebSocket.sendMessage(message);
		}
	}

}