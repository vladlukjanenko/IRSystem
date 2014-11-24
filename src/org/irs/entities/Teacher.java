package org.irs.entities;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Teacher")
@NamedQueries({
	@NamedQuery(name = "Teacher.selectAllTeachers", // get all teachers
				query = "select t from Teacher t"),
	@NamedQuery(name = "Teacher.selectTeacherByName", // get teacher by name
				query = "select t from Teacher t where t.teacherFullName = :name"),
	@NamedQuery(name = "Teacher.selectTeacherByStatus", // get teacher by status
				query = "select t from Teacher t where t.teacherStatus = :status"),
	@NamedQuery(name = "Teacher.selectTeacherByPublication", // get teacher by status
				query = "select t from Teacher t join t.publications p where p.publicationId = :id"),
	@NamedQuery(name = "Teacher.deleteTeacherById", // delete teacher by id
				query = "delete from Teacher where teacherID = :id")
})
public class Teacher implements Serializable {
	public Teacher() {}
	
	public Teacher(String teacherFullName, String teacherStatus) {
		this.teacherFullName = teacherFullName;
		this.teacherStatus = teacherStatus;
	}

	// create connectivity with table Publication
	private Set<Publication> publications = new HashSet<Publication>();
	
	@ManyToMany
	@JoinTable(name = "Bridge2",
			   joinColumns = @JoinColumn(name = "TeacherId"),
			   inverseJoinColumns = @JoinColumn(name = "PublicationId"))
	public Set<Publication> getPublications() {
		return publications;
	}

	public void setPublications(Set<Publication> publications) {
		this.publications = publications;
	}
	
	// =============================================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_id_seq")
	@SequenceGenerator(name = "teacher_id_seq", sequenceName = "Teacher_seq", allocationSize = 1)
	@Column(name = "TeacherID")
	public Long getTeacherID() {
		return this.teacherID;
	}

	public void setTeacherID(Long teacherID) {
		this.teacherID = teacherID;
	}
	
	@Column(name = "TeacherFullName")
	public String getTeacherFullName() {
		return this.teacherFullName;
	}

	public void setTeacherFullName(String teacherFullName) {
		this.teacherFullName = teacherFullName;
	}

	@Column(name = "TeacherStatus")
	public String getTeacherStatus() {
		return this.teacherStatus;
	}

	public void setTeacherStatus(String teacherStatus) {
		this.teacherStatus = teacherStatus;
	}

	// table Teacher fields
	private Long teacherID;
	private String teacherFullName;
	private String teacherStatus;
	
}
