package com.company.app.configuration;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.company.app.entity.User;

public class HibernateConfiguration {

	private static SessionFactory factory;

	public static SessionFactory getSessionFactory() {
		if (factory == null) {
			try {
				Configuration configuration = new Configuration();

				Properties properties = new Properties();
				properties.put(Environment.USER, "sa");
				properties.put(Environment.PASS, "sa");
				properties.put(Environment.DRIVER, "org.h2.Driver");
//				properties.put(Environment.URL, "jdbc:h2:mem:supratim");
				properties.put(Environment.URL, "jdbc:h2:file:E:/STS-APR/DB/H2/supratim");
				properties.put(Environment.SHOW_SQL, "true");
				properties.put(Environment.HBM2DDL_AUTO, "update");
				properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				properties.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");

				configuration.setProperties(properties);
				configuration.addAnnotatedClass(User.class);
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				factory = configuration.buildSessionFactory(serviceRegistry);
				return factory;
			} catch (HibernateException e) {
				System.out.println("----- " + e + " -----");
			}
		}
		return factory;
	}

}
