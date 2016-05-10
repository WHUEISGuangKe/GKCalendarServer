package com.guangke.service.impl;

import com.guangke.bean.UserBean;
import com.guangke.dao.UserDao;
import com.guangke.dao.impl.UserDaoImpl;
import com.guangke.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao dao = new UserDaoImpl();
	@Override
	public boolean login(UserBean user) {
		UserBean bean = dao.findByNameAndPwd(user);
		if(bean == null)
			return false;
		else
			return true;
	}

	@Override
	public boolean register(UserBean user) {
		boolean isExists = dao.userExists(user.getUserName());
		if (isExists) {
			return false;
		}else {
			return dao.register(user);
		}
	}
}
