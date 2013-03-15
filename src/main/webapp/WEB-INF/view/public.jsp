<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3c.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title> Oscar Vehicle Rental System </title> 
	</head>
	<script language="JavaScript" type="text/javascript" src="/VRS/resources/js/jquery-1.6.1.min.js" ></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("select#countryList").change(function() { 
				$("select#cityList").empty(); 
				$("select#branchList").empty(); 
				$.ajax({
					url: "/VRS/home/public/cities",
					data: "countryId=" + $(this).children(":selected").val(), 
					success: function(data) {
						$("select#cityList").append("<option value='' selected></option>");
						$.each(data, function(k,value) {
							$.each(value, function(key, v) { 
								$("select#cityList").append("<option value='" + key + "'>" + v + "</option>"); 
							}); 
						}); 
					} 
				}); 
			});
		}); 
	</script>
	<style>
		#main { 
			padding: 5px 5px 5px 5px; 
		}
		#account { 
			border: 1px solid black; 
			padding: 5px 5px 5px 5px;
			margin-top: 10px; 
		} 
	</style>
	<body>
		<div id="main">
			<div class="countryDiv">
				<label for="countriesList"> Countries </label>
				<select id="countryList">
					<c:forEach var="entry" items="${countries}">
						<c:forEach var="e" items="${entry}">
							<option class="" value="${e.key}">${e.value}</option>
						</c:forEach>
					</c:forEach>
				</select><br />
				<label for="cityList"> Cities </label>
				<select id="cityList">
					<option value="test">test</option>
				</select><br />
				<label for="branchList">Branches</label>
				<select id="branchList" disabled>
				</select><br />
				<label for="vehicleList">Vehicles list</label>
				<table id="vehicleList">
				</table>
			</div>
		</div>
	</body>
</html>