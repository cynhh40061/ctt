package tw.com.ctt.webSocket;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GameWebSocketMapUtil {
    public static ConcurrentMap<String, GameWebSocket> webSocketMap = new ConcurrentHashMap<>();
    
    public static Set<String> getKeys(){
        return webSocketMap.keySet();
    }
    
    public static void put(String key, GameWebSocket myWebSocket){
        webSocketMap.put(key, myWebSocket);
    }

    public static GameWebSocket get(String key){
         return webSocketMap.get(key);
    }

    public static void remove(String key){
         webSocketMap.remove(key);
    }

    public static Collection<GameWebSocket> getValues(){
        return webSocketMap.values();
    }
}