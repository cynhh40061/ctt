package tw.com.ctt.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import tw.com.ctt.util.Configuration;
import tw.com.ctt.util.ShowLog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class test extends ArrayList<Object> {

	private static final long serialVersionUID = -870141813457591101L;

	private static final Logger LOG = LogManager.getLogger(test.class.getName());

	private String[] USERS;
	private String[] PASSWDS;
	private String[] URLS;
	private String[] SOURCES;
	private int COUNT = 0;
	private final String UTF8_CHARSET = "?useUnicode=true&characterEncoding=UTF-8&collation=utf8_unicode_ci&useServerPrepStmts=false&autoReconnect=true&useSSL=false&serverTimezone=GMT%2B8";
	private final String TIMEOUT = "&connectTimeout=30000&socketTimeout=600000";
	private final String SHORT_TIMEOUT = "&connectTimeout=15000&socketTimeout=15000";

	List<Connection> readConnList = new ArrayList<Connection>();
	List<Connection> writeConnList = new ArrayList<Connection>();

	private static Configuration config = null;
	private int readDBMinConn = 1;
	private int readDBMaxConn = 10;
	private int writeDBMinConn = 1;
	private int writeDBMaxConn = 10;

	public test() {
		initializeConnectionPool();
	}

	@SuppressWarnings("static-access")
	public synchronized void initializeConnectionPool() {
		if (config == null) {
			config = Configuration.getInstance();
			readDBMinConn = config.READ_DB_MIN_CONNECTIONS;
			readDBMaxConn = config.READ_DB_MAX_CONNECTIONS;
			writeDBMinConn = config.WRITE_DB_MIN_CONNECTIONS;
			writeDBMaxConn = config.WRITE_DB_MAX_CONNECTIONS;
		}
		if (readConnList == null || writeConnList == null) {
			readConnList = new ArrayList<Connection>();
			writeConnList = new ArrayList<Connection>();
		}
		while (readConnList.size() < readDBMaxConn) {
			if (readConnList.size() <= readDBMinConn) {
				readConnList.add(createNewReadConnectionForPool(true));
			} else if (readConnList.size() > readDBMinConn) {
				readConnList.add(null);
			}
		}
		while (writeConnList.size() < writeDBMaxConn) {
			if (writeConnList.size() <= writeDBMinConn) {
				writeConnList.add(createNewWriteConnectionForPool(true));
			} else if (writeConnList.size() > writeDBMinConn) {
				writeConnList.add(null);
			}
		}
	}

	@SuppressWarnings("static-access")
	private Connection createNewReadConnectionForPool(boolean timeOutType) {
		Connection connection = null;
		String options = null;
		try {
			if (config == null) {
				config = Configuration.getInstance();
				readDBMinConn = config.READ_DB_MIN_CONNECTIONS;
				readDBMaxConn = config.READ_DB_MAX_CONNECTIONS;
				writeDBMinConn = config.WRITE_DB_MIN_CONNECTIONS;
				writeDBMaxConn = config.WRITE_DB_MAX_CONNECTIONS;
			}

			if (timeOutType) {
				options = UTF8_CHARSET + TIMEOUT;
			} else {
				options = UTF8_CHARSET + SHORT_TIMEOUT;
			}

			if (config.READ_DATASOURCE == null || config.READ_DATASOURCE.isEmpty() || "".equals(config.READ_DATASOURCE.trim())) {
				if (USERS == null || USERS.length == 0) {
					USERS = config.READ_DB_USER_NAME.split(";");
					PASSWDS = config.READ_DB_PASSWORD.split(";");
					URLS = config.READ_DB_URL.split(";");
				}
				Class.forName(config.READ_DB_DRIVER);
				connection = (Connection) DriverManager.getConnection(URLS[COUNT] + options, USERS[COUNT], PASSWDS[COUNT]);
				if (COUNT == USERS.length - 1) {
					COUNT = 0;
				} else {
					COUNT++;
				}
			} else {
				if (SOURCES == null || SOURCES.length == 0) {
					SOURCES = config.READ_DATASOURCE.split(";");
				}
				DataSource ds = (DataSource) new InitialContext().lookup(SOURCES[COUNT]);
				connection = (Connection) ds.getConnection();
				if (COUNT == SOURCES.length - 1) {
					COUNT = 0;
				} else {
					COUNT++;
				}
			}

			if (!isDbConnected(connection)) {
				connection = createNewReadConnectionForPool(timeOutType);
			}

			connection = setConnInit(connection);
			return connection;
		} catch (Exception e) {
			LOG.info("Exception");
			ShowLog.err(LOG, e);
		} finally {
			options = "";
			options = null;
		}
		return null;
	}

	@SuppressWarnings("static-access")
	private Connection createNewWriteConnectionForPool(boolean timeOutType) {
		Connection connection = null;
		String options = null;
		try {
			if (config == null) {
				config = Configuration.getInstance();
				readDBMinConn = config.READ_DB_MIN_CONNECTIONS;
				readDBMaxConn = config.READ_DB_MAX_CONNECTIONS;
				writeDBMinConn = config.WRITE_DB_MIN_CONNECTIONS;
				writeDBMaxConn = config.WRITE_DB_MAX_CONNECTIONS;
			}

			if (timeOutType) {
				options = UTF8_CHARSET + TIMEOUT;
			} else {
				options = UTF8_CHARSET + SHORT_TIMEOUT;
			}

			if (config.WRITE_DATASOURCE == null || config.WRITE_DATASOURCE.isEmpty() || "".equals(config.WRITE_DATASOURCE.trim())) {
				if (USERS == null || USERS.length == 0) {
					USERS = config.WRITE_DB_USER_NAME.split(";");
					PASSWDS = config.WRITE_DB_PASSWORD.split(";");
					URLS = config.WRITE_DB_URL.split(";");
				}
				Class.forName(config.WRITE_DB_DRIVER);
				connection = (Connection) DriverManager.getConnection(URLS[COUNT] + options, USERS[COUNT], PASSWDS[COUNT]);
				if (COUNT == USERS.length - 1) {
					COUNT = 0;
				} else {
					COUNT++;
				}
			} else {
				if (SOURCES == null || SOURCES.length == 0) {
					SOURCES = config.READ_DATASOURCE.split(";");
				}
				DataSource ds = (DataSource) new InitialContext().lookup(SOURCES[COUNT]);
				connection = (Connection) ds.getConnection();
				if (COUNT == SOURCES.length - 1) {
					COUNT = 0;
				} else {
					COUNT++;
				}
			}

			if (!isDbConnected(connection)) {
				connection = createNewWriteConnectionForPool(timeOutType);
			}

			connection = setConnInit(connection);
			return connection;
		} catch (Exception e) {
			LOG.info("Exception");
			ShowLog.err(LOG, e);
		} finally {
			options = "";
			options = null;
		}
		return null;
	}

	public synchronized Connection getReadConnectionFromPool() {
		Connection connection = null;
		boolean timeOutType = false;
		try {
			if (readConnList.size() > 0) {
				while (readConnList.size() > 0) {
					connection = (Connection) readConnList.get(0);
					readConnList.remove(0);
					if (readConnList.size() < readDBMaxConn) {
						timeOutType = true;
					}

					if (!isDbConnected(connection)) {
						if (connection != null) {
							if (!connection.isClosed()) {
								connection.close();
							}
							connection = null;
						}
					} else {
						break;
					}
				}
			}

			if (connection == null) {
				connection = createNewReadConnectionForPool(timeOutType);
			}

			setConnInit(connection);
			return connection;
		} catch (Exception e) {
			LOG.info("Exception");
			ShowLog.err(LOG, e);
			connection = null;
		} finally {
			timeOutType = false;
		}
		return null;
	}

	public synchronized Connection getWriteConnectionFromPool() {
		Connection connection = null;
		boolean timeOutType = false;
		try {
			if (writeConnList.size() > 0) {
				while (writeConnList.size() > 0) {
					connection = (Connection) writeConnList.get(0);
					writeConnList.remove(0);
					if (writeConnList.size() < writeDBMaxConn) {
						timeOutType = true;
					}

					if (!isDbConnected(connection)) {
						if (connection != null) {
							if (!connection.isClosed()) {
								connection.close();
							}
							connection = null;
						}
					} else {
						break;
					}
				}
			}

			if (connection == null) {
				connection = createNewWriteConnectionForPool(timeOutType);
			}

			setConnInit(connection);
			return connection;
		} catch (Exception e) {
			LOG.info("Exception");
			ShowLog.err(LOG, e);
			connection = null;
		} finally {
			timeOutType = false;
		}
		return null;
	}

	/*
	 * return read conn list
	 * 超過最大連線數清除conn，未超過時判斷是否還有連線，有連線直接歸還list，無連線清除conn，小於時須建立新連線
	 */
	public synchronized void returnReadConnectionToPool(Connection connection) {
		try {
			if (connection != null) {
				if (readConnList.size() >= readDBMaxConn) {
					if (!connection.isClosed()) {
						connection.close();
					}
					connection = null;
				} else {
					if (connection.isClosed()) {
						connection = null;
					} else {
						setConnInit(connection);
					}
					readConnList.add(connection);
				}
			} else {
				if (readConnList.size() < readDBMinConn) {
					readConnList.add(0, createNewReadConnectionForPool(true));
				}
			}
		} catch (Exception e) {
			LOG.info("Exception");
			ShowLog.err(LOG, e);
			connection = null;
		} finally {
		}
	}

	/*
	 * return write conn list
	 * 超過最大連線數清除conn，未超過時判斷是否還有連線，有連線直接歸還list，無連線清除conn，小於時須建立新連線
	 */
	public synchronized void returnWriteConnectionToPool(Connection connection) {
		try {
			if (connection != null) {
				if (writeConnList.size() >= writeDBMaxConn) {
					if (!connection.isClosed()) {
						connection.close();
					}
					connection = null;
				} else {
					if (connection.isClosed()) {
						connection = null;
					} else {
						setConnInit(connection);
					}
					writeConnList.add(connection);
				}
			} else {
				if (writeConnList.size() < writeDBMinConn) {
					writeConnList.add(0, createNewWriteConnectionForPool(true));
				}
			}
		} catch (Exception e) {
			LOG.info("Exception");
			ShowLog.err(LOG, e);
			connection = null;
		} finally {
		}
	}

	public boolean isDbConnected(Connection connection) {
		String CHECK_SQL_QUERY = "SELECT 1";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			if (connection == null || connection.isClosed()) {
				return false;
			}
			stmt = connection.createStatement();
			rs = stmt.executeQuery(CHECK_SQL_QUERY);
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			LOG.info("Exception");
			ShowLog.err(LOG, e);
			return false;
		} finally {
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					if (!stmt.isClosed()) {
						stmt.close();
					}
				} catch (SQLException e) {
				}
				stmt = null;
			}
			CHECK_SQL_QUERY = "";
			CHECK_SQL_QUERY = null;
		}
		return false;
	}

	public Connection setConnInit(Connection connection) {
		try {
			if (isDbConnected(connection)) {
				connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				if (!connection.getAutoCommit()) {
					connection.setAutoCommit(true);
				}
			} else {
				connection = null;
			}
		} catch (Exception e) {
			LOG.info("SException");
			ShowLog.err(LOG, e);
		}
		return connection;
	}

	@Override
	public boolean add(Object e) {
		if (readConnList != null && writeConnList != null) {
			if (readConnList.size() < readDBMaxConn || writeConnList.size() < writeDBMaxConn) {
				return super.add(e);
			}
		}
		return false;
	}

	@Override
	public void add(int index, Object element) {
		if (readConnList != null && writeConnList != null) {
			if (readConnList.size() < readDBMaxConn || writeConnList.size() < writeDBMaxConn) {
				super.add(index, element);
			}
		}
	}

	@Override
	public Object remove(int index) {
		if (readConnList.size() < readDBMaxConn || writeConnList.size() < writeDBMaxConn) {
			return super.remove(index);
		}
		return null;
	}
}