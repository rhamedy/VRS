<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html xmlns="http://www.w3c.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title> Oscar Vehicle Rental System </title> 
		<link rel="stylesheet" href="/VRS/resources/css/ui-lightness/jquery-ui-1.7.3.custom.css" type="text/css"/> 
	</head>
	
	<body>
		<div id="main">
			<div id="editUser">
				<form:form action="/VRS/user/editUser" method="POST" commandName="user">
					<table border="1">
						<thead>
						</thead> 
						<tbody>
							<form:hidden path="username" />
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
									<form:label path="role.roleName" cssClass="inputTitle">Role</form:label>
								</td>
								<td>
									<form:input path="role.roleName" cssClass="inputControl" />
								</td>
								<td>
									<form:errors path="role.roleName" cssClass="inputError"/>  
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
		</div>
	</body>
	<script language="JavaScript" type="text/javascript" src="/VRS/resources/js/jquery-1.6.1.min.js"></script>
	<script language="JavaScript" type="text/javascript" src="/VRS/resources/js/jquery-ui-1.7.3.custom.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() { 
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
						alert("success!");  
					}, 
					error: function(data) {
						alert("failed!"); 
					}
				}); 
				
				return false; 
			}); 
		}); 
	</script>
</html>
	