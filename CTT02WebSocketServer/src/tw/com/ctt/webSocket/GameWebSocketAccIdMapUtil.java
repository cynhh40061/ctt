package tw.com.ctt.webSocket;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GameWebSocketAccIdMapUtil {
    public static ConcurrentMap<String, Map<String,String>> accIdMap = new ConcurrentHashMap<>();
    
    public static Set<String> getKeys(){
        return accIdMap.keySet();
    }
    
    public static void put(String accId, Map<String,String> Obj){
    	accIdMap.put(accId, Obj);
    }

    public static Map<String,String> get(String accId){
         return accIdMap.get(accId);
    }

    public static void remove(String accId){
    	accIdMap.remove(accId);
    }

    public static Collection<Map<String,String>> getValues(){
        return accIdMap.values();
    }
}