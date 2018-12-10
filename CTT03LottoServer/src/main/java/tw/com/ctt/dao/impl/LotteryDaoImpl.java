package tw.com.ctt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.jdbc.CallableStatement;

import tw.com.ctt.model.LotteryNumBean;
import tw.com.ctt.model.LotterySettingBean;
import tw.com.ctt.model.LotteryTimeSetBean;
import tw.com.ctt.model.LotteryTypeBean;
import tw.com.ctt.dao.ILotteryDao;
import tw.com.ctt.util.DataSource;
import tw.com.ctt.util.MyUtil;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class LotteryDaoImpl extends BaseDao implements ILotteryDao {

	private static final Logger LOG = LogManager.getLogger(LotteryDaoImpl.class.getName());

	public void checkRead() {
		try {
			if (READ_CONN == null || READ_CONN.isClosed()) {
				READ_CONN = DataSource.getReadConnection();
			}
			if (READ_CONN.getTransactionIsolation() != Connection.TRANSACTION_READ_COMMITTED) {
				READ_CONN.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}
		} catch (SQLException e) {
			ShowLog.err(LOG, e);
			LOG.error("checkWriteConn(): no connection");
		} catch (Exception e) {
			ShowLog.err(LOG, e);
			LOG.error("Exception");
		}
	}

	public void checkWrite() {
		try {
			if (WRITE_CONN == null || WRITE_CONN.isClosed()) {
				WRITE_CONN = DataSource.getWriteConnection();
			}
			if (WRITE_CONN.getTransactionIsolation() != Connection.TRANSACTION_READ_COMMITTED) {
				WRITE_CONN.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}

		} catch (SQLException e) {
			ShowLog.err(LOG, e);
			LOG.error("checkWriteConn(): no connection");
		} catch (Exception e) {
			ShowLog.err(LOG, e);
			LOG.error("Exception");
		}
	}

	@Override
	public boolean checkLotteryDate(Date date, int id) throws Exception {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				sb.append("select count(1) as count  \n");
				sb.append("from ctt_manager.ctt_lottery where DATE_FORMAT(date,'%Y/%m/%d') = DATE_FORMAT(?,'%Y/%m/%d') and id = ?  \n");

				if (MyUtil.dateFormat(date, "yyyy/MM/dd").length() > 0 ? !"".equals(MyUtil.dateFormat(date, "yyyy/MM/dd")) : false) {
					selectObj.add(MyUtil.dateFormat(date, "yyyy/MM/dd"));
				} else {
					return false;
				}
				selectObj.add(id);

				List<Map<String, Object>> listObj = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				if (listObj.size() == 1) {
					Map<String, Object> map = listObj.get(0);
					if (map.containsKey("count")) {
						if (Integer.parseInt(map.get("count").toString()) > 0) {
							return true;
						}
					}
				}

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return false;
	}

	@Override
	public List<LotteryNumBean> selectLotteryData(Date date, int id) throws Exception {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		List<LotteryNumBean> lotteryNumBeanList = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return lotteryNumBeanList;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				lotteryNumBeanList = new ArrayList<LotteryNumBean>();
				sb.append(
						"select id,period_num,DATE_FORMAT(date,'%Y/%m/%d %H:%i:%S') as date,data,DATE_FORMAT(kj_time,'%Y/%m/%d %H:%i:%S') as kj_time \n");
				sb.append(
						", DATE_FORMAT(actual_kj_time,'%Y/%m/%d %H:%i:%S') as actual_kj_time ,DATE_FORMAT(api_kj_time,'%Y/%m/%d %H:%i:%S') as api_kj_time  \n");
				sb.append(", DATE_FORMAT(give_up_kj_time,'%Y/%m/%d %H:%i:%S') as give_up_kj_time  \n");

				sb.append(" from ctt_manager.ctt_lottery where DATE_FORMAT(date,'%Y/%m/%d') = DATE_FORMAT(?,'%Y/%m/%d') and id = ? and data = '' \n");

				System.out.println(sb.toString());

				if (MyUtil.dateFormat(date, "yyyy/MM/dd").length() > 0 ? !"".equals(MyUtil.dateFormat(date, "yyyy/MM/dd")) : false) {
					selectObj.add(MyUtil.dateFormat(date, "yyyy/MM/dd"));
				} else {
					return null;
				}

				selectObj.add(id);

				List<Map<String, Object>> listObj = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				for (int i = 0; i < listObj.size(); i++) {
					Map<String, Object> map = listObj.get(i);
					LotteryNumBean lotteryNumBean = new LotteryNumBean();
					if (map.containsKey("kjTime")) {
						lotteryNumBean.setKjTime(MyUtil.toDate(map.get("kjTime").toString()));
					}
					if (map.containsKey("periodNum")) {
						lotteryNumBean.setPeriodNum(MyUtil.toLong(map.get("periodNum").toString()));
					}
					if (map.containsKey("date")) {
						lotteryNumBean.setDate(MyUtil.toDate(map.get("date").toString()));
					}
					if (map.containsKey("data")) {
						lotteryNumBean.setData(map.get("data").toString());
					}
					if (map.containsKey("id")) {
						lotteryNumBean.setId(Integer.parseInt(map.get("id").toString()));
					}
					if (map.containsKey("giveUpKjTime")) {
						lotteryNumBean.setGiveUpKjTime(MyUtil.toDate(map.get("giveUpKjTime").toString()));
					}
					lotteryNumBeanList.add(lotteryNumBean);
				}

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return lotteryNumBeanList;
	}

	@Override
	public List<LotteryNumBean> selectLotteryData(Date startTime, Date endTime, int id) throws Exception {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		List<LotteryNumBean> lotteryNumBeanList = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return lotteryNumBeanList;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				lotteryNumBeanList = new ArrayList<LotteryNumBean>();

				sb.append(
						"select id,period_num,DATE_FORMAT(date,'%Y/%m/%d %H:%i:%S') as date,data,DATE_FORMAT(kj_time,'%Y/%m/%d %H:%i:%S') as kj_time \n");
				sb.append(",DATE_FORMAT(actual_kj_time,'%Y/%m/%d %H:%i:%S') as actual_kj_time \n");
				sb.append(", DATE_FORMAT(give_up_kj_time,'%Y/%m/%d %H:%i:%S') as give_up_kj_time \n");

				sb.append(" from ctt_manager.ctt_lottery \n");
				sb.append(" where date BETWEEN DATE_FORMAT(?,'%Y/%m/%d') and DATE_FORMAT(?,'%Y/%m/%d') and id =? and data = '' \n");

				if (MyUtil.dateFormat(startTime, "yyyy/MM/dd").length() > 0 ? !"".equals(MyUtil.dateFormat(startTime, "yyyy/MM/dd")) : false) {
					selectObj.add(MyUtil.dateFormat(startTime, "yyyy/MM/dd"));
				} else {
					return null;
				}

				if (MyUtil.dateFormat(endTime, "yyyy/MM/dd").length() > 0 ? !"".equals(MyUtil.dateFormat(endTime, "yyyy/MM/dd")) : false) {
					selectObj.add(MyUtil.dateFormat(endTime, "yyyy/MM/dd"));
				} else {
					return null;
				}

				// selectObj.add(startTime);
				// selectObj.add(endTime);
				selectObj.add(id);

				List<Map<String, Object>> listObj = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				for (int i = 0; i < listObj.size(); i++) {
					Map<String, Object> map = listObj.get(i);
					LotteryNumBean lotteryNumBean = new LotteryNumBean();
					if (map.containsKey("kjTime")) {
						lotteryNumBean.setKjTime(MyUtil.toDate(map.get("kjTime").toString()));
					}
					if (map.containsKey("periodNum")) {
						lotteryNumBean.setPeriodNum(MyUtil.toLong(map.get("periodNum").toString()));
					}
					if (map.containsKey("date")) {
						lotteryNumBean.setDate(MyUtil.toDate(map.get("date").toString()));
					}
					if (map.containsKey("data")) {
						lotteryNumBean.setData(map.get("data").toString());
					}
					if (map.containsKey("id")) {
						lotteryNumBean.setId(Integer.parseInt(map.get("id").toString()));
					}
					if (map.containsKey("giveUpKjTime")) {
						lotteryNumBean.setGiveUpKjTime(MyUtil.toDate(map.get("giveUpKjTime").toString()));
					}
					lotteryNumBeanList.add(lotteryNumBean);
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return lotteryNumBeanList;
	}

	@Override
	public boolean addLotteryData(List<LotteryNumBean> bean) throws Exception {
		StringBuilder sb = null;
		List<List<Object>> insertObj = null;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {

				sb = new StringBuilder();
				insertObj = new ArrayList<List<Object>>();
				sb.append(
						"insert into  ctt_manager.ctt_lottery (id,period_num,date,kj_time,give_up_kj_time , stop_betting_time , start_betting_time , expected_start_betting_time,index_of_today , jump_off_time , zodiac_type)  \n");
				sb.append(" (select ? as id, ? as period_num, ? as date, ? as kj_time , ? as give_up_kj_time , ? as stop_betting_time  \n");
				sb.append(
						" , ? as start_betting_time , ? as expected_start_betting_time , ? as index_of_today , ? as jump_off_time , ? as zodiac_type  \n");
				sb.append(
						" from dual where (select count(1) as count from ctt_manager.ctt_lottery where id = ? and period_num = ? and date = ?) = 0) \n");
				for (int k = 0; k < bean.size(); k++) {
					List<Object> listObj = new ArrayList<Object>();
					LotteryNumBean b = bean.get(k);
					listObj.add(b.getId());
					listObj.add(b.getPeriodNum());
					if (MyUtil.dateFormat(b.getDate(), "yyyy/MM/dd").length() > 0 ? !"".equals(MyUtil.dateFormat(b.getDate(), "yyyy/MM/dd"))
							: false) {
						listObj.add(MyUtil.dateFormat(b.getDate(), "yyyy/MM/dd"));
					} else {
						listObj.add(null);
					}
					if (MyUtil.dateFormat(b.getKjTime(), "yyyy/MM/dd HH:mm:ss").length() > 0
							? !"".equals(MyUtil.dateFormat(b.getKjTime(), "yyyy/MM/dd HH:mm:ss"))
							: false) {
						listObj.add(MyUtil.dateFormat(b.getKjTime(), "yyyy/MM/dd HH:mm:ss"));
					} else {
						listObj.add(null);
					}

					if (MyUtil.dateFormat(b.getGiveUpKjTime(), "yyyy/MM/dd HH:mm:ss").length() > 0
							? !"".equals(MyUtil.dateFormat(b.getGiveUpKjTime(), "yyyy/MM/dd HH:mm:ss"))
							: false) {
						listObj.add(MyUtil.dateFormat(b.getGiveUpKjTime(), "yyyy/MM/dd HH:mm:ss"));
					} else {
						listObj.add(null);
					}

					if (MyUtil.dateFormat(b.getStopBettingTime(), "yyyy/MM/dd HH:mm:ss").length() > 0
							? !"".equals(MyUtil.dateFormat(b.getStopBettingTime(), "yyyy/MM/dd HH:mm:ss"))
							: false) {
						listObj.add(MyUtil.dateFormat(b.getStopBettingTime(), "yyyy/MM/dd HH:mm:ss"));
					} else {
						listObj.add(null);
					}

					if (MyUtil.dateFormat(b.getStartBettingTime(), "yyyy/MM/dd HH:mm:ss").length() > 0
							? !"".equals(MyUtil.dateFormat(b.getStartBettingTime(), "yyyy/MM/dd HH:mm:ss"))
							: false) {
						listObj.add(MyUtil.dateFormat(b.getStartBettingTime(), "yyyy/MM/dd HH:mm:ss"));
					} else {
						listObj.add(null);
					}

					if (MyUtil.dateFormat(b.getExpectedStartBettingTime(), "yyyy/MM/dd HH:mm:ss").length() > 0
							? !"".equals(MyUtil.dateFormat(b.getExpectedStartBettingTime(), "yyyy/MM/dd HH:mm:ss"))
							: false) {
						listObj.add(MyUtil.dateFormat(b.getExpectedStartBettingTime(), "yyyy/MM/dd HH:mm:ss"));
					} else {
						listObj.add(null);
					}

					listObj.add(b.getIndexOfToday());

					if (MyUtil.dateFormat(b.getJumpOffTime(), "yyyy/MM/dd HH:mm:ss").length() > 0
							? !"".equals(MyUtil.dateFormat(b.getJumpOffTime(), "yyyy/MM/dd HH:mm:ss"))
							: false) {
						listObj.add(MyUtil.dateFormat(b.getJumpOffTime(), "yyyy/MM/dd HH:mm:ss"));
					} else {
						listObj.add(null);
					}

					listObj.add(b.getZodiacType());

					listObj.add(b.getId());
					listObj.add(b.getPeriodNum());

					if (MyUtil.dateFormat(b.getDate(), "yyyy/MM/dd").length() > 0 ? !"".equals(MyUtil.dateFormat(b.getDate(), "yyyy/MM/dd"))
							: false) {
						listObj.add(MyUtil.dateFormat(b.getDate(), "yyyy/MM/dd"));
					} else {
						listObj.add(null);
					}

					insertObj.add(listObj);

				}

				int[] listRowCount = StmtUtil.updateBatch(this.WRITE_CONN, sb.toString(), insertObj);
				return true;
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertObj != null) {
				insertObj.clear();
				insertObj = null;
			}
		}
		return false;
	}

	@Override
	public boolean updateLotteryData(List<LotteryNumBean> bean) throws Exception {
		StringBuilder sb = null;
		List<List<Object>> insertObj = null;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {

				sb = new StringBuilder();
				insertObj = new ArrayList<List<Object>>();
				sb.append("update ctt_manager.ctt_lottery set data = ? ,actual_kj_time = now() , api_kj_time = ? , status = 1 \n");
				sb.append(" where id = ? and period_num = ? and data = '' and status = 0 \n");
				for (int k = 0; k < bean.size(); k++) {
					List<Object> listObj = new ArrayList<Object>();
					LotteryNumBean b = bean.get(k);

					listObj.add(b.getData());

					if (MyUtil.dateFormat(b.getApiKjTime(), "yyyy/MM/dd HH:mm:ss").length() > 0
							? !"".equals(MyUtil.dateFormat(b.getApiKjTime(), "yyyy/MM/dd HH:mm:ss"))
							: false) {
						listObj.add(MyUtil.dateFormat(b.getApiKjTime(), "yyyy/MM/dd HH:mm:ss"));
					} else {
						System.out.println(" getApiKjTime is null");
						listObj.add(null);
					}

					listObj.add(b.getId());
					listObj.add(b.getPeriodNum());
					insertObj.add(listObj);
				}
				int[] listRowCount = StmtUtil.updateBatch(this.WRITE_CONN, sb.toString(), insertObj);
				return true;
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertObj != null) {
				insertObj.clear();
				insertObj = null;
			}
		}
		return false;
	}

	@Override
	public boolean addSettingLottery(List<Map<String, Object>> listMap) throws Exception {
		StringBuilder sb = null;
		List<List<Object>> insertObj = null;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				insertObj = new ArrayList<List<Object>>();
				sb.append("insert into  ctt_manager.ctt_lottery_setting (id,issue,kj_time,stop_betting_time,give_up_kj_time , platform_kj_time)  \n");
				sb.append(
						" (select ? as id, ? as issue, ? as kj_time, ? as stop_betting_time , ? as give_up_kj_time , ? as  platform_kj_time from dual  \n");
				sb.append(" where (select count(1) as count from ctt_manager.ctt_lottery_setting where id = ? and issue = ?) = 0) \n");

				for (int i = 0; i < listMap.size(); i++) {
					List<Object> listObj = new ArrayList<Object>();
					Map<String, Object> map = listMap.get(i);
					if (map.containsKey("id")) {
						listObj.add(map.get("id"));
					}
					if (map.containsKey("issue")) {
						listObj.add(map.get("issue"));
					}
					if (map.containsKey("kjTime")) {
						listObj.add(map.get("kjTime"));
					}
					if (map.containsKey("stopBettingTime")) {
						listObj.add(map.get("stopBettingTime"));
					}
					if (map.containsKey("giveUpKjTime")) {
						listObj.add(map.get("giveUpKjTime"));
					}

					if (map.containsKey("platformKjTime")) {
						listObj.add(map.get("platformKjTime"));
					}
					if (map.containsKey("id")) {
						listObj.add(map.get("id"));
					}
					if (map.containsKey("issue")) {
						listObj.add(map.get("issue"));
					}

					insertObj.add(listObj);
				}

				int[] listRowCount = StmtUtil.updateBatch(this.WRITE_CONN, sb.toString(), insertObj);
				return true;
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertObj != null) {
				insertObj.clear();
				insertObj = null;
			}
		}
		return false;
	}

	@Override
	public List<LotterySettingBean> selectLotterySettingData(int id) throws Exception {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		List<LotterySettingBean> lotterySettingBeanList = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return lotterySettingBeanList;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				lotterySettingBeanList = new ArrayList<LotterySettingBean>();
				sb.append(
						"SELECT id , issue  , kj_time , stop_betting_time , give_up_kj_time , platform_kj_time  , jump_off_time FROM ctt_manager.ctt_lottery_setting where id = ?  \n");
				selectObj.add(id);

				List<Object> listObj = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), selectObj, new LotterySettingBean());
				if (listObj != null && listObj.size() > 0) {
					for (int i = 0; i < listObj.size(); i++) {
						LotterySettingBean b1 = (LotterySettingBean) listObj.get(i);
						lotterySettingBeanList.add(b1);
					}
				}

			}
		} catch (Exception e) {
			lotterySettingBeanList = null;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return lotterySettingBeanList;
	}

	@Override
	public List<LotteryTypeBean> selectLotteryBlastType() throws Exception {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		List<LotteryTypeBean> lotteryTypeBeanList = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return lotteryTypeBeanList;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				lotteryTypeBeanList = new ArrayList<LotteryTypeBean>();
				sb.append(
						"SELECT id,name,cast(create_period_type as char) as create_period_type,issue_format ,zodiac_type, lottery_lowfreq FROM ctt_manager.ctt_lottery_type");

				List<Map<String, Object>> listObj = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), new ArrayList<Object>());
				if (listObj != null && listObj.size() > 0) {
					for (int i = 0; i < listObj.size(); i++) {
						Map<String, Object> map = listObj.get(i);
						LotteryTypeBean b1 = new LotteryTypeBean();
						if (map.containsKey("id")) {
							b1.setId(Integer.parseInt(map.get("id").toString()));
						}

						if (map.containsKey("name")) {
							b1.setName(map.get("name").toString());
						}

						if (map.containsKey("issueFormat")) {
							b1.setIssueFormat(map.get("issueFormat").toString());
						}

						if (map.containsKey("createPeriodType")) {
							b1.setCreatePeriodType(Integer.parseInt(map.get("createPeriodType").toString()));
						}

						if (map.containsKey("lotteryLowfreq")) {
							b1.setLotteryLowfreq(Boolean.parseBoolean(map.get("lotteryLowfreq").toString()));
						}
						if (map.containsKey("zodiacType")) {
							// System.out.println(Integer.parseInt(map.get("zodiacType").toString()));
							b1.setZodiacType(Integer.parseInt(map.get("zodiacType").toString()));
						}

						lotteryTypeBeanList.add(b1);

					}
				}

			}
		} catch (Exception e) {
			lotteryTypeBeanList = null;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return lotteryTypeBeanList;
	}

	@Override
	public long selectMaxIssue(int id) throws Exception {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		long result = 0;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return result;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				sb.append("SELECT MAX(period_num) as period_num FROM ctt_manager.ctt_lottery where id = ?   \n");
				selectObj.add(id);
				List<Map<String, Object>> listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				if (listMap != null && listMap.size() == 1) {
					if (listMap.get(0).containsKey("periodNum")) {
						result = Long.parseLong(listMap.get(0).get("periodNum").toString());
					}
				}
			}
		} catch (Exception e) {
			result = 0;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return result;
	}

	@Override
	public boolean checkIssueIsLottery(int id, long issue) throws Exception {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		boolean todo = false;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				sb.append("SELECT count(1) as count FROM ctt_manager.ctt_lottery  \n");
				sb.append(" where id = ? and period_num = ? and status = 0 and data <> '' and data is not null");
				selectObj.add(id);
				selectObj.add(issue);
				List<Map<String, Object>> listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				if (listMap != null && listMap.size() == 1) {
					if (listMap.get(0).containsKey("count")) {
						if (Integer.parseInt(listMap.get(0).get("count").toString()) > 0) {
							todo = true;
						}
					}
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return todo;
	}

	@Override
	public boolean addPairAward(String tableName, long periodNum, Map<String, String> answerMap) throws Exception {
		StringBuilder sb = null;
		List<List<Object>> insertObj = null;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				insertObj = new ArrayList<List<Object>>();

				sb.append("insert into " + tableName + " (period_num , played_id , answer)	\n");
				sb.append("       select ? as p_n,a.played_id as p_i  , ? as an  from	\n");
				sb.append("        (select played_id  from  `ctt_manager`.`ctt_lottery_sub_played` where played_name = ?) a	\n");
				sb.append("  where (	\n");
				sb.append("  select case when c2.c2c=0 then true else false end from (	\n");
				sb.append("      select count(1) as c2c from		\n");
				sb.append("        (select played_id  from  `ctt_manager`.`ctt_lottery_sub_played` where played_name = ?) a2		\n");
				sb.append("        inner join		\n");
				sb.append("        (select played_id from " + tableName + "  where period_num = ?) b2 		\n");
				sb.append("        on a2.played_id = b2.played_id	\n");
				sb.append("      )c2		\n");
				sb.append("  )  	\n");

				System.out.println(sb.toString());
				for (String key : answerMap.keySet()) {
					List<Object> listObj = new ArrayList<Object>();

					listObj.add(periodNum);
					listObj.add(answerMap.get(key).toString());
					listObj.add(key);
					listObj.add(key);
					listObj.add(periodNum);

					insertObj.add(listObj);
				}
				System.out.println(insertObj.toString());
				int[] cou = StmtUtil.updateBatchNoCommit(this.WRITE_CONN, sb.toString(), insertObj);

				return true;
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertObj != null) {
				insertObj.clear();
				insertObj = null;
			}
		}
		return false;
	}

	@Override
	public boolean callProcedure(long periodNum, int lotetryId, String tableName) throws Exception {
		CallableStatement cs = null;
		boolean todo = false;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				this.WRITE_CONN.setAutoCommit(false);
				cs = (CallableStatement) this.WRITE_CONN.prepareCall("{call ctt_manager.ssc_kj_proc(?,?,?)}");

				cs.setLong(1, periodNum);
				cs.setInt(2, lotetryId);
				cs.setString(3, tableName);

				// LOG.error("call ctt_manager.ssc_kj_proc(" + periodNum + "," + lotetryId + ","
				// + tableName + ")");
				

				cs.execute();

				todo = true;

			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				this.WRITE_CONN.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				if (cs != null) {
					cs.cancel();
					cs = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return todo;
	}

	public Date getLastBettingTime(int id) throws Exception {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		Date lastStopBettingTime = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return lastStopBettingTime;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				sb.append("select MAX(stop_betting_time) as lastStopBettingTime from ctt_manager.ctt_lottery where id = ?  \n");
				selectObj.add(id);
				List<Map<String, Object>> listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				if (listMap != null && listMap.size() == 1) {
					if (listMap.get(0).containsKey("lastStopBettingTime")) {
						lastStopBettingTime = MyUtil.toDate(listMap.get(0).get("lastStopBettingTime").toString());
					}
				}
			}
		} catch (Exception e) {
			lastStopBettingTime = null;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return lastStopBettingTime;
	}

	@Override
	public boolean createMainOrderTable(String dataBase, String tableName) throws Exception {
		CallableStatement cs = null;
		boolean todo = false;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {

				cs = (CallableStatement) this.WRITE_CONN.prepareCall("{call ctt_manager.create_main_order_table(?,?)}");

				cs.setString(1, dataBase);
				cs.setString(2, tableName);

				cs.execute();

				todo = true;

			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (cs != null) {
					cs.cancel();
					cs = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return todo;
	}

	@Override
	public boolean createMidOrderTable(String dataBase, String tableName) throws Exception {
		CallableStatement cs = null;
		boolean todo = false;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {

				cs = (CallableStatement) this.WRITE_CONN.prepareCall("{call ctt_manager.create_mid_order_table(?,?)}");

				cs.setString(1, dataBase);
				cs.setString(2, tableName);

				cs.execute();

				todo = true;

			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (cs != null) {
					cs.cancel();
					cs = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return todo;
	}

	@Override
	public boolean createBetOrderTable(String dataBase, String tableName) throws Exception {
		CallableStatement cs = null;
		boolean todo = false;
		checkWrite();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {

				cs = (CallableStatement) this.WRITE_CONN.prepareCall("{call ctt_manager.create_bet_order_table(?,?)}");

				cs.setString(1, dataBase);
				cs.setString(2, tableName);

				cs.execute();

				todo = true;

			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (cs != null) {
					cs.cancel();
					cs = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return todo;
	}

	@Override
	public boolean createWinningOrderTable(String dataBase, String tableName) throws Exception {
		CallableStatement cs = null;
		boolean todo = false;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {

				cs = (CallableStatement) this.WRITE_CONN.prepareCall("{call ctt_manager.create_winning_order_table(?,?)}");

				cs.setString(1, dataBase);
				cs.setString(2, tableName);

				cs.execute();

				todo = true;

			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (cs != null) {
					cs.cancel();
					cs = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return todo;
	}

	@Override
	public boolean checkTableIsExists(String dataBase, String tableName) throws Exception {
		boolean todo = false;
		StringBuilder sb = null;
		List<Object> selectObj = null;
		checkRead();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();

				sb.append("SELECT COUNT(1) as count FROM information_schema.tables where TABLE_SCHEMA = ? and TABLE_NAME = ?");
				selectObj.add(dataBase);
				selectObj.add(tableName);
				List<Map<String, Object>> listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				if (listMap.size() == 1) {
					if (listMap.get(0).containsKey("count") && !"".equals(listMap.get(0).get("count").toString())) {
						if (Integer.parseInt(listMap.get(0).get("count").toString()) > 0) {
							todo = true;
						}
					}
				}

			}
		} catch (SQLException e) {
			todo = false;
			LOG.info("SQLException, " + e.getMessage());
			ShowLog.err(LOG, e);
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {

		}
		return todo;
	}

	@Override
	public boolean checkLotteryCurrentRatio(Date date) throws Exception {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		boolean todo = false;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				sb.append("select COUNT(1) as count from `ctt_manager`.`ctt_lottery_current_ratio`  where date = ?  \n");

				if (MyUtil.dateFormat(date, "yyyy/MM/dd").length() > 0 ? !"".equals(MyUtil.dateFormat(date, "yyyy/MM/dd")) : false) {
					selectObj.add(MyUtil.dateFormat(date, "yyyy/MM/dd"));

					List<Map<String, Object>> listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
					if (listMap != null && listMap.size() == 1) {
						if (listMap.get(0).containsKey("count")) {
							if (Integer.parseInt(listMap.get(0).get("count").toString()) > 0) {
								todo = true;
							}
						}
					}

				} else {
					todo = false;
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return todo;
	}

	@Override
	public boolean insertLotteryCurrentRatio(Date date) throws Exception {
		StringBuilder sb = null;
		List<Object> insertObj = null;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				insertObj = new ArrayList<Object>();
				String dateStr = MyUtil.dateFormat(date, "yyyy/MM/dd");

				sb.append("INSERT INTO `ctt_manager`.`ctt_lottery_current_ratio` 	\n");
				sb.append(
						"(`date`,`local_id`, `lottery_type_id`,`lottery_mid_id`,`lottery_min_id`,`baseline_origin`,`baseline_level1`,`baseline_level2`,`baseline_threshold1`,`baseline_threshold2`,`prize_level`,`amount`,`ratio_index`)	\n");
				sb.append("SELECT '" + dateStr
						+ "' , A.id, B.type_id, B.mid_id, B.min_id, C.baseline, C.baseline_level1, C.baseline_level2, C.bet_level1, C.bet_level2, C.prize_level,'0', '0'  FROM	\n");
				sb.append("(SELECT id,type,title FROM `ctt_manager`.`ctt_lottery_type` where id <> 26) A	\n");
				sb.append("LEFT JOIN	\n");
				sb.append("(	\n");
				sb.append("SELECT level1_id AS type_id,level2_id AS mid_id,auth_id AS min_id FROM `ctt_manager`.`ctt_lottery_auth`	\n");
				sb.append("WHERE 	\n");
				sb.append("level2_id IN (	\n");
				sb.append("   SELECT auth_id FROM `ctt_manager`.`ctt_lottery_auth` 	\n");
				sb.append("   WHERE 	\n");
				sb.append("   level1_id IN (	\n");
				sb.append("      SELECT auth_id FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 1 ) 	\n");
				sb.append("   AND auth_level_type = 2) 	\n");
				sb.append("AND auth_level_type = 3) B	\n");
				sb.append("ON A.type = B.type_id	\n");
				sb.append("LEFT JOIN	\n");
				sb.append("(	\n");
				sb.append("SELECT * FROM `ctt_manager`.`ctt_lottery_amount` 	\n");
				sb.append(")C	\n");
				sb.append(
						"ON A.id=C.lottery_local_id AND A.type = C.lottery_type_id AND B.mid_id = C.lottery_mid_id AND B.min_id = C.lottery_min_id	\n");
				sb.append(
						"where (select COUNT(1) as count from `ctt_manager`.`ctt_lottery_current_ratio`  where date = '" + dateStr + "' ) = 0 	\n");
				sb.append("ORDER BY A.id ASC,A.type ASC, B.mid_id ASC ;	\n");

				long cou = StmtUtil.update(this.WRITE_CONN, sb.toString(), null);

				return true;

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertObj != null) {
				insertObj.clear();
				insertObj = null;
			}
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> selectNowKjPeriodNum() throws Exception {
		StringBuilder sb = null;
		List<Map<String, Object>> lsitMap = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return lsitMap;
			} else {
				sb = new StringBuilder();
				lsitMap = new ArrayList<Map<String, Object>>();

				sb.append(
						"select * from ctt_manager.ctt_lottery where complete_time is null and data <> '' and kj_time <= now() and give_up_kj_time >= now()  \n");
				lsitMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), null);

			}
		} catch (Exception e) {
			lsitMap = null;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return lsitMap;
	}

	@Override
	public boolean goCheckLottery(int id, long periodNum) throws Exception {

		StringBuilder sb = null;
		List<Object> updateObj = null;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				updateObj = new ArrayList<Object>();
				sb.append(
						"update ctt_manager.ctt_lottery set is_check_lottery = true , start_check_lottery_time = now() where id = ? and period_num = ? and is_check_lottery = false \n");
				updateObj.add(id);
				updateObj.add(periodNum);

				long cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
				if (cou != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
		}
		return false;
	}

	@Override
	public boolean createLotteryMonthAndWeekStatistical() throws Exception {
		CallableStatement cs = null;
		boolean todo = false;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {

				cs = (CallableStatement) this.WRITE_CONN.prepareCall("{call ctt_manager.merge_lottery_statistical()}");
				cs.execute();

				todo = true;

			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (cs != null) {
					cs.cancel();
					cs = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return todo;
	}

	@Override
	public boolean isCreateLotteryStatistical() throws Exception {
		StringBuilder sb = null;
		List<Map<String, Object>> lsitMap = null;
		boolean todo = false;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				sb = new StringBuilder();
				lsitMap = new ArrayList<Map<String, Object>>();

				sb.append(
						"select case when A.month = false and  B.week = false and C.month = false and  D.week = false  then true else false end as isCreate from 	\n");
				sb.append(
						"(select case when COUNT(1) > 0 then true else false end as month from ctt_manager.manager_lottery_statistical_month_report where date = DATE_SUB(CURDATE(),INTERVAL 1 DAY) )A	\n");
				sb.append("join	\n");
				sb.append(
						"(select case when COUNT(1) > 0 then true else false end as week from ctt_manager.manager_lottery_statistical_week_report where date = DATE_SUB(CURDATE(),INTERVAL 1 DAY) )B	\n");
				sb.append("join	\n");
				sb.append(
						"(select case when COUNT(1) > 0 then true else false end as month from ctt_manager.member_lottery_statistical_month_report where date = DATE_SUB(CURDATE(),INTERVAL 1 DAY) )C	\n");
				sb.append("join	\n");
				sb.append(
						"(select case when COUNT(1) > 0 then true else false end as week from ctt_manager.member_lottery_statistical_week_report where date = DATE_SUB(CURDATE(),INTERVAL 1 DAY) )D	\n");

				lsitMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), null);

				if (lsitMap != null && lsitMap.size() == 1) {
					if (lsitMap.get(0).containsKey("isCreate")) {
						if ("1".equals(lsitMap.get(0).get("isCreate"))) {
							todo = true;
						}
					}
				}

			}
		} catch (Exception e) {
			lsitMap = null;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}

	@Override
	public boolean callMainOrderAfterGiveUp(int lotetryId, long periodNum) throws Exception {
		boolean todo = false;
		StringBuilder sb = null;
		List<Object> listStr = null;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				sb = new StringBuilder();
				listStr = new ArrayList<Object>();
				this.WRITE_CONN.setAutoCommit(false);

				// LOG.error("call `ctt_manager`.`cancle_main_order_after_giveup`(" + periodNum
				// + "," + lotetryId +")");

				sb.append("call `ctt_manager`.`cancle_main_order_after_giveup`(?,?) \n");

				listStr.add(periodNum);
				listStr.add(lotetryId);

				StmtUtil.callStored(this.WRITE_CONN, sb.toString(), listStr);

				todo = true;

			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (listStr != null) {
				listStr.clear();
				listStr = null;
			}

		}
		return todo;
	}

	@Override
	public List<Map<String, Object>> selectGiveUpPeriodNum() throws Exception {
		StringBuilder sb = null;
		List<Map<String, Object>> lsitMap = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return lsitMap;
			} else {
				sb = new StringBuilder();
				lsitMap = new ArrayList<Map<String, Object>>();

				sb.append(
						"select id,period_num from ctt_manager.ctt_lottery where complete_time is null and jump_off_time < now() and status = 0 and is_giveup = false  \n");
				lsitMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), null);

			}
		} catch (Exception e) {
			lsitMap = null;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw e;
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return lsitMap;
	}

	@Override
	public boolean goGiveUpPeriodNum(int id, long periodNum) throws Exception {

		StringBuilder sb = null;
		List<Object> updateObj = null;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				updateObj = new ArrayList<Object>();
				sb.append(
						"update ctt_manager.ctt_lottery set is_giveup = true where id = ? and period_num = ? and complete_time is null and jump_off_time < now() and status = 0 and is_giveup = false \n");
				updateObj.add(id);
				updateObj.add(periodNum);

				long cou = StmtUtil.update(this.WRITE_CONN, sb.toString(), updateObj);
				if (cou != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
		}
		return false;
	}

	@Override
	public List<LotteryNumBean> getLotteryData(int id) throws Exception {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		List<LotteryNumBean> lotteryNumBeanList = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return lotteryNumBeanList;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				lotteryNumBeanList = new ArrayList<LotteryNumBean>();
				sb.append(
						"select id,period_num,DATE_FORMAT(date,'%Y/%m/%d %H:%i:%S') as date,data,DATE_FORMAT(kj_time,'%Y/%m/%d %H:%i:%S') as kj_time \n");
				sb.append(
						", DATE_FORMAT(actual_kj_time,'%Y/%m/%d %H:%i:%S') as actual_kj_time ,DATE_FORMAT(api_kj_time,'%Y/%m/%d %H:%i:%S') as api_kj_time  \n");
				sb.append(", DATE_FORMAT(give_up_kj_time,'%Y/%m/%d %H:%i:%S') as give_up_kj_time  \n");

				sb.append(" from ctt_manager.ctt_lottery where give_up_kj_time > now() and id = ? and data = '' \n");

				selectObj.add(id);

				List<Map<String, Object>> listObj = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				for (int i = 0; i < listObj.size(); i++) {
					Map<String, Object> map = listObj.get(i);
					LotteryNumBean lotteryNumBean = new LotteryNumBean();
					if (map.containsKey("kjTime")) {
						lotteryNumBean.setKjTime(MyUtil.toDate(map.get("kjTime").toString()));
					}
					if (map.containsKey("periodNum")) {
						lotteryNumBean.setPeriodNum(MyUtil.toLong(map.get("periodNum").toString()));
					}
					if (map.containsKey("date")) {
						lotteryNumBean.setDate(MyUtil.toDate(map.get("date").toString()));
					}
					if (map.containsKey("data")) {
						lotteryNumBean.setData(map.get("data").toString());
					}
					if (map.containsKey("id")) {
						lotteryNumBean.setId(Integer.parseInt(map.get("id").toString()));
					}
					if (map.containsKey("giveUpKjTime")) {
						lotteryNumBean.setGiveUpKjTime(MyUtil.toDate(map.get("giveUpKjTime").toString()));
					}
					lotteryNumBeanList.add(lotteryNumBean);
				}

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return lotteryNumBeanList;
	}

	@Override
	public long getTimeDiffFromDB() throws Exception {
		StringBuilder sb = null;
		long correctionTime = 0;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return correctionTime;
			} else {
				sb = new StringBuilder();
				sb.append("select DATE_FORMAT(now(),'%Y/%m/%d %H:%i:%S') as now \n");

				List<Map<String, Object>> listObj = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), null);

				if (listObj != null && listObj.size() == 1) {
					if (listObj.get(0).containsKey("now")) {
						long nowTime = MyUtil.toDate(listObj.get(0).get("now").toString()).getTime();
						correctionTime = nowTime - new Date().getTime();
					}
				}
			}
		} catch (Exception e) {
			correctionTime = -1;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return correctionTime;
	}

	@Override
	public boolean updateLotteryStatisticalReportData() throws Exception {
		boolean todo = false;
		StringBuilder sb = null;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				sb = new StringBuilder();
				this.WRITE_CONN.setAutoCommit(false);

				sb.append("call `ctt_manager`.`merge_lottery_statistical_report`() \n");

				StmtUtil.callStored(this.WRITE_CONN, sb.toString(), null);

				todo = true;

			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

		}
		return todo;
	}

	@Override
	public boolean checkWeekLotteryData(int id, int startWeek, int endWeek) throws Exception {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				sb.append(
						"select count(1) as count from ctt_manager.ctt_lottery where date >= subdate(curdate(),date_format(curdate(),'%w')-(?)) and date <= subdate(curdate(),date_format(curdate(),'%w')-(?))  and id = ?  \n");

				selectObj.add(startWeek);
				selectObj.add(endWeek);
				selectObj.add(id);

				List<Map<String, Object>> listObj = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				if (listObj.size() == 1) {
					Map<String, Object> map = listObj.get(0);
					if (map.containsKey("count")) {
						if (Integer.parseInt(map.get("count").toString()) > 0) {
							return true;
						}
					}
				}

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return false;
	}

	@Override
	public boolean checkCreatPeriodThisWeekData(int id) throws Exception {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				sb.append("	select count(1) as count  from 	\n");
				sb.append("	(	\n");
				sb.append("	select id,	\n");
				sb.append("		(CASE when date_format(curdate(),'%w') >= create_week_type THEN 	\n");
				sb.append("		subdate(curdate(),date_format(curdate(),'%w')-(create_week_type+7)) 	\n");
				sb.append("		ELSE 	\n");
				sb.append("		subdate(curdate(),date_format(curdate(),'%w')-(create_week_type)) 	\n");
				sb.append("		END) as next_week_create_period_date  	\n");
				sb.append("	from ctt_manager.ctt_lottery_setting where id = ? and create_period_week_type = date_format(curdate(),'%w') 	\n");
				sb.append("	 ) A	\n");
				sb.append("	 inner join 	\n");
				sb.append("	 (	\n");
				sb.append("	 select * from ctt_manager.ctt_lottery 	\n");
				sb.append("	 ) B	\n");
				sb.append("	 on A.id = B.id and A.next_week_create_period_date = B.date	\n");

				selectObj.add(id);

				List<Map<String, Object>> listObj = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				if (listObj.size() == 1) {
					Map<String, Object> map = listObj.get(0);
					if (map.containsKey("count")) {
						if (Integer.parseInt(map.get("count").toString()) > 0) {
							return true;
						}
					}
				}

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return false;
	}

	@Override
	public List<LotterySettingBean> getNextInsertWeekPeriod(int id) throws Exception {
		StringBuilder sb = null;
		List<LotterySettingBean> lotterySettingBeanList = null;
		List<Object> selectObj = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return lotterySettingBeanList;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				lotterySettingBeanList = new ArrayList<LotterySettingBean>();

				sb.append(
						"	select A.id,A.issue,A.kj_time,A.platform_kj_time,A.stop_betting_time,A.give_up_kj_time,A.jump_off_time,A.create_week_time,A.start_betting_week_time,A.start_betting_time from 	\n");
				sb.append("	(	\n");
				sb.append(
						"	select id,issue,kj_time,platform_kj_time,stop_betting_time,give_up_kj_time,jump_off_time,UNIX_TIMESTAMP(subdate(curdate(),date_format(curdate(),'%w')-(create_week_type-7))) as create_week_time	\n");
				sb.append(
						"	, UNIX_TIMESTAMP(subdate(curdate(),date_format(curdate(),'%w')-(start_betting_week_type-7))) as start_betting_week_time , start_betting_time	\n");
				sb.append("	from ctt_manager.ctt_lottery_setting where id = ? 	\n");
				sb.append("	union	\n");
				sb.append(
						"	select id,issue,kj_time,platform_kj_time,stop_betting_time,give_up_kj_time,jump_off_time,UNIX_TIMESTAMP(subdate(curdate(),date_format(curdate(),'%w')-(create_week_type))) as create_week_time	\n");
				sb.append(
						"	, UNIX_TIMESTAMP(subdate(curdate(),date_format(curdate(),'%w')-(start_betting_week_type))) as start_betting_week_time , start_betting_time	\n");
				sb.append("	from ctt_manager.ctt_lottery_setting where id = ?  and create_period_week_type = date_format(curdate(),'%w')	\n");
				sb.append("	) A	\n");
				sb.append("	left join 	\n");
				sb.append("	(select UNIX_TIMESTAMP(date) as date from ctt_manager.ctt_lottery where id = ?) B	\n");
				sb.append("	 on A.create_week_time = B.date 	\n");
				sb.append("	 where B.date is null and A.create_week_time >= UNIX_TIMESTAMP(curdate())  order by A.create_week_time asc \n");

				selectObj.add(id);

				List<Object> listObj = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), selectObj, new ArrayList<LotterySettingBean>());
				if (listObj != null && listObj.size() > 0) {
					for (int i = 0; i < listObj.size(); i++) {
						lotterySettingBeanList.add((LotterySettingBean) listObj.get(i));
					}
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return lotterySettingBeanList;
	}

	@Override
	public List<LotterySettingBean> getThisWeekInsertWeekPeriod(int id) throws Exception {
		StringBuilder sb = null;
		List<LotterySettingBean> lotterySettingBeanList = null;
		List<Object> selectObj = null;
		checkRead();
		try {
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return lotterySettingBeanList;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				lotterySettingBeanList = new ArrayList<LotterySettingBean>();

				sb.append(
						"	select A.id,A.issue,A.kj_time,A.platform_kj_time,A.stop_betting_time,A.give_up_kj_time,A.jump_off_time,A.create_week_time,A.start_betting_week_time,A.start_betting_time from 	\n");
				sb.append("	(	\n");
				sb.append(
						"	select id,issue,kj_time,platform_kj_time,stop_betting_time,give_up_kj_time,jump_off_time,UNIX_TIMESTAMP(subdate(curdate(),date_format(curdate(),'%w')-(create_week_type-7))) as create_week_time	\n");
				sb.append(
						"	, UNIX_TIMESTAMP(subdate(curdate(),date_format(curdate(),'%w')-(start_betting_week_type-7))) as start_betting_week_time , start_betting_time	\n");
				sb.append("	from ctt_manager.ctt_lottery_setting where id = ? 	\n");
				sb.append("	union	\n");
				sb.append(
						"	select id,issue,kj_time,platform_kj_time,stop_betting_time,give_up_kj_time,jump_off_time,UNIX_TIMESTAMP(subdate(curdate(),date_format(curdate(),'%w')-(create_week_type))) as create_week_time	\n");
				sb.append(
						"	, UNIX_TIMESTAMP(subdate(curdate(),date_format(curdate(),'%w')-(start_betting_week_type))) as start_betting_week_time , start_betting_time	\n");
				sb.append("	from ctt_manager.ctt_lottery_setting where id = ?  and create_period_week_type < date_format(curdate(),'%w')	\n");
				sb.append("	) A	\n");
				sb.append("	left join 	\n");
				sb.append("	(select UNIX_TIMESTAMP(date) as date from ctt_manager.ctt_lottery where id = ?) B	\n");
				sb.append("	 on A.create_week_time = B.date 	\n");
				sb.append("	 where B.date is null and A.create_week_time >= UNIX_TIMESTAMP(curdate())  order by A.create_week_time asc \n");

				selectObj.add(id);
				selectObj.add(id);
				selectObj.add(id);

				List<Object> listObj = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), selectObj, new LotterySettingBean());

				if (listObj != null && listObj.size() > 0) {
					for (int i = 0; i < listObj.size(); i++) {
						LotterySettingBean b = (LotterySettingBean) listObj.get(i);
						if (b != null) {
							lotterySettingBeanList.add(b);
						}
					}
				}

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return lotterySettingBeanList;
	}

	@Override
	public boolean deleteLotterySetting(int id) {
		List<Object> deleteObj = null;
		StringBuilder sb = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				deleteObj = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("	delete from ctt_manager.ctt_lottery_setting where id = ?	\n");
				deleteObj.add(id);

				int cout = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), deleteObj);

				if (cout != 0) {
					return true;
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (deleteObj != null) {
				deleteObj.clear();
				deleteObj = null;
			}

		}
		return false;
	}

	@Override
	public boolean insertLotteryTime(List<LotteryTimeSetBean> lotteryTimeSetBeanList) {
		List<List<Object>> insertList = null;
		StringBuilder sb = null;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {

				insertList = new ArrayList<List<Object>>();
				sb = new StringBuilder();
				sb.append("	insert into ctt_manager.ctt_lottery_setting 	\n");
				sb.append(
						" (id,issue,kj_time,platform_kj_time,stop_betting_time,give_up_kj_time,jump_off_time,create_period_week_type,create_week_type,start_betting_week_type,create_period_time,start_betting_time)	\n");
				sb.append("	values (?,?,?,?,?,?,?,?,?,?,?,?)	\n");

				for (int i = 0; i < lotteryTimeSetBeanList.size(); i++) {
					List<Object> insertObj = new ArrayList<Object>();

					LotteryTimeSetBean b1 = lotteryTimeSetBeanList.get(i);

					insertObj.add(b1.getId());
					insertObj.add(b1.getIssue());
					insertObj.add(b1.getKjTime());
					insertObj.add(b1.getPlatformKjTime());
					insertObj.add(b1.getStopBettingTime());
					insertObj.add(b1.getGiveUpKjTime());

					insertObj.add(b1.getJumpOffTime());
					insertObj.add(b1.getCreatePeriodWeekType());
					insertObj.add(b1.getCreateWeekType());

					insertObj.add(b1.getStartBettingWeekType());// start_betting_week_type
					insertObj.add(b1.getCreatePeriodTime());// create_period_time
					insertObj.add(b1.getStartBettingTime());// start_betting_time

					insertList.add(insertObj);
				}

				int cou[] = StmtUtil.updateBatchNoCommit(this.WRITE_CONN, sb.toString(), insertList);

				for (int i = 0; i < cou.length; i++) {
					if (cou[i] != 0) {
						todo = true;
					} else {
						todo = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertList != null) {
				insertList.clear();
				insertList = null;
			}

		}
		return todo;
	}

	@Override
	public boolean updateLotteryType(int id, int createPeriodType) {
		List<Object> updateObj = null;
		StringBuilder sb = null;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {

				updateObj = new ArrayList<Object>();
				sb = new StringBuilder();
				sb.append("	update ctt_manager.ctt_lottery_type set create_period_type = ? where id = ? 	\n");

				updateObj.add(createPeriodType);
				updateObj.add(id);

				int cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);

				if (cou != 0) {
					todo = true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}

		}
		return todo;
	}

	@Override
	public boolean inserLlowfreqLotteryCurrentRatio(int id, String periodNum, Date date) throws Exception {
		StringBuilder sb = null;
		List<Object> insertObj = null;
		checkWrite();
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				insertObj = new ArrayList<Object>();
				String dateStr = MyUtil.dateFormat(date, "yyyy/MM/dd");
				// System.out.println(dateStr);
				sb.append("	INSERT INTO `ctt_manager`.`ctt_lottery_lowfreq_current_ratio` 	\n");
				sb.append(
						"	(`period_num`,`date`,`lottery_local_id`, `lottery_type_id`,`lottery_mid_id`,`lottery_min_id`,`base_bet`,`baseline`,`now_baseline`,`prize_level`,`prize_name`,`handicap`)	\n");
				sb.append("	SELECT ?,'" + dateStr
						+ "', A.id, B.type_id, B.mid_id, B.min_id, C.base_bet, C.baseline, C.baseline, C.prize_level, C.prize_name , C.handicap  FROM	\n");
				sb.append("	(SELECT id,type,title FROM `ctt_manager`.`ctt_lottery_type` where id = ?) A	\n");
				sb.append("	LEFT JOIN	\n");
				sb.append("	(	\n");
				sb.append("	SELECT level1_id AS type_id,level2_id AS mid_id,auth_id AS min_id FROM `ctt_manager`.`ctt_lottery_auth`	\n");
				sb.append("	WHERE 	\n");
				sb.append("	level2_id IN (	\n");
				sb.append("	   SELECT auth_id FROM `ctt_manager`.`ctt_lottery_auth` 	\n");
				sb.append("	   WHERE 	\n");
				sb.append("	   level1_id IN (	\n");
				sb.append("	      SELECT auth_id FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 1) 	\n");
				sb.append("	   AND auth_level_type = 2) 	\n");
				sb.append("	AND auth_level_type = 3) B	\n");
				sb.append("	ON A.type = B.type_id	\n");
				sb.append("	LEFT JOIN	\n");
				sb.append("	(	\n");
				sb.append("	SELECT * FROM `ctt_manager`.`ctt_lottery_lowfreq_amount` 	\n");
				sb.append("	)C	\n");
				sb.append(
						"	ON A.id=C.lottery_local_id AND A.type = C.lottery_type_id AND B.mid_id = C.lottery_mid_id AND B.min_id = C.lottery_min_id	\n");
				sb.append("	WHERE (select COUNT(1) from `ctt_manager`.`ctt_lottery_lowfreq_current_ratio` where date = '" + dateStr
						+ "' and period_num = ? and lottery_local_id = ? ) = 0	\n");
				sb.append("	ORDER BY A.id ASC,A.type ASC, B.mid_id ASC ;	\n");
				// System.out.println(sb.toString());
				insertObj.add(periodNum);
				insertObj.add(id);
				insertObj.add(periodNum);
				insertObj.add(id);
				// System.out.println(insertObj.toString());
				long cou = StmtUtil.update(this.WRITE_CONN, sb.toString(), insertObj);

				return true;

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertObj != null) {
				insertObj.clear();
				insertObj = null;
			}
		}
		return false;
	}
}
