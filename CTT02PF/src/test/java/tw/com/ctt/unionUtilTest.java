package tw.com.ctt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tw.com.ctt.util.DataSource;
import tw.com.ctt.util.ShowLog;

public class unionUtilTest {
	private static final Logger LOG = LogManager.getLogger(JUnitTest.class.getName());

	@Test
	public void nowDateTest() {
		List<String> tableDateList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			tableDateList.add("a" + i);
		}

		try {
			LOG.debug(getUnionTable(tableDateList, "SELECT column FROM database.table_name", "tableName"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Before
	public void Before() {
	}

	private static String getUnionTable(List<String> tableDateList, String sb, String tableName) throws SQLException {
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String output = null;
		try {
			if (tableDateList.size() > 0) {
				output = "";
				for (int i = 0; i < tableDateList.size(); i++) {
					if ("".equals(output)) {
						output = sb;
					} else {
						output = output + "\nUNION ALL \n" + sb;
					}
				}
				if ("".equals(output)) {
					LOG.info("error!!!!!!");
					return null;
				} else {
					output = "(SELECT * FROM(\n" + output + ")G_ALL) grp_" + tableName;
					LOG.debug("getUnionTable:\n" + output);
					return output.toString();
				}
			}
			return null;
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e1) {
					LOG.info("SQLException:" + e1.getMessage());
					ShowLog.err(LOG, e1);
					throw e1;
				} finally {
					ps = null;
				}
			}
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e1) {
					LOG.info("SQLException:" + e1.getMessage());
					ShowLog.err(LOG, e1);
					throw e1;
				} finally {
					rs = null;
				}
			}
			sql = "";
			sql = null;
			output = "";
			output = null;
		}
	}

	@After
	public void after() {

	}
}
