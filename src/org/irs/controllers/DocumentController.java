package org.irs.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.RowId;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.CellEditor;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.util.Comparators;
import org.irs.entities.Examination;
import org.irs.entities.GroupStudent;
import org.irs.entities.Olympiad;
import org.irs.entities.Progress;
import org.irs.entities.Publication;
import org.irs.entities.Student;
import org.irs.entities.Subject;
import org.irs.entities.Teacher;
import org.irs.service.ExaminationService;
import org.irs.service.GroupStudentService;
import org.irs.service.OlympiadService;
import org.irs.service.ProgressService;
import org.irs.service.PublicationService;
import org.irs.service.StudentService;
import org.irs.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

/*
 * Controller which handles all request
 * connected to generation of documents
 * */
@Controller
public class DocumentController {
	
	@Autowired
	private GroupStudentService gss;
	
	@Autowired
	private StudentService sts;
	
	@Autowired
	private SubjectService ss;
	
	@Autowired
	private OlympiadService ol;
	
	@Autowired
	private PublicationService ps;
	
	@Autowired
	private ExaminationService es;
	
	@Autowired
	private ProgressService pss;
	
	@RequestMapping(value = "/genDoc.html", method = RequestMethod.GET)
	public ModelAndView getDocument() {
		ModelAndView mav = new ModelAndView("genDocument"); // create MVC object
		   											  // to pass data to JSP page
		
		mav.getModelMap().put("groups", gss.selectAllGroups());
		mav.getModelMap().put("subjects", ss.selectAllSubjects());
		
		return mav;
	}
	
	@RequestMapping(value = "/genDoc.html", method = RequestMethod.POST)
	public ModelAndView postDocument() {
		ModelAndView mav = new ModelAndView("genDocument"); // create MVC object
		   											  // to pass data to JSP page
		
		
		return mav;
	}
	
