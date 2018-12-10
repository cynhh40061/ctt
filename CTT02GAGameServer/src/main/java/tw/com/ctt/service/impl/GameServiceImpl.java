package tw.com.ctt.service.impl;

import static tw.com.ctt.constant.LogToDBConstant.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.GameDaoImpl;
import tw.com.ctt.model.CommissionRawBean;
import tw.com.ctt.model.GamePuncheRecordsBean;
import tw.com.ctt.service.IGameService;

public class GameServiceImpl extends BaseService implements IGameService {

	private static final long serialVersionUID = 5855274351289897836L;
	private static final Logger LOG = LogManager.getLogger(GameServiceImpl.class.getName());
	
	/**
	 * checkPwd
	 */
	@Override
	public Map<String, String> checkPwd(String userName, String pwd) {
		Map<String,String> result = null;
		try{
			result = ((GameDaoImpl) dao).checkPwd(userName, pwd);
		}catch(Exception e) {
			LOG.error(e.getMessage());
		}
		finally {
			
			dao.close();
		}
		return result;
	}

	/**
	 * getPlayer basic info
	 */
	@Override
	public List<Map<String, Object>> getPlayer(long accId) {
		List<Map<String, Object>> result = null;
		try {
			result = ((GameDaoImpl) dao).getPlayer(accId);
		}catch(Exception e) {
			LOG.error(e.getMessage());
		}
		finally {
			dao.close();
		}
		return result;
	}

