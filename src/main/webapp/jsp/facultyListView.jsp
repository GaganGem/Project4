<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.beans.FacultyBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.co.rays.project4.controller.FacultyListCtl"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

</head>
<body>

	<%@ include file="header.jsp"%>
	<jsp:useBean id="bean" class="in.co.rays.project4.beans.FacultyBean"
		scope="request"></jsp:useBean>



	<form action="<%=ORSView.FACULTY_LIST_CTL%>" method="post">

		<%
			List colList = (List) request.getAttribute("CollegeList");

			List courseList = (List) request.getAttribute("CourseList");
		%>

		<center>
<h1>Faculty List</h1>

<b><font color="red" size="5px"> <%=ServletUtility.getErrorMessage(request)%></font>
			<font color="red" size="5px"> <%=ServletUtility.getSuccessMessage(request)%>
			</font></b>
			
			<div style="margin-bottom: 15px; margin-top: 20px">
				<label><b>First Name :</b></label> <input type="text" name="firstName"
					placeholder="Enter First Name"
					value=<%=ServletUtility.getParameter("firstName", request)%>>
				&nbsp; <label><b>College Name :</b></label>
				<%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), colList)%>

				&nbsp; <label><b>Course Name :</b></label>
				<%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), courseList)%>
				&nbsp; <input type="submit" name="operation"
					value="<%=FacultyListCtl.OP_SEARCH%>"> <input type="submit"
					name="operation" value="<%=FacultyListCtl.OP_RESET%>">

			</div>
			
			<%
				ArrayList<FacultyBean> list = (ArrayList<FacultyBean>) request.getAttribute("list");
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				int index = ((pageNo - 1) * pageSize) + 1;
				if(list.size() != 0){
				
			%>

			

			<table width="100%" border="1">
				<tr>
					<th><input type="checkbox" id="select_all" name="select">Select
						All.</th>
					<th>S.No.</th>
					<th>First Name.</th>
					<th>Last Name.</th>
					<th>EmailId.</th>
					<th>Qualification</th>
					<th>College Name.</th>
					<th>Course Name.</th>
					<th>Subject Name.</th>
					<th>DOJ.</th>
					<th>Mobile No.</th>
					<th>Edit</th>
				</tr>
				<%
					if (list != null) {
						for (FacultyBean b : list) {
				%>
				<tr>
					<td align="center" width="100px"><input type="checkbox" class = "checkbox" name="ids" value="<%=b.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td><%=b.getFirstName()%></td>
					<td><%=b.getLastName()%></td>
					<td><%=b.getEmail()%></td>
					<td><%=b.getQualification()%></td>
					<td><%=b.getCollegeName()%></td>
					<td><%=b.getCourseName()%></td>
					<td><%=b.getSubName()%></td>
					<td><%=b.getDoj()%></td>
					<td><%=b.getMobileNo()%></td>
					<td><a href="FacultyCtl?id=<%=b.getId()%>">Edit</a></td>
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
						value="<%=FacultyListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>
					<td><input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_NEXT%>"
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
				value="<%=FacultyListCtl.OP_BACK%>">
			<%
				}
			%>

		</center>

	</form>


	<%@ include file="footer.jsp"%>


</body>
</html>