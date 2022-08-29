package com.company.app.serviceimpl;

import java.util.List;

import com.company.app.dao.CourseDao;
import com.company.app.daoimpl.CourseDaoImpl;
import com.company.app.entity.Course;
import com.company.app.exception.CourseNotFoundException;
import com.company.app.exception.NoCourseFoundException;
import com.company.app.service.CourseService;

public class CourseServiceImpl implements CourseService {

	private static final CourseDao DAO = new CourseDaoImpl();

	@Override
	public List<Course> getAllCourses() throws NoCourseFoundException {
		return DAO.getAllCourses();
	}

	@Override
	public String createCourse(Course course) {
		return DAO.createCourse(course);
	}

	@Override
	public String deleteCourse(Long courseId) throws CourseNotFoundException {
		return DAO.deleteCourse(courseId);
	}

	@Override
	public List<Course> getCourseByPriceRange(Long low, Long high) throws NoCourseFoundException {
		return DAO.getCourseByPriceRange(low, high);
	}

}
