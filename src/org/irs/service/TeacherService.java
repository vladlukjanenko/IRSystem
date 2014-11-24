package org.irs.service;

import org.irs.entities.Teacher;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

@Transactional
public interface TeacherService {
	
	@Transactional
	public List<Teacher> selectAllTeachers();
	
	@Transactional
	public Teacher selectTeacherByName(String name);
	
	@Transactional
	public Teacher selectTeacherByStatus(String status);
	
	@Transactional
	public List<Teacher> selectTeacherByPublication(Long publicationId);
	
	@Transactional
	public Teacher insertTeacher(Teacher teacher) throws SQLException;
	
	@Transactional
	public Teacher updateTeacher(Teacher teacher);
	
	@Transactional
	public Teacher deleteTeacher(Teacher teacher);
	
	@Transactional
	public void deleteTeacherById(Long teacherId);
	
}
