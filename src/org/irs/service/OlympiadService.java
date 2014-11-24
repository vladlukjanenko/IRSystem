package org.irs.service;

import org.irs.entities.Olympiad;
import org.irs.entities.Student;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

@Transactional
public interface OlympiadService {
	
	@Transactional
	public List<Olympiad> selectAllOlympiads();
	
	@Transactional
	public List<Olympiad> selectOlympiadsByYear(int year, String type);
	
	@Transactional
	public List<Olympiad> selectOlympiadsByStudent(Long id);
	
	@Transactional
	public Olympiad insertOlympiad(Olympiad olympiad) throws SQLException;
	
	@Transactional
	public Olympiad updateOlympiad(Olympiad olympiad);

	@Transactional
	public Olympiad deleteOlympiad(Olympiad olympiad);
	
	@Transactional
	public void deleteOlympiadById(Long id);
	
}