	/*
	 * Generate document "Підсумковий список"
	 * */
	@RequestMapping(value = "/createRezultDoc.html", method = RequestMethod.POST)
	public @ResponseBody String createRezultList(@RequestParam(value = "groups", required = false) Long[] groups,
										 @RequestParam(value = "okr") int okr,
										 @RequestParam(value = "ids", required = false) Long[] ids) {
		
		// xlsx file
		File file = new File(System.getProperty("user.home") + "/Documents/Підсумковий список.xlsx");
		FileOutputStream out = null;
		
		// check if can write to file
		try {
			out = new FileOutputStream(file);
		} catch(FileNotFoundException e) {
			return "error";	
		}
		
		// create workbook
		Workbook wb = new XSSFWorkbook();
		
		// create sheet
		Sheet sheet = null;
		
		if(okr == 0)
			sheet = wb.createSheet("Підсумковий список спеціалісти");
		
		if(okr == 1)
			sheet = wb.createSheet("Підсумковий список магістри");
		
		// create cell
		Cell cell = null;
		
		// create row
		Row row = null;
		
		/**** create cell styles for header ****/
		
		CellStyle sl1 = wb.createCellStyle(); // fisrt style
		sl1.setAlignment(CellStyle.ALIGN_CENTER);
		
		Font f1 = wb.createFont();
		f1.setFontHeightInPoints((short)12);
		f1.setFontName("Arial Cyr");
		f1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		sl1.setFont(f1);
		
		CellStyle sl2 = wb.createCellStyle(); // second style
		sl2.setAlignment(CellStyle.ALIGN_CENTER);
		
		Font f2 = wb.createFont();
		f2.setFontHeightInPoints((short) 14);
		f2.setFontName("Arial Cyr");
		f2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		sl2.setBorderBottom(CellStyle.BORDER_THIN);
		sl2.setFont(f2);
		
		CellStyle sl3 = wb.createCellStyle(); // third style
		sl3.setAlignment(CellStyle.ALIGN_CENTER);
		
		Font f3 = wb.createFont();
		f3.setFontHeightInPoints((short) 6);
		f3.setFontName("Arial Cyr");
		
		sl3.setFont(f3);
		
		CellStyle sl4 = wb.createCellStyle(); // fourth style
		sl4.setAlignment(CellStyle.ALIGN_CENTER);
		
		Font f4 = wb.createFont();
		f4.setFontHeightInPoints((short) 12);
		f4.setFontName("Arial Cyr");
		
		sl4.setFont(f4);
		
		CellStyle sl5 = wb.createCellStyle(); // fifth style
		sl5.setAlignment(CellStyle.ALIGN_CENTER);
		sl5.setBorderBottom(CellStyle.BORDER_THIN);
		
		Font f5 = wb.createFont();
		f5.setFontHeightInPoints((short) 12);
		f5.setFontName("Arial Cyr");
		
		sl5.setFont(f5);
		
		CellStyle header = wb.createCellStyle();
		header.setAlignment(CellStyle.ALIGN_CENTER);
		header.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		header.setBorderBottom(CellStyle.BORDER_THIN);
		header.setBorderLeft(CellStyle.BORDER_THIN);
		header.setBorderRight(CellStyle.BORDER_THIN);
		header.setBorderTop(CellStyle.BORDER_THIN);
		header.setWrapText(true);
		
		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setFontName("Arial Cyr");
		
		header.setFont(headerFont);
		
		CellStyle headerVertical = wb.createCellStyle();
		headerVertical.setAlignment(CellStyle.ALIGN_CENTER);
		headerVertical.setBorderBottom(CellStyle.BORDER_THIN);
		headerVertical.setBorderLeft(CellStyle.BORDER_THIN);
		headerVertical.setBorderRight(CellStyle.BORDER_THIN);
		headerVertical.setBorderTop(CellStyle.BORDER_THIN);
		headerVertical.setRotation((short) 90);
		headerVertical.setWrapText(true);
		
		Font verticalFont = wb.createFont();
		verticalFont.setFontHeightInPoints((short) 10);
		verticalFont.setFontName("Arial Cyr");
		
		headerVertical.setFont(verticalFont);
		
		
		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
		bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
		bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
		bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
		
		CellStyle nameStyle = wb.createCellStyle();
		nameStyle.setAlignment(CellStyle.ALIGN_LEFT);
		nameStyle.setBorderBottom(CellStyle.BORDER_THIN);
		nameStyle.setBorderLeft(CellStyle.BORDER_THIN);
		nameStyle.setBorderRight(CellStyle.BORDER_THIN);
		nameStyle.setBorderTop(CellStyle.BORDER_THIN);
		
		Font bodyFont = wb.createFont();
		bodyFont.setFontHeightInPoints((short) 10);
		bodyFont.setFontName("Arial Cyr");
		
		bodyStyle.setFont(bodyFont);
		
		CellStyle footer = wb.createCellStyle();
		footer.setAlignment(CellStyle.ALIGN_CENTER);
		footer.setFont(bodyFont);
		
		/**** Create header ****/
	
		row = sheet.createRow(0); // create first row to insert data
		
		// merge cells B1:F1
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 5));
		cell = row.createCell(1);
		cell.setCellStyle(sl1);
		cell.setCellValue("П І Д С У М К О В И Й   С П И С О К");
		
		// merge cells G1:H1
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
		cell = row.createCell(6);
		cell.setCellStyle(sl2);
		cell.setCellValue("ФІОТ (АСОІУ)");
		cell = row.createCell(7);
		cell.setCellStyle(sl2);
		
		row = sheet.createRow(1); // create second row to insert data
		row.setHeightInPoints((float) 12.75);
		
		// merge cells G2:H2
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 7));
		cell = row.createCell(6);
		cell.setCellStyle(sl3);
		cell.setCellValue("скор. назва ф-ту\\ін-ту");
		
		row = sheet.createRow(2); // create third row to insert data
		
		// merge cells B3:D3
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));
		cell = row.createCell(1);
		cell.setCellStyle(sl4);
		
		if(okr == 0)
			cell.setCellValue("випускників на ОППП спеціаліста");
		
		if(okr == 1)
			cell.setCellValue("випускників на ОППП магістра");
		
		// merge cells E3:F3
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));
		cell = row.createCell(4);
		cell.setCellStyle(sl1);
		
		if(okr == 0)
			cell.setCellValue("7.05010101");
		else
			cell.setCellValue("8.05010101");
		
		row = sheet.createRow(3); // create fourth row to insert data
		row.setHeightInPoints((float) 12.75);
		
		// merge cells E4:F4
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 5));
		cell = row.createCell(4);
		cell.setCellStyle(sl3);
		cell.setCellValue("код.");
		
		row = sheet.createRow(4); // create fifth row to insert data
		
		// merge cells B5:H5
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 7));
		cell = row.createCell(1);
		cell.setCellStyle(sl5);
		cell.setCellValue("Інформаційні управляючі системи та технології");
		
		for(int i = 2; i < 8; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(sl5);
		}
		
		row = sheet.createRow(5); // create sixth row to insert data
		row.setHeightInPoints((float) 12.75);
		
		// merge cells B5:H5
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 7));
		cell = row.createCell(1);
		cell.setCellStyle(sl3);
		cell.setCellValue("найменування спеціальності");
		
		/**** Create body ****/
		
		row = sheet.createRow(7); // create 8-th row t insert data
		row.setHeightInPoints((float) 93.00);
		
		cell = row.createCell(0);
		cell.setCellStyle(header);
		cell.setCellValue("№ з\\п");
		
		cell = row.createCell(1);
		cell.setCellStyle(header);
		cell.setCellValue("Прізвище Ім'я по-Батькові");
		
		cell = row.createCell(2);
		cell.setCellStyle(headerVertical);
		cell.setCellValue("Середня оцінка за додатком до диплому");
		
		
		cell = row.createCell(3);
		cell.setCellStyle(headerVertical);
		cell.setCellValue("Оцінка творчих досягнень");
		
		cell = row.createCell(4);
		cell.setCellStyle(headerVertical);
		cell.setCellValue("Академічний рейтинг вступника");
		
		cell = row.createCell(5);
		cell.setCellStyle(headerVertical);
		cell.setCellValue("Оцінка вступного фахового випробування");
		
		cell = row.createCell(6);
		cell.setCellStyle(headerVertical);
		cell.setCellValue("Оцінка іспиту з іноземної мови");
		
		cell = row.createCell(7);
		cell.setCellStyle(headerVertical);
		cell.setCellValue("Інтегральний рейтинг вступника");
		
		// set width of second column
		sheet.autoSizeColumn(1, false);
		
		/* get all students */
		
		int counter = 1; // count quantity of students
		int rowIndex = 8; // we start from 9-th row in Excel
		Double progressMark = 0.0;
		Double achievementMark = 0.0;
		
		for(int i = 0; i < groups.length; i++) { // iterate through all groups
			GroupStudent group = gss.getGroupById(groups[i]); // get group
			
			if(group != null) {
				// iterate through all students
				for(Student student : group.getStudents()) {
					
					// check  if we need to add student
					if(check(student.getStudentId(), ids)) {
						// add them to Excel file
						row = sheet.createRow(rowIndex); // create row
						
						cell = row.createCell(0); // create cell
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(counter);
						
						cell = row.createCell(1); // create cell
						cell.setCellStyle(nameStyle);
						cell.setCellValue(student.getStudentFullName());
						
						cell = row.createCell(2); // create cell
						cell.setCellStyle(bodyStyle);
						progressMark = getProgressMark(student.getStudentId());
						cell.setCellValue(Math.round(progressMark*100.0)/100.0);
						
						cell = row.createCell(3); // create cell
						cell.setCellStyle(bodyStyle);
						achievementMark = getAchievementMark(student.getStudentId()); 
						cell.setCellValue(Math.round(achievementMark*100.0)/100.0);
						
						cell = row.createCell(4); // create cell
						cell.setCellStyle(bodyStyle);
						cell.setCellFormula("SUM(C"+(rowIndex+1) + ":D"+ (rowIndex+1)+")"); // set formula
						
						cell = row.createCell(5); // create cell
						cell.setCellStyle(bodyStyle);
						ExamMark examValue = getExamMark(student.getStudentId());
						cell.setCellValue(Math.round(examValue.main*100.0)/100.0);
						
						cell = row.createCell(6); // create cell
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(Math.round(examValue.english*100.0)/100.0);
						
						cell = row.createCell(7); // create cell
						cell.setCellStyle(bodyStyle);
						cell.setCellFormula("SUM(E"+(rowIndex+1) + ":G"+ (rowIndex+1)+")"); // set formula
						
						counter++;
						rowIndex++;
					}
				}
			}
		}
		
		/**** Create footer ****/
		
		row = sheet.createRow(rowIndex+2); // create row 
		
		cell = row.createCell(1);
		cell.setCellStyle(footer);
		cell.setCellValue("Голова атестаційної комісії");
		
		// merge cells G(rowIndex+2):H(rowIndex+2)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex+2, rowIndex+2, 6, 7));
		
		cell = row.createCell(6);
		cell.setCellStyle(footer);
		cell.setCellValue("О.А.Павлов");
		
		row = sheet.createRow(rowIndex+3); // create row 
		row.setHeightInPoints((float) 12.75);
		
		// merge cells G(rowIndex+2):H(rowIndex+2)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex+3, rowIndex+3, 6, 7));
		
		cell = row.createCell(6);
		cell.setCellStyle(sl3);
		cell.setCellValue("І.П. Прізвише");
		
		rowIndex += 4;
		
		row = sheet.createRow(rowIndex+2); // create row 
		
		cell = row.createCell(1);
		cell.setCellStyle(footer);
		cell.setCellValue("Підголова атестаційної комісії");
		
		// merge cells G(rowIndex+2):H(rowIndex+2)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex+2, rowIndex+2, 6, 7));
		
		cell = row.createCell(6);
		cell.setCellStyle(footer);
		cell.setCellValue("О.А.Павлов");
		
		row = sheet.createRow(rowIndex+3); // create row 
		row.setHeightInPoints((float) 12.75);
		
		// merge cells G(rowIndex+2):H(rowIndex+2)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex+3, rowIndex+3, 6, 7));
		
		cell = row.createCell(6);
		cell.setCellStyle(sl3);
		cell.setCellValue("І.П. Прізвише");
		
		sheet.setColumnWidth(1, 10000);
		
		try {
			wb.write(out); // write to file 
			out.close();
		} catch (IOException e) {
			return "error";
		} // close stream
		
		return "success";
	}
	
	/* 
	 * Check student to be added to excel
	 * */
	private boolean check(Long id, Long[] ids) {
		
		if(ids != null) 
			for(Long i : ids)
				if(id.equals(i))
					return false;
		
		
		return true;
	}
	
	/*
	 * Generate document "Публікації"
	 * */
	@RequestMapping(value = "/createPublicationDoc.html", method = RequestMethod.POST)
	public @ResponseBody String createPublicationList(@RequestParam(value = "year", required = false) Integer year,
													  @RequestParam(value = "docType", required = false) Integer docType) {
		
		if (year == null || year < 2005 || year > 2070)
			return "errorYear";
		
		// get all publications by year
		// xlsx file
		File file = null; 
		
		if(docType > 3)
			file = new File(System.getProperty("user.home") + "/Documents/Пубцікації за " + year + "(тези).xlsx");
		else
			file = new File(System.getProperty("user.home") + "/Documents/Пубцікації за " + year + "(статті).xlsx");
		
		FileOutputStream out = null;
		

		List<Publication> list = null; // list of all publications
		
		// check if can write to file
		try {
			out = new FileOutputStream(file);
		} catch(FileNotFoundException e) {
			return "error";	
		}
		
		// create workbook
		Workbook wb = new XSSFWorkbook();
		
		// create sheet
		Sheet sheet = null;
		
		switch(docType) { // choose sheet name
			case 1: sheet = wb.createSheet("Статті загалом"); break;
			case 2: sheet = wb.createSheet("Статті студентів"); break;
			case 3: sheet = wb.createSheet("Статті викладачів"); break;
			case 4: sheet = wb.createSheet("Тези загалом"); break;
			case 5: sheet = wb.createSheet("Тези студентів"); break;
			case 6: sheet = wb.createSheet("Тези викладачів"); break;
		}
		
		// create cell
		Cell cell = null;
		
		// create row
		Row row = null;
		
		/**** Create cell style ****/
		
		CellStyle header = wb.createCellStyle();
		header.setAlignment(CellStyle.ALIGN_CENTER);
		header.setBorderBottom(CellStyle.BORDER_THIN);
		header.setBorderTop(CellStyle.BORDER_THIN);
		header.setBorderRight(CellStyle.BORDER_THIN);
		header.setBorderLeft(CellStyle.BORDER_THIN);
		
		Font headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setFontName("Times New Roman");
		headerFont.setFontHeightInPoints((short) 14);
		
		header.setFont(headerFont);
		
		CellStyle subHeader = wb.createCellStyle();
		subHeader.setAlignment(CellStyle.ALIGN_CENTER);
		
		Font subHeaderFont = wb.createFont();
		subHeaderFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		subHeaderFont.setItalic(true);
		subHeaderFont.setFontName("Times New Roman");
		subHeaderFont.setFontHeightInPoints((short) 14);
		
		subHeader.setFont(subHeaderFont);
		
		CellStyle number = wb.createCellStyle(); // counter style
		number.setAlignment(CellStyle.ALIGN_CENTER);
		number.setBorderBottom(CellStyle.BORDER_THIN);
		number.setBorderTop(CellStyle.BORDER_THIN);
		number.setBorderRight(CellStyle.BORDER_THIN);
		number.setBorderLeft(CellStyle.BORDER_THIN);
		
		CellStyle body = wb.createCellStyle();
		body.setWrapText(true);
		body.setAlignment(CellStyle.ALIGN_LEFT);
		body.setBorderBottom(CellStyle.BORDER_THIN);
		body.setBorderTop(CellStyle.BORDER_THIN);
		body.setBorderRight(CellStyle.BORDER_THIN);
		body.setBorderLeft(CellStyle.BORDER_THIN);
		
		CellStyle nmb = wb.createCellStyle();
		nmb.setAlignment(CellStyle.ALIGN_LEFT);
		nmb.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		nmb.setBorderBottom(CellStyle.BORDER_THIN);
		nmb.setBorderTop(CellStyle.BORDER_THIN);
		nmb.setBorderRight(CellStyle.BORDER_THIN);
		nmb.setBorderLeft(CellStyle.BORDER_THIN);
		
		// merge cells A1:B1
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
		
		row = sheet.createRow(0); // 1-st row
		cell = row.createCell(0); 
		cell.setCellStyle(header);
		
		StringBuilder name = new StringBuilder();
		name.append("Наукові досягнення кафедри АСОІУ за " + year + " рік ");
		
		switch(docType) { // choose sheet name
			case 1: name.append("(Статті загалом)"); break;
			case 2: name.append("(Статті студентів)"); break;
			case 3: name.append("(Статті викладачів)"); break;
			case 4: name.append("(Тези доповіді)"); break;
			case 5: name.append("(Тези доповіді студентів)"); break;
			case 6: name.append("(Тези доповіді викладачів)"); break;
		}
		
		
		cell.setCellValue(name.toString());
		cell = row.createCell(1); 
		cell.setCellStyle(header);
		cell = row.createCell(2); 
		cell.setCellStyle(header);
		
		// merge cells B3:C3
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
		row = sheet.createRow(2); // 3-d row
		cell = row.createCell(0);
		cell.setCellStyle(header);
		cell.setCellValue("№ з/п");
		
		cell = row.createCell(1);
		cell.setCellStyle(header);
		
		if(docType < 4)
			cell.setCellValue("Інформація про статтю");
		else
			cell.setCellValue("Інформація про тези доповіді");
			
		cell = row.createCell(2);
		cell.setCellStyle(header);
		
		sheet.setColumnWidth(1, 30000);
		
		// merge cells A4:B4
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
		row = sheet.createRow(3); // 4-th row
		cell = row.createCell(0);
		cell.setCellStyle(subHeader);
		
		if(docType < 4) {
			cell.setCellValue("Міжнародні журнали та збірники наукових праць");
			list = ps.selectPublicationsByTypeThesis("Міжнародна конференція", year, 2);
		} else {
			cell.setCellValue("Виступи на міжнародних конференціях");
			list = ps.selectPublicationsByTypeThesis("Міжнародна конференція", year, 1);
		}
		
		/*
		 * 'Міжнародна конференція'
		 * 'Всеукраїнська конференція'
		 * 'Університетська'
		 * */
		int counter = 1;
		int rowIndex = 4; // start from 5-th row
		
		// insert all publications with type = "Міжнародні ..." and year = "year" (and thesis = "thesis")
		for(Publication p : list) {
			
			switch(docType) {
				case 1: // general articles

					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter	
					
					// add publication
					cell = row.createCell(1);
					cell.setCellStyle(body);
					cell.setCellValue(getFullPublicationOrThesis(p, 1));
					
					cell = row.createCell(2);
					cell.setCellStyle(nmb);
					cell.setCellValue(p.getPublicationBase() == 1 ? "Знаходиться в науковометричній базі" : "");
					
					rowIndex++;
					break;
				case 2: // students articles
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter
					
					if(p.getStudent() == null) { // if we do not have teachers 
						
						// add publication
						cell = row.createCell(1);
						cell.setCellStyle(body);
						cell.setCellValue(getFullPublicationOrThesis(p, 1));
						
						cell = row.createCell(2);
						cell.setCellStyle(nmb);
						cell.setCellValue(p.getPublicationBase() == 1 ? "Знаходиться в науковометричній базі" : "");
						
						rowIndex++;	
					}
					break;
				case 3: // teachers articles
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter
					
					if(p.getTeachers() != null) { // if we have teachers		
						
						// add publication
						cell = row.createCell(1);
						cell.setCellStyle(body);
						cell.setCellValue(getFullPublicationOrThesis(p, 1));
						
						cell = row.createCell(2);
						cell.setCellStyle(nmb);
						cell.setCellValue(p.getPublicationBase() == 1 ? "Знаходиться в науковометричній базі" : "");
						
						rowIndex++;	
					}
					break;
				case 4: // general thesis
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter	
					
					// add publication
					cell = row.createCell(1);
					cell.setCellStyle(body);
					cell.setCellValue(getFullPublicationOrThesis(p, 2));
					rowIndex++;
					break;
				case 5: // students thesis
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter
					
					if(p.getStudent() == null) { // if we do not have teachers 
						
						// add publication
						cell = row.createCell(1);
						cell.setCellStyle(body);
						cell.setCellValue(getFullPublicationOrThesis(p, 2));
						rowIndex++;	
					}
					break;
				case 6: // teachers thesis
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter
					
					if(p.getTeachers() != null) { // if we have teachers		
						
						// add publication
						cell = row.createCell(1);
						cell.setCellStyle(body);
						cell.setCellValue(getFullPublicationOrThesis(p, 2));
						rowIndex++;	
					}
					break;
			}
		}
		
		/*
		 * "Міжнародні журнали та збірники наукових праць"
		 * "Всеукраїнський науковий журнал"
		 * "Університетська збірка наукових праць"
		 * */
		
		rowIndex+=2;
		
		// merge cells A(rowIndex+1):B(rowIndex+1)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));
		row = sheet.createRow(rowIndex++);
		cell = row.createCell(0);
		cell.setCellStyle(subHeader);
		
		if(docType < 4) {
			cell.setCellValue("Всеукраїнський науковий журнал");
			list = ps.selectPublicationsByTypeThesis("Всеукраїнська конференція", year, 2);
		} else {
			cell.setCellValue("Виступи на всеукраїнських конференціях");
			list = ps.selectPublicationsByTypeThesis("Всеукраїнська конференція", year, 1);
		}
		
		if(list.size() == 0) {
			row = sheet.createRow(rowIndex); // create row
			cell = row.createCell(0);
			cell.setCellStyle(number);
			cell.setCellValue(counter); // add counter
			
			cell = row.createCell(1);
			cell.setCellStyle(body);
		}
		
		// insert all publications with type = "Всеукраїнський ..." and year = "year"
		for(Publication p : list) {
			
			switch(docType) {
				case 1: // general articles

					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter	
					
					// add publication
					cell = row.createCell(1);
					cell.setCellStyle(body);
					cell.setCellValue(getFullPublicationOrThesis(p, 1));
					
					cell = row.createCell(2);
					cell.setCellStyle(nmb);
					cell.setCellValue(p.getPublicationBase() == 1 ? "Знаходиться в науковометричній базі" : "");
					
					rowIndex++;
					break;
				case 2: // students articles
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter
					
					if(p.getStudent() == null) { // if we do not have teachers 
						
						// add publication
						cell = row.createCell(1);
						cell.setCellStyle(body);
						cell.setCellValue(getFullPublicationOrThesis(p, 1));
						
						cell = row.createCell(2);
						cell.setCellStyle(nmb);
						cell.setCellValue(p.getPublicationBase() == 1 ? "Знаходиться в науковометричній базі" : "");
						
						rowIndex++;	
					}
					break;
				case 3: // teachers articles
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter
					
					if(p.getTeachers() != null) {// if we have teachers		
						
						// add publication
						cell = row.createCell(1);
						cell.setCellStyle(body);
						cell.setCellValue(getFullPublicationOrThesis(p, 1));
						
						cell = row.createCell(2);
						cell.setCellStyle(nmb);
						cell.setCellValue(p.getPublicationBase() == 1 ? "Знаходиться в науковометричній базі" : "");
						
						rowIndex++;
					}
					break;
				case 4: // general thesis
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter	
					
					// add publication
					cell = row.createCell(1);
					cell.setCellStyle(body);
					cell.setCellValue(getFullPublicationOrThesis(p, 2));
					rowIndex++;
					break;
				case 5: // students thesis
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter
					
					if(p.getStudent() == null) {// if we do not have teachers 
						
						// add publication
						cell = row.createCell(1);
						cell.setCellStyle(body);
						cell.setCellValue(getFullPublicationOrThesis(p, 2));
						rowIndex++;	
					}
					break;
				case 6: // teachers thesis
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter
					
					if(p.getTeachers() != null) {// if we have teachers		
						
						// add publication
						cell = row.createCell(1);
						cell.setCellStyle(body);
						cell.setCellValue(getFullPublicationOrThesis(p, 2));
						rowIndex++;	
					}
					break;
			}
		}
		
		rowIndex+=2;

		// merge cells A(rowIndex+1):B(rowIndex+1)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 1));
		row = sheet.createRow(rowIndex++);
		cell = row.createCell(0);
		cell.setCellStyle(subHeader);
		
		if(docType < 4) {
			cell.setCellValue("Університетська збірка наукових праць");
			list = ps.selectPublicationsByTypeThesis("Університетська", year, 2);
		} else {
			cell.setCellValue("Виступи на університетських конференціях");
			list = ps.selectPublicationsByTypeThesis("Університетська", year, 1);
		}
			
		if(list.size() == 0) {
			row = sheet.createRow(rowIndex); // create row
			cell = row.createCell(0);
			cell.setCellStyle(number);
			cell.setCellValue(counter); // add counter
			
			cell = row.createCell(1);
			cell.setCellStyle(body);
		}
		
		// insert all publications with type = "Університетська ..." and year = "year"
		for(Publication p : list) {
			
			switch(docType) {
				case 1: // general articles

					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter	
					
					// add publication
					cell = row.createCell(1);
					cell.setCellStyle(body);
					cell.setCellValue(getFullPublicationOrThesis(p, 1));
					
					cell = row.createCell(2);
					cell.setCellStyle(nmb);
					cell.setCellValue(p.getPublicationBase() == 1 ? "Знаходиться в науковометричній базі" : "");
					
					rowIndex++;
					break;
				case 2: // students articles
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter
					
					if(p.getStudent() == null) {// if we do not have teachers 
						
						// add publication
						cell = row.createCell(1);
						cell.setCellStyle(body);
						cell.setCellValue(getFullPublicationOrThesis(p, 1));
						
						cell = row.createCell(2);
						cell.setCellStyle(nmb);
						cell.setCellValue(p.getPublicationBase() == 1 ? "Знаходиться в науковометричній базі" : "");
						
						rowIndex++;	
					}
					break;
				case 3: // teachers articles
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter
					
					if(p.getTeachers() != null) {// if we have teachers		
						
						// add publication
						cell = row.createCell(1);
						cell.setCellStyle(body);
						cell.setCellValue(getFullPublicationOrThesis(p, 1));
						
						cell = row.createCell(2);
						cell.setCellStyle(nmb);
						cell.setCellValue(p.getPublicationBase() == 1 ? "Знаходиться в науковометричній базі" : "");
						
						rowIndex++;
					}
					break;
				case 4: // general thesis
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter	
					
					// add publication
					cell = row.createCell(1);
					cell.setCellStyle(body);
					cell.setCellValue(getFullPublicationOrThesis(p, 2));
					rowIndex++;
					break;
				case 5: // students thesis
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter
					
					if(p.getStudent() == null) {// if we do not have teachers 
						
						// add publication
						cell = row.createCell(1);
						cell.setCellStyle(body);
						cell.setCellValue(getFullPublicationOrThesis(p, 2));
						rowIndex++;	
					}
					break;
				case 6: // teachers thesis
					
					row = sheet.createRow(rowIndex); // create row
					cell = row.createCell(0);
					cell.setCellStyle(number);
					cell.setCellValue(counter); // add counter
					
					if(p.getTeachers() != null) { // if we have teachers		
							
						// add publication
						cell = row.createCell(1);
						cell.setCellStyle(body);
						cell.setCellValue(getFullPublicationOrThesis(p, 2));
						rowIndex++;	
					}
					break;
			}
		}
		
		sheet.setColumnWidth(2, 9200);
		
		try {
			wb.write(out); // write to file 
			out.close();
		} catch (IOException e) {
			return "error";
		} // close stream
		
		return "success";
	}
	
	@RequestMapping(value = "/createOlympiadDoc.html", method = RequestMethod.POST)
	public @ResponseBody String createOlympDoc(@RequestParam(value = "year") Integer year) {
		
		if (year == null || year < 2005 || year > 2070)
			return "errorYear";
		
		// get all olympiad by year
		// xlsx file
		File file = new File(System.getProperty("user.home") + "/Documents/Олімпіади за " + year + ".xlsx");
		FileOutputStream out = null;
		

		List<Olympiad> list = null; // list of all olympiads
		
		// check if can write to file
		try {
			out = new FileOutputStream(file);
		} catch(FileNotFoundException e) {
			return "error";	
		}
		
		// create workbook
		Workbook wb = new XSSFWorkbook();
		
		// create sheet
		Sheet sheet = wb.createSheet("Олімпіади за " + year);
		
		// create cell
		Cell cell = null;
		
		// create row
		Row row = null;
		
		/**** Create cell style ****/
		
		CellStyle header = wb.createCellStyle();
		header.setAlignment(CellStyle.ALIGN_CENTER);
		header.setBorderBottom(CellStyle.BORDER_THIN);
		header.setBorderTop(CellStyle.BORDER_THIN);
		header.setBorderRight(CellStyle.BORDER_THIN);
		header.setBorderLeft(CellStyle.BORDER_THIN);
		
		Font headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setFontName("Times New Roman");
		headerFont.setFontHeightInPoints((short) 14);
		
		header.setFont(headerFont);
		
		CellStyle subHeader = wb.createCellStyle();
		subHeader.setAlignment(CellStyle.ALIGN_CENTER);
		
		Font subHeaderFont = wb.createFont();
		subHeaderFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		subHeaderFont.setItalic(true);
		subHeaderFont.setFontName("Times New Roman");
		subHeaderFont.setFontHeightInPoints((short) 14);
		
		subHeader.setFont(subHeaderFont);
		
		CellStyle number = wb.createCellStyle(); // counter style
		number.setAlignment(CellStyle.ALIGN_CENTER);
		number.setBorderBottom(CellStyle.BORDER_THIN);
		number.setBorderTop(CellStyle.BORDER_THIN);
		number.setBorderRight(CellStyle.BORDER_THIN);
		number.setBorderLeft(CellStyle.BORDER_THIN);
		
		CellStyle body = wb.createCellStyle();
		body.setWrapText(true);
		body.setAlignment(CellStyle.ALIGN_LEFT);
		body.setBorderBottom(CellStyle.BORDER_THIN);
		body.setBorderTop(CellStyle.BORDER_THIN);
		body.setBorderRight(CellStyle.BORDER_THIN);
		body.setBorderLeft(CellStyle.BORDER_THIN);
		
		// merge cells A1:B1
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,2));
		
		row = sheet.createRow(0); // 1-st row
		cell = row.createCell(0); 
		cell.setCellStyle(header);
		
		StringBuilder name = new StringBuilder();
		name.append("Наукові досягнення кафедри АСОІУ за " + year +" рік (олімпіади)");
		
		cell.setCellValue(name.toString());
		cell = row.createCell(1); 
		cell.setCellStyle(header);
		
		cell = row.createCell(2); 
		cell.setCellStyle(header);
		
		
		// merge cells B3:C3
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
		row = sheet.createRow(2); // 3-d row
		cell = row.createCell(0);
		cell.setCellStyle(header);
		cell.setCellValue("№ з/п");
		
		cell = row.createCell(1);
		cell.setCellStyle(header);
		cell.setCellValue("Інформація про олімпіаду");
		
		cell = row.createCell(2);
		cell.setCellStyle(header);
		
		sheet.setColumnWidth(1, 30000);
		
		// merge cells A4:B4
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 2));
		row = sheet.createRow(3); // 4-th row
		cell = row.createCell(0);
		cell.setCellStyle(subHeader);
				
		cell.setCellValue("Міжнародні олімпіади");
		
		int rowIndex = 4;
		int counter = 1;
		List<Olympiad> listOl = null;
 		
		listOl = ol.selectOlympiadsByYear(year, "Міжнародна");
		
		if(listOl.size() == 0) {
			row = sheet.createRow(rowIndex);
			
			cell = row.createCell(0);
			cell.setCellStyle(number);
			cell.setCellValue(counter);
			
			
			cell = row.createCell(1);
			cell.setCellStyle(body);
		}
		
		listOl = ol.selectOlympiadsByYear(year, "Міжнародна");
		
		for(Olympiad o : listOl) {
			row = sheet.createRow(rowIndex);
			
			cell = row.createCell(0);
			cell.setCellStyle(number);
			cell.setCellValue(counter);
			
			
			cell = row.createCell(1);
			cell.setCellStyle(body);
			cell.setCellValue(sumOlymiad(o));
			
			cell = row.createCell(2);
			cell.setCellStyle(body);
			cell.setCellValue(o.getOlympiadCore().equals("True") ? "Профільна" : "Непрофільна");
			
			rowIndex++;
		}
		
		rowIndex += 2;
		
		// merge cells A(rowIndex+1):B(rowIndex+1)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 2));
		row = sheet.createRow(rowIndex++);
		cell = row.createCell(0);
		cell.setCellStyle(subHeader);
		
		cell.setCellValue("Всеукраїнські олімпіади");
		
		listOl = ol.selectOlympiadsByYear(year, "Всеукраїнська");
		
		if(listOl.size() == 0) {
			row = sheet.createRow(rowIndex);
			
			cell = row.createCell(0);
			cell.setCellStyle(number);
			cell.setCellValue(counter);
			
			
			cell = row.createCell(1);
			cell.setCellStyle(body);
		}
		
		for(Olympiad o : listOl) {
			row = sheet.createRow(rowIndex);
			
			cell = row.createCell(0);
			cell.setCellStyle(number);
			cell.setCellValue(counter);
			
			
			cell = row.createCell(1);
			cell.setCellStyle(body);
			cell.setCellValue(sumOlymiad(o));
			
			cell = row.createCell(2);
			cell.setCellStyle(body);
			cell.setCellValue(o.getOlympiadCore().equals("True") ? "Профільна" : "Непрофільна");
			
			rowIndex++;
		}
		
		rowIndex += 2;
		
		// merge cells A(rowIndex+1):B(rowIndex+1)
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 2));
		row = sheet.createRow(rowIndex++);
		cell = row.createCell(0);
		cell.setCellStyle(subHeader);
		
		cell.setCellValue("Університетські олімпіади");
		
		listOl = ol.selectOlympiadsByYear(year, "Університетська");
		
		if(listOl.size() == 0) {
			row = sheet.createRow(rowIndex);
			
			cell = row.createCell(0);
			cell.setCellStyle(number);
			cell.setCellValue(counter);
			
			
			cell = row.createCell(1);
			cell.setCellStyle(body);
		}
		
		for(Olympiad o : listOl) {
			row = sheet.createRow(rowIndex);
			
			cell = row.createCell(0);
			cell.setCellStyle(number);
			cell.setCellValue(counter);
			
			
			cell = row.createCell(1);
			cell.setCellStyle(body);
			cell.setCellValue(sumOlymiad(o));
			
			cell = row.createCell(2);
			cell.setCellStyle(body);
			cell.setCellValue(o.getOlympiadCore().equals("True") ? "Профільна" : "Непрофільна");
			
			rowIndex++;
		}
		
		sheet.setColumnWidth(2, 3300);
		
		try {
			wb.write(out); // write to file 
			out.close();
		} catch (IOException e) {
			return "error";
		} // close stream
		
		return "success";
	}
	
	private String sumOlymiad(Olympiad ol) {
		StringBuilder str = new StringBuilder(300);
		
		Student s = ol.getStudent();
		
		str.append(converName(s.getStudentFullName())); // add student
		str.append(", ");
		str.append(s.getGroupStudent().getGroupStudentNumber()); // add group
		str.append(", ");
		
		// olympiad result
		switch(ol.getOlympiadPlace()) {
			case 1: str.append("І місце"); break;
			case 2: str.append("ІІ місце"); break;
			case 3: str.append("ІІІ місце"); break;
		}
		
		str.append(", ");
		str.append(ol.getOlympiadDirection() + ' ');
		str.append(", ");
		
		str.append(ol.getOlympiadDate());
		str.append(" рік");
		
		return str.toString();
	}
	
	@RequestMapping(value = "/createProgDoc.html", method = RequestMethod.POST)
	public @ResponseBody String createProgDoc(@RequestParam(value = "yearProg", required = false) Integer year,
											  @RequestParam(value = "subjects") Long[] s,
											  @RequestParam(value = "length") Integer len,
											  @RequestParam(value = "type") Integer type,
											  @RequestParam(value = "group", required = false) Long group) {
	
		if(type == 1)
			if((year == null || year < 2005))
				return "errorYear";
		
		if(type == 2)
			if(group == null || group < -1)
				return "errorGroup";
		
		Long[][] subjects = new Long[len][2];
		
		/* convert array */
		for(int  i = 0; i < len; i++) {
			subjects[i][0] = s[2*i];
			subjects[i][1] = s[2*i+1];
		}
		
		// get all publications by year
		// xlsx file
		File file = null;
		if(type == 1)
			file = new File(System.getProperty("user.home") + "/Documents/Рейтинг за " + year + ".xlsx");
		else
			file = new File(System.getProperty("user.home") + "/Documents/Рейтинг групи " + gss.getGroupById(group).getGroupStudentNumber() + ".xlsx");
			
		FileOutputStream out = null;
		
		// check if can write to file
		try {
			out = new FileOutputStream(file);
		} catch(FileNotFoundException e) {
			return "error";	
		}
		
		// create workbook
		Workbook wb = new XSSFWorkbook(); 
		
		// create sheets
		Sheet sheet = wb.createSheet("Заліки"); // "заліки"
		Sheet sheetTest = wb.createSheet("Диф. заліки"); // "Диф. заліки"
		Sheet sheetExam = wb.createSheet("Екзамени"); // "екзамени"
		Sheet sheetCourse = wb.createSheet("Курсові роботи (проекти)"); // "Курсач"
		
		
		// create row
		Row row = sheet.createRow(6);
		Row rowExam = sheetExam.createRow(6);
		Row rowTest = sheetTest.createRow(6);
		Row rowCourse = sheetCourse.createRow(6);
		
		// create cell
		Cell cell = null;
		
		Name name; // create cell name

		name = wb.createName();
		name.setNameName("five"); // set cell Name
		name.setRefersToFormula("'" + sheet.getSheetName() +"'!$E$3");

		name = wb.createName();
		name.setNameName("ffive"); // set cell Name
		name.setRefersToFormula("'" + sheet.getSheetName() +"'!$F$3");
		
		name = wb.createName();
		name.setNameName("four"); // set cell Name
		name.setRefersToFormula("'" + sheet.getSheetName() +"'!$G$3");
		
		name = wb.createName();
		name.setNameName("tfour"); // set cell Name
		name.setRefersToFormula("'" + sheet.getSheetName() +"'!$H$3");
		
		name = wb.createName();
		name.setNameName("three"); // set cell Name
		name.setRefersToFormula("'" + sheet.getSheetName() +"'!$I$3");
		
		row = sheet.createRow(1);
		String[] mark = {"A", "B", "C", "D", "E"};
		for(int i = 4; i < 9; i++) {
			cell = row.createCell(i);
			cell.setCellValue(mark[i-4]);
		}
		
		row = sheet.createRow(2);
		double[] mark2 = {5.0, 4.5, 4.0, 3.5, 3.0};
		for(int i = 4; i < 9; i++) {
			cell = row.createCell(i, Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(mark2[i-4]);
		}
	
		/* Cell styles */
		CellStyle subjectStyle = wb.createCellStyle();
		subjectStyle.setRotation((short)90);
		subjectStyle.setAlignment(CellStyle.ALIGN_LEFT);
		subjectStyle.setWrapText(true);
		subjectStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
		subjectStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
		subjectStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
		subjectStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
		subjectStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		subjectStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		Font subjectFont = wb.createFont();
		subjectFont.setFontHeightInPoints((short)10);
		subjectFont.setFontName("Arial");
		
		subjectStyle.setFont(subjectFont);
		
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		
		cellStyle.setFont(subjectFont);
		
		CellStyle numCellStyle = wb.createCellStyle();
		numCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		numCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		numCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		numCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		numCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		numCellStyle.setDataFormat(wb.createDataFormat().getFormat("0.00"));
		numCellStyle.setFont(subjectFont);
		
		CellStyle percentStyle = wb.createCellStyle();
		percentStyle.setAlignment(CellStyle.ALIGN_CENTER);
		percentStyle.setBorderLeft(CellStyle.BORDER_THIN);
		percentStyle.setBorderTop(CellStyle.BORDER_THIN);
		percentStyle.setBorderRight(CellStyle.BORDER_THIN);
		percentStyle.setBorderBottom(CellStyle.BORDER_THIN);
		percentStyle.setDataFormat(wb.createDataFormat().getFormat("0.00%"));
		percentStyle.setFont(subjectFont);
		
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
		headerStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
		headerStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
		headerStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
		
		headerStyle.setFont(subjectFont);
		
		CellStyle nameStyle = wb.createCellStyle();
		nameStyle.setAlignment(CellStyle.ALIGN_CENTER);
		nameStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		nameStyle.setFont(subjectFont);
		
		/* =============================== */
		
		row = sheet.createRow(7);
		cell = row.createCell(1);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("№ п/п");
		
		rowTest = sheetTest.createRow(7);
		cell = rowTest.createCell(1);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("№ п/п");
		
		rowExam = sheetExam.createRow(7);
		cell = rowExam.createCell(1);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("№ п/п");
		
		rowCourse = sheetCourse.createRow(7);
		cell = rowCourse.createCell(1);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("№ п/п");
		
		sheet.setColumnWidth(2, 9600);
		sheetExam.setColumnWidth(2, 9600);
		sheetTest.setColumnWidth(2, 9600);
		sheetCourse.setColumnWidth(2, 9600);
		
		cell = row.createCell(2);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Прізвище Ім'я По батькові");
		
		cell = rowExam.createCell(2);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Прізвище Ім'я По батькові");
		
		cell = rowTest.createCell(2);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Прізвище Ім'я По батькові");
		
		cell = rowCourse.createCell(2);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Прізвище Ім'я По батькові");
		
		sheet.setColumnWidth(3, 2560);
		sheetExam.setColumnWidth(3, 2560);
		sheetTest.setColumnWidth(3, 2560);
		sheetCourse.setColumnWidth(3, 2560);
		
		cell = row.createCell(3);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Група");
		
		cell = rowExam.createCell(3);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Група");
		
		cell = rowTest.createCell(3);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Група");
		
		cell = rowCourse.createCell(3);
		cell.setCellStyle(headerStyle);
		cell.setCellValue("Група");
		
		// sortArray of subjects
		Arrays.sort(subjects, new Comparator<Long[]>() {

			@Override
			public int compare(Long[] o1, Long[] o2) {
				
				return o1[1].compareTo(o2[1]);
			}
			
		});
		
		int rowIndex = 8; // row index for adding students and marks
		int cellIndexCourse = 4, cellIndexTest = 4, cellIndex = 4, cellIndexExam = 4 ; // cell index to add subjects
		int counter = 1;
		int lastCellCourse = 0, lastCellExam = 0, lastCellTest = 0, lastCell = 0;
		
		List<Student> students = null;
		
		if(type == 1)
			students = sts.selectStudentByYear(year); // get all students by :year (asc)
		
		if(type == 2)
			students = sts.selectStudentByGroup(gss.getGroupById(group).getGroupStudentNumber()); // get all students by group
		
		Long[] studentIds = new Long[students.size()];
		
		// add each student to file
		for(Student st : students) {
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, 3, 3));
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, 2, 2));
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, 1, 1));
			
			sheetExam.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, 3, 3));
			sheetExam.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, 2, 2));
			sheetExam.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, 1, 1));
			
			sheetTest.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, 3, 3));
			sheetTest.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, 2, 2));
			sheetTest.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, 1, 1));
			
			sheetCourse.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, 3, 3));
			sheetCourse.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, 2, 2));
			sheetCourse.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, 1, 1));
			
			row = sheet.createRow(rowIndex); // create row
			cell = row.createCell(1);
			cell.setCellStyle(nameStyle);
			cell.setCellValue(counter);
			
			rowExam = sheetExam.createRow(rowIndex); // create row
			cell = rowExam.createCell(1);
			cell.setCellStyle(nameStyle);
			cell.setCellValue(counter);
			
			rowTest = sheetTest.createRow(rowIndex); // create row
			cell = rowTest.createCell(1);
			cell.setCellStyle(nameStyle);
			cell.setCellValue(counter);
			
			rowCourse = sheetCourse.createRow(rowIndex); // create row
			cell = rowCourse.createCell(1);
			cell.setCellStyle(nameStyle);
			cell.setCellValue(counter);
			
			cell = row.createCell(2); // create cell
			cell.setCellStyle(nameStyle);
			cell.setCellValue(st.getStudentFullName()); // add student name to cell
			
			cell = rowExam.createCell(2); // create cell
			cell.setCellStyle(nameStyle);
			cell.setCellValue(st.getStudentFullName()); // add student name to cell
			
			cell = rowTest.createCell(2); // create cell
			cell.setCellStyle(nameStyle);
			cell.setCellValue(st.getStudentFullName()); // add student name to cell
			
			cell = rowCourse.createCell(2); // create cell
			cell.setCellStyle(nameStyle);
			cell.setCellValue(st.getStudentFullName()); // add student name to cell
			
			cell = row.createCell(3); // create cell
			cell.setCellStyle(nameStyle);
			cell.setCellValue(st.getGroupStudent().getGroupStudentNumber()); // add student name to cell
			
			cell = rowExam.createCell(3); // create cell
			cell.setCellStyle(nameStyle);
			cell.setCellValue(st.getGroupStudent().getGroupStudentNumber()); // add student name to cell
			
			cell = rowTest.createCell(3); // create cell
			cell.setCellStyle(nameStyle);
			cell.setCellValue(st.getGroupStudent().getGroupStudentNumber()); // add student name to cell
			
			cell = rowCourse.createCell(3); // create cell
			cell.setCellStyle(nameStyle);
			cell.setCellValue(st.getGroupStudent().getGroupStudentNumber()); // add student name to cell
			
			studentIds[counter-1] = st.getStudentId();
			rowIndex+=2; // increase rowIndex
			counter++;
		}
		
		Row subjectRow = sheet.getRow(7);
		subjectRow.setHeightInPoints((float) 115.5);
		
		Row subjectRowExam = sheetExam.getRow(7);
		subjectRowExam.setHeightInPoints((float) 115.5);
		
		Row subjectRowTest = sheetTest.getRow(7);
		subjectRowTest.setHeightInPoints((float) 115.5);
		
		Row subjectRowCourse = sheetCourse.getRow(7);
		subjectRowCourse.setHeightInPoints((float) 115.5);
		
		int[] ch = new int[3];
		int mainRowIndex = 8;
		int lastRow = rowIndex;
		int six = -1, seven = -1, eight = -1;
		int sixCourse = -1, sevenCourse = -1, eightCourse = -1;
		int sixExam = -1, sevenExam = -1, eightExam = -1;
		int sixTest = -1, sevenTest = -1, eightTest = -1;
		List<Progress> progress = null;
		
		/* add all subjects to file (course works for all students)*/	
		Long lastsem = 1L;
		int startsem = 4;
		boolean last = true;
		for(int i = 0; i < subjects.length; i++) {
			
			mainRowIndex = 8;
			
			if(type == 1)
				progress = pss.selectProgressYear(year, subjects[i][0], 2);
			else
				progress = pss.selectProgressGroup(group, subjects[i][0], 2);
			if(progress.isEmpty() == false) {
			// get progress by student year & subject[i][0] & type=2 (course work)
			for(Progress p : progress) {
				if(ch[1] == 0) {
					ch[1] = 1;	
					cell = subjectRowCourse.createCell(cellIndexCourse);
					cell.setCellStyle(subjectStyle);
					cell.setCellValue(p.getSubject().getSubjectTitle().replaceAll("&quot", "\"") + "(КП(Р))");
					sheetCourse.setDefaultColumnStyle(cellIndexCourse, cellStyle);
					sheetCourse.setColumnWidth(cellIndexCourse, 2360);
					cellIndexCourse++;
				}
				
				for(int k = 0; k < studentIds.length; k++) {
					
					if(p.getStudent().getStudentId() == studentIds[k]) {
						rowCourse = sheetCourse.getRow(mainRowIndex);
						cell = rowCourse.createCell(cellIndexCourse-1);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(p.getprogressMark());
						
						if(sheetCourse.getRow(mainRowIndex+1) != null)
							rowCourse = sheetCourse.getRow(mainRowIndex+1);
						else
							rowCourse = sheetCourse.createRow(mainRowIndex+1);
						
						cell = rowCourse.createCell(cellIndexCourse-1);
						cell.setCellStyle(cellStyle);
						StringBuilder formula = new StringBuilder("if(");
						formula.append(CellReference.convertNumToColString(cellIndexCourse-1)).append(mainRowIndex+1).append("=\"A\",five,if(");
						formula.append(CellReference.convertNumToColString(cellIndexCourse-1)).append(mainRowIndex+1).append("=\"B\",ffive,if(");
						formula.append(CellReference.convertNumToColString(cellIndexCourse-1)).append(mainRowIndex+1).append("=\"C\",four,if(");
						formula.append(CellReference.convertNumToColString(cellIndexCourse-1)).append(mainRowIndex+1).append("=\"D\",tfour,if(");
						formula.append(CellReference.convertNumToColString(cellIndexCourse-1)).append(mainRowIndex+1).append("=\"E\",three,0)))))");
						
						cell.setCellFormula(formula.toString());
						
						mainRowIndex+=2;
					} else { 
						if(sheetCourse.getRow(mainRowIndex+1) != null)
							rowCourse = sheetCourse.getRow(mainRowIndex+1);
						else
							rowCourse = sheetCourse.createRow(mainRowIndex+1);
						cell = rowCourse.createCell(cellIndexCourse-1);
						cell.setCellStyle(cellStyle);
						
						StringBuilder formula = new StringBuilder("if(");
						formula.append(CellReference.convertNumToColString(cellIndexCourse-1)).append(mainRowIndex+1).append("=\"A\",five,if(");
						formula.append(CellReference.convertNumToColString(cellIndexCourse-1)).append(mainRowIndex+1).append("=\"B\",ffive,if(");
						formula.append(CellReference.convertNumToColString(cellIndexCourse-1)).append(mainRowIndex+1).append("=\"C\",four,if(");
						formula.append(CellReference.convertNumToColString(cellIndexCourse-1)).append(mainRowIndex+1).append("=\"D\",tfour,if(");
						formula.append(CellReference.convertNumToColString(cellIndexCourse-1)).append(mainRowIndex+1).append("=\"E\",three,0)))))");
						
						cell.setCellFormula(formula.toString());
						mainRowIndex+=2;
					}
				}
				mainRowIndex = 8;
			}
			ch[1] = 0;
			
			// add half
			if(subjects[i][1] > lastsem) { // if we have new half
				if((cellIndexCourse-2) >= 4) 
					sheetCourse.addMergedRegion(new CellRangeAddress(6, 6, startsem, cellIndexCourse-2));
				
				rowCourse = sheetCourse.getRow(6); // get the six-th row
				cell = rowCourse.createCell(startsem); // create cell to add value
				
				cell.setCellValue(lastsem + " семестр");
				
				lastsem = subjects[i][1];
				startsem = cellIndexCourse-1;
			} 
			
			// last step
			
				/*if((cellIndexCourse-2) >= startsem )
					sheetCourse.addMergedRegion(new CellRangeAddress(6, 6, startsem, cellIndexCourse-1));
				
				rowCourse = sheetCourse.getRow(6);
				cell = rowCourse.createCell(startsem);
				cell.setCellValue((subjects[i][1]) + " семестр");*/
			
				
				if(subjects[i][1] == 6) // get end of six part
					sixCourse = cellIndexCourse-1;
				
				if(subjects[i][1] == 7) // end of seven
					sevenCourse = cellIndexCourse-1;
				
				eightCourse = cellIndexCourse-1; // end of eight
			}
		}
		
		lastCellCourse = cellIndexCourse-1;
		
		/* add all subjects to file (exam)*/
		Long lastsem1 = 1L;
		int startsem1 = 4;
		six = -1; seven = -1; eight = -1;
		for(int i = 0; i < subjects.length; i++) {
			
			mainRowIndex = 8;
			
			if(type == 1)
				progress = pss.selectProgressYear(year, subjects[i][0], 1);
			else
				progress = pss.selectProgressGroup(group, subjects[i][0], 1);
			
			if(progress.isEmpty() == false) {
			// get progress by student year & subject[i][0] & type=1 (exam)
			for(Progress p : progress) {
				if(ch[1] == 0) {
					ch[1] = 1;	
					cell = subjectRowExam.createCell(cellIndexExam);
					cell.setCellStyle(subjectStyle);
					cell.setCellValue(p.getSubject().getSubjectTitle().replaceAll("&quot", "\"") + "(екзамен)");
					sheetExam.setDefaultColumnStyle(cellIndexExam, cellStyle);
					sheetExam.setColumnWidth(cellIndexExam, 1280);
					cellIndexExam++;
				}
				
				for(int k = 0; k < studentIds.length; k++) {
					
					if(p.getStudent().getStudentId() == studentIds[k]) {
						rowExam = sheetExam.getRow(mainRowIndex);
						cell = rowExam.createCell(cellIndexExam-1);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(p.getprogressMark());
						
						if(sheetExam.getRow(mainRowIndex+1) != null)
							rowExam = sheetExam.getRow(mainRowIndex+1);
						else
							rowExam = sheetExam.createRow(mainRowIndex+1);
						
						cell = rowExam.createCell(cellIndexExam-1);
						cell.setCellStyle(cellStyle);
						StringBuilder formula = new StringBuilder("if(");
						formula.append(CellReference.convertNumToColString(cellIndexExam-1)).append(mainRowIndex+1).append("=\"A\",five,if(");
						formula.append(CellReference.convertNumToColString(cellIndexExam-1)).append(mainRowIndex+1).append("=\"B\",ffive,if(");
						formula.append(CellReference.convertNumToColString(cellIndexExam-1)).append(mainRowIndex+1).append("=\"C\",four,if(");
						formula.append(CellReference.convertNumToColString(cellIndexExam-1)).append(mainRowIndex+1).append("=\"D\",tfour,if(");
						formula.append(CellReference.convertNumToColString(cellIndexExam-1)).append(mainRowIndex+1).append("=\"E\",three,0)))))");
						
						cell.setCellFormula(formula.toString());
						
						mainRowIndex+=2;
					} else { 
						if(sheetExam.getRow(mainRowIndex+1) != null)
							rowExam = sheetExam.getRow(mainRowIndex+1);
						else
							rowExam = sheetExam.createRow(mainRowIndex+1);
						cell = rowExam.createCell(cellIndexExam-1);
						cell.setCellStyle(cellStyle);
						
						StringBuilder formula = new StringBuilder("if(");
						formula.append(CellReference.convertNumToColString(cellIndexExam-1)).append(mainRowIndex+1).append("=\"A\",five,if(");
						formula.append(CellReference.convertNumToColString(cellIndexExam-1)).append(mainRowIndex+1).append("=\"B\",ffive,if(");
						formula.append(CellReference.convertNumToColString(cellIndexExam-1)).append(mainRowIndex+1).append("=\"C\",four,if(");
						formula.append(CellReference.convertNumToColString(cellIndexExam-1)).append(mainRowIndex+1).append("=\"D\",tfour,if(");
						formula.append(CellReference.convertNumToColString(cellIndexExam-1)).append(mainRowIndex+1).append("=\"E\",three,0)))))");
						
						cell.setCellFormula(formula.toString());
						mainRowIndex+=2;
					}
				}
				mainRowIndex = 8;
			}
			ch[1] = 0;
			
			// add half
			if(subjects[i][1] > lastsem1) { // if we have new half
				if((cellIndexExam-2) >= 4) 
					sheetExam.addMergedRegion(new CellRangeAddress(6, 6, startsem1, cellIndexExam-2));
				
				rowExam = sheetExam.getRow(6); // get the six-th row
				cell = rowExam.createCell(startsem1); // create cell to add value
				
				cell.setCellValue(lastsem1 + " семестр");
				
				lastsem1 = subjects[i][1];
				startsem1 = cellIndexExam-1;
			} 
			
			// last step
			
			/*	if((cellIndexExam-2) >= startsem1 )
					sheetExam.addMergedRegion(new CellRangeAddress(6, 6, startsem1, cellIndexExam-1));
				
				rowExam = sheetExam.getRow(6);
				cell = rowExam.createCell(startsem1);
				cell.setCellValue((subjects[i][1]) + " семестр");*/
				
				if(subjects[i][1] == 6) // get end of six part
					sixExam = cellIndexExam-1;
				
				if(subjects[i][1] == 7) // end of seven
					sevenExam = cellIndexExam-1;
				
				eightExam = cellIndexExam-1; // end of eight
			}
		}
		
		lastCellExam = cellIndexExam - 1;
		
		/* add all subjects to file (test) */
		Long lastsem2 = 1L;
		int startsem2 = 4;
		six = -1; seven = -1; eight = -1;
		for(int i = 0; i < subjects.length; i++) {
			
			mainRowIndex = 8;
			
			if(type == 1)
				progress = pss.selectProgressYear(year, subjects[i][0], 3);
			else
				progress = pss.selectProgressGroup(group, subjects[i][0], 3);
			
			if(progress.isEmpty() == false) {
			// get progress by student year & subject[i][0] & type=3 (test)
			for(Progress p : progress) {
				if(ch[1] == 0) {
					ch[1] = 1;	
					cell = subjectRowTest.createCell(cellIndexTest);
					cell.setCellStyle(subjectStyle);
					cell.setCellValue(p.getSubject().getSubjectTitle().replaceAll("&quot", "\"") + "(екзамен)");
					sheetTest.setDefaultColumnStyle(cellIndexTest, cellStyle);
					sheetTest.setColumnWidth(cellIndexTest, 1280);
					cellIndexTest++;
				}
				
				for(int k = 0; k < studentIds.length; k++) {
					
					if(p.getStudent().getStudentId() == studentIds[k]) {
						rowTest = sheetTest.getRow(mainRowIndex);
						cell = rowTest.createCell(cellIndexTest-1);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(p.getprogressMark());
						
						if(sheetTest.getRow(mainRowIndex+1) != null)
							rowTest = sheetTest.getRow(mainRowIndex+1);
						else
							rowTest = sheetTest.createRow(mainRowIndex+1);
						
						cell = rowTest.createCell(cellIndexTest-1);
						cell.setCellStyle(cellStyle);
						StringBuilder formula = new StringBuilder("if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"A\",five,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"B\",ffive,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"C\",four,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"D\",tfour,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"E\",three,0)))))");
						
						cell.setCellFormula(formula.toString());
						
						mainRowIndex+=2;
					} else { 
						if(sheetTest.getRow(mainRowIndex+1) != null)
							rowTest = sheetTest.getRow(mainRowIndex+1);
						else
							rowTest = sheetTest.createRow(mainRowIndex+1);
						cell = rowTest.createCell(cellIndexTest-1);
						cell.setCellStyle(cellStyle);
						
						StringBuilder formula = new StringBuilder("if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"A\",five,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"B\",ffive,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"C\",four,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"D\",tfour,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"E\",three,0)))))");
						
						cell.setCellFormula(formula.toString());
						mainRowIndex+=2;
					}
				}
				mainRowIndex = 8;
			}
			ch[1] = 0;
			
			 
			// add half
			if(subjects[i][1] > lastsem2) { // if we have new half
				if((cellIndexTest-2) >= 4) 
					sheetTest.addMergedRegion(new CellRangeAddress(6, 6, startsem2, cellIndexTest-2));
				
				rowTest = sheetTest.getRow(6); // get the six-th row
				cell = rowTest.createCell(startsem2); // create cell to add value
				
				cell.setCellValue(lastsem2 + " семестр");
				
				lastsem2 = subjects[i][1];
				startsem2 = cellIndexTest-1;
			} // last step
				//if(i == (subjects.length-1)) {
					
					/*if((cellIndexTest-2) >= startsem2 )
						sheetTest.addMergedRegion(new CellRangeAddress(6, 6, startsem2, cellIndexTest-1));
					
					rowTest = sheetTest.getRow(6);
					cell = rowTest.createCell(startsem2);
					cell.setCellValue((subjects[i][1]) + " семестр");*/
				//}
					
				if(subjects[i][1] == 6) // get end of six part
					sixTest = cellIndexTest-1;
				
				if(subjects[i][1] == 7) // end of seven
					sevenTest = cellIndexTest-1;
				
				eightTest = cellIndexTest-1; // end of eight
			}
		}
		
		lastCellTest = cellIndexTest - 1;
		
		/* add all subjects to file (simple test) */
		Long lastsem3 = 1L;
		int startsem3 = 4;
		six = -1; seven = -1; eight = -1;
		for(int i = 0; i < subjects.length; i++) {
			
			mainRowIndex = 8;
			
			if(type == 1)
				progress = pss.selectProgressYear(year, subjects[i][0], 4);
			else
				progress = pss.selectProgressGroup(group, subjects[i][0], 4);
			
			if(progress.isEmpty() == false) {
			// get progress by student year & subject[i][0] & type=4 (simple test)
			for(Progress p : progress) {
				if(ch[1] == 0) {
					ch[1] = 1;	
					cell = subjectRow.createCell(cellIndex);
					cell.setCellStyle(subjectStyle);
					cell.setCellValue(p.getSubject().getSubjectTitle().replaceAll("&quot", "\"") + "(залік)");
					sheet.setDefaultColumnStyle(cellIndex, cellStyle);
					sheet.setColumnWidth(cellIndex, 1280);
					cellIndex++;
				}
				
				for(int k = 0; k < studentIds.length; k++) {
					
					if(p.getStudent().getStudentId() == studentIds[k]) {
						row = sheet.getRow(mainRowIndex);
						cell = row.createCell(cellIndex-1);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(p.getprogressMark());
						
						if(sheet.getRow(mainRowIndex+1) != null)
							row = sheet.getRow(mainRowIndex+1);
						else
							row = sheet.createRow(mainRowIndex+1);
						
						cell = row.createCell(cellIndex-1);
						cell.setCellStyle(cellStyle);
						StringBuilder formula = new StringBuilder("if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"A\",five,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"B\",ffive,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"C\",four,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"D\",tfour,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"E\",three,0)))))");
						
						cell.setCellFormula(formula.toString());
						
						mainRowIndex+=2;
					} else { 
						if(sheet.getRow(mainRowIndex+1) != null)
							row = sheet.getRow(mainRowIndex+1);
						else
							row = sheet.createRow(mainRowIndex+1);
						cell = row.createCell(cellIndex-1);
						cell.setCellStyle(cellStyle);
						
						StringBuilder formula = new StringBuilder("if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"A\",five,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"B\",ffive,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"C\",four,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"D\",tfour,if(");
						formula.append(CellReference.convertNumToColString(cellIndexTest-1)).append(mainRowIndex+1).append("=\"E\",three,0)))))");
						
						cell.setCellFormula(formula.toString());
						mainRowIndex+=2;
					}
				}
				mainRowIndex = 8;
			}
			ch[1] = 0;
			
			 
			// add half
			if(subjects[i][1] > lastsem3) { // if we have new half
				if((cellIndex-2) >= 4) 
					sheet.addMergedRegion(new CellRangeAddress(6, 6, startsem3, cellIndex-2));
				
				row = sheet.getRow(6); // get the six-th row
				cell = row.createCell(startsem3); // create cell to add value
				
				cell.setCellValue(lastsem3 + " семестр");
				
				lastsem3 = subjects[i][1];
				startsem3 = cellIndex-1;
			} // last step
				//if(i == (subjects.length-1)) {
					
					/*if((cellIndex-2) >= startsem3 )
						sheet.addMergedRegion(new CellRangeAddress(6, 6, startsem3, cellIndex-1));
					
					row = sheet.getRow(6);
					cell = row.createCell(startsem3);
					cell.setCellValue((subjects[i][1]) + " семестр");*/
				//}
			
				if(subjects[i][1] == 6) // get end of six part
					six = cellIndex-1;
				
				if(subjects[i][1] == 7) // end of seven
					seven = cellIndex-1;
				
				eight = cellIndex-1; // end of eight
			}
		}
	
		lastCell = cellIndex-1;
		
		/* summary */
		row = sheet.getRow(7);
		
		sheet.setDefaultColumnStyle(cellIndex, cellStyle);
		sheet.setColumnWidth(cellIndex, 1280);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(subjectStyle);
		cell.setCellValue("Оцінка (1-6 семестри)");
		
		sheet.setDefaultColumnStyle(cellIndex+1, cellStyle);
		sheet.setColumnWidth(cellIndex+1, 1280);
		cell = row.createCell(cellIndex+1);
		cell.setCellStyle(subjectStyle);
		cell.setCellValue("Оцінка (1-7 семестри)");
		
		sheet.setDefaultColumnStyle(cellIndex+2, cellStyle);
		sheet.setColumnWidth(cellIndex+2, 1280);
		cell = row.createCell(cellIndex+2);
		cell.setCellStyle(subjectStyle);
		cell.setCellValue("Оцінка (1-8 семестри)");
		
		Row r;
		Cell c;
		for (int i = 9; i < lastRow; i += 2) {
			r = sheet.getRow(i);
			
			if(r != null) {
				c = r.createCell(cellIndex);
				c.setCellStyle(numCellStyle);
				if(six != -1)
					c.setCellFormula("SUM(E" + (i+1) + ":" + CellReference.convertNumToColString(six) + "" + (i+1) + ")/" + (six-3));
				
				c = r.createCell(cellIndex+1);
				c.setCellStyle(numCellStyle);
				if(seven != -1)
					c.setCellFormula("SUM(E" + (i+1) + ":" + CellReference.convertNumToColString(seven) + "" + (i+1) + ")/" + (seven-3));
				
				c = r.createCell(cellIndex+2);
				c.setCellStyle(numCellStyle);
				c.setCellFormula("SUM(E" + (i+1) + ":" + CellReference.convertNumToColString(eight) + "" + (i+1) + ")/" + (eight-3));
			}
		}
		
		rowCourse = sheetCourse.getRow(7);
		
		sheetCourse.setDefaultColumnStyle(cellIndexCourse, cellStyle);
		sheetCourse.setColumnWidth(cellIndexCourse, 1280);
		cell = rowCourse.createCell(cellIndexCourse);
		cell.setCellStyle(subjectStyle);
		cell.setCellValue("Оцінка (1-6 семестри)");
		
		sheetCourse.setDefaultColumnStyle(cellIndexCourse+1, cellStyle);
		sheetCourse.setColumnWidth(cellIndexCourse+1, 1280);
		cell = rowCourse.createCell(cellIndexCourse+1);
		cell.setCellStyle(subjectStyle);
		cell.setCellValue("Оцінка (1-7 семестри)");
		
		sheetCourse.setDefaultColumnStyle(cellIndexCourse+2, cellStyle);
		sheetCourse.setColumnWidth(cellIndexCourse+2, 1280);
		cell = rowCourse.createCell(cellIndexCourse+2);
		cell.setCellStyle(subjectStyle);
		cell.setCellValue("Оцінка (1-8 семестри)");
		
		for (int i = 9; i < lastRow; i += 2) {
			r = sheetCourse.getRow(i);
			
			if(r != null) {
				c = r.createCell(cellIndexCourse);
				c.setCellStyle(numCellStyle);
				if(sixCourse != -1)
					c.setCellFormula("SUM(E" + (i+1) + ":" + CellReference.convertNumToColString(sixCourse) + "" + (i+1) + ")/" + (sixCourse-3));
				
				c = r.createCell(cellIndexCourse+1);
				c.setCellStyle(numCellStyle);
				if(sevenCourse != -1)
					c.setCellFormula("SUM(E" + (i+1) + ":" + CellReference.convertNumToColString(sevenCourse) + "" + (i+1) + ")/" + (sevenCourse-3));
				
				c = r.createCell(cellIndexCourse+2);
				c.setCellStyle(numCellStyle);
				c.setCellFormula("SUM(E" + (i+1) + ":" + CellReference.convertNumToColString(eightCourse) + "" + (i+1) + ")/" + (eightCourse-3));
			}
		}
		
		rowExam = sheetExam.getRow(7);
		
		sheetExam.setDefaultColumnStyle(cellIndexExam, cellStyle);
		sheetExam.setColumnWidth(cellIndexExam, 1280);
		cell = rowExam.createCell(cellIndexExam);
		cell.setCellStyle(subjectStyle);
		cell.setCellValue("Оцінка (1-6 семестри)");
		
		sheetExam.setDefaultColumnStyle(cellIndexExam+1, cellStyle);
		sheetExam.setColumnWidth(cellIndexExam+1, 1280);
		cell = rowExam.createCell(cellIndexExam+1);
		cell.setCellStyle(subjectStyle);
		cell.setCellValue("Оцінка (1-7 семестри)");
		
		sheetExam.setDefaultColumnStyle(cellIndexExam+2, cellStyle);
		sheetExam.setColumnWidth(cellIndexExam+2, 1280);
		cell = rowExam.createCell(cellIndexExam+2);
		cell.setCellStyle(subjectStyle);
		cell.setCellValue("Оцінка (1-8 семестри)");
		
		for (int i = 9; i < lastRow; i += 2) {
			r = sheetExam.getRow(i);
			
			if(r != null) {
				c = r.createCell(cellIndexExam);
				c.setCellStyle(numCellStyle);
				if(sixExam != -1)
					c.setCellFormula("SUM(E" + (i+1) + ":" + CellReference.convertNumToColString(sixExam) + "" + (i+1) + ")/" + (sixExam-3));
				
				c = r.createCell(cellIndexExam+1);
				c.setCellStyle(numCellStyle);
				if(sevenExam != -1)
					c.setCellFormula("SUM(E" + (i+1) + ":" + CellReference.convertNumToColString(sevenExam) + "" + (i+1) + ")/" + (sevenExam-3));
				
				c = r.createCell(cellIndexExam+2);
				c.setCellStyle(numCellStyle);
				c.setCellFormula("SUM(E" + (i+1) + ":" + CellReference.convertNumToColString(eightExam) + "" + (i+1) + ")/" + (eightExam-3));
			}
		}
		
		rowTest = sheetTest.getRow(7);
		
		sheetTest.setDefaultColumnStyle(cellIndexTest, cellStyle);
		sheetTest.setColumnWidth(cellIndexTest, 1280);
		cell = rowTest.createCell(cellIndexTest);
		cell.setCellStyle(subjectStyle);
		cell.setCellValue("Оцінка (1-6 семестри)");
		
		sheetTest.setDefaultColumnStyle(cellIndexTest+1, cellStyle);
		sheetTest.setColumnWidth(cellIndexTest+1, 1280);
		cell = rowTest.createCell(cellIndexTest+1);
		cell.setCellStyle(subjectStyle);
		cell.setCellValue("Оцінка (1-7 семестри)");
		
		sheetTest.setDefaultColumnStyle(cellIndexTest+2, cellStyle);
		sheetTest.setColumnWidth(cellIndexTest+2, 1280);
		cell = rowTest.createCell(cellIndexTest+2);
		cell.setCellStyle(subjectStyle);
		cell.setCellValue("Оцінка (1-8 семестри)");
		
		for (int i = 9; i < lastRow; i += 2) {
			r = sheetTest.getRow(i);
			
			if(r != null) {
				c = r.createCell(cellIndexTest);
				c.setCellStyle(numCellStyle);
				if(sixTest != -1)
					c.setCellFormula("SUM(E" + (i+1) + ":" + CellReference.convertNumToColString(sixTest) + "" + (i+1) + ")/" + (sixTest-3));
				
				c = r.createCell(cellIndexTest+1);
				c.setCellStyle(numCellStyle);
				if(sevenTest != -1)
					c.setCellFormula("SUM(E" + (i+1) + ":" + CellReference.convertNumToColString(sevenTest) + "" + (i+1) + ")/" + (sevenTest-3));
				
				c = r.createCell(cellIndexTest+2);
				c.setCellStyle(numCellStyle);
				c.setCellFormula("SUM(E" + (i+1) + ":" + CellReference.convertNumToColString(eightTest) + "" + (i+1) + ")/" + (eightTest-3));
			}
		}
		
		/* summarize */
		
		cellIndex += 3;
		cellIndexCourse += 3;
		cellIndexExam += 3;
		cellIndexTest += 3;
		
		sheet.addMergedRegion(new CellRangeAddress(6, 6, cellIndex, cellIndex+2));
		r = sheet.createRow(6);
		c = r.createCell(cellIndex);
		c.setCellValue("Метод середньої оцінки");
		
		sheetExam.addMergedRegion(new CellRangeAddress(6, 6, cellIndexExam, cellIndexExam+2));
		r = sheetExam.createRow(6);
		c = r.createCell(cellIndexExam);
		c.setCellValue("Метод середньої оцінки");
		
		sheetTest.addMergedRegion(new CellRangeAddress(6, 6, cellIndexTest, cellIndexTest+2));
		r = sheetTest.createRow(6);
		c = r.createCell(cellIndexTest);
		c.setCellValue("Метод середньої оцінки");
		
		sheetCourse.addMergedRegion(new CellRangeAddress(6, 6, cellIndexCourse, cellIndexCourse+2));
		r = sheetCourse.createRow(6);
		c = r.createCell(cellIndexCourse);
		c.setCellValue("Метод середньої оцінки");
		
		r = sheet.getRow(7);
		c = r.createCell(cellIndex);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Сума");
		
		r = sheetTest.getRow(7);
		c = r.createCell(cellIndexTest);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Сума");
		
		r = sheetExam.getRow(7);
		c = r.createCell(cellIndexExam);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Сума");
		
		r = sheetCourse.getRow(7);
		c = r.createCell(cellIndexCourse);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Сума");
		
		r = sheet.getRow(7);
		c = r.createCell(cellIndex+1);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Кількість");
		
		r = sheetTest.getRow(7);
		c = r.createCell(cellIndexTest+1);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Кількість");
		
		r = sheetExam.getRow(7);
		c = r.createCell(cellIndexExam+1);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Кількість");
		
		r = sheetCourse.getRow(7);
		c = r.createCell(cellIndexCourse+1);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Кількість");
		
		r = sheet.getRow(7);
		c = r.createCell(cellIndex+2);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Середня оцінка");
		
		r = sheetTest.getRow(7);
		c = r.createCell(cellIndexTest+2);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Середня оцінка");
		
		r = sheetExam.getRow(7);
		c = r.createCell(cellIndexExam+2);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Середня оцінка");
		
		r = sheetCourse.getRow(7);
		c = r.createCell(cellIndexCourse+2);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Середня оцінка");
		
		mainRowIndex = 9;
		int countSubject = subjects.length;
		int countSubjectExam = subjects.length;
		int countSubjectCourse = subjects.length;
		int countSubjectTest = subjects.length;
		for(int j = 0; j < students.size(); j++) {
			
			r = sheet.getRow(mainRowIndex);
			if(r != null) {
				c = r.createCell(cellIndex);
				c.setCellStyle(numCellStyle);
				c.setCellFormula("SUM(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(cellIndex-4) + "" + (mainRowIndex+1) + ")");
				
				countSubject = cellIndex+1;
				c = r.createCell(cellIndex+1);
				c.setCellStyle(numCellStyle);
				c.setCellFormula("COUNT(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(cellIndex-4) + "" + (mainRowIndex+1) + ")");
				
				c = r.createCell(cellIndex+2);
				c.setCellStyle(numCellStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndex) + "" + (mainRowIndex+1) + "/" + CellReference.convertNumToColString(cellIndex+1) + "" + (mainRowIndex+1));		
			}
			
			////////////////////////////////////////
			
			r = sheetExam.getRow(mainRowIndex);
			if(r != null) {
				c = r.createCell(cellIndexExam);
				c.setCellStyle(numCellStyle);
				c.setCellFormula("SUM(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(cellIndexExam-4) + "" + (mainRowIndex+1) + ")");
				
				countSubjectExam = cellIndexExam+1;
				c = r.createCell(cellIndexExam+1);
				c.setCellStyle(numCellStyle);
				c.setCellFormula("COUNT(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(cellIndexExam-4) + "" + (mainRowIndex+1) + ")");
				
				c = r.createCell(cellIndexExam+2);
				c.setCellStyle(numCellStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexExam) + "" + (mainRowIndex+1) + "/" + CellReference.convertNumToColString(cellIndexExam+1) + "" + (mainRowIndex+1));		
			}
			////////////////////////////////////////
						
			r = sheetTest.getRow(mainRowIndex);
			if(r != null) {
				c = r.createCell(cellIndexTest);
				c.setCellStyle(numCellStyle);
				c.setCellFormula("SUM(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(cellIndexTest-4) + "" + (mainRowIndex+1) + ")");
				
				countSubjectTest = cellIndexTest+1;
				c = r.createCell(cellIndexTest+1);
				c.setCellStyle(numCellStyle);
				c.setCellFormula("COUNT(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(cellIndexTest-4) + "" + (mainRowIndex+1) + ")");
				
				c = r.createCell(cellIndexTest+2);
				c.setCellStyle(numCellStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexTest) + "" + (mainRowIndex+1) + "/" + CellReference.convertNumToColString(cellIndexTest+1) + "" + (mainRowIndex+1));		
			}
			////////////////////////////////////////
			
			r = sheetCourse.getRow(mainRowIndex);
			if(r != null) {
				c = r.createCell(cellIndexCourse);
				c.setCellStyle(numCellStyle);
				c.setCellFormula("SUM(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(cellIndexCourse-4) + "" + (mainRowIndex+1) + ")");
				
				countSubjectCourse = cellIndexCourse+1;
				c = r.createCell(cellIndexCourse+1);
				c.setCellStyle(numCellStyle);
				c.setCellFormula("COUNT(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(cellIndexCourse-4) + "" + (mainRowIndex+1) + ")");
				
				c = r.createCell(cellIndexCourse+2);
				c.setCellStyle(numCellStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexCourse) + "" + (mainRowIndex+1) + "/" + CellReference.convertNumToColString(cellIndexCourse+1) + "" + (mainRowIndex+1));		
			}
			
			mainRowIndex+=2;
		}
		
		cellIndex += 3;
		cellIndexExam += 3;
		cellIndexTest += 3;
		cellIndexCourse += 3;
		
		char subjectMark = 'A';
		Row rExam, rTest, rCourse;
		
		for(int j = cellIndex; j < cellIndex + 10; j+=2) {
			
			r = sheet.getRow(7);
			
			sheet.setDefaultColumnStyle(j, cellStyle);
			sheet.setColumnWidth(j, 1280);
			c = r.createCell(j);
			c.setCellStyle(subjectStyle);
			c.setCellValue("Кількість \"" + subjectMark + "\"");

			sheet.setDefaultColumnStyle(j+1, cellStyle);
			sheet.setColumnWidth(j+1, 2540);
			c = r.createCell(j+1);
			c.setCellStyle(subjectStyle);
			c.setCellValue("Процент \"" + subjectMark + "\"");
			
			subjectMark++;
		}

		////////////////////////////////////////////
		
		subjectMark = 'A';
		for(int j = cellIndexExam; j < cellIndexExam + 10; j+=2) {
			
			rExam = sheetExam.getRow(7);
			
			sheetExam.setDefaultColumnStyle(j, cellStyle);
			sheetExam.setColumnWidth(j, 1280);
			c = rExam.createCell(j);
			c.setCellStyle(subjectStyle);
			c.setCellValue("Кількість \"" + subjectMark + "\"");

			sheetExam.setDefaultColumnStyle(j+1, cellStyle);
			sheetExam.setColumnWidth(j+1, 2540);
			c = rExam.createCell(j+1);
			c.setCellStyle(subjectStyle);
			c.setCellValue("Процент \"" + subjectMark + "\"");
			
			subjectMark++;
		}
		
		subjectMark = 'A';
		for(int j = cellIndexTest; j < cellIndexTest + 10; j+=2) {
			
			rTest = sheetTest.getRow(7);
			
			sheetTest.setDefaultColumnStyle(j, cellStyle);
			sheetTest.setColumnWidth(j, 1280);
			c = rTest.createCell(j);
			c.setCellStyle(subjectStyle);
			c.setCellValue("Кількість \"" + subjectMark + "\"");

			sheetTest.setDefaultColumnStyle(j+1, cellStyle);
			sheetTest.setColumnWidth(j+1, 2540);
			c = rTest.createCell(j+1);
			c.setCellStyle(subjectStyle);
			c.setCellValue("Процент \"" + subjectMark + "\"");
			
			subjectMark++;
		}
		
		subjectMark = 'A';
		for(int j = cellIndexCourse; j < cellIndexCourse + 10; j+=2) {
			
			rCourse = sheetCourse.getRow(7);
			
			sheetCourse.setDefaultColumnStyle(j, cellStyle);
			sheetCourse.setColumnWidth(j, 1280);
			c = rCourse.createCell(j);
			c.setCellStyle(subjectStyle);
			c.setCellValue("Кількість \"" + subjectMark + "\"");

			sheetCourse.setDefaultColumnStyle(j+1, cellStyle);
			sheetCourse.setColumnWidth(j+1, 2540);
			c = rCourse.createCell(j+1);
			c.setCellStyle(subjectStyle);
			c.setCellValue("Процент \"" + subjectMark + "\"");
			
			subjectMark++;
		}
		
		r = sheet.getRow(7);
		c = r.createCell(cellIndex+10);
		sheet.setDefaultColumnStyle(cellIndex+10, cellStyle);
		sheet.setColumnWidth(cellIndex+10, 2540);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Перевірка");
		
		for(int  i = cellIndex; i < cellIndex + 10; i++) {
			sheet.setDefaultColumnStyle(i, cellStyle);
		}
		
		/////////////////////////
		
		rExam = sheetExam.getRow(7);
		c = rExam.createCell(cellIndexExam+10);
		sheetExam.setDefaultColumnStyle(cellIndexExam+10, cellStyle);
		sheetExam.setColumnWidth(cellIndexExam+10, 2540);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Перевірка");
		
		for(int  i = cellIndexExam; i < cellIndexExam + 10; i++) {
			sheetExam.setDefaultColumnStyle(i, cellStyle);
		}
		
		///////////////////////////
		
		rTest = sheetTest.getRow(7);
		c = rTest.createCell(cellIndexTest+10);
		sheetTest.setDefaultColumnStyle(cellIndexTest+10, cellStyle);
		sheetTest.setColumnWidth(cellIndexTest+10, 2540);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Перевірка");
		
		for(int  i = cellIndexTest; i < cellIndexTest + 10; i++) {
			sheetTest.setDefaultColumnStyle(i, cellStyle);
		}
		
		//////////////////////////////
		
		rCourse = sheetCourse.getRow(7);
		c = rCourse.createCell(cellIndexCourse+10);
		sheetCourse.setDefaultColumnStyle(cellIndexCourse+10, cellStyle);
		sheetCourse.setColumnWidth(cellIndexCourse+10, 2540);
		c.setCellStyle(subjectStyle);
		c.setCellValue("Перевірка");
		
		for(int  i = cellIndexCourse; i < cellIndexCourse + 10; i++) {
			sheetCourse.setDefaultColumnStyle(i, cellStyle);
		}
		
		mainRowIndex = 9;
		
		for(int j = 0; j < students.size(); j++) {
			r = sheet.getRow(mainRowIndex);
			rExam = sheetExam.getRow(mainRowIndex);
			rTest = sheetTest.getRow(mainRowIndex);
			rCourse = sheetCourse.getRow(mainRowIndex);
			
			// count of mark A
			if(r != null) {
				c = r.createCell(cellIndex);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCell) + "" + (mainRowIndex+1) + ",5)");
			}
			
			if(rExam != null) {
				c = rExam.createCell(cellIndexExam);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellExam) + "" + (mainRowIndex+1) + ",5)");
			}
			
			if(rTest != null) {
				c = rTest.createCell(cellIndexTest);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellTest) + "" + (mainRowIndex+1) + ",5)");
			}
			
			if(rCourse != null) {
				c = rCourse.createCell(cellIndexCourse);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellCourse) + "" + (mainRowIndex+1) + ",5)");
			}
			
			// percent
			if(r != null) {
				c = r.createCell(cellIndex+1);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndex) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubject) + "" + (mainRowIndex+1));
			}
			
			if(rExam != null) {
				c = rExam.createCell(cellIndexExam+1);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexExam) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectExam) + "" + (mainRowIndex+1));
			}
			
			if(rTest != null) {
				c = rTest.createCell(cellIndexTest+1);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexTest) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectTest) + "" + (mainRowIndex+1));
			}
			
			if(rCourse != null) {
				c = rCourse.createCell(cellIndexCourse+1);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexCourse) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectCourse) + "" + (mainRowIndex+1));
			}
			
			// count of mark B
			if(r != null) {
				c = r.createCell(cellIndex+2);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCell) + "" + (mainRowIndex+1) + ",4.5)");
			}
			
			if(rExam != null) {
				c = rExam.createCell(cellIndexExam+2);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellExam) + "" + (mainRowIndex+1) + ",4.5)");
			}
			
			if(rTest != null) {
				c = rTest.createCell(cellIndexTest+2);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellTest) + "" + (mainRowIndex+1) + ",4.5)");
			}
			
			if(rCourse != null) {
				c = rCourse.createCell(cellIndexCourse+2);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellCourse) + "" + (mainRowIndex+1) + ",4.5)");
			}
		
			// percent
			if(r != null) {
				c = r.createCell(cellIndex+3);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndex+2) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubject) + "" + (mainRowIndex+1));
			}
			
			if(rExam != null) {
				c = rExam.createCell(cellIndexExam+3);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexExam+2) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectExam) + "" + (mainRowIndex+1));
			}
			
			if(rTest != null) {
				c = rTest.createCell(cellIndexTest+3);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexTest+2) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectTest) + "" + (mainRowIndex+1));
			}
			
			if(rCourse != null) {
				c = rCourse.createCell(cellIndexCourse+3);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexCourse+2) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectCourse) + "" + (mainRowIndex+1));
			}		
			
			// count of mark C
			if(r != null) {
				c = r.createCell(cellIndex+4);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCell) + "" + (mainRowIndex+1) + ",4)");
			}
			
			if(rExam != null) {
				c = rExam.createCell(cellIndexExam+4);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellExam) + "" + (mainRowIndex+1) + ",4)");
			}
			
			if(rTest != null) {
				c = rTest.createCell(cellIndexTest+4);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellTest) + "" + (mainRowIndex+1) + ",4)");
			}
			
			if(rCourse != null) {
				c = rCourse.createCell(cellIndexCourse+4);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellCourse) + "" + (mainRowIndex+1) + ",4)");
			}
		
			// percent
			if(r != null) {
				c = r.createCell(cellIndex+5);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndex+4) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubject) + "" + (mainRowIndex+1));
			}
			
			if(rExam != null) {
				c = rExam.createCell(cellIndexExam+5);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexExam+4) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectExam) + "" + (mainRowIndex+1));
			}
			
			if(rTest != null) {
				c = rTest.createCell(cellIndexTest+5);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexTest+4) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectTest) + "" + (mainRowIndex+1));
			}
			
			if(rCourse != null) {
				c = rCourse.createCell(cellIndexCourse+5);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexCourse+4) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectCourse) + "" + (mainRowIndex+1));
			}		
			
			// count of mark D
			if(r != null) {
				c = r.createCell(cellIndex+6);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCell) + "" + (mainRowIndex+1) + ",3.5)");
			}
			
			if(rExam != null) {
				c = rExam.createCell(cellIndexExam+6);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellExam) + "" + (mainRowIndex+1) + ",3.5)");
			}
			
			if(rTest != null) {
				c = rTest.createCell(cellIndexTest+6);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellTest) + "" + (mainRowIndex+1) + ",3.5)");
			}
			
			if(rCourse != null) {
				c = rCourse.createCell(cellIndexCourse+6);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellCourse) + "" + (mainRowIndex+1) + ",3.5)");
			}
		
			// percent
			if(r != null) {
				c = r.createCell(cellIndex+7);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndex+6) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubject) + "" + (mainRowIndex+1));
			}
			
			if(rExam != null) {
				c = rExam.createCell(cellIndexExam+7);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexExam+6) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectExam) + "" + (mainRowIndex+1));
			}
			
			if(rTest != null) {
				c = rTest.createCell(cellIndexTest+7);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexTest+6) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectTest) + "" + (mainRowIndex+1));
			}
			
			if(rCourse != null) {
				c = rCourse.createCell(cellIndexCourse+7);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexCourse+6) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectCourse) + "" + (mainRowIndex+1));
			}

			// count of mark E
			if(r != null) {
				c = r.createCell(cellIndex+8);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCell) + "" + (mainRowIndex+1) + ",3)");
			}
			
			if(rExam != null) {
				c = rExam.createCell(cellIndexExam+8);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellExam) + "" + (mainRowIndex+1) + ",3)");
			}
			
			if(rTest != null) {
				c = rTest.createCell(cellIndexTest+8);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellTest) + "" + (mainRowIndex+1) + ",3)");
			}
			
			if(rCourse != null) {
				c = rCourse.createCell(cellIndexCourse+8);
				c.setCellStyle(cellStyle);
				c.setCellFormula("COUNTIF(E" + (mainRowIndex+1) + ":" + CellReference.convertNumToColString(lastCellCourse) + "" + (mainRowIndex+1) + ",3)");
			}
		
			// percent
			if(r != null) {
				c = r.createCell(cellIndex+9);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndex+8) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubject) + "" + (mainRowIndex+1));
			}
			
			if(rExam != null) {
				c = rExam.createCell(cellIndexExam+9);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexExam+8) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectExam) + "" + (mainRowIndex+1));
			}
			
			if(rTest != null) {
				c = rTest.createCell(cellIndexTest+9);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexTest+8) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectTest) + "" + (mainRowIndex+1));
			}
			
			if(rCourse != null) {
				c = rCourse.createCell(cellIndexCourse+9);	
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexCourse+8) + "" +  (mainRowIndex+1) + "/" + CellReference.convertNumToColString(countSubjectCourse) + "" + (mainRowIndex+1));
			}

			/////////////////////////////////////////
			
			if(r != null) {
				c = r.createCell(cellIndex + 10);
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndex+1) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndex+3) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndex+5) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndex+7) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndex+9) + (mainRowIndex+1));
			}
			
			if(rExam != null) {
				c = rExam.createCell(cellIndexExam + 10);
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexExam+1) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndexExam+3) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndexExam+5) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndexExam+7) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndexExam+9) + (mainRowIndex+1));
			}
			
			if(rTest != null) {
				c = rTest.createCell(cellIndexTest + 10);
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexTest+1) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndexTest+3) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndexTest+5) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndexTest+7) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndexTest+9) + (mainRowIndex+1));
			}
			
			if(rCourse != null) {
				c = rCourse.createCell(cellIndexCourse + 10);
				c.setCellStyle(percentStyle);
				c.setCellFormula(CellReference.convertNumToColString(cellIndexCourse+1) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndexCourse+3) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndexCourse+5) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndexCourse+7) + (mainRowIndex+1) + "+" +
								 CellReference.convertNumToColString(cellIndexCourse+9) + (mainRowIndex+1));
			}

			mainRowIndex+=2;
		}
	
		try {
			wb.write(out); // write to file 
			out.close();
		} catch (IOException e) {
			return "error";
		} // close stream
		  
		return "success";
	}
	
	/*
	 * Function for converting to short name
	 * */
	private StringBuilder converName(String name) {
		
		String[] parts = name.split(" "); 
		StringBuilder shortName = new StringBuilder(25);
		
		shortName.append(parts[0]);
		shortName.append(' ');
		for(int i = 1; i< parts.length; i++) {
			shortName.append(parts[i].charAt(0));
			shortName.append('.');
		}
		
		return shortName;
	}
	
	/*
	 * Function for converting to short name (reverse A. S. Lastname)
	 * */
	private StringBuilder converNameReverse(String name) {
		
		String[] parts = name.split(" "); 
		StringBuilder shortName = new StringBuilder(25);
		
		for(int i = 1; i< parts.length; i++) {
			shortName.append(parts[i].charAt(0));
			shortName.append('.');
		}
		shortName.append(' ');
		shortName.append(parts[0]);
		
		return shortName;
	}
	
	private String getFullPublicationOrThesis(Publication pub, int docType) {
		
		// full article name
		StringBuilder string = new StringBuilder();
		
		if(docType == 1) { // article
			
			// add names
			for(Student s : pub.getStudent())
				string.append(converName(s.getStudentFullName())).append(", ");
			
			for(Teacher t : pub.getTeachers())
				string.append(converName(t.getTeacherFullName())).append(", ");
			
			string.deleteCharAt(string.length()-2); // delete last comma
			
			string.append(pub.getPublicationTitle()); // add title
			
			string.append(" / "); 
			
			// add names (reverse)
			for(Student s : pub.getStudent())
				string.append(converNameReverse(s.getStudentFullName())).append(", ");
			
			for(Teacher t : pub.getTeachers())
				string.append(converNameReverse(t.getTeacherFullName())).append(", ");
			
			string.deleteCharAt(string.length()-2);
			
			string.append("// "); 
			
			string.append(pub.getPublicationMag() + '.'); // add journal
			
			string.append(" – ");
			
			string.append(pub.getPublicationPlace() + ".: "); 
			
			string.append(pub.getPublicationPubl() + ", ");
			
			string.append(pub.getPublicationDate() + ". ");
			
			string.append("– ");
			
			string.append(pub.getPublicationMagNum() + ". ");
			
			string.append('–');
			
			string.append(' ' + pub.getPublicationPage() + '.');
			
		} else { // thesis
			
			for(Student s : pub.getStudent())
				string.append(converName(s.getStudentFullName())).append(", ");
			
			for(Teacher t : pub.getTeachers())
				string.append(converName(t.getTeacherFullName())).append(", ");
			
			string.deleteCharAt(string.length()-2); // delete last comma
			
			string.append(pub.getPublicationTitle() + " [Текст] / "); // add title
			
			// add reverse names
			
			for(Student s : pub.getStudent())
				string.append(converNameReverse(s.getStudentFullName())).append(", ");
			
			for(Teacher t : pub.getTeachers())
				string.append(converNameReverse(t.getTeacherFullName())).append(", ");
			
			string.deleteCharAt(string.length()-2); // delete last comma
			
			string.append("// ").append(pub.getPublicationMag()); // add name of meeting
			
			string.append(" – " + pub.getPublicationPlace()).append("., ");
			
			string.append(pub.getPublicationDate() + ". ");
			
			string.append('–');
			
			string.append(' ' + pub.getPublicationPage()).append('.');
		}
		
		return string.toString();
	}	
	
	@RequestMapping(value = "/viewRezultDoc.html", method = RequestMethod.GET)
	public ModelAndView viewRezultList(@RequestParam(value = "groups", required = false) Long[] groups,
								       @RequestParam(value = "okr") int okr) {
		
		ModelAndView mav = new ModelAndView("rezDocumentView"); // create Model and View
		
		List<Student> studentList = new LinkedList<Student>(); // list for all students
		
		for(Long g : groups)  // add all students to list
			studentList.addAll(gss.getGroupById(g).getStudents());
			
		mav.getModelMap().put("studentList", studentList); // add list to model
		mav.getModelMap().put("groups", groups);
		mav.getModelMap().put("Allgroups", gss.selectAllGroups());
		mav.getModelMap().put("okr", okr);
			
		return mav;
	}
	
	/*
	 * Get achievement value
	 * */
	private double getAchievementMark(Long studentId) {
		
		double totalAchievments = 0.0; // achievement mark
		List<Olympiad> olympiadsList = ol.selectOlympiadsByStudent(studentId);
		List<Publication> publicationsList = ps.selectPublicationsByStudent(studentId);
		
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
			if(p.getPublicationType().equals("Міжнародна конференція"))
				totalAchievments += 2.0/(p.getStudent().size()+p.getTeachers().size());
			if(p.getPublicationType().equals("Всеукраїнська конференція"))
				totalAchievments += 1.0/(p.getStudent().size()+p.getTeachers().size());
			if(p.getPublicationType().equals("Університетська"))
				totalAchievments += 0.5/(p.getStudent().size()+p.getTeachers().size());
		}
		
		if(totalAchievments > 3.0)
			return 3.0; // we add only 3.0
		else
			return totalAchievments;
	}
	
	/*
	 * Get examination value
	 * */
	private ExamMark getExamMark(Long studentId) {
		
		double temp1 = 0.0;
		double temp2 = 0.0;
		List<Examination> examList = es.selectAllExamsByStudent(studentId);
		
		for(Examination e : examList) {	
			if("MainExam".equals(e.getExaminationName()))
				switch(e.getExaminationMark()) {
					case "A": temp1+=10.0; break;
					case "B": temp1+=9.0; break;
					case "C": temp1+=8.0; break;
					case "D": temp1+=7.0; break;
					case "E": temp1+=6.0; break;
					case "F": temp1+=4.0; break;
				}
			else
				switch(e.getExaminationMark()) {
					case "A": temp2+=5.0; break;
					case "B": temp2+=4.5; break;
					case "C": temp2+=4.0; break;
					case "D": temp2+=3.5; break;
					case "E": temp2+=3.0; break;
					case "F": temp2+=2.0; break;
				}
		}
		
		return new ExamMark(temp2, temp1);
	}
	
	/*
	 * Get progress value
	 * */
	private double getProgressMark(Long studentId) {
		
		double progressMark = 0.0;
		List<Progress> progressList = pss.selectAllProgressByStudent(studentId);
		
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
			return progressMark/progressList.size();
		else
			return 0.0;
	}
	
	private class ExamMark {
		
		public ExamMark(Double english, Double main) {
			this.english = english;
			this.main = main;
		}
		
		public Double english = 0.0;
		public Double main = 0.0;
	}
}
