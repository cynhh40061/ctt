package tw.com.ctt.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.model.LotteryNumBean;
import tw.com.ctt.model.LotterySettingBean;
import tw.com.ctt.model.LotteryTypeBean;
import tw.com.ctt.service.impl.LotteryServiceImpl;
import tw.com.ctt.util.MyUtil;
import static tw.com.ctt.constant.LotteryConstant.*;

import tw.com.ctt.constant.LotteryUtilConstant;

public class LotteryRunnable implements Runnable {

	private static final Logger LOG = LogManager.getLogger(LotteryRunnable.class.getName());
	private List<LotteryTypeBean> lotteryTypeBeanList;
	private boolean isStatus;

	private LotteryServiceImpl lotteryService;
	private List<String> lotteryTypeList;

	private long startThreadDate;

	private Date shutDownTime;

	private long timeout = 1000 * 60 * 10L;

	private Date createTableAndCurrentRatioDate = null;
	private Date createLotteryStatisticalDate = null;
	private Date updateLotteryStatisticalReportTime = null;

	List<LotteryApiThreadServer> apiThreadList = null;

	public long updayeLotteryTypeDateLong = 0;
	public long updateLotterySettingDateLong = 0;

	public long updateLotteryPeriodWeekTypeDateLong = 0;

	public LotteryRunnable() {
		apiThreadList = new ArrayList<LotteryApiThreadServer>();

		this.startThreadDate = LotteryUtilConstant.getNowDate().getTime();
		this.lotteryTypeBeanList = new CopyOnWriteArrayList<LotteryTypeBean>();
		this.lotteryTypeList = new CopyOnWriteArrayList<String>();
		this.isStatus = true;

		lotteryService = new LotteryServiceImpl();

	}

