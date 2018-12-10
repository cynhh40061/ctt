package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

public interface IGameRecordsDao {

	public int searchRecordsTotleCount(long opsAccId, long gameId, String accName, String startTime, String endTime, int gameType);
	
	public List<Map<String, Object>> searchRecords(long opsAccId, long gameId, String accName, String startTime, String endTime, int gameType, int firstCount, int count);

}
