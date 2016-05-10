package com.guangke.dao;

import java.util.List;

import com.guangke.bean.CalendarBean;

public interface CalendarDao {
	/**
	 * 查询日程列表
	 * @param userName 对应用户
	 * @return 日程列表
	 */
	public List<CalendarBean> queryCalendars(String userName);
	
	/**
	 * 创建日程（包括创建日程和将创建者加入该日程群组，应该是一个事务操作，本次尚未实现。）
	 * @param userName 用户名
	 * @param calendar_title 日程标题
	 * @param date 日程时间
	 * @param content 日程内容
	 * @return -1:创建失败 其他则为创建的日程id
	 */
	public int createCalendar(String userName, String calendar_title, int date, String content);
	
	/**
	 * 添加一名成员到日程
	 * @param memberName 被添加者
	 * @param calendar_id 日程id
	 * @return
	 */
	public boolean addMember(String memberName, int calendar_id);
	
	/**
	 * 修改内容
	 * @param version 客户端日程版本
	 * @param calendar_id 日程id
	 * @param content 新内容
	 * @param date 新日期
	 * @return
	 */
	public boolean alterContent(int version, int calendar_id, String content, int date);
	
	/**
	 * 读取VersionMap缓存在服务器
	 * @return
	 */
	public boolean readVersionMap();
}
