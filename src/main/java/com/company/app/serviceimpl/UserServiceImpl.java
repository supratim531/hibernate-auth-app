package com.company.app.serviceimpl;

import com.company.app.dao.UserDao;
import com.company.app.daoimpl.UserDaoImpl;
import com.company.app.entity.User;
import com.company.app.service.UserService;

public class UserServiceImpl implements UserService {

	private static final UserDao DAO = new UserDaoImpl();

	@Override
	public String register(User user) {
		return DAO.register(user);
	}

	@Override
	public User login(String username, String password) {
		return DAO.login(username, password);
	}

}
