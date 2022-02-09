<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Marksheet Page</title>
</head>
<body>
	<%@ include file="header.jsp"%>
<jsp:useBean id="bean" class="in.co.rays.project4.beans.MarksheetBean" scope="request"></jsp:useBean>

	<center>
		<h1>Get Marksheet</h1>
		<font color="red"><%String err = (String) request.getAttribute("rollNo");
		if(err != null){%>
		<%=err%>
		<%} %>
		</font>
		<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
		
		<form action="<%=ORSView.GET_MARKSHEET_CTL %>" method="post"><b>
			Enter Your Roll No Here</b>:<input type="text" name="rollNo" value="<%=DataUtility.getStringData(bean.getRollNo()) %>" placeholder="Enter Roll No"> <input
				type="submit" value="Go"><br>
		</form>
		<%-- <jsp:useBean id="bean" class="in.co.rays.project4.beans.MarksheetBean" scope="request"></jsp:useBean>
 --%>
		<%
			if (bean.getName() != null) {
		%>
		<table>
			<tr>
				<th align="left">Roll No:</th>
				<td><%=bean.getRollNo()%></td>
			</tr>
			<tr>
				<th align="left">Name:</th>
				<td><%=bean.getName()%></td>
			</tr>
			<tr>
				<th align="left">Physics:</th>
				<td><%=bean.getPhysics()%></td>
			</tr>
			<tr>
				<th align="left">Chemistry:</th>
				<td><%=bean.getChemistry()%></td>
			</tr>
			<tr>
				<th align="left">Maths:</th>
				<td><%=bean.getMaths()%></td>
			</tr>



		</table>

		<%
			}
		%>

	</center>

	<%@ include file="footer.jsp"%>

</body>

</html>