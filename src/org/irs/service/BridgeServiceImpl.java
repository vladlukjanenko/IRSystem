package org.irs.service;

import org.hibernate.SessionFactory;
import org.irs.entities.Bridge;
import org.irs.entities.Bridge2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class BridgeServiceImpl implements BridgeService {

	@Autowired
	private SessionFactory sessionFactory; // hibernate session object
											// to get connection to database
	
	@Override
	@Transactional
	public void insertPublicationToStudent(Bridge bridge) {
		sessionFactory.getCurrentSession().save(bridge);
	}

	@Override
	@Transactional
	public void insertPublicationToTeacher(Bridge2 bridge) {
		sessionFactory.getCurrentSession().save(bridge);
	}

	@Override
	@Transactional
	public void deletePublication(Long publicationId) {
		sessionFactory.getCurrentSession()
				.getNamedQuery("Bridge.deletePublication")
				.setParameter("id", publicationId).executeUpdate();
	}

}
