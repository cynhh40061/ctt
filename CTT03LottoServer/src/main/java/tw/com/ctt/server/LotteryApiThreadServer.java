package tw.com.ctt.server;

import static tw.com.ctt.constant.LotteryConstant.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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

public class LotteryApiThreadServer extends Thread {

	public static String CHARSET = "UTF8";
	public static String DATA_TYPE = "JSON";

	protected String errDataFile;
	protected String DataFile;

	protected List<LotteryNumBean> openedLotteryList;
	protected List<LotteryWorkBean> lotteryWorkBeanList;
	public static boolean LOG_SWITCH = false;

	protected String TEST_URL;

	protected String URL;

	private int delayTime = 10000;

	protected static int DEFAULE_DATA_COUNT = 10;

	protected Map<String, Object> apiBaseMap = null;

	private static final Logger LOG = LogManager.getLogger(LotteryApiThreadServer.class.getName());
	
//	protected static final String TEST_URL = "http://free.jiangyuan365.com/K25b480a83b1fad";
//	protected static final String URL = "http://vip.jiangyuan365.com/K25b480a83b1fad";
//	protected String TEST_URL = "http://f.apiplus.net";
//	protected String URL = "http://ho.apiplus.net/tf892d6157b54f1f4k";
	

	public LotteryApiThreadServer(int delayTime) {

		this.openedLotteryList = new CopyOnWriteArrayList<LotteryNumBean>();
		this.lotteryWorkBeanList = new CopyOnWriteArrayList<LotteryWorkBean>();

		this.delayTime = delayTime;

	}

	public LotteryApiThreadServer() {

		this.openedLotteryList = new CopyOnWriteArrayList<LotteryNumBean>();
		this.lotteryWorkBeanList = new CopyOnWriteArrayList<LotteryWorkBean>();

		this.delayTime = 10000;

	}

	protected void getApiData(String[] lotteryType_d, String[] issueFormat_d, String[] name_d,
			int[] type_d) {
		try {
			this.apiBaseMap = new HashMap<String, Object>();
			for (int i = 0; i < lotteryType_d.length; i++) {
				if (issueFormat_d.length >= i && name_d.length >= i && type_d.length >= i) {
					Map<String, Object> mapVal = new HashMap<String, Object>();
					mapVal.put("issueFormat", issueFormat_d[i]);
					mapVal.put("name", name_d[i]);
					mapVal.put("type", type_d[i]);
					this.apiBaseMap.put(lotteryType_d[i].toLowerCase(), mapVal);
					//System.out.println(lotteryType_d[i].toLowerCase()+"=>"+mapVal.toString());
				}
			}
		} catch (Exception e) {
			this.apiBaseMap = null;
			LOG.error(e.getMessage());
		}
		//LOG.error(this.apiBaseMap.toString());
	}

	public void setLotteryNum(int id, String type, String issueFormat,int zodiacType) {
		boolean isNull = true;
		for (int i = 0; i < lotteryWorkBeanList.size(); i++) {
			LotteryWorkBean bean = lotteryWorkBeanList.get(i);
			if (bean.getId() == id && bean.getType().equals(type)) {
				isNull = false;
				break;
			}
		}

		if (isNull == true) {
			LotteryWorkBean b1 = new LotteryWorkBean();
			b1.setId(id);
			b1.setType(type);
			b1.setIssueFormat(issueFormat);
			b1.setZodiacType(zodiacType);
			lotteryWorkBeanList.add(b1);
		}
	}

	public int getLotteryCount() {
		return openedLotteryList.size();
	}

	public List<LotteryNumBean> getLotteryNum() {
		List<LotteryNumBean> beanList = new CopyOnWriteArrayList<LotteryNumBean>();
		if (openedLotteryList != null && openedLotteryList.size() > 0) {
			beanList.addAll(openedLotteryList);
			openedLotteryList.clear();
		}
		return beanList;
	}

