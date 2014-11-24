package org.irs.service;

import org.irs.entities.Publication;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

@Transactional
public interface PublicationService {
	
	@Transactional
	public List<Publication> selectAllPublications();
	
	@Transactional
	public List<Publication> selectPublicationsByTypeYear(String type, Integer year);
	
	@Transactional
	public List<Publication> selectPublicationsByTypeThesis(String type, Integer year, Integer thesis);
	
	@Transactional
	public void deletePublicationById(Long id);
	@Transactional
	public void deletePublication(Publication p);
	
	@Transactional
	public List<Publication> selectPublicationsByDate(Date date);
	
	@Transactional
	public Publication selectPublicationsById(Long id);
	
	@Transactional
	public List<Publication> selectPublicationsByStudent(Long id);
	
	@Transactional
	public List<Publication> selectPublicationsNull();

	@Transactional
	public Publication selectPublicationsByPlace(String place);
	
	@Transactional
	public Publication insertPublication(Publication publication);
	
	@Transactional
	public Publication updatePublication(Publication publication);
	
}
