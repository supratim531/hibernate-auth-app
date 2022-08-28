package com.company.app;

import java.util.Date;

import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;

import com.company.app.entity.Course;
import com.company.app.entity.User;
import com.company.app.exception.WrongCredentialException;
import com.company.app.service.CourseService;
import com.company.app.service.UserService;
import com.company.app.serviceimpl.CourseServiceImpl;
import com.company.app.serviceimpl.UserServiceImpl;

public class App {

	private static final UserService USER_SERVICE = new UserServiceImpl();
	private static final CourseService COURSE_SERVICE = new CourseServiceImpl();

	private static void error(Throwable e) {
		System.out.println("----- " + e + " [" + new Date() + "] -----");
	}

	private static void loginUser() {
		String username = JOptionPane.showInputDialog("Enter your Username", "Type Here");
		String password = JOptionPane.showInputDialog("Enter your Password", "Type Here");

		try {
			User user = USER_SERVICE.login(username, password);
			String msg = "Welcome User " + user.getUsername() + " (" + user.getEmailAddress() + ")";
			JOptionPane.showMessageDialog(null, msg);
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

	public static void main(String[] args) {
//		businessLogic();
		debug();
	}

	public static void debug() {
		User user = User.builder().username("a").password("a").emailAddress("a@email").build();
		USER_SERVICE.register(user);

		Course course = Course.builder().courseId(12L).title("JAVA").price(1200L).published(false).build();
		COURSE_SERVICE.createCourse(course);
	}

}
