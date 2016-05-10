package com.guangke.dao;

import com.guangke.bean.UserBean;

public interface UserDao {
	/*
	 * 注册
	 */
	public boolean register(UserBean user);
	
	/*
	 * 根据用户密码查询
	 */
	public UserBean findByNameAndPwd(UserBean user);
	
	/*
	 * 检查用户名是否存在
	 */
	public boolean userExists(String name);
}
