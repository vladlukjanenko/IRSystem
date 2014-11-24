package org.irs.entities;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;


@Entity
@Table(name = "GroupStudent")
@NamedQueries({ 
	@NamedQuery(name = "GroupStudent.getAllGroups", // get all groups
			    query = "select g from GroupStudent g"),
	@NamedQuery(name = "GroupStudent.getGroupByName", // get group by name
	            query = "select g from GroupStudent g where g.groupStudentNumber = :name"),
	@NamedQuery(name = "GroupStudent.getGroupById", // get group by id
	            query = "select g from GroupStudent g where g.groupStudentId = :id"),
	@NamedQuery(name =  "GroupStudent.deleteGroupById",
				query = "delete from GroupStudent where groupStudentId = :id")
})
public class GroupStudent implements Serializable {
	public GroupStudent() {}
	
	public GroupStudent(String groupStudentNumber) {
		this.groupStudentNumber = groupStudentNumber;
	}
	
	// create connectivity with table Student
	private List<Student> students;
	
	@OneToMany(mappedBy = "groupStudent", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Student> getStudents() {
		return this.students;
	}	
	
	public void setStudents(List<Student> students) {
		this.students = students;
	}	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_id_seq")
	@SequenceGenerator(name = "group_id_seq", sequenceName = "GroupStudent_seq", allocationSize = 1)
	@Column(name = "GroupStudentId")
	public Long getGroupStudentId() {
		return this.groupStudentId;
	}
	
	public void setGroupStudentId(Long groupStudentId) {
		this.groupStudentId = groupStudentId;
	}
	
	@Column(name = "GroupStudentNumber")
	public String getGroupStudentNumber() {
		return this.groupStudentNumber;
	}
	
	public void setGroupStudentNumber(String groupStudentNumber) {
		this.groupStudentNumber = groupStudentNumber;
	}
	
	@Override
	public boolean equals(Object arg) {
		GroupStudent g = (GroupStudent) arg;
		
		// check if we have two object with one GroupStudentNumber
		if(this.getGroupStudentNumber().equals(g.getGroupStudentNumber())) 
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.groupStudentId.hashCode();
	}
	
	// table GroupStudent fields
	private Long groupStudentId;
	private String groupStudentNumber;
}
