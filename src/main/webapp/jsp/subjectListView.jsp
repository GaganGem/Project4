<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.beans.CourseBean"%>
<%@page import="in.co.rays.project4.models.CourseModel"%>
<%@page import="in.co.rays.project4.beans.SubjectBean"%>
<%@page import="in.co.rays.project4.controller.SubjectListCtl"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Subject List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>
	<jsp:useBean id="bean" class="in.co.rays.project4.beans.SubjectBean"
		scope="request"></jsp:useBean>
	<jsp:useBean id="cobean" class="in.co.rays.project4.beans.CourseBean"
		scope="request"></jsp:useBean>


	<form action="<%=ORSView.SUBJECT_LIST_CTL%>" method="post">
		<%@ include file="header.jsp"%>

		<%
			List clist = (List) request.getAttribute("courseList");
		%>

		<center>
			<h1>Subject List</h1>

	<h3>
				<font color="green"><b> <%=ServletUtility.getSuccessMessage(request)%>
				</b></font><font color="red"><b> <%=ServletUtility.getErrorMessage(request)%>
				</b></font>
			</h3>
			
			<div style="margin-bottom: 15px; margin-top: 20px">
				<label><b>Subject Name :</b></label> <input type="text" name="name"
					placeholder="Enter Subject Name"
					value="<%=ServletUtility.getParameter("name", request)%>">
				<label><b>CourseName :</b></label>
				<%=HTMLUtility.getList("coursename", String.valueOf(bean.getCourseId()), clist)%>
				<input type="submit" name="operation"
					value="<%=SubjectListCtl.OP_SEARCH%>"><input type="submit"
					name="operation" value="<%=SubjectListCtl.OP_RESET%>">
			</div>
			
			<%
				ArrayList<SubjectBean> list = (ArrayList<SubjectBean>) request.getAttribute("list");
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				if(list.size() != 0){
			%>

			<table width="100%" border="1">
				<tr>
					<th><input type="checkbox" id="select_all" name="select">Select
						All.</th>
					<th>S No:</th>
					<th>Subject Name:</th>
					<th>Description:</th>
					<th>Course Name:</th>
					<th>EDIT:</th>
				</tr>
				<%
					if (list != null) {
						for (SubjectBean b : list) {
							CourseBean courseBean =  CourseModel.findByPK(b.getCourseId());
				%>
				<tr>
					<td align="center" width="130px"><input type="checkbox" class ="checkbox" name="ids" value="<%=b.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td><%=b.getSubName()%></td>
					<td><%=b.getDescription()%></td>
					<td><%=courseBean.getCourseName()%></td>
					<td><a href="SubjectCtl?id=<%=b.getId()%>">Edit</a></td>
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
						value="<%=SubjectListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>
					<td><input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_NEXT%>"
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
				value="<%=SubjectListCtl.OP_BACK%>">
			<%
				}
			%>
		</center>

	</form>

	<%@ include file="footer.jsp"%>



</body>
</html>