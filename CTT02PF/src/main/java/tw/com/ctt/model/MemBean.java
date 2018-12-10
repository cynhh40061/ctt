package tw.com.ctt.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MemBean implements Serializable {
	private static final long serialVersionUID = 3250742988552172427L;
	private long accId;
	private String accName;
	private String pwd;
	private int pwdStatus;
	private String pwdWithdraw;
	private int pwdWithdrawStatus;
	private String nickname;
	private int memberType;
	private BigDecimal lockedAmount;
	private BigDecimal balance;
	private int loginFailed;
	private int pwdWithdrawFailed;

	private int upperAccLevelType;
	private long sc;
	private long bc;
	private long co;
	private long sa;
	private long ag;
	private int accStatus;

	private int gameType;
	private int upperMinRatio;
	private int nextUpperMinRatio;

	private int g1MinRatio;
	private int g2MinRatio;
	private int g3MinRatio;
	private int g4MinRatio;
	private int g5MinRatio;
	private int g1MinRatioNext;
	private int g2MinRatioNext;
	private int g3MinRatioNext;
	private int g4MinRatioNext;
	private int g5MinRatioNext;
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

	public int getPwdStatus() {
		return pwdStatus;
	}

	public void setPwdStatus(int pwdStatus) {
		this.pwdStatus = pwdStatus;
	}

	public String getPwdWithdraw() {
		return pwdWithdraw;
	}

	public void setPwdWithdraw(String pwdWithdraw) {
		this.pwdWithdraw = pwdWithdraw;
	}

	public int getPwdWithdrawStatus() {
		return pwdWithdrawStatus;
	}

	public void setPwdWithdrawStatus(int pwdWithdrawStatus) {
		this.pwdWithdrawStatus = pwdWithdrawStatus;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getMemberType() {
		return memberType;
	}

	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}

	public BigDecimal getLockedAmount() {
		return lockedAmount;
	}

	public void setLockedAmount(BigDecimal lockedAmount) {
		this.lockedAmount = lockedAmount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public int getLoginFailed() {
		return loginFailed;
	}

	public void setLoginFailed(int loginFailed) {
		this.loginFailed = loginFailed;
	}

	public int getPwdWithdrawFailed() {
		return pwdWithdrawFailed;
	}

	public void setPwdWithdrawFailed(int pwdWithdrawFailed) {
		this.pwdWithdrawFailed = pwdWithdrawFailed;
	}

	public int getUpperAccLevelType() {
		return upperAccLevelType;
	}

	public void setUpperAccLevelType(int upperAccLevelType) {
		this.upperAccLevelType = upperAccLevelType;
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

	public int getNextUpperMinRatio() {
		return nextUpperMinRatio;
	}

	public void setNextUpperMinRatio(int nextUpperMinRatio) {
		this.nextUpperMinRatio = nextUpperMinRatio;
	}

	public int getG1MinRatio() {
		return g1MinRatio;
	}

	public void setG1MinRatio(int g1MinRatio) {
		this.g1MinRatio = g1MinRatio;
	}

	public int getG2MinRatio() {
		return g2MinRatio;
	}

	public void setG2MinRatio(int g2MinRatio) {
		this.g2MinRatio = g2MinRatio;
	}

	public int getG3MinRatio() {
		return g3MinRatio;
	}

	public void setG3MinRatio(int g3MinRatio) {
		this.g3MinRatio = g3MinRatio;
	}

	public int getG4MinRatio() {
		return g4MinRatio;
	}

	public void setG4MinRatio(int g4MinRatio) {
		this.g4MinRatio = g4MinRatio;
	}

	public int getG5MinRatio() {
		return g5MinRatio;
	}

	public void setG5MinRatio(int g5MinRatio) {
		this.g5MinRatio = g5MinRatio;
	}

	public int getG1MinRatioNext() {
		return g1MinRatioNext;
	}

	public void setG1MinRatioNext(int g1MinRatioNext) {
		this.g1MinRatioNext = g1MinRatioNext;
	}

	public int getG2MinRatioNext() {
		return g2MinRatioNext;
	}

	public void setG2MinRatioNext(int g2MinRatioNext) {
		this.g2MinRatioNext = g2MinRatioNext;
	}

	public int getG3MinRatioNext() {
		return g3MinRatioNext;
	}

	public void setG3MinRatioNext(int g3MinRatioNext) {
		this.g3MinRatioNext = g3MinRatioNext;
	}

	public int getG4MinRatioNext() {
		return g4MinRatioNext;
	}

	public void setG4MinRatioNext(int g4MinRatioNext) {
		this.g4MinRatioNext = g4MinRatioNext;
	}

	public int getG5MinRatioNext() {
		return g5MinRatioNext;
	}

	public void setG5MinRatioNext(int g5MinRatioNext) {
		this.g5MinRatioNext = g5MinRatioNext;
	}

}
