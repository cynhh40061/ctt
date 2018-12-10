package tw.com.ctt.server;

import java.io.IOException;
import java.net.URI;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.OnError;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@ClientEndpoint
public class GameServerWSClient {
	
	private static final Logger LOG = LogManager.getLogger(GameServerWSClient.class.getName());
	
	 Session userSession = null;
	    private MessageHandler messageHandler;
	    private int wsIndex;
	    private boolean isSuccess = false;
	    private URI endpointURI;
	    private WebSocketContainer container;
	    
	    public boolean isSuccess() {
			return isSuccess;
		}

		public GameServerWSClient(URI endpointURI, int wsIndex) {
	        	this.endpointURI = endpointURI;
	        	this.wsIndex = wsIndex;
	        	container = ContainerProvider.getWebSocketContainer();
	    }
		
		public void connect() {
//			LOG.debug("Re " +wsIndex);
			try {	            
	            container.connectToServer(this, endpointURI);
	        } catch (DeploymentException e) {
            } catch (IOException e) {
            } 
		}

	    /**
	     * Callback hook for Connection open events.
	     *
	     * @param userSession the userSession which is opened.
	     */
	    @OnOpen
	    public void onOpen(Session userSession) {
	    	isSuccess = true;
	        LOG.debug("open "  +wsIndex);
	        this.userSession = userSession;
	    }

	    /**
	     * Callback hook for Connection close events.
	     *
	     * @param userSession the userSession which is getting closed.
	     * @param reason the reason for connection close
	     */
	    @OnClose
	    public void onClose(Session userSession, CloseReason reason) {
	        LOG.debug("close "  +wsIndex);
	        isSuccess = false;
	        this.userSession = null;
	    }

	    
	    @OnError
	    public void OnError(Session userSession, Throwable t) {
	        LOG.debug("error " + t.toString());
	        isSuccess = false;
	    }
	    
	    
	    /**
	     * Callback hook for Message Events. This method will be invoked when a client send a message.
	     *
	     * @param message The text message
	     */
	    @OnMessage
	    public void onMessage(String message) {
	        if (this.messageHandler != null) {
	            this.messageHandler.handleMessage(message, this.wsIndex);
	        }
	    }

	    /**
	     * register message handler
	     *
	     * @param msgHandler
	     */
	    public void addMessageHandler(MessageHandler msgHandler) {
	        this.messageHandler = msgHandler;
	    }

	    /**
	     * Send a message.
	     *
	     * @param message
	     */
	    public void sendMessage(String message) {
	        this.userSession.getAsyncRemote().sendText(message);
	    }

	    /**
	     * Message handler.
	     *
	     * @author Jiji_Sasidharan
	     */
	    public static interface MessageHandler {

	        public void handleMessage(String message,int wsIndex);
	    }
	    
	    public void closeSession() {
	    	try {
				userSession.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
}
