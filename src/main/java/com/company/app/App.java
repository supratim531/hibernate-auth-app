package com.company.app;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.app.entity.Course;
import com.company.app.entity.User;
import com.company.app.exception.CourseNotFoundException;
import com.company.app.exception.NoCourseFoundException;
import com.company.app.exception.WrongCredentialException;
import com.company.app.service.CourseService;
import com.company.app.service.UserService;
import com.company.app.serviceimpl.CourseServiceImpl;
import com.company.app.serviceimpl.UserServiceImpl;

public class App {

	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	private static final UserService USER_SERVICE = new UserServiceImpl();
	private static final CourseService COURSE_SERVICE = new CourseServiceImpl();

	private static void error(Throwable e) {
		System.out.println("----- " + e + " [" + new Date() + "] -----");
	}

	private static void createCourse(User user) {
		try {
			Course course = new Course();

			course.setTitle(JOptionPane.showInputDialog("Enter your course name", "Type Here"));
			course.setPrice(Long.parseLong(JOptionPane.showInputDialog("Enter your course price", "Type Here")));
			course.setPublished(false);
			course.setUser(user);

			String msg = COURSE_SERVICE.createCourse(course);
			JOptionPane.showMessageDialog(null, msg);
		} catch (Exception e) {
			error(e);
			String msg = "Internal Server Error (500)\nCause: ";
			JOptionPane.showMessageDialog(null, msg + e.getMessage());
		}
	}

	private static void deleteCourse(User user) {
		try {
			Long courseId = Long.parseLong(JOptionPane.showInputDialog("Enter your course id", "Type Here"));
			String msg = user.getUsername() + ", " + COURSE_SERVICE.deleteCourse(courseId);
			JOptionPane.showMessageDialog(null, msg);
		} catch (CourseNotFoundException e) {
			error(e);
			String msg = "Internal Server Error (500)\nCause: ";
			JOptionPane.showMessageDialog(null, msg + e.getMessage());
		} catch (Exception e) {
			error(e);
			String msg = "Internal Server Error (500)\nCause: ";
			JOptionPane.showMessageDialog(null, msg + e.getMessage());
		}
	}

	private static void getAllCourses(User user) {
		try {
			List<Course> courses = COURSE_SERVICE.getAllCourses();
			JOptionPane.showMessageDialog(null, "See the console for output");
			LOG.info("################### ALL COURSES OF " + user.getUsername() + " ###################");
			courses.forEach(e -> {
				LOG.info(e.getCourseId() + " " + e.getTitle() + " " + e.getPrice());
			});
			LOG.info("################### ALL COURSES OF " + user.getUsername() + " ###################");
		} catch (NoCourseFoundException e) {
			error(e);
			String msg = "Internal Server Error (500)\nCause: ";
			JOptionPane.showMessageDialog(null, msg + e.getMessage());
		} catch (Exception e) {
			error(e);
			String msg = "Internal Server Error (500)\nCause: ";
			JOptionPane.showMessageDialog(null, msg + e.getMessage());
		}
	}

	private static void loginUser() {
		String username = JOptionPane.showInputDialog("Enter your Username", "Type Here");
		String password = JOptionPane.showInputDialog("Enter your Password", "Type Here");

		try {
			User user = USER_SERVICE.login(username, password);
			String msg = "Welcome User " + user.getUsername() + " (" + user.getEmailAddress() + ")";
			JOptionPane.showMessageDialog(null, msg);

			courseLogic(user);
		} catch (WrongCredentialException e) {
			error(e);
			String msg = "Internal Server Error (500)\nCause: ";
			JOptionPane.showMessageDialog(null, msg + e.getMessage());
		} catch (Exception e) {
			error(e);
			String msg = "Internal Server Error (500)\nCause: ";
			JOptionPane.showMessageDialog(null, msg + e.getMessage());
		}
	}

	private static void registerUser() {
		try {
			User user = new User();

			user.setUsername(JOptionPane.showInputDialog("Enter your Username", "Type Here"));
			user.setPassword(JOptionPane.showInputDialog("Enter your Password", "Type Here"));
			user.setEmailAddress(JOptionPane.showInputDialog("Enter your Email", "Type Here"));

			String msg = USER_SERVICE.register(user);
			JOptionPane.showMessageDialog(null, msg);
		} catch (PersistenceException e) {
			error(e);
			String msg = "Internal Server Error (500)\nCause: Username or Email already exists";
			JOptionPane.showMessageDialog(null, msg);
		} catch (Exception e) {
			error(e);
			String msg = "Internal Server Error (500)\nCause: ";
			JOptionPane.showMessageDialog(null, msg + e.getMessage());
		}
	}

	private static void businessLogic() {
		while (true) {
			String options = "Login: L\n" + "Register: R\n" + "Exit: E or Q\n";
			String choice = JOptionPane.showInputDialog(options, "Type your choice");
			switch (choice.toUpperCase()) {
			case "LOGIN", "L":
				loginUser();
				break;

			case "REGISTER", "R":
				registerUser();
				break;

			case "QUIT", "EXIT", "Q", "E":
				System.exit(0);

			default:
				JOptionPane.showMessageDialog(null, "Please enter the right choice");
			}
		}
	}

	private static void courseLogic(User user) {
		while (true) {
			String options = "Add Course: A\n" + "Delete Course: D\n" + "Show All Courses: S\n" + "Exit: E or Q\n";
			String choice = JOptionPane.showInputDialog(options, "Type your choice");
			switch (choice.toUpperCase()) {
			case "ADD", "A":
				createCourse(user);
				break;

			case "SHOW", "S":
				getAllCourses(user);
				break;

			case "DELETE", "D":
				deleteCourse(user);
				break;

			case "QUIT", "EXIT", "Q", "E":
				businessLogic();

			default:
				JOptionPane.showMessageDialog(null, "Please enter the right choice");
			}
		}
	}

	public static void main(String[] args) {
		businessLogic();
//		debug();
	}

	public static void debug() {
		User user = User.builder().username("a").password("a").emailAddress("a@email").build();
		USER_SERVICE.register(user);

		Course course = Course.builder().courseId(12L).title("JAVA").price(1200L).published(false).user(user).build();
		COURSE_SERVICE.createCourse(course);

		try {
			System.out.println(COURSE_SERVICE.getAllCourses().get(0).getCourseId());
			System.out.println(COURSE_SERVICE.getAllCourses().get(0).getTitle());
			System.out.println(COURSE_SERVICE.getAllCourses().get(0).getPrice());
		} catch (NoCourseFoundException e) {
			LOG.error(e.getMessage());
		}

		try {
			System.out.println("---DELETE---" + COURSE_SERVICE.deleteCourse(1L));
		} catch (CourseNotFoundException e1) {
		}

		try {
			System.out.println(COURSE_SERVICE.getAllCourses().get(0).getCourseId());
			System.out.println(COURSE_SERVICE.getAllCourses().get(0).getTitle());
			System.out.println(COURSE_SERVICE.getAllCourses().get(0).getPrice());
		} catch (NoCourseFoundException e) {
			LOG.error(e.getMessage());
		}
	}

}
