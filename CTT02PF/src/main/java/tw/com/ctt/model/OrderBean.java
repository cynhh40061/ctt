package tw.com.ctt.model;

import java.math.BigDecimal;

public class OrderBean implements java.io.Serializable {

	private static final long serialVersionUID = 3259058430021613L;

	private long opsAccId;
	private long accId;
	private String opsAccName;
	private String accName;
	private long dataId;// orderid
	private int opsType;
	private int action;
	private String detail;
	private String opsDateTime;
	private String ip;

	private BigDecimal amount;
	private String bankAccName;
	private String bankAcc;
	private String bank;
	private String note;
	private String bankSid;
	private BigDecimal bankCheckAmount;
	private String bankDepositDatetime;
	private String remark;// 備註=remark
	private int backOrderStatus;// 水單型態:補單、會員水單
	private int currency;

	private int rechargeType;// 提款帳單類型:網銀、API
	private String createDatetime;
	private long firstAuditor;
	private String firstDatetime;
	private long secondAuditor;
	private String secondDatetime;
	private long lastAuditor;
	private int status;

	public long getOpsAccId() {
		return opsAccId;
	}

	public void setOpsAccId(long opsAccId) {
		this.opsAccId = opsAccId;
	}

	public long getAccId() {
		return accId;
	}

	public void setAccId(long accId) {
		this.accId = accId;
	}

	public String getOpsAccName() {
		return opsAccName;
	}

	public void setOpsAccName(String opsAccName) {
		this.opsAccName = opsAccName;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public long getDataId() {
		return dataId;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
	}

	public int getOpsType() {
		return opsType;
	}

	public void setOpsType(int opsType) {
		this.opsType = opsType;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getOpsDateTime() {
		return opsDateTime;
	}

	public void setOpsDateTime(String opsDateTime) {
		this.opsDateTime = opsDateTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBankAccName() {
		return bankAccName;
	}

	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}

	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getBankSid() {
		return bankSid;
	}

	public void setBankSid(String bankSid) {
		this.bankSid = bankSid;
	}

	public BigDecimal getBankCheckAmount() {
		return bankCheckAmount;
	}

	public void setBankCheckAmount(BigDecimal bankCheckAmount) {
		this.bankCheckAmount = bankCheckAmount;
	}

	public String getBankDepositDatetime() {
		return bankDepositDatetime;
	}

	public void setBankDepositDatetime(String bankDepositDatetime) {
		this.bankDepositDatetime = bankDepositDatetime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getBackOrderStatus() {
		return backOrderStatus;
	}

	public void setBackOrderStatus(int backOrderStatus) {
		this.backOrderStatus = backOrderStatus;
	}

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public int getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(int rechargeType) {
		this.rechargeType = rechargeType;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public long getFirstAuditor() {
		return firstAuditor;
	}

	public void setFirstAuditor(long firstAuditor) {
		this.firstAuditor = firstAuditor;
	}

	public String getFirstDatetime() {
		return firstDatetime;
	}

	public void setFirstDatetime(String firstDatetime) {
		this.firstDatetime = firstDatetime;
	}

	public long getSecondAuditor() {
		return secondAuditor;
	}

	public void setSecondAuditor(long secondAuditor) {
		this.secondAuditor = secondAuditor;
	}

	public String getSecondDatetime() {
		return secondDatetime;
	}

	public void setSecondDatetime(String secondDatetime) {
		this.secondDatetime = secondDatetime;
	}

	public long getLastAuditor() {
		return lastAuditor;
	}

	public void setLastAuditor(long lastAuditor) {
		this.lastAuditor = lastAuditor;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
