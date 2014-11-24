package org.irs.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Bridge")
@NamedQueries({
	@NamedQuery(name = "Bridge.deletePublication", // delete publication from student
				query = "delete from Bridge where studentId = :id")
})
public class Bridge implements Serializable {
	
	public Bridge() {}
	
	public Bridge(Long studentId, Long publicationId) {
		this.studentId = studentId;
		this.publicationId = publicationId;
	}
	
	@Id
	@Column(name = "StudentID")
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	@Id
	@Column(name = "PublicationID")
	public Long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}
	
	@Override
	public boolean equals(Object arg) {
		
		if(this.publicationId == ((Bridge) arg).getPublicationId() &&
		   this.studentId == ((Bridge) arg).getStudentId()) {
			
			return true;
		} else return false;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	// table Bridge fields
	private Long studentId;
	private Long publicationId;
}
