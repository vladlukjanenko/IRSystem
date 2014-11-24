package org.irs.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.irs.entities.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class PublicationServiceImpl implements PublicationService {

	@Autowired
	private SessionFactory sessionFactory; // hibernate session object
										   // to get conncetion to database
	
	@Override
	@Transactional
	public List<Publication> selectAllPublications() {
		return sessionFactory.getCurrentSession(). // select all publications
							  getNamedQuery("Publication.getAllPublication").list();
	}

	@Override
	@Transactional
	public List<Publication> selectPublicationsByTypeYear(String type, Integer year) {
		return sessionFactory.getCurrentSession() // select publication
				.getNamedQuery("Publication.getAllPublicationByTypeYear")
				.setParameter("type", type).setParameter("year", year).list();
	}

	@Override
	@Transactional
	public List<Publication> selectPublicationsByDate(Date date) {
		return sessionFactory.getCurrentSession()  // select publication
				.getNamedQuery("Publication.getAllPublicationByDate")
				.setParameter("date", date).list(); // by date
	}

	@Override
	@Transactional
	public Publication selectPublicationsByPlace(String place) {
		return (Publication) sessionFactory.getCurrentSession(). // select publication
				  getNamedQuery("Publication.getAllPublicationByPlace").list(); // by place
	}

	@Override
	@Transactional
	public Publication insertPublication(Publication publication) {
		sessionFactory.getCurrentSession().save(publication); // insert new publication
		return publication;
	}

	@Override
	@Transactional
	public Publication updatePublication(Publication publication) { 
		sessionFactory.getCurrentSession().update(publication); // update exsinting publication
		return publication;
	}

	@Override
	@Transactional
	public void deletePublication(Publication p) {
		sessionFactory.getCurrentSession().delete(p);		// delete publication
	}
	
	@Override
	@Transactional
	public void deletePublicationById(Long id) {
		sessionFactory.getCurrentSession().		// delete publication
        getNamedQuery("Publication.deletePublicationById").
        setParameter("id", id).executeUpdate();	
	}

	@Override
	@Transactional
	public List<Publication> selectPublicationsByStudent(Long id) {
		return sessionFactory.getCurrentSession().
	              getNamedQuery("Publication.getAllPublicationByStudent").
	              setParameter("id", id).list();				
	}

	@Override
	@Transactional
	public Publication selectPublicationsById(Long id) {
		return (Publication) sessionFactory.getCurrentSession().
	              getNamedQuery("Publication.getAllPublicationById").
	              setParameter("id", id).uniqueResult();		
	}

	@Override
	@Transactional
	public List<Publication> selectPublicationsByTypeThesis(String type,
			Integer year, Integer thesis) {
		return sessionFactory.getCurrentSession() // select publication
				.getNamedQuery("Publication.getAllPublicationByTypeThesis")
				.setParameter("type", type).setParameter("year", year)
				.setParameter("thesis", thesis).list();
	}

	@Override
	@Transactional
	public List<Publication> selectPublicationsNull() {
		return sessionFactory.getCurrentSession() // select publication
				.getNamedQuery("Publication.getAllPublicationsNull").list();
	}

}
