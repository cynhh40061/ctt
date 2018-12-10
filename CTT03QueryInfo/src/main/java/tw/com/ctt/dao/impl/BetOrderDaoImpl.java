package tw.com.ctt.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IBetOrderDao;
import tw.com.ctt.dao.IKjInfoDao;
import tw.com.ctt.model.EachLotteryAuthInfoBean;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class BetOrderDaoImpl extends BaseDao implements IBetOrderDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5393598519122647798L;
	private static final Logger LOG = LogManager.getLogger(BetOrderDaoImpl.class.getName());
	
	@Override
	public Map<String, Object> getBetOrderInfo(long accId) {
		HashMap<String, Object> mapAll = null;
		HashMap<String, Object> mapL1 = null;
		HashMap<String, String> mapL2 = null;
		
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String level1_name = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return null;
			} else {
				sb = new StringBuilder();				
				sb.append(" select A.main_order_id, C.acc_name,D.title, A.start_period_num, B.period_num, E.played_text, A.bet_data, A.no_of_bet_times, B.no_of_bet, A.money_unit, B.amount as mid_amount, A.no_of_period, A.action_time, B.bonus as mid_bonus, B.order_status as mid_order_status, B.fan_den as mid_fan_den, \n");
				sb.append(" A.no_of_kj_period,A.acc_id, A.game_id, A.played_id ,A.amount as main_amount, A.bonus as main_bonus,A.fan_den as main_fan_den, A.order_type as main_order_type, A.order_status as main_order_status, B.mid_order_id  \n");
				sb.append(" from  \n");
				sb.append(" (select main_order_id, start_period_num, bet_data, no_of_period, no_of_kj_period, acc_id, game_id, played_id ,action_time, amount, money_unit, bonus, fan_den, order_type,no_of_bet_times, order_status from `ctt_manager`.`main_order` where acc_id = ? AND DATE_FORMAT(action_time,'%Y/%m/%d') between DATE_FORMAT(DATE_SUB(current_timestamp(),INTERVAL 7 DAY),'%Y/%m/%d') AND DATE_FORMAT(current_timestamp(),'%Y/%m/%d')order by order_time desc)A \n");
				sb.append(" left join  \n");
				sb.append(" (select main_order_id, mid_order_id, period_num, no_of_bet, amount,  bonus, order_status, fan_den from `ctt_manager`.`mid_order` where acc_id = ? order by period_num desc)B  \n");
				sb.append(" on A.main_order_id = B.main_order_id \n");
				sb.append(" left join  \n");
				sb.append(" (select acc_id, acc_name from `ctt_manager`.`ctt_member_acc` where acc_id= ?)C  \n");
				sb.append(" on A.acc_id = C.acc_id  \n");
				sb.append(" left join  \n");
				sb.append(" (select id, title from `ctt_manager`.`ctt_lottery_type`)D  \n");
				sb.append(" on D.id = A.game_id  \n");
				sb.append(" left join  \n");
				sb.append(" (select played_id, played_text from `ctt_manager`.`ctt_lottery_sub_played`)E  \n");
				sb.append(" on E.played_id = A.played_id  \n");
				sb.append(" order by A.main_order_id asc, B.mid_order_id asc; \n");
				
				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setLong(1, accId);
				ps.setLong(2, accId);
				ps.setLong(3, accId);
				rs = ps.executeQuery();
				
				mapAll = new HashMap<String, Object>();
				mapL1 = new HashMap<String,Object>();
				mapL2 = new HashMap<String, String>();
				
				level1_name = null;
				
				String acc_name = null;
				while (rs.next()) {
					if (acc_name == null) {
						acc_name = rs.getString("acc_name");
					}					
					if (level1_name == null) {
						level1_name = rs.getString("main_order_id");
						mapL1.put("title", rs.getString("title"));
						mapL1.put("start_period_num", rs.getString("start_period_num"));
						mapL1.put("bet_data", rs.getString("bet_data"));
						mapL1.put("no_of_bet_times", rs.getString("no_of_bet_times"));
						mapL1.put("money_unit", rs.getString("money_unit"));
						mapL1.put("no_of_period", rs.getString("no_of_period"));
						mapL1.put("no_of_kj_period", rs.getString("no_of_kj_period"));
						mapL1.put("action_time", rs.getString("action_time"));
						mapL1.put("main_amount", rs.getString("main_amount"));
						mapL1.put("main_bonus", rs.getString("main_bonus"));
						mapL1.put("main_fan_den", rs.getString("main_fan_den"));
						mapL1.put("main_order_type", rs.getString("main_order_type"));
						mapL1.put("main_order_status", rs.getString("main_order_status"));
						mapL1.put("played_text", rs.getString("played_text"));
					}
					
					if (!level1_name.equals(rs.getString("main_order_id"))) {
						mapAll.put(level1_name, mapL1.clone());
						level1_name = rs.getString("main_order_id");
						mapL1.clear();
//						mapL1 = null;
//						mapL1 = new HashMap<String, Object>();
						mapL1.put("title", rs.getString("title"));
						mapL1.put("start_period_num", rs.getString("start_period_num"));
						mapL1.put("bet_data", rs.getString("bet_data"));
						mapL1.put("no_of_bet_times", rs.getString("no_of_bet_times"));
						mapL1.put("money_unit", rs.getString("money_unit"));
						mapL1.put("no_of_period", rs.getString("no_of_period"));
						mapL1.put("no_of_kj_period", rs.getString("no_of_kj_period"));
						mapL1.put("action_time", rs.getString("action_time"));
						mapL1.put("main_amount", rs.getString("main_amount"));
						mapL1.put("main_bonus", rs.getString("main_bonus"));
						mapL1.put("main_fan_den", rs.getString("main_fan_den"));
						mapL1.put("main_order_type", rs.getString("main_order_type"));
						mapL1.put("main_order_status", rs.getString("main_order_status"));
						mapL1.put("played_text", rs.getString("played_text"));
					}					
//					mapL2 = null;
//					mapL2 = new HashMap<String, String>();
					mapL2.clear();
					mapL2.put("period_num", rs.getString("period_num"));					
					mapL2.put("no_of_bet", rs.getString("no_of_bet"));
					mapL2.put("mid_amount", rs.getString("mid_amount"));
					mapL2.put("mid_bonus", rs.getString("mid_bonus"));
					mapL2.put("mid_order_status", rs.getString("mid_order_status"));
					mapL2.put("mid_fan_den", rs.getString("mid_fan_den"));
					mapL2.put("mid_bonus", rs.getString("mid_bonus"));
					mapL2.put("mid_order_id", rs.getString("mid_order_id"));
					mapL1.put(rs.getString("mid_order_id"), mapL2.clone());
				}
				if (level1_name != null) {
					mapAll.put(level1_name, mapL1.clone());
				}
				return mapAll;
			}
		} catch (Exception e) {
			LOG.error("test()_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			mapAll = null;
			return null;
		} finally {
			sb.setLength(0);
			sb = null;
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
				}
			}
			rs = null;
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
				}
			}
			ps = null;
			mapL1 = null;
			mapL2 = null;
			level1_name = "";
			level1_name = null;
		}
	}
}