	public void updateLotteryBeanList(List<LotteryNumBean> beanList) {
		try {
			if (beanList != null && beanList.size() > 0) {
				List<LotteryNumBean> updateBeanList = new ArrayList<LotteryNumBean>();
				for (int k = 0; k < beanList.size(); k++) {
					LotteryNumBean bean = beanList.get(k);
					if (bean.getData().length() > 0 && !"".equals(bean.getData())) {
						for (int i = 0; i < this.lotteryTypeBeanList.size(); i++) {
							LotteryTypeBean lotteryTypeBean = this.lotteryTypeBeanList.get(i);
							if (lotteryTypeBean.checkId(bean.getId())) {
								if (lotteryTypeBean.isMyIssue(bean.getPeriodNum())) {
									System.out.println(
											bean.getId() + "-" + lotteryTypeBean.getName() + "-" + bean.getPeriodNum() + "-" + bean.getData());
									updateBeanList.add(bean);
								}
							}
						}
					}
				}
				if (updateBeanList != null && updateBeanList.size() > 0) {
					this.lotteryService.updateLotteryData(updateBeanList);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						LOG.error(e.getMessage());
					} finally {
						updateLotteryData(updateBeanList);
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	public void updateLotteryData(List<LotteryNumBean> beanList) {
		for (int k = 0; k < this.lotteryTypeBeanList.size(); k++) {
			LotteryTypeBean lotteryTypeBean = this.lotteryTypeBeanList.get(k);
			for (int i = 0; i < beanList.size(); i++) {
				if (lotteryTypeBean.checkId(beanList.get(i).getId())) {
					List<LotteryNumBean> LotteryNumBeanList = lotteryService.getLotteryData(lotteryTypeBean.getId());
					if (LotteryNumBeanList.size() > 0) {
						this.lotteryTypeBeanList.get(k).setNewLotteryNumBeanList(LotteryNumBeanList);
					}
					break;
				}
			}
		}
	}

	public void close() {
		this.isStatus = false;
		this.lotteryTypeBeanList.clear();
		this.lotteryTypeBeanList = null;
		this.lotteryTypeList.clear();
		this.lotteryTypeList = null;
	}

	/**
	 * <p>
	 * 設定彩票種類
	 * 
	 * @param type
	 */
	public void setLotteryType(String type) {
		boolean isNull = true;
		for (int i = 0; i < this.lotteryTypeList.size(); i++) {
			if (lotteryTypeList.get(i).equals(type)) {
				isNull = false;
				break;
			}
		}
		if (isNull == true)
			this.lotteryTypeList.add(type.toLowerCase());
	}

	/**
	 * 更新 lottery type 資料
	 */
	public void updateLotteryTypeList() {
		List<LotteryTypeBean> newLotteryTypeBeanList = this.lotteryService.selectLotteryBlastType();
		boolean isExists = false;
		if (newLotteryTypeBeanList != null && newLotteryTypeBeanList.size() > 0) {
			if (this.lotteryTypeBeanList != null && this.lotteryTypeBeanList.size() > 0) {
				for (int j = 0; j < newLotteryTypeBeanList.size(); j++) {
					isExists = false;
					for (int i = 0; i < this.lotteryTypeBeanList.size(); i++) {
						if (this.lotteryTypeBeanList.get(i).checkId(newLotteryTypeBeanList.get(j).getId())) {
							isExists = true;
							this.lotteryTypeBeanList.get(i).setIssueFormat(newLotteryTypeBeanList.get(j).getIssueFormat());
							this.lotteryTypeBeanList.get(i).setName(newLotteryTypeBeanList.get(j).getName());
							this.lotteryTypeBeanList.get(i).setCreatePeriodType(newLotteryTypeBeanList.get(j).getCreatePeriodType());
							break;
						}
					}
					if (isExists == false) {
						this.lotteryTypeBeanList.add(newLotteryTypeBeanList.get(j));
					}
				}
			} else {
				this.lotteryTypeBeanList.addAll(newLotteryTypeBeanList);
			}
		}
	}

	/**
	 * start API THREAD
	 */
	public void starApiThread() {
		if (apiThreadList != null) {
			// apiThreadList.add(new GetApiB1cpLotteryNumThread(7000));--容易過期
			apiThreadList.add(new GetApiOpencaiLotteryNumThread(8000));
			apiThreadList.add(new GetApi365LotteryNumThread(8000));
			apiThreadList.add(new GetApiLotteryNumThread(8000));
			apiThreadList.add(new GetApiCaipiaojieguoLotteryNumThread(8000));

			for (int i = 0; i < apiThreadList.size(); i++) {
				apiThreadList.get(i).start();
			}
		} else {
			apiThreadList = new ArrayList<LotteryApiThreadServer>();
			starApiThread();
		}
	}

	/**
	 * stop API THREAD
	 */
	public void stopApiThread() {
		if (apiThreadList != null && apiThreadList.size() > 0) {
			for (int i = 0; i < apiThreadList.size(); i++) {
				apiThreadList.get(i).interrupt();
			}
			apiThreadList.clear();
		}
	}

	/**
	 * 對獎 跟 跳開 THREAD
	 */
	public void startKjThread() {
		KjRunnable kjThread = null;
		kjThread = new KjRunnable();
		kjThread.start();
	}

	/**
	 * 建立賠率 跟 建立子單 母單 追號單 TABLE
	 */
	public void createLotteryCurrentRatioAndOrderTable(Date date) {
		if (this.lotteryService.createOrderTable(date) && this.lotteryService.insertLotteryCurrentRatio(date)) {
			createTableAndCurrentRatioDate = LotteryUtilConstant.getNowDate();
		}
	}

	/**
	 * 建立 日 周 月 報表
	 */
	public void createLotteryMonthAndWeekStatistical() {

		if (this.lotteryService.createLotteryMonthAndWeekStatistical()) {
			createLotteryStatisticalDate = LotteryUtilConstant.getNowDate();
		}
	}

	/**
	 * 更新及時報表
	 */
	public void updateLotteryStatisticalReport() {
		if (isUpdateLotteryStatisticalReport()) {
			if (this.lotteryService.updateLotteryStatisticalReport()) {
				updateLotteryStatisticalReportTime = LotteryUtilConstant.getNowDate();
			}
		}
	}

	/**
	 * 設定 向API索取號碼 的 彩種
	 * 
	 * @param id
	 * @param name
	 * @param issueFormat
	 */
	public void setApiWork(int id, String name, String issueFormat, int zodiacType) {
		for (int s = 0; s < apiThreadList.size(); s++) {
			apiThreadList.get(s).setLotteryNum(id, name, issueFormat, zodiacType);
		}
	}

	/**
	 * 取的API Thread 號碼 更新
	 */
	public void getApiLotteryDataUpdateDB() {
		List<LotteryNumBean> beanList_api = null;
		try {
			beanList_api = new ArrayList<LotteryNumBean>();
			for (int s = 0; s < apiThreadList.size(); s++) {
				if (apiThreadList.get(s).getLotteryCount() > 0) {
					beanList_api.addAll(apiThreadList.get(s).getLotteryNum());
				}
			}
			if (beanList_api != null && beanList_api.size() > 0) {
				updateLotteryBeanList(beanList_api);

			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			if (beanList_api != null) {
				beanList_api.clear();
				beanList_api = null;
			}
		}
	}

	public boolean isUpdateLotteryPeriodWeekTypeDateLong() {
		long updateTime = 1000 * 60 * 30;
		if ((updateLotteryPeriodWeekTypeDateLong - LotteryUtilConstant.getNowDate().getTime()) > updateTime) {
			return true;
		} else if (updateLotteryPeriodWeekTypeDateLong == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 主程式
	 */
	@Override
	public void run() {
		boolean isNotShutDown = true;
		try {

			updateLotteryTypeList();
			updateTimeDiffFromDB();

			startThreadDate = LotteryUtilConstant.getNowDate().getTime();
			for (int i = 0; i < this.lotteryTypeBeanList.size(); i++) {
				LotteryTypeBean lotteryTypeBean = this.lotteryTypeBeanList.get(i);
				if (lotteryTypeBean.getCreatePeriodType() == CREATE_DAY_PERIOD_TYPE) {
					if (this.lotteryService.checkLotteryData(LotteryUtilConstant.getNowDate(), lotteryTypeBean.getId())) {
						List<LotteryNumBean> beanList = getNowLotteryData(lotteryTypeBean.getId());
						if (beanList.size() > 0) {
							this.lotteryTypeBeanList.get(i).setLotteryNumBeanList(beanList);
						}
					} else {
						insertLotteryData(this.lotteryTypeBeanList.get(i), lotteryTypeBean.getId(), LotteryUtilConstant.getNowDate(),
								this.lotteryTypeBeanList.get(i).getZodiacType());
					}
				} else if (lotteryTypeBean.getCreatePeriodType() == CREATE_WEEK_PERIOD_TYPE) {
					List<LotteryNumBean> beanList = getNowLotteryData(lotteryTypeBean.getId());
					if (beanList.size() > 0) {
						this.lotteryTypeBeanList.get(i).setLotteryNumBeanList(beanList);
					}
					insertThisWeekLotteryPeriod(this.lotteryTypeBeanList.get(i), lotteryTypeBean.getId(),
							this.lotteryTypeBeanList.get(i).getZodiacType());
				}
			}

			createLotteryCurrentRatioAndOrderTable(LotteryUtilConstant.getNowDate());
			createLotteryMonthAndWeekStatistical();
			updateLotteryStatisticalReport();

			starApiThread();
			startKjThread();

			System.out.println("Lottery start");
			boolean fff = false;

			while (fff) {
				this.shutDownTime = LotteryUtilConstant.getNowDate();
				for (int j = 0; j < this.lotteryTypeBeanList.size(); j++) {
					LotteryTypeBean bean = this.lotteryTypeBeanList.get(j);
					if (bean.getCheckNowDataTime() < LotteryUtilConstant.getNowDate().getTime()
							&& bean.getCheckYesterDayDataTime() < LotteryUtilConstant.getNowDate().getTime()) {
						if (bean.isNowDate() == false) {
							List<LotteryNumBean> beanList2 = getNowLotteryData(bean.getId());
							if (beanList2.size() > 0) {
								this.lotteryTypeBeanList.get(j).setLotteryNumBeanList(beanList2);
							} else {
								bean.setCheckNowDataTime(5 * 60 * 1000);
							}
						} else {
							if (bean.isOpenLottery(this.startThreadDate) == true) {

								if (bean.getId() == TXFFC || bean.getId() == QQFFC) {
									List<LotteryNumBean> beanListFFC = bean.nowLotteryBeanList;

									for (int k = 0; k < beanListFFC.size(); k++) {
										String[] numArr = new String[5];
										for (int k1 = 0; k1 < numArr.length; k1++) {
											Random ran = new Random();
											numArr[k1] = "" + ran.nextInt(10);
										}
										beanListFFC.get(k).setData(String.join(",", numArr));
										beanListFFC.get(k).setActualKjTime(LotteryUtilConstant.getNowDate());
										beanListFFC.get(k).setApiKjTime(LotteryUtilConstant.getNowDate());
									}
									updateLotteryBeanList(beanListFFC);
								} else {
									setApiWork(bean.getId(), bean.getName(), bean.getIssueFormat(), bean.getZodiacType());
								}
							}
							if (bean.isInsertTomorrow() == true) {
								updateTimeDiffFromDB();
								if (bean.getCreatePeriodType() == CREATE_DAY_PERIOD_TYPE) {
									if (this.lotteryService.checkLotteryData(
											LotteryUtilConstant.getNowDate(LotteryUtilConstant.getNowDate().getTime() + (24 * 60 * 60 * 1000)),
											bean.getId())) {
										List<LotteryNumBean> beanList = getNowLotteryData(bean.getId());
										if (beanList.size() > 0) {
											this.lotteryTypeBeanList.get(j).setLotteryNumBeanList(beanList);
										}
									} else {
										insertLotteryData(bean, bean.getId(),
												LotteryUtilConstant.getNowDate(LotteryUtilConstant.getNowDate().getTime() + (24 * 60 * 60 * 1000)),
												bean.getZodiacType());
									}
								} else if (bean.getCreatePeriodType() == CREATE_WEEK_PERIOD_TYPE) {
									List<LotteryNumBean> beanList = getNowLotteryData(bean.getId());
									if (beanList.size() > 0) {
										this.lotteryTypeBeanList.get(j).setLotteryNumBeanList(beanList);
									}
									insertNextWeekLotteryPeriod(bean, bean.getId(), bean.getZodiacType());
								}
							}
						}
					}
				}

				if (isCreateNewTableAndCurrentRatio()) {
					createLotteryCurrentRatioAndOrderTable(
							LotteryUtilConstant.getNowDate(LotteryUtilConstant.getNowDate().getTime() + (24 * 60 * 60 * 1000)));
				}

				if (isCreateMonthAndWeekLotteryStatistical()) {
					createLotteryMonthAndWeekStatistical();
				}

				updateLotteryStatisticalReport();
				getApiLotteryDataUpdateDB();

				Thread.sleep(1000);
			}

		} catch (Exception e) {
			LOG.error("run Exception");
			LOG.error(e.getMessage());
			isStatus = false;
		}
	}

	/**
	 * <p>
	 * 建立今天的所有期號
	 * 
	 * @param lotteryType
	 * @return
	 */
	public void insertLotteryData(LotteryTypeBean lotteryTypeBean, int id, Date date, int zodiacType) {
		List<LotterySettingBean> lotterySettingBeanList = this.lotteryService.selectLotterySettingData(id);
		List<LotteryNumBean> insertBeanList = new ArrayList<LotteryNumBean>();
		Date now = MyUtil.toDate(MyUtil.dateFormat(date, "yyyy/MM/dd"));
		long setLong = 0;
		Date upStopBettingTime = null;
		// if (lotteryTypeBean.size() > 0 && lotteryTypeBean != null) {
		// for (int num = 0; num < this.lotteryTypeBeanList.size(); num++) {
		// LotteryTypeBean lotteryTypeBean = this.lotteryTypeBeanList.get(num);
		if (lotteryTypeBean.checkId(id)) {
			int issueNums = 1;
			int indexOfToday = 1;
			Date lastStopBettingTime = this.lotteryService.getLastBettingTime(id);
			String issueFormat = lotteryTypeBean.getIssueFormat();

			for (int i = 0; i < lotterySettingBeanList.size(); i++) {
				LotterySettingBean b1 = lotterySettingBeanList.get(i);
				LotteryNumBean bean = new LotteryNumBean();
				bean.setId(id);
				bean.setDate(now);
				Date kjTime = LotteryUtilConstant.getNowDate(now.getTime() + (b1.getKjTime() * 1000));
				bean.setKjTime(new java.sql.Date(kjTime.getTime()));
				Date giveUpTime = LotteryUtilConstant.getNowDate(now.getTime() + (b1.getGiveUpKjTime() * 1000));
				bean.setGiveUpKjTime(giveUpTime);
				Date jumpOffTime = LotteryUtilConstant.getNowDate(now.getTime() + (b1.getJumpOffTime() * 1000));
				bean.setJumpOffTime(jumpOffTime);

				bean.setZodiacType(zodiacType);

				Date stopBettingTime = LotteryUtilConstant.getNowDate(kjTime.getTime() - (b1.getStopBettingTime() * 1000));
				bean.setStopBettingTime(stopBettingTime);
				if (setLong == now.getTime()) {
					bean.setIndexOfToday(indexOfToday);
					indexOfToday++;
				} else {
					indexOfToday = 1;
					bean.setIndexOfToday(indexOfToday);
				}
				if (lastStopBettingTime != null) {
					Calendar c1 = Calendar.getInstance();
					Calendar c2 = Calendar.getInstance();
					c1.setTime(lastStopBettingTime);
					c2.setTime(now);

					if (c1.get(Calendar.DAY_OF_YEAR) < c2.get(Calendar.DAY_OF_YEAR)) {
						bean.setStartBettingTime(now);
					} else if (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
						bean.setStartBettingTime(lastStopBettingTime);
					}
				} else {
					bean.setStartBettingTime(now);
				}

				if (upStopBettingTime != null) {
					bean.setExpectedStartBettingTime(upStopBettingTime);
				} else {
					bean.setExpectedStartBettingTime(now);
				}

				upStopBettingTime = stopBettingTime;

				if (id == FC3D || id == PL5 || id == XGLHC) {
					String issueStr = this.lotteryService.selectMaxIssue(id) + "";
					String[] issueFormatArr = issueFormat.split("");
					String[] issueArr = issueStr.split("");
					String year = "";
					String issueNumStr = "";
					for (int s = 0; s < issueFormatArr.length && issueArr.length == issueFormatArr.length; s++) {
						if (issueFormatArr[s].equalsIgnoreCase("y")) {
							if (issueArr[s] != null && !"".equals(issueArr[s])) {
								year += issueArr[s];
							}
						} else if (issueFormatArr[s].equalsIgnoreCase("x")) {
							if (issueArr[s] != null && !"".equals(issueArr[s])) {
								issueNumStr += issueArr[s];
							}
						}
					}
					Calendar cal = Calendar.getInstance();
					cal.setTime(LotteryUtilConstant.getNowDate());
					if (!year.equals("" + cal.get(Calendar.YEAR)) || issueArr.length != issueFormatArr.length) {
						issueNumStr = issueNums + "";
					} else {
						int ss = Integer.parseInt(issueNumStr) + issueNums;
						issueNumStr = "" + ss;
					}
					bean.setPeriodNum(MyUtil.toIssue(now, issueNumStr, issueFormat));
					issueNums++;
				} else if (id == BJPK10) {
					long issue = this.lotteryService.selectMaxIssue(id) + b1.getIssue();
					bean.setPeriodNum(MyUtil.toIssue(now, issue + "", issueFormat));
				} else {
					bean.setPeriodNum(MyUtil.toIssue(now, "" + b1.getIssue(), issueFormat));
				}

				lotteryTypeBean.addLotteryNumBeanList(bean);
				insertBeanList.add(bean);

				if (lotteryTypeBean.isLotteryLowfreq() == true) {
					this.lotteryService.inserLlowfreqLotteryCurrentRatio(bean.getId(), "" + bean.getPeriodNum(), bean.getDate());
				}
			}
		}
		// }
		// }
		if (insertBeanList != null && insertBeanList.size() > 0) {
			this.lotteryService.addLotteryData(insertBeanList);
		}
	}

	/**
	 * 補建 這禮拜 未建立 的期號
	 * 
	 * @param id
	 */
	public void insertThisWeekLotteryPeriod(LotteryTypeBean lotteryTypeBean, int id, int zodiacType) {
		List<LotterySettingBean> lotterySettingBeanList = this.lotteryService.getThisWeekInsertWeekPeriod(id);
		insertWeekTypeLotteryData(lotteryTypeBean, id, lotterySettingBeanList, zodiacType);

		if (lotterySettingBeanList.size() > 0 && lotterySettingBeanList != null) {
			for (int i = 0; i < lotterySettingBeanList.size(); i++) {
				this.lotteryService.createOrderTable(new Date(lotterySettingBeanList.get(i).getCreateWeekTime() * 1000));
			}
		}
	}

	/**
	 * 補建 這禮拜 未建立 的期號
	 * 
	 * @param id
	 */
	public void insertNextWeekLotteryPeriod(LotteryTypeBean lotteryTypeBean, int id, int zodiacType) {
		List<LotterySettingBean> lotterySettingBeanList = this.lotteryService.getNextInsertWeekPeriod(id);
		insertWeekTypeLotteryData(lotteryTypeBean, id, lotterySettingBeanList, zodiacType);

		if (lotterySettingBeanList.size() > 0 && lotterySettingBeanList != null) {
			for (int i = 0; i < lotterySettingBeanList.size(); i++) {
				this.lotteryService.createOrderTable(new Date(lotterySettingBeanList.get(i).getCreateWeekTime() * 1000));
			}
		}
	}

	/**
	 * <p>
	 * 建立周版本 期號
	 * 
	 * @param lotteryType
	 * @return
	 */
	public void insertWeekTypeLotteryData(LotteryTypeBean lotteryTypeBean, int id, List<LotterySettingBean> lotterySettingBeanList, int zodiacType) {
		List<LotteryNumBean> insertBeanList = new ArrayList<LotteryNumBean>();
		Date now = null;
		long setLong = 0;
		// if (lotterySettingBeanList.size() > 0 && lotterySettingBeanList != null) {
		// for (int num = 0; num < this.lotteryTypeBeanList.size(); num++) {
		// LotteryTypeBean lotteryTypeBean = this.lotteryTypeBeanList.get(num);
		if (lotteryTypeBean.checkId(id)) {
			int issueNums = 1;
			int indexOfToday = 1;
			String issueFormat = lotteryTypeBean.getIssueFormat();
			for (int i = 0; i < lotterySettingBeanList.size(); i++) {
				LotterySettingBean b1 = lotterySettingBeanList.get(i);
				LotteryNumBean bean = new LotteryNumBean();
				now = new Date(b1.getCreateWeekTime() * 1000);
				if (now != null) {
					bean.setId(id);
					bean.setDate(now);
					Date kjTime = LotteryUtilConstant.getNowDate(now.getTime() + (b1.getKjTime() * 1000));
					bean.setKjTime(new java.sql.Date(kjTime.getTime()));
					Date giveUpTime = LotteryUtilConstant.getNowDate(now.getTime() + (b1.getGiveUpKjTime() * 1000));
					bean.setGiveUpKjTime(giveUpTime);
					Date jumpOffTime = LotteryUtilConstant.getNowDate(now.getTime() + (b1.getJumpOffTime() * 1000));
					bean.setJumpOffTime(jumpOffTime);

					bean.setZodiacType(zodiacType);

					Date startBettingTime = new Date((b1.getStartBettingWeekTime() + b1.getStartBettingTime()) * 1000);

					Date stopBettingTime = LotteryUtilConstant.getNowDate(kjTime.getTime() - (b1.getStopBettingTime() * 1000));
					bean.setStopBettingTime(stopBettingTime);
					if (setLong == now.getTime()) {
						bean.setIndexOfToday(indexOfToday);
						indexOfToday++;
					} else {
						indexOfToday = 1;
						bean.setIndexOfToday(indexOfToday);
					}

					bean.setStartBettingTime(startBettingTime);

					bean.setExpectedStartBettingTime(startBettingTime);

					if (id == FC3D || id == PL5 || id == XGLHC) {
						String issueStr = this.lotteryService.selectMaxIssue(id) + "";
						String[] issueFormatArr = issueFormat.split("");
						String[] issueArr = issueStr.split("");
						String year = "";
						String issueNumStr = "";
						for (int s = 0; s < issueFormatArr.length && issueArr.length == issueFormatArr.length; s++) {
							if (issueFormatArr[s].equalsIgnoreCase("y")) {
								if (issueArr[s] != null && !"".equals(issueArr[s])) {
									year += issueArr[s];
								}
							} else if (issueFormatArr[s].equalsIgnoreCase("x")) {
								if (issueArr[s] != null && !"".equals(issueArr[s])) {
									issueNumStr += issueArr[s];
								}
							}
						}
						Calendar cal = Calendar.getInstance();
						cal.setTime(LotteryUtilConstant.getNowDate());
						if (!year.equals("" + cal.get(Calendar.YEAR)) || issueArr.length != issueFormatArr.length) {
							issueNumStr = issueNums + "";
						} else {
							int ss = Integer.parseInt(issueNumStr) + issueNums;
							issueNumStr = "" + ss;
						}
						bean.setPeriodNum(MyUtil.toIssue(now, issueNumStr, issueFormat));
						issueNums++;
					} else if (id == BJPK10) {
						long issue = this.lotteryService.selectMaxIssue(id) + b1.getIssue();
						bean.setPeriodNum(MyUtil.toIssue(now, issue + "", issueFormat));
					} else {
						bean.setPeriodNum(MyUtil.toIssue(now, "" + b1.getIssue(), issueFormat));
					}

					lotteryTypeBean.addLotteryNumBeanList(bean);
					insertBeanList.add(bean);

					if (lotteryTypeBean.isLotteryLowfreq() == true) {
						this.lotteryService.inserLlowfreqLotteryCurrentRatio(bean.getId(), "" + bean.getPeriodNum(), bean.getDate());
					}
				}
			}

		}
		// }
		// }
		if (insertBeanList != null && insertBeanList.size() > 0) {
			this.lotteryService.addLotteryData(insertBeanList);
		}
	}

	/**
	 * <p>
	 * 跟DB取得當日所有彩票資料
	 * 
	 * @return
	 */
	private List<LotteryNumBean> getNowLotteryData(int id) {
		// Date now = MyUtil.toDate(MyUtil.dateFormat(LotteryUtilConstant.getNowDate(),
		// "yyyy/MM/dd"));
		return this.lotteryService.getLotteryData(id);
	}

	/**
	 * <p>
	 * check有沒有昨天的彩票資料
	 * 
	 * @param id
	 * @return
	 */
	private boolean checkYesterDayLotteryDate(int id) {
		long day = 24 * 60 * 60 * 1000;
		Date yesterDay = LotteryUtilConstant
				.getNowDate(MyUtil.toDate(MyUtil.dateFormat(LotteryUtilConstant.getNowDate(), "yyyy/MM/dd")).getTime() - day);
		return this.lotteryService.checkLotteryData(yesterDay, id);
	}

	private boolean isCreateNewTableAndCurrentRatio() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(LotteryUtilConstant.getNowDate());
		if (cal.get(Calendar.HOUR_OF_DAY) >= 22) {
			if (createTableAndCurrentRatioDate != null) {
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(createTableAndCurrentRatioDate);
				if (cal.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR) || cal2.get(Calendar.HOUR_OF_DAY) < 22) {
					return true;
				}
			} else if (createTableAndCurrentRatioDate == null) {
				return true;
			}
		}
		return false;
	}

	private boolean isCreateMonthAndWeekLotteryStatistical() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(LotteryUtilConstant.getNowDate());

		if (cal.get(Calendar.HOUR_OF_DAY) >= 4) {
			if (createLotteryStatisticalDate != null) {
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(createLotteryStatisticalDate);
				if (cal.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR) || cal2.get(Calendar.HOUR_OF_DAY) < 4) {
					return true;
				}
			} else if (createLotteryStatisticalDate == null) {
				return true;
			}
		}

		return false;
	}

	public void updateTimeDiffFromDB() {
		LotteryUtilConstant.CORRECTION_TIME = this.lotteryService.getTimeDiffFromDB();
		LOG.error("DB Time: " + LotteryUtilConstant.CORRECTION_TIME);
		LOG.error("Server Time: " + new Date());
		LOG.error("CORRECTION Time: " + LotteryUtilConstant.getNowDate());
	}

	public boolean isUpdateLotteryStatisticalReport() {
		if (updateLotteryStatisticalReportTime != null) {
			long mSec = LotteryUtilConstant.getNowDate().getTime() - updateLotteryStatisticalReportTime.getTime();
			long updateTimeSec = 8 * 60 * 1000;
			if (mSec <= updateTimeSec) {
				return false;
			}
		}
		return true;

	}

}
