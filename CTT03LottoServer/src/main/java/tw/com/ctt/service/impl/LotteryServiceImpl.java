package tw.com.ctt.service.impl;

import static tw.com.ctt.constant.LotteryConstant.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.model.LotteryNumBean;
import tw.com.ctt.model.LotterySettingBean;
import tw.com.ctt.model.LotteryTimeSetBean;
import tw.com.ctt.model.LotteryTypeBean;
import tw.com.ctt.dao.impl.BaseDao;
import tw.com.ctt.dao.impl.LotteryDaoImpl;
import tw.com.ctt.service.ILotteryService;
import tw.com.ctt.util.CTTLotteryCalc115;
import tw.com.ctt.util.CTTLotteryCalc3d;
import tw.com.ctt.util.CTTLotteryCalcK3;
import tw.com.ctt.util.CTTLotteryCalcP3p5;
import tw.com.ctt.util.CTTLotteryCalcPk10;
import tw.com.ctt.util.CTTLotteryCalcSsc;
import tw.com.ctt.util.MyUtil;

public class LotteryServiceImpl extends BaseService implements ILotteryService {

	private static final Logger LOG = LogManager.getLogger(LotteryServiceImpl.class.getName());
	BaseDao dao = null;
	
	public void getDao() {
		dao = new LotteryDaoImpl();
	}
	
