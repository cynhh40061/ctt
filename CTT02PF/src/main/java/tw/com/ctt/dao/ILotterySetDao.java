package tw.com.ctt.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ILotterySetDao {

	public List<Map<String, Object>> getLotteryTypeList();

	public List<Map<String, Object>> getUpperSwitchInfo();

	public List<Map<String, Object>> getSwitchDetailTitle(int lotteryId);

	public List<Map<String, Object>> getSwitchDetail(int lotteryId);

	public boolean updateLotterySwitch(int lotteryId, int midId, int lotterySwitch);

	public List<Map<String, Object>> getLotteryAmountLocalList();

	public List<Map<String, Object>> getLotteryAmountPlayedList();

	public List<Map<String, Object>> getHandicapInfo();

	public List<Map<String, Object>> getTotalAmountInfo();

	public boolean upadteDescription(int minId, String playedText, String lotteryRule, String lotteryExample);

	boolean updateAmountInfo(int localId, int typeId, int minId, BigDecimal baseBet, BigDecimal betLevel1, BigDecimal betLevel2, BigDecimal baseline,
			BigDecimal baselineLevel1, BigDecimal baselineLevel2, boolean dtSwitch, BigDecimal dtRatio, BigDecimal dtBonus, int prizeLevel);

	public boolean upadteHandicap(int handicapId, BigDecimal bonusSetMin, BigDecimal bonusSetMax, int relativeBaseline, int maxWinBonus);

	public List<Map<String, Object>> getMidSwitchInfo(int localId);

	public boolean updateMidSwitch(int localId, int midId, int midSwitch);

	public boolean updateTotalSwitch(int localId, int totalSwitch);

	public boolean upadteZero();

	public List<Map<String, Object>> getLotterySwitchSettingLog(long opsAccId, int upperAction);

	public List<Map<String, Object>> getLotteryMidList();

	public List<Map<String, Object>> getLotteryAmountSettingLog(long opsAccId, int upperAction);

}
