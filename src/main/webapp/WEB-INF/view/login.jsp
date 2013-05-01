<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html xmlns="http://www.w3c.org/1999/xhtml">
	<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>Oscars Vehicle Rental System</title>
	<link rel="stylesheet" href="/VRS/resources/css/style.css" type="text/css" />
	<body>
		<br />
		<h1><center>Oscar Vehicle Rental System</center></h1><br>
		<div id="container">
			<div id="public_menu_bar">
				<ul class="public_menu_bar">
					<li><a href="#" onclick=""> Public </a></li>
				</ul>
			</div><br />
			<div class="section_title">
				<p> Login using your credentials <br></p>
			</div>
			<div id="login">
				<div id="login_div">
					<br />
			    	<form action="/VRS/j_spring_security_check" method="POST" id="loginForm">
				    	<label for="username"> Username</label>
				        <input id="username" type="text" name="j_username"/><br><br> 
				        <label for="password"> Password</label>
				        <input style="margin-left:5px;" id="password" type="password" name="j_password"/><br><br> 
				    </form>
				    <button style="margin-left:130px;" onclick="javascript:document.getElementById('loginForm').submit()">Sign in</button><br />
				     <!-- <button style="margin-left:110px;" onclick="">Forgot password</button><br /> -->
				    <br><br>
			    </div>
			</div>
			<div class="error_title">
				<p> Logging status </p>
			</div>
			<div class="error">
				<div class="error_content">
					<p> Registered users, use your username and password to login.</p>
					<p>
						<font color="red">
							${fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'Bad credentials', 'Username and/or Password is incorrect.')}
						</font>
				</div>
			</div>
			<br /><br />
		</div>
	</body>
</html>