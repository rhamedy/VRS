<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html xmlns="http://www.w3c.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title> Oscar Vehicle Rental System </title> 
		<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/themes/ui-lightness/jquery-ui.css" type="text/css" />
	</head>
	
	<body>
		<div id="main">
			<div id="editUser">
				<form:form action="/VRS/user/editUser" method="POST" commandName="user">
					<table border="1">
						<thead>
						</thead> 
						<tbody>
							<c:choose>
								<c:when test="${username == 'new'}">
									<tr>
										<td>
											<form:label path="username" cssClass="inputTitle">Username (Valid Email)</form:label>
										</td>
										<td>
											<form:input path="username" cssClass="inputControl" />
										</td>
										<td>
											<form:errors path="username" cssClass="inputError"/>  
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<form:hidden path="username" />
								</c:otherwise>
							</c:choose>
							<tr>
								<td>
									<form:label path="firstName" cssClass="inputTitle">First name</form:label>
								</td>
								<td>
									<form:input path="firstName" cssClass="inputControl" />
								</td>
								<td>
									<form:errors path="firstName" cssClass="inputError"/>  
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="lastName" cssClass="inputTitle">Last name</form:label>
								</td>
								<td>
									<form:input path="lastName" cssClass="inputControl" />
								</td>
								<td>
									<form:errors path="lastName" cssClass="inputError"/>  
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="dob" cssClass="dateTitle">Date of birth</form:label>
								</td>
								<td>
									<form:input path="dob" cssClass="dateControl"/>
								</td>
								<td>
									<form:errors path="dob" cssClass="dateError"/>  
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="mobile" cssClass="inputTitle">Mobile</form:label>
								</td>
								<td>
									<form:input path="mobile" cssClass="inputControl" />
								</td>
								<td>
									<form:errors path="mobile" cssClass="inputError"/>  
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="licenseNo" cssClass="inputTitle">License no</form:label>
								</td>
								<td>
									<form:input path="licenseNo" cssClass="inputControl" />
								</td>
								<td>
									<form:errors path="licenseNo" cssClass="inputError"/>  
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="licenseValidity" cssClass="dateTitle">License validity</form:label>
								</td>
								<td>
									<form:input path="licenseValidity" cssClass="dateControl"/>
								</td>
								<td>
									<form:errors path="licenseValidity" cssClass="dateError"/>  
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="createdDate" cssClass="inputTitle">Created date</form:label>
								</td>
								<td>
									<form:input path="createdDate" cssClass="inputControl" disabled="disabled"/>
								</td>
								<td>
									<form:errors path="createdDate" cssClass="inputError"/>  
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="disabled" cssClass="inputTitle">Access</form:label>
								</td>
								<td>
									<form:radiobutton path="disabled" value="true" cssClass="inputControl" />disabled
									<form:radiobutton path="disabled" value="false" cssClass="inputControl" />enabled
								</td>
								<td>
									<form:errors path="disabled" cssClass="inputError"/>  
								</td>
							</tr>
							<tr>
								<td>
									<label name="roleName" class="inputTitle">Roles<label>
								</td>
								<td>
									<c:forEach items="${userRoles}" var="uRole">
										<input type="checkbox" name="role" value="${uRole.roleId}" checked>${uRole.roleName}</checkbox><br />
									</c:forEach>
									<c:forEach items="${nonUserRoles}" var="nRole">
										<input type="checkbox" name="role" value="${nRole.roleId}">${nRole.roleName}</checkbox><br />
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<button id="update">Update</button>
								</td>
							</tr>
						</tbody> 
					</table> 
				</form:form> 
			</div> 
			<div id="updateStatus">
				<p id="updateStatusTitle"></p>
				<table id="errorsTable" border="1">
				</table> 
			</div>
		</div>
	</body>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() { 
			if($('#username').val() == 'new') {
				console.log("resetting value of username."); 
				$('#username').attr('value',''); 
				$('#username').text('');
			}
			
			$('.dateControl').datepicker({
				changeMonth: true, 
				changeYear: true, 
				dateFormat: 'yy-mm-dd'
			}); 
			
			$('#update').click(function() { 
				$.ajax({ 
					url: '/VRS/user/editUser', 
					data: $('#user').serialize(), 
					dataType: 'json', 
					type: 'POST', 
					success: function(data) {
						$('table#errorsTable').empty();
						$.each(data, function(key, value) { 
							if(key == "status" && value == "success") { 
								$('p#updateStatusTitle').css('color','green');	
							} else if(key == "status" && value == "failure") { 
								$('p#updateStatusTitle').css('color','red');	
							} else if(key == "message") { 
								$('p#updateStatusTitle').html(value); 
							} else if(key == "errors") { 
								if(value.length > 0) {	
									$('table#errorsTable').append("<thead><th>Error</th><th>Field</th></thead>"); 
									$('table#errorsTable').append("<tbody id='errorsTableBody'></tbody>"); 
									$.each(value, function(k,v) {
										var key; 
										var value; 
										$.each(v, function(kk,vv) {
											if(kk == "key") { 
												key = vv; 
											} else if(kk == "value"){
												value = vv;  
											}
										});
										$("tbody[id='errorsTableBody']").append("<tr><td>"+key+"</td><td>"+value+"</td></tr>"); 
									}); 
								}
							}
						});
					}, 
					error: function(data) {
						 $('p#updateStatusTitle').text("Connection error[System Unreachable]."); 
						 $('p#updateStatusTitle').css('color','red');
					}
				}); 
				return false; 
			}); 
		}); 
	</script>
</html>
	