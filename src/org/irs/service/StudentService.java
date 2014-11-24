package org.irs.service;

import java.util.List;

import org.irs.entities.Student;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface StudentService {
	
	@Transactional
	public List<Student> selectAllStudents(); 
	
	@Transactional
	public Student selectStudentByName(String name);
	
	@Transactional
	public List<Student> selectStudentByYear(Integer name);
	
	@Transactional
	public List<Student> selectStudentByGroup(String name);
	
	@Transactional
	public Student selectStudentById(Long id);
	
	@Transactional
	public Student insertStudent(Student student);
	
	@Transactional
	public Student updateStudent(Student student);
	
	@Transactional
	public void deleteStudent(Long studentId);
	
	@Transactional
	public List<String> selectAllNames();
}
