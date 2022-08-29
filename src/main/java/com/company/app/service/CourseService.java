package com.company.app.service;

import java.util.List;

import com.company.app.entity.Course;
import com.company.app.exception.CourseNotFoundException;
import com.company.app.exception.NoCourseFoundException;

public interface CourseService {

	public List<Course> getAllCourses() throws NoCourseFoundException;

	public String createCourse(Course course);

	public String deleteCourse(Long courseId) throws CourseNotFoundException;

}
