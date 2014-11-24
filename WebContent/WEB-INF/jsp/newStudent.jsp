<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
	    <title>Home</title>
	    <script type="text/javascript" 
	    	    src="<c:url value="resources/jquery.js"/>"></script>
	    	    
	    <script type="text/javascript" charset="UTF-8">
	    	function validateAjax() {
	    		var studentFullName = $('#studentFullName').val();
	    		var studentBook = $('#studentBook').val();
	    		var studentEnter = $('#studentEnter').val();
	    		var groupStudent = $('#groupStudent').val();
	    		var studentOKR = $('#studentOKR').val();
	    		var studentMode = $('#studentMode').val();
	    	
	    		if(studentEnter == '')
	    			studentEnter = 0;
	    		
	    			$.ajax({
						type: "POST",
	    				url: "/IRSystem/newStudent.html",
	    				data: "studentFullName=" + studentFullName + 
	    					  "&studentBook=" + studentBook +
	    					  "&studentEnter=" + studentEnter +
	    					  "&groupStudent=" + groupStudent +
	    					  "&studentOKR=" + studentOKR +
	    					  "&studentMode=" + studentMode,
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
	    </script>
	</head>
	<body>
		<table>
			<tr>
				<td><label>П.І.Б.</label></td>
				<td><input id="studentFullName" /></td>
			</tr>
			<tr>
				<td><label>Залікова книга</label></td>
				<td><input id="studentBook" /></td>
			</tr>
	
			<tr>
				<td><label>Рік вступу</label></td>
				<td><input id="studentEnter" /></td> 
			</tr>
			<tr>
				<td><label>Група</label></td>
				<td>
					<select id="groupStudent">
						<c:forEach var="g" items="${allGroups}"> 
							<option value="${g.groupStudentNumber}" >${g.groupStudentNumber}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><label>ОКР</label></td>
				<td>
					<select id="studentOKR">
						<option  value="Бакалавр">Бакалавр</option>
						<option value="Спеціаліст">Среціаліст</option>
						<option value="Магістр">Магістр</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td><label>Форма навчання</label></td>
				<td>
					<select id="studentMode">
						<option value="Денна">Денна</option>
						<option value="Заочна">Заочна</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" ><input type="submit" value="Додати" onclick="validateAjax()" /></td>
			</tr>
			<tr>
				<td colspan="2" >
					<div id="success" style="color:green"></div>
					<div id="error" style="color:red"></div>
				</td>
			</tr>
		</table>
	</body>
</html>