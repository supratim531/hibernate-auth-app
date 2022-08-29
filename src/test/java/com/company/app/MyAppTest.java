package com.company.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.company.app.entity.User;
import com.company.app.exception.WrongCredentialException;
import com.company.app.service.CourseService;
import com.company.app.service.UserService;
import com.company.app.serviceimpl.CourseServiceImpl;
import com.company.app.serviceimpl.UserServiceImpl;

@TestMethodOrder(OrderAnnotation.class)
class MyAppTest {

	private static final UserService USER_SERVICE = new UserServiceImpl();
	private static final CourseService COURSE_SERVICE = new CourseServiceImpl();

	@Test
	@Order(1)
	void authenticationTest() throws WrongCredentialException {
		User registeredUser = User.builder().username("a").password("123").emailAddress("a@gmail").build();
		USER_SERVICE.register(registeredUser);
		User loggedInUser = USER_SERVICE.login("a", "123");
		assertThat(loggedInUser).isNotNull();
		assertThat(loggedInUser.getUsername()).isEqualTo("a");
		assertThat(loggedInUser.getPassword()).isEqualTo("123");
		assertThat(loggedInUser.getEmailAddress()).isEqualTo("a@gmail");
	}

}
