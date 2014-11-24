package org.irs.service;

import org.irs.entities.Progress;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public interface ProgressService {
	
	@Transactional
	public List<Progress> selectAllProgress();
	
	@Transactional
	public List<Progress> selectProgressYear(Integer year, Long id, Integer type);
	
	@Transactional
	public List<Progress> selectProgressGroup(Long group, Long id, Integer type);
	
	@Transactional
	public List<Progress> selectAllProgressByStudent(Long id);
	
	@Transactional
	public Progress insertProgress(Progress progress);
	
	@Transactional
	public Progress updateProgress(Progress progress);
	
	@Transactional
	public Progress deleteProgress(Progress progress);
	
	@Transactional
	public void deleteProgressById(Long id);
}
