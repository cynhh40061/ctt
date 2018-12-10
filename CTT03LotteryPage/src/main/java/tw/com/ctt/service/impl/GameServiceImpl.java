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

	@Override
	public Map<String, String> checkPwd(String userName, String pwd) {
		return ((GameDaoImpl) dao).checkPwd(userName, pwd);
	}

	@Override
	public List<Map<String, Object>> getPlayer(long accId) {
		return ((GameDaoImpl) dao).getPlayer(accId);
	}

	@Override
	public boolean withdraw(long accId, BigDecimal money) {
		try {
			return ((GameDaoImpl) dao).withdraw(accId, money);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deposit(long accId, BigDecimal money) {
		try {
			return ((GameDaoImpl) dao).deposit(accId, money);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public BigDecimal withdrawAllMoneyToGame(long accId) {
		BigDecimal balance =  new BigDecimal("0");
		List<Map<String, Object>> tmpList = ((GameDaoImpl) dao).getPlayer(accId);
		if (tmpList.size() == 1) {
			balance = (BigDecimal)tmpList.get(0).get("balance");
			try {
				if(!((GameDaoImpl) dao).withdraw(accId, balance)) {
					balance =  new BigDecimal("0");
				}
			} catch (SQLException e) {
				balance =  new BigDecimal("0");
			}
		}
		return balance;
	}

	@Override
	public boolean depositAllMoneyFromGame(long accId, BigDecimal money) {
		try {
			List<Map<String, Object>> tmpList = ((GameDaoImpl) dao).getPlayer(accId);
			if (tmpList.size() == 1) {
				return ((GameDaoImpl) dao).deposit(accId, money);
			}
		} catch (SQLException e) {
		}
		return false;
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
