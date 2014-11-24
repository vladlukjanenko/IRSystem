package org.irs.service;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.SessionFactory;
import org.irs.entities.Olympiad;
import org.irs.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class OlympiadServiceImpl implements OlympiadService {

	@Autowired
	private SessionFactory sessionFactory; // hibernate session object
											// to get conncetion to database

	@Override
	@Transactional
	public List<Olympiad> selectAllOlympiads() {
		return sessionFactory.getCurrentSession()
				.getNamedQuery("Olympiad.getAllOlympiads").list();
	}

	@Override
	@Transactional
	public List<Olympiad> selectOlympiadsByYear(int year, String type) {
		return sessionFactory.getCurrentSession()
				.getNamedQuery("Olympiad.getOlympiadsByYear")
				.setParameter("year", year).setParameter("type", type).list();
	}

	@Override
	@Transactional
	public List<Olympiad> selectOlympiadsByStudent(Long id) {
		return sessionFactory.getCurrentSession()
				.getNamedQuery("Olympiad.getOlympiadsByStudent")
				.setParameter("id", id).list();
	}

	@Override
	@Transactional
	public Olympiad insertOlympiad(Olympiad olympiad) throws SQLException {
		sessionFactory.getCurrentSession().save(olympiad); // save olympiad
		return olympiad;
	}

	@Override
	@Transactional
	public Olympiad updateOlympiad(Olympiad olympiad) {
		sessionFactory.getCurrentSession().update(olympiad);
		return olympiad;
	}

	@Override
	@Transactional
	public Olympiad deleteOlympiad(Olympiad olympiad) {
		sessionFactory.getCurrentSession().delete(olympiad);
		return olympiad;
	}

	@Override
	@Transactional
	public void deleteOlympiadById(Long id) {
		sessionFactory.getCurrentSession()
				.getNamedQuery("Olympiad.deleteOlympiadById")
				.setParameter("id", id).executeUpdate();

	}

}
