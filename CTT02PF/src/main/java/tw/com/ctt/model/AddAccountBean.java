package tw.com.ctt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class AddAccountBean implements Serializable {

	private static final long serialVersionUID = -6011143283515513605L;
	private int accId;
	private BigDecimal balance;
	private String accName;
	private int fullRatio;
	private int g1MaxRatio;
	private int g1MinRatio;
	private int g1CurrentRatio;
	private int g2MaxRatio;
	private int g2MinRatio;
	private int g2CurrentRatio;
	private int g3MaxRatio;
	private int g3MinRatio;
	private int g3CurrentRatio;
	private int g4MaxRatio;
	private int g4MinRatio;
	private int g4CurrentRatio;
	private int g5MaxRatio;
	private int g5MinRatio;
	private int g5CurrentRatio;
	private int accLevelType;
	private int handicap;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance.setScale(2, RoundingMode.HALF_UP);
	}

	public int getAccLevelType() {
		return accLevelType;
	}

	public void setAccLevelType(int accLevelType) {
		this.accLevelType = accLevelType;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public int getFullRatio() {
		return fullRatio;
	}

	public void setFullRatio(int fullRatio) {
		this.fullRatio = fullRatio;
	}

	public int getG1MaxRatio() {
		return g1MaxRatio;
	}

	public void setG1MaxRatio(int g1MaxRatio) {
		this.g1MaxRatio = g1MaxRatio;
	}

	public int getG1MinRatio() {
		return g1MinRatio;
	}

	public void setG1MinRatio(int g1MinRatio) {
		this.g1MinRatio = g1MinRatio;
	}

	public int getG1CurrentRatio() {
		return g1CurrentRatio;
	}

	public void setG1CurrentRatio(int g1CurrentRatio) {
		this.g1CurrentRatio = g1CurrentRatio;
	}

	public int getG2MaxRatio() {
		return g2MaxRatio;
	}

	public void setG2MaxRatio(int g2MaxRatio) {
		this.g2MaxRatio = g2MaxRatio;
	}

	public int getG2MinRatio() {
		return g2MinRatio;
	}

	public void setG2MinRatio(int g2MinRatio) {
		this.g2MinRatio = g2MinRatio;
	}

	public int getG2CurrentRatio() {
		return g2CurrentRatio;
	}

	public void setG2CurrentRatio(int g2CurrentRatio) {
		this.g2CurrentRatio = g2CurrentRatio;
	}

	public int getG3MaxRatio() {
		return g3MaxRatio;
	}

	public void setG3MaxRatio(int g3MaxRatio) {
		this.g3MaxRatio = g3MaxRatio;
	}

	public int getG3MinRatio() {
		return g3MinRatio;
	}

	public void setG3MinRatio(int g3MinRatio) {
		this.g3MinRatio = g3MinRatio;
	}

	public int getG3CurrentRatio() {
		return g3CurrentRatio;
	}

	public void setG3CurrentRatio(int g3CurrentRatio) {
		this.g3CurrentRatio = g3CurrentRatio;
	}

	public int getG4MaxRatio() {
		return g4MaxRatio;
	}

	public void setG4MaxRatio(int g4MaxRatio) {
		this.g4MaxRatio = g4MaxRatio;
	}

	public int getG4MinRatio() {
		return g4MinRatio;
	}

	public void setG4MinRatio(int g4MinRatio) {
		this.g4MinRatio = g4MinRatio;
	}

	public int getG4CurrentRatio() {
		return g4CurrentRatio;
	}

	public void setG4CurrentRatio(int g4CurrentRatio) {
		this.g4CurrentRatio = g4CurrentRatio;
	}

	public int getG5MaxRatio() {
		return g5MaxRatio;
	}

	public void setG5MaxRatio(int g5MaxRatio) {
		this.g5MaxRatio = g5MaxRatio;
	}

	public int getG5MinRatio() {
		return g5MinRatio;
	}

	public void setG5MinRatio(int g5MinRatio) {
		this.g5MinRatio = g5MinRatio;
	}

	public int getG5CurrentRatio() {
		return g5CurrentRatio;
	}

	public void setG5CurrentRatio(int g5CurrentRatio) {
		this.g5CurrentRatio = g5CurrentRatio;
	}

	public int getHandicap() {
		return handicap;
	}

	public void setHandicap(int handicap) {
		this.handicap = handicap;
	}

	@Override
	public String toString() {
		return "AddAccountBean [accId=" + accId + ", balance=" + balance + ", accName=" + accName + ", fullRatio=" + fullRatio + ", g1MaxRatio="
				+ g1MaxRatio + ", g1MinRatio=" + g1MinRatio + ", g1CurrentRatio=" + g1CurrentRatio + ", g2MaxRatio=" + g2MaxRatio + ", g2MinRatio="
				+ g2MinRatio + ", g2CurrentRatio=" + g2CurrentRatio + ", g3MaxRatio=" + g3MaxRatio + ", g3MinRatio=" + g3MinRatio
				+ ", g3CurrentRatio=" + g3CurrentRatio + ", g4MaxRatio=" + g4MaxRatio + ", g4MinRatio=" + g4MinRatio + ", g4CurrentRatio="
				+ g4CurrentRatio + ", g5MaxRatio=" + g5MaxRatio + ", g5MinRatio=" + g5MinRatio + ", g5CurrentRatio=" + g5CurrentRatio + "]";
	}

}
