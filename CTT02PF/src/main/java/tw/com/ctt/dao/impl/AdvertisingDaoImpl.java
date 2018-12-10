package tw.com.ctt.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IAdvertisingDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class AdvertisingDaoImpl extends BaseDao implements IAdvertisingDao {

	private static final long serialVersionUID = -4932173986246871073L;
	private static final Logger LOG = LogManager.getLogger(CheckPwdDaoImpl.class.getName());

	public AdvertisingDaoImpl() {
		super();
	}
	@Override
	public List<Map<String, Object>> getAllPlatformId(long accId) {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				sb.append("SELECT platform_id,ad_maximum FROM ctt_manager.ctt_platform \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), new ArrayList<>());
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getAdData(int platformId) {
		List<Map<String, Object>> listMap = null;
		List<Object> selectList = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				selectList = new ArrayList<Object>();
				sb = new StringBuilder();
				sb.append(
						"SELECT platform_id,ad_position_code,ad_url,ad_src,last_update_time,ad_text FROM ctt_manager.ctt_advertising WHERE  platform_id = ?  \n");

				selectList.add(platformId);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectList);

				LOG.debug(listMap.toString());
				return listMap;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectList != null) {
				selectList.clear();
				selectList = null;
			}
		}

		return listMap;
	}

	@Override
	public boolean addAdData(int platformId) {
		//int platformId, String adPosition, String imgText
		StringBuilder sb = null;
		boolean todo = false;
		List<Object> insertList = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				sb = new StringBuilder();
				insertList = new ArrayList<Object>();
				sb.append("INSERT INTO ctt_manager.ctt_advertising \n");
				sb.append("(platform_id,ad_position_code,last_update_time) \n");
				sb.append("VALUES \n");
				sb.append("(?,'leftBig',CURRENT_TIMESTAMP) \n");
				insertList.add(platformId);
				sb.append(",(?,'leftSmall',CURRENT_TIMESTAMP) \n");
				insertList.add(platformId);
				sb.append(",(?,'rightBig',CURRENT_TIMESTAMP) \n");
				insertList.add(platformId);
				sb.append(",(?,'rightSmall',CURRENT_TIMESTAMP) \n");
				insertList.add(platformId);
				sb.append(",(?,'banner1',CURRENT_TIMESTAMP) \n");
				insertList.add(platformId);
				sb.append(",(?,'banner2',CURRENT_TIMESTAMP) \n");
				insertList.add(platformId);
				sb.append(",(?,'banner3',CURRENT_TIMESTAMP) \n");
				insertList.add(platformId);
				sb.append(",(?,'banner4',CURRENT_TIMESTAMP) \n");
				insertList.add(platformId);
				sb.append(",(?,'banner5',CURRENT_TIMESTAMP) \n");
				insertList.add(platformId);

				long cou = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), insertList);
				if (cou != 0) {
					todo = true;
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertList != null) {
				insertList.clear();
				insertList = null;
			}

		}

		return todo;
	}

	@Override
	public boolean updateAdData(int platformId, String adPosition, String imgURL, String imgSRC) {
		StringBuilder sb = null;
		boolean todo = false;
		List<Object> updateList = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				sb = new StringBuilder();
				updateList = new ArrayList<Object>();
				sb.append("update ctt_manager.ctt_advertising set ad_url = ? , ad_src = ? , last_update_time = CURRENT_TIMESTAMP where platform_id =? and ad_position_code = ? \n");

				updateList.add(imgURL);
				updateList.add(imgSRC);
				updateList.add(platformId);
				updateList.add(adPosition);

				long cou = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), updateList);
				if (cou != 0) {
					todo = true;
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (updateList != null) {
				updateList.clear();
				updateList = null;
			}

		}

		return todo;
	}

	@Override
	public boolean deleteAdData(int platformId, String adPosition) {
		StringBuilder sb = null;
		boolean todo = false;
		List<Object> deleteList = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				sb = new StringBuilder();
				deleteList = new ArrayList<Object>();
				sb.append("update ctt_manager.ctt_advertising set ad_url = '' , ad_src = '' , last_update_time = CURRENT_TIMESTAMP where platform_id =? and ad_position_code = ? \n");
				deleteList.add(platformId);
				deleteList.add(adPosition);

				long cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), deleteList);
				if (cou > 0) {
					todo = true;
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (deleteList != null) {
				deleteList.clear();
				deleteList = null;
			}

		}

		return todo;
	}

	@Override
	public boolean checkAdPosition(int platformId, String adPosition) {
		List<Map<String, Object>> listMap = null;
		List<Object> selectList = null;
		StringBuilder sb = null;
		boolean todo = false;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				selectList = new ArrayList<Object>();
				sb = new StringBuilder();
				sb.append("SELECT count(1) as count FROM ctt_manager.ctt_advertising WHERE  platform_id = ? and ad_position_code = ? \n");

				selectList.add(platformId);
				selectList.add(adPosition);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectList);

				if (listMap.size() == 1) {
					if (listMap.get(0).containsKey("count")) {
						if (Integer.parseInt(listMap.get(0).get("count").toString()) == 0) {
							todo = true;
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectList != null) {
				selectList.clear();
				selectList = null;
			}
		}

		return todo;
	}
}
