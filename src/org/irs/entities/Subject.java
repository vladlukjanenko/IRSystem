package org.irs.entities;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Subject")
@NamedQueries({
	@NamedQuery(name = "Subject.selectAllSubjects", // get all progress
				query = "select s from Subject s"), 
	@NamedQuery(name = "Subject.selectSubjectByName", // get subject by name
				  query = "select s from Subject s where s.subjectTitle = :name"),
	@NamedQuery(name = "Subject.deleteSubjectById", // delete subject by id
				query = "delete from Subject where subjectId = :id"),
	@NamedQuery(name = "Subject.selectSubjectById", // select subject by id
				query = "select s from Subject s where s.subjectId = :id"),
	@NamedQuery(name = "Subject.selectSubjectByIdType", // select subject by id and type
				query = "select s from Subject s join s.progress p where s.subjectId = :id and p.progressExam = :type")			
	
})
public class Subject implements Serializable {
	public Subject() {}
	
	public Subject(String subjectTitle, int subjectHours) {
		this.subjectTitle = subjectTitle;
		this.subjectHours = subjectHours;
	}

	// create connectivity with table Progress
	private Set<Progress> progress;
	
	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<Progress> getProgress() {
		return progress;
	}

	public void setProgress(Set<Progress> progress) {
		this.progress = progress;
	}
	
	// =======================================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_id_seq")
	@SequenceGenerator(name = "subject_id_seq", sequenceName = "Subject_seq", allocationSize = 1)
	@Column(name = "SubjectID")
	public Long getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "SubjectTitle")
	public String getSubjectTitle() {
		return this.subjectTitle;
	}

	public void setSubjectTitle(String subjectTitle) {
		this.subjectTitle = subjectTitle;
	}

	@Column(name = "SubjectHours")
	public int getSubjectHours() {
		return subjectHours;
	}

	public void setSubjectHours(int subjectHours) {
		this.subjectHours = subjectHours;
	}
	
	@Override
	public boolean equals(Object object) {
		Subject subject = (Subject) object;
		
		if(this.getSubjectTitle().equals(subject.getSubjectTitle()))
			return true;
		
		return false;
	}

	// table Subject fields
	private Long subjectId;
	private String subjectTitle;
	private int subjectHours;
	
}
