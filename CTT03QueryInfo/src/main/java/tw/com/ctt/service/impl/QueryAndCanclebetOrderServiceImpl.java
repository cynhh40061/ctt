package tw.com.ctt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IQueryAndCanclebetOrderDao;
import tw.com.ctt.service.IQueryAndCanclebetOrderService;
import tw.com.ctt.util.ShowLog;

public class QueryAndCanclebetOrderServiceImpl extends BaseService implements IQueryAndCanclebetOrderService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5501220165554092157L;
	private static final Logger LOG = LogManager.getLogger(QueryAndCanclebetOrderServiceImpl.class.getName());

	@Override
	public Map<String, Object> searchMidBetOrder(long accId) {
		Map<String, Object> returnMap = new ConcurrentHashMap<String, Object>();
		List<String> tableDateList = null;
		try {
			tableDateList = getSevenTableDate();
			if (tableDateList.size() <= 11) {
				returnMap = ((IQueryAndCanclebetOrderDao) dao).searchMidByUnionTable(accId, tableDateList);
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> searchMainBetOrder(long accId) {
		Map<String, Object> returnMap = new ConcurrentHashMap<String, Object>();
		List<String> tableDateList = null;
		try {
			tableDateList = getSevenTableDate();
			if (tableDateList.size() <= 11) {
				returnMap = ((IQueryAndCanclebetOrderDao) dao).searchMainByUnionTable(accId, tableDateList);
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return returnMap;
	}

	private List<String> getSevenTableDate() {
		List<String> newDateList = null;
		List<String> mainList = null;
		List<String> midList = null;

		try {
			newDateList = new ArrayList<String>();
			mainList = ((IQueryAndCanclebetOrderDao) dao).getTableDate("main");
			midList = ((IQueryAndCanclebetOrderDao) dao).getTableDate("mid");

			for (int i = 0; i < mainList.size(); i++) {
				if (mainList.get(i).toString().equals(midList.get(i).toString())) {
					newDateList.add(mainList.get(i));
				}
			}
		} catch (Exception e) {
			newDateList.clear();
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return newDateList;
	}
}
