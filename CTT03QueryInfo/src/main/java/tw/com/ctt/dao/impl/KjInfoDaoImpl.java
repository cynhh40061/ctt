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

import tw.com.ctt.dao.IKjInfoDao;
import tw.com.ctt.model.EachLotteryAuthInfoBean;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class KjInfoDaoImpl extends BaseDao implements IKjInfoDao {
	private static final long serialVersionUID = -5393598519122647798L;
	private static final Logger LOG = LogManager.getLogger(KjInfoDaoImpl.class.getName());
	
	public String getNumByLen(int val, int len) {
		String tmpStr = null;
		try {
			tmpStr = "" + val;
			while (tmpStr.length() < len) {
				tmpStr = "0" + tmpStr;
			}
			return tmpStr.toString();
		} catch (Exception e) {
			return tmpStr.toString();
		} finally {
			tmpStr = "";
			tmpStr = null;
		}
	}
	
	/**
	 * getAllKjTimeStatus will query the period and kj data,
	 * include last 10 periods's kj data and all future period for each lottery( 1~26 ).
	 * 
	 */
	@Override
	public Map<String, Object> getAllKjTimeStatus() {
		HashMap<String, Object> mapAll = null;
		HashMap<String, Object> mapL0 = null;
		HashMap<String, Object> mapL1 = null;
		HashMap<String, String> mapL2 = null;
		
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String level0_name = null;
		String level1_name = null;
		
		String queryTimestamp = null;
		
		List<Object> selectObj = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return null;
			} else {
				sb = new StringBuilder();
				sb.append(" 	select E.type as main_id, D.now_timestamp,D.index_of_today, D.id as local_id,D.period_num as period_num, DATE_FORMAT(D.date, '%Y%m%d') as period_date, E.name as local_name, 	\n");
				sb.append(" 	D.start_betting_time , D.zodiac_type,D.expected_start_betting_time as expected_start_betting_time , D.stop_betting_time ,E.title as title,  D.data as data , D.is_wait_kj, D.status from(  	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status,	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time >= current_timestamp())	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id =1 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 2 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 3 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 4 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 5 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 6 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 7 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 8 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 9 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 10 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 11 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 12 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 13 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 14 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 15 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 16 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 17 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 18 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 19 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 20 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 21 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 22 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 23 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 24 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 25 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	zodiac_type,date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, status, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 26 order by period_num desc LIMIT 11)	\n");
				sb.append(" 	)D	\n");
				sb.append(" 	left join  	\n");
				sb.append(" 	(	\n");
				sb.append(" 	select id, type, name, title from ctt_manager.ctt_lottery_type	\n");
				sb.append(" 	)E  	\n");
				sb.append(" 	on E.id = D.id  	\n");
				sb.append(" 	order by D.id asc, D.period_num asc;	\n");
				
				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				
				mapAll = new HashMap<String, Object>();
				mapL0 = new HashMap<String, Object>();
				mapL2 = new HashMap<String, String>();
				
				int indexL2 = 1;
				
				level0_name = null;
				level1_name = null;
				while (rs.next()) {	
					if(queryTimestamp == null) {
						queryTimestamp = rs.getString("now_timestamp")!=null?rs.getString("now_timestamp"):"";	
					}					
					if (level0_name == null) {
						level0_name = rs.getString("local_id");
					}

					if (!level0_name.equals(rs.getString("local_id"))) {
						mapAll.put(level0_name, mapL0.clone());
						level0_name = rs.getString("local_id");
						mapL0.clear();
//						mapL0 = null;
//						mapL0 = new HashMap<String,Object>();

						indexL2 = 1;
					}
//					mapL2 = null;
//					mapL2 = new HashMap<String, String>();		
					mapL2.clear();
					mapL2.put("i", rs.getString("index_of_today")!=null?rs.getString("index_of_today"):"");
					mapL2.put("e", rs.getString("stop_betting_time")!=null?rs.getString("stop_betting_time"):"");
					mapL2.put("s", rs.getString("start_betting_time")!=null?rs.getString("start_betting_time"):"");
					mapL2.put("exp_s", rs.getString("expected_start_betting_time")!=null?rs.getString("expected_start_betting_time"):"");
					mapL2.put("stat", rs.getString("status")!=null?rs.getString("status"):"");					
					mapL2.put("zodiac_type", rs.getString("zodiac_type")!=null?rs.getString("zodiac_type"):"");
					mapL2.put("date", rs.getString("period_date")!=null?rs.getString("period_date"):"");
					mapL2.put("data", rs.getString("data")!=null?rs.getString("data"):"");
					mapL2.put("p_n", rs.getString("period_num")!=null?rs.getString("period_num"):"");
					mapL0.put(getNumByLen(indexL2,3), mapL2.clone());
					indexL2 ++;
				}
				if (level0_name != null) {
					mapAll.put(level0_name, mapL0.clone());
				}
				mapAll.put("queryTimestamp", queryTimestamp);
				return mapAll;
			}
		} catch (Exception e) {
			LOG.error("getAllKjTimeStatus_Exception===" + e.getMessage());
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
	
	/**
	 * get kj data of 7 days.
	 */
	@Override
	public Map<String, Object> getKjHistoryTimeStatus() {
		HashMap<String, Object> mapAll = null;
		HashMap<String, Object> mapL0 = null;
		List<Object> mapL1 = null;
		HashMap<String, String> mapL2 = null;
		
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String level0_name = null;
		String level1_name = null;
		
		String queryTimestamp = null;
		
		List<Object> selectObj = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return null;
			} else {
				sb = new StringBuilder();
				sb.append(" select unix_timestamp(current_timestamp()) as now_timestamp, unix_timestamp(A.kj_time)as kj_time, A.id as local_id, A.data as data, A.period_num as period_num, B.type as main_id, B.name as name, B.title as title from (\n");
				sb.append(" (select id, data, kj_time, period_num from `ctt_manager`.`ctt_lottery` where DATE_FORMAT(date,'%Y/%m/%d') > DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL 7 DAY),'%Y/%m/%d') and kj_time < current_timestamp())A \n");
				sb.append(" left join\n");
				sb.append(" (select id, type, name, title from `ctt_manager`.`ctt_lottery_type`)B\n");
				sb.append(" on A.id = B.id\n");
				sb.append(" )\n");
				sb.append(" order by A.id asc, A.period_num desc\n");

				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				
				mapAll = new HashMap<String, Object>();
				mapL0 = new HashMap<String, Object>();
				mapL1 = new ArrayList<Object>();
				mapL2 = new HashMap<String, String>();
				
				int indexL1 = 1;
				int indexL0 = 1;
				
				level0_name = null;
				level1_name = null;
				while (rs.next()) {	
					if(queryTimestamp == null) {
						queryTimestamp = rs.getString("now_timestamp")!=null?rs.getString("now_timestamp"):"";	
					}					
					if (level0_name == null) {
						level0_name = rs.getString("main_id");
					}
					if (level1_name == null) {
						level1_name = rs.getString("local_id");
					}					
					if (!level1_name.equals(rs.getString("local_id"))) {
						mapL0.put(getNumByLen(indexL1,3)+"-" +level1_name, mapL1);
						level1_name = rs.getString("local_id");
						mapL1 = null;
						mapL1 = new ArrayList<Object>();
						mapL1.clear();
						indexL1 ++;						
					}
					if (!level0_name.equals(rs.getString("main_id"))) {
						mapAll.put(getNumByLen(indexL0,3)+"-" +level0_name, mapL0.clone());
						level0_name = rs.getString("main_id");
						mapL0.clear();
//						mapL0 = null;
//						mapL0 = new HashMap<String,Object>();
						indexL0++;
						indexL1 = 1;
					}
					mapL2.clear();
//					mapL2 = null;
//					mapL2 = new HashMap<String, String>();
					mapL2.put("data", rs.getString("data")!=null?rs.getString("data"):"");
					mapL2.put("p_n", rs.getString("period_num")!=null?rs.getString("period_num"):"");
					mapL2.put("kj_t", rs.getString("kj_time")!=null?rs.getString("kj_time"):"");
					mapL1.add(mapL2.clone());
				}
				if (level1_name != null) {
					mapL0.put(getNumByLen(indexL1,3)+"-" +level1_name, mapL1);
				}
				if (level0_name != null) {
					mapAll.put(getNumByLen(indexL0,3)+"-" +level0_name, mapL0.clone());
				}
				mapAll.put("queryTimestamp", queryTimestamp);
				return mapAll;
			}
		} catch (Exception e) {
			LOG.error("getAllKjTimeStatus_Exception===" + e.getMessage());
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
	
	/**
	 * getKjLastUpdateTime
	 */
	@Override
	public long getKjLastUpdateTime() {
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long t1 = 0;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return 0;
			} else {
				sb.append("SELECT UNIX_TIMESTAMP(MAX(actual_kj_time)) AS t1 FROM `ctt_manager`.`ctt_lottery` limit 1; \n");
				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				if (rs.next()) {
					t1 = rs.getLong("t1");
				}
				sb.setLength(0);
				ps.clearParameters();
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				return t1;
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return t1;
	}
}
