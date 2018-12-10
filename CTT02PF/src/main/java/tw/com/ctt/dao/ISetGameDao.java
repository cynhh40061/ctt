package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

public interface ISetGameDao {

	public List<Map<String, Object>> getGameParamData(long accId);

	public boolean updateGameParamData(int sid, int bet, int gameTimesType, int gameTimeOut, int waitContinueTimeOut, int commission, int continueTimesType, long accId);

}
