package com.guangke.action.calendar;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.guangke.bean.CalendarBean;
import com.guangke.service.CalendarService;
import com.guangke.service.impl.CalendarServiceImpl;
import com.guangke.util.VersionController;
import com.guangke.util.WriteResponseUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xml.internal.security.Init;

public class CalendarAction extends ActionSupport {
	private String username; // 操作用户
	private String token; // 校验
	private int version; // 日程版本
	private int date; // 日程日期
	private String content; // 日程内容
	private int calendar_id; // 日程id
	private String calendar_name; // 日程标题
	private String newMemberName;
	
	/**
	 * 查询所有关联日程
	 */
	public void query() {
		CalendarService service = new CalendarServiceImpl();
		List<CalendarBean> list = service.queryCalendars(username);
		JSONArray jsonArray = new JSONArray();
		JSONObject all = new JSONObject();
		boolean flag = true;
		if (list == null) {
			try{
				all.put("ret_code", 0);
				all.put("message", "error");
			}catch (Exception e){
				e.printStackTrace();
			}
		}else{
			try {
				for (CalendarBean calendarBean : list) {
					JSONObject calendarJson = new JSONObject();

					calendarJson.put("calendar_id", calendarBean.getCalendar_id());
					calendarJson.put("calendar_title", calendarBean.getCalendar_title());
					calendarJson.put("version", calendarBean.getVersion());
					calendarJson.put("unixstamp", calendarBean.getDate());
					calendarJson.put("creator", calendarBean.getCreator());
					calendarJson.put("content", calendarBean.getContent());
					jsonArray.put(calendarJson);
				}
		
				all.put("ret_code", 1);
				all.put("message", jsonArray);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		WriteResponseUtil.writeResponse(all);
	}

	/**
	 * 创建日程
	 */
	public void create(){
		CalendarService service = new CalendarServiceImpl();
		boolean flag = false;
		System.out.println("创建日程："+ calendar_name + "-" + content);
		flag = service.createCalendar(username, calendar_name, date, content);
		JSONObject jsonObject = new JSONObject();
		try {
			if (flag) {
				jsonObject.put("ret_code", 1);
				jsonObject.put("message", "创建日程成功");
			}else {
				jsonObject.put("ret_code", 0);
				jsonObject.put("message", "创建失败");
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		WriteResponseUtil.writeResponse(jsonObject);
			
	}

	/**
	 * 添加成员
	 */
	public void addMember(){
		CalendarService service = new CalendarServiceImpl();
		boolean flag = false;
		flag = service.addMember(username, newMemberName, calendar_id);
		JSONObject jsonObject = new JSONObject();
		try {
			if (flag) {
				jsonObject.put("ret_code", 1);
				jsonObject.put("message", "添加成员成功");
			}else {
				jsonObject.put("ret_code", 0);
				jsonObject.put("message", "添加失败");
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		WriteResponseUtil.writeResponse(jsonObject);
	}

	/**
	 * 修改内容
	 */
	public void alter(){
		CalendarService service = new CalendarServiceImpl();
		boolean flag = false;
		synchronized (CalendarAction.class) {
			flag = service.alterContent(username, version, calendar_id, content, date);
		}
		JSONObject jsonObject = new JSONObject();
		try {
			if (flag) {
				jsonObject.put("ret_code", 1);
				jsonObject.put("message", "success");
				jsonObject.put("version", ++version);
				jsonObject.put("calendar_id", calendar_id);
			}else {
				jsonObject.put("ret_code", 0);
				jsonObject.put("message", "failure");
				jsonObject.put("version", ++version);
				jsonObject.put("calendar_id", calendar_id);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		WriteResponseUtil.writeResponse(jsonObject);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCalendar_id(int calendar_id) {
		this.calendar_id = calendar_id;
	}

	public void setCalendar_name(String calendar_name) {
		this.calendar_name = calendar_name;
	}
	
	public void setNewMemberName(String newMemberName) {
		this.newMemberName = newMemberName;
	}
}
