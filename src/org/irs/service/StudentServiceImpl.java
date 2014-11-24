package org.irs.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.irs.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class StudentServiceImpl implements StudentService {

	@Autowired
	private SessionFactory sessionFactory; // hibernate session object
										   // to get connection to database
	
	/*
	 * Select all student from database
	 * */
	@Override
	@Transactional(readOnly=true)
	public List<Student> selectAllStudents() {
		return sessionFactory.getCurrentSession(). // select all students
							  getNamedQuery("Student.selectAll").list(); // and pass it to List
	}

	@Override
	@Transactional
	public Student selectStudentByName(String name) {
		return (Student) sessionFactory.getCurrentSession(). // select student
				  getNamedQuery("Student.selectStudentByName"). // by name
				  setParameter("name", name).uniqueResult();
	}
	
	/*
	 * Insert student to database table
	 * */
	@Override
	@Transactional
	public Student insertStudent(Student student) {
		sessionFactory.getCurrentSession().save(student); // save student
		return student;
	}

	/*
	 * Delete student from database
	 * */
	@Override
	@Transactional
	public void deleteStudent(Long studentId) {
		sessionFactory.getCurrentSession()
				.getNamedQuery("Student.deleteStudentById")
				.setParameter("id", studentId).executeUpdate(); // update student
	}

	@Override
	@Transactional
	public Student updateStudent(Student student) {
		sessionFactory.getCurrentSession().update(student); // delete student
		return student;
	}

	@Override
	@Transactional
	public List<String> selectAllNames() {
		return sessionFactory.getCurrentSession()
					  .getNamedQuery("Student.selectAllNames").list();
		
	}

	@Override
	@Transactional
	public Student selectStudentById(Long id) {
		return (Student) sessionFactory.getCurrentSession()
				             .getNamedQuery("Student.selectStudentById")
				             .setParameter("id", id).uniqueResult();
	}

	@Override
	@Transactional
	public List<Student> selectStudentByYear(Integer name) {
		return sessionFactory.getCurrentSession()
				.getNamedQuery("Student.selectStudentByYear")
				.setParameter("enter", name).list();
	}

	@Override
	@Transactional
	public List<Student> selectStudentByGroup(String name) {
		return sessionFactory.getCurrentSession()
				.getNamedQuery("Student.selectStudentByGroup")
				.setParameter("group", name).list();
	}

}
