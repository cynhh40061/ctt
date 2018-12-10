
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tw.com.ctt.model.MgrBean;
import tw.com.ctt.model.PunchGameRecordsBean;
import tw.com.ctt.util.CommandUtil;
import tw.com.ctt.util.DataSource;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class CTTUtilityTest {

	private static final Logger LOG = LogManager.getLogger(CTTUtilityTest.class.getName());
	protected Connection READ_CONN;

	// @Test
	public void queryToBeanDateTimeTest() {
		READ_CONN = DataSource.getReadConnection();
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		MgrBean bean = new MgrBean();
		List<Object> list = new ArrayList<Object>();
		try {
			sb.append("	select online_datetime AS datetime from `ctt_manager`.`ctt_manager_token`	\n");
			list = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), params, bean);
			for (int k = 0; k < list.size(); k++) {
				LOG.debug(((MgrBean) list.get(k)).getDatetime());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (list != null) {
				list.clear();
				list = null;
			}
		}
	}

//	@Test
	public void getBeanToMapByMapUtilTest() {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Object> list = null;
		Map<String, Object> tmpMap = null;
		List<Map<String, Object>> tmpMapList = null;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			tmpMapList = new ArrayList<Map<String, Object>>();
			sb.append("SELECT game_id,server_id,p1_acc_id AS acc_id \n");
			sb.append(",(SELECT acc_name FROM ctt_manager.ctt_member_acc WHERE acc_id = p1_acc_id) AS acc_name \n");
			sb.append(
					",start_time,end_time,bet,p1_acc_start_balance AS acc_start_balance,p1_acc_win_goal AS acc_win_goal,game_process,game_type,result \n");
			sb.append("FROM \n");
			sb.append("ctt_manager.ctt_game_punche_records \n");
			sb.append("WHERE \n");
			sb.append("(start_time > ? && end_time < ?) \n");
			params.add("2018-01-01 00:00:00");
			params.add("2018-06-01 00:00:00");
			sb.append("AND \n");
			sb.append("p1_acc_id IN(SELECT A.acc_id FROM (SELECT acc_id FROM ctt_manager.ctt_member_acc_level WHERE \n");
			sb.append("(CASE(SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
			params.add(1);
			sb.append("WHEN 0 THEN TRUE \n");
			sb.append("WHEN 1 THEN TRUE \n");
			sb.append("WHEN 2 THEN sc \n");
			sb.append("WHEN 3 THEN bc \n");
			sb.append("WHEN 4 THEN co \n");
			sb.append("WHEN 5 THEN sa \n");
			sb.append("WHEN 6 THEN ag \n");
			sb.append("WHEN 7 THEN ag1 \n");
			sb.append("WHEN 8 THEN ag2 \n");
			sb.append("WHEN 9 THEN ag3 \n");
			sb.append("WHEN 10 THEN ag4 \n");
			sb.append("WHEN 11 THEN ag5 \n");
			sb.append("WHEN 12 THEN ag6 \n");
			sb.append("WHEN 13 THEN ag7 \n");
			sb.append("WHEN 14 THEN ag8 \n");
			sb.append("WHEN 15 THEN ag9 \n");
			sb.append("WHEN 16 THEN ag10 \n");
			sb.append("END ) = ?)A)\n");
			params.add(1);
			sb.append("UNION \n");
			sb.append("SELECT game_id,server_id,p2_acc_id AS acc_id \n");
			sb.append(",(SELECT acc_name FROM ctt_manager.ctt_member_acc WHERE acc_id = p2_acc_id) AS acc_name \n");
			sb.append(
					",start_time,end_time,bet,p2_acc_start_balance AS acc_start_balance,p2_acc_win_goal AS acc_win_goal,game_process,game_type,result \n");
			sb.append("FROM \n");
			sb.append("ctt_manager.ctt_game_punche_records \n");
			sb.append("WHERE \n");
			sb.append("(start_time > ? && end_time < ?) \n");
			params.add("2018-01-01 00:00:00");
			params.add("2018-06-01 00:00:00");
			sb.append("AND \n");
			sb.append("p2_acc_id IN(SELECT B.acc_id FROM (SELECT acc_id FROM ctt_manager.ctt_member_acc_level WHERE \n");
			sb.append("(CASE(SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
			params.add(1);
			sb.append("WHEN 0 THEN TRUE \n");
			sb.append("WHEN 1 THEN TRUE \n");
			sb.append("WHEN 2 THEN sc \n");
			sb.append("WHEN 3 THEN bc \n");
			sb.append("WHEN 4 THEN co \n");
			sb.append("WHEN 5 THEN sa \n");
			sb.append("WHEN 6 THEN ag \n");
			sb.append("WHEN 7 THEN ag1 \n");
			sb.append("WHEN 8 THEN ag2 \n");
			sb.append("WHEN 9 THEN ag3 \n");
			sb.append("WHEN 10 THEN ag4 \n");
			sb.append("WHEN 11 THEN ag5 \n");
			sb.append("WHEN 12 THEN ag6 \n");
			sb.append("WHEN 13 THEN ag7 \n");
			sb.append("WHEN 14 THEN ag8 \n");
			sb.append("WHEN 15 THEN ag9 \n");
			sb.append("WHEN 16 THEN ag10 \n");
			sb.append("END ) = ?)B) \n");
			params.add(1);
			sb.append("ORDER BY end_time DESC \n");
			sb.append("limit ?,? \n");
			params.add(0);
			params.add(20);

			list = new ArrayList<Object>();
			list = StmtUtil.queryToBean(READ_CONN, sb.toString(), params, new PunchGameRecordsBean());
			if (list.size() > 0) {
				
				
				for (int i = 0; i < list.size(); i++) {
					PunchGameRecordsBean bean = (PunchGameRecordsBean) list.get(i);
					String beanKey[] = {"game_name", "acc_name", "acc_id", "game_id", "game_type", "start_time", "end_time", "start_balance", "end_balance", "bet", "net_amount", "win_goal", "fees", "game_times", "game_process"};
					String searchKey[] = { "gameName", "accName", "accId", "gameId", "gameType", "startTime", "endTime", "startBalance", "endBalance",
							"bet", "netAmount", "winGoal", "fees", "gameTimes", "gameProcess" };
					Map<String, String> beanMap = new HashMap<String, String>();
					for(int num = 0;num < searchKey.length;num++) {
						beanMap.put(beanKey[num], searchKey[num]);
					}
					tmpMap = new ConcurrentHashMap<String, Object>();
					tmpMap = CommandUtil.getBeanToMapByMap(bean, beanMap);
					tmpMapList.add(tmpMap);
				}
			}
		} catch (Exception e) {
			tmpMapList = null;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params.clear();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (list != null) {
				list.clear();
				list = null;
			}
			LOG.debug(tmpMapList.toString());
		}
	}
	@Test
	public void getUnionTableTest() {
		String fmt = null;
		String test = null;
		try {
			fmt = "201806";
			test = StmtUtil.getUnionTable(READ_CONN, "ctt_manager", "bet_order", "bet_order_"+fmt+"%", "");
			LOG.debug(test);
		} catch (SQLException e) {
			LOG.debug(e);
			e.printStackTrace();
		}finally {
		}
	}

	@Before
	public void contextInitialized() {
		Properties props = new Properties();
		InputStream inputStream = null;
		String username = "";
		String passwd = "";
		String url = "";
		String driver = "";
		String datasource = "";
		int maxConn = 0;
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream("baseconfig.properties");
			props.load(inputStream);
			username = (String) props.get("READ_DB_USER_NAME");
			passwd = (String) props.get("READ_DB_PASSWORD");
			url = (String) props.get("READ_DB_URL");
			driver = (String) props.get("READ_DB_DRIVER");
			datasource = (String) props.get("READ_DATASOURCE");
			maxConn = Integer.parseInt((String) props.get("READ_DB_MAX_CONNECTIONS"));
			tw.com.ctt.util.Configuration.READ_DB_USER_NAME = username;
			tw.com.ctt.util.Configuration.READ_DB_PASSWORD = passwd;
			tw.com.ctt.util.Configuration.READ_DB_URL = url;
			tw.com.ctt.util.Configuration.READ_DB_DRIVER = driver;
			tw.com.ctt.util.Configuration.READ_DATASOURCE = datasource;
			tw.com.ctt.util.Configuration.READ_DB_MAX_CONNECTIONS = maxConn;
			username = (String) props.get("WRITE_DB_USER_NAME");
			passwd = (String) props.get("WRITE_DB_PASSWORD");
			url = (String) props.get("WRITE_DB_URL");
			driver = (String) props.get("WRITE_DB_DRIVER");
			datasource = (String) props.get("WRITE_DATASOURCE");
			maxConn = Integer.parseInt((String) props.get("WRITE_DB_MAX_CONNECTIONS"));
			tw.com.ctt.util.Configuration.WRITE_DB_USER_NAME = username;
			tw.com.ctt.util.Configuration.WRITE_DB_PASSWORD = passwd;
			tw.com.ctt.util.Configuration.WRITE_DB_URL = url;
			tw.com.ctt.util.Configuration.WRITE_DB_DRIVER = driver;
			tw.com.ctt.util.Configuration.WRITE_DATASOURCE = datasource;
			tw.com.ctt.util.Configuration.WRITE_DB_MAX_CONNECTIONS = maxConn;
			DataSource.returnReadConnection(null);
			DataSource.returnWriteConnection(null);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				inputStream = null;
			}
			if (props != null) {
				props.clear();
				props = null;
			}
			username = "";
			passwd = "";
			url = "";
			driver = "";
			datasource = "";
			username = null;
			passwd = null;
			url = null;
			driver = null;
			datasource = null;
			maxConn = 0;
		}
	}

	@After
	public void after() {

	}
}
