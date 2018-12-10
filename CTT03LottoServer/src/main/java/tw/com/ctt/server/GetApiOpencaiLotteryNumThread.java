package tw.com.ctt.server;
import static tw.com.ctt.constant.LotteryConstant.BJPK10;
import static tw.com.ctt.constant.LotteryConstant.FC3D;
import static tw.com.ctt.constant.LotteryConstant.PL5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import tw.com.ctt.model.LotteryAPIOpencaiBean;
import tw.com.ctt.model.LotteryNumBean;
import tw.com.ctt.model.LotteryWorkBean;
import tw.com.ctt.util.MyUtil;

public class GetApiOpencaiLotteryNumThread extends LotteryApiThreadServer{
	
	private static final Logger LOG = LogManager.getLogger(GetApiOpencaiLotteryNumThread.class.getName());
	
	
	private static String[] lotteryType_d = new String[] { "cqssc", "tjssc", "ynssc", "xjssc", "qqffc", "txffc", "gd11x5", "jx11x5",
			"sh11x5", "sd11x5", "js11x5", "ln11x5", "gx11x5", "af11x5", "hlj11x5", "yn11x5", "hbk3", "hlk3", "gxk3",
			"jsk3", "jlk3", "ahk3", "bjpk10", "fc3d", "pl5","xglhc" };
	private static String[] issueFormat_d  = new String[] { "yyyymmddx", "yyyymmddxxx", "yyyymmddxxx", "yyyymmddxxx", "yyyymmddxxxx",
			"yyyymmddxxxx", "yyyymmddxx", "yyyymmddxx", "yyyymmddxx", "yyyymmddxx", "yyyymmddxx", "yyyymmddxx",
			"yyyymmddxx", "yyyymmddxx", "yyyymmddxx", "yyyymmddxx", "yyyymmddxxx", "yyyymmddxxx", "yyyymmddxxx",
			"yyyymmddxxx", "yyyymmddxxx", "yyyymmddxxx", "n", "yyyyxxx", "yyyyxxx","yyyyxxx" };
	private static String[] name_d = new String[] { "cqssc", "tjssc", "ynssc", "xjssc", "qqffc", "txffc", "gd11x5", "jx11x5",
			"sh11x5", "sd11x5", "js11x5", "ln11x5", "gx11x5", "af11x5", "hlj11x5", "yn11x5", "hbk3", "hlk3", "gxk3",
			"jsk3", "jlk3", "ahk3", "bjpk10", "fc3d", "pl5","xglhc" };
	private static int[] type_d = new int[] { 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0,0,-1 };

	private static final String TOKEN = "/tf892d6157b54f1f4k";
	
	public GetApiOpencaiLotteryNumThread(int delayTime) {

		super(delayTime);

		super.DEFAULE_DATA_COUNT = 10;
		super.errDataFile = "C:\\lottery3";
		super.DataFile = "C:\\lottery3";

		super.URL = "http://ho.apiplus.net";
		
		super.TEST_URL = "http://f.apiplus.net";
		

		
		super.getApiData(lotteryType_d,issueFormat_d,name_d,type_d);

	}
	
	@Override
	protected List<LotteryAPIOpencaiBean> ApiOpencai(String lotteryType) {
		JSONObject jsonObj = null;
		JSONArray jsonArray = null;
		StringBuilder jsonSB = null;
		List<LotteryAPIOpencaiBean> beanList = null;
		String url = "";
		String code = "";

		try {
			if (super.apiBaseMap != null && super.apiBaseMap.size() > 0 && super.apiBaseMap.containsKey(lotteryType.toLowerCase())) {
				try {
					Map<String ,Object> mapData = (Map<String ,Object>) super.apiBaseMap.get(lotteryType.toLowerCase());
					if (mapData.containsKey("type") && mapData.containsKey("name")) {
						if("-1".equals(mapData.get("type").toString())) {
							return null;
						}
						else if ("1".equals(mapData.get("type").toString())
								&& !"".equals(URL)) {
							url = URL + "/" + mapData.get("name").toString() + "-"
									+ DEFAULE_DATA_COUNT + "." + DATA_TYPE;
						} else {
							url = TEST_URL + "/"  + mapData.get("name").toString() + "-"
									+ DEFAULE_DATA_COUNT + "." + DATA_TYPE;
						}
					} else {
						return null;
					}

					//LOG.error("URL=>>" + url);
					jsonSB = getUrlJson(url);

				} catch (Exception e) {
					jsonSB = null;
					LOG.error(e.getMessage());
					LOG.error(url);
				}
				if(jsonSB != null) {
					beanList = new ArrayList<LotteryAPIOpencaiBean>();
					addLotteryTextData(lotteryType,MyUtil.dateFormat(new Date(),"yyyyMMdd")+".txt",url,jsonSB);
					if(MyUtil.isJSON(jsonSB.toString())){
						jsonObj = new JSONObject(jsonSB.toString());
						if(jsonObj.has("code")) {
							code = jsonObj.getString("code");
							for(String key : jsonObj.keySet()) {
								if(key.equals("data")) {
									if(jsonObj.has("data") && jsonObj != null) {
										JSONArray jsonArr = jsonObj.getJSONArray("data");
										for(int k = 0 ; k < jsonArr.length() ; k++) {
											LotteryAPIOpencaiBean bean = new LotteryAPIOpencaiBean();
											JSONObject json = jsonArr.getJSONObject(k);
											bean.setLotteryType(code);
											for(String key2 : json.keySet()) {
												if(key2.equals("expect")) { //期號
													bean.setIssue(json.getString("expect"));
												}
												else if(key2.equals("opencode")){ // 開獎號碼
													bean.setData(json.getString("opencode"));
												}
												else if(key2.equals("opentime")){ // 開獎時間
													bean.setApiKjTime(MyUtil.toDate(json.getString("opentime")));
												}
											}
											beanList.add(bean);
										}
									}
									else if(MyUtil.isJSON(jsonObj.getString("data"))) {
										LotteryAPIOpencaiBean bean = new LotteryAPIOpencaiBean();
										JSONObject json =  new JSONObject(jsonObj.getString("data"));
										bean.setLotteryType(code);
										for(String key2 : json.keySet()) {
											if(key2.equals("expect")) { //期號
												bean.setIssue(json.getString("expect"));
											}
											else if(key2.equals("opencode")){ // 開獎號碼
												bean.setData(json.getString("opencode"));
											}
											else if(key2.equals("opentime")){ // 開獎時間
												bean.setApiKjTime(MyUtil.toDate(json.getString("opentime")));
											}
										}
										beanList.add(bean);
									}
									else {
										System.out.println("Data is not JSON:"+jsonObj.getString("data"));
									}
								}
							}
						}
						else {
							System.out.println(" data key is not code :"+jsonSB.toString());
						}
					}
					else {
						addErrorTextData(lotteryType,URL+"/"+lotteryType+"-20"+"."+DATA_TYPE,jsonSB);
						System.out.println("ApiMcai error:"+jsonSB);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("ApiMcai " + e.getMessage());
			beanList = null;
		} finally {
			if (jsonSB != null) {
				jsonSB.setLength(0);
				jsonSB = null;
			}

		}

		return beanList;
	}

}
