package tw.com.ctt.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IBlackWhiteDao;
import tw.com.ctt.model.BlackWhiteBean;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

/**
 * <p>
 * created date:2018/06/05 11:00<br>
 * modify date:2018/06/05 11:00<br>
 * BlackWhiteDaoImpl for manage ctt_manager.ctt_black_white_list
 * 
 * @author paul <br>
 * @version 0.0.1 <br>
 */
public class BlackWhiteDaoImpl extends BaseDao implements IBlackWhiteDao {

	private static final long serialVersionUID = 2790391094883011559L;
	private static final Logger LOG = LogManager.getLogger(BlackWhiteDaoImpl.class.getName());

	/**
	 * initial BlackWhiteDaoImpl()
	 */
	public BlackWhiteDaoImpl() {
		super();
		LOG.debug("BlackWhiteDaoImpl() start...");
	}

	/**
	 * get all black white list
	 */
	@Override
	public List<Object> getAllList() {
		LOG.debug("BlackWhiteDaoImpl.getAllList() start...");
		return this.getList(null, null);
	}

	/**
	 * get black white list by (name or ip)
	 */
	@Override
	public List<Object> getList(String name, String ip) {
		StringBuilder sql = null;
		boolean hasWhere = false;
		List<Object> objs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return null;
			} else {
				sql = new StringBuilder();
				sql.append(
						"SELECT id,name,CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS ip,ip1,ip2,ip3,ip4,text,area,type FROM ctt_manager.ctt_black_white_list ORDER BY id DESC ");
				if (name != null ? !name.isEmpty() ? !"".equals(name.trim()) : false : false) {
					hasWhere = true;
					sql.append("WHERE name LIKE ? ");
					objs = new ArrayList<Object>();
					objs.add("%" + name + "%");
				}
				if (ip != null ? !ip.isEmpty() ? !"".equals(ip.trim()) : false : false) {
					if (hasWhere) {
						sql.append(" AND CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) LIKE ?");
					} else {
						hasWhere = true;
						sql.append(" WHERE CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) LIKE ?");
					}
					objs.add(ip + "%");
				}
				return StmtUtil.queryToBean(this.READ_CONN, sql.toString(), objs, new BlackWhiteBean());
			}
		} catch (Exception e) {
			LOG.error("BlackWhiteDaoImpl.getList(String name, String ip)_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return null;
		} finally {
			hasWhere = false;
			if (sql != null) {
				sql.setLength(0);
				sql = null;
			}
			if (objs != null) {
				objs.clear();
				objs = null;
			}
		}
	}

	/**
	 * insert a row into ctt_manager.ctt_black_white_list
	 */
	@Override
	public int insertRow(BlackWhiteBean bean) {
		StringBuilder sql = null;
		List<Object> objs = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.error("no connection");
				return -1;
			} else {
				sql = new StringBuilder();
				sql.append("INSERT INTO ctt_manager.ctt_black_white_list (name, ip1, ip2, ip3, ip4, text, type, area) \n");
				sql.append(
						"SELECT ?,?,?,?,?,?,?,? FROM DUAL WHERE (SELECT COUNT(*) FROM ctt_manager.ctt_black_white_list WHERE ip1=? AND ip2=? AND ip3=? AND ip4=?)=0");
				objs = new ArrayList<Object>();
				objs.add(bean.getName());
				objs.add(bean.getIp1());
				objs.add(bean.getIp2());
				objs.add(bean.getIp3());
				objs.add(bean.getIp4());
				objs.add(bean.getText());
				objs.add(bean.isType());
				objs.add(bean.getArea());
				objs.add(bean.getIp1());
				objs.add(bean.getIp2());
				objs.add(bean.getIp3());
				objs.add(bean.getIp4());
				return StmtUtil.update(this.WRITE_CONN, sql.toString(), objs);
			}
		} catch (Exception e) {
			LOG.error("BlackWhiteDaoImpl.insertRow(BlackWhiteBean bean)_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return -1;
		} finally {
			if (sql != null) {
				sql.setLength(0);
				sql = null;
			}
			if (objs != null) {
				objs.clear();
				objs = null;
			}
		}
	}

	/**
	 * update a row from ctt_manager.ctt_black_white_list
	 */
	@Override
	public int updateRow(BlackWhiteBean bean) {
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.error("no connection");
				return -1;
			} else {
				return StmtUtil.updateByBean(this.WRITE_CONN, "ctt_manager.ctt_black_white_list", bean, "ID", bean.getId());
			}
		} catch (Exception e) {
			LOG.error("BlackWhiteDaoImpl.updateRow(BlackWhiteBean bean)_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return -1;
		} finally {

		}
	}

	/**
	 * delete a row from ctt_manager.ctt_black_white_list
	 */
	@Override
	public int deleteRow(int id) {
		StringBuilder sql = null;
		List<Object> objs = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.error("no connection");
				return -1;
			} else {
				sql = new StringBuilder();
				sql.append("DELETE FROM ctt_manager.ctt_black_white_list WHERE ID = ?");
				objs = new ArrayList<Object>();
				objs.add(id);
				return StmtUtil.update(this.WRITE_CONN, sql.toString(), objs);
			}
		} catch (Exception e) {
			LOG.error("BlackWhiteDaoImpl.deleteRow(int id)_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return -1;
		} finally {
			if (sql != null) {
				sql.setLength(0);
				sql = null;
			}
			if (objs != null) {
				objs.clear();
				objs = null;
			}
		}
	}

}
