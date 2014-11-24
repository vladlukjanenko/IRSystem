<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Формування докумнтації</title>
		
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
	    	    
	    <!-- Ladda  -->
	    <link rel="stylesheet" href="<c:url value="resources/ladda/ladda-themeless.min.css"/>">
		<script src="<c:url value="resources/ladda/spin.min.js"/>"></script>
		<script src="<c:url value="resources/ladda/ladda.min.js"/>"></script> 	    
	    	    
	    <script type="text/javascript">
	   
		    function genRezDoc(button) {
		    	
		    	var l = Ladda.create(button);
			 	l.start();
		    	
	   			var groups = $("#groupsSelect").val() || [];
	   			var okr = $("#okrSelect").val();
	   			$('#error').html(""); 
		    	
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/createRezultDoc.html",
	   				data: "groups=" + groups +
	   					  "&okr=" + okr,
	   				success: function(response) {
	   					if(response == 'error')
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Файл відкритий в іншій програмі</span></div>' ).insertAfter( $( "#total" ) );
						
	   					if(response == 'success')
	   						$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Файл підсумкового списку успішно згенерований</span></div>' ).insertAfter( $( "#total" ) );
	   						
	   					l.stop();
	   				},
	   				error: function(e) {
	   					alert("Error" + e);
	   				}
	   			});
	   		} 
		    
		    function genPublDoc(button) {
		    	
		    	var l = Ladda.create(button);
			 	l.start();
		    	
	   			var year = $("#year").val();
	   			var docType = $("#docType").val();
		    	
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/createPublicationDoc.html",
	   				data: "year=" + year +
	   					  "&docType=" + docType,
	   				success: function(response) {	
	   					
	   					if(response == 'error')
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Файл відкритий в іншій програмі</span></div>' ).insertAfter( $( "#publ" ) );
						
	   					if(response == 'errorYear')
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Рік введений неправильно</span></div>' ).insertAfter( $( "#publ" ) );
							   					
	   					if(response == 'success')
	   						$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Файл успішно згенерований</span></div>' ).insertAfter( $( "#publ" ) );
	   						   					
	   					l.stop();
	   				},
	   				error: function(e) {
	   					alert("Error" + e);
	   				}
	   			});
	   		}
		    
		    function genProgDoc(button) {
	   			var year = $("#yearProg").val();
	   			
				var subjects = [];
		    	
		    	var rows = $("#subjecttable tr:gt(1)"); /* skip two rows */
		    	
		    	/* iterate through all rows*/
		    	rows.each(function(index) {
		    		subjects[index] = [];
		    		subjects[index][0] = this.className; /* add subject name*/
		    		subjects[index][1] = $("#progressHalf"+ (index+1)).val(); /* get select element */
		    	});
		    	
	   			var l = Ladda.create(button);
			 	l.start();
		    	
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/createProgDoc.html",
	   				data: "yearProg=" + year + 
	   					  "&subjects=" + subjects +
	   					  "&length=" + rows.length +
	   					  "&type=" + 1,
	   				success: function(response) {	
	   					
	   					if(response == 'error')
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Файл відкритий в іншій програмі</span></div>' ).insertAfter( $( "#prog" ) );
						
	   					if(response == 'errorYear')
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Рік введений неправильно</span></div>' ).insertAfter( $( "#prog" ) );
							   					
	   					if(response == 'success') 
	   						$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Файл успішно згенерований</span></div>' ).insertAfter( $( "#prog" ) );
	   						
	   					l.stop();
	   				},
	   				error: function(e) {
	   					alert("Error" + e);
	   				}
	   			});
	   		}
		    
		    function genProgGroupDoc(button) {
		    	
		    	var l = Ladda.create(button);
			 	l.start();
		    	
				var subjects = [];
		    	
		    	var rows = $("#subjecttable tr:gt(1)"); /* skip two rows */
		    	
		    	/* iterate through all rows*/
		    	rows.each(function(index) {
		    		subjects[index] = [];
		    		subjects[index][0] = this.className; /* add subject name*/
		    		subjects[index][1] = $("#progressHalf"+ (index+1)).val(); /* get select element */
		    	});
		    	
		    	var group = $("#groups").val(); /* get group*/
		    	
		    	$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/createProgDoc.html",
	   				data: "subjects=" + subjects + 
	   					  "&group=" + group +
	   					  "&length=" + rows.length +
	   					  "&type=" + 2,
	   				success: function(response) {	
	   					if(response == 'error')
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Файл відкритий в іншій програмі</span></div>' ).insertAfter( $( "#prog" ) );
						  					
	   					if(response == 'success')
	   						$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Файл успішно згенерований</span></div>' ).insertAfter( $( "#prog" ) );
	   					
	   					if(response == 'errorGroup')
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Помилка під час генерації файлу</span></div>' ).insertAfter( $( "#prog" ) );
	   					
	   					l.stop();
	   				},
	   				error: function(e) {
	   					alert("Error" + e);
	   				}
	   			});
		    }
		    
		    function genOlDoc(button) {
		    	
		    	var l = Ladda.create(button);
			 	l.start();
			 	
	   			var year = $("#yearOl").val();
		    	
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/createOlympiadDoc.html",
	   				data: "year=" + year,
	   				success: function(response) {	
	   					
	   					if(response == 'error')
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Файл відкритий в іншій програмі</span></div>' ).insertAfter( $( "#olym" ) );
						
	   					if(response == 'errorYear')
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Рік введений неправильно</span></div>' ).insertAfter( $( "#olym" ) );
							   					
	   					if(response == 'success')
	   						$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Файл успішно згенерований</span></div>' ).insertAfter( $( "#olym" ) );
	   						 
	   					l.stop();
	   				},
	   				error: function(e) {
	   					alert("Error" + e);
	   				}
	   			});
	   		}
		    
		    function viewRezDoc() {
	   			var groups = $("#groupsSelect").val() || [];
	   			var okr = $("#okrSelect").val();
	   			$('#error').html("");
	   			
	   			window.location.href = "viewRezultDoc.html?groups="+groups+"&okr="+okr;
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
				<div class="span3 offset1 well" >
					<legend>Підсумковий список</legend>
					<select id="groupsSelect" multiple="multiple">
						<c:forEach var="g" items="${groups}">
							<option value="${g.groupStudentId}">${g.groupStudentNumber}</option>
						</c:forEach>
					</select>
					<select id="okrSelect">
						<option value="0">Среціаліст</option>
						<option value="1">Магістр</option>
					</select>
					<button class="btn btn-small btn-success ladda-button" data-style="zoom-out" onclick="genRezDoc(this)"><span class="ladda-label">Згенерувати</span></button>
					<input type="submit" class="btn btn-small btn-success" value="Переглянути" onclick="viewRezDoc()"/>
				</div>
				
				<div class="span3 well" style="height: 214px;" id="">
					<legend>Публікації</legend>
					<input id="year" type="text" placeholder="Рік публікації" />
					<select id="docType">
						<option value="1">Статті загалом</option>
						<option value="2">Статті студентів</option>
						<option value="3">Статті викладачів</option>
						<option value="4">Тези загалом</option>
						<option value="5">Тези студентів</option>
						<option value="6">Тези викладачів</option>
					</select>
					<button class="btn btn-success ladda-button" data-style="zoom-out" onclick="genPublDoc(this)"><span class="ladda-label">Згенерувати</span></button>
				</div>
				
				<div class="span3 well" style="height: 214px;">
					<legend>Олімпіади</legend>
					<input id="yearOl" type="text" placeholder="Рік участі в олімпіаді"/>
					<button class="btn btn-success ladda-button" data-style="zoom-out" onclick="genOlDoc(this)"><span class="ladda-label">Згенерувати</span></button>
					
				</div>
			</div>
			
			<div class="row">
				<div class="span3 offset1" style="width: 260px;">
					<div id="total"></div>
					<div id="prog"></div>
				</div>
				
				<div class="span3" style="width: 260px;">
					<div id="publ"></div>
				</div>
				
				<div class="span3" style="width: 260px;">
					<div id="olym"></div>
				</div>
			</div>
			
			<div class="row">
				<div class="span3 well offset1">
					<legend>Успішність</legend>
					<input id="yearProg" type="text" placeholder="Рік вступу студентів"/>
					<button class="btn btn-success ladda-button" data-style="zoom-out" onclick="genProgDoc(this)"><span class="ladda-label">Згенерувати</span></button>
					<div id="errorYear" style="color: red;"></div>
					
					<select id="groups" style="margin-top: 10px;">
						<c:forEach var="g" items="${groups}">
							<option value="${g.groupStudentId}">${g.groupStudentNumber}</option>
						</c:forEach>
					</select>
					<button class="btn btn-success ladda-button" data-style="zoom-out" onclick="genProgGroupDoc(this)"><span class="ladda-label">Згенерувати</span></button>
				</div>
				
				<div class="span7">
					<table id="subjecttable" class="table table-hover table-striped table-condensed">
				    	<thead>
							<tr class="tableHeader">
								<th>№</th>
								<th>Дисципліна</th>
								<th>Семестр</th>
							</tr>
							<tr>
								<td></td>
								<td><input id="progSearch" style="margin-bottom: 1px; height: 15px;" placeholder="Введіть дисципліну" type="text"></td>
								<td></td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="subject" items="${subjects}" varStatus="loopCounter">
									<tr class="${subject.subjectId}">
										<td>${loopCounter.count}</td>
										<td>${subject.subjectTitle}</td>
										<td>
											<select class="input-small" style="padding-top: 0px; margin-bottom: 0px; height: 20px;" id="progressHalf${subject.subjectId}">
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
												<option value="8">8</option>
												<option value="9">9</option>
												<option value="10">10</option>
												<option value="11">11</option>
												<option value="12">12</option>
											</select>
										</td>
									</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>