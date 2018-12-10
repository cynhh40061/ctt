package tw.com.ctt.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlayerInfoBean implements Serializable {
	private static final long serialVersionUID = 3157478025557305706L;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	private int accId;
	private String accName;
	private String nickname;
	private BigDecimal balance;
}