package com.company.app.daoimpl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.company.app.configuration.HibernateConfiguration;
import com.company.app.dao.UserDao;
import com.company.app.entity.User;

public class UserDaoImpl implements UserDao {

	@Override
	public String register(User user) {
		Session session = HibernateConfiguration.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		String result = (String) session.save(user);
		transaction.commit();
		session.close();
		System.out.println("----- Result is: " + result + " -----");

		return "Something";
	}

	@Override
	public User login(String username, String password) {
		try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
			User user = null;
			String HQL = "from User u where u.username=:uname and u.password=:passw";
			Query<?> query = session.createQuery(HQL);
			query.setParameter("uname", username);
			query.setParameter("passw", password);
			query.setMaxResults(1);
			user = (User) query.uniqueResult();
			return user;
		}
	}

//	@Override
//	public User login(String username) {
//		Session session = HibernateConfiguration.getSessionFactory().openSession();
//		User user = session.get(User.class, username);
//		return user;
//	}

}
