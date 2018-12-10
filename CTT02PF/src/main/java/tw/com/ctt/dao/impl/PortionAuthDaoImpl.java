package tw.com.ctt.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IPortionAuthDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class PortionAuthDaoImpl extends BaseDao implements IPortionAuthDao {

	private static final long serialVersionUID = -2957661922178019489L;
	private static final Logger LOG = LogManager.getLogger(CheckPwdDaoImpl.class.getName());
	
	public PortionAuthDaoImpl() {
		super();
	}
	@Override
	public List<Map<String, Object>> getLevel3Auth(String url) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("ON CONN");
				return result;
			} else {
				sb.append("select B.url,B.auth_name  from \n");
				sb.append("(select auth_id from ctt_manager.ctt_auth  where url = ? and auth_level_type = 2) A \n");
				sb.append("inner join  \n");
				sb.append("(select auth_level_type,level2_id,url,auth_name from ctt_manager.ctt_auth ) B \n");
				sb.append("on A.auth_id = B.level2_id  and B.auth_level_type = 3");
				params.add(url);
				result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug(result);
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}

		}
		return result;
	}

}
