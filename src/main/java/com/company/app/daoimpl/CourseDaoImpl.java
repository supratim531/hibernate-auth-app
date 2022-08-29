package com.company.app.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.app.configuration.HibernateConfiguration;
import com.company.app.dao.CourseDao;
import com.company.app.entity.Course;
import com.company.app.exception.CourseNotFoundException;
import com.company.app.exception.NoCourseFoundException;

@SuppressWarnings("unchecked")
public class CourseDaoImpl implements CourseDao {

	private static final Logger LOG = LoggerFactory.getLogger(CourseDaoImpl.class);

	public Course getCourseById(Long courseId) throws CourseNotFoundException {
		try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
			Course course = null;
			String HQL = "from Course c where c.courseId=:id";
			Query<?> query = session.createQuery(HQL);
			query.setParameter("id", courseId);
			query.setMaxResults(1);
			course = (Course) query.uniqueResult();

			if (course == null)
				throw new CourseNotFoundException("No Course found with id: " + courseId);
			return course;
		}
	}

	@Override
	public List<Course> getAllCourses() throws NoCourseFoundException {
		try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
			List<Course> courses = null;
			String HQL = "from Course";
			Query<?> query = session.createQuery(HQL);
			courses = (List<Course>) query.list();

			if (courses.size() <= 0)
				throw new NoCourseFoundException("No course exists");
			return courses;
		}
	}

	@Override
	public String createCourse(Course course) {
		Session session = HibernateConfiguration.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.save(course);
		Long result = (Long) session.save(course);
		transaction.commit();
		session.close();
		LOG.info("----- Create-Course result is: " + result + " -----");

		return "Course " + course.getTitle() + " added successfully";
	}

	@Override
	public String deleteCourse(Long courseId) throws CourseNotFoundException {
		try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
			Course course = this.getCourseById(courseId);
			Transaction transaction = session.beginTransaction();
			String HQL = "delete from Course c where c.courseId=:id";
			Query<?> query = session.createQuery(HQL);
			query.setParameter("id", courseId);
			query.executeUpdate();
			transaction.commit();
			LOG.info("Course (" + course.getTitle() + ") with id " + courseId + " deleted successfully");
			return "Course (" + course.getTitle() + ") with id " + courseId + " deleted successfully";
		}
	}

	@Override
	public List<Course> getCourseByPriceRange(Long low, Long high) throws NoCourseFoundException {
		try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
			List<Course> courses = null;
			String HQL = "from Course c where c.price between :low and :high";
			Query<?> query = session.createQuery(HQL);
			query.setParameter("low", low);
			query.setParameter("high", high);
			courses = (List<Course>) query.list();

			if (courses.size() <= 0)
				throw new NoCourseFoundException("No course exists in price range " + low + " to " + high);
			return courses;
		}
	}

}
