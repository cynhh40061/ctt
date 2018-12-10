package tw.com.ctt.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface IBetOrderService {
	public Map<String, Object> getBetOrderInfo(long accId);	
}
