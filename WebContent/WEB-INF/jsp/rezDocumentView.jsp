<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Insert title here</title>
		
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
	    
	    <style>
	    	.tableHeader {
	    		background-color: #c7edb7;
	    	}
	    </style>
		
		<script>
			function genRezDoc(button) {
				var l = Ladda.create(button);
			 	l.start();
				
				var groups = new Array();
	   			
	   			<c:forEach var="g" items="${groups}">
	   				groups.push("${g}");
		   		</c:forEach>
	   			
	   			var okr = ${okr};
	   			var checkBoxes = $("#s  tudentsToRemove").find("td input[type='checkbox']:checked");
	   			var checkBoxValues = checkBoxes.map(function() { return $(this).val(); }).toArray();
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/createRezultDoc.html",
	   				data: "groups=" + groups +
	   					  "&okr=" + okr +
	   					  "&ids=" + checkBoxValues,
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
										<c:forEach var="g" items="${Allgroups}"> 
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
		
			<div class="alert alert-warning">
				Відмітьте студентів яких Ви НЕ ХОЧЕТЕ включати в список
			</div>	
		
			<table id="studentsToRemove" class="table table-hover table-striped table-condensed">
				<c:forEach var="student" items="${studentList}" varStatus="loopCounter">
					<tr>
						<td>${loopCounter.count}</td>
						<td>${student.studentFullName}</td>
						<td>${student.groupStudent.groupStudentNumber}</td>
						<td>${student.studentOKR}</td>
						<td><input type="checkbox" value="${student.studentId}"/></td>
					</tr>
				</c:forEach>
			</table>
			
			<div class="row">
				<div class="span4">
					<div id="total"></div>
				</div>
			</div>
			
			<button class="btn btn-success ladda-button" data-style="zoom-out" onclick="genRezDoc(this)"><span class="ladda-label">Згенерувати</span></button>
		</div>
	</body>
</html>