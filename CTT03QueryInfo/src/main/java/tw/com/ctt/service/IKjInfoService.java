package tw.com.ctt.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface IKjInfoService {
	public Map<String, Object> getAllKjTimeStatus();
	public Map<String, Object> getKjHistoryTimeStatus();
	public long getKjLastUpdateTime();
}
