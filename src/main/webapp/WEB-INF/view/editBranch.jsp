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
		<br />
		<h1><center>Oscar Vehicle Rental System</center></h1><br>
		<div id="main">
			<div id="editBranch">
				<form:form action="/VRS/branch/editBranch" method="POST" commandName="branch">
					<input type="hidden" value="${existingBranch}" id="existingBranch" />
					<form:hidden path="id" />
					<table border="1">
						<thead>
						</thead> 
						<tbody>
							<tr>
								<td>
									<form:label path="name" cssClass="inputTitle">Branch name</form:label>
								</td>
								<td>
									<form:input path="name" cssClass="inputControl" />
								</td>
								<td>
									<form:errors path="name" cssClass="inputError"/>  
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="streetName" cssClass="inputTitle">Street name</form:label>
								</td>
								<td>
									<form:input path="streetName" cssClass="inputControl" />
								</td>
								<td>
									<form:errors path="streetName" cssClass="inputError"/>  
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="postcode" cssClass="inputTitle">Postcode</form:label>
								</td>
								<td>
									<form:input path="postcode" cssClass="inputControl" />
								</td>
								<td>
									<form:errors path="postcode" cssClass="inputError"/>  
								</td>
							</tr>
							<tr>
								<td>
									<label name="make" class="inputTitle">City</label>
								</td>
								<td>
									<select name="city" id="city">
										<c:forEach items="${cities}" var="city">
											<c:forEach items="${city}" var="c">
												<c:choose>
													<c:when test="${c.key == branch.cityId}">
														<option value="${c.key}" selected>${c.value}</option>
													</c:when>
													<c:otherwise>
														<option value="${c.key}">${c.value}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:forEach>
									</select>
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
			<div id="cityModalDialog">
				<p> Select a city to proceed! </p>
			</div>
		</div>
	</body>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() { 
			$('#cityModalDialog').dialog({ 
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
			
			$('#update').click(function() { 
				$.ajax({ 
					url: '/VRS/branch/editBranch', 
					data: $('#branch').serialize(), 
					dataType: 'json', 
					method: 'POST', 
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
	