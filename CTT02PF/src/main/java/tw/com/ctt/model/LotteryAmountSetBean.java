package tw.com.ctt.model;

import java.math.BigDecimal;

public class LotteryAmountSetBean implements java.io.Serializable {

	private static final long serialVersionUID = 3469172930913933323L;

	private int localId;

	private int typeId;

	private int midId;

	private int minId;

	private String minName;

	private BigDecimal baseBet;

	private BigDecimal betLevel1;

	private BigDecimal betLevel2;

	private BigDecimal baseline;

	private BigDecimal baselineLevel1;

	private BigDecimal baselineLevel2;

	private boolean dtSwitch;

	private String dtRatio;

	private BigDecimal dtBonus;

	private String playedText;

	private String lotteryRule;

	private String lotteryExample;

	private String prizeLevel;

	public int getLocalId() {
		return localId;
	}

	public void setLocalId(int localId) {
		this.localId = localId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getMidId() {
		return midId;
	}

	public void setMidId(int midId) {
		this.midId = midId;
	}

	public int getMinId() {
		return minId;
	}

	public void setMinId(int minId) {
		this.minId = minId;
	}

	public String getMinName() {
		return minName;
	}

	public void setMinName(String minName) {
		this.minName = minName;
	}

	public BigDecimal getBaseBet() {
		return baseBet;
	}

	public void setBaseBet(BigDecimal baseBet) {
		this.baseBet = baseBet;
	}

	public BigDecimal getBetLevel1() {
		return betLevel1;
	}

	public void setBetLevel1(BigDecimal betLevel1) {
		this.betLevel1 = betLevel1;
	}

	public BigDecimal getBetLevel2() {
		return betLevel2;
	}

	public void setBetLevel2(BigDecimal betLevel2) {
		this.betLevel2 = betLevel2;
	}

	public BigDecimal getBaseline() {
		return baseline;
	}

	public void setBaseline(BigDecimal baseline) {
		this.baseline = baseline;
	}

	public BigDecimal getBaselineLevel1() {
		return baselineLevel1;
	}

	public void setBaselineLevel1(BigDecimal baselineLevel1) {
		this.baselineLevel1 = baselineLevel1;
	}

	public BigDecimal getBaselineLevel2() {
		return baselineLevel2;
	}

	public void setBaselineLevel2(BigDecimal baselineLevel2) {
		this.baselineLevel2 = baselineLevel2;
	}

	public boolean isDtSwitch() {
		return dtSwitch;
	}

	public void setDtSwitch(boolean dtSwitch) {
		this.dtSwitch = dtSwitch;
	}

	public String getDtRatio() {
		return dtRatio;
	}

	public void setDtRatio(String dtRatio) {
		this.dtRatio = dtRatio;
	}

	public BigDecimal getDtBonus() {
		return dtBonus;
	}

	public void setDtBonus(BigDecimal dtBonus) {
		this.dtBonus = dtBonus;
	}

	public String getPlayedText() {
		return playedText;
	}

	public void setPlayedText(String playedText) {
		this.playedText = playedText;
	}

	public String getLotteryRule() {
		return lotteryRule;
	}

	public void setLotteryRule(String lotteryRule) {
		this.lotteryRule = lotteryRule;
	}

	public String getLotteryExample() {
		return lotteryExample;
	}

	public void setLotteryExample(String lotteryExample) {
		this.lotteryExample = lotteryExample;
	}

	public String getPrizeLevel() {
		return prizeLevel;
	}

	public void setPrizeLevel(String prizeLevel) {
		this.prizeLevel = prizeLevel;
	}

}
