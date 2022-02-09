<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forget Password Page</title>
</head>
<body>

	<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">
		<%@ include file="header.jsp"%>
		
		<jsp:useBean id="bean" class="in.co.rays.project4.beans.UserBean" scope="request"></jsp:useBean>
		<center>

			<h1 style="color: red">Forgot your Password?</h1>
			<h4>Submit your email address and we'll send you password</h4>

			<h4>
				<b> <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				<%=ServletUtility.getErrorMessage("login", request)%>
				</font> <font color="green"><%=ServletUtility.getSuccessMessage(request)%>
				</font></b>

			</h4>

			<table>
				<tr>
					<th>Email Id<font style="color: red">*</font></th>
					<td><input type="text" name="login"
						placeholder="Enter Login Id" value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
					<td><input type="submit" name="operation" value="Click"></td>
				</tr>  
			</table>
	</form>

	</center>
	<%@ include file="footer.jsp"%>

</body>
</html>