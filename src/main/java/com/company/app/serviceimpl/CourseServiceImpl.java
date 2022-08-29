package com.company.app.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.app.dao.CourseDao;
import com.company.app.daoimpl.CourseDaoImpl;
import com.company.app.entity.Course;
import com.company.app.service.CourseService;

public class CourseServiceImpl implements CourseService {

	private static final Logger LOG = LoggerFactory.getLogger(CourseServiceImpl.class);
	private static final CourseDao DAO = new CourseDaoImpl();

	@Override
	public String createCourse(Course course) {
		return DAO.createCourse(course);
	}

}
