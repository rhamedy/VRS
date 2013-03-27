<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html xmlns="http://www.w3c.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title> Oscar Vehicle Rental System </title> 
		<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/themes/ui-lightness/jquery-ui.css" type="text/css" />
		<style>
			#main { 
				padding: 5px 5px 5px 5px; 
			}
			#vehicleDiv { 
				border: 1px solid black; 
				padding: 5px 5px 5px 5px;
				margin-top: 10px; 
			}
			#accountDiv { 
				border: 1px solid black; 
				padding: 5px 5px 5px 5px;
				margin-top: 10px; 
			} 
	</style>
	</head>
	<body>
		<div id="main">
			<div class="vehicleDiv">
				<label for="countriesList"> Countries </label>
				<select id="countryList">
					<option value="" selected></option>
					<c:forEach var="entry" items="${countries}">
						<c:forEach var="e" items="${entry}">
							<option class="" value="${e.key}">${e.value}</option>
						</c:forEach>
					</c:forEach>
				</select><br />
				<label for="cityList"> Cities </label>
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
						</tr>
					</thead>
					<tbody id="vehicleData">
					</tbody>
				</table>
			</div>
			<div id="account">
				<h4> Fill out an account request form </h4>
				<form:form action="/VRS/home/public/accountRequest" method="POST" commandName="user">
					<input type="hidden" id="usernameAvailable" value="false" />
					<table border="1">
						<thead>
						</thead> 
						<tbody>
							<tr>
								<td>
									<form:label path="username" cssClass="inputControl">Username (Valid email)</form:label>
								</td>
								<td>
									<form:input path="username" cssClass="inputError" />
								</td>
								<td>
									<form:errors path="username" cssClass="inputError"/>  
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="firstName" cssClass="inputControl">First name</form:label>
								</td>
								<td>
									<form:input path="firstName" cssClass="inputError" />
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
									<form:input path="dob" cssClass="dateControl" />
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
									<form:input path="licenseValidity" cssClass="dateControl" />
								</td>
								<td>
									<form:errors path="licenseValidity" cssClass="dateError"/>  
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
			<div id="usernameModalDialog">
				<p> The username is already in use, choose a different one. </p>
			</div>
		</div>
	</body>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("select#countryList").change(function() {
				$("select#cityList").empty(); 
				$("select#branchList").empty();  
				if($(this).children(":selected").val().trim().length > 0) { 
					$.ajax({
						url: "/VRS/home/public/cities",
						data: "countryId=" + $(this).children(":selected").val(), 
						success: function(data) {
							var flag = false; 
							$.each(data, function(k,value) {
								$.each(value, function(key, v) { 
									$("select#cityList").append("<option value='" + key + "'>" + v + "</option>"); 
									flag = true; 
								}); 
							}); 
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
			
			$('button#update').click(function() {
				if($('#usernameAvailable').attr('value') == 'true') { 
					$('#usernameModalDialog').dialog('open');
				} else if($('#usernameAvailable').attr('value') == 'false'){ 
					$.ajax({ 
						url: '/VRS/home/public/accountRequest', 
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
						error: function(req, status, error) {
							$('p#updateStatusTitle').text("Server Error. Failed sending account request. Try later."); 
							$('p#updateStatusTitle').css('color','red');
						}
					});
				}
				return false;
			}); 
			
			$('input#username').change(function() { 
				$.ajax({ 
					url: "/VRS/home/public/usernameAvailable", 
					data: "username=" + $('input#username').val(), 
					dataType: 'json', 
					method: 'GET', 
					success: function(data) {
						if(data['status'] == 'success') { 
							$('input#usernameAvailable').attr('value','false');
						} else if(data['status'] == 'failure') { 
							$('input#usernameAvailable').attr('value','true');
						} 
					}, 
					error: function(req, status, error) {
						$('input#usernameAvailable').attr('value','false'); 
					}
				}); 
			}); 
			
			$('#usernameModalDialog').dialog({
				modal: true, 
				autoOpen: false, 
				width: 'auto', 
				resizable: false, 
				buttons: { 
					Yes: function() {
						$(this).dialog("close"); 
					}, 
					No: function() { 
						$(this).dialog("close"); 
					}
				}
			}); 
		}); 
	</script>
</html>