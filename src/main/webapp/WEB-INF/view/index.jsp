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
			<c:if test="${userType == 'staff'}">
				<div id="userDetails">
					<table border="1"">
						<tbody id="userListBody">
								<tr>
									<td>First name</td><td>${user.firstName}</td>
								</tr>
								<tr>
									<td>Last name</td><td>${user.lastName}</td>
								</tr>
								<tr>
									<td>Date of birth</td><td>${user.dob}</td>
								</tr>
								<tr>
									<td>Mobile</td><td>${user.mobile}</td>
								</tr>
						</tbody>
					</table>
				</div><br /><br />
				<div id="branchDetails">
					<table border="1">
						<thead>
							<tr>
								<th>Branch name</th>
								<th>Street name</th>
								<th>Postcode</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${branch.name}</td>
								<td>${branch.streetName}</td>
								<td>${branch.postcode}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</c:if> 
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
					<table id="countriesListTable" border="1">
						<thead>
						</thead>
						</tbody>
						</tbody>
					</table>
					<br />
					<label for="cityList">Cities</label>
					<select id="cityList">
					</select><br />
					<table id="cityListTable" border="1">
						<thead>
						</thead>
						<tbody>
						</tbody>
					</table>
					<br />
					<label for="branchList">Branches</label>
					<select id="branchList">
					</select><br /><br />
					<table id="branchListTable" border="1">
					</table><br /><br />
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
									<td>${branch.name}</td>
									<c:choose>
										<c:when test="${vehicle.available}">
											<td><a href="/VRS/vehicle/editVehicle?vin=${vehicle.vin}">Edit|</a>
												<a id="deleteVehicle" href="/VRS/vehicle/deleteVehicle?vin=${vehicle.vin}">Delete</a>
											</td>
										</c:when>
										<c:otherwise>
											<td><a id="cancelVehicleBooking" href="/VRS/vehicle/cancelBooking?vin=${vehicle.vin}">Cancel booking|</a>
												<a id="extendVehicleHirePeriod" href="/VRS/vehicle/extendHirePeriod?vin=${vehicle.vin}">Extend Hire</a>
											</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</tobdy>
					</table>
				</div>
			</c:if>
			<br />
		</div>
		<c:if test="${userType == 'customer'}">
			<div id="customerDiv">
				<div id="userDetails">
					<table border="1" id="userList">
						<tbody id="userListBody">
								<tr>
									<td>First name</td><td>${user.firstName}</td>
								</tr>
								<tr>
									<td>Last name</td><td>${user.lastName}</td>
								</tr>
								<tr>
									<td>Date of birth</td><td>${user.dob}</td>
								</tr>
								<tr>
									<td>Mobile</td><td>${user.mobile}</td>
								</tr>
								<tr>
									<td>License no</td><td>${user.licenseNo}</td>
								</tr>
								<tr>
									<td>License validity</td><td>${user.licenseValidity}</td>
								</tr>	
						</tbody>
					</table>
				</div><br /><br />
				<div id="vehiclesForHire">
					<label for="countriesList"> Countries </label>
					<select id="countryList">
						<option value="" selected></option>
						<c:forEach var="entry" items="${countries}">
							<c:forEach var="e" items="${entry}">
								<option class="" value="${e.key}">${e.value}</option>
							</c:forEach>
						</c:forEach>
					</select><br />
					<label for="cityListNoTable">Cities</label>
					<select id="cityList">
					</select><br />
					<label for="branchList">Branches</label>
					<select id="branchList">
					</select><br /><br />
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
			</div>
		</c:if>
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
		<div id="hireVehicleModalDialog" title="Book vehicle">
			 <p> Hire details for the following vehicle; </p>
			 <table border="1">
			 </table> 
		</div>
		<div id="customAlertModalDialog">
		</div>
		<div id="cancelVehicleBookingModalDialog" title="Cancel booking">
			<p>Do you wish to conitnue deleting current booking?</p>
		</div>
		<div id="extendVehicleHirePeriodModalDialog" title="Extend booking">
			<label for='extendNoOfDays'>No of days to extend </label>
			<input type='text' name='extendNoOfDays' id='extendNoOfDays' />
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
									$("table#cityListTable").append("<tr><td>" + v + "</td><td><a href='/VRS/city/delete?id=" + key + "'>Delete</a></td></tr>");  
									$("select#cityList").append("<option value='" + key + "'>" + v + "</option>");  
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
				$("table#branchListTable").empty();
				if($(this).children(":selected").val().trim().length > 0) {
					$.ajax({ 
						url: "/VRS/home/public/branches", 
						data: "cityId=" + $(this).children(":selected").val(), 
						success: function(data) {
							var flag = false;
							var id; 
							var name; 
							var streetName; 
							var postcode; 
							
							$("table#branchListTable").append('<thead><tr><th>Branch name</th><th>Street name</th><th>postcode</th><th>Action</th></tr></thead>');  
							$("table#branchListTable").append('<tbody>');  
							$.each(data, function(k,value) {
								$.each(value, function(key, v) { 
									if(key == "id") { 
										id = v; 
									extendVehicleHirePeriodModalDialog} else if(key == "name") { 
										name = v; 
									} else if(key == "streetName") { 
										streetName = v; 
									} else if(key == "postcode") { 
										postcode = v; 
									}
									flag = true; 
								}); 
								$("table#branchListTable").append("<tr><td>" + name + "</td><td>" + streetName + "</td><td>" + postcode +"</td><td><a href='/VRS/home/branch/delete?id=" + id + "'>Delete</a></td></tr>"); 
								$("select#branchList").append("<option value='" + id + "'>" + name + "</option>");	
							});
							$("table#branchListTable").append('</tbody>');
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
						url: "/VRS/vehicle/vehiclesForHire", 
						data: "branchId=" + $(this).children(":selected").val(), 
						success: function(data) { 
							console.log('${userType}'); 
							if('${accountType}' == "") { 
								console.log("working ..."); 
							} else { 
								console.log("failed ..."); 
							}
							if(data.length > 0) { 
								for(i=0; i< data.length; i++) { 
									if('${userType}' == 'customer') {
										var vinNumber = data[i].vin; 
										var dailyCost = data[i].dailyCost; 
										$("tbody#vehicleData").append("<tr><td>" + data[i].make + "</td><td>" + 
										data[i].model + "</td><td>" + data[i].maxSpeed + "</td><td>" + 
										data[i].fuel + "</td><td>" + data[i].seating + "</td><td>" + 
										data[i].dailyCost +"</td><td><a id='vehicleHire" + 
										data[i].vin +"' href='#'>Book</a></td></tr>"); 
										
										$("#vehicleHire" + vinNumber).click(function(event) {
											$.ajax({ 
												url: "/VRS/vehicle/getVehicle", 
												data: "vin=" + (event.target.id).substring(11,(event.target.id).length),  
												success: function(data) {
													$('#hireVehicleModalDialog table').empty();
													$('#hireVehicleModalDialog table').append("<tbody>"); 
													$('#hireVehicleModalDialog').append("<input type='hidden' value='" + vinNumber +"' id='hireVinNumber' />");
													$('#hireVehicleModalDialog table').append("<tr><td>Make</td><td>" + data.make +"</td></tr>");
													$('#hireVehicleModalDialog table').append("<tr><td>Model</td><td>" + data.model +"</td></tr>");
													$('#hireVehicleModalDialog table').append("<tr><td>Max Speed</td><td>" + data.maxSpeed +"</td></tr>");
													$('#hireVehicleModalDialog table').append("<tr><td>Seating</td><td>" + data.seating +"</td></tr>");
													$('#hireVehicleModalDialog table').append("<tr><td>Fuel type</td><td>" + data.fuel +"</td></tr>");
													$('#hireVehicleModalDialog table').append("<tr><td>Daily cost</td><td>" + data.dailyCost +"</td></tr>");
													$('#hireVehicleModalDialog table').append("<tr><td>Start date</td><td><input type='text' id='hireStartDate' class='dateControl' /></td></tr>");
													$('#hireVehicleModalDialog table').append("<tr><td>End date</td><td><input type='text' id='hireEndDate' class='dateControl'/></td></tr>");
													$('#hireVehicleModalDialog table').append("<tr><td>Insurance</td><td><input type='radio' name='insurance' value='yes' />Yes<input type='radio' name='insurance' value='no' checked />No</td></tr>");	
													$('#hireVehicleModalDialog table').append("<tr><td>Total cost</td><td id='hireTotalCost'></td></tr>"); 
													$('#hireVehicleModalDialog table').append("</tbody>");	
													
													$('#hireStartDate').datepicker({
														changeMonth: true, 
														changeYear: false,
														minDate: 1,
														maxDate: 3,  
														dateFormat: 'yy-mm-dd'
													}); 
													
													$('#hireEndDate').datepicker({
														changeMonth: true, 
														changeYear: true,
														minDate: 2,
														dateFormat: 'yy-mm-dd'
													}); 
													
													$("#hireStartDate, #hireEndDate, input[type='radio'][name='insurance']").change(function(){ 
														console.log("event triggered ..."); 
														if($('#hireEndDate').datepicker('getDate') != null) { 
															var startDate = $('#hireStartDate').datepicker('getDate'); 
															var endDate = $('#hireEndDate').datepicker('getDate'); 
															
															var dateDifference = endDate.getTime() - startDate.getTime(); 
															var days = dateDifference/(86400000); 
															
															var insuranceTaken = $("input[type='radio'][name='insurance']:checked"); 
															var total; 
															if(dateDifference > 1) {
																if(insuranceTaken.val() == 'yes') { 
																	total = (days * dailyCost) + 110; //110 is insurance value 
																	$('#hireTotalCost').text(total + 'Pound'); 
																} else { 
																	total = days * dailyCost; 
																	$('#hireTotalCost').text(total + 'Pound'); 
																}
															} else { 
																$('#customAlertModalDialog').empty(); 
																$('#customAlertModalDialog').append('<p>Invalid selection. Choose valid start and end date.</p>'); 
																$('#customAlertModalDialog').dialog('open');
																return false;
															}							
														}
													});
												}, 
												error: function() { 
													alert("failed.");
												}
											}); 
				 
											$('#hireVehicleModalDialog')
												.data('link', this)
												.dialog('open'); 
												 return false;
										}); 			
									} else { 
										$("tbody#vehicleData").append("<tr><td>" + data[i].make + "</td><td>" + 
										data[i].model + "</td><td>" + data[i].maxSpeed + "</td><td>" + 
										data[i].fuel + "</td><td>" + data[i].seating + "</td><td>" + 
										data[i].available + "</td><td><a href='/VRS/home/public/vehicles/delete?vin=" + 
										data[i].vin +"'>Delete</a></td></tr>"); 
									}
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
			
			$('#hireVehicleModalDialog').dialog({
				modal: true, 
				autoOpen: false, 
				width: 'auto', 
				resizable: false, 
				buttons: { 
					Confirm: function() { 
						 
						var startDate = $('#hireStartDate').datepicker({dateFormat:'yy-mm-dd'}).val();
						var endDate = $('#hireEndDate').datepicker({dateFormat:'yy-mm-dd'}).val();
						
						var vinNumber = $('#hireVinNumber').val(); 
						var insurance = $("input[type='radio'][name='insurance']:checked"); 
						var diff =  $('#hireEndDate').datepicker('getDate').getTime() - 
							$('#hireStartDate').datepicker('getDate').getTime(); 
						if(diff < 1) { 
							$('#customAlertModalDialog').empty(); 
							$('#customAlertModalDialog').append('<p> Invalid start and end date selection.'); 
							$('#customAlertModalDialog').dialog('open');
						} else { 
							$.ajax({
								type:'POST', 
								url: '/VRS/vehicle/hireVehicle',
								data: 'vin=' + vinNumber + '&startDate=' + startDate + '&endDate=' + 
									endDate + '&insurance=' + insurance.val(),
								dataType: 'json', 
								success: function(data) {  
									
									$('#customAlertModalDialog').empty(); 
									$('#customAlertModalDialog').append('<p> The booking has been added to the system. Press OK to reload.</p>'); 
									$('#customAlertModalDialog').dialog('open');
									$(this).dialog("close");
									
								}, 
								error: function() { 
									
									$('#customAlertModalDialog').empty(); 
									$('#customAlertModalDialog').append('<p> The booking process failed. </p>'); 
									$('#customAlertModalDialog').dialog('open');
									$(this).dialog('close'); 
									
									//fix the dissapearing dialog issue.
								}	
							}); 
						}
					}, 
					Cancel: function() { 
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
			
			$('#customAlertModalDialog').dialog({
				modal: true, 
				autoOpen: false, 
				width: 'auto', 
				resizable: false, 
				buttons: {
					OK: function() { 
						$(this).dialog("close");
						if($('#hireVehicleModalDialog').is(':visible')) {
							$('#hireVehicleModalDialog').dialog('close');
							location.reload(); 
						}
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
			
			$('#cancelVehicleBookingModalDialog').dialog({
				modal: true, 
				autoOpen: false, 
				width: 'auto', 
				resizable: false, 
				buttons: {
					Yes: function() { 
						$.ajax({
							type: 'GET', 
							url: '/VRS/vehicle/cancelBooking', 
							data: 'vin=' + (($(this).data('link').href).split('=')[1]), 
							dataType: 'json', 
							success: function() { 
								alert("the booking has been cancelled successfully.");
								$(this).dialog('close');
							}, 
							error: function() { 
								alert("cancelling the booking failed.");
								$(this).dialog('close');
							}
						}); 
					}, 
					No: function() {
						$(this).dialog('close'); 
					}
				}
			}); 
			
			$('#extendVehicleHirePeriodModalDialog').dialog({
				modal: true, 
				autoOpen: false, 
				width: 'auto', 
				resizable: false, 
				buttons: {
					Yes: function() { 
						$.ajax({
							type: 'GET', 
							url: '/VRS/vehicle/extendBooking', 
							data: 'vin=' + (($(this).data('link').href).split('=')[1]) +'&days=' + $('#extendNoOfDays').val(), 
							dataType: 'json', 
							success: function() { 
								alert("the booking has been extended successfully.");
								$(this).dialog('close');
							}, 
							error: function() { 
								alert("Extending the booking has failed.");
								$(this).dialog('close');
							}
						}); 
					}, 
					No: function() {
						$(this).dialog('close'); 
					}
				}
			}); 
			
			$('#cancelVehicleBooking').click(function() { 
				 $('#cancelVehicleBookingModalDialog')
				 .data('link',this)
				 .dialog('open');
				 return false; 
			}); 
			
			$('#extendVehicleHirePeriod').click(function() { 
				 $('#extendVehicleHirePeriodModalDialog')
				 .data('link',this)
				 .dialog('open');
				 return false; 
			}); 
		}); 
	</script>
</html>