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
			<div id="editVehicle">
				<form:form action="/VRS/rental/editVehicle" method="POST" commandName="vehicle">
					<table border="1">
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
									<form:label path="max_speed" cssClass="dateTitle">Max Speed</form:label>
								</td>
								<td>
									<form:input path="max_speed" cssClass="dateControl"/>
								</td>
								<td>
									<form:errors path="max_speed" cssClass="dateError"/>  
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
									<label name="make" class="inputTitle">Make</label>
								</td>
								<td>
									<input name="make" class="inputControl"/>
								</td>
								
							</tr>
							<tr>
								<td>
									<label name="model" class="inputTitle">Model</label>
								</td>
								<td>
									<input name="model" class="inputControl"/>
								</td>
								
							</tr>
							<tr>
								<td>
									<label name="city" class="inputTitle">City</label>
								</td>
								<td>
									<input name="city" class="inputControl"/>
								</td>
							</tr>
							<tr>
								<td>
									<label name="branch" class="inputTitle">Branch</label>
								</td>
								<td>
									<input name="branch" class="inputControl"/>
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
			$('#update').click(function() { 
			}); 
		}); 
	</script>
</html>
	