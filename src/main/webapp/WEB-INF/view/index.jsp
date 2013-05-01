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
					<li><a href="#" id="changePasswordLink"> Change password | </a></li>
					<li><a href="/VRS/logout"> Logout </a></li>
				</ul>
			</div><br />
			<c:if test="${userType == 'staff'}">
				<div class="section_title">
					<p>Staff personal details</p>
				</div>
				<div id="user_details" align="center" style="width:400px;height:110px;">
					<br />
					<table border="0" style="width:230px;margin-left:30px;">
						<tbody id="userListBody">
								<tr>
									<td align="left">First name</td><td>${user.firstName}</td>
								</tr>
								<tr>
									<td align="left">Last name</td><td>${user.lastName}</td>
								</tr>
								<tr>
									<td align="left">Date of birth</td><td>${user.dob}</td>
								</tr>
								<tr>
									<td align="left">Mobile</td><td>${user.mobile}</td>
								</tr>
						</tbody>
					</table>
				</div>
				<div class="section_title">
					<p>Staff branch details</p>
				</div>
				<div id="branch_details" align="center" style="width:400px;height:90px;">
					<br />
					<table border="0" style="width:330px;">
						<thead>
							<tr>
								<th align="left">Branch name</th>
								<th align="left"> Street name</th>
								<th align="left">Postcode</th>
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
				<div class="section_title">
					<p>User management</p>
				</div>
				<div id="users_div" style="height:150px;">
					<div>
						<a style="margin-left:300px;" href="/VRS/user/editUser"> Add new user</a><br><br />
					</div>
					<div id="usersListDiv">
						<table border="0" id="userList" cellspacing="0" cellpadding="0" style="width:660px;margin-left:5px;">
							<thead>
								<tr>
									<th>Full name</th>
									<th>DOB &nbsp;&nbsp;</th>
									<th>Mobile</th>
									<th>License no &nbsp;</th>
									<th>License validity</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody id="userListBody">
								<c:forEach items="${users}" var="user" varStatus="rowCounter">
									<c:choose>
										<c:when test="${rowCounter.count%2==1}">
											<tr bgcolor="#BDEDFF">
										</c:when>
										<c:otherwise>
											<tr>
										</c:otherwise>
									</c:choose>
										<td align="center">${user.firstName} ${user.lastName}</td>
										<td align="center">${user.dob}</td>
										<td align="center">${user.mobile}</td>
										<td align="center">${user.licenseNo}</td>
										<td align="center">${user.licenseValidity}</td>
										<td align="center"><a href="/VRS/user/editUser?username=${user.username}">Edit|</a>
											<a class="deleteUser" href="/VRS/user/deleteUserFromSystem?username=${user.username}">Delete|</a>
											<a class="resetPassword" href="/VRS/user/resetPassword?username=${user.username}">Reset password</a>
										</td>
									</tr>
								</c:forEach>
							</tobdy>
						</table>
					</div><br /><br />
				</div>
				<div class="section_title">
					<p>Branch data management</p>
				</div>
				<div id="vehicle_div" align="center">
					<br /><label for="countriesList"> Select country </label>
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
					<label for="cityList">Select city</label>
					<select id="cityList">
					</select><br /><br />
					<div id="tableCityListDiv">
						<table id="cityListTable">
							<thead>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<br />
					<label for="branchList">Select branch</label>
					<select id="branchList">
					</select><br /><br />
					<div id="tableBranchListDiv">
						<table id="branchListTable" border="0">
						</table><br />
					</div><br /><br />
					<label for="vehicleList"><b>Vehicles list</b></label><br />
					<div id="tableVehicleListDiv">
						<table id="vehicleList" border="0">
							<thead>
								<tr>
									<th align="left">Make</th>
									<th align="left">Model</th>
									<th align="left">Max Speed</th>
									<th align="left">Fuel</th>
									<th align="left">Seating</th>
									<th align="left">Available</th>
									<th align="left">Action</th>
								</tr>
							</thead>
							<tbody id="vehicleData">
							</tbody>
						</table>
					</div><br /><br />
				</div><br /><br />
			</c:if>
			<c:if test="${userType == 'staff'}">
				<div class="section_title">
					<p>Registered vehicles</p>
				</div>
				<div id="vehicles_div">
					<div>
						<center><b><a href="/VRS/vehicle/editVehicle">Add new vehicle</a></b></center><br />
					</div>
					<div id="tableVehicleListDiv">
						<table border="0" id="vehicleList">
							<thead>
								<tr>
									<th align="left">Vin No</th>
									<th align="left">Plate No</th>
									<th align="left">Max speed</th>
									<th align="left">Seating</th>
									<th align="left">Fuel</th>
									<th align="left">Model</th>
									<th align="left">Branch</th>
									<th align="left">Action</th>
								</tr>
							</thead>
							<tbody id="vehicleListBody">
								<c:forEach items="${vehicles}" var="vehicle" varStatus="rowCounter">
									<c:choose>
										<c:when test="${rowCounter.count%2==1}">
											<tr bgcolor="#BDEDFF">
										</c:when>
										<c:otherwise>
											<tr>
										</c:otherwise>
									</c:choose>
										<td align="left">${vehicle.vin}</td>
										<td align="left">${vehicle.numberPlate}</td>
										<td align="left">${vehicle.maxSpeed}</td>
										<td align="left">${vehicle.seating}</td>
										<td align="left">${vehicle.fuel}</td>
										<td align="left">${vehicle.model}</td>
										<td align="left">${branch.name}</td>
										<td align="left"><a href="/VRS/vehicle/editVehicle?vin=${vehicle.vin}">Edit|</a>
											<a class="deleteVehicle" href="/VRS/vehicle/deleteVehicle?vin=${vehicle.vin}">Delete</a>
										</td>
									</tr>
								</c:forEach>
							</tobdy>
						</table><br />
					</div><br />
				</div><br />
				<div class="section_title">
					<p>Unavailable vehicles</p>
				</div>
				<div id="vehicles_div_unavailable">
					<div id="tableVehicleListDiv_unavailable">
						<table border="0" id="vehicleList">
							<thead>
								<tr>
									<th align="left">Vin No</th>
									<th align="left">Plate No</th>
									<th align="left">Seating</th>
									<th align="left">Fuel</th>
									<th align="left">Model</th>
									<th align="left">Branch</th>
									<th align="left">Available</th>
									<th align="left">Action</th>
								</tr>
							</thead>
							<tbody id="vehicleListBody">
								<c:forEach items="${damagedVehicles}" var="vehicle" varStatus="rowCounter">
									<c:choose>
										<c:when test="${rowCounter.count%2==1}">
											<tr bgcolor="#BDEDFF">
										</c:when>
										<c:otherwise>
											<tr>
										</c:otherwise>
									</c:choose>
										<td align="left">${vehicle.vin}</td>
										<td align="left">${vehicle.numberPlate}</td>
										<td align="left">${vehicle.seating}</td>
										<td align="left">${vehicle.fuel}</td>
										<td align="left">${vehicle.model}</td>
										<td align="left">${branch.name}</td>
										<td align="left">${vehicle.available}</td>
										<td align="left"><a href="/VRS/vehicle/editVehicle?vin=${vehicle.vin}">Edit</a>
										</td>
									</tr>
								</c:forEach>
							</tobdy>
						</table><br />
					</div><br />
				</div><br />
				<div class="section_title">
					<p>List of Active Bookings</p>
				</div>
				<div id="booking_div">
					<div id="tableBookingListDiv"  style="height:180px;">
						<table border="0" id="bookingList">
							<thead>
								<tr>
									<th align="left">Vin No</th>
									<th align="left">Username</th>
									<th align="left">Start date</th>
									<th align="left">End date</th>
									<th align="left">Insurance</th>
									<th align="left">Cost</th>
									<th align="left">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${bookings}" var="booking" varStatus="rowCounter">
									<c:choose>
										<c:when test="${rowCounter.count%2==1}">
											<tr bgcolor="#BDEDFF">
										</c:when>
										<c:otherwise>
											<tr>
										</c:otherwise>
									</c:choose>
										<td align="left">${booking.vehicleVin}</td>
										<td align="left">${booking.username}</td>
										<td align="left">${booking.startDate}</td>
										<td align="left">${booking.endDate}</td>
										<td align="left">${booking.insurance}</td>
										<td align="left">${booking.hireCost}</td>
										<td align="left"><a class="cancelVehicleBooking" href="/VRS/vehicle/cancelBooking?bookingId=${booking.id}">Cancel |</a>
											<a class="extendVehicleHirePeriod" href="/VRS/vehicle/extendHirePeriod?bookingId=${booking.id}">Extend |</a>
											<a class="returnedVehicle" href="/VRS/vehicle/vehicleReturned?bookingId=${booking.id}"> Return</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div><br>
				<div class="section_title">
					<p>List of Past Bookings</p>
				</div>
				<div id="booking_div_past">
					<div id="tableBookingListDiv_past"  style="height:180px;">
						<table border="0" id="bookingList">
							<thead>
								<tr>
									<th align="left">Vin no</th>
									<th align="left">Username</th>
									<th align="left">Start date</th>
									<th align="left">End date</th>
									<th align="left">Total</th>
									<th align="left">Charged</th>
									<th align="left">Remains</th>
									<th align="left">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pastBookings}" var="booking" varStatus="rowCounter">
									<c:choose>
										<c:when test="${rowCounter.count%2==1}">
											<tr bgcolor="#BDEDFF">
										</c:when>
										<c:otherwise>
											<tr>
										</c:otherwise>
									</c:choose>
										<td align="left">${booking.vehicleVin}</td>
										<td align="left">${booking.username}</td>
										<td align="left">${booking.startDate}</td>
										<td align="left">${booking.endDate}</td>
										<td align="left">${booking.hireCost}</td>
										<td align="left">${booking.chargedAmount}</td>
										<td align="left">${booking.remainingAmount}</td>
										<td align="left">
											<a href="javascript:alert('Function not implemented.');"> Update record</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</c:if>
		<c:if test="${userType == 'customer'}">
			<div class="section_title">
				<p>Customer personal details</p>
			</div>
			<div id="user_details" align="center" style="width:410px;height:150px;">
				<br />
				<table border="0" id="usersList" style="width:230px;">
					<tbody id="userListBody">
							<tr>
								<td align="left">First name</td><td>${user.firstName}</td>
							</tr>
							<tr>
								<td align="left">Last name</td><td>${user.lastName}</td>
							</tr>
							<tr>
								<td align="left">Date of birth</td><td>${user.dob}</td>
							</tr>
							<tr>
								<td align="left">Mobile</td><td>${user.mobile}</td>
							</tr>
							<tr>
								<td align="left">License no</td><td>${user.licenseNo}</td>
							</tr>
							<tr>
								<td align="left">License validity</td><td>${user.licenseValidity}</td>
							</tr>	
					</tbody>
				</table>
			</div><br />
			<div class="section_title">
				<p>Find a vehicle for booking</p>
			</div>
			<div id="vehicle_for_hire">
				<br />
				<table align="center">
					<tr>
						<td><label for="countriesList"> Select a country </label></td>
						<td>
							<select id="countryList">
								<option value="" selected></option>
								<c:forEach var="entry" items="${countries}">
									<c:forEach var="e" items="${entry}">
										<option class="" value="${e.key}">${e.value}</option>
									</c:forEach>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td><label for="cityList">Choose a city</label></td>
						<td>
							<select id="cityList">
							</select><br />
						</td>
					</tr>
					<tr>
						<td><label for="branchList">Choose a branche</label></td>
						<td>
							<select id="branchList">
							</select><br /><br />
						</td>
					</tr>
				</table><br />
				<center><label for="vehicleList"><b>Vehicles list</b></label></center><br /><br />
				<div id="tableVehicleListDiv">
					<table id="vehicleList" border="0" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th align="left">Make</th>
								<th align="left">Model</th>
								<th align="left">Max Speed</th>
								<th align="left">Fuel</th>
								<th align="left">Seating</th>
								<th align="left">Available</th>
								<th align="left">Action</th>
							</tr>
						</thead>
						<tbody id="vehicleData">
						</tbody>
					</table>
				</div>
			</div><br />
		</c:if>
		<br />
		<div id="deleteModalDialog" title="Delete user">
			<p> The selected user will be deleted permanently. Do you want to proceed? </p>
		</div>
		<div id="deleteBranchModalDialog" title="Delete branch">
			<p> The selected branch and all assets and its users will be deleted permanently. Do you want to proceed? </p>
		</div>
		<div id="deleteVehicleModalDialog" title="Delete vehicle">
			<p> The selected vehicle and it's booking records will be deleted permanently. Do you want to proceed? </p>
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
		<div id="customSuccessAlertModalDialog">
		</div>
		<div id="cancelVehicleBookingModalDialog" title="Cancel booking">
			<p>Do you wish to conitnue deleting current booking?</p>
		</div>
		<div id="extendVehicleHirePeriodModalDialog" title="Extend booking">
			<label for='extensionStartDate'>Start date</label>
			<input type='text' name='extensionStartDate' id='extensionStartDate' /><br /><br />
			<label for='extensionEndDate'>End date</label>
			<input type='text' name='extensionEndDate' id='extensionEndDate' />
		</div>
		<div id="returnedVehicleModalDialog" title="Return Vehicle">
			<p> Vehicle inspection and payment details ... </p><br>
			<table id="returnedVehicleTable">
				<tbody>
				</tbody>
			</table>
		</div>
		<div id="changePasswordModalDialog" title="Change Your Password">
			<p>To change your password provide the following info:</p><br>
			<table>
				<tr>
					<td><label for="currentPassword"> Current password</label></td>
					<td><input type="password" id="currentPassword" /></td>
				</tr>
				<tr>
					<td><label for="newPassword">New Password </label></td>
					<td><input type="password" id="newPassword" /></td>
				</tr>
				<tr>
					<td><label for="newPasswordRetyped"> New Password (Retyped) </label></td>
					<td><input type="password" id="newPasswordRetyped" /></td>
				</tr>
			</table>
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
							$("table#cityListTable").append("<thead><tr><th align='left'>City name</th><th align='left'>Action</th></tr></thead>");  
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
							
							$("table#branchListTable").append("<thead><tr><th align='left'>Branch name</th><th align='left'>Street name</th><th align='left'>postcode</th><th align='left'>Action</th></tr></thead>");  
							$("table#branchListTable").append('<tbody>');  
							$.each(data, function(k,value) {
								$.each(value, function(key, v) { 
									if(key == "id") { 
										id = v; 
									} else if(key == "name") { 
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
								for(i=0; i< data.length;i++) { 
									if('${userType}' == 'customer') {
										var vinNumber = data[i].vin; 
										var dailyCost = data[i].dailyCost; 
										$("tbody#vehicleData").append("<tr><td>" + data[i].make + "</td><td>" + 
										data[i].model + "</td><td>" + data[i].maxSpeed + "</td><td>" + 
										data[i].fuel + "</td><td>" + data[i].seating + "</td><td>" + 
										data[i].dailyCost +"</td><td><a id='vehicleHire" + 
										data[i].vin +"' href='#'>Book</a></td></tr>"); 
										
										$("#vehicleHire" + vinNumber).click(function(event) {
											var unavailable;  
											
											function addZero(no) { 
												if(no < 10) { 
													return "0" + no; 
												} else { 
													return no; 
												}
											}
											
											function unavailableDates(date) { 
												var dmy = date.getFullYear() + "-" + addZero(date.getMonth()) + "-" + addZero(date.getDate()); 
												console.log('dmy is : ' + dmy); 
												if($.inArray(dmy, unavailable) == -1) { 
													console.log("it is unavailable!"); 
													return [true, ""]; 
												} else { 
													console.log("it is available!");
													return [false, ""]; 
												}
											} 
											
											$.ajax({ 
												method: 'GET', 
												url: '/VRS/vehicle/bookingDatesByVehicle',
												data: 'vin=' + (event.target.id).substring(11,(event.target.id).length),
												contentType: 'application/json; charset=utf-8', 
												dataType: 'json',
												success: function(data) { 
													unavailable = data;
												}, 
												error: function() { 
												}
											}); 
											
											$.ajax({ 
												url: "/VRS/vehicle/getVehicle", 
												data: "vin=" + (event.target.id).substring(11,(event.target.id).length),  
												success: function(data) {
													$('#hireVehicleModalDialog table').empty();
													$('#hireVehicleModalDialog table').append("<tbody>"); 
													$('#hireVehicleModalDialog').append("<input type='hidden' value='" + data.vin +"' id='hireVinNumber' />");
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
														changeYear: true,
														minDate: new Date(),  
														dateFormat: 'yy-mm-dd',
														beforeShowDay: unavailableDates
													}); 
													
													$('#hireEndDate').datepicker({
														changeMonth: true, 
														changeYear: true,
														minDate: new Date(),
														dateFormat: 'yy-mm-dd', 
														beforeShowDay: unavailableDates
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
						$.ajax({
							method: 'GET', 
							url: '/VRS/user/deleteUser', 
							data: 'username=' + ($(this).data('link').href).split('=')[1],
							dataType: 'json', 
							success: function(data) { 
								if(data.status == "success") { 
									$('#customSuccessAlertModalDialog').empty(); 
									$('#customSuccessAlertModalDialog').append('<p>' + data.message + '</p>'); 
									$('#customSuccessAlertModalDialog').dialog('open');
								} else { 
									$('#customAlertModalDialog').empty(); 
									$('#customAlertModalDialog').append('<p>' + data.message + '</p>'); 
									$('#customAlertModalDialog').dialog('open');
								}
							}, 
							error: function(data) { 
								$('#customAlertModalDialog').empty(); 
								$('#customAlertModalDialog').append('<p>Deleting the user failed. Try again later.</p>'); 
								$('#customAlertModalDialog').dialog('open');
							}
						}); 
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
						alert('vin number is : ' + vinNumber);
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
									if(data.status == "success") { 
										$('#customSuccessAlertModalDialog').empty(); 
										$('#customSuccessAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
										$('#customSuccessAlertModalDialog').dialog('open');	
									} else { 
										$('#customAlertModalDialog').empty(); 
										$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
										$('#customAlertModalDialog').dialog('open');	
									}		
								}, 
								error: function(data) { 
									$('#customAlertModalDialog').empty(); 
									$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customAlertModalDialog').dialog('open');
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
						$.ajax({ 
							method: 'GET', 
							url: '/VRS/user/resetPassword', 
							data: 'username=' + ($(this).data('link').href).split('=')[1], 
							dataType: 'json', 
							success: function(data) { 
								if(data.status == "success") { 
									$('#customSuccessAlertModalDialog').empty(); 
									$('#customSuccessAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customSuccessAlertModalDialog').dialog('open');	
								} else { 
									$('#customAlertModalDialog').empty(); 
									$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customAlertModalDialog').dialog('open');	
								}		
							}, 
							error: function() { 
								$('#customAlertModalDialog').empty(); 
								$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
								$('#customAlertModalDialog').dialog('open');	
							}
						}); 
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
				open: function() { 
					$.ajax({
						method: 'GET', 
						url: '/VRS/vehicle/bookingRecords', 
						data: 'vin=' + (($(this).data('link').href).split('=')[1]),
						success: function(data) { 
							if(data.current > 0) { 
								$('#deleteVehicleModalDialog').empty(); 
								$('#deleteVehicleModalDialog').append('<p> There are current bookings for this vehicle. Cancel the bookings first.</p>'); 
								$(":button:contains('Delete')").prop("disabled",true).addClass('ui-state-disabled'); 
							} else if(data.past > 0) { 
								$('#deleteVehicleModalDialog').empty(); 
								$('#deleteVehicleModalDialog').append('<p> There are expired bookings for this vehicle. Do you wish to proceed.</p>'); 
							} else { 
								$('#deleteVehicleModalDialog').empty(); 
								$('#deleteVehicleModalDialog').append('<p> Are you sure, you wish to delete this vehicle? </p>'); 
							}
						}, 
						error: function(data) { 
							$('#deleteVehicleModalDialog').empty(); 
							$('#deleteVehicleModalDialog').append('<p> Failed to retrieve vehicle booking history. Try later.</p>'); 
							$(":button:contains('Delete')").prop("disabled",true).addClass('ui-state-disabled');
						}
					}); 
				}, 
				buttons: { 
					Delete: function() {
						$.ajax({
							method: 'GET', 
							url: '/VRS/vehicle/delete', 
							data: 'vin=' + (($(this).data('link').href).split('=')[1]),
							dataType: 'json', 
							success: function(data) {
							 if(data.status == "success") { 
									$('#customSuccessAlertModalDialog').empty(); 
									$('#customSuccessAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customSuccessAlertModalDialog').dialog('open');	
								} else { 
									$('#customAlertModalDialog').empty(); 
									$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customAlertModalDialog').dialog('open');	
								}		
							}, 
							error: function(data) { 
								$('#customAlertModalDialog').empty(); 
								$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
								$('#customAlertModalDialog').dialog('open');	
							}
						}); 
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
					}
				}
			}); 
			
			$('#customSuccessAlertModalDialog').dialog({
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
						if($('#hireVehicleModalDialog').is(':visible')) {
							$('#hireVehicleModalDialog').dialog('close');
							location.reload(); 
						}
						if($('#extendVehicleHirePeriodModalDialog').is(':visible')) { 
							$('#extendVehicleHirePeriodModalDialog').dialog('close');
							location.reload(); 
						}
						if($('#deleteVehicleModalDialog').is(':visible')) { 
							$('#deleteVehicleModalDialog').dialog('close'); 
							location.reload(); 
						}
						if($('#cancelVehicleBookingModalDialog').is(':visible')) { 
							$('#cancelVehicleBookingModalDialog').dialog('close'); 
							location.reload();
						}
						if($('#deleteModalDialog').is(':visible')) { 
							$('#deleteModalDialog').dialog('close'); 
							location.reload();
						}
					}
				}
			}); 
			
			$('.deleteUser').click(function() {
				$('#deleteModalDialog')
				.data('link',this)
				.dialog('open');
				return false; 
			});  
			
			$('.resetPassword').click(function() {
				$('#resetModalDialog')
				.data('link',this)
				.dialog('open');
				return false; 
			});  
			
			$('.deleteVehicle').click(function() {	
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
							data: 'bookingId=' + (($(this).data('link').href).split('=')[1]), 
							dataType: 'json', 
							success: function(data) { 
								if(data.status == "success") {  
									$('#customSuccessAlertModalDialog').empty(); 
									$('#customSuccessAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customSuccessAlertModalDialog').dialog('open');
								} else { 
									$('#customAlertModalDialog').empty(); 
									$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customAlertModalDialog').dialog('open');
								}
							}, 
							error: function(data) { 
								$('#customAlertModalDialog').empty(); 
								$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
								$('#customAlertModalDialog').dialog('open');
							}
						}); 
					}, 
					No: function() {
						$(this).dialog('close'); 
					}
				}
			}); 
			
			$('#returnedVehicleModalDialog').dialog({
				modal: true, 
				autoOpen: false, 
				width: 'auto', 
				resizable: false, 
				open: function() {
					$.ajax({ 
						method: 'GET', 
						url: '/VRS/vehicle/booking', 
						data: 'bookingId=' + (($(this).data('link').href).split('=')[1]), 
						dataType: 'json', 
						success: function(data) {
							var vinNumber = data.vehicleVin;
							$('table#returnedVehicleTable tbody').empty();
							$('table#returnedVehicleTable').append("<tr><td>Booking start date</td><td>"
							 + "<input type='text' id='returnedVehicleBookingStartDate' value='" +data.startDate+ "' disabled /></td></tr>"); 
							 $('table#returnedVehicleTable').append("<tr><td>Booking end date</td><td>"
							 + "<input type='text' id='returnedVehicleBookingEndDate' value='" + data.endDate + "' /></td></tr>"); 
							 $('table#returnedVehicleTable').append("<tr><td>Insurance</td><td>"
							 + "<input type='text' id='returnedVehicleInsurance' value='" + data.insurance + "' /></td></tr>"); 
							 $('table#returnedVehicleTable').append("<tr><td>Daily cost</td><td>"
							 + "<input type='text' id='returnedVehicleDailyCost'></input></td></tr>"); 
							 $('table#returnedVehicleTable').append("<tr><td>Damaged?</td><td>"
							 + "<input type='radio' name='returnedVehicleDamaged' value='Yes' checked/> Yes"
							 + "<input type='radio' name='returnedVehicleDamaged' value='No' /> No </td></tr>"); 
							 $('table#returnedVehicleTable').append("<tr><td>Damage cost</td><td>"
							 + "<input type='text' id='returnedVehicleDamageCost' value='" +data.damageCost + "' /></td></tr>"); 
							 $('table#returnedVehicleTable').append("<tr><td>Total cost (Damage (if no insurace taken))</td><td>"
							 + "<input type='text' id='returnedVehicleTotalCost' /></td></tr>");
							 $('table#returnedVehicleTable').append("<tr><td>Amount Paying</td><td>"
							 + "<input type='text' id='returnedVehicleAmountPaying' /></td></tr>");  
							 $('table#returnedVehicleTable').append("<tr><td>Amount Remaining</td><td>"
							 + "<input type='text' id='returnedVehicleAmountRemaining' /></td></tr>");  
							 
							 var endDateVal = data.endDate; 
							 var startDateVal = data.startDate; 
							 var startDateChunks = startDateVal.split('-'); 
							 var endDateChunks = endDateVal.split('-'); 
							 var date = new Date(); 
							 
							 if(date.getFullYear() == endDateChunks[0] && 
							    date.getMonth() == endDateChunks[1] && 
							    date.getDate() == endDateChunks[2]) { 
							    	$('#returnedVehicleBookingEndDate').attr('disabled','disabled'); 
							 } else { 
						    	$('#returnedVehicleBookingEndDate').datepicker({
						    		changeMonth: false, 
									changeYear: false, 
									dateFormat: 'yy-mm-dd', 
									minDate: startDateVal,
									maxDate: endDateVal
						    	}); 
							 }
							 
							 $.ajax({
							 	method: 'GET', 
							 	url: '/VRS/vehicle/vehicleDailyCost', 
							 	data: 'vin=' + vinNumber, 
							 	dataType: 'json', 
							 	success: function(data) { 
							 		$('#returnedVehicleDailyCost').attr('value',data.cost); 
							 		$('#returnedVehicleDailyCost').text(data.cost); 
							 		 
							 		console.log("daily cost: " + $("#returnedVehicleDailyCost").val()); 
							 	}, 
							 	error: function() { 
							 		alert('failed to retrieve vehicle cost!.'); 
							 	}
							}); 
							
							$("input[name='returnedVehicleDamaged'][type='radio']," +
							  "#returnedVehicleDamageCost, #returnedVehicleBookingEndDate").change(function() {
							  	console.log("event triggered .... "); 
							  	var startDate = new Date(startDateChunks[0],startDateChunks[1]-1,startDateChunks[2]); 
							  	var endDateVal = $("#returnedVehicleBookingEndDate").val(); 
							  	var endDateChunks = endDateVal.split('-'); 
							  	var endDate = new Date(endDateChunks[0], endDateChunks[1]-1,endDateChunks[2]); 
							  	
							  	var dateDifference = endDate.getTime() - startDate.getTime(); 
								var days = dateDifference/(86400000); 
								//console.log("days : " + days);
								var dailyCost = $("#returnedVehicleDailyCost").val(); 
								//console.log("daily cost : " + dailyCost); 
								var damage = $("#returnedVehicleDamageCost").val(); 
								//console.log("damage cost : " + damage); 
															
								var insuranceTaken = $("#returnedVehicleInsurance"); 
								//console.log("insurance : " + insuranceTaken.val()); 
								var total; 
							
								if(insuranceTaken.val() == 'true') { 
									total = (days * dailyCost) + 110; //110 is insurance value 
									$('#returnedVehicleTotalCost').attr('value',total); 
								} else { 
									if($("input[name='returnedVehicleDamaged']:checked").val() == "Yes") {
										//console.log("damaged yes.");  
										total = (days * dailyCost) + parseInt(damage); 
										$('#returnedVehicleTotalCost').attr('value',total); 
									} else { 
										//console.log("not damaged."); 
										total = days * dailyCost; 
										$('#returnedVehicleTotalCost').attr('value',total); 
									}
								}
							}); 
						}, 
						error: function() { 
							alert("retrieving booking details failed."); 
						}
					}); 
				}, 
				buttons: {
					OK: function() { 
						$.ajax({ 
							method: 'POST', 
							url: '/VRS/vehicle/returnedVehicle', 
							data: 'bookingId=' + (($(this).data('link').href).split('=')[1]) + 
								'&endDate=' + $('#returnedVehicleBookingEndDate').val() +
								'&damaged=' + $("input[name='returnedVehicleDamaged']:checked").val() +
								'&damageCost=' + $('#returnedVehicleDamageCost').val() +
								'&amountPaying=' + $('#returnedVehicleAmountPaying').val() +
								'&amountRemaining=' + $('#returnedVehicleAmountRemaining').val(), 
							dataType: 'json', 
							success: function(data) {
								if(data.status == "success") {  
									$('#customSuccessAlertModalDialog').empty(); 
									$('#customSuccessAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customSuccessAlertModalDialog').dialog('open');
								} else { 
									$('#customAlertModalDialog').empty(); 
									$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customAlertModalDialog').dialog('open');
								}  
							}, 
							error: function() { 
								$('#customAlertModalDialog').empty(); 
								$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
								$('#customAlertModalDialog').dialog('open');
							}
						}); 
						$(this).dialog('close'); 
					}, 
					Cancel: function() { 
						$('this').dialog('close');
					} 
				}
			}); 
			
			$('#changePasswordModalDialog').dialog({ 
				modal: true, 
				autoOpen: false, 
				width: 'auto', 
				resizable: false, 
				buttons: {
					Change: function() { 
						$.ajax({
							method: 'GET', 
							url: '/VRS/user/changePassword', 
							data: 'currentPassword=' + $('#currentPassword').val() + 
							'&newPassword=' + $('#newPassword').val() + 
							'&newPasswordRetyped=' + $('#newPasswordRetyped').val(),
							dataType: 'json', 
							success: function(data) { 
								if(data.status == "success") {  
									$('#customSuccessAlertModalDialog').empty(); 
									$('#customSuccessAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customSuccessAlertModalDialog').dialog('open');
								} else { 
									$('#customAlertModalDialog').empty(); 
									$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customAlertModalDialog').dialog('open');
								}  
							}, 
							error: function() { 
								$('#customAlertModalDialog').empty(); 
								$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
								$('#customAlertModalDialog').dialog('open');
							}
						});
						$(this).dialog('close'); 
					}, 
					Cancel: function() { 
						$(this).dialog('close'); 
					}
				}
			}); 
			
			$('#extendVehicleHirePeriodModalDialog').dialog({
				modal: true, 
				autoOpen: false, 
				width: 'auto', 
				resizable: false,  
				open: function() {
					var unavailable;
					var currentBookingStartDate; 
					var currentBookingEndDate;   
					var bookingId = (($(this).data('link').href).split('=')[1]); 
					
					$.ajax({ 
						method: 'GET', 
						url: '/VRS/vehicle/booking',
						data: 'bookingId=' + bookingId,
						contentType: 'application/json; charset=utf-8', 
						dataType: 'json',
						success: function(bookingData) { 
							currentBookingStartDate = bookingData.startDate; 
							currentBookingEndDate = bookingData.endDate; 
							
							$.ajax({ 
								method: 'GET', 
								url: '/VRS/vehicle/bookingDates',
								data: 'bookingId=' + bookingId,
								contentType: 'application/json; charset=utf-8', 
								dataType: 'json',
								success: function(data) { 
									unavailable = data; 
									
									var realEndDate = new Date(currentBookingEndDate.split('-')[0], 
									(currentBookingEndDate.split('-')[1] - 1),
									currentBookingEndDate.split('-')[2]); 
									
									var realStartDate = new Date(currentBookingStartDate.split('-')[0], 
									(currentBookingStartDate.split('-')[1] -1),
									currentBookingStartDate.split('-')[2]); 
									
									
									$('#extensionEndDate').datepicker({
										changeMonth: true, 
										changeYear: true, 
										dateFormat: 'yy-mm-dd', 
										minDate: realEndDate,
										beforeShowDay: unavailableDates
									}); 
									
									$('#extensionStartDate').datepicker({
										changeMonth: true, 
										changeYear: true, 
										dateFormat: 'yy-mm-dd', 
										minDate: realStartDate,
										maxDate: realStartDate
									}); 
																
									$('#extensionStartDate').datepicker('setDate',realStartDate);
									$('#extensionEndDate').datepicker('setDate',realEndDate); 
								},
								error: function() {
									console.log("call failed."); 
								} 
							});
						},
						error: function() { 
							alert("failed to fetch current booking dates. "); 
						}
					});
					
					function addZero(no) { 
						if(no < 10) { 
							return "0" + no; 
						} else { 
							return no; 
						}
					}
					
					function unavailableDates(date) { 
						var dmy = date.getFullYear() + "-" + addZero(date.getMonth()) + "-" + addZero(date.getDate()); 
						console.log('dmy : ' + dmy);
						if($.inArray(dmy, unavailable) == -1) { 
							console.log("it is unavailable!"); 
							return [true, ""]; 
						} else { 
							console.log("it is available!");
							return [false, ""]; 
						}
					} 
					
				}, 
				buttons: {
					Yes: function() { 
						$.ajax({
							type: 'GET', 
							url: '/VRS/vehicle/extendBooking', 
							data: 'bookingId=' + (($(this).data('link').href).split('=')[1]) + '&endDate=' + $('#extensionEndDate').val(), 
							dataType: 'json', 
							success: function(data) {
								if(data.status == "success") {  
									$('#customSuccessAlertModalDialog').empty(); 
									$('#customSuccessAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customSuccessAlertModalDialog').dialog('open');
								} else { 
									$('#customAlertModalDialog').empty(); 
									$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
									$('#customAlertModalDialog').dialog('open');
								}
							}, 
							error: function() { 
								$('#customAlertModalDialog').empty(); 
								$('#customAlertModalDialog').append('<p> ' + data.message + ' </p>'); 
								$('#customAlertModalDialog').dialog('open');
							}
						}); 
					}, 
					No: function() {
						$(this).dialog('close'); 
					}
				}
			}); 
			
			$('.cancelVehicleBooking').click(function() { 
				 $('#cancelVehicleBookingModalDialog')
				 .data('link',this)
				 .dialog('open');
				 return false; 
			}); 
			
			$('.extendVehicleHirePeriod').click(function() { 
				 $('#extendVehicleHirePeriodModalDialog')
				 .data('link',this)
				 .dialog('open');
				 return false; 
			}); 
			
			$('.returnedVehicle').click(function() { 
				$('#returnedVehicleModalDialog')
				.data('link', this)
				.dialog('open'); 
				return false; 
			}); 
			
			$('#changePasswordLink').click(function() { 
				$('#changePasswordModalDialog').dialog('open'); 
				return false;
			}); 
		}); 
	</script>
</html>