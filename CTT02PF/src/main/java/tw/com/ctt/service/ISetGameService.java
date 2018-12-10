package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

public interface ISetGameService {

	public List<Map<String, Object>> getGameParamData();

	public boolean updateGameParamData(String updateObj);

}
