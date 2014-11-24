function validateAjax() {
	
   		var studentFullName = $('#studentFullName').val();
   		var studentBook = $('#studentBook').val();
   		var studentEnter = $('#studentEnter').val();
   		var groupStudent = $('#groupStudent').val();
   		var studentOKR = $('#studentOKR').val();
   		var studentMode = $('#studentMode').val();
   		
   		studentFullName = studentFullName.replace(/\'/g, "`");
   		
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
   					
   					if(response == 'errorGroup') {
   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Група відсутня</span></div>' ).insertAfter( $( "#alertId" ) );
   					} else if(response == 'errorName') {
   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Ім\'я студента не правильно введене або > 120 символів</span></div>' ).insertAfter( $( "#alertId" ) );
   					} else if(response == 'errorBook') {
   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Залікова книга введена неправильно</span></div>' ).insertAfter( $( "#alertId" ) );
   					} else if(response == 'errorEnter') {
   						$( '<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>Рік вступу введений неправильно</span></div>' ).insertAfter( $( "#alertId" ) );
   					} else if (response == 'success') {
   						$( '<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>Студент: ' + studentFullName + ' успішно доданий</span></div>' ).insertAfter( $( "#alertId" ) );

	    					$('#error').html('');
	    					$('#studentFullName').val('');
	    					$('#studentBook').val('');
	    					$('#studentEnter').val('');
   					}
   				},
   				error: function(e) {alertId
   					alert('Error' + e);
   				}
   			});
   		}