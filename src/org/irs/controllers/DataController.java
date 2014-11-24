package org.irs.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.sql.SQLException;

import oracle.sql.SQLName;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.loader.custom.sql.SQLQueryParser;
import org.irs.entities.Bridge;
import org.irs.entities.Bridge2;
import org.irs.entities.Examination;
import org.irs.entities.GroupStudent;
import org.irs.entities.Olympiad;
import org.irs.entities.Progress;
import org.irs.entities.Publication;
import org.irs.entities.Student;
import org.irs.entities.Subject;
import org.irs.entities.Teacher;
import org.irs.regexpr.RegularExpression;
import org.irs.service.BridgeService;
import org.irs.service.BridgeServiceImpl;
import org.irs.service.ExaminationService;
import org.irs.service.ExaminationServiceImpl;
import org.irs.service.GroupStudentService;
import org.irs.service.GroupStudentServiceImpl;
import org.irs.service.OlympiadService;
import org.irs.service.OlympiadServiceImpl;
import org.irs.service.ProgressService;
import org.irs.service.ProgressServiceImpl;
import org.irs.service.PublicationService;
import org.irs.service.PublicationServiceImpl;
import org.irs.service.StudentService;
import org.irs.service.StudentServiceImpl;
import org.irs.service.SubjectService;
import org.irs.service.SubjectServiceImpl;
import org.irs.service.TeacherService;
import org.irs.service.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/*
 * Controller which handles all request
 * connected to information about groups,
 * teachers and subjects
 * */
@Controller
public class DataController {
	
	@Autowired
	private GroupStudentService gss; // get all available operations 
    								 // for GroupStudent table
	
	@Autowired
	private TeacherService ts; // get all available operations 
    						   // for Teacher table

	@Autowired
	private SubjectService ss; // get all available operations 
    						   // for Subject table
	
	@Autowired
	private PublicationService ps;
	
	/*
	 * Handle URL which has type: /addData.html
	 * using GET request method
	 * */
	@RequestMapping(value = "/addData.html", method = RequestMethod.GET)
	public ModelAndView getPage() {
		ModelAndView mav = new ModelAndView("addData");
		
		mav.getModelMap().put("groups", gss.selectAllGroups());
		mav.getModelMap().put("subjects", ss.selectAllSubjects());
		mav.getModelMap().put("teachers", ts.selectAllTeachers());
		
		return mav; 
	}
	
	
	/*
	 * Handle URL which has type: /addData.html
	 * using POST request method
	 * */
	@RequestMapping(value = "/addGroup.html", method = RequestMethod.POST)
	public @ResponseBody String addNewGroup(@RequestBody GroupStudent group){
	
		// check if we have already added current group
		if(gss.selectAllGroups().contains(group)) 
			return "{\"error\":\"errorGroup\"}";
	
		if(!RegularExpression.studentBookExprAndGroup(group.getGroupStudentNumber())
			|| !RegularExpression.checkSizeGroupStudentNumber(group.getGroupStudentNumber())) { 
			return "{\"error\":\"errorGroup\"}";
		} else {
			try {
				gss.insertGroup(group);
			} catch (Exception e) {
				return "{\"error\":\"errorGroup\"}";
			} // add new group
			return "{\"success\":\"successGroup\"}";
		}
	}
	
	@RequestMapping(value = "/addTeacher.html", method = RequestMethod.POST)
	public @ResponseBody String addNewTeacher(@RequestBody Teacher teacher) {
		
		if(!RegularExpression.studentNameAndTeacherSubject(teacher.getTeacherFullName()) ||
		   !RegularExpression.checkSizeTeacherFullName(teacher.getTeacherFullName())) {
			return "{\"error\":\"errorTeacher\"}";
		} else {
			
			try {
				ts.insertTeacher(teacher); // add new teacher
			} catch(Exception e) {
				return "{\"error\":\"errorTeacher\"}";
			}
			return "{\"success\":\"successTeacher\"}";
			
		}
	}
	
	@RequestMapping(value = "/addSubject.html", method = RequestMethod.POST)
	public @ResponseBody String addNewSubject(@RequestBody Subject subject){
		
		// check if we already have added current subject
		if(ss.selectAllSubjects().contains(subject))
			return "{\"error\":\"errorSubject\"}";
			
		if(!RegularExpression.checkSizeSubjectTitle(subject.getSubjectTitle())) {
			
			return "{\"error\":\"errorSubject\"}";
		} else if (subject.getSubjectHours() == 0 || subject.getSubjectHours() < 0 || subject.getSubjectHours() > 450) {
			return "{\"error\":\"errorHour\"}";
		} else {
			
			try {
				ss.insertSubject(subject);
			} catch (Exception e) {
				return "{\"error\":\"errorSubject\"}";
			} // add subject
			return "{\"success\":\"successSubject\"}";
		}
	}
	
	@RequestMapping(value = "/deleteGroup.html", method = RequestMethod.POST)
	public @ResponseBody String deleteGroup(@RequestParam("groupId") Long groupId){
		
		if(groupId != null) {
			gss.deleteGroupById(groupId);
			
			// delete all empty publications
			for(Publication p : ps.selectPublicationsNull()) {
					ps.deletePublication(p);
			}
			
			return "success";
		} else 
			return "error";
	}
	
	@RequestMapping(value = "/deleteTeacher.html", method = RequestMethod.POST)
	public @ResponseBody String deleteTeacher(@RequestParam("teacherId") Long teacherId){
		
		if(teacherId != null) {
			ts.deleteTeacherById(teacherId);
			return "success";
		} else 
			return "error";
	}
	
	@RequestMapping(value = "/deleteSubject.html", method = RequestMethod.POST)
	public @ResponseBody String deleteSubject(@RequestParam("subjectId") Long subjectId){
		
		if(subjectId != null) {
			ss.deleteSubjectById(subjectId);
			return "success";
		} else 
			return "error";
	}
}