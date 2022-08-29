package com.company.app.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.app.dao.UserDao;
import com.company.app.daoimpl.UserDaoImpl;
import com.company.app.entity.User;
import com.company.app.exception.WrongCredentialException;
import com.company.app.service.UserService;

public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	private static final UserDao DAO = new UserDaoImpl();

	@Override
	public String register(User user) {
		return DAO.register(user);
	}

	@Override
	public User login(String username, String password) throws WrongCredentialException {
		return DAO.login(username, password);
	}

}
