package tw.com.ctt.model;

import java.math.BigDecimal;

public class MgrBean implements java.io.Serializable {
	private static final long serialVersionUID = -4960767741224737043L;
	private long accId;
	private String accName;
	private String pwd;
	private String nickname;
	private int authGroup;
	private String groupText;
	private int fullRatio;
	private int nextFullRatio;
	private BigDecimal balance;

	private BigDecimal lowerLevelAccBalance;

	private int accLevelType;
	private long sc;
	private long bc;
	private long co;
	private long sa;
	private long ag;
	private int accStatus;

	private int gameType;
	private int upperMinRatio;
	private int upperMaxRatio;
	private int currentMaxRatio;
	private int nextUpperMinRatio;
	private int nextUpperMaxRatio;
	private int nextCurrentMaxRatio;

	private int g1MinRatio;
	private int g1MaxRatio;
	private int g1CurrentMaxRatio;
	private int g2MinRatio;
	private int g2MaxRatio;
	private int g2CurrentMaxRatio;
	private int g3MinRatio;
	private int g3MaxRatio;
	private int g3CurrentMaxRatio;
	private int g4MinRatio;
	private int g4MaxRatio;
	private int g4CurrentMaxRatio;
	private int g5MinRatio;
	private int g5MaxRatio;
	private int g5CurrentMaxRatio;
	private int g1MinRatioNext;
	private int g1MaxRatioNext;
	private int g1CurrentMaxRatioNext;
	private int g2MinRatioNext;
	private int g2MaxRatioNext;
	private int g2CurrentMaxRatioNext;
	private int g3MinRatioNext;
	private int g3MaxRatioNext;
	private int g3CurrentMaxRatioNext;
	private int g4MinRatioNext;
	private int g4MaxRatioNext;
	private int g4CurrentMaxRatioNext;
	private int g5MinRatioNext;
	private int g5MaxRatioNext;
	private int g5CurrentMaxRatioNext;
	private int handicap;

	public int getHandicap() {
		return handicap;
	}

	public void setHandicap(int handicap) {
		this.handicap = handicap;
	}

	public long getAccId() {
		return accId;
	}

