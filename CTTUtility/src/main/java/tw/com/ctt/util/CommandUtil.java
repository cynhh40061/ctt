package tw.com.ctt.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandUtil {
	private static final Logger LOG = LogManager.getLogger(CommandUtil.class.getName());
	public static final char UNDERLINE = '_';

	/**
	 * 給定Map物件及要產生的Bean類別名稱 可以取回已經設定完成的物件
	 * 
	 * @param requestMap:已裝好要傳入bean中的值的Map
	 * @param commandClass:bean
	 *            package className (bean.getClass().getName())
	 * @return bean
	 * @throws Exception
	 */
	public static Object getCommand(Map<String, Object> requestMap, String commandClass) throws Exception {
		@SuppressWarnings("rawtypes")
		Class c = Class.forName(commandClass);
		Object o = c.newInstance();
		c = null;
		return updateCommand(requestMap, o);
	}

	/**
	 * 使用reflection自動找出要更新的屬性 找出bean中setter的method自動將map的值傳入bean中
	 * 
	 * @param requestMap:已裝好要傳入bean中的值的Map
	 * @param command:要被傳入值的bean
	 * @return bean
	 * @throws Exception
	 */
	public static Object updateCommand(Map<String, Object> requestMap, Object command) throws Exception {
		Method[] methods = command.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			// 略過private、protected成員
			// 且找出必須是set開頭的方法名稱
			if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
					&& methods[i].getName().startsWith("set")) {
				// 取得不包括set的名稱
				String name = methods[i].getName().substring(3).toLowerCase();
				// 如果setter名稱與鍵值相同
				// 呼叫對應的setter並設定值
				if (requestMap.containsKey(name)) {
					Object param = requestMap.get(name);
					Object[] values = findOutParamValues(param, methods[i]);
					if (values != null && values.length > 0) {
						methods[i].invoke(command, values);
					}
				}
				name = "";
				name = null;
			}
		}
		methods = null;
		return command;
	}

	/**
	 * 轉換為對應型態的值 將取出Map中的Object與bean中要使用的method型態比對，並轉成正確的型態
	 * 
	 * @param param:要傳入value的object
	 * @param method:setter
	 *            method
	 * @return Object[]:轉型過的value
	 */
	private static Object[] findOutParamValues(Object param, Method method) {
		@SuppressWarnings("rawtypes")
		Class[] params = method.getParameterTypes();
		Object[] objs = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			if (params[i] == param.getClass()) {
				objs[i] = param;
			} else if (params[i] == String.class) {
				objs[i] = "" + param.toString();
			} else if (params[i] == Short.TYPE) {
				short number = Short.parseShort("" + param.toString());
				objs[i] = new Short(number);
			} else if (params[i] == Integer.TYPE) {
				int number = Integer.parseInt("" + param.toString());
				objs[i] = new Integer(number);
			} else if (params[i] == Long.TYPE) {
				long number = Long.parseLong("" + param.toString());
				objs[i] = new Long(number);
			} else if (params[i] == Float.TYPE) {
				float number = Float.parseFloat("" + param.toString());
				objs[i] = new Float(number);
			} else if (params[i] == Double.TYPE) {
				double number = Double.parseDouble("" + param.toString());
				objs[i] = new Double(number);
			} else if (params[i] == Boolean.TYPE) {
				boolean bool = Boolean.parseBoolean("" + param.toString());
				objs[i] = new Boolean(bool);
			} else if (params[i] == Byte.TYPE) {
				byte byt = Byte.parseByte("" + param.toString());
				objs[i] = new Byte(byt);
			} else if (params[i] == BigDecimal.class) {
				objs[i] = new BigDecimal("" + param.toString());
			} else if (params[i] == BigInteger.class) {
				objs[i] = new BigInteger("" + param.toString());
			} else if (params[i] == Timestamp.class) {
				objs[i] = Timestamp.valueOf("" + param.toString());
			} else if (params[i] == java.sql.Date.class) {
				objs[i] = new java.sql.Date(Long.parseLong("" + param.toString()));
			} else if (params[i] == java.util.Date.class) {
				// java.util.Date轉java.sql.Date
				// objs[i] = new java.sql.Date(((java.util.Date)
				// objs[i]).getTime());
				objs[i] = new java.util.Date(Long.parseLong("" + param.toString()));
			} else {
				// objs[i] = param;
				objs[i] = null;
			}
		}
		return objs;
	}

	/**
	 * 使用reflection自動將Object的屬性塞入Map
	 * 
	 * @param command:原本已存值的bean
	 * @return map:返回已傳入值的map
	 * @throws Exception
	 */
	public static Map<String, Object> getMap(Object command) throws Exception {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		Method[] methods = command.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			// 略過private、protected成員
			// 且找出必須是get is開頭的方法名稱
			if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
					&& (methods[i].getName().startsWith("get") || methods[i].getName().startsWith("is"))) {
				// 取得不包括get is的名稱
				String name = "";
				if (methods[i].getName().startsWith("is")) {
					name = methods[i].getName().substring(2);
				} else {
					name = methods[i].getName().substring(3);
				}
				name = name.substring(0, 1).toLowerCase() + name.substring(1);
				Object obj = methods[i].invoke(command);
				requestMap.put(name, obj);
			}
		}
		return requestMap;
	}

	/**
	 * 將bean中的getter的名稱取出不含 get,is 轉成小寫，加入list
	 * 
	 * @param command:要解析的bean
	 * @return list:傳入變數名稱清單
	 * @throws Exception
	 */
	public static List<String> getList(Object command) throws Exception {
		List<String> list = new ArrayList<String>();
		Method[] methods = command.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			// 略過private、protected成員
			// 且找出必須是get is開頭的方法名稱
			if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
					&& (methods[i].getName().startsWith("get") || methods[i].getName().startsWith("is"))) {
				// 取得不包括get is的名稱
				String name = "";
				if (methods[i].getName().startsWith("is")) {
					name = methods[i].getName().substring(2).toLowerCase();
				} else {
					name = methods[i].getName().substring(3).toLowerCase();
				}
				list.add(name);
			}
		}
		return list;
	}

	/**
	 * 使用reflection自動將Object的屬性塞入Map(camelToUnderline)
	 * 
	 * @param command:原本已存值的bean
	 * @return map:返回已傳入值的map
	 * @throws Exception
	 */
	public static Map<String, Object> getBeanToMap(Object command) throws Exception {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		Method[] methods = command.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			// 略過private、protected成員
			// 且找出必須是get is開頭的方法名稱
			if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
					&& (methods[i].getName().startsWith("get") || methods[i].getName().startsWith("is"))) {
				// 取得不包括get is的名稱
				String name = "";
				if (methods[i].getName().startsWith("is")) {
					name = camelToUnderline(methods[i].getName().substring(2));
				} else {
					name = camelToUnderline(methods[i].getName().substring(3));
				}
				Object obj = methods[i].invoke(command);
				requestMap.put(name, obj);
			}
		}
		return requestMap;
	}

	/**
	 * 使用reflection自動將(只有符合Map<String,
	 * String>中的name)Object的屬性塞入Map(camelToUnderline)
	 * 
	 * @param command:原本已存值的bean
	 * @param names:bean的key對應到要輸出的key
	 * @return map:返回已傳入值的map
	 * @throws Exception
	 */
	public static Map<String, Object> getBeanToMapByMap(Object command, Map<String, String> names) throws Exception {
		if (names == null || names.size() == 0) {
			return getBeanToMap(command);
		}
		Map<String, Object> requestMap = new HashMap<String, Object>();
		Method[] methods = command.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			// 略過private、protected成員
			// 且找出必須是get is開頭的方法名稱
			if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
					&& (methods[i].getName().startsWith("get") || methods[i].getName().startsWith("is"))) {
				// 取得不包括get is的名稱
				String name = "";
				if (methods[i].getName().startsWith("is")) {
					name = camelToUnderline(methods[i].getName().substring(2));
				} else {
					name = camelToUnderline(methods[i].getName().substring(3));
				}
				if (names.containsKey(name)) {
					Object obj = methods[i].invoke(command);
					requestMap.put(names.get(name), obj);
				}
			}
		}
		return requestMap;
	}

	/**
	 * 比對兩個物件的值，是否完全相符
	 * 
	 * @param obj1
	 *            傳入要比對的物件一
	 * @param obj2
	 *            傳入要比對的物件二
	 * @return true相符 false不相符
	 */
	public static boolean compareBean(Object obj1, Object obj2) {
		Method[] methods1 = obj1.getClass().getDeclaredMethods();
		Method[] methods2 = obj2.getClass().getDeclaredMethods();
		try {
			if (!obj1.getClass().getName().equals(obj2.getClass().getName()) || methods1 == null || methods2 == null
					|| methods1.length != methods2.length || methods1.length == 0 || methods2.length == 0) {
				LOG.debug("Two Object is different!");
				return false;
			}
			for (int i = 0; i < methods1.length; i++) {
				// 略過private、protected成員
				// 且找出必須是get is開頭的方法名稱
				if (!Modifier.isPrivate(methods1[i].getModifiers()) && !Modifier.isProtected(methods1[i].getModifiers())
						&& (methods1[i].getName().startsWith("get") || methods1[i].getName().startsWith("is"))) {
					Object tmpObj1 = methods1[i].invoke(obj1);
					Object tmpObj2 = methods2[i].invoke(obj2);
					if (tmpObj1 != tmpObj2 && (tmpObj1 == null || (tmpObj1 != null && !tmpObj1.equals(tmpObj2)))) {
						tmpObj1 = null;
						tmpObj2 = null;
						return false;
					}
					tmpObj1 = null;
					tmpObj2 = null;
				}
			}
			return true;
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
			methods1 = null;
			methods2 = null;
		}
	}

	/**
	 * 比對兩個Map的值，是否完全相符
	 * 
	 * @param map1
	 *            傳入要比對的Map1
	 * @param map2
	 *            傳入要比對的Map2
	 * @return true相符 false不相符
	 */
	// 每個Entry就是map中一個key及其它對應的value被重新封裝的對象
	public static boolean compareMap(Map<?, ?> map1, Map<?, ?> map2) {
		try {
			if (map1 == null || map2 == null || map1.entrySet().size() != map2.entrySet().size()) {
				LOG.debug("Two Map is different!");
				return false;
			}
			for (Map.Entry<?, ?> entry1 : map1.entrySet()) {
				if (entry1.getValue() != map2.get(entry1.getKey())
						&& (entry1.getValue() == null || (entry1.getValue() != null && !entry1.getValue().equals(map2.get(entry1.getKey()))))) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {

		}
	}

	/**
	 * 比對兩個LIST物件的值，是否完全相符
	 * 
	 * @param list1
	 *            傳入要比對的LIST物件一
	 * @param list2
	 *            傳入要比對的LIST物件二
	 * @return true相符 false不相符
	 */
	public static boolean compareListBean(List<?> list1, List<?> list2) {
		if (list1 == null || list2 == null || list1.size() != list2.size() || list1.size() == 0 || list2.size() == 0
				|| !list1.get(0).getClass().getName().equals(list2.get(0).getClass().getName())) {
			LOG.debug("Two List is different!");
			return false;
		}
		Method[] methods1;
		Method[] methods2;
		try {
			for (int j = 0; j < list1.size(); j++) {
				methods1 = list1.get(j).getClass().getDeclaredMethods();
				methods2 = list2.get(j).getClass().getDeclaredMethods();
				if (methods1.length != methods2.length || methods1.length == 0 || methods2.length == 0) {
					return false;
				}
				for (int i = 0; i < methods1.length; i++) {
					// 略過private、protected成員
					// 且找出必須是get is開頭的方法名稱
					if (!Modifier.isPrivate(methods1[i].getModifiers()) && !Modifier.isProtected(methods1[i].getModifiers())
							&& (methods1[i].getName().startsWith("get") || methods1[i].getName().startsWith("is"))) {
						Object tmpObj1 = methods1[i].invoke(list1.get(j));
						Object tmpObj2 = methods2[i].invoke(list2.get(j));
						if (tmpObj1 != tmpObj2 && (tmpObj1 == null || (tmpObj1 != null && !tmpObj1.equals(tmpObj2)))) {
							tmpObj1 = null;
							tmpObj2 = null;
							return false;
						}
						tmpObj1 = null;
						tmpObj2 = null;
					}
				}
			}
			return true;
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
			methods1 = null;
			methods2 = null;
		}
	}

	/**
	 * 比對兩個List<Map>的值，是否完全相符
	 * 
	 * @param map1
	 *            傳入要比對的Map一
	 * @param map2
	 *            傳入要比對的Map一
	 * @return true相符 false不相符
	 */
	public static boolean compareListMap(List<Map<String, Object>> listmap1, List<Map<String, Object>> listmap2) {
		if (listmap1 == null || listmap2 == null || listmap1.size() != listmap2.size()) {
			LOG.debug("Two List is different!");
			return false;
		}
		try {
			for (int j = 0; j < listmap1.size(); j++) {
				if (listmap1.get(j).entrySet().size() != listmap2.get(j).entrySet().size()) {
					return false;
				}
				for (Map.Entry<?, ?> entry1 : listmap1.get(j).entrySet()) {
					if (entry1.getValue() != listmap1.get(j).get(entry1.getKey()) && (entry1.getValue() == null
							|| (entry1.getValue() != null && !entry1.getValue().equals(listmap1.get(j).get(entry1.getKey()))))) {
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {

		}
	}

	/**
	 * 字串駝峰寫法改底線
	 * 
	 * @param param
	 * @return
	 */
	public static String camelToUnderline(String param) {
		if (param == null || param.isEmpty() || "".equals(param.trim())) {
			return "";
		}
		int len = 0;
		StringBuilder sb = null;
		try {
			len = param.length();
			sb = new StringBuilder(len);
			for (int i = 0; i < len; i++) {
				char c = param.charAt(i);
				if (Character.isUpperCase(c)) {
					sb.append(UNDERLINE);
					sb.append(Character.toLowerCase(c));
				} else {
					sb.append(c);
				}
			}
			if (sb.toString().startsWith("_")) {
				return sb.toString().substring(1);
			} else {
				return sb.toString();
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return null;
		} finally {
			len = 0;
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
	}

	/**
	 * 字串底線改駝峰寫法
	 * 
	 * @param param
	 * @return
	 */
	public static String underlineToCamel(String param) {
		if (param == null || param.isEmpty() || "".equals(param.trim())) {
			return "";
		}
		int len = 0;
		StringBuilder sb = null;
		try {
			len = param.length();
			sb = new StringBuilder(len);
			for (int i = 0; i < len; i++) {
				char c = param.charAt(i);
				if (c == UNDERLINE) {
					if (++i < len) {
						sb.append(Character.toUpperCase(param.charAt(i)));
					}
				} else {
					sb.append(c);
				}
			}
			return sb.toString();
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return null;
		} finally {
			len = 0;
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
	}

	/**
	 * 字串底線改駝峰寫法
	 * 
	 * @param param
	 * @return
	 */
	public static String underlineToCamel2(String param) {
		if (param == null || param.isEmpty() || "".equals(param.trim())) {
			return "";
		}
		StringBuilder sb = null;
		Matcher mc = null;
		int i = 0;
		int position = 0;
		try {
			sb = new StringBuilder(param);
			mc = Pattern.compile("_").matcher(param);
			while (mc.find()) {
				position = mc.end() - (i++);
				// String.valueOf(Character.toUpperCase(sb.charAt(position)));
				sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
				position = 0;
			}
			return sb.toString();
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return null;
		} finally {
			i = 0;
			position = 0;
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			mc = null;
		}
	}

	/**
	 * 將物件轉成String
	 * 
	 * @param obj
	 *            傳入的物件
	 * @return 物件轉成String
	 */
	public static String objectToString(Object obj) {
		if (obj == null) {
			return null;
		}
		Class<?> c;
		String tmpStr = "";
		DateFormat sdf = null;
		boolean toClean = true;
		try {
			c = obj.getClass();
			if (c == null) {
				System.out.println("NULL");
				return null;
			} else if (c == String.class || c.equals(String.class) || obj instanceof String) {
				return "" + obj.toString();
			} else if (c == BigDecimal.class || c.equals(BigDecimal.class) || obj instanceof BigDecimal) {
				return "" + String.valueOf(((BigDecimal) obj));
			} else if (c == BigInteger.class || c.equals(BigInteger.class) || obj instanceof BigInteger) {
				return "" + (BigInteger) obj;
			} else if (obj instanceof Short) {
				return "" + (Short) obj;
			} else if (obj instanceof Integer) {
				return "" + (Integer) obj;
			} else if (obj instanceof Long) {
				return "" + (Long) obj;
			} else if (obj instanceof Float) {
				return "" + String.valueOf(new BigDecimal("" + (Float) obj));
			} else if (obj instanceof Double) {
				return "" + String.valueOf(new BigDecimal("" + (Double) obj));
			} else if (obj instanceof Boolean) {
				return "" + ((Boolean) obj).toString();
			} else if (obj instanceof Byte) {
				return "" + ((Byte) obj).toString();
			} else if (c == Timestamp.class) {
				sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
				tmpStr = sdf.format(obj);
				toClean = false;
				return tmpStr;
			} else if (c == java.sql.Date.class) {
				sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
				tmpStr = sdf.format(obj);
				toClean = false;
				return "" + tmpStr;
			} else if (c == java.util.Date.class) {
				sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
				tmpStr = sdf.format(obj);
				toClean = false;
				return "" + tmpStr;
			} else {
				return "" + obj.toString();
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return null;
		} finally {
			c = null;
			if (toClean) {
				tmpStr = "";
				tmpStr = null;
			}
			sdf = null;
			toClean = false;
		}
	}

	/**
	 * Object slice to a new Object
	 * 
	 * @param obj
	 * @return the same values of new Object
	 */
	public static Object sliceBean(Object obj) {
		Class<?> c = null;
		Object o = null;
		Method[] methods = null;
		if (obj == null) {
			LOG.info("sliceBean(Object obj), the Object is null!");
			return o;
		}
		try {
			c = Class.forName(obj.getClass().getName());
			o = c.newInstance();
			methods = obj.getClass().getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				// 略過private、protected成員
				// 且找出必須是set開頭的方法名稱
				if (!Modifier.isPrivate(methods[i].getModifiers()) && !Modifier.isProtected(methods[i].getModifiers())
						&& methods[i].getName().startsWith("set")) {
					// 取得不包括set的名稱
					String name = methods[i].getName().substring(3).toLowerCase();
					for (Method m : methods) {
						if (("is" + name).equalsIgnoreCase(m.getName()) || ("get" + name).equalsIgnoreCase(m.getName())) {
							Object tmpO = m.invoke(obj);
							if (tmpO != null) {
								methods[i].invoke(o, tmpO);
							}
							tmpO = null;
							break;
						}
						m = null;
					}
				}
			}
			methods = null;
		} catch (ClassNotFoundException e) {
			LOG.info("ClassNotFoundException, " + e.getMessage());
			ShowLog.err(LOG, e);
		} catch (InstantiationException e) {
			LOG.info("InstantiationException, " + e.getMessage());
			ShowLog.err(LOG, e);
		} catch (IllegalAccessException e) {
			LOG.info("IllegalAccessException, " + e.getMessage());
			ShowLog.err(LOG, e);
		} catch (IllegalArgumentException e) {
			LOG.info("IllegalArgumentException, " + e.getMessage());
			ShowLog.err(LOG, e);
		} catch (InvocationTargetException e) {
			LOG.info("InvocationTargetException, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			c = null;
			methods = null;
		}
		return o;
	}

	/**
	 * 比對兩個物件的值，將不相符的資料轉成Map,EX:{balance={0=null, 1=34453434534.55}, accId={0=32423,
	 * 1=0}}
	 * 
	 * @param obj1
	 *            傳入要比對的物件一
	 * @param obj2
	 *            傳入要比對的物件二
	 * @return Map<String, Map<String, String>> map
	 */
	public static Map<String, Map<String, String>> compareBeanDiff(Object obj1, Object obj2) {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Method[] methods1 = obj1.getClass().getDeclaredMethods();
		Method[] methods2 = obj2.getClass().getDeclaredMethods();
		try {
			if (!obj1.getClass().getName().equals(obj2.getClass().getName()) || methods1 == null || methods2 == null
					|| methods1.length != methods2.length || methods1.length == 0 || methods2.length == 0) {
				LOG.debug("Two Object is different!");
				return map;
			} else {
				for (int i = 0; i < methods1.length; i++) {
					// 略過private、protected成員
					// 且找出必須是get is開頭的方法名稱
					if (!Modifier.isPrivate(methods1[i].getModifiers()) && !Modifier.isProtected(methods1[i].getModifiers())
							&& (methods1[i].getName().startsWith("get") || methods1[i].getName().startsWith("is"))) {
						Object tmpObj1 = methods1[i].invoke(obj1);
						Object tmpObj2 = methods2[i].invoke(obj2);
						Map<String, String> tmpMap = null;
						String name = "";
						if (methods1[i].getName().startsWith("is")) {
							name = methods1[i].getName().substring(2);
						} else {
							name = methods1[i].getName().substring(3);
						}
						name = "" + name.substring(0, 1).toLowerCase() + "" + name.substring(1);
						// LOG.info("name:"+name);
						// LOG.info("methods1["+i+"]:"+methods1[i].getReturnType().getName());
						if (tmpObj1 == null && tmpObj2 == null) {
							//
						} else if (tmpObj1 == null) {
							tmpMap = new HashMap<String, String>();
							tmpMap.put("0", null);
							tmpMap.put("1", objectToString(tmpObj2));
							map.put(name, tmpMap);
						} else if (tmpObj2 == null) {
							tmpMap = new HashMap<String, String>();
							tmpMap.put("0", objectToString(tmpObj1));
							tmpMap.put("1", null);
							map.put(name, tmpMap);
						} else {
							if (!objectToString(tmpObj1).equals(objectToString(tmpObj2))) {
								tmpMap = new HashMap<String, String>();
								tmpMap.put("0", objectToString(tmpObj1));
								tmpMap.put("1", objectToString(tmpObj2));
								map.put(name, tmpMap);
							}
						}
						tmpObj1 = null;
						tmpObj2 = null;
						tmpMap = null;
						name = "";
						name = null;
					}
				}
			}
			return map;
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return map;
		} finally {
			methods1 = null;
			methods2 = null;
		}
	}

	/**
	 * From a Map to a new Bean By className
	 * 
	 * @param map
	 * @param className
	 *            => Bean.getClass().getName()
	 * @return new Bean
	 */
	public static Object getBeanByMap(Map<String, Object> map, String className) {
		Class<?> c = null;
		Object o = null;
		Map<String, Object> tmpMap = null;
		try {
			c = Class.forName(className);
			o = c.newInstance();
			tmpMap = getMap(o);
			for (String str : map.keySet()) {
				if (tmpMap.containsKey(str)) {
					o.getClass().getMethod("set" + str.substring(0, 1).toUpperCase() + str.substring(1)).invoke(o, map.get(str));
				}
			}
			return o;
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			o = null;
		} finally {
			c = null;
		}
		return null;
	}

	/**
	 * From a Map to a new Bean
	 * 
	 * @param map
	 * @param obj
	 * @return new Bean
	 */
	public static Object getBeanByMap(Map<String, Object> map, Object obj) {
		return getBeanByMap(map, obj.getClass().getName());
	}

	/**
	 * if type obj1,obj2 == String and obj1.value = obj2.value then return true,
	 * else return false
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equalsString(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1 instanceof String && obj2 instanceof String && ((String) obj1).equals((String) obj2)) {
			return true;
		}
		return false;
	}

	/**
	 * if type obj1,obj2 == Boolean and obj1.value = obj2.value then return true,
	 * else return false
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equalsBoolean(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1 instanceof Boolean && obj2 instanceof Boolean && ((Boolean) obj1).equals((Boolean) obj2)) {
			return true;
		}
		return false;
	}

	/**
	 * if type obj1,obj2 == Integer and obj1.value = obj2.value then return true,
	 * else return false
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equalsInt(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1 instanceof Integer && obj2 instanceof Integer && ((Integer) obj1).equals((Integer) obj2)) {
			return true;
		}
		return false;
	}

	/**
	 * if type obj1,obj2 == Float and obj1.value = obj2.value then return true, else
	 * return false
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equalsFloat(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1 instanceof Float && obj2 instanceof Float && ((Float) obj1).equals((Float) obj2)) {
			return true;
		}
		return false;
	}

	/**
	 * if type obj1,obj2 == Double and obj1.value = obj2.value then return true,
	 * else return false
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equalsDouble(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1 instanceof Double && obj2 instanceof Double && ((Double) obj1).equals((Double) obj2)) {
			return true;
		}
		return false;
	}

	/**
	 * if type obj1,obj2 == BigDecimal and obj1.value = obj2.value then return true,
	 * else return false
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equalsBigDecimal(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1 instanceof BigDecimal && obj2 instanceof BigDecimal) {
			if (((BigDecimal) obj1).compareTo((BigDecimal) obj2) == 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * obj1,obj2 transTo String and obj1.value = obj2.value then return true, else
	 * return false
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean compareString(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1 instanceof String && obj2 instanceof String) {
			if (((String) obj1).equals((String) obj2)) {
				return true;
			} else {
				return false;
			}
		}
		String t1 = null;
		String t2 = null;
		try {
			if (obj1 instanceof String) {
				t1 = (String) obj1;
			} else {
				t1 = obj1.toString();
			}
			if (obj2 instanceof String) {
				t2 = (String) obj2;
			} else {
				t2 = obj2.toString();
			}
			if (t1.equals(t2)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

		} finally {
			t1 = "";
			t1 = null;
			t2 = "";
			t2 = null;
		}
		return false;
	}

	/**
	 * obj1,obj2 transTo Boolean and obj1.value = obj2.value then return true, else
	 * return false
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean compareBoolean(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1 instanceof Boolean && obj2 instanceof Boolean) {
			if (((Boolean) obj1).equals((Boolean) obj2)) {
				return true;
			} else {
				return false;
			}
		}
		Boolean t1 = null;
		Boolean t2 = null;
		try {
			if (obj1 instanceof Boolean) {
				t1 = (Boolean) obj1;
			} else {
				if ("0".equals(obj1.toString().trim()) || "false".equalsIgnoreCase(obj1.toString().trim())) {
					t1 = false;
				} else if ("1".equals(obj1.toString().trim()) || "true".equalsIgnoreCase(obj1.toString().trim())) {
					t1 = true;
				} else {
					return false;
				}
			}
			if (obj2 instanceof Boolean) {
				t2 = (Boolean) obj2;
			} else {
				if ("0".equals(obj2.toString().trim()) || "false".equalsIgnoreCase(obj2.toString().trim())) {
					t2 = false;
				} else if ("1".equals(obj2.toString().trim()) || "true".equalsIgnoreCase(obj2.toString().trim())) {
					t2 = true;
				} else {
					return false;
				}
			}
			if ((t1).equals(t2)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

		} finally {
			t1 = false;
			t2 = false;
			t1 = null;
			t2 = null;
		}
		return false;
	}

	/**
	 * obj1,obj2 transTo Integer and obj1.value = obj2.value then return true, else
	 * return false
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean compareInt(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1 instanceof Integer && obj2 instanceof Integer) {
			if (((Integer) obj1).equals((Integer) obj2)) {
				return true;
			} else {
				return false;
			}
		}
		Integer t1 = null;
		Integer t2 = null;
		try {
			if (obj1 instanceof Integer) {
				t1 = (Integer) obj1;
			} else {
				t1 = Integer.parseInt(obj1.toString());
			}
			if (obj2 instanceof Integer) {
				t2 = (Integer) obj2;
			} else {
				t2 = Integer.parseInt(obj2.toString());
			}
			if ((t1).equals(t2)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

		} finally {
			t1 = 0;
			t2 = 0;
			t1 = null;
			t2 = null;
		}
		return false;
	}

	/**
	 * obj1,obj2 transTo Float and obj1.value = obj2.value then return true, else
	 * return false
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean compareFloat(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1 instanceof Float && obj2 instanceof Float) {
			if (((Float) obj1).equals((Float) obj2)) {
				return true;
			} else {
				return false;
			}
		}
		Float t1 = null;
		Float t2 = null;
		try {
			if (obj1 instanceof Float) {
				t1 = (Float) obj1;
			} else {
				t1 = Float.parseFloat(obj1.toString());
			}
			if (obj2 instanceof Float) {
				t2 = (Float) obj2;
			} else {
				t2 = Float.parseFloat(obj2.toString());
			}
			if ((t1).equals(t2)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

		} finally {
			t1 = 0F;
			t2 = 0F;
			t1 = null;
			t2 = null;
		}
		return false;
	}

	/**
	 * obj1,obj2 transTo Double and obj1.value = obj2.value then return true, else
	 * return false
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean compareDouble(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1 instanceof Double && obj2 instanceof Double) {
			if (((Double) obj1).equals((Double) obj2)) {
				return true;
			} else {
				return false;
			}
		}
		Double t1 = null;
		Double t2 = null;
		try {
			if (obj1 instanceof Double) {
				t1 = (Double) obj1;
			} else {
				t1 = Double.parseDouble(obj1.toString());
			}
			if (obj2 instanceof Double) {
				t2 = (Double) obj2;
			} else {
				t2 = Double.parseDouble(obj2.toString());
			}
			if ((t1).equals(t2)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

		} finally {
			t1 = 0D;
			t2 = 0D;
			t1 = null;
			t2 = null;
		}
		return false;
	}

	/**
	 * obj1,obj2 transTo BigDecimal and obj1.value = obj2.value then return true,
	 * else return false
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean compareBigDecimal(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		if (obj1 instanceof BigDecimal && obj2 instanceof BigDecimal) {
			if (((BigDecimal) obj1).compareTo((BigDecimal) obj2) == 0) {
				return true;
			} else {
				return false;
			}
		}
		BigDecimal b1 = null;
		BigDecimal b2 = null;
		try {
			if (obj1 instanceof BigDecimal) {
				b1 = (BigDecimal) obj1;
			} else {
				b1 = new BigDecimal(obj1.toString());
			}
			if (obj2 instanceof BigDecimal) {
				b2 = (BigDecimal) obj2;
			} else {
				b2 = new BigDecimal(obj2.toString());
			}
			if (b1.compareTo(b2) == 0) {
				return true;
			}
		} catch (Exception e) {

		} finally {
			b1 = BigDecimal.ZERO;
			b1 = null;
			b2 = BigDecimal.ZERO;
			b2 = null;
		}
		return false;
	}

}