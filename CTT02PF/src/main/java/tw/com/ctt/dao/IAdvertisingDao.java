package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

public interface IAdvertisingDao {

	public List<Map<String, Object>> getAllPlatformId(long accId);

	public List<Map<String, Object>> getAdData(int platformId);

//	public boolean addAdData(int platformId, String adPosition, String imgText);
	public boolean addAdData(int platformId);

	public boolean updateAdData(int platformId, String adPosition, String imgURL, String imgSRC);

	public boolean deleteAdData(int platformId, String adPosition);

	public boolean checkAdPosition(int platformId, String adPosition);
}
