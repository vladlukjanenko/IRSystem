<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="<c:url value="resources/jquery-ui.css"/>" />
	    <link rel="stylesheet" href="<c:url value="resources/bootstrap/css/bootstrap.css"/>" media="screen"> 
	     
	    <script type="text/javascript" 
	    	    src="<c:url value="resources/jquery.js"/>"></script> 
	    <script type="text/javascript" 
	    	    src="<c:url value="resources/bootstrap/js/bootstrap.min.js"/>"></script> 
	    <script type="text/javascript" 
	    	    src="<c:url value="resources/jquery-ui.js"/>"></script>
	    <script type="text/javascript" 
	    	    src="<c:url value="resources/js/newStudent.js"/>"></script>
		
		<style>
	    	.tableHeader {
	    		background-color: #c7edb7;
	    	}
	    </style>
		
 		<script type="text/javascript">
 		
	 		$(function() { 
	 		   $(".DELETE_PUBL").click(function(event){
		 		   event.preventDefault();
		 		   var num = $(this).attr("title");
		 		   var clas = "." + num;
					
		 		   $.ajax({
		   				type: "POST",
		   				url: "/IRSystem/deletePublication.html",
		   				data: "publicationId=" + num + 
		   					  "&studentId=" + ${student.studentId},
		   				success: function(response) {
		   					if(response == 'successPubl') {
		   						
		   						$(clas).remove();
		   							   						
		   					} else if(response == 'errorPubl') {
		   						alert("Помилка при видаленні рядка. Спробуйте перезавантажити сторінку.");
		   					}
	   				},
	   				error: function(e) {
	   					alert('Error' + e);
	   				}
		   				
		   			}); 
	 		   });
	 		});
	 		
	 		$(function() { 
		 		   $(".DELETE_OLYM").click(function(event){
			 		   event.preventDefault();
			 		   var num = $(this).attr("title");
			 		   var clas = "." + num + "o";
			 		   
			 		   $.ajax({
			   				type: "POST",
			   				url: "/IRSystem/deleteOlympiad.html",
			   				data: "olympiadId=" + num,
			   				success: function(response) {
			   					if(response == 'successOlym') {
			   						$(clas).remove();   						
			   					} else if(response == 'errorOlym') {
			   						alert("Помилка при видаленні рядка. Спробуйте перезавантажити сторінку.");
			   					}
		   				},
		   				error: function(e) {
		   					alert('Error' + e);
		   				}
			   				
			   			});
		 		   });
		 		});
	 		
	 		$(function() { 
		 		   $(".DELETE_PROG").click(function(event){
			 		   event.preventDefault();
			 		   var num = $(this).attr("title");
			 		   var clas = "." + num + "p";
					
			 		   $.ajax({
			   				type: "POST",
			   				url: "/IRSystem/deleteProgress.html",
			   				data: "progressId=" + num,
			   				success: function(response) {
			   					if(response == 'successProg') {
			   						$(clas).remove();   						
			   					} else if(response == 'errorProg') {
			   						alert("Помилка при видаленні рядка. Спробуйте перезавантажити сторінку.");
			   					}
		   				},
		   				error: function(e) {
		   					alert('Error' + e);
		   				}
			   				
			   			});
		 		   });
		 		});
	 		
	 		$(function() { 
		 		   $(".DELETE_EXAM").click(function(event){
			 		   event.preventDefault();
			 		   var num = $(this).attr("title");
			 		   var clas = "." + num + "e";
			 		   
			 		   $.ajax({
			   				type: "POST",
			   				url: "/IRSystem/deleteExamination.html",
			   				data: "examinationId=" + num,
			   				success: function(response) {
			   					if(response == 'successExam') {
			   						$(clas).remove();   
			   					} else if(response == 'errorExam') {
			   						alert("Помилка при видаленні рядка. Спробуйте перезавантажити сторінку.");
			   					}
		   				},
		   				error: function(e) {
		   					alert('Error' + e);
		   				}
			   				
			   			});
		 		   });
		 		});
	 		
	 		$(function() { 
		 		   $(".DELETE_STUD").click(function(event){
			 		   event.preventDefault();
			 		   var num = $(this).attr("title");
			 
			 		   $.ajax({
			   				type: "POST",
			   				url: "/IRSystem/deleteStudent.html",
			   				data: "studentId=" + num,
			   				success: function(response) {
			   					if(response == 'success') { 
			   						window.location.href = "index.html";
			   					} else if(response == 'error') {
			   						alert("Помилка при видаленні рядка. Спробуйте перезавантажити сторінку.");
			   					}
		   				},
		   				error: function(e) {
		   					alert('Error' + e);
		   				}
			   				
			   			});
		 		   });
		 		});
	 		
	 	   function validateAjax() {
		   		var studentFullName = $('#studentFullName').val();
		   		var studentBook = $('#studentBook').val();
		   		var studentEnter = $('#studentEnter').val();
		   		var groupStudent = $('#groupStudent').val();
		   		var studentOKR = $('#studentOKR').val();
		   	
		   		if(studentEnter == '')
		   			studentEnter = 0;
		   		
		   			$.ajax({
							type: "POST",
		   				url: "/IRSystem/newStudent.html",
		   				data: "studentFullName=" + studentFullName + 
		   					  "&studentBook=" + studentBook +
		   					  "&studentEnter=" + studentEnter +
		   					  "&groupStudent=" + groupStudent +
		   					  "&studentOKR=" + studentOKR,
		   				success: function(response) {
		   					if(response == 'errorName') {
		   						$('#error').html('Поле імені студента не правильно введене або > 120 символів');
		   						$('#success').html('');
		   					} else if(response == 'errorBook') {
		   						$('#error').html('Поле залікової книги не правильно введене');
		   						$('#success').html('');
		   					} else if(response == 'errorEnter') {
		   						$('#error').html('Поле року вступу не правильно введене');
		   						$('#success').html('');
		   					} else if (response == 'success') {
			    					$('#success').html('Студент ' + studentFullName + ' успішно доданий');
			    					$('#error').html('');
			    					$('#studentFullName').val('');
			    					$('#studentBook').val('');
			    					$('#studentEnter').val('');
		   					}
		   				},
		   				error: function(e) {
		   					alert('Error' + e);
		   				}
		   			});
		   		}
			   
			   $(function () {
					$("#dialog").dialog({
						autoOpen: false,
						width: 410,
						modal: false,
						resizable: false,
						show: 'blind',
						hide: 'blind'
					});
					
					$("#open").click(function(){
						$("#dialog").dialog("open");
					});
			   });
			   
			   /* search in tables */
				$(document).ready(function(){
					// Write on keyup event of keyword input element
					$("#progSearch").keyup(function(){
						// When value of the input is not blank
						if( $(this).val() != "")
						{
							// Show only matching TR, hide rest of them
							$("#progress tbody>tr").hide();
							$("#progress td:contains-ci('" + $(this).val() + "')").parent("tr").show();
						}
						else
						{
							// When there is no input or clean again, show everything back
							$("#progress tbody>tr").show();
						}
					});
				});
		 		
				// jQuery expression for case-insensitive filter
				$.extend($.expr[":"], 
				{
				    "contains-ci": function(elem, i, match, array) 
					{
						return (elem.textContent || elem.innerText || $(elem).text() || "").toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
					}
				});
			   
 		</script>
	</head>
	
	<body class="body">
	
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
								<div class="span3">
									<label>Група</label>
									<select id="groupStudent">
										<c:forEach var="g" items="${groups}"> 
											<option value="${g.groupStudentNumber}" >${g.groupStudentNumber}</option>
										</c:forEach>
									</select>
								</div>
								<div class="span3">
									<label>ОКР</label>
									<select id="studentOKR">
										<option value="Бакалавр">Бакалавр</option>
										<option value="Спеціаліст">Среціаліст</option>
										<option value="Магістр">Магістр</option>
									</select>
								</div>
							</div>
							<div id="success" style="color:green"></div>
							<div id="error" style="color:red"></div>
						</div>
						<div class="modal-footer">
							<input type="submit" class="btn btn-primary" value="Додати" onclick="validateAjax()" />
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="span10 offset1 well">
					<legend>${student.studentFullName}
					<a style="float: right;" class="DELETE_STUD btn btn-warning" href="#" title="${student.studentId}">Видалити студента</a>
					</legend>
					
					
					<div class="row">
						<div class="span6">
							<label><b>Номер залікової книги:</b> ${student.studentBook}</label>
							<label><b>Група:</b> ${student.groupStudent.groupStudentNumber}</label>
							<label><b>Рік вступу:</b> ${student.studentEnter}</label>
							<label><b>ОКР:</b> ${student.studentOKR}</label>
							<label><b>Форма навчання:</b> ${student.studentMode}</label>
						</div>
						<div class="span4">
							<label><b>Бал за наукові досягнення:</b> ${achievmentsMark}</label>
							<label><b>Середньозважена оцінка:</b> ${progressMark}</label>
							<label><b>Бал за вступні іспити:</b> ${examMark}</label>
							<label><b>Середній бал: </b> ${totalMark}</label>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<legend>Наукові досягнення</legend>
				<table id="publications" class="table table-hover table-striped table-condensed">
					<thead>
						<tr class="tableHeader" >
							<td>№</td>
							<td>Автори</td>
							<td>Назва публікації</td>
							<td>Журнал</td>
							<td>Тип</td>
							<td>Місце публікації</td>
							<td>№ журналу</td>
							<td>Рік проведення</td>
							<td>Сторінки</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="publication" items="${publications}" varStatus="loopCounter">
							<tr class="${publication.publicationId}">
								<td>${loopCounter.count}</td>
								<td>
									<!-- view all teachers -->
									<c:forEach var="t" items="${publication.teachers}">
										${t.teacherFullName}<br/>
									</c:forEach>
									
									<!-- view all teachers -->
									<c:forEach var="s" items="${publication.student}">
										${s.studentFullName}<br/>
									</c:forEach>
								</td>
								<td style="word-break: break-all; word-wrap: break-word;">${publication.publicationTitle}</td>
								<td>${publication.publicationMag}</td>
								<td>${publication.publicationType}</td>
								<td>${publication.publicationPlace}</td>
								<td>${publication.publicationMagNum}</td>
								<td>${publication.publicationDate}</td>
								<td>${publication.publicationPage}</td>
								<td><a class="DELETE_PUBL" href="#" title="${publication.publicationId}"><i class="icon-remove"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<div class="row">
				<legend>Олімпіади</legend>
				<table id="olympiads" class="table table-hover table-striped table-condensed">
					<thead>
						<tr class="tableHeader">
							<td>№</td>
							<td>Назва</td>
							<td>Призове місце</td>
							<td>Тип</td>
							<td>Рік проведення</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="olympiad" items="${olympiads}" varStatus="loopCounter">
							<tr class="${olympiad.olympiadId}o">
								<td>${loopCounter.count}</td>
								<td>${olympiad.olympiadDirection}</td>
								<td>${olympiad.olympiadPlace}</td>
								<td>${olympiad.olympiadType}</td>
								<td>${olympiad.olympiadDate}</td>
								<td><a class="DELETE_OLYM" href="#" title="${olympiad.olympiadId}"><i class="icon-remove"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>			
			</div>
			
			<div class="row">
				<legend>Вступні випробування</legend>
				<table class="table table-hover table-striped table-condensed">
					<thead>
						<tr class="tableHeader">
							<td>№</td>
							<td>Назва</td>
							<td>Оцінка</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="exam" items="${exams}" varStatus="loopCounter">
							<tr class="${exam.examinationId}e">
								<td>${loopCounter.count}</td>
								<c:if test="${\"MainExam\" == exam.examinationName}">
									<td>Фахове випробування</td>
								</c:if>
								<c:if test="${\"EngExam\" == exam.examinationName}">
									<td>Іноземна мова</td>
								</c:if>
								<td>${exam.examinationMark}</td>
								<td><a class="DELETE_EXAM" href="#" title="${exam.examinationId}"><i class="icon-remove"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<div class="row">
				<legend>Результати успішності</legend>
				<table id="progress" class="table table-hover table-striped table-condensed">
					<thead>
						<tr class="tableHeader">
							<td>№</td>
							<td>Назва предмету</td>
							<td>Оцінка</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td><input id="progSearch" style="margin-bottom: 1px; height: 15px;" placeholder="Введіть дисципліну" type="text"></td>
							<td></td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="prog" items="${progress}" varStatus="loopCounter">
							<tr class="${prog.progressId}p">
								<td>${loopCounter.count}</td>
								<td>${prog.subject.subjectTitle}</td>
								<td>${prog.progressMark}</td>
								<td><a class="DELETE_PROG" href="#" title="${prog.progressId}"><i class="icon-remove"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>