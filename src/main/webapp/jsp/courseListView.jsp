<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.controller.CourseListCtl"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="in.co.rays.project4.beans.CourseBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>
	<%@ include file="header.jsp"%>


	<form action="<%=ORSView.COURSE_LIST_CTL%>" method="post">

		<center>
		
		<jsp:useBean id="bean" class="in.co.rays.project4.beans.CourseBean" scope="request"></jsp:useBean>

			<h1>Course List</h1>
			
			<h3>	<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			<font color="red"> <%=ServletUtility.getSuccessMessage(request)%>
			</font> </h3>
			
			<div style="margin-bottom: 15px; margin-top: 20px">
				<label for="name"><b>Name: </b></label><input type="text" placeholder="Enter Course" name="name"
				value="<%=DataUtility.getString(bean.getCourseName())%>">
				<label for="Desc" style="margin-left: 20px"><b>Desc: </b></label><input
					type="text" placeholder="Enter Description"
					value="<%=DataUtility.getString(bean.getDescription())%>" name="Desc"> <input type="submit"
					value="Search" style="margin-left: 40px"><input type="submit"
					value="<%=CourseListCtl.OP_RESET%>" style="margin-left: 40px">
			</div>
			
			<%
				ArrayList<CourseBean> list = (ArrayList<CourseBean>) request.getAttribute("list");
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				if (list.size() != 0) {
			%>

			
			<table width="100%" border="1">
				<tr>
					<th><input type="checkbox" id="select_all" name="select">Select
						All.</th>
					<th>S No:</th>
					<th>Course Name:</th>
					<th>Description:</th>
					<th>Duration:</th>
					<th>EDIT:</th>
				</tr>
				<%
					if (list != null) {
						for (CourseBean b : list) {
				%>
				<tr>
					<td align="center" width="130px"><input type="checkbox" class ="checkbox" name="ids" value="<%=b.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td><%=b.getCourseName()%></td>
					<td><%=b.getDescription()%></td>
					<td><%=b.getDuration()%></td>
					<td><a href="CourseCtl?id=<%=b.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>
			<%
				}
			%>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						value="<%=CourseListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>
					<td><input type="submit" name="operation"
						value="<%=CourseListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						value="<%=CourseListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=CourseListCtl.OP_NEXT%>"
						<%=(nextPageSize == 0) ? "disabled" : ""%>></td>
				</tr>
			</table>
			
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

<%
				}
				if (list.size() == 0) {
			%>
			<input type="submit" name="operation"
				value="<%=CourseListCtl.OP_BACK%>">
			<%
				}
			%>
		</center>

	</form>


	<%@ include file="footer.jsp"%>

</body>
</html>