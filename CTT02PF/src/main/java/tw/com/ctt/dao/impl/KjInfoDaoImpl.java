package tw.com.ctt.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IKjInfoDao;
import tw.com.ctt.util.ShowLog;

public class KjInfoDaoImpl extends BaseDao implements IKjInfoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5393598519122647798L;
	private static final Logger LOG = LogManager.getLogger(KjInfoDaoImpl.class.getName());

	@Override
	public Map<String, Object> getAllKjTimeStatusNoFrontNumber() {
		Map<String, Object> mapAll = null;
		Map<String, Map<String, String>> mapL1 = null;
		Map<String, String> mapL2 = null;
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String level1_name = null;
		String queryTimestamp = null;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return null;
			} else {
				sb = new StringBuilder();
				sb.append(
						" 	select E.type as main_id, D.now_timestamp,D.index_of_today, D.id as local_id,D.period_num as period_num, DATE_FORMAT(D.date, '%Y%m%d') as period_date, E.name as local_name, 	\n");
				sb.append(
						" 	D.start_betting_time , D.expected_start_betting_time as expected_start_betting_time , D.stop_betting_time ,E.title as title,  D.data as data , D.is_wait_kj from(  	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(" 	from ctt_manager.ctt_lottery where expected_start_betting_time >= current_timestamp())	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id =1 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 2 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 3 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 4 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 5 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 6 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 7 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 8 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 9 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 10 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 11 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 12 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 13 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 14 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 15 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 16 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 17 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 18 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 19 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 20 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 21 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 22 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 23 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	union 	\n");
				sb.append(" 	(select 	\n");
				sb.append(" 	date,id,period_num,unix_timestamp(expected_start_betting_time)as  expected_start_betting_time,	\n");
				sb.append(" 	unix_timestamp(current_timestamp()) as now_timestamp, 	\n");
				sb.append(" 	unix_timestamp(start_betting_time)as start_betting_time, 	\n");
				sb.append(" 	unix_timestamp(stop_betting_time) as stop_betting_time, 	\n");
				sb.append(
						" 	data ,case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_wait_kj, 	\n");
				sb.append(" 	index_of_today 	\n");
				sb.append(
						" 	from ctt_manager.ctt_lottery where expected_start_betting_time < current_timestamp() AND id = 24 order by period_num desc LIMIT 10)	\n");
				sb.append(" 	)D	\n");
				sb.append(" 	left join  	\n");
				sb.append(" 	(	\n");
				sb.append(" 	select id, type, name, title from ctt_manager.ctt_lottery_type	\n");
				sb.append(" 	)E  	\n");
				sb.append(" 	on E.id = D.id  	\n");
				sb.append(" 	order by D.id asc, D.period_num asc \n");

				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();

				mapAll = new HashMap<String, Object>();
				mapL1 = new HashMap<String, Map<String, String>>();
				mapL2 = new HashMap<String, String>();
				level1_name = null;
				while (rs.next()) {
					if (queryTimestamp == null) {
						queryTimestamp = rs.getString("now_timestamp") != null ? rs.getString("now_timestamp") : "";
					}
					if (level1_name == null) {
						level1_name = rs.getString("local_id");
					}
					if (!level1_name.equals(rs.getString("local_id"))) {
						mapAll.put(level1_name, mapL1);
						level1_name = rs.getString("local_id");
						mapL1 = null;
						mapL1 = new HashMap<String, Map<String, String>>();
					}
					mapL2 = null;
					mapL2 = new HashMap<String, String>();
					mapL2.put("stop", rs.getString("stop_betting_time") != null ? rs.getString("stop_betting_time") : "");
					mapL2.put("start", rs.getString("start_betting_time") != null ? rs.getString("start_betting_time") : "");
					mapL2.put("expected_start",
							rs.getString("expected_start_betting_time") != null ? rs.getString("expected_start_betting_time") : "");
					mapL2.put("is_wait_kj", rs.getString("is_wait_kj") != null ? rs.getString("is_wait_kj") : "");
					mapL2.put("period_date", rs.getString("period_date") != null ? rs.getString("period_date") : "");
					mapL2.put("data", rs.getString("data") != null ? rs.getString("data") : "");
					mapL1.put(rs.getString("period_num"), mapL2);
				}
				if (level1_name != null) {
					mapAll.put(level1_name, mapL1);
				}
				mapAll.put("queryTimestamp", queryTimestamp);
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

	public boolean checkKjTimeStatus(long periodNum, int localId) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return false;
			} else {
				sb = new StringBuilder();
				sb.append(" select case when cou=1 AND is_order_can_be_delete = 1 then 1 else 0 end as result from( \n");
				sb.append(
						" select count(1) as cou, case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_order_can_be_delete from ctt_manager.ctt_lottery where period_num = ? AND id = ?  \n");
				sb.append(" )A \n");
				ps = this.READ_CONN.prepareStatement(sb.toString());

				ps.setLong(1, periodNum);
				ps.setInt(2, localId);

				rs = ps.executeQuery();
				while (rs.next()) {
					String result = rs.getString("result") != null ? rs.getString("result") : "0";
					if ("1".equals(result)) {
						return true;
					}
					return false;
				}
			}
		} catch (Exception e) {
			LOG.error("checkKjTimeStatus_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
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
		}
		return false;
	}

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
				sb.append("SELECT UNIX_TIMESTAMP(complete_time) AS t1 FROM `ctt_manager`.`ctt_lottery` ORDER BY complete_time DESC");
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