	public void closeDao() {
		dao.close();
	}

	
	@Override
	public boolean checkLotteryData(Date date,int id) {
		boolean todo = false;
		try{
			getDao();
			todo = ((LotteryDaoImpl) dao).checkLotteryDate(date , id);
		}catch(Exception e) {
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return todo;
	}
	
	@Override
	public List<LotteryNumBean> selectLotteryData(Date date,int id) {
		List<LotteryNumBean> lotteryNumBeanList = null;
		try{
			getDao();
			lotteryNumBeanList = ((LotteryDaoImpl) dao).selectLotteryData(date , id);
		}catch(Exception e) {
			LOG.error(e.getMessage());
			lotteryNumBeanList = null;
		}
		finally {
			closeDao();
		}
		return lotteryNumBeanList;
	}
	
	@Override
	public List<LotteryNumBean> selectLotteryData(Date startTime ,Date endTime , int id){
		List<LotteryNumBean> lotteryNumBeanList = null;
		try{
			getDao();
			lotteryNumBeanList = ((LotteryDaoImpl) dao).selectLotteryData(startTime,endTime,id);
		}catch(Exception e) {
			LOG.error(e.getMessage());
			lotteryNumBeanList = null;
		}
		finally {
			closeDao();
		}
		return lotteryNumBeanList;
	}
	
	
	@Override
	public void addLotteryData(List<LotteryNumBean> beanList) {
		try{
			getDao();
			((LotteryDaoImpl) dao).addLotteryData(beanList);
		}catch(Exception e) {
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
	}

	@Override
	public void updateLotteryData(List<LotteryNumBean> beanList) {
		try{
			getDao();
			((LotteryDaoImpl) dao).updateLotteryData(beanList);
			
		}catch(Exception e) {
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
	}
	
	@Override
	public void addSettingLottery(List<Map<String ,Object>> listMap){
		try{
			getDao();
			((LotteryDaoImpl) dao).addSettingLottery(listMap);
		}catch(Exception e) {
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
	}
	
	@Override
	public List<LotterySettingBean> selectLotterySettingData(int id) {
		List<LotterySettingBean> lotterySettingBeanList = null;
		try{
			getDao();
			lotterySettingBeanList = ((LotteryDaoImpl) dao).selectLotterySettingData(id);
		}catch(Exception e) {
			LOG.error(e.getMessage());
			lotterySettingBeanList = null;
		}
		finally {
			closeDao();
		}
		return lotterySettingBeanList;
	}
	
	@Override
	public List<LotteryTypeBean> selectLotteryBlastType(){
		List<LotteryTypeBean> lotteryTypeBeanList = null;
		try{
			getDao();
			lotteryTypeBeanList = ((LotteryDaoImpl) dao).selectLotteryBlastType();
		}catch(Exception e) {
			LOG.error(e.getMessage());
			lotteryTypeBeanList = null;
		}
		finally {
			closeDao();
		}
		return lotteryTypeBeanList;
	}
	
	@Override
	public long selectMaxIssue(int id) {
		long result = 0;
		try{
			getDao();
			result = ((LotteryDaoImpl) dao).selectMaxIssue(id);
		}catch(Exception e) {
			LOG.error(e.getMessage());
			result = 0;
		}
		finally {
			closeDao();
		}
		return result;
	}
	
	@Override
	public boolean checkIssueIsLottery (int id,long issue) {
		boolean todo = false;
		try{
			getDao();
			todo = ((LotteryDaoImpl) dao).checkIssueIsLottery(id,issue);
		}catch(Exception e) {
			LOG.error(e.getMessage());
			todo = false;
		}
		finally {
			closeDao();
		}
		return todo;
	}
	
	@Override
	public boolean addPairAward(int id ,long periodNum ,Map<String ,String> answerMap) {
		boolean todo = false;
		String tableName = "";
		
		try{
			getDao();
			switch(id) {
				case CQSSC:
					tableName = CQSSC_TABLE_NAME;
					break;
				case TJSSC:
					tableName = TJSSC_TABLE_NAME;
					break;
				case YNASSC:
					tableName = YNASSC_TABLE_NAME;
					break;
				case XJSSC:
					tableName = XJSSC_TABLE_NAME;
					break;
				case QQFFC:
					tableName = QQFFC_TABLE_NAME;
					break;
				case TXFFC:
					tableName = TXFFC_TABLE_NAME;
					break;
				case GD11X5:
					tableName = GD11X5_TABLE_NAME;
					break;
				case JX11X5:
					tableName = JX11X5_TABLE_NAME;
					break;
				case SH11X5:
					tableName = SH11X5_TABLE_NAME;
					break;
				case SD11X5:
					tableName = SD11X5_TABLE_NAME;
					break;
				case JS11X5:
					tableName = JS11X5_TABLE_NAME;
					break;
				case LN11X5:
					tableName = LN11X5_TABLE_NAME;
					break;
				case GX11X5:
					tableName = GX11X5_TABLE_NAME;
					break;
				case AF11X5:
					tableName = AF11X5_TABLE_NAME;
					break;
				case HLJ11X5:
					tableName = HLJ11X5_TABLE_NAME;
					break;
				case YN11X5:
					tableName = YN11X5_TABLE_NAME;
					break;
				case HBK3:
					tableName = HBK3_TABLE_NAME;
					break;
				case HLK3:
					tableName = HLK3_TABLE_NAME;
					break;
				case GXK3:
					tableName = GXK3_TABLE_NAME;
					break;
				case JSK3:
					tableName = JSK3_TABLE_NAME;
					break;
				case JLK3:
					tableName = JLK3_TABLE_NAME;
					break;
				case AHK3:
					tableName = AHK3_TABLE_NAME;
					break;
				case BJPK10:
					tableName = BJPK10_TABLE_NAME;
					break;
				case FC3D:
					tableName = FC3D_TABLE_NAME;
					break;
				case PL5:
					tableName = PL5_TABLE_NAME;
					break;
				case XGLHC:
					tableName = XGLHC_TABLE_NAME;
					break;	
					
			}
			todo = ((LotteryDaoImpl) dao).goCheckLottery(id,periodNum);
			if(todo) {
				todo = ((LotteryDaoImpl) dao).addPairAward(tableName,periodNum,answerMap);
			}
			
			if(todo) {
				try {
					todo = dao.commitDB();
					if(todo) {
						LOG.debug("callProcedure: "+periodNum+","+id+","+tableName);
						todo = ((LotteryDaoImpl) dao).callProcedure(periodNum, id, tableName);
					}
				} catch (SQLException e) {
					try {
						todo = dao.rollBackDB();
					} catch (SQLException e1) {
						LOG.error(e1.getMessage());
					}
				}
			}
			else {
				try {
					todo = dao.rollBackDB();
				} catch (SQLException e) {
					LOG.error(e.getMessage());
				}
			}
			
			
		}catch(Exception e) {
			LOG.error(e.getMessage());
			todo = false;
		}
		finally {
			
			closeDao();
		}
		return todo;
	}
	
	@Override
	public Date getLastBettingTime(int id) {
		Date lastStopBettingTime = null;
		try{
			getDao();
			lastStopBettingTime = ((LotteryDaoImpl) dao).getLastBettingTime(id);
		}catch(Exception e) {
			LOG.error(e.getMessage());
			lastStopBettingTime = null;
		}
		finally {
			closeDao();
		}
		return lastStopBettingTime;
	}
	
	@Override
	public boolean createOrderTable(Date date) {
		boolean todo = true;
		try {
			getDao();
			String dateStr = MyUtil.dateFormat(date, "yyyymmdd");
			String dataBaseName = "ctt_manager";
			
			String mainOrderTableName = "main_order_"+dateStr;
			String midOrderTableName = "mid_order_"+dateStr;
			String betOrderTableName = "bet_order_"+dateStr;
			String winningOrderTableName = "winning_order_"+dateStr;
			
			
			todo &= ((LotteryDaoImpl) dao).createMainOrderTable(dataBaseName,mainOrderTableName);
			todo &= ((LotteryDaoImpl) dao).createMidOrderTable(dataBaseName,midOrderTableName);
			todo &= ((LotteryDaoImpl) dao).createBetOrderTable(dataBaseName,betOrderTableName);
			//todo &= ((LotteryDaoImpl) dao).createWinningOrderTable(dataBaseName,winningOrderTableName);
			
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		
		return todo;
	}
	
	@Override
	public boolean checkLotteryCurrentRatio(Date date) {
		boolean todo = false;
		
		try {
			getDao();
			todo = ((LotteryDaoImpl) dao).checkLotteryCurrentRatio(date);
		}
		catch(Exception e){
			todo = false;
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return todo;
	}
	
	@Override
	public boolean insertLotteryCurrentRatio(Date date) {
		boolean todo = false;
		try {
			getDao();
			todo = ((LotteryDaoImpl) dao).insertLotteryCurrentRatio(date);
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return todo;
	}
	
	@Override
	public List<Map<String ,Object>> selectNowKjPeriodNum() {
		List<Map<String ,Object>> listMap = null;
		try {
			getDao();
			listMap = ((LotteryDaoImpl) dao).selectNowKjPeriodNum();
		}
		catch(Exception e){
			listMap = null;
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return listMap;
	}
	@Override
	public boolean createLotteryMonthAndWeekStatistical() {
		try {
			getDao();
			return ((LotteryDaoImpl) dao).createLotteryMonthAndWeekStatistical();
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return false;
	}
	@Override
	public boolean isCreateLotteryStatistical() {
		try {
			getDao();
			return ((LotteryDaoImpl) dao).isCreateLotteryStatistical();
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return false;
	}
	
	@Override
	public boolean callMainOrderAfterGiveUp(int lotetryId , long periodNum) {
		boolean todo = false;
		try {
			getDao();
			
			todo = ((LotteryDaoImpl) dao).goGiveUpPeriodNum(lotetryId,periodNum);
			if(todo) {
				todo = ((LotteryDaoImpl) dao).callMainOrderAfterGiveUp(lotetryId,periodNum);
			}
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return todo;
	}
	@Override
	public List<Map<String, Object>> selectGiveUpPeriodNum(){
		try {
			getDao();
			return ((LotteryDaoImpl) dao).selectGiveUpPeriodNum();
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return null;
	}
	
	@Override
	public List<LotteryNumBean> getLotteryData(int id){
		try {
			getDao();
			return ((LotteryDaoImpl) dao).getLotteryData(id);
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return null;
	}
	
	@Override
	public long getTimeDiffFromDB() {
		try {
			getDao();
			return ((LotteryDaoImpl) dao).getTimeDiffFromDB();
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return 0;
	}
	@Override
	public boolean updateLotteryStatisticalReport() {
		try {
			getDao();
			return ((LotteryDaoImpl) dao).updateLotteryStatisticalReportData();
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return false;
	}
	
	@Override
	public boolean checkThisWeekLotteryData(int id) {
		try {
			getDao();
			return ((LotteryDaoImpl) dao).checkWeekLotteryData(id, 1, 7);
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return false;
	}
	
	@Override
	public boolean checkNextWeekLotteryData(int id) {
		try {
			getDao();
			return ((LotteryDaoImpl) dao).checkWeekLotteryData(id, 8, 14);
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return false;
	}
	
	@Override
	public boolean checkCreatPeriodThisWeekData(int id) {
		try {
			getDao();
			return ((LotteryDaoImpl) dao).checkCreatPeriodThisWeekData(id);
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return false;
	}
	
	@Override
	public List<LotterySettingBean> getNextInsertWeekPeriod(int id){
		try {
			getDao();
			return ((LotteryDaoImpl) dao).getNextInsertWeekPeriod(id);
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return null;
	}
	
	@Override
	public List<LotterySettingBean> getThisWeekInsertWeekPeriod(int id){
		try {
			getDao();
			return ((LotteryDaoImpl) dao).getThisWeekInsertWeekPeriod(id);
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return null;
	}
	
	
	
	@Override
	public boolean saveLotteryTime(List<LotteryTimeSetBean> lotteryTimeSetBeanList) {
		boolean todo = false;
		int id = 0;
		int createPeriodType = 0;
		try {
			getDao();
			if(lotteryTimeSetBeanList != null && lotteryTimeSetBeanList.size() > 0) {
				id = lotteryTimeSetBeanList.get(0).getId();
				createPeriodType = lotteryTimeSetBeanList.get(0).getType();
				List<Integer> issueList = new ArrayList<Integer>();

				todo = ((LotteryDaoImpl) dao).deleteLotterySetting(id);
				
				
				todo = ((LotteryDaoImpl) dao).insertLotteryTime(lotteryTimeSetBeanList);
				
				if(todo) {
					todo = ((LotteryDaoImpl) dao).updateLotteryType(id , createPeriodType);
				}
			}
		}catch(Exception e1) {
			LOG.debug(e1.getMessage());
		}
		finally {
			if(todo) {
				try{
					todo = dao.commitDB();
				}catch(SQLException e) {
					try {
						todo = dao.rollBackDB();
					} catch (SQLException e1) {
						LOG.debug(e1.getMessage());
					}
				}
			}
			else {
				try {
					todo = dao.rollBackDB();
				} catch (SQLException e) {
					LOG.debug(e.getMessage());
				}
			}
			
			closeDao();
		}
		return todo;
	}
	
	@Override
	public boolean inserLlowfreqLotteryCurrentRatio(int id ,String periodNum , Date date) {
		try {
			getDao();
			return ((LotteryDaoImpl) dao).inserLlowfreqLotteryCurrentRatio(id, periodNum, date);
		}
		catch(Exception e){
			LOG.error(e.getMessage());
		}
		finally {
			closeDao();
		}
		return false;
	}
}
