<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>

	<%@ include file="header.jsp"%>

	<center>
		<h1 style="color: red;">Login to ORS</h1>
		<form action="<%=ORSView.LOGIN_CTL%>" method="post">
			<jsp:useBean id="bean" class="in.co.rays.project4.beans.UserBean" scope="request"></jsp:useBean>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=bean.getModifiedDatetime()%>">

<%
						String uri = (String) request.getAttribute("uri");
					%>
			<table>
				<h3 style="color: red"><%=ServletUtility.getErrorMessage(request)%></h3>
				<h3 style="color: green"><%=ServletUtility.getSuccessMessage(request)%></h3>
				<h3 style="color: red"><%=DataUtility.getString((String)request.getAttribute("message")) %></h3>
				<tr>
					<th align="left"><label style="font-size: 20px;" for="login"><b>Login Id<font
								color="red">*</font></b></label></th>
					<td><input type="text" name="login"
						placeholder="Enter Your Email Id"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
					<td style="position:fixed"><font color="red"><%=ServletUtility.getErrorMessage("login", request)%>
							</font></td>

				</tr>
				<tr>
					<th align="left"><label style="font-size: 20px;" for="password"><b>Password<font
								color="red">*</font></b></label></th>
					<td><input type="password" name="password"
						placeholder="Enter Your Password Here" value="<%=DataUtility.getStringData(bean.getPass())%>">
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("password", request) %>
					</font></td>

				</tr>
				<tr>
					<td></td>
					<td><input type="submit" name="operation"
						value="Sign In">&nbsp;<input
						type="submit" name="operation" value="Sign Up"></td>
				</tr>
				<tr>
					<td></td>
					<td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget
								my password</b></a></td>
				</tr>

			</table>
<input type="hidden" name="uri" value="<%=uri%>">
		</form>

	</center>
	<%@ include file="footer.jsp"%>


</body>
</html>