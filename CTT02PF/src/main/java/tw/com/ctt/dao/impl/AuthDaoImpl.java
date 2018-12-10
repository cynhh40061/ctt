package tw.com.ctt.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IAuthDao;
import tw.com.ctt.model.AuthGroupBean;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class AuthDaoImpl extends BaseDao implements IAuthDao {

	private static final long serialVersionUID = -6743055222073093739L;
	private static final Logger LOG = LogManager.getLogger(CheckPwdDaoImpl.class.getName());

	public AuthDaoImpl() {
		super();
	}
	@Override
	public List<Map<String, Object>> getAuthGroup(long accId, int accLevelType) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append("select B.create_datetime as createDatetime,B.update_datetime as updateDatetime,\n");
				sb.append("B.group_id as groupId,\n");
				sb.append("B.group_text as groupText\n");
				sb.append("from \n");
				sb.append("(\n");
				sb.append("select acc_level_type from `ctt_manager`.`ctt_manager_acc_level` where acc_id=?\n");
				sb.append(") A\n");
				sb.append("left join(\n");
				sb.append("select group_id ,group_text,create_datetime,update_datetime  from `ctt_manager`.`ctt_auth_group`\n");
				sb.append(") B\n");
				sb.append("on A.acc_level_type=0\n");
				params.add(accId);

				result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug("result--" + result.size());
				return result;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
		}

		return result;
	}

	@Override
	public List<Map<String, Object>> getAuthList() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		StringBuilder sb = new StringBuilder();
		List<Object> list = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append("select auth_id,auth_name,auth_remark,auth_level_type,url,admin_auth,'0' as checked from(\n");
				sb.append(
						"SELECT sort_id*65536 AS sort_id,auth_id,auth_name,auth_remark,level1_id,level2_id,auth_level_type,url,admin_auth FROM `ctt_manager`.`ctt_auth` WHERE auth_level_type=1\n");
				sb.append("UNION ALL\n");
				sb.append("select sort_id_T*256 + (SELECT sort_id*65536 from `ctt_manager`.`ctt_auth` where auth_id=B.level1_id_T) as sort_id \n");
				sb.append(", auth_id,auth_name,auth_remark,level1_id_T as level1_id ,level2_id_T as level2_id ,auth_level_type,url,admin_auth \n");
				sb.append("from(\n");
				sb.append(
						"SELECT sort_id AS sort_id_T,auth_id,auth_name,auth_remark,level1_id as level1_id_T,level2_id as level2_id_T,auth_level_type,url,admin_auth FROM `ctt_manager`.`ctt_auth` WHERE auth_level_type=2\n");
				sb.append(") B\n");
				sb.append("UNION ALL\n");
				sb.append(
						"select sort_id_T + (SELECT sort_id*256 from `ctt_manager`.`ctt_auth` where auth_id=C.level2_id_T)+(SELECT sort_id*65536 from `ctt_manager`.`ctt_auth` where auth_id=C.level1_id_T)  as sort_id \n");
				sb.append(", auth_id,auth_name,auth_remark,level1_id_T as level1_id ,level2_id_T as level2_id ,auth_level_type,url,admin_auth \n");
				sb.append("from(\n");
				sb.append(
						"SELECT sort_id AS sort_id_T,auth_id,auth_name,auth_remark,level1_id as level1_id_T,level2_id as level2_id_T,auth_level_type,url,admin_auth FROM `ctt_manager`.`ctt_auth` WHERE auth_level_type=3\n");
				sb.append(") C\n");
				sb.append(")D  order by sort_id \n");

				list = new ArrayList<Object>();

				list = StmtUtil.queryToBean(READ_CONN, sb.toString(), null, new AuthGroupBean());

				int level1Con = 0;
				int level2Con = 0;
				List<Map<String, Object>> firstList1 = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> firstList2 = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < list.size(); i++) {
					AuthGroupBean authGroupBean = (AuthGroupBean) list.get(i);
					if (authGroupBean.getAuthLevelType() == 1) {
						Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
						tmpMap.put("authId", authGroupBean.getAuthId());
						tmpMap.put("authLevelType", authGroupBean.getAuthLevelType());
						tmpMap.put("authName", authGroupBean.getAuthName());
						tmpMap.put("authRemark", authGroupBean.getAuthRemark());
						tmpMap.put("checked", authGroupBean.getChecked());
						tmpMap.put("adminAuth", authGroupBean.getAdminAuth());
						List<Map<String, Object>> innerList = new ArrayList<Map<String, Object>>();
						tmpMap.put("list", innerList);
						result.add(tmpMap);
						level1Con++;
						level2Con = 0;
					} else if (authGroupBean.getAuthLevelType() == 2) {
						firstList1 = (List<Map<String, Object>>) (result.get(level1Con - 1).get("list"));
						Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
						tmpMap.put("authId", authGroupBean.getAuthId());
						tmpMap.put("authLevelType", authGroupBean.getAuthLevelType());
						tmpMap.put("authName", authGroupBean.getAuthName());
						tmpMap.put("authRemark", authGroupBean.getAuthRemark());
						tmpMap.put("checked", authGroupBean.getChecked());
						tmpMap.put("adminAuth", authGroupBean.getAdminAuth());
						List<Map<String, Object>> innerList = new ArrayList<Map<String, Object>>();
						tmpMap.put("list", innerList);
						firstList1.add(tmpMap);
						level2Con++;
					} else if (authGroupBean.getAuthLevelType() == 3) {
						firstList2 = (List<Map<String, Object>>) (((List<Map<String, Object>>) (result.get(level1Con - 1).get("list")))
								.get(level2Con - 1).get("list"));
						Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
						tmpMap.put("authId", authGroupBean.getAuthId());
						tmpMap.put("authLevelType", authGroupBean.getAuthLevelType());
						tmpMap.put("authName", authGroupBean.getAuthName());
						tmpMap.put("authRemark", authGroupBean.getAuthRemark());
						tmpMap.put("checked", authGroupBean.getChecked());
						tmpMap.put("adminAuth", authGroupBean.getAdminAuth());
						firstList2.add(tmpMap);
					}
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			LOG.debug("finally------------------------");
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getGroupAuthList(int groupId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append("select auth_id, auth_name,auth_remark,auth_level_type,url,admin_auth ,CASE WHEN auth_id in (\n");
				sb.append("select auth_id from `ctt_manager`.`ctt_auth_list` where group_id = ? \n");
				sb.append(") THEN 1 ELSE 0 END AS 'checked' from(\n");
				sb.append("select auth_id,auth_name,auth_remark,level1_id,level2_id,auth_level_type,url,admin_auth from(\n");
				sb.append(
						"SELECT sort_id*65536 AS sort_id,auth_id,auth_name,auth_remark,level1_id,level2_id,auth_level_type,url,admin_auth FROM `ctt_manager`.`ctt_auth` WHERE auth_level_type=1 \n");
				sb.append("UNION ALL\n");
				sb.append(
						"select sort_id_T*256 + (SELECT sort_id*65536 from `ctt_manager`.`ctt_auth` where auth_id=B.level1_id_T) as sort_id , auth_id,auth_name,auth_remark,level1_id_T as level1_id ,level2_id_T as level2_id ,auth_level_type,url,admin_auth \n");
				sb.append("from(\n");
				sb.append(
						"SELECT sort_id AS sort_id_T,auth_id,auth_name,auth_remark,level1_id as level1_id_T,level2_id as level2_id_T,auth_level_type,url,admin_auth FROM `ctt_manager`.`ctt_auth` WHERE auth_level_type=2\n");
				sb.append(") B\n");
				sb.append("UNION ALL\n");
				sb.append(
						"select sort_id_T + (SELECT sort_id*256 from `ctt_manager`.`ctt_auth` where auth_id=C.level2_id_T) +(SELECT sort_id*65536 from `ctt_manager`.`ctt_auth` where auth_id=C.level1_id_T)  as sort_id , auth_id,auth_name,auth_remark,level1_id_T as level1_id\n");
				sb.append(",level2_id_T as level2_id ,auth_level_type,url,admin_auth \n");
				sb.append("from(\n");
				sb.append(
						"SELECT sort_id AS sort_id_T,auth_id,auth_name,auth_remark,level1_id as level1_id_T,level2_id as level2_id_T,auth_level_type,url,admin_auth FROM `ctt_manager`.`ctt_auth` WHERE auth_level_type=3\n");
				sb.append(") C\n");
				sb.append(")D  order by D.sort_id \n");
				sb.append(")AA\n");
				List<Object> list = new ArrayList<Object>();
				params.add(groupId);

				list = StmtUtil.queryToBean(READ_CONN, sb.toString(), params, new AuthGroupBean());

				int level1Con = 0;
				int level2Con = 0;
				List<Map<String, Object>> firstList1 = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> firstList2 = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < list.size(); i++) {
					AuthGroupBean authGroupBean = (AuthGroupBean) list.get(i);
					if (authGroupBean.getAuthLevelType() == 1) {
						Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
						tmpMap.put("authId", authGroupBean.getAuthId());
						tmpMap.put("authLevelType", authGroupBean.getAuthLevelType());
						tmpMap.put("authName", authGroupBean.getAuthName());
						tmpMap.put("authRemark", authGroupBean.getAuthRemark());
						tmpMap.put("checked", authGroupBean.getChecked());
						tmpMap.put("adminAuth", authGroupBean.getAdminAuth());
						List<Map<String, Object>> innerList = new ArrayList<Map<String, Object>>();
						tmpMap.put("list", innerList);
						result.add(tmpMap);
						level1Con++;
						level2Con = 0;
					} else if (authGroupBean.getAuthLevelType() == 2) {
						firstList1 = (List<Map<String, Object>>) (result.get(level1Con - 1).get("list"));
						Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
						tmpMap.put("authId", authGroupBean.getAuthId());
						tmpMap.put("authLevelType", authGroupBean.getAuthLevelType());
						tmpMap.put("authName", authGroupBean.getAuthName());
						tmpMap.put("authRemark", authGroupBean.getAuthRemark());
						tmpMap.put("checked", authGroupBean.getChecked());
						tmpMap.put("adminAuth", authGroupBean.getAdminAuth());
						List<Map<String, Object>> innerList = new ArrayList<Map<String, Object>>();
						tmpMap.put("list", innerList);
						firstList1.add(tmpMap);
						level2Con++;
					} else if (authGroupBean.getAuthLevelType() == 3) {
						firstList2 = (List<Map<String, Object>>) (((List<Map<String, Object>>) (result.get(level1Con - 1).get("list")))
								.get(level2Con - 1).get("list"));
						Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
						tmpMap.put("authId", authGroupBean.getAuthId());
						tmpMap.put("authLevelType", authGroupBean.getAuthLevelType());
						tmpMap.put("authName", authGroupBean.getAuthName());
						tmpMap.put("authRemark", authGroupBean.getAuthRemark());
						tmpMap.put("checked", authGroupBean.getChecked());
						tmpMap.put("adminAuth", authGroupBean.getAdminAuth());
						firstList2.add(tmpMap);
					}
				}

				return result;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params.clear();
			}
			params = null;
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getUpLevelAuth() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		StringBuilder sb = new StringBuilder();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append("select auth_id as authId ,auth_name as authName,auth_level_type as authLevelType from(\n");
				sb.append(
						"SELECT sort_id*65536 AS sort_id,auth_id,auth_name,auth_level_type FROM `ctt_manager`.`ctt_auth` WHERE auth_level_type=1   \n");
				sb.append("UNION ALL     \n");
				sb.append(
						"select sort_id_T*256 + (SELECT sort_id*65536 from `ctt_manager`.`ctt_auth` where auth_id=B.level1_id_T) as sort_id , auth_id,auth_name,auth_level_type\n");
				sb.append("from(\n");
				sb.append(
						"SELECT sort_id AS sort_id_T,auth_id,auth_name ,auth_level_type ,level1_id as level1_id_T   FROM `ctt_manager`.`ctt_auth` WHERE auth_level_type=2\n");
				sb.append(") B \n");
				sb.append(")D  order by sort_id \n");

				List<Map<String, Object>> selectResult = new ArrayList<Map<String, Object>>();
				selectResult = StmtUtil.queryToMap(READ_CONN, sb.toString(), null);

				int level1Con = 0;
				List<Map<String, Object>> firstList1 = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < selectResult.size(); i++) {
					int authLevelType = Integer.parseInt("" + selectResult.get(i).get("authLevelType"));
					int authId = Integer.parseInt("" + selectResult.get(i).get("authId"));
					String authName = "" + selectResult.get(i).get("authName");
					if (authLevelType == 1) {
						Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
						tmpMap.put("authId", authId);
						tmpMap.put("authLevelType", authLevelType);
						tmpMap.put("authName", authName);
						List<Map<String, Object>> innerList = new ArrayList<Map<String, Object>>();
						tmpMap.put("list", innerList);
						result.add(tmpMap);
						level1Con++;
					} else if (authLevelType == 2) {
						firstList1 = (List<Map<String, Object>>) (result.get(level1Con - 1).get("list"));
						Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
						tmpMap.put("authId", authId);
						tmpMap.put("authLevelType", authLevelType);
						tmpMap.put("authName", authName);
						firstList1.add(tmpMap);

					}
				}
				return result;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
		}
		return result;
	}

	@Override
	public int addAuth(int authLevelType, int level1, int level2, String authName, String authRemark, String url, int adminAuth) {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		List<Object> insertList = new ArrayList<Object>();
		int cou = 0;
		StringBuilder sb = new StringBuilder();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return 0;
			} else {
				sb.append("insert into ctt_manager.ctt_auth(auth_name,auth_remark,level1_id,level2_id,auth_level_type,url,sort_id,admin_auth) \n");
				sb.append(
						"(select  ? as auth_name , ? as auth_remark , ? as level1_id, ? as level2_id,? as auth_level_type , ? as url , max(A.sort_id)+1 as sort_id, ? as admin_auth from( \n");
				sb.append("select sort_id from  ctt_manager.ctt_auth where auth_level_type = ? and level1_id = ? and level2_id = ? \n");
				sb.append("union \n");
				sb.append("select 0 from dual \n");
				sb.append(") A) \n");

				insertList.add(authName);
				insertList.add(authRemark);
				insertList.add(level1);
				insertList.add(level2);
				insertList.add(authLevelType);
				insertList.add(url);
				insertList.add(adminAuth);
				insertList.add(authLevelType);
				insertList.add(level1);
				insertList.add(level2);

				cou = Integer.parseInt("" + StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), insertList));
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return cou;
	}

	@Override
	public boolean updateAuthGroup(int groupId, String[] authList) {
		List<List<Object>> inertList = new ArrayList<List<Object>>();
		List<Object> params = new ArrayList<Object>();
		StringBuilder sbDeleteGroupAuthList = new StringBuilder();
		StringBuilder sbUpdateGroup = new StringBuilder();
		StringBuilder sbInsertGroupAuthList = new StringBuilder();
		List<Object> listMap = new ArrayList<Object>();
		boolean bool = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return bool;
			} else {
				sbDeleteGroupAuthList.append("delete from ctt_manager.ctt_auth_list where group_id = ?");
				sbUpdateGroup.append("update ctt_manager.ctt_auth_group set update_datetime = CURRENT_TIMESTAMP where group_id = ?");
				params.add(groupId);

				sbInsertGroupAuthList.append("insert into ctt_manager.ctt_auth_list(group_id,auth_id) values (?,?)");

				if (authList != null) {
					for (int i = 0; i < authList.length; i++) {
						listMap = new ArrayList<Object>();
						listMap.add(groupId);
						listMap.add(Integer.parseInt(authList[i]));
						inertList.add(listMap);
					}
				}

				try {
					int c1 = StmtUtil.updateNoCommit(this.WRITE_CONN, sbDeleteGroupAuthList.toString(), params);
					int c2 = StmtUtil.updateNoCommit(this.WRITE_CONN, sbUpdateGroup.toString(), params);
					int c3[] = {};
					if (inertList.size() > 0) {
						c3 = StmtUtil.updateBatchNoCommit(this.WRITE_CONN, sbInsertGroupAuthList.toString(), inertList);
						LOG.debug(sbInsertGroupAuthList.toString());
					}

					if (c2 == 1) {
						if (inertList.size() > 0) {
							if (c3.length == inertList.size()) {
								bool = true;
							} else {
								LOG.debug("updateAuthGroup = >update size c3:" + c3);
								try {
									this.WRITE_CONN.rollback();
								} catch (SQLException e1) {
									LOG.debug("SQLException:" + e1.getMessage());
									LOG.error("\nERROR SQL rollback");
									ShowLog.err(LOG, e1);
								}
							}
						} else {
							bool = true;
						}
					} else {
						LOG.debug("updateAuthGroup = >update size c2:" + c2);
						try {
							this.WRITE_CONN.rollback();
						} catch (SQLException e1) {
							LOG.debug("SQLException:" + e1.getMessage());
							LOG.error("\nERROR SQL rollback");
							ShowLog.err(LOG, e1);
						}
					}

				} catch (SQLException e) {
					try {
						this.WRITE_CONN.rollback();
					} catch (SQLException e1) {
						LOG.debug("SQLException:" + e1.getMessage());
						LOG.error("\nERROR SQL rollback");
						ShowLog.err(LOG, e1);
					}
					LOG.debug("Exception, " + e.getMessage());
					ShowLog.err(LOG, e);
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (inertList != null) {
				inertList = new ArrayList<List<Object>>();
				inertList = null;
			}
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			if (listMap != null) {
				listMap = new ArrayList<Object>();
				listMap = null;
			}

			sbDeleteGroupAuthList.setLength(0);
			sbDeleteGroupAuthList = null;

			sbUpdateGroup.setLength(0);
			sbUpdateGroup = null;

			sbInsertGroupAuthList.setLength(0);
			sbInsertGroupAuthList = null;

		}

		return bool;
	}

	@Override
	public boolean createAuthGroup(String groupText, String[] authList) {
		List<List<Object>> inertList = new ArrayList<List<Object>>();
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		StringBuilder sbInsertGroupAuthList = new StringBuilder();
		List<Object> listMap = new ArrayList<Object>();
		boolean bool = false;
		int cou = 0;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return bool;
			} else {
				sb.append("insert into ctt_manager.ctt_auth_group(group_text,update_datetime,create_datetime) values(?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)");
				sbInsertGroupAuthList.append("insert into ctt_manager.ctt_auth_list(group_id,auth_id) values (?,?)");

				params.add(groupText);

				try {
					cou = Integer.parseInt("" + StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), params));
					if (cou > 0) {
						if (authList != null) {
							for (int i = 0; i < authList.length; i++) {
								listMap = new ArrayList<Object>();

								LOG.debug(cou + "-----" + Integer.parseInt(authList[i]));
								listMap.add(cou);
								listMap.add(Integer.parseInt(authList[i]));
								inertList.add(listMap);
							}
						}
						int c3[] = {};
						if (inertList.size() > 0) {
							c3 = StmtUtil.updateBatchNoCommit(this.WRITE_CONN, sbInsertGroupAuthList.toString(), inertList);
							LOG.debug(inertList.size() + "===============" + c3);
						}

						if (inertList.size() > 0) {
							if (c3.length == inertList.size()) {
								bool = true;

							}
						} else {
							bool = true;
						}

					}
				} catch (SQLException e) {
					try {
						this.WRITE_CONN.rollback();
					} catch (SQLException e1) {
						LOG.debug("SQLException:" + e1.getMessage());
						LOG.error("\nERROR SQL rollback");
						ShowLog.err(LOG, e1);
					}
					LOG.debug("Exception, " + e.getMessage());
					ShowLog.err(LOG, e);
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}

			if (inertList != null) {
				inertList = new ArrayList<List<Object>>();
				inertList = null;
			}
			if (listMap != null) {
				listMap = new ArrayList<Object>();
				listMap = null;
			}
			sb.setLength(0);
			sb = null;

			sbInsertGroupAuthList.setLength(0);
			sbInsertGroupAuthList = null;
		}

		return bool;
	}

	@Override
	public int checkAuthGroupName(String groupName) {
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int cou = -1;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return cou;
			} else {
				sb.append("select count(group_id) as cou from ctt_manager.ctt_auth_group where group_text = ?");
				LOG.debug(sb.toString());
				ps = this.READ_CONN.prepareStatement(sb.toString());

				ps.setString(1, groupName);

				rs = ps.executeQuery();

				if (rs.next()) {
					LOG.debug(rs.getInt("cou"));
					cou = rs.getInt("cou");
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.debug("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.debug("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}

			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
		}

		return cou;
	}

	@Override
	public List<Map<String, Object>> portopnGroupToAccLevel(int accLevelType) {
		StringBuilder sb = new StringBuilder();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				sb.append("SELECT * FROM ctt_manager.acclevel_init_auth");
				result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), null);
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
		}

		return result;
	}

	@Override
	public boolean updateLevelTypeGroup(int levelType, String[] groupIdList) {
		List<List<Object>> inertList = new ArrayList<List<Object>>();
		StringBuilder sb = new StringBuilder();
		List<Object> listObj = null;
		boolean bool = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return bool;
			} else {
				
				sb.append("insert into ctt_manager.acclevel_init_auth(acc_level_type,group_id) values (?,?)");

				if (groupIdList != null) {
					for (int i = 0; i < groupIdList.length; i++) {
						listObj = new ArrayList<Object>();
						listObj.add(levelType);
						listObj.add(Integer.parseInt(groupIdList[i]));
						inertList.add(listObj);
					}
				}

				int[] cou = StmtUtil.updateBatchNoCommit(this.WRITE_CONN, sb.toString(), inertList);
				
				for(int i = 0 ; i < cou.length ; i++) {
					if(cou[i] != 0) {
						bool = true;
					}
					else {
						return false;
					}
				}

			}
		} catch (Exception e) {
			bool = false;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (inertList != null) {
				inertList = new ArrayList<List<Object>>();
				inertList = null;
			}
			if (listObj != null) {
				listObj = new ArrayList<Object>();
				listObj = null;
			}

			sb.setLength(0);
			sb = null;

		}

		return bool;
	}
	
	
	@Override
	public boolean deleteLevelTypeGroup(int levelType) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		boolean bool = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return bool;
			} else {
				sb.append("delete from ctt_manager.acclevel_init_auth where acc_level_type = ?");
				params.add(levelType);

				int c1 = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				if(c1 != 0) {
					bool = true;
				}
			}
				
		} catch (Exception e) {
			bool = false;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			sb.setLength(0);
			sb = null;
		}

		return bool;
	}
	

	@Override
	public int checkAuthRemarkText(String AuthRemarkText) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int cou = -1;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return cou;
			} else {
				sb.append("select count(auth_id) as cou from ctt_manager.ctt_auth where auth_remark = ?");

				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setString(1, AuthRemarkText);
				LOG.debug(ps.toString());
				rs = ps.executeQuery();

				if (rs.next()) {
					cou = rs.getInt("cou");
					LOG.debug(cou);
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.debug("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.debug("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}

			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
		}

		return cou;
	}
}
