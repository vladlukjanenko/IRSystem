package org.irs.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Bridge2")
public class Bridge2 implements Serializable {
	
	public Bridge2() {}
	
	public Bridge2(Long teacherId, Long publicationId) {
		this.teacherId = teacherId;
		this.publicationId = publicationId;
	}
	
	@Id
	@Column(name = "TeacherID")
	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
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
		if(this.publicationId == ((Bridge2) arg).getPublicationId() &&
		   this.teacherId == ((Bridge2) arg).getTeacherId()) {
			
			return true;
		} else return false;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	// table Bridge fields
	private Long teacherId;
	private Long publicationId;
}
