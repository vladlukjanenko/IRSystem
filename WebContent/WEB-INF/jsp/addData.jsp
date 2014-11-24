<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>	
	    <title>Add data</title>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    
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
	   		function addGroupAjax() {
	   			var groupStudentNumber = $('#groupStudentNumber').val();
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/addGroup.html",
	   				contentType: "application/json; charset=utf-8",
	   			    dataType: "json",
	   				data: "{\"groupStudentNumber\":" + "\"" + groupStudentNumber + "\"}",
	   				success: function(response) {
	   					if (response.error == 'errorGroup') {
	   						$("#groupStudentNumber").val("");
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Группа введена неправильно або вже існує</span></div>' ).insertAfter( $( "#group" ) );
	   					} else if (response.success == 'successGroup') {
	   						$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Група ' + groupStudentNumber + ' додана</span></div>' ).insertAfter( $( "#group" ) );
	   						$("#groupStudentNumber").val("");
	   					}
	   				},
	   				error: function(e) {
	   					alert("Error" + e);
	   				}
	   			});
	   		} 
	   		
	   		function addTeacherAjax() {
	   			var teacherFullName = $('#teacherFullName').val();
	   			var teacherStatus = $('#teacherStatus').val();
	   			
	   			teacherFullName = teacherFullName.replace(/\'/g, "`");
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/addTeacher.html",
	   				contentType: "application/json; charset=utf-8",
	   			    dataType: "json",
	   				data: "{\"teacherFullName\":" + "\"" + teacherFullName + "\"" +
	   					  ",\"teacherStatus\":" + "\"" + teacherStatus +"\"" + "}",
	   				success: function(response) {
	   					if (response.error == 'errorTeacher') {
	   						$("#teacherFullName").val("");
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Ім\'я викладача не правильно введене або > 200 символів</span></div>' ).insertAfter( $( "#teacher" ) );
	   					} else if (response.success == 'successTeacher') {
	   						$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Викладач доданий: ' + teacherFullName + '</span></div>' ).insertAfter( $( "#teacher" ) );
	   						$("#teacherFullName").val("");
	   					}
	   				},
	   				error: function(e) {
	   					alert("Error" + e);
	   				}
	   			});
	   		} 
	   		
	   		function addSubjectAjax() {
	   			var subjectTitle = $('#subjectTitle').val();
	   			var subjectHours = $('#subjectHours').val();
	   			
	   			subjectTitle = subjectTitle.replace(/\"/g, "&quot");
	   			subjectTitle = subjectTitle.replace(/\'/g, "`");
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/addSubject.html",
	   				contentType: "application/json; charset=utf-8",
	   			    dataType: "json",
	   				data: "{\"subjectTitle\":" + "\"" + subjectTitle + "\"" +
 						  ",\"subjectHours\":" + "\"" + subjectHours +"\"" + "}",
	   				success: function(response) {
	   					if (response.error == 'errorHour') {
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Кількість годин дисципліни введена неправильно</span></div>' ).insertAfter( $( "#subject" ) );
	   						$("#subjectTitle").val("");
	   						$("#subjectHours").val("");
	   					} else if (response.error == 'errorSubject') {
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Ім\'я дисципліни не правильно введене або > 550 символіво</span></div>' ).insertAfter( $( "#subject" ) );
	   						$("#subjectTitle").val("");
	   						$("#subjectHours").val("");
	   					} else if (response.success == 'successSubject') {
	   						$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Дисципліна ' + subjectTitle + ' додана</span></div>' ).insertAfter( $( "#subject" ) );
	   						$("#subjectTitle").val("");
	   						$("#subjectHours").val("");
	   					}
	   				},
	   				error: function(e) {
	   					alert("Error" + e);
	   				}
	   			});
	   		} 
	 		
	 		$(function() { 
		 		   $(".DELETE_GROUP").click(function(event){
			 		   event.preventDefault();
			 		   var num = $(this).attr("title");
			 		   var clas = "." + num + "g";
						
			 		   $.ajax({
			   				type: "POST",
			   				url: "/IRSystem/deleteGroup.html",
			   				data: "groupId=" + num,
			   				success: function(response) {
			   					if(response == 'success') {
			   						$(clas).remove();   							   						
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
	 		
	 		$(function() { 
		 		   $(".DELETE_TEACHER").click(function(event){
			 		   event.preventDefault();
			 		   var num = $(this).attr("title");
			 		   var clas = "." + num + "t";
						
			 		   $.ajax({
			   				type: "POST",
			   				url: "/IRSystem/deleteTeacher.html",
			   				data: "teacherId=" + num,
			   				success: function(response) {
			   					if(response == 'success') {
			   						$(clas).remove();   							   						
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
	 		
	 		$(function() { 
		 		   $(".DELETE_SUBJECT").click(function(event){
			 		   event.preventDefault();
			 		   var num = $(this).attr("title");
			 		   var clas = "." + num + "s";
						
			 		   $.ajax({
			   				type: "POST",
			   				url: "/IRSystem/deleteSubject.html",
			   				data: "subjectId=" + num,
			   				success: function(response) {
			   					if(response == 'success') {
			   						$(clas).remove();   							   						
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
	 		
	 		$(function() {
	 			for(var i = 1; i < 5; i++) 
	 				$("#table"+i).hide();	
	 		});
	 		
	 		
	 		function showTable(Id) {
	 			for(var i = 1; i < 5; i++) 
	 				$("#table"+i).hide();	
	 			$("#table"+Id).show();
	 		}
	 		
	 		/* search in tables */
			$(document).ready(function(){
				// Write on keyup event of keyword input element
				$("#progSearch").keyup(function(){
					// When value of the input is not blank
					if( $(this).val() != "")
					{
						// Show only matching TR, hide rest of them
						$("#subjecttable tbody>tr").hide();
						$("#subjecttable td:contains-ci('" + $(this).val() + "')").parent("tr").show();
					}
					else
					{
						// When there is no input or clean again, show everything back
						$("#subjecttable tbody>tr").show();
					}
				});
				
				$("#groupSearch").keyup(function(){
					// When value of the input is not blank
					if( $(this).val() != "")
					{
						// Show only matching TR, hide rest of them
						$("#grouptable tbody>tr").hide();
						$("#grouptable td:contains-ci('" + $(this).val() + "')").parent("tr").show();
					}
					else
					{
						// When there is no input or clean again, show everything back
						$("#grouptable tbody>tr").show();
					}
				});
				
				$("#teacherSearch").keyup(function(){
					// When value of the input is not blank
					if( $(this).val() != "")
					{
						// Show only matching TR, hide rest of them
						$("#teachertable tbody>tr").hide();
						$("#teachertable td:contains-ci('" + $(this).val() + "')").parent("tr").show();
					}
					else
					{
						// When there is no input or clean again, show everything back
						$("#teachertable tbody>tr").show();
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
					<div class="span3 offset1 well" style="height: 191px;">
						<div class="control-group"> 
							<legend>Додати групу</legend> 
								<div class="controls">
					    			<input type="text" id="groupStudentNumber" placeholder="Введіть групу"/>
					    		</div>
			    			<input type="submit" class="btn btn-success" value="Додати" onclick="addGroupAjax()" />
			    		</div>
			    	</div>
			
				    <div class="span3 well" style="height: 191px;">
				    	<div class="control-group">
				    		<legend>Додати викладача</legend>
				    		<div class="controls">
						    	<input type="text" id="teacherFullName" placeholder="П.І.Б"/>
						    	<select id="teacherStatus">
									<option value="Асистент">Асистент</option>
									<option value="Старший викладач">Старший викладач</option>
									<option value="Доцент">Доцент</option>
									<option value="Професор">Професор</option>
								</select>
						    </div>
					    	<input class="btn btn-success" type="submit" value="Додати" onclick="addTeacherAjax()"/>
					    	<div id="teacherError" style="color:red"></div>
					    	<div id="teacherSuccess" style="color:green"></div>
					    </div>
				    </div>
			 
				    <div class="span3 well" style="height: 191px;">	
				    	<div class="control-group">
				    		<legend>Додати дисципліну</legend>
				    		<div class="controls">
						    	<input type="text" id="subjectTitle" placeholder="Назва предемету"/>
						    	<input type="text" id="subjectHours" placeholder="Кількість годин"/>
					    	</div>
					    	<input class="btn btn-success" type="submit" value="Додати" onclick="addSubjectAjax()"/>
					    	<div id="subjectError" style="color:red"></div>
					    	<div id="subjectSuccess" style="color:green"></div>
				    	</div>
				    </div>
		   		</div>
		   		
		   	<div class="row">
				<div class="span3 offset1" style="width: 260px;">
					<div id="group"></div>
				</div>
				
				<div class="span3" style="width: 260px;">
					<div id="teacher"></div>
				</div>
				
				<div class="span3" style="width: 260px;">
					<div id="subject"></div>
				</div>
			</div>
		   
		   <div class="row">
		   	<div class="span4">
		   		<ul class="nav nav-tabs nav-stacked">
		   			<li><a id="2" onclick="showTable(this.id)">Групи</a></li>
		   			<li><a id="3" onclick="showTable(this.id)">Викладачі</a></li>
		   			<li><a id="4" onclick="showTable(this.id)">Дисципліни</a></li>
		   		</ul>
		   	</div>
		   	<div class="span8">
		   		<div class="mainDiv">				
					<div id="table2">
						<table id="grouptable" class="table table-hover table-striped table-condensed">
					    	<thead>
								<tr class="tableHeader">
									<th>№</th>
									<th>Група</th>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td><input id="groupSearch" style="margin-bottom: 1px; height: 15px;" placeholder="Введіть групу" type="text"></td>
									<td></td>
								</tr> 
							</thead>
							<tbody>
								<c:forEach var="group" items="${groups}" varStatus="loopCounter">
										<tr class="${group.groupStudentId}g"> 
											<td>${loopCounter.count}</td>
											<td>${group.groupStudentNumber}</td>
											<td><a class="DELETE_GROUP" href="#" title="${group.groupStudentId}"><i class="icon-remove"></i></a></td>
										</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					
					<div id="table3">
						<table id="teachertable" class="table table-hover table-striped table-condensed">
					    	<thead>
								<tr class="tableHeader">
									<th>№</th>
									<th>Викладач</th>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td><input id="teacherSearch" style="margin-bottom: 1px; height: 15px;" placeholder="Введіть викладача" type="text"></td>
									<td></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="teacher" items="${teachers}" varStatus="loopCounter">
										<tr class="${teacher.teacherID}t">
											<td>${loopCounter.count}</td>
											<td>${teacher.teacherFullName}</td>
											<td><a class="DELETE_TEACHER" href="#" title="${teacher.teacherID}"><i class="icon-remove"></i></a></td>
										</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					
					<div id="table4">
						<table id="subjecttable" class="table table-hover table-striped table-condensed">
					    	<thead>
								<tr class="tableHeader">
									<th>№</th>
									<th>Дисципліна</th>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td><input id="progSearch" style="margin-bottom: 1px; height: 15px;" placeholder="Введіть дисципліну" type="text"></td>
									<td></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="subject" items="${subjects}" varStatus="loopCounter">
										<tr class="${subject.subjectId}s">
											<td>${loopCounter.count}</td>
											<td>${subject.subjectTitle}</td>
											<td><a class="DELETE_SUBJECT" href="#" title="${subject.subjectId}"><i class="icon-remove"></i></a></td>
										</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
		   	</div>
		   </div>
	    </div>
	    
	</body>
</html>