package tw.com.ctt.model;

import static tw.com.ctt.constant.LotteryConstant.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import tw.com.ctt.constant.LotteryUtilConstant;

import tw.com.ctt.util.MyUtil;

public class LotteryTypeBean {
	
	private String name;
	private List<LotteryNumBean> lotteryNumBeanList;
	private int id ;
	private String issueFormat;
	private long checkNowDataTime; 
	private long checkYesterDayDataTime;
	private int noLotteryCount ;
	private int createPeriodType;
	private int zodiacType;
	private boolean lotteryLowfreq;
	
	public List<LotteryNumBean> nowLotteryBeanList;
	
	public long getCheckNowDataTime() {
		return checkNowDataTime;
	}
	public void setCheckNowDataTime(long checkNowDataTime) {
		long nowLong = LotteryUtilConstant.getNowDate().getTime();
		this.checkNowDataTime = nowLong+checkNowDataTime;
	}
	public long getCheckYesterDayDataTime() {
		return checkYesterDayDataTime;
	}
	public void setCheckYesterDayDataTime(long checkYesterDayDataTime) {
		long nowLong = LotteryUtilConstant.getNowDate().getTime();
		this.checkYesterDayDataTime = nowLong+checkYesterDayDataTime;
	}

	
	public LotteryTypeBean() {
		this.createPeriodType = 0;
		this.noLotteryCount = 0;
		this.checkNowDataTime = 0;
		this.checkYesterDayDataTime = 0;
		this.nowLotteryBeanList = new CopyOnWriteArrayList<LotteryNumBean>();
		this.name = "";
		this.lotteryNumBeanList = new CopyOnWriteArrayList<LotteryNumBean>();
		this.id = 0;
		this.issueFormat = "";
		this.lotteryLowfreq = false;
		
	}
	public void setName(String name) {
		this.name = name.toLowerCase();
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIssueFormat() {
		return issueFormat;
	}

	public void setIssueFormat(String issueFormat) {
		this.issueFormat = issueFormat;
	}
	public String getName() {
		return name;
	}
	public void setType(String name) {
		this.name = name.toLowerCase();
	}
	public List<LotteryNumBean> getLotteryNumBeanList() {
		return lotteryNumBeanList;
	}
	public void setLotteryNumBeanList(List<LotteryNumBean> beanList) {
		this.lotteryNumBeanList.addAll(beanList);
	}
	public int getCreatePeriodType() {
		return createPeriodType;
	}
	public void setCreatePeriodType(int createPeriodType) {
		this.createPeriodType = createPeriodType;
	}
	public boolean checkId(int id) {
		return this.id == id;
	}
	
	public void addLotteryNumBeanList(LotteryNumBean lotteryNumBean) {
		boolean isNull = true;
		for(int i = 0 ; i < this.lotteryNumBeanList.size() ; i++) {
			LotteryNumBean b1 = this.lotteryNumBeanList.get(i);
			if(b1.getPeriodNum() == lotteryNumBean.getPeriodNum()) {
				isNull = false;
				break;
			}
		}
		if(isNull == true) this.lotteryNumBeanList.add(lotteryNumBean);
	}
	public void removeLotteryNumBeanList(LotteryNumBean bean) {
		for(int i = 0 ; i < this.lotteryNumBeanList.size() ; i++) {
			LotteryNumBean b1 = this.lotteryNumBeanList.get(i);
			if(b1.getPeriodNum() == bean.getPeriodNum()) {
				this.lotteryNumBeanList.remove(i);
				break;
			}
		}
	}
	public void setNewLotteryNumBeanList(List<LotteryNumBean> beanList) {
		this.lotteryNumBeanList = null;
		this.lotteryNumBeanList = new CopyOnWriteArrayList<LotteryNumBean>();
		this.lotteryNumBeanList.addAll(beanList);
	}
	
	public boolean isOpenLottery(long startThreadTime) {
		this.nowLotteryBeanList = null;
		this.nowLotteryBeanList = new CopyOnWriteArrayList<LotteryNumBean>();
		this.noLotteryCount = 0;
		
		for(int i = 0 ; i < this.lotteryNumBeanList.size() ; i++) {
			LotteryNumBean bean = this.lotteryNumBeanList.get(i);
			if( bean.getKjTime().getTime() < LotteryUtilConstant.getNowDate().getTime()
					&& bean.getGiveUpKjTime().getTime() > LotteryUtilConstant.getNowDate().getTime() 
					&& (bean.getData() == null || bean.getData().isEmpty() || "".equals(bean.getData()))) {
				this.nowLotteryBeanList.add(bean);
			}
			
			if(bean.getKjTime().getTime() > LotteryUtilConstant.getNowDate().getTime()
					&& (bean.getData() == null || bean.getData().isEmpty() || "".equals(bean.getData()))) {
				this.noLotteryCount++;
			}
		}
		
		if(this.nowLotteryBeanList.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isInsertTomorrow() {
		boolean isNull = true;
		Date tomorrow = LotteryUtilConstant.getNowDate(MyUtil.toDate(MyUtil.dateFormat(LotteryUtilConstant.getNowDate(), "yyyy/MM/dd")).getTime() + (24*60*60*1000));
		for(int i = 0 ; i < this.lotteryNumBeanList.size() ; i++) {
			Date date = MyUtil.toDate(MyUtil.dateFormat(this.lotteryNumBeanList.get(i).getDate(), "yyyy/MM/dd"));
			if(date.getTime() == tomorrow.getTime() || (date.getTime() >= tomorrow.getTime() && this.createPeriodType == CREATE_WEEK_PERIOD_TYPE)) {
				return false;
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(LotteryUtilConstant.getNowDate());
		if(cal.get(Calendar.HOUR_OF_DAY) >= 22) {
			return true;
		}
		return false;
	}
	
	public boolean isNowDate() {
		Date now = MyUtil.toDate(MyUtil.dateFormat(LotteryUtilConstant.getNowDate(), "yyyy/MM/dd"));
		for(int i = 0 ; i < this.lotteryNumBeanList.size() ; i++) {
			Date date = MyUtil.toDate(MyUtil.dateFormat(this.lotteryNumBeanList.get(i).getDate(), "yyyy/MM/dd"));
			if(date.getTime() == now.getTime() || (date.getTime() >= now.getTime() && this.createPeriodType == CREATE_WEEK_PERIOD_TYPE)) {
				return true;
			}
		}
		return false;
	}
	
	public void deleteTimeoutLottery(long startThreadTime) {
		int num = 0;
		while(num != lotteryNumBeanList.size()) {
			if(lotteryNumBeanList.get(num).getKjTime().getTime() < startThreadTime 
					&& lotteryNumBeanList.get(num).getGiveUpKjTime().getTime() < LotteryUtilConstant.getNowDate().getTime()
					|| !"".equals(lotteryNumBeanList.get(num).getData())) {
				this.lotteryNumBeanList.remove(num);
				num--;
			}
			num++;
		}
	}
	
	public boolean isMyIssue(long issue) {
		if(nowLotteryBeanList != null && nowLotteryBeanList.size() > 0) {
			for(int i = 0 ; i < nowLotteryBeanList.size() ; i++) {
				LotteryNumBean nowLotteryBean = nowLotteryBeanList.get(i);
				if(nowLotteryBean.getPeriodNum() == issue) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isName(String n) {
		return this.name.equals(n);
	}
	public int lotteryBeanSize() {
		return this.lotteryNumBeanList.size();
	}
	
	public void close() {
		this.checkNowDataTime = 0;
		this.checkYesterDayDataTime = 0;

		this.name = null;
		this.lotteryNumBeanList.clear();
		this.lotteryNumBeanList = null;
	}
	public int getZodiacType() {
		return zodiacType;
	}
	public void setZodiacType(int zodiacType) {
		this.zodiacType = zodiacType;
	}
	public boolean isLotteryLowfreq() {
		return lotteryLowfreq;
	}
	public void setLotteryLowfreq(boolean lotteryLowfreq) {
		this.lotteryLowfreq = lotteryLowfreq;
	}
	
	

}
