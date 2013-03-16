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
									$("tbody#vehicleData").append("<tr><td>" + data[i].maxSpeed + "</td><td>" + data[i].fuel + "</td><td>" + data[i].seating + "</td><td>" + data[i].available + "</td></tr>"); 
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
		</div>
	</body>
</html>