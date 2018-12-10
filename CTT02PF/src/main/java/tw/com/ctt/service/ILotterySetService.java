package tw.com.ctt.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ILotterySetService {

	public List<Map<String, Object>> getLotteryTypeList();

	public List<Map<String, Object>> getUpperSwitchInfo();

	public List<Map<String, Object>> getSwitchDetailTitle(int lotteryId);

	public List<Map<String, Object>> getSwitchDetail(int lotteryId);

	public Boolean updateLotterySwitch(int lotteryId, List<Map<String, Object>> mapList, int totalSwitch);

	public List<Map<String, Object>> getLotteryAmountLocalList();

	public List<Map<String, Object>> getLotteryAmountPlayedList();

	public List<Map<String, Object>> getHandicapInfo();

	public List<Map<String, Object>> getTotalAmountInfo();

	public Boolean upadteDescription(int minId, String playedText, String lotteryRule, String lotteryExample, String descriptionLog);

	public Boolean updateAmountInfo(List<Map<String, Object>> mapList, String amountLogList);

	public Boolean upadteHandicap(int handicapId, BigDecimal bonusSetMin, BigDecimal bonusSetMax, int relativeBaseline, int maxWinBonus,
			String handicapLog);

	public List<Map<String, Object>> getLotterySwitchSettingLog();

	public List<Map<String, Object>> getLotteryMidList();

	public List<Map<String, Object>> getLotteryAmountSettingLog();

}
