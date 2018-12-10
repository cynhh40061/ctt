package tw.com.ctt.webSocket;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@ServerEndpoint("/cacheWS")
public class CacheWebSocket {

	private Session session;

	@OnOpen

	public void onOpen(Session session) throws Exception {
		this.session = session;
		CacheWebSocketMapUtil.put(session.getQueryString(), this);
//		System.out.println("OPEN " + session.getQueryString());
	}

	@OnClose
	public void onClose() throws Exception {
//		System.out.println("CLOSE " + session.getQueryString());
		GameWebSocketMapUtil.remove(session.getQueryString());
	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		try {			
			if (session.getQueryString().indexOf("client") == 0) {
//				System.out.println("get Client Msg  " + session.getQueryString()+" : "+ message); 
				CacheWebSocket myWebSocket = ((CacheWebSocket) CacheWebSocketMapUtil.get("server"));
				if (myWebSocket != null) {					
					synchronized (myWebSocket) {
						myWebSocket.sendMessage(message);
			        }
				}			
			} else {
//				System.out.println("get Server Msg  "+ session.getQueryString()+" : "+  message); 
				if(isJSONValid(message)) {
					JSONObject  jsonObj = new JSONObject(message);
					String msgAddress = ""+jsonObj.getString("to");
					String msg = ""+jsonObj.getString("msg");
//					System.out.println(msgAddress +"   "+ msg); 
					CacheWebSocket myWebSocket = ((CacheWebSocket) CacheWebSocketMapUtil.get(msgAddress));
					if (myWebSocket != null) {						
						synchronized (myWebSocket) {
							myWebSocket.sendMessage(msg);
				        }
					}					
				}			
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
//		this.session.getAsyncRemote().sendText(message);
	}

	public void sendMessageAll(String message) throws IOException {
		for (CacheWebSocket myWebSocket : CacheWebSocketMapUtil.getValues()) {
			myWebSocket.sendMessage(message);
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

}