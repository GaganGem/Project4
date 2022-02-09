<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.beans.StudentBean"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.controller.StudentListCtl"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>
	<form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">
		<%@ include file="header.jsp"%>

		<%
			List clist = (List) request.getAttribute("CollegeList");
		%>

		<center>
		
		<jsp:useBean id="bean" class="in.co.rays.project4.beans.StudentBean" scope="request"></jsp:useBean>

			<h1>Student List</h1>

			<h3>
				<font color="green"><b> <%=ServletUtility.getSuccessMessage(request)%>
				</b></font><font color="red"><b> <%=ServletUtility.getErrorMessage(request)%>
				</b></font>
			</h3>
			<div style="margin-bottom: 15px; margin-top: 20px">
				<label for="fName"><b>First Name: </b></label><input type="text" placeholder="Enter Name"
					value="<%=DataUtility.getString(bean.getFirstName())%>" name="fName" > <label for="lName"><b>Last Name: </b></label><input
					type="text" placeholder="Enter Last Name"
					value="<%=DataUtility.getString(bean.getLastName())%>" name="lName"> <label for="college"
					style="margin-left: 20px"><b>College: </b></label>
				<%=HTMLUtility.getList("collegeid", ServletUtility.getParameter("collegeid", request), clist)%>
				<input type="submit" name="operation"
					value="<%=StudentListCtl.OP_SEARCH%>"> <input type="submit"
					name="operation" value="<%=StudentListCtl.OP_RESET%>">

			</div>
			<%
				ArrayList<StudentBean> list = (ArrayList<StudentBean>) request.getAttribute("list");
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
					<th>FIRST NAME:</th>
					<th>LAST NAME:</th>
					<th>COLLEGE:</th>
					<th>DOB:</th>
					<th>PHONE NO:</th>
					<th>EMAIL:</th>
					<th>EDIT:</th>
				</tr>
				<%
					if (list != null) {
							for (StudentBean b : list) {
				%>
				<tr>
					<td align="center" width="130px"><input type="checkbox" class="checkbox" name="ids"
						value="<%=b.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td><%=b.getFirstName()%></td>
					<td><%=b.getLastName()%></td>
					<td><%=b.getCollegeName()%></td>
					<td><%=b.getDob()%></td>
					<td><%=b.getMobileNo()%></td>
					<td><%=b.getEmail()%></td>
					<td><a href="StudentCtl?id=<%=b.getId()%>">Edit</a></td>
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
						value="<%=StudentListCtl.OP_PREVIOUS%>"
						<%=((pageNo == 1)) ? "disabled" : ""%>></td>
					<td><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_NEXT%>"
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
				value="<%=StudentListCtl.OP_BACK%>">
			<%
				}
			%>
		</center>

	</form>

	<%@ include file="footer.jsp"%>


</body>
</html>