package com.company.app.daoimpl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.app.configuration.HibernateConfiguration;
import com.company.app.dao.UserDao;
import com.company.app.entity.User;
import com.company.app.exception.WrongCredentialException;

public class UserDaoImpl implements UserDao {

	private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);

	@Override
	public String register(User user) {
		Session session = HibernateConfiguration.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		String result = (String) session.save(user);
		transaction.commit();
		session.close();
		LOG.info("----- Registration result is: " + result + " -----");

		return result + ", your registration is successful";
	}

	@Override
	public User login(String username, String password) throws WrongCredentialException {
		try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
			User user = null;
			String HQL = "from User u where u.username=:uname and u.password=:passw";
			Query<?> query = session.createQuery(HQL);
			query.setParameter("uname", username);
			query.setParameter("passw", password);
			query.setMaxResults(1);
			user = (User) query.uniqueResult();

			if (user == null)
				throw new WrongCredentialException("Wrong Username or Password");
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
