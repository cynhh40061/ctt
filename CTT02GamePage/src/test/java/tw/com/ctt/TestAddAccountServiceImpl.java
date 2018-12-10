package tw.com.ctt;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import tw.com.ctt.dao.impl.AccountManageDaoImpl;
//import tw.com.ctt.service.impl.AccountManageServiceImpl;
import tw.com.ctt.util.DataSource;

public class TestAddAccountServiceImpl {

	
	@Test
	public void	updateMgr() {
//		TestAddAccountServiceImpl context = new TestAddAccountServiceImpl();
//		context.contextInitialized();
//		AccountManageServiceImpl testService = new AccountManageServiceImpl();
//		AccountManageDaoImpl testDao = new AccountManageDaoImpl();
//		testService.setDao(testDao);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("minRatioG1", 40);
//		map.put("minRatioG2", 40);
//		map.put("minRatioG3", 40);
//		map.put("minRatioG4", 40);
//		map.put("minRatioG5", 40);
//		
//		map.put("maxRatioG1", 80);
//		map.put("maxRatioG2", 80);
//		map.put("maxRatioG3", 80);
//		map.put("maxRatioG4", 80);
//		map.put("maxRatioG5", 80);
//		
//		map.put("currentRatioG1", 80);
//		map.put("currentRatioG2", 80);
//		map.put("currentRatioG3", 80);
//		map.put("currentRatioG4", 80);
////		map.put("currentRatioG5", 80);
//		map.put("nickname", "nickname");
//		map.put("XXXaccId", 27);
//		
//		boolean test = testService.updateCom(map);	
//		boolean test = false;
//		System.out.println("test==="+test);
//		assertTrue(test);
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
