package com.guangke.service;

import java.util.List;

import com.guangke.bean.CalendarBean;

public interface CalendarService {
	/**
	 * 查询calendar列表结果
	 * @param userName
	 * @return 
	 */
	public List<CalendarBean> queryCalendars(String userName);
	
	/**
	 * 添加日程，参数待封装
	 * @param userName
	 * @param calendar_title
	 * @param date
	 * @param content
	 * @return
	 */
	public boolean createCalendar(String userName, String calendar_title, int date, String content);
	
	/**
	 * 添加成员，权限校验
	 * @param username （只有日志创建者有权限添加，未实现）
	 * @param memberName
	 * @param calendar_id
	 * @return
	 */
	public boolean addMember(String username, String memberName, int calendar_id);
	
	/**
	 * 修改内容（未做权限控制）
	 * @param username
	 * @param version
	 * @param calendar_id
	 * @param content
	 * @param date
	 * @return
	 */
	public boolean alterContent(String username, int version, int calendar_id, String content, int date);
}
