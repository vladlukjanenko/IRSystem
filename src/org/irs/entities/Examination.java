package org.irs.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Examination")
@NamedQueries({
	@NamedQuery(name = "Examination.selectAllExams",  // get all exams
				query = "select e from Examination e"),
	@NamedQuery(name = "Examination.selectAllExamsByStudent",  // get all exams
				query = "select e from Examination e left join e.student where e.student.studentId = :id"), // by student
	@NamedQuery(name = "Examination.deleteExaminationById", // delete publication by id
				query = "delete from Examination where examinationId = :id")
})
public class Examination implements Serializable {
	
	public Examination() {}
	
	public Examination(String examinationMark) {
		this.examinationMark = examinationMark;
	}

	// create connectivity with table Student
	private Student student;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "StudentId")
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	// =========================================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examination_id_seq")
	@SequenceGenerator(name = "examination_id_seq", sequenceName = "Examination_seq", allocationSize = 1)
	@Column(name = "ExaminationId")
	public Long getExaminationId() {
		return this.examinationId;
	}
	
	public void setExaminationId(Long examinationId) {
		this.examinationId = examinationId;
	}
	
	@Column(name = "examinationMark")
	public String getExaminationMark() {
		return this.examinationMark;
	}
	
	public void setExaminationMark(String examinationMark) {
		this.examinationMark = examinationMark;
	}
	
	@Column(name = "ExaminationName")
	public String getExaminationName() {
		return examinationName;
	}

	public void setExaminationName(String examinationName) {
		this.examinationName = examinationName;
	}
	
	@Override
	public boolean equals(Object arg) {
		Examination exam = (Examination) arg;

		if (exam.getExaminationName().equals(this.examinationName)
				&& "MainExam".equals(exam.getExaminationName())) {
			return true;
		}

		if (exam.getExaminationName().equals(this.examinationName)
				&& "EngExam".equals(exam.getExaminationName())) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.examinationId.hashCode();
	}
	
	// table Examination fields
	private Long examinationId;
	private String examinationMark;
	private String examinationName;
}
