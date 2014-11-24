package org.irs.service;

import org.irs.entities.Bridge;
import org.irs.entities.Bridge2;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BridgeService {
	
	@Transactional
	public void insertPublicationToStudent(Bridge bridge);
	
	@Transactional
	public void deletePublication(Long publicationId);
	
	@Transactional
	public void insertPublicationToTeacher(Bridge2 bridge);
}
