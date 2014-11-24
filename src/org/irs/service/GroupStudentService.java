package org.irs.service;

import org.irs.entities.GroupStudent;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Transactional
public interface GroupStudentService {
	
	@Transactional
	public List<GroupStudent> selectAllGroups();
	
	@Transactional
	public GroupStudent getGroupByName(String name);
	
	@Transactional
	public GroupStudent getGroupById(Long id);
	
	@Transactional
	public GroupStudent insertGroup(GroupStudent groupStudent) throws SQLException;
	
	@Transactional
	public GroupStudent deleteGroup(GroupStudent groupStudent);
	
	@Transactional
	public void deleteGroupById(Long groupId);
	
}
