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

public class GetApi365LotteryNumThread extends LotteryApiThreadServer {

	private static final Logger LOG = LogManager.getLogger(GetApiOpencaiLotteryNumThread.class.getName());

	private static String[] lotteryType_d = new String[] { "cqssc", "tjssc", "ynssc", "xjssc", "qqffc", "txffc",
			"gd11x5", "jx11x5", "sh11x5", "sd11x5", "js11x5", "ln11x5", "gx11x5", "af11x5", "hlj11x5", "yn11x5", "hbk3",
			"hlk3", "gxk3", "jsk3", "jlk3", "ahk3", "bjpk10", "fc3d", "pl5","xglhc" };
	private static String[] issueFormat_d = new String[] { "yyyymmddxxx", "yyyymmddxx", "yyyymmddxxx", "yyyymmddxxx",
			"yyyymmdd-xxxx", "yyyymmdd-xxxx", "yymmddxxx", "yyyymmddxxx", "yyyymmddxx", "yymmddxx", "yyyymmddxx",
			"yyyymmddxx", "yymmddxx", "yymmddxx", "yymmddxx", "yyyymmddxxx", "yyyymmddxxx", "yyyymmddxx", "yyyymmddxxx",
			"yymmddxxx", "yyyymmddxx", "yyyymmddxx", "n", "yyyyxxx", "yyyyxxx","yy\\/xxx" };
	private static String[] name_d = new String[] { "cqssc", "tjssc", "ynssc", "xjssc", "qqffc", "txffc", "gd11x5",
			"jx11x5", "sh11x5", "sd11x5", "js11x5", "ln11x5", "gx11x5", "af11x5", "hlj11x5", "yn11x5", "hbk3", "hlk3",
			"gxks", "jsks", "jlk3", "ahk3", "bjpk10", "fc3d", "tcpl5","xglhc" };
	//18\/094
	private static int[] type_d = new int[] { 0, 0, -1, 0, -1, -1, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0,0 };
	
//	private static int[] type_d = new int[] { 1, -1, -1, -1, -1, -1, -1,-1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1, -1, 1, -1,
//			-1 };
	
	private static final String TOKEN = "/K25b480a83b1fad";
	
	public GetApi365LotteryNumThread(int delayTime) {

		super(delayTime);

		super.DEFAULE_DATA_COUNT = 10;
		super.errDataFile = "C:\\lottery3";
		super.DataFile = "C:\\lottery3";

		super.URL = "http://vip.jiangyuan365.com";

		super.TEST_URL = "http://free.jiangyuan365.com";

		super.getApiData(lotteryType_d, issueFormat_d, name_d, type_d);

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
			if (super.apiBaseMap != null && super.apiBaseMap.size() > 0
					&& super.apiBaseMap.containsKey(lotteryType.toLowerCase())) {
				try {
					Map<String, Object> mapData = (Map<String, Object>) super.apiBaseMap.get(lotteryType.toLowerCase());
					if (mapData.containsKey("type") && mapData.containsKey("name") ) {
						if("-1".equals(mapData.get("type").toString())) {
							return null;
						}
						else if ("1".equals(mapData.get("type").toString()) && !"".equals(URL)) {
							url = URL  + TOKEN + "/" + mapData.get("name").toString() + "-" + DEFAULE_DATA_COUNT + "."
									+ DATA_TYPE;
						} else {
							url = TEST_URL + TOKEN + "/" + mapData.get("name").toString() + "-" + DEFAULE_DATA_COUNT + "."
									+ DATA_TYPE;
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
				if (jsonSB != null) {
					beanList = new ArrayList<LotteryAPIOpencaiBean>();
					addLotteryTextData(lotteryType, MyUtil.dateFormat(new Date(), "yyyyMMdd") + ".txt", url, jsonSB);
					if (MyUtil.isJSONArray(jsonSB.toString())) {
						jsonArray = new JSONArray(jsonSB.toString());
						if(jsonArray != null && jsonArray.length() > 0) {
							for (int i = 0; i < jsonArray.length(); i++) {
								LotteryAPIOpencaiBean bean = new LotteryAPIOpencaiBean();
								jsonObj = jsonArray.getJSONObject(i);
								bean.setLotteryType(lotteryType);
								if (jsonObj.has("issue")) {
									bean.setIssue(jsonObj.getString("issue"));
								}
								if (jsonObj.has("opendate")) {
									bean.setApiKjTime(MyUtil.toDate(jsonObj.getString("opendate")));
								}
								if (jsonObj.has("code")) {
									bean.setData(jsonObj.getString("code"));
								}
								beanList.add(bean);
								
							}
						}
						
					} else {
						addErrorTextData(lotteryType, URL + "/" + lotteryType + "-20" + "." + DATA_TYPE, jsonSB);
						System.out.println("ApiMcai error:" + jsonSB);
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
