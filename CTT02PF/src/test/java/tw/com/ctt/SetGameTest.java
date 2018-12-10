package tw.com.ctt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tw.com.ctt.dao.impl.LotterySetDaoImpl;
import tw.com.ctt.dao.impl.SetGameDaoImpl;
import tw.com.ctt.service.impl.LotterySetServiceImpl;
import tw.com.ctt.service.impl.SetGameServiceImpl;
import tw.com.ctt.util.DataSource;

public class SetGameTest {
	
	private static final Logger LOG = LogManager.getLogger(SetGameTest.class.getName());
	
//	@Test
	public void	SetGmaeTest() {
		SetGameServiceImpl service = null;
		SetGameDaoImpl dao = null;
		service = new SetGameServiceImpl(1, "0.0.0.0");
		dao = new SetGameDaoImpl();
		service.setDao(dao);
		LOG.debug("searchRecordsTotleCount="+service.getGameParamData());
		LOG.debug("size="+service.getGameParamData().size());
	}
//	@Test
	public void	getTotalAmountInfo() {
		LotterySetServiceImpl service = null;
		LotterySetDaoImpl dao = null;
		
		service = new LotterySetServiceImpl();
		dao = new LotterySetDaoImpl();
		service.setDao(dao);
		
		LOG.debug(service.getTotalAmountInfo());
	}
	@Test
	public void	updateAmountInfo() {
		LotterySetServiceImpl service = null;
		LotterySetDaoImpl dao = null;
		String amountInfoListStr = null;
		String[] amountInfoList = null;
		
		service = new LotterySetServiceImpl();
		dao = new LotterySetDaoImpl();
		service.setDao(dao);
		
//		amountInfoListStr = [{"baseLine":"1111","playedText":"1234","midName":"五星直選","dtRatio":1,"lotteryRule":"1234","baseBet":"111","betLevel1":0,"playedId":8,"betLevel2":0,"dtSwtch":false,"handicapId":1,"midId":155,"baseLineLevel1":"1111","baseLineLevel2":1,"dtBonus":0,"typeId":1,"lotteryExample":"1234"},
//		                     {"baseLine":"222","playedText":"","midName":"五星單式","dtRatio":1,"lotteryRule":"","baseBet":"222","betLevel1":0,"playedId":8,"betLevel2":0,"dtSwtch":false,"handicapId":1,"midId":156,"baseLineLevel1":1,"baseLineLevel2":1,"dtBonus":0,"typeId":1,"lotteryExample":""}];
//		
//		String[] amountInfoList
//		
//		LOG.debug(service.updateAmountInfo());
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
