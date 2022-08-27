package com.company.app;

import com.company.app.service.UserService;
import com.company.app.serviceimpl.UserServiceImpl;

public class App {

	private static final UserService SERVICE = new UserServiceImpl();

	public static void main(String[] args) {
//		User user = new User("dinka", "dinka123", "dinka@gmail.com");
//		System.out.println(SERVICE.register(user));
		System.out.println(SERVICE.login("arpan", "arpan123"));
	}

}
