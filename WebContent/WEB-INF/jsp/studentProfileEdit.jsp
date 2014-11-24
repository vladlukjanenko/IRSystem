<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Анкета студента</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
	    <link rel="stylesheet" type="text/css" href="<c:url value="resources/jquery-ui.css"/>" />
	    <link rel="stylesheet" href="<c:url value="resources/bootstrap/css/bootstrap.css"/>" media="screen"> 
	    <link rel="stylesheet" type="text/css" href="<c:url value="resources/datepicker/css/datepicker.css"/>" /> 
	     
	    <script type="text/javascript" 
	    	    src="<c:url value="resources/jquery.js"/>"></script>
	    <script type="text/javascript" 
	    	    src="<c:url value="resources/bootstrap/js/bootstrap.min.js"/>"></script> 
	    <script type="text/javascript" 
	    	    src="<c:url value="resources/jquery-ui.js"/>"></script>
	    <script type="text/javascript" 
	    	    src="<c:url value="resources/js/newStudent.js"/>"></script> 
		<script type="text/javascript" 
	    	    src="<c:url value="resources/datepicker/js/bootstrap-datepicker.js"/>"></script>
	    
	   	<script>
	   	$(function() {
	   	    var availableTags = new Array();
	   	    var univerTags = new Array();
	   	    
	   	    
		   	<c:forEach var="name" items="${allStudents}">
		   		availableTags.push("${name}");
		   	</c:forEach>
		   	
	   	    $( "#searchName" ).autocomplete({
	   	      source: availableTags
	   	    });
	   	  });
	   	</script>
	   	
	   	<script type="text/javascript">
	   	var globalName = "";
	   	
	   	var examSubject = document.getElementById('subject');
	   	var progSubject = document.getElementById('sub');
	   	
	   		function updatePage() {
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/updatePage.html",
	   				contentType: "application/json; charset=utf-8",
	   			    dataType: "json",
	   				data: "{\"searchName\":" + "\"" + $('#searchName').val() + "\"}",
	   				success: function(response) {
	   					
	   					if (response.error == 'error') {
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Студента не знайдено</span></div>' ).insertAfter( $( "#errSearch" ) );
	   						$('#searchName').val('');
	   					} else {
	   						globalName = response.studentFullName;
	   						
	   						$('#searchName').val('');
		   					$('#studName').html('Основні дані: ' + response.studentFullName);
		   					$('#button').attr('href', '/IRSystem/studentProfileView.html?id=' + response.studentId);
		   					
	   					}
    				},
    				error: function(e) {
    					alert('Error' + e);
    				}
	   				
	   			});
	   		}
	   	
	   		function changeName() {
	   			var studentFullName = $('#studentFullName1').val();
	   			var findName = globalName;
	   			
	   			studentFullName = studentFullName.replace(/\'/g, "`");
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/studentProfileEdit.html",
	   				data: "studentFullName=" + studentFullName +
	   				      "&funkName=" + 'changeName' +
	   					  "&findName=" + findName,
	   				success: function(response) {
	   					if(response == 'errorName') {
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Ім\'я студента не правильно введене або > 120 символів</span></div>' ).insertAfter( $( "#nameNotice" ) );
    					} else if(response == 'successName') {
	   						$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Ім\'я студента змінене</span></div>' ).insertAfter( $( "#nameNotice" ) );
    						$('#studentFullName1').val('');
    					}
    				},
    				error: function(e) {
    					alert('Error' + e);
    				}
	   				
	   			});
	   		}
	   		
	   		function changeGroup() {
	   			var groupStudent = $('#groupStudent1').val();
	   			var findName = globalName;
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/studentProfileEdit.html",
	   				data: "groupStudent=" + groupStudent +
	   				      "&funkName=" + 'changeGroup' +
 					      "&findName=" + findName,
	   				success: function(response) {
	   					if(response == 'success')
   							$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Групу змінено</span></div>' ).insertAfter( $( "#groupNotice" ) );
    				},
    				error: function(e) {
    					alert('Error' + e);
    				}
	   				
	   			});
	   		}
	   		
	   		function changeBook() {
	   			var studentBook = $('#studentBook1').val();
	   			var findName = globalName;
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/studentProfileEdit.html",
	   				data: "studentBook=" + studentBook +
	   		              "&funkName=" + 'changeBook' +
 					      "&findName=" + findName,
	   				success: function(response) {
	   					if(response == 'errorBook') {
   							$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Залікова книга не правильно введена</span></div>' ).insertAfter( $( "#bookNotice" ) );
    					} else if(response == 'successBook') {
   							$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Залікову книгу змінено</span></div>' ).insertAfter( $( "#bookNotice" ) );
    						$('#studentBook1').val('');
    					}
    					$('#studentBook').val('');
    				},
    				error: function(e) {
    					alert('Error' + e);
    				}
	   				
	   			});
	   		}
	   		
	   		function changeEnter() {
	   			var studentEnter = $('#studentEnter1').val();
	   			var findName = globalName;
	   			
	   			if(studentEnter == "")
	   				studentEnter = 0;
	   			
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/studentProfileEdit.html",
	   				data: "studentEnter=" + studentEnter +
 		                  "&funkName=" + 'changeEnter' +
 					      "&findName=" + findName,
	   				success: function(response) {
	   					if(response == 'errorEnter') {
   							$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Рік вступу введений неправильно</span></div>' ).insertAfter( $( "#enterNotice" ) );
    					} else if(response == 'successEnter') {
   							$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Рік вступу змінений</span></div>' ).insertAfter( $( "#enterNotice" ) );
    						$('#studentEnter1').val('');
    					}
    				},
    				error: function(e) {
    					alert('Error' + e);
    				}
	   				
	   			});
	   		}
	   		
	   		function changeOKR() {
	   			var studentOKR = $('#studentOKR1').val();
	   			var findName = globalName;
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/studentProfileEdit.html",
	   				data: "studentOKR=" + studentOKR +
	                      "&funkName=" + 'changeOKR' +
 					      "&findName=" + findName,
	   				success: function(response) {
	   					if(response == 'success')
							$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>ОКР змінено</span></div>' ).insertAfter( $( "#okrNotice" ) );
    				},
    				error: function(e) {
    					alert('Error' + e);
    				}
	   				
	   			});
	   		}
	   		
	   		function changeMode() {
	   			var studentMode = $('#studentMode1').val();
	   			var findName = globalName;
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/studentProfileEdit.html",
	   				data: "studentMode=" + studentMode +
	                      "&funkName=" + 'changeMode' +
 					      "&findName=" + findName,
	   				success: function(response) {
	   					if (response == 'success')
							$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Тип навчання змінено</span></div>' ).insertAfter( $( "#modeNotice" ) );
    				},
    				error: function(e) {
    					alert('Error' + e);
    				}
	   				
	   			});
	   		}
	   		
	   		function addOlympiad() {
	   			var olympiadDirection = $('#olympiadDirection').val();
	   			var olympiadPlace = $('#olympiadPlace').val();
	   			var olympiadType = $('#olympiadType').val();
	   			var date1 = $('#date').val();
	   			var findName = globalName;
	   			
	   			olympiadDirection = olympiadDirection.replace(/\'/g, "`");
	   			
	   			var core;
	   			if ($('#core').is(":checked")) 
	   				core = 'True';
	   			else
	   				core = 'False';
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/studentProfileEdit.html",
	   				data: "olympiadDirection=" + olympiadDirection +
	   					  "&olympiadPlace=" + olympiadPlace +
	   					  "&olympiadType=" + olympiadType +
	   					  "&olympiadDate=" + $.trim(date1) +
	   					  "&funkName=" + 'addOlympiad' +
	   					  "&findName=" + findName +
	   					  "&olympiadCore=" + core,
	   				success: function(response) {
	   					if(response == 'errorDirection') {
							$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Ім\'я олімпіади не правильно введене або > 900 символів</span></div>' ).insertAfter( $( "#olNotice" ) );
    					} else if(response == 'successOl') {
							$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Олімпіаду додано</span></div>' ).insertAfter( $( "#olNotice" ) );
    						$('#olympiadDirection').val('');
    					}
    				},
    				error: function(e) {
    					alert('Error' + e);
    				}
	   				
	   			});
	   		}
	   		
	   		
	   		
	   		function addExam() {
	   			var examinationMark = $('#examinationMark').val();
	   			var findName = globalName;
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/studentProfileEdit.html",
	   				data: "examinationMark=" + examinationMark +
	   					  "&funkName=" + 'addExam' +
	   					  "&examinationName=" + 'MainExam' + 
	   					  "&findName=" + findName,
	   				success: function(response) {
	   					if(response == 'success') {
							$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Оцінка з фахового випробування додана</span></div>' ).insertAfter( $( "#examNotice" ) );

	   					} else if(response == 'error') {
							$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Оцінка з фахового випробування вже додана</span></div>' ).insertAfter( $( "#examNotice" ) );

	   					}
    				},
    				error: function(e) {
    					alert('Error' + e);
    				}
	   				
	   			});
	   		}
	   		
	   		$(function() {
	   			$("#date1").datepicker({
	   				format: " yyyy",
				    viewMode: "years",
				    minViewMode: "years"
	   			});
	   		});
	   		
	   		function addEngExam() {
	   			var examMark = $('#engExamMark').val();
	   			var findName = globalName;
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/studentProfileEdit.html",
	   				data: "examinationMark=" + examMark +
	   					  "&funkName=" + 'addExam' +
	   					  "&examinationName=" + 'EngExam' + 
	   					  "&findName=" + findName,
	   				success: function(response) {
	   					if(response == 'success') {
							$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Оцінка з іноземної мови додана</span></div>' ).insertAfter( $( "#examNotice" ) );

	   					} else if(response == 'error') {
							$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Оцінка з іноземної мови вже додана</span></div>' ).insertAfter( $( "#examNotice" ) );

	   					}
    				},
    				error: function(e) {
    					alert('Error' + e);
    				}
	   				
	   			});
	   		}
	   		
	   		function addProgress() {
	   			var progressMark = $('#progressMark').val();
	   			var subject = $('#sub').val();
	   			var findName = globalName;
	   			
	   			var type = 4;
	   			if ($('#isExam').is(":checked")) 
	   				type = 1;
	   			else if($('#isKR').is(":checked"))
	   				type= 2;
	   			else if($('#isTest').is(":checked"))
	   				type= 3;
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/studentProfileEdit.html",
	   				data: "progressMark=" + progressMark +
	   					  "&subjectProg=" + subject +
	   					  "&funkName=" + 'addProgress' +
	   					  "&findName=" + findName +
	   					  "&progressExam=" + type,
	   				success: function(response) {
	   					if(response == 'success') {
	   						if(type == 1)
								$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>' + 'Успішність додана: ' + subject + ' ' + progressMark + ' (екзамен)' + '</span></div>' ).insertAfter( $( "#progNotice" ) );
	   						else if(type == 2)
								$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>' + 'Успішність додана: ' + subject + ' ' + progressMark + ' (КП(Р))' + '</span></div>' ).insertAfter( $( "#progNotice" ) );
	   						else if(type == 3)
								$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>' + 'Успішність додана: ' + subject + ' ' + progressMark + ' (ДЗ)' + '</span></div>' ).insertAfter( $( "#progNotice" ) );
	   						else
								$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>' + 'Успішність додана: ' + subject + ' ' + progressMark + ' (залік)' + '</span></div>' ).insertAfter( $( "#progNotice" ) );
	   						
	   						$('#progressError').html('');
	   					} else if (response == 'error') {
							$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Оцінка вже додана</span></div>' ).insertAfter( $( "#progNotice" ) );
	   					}
    				},
    				error: function(e) {
    					alert('Error' + e);
    				}
	   				
	   			});
	   		}
	   		
	   		
	   	</script>
	</head>
	<body>
		<div class="container">
			<div class="navbar">
				<nav class="navbar-inner">
					<ul class="nav">
					  <li><a href="addData.html">Загальна інформація</a></li>
					  <li class="divider-vertical"></li>
					  <li class="dropdown">
					  	  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Студент <b class="caret"></b></a>
						  <ul class="dropdown-menu">
						  	<li><a href="#" role="button" data-toggle="modal" data-target="#modalWindow">Додати новго студента</a></li>
					      	<li><a href="studentProfileEdit.html">Анкета студента</a></li>
						  </ul>
					  </li>
					  <li class="divider-vertical"></li>
				      <li><a href="addPublications.html">Публікації</a></li>
				      <li class="divider-vertical"></li>
				      <li><a href="genDoc.html">Документація</a></li>
				      <li class="divider-vertical"></li>
				      <li><a href="viewStudents.html">Керівництво користувача</a></li>
					</ul>
				</nav>
			</div>
			
			<div id="modalWindow" class="modal hide fade" tabindex="-1" role="dialog" arialabelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Введіть нового студента</h4>
						</div>
						<div class="modal-body">
							<input type="text" id="studentFullName" placeholder="П.І.Б."/>
							<input type="text" id="studentBook" style="margin-left: 15px;" class="input-small" placeholder="Залікова книга"/>
							<input type="text" id="studentEnter" style="margin-left: 15px;" class="input-small" placeholder="Рік вступу"/>
							<div class="row">
								<div class="span2">
									<label>Група</label>
									<select id="groupStudent" class="input-small">
										<c:forEach var="g" items="${groups}"> 
											<option value="${g.groupStudentNumber}" >${g.groupStudentNumber}</option>
										</c:forEach>
									</select>
								</div>
								<div class="span2">
									<label>ОКР</label>
									<select id="studentOKR" class="input-small">
										<option value="Бакалавр">Бакалавр</option>
										<option value="Спеціаліст">Спеціаліст</option>
										<option value="Магістр">Магістр</option>
									</select>
								</div>
								<div class="span2">
									<label>Форма навчання</label>
									<select id="studentMode" class="input-small">
										<option value="Денна">Денна</option>
										<option value="Заочна">Заочна</option>
									</select>
								</div>
							</div>
							<div id="alertId"></div>
						</div>
						<div class="modal-footer">
							<input type="submit" class="btn btn-primary" value="Додати" onclick="validateAjax()" />
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="span9 offset1 well">
					<div class="span5">
						<input id="searchName" type="text"  class="search-query"/>
						<input type="submit" class="btn btn-success" value="Знайти" onclick="updatePage()"/>
					</div>
					<div class="span3">
						<div id="errSearch"></div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="span9 offset1 well">
					<legend id="studName">Основні дані:</legend>
					<div class="row">
						<div class="span3">	
							<input id="studentFullName1" placeholder="Ім'я студента" type="text"/>
							<input id="nameNotice" type="submit" class="btn btn-success" value="Змінити ім'я" onclick="changeName()" />
							
							<select id="groupStudent1" style="margin-top: 10px;">
								<c:forEach var="g" items="${groups}"> 
									<option value="${g.groupStudentNumber}" >${g.groupStudentNumber}</option>
								</c:forEach>
							</select>
							<input id="groupNotice" type="submit" class="btn btn-success" value="Змінити групу" onclick="changeGroup()" />
						</div>
						
						<div class="span3">
							<input id="studentBook1" placeholder="Залікова книга" type="text" />
							<input id="bookNotice" type="submit" class="btn btn-success" value="Змінити номер" onclick="changeBook()"></input>
							
							<select id="studentOKR1" style="margin-top: 10px;">
								<option  value="Бакалавр">Бакалавр</option>
								<option value="Спеціаліст">Среціаліст</option>
								<option value="Магістр">Магістр</option>
							</select>
							<input id="okrNotice" type="submit" class="btn btn-success" value="Змінити ОКР" onclick="changeOKR()" />
						</div>
						
						<div class="span3">
							<input id="studentEnter1" placeholder="Рік вступу" type="text"/>
							<input id="enterNotice" type="submit" class="btn btn-success" value="Змінити рік" onclick="changeEnter()" />
							
							<select id="studentMode1" style="margin-top: 10px;">
								<option value="Денна">Денна</option>
								<option value="Заочна">Заочна</option>
							</select>
							<input id="modeNotice" type="submit" class="btn btn-success" value="Змінити" onclick="changeMode()" />
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="span9 offset1 well">
					<legend>Олімпіади</legend>
					<input id="olympiadDirection" placeholder="Назва олімпіади" type="text"/>
					<select id="olympiadPlace">
						<option value="1">Перше призове місце</option>
						<option  value="2">Друге призове місце</option>
						<option value="3">Третє призове місце</option>
					</select>
					<select id="olympiadType">
						<option value="Міжнародна">Міжнародна</option>
						<option value="Всеукраїнська">Всеукраїнська</option>
						<option value="Університетська">Університетська</option>
					</select>
					<div class="input-append date" id="date1" data-date="12-02-2012" data-date-format="dd-mm-yyyy" data-date-viewmode="years">
						<input class="span2" id="date" size="16" value="" type="text" placeholder="Дата" readonly="">
						<span class="add-on"><i class="icon-calendar"></i></span>
					</div>
					<label><input id="core" name="prof" type="checkbox" value="True"> Профільна</label>
					<input type="submit" class="btn btn-success" value="Додати олімпіаду" onclick="addOlympiad()" />
					<div id="olymp" style="color:green"></div>
					<div id="dirError" style="color:red"></div>
				</div>
			</div>
			
			<div class="row">
				<div class="span3 offset1" style="width: 260px;">
					<div id="olNotice"></div>
				</div>
			</div>
			
			<div class="row"> 
				<div class="span4 offset1 well" style="height: 295px;">
					<legend>Вступні іспити</legend>
					<label>Оцінка з фахового випробування</label>
					<select id="examinationMark">
						<option value="A">A</option>
						<option value="B">B</option>
						<option value="C">C</option>
						<option value="D">D</option>
						<option value="E">E</option>
						<option value="F">F</option>
						<option value="Fx">Fx</option>
					</select><br/>  
					<input type="submit" class="btn btn-success" value="Додати" onclick="addExam()" />
					
					<label>Екзамен з англійської мови</label>
					<select id="engExamMark">
						<option value="A">A</option>
						<option value="B">B</option>
						<option value="C">C</option>
						<option value="D">D</option>
						<option value="E">E</option>
						<option value="F">F</option>
						<option value="Fx">Fx</option>
					</select><br/>
					<input type="submit" class="btn btn-success" value="Додати" onclick="addEngExam()" />
					<div id="exam" style="color:green"></div>
					<div id="examError" style="color:red"></div>
				</div>
			
				<div class="span4 well" style="margin-left: 60px;">
					<legend>Успішність</legend>
					<label>Оцінка з дисципліни</label>
					<select id="sub">
						<c:forEach var="s" items="${subjects}"> 
							<option value="${s.subjectTitle}" >${s.subjectTitle}</option>
						</c:forEach>
					</select>
					<label>Отримана оцінка</label>
					<select id="progressMark">
						<option value="A">A</option>
						<option value="B">B</option>
						<option value="C">C</option>
						<option value="D">D</option>
						<option value="E">E</option>
						<option value="F">F</option>
						<option value="Fx">Fx</option>
					</select>
					<label>Екзамен <input id="isExam" name="myGroup" type="radio" value="1"></label>
					<label>КП(Р) <input id="isKR" name="myGroup" type="radio" value="2"></label>
					<label>ДЗ <input id="isTest" name="myGroup" type="radio" value="3"></label>
					<input type="submit" class="btn btn-success" value="Додати дисципліну" onclick="addProgress()" />
					<div id="progress" style="color:green"></div>
					<div id="progressError" style="color:red"></div>
				</div>
			</div>
			
			<div class="row"> 
				<div class="span4 offset1" style="width: 260px;">
					<div id="examNotice"></div>
				</div>
				
				<div class="span4" style="width: 260px; margin-left: 140px;">
					<div id="progNotice"></div>
				</div>
			</div>
			
			<div class="row">
				<center><a href="" id="button" target="_blank"><input class="btn btn-primary" value="Переглянути анкету" /></a></center>
			</div>
		</div>
	</body>
</html>