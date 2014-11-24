package org.irs.entities;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Publication")
@NamedQueries({
	@NamedQuery(name = "Publication.getAllPublication", // get all publication
				query = "select p from Publication p"), 
	@NamedQuery(name = "Publication.getAllPublicationByTypeYear", // get publications by type and date
				query = "select p from Publication p where p.publicationType = :type " +
						"and p.publicationDate = :year"),
	@NamedQuery(name = "Publication.getAllPublicationByDate", // get publications by date
				query = "select p from Publication p where p.publicationDate = :date"),
	@NamedQuery(name = "Publication.getAllPublicationByPlace", // get publications by place
				query = "select p from Publication p where p.publicationDate = :place"),
	@NamedQuery(name = "Publication.getAllPublicationById", // get all publication by id
				query = "select p from Publication p where p.publicationId = :id"),
	@NamedQuery(name = "Publication.deletePublicationById", // delete publication by id
				query = "delete from Publication where publicationId = :id"),
	@NamedQuery(name = "Publication.getAllPublicationByStudent", // get publications by student
				query = "select p from Publication p join p.student s where s.studentId = :id"),
	@NamedQuery(name = "Publication.getAllPublicationByTypeThesis", // get publications by thesis
				query = "select p from Publication p where p.publicationType = :type " +
						"and p.publicationDate = :year " +
						"and p.publicationThesis = :thesis"),
	@NamedQuery(name = "Publication.getAllPublicationsNull", // get publications by thesis
				query = "select p from Publication as p left join fetch p.student as st where st is null")	
})
public class Publication implements Serializable {
	public Publication() {}
	
	public Publication(String publicationTitle, String publicationType,
			           String publicationPlace, Integer publicationDate,
			           String publicationMag, String publicationPage,
					   String publicationMagNum) {
		
		this.publicationTitle = publicationTitle;
		this.publicationType = publicationType;
		this.publicationPlace = publicationPlace;
		this.publicationDate = publicationDate;
		this.publicationMag = publicationMag;
		this.publicationPage = publicationPage;
		this.publicationMagNum = publicationMagNum;
	}

	// create connectivity with table Student
	private Set<Student> student;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Bridge",
			   joinColumns = @JoinColumn(name = "PublicationId"),
			   inverseJoinColumns = @JoinColumn(name = "StudentId"))
	public Set<Student> getStudent() {
		return this.student;
	}

	public void setStudent(Set<Student> student) {
		this.student = student;
	}
	
	
	// create connectivity with table Teacher
	private Set<Teacher> teachers = new HashSet<Teacher>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Bridge2",
			   joinColumns = @JoinColumn(name = "PublicationId"),
			   inverseJoinColumns = @JoinColumn(name = "TeacherId"))
	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
	
	// ===========================================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publication_id_seq")
	@SequenceGenerator(name = "publication_id_seq", sequenceName = "Publication_seq", allocationSize = 1)
	@Column(name = "PublicationID")
	public Long getPublicationId() {
		return this.publicationId;
	}
	
	public void setPublicationId(Long publicationId) {
		this.publicationId = publicationId;
	}
	
	@Column(name = "PublicationTitle")
	public String getPublicationTitle() {
		return this.publicationTitle;
	}
	
	public void setPublicationTitle(String publicationTitle) {
		this.publicationTitle = publicationTitle;
	}
	
	@Column(name = "PublicationType")
	public String getPublicationType() {
		return this.publicationType;
	}
	
	public void setPublicationType(String publicationType) {
		this.publicationType = publicationType;
	}
	
	@Column(name = "PublicationPlace")
	public String getPublicationPlace() {
		return this.publicationPlace;
	}
	
	public void setPublicationPlace(String publicationPlace) {
		this.publicationPlace = publicationPlace;
	}
	
	@Column(name = "PublicationDate")
	public Integer getPublicationDate() {
		return this.publicationDate;
	}
	
	public void setPublicationDate(Integer publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	@Column(name = "PublicationMag")
	public String getPublicationMag() {
		return publicationMag;
	}

	public void setPublicationMag(String publicationMag) {
		this.publicationMag = publicationMag;
	}

	@Column(name = "PublicationPage")
	public String getPublicationPage() {
		return publicationPage;
	}

	public void setPublicationPage(String publicationPage) {
		this.publicationPage = publicationPage;
	}

	@Column(name = "PublicationMagNum")
	public String getPublicationMagNum() {
		return publicationMagNum;
	}

	public void setPublicationMagNum(String publicationMagNum) {
		this.publicationMagNum = publicationMagNum;
	}
	
	@Column(name = "PublicationBase")
	public Integer getPublicationBase() {
		return publicationBase;
	}

	public void setPublicationBase(Integer publicationBase) {
		this.publicationBase = publicationBase;
	}
	
	@Column(name = "publicationThesis")
	public Integer getPublicationThesis() {
		return publicationThesis;
	}

	public void setPublicationThesis(Integer publicationThesis) {
		this.publicationThesis = publicationThesis;
	}
	
	@Column(name = "publicationPubl")
	public String getPublicationPubl() {
		return publicationPubl;
	}

	public void setPublicationPubl(String publicationPubl) {
		this.publicationPubl = publicationPubl;
	}


	// table Publication fields
	private Long publicationId;
	private String publicationTitle;
	private String publicationType;
	private String publicationMag;
	private String publicationPage;
	private String publicationMagNum;
	private String publicationPlace;
	private Integer publicationDate;
	private Integer publicationBase;
	private Integer publicationThesis;
	private String publicationPubl;
	
}
