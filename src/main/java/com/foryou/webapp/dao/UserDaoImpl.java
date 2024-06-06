package com.foryou.webapp.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foryou.webapp.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDaoImpl implements UserDao {

	private EntityManager entityManager;

	public UserDaoImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public User findByUserName(String theUserName) {
		// retrieve/read from database using username
		TypedQuery<User> theQuery = entityManager.createQuery("from User where userName=:uName and enabled=true",
				User.class);
		theQuery.setParameter("uName", theUserName);

		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	@Transactional
	public void save(User theUser) {
		// create the user ... finally LOL
		entityManager.merge(theUser);
	}

	@Override
	public User findByFirstName(String firstName) {
		return null;
	}

}
