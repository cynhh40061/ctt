package tw.com.ctt.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface IBaselineInfoService {
	public Map<String, Object> getBaseline();

	public long getBaselineLastUpdateTime();

	public Map<String, Object> getBaselineLF();

	public long getBaselineLastUpdateTimeLF();
}
