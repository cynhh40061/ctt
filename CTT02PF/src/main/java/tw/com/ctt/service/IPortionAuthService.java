package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

public interface IPortionAuthService {

	public List<Map<String, Object>> getLevel3Auth(String url);
}
