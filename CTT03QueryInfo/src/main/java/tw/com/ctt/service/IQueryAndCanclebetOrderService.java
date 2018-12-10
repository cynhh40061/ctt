package tw.com.ctt.service;

import java.util.Map;

public interface IQueryAndCanclebetOrderService {

	public Map<String, Object> searchMidBetOrder(long accId);

	public Map<String, Object> searchMainBetOrder(long accId);

}
