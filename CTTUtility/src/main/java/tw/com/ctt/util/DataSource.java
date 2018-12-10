package tw.com.ctt.util;

import java.sql.Connection;

public class DataSource {

	static JdbcConnectionPool pool = new JdbcConnectionPool();

	/**
	 * 使用完後一定要call returnReadConnection 把 Connection 還回 pool
	 * 
	 * @return
	 */
	public static Connection getReadConnection() {
		Connection connection = pool.getReadConnectionFromPool();
		return connection;
	}

	public static void returnReadConnection(Connection connection) {
		Connection conn = connection;
		pool.returnReadConnectionToPool(conn);
	}

	/**
	 * 使用完後一定要call returnWriteConnection 把 Connection 還回 pool
	 * 
	 * @return
	 */
	public static Connection getWriteConnection() {
		Connection connection = pool.getWriteConnectionFromPool();
		return connection;
	}

	public static void returnWriteConnection(Connection connection) {
		Connection conn = connection;
		pool.returnWriteConnectionToPool(conn);
	}
	
	public static boolean checkMySQLConnection() {
		if(pool.availableReadConnections.size() == 0 || pool.availableWriteConnections.size() == 0) {
			pool.initializeConnectionPool();
		}
		if(pool.availableReadConnections.size() > 0 && pool.availableWriteConnections.size() > 0) {
			return true;
		}
		return false;
	}
}
