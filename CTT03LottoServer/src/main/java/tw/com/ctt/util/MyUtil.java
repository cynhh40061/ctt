package tw.com.ctt.util;

import static tw.com.ctt.constant.LotteryConstant.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.util.Strings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyUtil {
	
	
	public static int searchStringCount(String allStr , String searchStr) {
		int count = 0;
		String s1 = allStr.toLowerCase();
		String s2  = searchStr.toLowerCase();
		
		String[] allStrArr = s1.split("");
		for(int i = 0 ; i < allStrArr.length ; i++) {
			if(allStrArr[i].equals(s2)) {
				count++;
			}
		}
		return count;
	}
	
	
	
	public boolean setIssueFormat(String str) {
		String resultStr = "";
		try {
			if(str.matches("([Yy]{2,4}[Mm]{0,2}[Dd]{0,2}[-]{0,1}[Xx]{0,4})")) {
				return true;
			}
		}catch(Exception e) {
			System.out.println("issueFormat Exception:"+e.getMessage());
		}
		return false;
	}
	
	public static String dateFormat(Date date , String format) {
		SimpleDateFormat sdf = null;
		String resultStr = "";
		try {
			if(date != null) {
				format = format.trim().replaceAll("Y", "y");
				format = format.trim().replaceAll("D", "d");
				format = format.trim().replaceAll("h", "H");
				format = format.trim().replaceAll("S", "s");
				format = format.trim().replaceAll("yy/mm/d", "yy/MM/d");
				format = format.trim().replaceAll("yy-mm-d", "yy-MM-d");
				format = format.trim().replaceAll("yymmd", "yyMMd");
				format = format.trim().replaceAll("yy-mm", "yy-MM");
				format = format.trim().replaceAll("yy/mm", "yy/MM");
				format = format.trim().replaceAll("yymm", "yyMM");
				format = format.trim().replaceAll("H:MM:s", "H:mm:s");
				if(format.matches("[y]{2,4}[/-]{0,1}[M]{0,2}[/-]{0,1}[d]{0,2}[\\s]{0,1}[H]{0,2}[:]{0,1}[m]{0,2}[:]{0,1}[s]{0,2}")) {
					sdf = new SimpleDateFormat(format);
					resultStr = sdf.format(date);
				}
			}
		}catch(Exception e) {
			System.out.println("addErrorTextData Exception:"+e.getMessage());
		}
		finally{
			if(sdf != null) {
				sdf.clone();
				sdf = null;
			}
		}
		return resultStr;
	}
	
	public static boolean isJSON(String jsonStr) {
		boolean todo = false;
		try {
			new JSONObject(jsonStr);
			todo = true;
		}catch(JSONException e) {
			System.out.println("isJSON JSONException:"+e.getMessage());
			todo = false;
		}
		return todo;
	}
	
	public static  boolean isJSONArray(String jsonStr) {
		boolean todo = false;
		try {
			new JSONArray(jsonStr);
			todo = true;
		}catch(JSONException e) {
			System.out.println("isJSONArray JSONException:"+e.getMessage());
			todo = false;
		}
		return todo;
	}
	
	public static String clearNum(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			str = str.trim();
			String tmpStr = "";
			Pattern pattern = Pattern.compile("^\\d");
			for (int i = 0; i < str.length(); i++) {
				String tmpC = str.substring(i, i + 1);
				if (pattern.matcher(tmpC).find()) {
					tmpStr += tmpC;
				}
				tmpC = null;
				tmpC = "";
			}
			str = tmpStr;
			tmpStr = "";
			tmpStr = null;
			pattern = null;
			return str;
		}
	}
	
	public static long toLong(String str) {
		String tmpStr = "" ;
		if(str != null && str.length() > 0) {
			tmpStr = clearNum(str);
			if(tmpStr.length() > 0 && tmpStr != null && !"".equals(tmpStr)) {
				return Long.parseLong(tmpStr);
			}
		}
		return -1;
	}
	
	public static int toInt(String str) {
		String tmpStr = "" ;
		if(str != null && str.length() > 0) {
			tmpStr = clearNum(str);
			if(tmpStr.length() > 0 && tmpStr != null && !"".equals(tmpStr)) {
				return Integer.parseInt(tmpStr);
			}
		}
		return 0;
	}
	
	public static String checkDateTimeFormat(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			str = str.trim().replaceAll("-", "/");
			Pattern pattern = Pattern.compile(
					"^(?:(?!0000)[0-9]{4}/(?:(?:0[1-9]|1[0-2])/(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])/(?:29|30)|(?:0[13578]|1[02])/31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)/02/29)[ ]([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
			if (pattern.matcher(str).find()) {
				return str;
			} else {
				return "";
			}
		}
	}

	public static String checkDateFormat(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			str = str.trim().replaceAll("-", "/");
			Pattern pattern = Pattern.compile(
					"^(?:(?!0000)[0-9]{4}/(?:(?:0[1-9]|1[0-2])/(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])/(?:29|30)|(?:0[13578]|1[02])/31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)/02/29)$");
			if (pattern.matcher(str).find()) {
				return str;
			} else {
				return "";
			}
		}
	}
	
	
	public static String checkDateTimeFormat2(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			str = str.trim().replaceAll("-", "/");
			Pattern pattern = Pattern.compile(
					"^(?:(?!0000)[0-9]{2}/(?:(?:0[1-9]|1[0-2])/(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])/(?:29|30)|(?:0[13578]|1[02])/31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)/02/29)[ ]([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
			if (pattern.matcher(str).find()) {
				return str;
			} else {
				return "";
			}
		}
	}
	
	public static String checkDateFormat2(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			str = str.trim().replaceAll("-", "/");
			Pattern pattern = Pattern.compile(
					"^(?:(?!0000)[0-9]{2}/(?:(?:0[1-9]|1[0-2])/(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])/(?:29|30)|(?:0[13578]|1[02])/31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)/02/29)$");
			if (pattern.matcher(str).find()) {
				return str;
			} else {
				return "";
			}
		}
	}
	
	public static Date toDate(String str) {
		Date date = null;
		try {
			str = str.trim().replaceAll("-", "/");
			
			String dateTimeStr = checkDateTimeFormat(str);
			String dateStr = checkDateFormat(str);
			String dateTimeStr2 = checkDateTimeFormat2(str);
			String dateStr2 = checkDateFormat2(str);
			if(!"".equals(dateTimeStr) && dateTimeStr.length() > 0) {
				date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(dateTimeStr);
			}
			else if(!"".equals(dateStr) && dateStr.length() > 0){
				date = new SimpleDateFormat("yyyy/MM/dd").parse(dateStr);
			}
			else if(!"".equals(dateTimeStr2) && dateTimeStr2.length() > 0){
				date = new SimpleDateFormat("yy/MM/dd HH:mm:ss").parse(dateTimeStr2);
			}
			else if(!"".equals(dateStr2) && dateStr2.length() > 0){
				date = new SimpleDateFormat("yy/MM/dd").parse(dateStr2);
			}
			
		}catch(Exception e) {
			date = null;
			System.out.println("toDate Exception"+e.getMessage());
		}
		
		return date;
	}
	
	public static boolean toBoolean(String str) {
		if(str.equals("true")) {
			return true;
		}
		else if(str.equals("1")){
			return true;
		}
		return false;
	}
	public static long toIssue(Date date , String issueNumStr , String issueFormat) {
		String issue = "";
		issueFormat = issueFormat.trim().replaceAll("X", "x");
		issueFormat = issueFormat.trim().replaceAll("Y", "y");
		issueFormat = issueFormat.trim().replaceAll("m", "M");
		issueFormat = issueFormat.trim().replaceAll("D", "d");
		issueFormat = issueFormat.trim().replaceAll("N", "n");
		
		
		String [] issueFormatArr = issueFormat.trim().split("");
		String issueFormatStr = "";
		for(int i = 0 ; i < issueFormatArr.length ; i++) {
			Pattern pattern = Pattern.compile("[yMdxn]{0}");
			if (pattern.matcher(issueFormatArr[i]).find()) {
				issueFormatStr += issueFormatArr[i];
			} 
		}
		int xCount = MyUtil.searchStringCount(issueFormat,"x");
		int nCount = MyUtil.searchStringCount(issueFormat,"n");
		
		int yCount = MyUtil.searchStringCount(issueFormat,"y");
		int mCount = MyUtil.searchStringCount(issueFormat,"M");
		int dCount = MyUtil.searchStringCount(issueFormat,"d");
		
		
		String num = "";
		String dateFormatStr = "";
		if(nCount > 0) {
			num = issueNumStr;
		}
		else if(xCount > 0) {
			while(issueNumStr.length() < xCount) {
				issueNumStr = "0"+issueNumStr;
			}
			num = issueNumStr;
		}
		String dateFormat = issueFormat.trim().replaceAll("x", "");
		dateFormat = dateFormat.trim().replaceAll("X", "");
		dateFormat = dateFormat.trim().replaceAll("n", "");
		dateFormat = dateFormat.trim().replaceAll("N", "");
		dateFormat = dateFormat.trim().replaceAll("|", "");
		dateFormat = dateFormat.trim().replaceAll("-", "");
		dateFormat = dateFormat.trim().replaceAll("/", "");
		dateFormat = dateFormat.trim().replaceAll(",", "");
		
		if(date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if(yCount > 0 && mCount > 0 && dCount > 0) {
				dateFormatStr = MyUtil.dateFormat(date, dateFormat);
			}
			else if(yCount > 0){
				dateFormatStr += cal.get(Calendar.YEAR);
			}
			return toLong(dateFormatStr+""+num);
		}
		return toLong(num);
	}
	
	
	public static boolean checkLotteryNum(int id , String data) {
		String[] dataArr = data.split(",");
		int maxNum = Integer.MIN_VALUE;
		int minNum = Integer.MAX_VALUE;
		List<Integer> numList = new ArrayList<Integer>();
		for(int i = 0 ; i < dataArr.length ; i++) {
			if(MyUtil.toInt(dataArr[i]) != -1) {
				int num = MyUtil.toInt(dataArr[i]);
				numList.add(num);
				if(maxNum < num) {
					maxNum = num;
				}
				if(minNum > num) {
					minNum = num;
				}
			}
		}
		
		switch(id) {
			case CQSSC:
			case TJSSC:
			case YNASSC:
			case XJSSC:
			case QQFFC:
			case TXFFC:
			case PL5:
				if(maxNum > 9 || minNum < 0) {
					return false;
				}
				if(numList.size() == 5) {
					return true;
				}
				return false;
			case GD11X5:
			case JX11X5:
			case SH11X5:
			case SD11X5:
			case JS11X5:
			case LN11X5:
			case GX11X5:
			case AF11X5:
			case HLJ11X5:
			case YN11X5:
				if(maxNum > 11 || minNum < 1) {
					return false;
				}
				if(numList.size() == 5) {
					return true;
				}
				return false;
			case HBK3:
			case HLK3:
			case GXK3:
			case JSK3:
			case JLK3:
			case AHK3:
				if(maxNum > 6 || minNum < 1) {
					return false;
				}
				if(numList.size() == 3) {
					return true;
				}
				return false;
			case FC3D:
				if(maxNum > 9 || minNum < 0) {
					return false;
				}
				if(numList.size() == 3) {
					return true;
				}
				return false;
			case BJPK10:
				if(maxNum > 10 || minNum < 1) {
					return false;
				}
				if(numList.size() == 10) {
					return true;
				}
				return false;
			case XGLHC:
				if(maxNum > 49 || minNum < 1) {
					return false;
				}
				if(numList.size() == 7) {
					return true;
				}
				return false;
		}
		
		return false;
	}
	
	
	public static String toLotteryNum(int id , String data) {
		
		if(checkLotteryNum(id,data)) {
			String[] dataArr = data.split(",");
			String[] strArr = new String[dataArr.length];
			for(int i = 0 ; i < dataArr.length ; i++) {
				strArr[i] = ""+MyUtil.toInt(dataArr[i]);
			}
			return String.join(",", strArr);
		}
		return "";
	}
	
	public static int getDateWeek(Date date) {
		if(date != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			
			switch(dayOfWeek) {
				case Calendar.SUNDAY:
					return 7;
				case Calendar.MONDAY:
					return 1;
				case Calendar.TUESDAY:
					return 2;
				case Calendar.WEDNESDAY:
					return 3;
				case Calendar.THURSDAY:
					return 4;
				case Calendar.FRIDAY:
					return 5;
				case Calendar.SATURDAY:
					return 6;
			} 
		}
		return 0;
		 
	}
	
	public static Date getWeekDate(Date date , int week) {
		int dateWeek = getDateWeek(date);
		if(dateWeek > 0) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			
			while(dateWeek > week) {
				c.add(Calendar.DAY_OF_YEAR,-1);
				dateWeek--;
			}
			while(dateWeek < week) {
				c.add(Calendar.DAY_OF_YEAR,1);
				dateWeek++;
			}
			
			return c.getTime();
		}
		return date;
		
	}
	
	
	public static Date getNextWeekDate(Date date , int week) {
		int dateWeek = getDateWeek(date);
		if(dateWeek > 0) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			
			while(dateWeek > week) {
				c.add(Calendar.DAY_OF_YEAR,-1);
				dateWeek--;
			}
			while(dateWeek < week) {
				c.add(Calendar.DAY_OF_YEAR,1);
				dateWeek++;
			}
			
			c.add(Calendar.DAY_OF_YEAR,7);
			
			return c.getTime();
		}
		return date;
		
	}

}
