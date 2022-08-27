package com.company.app.service;

import com.company.app.entity.User;

public interface UserService {

	public String register(User user);

	public User login(String username, String password);

}
