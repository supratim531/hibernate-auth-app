package com.company.app.dao;

import com.company.app.entity.User;
import com.company.app.exception.WrongCredentialException;

public interface UserDao {

	public String register(User user);

	public User login(String username, String password) throws WrongCredentialException;

}
