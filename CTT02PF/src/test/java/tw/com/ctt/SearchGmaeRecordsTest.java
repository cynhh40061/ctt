package tw.com.ctt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tw.com.ctt.dao.impl.GameRecordsDaoImpl;
import tw.com.ctt.service.impl.GameRecordsServiceImpl;
import tw.com.ctt.util.DataSource;

public class SearchGmaeRecordsTest {
	
	private static final Logger LOG = LogManager.getLogger(SearchGmaeRecordsTest.class.getName());

	@Test
	public void	SearchGmaeRecordsMapTest() {
		GameRecordsServiceImpl service = null;
		GameRecordsDaoImpl dao = null;
		service = new GameRecordsServiceImpl(1, "0.0.0.0");
		dao = new GameRecordsDaoImpl();
		service.setDao(dao);
		LOG.debug("searchRecordsTotleCount="+service.searchRecordsTotleCount(Long.parseLong("201804260001000001"), "mm02", "2018/4/20 04:37:38", "2018/4/30 04:37:38", 3));
		LOG.debug("returnInfo="+service.searchRecords(Long.parseLong("201804260001000001"), "mm02", "2018/4/20 04:37:38", "2018/4/30 04:37:38", 3,0,25));
	}
	@Test
	public void	SearchGmaeRecordsMapTest2() {
		GameRecordsServiceImpl service = null;
		GameRecordsDaoImpl dao = null;
		service = new GameRecordsServiceImpl(1, "0.0.0.0");
		dao = new GameRecordsDaoImpl();
		service.setDao(dao);
		LOG.debug("searchRecordsTotleCount2="+service.searchRecordsTotleCount(Long.parseLong("0"), "mm02", "2018/4/20 04:37:38", "2018/4/30 04:37:38", 0));
		LOG.debug("returnInfo2="+service.searchRecords(Long.parseLong("0"), "mm02", "2018/4/20 04:37:38", "2018/4/30 04:37:38", 0,0,25));
	}
	@Test
	public void	SearchGmaeRecordsMapTest3() {
		GameRecordsServiceImpl service = null;
		GameRecordsDaoImpl dao = null;
		service = new GameRecordsServiceImpl(1, "0.0.0.0");
		dao = new GameRecordsDaoImpl();
		service.setDao(dao);
		LOG.debug("searchRecordsTotleCount3="+service.searchRecordsTotleCount(Long.parseLong("0"), "", "2018/4/20 04:37:38", "2018/4/30 04:37:38", 0));
		LOG.debug("returnInfo3="+service.searchRecords(Long.parseLong("0"), "", "2018/4/20 04:37:38", "2018/4/30 04:37:38", 0, 0,25));
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
