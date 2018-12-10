package tw.com.ctt.dao;

import java.math.BigDecimal;

public interface IUpdateLotteryLowfreqInfoDao {
	public boolean upadteHandicap(int handicapId, BigDecimal bonusSetMin, BigDecimal bonusSetMax, int maxWinBonus);

	public boolean upadteDescription(int minId, String playedText, String lotteryRule, String lotteryExample);

	public boolean upadteZero();

	public boolean updateBaseAmount(int localId, int typeId, int minId, BigDecimal baseBet, BigDecimal baseline, boolean dtSwitch, BigDecimal dtRatio,
			BigDecimal dtBonus, int prizeLevel, int handicap);

	public boolean updateNowAmount(int localId, int typeId, int minId, BigDecimal nowBaseline, int prizeLevel, int handicap);

	public boolean updateMark6BaseAmount(int localId, int typeId, int minId, BigDecimal baseBet, BigDecimal baseline, int prizeLevel, int handicap);

	public boolean updateMark6NowAmount(int localId, int typeId, int minId, BigDecimal nowBaseline, int prizeLevel, int handicap);
}
