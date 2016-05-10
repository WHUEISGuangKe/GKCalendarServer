package com.guangke.service.impl;

import java.util.List;

import com.guangke.bean.CalendarBean;
import com.guangke.dao.CalendarDao;
import com.guangke.dao.impl.CalendarDaoImpl;
import com.guangke.service.CalendarService;
import com.guangke.util.VersionController;

public class CalendarServiceImpl implements CalendarService {
	private CalendarDao dao = new CalendarDaoImpl();

	@Override
	public List<CalendarBean> queryCalendars(String userName) {
		return dao.queryCalendars(userName);
	}

	@Override
	public boolean createCalendar(String userName, String calendar_title, int date, String content) {
		int calendar_id = dao.createCalendar(userName, calendar_title, date, content);
		if (calendar_id <= 0)
			return false;
		VersionController.VersionMap.put(calendar_id, 1);
		boolean flag = dao.addMember(userName, calendar_id);
		return flag;
	}

	@Override
	public boolean addMember(String username, String memberName, int calendar_id) {
		// 权限校验(未实现)
		return dao.addMember(memberName, calendar_id);
	}

	@Override
	public boolean alterContent(String username, int version, int calendar_id, String content, int date) {
		int currentVersion = (int) VersionController.VersionMap.get(calendar_id);
		if (currentVersion != version) {
			return false;
		} else {
			dao.alterContent(version + 1, calendar_id, content, date);
			return true;
		}
	}
}
