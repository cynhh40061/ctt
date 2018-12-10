package tw.com.ctt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcConnectionPool extends ArrayList<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 524395725199324168L;

	private static final Logger LOG = LogManager.getLogger(JdbcConnectionPool.class.getName());

	private String[] USERS;
	private String[] PASSWDS;
	private String[] URLS;
	private String[] SOURCES;
	private int COUNT = 0;
	private final String UTF8_CHARSET = "?useUnicode=true&characterEncoding=UTF-8&collation=utf8_unicode_ci&useServerPrepStmts=false&useSSL=false&serverTimezone=GMT%2B8";
	private final String TIMEOUT = "&connectTimeout=30000&socketTimeout=600000";
	private final String SHORT_TIMEOUT = "&connectTimeout=15000&socketTimeout=15000";

	List<Connection> availableReadConnections = new ArrayList<Connection>();
	List<Connection> availableWriteConnections = new ArrayList<Connection>();
	private static Configuration config = null;
	boolean keepAlive = true;
	private int noOfInitRead = 1;
	private int noOfInitWrite = 1;
	private int noOfMaxRead = 10;
	private int noOfMaxWrite = 5;

	public JdbcConnectionPool() {
		initializeConnectionPool();
	}

	@SuppressWarnings("static-access")
	public synchronized void initializeConnectionPool() {
		config = Configuration.getInstance();
		noOfInitRead = config.READ_DB_MIN_CONNECTIONS;
		noOfInitWrite = config.WRITE_DB_MIN_CONNECTIONS;
		noOfMaxRead = config.READ_DB_MAX_CONNECTIONS;
		noOfMaxWrite = config.WRITE_DB_MAX_CONNECTIONS;

		while (!checkIfReadConnectionPoolIsFull(noOfInitRead)) {
			availableReadConnections.add(createNewReadConnectionForPool(true));
		}
		while (!checkIfWriteConnectionPoolIsFull(noOfInitWrite)) {
			availableWriteConnections.add(createNewWriteConnectionForPool(true));
		}

		while (!checkIfReadConnectionPoolIsFull()) {
			availableReadConnections.add(null);
		}
		while (!checkIfWriteConnectionPoolIsFull()) {
			availableWriteConnections.add(null);
		}
		keepAlive = false;
		noOfInitRead = 0;
		noOfInitWrite = 0;
	}

	private synchronized boolean checkIfReadConnectionPoolIsFull(int noOfInitRead) {
		if (availableReadConnections.size() < noOfInitRead) {
			return false;
		}
		return true;
	}

	private synchronized boolean checkIfWriteConnectionPoolIsFull(int noOfInitWrite) {
		if (availableWriteConnections.size() < noOfInitWrite) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("static-access")
	private synchronized boolean checkIfReadConnectionPoolIsFull() {
		if (availableReadConnections.size() < noOfMaxRead) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("static-access")
	private synchronized boolean checkIfWriteConnectionPoolIsFull() {
		if (availableWriteConnections.size() < noOfMaxWrite) {
			return false;
		}
		return true;
	}

	@Override
	public boolean add(Object e) {
		if (availableReadConnections.size() < noOfMaxRead || availableWriteConnections.size() < noOfMaxWrite) {
			return super.add(e);
		}
		return false;
	}

	@Override
	public void add(int index, Object element) {
		if (availableReadConnections.size() < noOfMaxRead || availableWriteConnections.size() < noOfMaxWrite) {
			super.add(index, element);
		}
	}

	@Override
	public Object remove(int index) {
		if (availableReadConnections.size() < noOfMaxRead || availableWriteConnections.size() < noOfMaxWrite) {
			return super.remove(index);
		}
		return null;
	}

	// Creating a read connection
	@SuppressWarnings("static-access")
	private Connection createNewReadConnectionForPool(boolean keepAlive) {
		if (config == null) {
			config = Configuration.getInstance();
			noOfInitRead = config.READ_DB_MIN_CONNECTIONS;
			noOfInitWrite = config.WRITE_DB_MIN_CONNECTIONS;
			noOfMaxRead = config.READ_DB_MAX_CONNECTIONS;
			noOfMaxWrite = config.WRITE_DB_MAX_CONNECTIONS;
		}
		Connection connection = null;
		String options = UTF8_CHARSET + TIMEOUT;
		if (!keepAlive) {
			options = UTF8_CHARSET + SHORT_TIMEOUT;
		}
		try {
			if (config.READ_DATASOURCE == null || config.READ_DATASOURCE.isEmpty()
					|| "".equals(config.READ_DATASOURCE.trim())) {
				connection = null;

				if (USERS == null || USERS.length == 0) {
					USERS = config.READ_DB_USER_NAME.split(";");
					PASSWDS = config.READ_DB_PASSWORD.split(";");
					URLS = config.READ_DB_URL.split(";");
				}
				Class.forName(config.READ_DB_DRIVER);
				connection = (Connection) DriverManager.getConnection(URLS[COUNT] + options, USERS[COUNT],
						PASSWDS[COUNT]);
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
				connection = createNewReadConnectionForPool(keepAlive);
			}
			connection = setConnInit(connection);
			return connection;
		} catch (SQLException e) {
			LOG.info("SQLException");
			ShowLog.err(LOG, e);
		} catch (NamingException e) {
			LOG.info("NamingException");
			ShowLog.err(LOG, e);
		} catch (ClassNotFoundException e) {
			LOG.info("ClassNotFoundException");
			ShowLog.err(LOG, e);
		} finally {
			config = null;
			options = "";
			options = null;
		}
		return null;
	}

	// Creating a write connection
	@SuppressWarnings("static-access")
	private Connection createNewWriteConnectionForPool(boolean keepAlive) {
		if (config == null) {
			config = Configuration.getInstance();
			noOfInitRead = config.READ_DB_MIN_CONNECTIONS;
			noOfInitWrite = config.WRITE_DB_MIN_CONNECTIONS;
			noOfMaxRead = config.READ_DB_MAX_CONNECTIONS;
			noOfMaxWrite = config.WRITE_DB_MAX_CONNECTIONS;
		}
		Connection connection = null;
		String options = UTF8_CHARSET + TIMEOUT;
		if (!keepAlive) {
			options = UTF8_CHARSET + SHORT_TIMEOUT;
		}
		try {
			if (config.WRITE_DATASOURCE == null || config.WRITE_DATASOURCE.isEmpty()
					|| "".equals(config.WRITE_DATASOURCE.trim())) {
				connection = null;
				Class.forName(config.WRITE_DB_DRIVER);
				connection = (Connection) DriverManager.getConnection(config.WRITE_DB_URL + options,
						config.WRITE_DB_USER_NAME, config.WRITE_DB_PASSWORD);
			} else {
				DataSource ds = (DataSource) new InitialContext().lookup(config.WRITE_DATASOURCE);
				connection = (Connection) ds.getConnection();
			}
			if (!isDbConnected(connection)) {
				connection = createNewWriteConnectionForPool(keepAlive);
			}
			connection = setConnInit(connection);
			return connection;
		} catch (SQLException e) {
			LOG.info("SQLException");
			ShowLog.err(LOG, e);
		} catch (NamingException e) {
			LOG.info("NamingException");
			ShowLog.err(LOG, e);
		} catch (ClassNotFoundException e) {
			LOG.info("ClassNotFoundException");
			ShowLog.err(LOG, e);
		} finally {
			config = null;
			options = "";
			options = null;
		}
		return null;
	}

	@SuppressWarnings("static-access")
	public synchronized Connection getReadConnectionFromPool() {
		Connection connection = null;
		if (availableReadConnections.size() > 0) {
			while (availableReadConnections.size() > noOfMaxRead ? connection == null : false) {
				connection = (Connection) availableReadConnections.remove(0);
				if (isDbConnected(connection)) {
					connection = setConnInit(connection);
					return connection;
				} else {
					connection = null;
				}
			}
			connection = (Connection) availableReadConnections.remove(0);
			if (!isDbConnected(connection)) {
				connection = null;
				connection = createNewReadConnectionForPool(true);
			}
		} else {
			connection = null;
			connection = createNewReadConnectionForPool(keepAlive);
		}
		connection = setConnInit(connection);
		return connection;
	}

	@SuppressWarnings("static-access")
	public synchronized Connection getWriteConnectionFromPool() {
		Connection connection = null;
		if (availableWriteConnections.size() > 0) {
			while (availableWriteConnections.size() > noOfMaxWrite ? connection == null : false) {
				connection = (Connection) availableWriteConnections.remove(0);
				if (isDbConnected(connection)) {
					connection = setConnInit(connection);
					return connection;
				} else {
					connection = null;
				}
			}
			connection = (Connection) availableWriteConnections.remove(0);
			if (!isDbConnected(connection)) {
				connection = null;
				connection = createNewWriteConnectionForPool(true);
			}
		} else {
			connection = null;
			connection = createNewWriteConnectionForPool(keepAlive);
		}
		connection = setConnInit(connection);
		return connection;
	}

	public synchronized void returnReadConnectionToPool(Connection connection) {
		if (connection == null) {
			if (availableReadConnections.size() < noOfInitRead) {
				availableReadConnections.add(0, createNewReadConnectionForPool(true));
				return;
			}
		}
		try {
			if (connection.isClosed()) {
				connection = null;
				if (availableReadConnections.size() < noOfInitRead) {
					availableReadConnections.add(0, createNewReadConnectionForPool(true));
					return;
				}
			}
		} catch (SQLException e1) {
		}
		if (isDbConnected(connection)) {
			connection = setConnInit(connection);
		}
		if (availableReadConnections.size() >= noOfMaxRead) {
			if (isDbConnected(connection)) {
				try {
					connection.close();
				} catch (SQLException e) {
					LOG.info("SQLException");
					ShowLog.err(LOG, e);
				}
			}
			connection = null;
		} else {
			if (connection != null) {
				connection = setConnInit(connection);
				availableReadConnections.add(0, connection);
			} else {
				availableReadConnections.add(0, createNewReadConnectionForPool(keepAlive));
			}
		}
	}

	public synchronized void returnWriteConnectionToPool(Connection connection) {
		if (connection == null) {
			if (availableWriteConnections.size() < noOfInitWrite) {
				availableWriteConnections.add(0, createNewWriteConnectionForPool(true));
				return;
			}
		}
		try {
			if (connection.isClosed()) {
				connection = null;
				if (availableWriteConnections.size() < noOfInitWrite) {
					availableWriteConnections.add(0, createNewWriteConnectionForPool(true));
					return;
				}
			}
		} catch (SQLException e1) {
		}
		if (isDbConnected(connection)) {
			connection = setConnInit(connection);
		}
		if (availableWriteConnections.size() >= noOfMaxWrite) {
			if (isDbConnected(connection)) {
				try {
					connection.close();
				} catch (SQLException e) {
					LOG.info("SQLException");
					ShowLog.err(LOG, e);
				}
			}
			connection = null;
		} else {
			if (connection != null) {
				connection = setConnInit(connection);
				availableWriteConnections.add(0, connection);
			} else {
				availableWriteConnections.add(0, createNewWriteConnectionForPool(keepAlive));
			}
		}
	}

	public boolean isDbConnected(Connection connection) {
		if (connection == null) {
			return false;
		}
		try {
			if (connection.isClosed()) {
				connection = null;
				return false;
			}
		} catch (SQLException e1) {
			return false;
		}
		boolean isConnected = false;
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
				isConnected = true;
			}
		} catch (SQLException | NullPointerException e) {
			LOG.info("SQLException");
			ShowLog.err(LOG, e);
			isConnected = false;
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
		return isConnected;
	}

	public Connection setConnInit(Connection connection) {
		if (isDbConnected(connection)) {
			try {
				if (!connection.getAutoCommit()) {
					connection.setAutoCommit(true);
				}
				connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				return connection;
			} catch (SQLException e) {
				LOG.info("SQLException:setAutoCommit:setTransactionIsolation:" + e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		connection = null;
		return connection;
	}

	protected void finalize() throws Throwable {
		if (USERS != null ? USERS.length > 0 : false) {
			for (int i = 0; i < USERS.length; i++) {
				USERS[i] = null;
			}
		}
		USERS = null;
		if (PASSWDS != null ? PASSWDS.length > 0 : false) {
			for (int i = 0; i < PASSWDS.length; i++) {
				PASSWDS[i] = null;
			}
		}
		PASSWDS = null;
		if (URLS != null ? URLS.length > 0 : false) {
			for (int i = 0; i < URLS.length; i++) {
				URLS[i] = null;
			}
		}
		URLS = null;
		if (SOURCES != null ? SOURCES.length > 0 : false) {
			for (int i = 0; i < SOURCES.length; i++) {
				SOURCES[i] = null;
			}
		}
		SOURCES = null;
		COUNT = 0;
		if (availableReadConnections != null ? availableReadConnections.size() > 0 : false) {
			while (availableReadConnections.size() > 0) {
				Connection conn = availableReadConnections.remove(0);
				if (isDbConnected(conn)) {
					try {
						conn.close();
					} catch (Exception e) {
					}
				}
				conn = null;
			}
		}
		availableReadConnections = null;
		if (availableWriteConnections != null ? availableWriteConnections.size() > 0 : false) {
			while (availableWriteConnections.size() > 0) {
				Connection conn = availableWriteConnections.remove(0);
				if (isDbConnected(conn)) {
					try {
						conn.close();
					} catch (Exception e) {
					}
				}
				conn = null;
			}
		}
		availableWriteConnections = null;
		config = null;
		keepAlive = false;
		noOfInitRead = 0;
		noOfInitWrite = 0;
		noOfMaxRead = 0;
		noOfMaxWrite = 0;
		System.gc();
		super.finalize();
	}
}