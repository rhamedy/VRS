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
	