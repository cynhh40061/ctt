package tw.com.ctt;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tw.com.ctt.util.DataSource;
import tw.com.ctt.util.ShowLog;

public class JUnitTest {
	private static final Logger LOG = LogManager.getLogger(JUnitTest.class.getName());

	// @Test
	// public void strTest() {
	// String str = "2018/06/21 00:00:00";
	// String orderstr = "20180621-5-20";
	// String pstr = "20180621026";
	//
	// LOG.debug(orderstr.substring(0, 8));
	// LOG.debug(pstr.substring(0, 8));
	// LOG.debug(str.replaceAll("/", "").replaceAll(":", "").trim().substring(0,
	// 8));
	// }
	@Test
	public void dateTest() {
		String start = "2018/05/21 00:00:00";
		String end = "2018/09/22 12:12:12";

		SimpleDateFormat dateFormat = null;
		SimpleDateFormat getdate = null;
		Calendar dateTime = null;
		Date startDate = null;
		Date endDate = null;
		Date nowDate = null;
		Date minDate = null;
		List<String> tableDate = null;

		try {
			dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			getdate = new SimpleDateFormat("yyyyMMdd");

			dateTime = Calendar.getInstance();

			startDate = dateFormat.parse(start);
			endDate = dateFormat.parse(end);

			nowDate = dateFormat.parse(dateFormat.format(new Date()));
			dateTime.setTime(nowDate);
			dateTime.add(Calendar.DAY_OF_MONTH, -30);
			minDate = dateTime.getTime();

			tableDate = new ArrayList<String>();

			dateTime.setTime(startDate);

			for (dateTime.getTime(); endDate.after(dateTime.getTime()); dateTime.add(Calendar.DAY_OF_MONTH, 1)) {
				if (nowDate.after(dateTime.getTime()) && minDate.before(dateTime.getTime())) {
					tableDate.add(getdate.format(dateTime.getTime()).toString());
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
	}

	@Test
	public void nowDateTest() {
		SimpleDateFormat getdate = null;
		String nowDate = null;
		getdate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		nowDate = getdate.format(new Date());
		LOG.debug(nowDate);
	}

	@Test
	public void JsonTest() {
		JSONObject jsonObj = new JSONObject(
				"{\"orders\":[{\"localId\":\"1\",\"mainOrderId\":\"13\",\"periodNum\":\"20180704031\",\"memberAccId\":\"1\"},{\"localId\":\"1\",\"mainOrderId\":\"12\",\"periodNum\":\"20180704031\",\"memberAccId\":\"1\"},{\"localId\":\"1\",\"mainOrderId\":\"11\",\"periodNum\":\"20180704031\",\"memberAccId\":\"1\"}]}");
		LOG.debug(jsonObj);
		
		JSONArray ordersArray = jsonObj.getJSONArray("orders");
		
		for(int i =0; i< ordersArray.length(); i++) {
			JSONObject eachMain = ordersArray.getJSONObject(i);
			LOG.debug(eachMain);
		}

	}

	protected Connection READ_CONN = null;

	@Test
	public void objForTest() {
		contextInitialized();
		String tableDate = "20180622";
		long accId = 1;
		String midPeriodNum = "null";
		String midStartTime = "2018/06/22 00:00:00";
		String midEndTime = "2018/06/22 23:59:59";
		String midAccName = "mmmm01";
		int midlotteryLocal = 0;
		// List<String> list = new ArrayList<String>();
		//
		// Object[] obj = { tableDate, accId, midPeriodNum, tableDate, accId,
		// midStartTime, midEndTime, midAccName, midlotteryLocal };
		//
		// for (int i = 0; i < obj.length; i++) {
		// LOG.debug(obj[i]);
		// if (obj[i] != null && !"".equals(obj[i])) {
		// list.add(obj[i].toString());
		// }
		// }
		// LOG.debug(list);

		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
			} else {
				sb = new StringBuilder();
				sb.append("SELECT ? AS a,? AS b,? AS c,? AS d,? AS e,? AS f,? AS g,? AS h,? AS i");

				ps = this.READ_CONN.prepareStatement(sb.toString());
				Object[] obj = { "", tableDate, accId, midPeriodNum, tableDate, accId, midStartTime, midEndTime, midAccName, midlotteryLocal };
				for (int i = 1; i < obj.length; i++) {
					if (obj[i] != null && !"".equals(obj[i])) {
						ps.setString(i, obj[i].toString());
					}
				}
				rs = ps.executeQuery();
				while (rs.next()) {
					LOG.debug(rs.getString("a"));
					LOG.debug(rs.getString("b"));
					LOG.debug(rs.getString("c"));
					LOG.debug(rs.getString("d"));
					LOG.debug(rs.getString("e"));
					LOG.debug(rs.getString("f"));
					LOG.debug(rs.getString("g"));
					LOG.debug(rs.getString("h"));
					LOG.debug(rs.getString("i"));
				}
			}
		} catch (Exception e) {
			LOG.error("test()_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {

		}
	}

	@Test
	public void splitTest() {
		String nowDate = "20180621-01-01";
		LOG.debug(nowDate.split("-"));
		LOG.debug(nowDate.split("-")[0]);
		LOG.debug(nowDate.split("-")[1]);
		LOG.debug(nowDate.split("-")[2]);
	}

	@Test
	public void splitAndAddTest() {
		String nowDate = "2018/06/25~2018/07/01";
		LOG.debug(nowDate.split("~")[1]);

		Calendar nowDateTime = null;
		SimpleDateFormat getdate = null;
		Date ddate = null;

		nowDateTime = Calendar.getInstance();
		getdate = new SimpleDateFormat("yyyy/MM/dd");
		try {
			ddate = getdate.parse(nowDate.split("~")[1]);
			nowDateTime.setTime(ddate);
			LOG.debug(getdate.format(nowDateTime.getTime()));
			nowDateTime.add(Calendar.DAY_OF_MONTH, 1);
			LOG.debug(getdate.format(nowDateTime.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void sevenDaysTest() {
		SimpleDateFormat getdate = null;
		Calendar nowDateTime = null;
		Calendar daysAgo = null;
		Calendar weekAgo = null;
		Calendar sixAgo = null;
		Calendar mounthAgo = null;
		Date nowDate = null;
		List<String> dayTableDate = null;
		List<String> weekTableDate = null;
		List<String> mounthTableDate = null;

		try {
			getdate = new SimpleDateFormat("yyyy/MM/dd");
			nowDate = getdate.parse(getdate.format(new Date()));

			nowDateTime = Calendar.getInstance();
			daysAgo = Calendar.getInstance();
			weekAgo = Calendar.getInstance();
			sixAgo = Calendar.getInstance();
			mounthAgo = Calendar.getInstance();

			nowDateTime.setTime(nowDate);

			daysAgo.add(Calendar.DAY_OF_MONTH, -30);
			weekAgo.add(Calendar.WEEK_OF_MONTH, -7);
			mounthAgo.add(Calendar.MONTH, -6);

			dayTableDate = new ArrayList<String>();
			weekTableDate = new ArrayList<String>();
			mounthTableDate = new ArrayList<String>();

			for (nowDateTime.getTime(); nowDateTime.getTime().after(daysAgo.getTime()); nowDateTime.add(Calendar.DAY_OF_MONTH, -1)) {
				getdate = new SimpleDateFormat("yyyy/MM/dd");
				dayTableDate.add(getdate.format(nowDateTime.getTime()).toString());
			}
			nowDateTime.setTime(nowDate);
			for (nowDateTime.getTime(); nowDateTime.getTime().after(weekAgo.getTime()); nowDateTime.add(Calendar.WEEK_OF_MONTH, -1)) {
				getdate = new SimpleDateFormat("yyyy/MM/dd");
				sixAgo.setTime(nowDateTime.getTime());
				sixAgo.add(Calendar.DAY_OF_MONTH, -6);
				weekTableDate.add(getdate.format(sixAgo.getTime()).toString() + "~" + getdate.format(nowDateTime.getTime()).toString());
			}
			nowDateTime.setTime(nowDate);
			for (nowDateTime.getTime(); nowDateTime.getTime().after(mounthAgo.getTime()); nowDateTime.add(Calendar.MONTH, -1)) {
				getdate = new SimpleDateFormat("yyyy/MM");
				mounthTableDate.add(getdate.format(nowDateTime.getTime()).toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			LOG.debug(getdate.format(nowDateTime.getTime()));
			LOG.debug(getdate.format(daysAgo.getTime()));
			LOG.debug(getdate.format(weekAgo.getTime()));
			LOG.debug(getdate.format(mounthAgo.getTime()));
			LOG.debug(" \n");
			LOG.debug(dayTableDate);
			LOG.debug(dayTableDate.size());
			LOG.debug(weekTableDate);
			LOG.debug(weekTableDate.size());
			LOG.debug(mounthTableDate);
			LOG.debug(mounthTableDate.size());
		}
	}

	private void checkRead() {
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

	@Test
	public void listTest() {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new ConcurrentHashMap<String, Object>();

		map.put("tableDate", "20180622");
		listMap.add(map);
		map.put("tableDate", "20180623");
		listMap.add(map);
		map.put("tableDate", "20180624");
		listMap.add(map);

		LOG.debug(listMap.toString());
		LOG.debug(listMap.get(0).get("tableDate").toString());
		LOG.debug(listMap.get(1).get("tableDate").toString());
		LOG.debug(listMap.get(2).get("tableDate").toString());
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
			inputStream = getClass().getClassLoader().getResourceAsStream("testconfig.properties");
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
