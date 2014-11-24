package org.irs.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.irs.entities.Examination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class ExaminationServiceImpl implements ExaminationService {

	@Autowired
	private SessionFactory sessionFactory; // hibernate session object
											// to get connection to database

	@Override
	@Transactional
	public List<Examination> selectAllExams() {
		return sessionFactory.getCurrentSession(). // select all exams
				getNamedQuery("Examination.selectAllExams").list();
	}

	@Override
	@Transactional
	public Examination insertExam(Examination exam) {
		sessionFactory.getCurrentSession().save(exam); // create new exam
		return exam;
	}

	@Override
	@Transactional
	public Examination updateExam(Examination exam) {
		sessionFactory.getCurrentSession().update(exam); // update existing exam
		return exam;
	}

	@Override
	@Transactional
	public Examination deleteExam(Examination exam) {
		sessionFactory.getCurrentSession().delete(exam); // delete exam
		return exam;
	}

	@Override
	@Transactional
	public List<Examination> selectAllExamsByStudent(Long id) {
		return sessionFactory.getCurrentSession()
				.getNamedQuery("Examination.selectAllExamsByStudent")
				.setParameter("id", id).list();
	}

	@Override
	@Transactional
	public void deleteExamById(Long id) {
		sessionFactory.getCurrentSession()
				.getNamedQuery("Examination.deleteExaminationById")
				.setParameter("id", id).executeUpdate();
	}

}
