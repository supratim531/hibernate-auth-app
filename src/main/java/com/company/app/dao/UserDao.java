package com.company.app.dao;

import com.company.app.entity.User;

public interface UserDao {

	public String register(User user);

	public User login(String username, String password);

}
