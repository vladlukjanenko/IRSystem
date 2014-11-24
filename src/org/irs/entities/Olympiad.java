package org.irs.entities;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "Olympiad")
@NamedQueries({
	@NamedQuery(name = "Olympiad.getAllOlympiads", // get all olympiads
				query = "select o from Olympiad o"),
	@NamedQuery(name = "Olympiad.getOlympiadsByYear", // get olympiad by year
				query = "select o from Olympiad o where o.olympiadDate = :year " +
						"and o.olympiadType = :type"),
	@NamedQuery(name = "Olympiad.getOlympiadsByStudent", // get olympiad by student
				query = "select o from Olympiad o left join o.student where o.student.studentId  = :id"),
	@NamedQuery(name = "Olympiad.deleteOlympiadById", // delete Olympiad by id
				query = "delete from Olympiad where olympiadId = :id")
})
public class Olympiad implements Serializable {
	public Olympiad() {}
	
	public Olympiad(String olympiadDirection, int olympiadPlace,
					String olympiadType, Integer olympiadDate, String olympiadCore) {
		
		this.olympiadDirection = olympiadDirection;
		this.olympiadPlace = olympiadPlace;
		this.olympiadType = olympiadType;
		this.olympiadDate = olympiadDate;
		this.olympiadCore = olympiadCore;
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
	
	// ==================================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "olympiad_id_seq")
	@SequenceGenerator(name = "olympiad_id_seq", sequenceName = "Olympiad_seq", allocationSize = 1)
	@Column(name = "OlympiadId")
	public Long getOlympiadId() {
		return this.olympiadId;
	}
	
	public void setOlympiadId(Long olympiadId) {
		this.olympiadId = olympiadId;
	}
	
	@Column(name = "OlympiadDirection")
	public String getOlympiadDirection() {
		return this.olympiadDirection;
	}
	
	public void setOlympiadDirection(String olympiadDirection) {
		this.olympiadDirection = olympiadDirection;
	}
	
	@Column(name = "OlympiadPlace")
	public int getOlympiadPlace() {
		return this.olympiadPlace;
	}
	
	public void setOlympiadPlace(int olympiadPlace) {
		this.olympiadPlace = olympiadPlace;
	}
	
	@Column(name = "OlympiadType")
	public String getOlympiadType() {
		return this.olympiadType;
	}
	
	public void setOlympiadType(String olympiadType) {
		this.olympiadType = olympiadType;
	}
	
	@Column(name = "OlympiadDate")
	public Integer getOlympiadDate() {
		return this.olympiadDate;
	}
	
	public void setOlympiadDate(Integer olympiadDate) {		
		this.olympiadDate = olympiadDate;
	}
	
	@Column(name = "OlympiadCore")
	public String getOlympiadCore() {
		return this.olympiadCore;
	}
	
	public void setOlympiadCore(String olympiadCore) {		
		this.olympiadCore = olympiadCore;
	}
	
	// table Olympiad fields
	private Long olympiadId;
	private String olympiadDirection;
	private int olympiadPlace;
	private String olympiadType;
	private Integer olympiadDate;
	private String olympiadCore;
	
}
