package tw.com.ctt.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IAdvertisingDao;
import tw.com.ctt.service.IAdvertisingService;

public class AdvertisingServiceImpl extends BaseService implements IAdvertisingService {

	private static final long serialVersionUID = 4837195338449549848L;
	private static final Logger LOG = LogManager.getLogger(AdvertisingServiceImpl.class.getName());

	public AdvertisingServiceImpl() {

	}

	@Override
	public List<Map<String, Object>> getAllPlatformId(long accId) {
		return ((IAdvertisingDao) dao).getAllPlatformId(accId);
	}

	@Override
	public List<Map<String, Object>> getAdData(int platformId) {
		return ((IAdvertisingDao) dao).getAdData(platformId);
	}

	@Override
	public boolean addAdData(int platformId) {
//		int platformId, String adPosition, String imgText
		boolean todo = false;

		try {
//			todo = ((IAdvertisingDao) dao).checkAdPosition(platformId, adPosition);
//			if (todo) {
//				todo = ((IAdvertisingDao) dao).addAdData(platformId, adPosition, imgText);
				todo = ((IAdvertisingDao) dao).addAdData(platformId);
//			}

//			if (todo) {
//				todo = dao.commitDB();
//			} else {
//				todo = dao.rollBackDB();
//			}

		} catch (Exception e) {
			LOG.debug(e.getMessage());
			try {
				todo = dao.rollBackDB();
			} catch (SQLException e1) {
				LOG.debug(e1.getMessage());
			}
		}

		return todo;
	}

	@Override
	public boolean updateAdData(int platformId, String adPosition, String imgURL, String imgSRC) {
		boolean todo = false;

		try {
			todo = ((IAdvertisingDao) dao).updateAdData(platformId, adPosition, imgURL, imgSRC);
			if (todo) {
				todo = dao.commitDB();
			} else {
				todo = dao.rollBackDB();
			}

		} catch (Exception e) {
			LOG.debug(e.getMessage());
			try {
				todo = dao.rollBackDB();
			} catch (SQLException e1) {
				LOG.debug(e1.getMessage());
			}
		}

		return todo;
	}

	@Override
	public boolean deleteAdData(int platformId, String adPosition) {
		boolean todo = false;

		try {
			todo = ((IAdvertisingDao) dao).deleteAdData(platformId, adPosition);
			if (todo) {
				todo = dao.commitDB();
			} else {
				todo = dao.rollBackDB();
			}

		} catch (Exception e) {
			LOG.debug(e.getMessage());
			try {
				todo = dao.rollBackDB();
			} catch (SQLException e1) {
				LOG.debug(e1.getMessage());
			}
		}

		return todo;
	}

	@Override
	public boolean checkAdPosition(int platformId, String adPosition) {
		return ((IAdvertisingDao) dao).checkAdPosition(platformId, adPosition);
	}
}
