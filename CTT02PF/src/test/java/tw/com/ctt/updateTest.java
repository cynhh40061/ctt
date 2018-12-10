package tw.com.ctt;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tw.com.ctt.dao.impl.AccountManageDaoImpl;
import tw.com.ctt.service.impl.AccountManageServiceImpl;
import tw.com.ctt.util.CommandUtil;
import tw.com.ctt.util.DataSource;

public class updateTest {
	private static final Logger LOG = LogManager.getLogger(SetGameTest.class.getName());
	
	@Test
	public void	SetGmaeTest() {
		AccountManageServiceImpl service = new AccountManageServiceImpl(1,"0.0.0.0");
		AccountManageDaoImpl dao = new AccountManageDaoImpl();
		Map<String, Object> map =  new ConcurrentHashMap<String, Object>();
		map.put("updateAccId", 1);
		map.put("ip", "0.0.0.0");
		
		map.put("accId", 11);
		map.put("pwd", "1234");
		map.put("nickname", "nickname");
		map.put("minRatioG1", 10);
		map.put("minRatioG2", 20);
		map.put("minRatioG3", 30);
		map.put("minRatioG4", 40);
		map.put("minRatioG5", 50);
//		MinRatioNext
//		map.put("minRatioG1", 10);
//		map.put("minRatioG2", 20);
//		map.put("minRatioG3", 30);
//		map.put("minRatioG4", 40);
//		map.put("minRatioG5", 50);
		
		service.setDao(dao);
		service.setDao(dao);
		
		LOG.debug("searchRecordsTotleCount="+service.updateMem(map));
	}
	@Test
	public void	eqlTest() {
		String str1 = "str1";
		String str2 = "str1";
		String str3 = "str3";
		
		int int1 = 1;
		int int2 = 1;
		int int3 = 3;
		
		float float1 = 1.01F;
		float float2 = 1.01F;
		float float3 = 3.03F;
		
		double double1 = 11.001D;
		double double2 = 11.001D;
		double double3 = 33.003D;
		
		boolean boo1 = true;
		boolean boo2 = true;
		boolean boo3 = false;
		
		BigDecimal big1 = new BigDecimal("10.01");
		BigDecimal big2 = new BigDecimal("10.01"); 
		BigDecimal big3 = new BigDecimal("30.03"); 
		
		LOG.debug("str12="+CommandUtil.equalsString(str1, str2));
		LOG.debug("str13="+CommandUtil.equalsString(str1, str3));
		LOG.debug("int12="+CommandUtil.equalsInt(int1, int2));
		LOG.debug("int13="+CommandUtil.equalsInt(int1, int3));
		LOG.debug("float12="+CommandUtil.equalsFloat(float1,float2));
		LOG.debug("float13="+CommandUtil.equalsFloat(float1,float3));
		LOG.debug("double12="+CommandUtil.equalsDouble(double1,double2));
		LOG.debug("double13="+CommandUtil.equalsDouble(double1,double3));
		LOG.debug("boo12="+CommandUtil.equalsBoolean(boo1,boo2));
		LOG.debug("boo13="+CommandUtil.equalsBoolean(boo1,boo3));
		LOG.debug("big12="+CommandUtil.equalsBigDecimal(big1,big2));
		LOG.debug("big13="+CommandUtil.equalsBigDecimal(big1,big3));
	}
	@Test
	public void	compareTest() {
		String str1 = "1";
		String str2 = "1";
		int str3 = 1;
		int str4 = 4;
		
		int int1 = 1;
		int int2 = 1;
		String int3 = "1";
		String int4 = "4";
		
		float float1 = 1.01F;
		float float2 = 1.01F;
		double float3 = 1.01D;
		double float4 = 4.04D;
		
		double double1 = 11.001D;
		double double2 = 11.001D;
		float double3 = 11.001F;
		float double4 = 44.004F;
		
		boolean boo1 = true;
		boolean boo2 = true;
		String boo3 = "true";
		String boo4 = "false";
		
		BigDecimal big1 = new BigDecimal("10.01");
		BigDecimal big2 = new BigDecimal("10.01"); 
		float big3 = 10.01f; 
		float big4 = 40.04f; 
		
		LOG.debug("str12="+CommandUtil.compareString(str1, str2));
		LOG.debug("str13="+CommandUtil.compareString(str1, str3));
		LOG.debug("str14="+CommandUtil.compareString(str1, str4));
		LOG.debug("int12="+CommandUtil.compareInt(int1, int2));
		LOG.debug("int13="+CommandUtil.compareInt(int1, int3));
		LOG.debug("int14="+CommandUtil.compareInt(int1, int4));
		LOG.debug("float12="+CommandUtil.compareFloat(float1,float2));
		LOG.debug("float13="+CommandUtil.compareFloat(float1,float3));
		LOG.debug("float14="+CommandUtil.compareFloat(float1,float4));
		LOG.debug("double12="+CommandUtil.compareDouble(double1,double2));//int13
		LOG.debug("double13="+CommandUtil.compareDouble(double1,double3));
		LOG.debug("double14="+CommandUtil.compareDouble(double1,double4));
		LOG.debug("boo12="+CommandUtil.compareBoolean(boo1,boo2));
		LOG.debug("boo13="+CommandUtil.compareBoolean(boo1,boo3));
		LOG.debug("boo14="+CommandUtil.compareBoolean(boo1,boo4));
		LOG.debug("big12="+CommandUtil.compareBigDecimal(big1,big2));
		LOG.debug("big13="+CommandUtil.compareBigDecimal(big1,big3));
		LOG.debug("big14="+CommandUtil.compareBigDecimal(big1,big4));
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
