package org.irs.service;

import java.sql.SQLException;
import java.util.List;

import org.irs.entities.Subject;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SubjectService {
	
	@Transactional
	public List<Subject> selectAllSubjects();
	
	@Transactional
	public Subject selectSubjectByName(String name);
	
	@Transactional
	public Subject selectSubjectById(Long name);
	
	@Transactional
	public Subject selectSubjectByIdType(Long id, Integer type);
	
	@Transactional
	public Subject insertSubject(Subject subject) throws SQLException;
	
	@Transactional
	public Subject updateSubject(Subject subject);
	
	@Transactional
	public Subject deleteSubject(Subject subject);
	
	@Transactional
	public void deleteSubjectById(Long subjectId);
}
