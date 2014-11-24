package org.irs.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.irs.entities.*;
import org.irs.regexpr.RegularExpression;
import org.irs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/*
 * Controller which handles all request
 * connected to information about students
 * */
@Controller
public class StudentController {

	@Autowired
	private BridgeService bs; // to insert publication to students
	
	@Autowired
	private StudentService sts; // get all available operations 
							    // for Student table
	
	@Autowired
	private GroupStudentService gss; // get all available operations 
									 // for GroupStudent table
	
	@Autowired
	private OlympiadService os; // get all available operations
								// for Olympiad table
	
	@Autowired
	private TeacherService ts; // get all available operations 
	 						   // for Teacher table

	@Autowired
	private PublicationService ps; // get all available operations 
	   							   // for Publication table
	
	@Autowired
	private ProgressService pss; // get all available operations 
	   							 // for Progress table
	
	@Autowired
	private SubjectService ss; // get all available operations 
	   						   // for Subject table
	
	@Autowired
	private ExaminationService es; // get all available operations 
	   							   // for Examination table
	
	
	/*
	 * Handle URL which has type: /newStudent.html
	 * using GET request method
	 * */
	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public ModelAndView newStudentForm() {

		for (Publication pid : ps.selectAllPublications()) {

			// if we have publication that has no students
			if (pid.getStudent().isEmpty()) {

				// we delete it
				ps.deletePublicationById(pid.getPublicationId());
			}
		}

		ModelAndView mav = new ModelAndView("index"); // create MVC object
														   // to pass data to JSP page
		
		mav.getModelMap().put("allGroups", gss.selectAllGroups()); // add all available groups
		
		return mav;
	}
	
	/*
	 * Handle URL which has type: /newStudent.html
	 * using POST request method
	 * */
	@RequestMapping(value = "/newStudent.html", headers="Accept=*/*", method = RequestMethod.POST)
	public @ResponseBody String createStudent(@ModelAttribute(value = "student") Student student) {

		String group = student.getGroupStudent().getGroupStudentNumber(); // get group name from
		 																   // select in JSP
		
		if (group.equals("null")) {
			return "errorGroup";
		 // check if we entered correct student name
		} else if (!RegularExpression.studentNameAndTeacherSubject(student.getStudentFullName()) || 
			!RegularExpression.checkSizeStudentFullName(student.getStudentFullName())) {
			
			return "errorName";
			
			// check if we enter correct student book
		} else if (!RegularExpression.studentBookExprAndGroup(student.getStudentBook())
					|| !RegularExpression.checkSizeStudentBook(student.getStudentBook())) {
			return "errorBook";
			
			// check if we entered correct year
		} else if (student.getStudentEnter() < 2005) {
			return "errorEnter";
			
		} else {
			GroupStudent g = gss.getGroupByName(group); // get selected group
														// object
			student.setGroupStudent(g); // add selected group to student
			
			// check studentBook
			if(g.getStudents().contains(student))
				return "errorBook";
			
			sts.insertStudent(student); // insert student to data base
										   
			return "success"; // "success"
		}
	}

	// =================================================================================
	
	/*
	 * Handle URL which has type: /studentProfileEdit.html
	 * using GET request method
	 * */
	@RequestMapping(value = "/studentProfileEdit.html", method = RequestMethod.GET)
	public ModelAndView getStudentProfile(@RequestParam(value = "searchName", required = false) String name) {
		ModelAndView mav = new ModelAndView("studentProfileEdit");
		mav.getModelMap().put("allStudents", sts.selectAllNames());
		mav.getModelMap().put("subjects", ss.selectAllSubjects());
		mav.getModelMap().put("teachers", ts.selectAllTeachers());
		mav.getModelMap().put("groups", gss.selectAllGroups());
			
		return mav;
	}
	
