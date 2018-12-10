package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

public interface IGameRecordsService {


	public int searchRecordsTotleCount(long gameId, String accName, String startTime, String endTime, int gameType);

	public List<Map<String, Object>> searchRecords(long gameId, String accName, String startTime, String endTime, int gameType, int firstCount, int count);

}
