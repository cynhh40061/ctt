package tw.com.ctt.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class SubOrderInfoBean implements Serializable {
	private static final long serialVersionUID = 3157478025557305706L;
	
	private int orderId;
	private String orderType;
	private String gameType;
	private String orderData;
	private int numOfPeriod;
	private String createDate;
	private String betOfOneTicket;
	private String numOfTicket;
	private String totalBet;
	private String prize;
	
	
	
	private int parentOrderId;
	
}