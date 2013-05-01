<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html xmlns="http://www.w3c.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title> Oscar Vehicle Rental System </title> 
		<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/themes/ui-lightness/jquery-ui.css" type="text/css" />
		<link rel="stylesheet" href="/VRS/resources/css/style.css" type="text/css" />
	</head>
	<body>
		<br />
		<h1><center>Oscar Vehicle Rental System</center></h1><br>
		<div id="container">
			<div id="public_menu_bar">
				<ul class="public_menu_bar">
					<li><a href="/VRS/home"> Home | &nbsp;</a></li>
					<li><a href="/VRS/logout"> Logout </a></li>
				</ul>
			</div><br />
			<div class="section_title">
				<p>Edit vehicle</p>
			</div>
			<div id="edit_vehicle">
				<div class="edit_vehicle" align="center">
					<form:form action="/VRS/vehicle/editVehicle" method="POST" commandName="vehicle">
						<table border="0" id="vehicleEditTable" align="center">
							<thead>
							</thead> 
							<tbody>
								<tr>
									<td>
										<form:label path="vin" cssClass="inputTitle">VIN (Vehicle Identification No)</form:label>
									</td>
									<td>
										<form:input path="vin" cssClass="inputControl" />
									</td>
									<td>
										<form:errors path="vin" cssClass="inputError"/>  
									</td>
								</tr>
								<tr>
									<td>
										<form:label path="numberPlate" cssClass="inputTitle">Number plate</form:label>
									</td>
									<td>
										<form:input path="numberPlate" cssClass="inputControl" />
									</td>
									<td>
										<form:errors path="numberPlate" cssClass="inputError"/>  
									</td>
								</tr>
								<tr>
									<td>
										<form:label path="maxSpeed" cssClass="dateTitle">Max Speed</form:label>
									</td>
									<td>
										<form:input path="maxSpeed" cssClass="dateControl"/>
									</td>
									<td>
										<form:errors path="maxSpeed" cssClass="dateError"/>  
									</td>
								</tr>
								<tr>
									<td>
										<form:label path="seating" cssClass="inputTitle">Seating</form:label>
									</td>
									<td>
										<form:input path="seating" cssClass="inputControl" />
									</td>
									<td>
										<form:errors path="seating" cssClass="inputError"/>  
									</td>
								</tr>
								<tr>
									<td>
										<form:label path="fuel" cssClass="inputTitle">Fuel</form:label>
									</td>
									<td>
										<form:radiobutton path="fuel" cssClass="inputControl" value="Petrol"/>Petrol
										<form:radiobutton path="fuel" cssClass="inputControl" value="Diesel"/>Diesel
									</td>
									<td>
										<form:errors path="fuel" cssClass="inputError"/>  
									</td>
								</tr>
								<tr>
									<td>
										<form:label path="available" cssClass="inputTitle">Available</form:label>
									</td>
									<td>
										<form:input path="available" cssClass="inputControl" />
									</td>
									<td>
										<form:errors path="available" cssClass="inputError"/>  
									</td>
								</tr>
								<tr>
									<td>
										<form:label path="dailyCost" cssClass="inputTitle">Cost per day</form:label>
									</td>
									<td>
										<form:input path="dailyCost" cssClass="inputControl" />
									</td>
									<td>
										<form:errors path="dailyCost" cssClass="inputError"/>  
									</td>
								</tr>
								<tr>
									<td>
										<label name="make" class="inputTitle">Make</label>
									</td>
									<td>
										<select name="make" id="makeSelect">
											<c:forEach items="${makes}" var="make">
												<c:choose>
													<c:when test="${make == vehicle.make}">
														<option value="${make}" selected>${make}</option>
													</c:when>
													<c:otherwise>
														<option value="${make}">${make}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</td>
									
								</tr>
								<tr>
									<td>
										<label name="model" class="inputTitle">Model</label>
									</td>
									<td>
										<select name="model" id="modelSelect">
											<c:forEach items="${models}" var="model">
												<c:choose>
													<c:when test="${model == vehicle.model}">
														<option value="${model}" selected>${model}</option>
													</c:when>
													<c:otherwise>
														<option value="${model}">${model}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</td>
									
								</tr>
								<tr>
									<td>
										<label name="branch" class="inputTitle">Branch</label>
									</td>
									<td>
										<select name="branch" id="branchSelect">
											<c:forEach items="${branches}" var="branch">
												<c:choose>
													<c:when test="${branch.id == vehicle.branchId}">
														<option value="${branch.id}" selected>${branch.name}</option>
													</c:when>
													<c:otherwise>
														<option value="${branch.id}">${branch.name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<center><button id="update">Update</button></center>
									</td>
								</tr>
							</tbody> 
						</table> 
					</form:form> 
				</div>
			</div><br /> 
			<div class="error_title">
				<p> Update status </p>
			</div>
			<div class="error" style="width:700px;margin-left:50px;" id="updateStatus">
				<div class="error_content" align="center">
					<p id="updateStatusTitle"></p>
					<table id="errorsTable">
					</table> 
				</div>
			</div><br /><br />
			<div id="modelModalDialog">
				<p> To update, you are required to choose a model! </p>
			</div>
		</div>
	</body>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() { 
			$('#modelModalDialog').dialog({ 
				modal: true, 
				autoOpen: false, 
				width: 'auto', 
				resizable: false, 
				buttons: { 
					Alright: function() {
						$(this).dialog("close"); 
					}
				}
			}); 
			
			$('#update').click(function() { 
				if($('#vin').val().trim().length <= 0) {
					 alert("Error# Provide a valid vin number!"); 
				} else if($('#numberPlate').val().trim().length <= 0 || $('#maxSpeed').val() == 0) { 
					alert("Error#Provide a valid number plate and max speed"); 
				} else { 
					$.ajax({ 
						url: '/VRS/vehicle/editVehicle', 
						data: $('#vehicle').serialize(), 
						dataType: 'json', 
						method: 'POST', 
						success: function(data) {
							console.log("success call."); 
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
				}
				return false;
			});
			
			$('select#makeSelect').change(function() { 
				$('select#modelSelect').empty(); 
				$.ajax({ 
					url: "/VRS/vehicle/listModels",
					data: "makeName=" + $(this).children(":selected").val(), 
					dataType: 'json', 
					method: 'GET', 
					success: function(data) { 
						var flag = false; 
						$.each(data, function(key, value) { 
							if(key == "models") {
								$.each(value, function(k,v) { 
									flag = true;  
									$('select#modelSelect').append("<option value='"+v+"'>"+v+"</option>"); 
								}); 
							}
						});
						if(flag) { 
							$('select#modelSelect').append("<option value='' selected></option>"); 
						} 
					}
				}); 
			});  
		}); 
	</script>
</html>
	