	@Override
	public void run() {
		try {
			while (true) {
				//System.out.println("thread LotteryApiThreadServer : ");
				checkOnLotteryData();
				Thread.sleep(delayTime);
			}
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
	}

	protected void checkOnLotteryData() {
		if (this.lotteryWorkBeanList != null && this.lotteryWorkBeanList.size() > 0) {
			List<LotteryWorkBean> lw = new ArrayList<LotteryWorkBean>();
			lw.addAll(this.lotteryWorkBeanList);
			this.lotteryWorkBeanList.clear();
			int size = lw.size();
			while(size > 0) {
				getOpencaiLotteryData(lw.get(0));
				lw.remove(0);
				size--;
			}
		}
	}

	protected void addLotteryNum(LotteryNumBean bean) {
		boolean isNull = true;
		for (int i = 0; i < openedLotteryList.size(); i++) {
			LotteryNumBean b1 = openedLotteryList.get(i);
			if (b1.getPeriodNum() == bean.getPeriodNum() && b1.getId() == bean.getId()) {
				if (!"".equals(bean.getData()) ? bean.getData().length() > 0 : false) {
					isNull = false;
					break;
				}
			}
		}
		if (isNull == true)
			openedLotteryList.add(bean);
	}

	protected void getOpencaiLotteryData(LotteryWorkBean lotteryWorkBean) {
		try {
//			if(lotteryWorkBean.getType().equals("xglhc")) {
//				System.out.println(lotteryWorkBean.toString());
//			}
			List<LotteryAPIOpencaiBean> mcailotteryList = ApiOpencai(lotteryWorkBean.getType());
			if (mcailotteryList != null && mcailotteryList.size() > 0) {
				for (int j = 0; j < mcailotteryList.size(); j++) {
					LotteryAPIOpencaiBean lotteryAPIOpencaiBean = (LotteryAPIOpencaiBean) mcailotteryList.get(j);
					//System.out.println(lotteryAPIOpencaiBean.toString());
					if (lotteryAPIOpencaiBean.getLotteryType().equalsIgnoreCase(lotteryWorkBean.getType()) && checkLotteryNum(lotteryWorkBean.getId(),lotteryAPIOpencaiBean.getData())) {
						LotteryNumBean lotteryNumBean = new LotteryNumBean();
						String data = MyUtil.toLotteryNum(lotteryWorkBean.getId(),lotteryAPIOpencaiBean.getData());
						if(!"".equals(data)) {
							lotteryNumBean.setId(lotteryWorkBean.getId());
							lotteryNumBean.setData(data);
							lotteryNumBean.setActualKjTime(new Date());
							lotteryNumBean.setApiKjTime(lotteryAPIOpencaiBean.getApiKjTime());
							long issue = issueFormat(lotteryWorkBean.getId(), lotteryAPIOpencaiBean.getIssue(),
									lotteryWorkBean.getType(), lotteryWorkBean.getIssueFormat());
							lotteryNumBean.setPeriodNum(issue);
							addLotteryNum(lotteryNumBean);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("setMcaiLotteryData:" + e.getMessage());
			LOG.error(e.getMessage());
		}
	}

	protected List<LotteryAPIOpencaiBean> ApiOpencai(String lotterType) {
		return null;
	}

	public long issueFormat(int id, String issue, String type, String format) {
		int issueLength = 0;
		if (apiBaseMap != null && apiBaseMap.size() > 0) {
			try {
				issueLength = issue.length();
				if (issueLength > 0 && apiBaseMap.containsKey(type.toLowerCase())) {
					Map<String, Object> mapData = (Map<String, Object>) apiBaseMap.get(type.toLowerCase());
					if (mapData.containsKey("issueFormat") && !"".equals(mapData.get("issueFormat").toString())) {
//						String[] issueFormatArr = mapData.get("issueFormat").toString().split("");
//						String[] issueArr = issue.split("");
						List<String> issueFormatList = new ArrayList<String>(Arrays.asList(mapData.get("issueFormat").toString().split("")));
						List<String> issueList = new ArrayList<String>(Arrays.asList(issue.split("")));
						
						Date date = null;
						String year = "";
						String month = "";
						String day = "";
						String num = "";// 2018071637
						
						for (int i = 0; i < issueList.size() ; i++) {
							
							if(issueFormatList.size() <= i) {
								if("x".equalsIgnoreCase(issueFormatList.get(i-1))) {
									issueFormatList.add("x");
								}
								else {
									return 0;
								}
							}
							if ("y".equalsIgnoreCase(issueFormatList.get(i))) {
								year += issueList.get(i);
							} else if ("m".equalsIgnoreCase(issueFormatList.get(i))) {
								month += issueList.get(i);
							} else if ("d".equalsIgnoreCase(issueFormatList.get(i))) {
								day += issueList.get(i);
							} else if ("x".equalsIgnoreCase(issueFormatList.get(i))) {
								num += issueList.get(i);
							} else if ("n".equalsIgnoreCase(issueFormatList.get(i))) {
								num = issue;
								break;
							}
						}
						
						if (year.length() > 0 && month.length() > 0 && day.length() > 0) {
							date = MyUtil.toDate(year + "/" + month + "/" + day);
						} else if (year.length() > 0 && month.length() > 0) {
							date = MyUtil.toDate(year + "/" + month + "/01");
						} else if (year.length() > 0) {
							date = MyUtil.toDate(year + "/01/01");
						}

						return MyUtil.toIssue(date, num, format);
					}
				}
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
		return 0;
	}

	public StringBuilder getUrlJson(String urlStr) throws Exception {
		URL url = null;
		StringBuilder sb = null;
		BufferedReader br = null;
		try {
			url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setReadTimeout(10 * 1000);
			conn.setConnectTimeout(10 * 1000);
			conn.connect();
			int resCode = conn.getResponseCode();
			if(resCode == 200) {
				InputStreamReader is = new InputStreamReader(conn.getInputStream(), CHARSET);
				br = new BufferedReader(is);
				sb = new StringBuilder();
				
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {

					LOG.error(e.getMessage());
				}
			}
		}

		return sb;
	}
	
	protected boolean checkLotteryNum(int id , String data) {
		return MyUtil.checkLotteryNum(id, data);
	}

	protected void addLotteryTextData(String lotterType, String fileName, String url, StringBuilder sb) {
		if (LOG_SWITCH) {
			FileWriter fw;

			try {
				fw = new FileWriter(DataFile + "/" + lotterType + "_" + fileName, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("time:" + new Date() + "\r\n");
				bw.write("URL:" + url + "\r\n");
				bw.write(sb.toString() + "\r\n");

				bw.flush();
				fw.flush();
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOG.error(e.getMessage());
			}
		}

	}

	protected void addErrorTextData(String lotterType, String url, StringBuilder sb) {
		if (LOG_SWITCH) {
			FileWriter fw;
			try {
				fw = new FileWriter(errDataFile + "/" + lotterType + "_err.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("time:" + new Date() + "\r\n");
				bw.write("URL:" + url + "\r\n");
				bw.write(sb.toString() + "\r\n");

				bw.flush();
				fw.flush();
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOG.error(e.getMessage());
			}
		}
	}

}
