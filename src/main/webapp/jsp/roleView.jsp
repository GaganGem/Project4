<%@page import="in.co.rays.project4.controller.RoleCtl"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Role Registration Page</title>
</head>
<body>

	<form action="<%=ORSView.ROLE_CTL%>" method="post">
		<%@ include file="header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.project4.beans.RoleBean" scope="request"></jsp:useBean>

		<center>

			<h1>
				<b> <%
 	if (bean.getId() > 0) {
 %> <%="Update Role"%> <%
 	} else {
 %> <%="Add Role"%> <%
 	}
 %>
				</b>
			</h1>

			<h3>
				<font color="green"><b> <%=ServletUtility.getSuccessMessage(request)%>
				</b></font><font color="red"><b> <%=ServletUtility.getErrorMessage(request)%>
				</b></font>
			</h3>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=bean.getModifiedDatetime()%>">

			<table>

				<tr>
					<th align="left">Name<font color="red">*</font></th>
					<td><input type="text" name="name" placeholder="Enter Role Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("name", request) %>
 				</font></td>
				</tr>
				<tr>
					<th align="left">Desciption<font color="red">*</font></th>
					<td><input type="text" name="desc" placeholder="Enter desc"
						value="<%=DataUtility.getStringData(bean.getDescription())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("desc", request) %>
 					</font></td>
				</tr>
				<tr>
					<td></td>
					<%
						if (bean.getId() > 0) {
					%>
					<td><input type="submit"
						name="operation" value="<%=RoleCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
						value="<%=RoleCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td><input type="submit"
						name="operation" value="<%=RoleCtl.OP_SAVE%>">
						<input type="submit" name="operation"
						value="<%=RoleCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>
			</table>
		</center>
	</form>

	<%@ include file="footer.jsp"%>


</body>
</html>