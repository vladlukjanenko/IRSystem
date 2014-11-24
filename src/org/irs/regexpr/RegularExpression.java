package org.irs.regexpr;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.sound.midi.SysexMessage;

public class RegularExpression {
	
	private static Pattern pattern;
	
	public static boolean studentNameAndTeacherSubject(String studentName) {
		String pat = "²?[à-ÿ`?³?¿?À-ß]+(-²?[à-ÿ`?³?¿?À-ß]+)?\\s(²?[à-ÿ³?`?¿?À-ß]+\\s?)+";
		Pattern pattern = Pattern.compile(pat);
		
		Matcher matcher = pattern.matcher(studentName);
		
		return matcher.matches();
	}
	
	public static boolean checkPage(String pageName) {
		String pat = "[ñÑ]\\.\\s[0-9]+\\s-\\s[0-9]+[ñÑ]?\\.?";
		Pattern pattern = Pattern.compile(pat);
		
		Matcher matcher = pattern.matcher(pageName);

		return matcher.matches();
	}
	
	public static boolean studentBookExprAndGroup(String studentBook) {
		String pat = "(Ñòîðîíí³)?([À-ß]?²?[À-ß]+)*-[0-9]+[à-ÿ]?";
		Pattern pattern = Pattern.compile(pat);
		
		Matcher matcher = pattern.matcher(studentBook);

		return matcher.matches();
	}
	
	// check fields size
	public static boolean checkSizeGroupStudentNumber(String toCheck) {
		
		if(toCheck.length() <= 28)
			return true;
		else
			return false;
	}
	
	public static boolean checkSizeOlympiadDirection(String toCheck) {
		
		if(toCheck.length() <= 898)
			return true;
		else
			return false;
	}	
	
	public static boolean checkSizePublicationTitle(String toCheck) {
		
		if(toCheck.length() <= 648)
			return true;
		else
			return false;
	}
	
	public static boolean checkSizePublicationMag(String toCheck) {
		
		if(toCheck.length() <= 648)
			return true;
		else
			return false;
	}
	
	public static boolean checkSizePublicationMagNum(String toCheck) {
		
		if(toCheck.length() <= 18)
			return true;
		else
			return false;
	}
	
	public static boolean checkSizePublicationPlace(String toCheck) {
		
		if(toCheck.length() <= 118)
			return true;
		else
			return false;
	}
	
	public static boolean checkSizePubl(String toCheck) {
		
		if(toCheck.length() <= 348)
			return true;
		else
			return false;
	}
	
	public static boolean checkSizeStudentFullName(String toCheck) {
		
		if(toCheck.length() <= 118)
			return true;
		else
			return false;
	}
	
	public static boolean checkSizeStudentBook(String toCheck) {
		
		if(toCheck.length() <= 13)
			return true;
		else
			return false;
	}
	
	public static boolean checkSizeSubjectTitle(String toCheck) {
		
		if(toCheck.length() <= 548)
			return true;
		else
			return false;
	}
	
	public static boolean checkSizeTeacherFullName(String toCheck) {
		
		if(toCheck.length() <= 198)
			return true;
		else
			return false;
	}
	
}
