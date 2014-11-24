package org.irs.service;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.SessionFactory;
import org.irs.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SessionFactory sessionFactory; // hibernate session object
											// to get connection to database

	@Override
	@Transactional
	public List<Subject> selectAllSubjects() {
		return sessionFactory.getCurrentSession(). // select all subjects
				getNamedQuery("Subject.selectAllSubjects").list();
	}

	@Override
	@Transactional
	public Subject selectSubjectByName(String name) {
		return (Subject) sessionFactory.getCurrentSession()
				.getNamedQuery("Subject.selectSubjectByName"). // select subject
				setParameter("name", name). // by it's name
				uniqueResult();
	}

	@Override
	@Transactional
	public Subject insertSubject(Subject subject) throws SQLException {
		sessionFactory.getCurrentSession().save(subject); // insert new subject
		return subject;
	}

	@Override
	@Transactional
	public Subject updateSubject(Subject subject) {
		sessionFactory.getCurrentSession().update(subject); // update existing
															// subject
		return subject;
	}

	@Override
	@Transactional
	public Subject deleteSubject(Subject subject) {
		sessionFactory.getCurrentSession().delete(subject); // delete subject
		return subject;
	}

	@Override
	@Transactional
	public void deleteSubjectById(Long subjectId) {
		sessionFactory.getCurrentSession()
				.getNamedQuery("Subject.deleteSubjectById")
				.setParameter("id", subjectId).executeUpdate();
	}

	@Override
	@Transactional
	public Subject selectSubjectById(Long name) {
		return (Subject) sessionFactory.getCurrentSession()
				. // select all subjects
				getNamedQuery("Subject.selectSubjectById")
				.setParameter("id", name).uniqueResult();
	}

	@Override
	@Transactional
	public Subject selectSubjectByIdType(Long id, Integer type) {
		return (Subject) sessionFactory.getCurrentSession()
				.getNamedQuery("Subject.selectSubjectByIdType")
				.setParameter("id", id).setParameter("type", type)
				.uniqueResult();
	}
}
