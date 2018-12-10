package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

public interface IPortionAuthDao {

	public List<Map<String, Object>> getLevel3Auth(String url);

}
