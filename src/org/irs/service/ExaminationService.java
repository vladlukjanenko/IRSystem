package org.irs.service;

import org.irs.entities.Examination;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface ExaminationService {
	
	@Transactional
	public List<Examination> selectAllExams();
	
	@Transactional
	public List<Examination> selectAllExamsByStudent(Long id);
	
	@Transactional
	public Examination insertExam(Examination exam);
	
	@Transactional
	public Examination updateExam(Examination exam);
	
	@Transactional
	public Examination deleteExam(Examination exam);
	
	@Transactional
	public void deleteExamById(Long id);
	
}
