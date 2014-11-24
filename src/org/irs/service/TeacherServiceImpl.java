package org.irs.service;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.SessionFactory;
import org.irs.entities.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private SessionFactory sessionFactory; // hibernate session object
										   // to get conncetion to database
	
	@Override
	@Transactional
	public List<Teacher> selectAllTeachers() {
		return sessionFactory.getCurrentSession(). // select all teachers
				              getNamedQuery("Teacher.selectAllTeachers").list();
	}

	@Override
	@Transactional
	public Teacher selectTeacherByName(String name) {
		return (Teacher) sessionFactory.getCurrentSession(). // select teacher
							  			getNamedQuery("Teacher.selectTeacherByName"). // by it's name
							  			setParameter("name", name).uniqueResult();
	}

	@Override
	@Transactional
	public Teacher selectTeacherByStatus(String status) {
		return (Teacher) sessionFactory.getCurrentSession(). // select teacher
									    getNamedQuery("Teacher.selectTeacherByStatus"). // by it's status
									    setParameter("status", status).uniqueResult();
	}

	@Override
	@Transactional
	public Teacher insertTeacher(Teacher teacher) {
		sessionFactory.getCurrentSession().save(teacher); // insert new teacher
		return teacher;
	}

	@Override
	@Transactional
	public Teacher updateTeacher(Teacher teacher) {
		sessionFactory.getCurrentSession().update(teacher); // update existing teacher
		return teacher;
	}

	@Override
	@Transactional
	public Teacher deleteTeacher(Teacher teacher) {
		sessionFactory.getCurrentSession().delete(teacher); // delete student
		return teacher;
	}

	@Override
	@Transactional
	public List<Teacher> selectTeacherByPublication(Long publicationId) {
		return sessionFactory.getCurrentSession(). // select teachers
			    getNamedQuery("Teacher.selectTeacherByPublication").
			    setParameter("id", publicationId).list();
	}

	@Override
	@Transactional
	public void deleteTeacherById(Long teacherId) {
		sessionFactory.getCurrentSession()
				.getNamedQuery("Teacher.deleteTeacherById")
				.setParameter("id", teacherId).executeUpdate();		
	}

}
