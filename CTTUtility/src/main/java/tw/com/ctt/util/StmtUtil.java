package tw.com.ctt.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StmtUtil {

	private static final Logger LOG = LogManager.getLogger(StmtUtil.class.getName());

	final static int batchSize = 500;

	/**
	 * execute prepare query SQL, return list<bean> (use className to create new
	 * bean)
	 * 
	 * @param conn
	 *            => connection or null
	 * @param sql
	 *            => SQL String
	 * @param objs
	 *            => prepare values or null
	 * @return list => List<Map<String, Object>> mapping select column values to
	 *         map, then add to list
	 */
	public static List<Map<String, Object>> queryToMap(Connection conn, String sql, List<Object> objs) throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			LOG.debug("\nQuery SQL1:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (sql == null || sql.isEmpty() || sql.trim().equals("")) {
				LOG.error("SQL is null");
				return null;
			}
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getReadConnection();
			}
			ps = conn.prepareStatement(sql);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			rs = ps.executeQuery();
			ps.clearParameters();
			List<Object> columnNames = new ArrayList<Object>();
			List<String> columnTypes = new ArrayList<String>();
			if (rs != null) {
				ResultSetMetaData columns = rs.getMetaData();
				for (int i = 1; i <= columns.getColumnCount(); i++) {
					columnNames.add(columns.getColumnLabel(i));
					String s = columns.getColumnTypeName(i);
					columnTypes.add(s);
				}
				Map<String, Object> map = null;
				while (rs.next()) {
					map = new ConcurrentHashMap<String, Object>();
					for (int i = 0; i < columnNames.size(); i++) {
						if (rs.getString((String) columnNames.get(i)) == null) {
							// map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), null);
						} else if (columnTypes.get(i).toLowerCase().indexOf("char") != -1) {
							map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), rs.getString((String) columnNames.get(i)));
						} else if (columnTypes.get(i).toLowerCase().indexOf("smallint") != -1) {
							map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), rs.getShort((String) columnNames.get(i)));
						} else if (columnTypes.get(i).toLowerCase().indexOf("bigint") != -1) {
							map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), rs.getLong((String) columnNames.get(i)));
						} else if (columnTypes.get(i).toLowerCase().indexOf("tinyint") != -1) {
							map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), rs.getInt((String) columnNames.get(i)));
						} else if (columnTypes.get(i).toLowerCase().indexOf("int") != -1) {
							map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), rs.getInt((String) columnNames.get(i)));
						} else if (columnTypes.get(i).toLowerCase().indexOf("float") != -1) {
							map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), rs.getFloat((String) columnNames.get(i)));
						} else if (columnTypes.get(i).toLowerCase().indexOf("double") != -1) {
							map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), rs.getDouble((String) columnNames.get(i)));
						} else if (columnTypes.get(i).toLowerCase().indexOf("boolean") != -1
								|| columnTypes.get(i).toLowerCase().indexOf("bit") != -1) {
							map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), rs.getBoolean((String) columnNames.get(i)));
						} else if (columnTypes.get(i).toLowerCase().indexOf("decimal") != -1) {
							map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), rs.getBigDecimal((String) columnNames.get(i)));
						} else if (columnTypes.get(i).toLowerCase().indexOf("timestamp") != -1
								|| columnTypes.get(i).toLowerCase().indexOf("datetime") != -1) {
							map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), rs.getTimestamp((String) columnNames.get(i)));
						} else if (columnTypes.get(i).toLowerCase().indexOf("date") != -1) {
							map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), rs.getDate((String) columnNames.get(i)));
						} else {
							map.put(CommandUtil.underlineToCamel((String) columnNames.get(i)), rs.getString((String) columnNames.get(i)));
						}
					}
					list.add(map);
				}
			}
			return list;
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
			if (toClose) {
				DataSource.returnReadConnection(conn);
			}
		}
	}

	/**
	 * execute prepare query SQL, return list<bean> (use className to create new
	 * bean)
	 * 
	 * @param conn
	 *            => connection or null
	 * @param sql
	 *            => SQL String
	 * @param objs
	 *            => prepare values or null
	 * @param className
	 *            => bean package className (bean.getClass().getName())
	 * @return List bean
	 */
	public static List<Object> queryToBean(Connection conn, String sql, List<Object> objs, String className) throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Object> list = new ArrayList<Object>();
		Class<?> c = null;
		Object o = null;
		Field[] flds = null;
		Method[] methods = null;
		Map<String, String> columnNames = null;
		ResultSetMetaData columns = null;
		try {
			c = Class.forName(className);
			o = c.newInstance();
			flds = o.getClass().getDeclaredFields();
			methods = o.getClass().getDeclaredMethods();
			LOG.debug("\nQuery SQL2:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (sql == null || sql.isEmpty() || sql.trim().equals("")) {
				LOG.error("SQL is null");
				return null;
			}
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getReadConnection();
			}
			ps = conn.prepareStatement(sql);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			rs = ps.executeQuery();
			ps.clearParameters();
			columnNames = new ConcurrentHashMap<String, String>();
			columns = rs.getMetaData();
			int j = 0;
			while (j < columns.getColumnCount()) {
				j++;
				columns.getColumnType(j);
				// java bean, pojo is not startwith 'is' on boolean type,but db
				if (columns.getColumnTypeName(j).toLowerCase().indexOf("boolean") != -1
						|| columns.getColumnTypeName(j).toLowerCase().indexOf("bit") != -1) {
					if (columns.getColumnLabel(j).toLowerCase().startsWith("is")) {
						columnNames.put(columns.getColumnLabel(j).replaceAll("_", "").substring(2).toLowerCase(), columns.getColumnLabel(j));
					} else {
						columnNames.put(columns.getColumnLabel(j).replaceAll("_", "").toLowerCase(), columns.getColumnLabel(j));
					}
				} else {
					columnNames.put(columns.getColumnLabel(j).replaceAll("_", "").toLowerCase(), columns.getColumnLabel(j));
				}
			}
			j = 0;
			while (rs.next()) {
				o = c.newInstance();
				if (flds.length > 0) {
					for (Field fld : flds) {
						if (columnNames.keySet().contains(fld.getName().toLowerCase())) {
							if (fld.getType() == String.class) {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o, rs.getString(columnNames.get(fld.getName().toLowerCase())).trim());
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							} else if (fld.getType() == Short.TYPE) {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									short number = rs.getShort(columnNames.get(fld.getName().toLowerCase()));
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o, number);
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							} else if (fld.getType() == Integer.TYPE) {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									int number = rs.getInt(columnNames.get(fld.getName().toLowerCase()));
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o, number);
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							} else if (fld.getType() == Long.TYPE) {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									long number = rs.getLong(columnNames.get(fld.getName().toLowerCase()));
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o, number);
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							} else if (fld.getType() == Float.TYPE) {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									float number = rs.getFloat(columnNames.get(fld.getName().toLowerCase()));
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o, number);
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							} else if (fld.getType() == Double.TYPE) {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									double number = rs.getDouble(columnNames.get(fld.getName().toLowerCase()));
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o, number);
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							} else if (fld.getType() == Boolean.TYPE) {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									boolean bool = rs.getBoolean(columnNames.get(fld.getName().toLowerCase()));
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o, bool);
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							} else if (fld.getType() == BigDecimal.class) {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o, rs.getBigDecimal(columnNames.get(fld.getName().toLowerCase())));
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							} else if (fld.getType() == Byte.TYPE) {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									byte byt = rs.getByte(columnNames.get(fld.getName().toLowerCase()));
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o, byt);
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							} else if (fld.getType() == Timestamp.class) {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o, rs.getTimestamp(columnNames.get(fld.getName().toLowerCase())));
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							} else if (fld.getType() == java.sql.Date.class) {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o, rs.getDate(columnNames.get(fld.getName().toLowerCase())));
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							} else if (fld.getType() == java.util.Date.class) {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o,
														new java.util.Date(rs.getDate(columnNames.get(fld.getName().toLowerCase())).getTime()
																+ rs.getTime(columnNames.get(fld.getName().toLowerCase())).getTime()));
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							} else {
								if (rs.getString(columnNames.get(fld.getName().toLowerCase())) != null) {
									for (int i = 0; i < methods.length; i++) {
										if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
												&& (methods[i].getName().equalsIgnoreCase("set" + CommandUtil.underlineToCamel(fld.getName())))) {
											try {
												methods[i].invoke(o, rs.getString(columnNames.get(fld.getName().toLowerCase())).trim());
											} catch (IllegalArgumentException e) {
												LOG.info("IllegalArgumentException:" + e.getMessage());
												ShowLog.err(LOG, e);
											} catch (InvocationTargetException e) {
												LOG.info("InvocationTargetException:" + e.getMessage());
												ShowLog.err(LOG, e);
											}
										}
									}
								}
							}
						}
					}
				}
				list.add(o);
			}
		} catch (SQLException e) {
			LOG.info("SQLException:" + e.getMessage());
			ShowLog.err(LOG, e);
			throw e;
		} catch (ClassNotFoundException e) {
			LOG.info("ClassNotFoundException:" + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} catch (InstantiationException e) {
			LOG.info("InstantiationException:" + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} catch (IllegalAccessException e) {
			LOG.info("IllegalAccessException:" + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
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
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					rs = null;
				}
			}
			c = null;
			o = null;
			flds = null;
			methods = null;
			if (columnNames != null) {
				columnNames.clear();
				columnNames = null;
			}
			columns = null;
			if (toClose) {
				DataSource.returnReadConnection(conn);
			}
		}
		return list;
	}

	/**
	 * execute prepare query SQL, return list<bean> (use new bean)
	 * 
	 * @param conn
	 *            => connection or null
	 * @param sql
	 *            => SQL String
	 * @param objs
	 *            => prepare values or null
	 * @param className
	 *            => bean (new bean())
	 * @return List bean
	 */
	public static List<Object> queryToBean(Connection conn, String sql, List<Object> objs, Object bean) throws SQLException {
		if (bean != null) {
			return queryToBean(conn, sql, objs, bean.getClass().getName());
		} else {
			LOG.info("Object bean is null");
			return null;
		}
	}

	/**
	 * insert/update/delete SQL, get update rows by conn, sql, params
	 * 
	 * @param conn
	 *            => connection or null
	 * @param sql
	 *            => SQL String
	 * @param objs
	 *            => prepare values or null
	 * @return int => update rows
	 */
	public static int update(Connection conn, String sql, List<Object> objs) throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		PreparedStatement ps = null;
		int cou = 0;
		try {
			LOG.debug("\nUpdate SQL1:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (sql == null || sql.isEmpty() || sql.trim().equals("")) {
				LOG.error("SQL is null");
				return 0;
			}
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getWriteConnection();
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			cou = ps.executeUpdate();
			ps.clearParameters();
			conn.commit();
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.info("SQLException:" + e1.getMessage());
				LOG.error("\nERROR SQL rollback:" + (cou + 1));
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					if (!conn.getAutoCommit()) {
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			if (toClose) {
				DataSource.returnWriteConnection(conn);
			}
		}
		return cou;
	}

	/**
	 * update SQL, get update rows by conn, bean
	 * 
	 * @param conn
	 *            => connection or null
	 * @param table
	 *            => to update table name
	 * @param bean
	 *            => has data bean
	 * @param pkey
	 *            => table primary key name
	 * @param val
	 *            => the value of primary key
	 * @return int => update rows
	 */
	public static int updateByBean(Connection conn, String table, Object bean, String pkey, Object val) throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		PreparedStatement ps = null;
		int cou = 0;
		String sql = "UPDATE " + table + " SET ";
		String tmp = "";
		List<Object> objs = new ArrayList<Object>();
		Map<String, Object> requestMap = new ConcurrentHashMap<String, Object>();
		try {
			requestMap = CommandUtil.getBeanToMap(bean);
			for (String key : requestMap.keySet()) {
				if (!key.equalsIgnoreCase(pkey) && requestMap.get(key) != null) {
					tmp += "," + key + "=?";
					objs.add(requestMap.get(key));
				}
			}
			if ("".equals(tmp)) {
				LOG.error("No values");
				return 0;
			} else {
				sql += tmp.substring(1);
				sql += " WHERE " + pkey + "=(?)";
				objs.add(val);
			}
			LOG.debug("\nUpdate SQL2:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getWriteConnection();
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			cou = ps.executeUpdate();
			ps.clearParameters();
			conn.commit();
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.info("SQLException:" + e1.getMessage());
				LOG.error("\nERROR SQL rollback:" + (cou + 1));
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					if (!conn.getAutoCommit()) {
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			sql = "";
			sql = null;
			tmp = "";
			tmp = null;
			if (objs != null) {
				objs.clear();
				objs = null;
			}
			if (requestMap != null) {
				requestMap.clear();
				requestMap = null;
			}
			if (toClose) {
				DataSource.returnWriteConnection(conn);
			}
		}
		return cou;
	}

	/**
	 * update SQL, get update rows by conn, map
	 * 
	 * @param conn
	 *            => connection or null
	 * @param table
	 *            => to update table name
	 * @param map
	 *            => key,value for data; ConcurrentHashMap<String, Object>()
	 * @param pkey
	 *            => table primary key name
	 * @param val
	 *            => the value of primary key
	 * @return int => update rows
	 */
	public static int updateByMap(Connection conn, String table, Map<String, Object> map, String pkey) throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		PreparedStatement ps = null;
		int cou = 0;
		String sql = "UPDATE " + table + " SET ";
		String tmp = "";
		List<Object> objs = new ArrayList<Object>();
		try {
			Object Pvalue = null;
			for (String key : map.keySet()) {
				if (!key.equalsIgnoreCase(pkey) && map.get(key) != null) {
					tmp += "," + key + "=?";
					objs.add(map.get(key));
				} else if (key.equalsIgnoreCase(pkey)) {
					Pvalue = map.get(key);
				}
			}
			if ("".equals(tmp) || Pvalue == null || Pvalue.toString().isEmpty() || "".equals(Pvalue.toString().trim())) {
				LOG.error("No values");
				return 0;
			} else {
				sql += tmp.substring(1);
				sql += " WHERE " + pkey + "=(?)";
				objs.add(Pvalue);
			}
			LOG.debug("\nUpdate SQL3:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getWriteConnection();
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			cou = ps.executeUpdate();
			ps.clearParameters();
			conn.commit();
			Pvalue = null;
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.info("SQLException:" + e1.getMessage());
				LOG.error("\nERROR SQL rollback:" + (cou + 1));
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					if (!conn.getAutoCommit()) {
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			sql = "";
			sql = null;
			tmp = "";
			tmp = null;
			if (objs != null) {
				objs.clear();
				objs = null;
			}
			if (toClose) {
				DataSource.returnWriteConnection(conn);
			}
		}
		return cou;
	}

	/**
	 * Batch sql insert/update/delete, get update rows by conn, sql, objs
	 * 
	 * @param conn
	 *            => connection or null
	 * @param sql
	 *            => SQL String
	 * @param objs
	 *            => List(List(Object)) prepare values of each row
	 * @return return int[] => rows[1,1,1,...], 0:no update, >0:update rows,
	 *         -2:PreparedStatement.SUCCESS_NO_INFO :: no return rows,
	 *         -3:PreparedStatement.EXECUTE_FAILED
	 */
	public static int[] updateBatch(Connection conn, String sql, List<List<Object>> objs) throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		PreparedStatement ps = null;
		int cou = 0;
		StringBuilder sb = new StringBuilder();
		int[] rows = new int[0];
		try {
			LOG.debug("\nUpdate Batch SQL1:" + sql);
			if (sql == null || sql.isEmpty() || sql.trim().equals("")) {
				LOG.error("SQL is null");
				return null;
			}
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getWriteConnection();
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			sb.append("\nparam:");
			ps = conn.prepareStatement(sql);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) != null && !objs.get(i).isEmpty() && objs.get(i).size() > 0) {
						sb.append("\n" + (i + 1) + ":");
						for (int j = 0; j < objs.get(i).size(); j++) {
							if (objs.get(i).get(j) != null) {
								sb.append("" + objs.get(i).get(j).toString() + "|#|");
							} else {
								sb.append("null|#|");
							}
							if (objs.get(i).get(j) == null) {
								ps.setString(i + 1, null);
							} else if (objs.get(i).get(j).getClass() == String.class) {
								ps.setString(j + 1, objs.get(i).get(j).toString());
							} else if (objs.get(i).get(j).getClass() == Short.class) {
								ps.setShort(j + 1, (short) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == Integer.class) {
								ps.setInt(j + 1, Integer.parseInt(objs.get(i).get(j).toString()));
							} else if (objs.get(i).get(j).getClass() == Long.class) {
								ps.setLong(j + 1, (Long) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == Float.class) {
								ps.setFloat(j + 1, (Float) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == Double.class) {
								ps.setDouble(j + 1, (Double) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == Boolean.class) {
								ps.setBoolean(j + 1, (Boolean) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == Byte.class) {
								ps.setByte(j + 1, (Byte) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == BigDecimal.class) {
								ps.setBigDecimal(j + 1, (BigDecimal) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == Timestamp.class) {
								ps.setTimestamp(j + 1, (Timestamp) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == java.sql.Date.class) {
								ps.setDate(j + 1, (java.sql.Date) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == java.util.Date.class) {
								ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
							} else {
								ps.setString(j + 1, objs.get(i).get(j).toString());
							}
						}
						ps.addBatch();
					}
					if ((i + 1) % batchSize == 0) {
						int[] tmpA = rows;
						int[] tmpB = ps.executeBatch();
						ps.clearBatch();
						ps.clearParameters();
						rows = new int[tmpA.length + tmpB.length];
						System.arraycopy(tmpA, 0, rows, 0, tmpA.length);
						System.arraycopy(tmpB, 0, rows, tmpA.length, tmpB.length);
						LOG.debug(sb.toString());
						sb.setLength(0);
						sb.append("\n");
						System.gc();
					}
					cou++;
				}
			}
			if (objs != null && !objs.isEmpty() && objs.size() > 0 && objs.size() % batchSize != 0) {
				int[] tmpA = rows;
				int[] tmpB = ps.executeBatch();
				ps.clearBatch();
				ps.clearParameters();
				rows = new int[tmpA.length + tmpB.length];
				System.arraycopy(tmpA, 0, rows, 0, tmpA.length);
				System.arraycopy(tmpB, 0, rows, tmpA.length, tmpB.length);
			}
			sb.append("\n");
			LOG.debug(sb.toString());
			conn.commit();
			return rows;
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			LOG.error("\nERROR SQL ROWS:" + (cou + 1));
			ShowLog.err(LOG, e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.info("SQLException:" + e1.getMessage());
				LOG.error("\nERROR SQL rollback:" + (cou + 1));
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					if (!conn.getAutoCommit()) {
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			sb.setLength(0);
			sb = null;
			if (toClose) {
				DataSource.returnWriteConnection(conn);
			}
			System.gc();
		}
	}

	/**
	 * Batch sql update, get update rows by conn, beans
	 * 
	 * @param conn
	 *            => connection or null
	 * @param table
	 *            => update table name
	 * @param beans
	 *            => update values on list<bean>
	 * @param pkey
	 *            => update table primary key name
	 * @return int => update rows
	 */
	public static int updateBatchByBean(Connection conn, String table, List<Object> beans, String pkey) throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		PreparedStatement ps = null;
		int cou = 0;
		StringBuilder sb = new StringBuilder();
		String sql = "UPDATE " + table + " SET ";
		String tmp = "";
		List<Object> objs = new ArrayList<Object>();
		Map<String, Object> requestMap = new ConcurrentHashMap<String, Object>();
		try {
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getWriteConnection();
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			if (beans != null && !beans.isEmpty() && beans.size() > 0) {
				for (int i = 0; i < beans.size(); i++) {
					if (beans.get(i) != null) {
						try {
							requestMap = CommandUtil.getBeanToMap(beans.get(i));
						} catch (Exception e1) {
							LOG.info("Exception:" + e1.getMessage());
							ShowLog.err(LOG, e1);
						}
						tmp = "";
						sb.setLength(0);
						sb.append("\nparam:");
						objs = new ArrayList<Object>();
						Object Pvalue = null;
						for (String key : requestMap.keySet()) {
							if (!key.equalsIgnoreCase(pkey) && requestMap.get(key) != null) {
								tmp += "," + key + "=?";
								objs.add(requestMap.get(key));
							} else if (key.equalsIgnoreCase(pkey)) {
								Pvalue = requestMap.get(key);
							}
						}
						if ("".equals(tmp)) {
							continue;
						} else {
							tmp = sql + tmp.substring(1) + " WHERE " + pkey + "=(?)";
							objs.add(Pvalue);
							LOG.debug("\nUpdate Batch SQL2:" + tmp);
							ps = conn.prepareStatement(tmp);
							for (int j = 0; j < objs.size(); j++) {
								if (objs.get(j) != null) {
									sb.append((j + 1) + ":" + objs.get(j).toString() + ", ");
								} else {
									sb.append((j + 1) + ":null, ");
								}
								if (objs.get(j) == null) {
									ps.setString(j + 1, null);
								} else if (objs.get(j).getClass() == String.class) {
									ps.setString(j + 1, objs.get(j).toString());
								} else if (objs.get(j).getClass() == Short.class) {
									ps.setShort(j + 1, (short) objs.get(j));
								} else if (objs.get(j).getClass() == Integer.class) {
									ps.setInt(j + 1, Integer.parseInt(objs.get(j).toString()));
								} else if (objs.get(j).getClass() == Long.class) {
									ps.setLong(j + 1, (Long) objs.get(j));
								} else if (objs.get(j).getClass() == Float.class) {
									ps.setFloat(j + 1, (Float) objs.get(j));
								} else if (objs.get(j).getClass() == Double.class) {
									ps.setDouble(j + 1, (Double) objs.get(j));
								} else if (objs.get(j).getClass() == Boolean.class) {
									ps.setBoolean(j + 1, (Boolean) objs.get(j));
								} else if (objs.get(j).getClass() == Byte.class) {
									ps.setByte(j + 1, (Byte) objs.get(j));
								} else if (objs.get(j).getClass() == BigDecimal.class) {
									ps.setBigDecimal(j + 1, (BigDecimal) objs.get(j));
								} else if (objs.get(j).getClass() == Timestamp.class) {
									ps.setTimestamp(j + 1, (Timestamp) objs.get(j));
								} else if (objs.get(j).getClass() == java.sql.Date.class) {
									ps.setDate(j + 1, (java.sql.Date) objs.get(j));
								} else if (objs.get(j).getClass() == java.util.Date.class) {
									ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
								} else {
									ps.setString(j + 1, objs.get(j).toString());
								}
							}
							LOG.debug(sb.toString());
							cou += ps.executeUpdate();
							ps.clearParameters();
							sb.setLength(0);
							tmp = "";
							if (objs != null && !objs.isEmpty() && objs.size() > 0) {
								objs.clear();
							}
						}
						Pvalue = null;
						if (requestMap != null) {
							requestMap.clear();
							requestMap = null;
						}
					}
				}
			}
			conn.commit();
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			LOG.error("\nERROR SQL ROWS:" + (cou + 1));
			ShowLog.err(LOG, e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.info("SQLException:" + e1.getMessage());
				LOG.error("\nERROR SQL rollback:" + (cou + 1));
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					if (!conn.getAutoCommit()) {
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			if (objs != null) {
				objs.clear();
			}
			objs = null;
			sql = "";
			sql = null;
			sb.setLength(0);
			sb = null;
			tmp = "";
			tmp = null;
			if (requestMap != null) {
				requestMap.clear();
			}
			requestMap = null;
			if (toClose) {
				DataSource.returnWriteConnection(conn);
			}
			System.gc();
		}
		return cou;
	}

	/**
	 * Batch sql update, get update rows by conn, map
	 * 
	 * @param conn
	 *            => connection or null
	 * @param table
	 *            => update table name
	 * @param beans
	 *            => update values on list<bean>
	 * @param pkey
	 *            => update table primary key name
	 * @return int => update rows
	 */
	public static int updateBatchByMap(Connection conn, String table, List<Map<String, Object>> list, String pkey) throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		PreparedStatement ps = null;
		int cou = 0;
		StringBuilder sb = new StringBuilder();
		String sql = "UPDATE " + table + " SET ";
		String tmp = "";
		List<Object> objs = new ArrayList<Object>();
		Map<String, Object> requestMap = new ConcurrentHashMap<String, Object>();
		try {
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getWriteConnection();
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			if (list != null && !list.isEmpty() && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i) != null) {
						requestMap = (Map<String, Object>) list.get(i);
						tmp = "";
						sb.setLength(0);
						sb.append("\nparam:");
						objs = new ArrayList<Object>();
						Object Pvalue = null;
						for (String key : requestMap.keySet()) {
							if (!key.equalsIgnoreCase(pkey) && requestMap.get(key) != null) {
								tmp += "," + key + "=?";
								objs.add(requestMap.get(key));
							} else if (key.equalsIgnoreCase(pkey)) {
								Pvalue = requestMap.get(key);
							}
						}
						if ("".equals(tmp)) {
							continue;
						} else {
							tmp = sql + tmp.substring(1) + " WHERE " + pkey + "=(?)";
							objs.add(Pvalue);
							LOG.debug("\nUpdate Batch SQL3:" + tmp);
							ps = conn.prepareStatement(tmp);
							for (int j = 0; j < objs.size(); j++) {
								if (objs.get(j) != null) {
									sb.append((j + 1) + ":" + objs.get(j).toString() + ", ");
								} else {
									sb.append((j + 1) + ":null, ");
								}
								if (objs.get(j) == null) {
									ps.setString(j + 1, null);
								} else if (objs.get(j).getClass() == String.class) {
									ps.setString(j + 1, objs.get(j).toString());
								} else if (objs.get(j).getClass() == Short.class) {
									ps.setShort(j + 1, (short) objs.get(j));
								} else if (objs.get(j).getClass() == Integer.class) {
									ps.setInt(j + 1, Integer.parseInt(objs.get(j).toString()));
								} else if (objs.get(j).getClass() == Long.class) {
									ps.setLong(j + 1, (Long) objs.get(j));
								} else if (objs.get(j).getClass() == Float.class) {
									ps.setFloat(j + 1, (Float) objs.get(j));
								} else if (objs.get(j).getClass() == Double.class) {
									ps.setDouble(j + 1, (Double) objs.get(j));
								} else if (objs.get(j).getClass() == Boolean.class) {
									ps.setBoolean(j + 1, (Boolean) objs.get(j));
								} else if (objs.get(j).getClass() == Byte.class) {
									ps.setByte(j + 1, (Byte) objs.get(j));
								} else if (objs.get(j).getClass() == BigDecimal.class) {
									ps.setBigDecimal(j + 1, (BigDecimal) objs.get(j));
								} else if (objs.get(j).getClass() == Timestamp.class) {
									ps.setTimestamp(j + 1, (Timestamp) objs.get(j));
								} else if (objs.get(j).getClass() == java.sql.Date.class) {
									ps.setDate(j + 1, (java.sql.Date) objs.get(j));
								} else if (objs.get(j).getClass() == java.util.Date.class) {
									ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
								} else {
									ps.setString(j + 1, objs.get(j).toString());
								}
							}
							LOG.debug(sb.toString());
							cou += ps.executeUpdate();
							ps.clearParameters();
							sb.setLength(0);
							tmp = "";
							if (objs != null && !objs.isEmpty() && objs.size() > 0) {
								objs.clear();
							}
						}
						Pvalue = null;
						if (requestMap != null) {
							requestMap.clear();
							requestMap = null;
						}
					}
				}
			}
			conn.commit();
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			LOG.error("\nERROR SQL ROWS:" + (cou + 1));
			ShowLog.err(LOG, e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.info("SQLException:" + e1.getMessage());
				LOG.error("\nERROR SQL rollback:" + (cou + 1));
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					if (!conn.getAutoCommit()) {
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			if (objs != null) {
				objs.clear();
			}
			objs = null;
			sql = "";
			sql = null;
			sb.setLength(0);
			sb = null;
			tmp = "";
			tmp = null;
			if (requestMap != null) {
				requestMap.clear();
			}
			requestMap = null;
			if (toClose) {
				DataSource.returnWriteConnection(conn);
			}
			System.gc();
		}
		return cou;
	}

	/**
	 * insert SQL, Auto Generated Primary Key by conn, sql, objs
	 * 
	 * @param conn
	 *            => connection or null
	 * @param sql
	 *            => SQL String
	 * @param objs
	 *            => prepare values or null
	 * @return long => Primary Key;0=>no insert row,-1=>no primary key
	 */
	public static long insert(Connection conn, String sql, List<Object> objs) throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		long cou = 0;
		try {
			LOG.debug("\nInsert SQL1:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (sql == null || sql.isEmpty() || sql.trim().equals("")) {
				LOG.error("SQL is null");
				return 0;
			}
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getWriteConnection();
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			cou = ps.executeUpdate();
			ps.clearParameters();
			conn.commit();
			LOG.debug("insert row:" + cou);
			if (cou > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					cou = rs.getLong(1);
				} else {
					cou = -1;
				}
			}
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.info("SQLException:" + e1.getMessage());
				LOG.error("\nERROR SQL rollback:" + (cou + 1));
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					if (!conn.getAutoCommit()) {
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					rs = null;
				}
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			if (toClose) {
				DataSource.returnWriteConnection(conn);
			}
		}
		return cou;
	}

	/**
	 * insert SQL, get update rows by conn, bean
	 * 
	 * @param conn
	 *            => connection or null
	 * @param table
	 *            => to insert table name
	 * @param bean
	 *            => has data bean
	 * @return long => Primary Key;0=>no insert row,-1=>no primary key
	 */
	public static long insertByBean(Connection conn, String table, Object bean) throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		long cou = 0;
		String sql = "INSERT INTO " + table + " ";
		String tmp1 = "";
		String tmp2 = "";
		List<Object> objs = new ArrayList<Object>();
		Map<String, Object> requestMap = new ConcurrentHashMap<String, Object>();
		try {
			requestMap = CommandUtil.getBeanToMap(bean);
			for (String key : requestMap.keySet()) {
				tmp1 += "," + key;
				tmp2 += ",?";
				objs.add(requestMap.get(key));
			}
			if ("".equals(tmp1)) {
				LOG.error("No values");
				return 0;
			} else {
				sql += "(" + tmp1.substring(1) + ") VALUES (" + tmp2.substring(1) + ")";
			}
			LOG.debug("\nInsert SQL2:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getWriteConnection();
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			cou = ps.executeUpdate();
			ps.clearParameters();
			conn.commit();
			LOG.debug("insert row:" + cou);
			if (cou > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					cou = rs.getLong(1);
				} else {
					cou = -1;
				}
			}
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.info("SQLException:" + e1.getMessage());
				LOG.error("\nERROR SQL rollback:" + (cou + 1));
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					if (!conn.getAutoCommit()) {
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					rs = null;
				}
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			sql = "";
			sql = null;
			tmp1 = "";
			tmp1 = null;
			tmp2 = "";
			tmp2 = null;
			if (objs != null) {
				objs.clear();
				objs = null;
			}
			if (requestMap != null) {
				requestMap.clear();
				requestMap = null;
			}
			if (toClose) {
				DataSource.returnWriteConnection(conn);
			}
		}
		return cou;
	}

	/**
	 * insert SQL, get insert rows by conn, map
	 * 
	 * @param conn
	 *            => connection or null
	 * @param table
	 *            => to insert table name
	 * @param map
	 *            => key,value for data; ConcurrentHashMap<String, Object>()
	 * @return long => Primary Key;0=>no insert row,-1=>no primary key
	 */
	public static long insertByMap(Connection conn, String table, Map<String, Object> map) throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		long cou = 0;
		String sql = "INSERT INTO " + table + " ";
		String tmp1 = "";
		String tmp2 = "";
		List<Object> objs = new ArrayList<Object>();
		try {
			for (String key : map.keySet()) {
				tmp1 += "," + key;
				tmp2 += ",?";
				objs.add(map.get(key));
			}
			if ("".equals(tmp1)) {
				LOG.error("No values");
				return 0;
			} else {
				sql += "(" + tmp1.substring(1) + ") VALUES (" + tmp2.substring(1) + ")";
			}
			LOG.debug("\nInsert SQL3:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getWriteConnection();
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			cou = ps.executeUpdate();
			ps.clearParameters();
			conn.commit();
			LOG.debug("insert row:" + cou);
			if (cou > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					cou = rs.getLong(1);
				} else {
					cou = -1;
				}
			}
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.info("SQLException:" + e1.getMessage());
				LOG.error("\nERROR SQL rollback:" + (cou + 1));
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					if (!conn.getAutoCommit()) {
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					rs = null;
				}
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			sql = "";
			sql = null;
			tmp1 = "";
			tmp1 = null;
			tmp2 = "";
			tmp2 = null;
			if (objs != null) {
				objs.clear();
				objs = null;
			}
			if (toClose) {
				DataSource.returnWriteConnection(conn);
			}
		}
		return cou;
	}

	/**
	 * call stored procedure
	 * 
	 * @param conn
	 *            => connection or null
	 * @param sql
	 *            => SQL String
	 * @param params
	 *            => prepare values or null. if you want to get return values,
	 *            please use java.sql.Types.*
	 * @return List<Object> => values of params (witch is setting java.sql.Types.*)
	 *         or is Empty
	 */
	public static List<Object> callStored(Connection conn, String sql, List<Object> params) throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		CallableStatement cs = null;
		List<Object> list = new ArrayList<Object>();
		try {
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getWriteConnection();
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			List<Integer> tmpList = new ArrayList<Integer>();
			LOG.debug("\ncall stored procedure SQL:" + sql);
			if (params != null) {
				LOG.debug("\nparams:" + params.toString());
			}
			cs = conn.prepareCall(sql);
			if (params != null && !params.isEmpty() && params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					if (params.get(i).getClass().getName().startsWith("java.sql.Types.")) {
						cs.registerOutParameter(i + 1, (int) params.get(i));
						tmpList.add(i);
					} else {
						if (params.get(i) == null) {
							cs.setString(i + 1, null);
						} else if (params.get(i).getClass() == String.class) {
							cs.setString(i + 1, params.get(i).toString());
						} else if (params.get(i).getClass() == Short.class) {
							cs.setShort(i + 1, (short) params.get(i));
						} else if (params.get(i).getClass() == Integer.class) {
							cs.setInt(i + 1, Integer.parseInt(params.get(i).toString()));
						} else if (params.get(i).getClass() == Long.class) {
							cs.setLong(i + 1, (Long) params.get(i));
						} else if (params.get(i).getClass() == Float.class) {
							cs.setFloat(i + 1, (Float) params.get(i));
						} else if (params.get(i).getClass() == Double.class) {
							cs.setDouble(i + 1, (Double) params.get(i));
						} else if (params.get(i).getClass() == Boolean.class) {
							cs.setBoolean(i + 1, (Boolean) params.get(i));
						} else if (params.get(i).getClass() == Byte.class) {
							cs.setByte(i + 1, (Byte) params.get(i));
						} else if (params.get(i).getClass() == BigDecimal.class) {
							cs.setBigDecimal(i + 1, (BigDecimal) params.get(i));
						} else if (params.get(i).getClass() == Timestamp.class) {
							cs.setTimestamp(i + 1, (Timestamp) params.get(i));
						} else if (params.get(i).getClass() == java.sql.Date.class) {
							cs.setDate(i + 1, (java.sql.Date) params.get(i));
						} else if (params.get(i).getClass() == java.util.Date.class) {
							cs.setTimestamp(i + 1, new Timestamp(((java.util.Date) params.get(i)).getTime()));
						} else {
							cs.setString(i + 1, params.get(i).toString());
						}
					}
				}
			} else {

			}
			cs.executeUpdate();
			if (tmpList != null && !tmpList.isEmpty() && tmpList.size() > 0) {
				for (int i : tmpList) {
					if (params.get(i).getClass().getName().toLowerCase().indexOf(".string") != -1) {
						list.add(cs.getString(i + 1));
					} else if (params.get(i).getClass().getName().toLowerCase().indexOf(".short") != -1) {
						list.add(cs.getShort(i + 1));
					} else if (params.get(i).getClass().getName().toLowerCase().indexOf(".integer") != -1) {
						list.add(cs.getInt(i + 1));
					} else if (params.get(i).getClass().getName().toLowerCase().indexOf(".long") != -1) {
						list.add(cs.getLong(i + 1));
					} else if (params.get(i).getClass().getName().toLowerCase().indexOf(".float") != -1) {
						list.add(cs.getFloat(i + 1));
					} else if (params.get(i).getClass().getName().toLowerCase().indexOf(".double") != -1) {
						list.add(cs.getDouble(i + 1));
					} else if (params.get(i).getClass().getName().toLowerCase().indexOf(".boolean") != -1) {
						list.add(cs.getBoolean(i + 1));
					} else if (params.get(i).getClass().getName().toLowerCase().indexOf(".byte") != -1) {
						list.add(cs.getByte(i + 1));
					} else if (params.get(i).getClass().getName().toLowerCase().indexOf(".bigdecimal") != -1) {
						list.add(cs.getBigDecimal(i + 1));
					} else if (params.get(i).getClass().getName().toLowerCase().indexOf(".timestamp") != -1) {
						list.add(cs.getTimestamp(i + 1));
					} else if (params.get(i).getClass().getName().toLowerCase().indexOf(".sql.date") != -1) {
						list.add(cs.getDate(i + 1));
					} else if (params.get(i).getClass().getName().toLowerCase().indexOf(".util.date") != -1) {
						list.add(new java.util.Date(cs.getDate(i + 1).getTime()));
					} else {
						list.add(cs.getString(i + 1));
					}
				}
			}
			if (tmpList != null) {
				tmpList.clear();
				tmpList = null;
			}
			conn.commit();
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.info("SQLException:" + e1.getMessage());
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					if (!conn.getAutoCommit()) {
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
			if (cs != null) {
				try {
					if (!cs.isClosed()) {
						cs.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					cs = null;
				}
			}
			if (params != null) {
				params.clear();
			}
			params = null;
			sql = "";
			sql = null;
			if (toClose) {
				DataSource.returnWriteConnection(conn);
			}
			System.gc();
		}
		return list;
	}

	/**
	 * not commit! update SQL, get update rows by conn, sql, params
	 * 
	 * @param conn
	 *            => connection not null
	 * @param sql
	 *            => SQL String
	 * @param objs
	 *            => prepare values or null
	 * @return int => update rows
	 */
	public static int updateNoCommit(Connection conn, String sql, List<Object> objs) throws SQLException {
		if (conn == null) {
			LOG.error("conn is null");
			return 0;
		}
		PreparedStatement ps = null;
		int cou = 0;
		try {
			LOG.debug("\nUpdate SQL4:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (sql == null || sql.isEmpty() || sql.trim().equals("")) {
				LOG.error("SQL is null");
				return 0;
			}
			if (conn.isClosed()) {
				LOG.error("conn is closed");
				conn = null;
				return 0;
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			cou = ps.executeUpdate();
			ps.clearParameters();
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
		}
		return cou;
	}

	/**
	 * No commit! update SQL, get update rows by conn, bean
	 * 
	 * @param conn
	 *            => connection not null
	 * @param table
	 *            => to update table name
	 * @param bean
	 *            => has data bean
	 * @param pkey
	 *            => table primary key name
	 * @param val
	 *            => the value of primary key
	 * @return int => update rows
	 */
	public static int updateNoCommitByBean(Connection conn, String table, Object bean, String pkey, Object val) throws SQLException {
		if (conn == null) {
			LOG.error("conn is null");
			return 0;
		}
		PreparedStatement ps = null;
		int cou = 0;
		String sql = "UPDATE " + table + " SET ";
		String tmp = "";
		List<Object> objs = new ArrayList<Object>();
		Map<String, Object> requestMap = new ConcurrentHashMap<String, Object>();
		try {
			if (conn.isClosed()) {
				LOG.error("conn is closed");
				conn = null;
				return 0;
			}
			requestMap = CommandUtil.getBeanToMap(bean);
			for (String key : requestMap.keySet()) {
				if (!key.equalsIgnoreCase(pkey) && requestMap.get(key) != null) {
					tmp += "," + key + "=?";
					objs.add(requestMap.get(key));
				}
			}
			if ("".equals(tmp)) {
				LOG.error("No values");
				return 0;
			} else {
				sql += tmp.substring(1);
				sql += " WHERE " + pkey + "=(?)";
				objs.add(val);
			}
			LOG.debug("\nUpdate SQL5:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			cou = ps.executeUpdate();
			ps.clearParameters();
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			sql = "";
			sql = null;
			tmp = "";
			tmp = null;
			if (objs != null) {
				objs.clear();
				objs = null;
			}
			if (requestMap != null) {
				requestMap.clear();
				requestMap = null;
			}
		}
		return cou;
	}

	/**
	 * No commit! update SQL, get update rows by conn, map
	 * 
	 * @param conn
	 *            => connection not null
	 * @param table
	 *            => to update table name
	 * @param map
	 *            => key,value for data; ConcurrentHashMap<String, Object>()
	 * @param pkey
	 *            => table primary key name
	 * @param val
	 *            => the value of primary key
	 * @return int => update rows
	 */
	public static int updateNoCommitByMap(Connection conn, String table, Map<String, Object> map, String pkey) throws SQLException {
		if (conn == null) {
			LOG.error("conn is null");
			return 0;
		}
		PreparedStatement ps = null;
		int cou = 0;
		String sql = "UPDATE " + table + " SET ";
		String tmp = "";
		List<Object> objs = new ArrayList<Object>();
		try {
			if (conn.isClosed()) {
				LOG.error("conn is closed");
				conn = null;
				return 0;
			}
			Object Pvalue = null;
			for (String key : map.keySet()) {
				if (!key.equalsIgnoreCase(pkey) && map.get(key) != null) {
					tmp += "," + key + "=?";
					objs.add(map.get(key));
				} else if (key.equalsIgnoreCase(pkey)) {
					Pvalue = map.get(key);
				}
			}
			if ("".equals(tmp) || Pvalue == null || Pvalue.toString().isEmpty() || "".equals(Pvalue.toString().trim())) {
				LOG.error("No values");
				return 0;
			} else {
				sql += tmp.substring(1);
				sql += " WHERE " + pkey + "=(?)";
				objs.add(Pvalue);
			}
			LOG.debug("\nUpdate SQL6:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			cou = ps.executeUpdate();
			ps.clearParameters();
			Pvalue = null;
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			sql = "";
			sql = null;
			tmp = "";
			tmp = null;
			if (objs != null) {
				objs.clear();
				objs = null;
			}
		}
		return cou;
	}

	/**
	 * No commit! Batch sql update, get update rows by conn, sql, objs
	 * 
	 * @param conn
	 *            => connection not null
	 * @param sql
	 *            => SQL String
	 * @param objs
	 *            => List(List(Object)) prepare values of each row
	 * @return return int[] => rows[1,1,1,...], 0:no update, >0:update rows,
	 *         -2:PreparedStatement.SUCCESS_NO_INFO :: no return rows,
	 *         -3:PreparedStatement.EXECUTE_FAILED
	 */
	public static int[] updateBatchNoCommit(Connection conn, String sql, List<List<Object>> objs) throws SQLException {
		if (conn == null) {
			LOG.error("conn is null");
			return null;
		}
		PreparedStatement ps = null;
		int cou = 0;
		StringBuilder sb = new StringBuilder();
		int[] rows = new int[0];
		try {
			LOG.debug("\nUpdate Batch SQL4:" + sql);
			if (sql == null || sql.isEmpty() || sql.trim().equals("")) {
				LOG.error("SQL is null");
				return null;
			}
			if (conn.isClosed()) {
				LOG.error("conn is closed");
				conn = null;
				return null;
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			sb.append("\nparam:");
			ps = conn.prepareStatement(sql);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) != null && !objs.get(i).isEmpty() && objs.get(i).size() > 0) {
						sb.append("\n" + (i + 1) + ":");
						for (int j = 0; j < objs.get(i).size(); j++) {
							if (objs.get(i).get(j) != null) {
								sb.append("" + objs.get(i).get(j).toString() + "|#|");
							} else {
								sb.append("null|#|");
							}
							if (objs.get(i).get(j) == null) {
								ps.setString(j + 1, null);
							} else if (objs.get(i).get(j).getClass() == String.class) {
								ps.setString(j + 1, objs.get(i).get(j).toString());
							} else if (objs.get(i).get(j).getClass() == Short.class) {
								ps.setShort(j + 1, (short) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == Integer.class) {
								ps.setInt(j + 1, Integer.parseInt(objs.get(i).get(j).toString()));
							} else if (objs.get(i).get(j).getClass() == Long.class) {
								ps.setLong(j + 1, (Long) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == Float.class) {
								ps.setFloat(j + 1, (Float) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == Double.class) {
								ps.setDouble(j + 1, (Double) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == Boolean.class) {
								ps.setBoolean(j + 1, (Boolean) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == Byte.class) {
								ps.setByte(j + 1, (Byte) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == BigDecimal.class) {
								ps.setBigDecimal(j + 1, (BigDecimal) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == Timestamp.class) {
								ps.setTimestamp(j + 1, (Timestamp) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == java.sql.Date.class) {
								ps.setDate(j + 1, (java.sql.Date) objs.get(i).get(j));
							} else if (objs.get(i).get(j).getClass() == java.util.Date.class) {
								ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
							} else {
								ps.setString(j + 1, objs.get(i).get(j).toString());
							}
						}
						ps.addBatch();
					}
					if ((i + 1) % batchSize == 0) {
						int[] tmpA = rows;
						int[] tmpB = ps.executeBatch();
						ps.clearBatch();
						ps.clearParameters();
						rows = new int[tmpA.length + tmpB.length];
						System.arraycopy(tmpA, 0, rows, 0, tmpA.length);
						System.arraycopy(tmpB, 0, rows, tmpA.length, tmpB.length);
						LOG.debug(sb.toString());
						sb.setLength(0);
						sb.append("\n");
						System.gc();
					}
					cou++;
				}
			}
			if (objs != null && !objs.isEmpty() && objs.size() > 0 && objs.size() % batchSize != 0) {
				int[] tmpA = rows;
				int[] tmpB = ps.executeBatch();
				ps.clearBatch();
				ps.clearParameters();
				rows = new int[tmpA.length + tmpB.length];
				System.arraycopy(tmpA, 0, rows, 0, tmpA.length);
				System.arraycopy(tmpB, 0, rows, tmpA.length, tmpB.length);
			}
			sb.append("\n");
			LOG.debug(sb.toString());
			return rows;
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			LOG.error("\nERROR SQL ROWS:" + (cou + 1));
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			sb.setLength(0);
			sb = null;
			System.gc();
		}
	}

	/**
	 * No commit! Batch sql update, get update rows by conn, beans
	 * 
	 * @param conn
	 *            => connection not null
	 * @param table
	 *            => update table name
	 * @param beans
	 *            => update values on list<bean>
	 * @param pkey
	 *            => update table primary key name
	 * @return int => update rows
	 */
	public static int updateBatchNoCommitByBean(Connection conn, String table, List<Object> beans, String pkey) throws SQLException {
		if (conn == null) {
			LOG.error("conn is null");
			return 0;
		}
		PreparedStatement ps = null;
		int cou = 0;
		StringBuilder sb = new StringBuilder();
		String sql = "UPDATE " + table + " SET ";
		String tmp = "";
		List<Object> objs = new ArrayList<Object>();
		Map<String, Object> requestMap = new ConcurrentHashMap<String, Object>();
		try {
			if (conn.isClosed()) {
				LOG.error("conn is closed");
				conn = null;
				return 0;
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			if (beans != null && !beans.isEmpty() && beans.size() > 0) {
				for (int i = 0; i < beans.size(); i++) {
					if (beans.get(i) != null) {
						try {
							requestMap = CommandUtil.getBeanToMap(beans.get(i));
						} catch (Exception e1) {
							LOG.info("Exception:" + e1.getMessage());
							ShowLog.err(LOG, e1);
						}
						tmp = "";
						sb.setLength(0);
						sb.append("\nparam:");
						objs = new ArrayList<Object>();
						Object Pvalue = null;
						for (String key : requestMap.keySet()) {
							if (!key.equalsIgnoreCase(pkey) && requestMap.get(key) != null) {
								tmp += "," + key + "=?";
								objs.add(requestMap.get(key));
							} else if (key.equalsIgnoreCase(pkey)) {
								Pvalue = requestMap.get(key);
							}
						}
						if ("".equals(tmp)) {
							continue;
						} else {
							tmp = sql + tmp.substring(1) + " WHERE " + pkey + "=(?)";
							objs.add(Pvalue);
							LOG.debug("\nUpdate Batch SQL5:" + tmp);
							ps = conn.prepareStatement(tmp);
							for (int j = 0; j < objs.size(); j++) {
								if (objs.get(j) == null) {
									sb.append((j + 1) + ":" + objs.get(j).toString() + ", ");
								} else {
									sb.append((j + 1) + ":null, ");
								}
								if (objs.get(j) == null) {
									ps.setString(j + 1, null);
								} else if (objs.get(j).getClass() == String.class) {
									ps.setString(j + 1, objs.get(j).toString());
								} else if (objs.get(j).getClass() == Short.class) {
									ps.setShort(j + 1, (short) objs.get(j));
								} else if (objs.get(j).getClass() == Integer.class) {
									ps.setInt(j + 1, Integer.parseInt(objs.get(j).toString()));
								} else if (objs.get(j).getClass() == Long.class) {
									ps.setLong(j + 1, (Long) objs.get(j));
								} else if (objs.get(j).getClass() == Float.class) {
									ps.setFloat(j + 1, (Float) objs.get(j));
								} else if (objs.get(j).getClass() == Double.class) {
									ps.setDouble(j + 1, (Double) objs.get(j));
								} else if (objs.get(j).getClass() == Boolean.class) {
									ps.setBoolean(j + 1, (Boolean) objs.get(j));
								} else if (objs.get(j).getClass() == Byte.class) {
									ps.setByte(j + 1, (Byte) objs.get(j));
								} else if (objs.get(j).getClass() == BigDecimal.class) {
									ps.setBigDecimal(j + 1, (BigDecimal) objs.get(j));
								} else if (objs.get(j).getClass() == Timestamp.class) {
									ps.setTimestamp(j + 1, (Timestamp) objs.get(j));
								} else if (objs.get(j).getClass() == java.sql.Date.class) {
									ps.setDate(j + 1, (java.sql.Date) objs.get(j));
								} else if (objs.get(j).getClass() == java.util.Date.class) {
									ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
								} else {
									ps.setString(j + 1, objs.get(j).toString());
								}
							}
							LOG.debug(sb.toString());
							cou += ps.executeUpdate();
							ps.clearParameters();
							sb.setLength(0);
							tmp = "";
							if (objs != null && !objs.isEmpty() && objs.size() > 0) {
								objs.clear();
							}
						}
						Pvalue = null;
						if (requestMap != null) {
							requestMap.clear();
							requestMap = null;
						}
					}
				}
			}
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			LOG.error("\nERROR SQL ROWS:" + (cou + 1));
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			if (objs != null) {
				objs.clear();
			}
			objs = null;
			sql = "";
			sql = null;
			sb.setLength(0);
			sb = null;
			tmp = "";
			tmp = null;
			if (requestMap != null) {
				requestMap.clear();
			}
			requestMap = null;
			System.gc();
		}
		return cou;
	}

	/**
	 * No commit! Batch sql update, get update rows by conn, map
	 * 
	 * @param conn
	 *            => connection not null
	 * @param table
	 *            => update table name
	 * @param beans
	 *            => update values on list<bean>
	 * @param pkey
	 *            => update table primary key name
	 * @return int => update rows
	 */
	public static int updateBatchNoCommitByMap(Connection conn, String table, List<Map<String, Object>> list, String pkey) throws SQLException {
		if (conn == null) {
			LOG.error("conn is null");
			return 0;
		}
		PreparedStatement ps = null;
		int cou = 0;
		StringBuilder sb = new StringBuilder();
		String sql = "UPDATE " + table + " SET ";
		String tmp = "";
		List<Object> objs = new ArrayList<Object>();
		Map<String, Object> requestMap = new ConcurrentHashMap<String, Object>();
		try {
			if (conn.isClosed()) {
				LOG.error("conn is closed");
				conn = null;
				return 0;
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			if (list != null && !list.isEmpty() && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i) != null) {
						requestMap = (Map<String, Object>) list.get(i);
						tmp = "";
						sb.setLength(0);
						sb.append("\nparam:");
						objs = new ArrayList<Object>();
						Object Pvalue = null;
						for (String key : requestMap.keySet()) {
							if (!key.equalsIgnoreCase(pkey) && requestMap.get(key) != null) {
								tmp += "," + key + "=?";
								objs.add(requestMap.get(key));
							} else if (key.equalsIgnoreCase(pkey)) {
								Pvalue = requestMap.get(key);
							}
						}
						if ("".equals(tmp)) {
							continue;
						} else {
							tmp = sql + tmp.substring(1) + " WHERE " + pkey + "=(?)";
							objs.add(Pvalue);
							LOG.debug("\nUpdate Batch SQL6:" + tmp);
							ps = conn.prepareStatement(tmp);
							for (int j = 0; j < objs.size(); j++) {
								if (objs.get(j) != null) {
									sb.append((j + 1) + ":" + objs.get(j).toString() + ", ");
								} else {
									sb.append((j + 1) + ":null, ");
								}
								if (objs.get(j) == null) {
									ps.setString(j + 1, null);
								} else if (objs.get(j).getClass() == String.class) {
									ps.setString(j + 1, objs.get(j).toString());
								} else if (objs.get(j).getClass() == Short.class) {
									ps.setShort(j + 1, (short) objs.get(j));
								} else if (objs.get(j).getClass() == Integer.class) {
									ps.setInt(j + 1, Integer.parseInt(objs.get(j).toString()));
								} else if (objs.get(j).getClass() == Long.class) {
									ps.setLong(j + 1, (Long) objs.get(j));
								} else if (objs.get(j).getClass() == Float.class) {
									ps.setFloat(j + 1, (Float) objs.get(j));
								} else if (objs.get(j).getClass() == Double.class) {
									ps.setDouble(j + 1, (Double) objs.get(j));
								} else if (objs.get(j).getClass() == Boolean.class) {
									ps.setBoolean(j + 1, (Boolean) objs.get(j));
								} else if (objs.get(j).getClass() == Byte.class) {
									ps.setByte(j + 1, (Byte) objs.get(j));
								} else if (objs.get(j).getClass() == BigDecimal.class) {
									ps.setBigDecimal(j + 1, (BigDecimal) objs.get(j));
								} else if (objs.get(j).getClass() == Timestamp.class) {
									ps.setTimestamp(j + 1, (Timestamp) objs.get(j));
								} else if (objs.get(j).getClass() == java.sql.Date.class) {
									ps.setDate(j + 1, (java.sql.Date) objs.get(j));
								} else if (objs.get(j).getClass() == java.util.Date.class) {
									ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
								} else {
									ps.setString(j + 1, objs.get(j).toString());
								}
							}
							LOG.debug(sb.toString());
							cou += ps.executeUpdate();
							ps.clearParameters();
							sb.setLength(0);
							tmp = "";
							if (objs != null && !objs.isEmpty() && objs.size() > 0) {
								objs.clear();
							}
						}
						Pvalue = null;
						if (requestMap != null) {
							requestMap.clear();
							requestMap = null;
						}
					}
				}
			}
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			LOG.error("\nERROR SQL ROWS:" + (cou + 1));
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			if (objs != null) {
				objs.clear();
			}
			objs = null;
			sql = "";
			sql = null;
			sb.setLength(0);
			sb = null;
			tmp = "";
			tmp = null;
			if (requestMap != null) {
				requestMap.clear();
			}
			requestMap = null;
			list = null;
			System.gc();
		}
		return cou;
	}

	/**
	 * No commit! insert SQL, Auto Generated Primary Key by conn, sql, objs
	 * 
	 * @param conn
	 *            => connection or null
	 * @param sql
	 *            => SQL String
	 * @param objs
	 *            => prepare values or null
	 * @return long => Primary Key;0=>no insert row,-1=>no primary key
	 */
	public static long insertNoCommit(Connection conn, String sql, List<Object> objs) throws SQLException {
		if (conn == null) {
			LOG.error("conn is null");
			return 0;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		long cou = 0;
		try {
			LOG.debug("\nInsert SQL4:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (sql == null || sql.isEmpty() || sql.trim().equals("")) {
				LOG.error("SQL is null");
				return 0;
			}
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getWriteConnection();
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			cou = ps.executeUpdate();
			ps.clearParameters();
			LOG.debug("insert row:" + cou);
			if (cou > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					cou = rs.getLong(1);
				} else {
					cou = -1;
				}
			}
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					rs = null;
				}
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
		}
		return cou;
	}

	/**
	 * No commit! insert SQL, get update rows by conn, bean
	 * 
	 * @param conn
	 *            => connection or null
	 * @param table
	 *            => to insert table name
	 * @param bean
	 *            => has data bean
	 * @return long => Primary Key;0=>no insert row,-1=>no primary key
	 */
	public static long insertNoCommitByBean(Connection conn, String table, Object bean) throws SQLException {
		if (conn == null) {
			LOG.error("conn is null");
			return 0;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		long cou = 0;
		String sql = "INSERT INTO " + table + " ";
		String tmp1 = "";
		String tmp2 = "";
		List<Object> objs = new ArrayList<Object>();
		Map<String, Object> requestMap = new ConcurrentHashMap<String, Object>();
		try {
			if (conn.isClosed()) {
				LOG.error("conn is closed");
				conn = null;
				return 0;
			}
			requestMap = CommandUtil.getBeanToMap(bean);
			for (String key : requestMap.keySet()) {
				tmp1 += "," + key;
				tmp2 += ",?";
				objs.add(requestMap.get(key));
			}
			if ("".equals(tmp1)) {
				LOG.error("No values");
				return 0;
			} else {
				sql += "(" + tmp1.substring(1) + ") VALUES (" + tmp2.substring(1) + ")";
			}
			LOG.debug("\nInsert SQL5:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			cou = ps.executeUpdate();
			ps.clearParameters();
			LOG.debug("insert row:" + cou);
			if (cou > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					cou = rs.getLong(1);
				} else {
					cou = -1;
				}
			}
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					rs = null;
				}
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			sql = "";
			sql = null;
			tmp1 = "";
			tmp1 = null;
			tmp2 = "";
			tmp2 = null;
			if (objs != null) {
				objs.clear();
				objs = null;
			}
			if (requestMap != null) {
				requestMap.clear();
				requestMap = null;
			}
		}
		return cou;
	}

	/**
	 * No commit! insert SQL, get insert rows by conn, map
	 * 
	 * @param conn
	 *            => connection or null
	 * @param table
	 *            => to insert table name
	 * @param map
	 *            => key,value for data; ConcurrentHashMap<String, Object>()
	 * @return long => Primary Key;0=>no insert row,-1=>no primary key
	 */
	public static long insertNoCommitByMap(Connection conn, String table, Map<String, Object> map) throws SQLException {
		if (conn == null) {
			LOG.error("conn is null");
			return 0;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		long cou = 0;
		String sql = "INSERT INTO " + table + " ";
		String tmp1 = "";
		String tmp2 = "";
		List<Object> objs = new ArrayList<Object>();
		try {
			if (conn.isClosed()) {
				LOG.error("conn is closed");
				conn = null;
				return 0;
			}
			for (String key : map.keySet()) {
				tmp1 += "," + key;
				tmp2 += ",?";
				objs.add(map.get(key));
			}
			if ("".equals(tmp1)) {
				LOG.error("No values");
				return 0;
			} else {
				sql += "(" + tmp1.substring(1) + ") VALUES (" + tmp2.substring(1) + ")";
			}
			LOG.debug("\nInsert SQL6:" + sql);
			if (objs != null) {
				LOG.debug("\nparam:" + objs.toString());
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			if (objs != null && !objs.isEmpty() && objs.size() > 0) {
				for (int i = 0; i < objs.size(); i++) {
					if (objs.get(i) == null) {
						ps.setString(i + 1, null);
					} else if (objs.get(i).getClass() == String.class) {
						ps.setString(i + 1, objs.get(i).toString());
					} else if (objs.get(i).getClass() == Short.class) {
						ps.setShort(i + 1, (short) objs.get(i));
					} else if (objs.get(i).getClass() == Integer.class) {
						ps.setInt(i + 1, Integer.parseInt(objs.get(i).toString()));
					} else if (objs.get(i).getClass() == Long.class) {
						ps.setLong(i + 1, (Long) objs.get(i));
					} else if (objs.get(i).getClass() == Float.class) {
						ps.setFloat(i + 1, (Float) objs.get(i));
					} else if (objs.get(i).getClass() == Double.class) {
						ps.setDouble(i + 1, (Double) objs.get(i));
					} else if (objs.get(i).getClass() == Boolean.class) {
						ps.setBoolean(i + 1, (Boolean) objs.get(i));
					} else if (objs.get(i).getClass() == Byte.class) {
						ps.setByte(i + 1, (Byte) objs.get(i));
					} else if (objs.get(i).getClass() == BigDecimal.class) {
						ps.setBigDecimal(i + 1, (BigDecimal) objs.get(i));
					} else if (objs.get(i).getClass() == Timestamp.class) {
						ps.setTimestamp(i + 1, (Timestamp) objs.get(i));
					} else if (objs.get(i).getClass() == java.sql.Date.class) {
						ps.setDate(i + 1, (java.sql.Date) objs.get(i));
					} else if (objs.get(i).getClass() == java.util.Date.class) {
						ps.setTimestamp(i + 1, new Timestamp(((java.util.Date) objs.get(i)).getTime()));
					} else {
						ps.setString(i + 1, objs.get(i).toString());
					}
				}
			}
			cou = ps.executeUpdate();
			ps.clearParameters();
			LOG.debug("insert row:" + cou);
			if (cou > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					cou = rs.getLong(1);
				} else {
					cou = -1;
				}
			}
		} catch (Exception e) {
			cou = 0;
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					rs = null;
				}
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					ps = null;
				}
			}
			sql = "";
			sql = null;
			tmp1 = "";
			tmp1 = null;
			tmp2 = "";
			tmp2 = null;
			if (objs != null) {
				objs.clear();
				objs = null;
			}
		}
		return cou;
	}

	/**
	 * <p>
	 * get union all tables of SQL string ex.StmtUtil.getUnionTable(conn, "mysql",
	 * "user", null); return g_user table sql
	 * 
	 * @param conn
	 *            => connection or null
	 * @param database
	 *            => database name
	 * @param tableName
	 *            => table name
	 * @param format
	 *            => if null , default 'tableName%'
	 * @return union all table sql string, which table name is grp_"tableName"
	 * @throws SQLException
	 */
	public static String getUnionTable(Connection conn, String database, String tableName, String format, String selectColumn) throws SQLException {
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String output = null;
		try {
			if (database == null || database.isEmpty() || "".trim().equals(database)) {
				LOG.error("database is null");
				return null;
			}
			if (tableName == null || tableName.isEmpty() || "".trim().equals(tableName)) {
				LOG.error("tableName is null");
				return null;
			}
			if (format == null || format.isEmpty() || "".trim().equals(format)) {
				format = tableName + "%";
			}
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getReadConnection();
			}
			sql = "SELECT table_name FROM information_schema.TABLES WHERE table_schema='" + database + "' AND table_name LIKE '" + format
					+ "' ORDER BY CAST(SUBSTRING(table_name FROM " + (tableName.length() + 1) + ") AS UNSIGNED) ASC";
			LOG.debug("\ngetUnionTable SQL:" + sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ps.clearParameters();
			if (selectColumn == null || selectColumn.isEmpty() || "".equals(selectColumn.trim())) {
				selectColumn = "*";
			}
			if (rs != null) {
				output = "";
				while (rs.next()) {
					if ("".equals(output)) {
						output = "SELECT " + selectColumn + " FROM " + database + "." + rs.getString("table_name");
					} else {
						output = output + "\nUNION ALL \n" + "SELECT " + selectColumn + " FROM " + database + "." + rs.getString("table_name");
					}
				}
				if ("".equals(output)) {
					LOG.info("getUnionTable: tableName(" + tableName + ") not find!");
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

}
