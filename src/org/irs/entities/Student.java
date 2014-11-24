package org.irs.entities;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@org.hibernate.annotations.DynamicUpdate(value = true)
@Table(name = "Student")
@NamedQueries({
	@NamedQuery(name = "Student.selectAll", // get all students
				query = "select s from Student s left join s.groupStudent order by s.studentFullName asc"),
	@NamedQuery(name = "Student.selectAllNames", // get all students names
				query = "select s.studentFullName from Student s"),
	@NamedQuery(name = "Student.selectStudentByName", // get student by name
				query = "select s from Student s where s.studentFullName = :name"),
	@NamedQuery(name = "Student.selectStudentById", // get student by id
				query = "select s from Student s where s.studentId = :id"),
	@NamedQuery(name = "Student.selectStudentByYear", // get student by year
				query = "select s from Student s where s.studentEnter = :enter order by s.studentFullName"),
	@NamedQuery(name = "Student.selectStudentByGroup", // get student by group
				query = "select s from Student s left join s.groupStudent g where g.groupStudentNumber = :group"),
	
	// select all student by year and okr
	@NamedQuery(name = "Student.deleteStudentById", 
				query = "delete from Student where studentId = :id")				
})

public class Student implements Serializable {
	
	public Student() {}
	
	public Student(String studentFullName, String studentBook,
				   int studentEnter, String studentOKR) {
		this.studentFullName = studentFullName;
		this.studentBook = studentBook;
		this.studentEnter =studentEnter;
		this.studentOKR = studentOKR;
	}
	
	// create connectivity with table GroupStudent
	private GroupStudent groupStudent;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "GroupStudentId")
	public GroupStudent getGroupStudent() {
		return this.groupStudent;
	}
	
	public void setGroupStudent(GroupStudent groupStudent) {
		this.groupStudent = groupStudent;
	}
	
	// create connectivity with table Olympiad
	private Set<Olympiad> olympiads = new HashSet<Olympiad>();
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<Olympiad> getOlympiads() {
		return this.olympiads;
	}

	public void setOlympiads(Set<Olympiad> olympiads) {
		this.olympiads = olympiads;
	}

	// create connectivity with table Publication
	private Set<Publication> publications = new HashSet<Publication>();
	
	@ManyToMany
	@JoinTable(name = "Bridge",
			   joinColumns = @JoinColumn(name = "StudentId"),
			   inverseJoinColumns = @JoinColumn(name = "PublicationId"))
	public Set<Publication> getPublications() {
		return publications;
	}

	public void setPublications(Set<Publication> publications) {
		this.publications = publications;
	}
	
	// create connectivity with table Progress
	private Set<Progress> progress = new HashSet<Progress>();
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<Progress> getProgress() {
		return progress;
	}

	public void setProgress(Set<Progress> progress) {
		this.progress = progress;
	}

	// create connectivity with table Examination
	private Set<Examination> examinations = new HashSet<Examination>();
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<Examination> getExaminations() {
		return this.examinations;
	}

	public void setExaminations(Set<Examination> examinations) {
		this.examinations = examinations;
	}
	
	// =============================================================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_seq")
	@SequenceGenerator(name = "student_id_seq", sequenceName = "Student_seq", allocationSize = 1)
	@Column(name = "StudentID")
	public Long getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	@Column(name = "StudentFullName")
	public String getStudentFullName() {
		return this.studentFullName;
	}
	
	public void setStudentFullName(String studentFullName) {
		this.studentFullName = studentFullName;
	}
	
	@Column(name = "StudentBook")
	public String getStudentBook() {
		return this.studentBook;
	}
	
	public void setStudentBook(String studentBook) {
		this.studentBook = studentBook;
	}
	
	@Column(name = "StudentEnter")
	public int getStudentEnter() {
		return this.studentEnter;
	}

	public void setStudentEnter(int studentEnter) {
		this.studentEnter = studentEnter;
	}

	@Column(name = "StudentOKR")
	public String getStudentOKR() {
		return this.studentOKR;
	}

	public void setStudentOKR(String studentOKR) {
		this.studentOKR = studentOKR;
	}	

	@Column(name = "StudentMode")
	public String getStudentMode() {
		return studentMode;
	}

	public void setStudentMode(String studentMode) {
		this.studentMode = studentMode;
	}

	@Override
	public boolean equals(Object arg) {
		Student s = (Student) arg;
		
		//check if we have students with one studentBook
		if(this.studentBook.equals(s.getStudentBook()))
			return true;
		
		return false;
	}
	
	// table Student fields
	private Long studentId;
	private String studentFullName;
	private String studentBook;
	private int studentEnter;
	private String studentOKR;
	private String studentMode;
	
}