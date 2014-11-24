package org.irs.entities;


import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Progress")
@NamedQueries({
	@NamedQuery(name = "Progress.selectAllProgress", // get all progress
				query = "select p from Progress p"),
	@NamedQuery(name = "Progress.selectAllProgressByStudent", // get all progress
				query = "select p from Progress p left join p.student where p.student.studentId = :id"), // by progress
	@NamedQuery(name = "Progress.deleteProgressById", // delete publication by id
		query = "delete from Progress where progressId = :id"),
		
	@NamedQuery(name = "Progress.selectProgressYear", 
		query = "select p from Progress p " +
				"left join p.subject s " +
				"left join p.student st where st.studentEnter = :year " +
				"and s.subjectId = :id and p.progressExam = :type order by st.studentFullName asc"),
	
	@NamedQuery(name = "Progress.selectProgressGroup", 
	query = "select p from Progress p " +
			"left join p.subject s " +
			"left join p.student st " +
			"left join st.groupStudent g  where g.groupStudentId = :group " +
			"and s.subjectId = :id and p.progressExam = :type order by st.studentFullName asc")
})
public class Progress implements Serializable {
	public Progress() {}
	
	public Progress(String progressMark) {
		this.progressMark = progressMark;
	}

	// create connectivity with table Student
	private Student student;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "StudentID")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	// create connectivity with table Subject
	private Subject subject;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SubjectId")
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	// =========================================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progress_id_seq")
	@SequenceGenerator(name = "progress_id_seq", sequenceName = "Progress_seq", allocationSize = 1)
	@Column(name = "ProgressID")
	public Long getProgressId() {
		return this.progressId;
	}
	
	public void setProgressId(Long progressId) {
		this.progressId = progressId;
	}

	@Column(name = "ProgressMark")
	public String getprogressMark() {
		return this.progressMark;
	}

	public void setProgressMark(String progressMark) {
		this.progressMark = progressMark;
	}
	
	@Column(name = "ProgressExam")
	public Integer getProgressExam() {
		return progressExam;
	}

	public void setProgressExam(Integer progressExam) {
		this.progressExam = progressExam;
	}
	
	@Override
	public boolean equals(Object arg) {
		Progress prog = (Progress) arg;
		
		// objects are equals if
		if (this.subject.getSubjectTitle().equals(prog.getSubject().getSubjectTitle()) && 
		    this.progressExam == prog.getProgressExam()) 
			return true;
		else
			return false;
	}

	// table Progress fields
	private Long progressId;
	private String progressMark;
	private Integer progressExam;
	
}
