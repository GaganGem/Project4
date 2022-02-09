<%@page import="in.co.rays.project4.controller.MarksheetMeritListCtl"%>
<%@page import="in.co.rays.project4.beans.MarksheetBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet Merit List</title>
</head>
<body>


	<%@ include file="header.jsp"%>
	<center>

		<h1>Merit List</h1>

		<form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="post">

			<%
				ArrayList<MarksheetBean> list = (ArrayList<MarksheetBean>) request.getAttribute("list");
			%>

			<table width="100%" border="1">
				<tr>
					<th>S No:</th>
					<th>ROLL NO:</th>
					<th>NAME:</th>
					<th>PHYSICS:</th>
					<th>CHEMISTRY:</th>
					<th>MATHS:</th>
					<th>Total</th>
					<th>Percentage(%)</th>
				</tr>
				<%
					if (list != null) {
						int index = 1;
						for (MarksheetBean b : list) {
					if(b.getChemistry()>33 && b.getMaths()>33 && b.getPhysics()>33){
				%>
				<tr>
					<td align="center"><%=index++%></td>
					<td><%=b.getRollNo()%></td>
					<td><%=b.getName()%></td>
					<td><%=b.getPhysics()%></td>
					<td><%=b.getChemistry()%></td>
					<td><%=b.getMaths()%></td>
					<td align="center">
						<%
							int total = (b.getChemistry() + b.getPhysics() + b.getMaths());
						%><%=total%></td>
					<td align="center">
						<%
							float percentage = ((total * 100) / 300);
						%> <%=percentage%></td>
				</tr>
				<%
					}
						}
				%>
			</table>
			<%
				}
			%>
			
			<br>
			<center>
			<table>
				<tr>
					<td><input type="submit" name="operation"
						value="<%=MarksheetMeritListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			</center>
		</form>

	</center>

	<%@ include file="footer.jsp"%>

</body>
</html>