	/**
	 * withdraw from player account
	 */
	@Override
	public boolean withdraw(long accId, BigDecimal money) {
		boolean todo = false;
		try {
			todo = ((GameDaoImpl) dao).withdraw(accId, money);
		} catch (SQLException e) {
			todo = false;
			LOG.error(e.getMessage());
		}
		finally {
			try {
				if(todo) {
					todo = dao.commitDB();
				}
				else {
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				LOG.error("SQLException:"+e.getMessage());
			}
			
			dao.close();
		}
		return todo;
	}

	/**
	 * deposit to player account
	 */
	@Override
	public boolean deposit(long accId, BigDecimal money) {
		boolean todo = false;
		try {
			todo = ((GameDaoImpl) dao).deposit(accId, money);
		} catch (SQLException e) {
			todo = false;
			LOG.error(e.getMessage());
		}
		finally {
			try {
				if(todo) {
					todo = dao.commitDB();
				}
				else {
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				LOG.error("SQLException:"+e.getMessage());
			}
			dao.close();
		}
		return todo;
	}

	/**
	 * withdrawAllMoneyToGame when player join to game server. and insert a log.
	 */
	@Override
	public BigDecimal withdrawAllMoneyToGame(long accId , String serverId , String  bet) {
		BigDecimal balance =  new BigDecimal("0");
		BigDecimal gameBet =  null;
		Map<String , Object> map = null;
		boolean todo = false;
		boolean isCommit = false;
		try {
			gameBet = new BigDecimal(bet);
			List<Map<String, Object>> tmpList = ((GameDaoImpl) dao).getPlayer(accId);
			if (tmpList.size() == 1) {
				try {
					balance = (BigDecimal)tmpList.get(0).get("balance");
					if(balance.compareTo(gameBet) >= 0) {
						isCommit = true;
						todo = ((GameDaoImpl) dao).withdraw(accId, balance);
						
						if(todo) {
						 todo = ((GameDaoImpl) dao).updateMoneyPuncheGameAccInfo(accId, balance, serverId);
						}
						if(todo) {
							map = new ConcurrentHashMap<String, Object>();
							map.put("serverId", serverId);
							map.put("punchGameBalance", balance);
							map.put("balance", BigDecimal.ZERO);
							JSONObject json = new JSONObject(map);
							
							todo = dao.setLogToDBNoCommit("ctt_manager.ctt_member_money_log",accId
									,accId,0, OPS_TYPE_MEMBER, ACC_TYPE_MEMBER
									, LOG_ACTION_END_GAME,json.toString(), "");
						}
					}
				} catch (SQLException e) {
					todo = false;
					LOG.error(e.getMessage());
					balance =  new BigDecimal("0");
				}finally {
					try {
						if(isCommit) {
							if(todo) {
								todo = dao.commitDB();
							}
							else {
								todo = dao.rollBackDB();
							}
						}
					} catch (SQLException e) {
						LOG.error("SQLException:"+e.getMessage());
					}
				}
			}
		}catch(Exception e) {
			LOG.error(e.getMessage());
			
		}
		finally {
			if(map != null) {
				map.clear();
				map = null;
			}
			dao.close();
		}
		return balance;
	}
	
/**
 * deposit to player account when the player leave server. and insert a log.
 */
	@Override
	public boolean depositAllMoneyFromGame(long accId, BigDecimal money , String serverId) {
		Map<String,Object> map = null;
		boolean todo = false;
		boolean isCommit = false;
		try {
			List<Map<String, Object>> tmpList = ((GameDaoImpl) dao).getPlayer(accId);
			if (tmpList.size() == 1) {
				LOG.debug("depositAllMoneyFromGame");
				isCommit = true;
				todo =  ((GameDaoImpl) dao).deposit(accId, money);
				
				if(todo) {
					 todo = ((GameDaoImpl) dao).updateMoneyPuncheGameAccInfo(accId, BigDecimal.ZERO, serverId);
				}
				if(todo) {
					map = new ConcurrentHashMap<String, Object>();
					map.put("serverId", serverId);
					map.put("punchGameBalance", BigDecimal.ZERO);
					map.put("balance", money);
					JSONObject json = new JSONObject(map);
					
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_member_money_log",accId
							,accId,0, OPS_TYPE_MEMBER, ACC_TYPE_MEMBER
							, LOG_ACTION_START_GAME,json.toString(), "");
				}
				
					
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		finally {
			try {
				if(isCommit) {
					if(todo) {
						todo = dao.commitDB();
					}
					else {
						todo = dao.rollBackDB();
					}
				}
			} catch (SQLException e) {
				LOG.error("SQLException:"+e.getMessage());
			}
			
			if(map != null) {
				map.clear();
				map = null;
			}
			dao.close();
		}
		return todo;
	}
	
	/**
	 * updateMoneyPuncheGameAccInfo
	 */
	@Override
	public boolean updateMoneyPuncheGameAccInfo(long accId, BigDecimal money, String serverId) {
		Map<String , Object> map = null;
		boolean todo = false;
		boolean isCommit = false;
		try {
			List<Map<String, Object>> tmpList = ((GameDaoImpl) dao).getPlayer(accId);
			if (tmpList.size() == 1) {
				isCommit = true;
				todo = ((GameDaoImpl) dao).updateMoneyPuncheGameAccInfo(accId, money, serverId);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			todo = false;
		}finally {
			try {
				if(isCommit) {
					if(todo) {
						todo = dao.commitDB();
					}
					else {
						todo = dao.rollBackDB();
					}
				}
			} catch (SQLException e) {
				LOG.error("SQLException:"+e.getMessage());
			}
			if(map != null) {
				map.clear();
				map = null;
			}
			dao.close();
		}
		return false;
	}
	
	/**
	 * getMemberUpperAccRatio
	 */
	@Override
	public List<Map<String , Object>> getMemberUpperAccRatio(long accId , int gameType) {
		List<Map<String , Object>> result = null;
		try {
			result = ((GameDaoImpl) dao).getMemberUpperAccRatio(accId, gameType);
		}catch(Exception e) {
			LOG.error(e.getMessage());
		}
		finally {
			dao.close();
		}
		return result;
	}
	
	/**
	 * insertGamePuncheRecords
	 */
	@Override
	public boolean insertGamePuncheRecords(GamePuncheRecordsBean gamePuncheRecordsBean , List<CommissionRawBean> commissionRawBeanList) {
		boolean todo = false;
		boolean isCommit = false;
		try {
			if(gamePuncheRecordsBean != null && (commissionRawBeanList != null && commissionRawBeanList.size() > 0)) {
				isCommit = true;
				todo = ((GameDaoImpl) dao).insertGamePuncheRecords(gamePuncheRecordsBean);
				if(todo) {
					for(int i = 0 ; i < commissionRawBeanList.size() ; i++) {
						CommissionRawBean rawBean = commissionRawBeanList.get(i);
						if(todo && rawBean.getMoney().compareTo(BigDecimal.ZERO) != 0) {
							todo = ((GameDaoImpl) dao).insertCommissionRawData(rawBean);
						}
					}
				}
			}
			
		}catch(Exception e) {
			LOG.error(e.getMessage());
		}
		finally {
			try {
				if(isCommit) {
					if(todo) {
						todo = dao.commitDB();
					}
					else {
						todo = dao.rollBackDB();
					}
				}
			} catch (SQLException e) {
				LOG.error("SQLException:"+e.getMessage());
			}
			
			if(gamePuncheRecordsBean != null) {
				gamePuncheRecordsBean.close();
				gamePuncheRecordsBean = null;
			}
			if(commissionRawBeanList != null) {
				commissionRawBeanList.clear();
				commissionRawBeanList = null;
			}
			dao.close();
		}
		return todo;
	}
	
	/**
	 * getPuncheGameLastSerialization
	 */
	@Override
	public int getPuncheGameLastSerialization(Date date , int serverId) {
		try {
			return ((GameDaoImpl) dao).getPuncheGameLastSerialization(date, serverId);
		}
		catch(Exception e) {
			LOG.error(e.getMessage());
		}
		finally {
			dao.close();
		}
		return 0;
	}
	
	/**
	 * addAccNowGameData
	 */
	@Override
	public void addAccNowGameData(long accId , int serverId , String gameHall , String gameName) {
		try {
			((GameDaoImpl) dao).addAccNowGameData(accId, serverId, gameHall, gameName);
		}
		catch(Exception e) {
			LOG.error(e.getMessage());
		}
		finally {
			dao.close();
		}
	}
	
	/**
	 * removeAccNowGameData
	 */
	@Override
	public void removeAccNowGameData(long accId , int serverId ) {
		try {
			((GameDaoImpl) dao).removeAccNowGameData(accId, serverId);
		}
		catch(Exception e) {
			LOG.error(e.getMessage());
		}
		finally {
			dao.close();
		}
	}
	
	/**
	 * getGameServerParam
	 */
	@Override
	public List<Map<String, Object>> getGameServerParam() {
		try {
			List<Map<String, Object>> list = ((GameDaoImpl) dao).getGameServerParam();
			
			return list;
			
		}catch(Exception e) {
			LOG.error(e.getMessage());
		}finally {
			dao.close();
		}
		return null;
		
	}
}
