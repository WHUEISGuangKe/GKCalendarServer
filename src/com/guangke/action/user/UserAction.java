package com.guangke.action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.guangke.bean.UserBean;
import com.guangke.service.UserService;
import com.guangke.service.impl.UserServiceImpl;
import com.guangke.util.VersionController;
import com.guangke.util.WriteResponseUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
	private String username;
	private String password;
	private String token;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setToken(String token){
		this.token = token;
	}

	public void login() throws JSONException, UnsupportedEncodingException {
		UserBean userBean = new UserBean();

		try {
			String string = new String(username.getBytes("ISO8859-1"), "UTF-8");
			System.out.println("UserAction.login() + 用户：" + string + "密码：" + password + " 尝试登陆");
		} catch (Exception e) {
			e.printStackTrace();
		}

		userBean.setUserName(username);
		userBean.setPassword(password);

		UserService service = new UserServiceImpl();
		boolean flag = false;
		flag = service.login(userBean);
		JSONObject jsonObject = new JSONObject();

		if (flag) {
			String token = username.hashCode() + "";
			VersionController.TokenMap.put(username, token);
			jsonObject.put("token", token);
			jsonObject.put("ret_code", 1);
			jsonObject.put("message", "登陆成功");
		} else {
			jsonObject.put("ret_code", 0);
			jsonObject.put("message", "登陆失败");
		}

		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();

			String jsonString = jsonObject.toString();
			out.println(jsonString);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logout() {
		VersionController.TokenMap.remove(username);
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("ret_code", 1);
			jsonObject.put("message", "退出成功");
			System.out.println("退出登录");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WriteResponseUtil.writeResponse(jsonObject);
	}

	public void register() throws UnsupportedEncodingException, JSONException {
		UserBean userBean = new UserBean();
		String string = new String(username.getBytes("ISO8859-1"), "UTF-8");
		System.out.println("UserAction.login() + 用户：" + string + "密码：" + password + " 尝试注册");
		userBean.setUserName(username);
		userBean.setPassword(password);
		UserService service = new UserServiceImpl();
		boolean flag = false;
		flag = service.register(userBean);

		JSONObject jsonObject = new JSONObject();

		if (flag) {
			jsonObject.put("ret_code", 1);
			jsonObject.put("message", "注册成功");
		} else {
			jsonObject.put("ret_code", 0);
			jsonObject.put("message", "注册失败");
		}

		WriteResponseUtil.writeResponse(jsonObject);
	}

	public void isLogin() {
		JSONObject jsonObject = new JSONObject();
		try {
			boolean flag = false;
			String realToken = VersionController.TokenMap.get(username);
			System.out.println("realToken:"+ realToken);
			System.out.println("token:"+ token);
			if (realToken != null) {
				if (realToken.equals(token)) {
					flag = true;
				}
			}
			System.out.println("校验：" + flag);
			if (flag) {
				// 保持登陆状态
				jsonObject.put("ret_code", 1);
				jsonObject.put("message", "保持登陆");
			} else {
				jsonObject.put("ret_code", 0);
				jsonObject.put("message", "token失效");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		WriteResponseUtil.writeResponse(jsonObject);
	}
}
