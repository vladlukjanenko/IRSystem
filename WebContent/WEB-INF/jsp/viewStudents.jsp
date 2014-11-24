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
				<div class="span10 offset1" >
					<p align="justify">
						Під час введення будь-яких назв забороняється вводити замість апострофу символ '.
						<b>Вводити символ ` (анг. розкладка і клавіша біля Esc).</b>
					</p>
					<p align="justify">
					Запуск програмного застосування відбувається на сервері Apache Tomcat 6, 
					тому спочатку потрібно запустити сервер. При успішному запуску сервера буде
					видано повідомлення Server startup in XXXX ms. Вікно запуску сервера показано на рисунку нижче.
					</p>
					<img alt="" src="resources/images/1.jpg">
					<p align="justify">
					Після успішного запуску сервера, заходимо в web-браузер та вводимо URL 
					з початковою сторінкою програмного продукту як показано на рисунку нижче, 
					а саме <b>localhost:8080/IRSystem/addData.html</b>.
					</p>
					<img alt="" src="resources/images/2.jpg">
					<p align="justify">
					В разі успішного з’єднання з базою даних з’явиться сторінка з полями для введення інформації, а саме групу, викладача, дисципліну та пунктами меню для перегляду вже введеної інформації як показано на рисунку нижче. 
					Під час введення групи потрібно дотримуватись формату ШИФР-НОМЕР, інакше буде виведено повідомлення про помилку в назві групи. Якщо група вже додана до бази даних буде виведено відповідне повідомлення.
					Ім’я викладача під час введення обмежене 200 символами. Якщо зазначену кількість буде перевищено система видасть відповідне повідомлення.
					Назва предмету обмежена 450 символами, а кількість академічних годин може бути максимально 500. В разі перевищення кількості відповідних показників буде виведено відповідні повідомлення.
					Для перегляду інформації, яка міститься в базі даних зліва містяться відповідні пункти меню. При натисненні відповідного пункту меню виводиться таблиця з наявною інформацією та полем пошуку по даній таблиці.			
					</p>
					<img alt="" src="resources/images/3.jpg">
					<p align="justify">
					Зверху сторінки міститься навігаційна панель, яка дозволяє переходити до відповідних 
					сторінок для введення та перегляду інформації. Дане меню містить випадаючий список,
					який показаний на рисунку нижче. В ньому міститься два підпункти, а саме 
					«Додати нового студента» та «Анкета студента»
					</p>
					<img alt="" src="resources/images/4.jpg">
					<p align="justify">
					Обравши пункт «Додати нового студента», з’являється вікно для додавання
					інформації про нового студента, яке показано на рисунку нижче. 
					Система генерує відповідні повідомлення, якщо поле було введено неправильно 
					або студента було успішно додано до бази даних.
					</p>
					<img alt="" src="resources/images/5.jpg">
					<p align="justify">
					Обравши пункт «Анкета студента» система перейде на сторінку, яка показана на рисунку нижче. Дана сторінка дозволяє ввести змінити інформацію про студента, додати інформацію про його участь в олімпіадах, про результати вступних випробувань та оцінки з дисциплін.
					Для того, щоб виконати зміни інформації або додати нову потрібно в поле пошуку написати ім’я студента та натиснути «Знайти». Якщо студент занесений до бази даних, то система видасть відповідне повідомлення і після цього можна редагувати та вводити нову інформацію. 
					Якщо студент в базі відсутній то буде виведено повідомлення, що студента з вказаним іменем не знайдено.
					</p>
					<img alt="" src="resources/images/6.jpg">
					<p align="justify">
					Натиснувши кнопку «Переглянути анкету» система перейде на сторінку де міститься
					відображення всієї інформації про студента, а саме відомостей про нього, 
					його наукові досягнення, результати вступних іспитів та оцінки з академічних  дисциплін.
					Сторінка з анкетою студента показана на рисунку нижче.
					</p>
					<img alt="" src="resources/images/7.jpg">
					<p align="justify">
					Обравши пункт меню «Публікації» система видасть сторінку де користувач може ввести 
					інформацію про наукові статті або тези конференцій студента. Під час додавання статей 
					або тез конференцій дозволяється вводити співавторів студентів та/або викладачів. 
					Максимальна кількість співавторів викладачів – 2, а студентів – 4. Коли додаються співавтори
					студенти система автоматично додає нове пусте поле для введення нового студента як співавтора. 
					Сторінка «Публікації» показана на рисунку нижче. В разі введення неправильних даних, система 
					виводить повідомлення з відповідною інформацією. Після правильного введення всієї інформації до бази даних, 
					її можна буде перелгянути в анкеті будь-якого студента котрий вказаний як співавтор наукової статті або тези.
					</p>
					<img alt="" src="resources/images/8.jpg">
					<p align="justify">
					Пункт меню «Документація» дозволяє згенерувати всю звітну документацію. Відповідна сторінка показана на рисунку нижче. 
					Для того, щоб згенерувати звіт необхідно ввести відповідні дані та натиснути кнопку генерації бажаного звіту.
					Після цього система почне генерацію звіту. Якщо під час генерації документу відбулась помилку, то 
					система виведе відповідне повідомлення, інакше система виведе повідомлення про успішну 
					генерацію відповідного звіту.
					Генерація звіту «Успішність» дозволяє розташувати відповідні академічні дисципліни за семестрами в яких вони читались. 
					Для цього з правого боку є таблиця з усіма дисциплінами та полем пошуку. 
					Звіт «Успішність» можна генерувати по року вступу студента на навчання чи по обраній групі.
					</p>
					<img alt="" src="resources/images/9.jpg">
				</div>
			</div>
		</div>
	</body>
</html>