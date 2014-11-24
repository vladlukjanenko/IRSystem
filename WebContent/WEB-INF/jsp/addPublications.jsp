<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	    
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
		
		
		<title></title>
		
		<script type="text/javascript">

		   $(function() {
			   $('#date').datepicker({
				   format: " yyyy",
				   viewMode: "years",
				   minViewMode: "years"
			   });
		   });
		   
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
		   
		    
	   </script>
		
		<script type="text/javascript">
			$(function() {
		   	    var availableTags = new Array();
		   	    
			   	<c:forEach var="name" items="${allStudents}">
			   		availableTags.push("${name}");
			   	</c:forEach>
			   	
		   	    
		   	    $( "#student1" ).autocomplete({
		   	      source: availableTags
		   	    });
		   	    
		   		$( "#student2" ).autocomplete({
		   	      source: availableTags
		   	    });
		   	 
		   		$( "#student3" ).autocomplete({
		   	      source: availableTags
		   	    });
		   	
		   		$( "#student4" ).autocomplete({
		   	      source: availableTags
		   	    });
		   		
		   	  });
			
			function addPublication() {
				var st1 = $('#student1').val();
				var st2 = $('#student2').val();
				var st3 = $('#student3').val();
				var st4 = $('#student4').val();
				
				var tc1 = $('#teacher1').val();
				var tc2 = $('#teacher2').val();
				
	   			var publicationTitle = $('#publicationTitle').val();
	   			var publicationType = $('#publicationType').val();
	   			var publicationPlace = $('#publicationPlace').val();
	   			var date = $('#mainDate').val();
	   			var publicationMag = $('#publicationMag').val();
	   			var publicationPage = $('#publicationPage').val();
	   			var publicationMagNum = $('#publicationMagNum').val();
	   			var publicationPubl = $('#publicationPubl').val();
	   			
	   			publicationTitle = publicationTitle.replace(/\'/g, "`");
	   			publicationPlace = publicationPlace.replace(/\'/g, "`");
	   			publicationMag = publicationMag.replace(/\'/g, "`");
	   			publicationPubl = publicationPubl.replace(/\'/g, "`");
	   			
	   			var thesis = 2;
	   			if ($('#publicationThesis').is(":checked")) 
	   				thesis = 1;
	   			
	   			var base = 2;
	   			if ($('#base').is(":checked")) 
	   				base = 1;
	   			
	   			$.ajax({
	   				type: "POST",
	   				url: "/IRSystem/addPublications.html",
	   				data: "publicationTitle=" + publicationTitle +
	   					  "&publicationType=" + publicationType +
	   					  "&publicationPlace=" + publicationPlace +
	   					  "&publicationDate=" + $.trim(date) +
	   					  "&publicationMag=" + publicationMag +
	   					  "&publicationPage=" + publicationPage +
	   					  "&publicationMagNum=" + publicationMagNum +
	   					  "&publicationBase=" + base +
	   					  "&publicationThesis=" + thesis +
	   					  "&publicationPubl=" + publicationPubl +
	   					  "&st1=" + st1 +
	   					  "&st2=" + st2 +
	   					  "&st3=" + st3 +
	   					  "&st4=" + st4 +
	   					  "&tc1=" + tc1 +
	   					  "&tc2=" + tc2,
	   				success: function(response) {
	   					if (response == 'errorStudent') {
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Не обрано жодного студента</span></div>' ).insertAfter( $( "#publNotice" ) );
	   					} else if (response == 'errorTitle') {
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Ім\'я статті не правильно введене</span></div>' ).insertAfter( $( "#publNotice" ) );
	   					} else if (response == 'errorMag') {
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Назва журналу більше 650 символів</span></div>' ).insertAfter( $( "#publNotice" ) );
	   					} else if (response == 'errorPage') {
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Формат сторінок введений не правильно</span></div>' ).insertAfter( $( "#publNotice" ) );
	   					} else if (response == 'errorPlace') {
	   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Місце проведення введено не правильно</span></div>' ).insertAfter( $( "#publNotice" ) );
	   					} else {
	   						$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Стаття успішно додана</span></div>' ).insertAfter( $( "#publNotice" ) );
	    					$('#successPubl').html(response);
	    					$('#publicationTitle').val('');
	    					$('#publicationPlace').val('');
	    					$('#publicationMag').val('');
	    					$('#publicationPage').val('');
	    					$('#publicationMagNum').val('');
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
					<legend>Співавтори студенти</legend>
					<input id="student1" type="text"/>
					<input id="student2" type="text"/>
					<input id="student3" type="text"/>
					<input id="student4" type="text"/>
				</div>
			</div>
			<div class="row">
				<div class="span9 offset1 well">
					<legend>Співавтори викладачі</legend>
					<select id="teacher1">
						<option value="" >Без викладача</option>
						<c:forEach var="t" items="${teachers}"> 
							<option value="${t.teacherFullName}" >${t.teacherFullName}</option>
						</c:forEach>
					</select>
					
					<select id="teacher2">
						<option value="" >Без викладача</option>
						<c:forEach var="t" items="${teachers}"> 
							<option value="${t.teacherFullName}" >${t.teacherFullName}</option>
						</c:forEach>
					</select>	
				</div>
			</div>
			
			<div class="row">
				<div class="span4 offset1">
					<div id="publNotice"></div>
				</div>
			</div>
			
			<div class="row">
				<div class="span9 offset1 well">
					<legend>Дані наукових досягнень</legend>
					<input id="publicationTitle" placeholder="Назва статті/тези" type="text"/>
					<input id="publicationMag" placeholder="Журнал публікації/Конференція" type="text"/>
					<input id="publicationPlace" placeholder="Місце публікації" type="text" />
					<input id="publicationMagNum" placeholder="Номер журналу" type="text"/>
					<input id="publicationPubl" placeholder="Видавництво" type="text" />
					<input id="publicationPage" placeholder="Сторінки" type="text"/>
					
					<select id="publicationType">
						<option value="Міжнародна конференція">Міжнародна конференція</option>
						<option value="Всеукраїнська конференція">Всеукраїнська конференція</option>
						<option value="Університетська">Університетська</option>
						<option value="">Відсутній</option>
					</select>
					<div class="input-append date" id="date" data-date="12-02-2012" data-date-format="dd-mm-yyyy" data-date-viewmode="years">
						<input class="span2" id="mainDate" size="16" type="text" placeholder="Дата" readonly="">
						<span class="add-on"><i class="icon-calendar"></i></span>
					</div>
					<label><input id="publicationThesis" type="checkbox" value="1"> Теза</label>
					<label><input id="base" name="base" type="checkbox" value="1"> В науковометричній базі</label>
					<input class="btn btn-success" type="submit" value="Додати публікацію" onclick="addPublication()" />
				</div>
			</div>
          </div>
	</body>
</html>