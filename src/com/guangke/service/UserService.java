package com.guangke.service;

import com.guangke.bean.UserBean;

public interface UserService {
	/*
	 * 登陆
	 */
	boolean login(UserBean user);
	
	/*
	 * 注册
	 */
	boolean register(UserBean user);
}
