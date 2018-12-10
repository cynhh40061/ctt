package tw.com.ctt.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.impl.GameDaoImpl;
import tw.com.ctt.service.IGameService;

public class GameServiceImpl extends BaseService implements IGameService {

	private static final long serialVersionUID = 5855274351289897836L;
	private static final Logger LOG = LogManager.getLogger(GameServiceImpl.class.getName());

	/**
	 * checkPwd
	 */
	@Override
	public Map<String, String> checkPwd(String userName, String pwd) {
		return ((GameDaoImpl) dao).checkPwd(userName, pwd);
	}

	/**
	 * getPlayer basic info.
	 */
	@Override
	public List<Map<String, Object>> getPlayer(long accId) {
		return ((GameDaoImpl) dao).getPlayer(accId);
	}
	
	@Override
	public boolean updateRolePuncheGameAccInfo(long accId, String role) {
		try {
//			LOG.debug("4");
			List<Map<String, Object>> tmpList = ((GameDaoImpl) dao).getPlayer(accId);
			if (tmpList.size() == 1) {
//				LOG.debug("5");
				return ((GameDaoImpl) dao).updateRolePuncheGameAccInfo(accId, role);
			}
		} catch (SQLException e) {
		}
		return false;
	}
	
	/**
	 * get role of player.
	 */
	@Override
	public List<Map<String, Object>> getPlayerRole(long accId) {
		try {
			return ((GameDaoImpl) dao).getPlayerRole(accId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
