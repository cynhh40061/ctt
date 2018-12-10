package tw.com.ctt.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IUpdateLotteryLowfreqInfoSetService {
	public Boolean upadteHandicap(int handicapId, BigDecimal bonusSetMin, BigDecimal bonusSetMax, int maxWinBonus, String log);

	public Boolean upadteDescription(int minId, String playedText, String lotteryRule, String lotteryExample, String log);

	public Boolean updateBaseAmount(List<Map<String, Object>> mapList, String amountLogList);

	public Boolean updateNowAmount(List<Map<String, Object>> mapList, String logList);

	public Boolean updateMark6BaseAmount(List<Map<String, Object>> mapList, String amountLogList);

	public Boolean updateMark6NowAmount(List<Map<String, Object>> mapList, String logList);

}
