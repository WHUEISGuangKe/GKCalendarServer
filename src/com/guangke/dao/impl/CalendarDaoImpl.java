package com.guangke.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.guangke.bean.CalendarBean;
import com.guangke.dao.CalendarDao;
import com.guangke.util.JdbcUtil;
import com.guangke.util.VersionController;

public class CalendarDaoImpl implements CalendarDao {

	@Override
	public List<CalendarBean> queryCalendars(String userName) {
		String sql_pre = "select calendar_id from calendar_member where username=?";
		List<Integer> idList;
		try {
			QueryRunner qr = JdbcUtil.getRunner();
			idList = qr.query(sql_pre, new ColumnListHandler<>("calendar_id"), userName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
		String sql = "select * from calender_detail where calendar_id=?";
		if (idList == null || idList.size() == 0) {
			return null;
		}
	
		List<CalendarBean> list = null;
		try {
			QueryRunner qr = JdbcUtil.getRunner();
			for (int i = 0; i < idList.size(); i++) {
				CalendarBean bean = qr.query(sql, new BeanHandler<CalendarBean>(CalendarBean.class), idList.get(i));
				if (bean != null) {
					if (list == null) {
						list = new ArrayList<>();
					}
					list.add(bean);
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	@Override
	public int createCalendar(String userName, String calendar_title, int date, String content) {
		String sql = "INSERT INTO calender_detail(version,state,calendar_title,creator,content,date) VALUES (?,?,?,?,?,?)";
		String sql2 = "SELECT calendar_id FROM calender_detail WHERE calendar_title=? AND creator=? AND date=? AND content=?";
		QueryRunner runner = JdbcUtil.getRunner();
		int calendar_id = -1;
		try {
			runner.update(sql, 1, 0, calendar_title, userName, content, date);
			Map<String, Object> map = runner.query(sql2, new MapHandler(), calendar_title, userName, date, content);
			calendar_id = (int)map.get("calendar_id");
		} catch (SQLException e) {
			e.printStackTrace();
			return calendar_id;
		}
		return calendar_id;
	}

	@Override
	public boolean addMember(String memberName, int calendar_id) {
		String sql = "INSERT INTO calendar_member(calendar_id,username) VALUES(?,?)";
		QueryRunner runner = JdbcUtil.getRunner();
		try {
			runner.update(sql, calendar_id, memberName);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean alterContent(int version, int calendar_id, String content, int date) {
		String sql = "UPDATE calender_detail SET version=?,content=?,date=? WHERE calendar_id=?";
		
		QueryRunner runner = JdbcUtil.getRunner();
		try {
			runner.update(sql, version, content, date, calendar_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		VersionController.VersionMap.put(calendar_id, ++ version);
		return true;
	}

	@Override
	public boolean readVersionMap() {
		String sql = "SELECT calendar_id,version FROM calender_detail";
		QueryRunner runner = JdbcUtil.getRunner();
		try {
			List<Map<String, Object>> list = runner.query(sql, new MapListHandler());
			for (Map<String, Object> map : list) {
				Set<Entry<String, Object>> entrySet = map.entrySet();
				Integer mapKey = 0;
				Integer mapValue = 0; 
				for (Entry<String, Object> entry : entrySet) {
					String key = entry.getKey();
					Integer value = (Integer)entry.getValue();
					if (key.equals("calendar_id")) {
						mapKey = value;
					}else if(key.equals("version")) {
						mapValue = value;
					}
				}
				VersionController.VersionMap.put(mapKey,mapValue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
