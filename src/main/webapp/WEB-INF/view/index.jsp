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
			<table border="1" id="userList">
				<thead>
					<tr>
						<th>First name</th>
						<th>Last name</th>
						<th>Date of birth</th>
						<th>Mobile</th>
						<th>License no</th>
						<th>License validity</th>
					</tr>
				</thead>
				<tbody id="userListBody">
					<c:forEach items="${users}" var="user">
						<tr>
							<td></td>
						</tr>
					</c:forEach>
				</tobdy>
			</table>
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