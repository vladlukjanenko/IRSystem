package org.irs.service;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.SessionFactory;
import org.irs.entities.GroupStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class GroupStudentServiceImpl implements GroupStudentService {

	@Autowired
	private SessionFactory sessionFactory; // hibernate session object
										   // to get connection to database
	
	/*
	 * Select all groups from database
	 * */
	@Override
	@Transactional(readOnly = true)
	public List<GroupStudent> selectAllGroups() {
		return sessionFactory.getCurrentSession(). // select all groups and 
			   getNamedQuery("GroupStudent.getAllGroups").list(); // pass it to List object
	}
	
	/*
	 * Select group object using group name
	 * */
	@Override
	@Transactional
	public GroupStudent getGroupByName(String name) {
		return (GroupStudent) sessionFactory.getCurrentSession(). // select group object
							  getNamedQuery("GroupStudent.getGroupByName"). // using named query from
							  setParameter("name", name).uniqueResult(); // entity GroupStudent
	}

	@Override
	@Transactional
	public GroupStudent insertGroup(GroupStudent groupStudent) throws SQLException {
		sessionFactory.getCurrentSession().save(groupStudent);
		return groupStudent;
	}

	@Override
	@Transactional
	public GroupStudent deleteGroup(GroupStudent groupStudent) {
		sessionFactory.getCurrentSession().delete(groupStudent);
		return groupStudent;
	}

	@Override
	@Transactional
	public void deleteGroupById(Long groupId) {
		sessionFactory.getCurrentSession(). 
		  getNamedQuery("GroupStudent.deleteGroupById"). 
		  setParameter("id", groupId).executeUpdate();
	}

	@Override
	@Transactional
	public GroupStudent getGroupById(Long id) {
		return (GroupStudent) sessionFactory.getCurrentSession()
				.getNamedQuery("GroupStudent.getGroupById")
				.setParameter("id", id).uniqueResult();
	}

}
