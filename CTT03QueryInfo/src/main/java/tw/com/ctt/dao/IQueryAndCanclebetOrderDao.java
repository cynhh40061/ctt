package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

public interface IQueryAndCanclebetOrderDao {

	public Map<String, Object> searchMidByUnionTable(long accId, List<String> tableDateList);

	public Map<String, Object> searchMainByUnionTable(long accId, List<String> tableDateList);

	public List<String> getTableDate(String format);

}
