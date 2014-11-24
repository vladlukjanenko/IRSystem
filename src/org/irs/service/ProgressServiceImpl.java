package org.irs.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.irs.entities.Progress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class ProgressServiceImpl implements ProgressService {

	@Autowired
	private SessionFactory sessionFactory; // hibernate session object
											// to get connection to database

	@Override
	@Transactional
	public List<Progress> selectAllProgress() {
		return sessionFactory.getCurrentSession(). // select all progress
				getNamedQuery("Progress.selectAllProgress").list();
	}

	@Override
	@Transactional
	public Progress insertProgress(Progress progress) {
		sessionFactory.getCurrentSession().save(progress); // insert new
															// progress
		return progress;
	}

	@Override
	@Transactional
	public Progress updateProgress(Progress progress) {
		sessionFactory.getCurrentSession().update(progress); // update existing
																// progress
		return progress;
	}

	@Override
	@Transactional
	public Progress deleteProgress(Progress progress) {
		sessionFactory.getCurrentSession().delete(progress); // delete progress
		return progress;
	}

	@Override
	@Transactional
	public List<Progress> selectAllProgressByStudent(Long id) {
		return sessionFactory.getCurrentSession()
				. // select all progress
				getNamedQuery("Progress.selectAllProgressByStudent")
				.setParameter("id", id).list();
	}

	@Override
	@Transactional
	public void deleteProgressById(Long id) {
		sessionFactory.getCurrentSession()
				.getNamedQuery("Progress.deleteProgressById")
				.setParameter("id", id).executeUpdate();

	}

	@Override
	@Transactional
	public List<Progress> selectProgressYear(Integer year, Long id, Integer type) {
		return sessionFactory.getCurrentSession()
				.getNamedQuery("Progress.selectProgressYear")
				.setParameter("year", year).setParameter("id", id)
				.setParameter("type", type).list();
	}

	@Override
	@Transactional
	public List<Progress> selectProgressGroup(Long group, Long id, Integer type) {
		return sessionFactory.getCurrentSession()
				.getNamedQuery("Progress.selectProgressGroup")
				.setParameter("group", group).setParameter("id", id)
				.setParameter("type", type).list();
	}

}