	/*
	 * Handle URL which has type: /studentProfileEdit.html
	 * using POST request method
	 * */
	@RequestMapping(value = "/studentProfileEdit.html", method = RequestMethod.POST)
	public @ResponseBody String editStudentProfile(@RequestParam(value = "findName") String findName,
												   @RequestParam(value = "funkName") String funkName,
											       @RequestParam(value = "studentFullName", required = false) String studentName,
  											       @RequestParam(value = "groupStudent", required = false) String groupStudent,
											       @RequestParam(value = "studentBook", required = false) String studentBook,
											       @RequestParam(value = "studentEnter", required = false) Integer studentEnter,
											       @RequestParam(value = "studentOKR", required = false) String studentOKR,		
											       @RequestParam(value = "subjectExam",  required = false) String subjectExamName,
											       @RequestParam(value = "subjectProg",  required = false) String subjectProgName,
											       @RequestParam(value = "teach", required = false) String teacherName,
											       @RequestParam(value = "studentMode", required = false) String studentMode,
										   @ModelAttribute(value = "olympiad") Olympiad olympiad,
										   @ModelAttribute(value = "examination") Examination examination,
										   @ModelAttribute(value = "progress") Progress progress) {
		
		// =======================================================================================================================
	
		Student currentStudent = sts.selectStudentByName(findName); // get current student
		Subject subjectExam = ss.selectSubjectByName(subjectExamName); // get selected subject for exam
	
		subjectProgName = subjectProgName.replaceAll("\"", "&quot");

		Subject subjectProg = ss.selectSubjectByName(subjectProgName); // get selected subject for progress
		Teacher teacher = null;
		
		if(currentStudent != null) {
			// check if we entered correct student name
			if (funkName.equals("changeName")) {
				if (!RegularExpression.studentNameAndTeacherSubject(studentName) ||
					!RegularExpression.checkSizeStudentFullName(studentName))
					return "errorName";
				else {
					currentStudent.setStudentFullName(studentName);
					sts.updateStudent(currentStudent);
					return "successName"; 
				}
			}
			
			// check if we enter correct student book
			if (funkName.equals("changeBook"))
				if (!RegularExpression.studentBookExprAndGroup(studentBook)
					|| !RegularExpression.checkSizeStudentBook(studentBook))
					return "errorBook";
				else {
					currentStudent.setStudentBook(studentBook);
					sts.updateStudent(currentStudent);
					return "successBook";
				}
			
			// check if we entered correct year
			if (funkName.equals("changeEnter"))
				if (studentEnter < 2005)
					return "errorEnter";
				else {
					currentStudent.setStudentEnter(studentEnter);
					sts.updateStudent(currentStudent);
					return "successEnter";
				}
			
			// change student OKR
			if (funkName.equals("changeOKR")) {
				currentStudent.setStudentOKR(studentOKR);
				sts.updateStudent(currentStudent);
				return "success";
		    }
			
			// change student group
			if (funkName.equals("changeGroup")) {
				GroupStudent group = gss.getGroupByName(groupStudent);
				currentStudent.setGroupStudent(group);
					sts.updateStudent(currentStudent);
				return "success";
			}
			
			// change student group
			if (funkName.equals("changeMode")) {
				currentStudent.setStudentMode(studentMode);
					sts.updateStudent(currentStudent);
				return "success";
			}
			
			// check publication direction
			if (funkName.equals("addOlympiad"))
				if(olympiad.getOlympiadDirection() == null || olympiad.getOlympiadDirection().equals("") || 
						!RegularExpression.checkSizeOlympiadDirection(olympiad.getOlympiadDirection()))
					
					return "errorDirection";
				else {
					olympiad.setStudent(currentStudent); // add new olympiad to current student
					
					olympiad.getOlympiadDirection().replaceAll("\"", "&quot"); // change " to html &quot
				
					try {
						os.insertOlympiad(olympiad);
					} catch (Exception e) {
						return "errorDirection";
					}
					return "successOl";
				}
			
			// check exam
			if (funkName.equals("addExam")) {
				
				examination.setStudent(currentStudent); // set student
				
				if(es.selectAllExamsByStudent(currentStudent.getStudentId()).contains(examination)) {
					return "error";
				} else {
					es.insertExam(examination); // add to database
					return "success";
				}
			}
			
			// check exam
			if (funkName.equals("addProgress")) {
				progress.setStudent(currentStudent); // set student
				
				progress.setSubject(subjectProg); // set subject

				if (pss.selectAllProgressByStudent(currentStudent.getStudentId()).contains(progress)) {
					return "error";
				} else {
					pss.insertProgress(progress); // add to database
					return "success";
				}
			}
			
			return "";
		} else return "";
	}
	
