<%@page import="in.co.rays.project4.controller.ChangePassCtl"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="in.co.rays.project4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Password</title>
</head>
<body>

	<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">
		<%@ include file="header.jsp"%>
		
		<jsp:useBean id="bean" class="in.co.rays.project4.beans.UserBean"
			scope="request"></jsp:useBean>

		<center>
			<h1>Change Password</h1>
			
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=bean.getModifiedDatetime()%>">
				
				
				<h2 style="color: red"><%=ServletUtility.getErrorMessage(request)%></h2>
			<h2 style="color: green"><%=ServletUtility.getSuccessMessage(request)%></h2>
			

			<table>

				<tr>
					<th align="left">Old Password <font color="red">*</font></th>
					<td><input type="password" name="oldPass" placeholder="Enter Your Old Password"></td>
					<td style="position:fixed"><font color="red"><%=ServletUtility.getErrorMessage("oldPass", request) %>
					</font></td>
				</tr>
				<tr>
					<th align="left">New Password <font color="red">*</font></th>
					<td><input type="password" name="newPass" placeholder="Enter new Password"></td>
					<td style="position:fixed"><font color="red">
							<%=ServletUtility.getErrorMessage("newPass", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">Confirm Password <font color="red">*</font></th>
					<td><input type="password" name="newConPass" placeholder="Confirm new Password"></td>
					<td style="position:fixed"><font color="red">
							<%=ServletUtility.getErrorMessage("newConPass", request)%>
							<%=ServletUtility.getErrorMessage("passMatch", request)%>
					</font></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" name="operation"
						value="<%=ChangePassCtl.OP_SAVE%>">
						<input type="submit" name="operation"
						value="<%=ChangePassCtl.OP_CHANGE_MY_PROFILE%>">
					</td>

				</tr>
			</table>

		</center>
	</form>

	<%@ include file="footer.jsp"%>

</body>
</html>