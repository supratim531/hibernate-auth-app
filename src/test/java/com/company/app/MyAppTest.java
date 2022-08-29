package com.company.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.app.entity.Course;
import com.company.app.entity.User;
import com.company.app.exception.WrongCredentialException;
import com.company.app.service.CourseService;
import com.company.app.service.UserService;
import com.company.app.serviceimpl.CourseServiceImpl;
import com.company.app.serviceimpl.UserServiceImpl;

@TestMethodOrder(OrderAnnotation.class)
class MyAppTest {

	private static final Logger LOG = LoggerFactory.getLogger(MyAppTest.class);
	private static final UserService USER_SERVICE = new UserServiceImpl();
	private static final CourseService COURSE_SERVICE = new CourseServiceImpl();

	@Test
	@Order(1)
	void authenticationTest() throws WrongCredentialException {
		LOG.info("----- Authentication Testing Done -----");
		User registeredUser = User.builder().username("a").password("123").emailAddress("a@gmail").build();
		USER_SERVICE.register(registeredUser);

		User loggedInUser = USER_SERVICE.login("a", "123");
		assertThat(loggedInUser).isNotNull();
		assertThat(loggedInUser.getUsername()).isEqualTo("a");
		assertThat(loggedInUser.getPassword()).isEqualTo("123");
		assertThat(loggedInUser.getEmailAddress()).isEqualTo("a@gmail");
	}

	@Test
	@Order(2)
	void oneToManyRelationMappingTest() {
		LOG.info("----- OneToMany Relationship Testing Done -----");
		User registeredUser = User.builder().username("a").password("123").emailAddress("a@gmail").build();
		Course java = Course.builder().title("Java").price(1500L).published(false).user(registeredUser).build();
		Course python = Course.builder().title("Python").price(2000L).published(false).user(registeredUser).build();

		List<Course> courses = new ArrayList<>();
		courses.add(java);
		courses.add(python);

		registeredUser.setCourses(courses);
		COURSE_SERVICE.createCourse(java);
		COURSE_SERVICE.createCourse(python);

		assertThat(java.getUser()).isEqualTo(registeredUser);
		assertThat(python.getUser()).isEqualTo(registeredUser);
		assertThat(registeredUser.getCourses().get(0).getTitle()).isEqualTo("Java");
		assertThat(registeredUser.getCourses().get(1).getTitle()).isEqualTo("Python");
	}

}