	public void setAccId(long accId) {
		this.accId = accId;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getAuthGroup() {
		return authGroup;
	}

	public void setAuthGroup(int authGroup) {
		this.authGroup = authGroup;
	}

	public int getFullRatio() {
		return fullRatio;
	}

	public void setFullRatio(int fullRatio) {
		this.fullRatio = fullRatio;
	}

	public int getNextFullRatio() {
		return nextFullRatio;
	}

	public void setNextFullRatio(int nextFullRatio) {
		this.nextFullRatio = nextFullRatio;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getLowerLevelAccBalance() {
		return lowerLevelAccBalance;
	}

	public void setLowerLevelAccBalance(BigDecimal lowerLevelAccBalance) {
		this.lowerLevelAccBalance = lowerLevelAccBalance;
	}

	public int getAccLevelType() {
		return accLevelType;
	}

	public void setAccLevelType(int accLevelType) {
		this.accLevelType = accLevelType;
	}

	public long getSc() {
		return sc;
	}

	public void setSc(long sc) {
		this.sc = sc;
	}

	public long getBc() {
		return bc;
	}

	public void setBc(long bc) {
		this.bc = bc;
	}

	public long getCo() {
		return co;
	}

	public void setCo(long co) {
		this.co = co;
	}

	public long getSa() {
		return sa;
	}

	public void setSa(long sa) {
		this.sa = sa;
	}

	public long getAg() {
		return ag;
	}

	public void setAg(long ag) {
		this.ag = ag;
	}

	public int getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(int accStatus) {
		this.accStatus = accStatus;
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public int getUpperMinRatio() {
		return upperMinRatio;
	}

	public void setUpperMinRatio(int upperMinRatio) {
		this.upperMinRatio = upperMinRatio;
	}

	public int getUpperMaxRatio() {
		return upperMaxRatio;
	}

	public void setUpperMaxRatio(int upperMaxRatio) {
		this.upperMaxRatio = upperMaxRatio;
	}

	public int getCurrentMaxRatio() {
		return currentMaxRatio;
	}

	public void setCurrentMaxRatio(int currentMaxRatio) {
		this.currentMaxRatio = currentMaxRatio;
	}

	public int getNextUpperMinRatio() {
		return nextUpperMinRatio;
	}

	public void setNextUpperMinRatio(int nextUpperMinRatio) {
		this.nextUpperMinRatio = nextUpperMinRatio;
	}

	public int getNextUpperMaxRatio() {
		return nextUpperMaxRatio;
	}

	public void setNextUpperMaxRatio(int nextUpperMaxRatio) {
		this.nextUpperMaxRatio = nextUpperMaxRatio;
	}

	public int getNextCurrentMaxRatio() {
		return nextCurrentMaxRatio;
	}

	public void setNextCurrentMaxRatio(int nextCurrentMaxRatio) {
		this.nextCurrentMaxRatio = nextCurrentMaxRatio;
	}

	public int getG1MinRatio() {
		return g1MinRatio;
	}

	public void setG1MinRatio(int g1MinRatio) {
		this.g1MinRatio = g1MinRatio;
	}

	public int getG1MaxRatio() {
		return g1MaxRatio;
	}

	public void setG1MaxRatio(int g1MaxRatio) {
		this.g1MaxRatio = g1MaxRatio;
	}

	public int getG1CurrentMaxRatio() {
		return g1CurrentMaxRatio;
	}

	public void setG1CurrentMaxRatio(int g1CurrentMaxRatio) {
		this.g1CurrentMaxRatio = g1CurrentMaxRatio;
	}

	public int getG2MinRatio() {
		return g2MinRatio;
	}

	public void setG2MinRatio(int g2MinRatio) {
		this.g2MinRatio = g2MinRatio;
	}

	public int getG2MaxRatio() {
		return g2MaxRatio;
	}

	public void setG2MaxRatio(int g2MaxRatio) {
		this.g2MaxRatio = g2MaxRatio;
	}

	public int getG2CurrentMaxRatio() {
		return g2CurrentMaxRatio;
	}

	public void setG2CurrentMaxRatio(int g2CurrentMaxRatio) {
		this.g2CurrentMaxRatio = g2CurrentMaxRatio;
	}

	public int getG3MinRatio() {
		return g3MinRatio;
	}

	public void setG3MinRatio(int g3MinRatio) {
		this.g3MinRatio = g3MinRatio;
	}

	public int getG3MaxRatio() {
		return g3MaxRatio;
	}

	public void setG3MaxRatio(int g3MaxRatio) {
		this.g3MaxRatio = g3MaxRatio;
	}

	public int getG3CurrentMaxRatio() {
		return g3CurrentMaxRatio;
	}

	public void setG3CurrentMaxRatio(int g3CurrentMaxRatio) {
		this.g3CurrentMaxRatio = g3CurrentMaxRatio;
	}

	public int getG4MinRatio() {
		return g4MinRatio;
	}

	public void setG4MinRatio(int g4MinRatio) {
		this.g4MinRatio = g4MinRatio;
	}

	public int getG4MaxRatio() {
		return g4MaxRatio;
	}

	public void setG4MaxRatio(int g4MaxRatio) {
		this.g4MaxRatio = g4MaxRatio;
	}

	public int getG4CurrentMaxRatio() {
		return g4CurrentMaxRatio;
	}

	public void setG4CurrentMaxRatio(int g4CurrentMaxRatio) {
		this.g4CurrentMaxRatio = g4CurrentMaxRatio;
	}

	public int getG5MinRatio() {
		return g5MinRatio;
	}

	public void setG5MinRatio(int g5MinRatio) {
		this.g5MinRatio = g5MinRatio;
	}

	public int getG5MaxRatio() {
		return g5MaxRatio;
	}

	public void setG5MaxRatio(int g5MaxRatio) {
		this.g5MaxRatio = g5MaxRatio;
	}

	public int getG5CurrentMaxRatio() {
		return g5CurrentMaxRatio;
	}

	public void setG5CurrentMaxRatio(int g5CurrentMaxRatio) {
		this.g5CurrentMaxRatio = g5CurrentMaxRatio;
	}

	public int getG1MinRatioNext() {
		return g1MinRatioNext;
	}

	public void setG1MinRatioNext(int g1MinRatioNext) {
		this.g1MinRatioNext = g1MinRatioNext;
	}

	public int getG1MaxRatioNext() {
		return g1MaxRatioNext;
	}

	public void setG1MaxRatioNext(int g1MaxRatioNext) {
		this.g1MaxRatioNext = g1MaxRatioNext;
	}

	public int getG1CurrentMaxRatioNext() {
		return g1CurrentMaxRatioNext;
	}

	public void setG1CurrentMaxRatioNext(int g1CurrentMaxRatioNext) {
		this.g1CurrentMaxRatioNext = g1CurrentMaxRatioNext;
	}

	public int getG2MinRatioNext() {
		return g2MinRatioNext;
	}

	public void setG2MinRatioNext(int g2MinRatioNext) {
		this.g2MinRatioNext = g2MinRatioNext;
	}

	public int getG2MaxRatioNext() {
		return g2MaxRatioNext;
	}

	public void setG2MaxRatioNext(int g2MaxRatioNext) {
		this.g2MaxRatioNext = g2MaxRatioNext;
	}

	public int getG2CurrentMaxRatioNext() {
		return g2CurrentMaxRatioNext;
	}

	public void setG2CurrentMaxRatioNext(int g2CurrentMaxRatioNext) {
		this.g2CurrentMaxRatioNext = g2CurrentMaxRatioNext;
	}

	public int getG3MinRatioNext() {
		return g3MinRatioNext;
	}

	public void setG3MinRatioNext(int g3MinRatioNext) {
		this.g3MinRatioNext = g3MinRatioNext;
	}

	public int getG3MaxRatioNext() {
		return g3MaxRatioNext;
	}

	public void setG3MaxRatioNext(int g3MaxRatioNext) {
		this.g3MaxRatioNext = g3MaxRatioNext;
	}

	public int getG3CurrentMaxRatioNext() {
		return g3CurrentMaxRatioNext;
	}

	public void setG3CurrentMaxRatioNext(int g3CurrentMaxRatioNext) {
		this.g3CurrentMaxRatioNext = g3CurrentMaxRatioNext;
	}

	public int getG4MinRatioNext() {
		return g4MinRatioNext;
	}

	public void setG4MinRatioNext(int g4MinRatioNext) {
		this.g4MinRatioNext = g4MinRatioNext;
	}

	public int getG4MaxRatioNext() {
		return g4MaxRatioNext;
	}

	public void setG4MaxRatioNext(int g4MaxRatioNext) {
		this.g4MaxRatioNext = g4MaxRatioNext;
	}

	public int getG4CurrentMaxRatioNext() {
		return g4CurrentMaxRatioNext;
	}

	public void setG4CurrentMaxRatioNext(int g4CurrentMaxRatioNext) {
		this.g4CurrentMaxRatioNext = g4CurrentMaxRatioNext;
	}

	public int getG5MinRatioNext() {
		return g5MinRatioNext;
	}

	public void setG5MinRatioNext(int g5MinRatioNext) {
		this.g5MinRatioNext = g5MinRatioNext;
	}

	public int getG5MaxRatioNext() {
		return g5MaxRatioNext;
	}

	public void setG5MaxRatioNext(int g5MaxRatioNext) {
		this.g5MaxRatioNext = g5MaxRatioNext;
	}

	public int getG5CurrentMaxRatioNext() {
		return g5CurrentMaxRatioNext;
	}

	public void setG5CurrentMaxRatioNext(int g5CurrentMaxRatioNext) {
		this.g5CurrentMaxRatioNext = g5CurrentMaxRatioNext;
	}

	public String getGroupText() {
		return groupText;
	}

	public void setGroupText(String groupText) {
		this.groupText = groupText;
	}

}