	@RequestMapping(value = "/updatePage.html", consumes="application/json;charset=utf-8", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> updateStudent(@RequestBody String searchName) {
		
		
			HttpHeaders responseHeaders = new HttpHeaders();
		    responseHeaders.add("Content-Type", "application/json; charset=utf-8");
			
			String name = searchName.substring(15);
			name = name.substring(0, name.indexOf("\""));
			Student student = sts.selectStudentByName(name);
			
			if (student != null && RegularExpression.studentNameAndTeacherSubject(student.getStudentFullName())) {	
				String json = "{" +
								"\"studentId\":" + "\"" + student.getStudentId() + "\"" +
								",\"studentFullName\":" + "\"" + student.getStudentFullName() + "\"" +
								",\"studentBook\":" + "\"" + student.getStudentBook() + "\"" +
								",\"studentEnter\":" + student.getStudentEnter() +
								",\"studentOKR\":" + "\"" + student.getStudentOKR() + "\"" +
								",\"groupStudent\":" + "\"" + student.getGroupStudent().getGroupStudentNumber() + "\"" +
								",\"studentMode\":" + "\"" + student.getStudentMode() + "\"" +
							  "}";
				
				return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
			} else return new ResponseEntity<String>("{\"error\":\"error\"}",responseHeaders, HttpStatus.OK);
	}
	
	// ==================================================================================
	
	/*
	 * Handle URL which has type: /newStudent.html
	 * using GET request method
	 * */
	@RequestMapping(value = "/viewStudents.html", method = RequestMethod.GET)
	public ModelAndView viewHelp() {

		ModelAndView mav = new ModelAndView("viewStudents"); // create MVC object
															 // to pass it to JSP page
		
		mav.getModelMap().put("students", sts.selectAllStudents()); // add all students
		mav.getModelMap().put("groups", gss.selectAllGroups()); // add all students

		return mav;
	}
	
	/*
	 * Handle URL which has type: /studentProfileView.html
	 * using GET request method
	 * */
	@RequestMapping(value = "/studentProfileView.html", method = RequestMethod.GET)
	public ModelAndView viewStudentProfile(@RequestParam("id") Long studentId) {
		ModelAndView viewStudent = new ModelAndView("studentProfileView");
		Student student = sts.selectStudentById(studentId); // get current student
		Long id = student.getStudentId();
		double totalMark = 0.0;
		
		List<Olympiad> olympiadsList = os.selectOlympiadsByStudent(id);
		List<Publication> publicationsList = ps.selectPublicationsByStudent(id);
		List<Examination> examList = es.selectAllExamsByStudent(id);
		List<Progress> progressList = pss.selectAllProgressByStudent(id);
		
		double totalAchievments = 0.0;
		for(Olympiad o : olympiadsList) {
			if(o.getOlympiadType().equals("Міжнародна"))
				totalAchievments += 2;
			if(o.getOlympiadType().equals("Всеукраїнська"))
				totalAchievments += 1;
			if(o.getOlympiadCore().equals("True")) {
				if(o.getOlympiadType().equals("Університетська"))
					totalAchievments += 0.5;
			} else
				if(o.getOlympiadType().equals("Університетська"))
					totalAchievments += 0.25;
		}
		
		
		for(Publication p : publicationsList) {
			if(p.getPublicationType() != null) {
				if(p.getPublicationType().equals("Міжнародна конференція"))
					totalAchievments += 2.0/(p.getStudent().size()+p.getTeachers().size());
				if(p.getPublicationType().equals("Всеукраїнська конференція"))
					totalAchievments += 1.0/(p.getStudent().size()+p.getTeachers().size());
				if(p.getPublicationType().equals("Університетська"))
					totalAchievments += 0.5/(p.getStudent().size()+p.getTeachers().size());
			}
		}
		
		if(totalAchievments > 3.0) {
			totalMark += 3.0; // we add only 3
			totalAchievments = 3.0; 
		} else
			totalMark += totalAchievments;
		
		double temp = 0.0;
		for(Examination e : examList) {	
			if("MainExam".equals(e.getExaminationName()))
				switch(e.getExaminationMark()) {
					case "A": temp+=10.0; break;
					case "B": temp+=9.0; break;
					case "C": temp+=8.0; break;
					case "D": temp+=7.0; break;
					case "E": temp+=6.0; break;
					case "F": temp+=4.0; break;
				}
			else
				switch(e.getExaminationMark()) {
					case "A": temp+=5.0; break;
					case "B": temp+=4.5; break;
					case "C": temp+=4.0; break;
					case "D": temp+=3.5; break;
					case "E": temp+=3.0; break;
					case "F": temp+=2.0; break;
				}
		}
		
		totalMark += temp; 
		
		double progressMark = 0.0;
		for(Progress p : progressList) 			
			switch(p.getprogressMark()) {
				case "A": progressMark+=5.0; break;
				case "B": progressMark+=4.5; break;
				case "C": progressMark+=4.0; break;
				case "D": progressMark+=3.5; break;
				case "E": progressMark+=3.0; break;
				case "F": progressMark+=2.0; break;
			}
		
		if(progressList.size() > 0)
			totalMark += (progressMark/progressList.size());
		
		viewStudent.getModelMap().put("student", student); // add student to model 
		viewStudent.getModelMap().put("olympiads", olympiadsList);
		viewStudent.getModelMap().put("publications", publicationsList);
		viewStudent.getModelMap().put("exams", examList);
		viewStudent.getModelMap().put("progress", progressList);
		viewStudent.getModelMap().put("totalMark", Math.round(totalMark*100.0)/100.0);
		viewStudent.getModelMap().put("examMark", Math.round(temp*100.0)/100.0);
		viewStudent.getModelMap().put("progressMark", Math.round((progressMark/progressList.size())*100.0)/100.0);
		viewStudent.getModelMap().put("achievmentsMark", Math.round(totalAchievments*100.0)/100.0);
		
		return viewStudent;
	}
	
	/*
	 * Delete publication
	 * */
	@RequestMapping(value = "/deletePublication.html", method = RequestMethod.POST)
	public @ResponseBody String deletePublication(@RequestParam("publicationId") Long publicationId,
												  @RequestParam("studentId") Long studentId) {
		
		if (publicationId != null && studentId != null) {
			if(ps.selectPublicationsById(publicationId).getStudent().size() == 1) {
				ps.deletePublicationById(publicationId); // delete from LAST student and teacher
			} else
				bs.deletePublication(studentId); // delete publication from student
			return "successPubl"; 
		} else return "errorPubl";
	}
	
	/*
	 * Delete olympiad
	 * */
	@RequestMapping(value = "/deleteOlympiad.html", method = RequestMethod.POST)
	public @ResponseBody String deleteOlympiad(@RequestParam("olympiadId") Long olympiadId) {
		
		if (olympiadId != null) {
			os.deleteOlympiadById(olympiadId);
			return "successOlym"; 
		} else return "errorOlym";
	}
	
	/*
	 * Delete progress
	 * */
	@RequestMapping(value = "/deleteProgress.html", method = RequestMethod.POST)
	public @ResponseBody String deleteProgress(@RequestParam("progressId") Long progressId) {
		
		if (progressId != null) {
			pss.deleteProgressById(progressId);
			return "successProg"; 
		} else return "errorProg";
	}
	
	/*
	 * Delete progress
	 * */
	@RequestMapping(value = "/deleteExamination.html", method = RequestMethod.POST)
	public @ResponseBody String deleteExamination(@RequestParam("examinationId") Long examinationId) {
		
		if (examinationId != null) {
			es.deleteExamById(examinationId);
			return "successExam"; 
		} else return "errorExam";
	}
	
	/*
	 * Delete student
	 * */
	@RequestMapping(value = "/deleteStudent.html", method = RequestMethod.POST)
	public @ResponseBody String deleteStudent(@RequestParam("studentId") Long studentId) {
		
		if (studentId != null) {
			sts.deleteStudent(studentId);
			return "success"; 
		} else return "error";
	}
	
	@RequestMapping(value = "/addPublications.html", method = RequestMethod.GET)
	public ModelAndView getPublications() {
		ModelAndView mav = new ModelAndView("addPublications");
		mav.getModelMap().put("allStudents", sts.selectAllNames());
		mav.getModelMap().put("teachers", ts.selectAllTeachers());
		mav.getModelMap().put("groups", gss.selectAllGroups());
			
		return mav;
	}
	
	/*
	 * Add publications to students
	 * */
	@RequestMapping(value = "/addPublications.html", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> addPublications(@ModelAttribute(value = "publication") Publication publication,
												@RequestParam(value = "st1", required = false) String st1,
												@RequestParam(value = "st2", required = false) String st2,
												@RequestParam(value = "st3", required = false) String st3,
												@RequestParam(value = "st4", required = false) String st4,
												@RequestParam(value = "tc1", required = false) String tc1,
												@RequestParam(value = "tc2", required = false) String tc2) {
    	
    	HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add("Content-Type", "text/html; charset=utf-8");
    	
	    String[] added = new String[6];
	    
		// check publication
		if(publication.getPublicationTitle() == null || publication.getPublicationTitle().equals("") || 
		   !RegularExpression.checkSizePublicationTitle(publication.getPublicationTitle())) {
			return new ResponseEntity<String>("errorTitle",responseHeaders, HttpStatus.OK);
		} else if(publication.getPublicationPlace() == null || publication.getPublicationPlace().equals("") ||
				!RegularExpression.checkSizePublicationPlace(publication.getPublicationPlace())) {
			return new ResponseEntity<String>("errorPlace",responseHeaders, HttpStatus.OK);
		} else if(!RegularExpression.checkPage(publication.getPublicationPage())) {
			return new ResponseEntity<String>("errorPage",responseHeaders, HttpStatus.OK);
		} else if(!RegularExpression.checkSizePublicationMag(publication.getPublicationMag())) {
			return new ResponseEntity<String>("errorMag",responseHeaders, HttpStatus.OK);
	    } else {
	    	
	    	if(st1.equals("") && st2.equals("") && st3.equals("") && st4.equals(""))
	    		return new ResponseEntity<String>("errorStudent",responseHeaders, HttpStatus.OK);
			
			Long pid = ps.insertPublication(publication).getPublicationId(); // insert publication and  get it's id

			
	    	// check students to add publication
			if (!st1.equals("")) 
				added[0] = insertPublicationStudent(pid, st1);
			
			if (!st2.equals("")) 
				added[1] = insertPublicationStudent(pid, st2);
			
			if (!st3.equals("")) 
				added[2] = insertPublicationStudent(pid, st3);
			
			if (!st4.equals(""))
				added[3] = insertPublicationStudent(pid, st4);
	    	
			// check teachers to add publication
			if(!tc1.equals(""))
				added[4] = insertPublicationTeacher(pid, tc1);
			
			if(!tc2.equals(""))
				added[5] = insertPublicationTeacher(pid, tc2);
			
			// information string
			StringBuilder str = new StringBuilder();
			str.append("Публікація була додана до: ");
			
			for (int i = 0; i < added.length; i++) 
				if(added[i] != null)
					if(!added[i].equals(""))
						str.append(added[i] + ",");
			
			if(str.charAt(str.length()-1) == ',')
				str.deleteCharAt(str.length()-1);
			
			return new ResponseEntity<String>(str.toString(),responseHeaders, HttpStatus.OK);
		}
	}
	
	private String insertPublicationStudent(Long pid, String name) {
		Student student = sts.selectStudentByName(name); // get student from database
		
		if(student != null) { // if we entered correct student name
			bs.insertPublicationToStudent(new Bridge(student.getStudentId(), pid)); // insert publication to student
			return name;
		} else
			return "";
	}
	
	private String insertPublicationTeacher(Long pid, String name) {
		Teacher teacher = ts.selectTeacherByName(name); // get teacher from database
		
		if(teacher != null) { // if we entered correct student name
			bs.insertPublicationToTeacher(new Bridge2(teacher.getTeacherID(), pid)); // insert publication to teacher
			return name;
		} else
			return "";
	}
}