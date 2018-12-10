package tw.com.ctt.webSocket;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CacheWebSocketMapUtil {
    public static ConcurrentMap<String, CacheWebSocket> webSocketMap = new ConcurrentHashMap<>();
    public static void put(String key, CacheWebSocket myWebSocket){
        webSocketMap.put(key, myWebSocket);
    }

    public static CacheWebSocket get(String key){
         return webSocketMap.get(key);
    }

    public static void remove(String key){
         webSocketMap.remove(key);
    }

    public static Collection<CacheWebSocket> getValues(){
        return webSocketMap.values();
    }
}