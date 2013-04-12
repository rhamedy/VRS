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
		#vehicleDiv { 
			border: 1px solid black; 
			padding: 5px 5px 5px 5px;
			margin-top: 10px; 
		}
	</style> 
	<body>
		<div id="main">
			<c:if test="${userType == 'admin'}">
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
				<div class="all">
					<label for="countriesList"> Countries </label>
					<select id="countryList">
						<option value="" selected></option>
						<c:forEach var="entry" items="${countries}">
							<c:forEach var="e" items="${entry}">
								<option class="" value="${e.key}">${e.value}</option>
							</c:forEach>
						</c:forEach>
					</select><br />
					<table id="countriesListTable">
						<thead>
						</thead>
						</tbody>
						</tbody>
					</table>
					<br />
					<label for="cityList"> Cities </label>
					<select id="cityList">
					</select><br />
					<table id="cityListTable">
						<thead>
						</thead>
						<tbody>
						</tbody>
					</table>
					<br />
					<label for="branchList">Branches</label>
					<select id="branchList">
					</select><br /><br />
					<table id="branchListTabel">
						<thead>
						</thead>
						<tbody>
						</tbody>
					</table>
					<label for="vehicleList">Vehicles list</label><br />
					<table id="vehicleList" border="1">
						<thead>
							<tr>
								<th>Make</th>
								<th>Model</th>
								<th>Max Speed</th>
								<th>Fuel</th>
								<th>Seating</th>
								<th>Available</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody id="vehicleData">
						</tbody>
					</table>
				</div>
			</c:if>
			<br /><br />
			<c:if test="${userType == 'staff'}">
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
			</c:if>
			<br />
		</div>
		<div id="deleteModalDialog" title="Delete user">
			<p> The selected user will be deleted permanently. Do you want to proceed? </p>
		</div>
		<div id="deleteBranchModalDialog" title="Delete branch">
			<p> The selected branch and all assets and its users will be deleted permanently. Do you want to proceed? </p>
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
			
			$("select#countryList").change(function() {
				$("select#cityList").empty(); 
				$("select#branchList").empty();  
				$("table#cityListTable").empty();
				if($(this).children(":selected").val().trim().length > 0) { 
					$.ajax({
						url: "/VRS/home/public/cities",
						data: "countryId=" + $(this).children(":selected").val(), 
						success: function(data) {
							var flag = false;
							$("table#cityListTable").append('<thead><tr><th>City name</th><th>Action</th></tr></thead>');  
							$("table#cityListTable").append('<tbody>');  
							$.each(data, function(k,value) {
								$.each(value, function(key, v) {
									$("table#cityListTable").append("<tr><td>" + v + "</td><td><a href='/VRS/city/delete?id='" + key + "'</td></tr>");  
									$("select#cityList").append("<option value='" + key + "'>" + v + "</option>"); 
									flag = true; 
								}); 
							}); 
							$("table#cityListTable").append('</tbody>');
							if(flag) { 
								$("select#cityList").append("<option value='' selected></option>");
							}
						}, 
						error: function() { 
							console.log("failed!"); 
						} 
					}); 
				} else { 
					console.log("empty selection!"); 
				}
			});
			
			$("select#cityList").change(function() { 
				$("select#branchList").empty(); 
				if($(this).children(":selected").val().trim().length > 0) {
					$.ajax({ 
						url: "/VRS/home/public/branches", 
						data: "cityId=" + $(this).children(":selected").val(), 
						success: function(data) {
							var flag = false;
							var id; 
							var name; 
							$.each(data, function(k,value) {
								$.each(value, function(key, v) { 
									if(key == "id") { 
										id = v; 
									} else if(key == "name") { 
										name = v; 
									}
									flag = true; 
								}); 
								if(flag) { 
									$("select#branchList").append("<option value='" + id + "'>" + name + "</option>");
								}
							});
							if(flag) { 
								$("select#branchList").append("<option value='' selected></option>");
							} 
						}
					}); 
				} else { 
					console.log("empty option was selected!"); 
				}
			});
			
			$("select#branchList").change(function() { 
				$("tbody#vehicleData").empty();
				if($(this).children(":selected").val().trim().length > 0) { 
					$.ajax({ 
						url: "/VRS/home/public/vehicles", 
						data: "branchId=" + $(this).children(":selected").val(), 
						success: function(data) { 
							if(data.length > 0) { 
								for(i=0; i< data.length; i++) { 
									$("tbody#vehicleData").append("<tr><td>" + data[i].make + "</td><td>" + 
									data[i].model + "</td><td>" + data[i].maxSpeed + "</td><td>" + 
									data[i].fuel + "</td><td>" + data[i].seating + "</td><td>" + 
									data[i].available + "</td></tr>"); 
								}
							}
						},
						error: function() { 
							conosle.log("error ajax request."); 
						}
					});
				} else { 
					console.log("empty branch selection!"); 
				}
			}); 
			
			$('.dateControl').datepicker({
				changeMonth: true, 
				changeYear: true, 
				dateFormat: 'yy-mm-dd'
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
			
			$('#deleteBranchModalDialog').dialog({
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
			
			$('#deleteBranch').click(function() {
				$('#deleteBranchModalDialog')
				.data('link',this)
				.dialog('open');
				return false; 
			}); 
		}); 
	</script>
</html>