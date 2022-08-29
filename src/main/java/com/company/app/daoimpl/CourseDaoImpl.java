package com.company.app.daoimpl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.app.configuration.HibernateConfiguration;
import com.company.app.dao.CourseDao;
import com.company.app.entity.Course;

public class CourseDaoImpl implements CourseDao {

	private static final Logger LOG = LoggerFactory.getLogger(CourseDaoImpl.class);

	@Override
	public String createCourse(Course course) {
		Session session = HibernateConfiguration.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.save(course);
		Long result = (Long) session.save(course);
		transaction.commit();
		session.close();
		System.out.println("----- Create-Course result is: " + result + " -----");

		return "Something";
	}

}
