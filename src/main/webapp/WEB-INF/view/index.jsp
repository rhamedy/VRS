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
	<style>
	</style> 
	<body>
		<div id="main">
			<div id="usersDiv">
				<a href="/VRS/user/editUser"> Add new user</a>
				<table border="1" id="userList">
					<thead>
						<tr>
							<th>First name</th>
							<th>Last name</th>
							<th>Date of birth</th>
							<th>Mobile</th>
							<th>License no</th>
							<th>License validity</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody id="userListBody">
						<c:forEach items="${users}" var="user">
							<tr>
								<td>${user.firstName}</td>
								<td>${user.lastName}</td>
								<td>${user.dob}</td>
								<td>${user.mobile}</td>
								<td>${user.licenseNo}</td>
								<td>${user.licenseValidity}</td>
								<td><a href="/VRS/user/editUser?username=${user.username}">Edit|</a>
									<a id="deleteUser" href="/VRS/user/deleteUser?username=${user.username}">Delete|</a>
									<a id="resetPassword" href="/VRS/user/resetPassword?username=${user.username}">Reset password</a>
								</td>
							</tr>
						</c:forEach>
					</tobdy>
				</table>
			</div>
			<br /><br />
			<a href="/VRS/vehicle/editVehicle">Add new vehicle</a>
			<div id="vehiclesDiv">
				<table border="1" id="vehicleList">
					<thead>
						<tr>
							<th>Vin No</th>
							<th>Plate No</th>
							<th>Max speed</th>
							<th>Seating</th>
							<th>Fuel</th>
							<th>Model</th>
							<th>Branch</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody id="vehicleListBody">
						<c:forEach items="${vehicles}" var="vehicle">
							<tr>
								<td>${vehicle.vin}</td>
								<td>${vehicle.numberPlate}</td>
								<td>${vehicle.maxSpeed}</td>
								<td>${vehicle.seating}</td>
								<td>${vehicle.fuel}</td>
								<td>${vehicle.model}</td>
								<td>
									<c:forEach items="${branches}" var="branch">
										<c:if test="${branch.id==vehicle.branchId}">
											${branch.name} 
										</c:if>
									</c:forEach>
								</td>
								<td><a href="/VRS/vehicle/editVehicle?vin=${vehicle.vin}">Edit|</a>
									<a id="deleteVehicle" href="/VRS/vehicle/deleteVehicle?vin=${vehicle.vin}">Delete|</a>
								</td>
							</tr>
						</c:forEach>
					</tobdy>
				</table>
			</div>
		</div>
		<div id="deleteModalDialog" title="Delete user">
			<p> The selected user will be deleted permanently. Do you want to proceed? </p>
		</div>
		<div id="deleteVehicleModalDialog" title="Delete vehicle">
			<p> The selected vehicle will be deleted permanently. Do you want to proceed? </p>
		</div>
		<div id="resetModalDialog" title="Reset password">
			<p> Do you wish to proceed with reseting this user's password? </p>
		</div>
	</body>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() { 
			var username; 
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
			
			$('#deleteModalDialog').dialog({
				modal: true, 
				autoOpen: false, 
				width: 'auto', 
				resizable: false, 
				buttons: { 
					Yes: function() {
						window.location.href= $(this).data('link').href; 
						$(this).dialog("close"); 
					}, 
					No: function() { 
						$(this).dialog("close"); 
					}
				}
			});
			
			$('#resetModalDialog').dialog({
				modal: true, 
				autoOpen: false, 
				width: 'auto', 
				resizable: false, 
				buttons: { 
					Yes: function() {
						window.location.href= $(this).data('link').href; 
						$(this).dialog("close"); 
					}, 
					No: function() { 
						$(this).dialog("close"); 
					}
				}
			});
			
			$('#deleteVehicleModalDialog').dialog({
				modal: true, 
				autoOpen: false, 
				width: 'auto', 
				resizable: false, 
				buttons: { 
					Yes: function() {
						window.location.href= $(this).data('link').href; 
						$(this).dialog("close"); 
					}, 
					No: function() { 
						$(this).dialog("close"); 
					}
				}
			});
			
			$('#deleteUser').click(function() {
				$('#deleteModalDialog')
				.data('link',this)
				.dialog('open');
				return false; 
			});  
			
			$('#resetPassword').click(function() {
				$('#resetModalDialog')
				.data('link',this)
				.dialog('open');
				return false; 
			});  
			
			$('#deleteVehicle').click(function() {
				$('#deleteVehicleModalDialog')
				.data('link',this)
				.dialog('open');
				return false; 
			});  
		}); 
	</script>
</html>