<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.controller.TimeTableListCtl"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.beans.TimetableBean"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TimeTable List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>

	<jsp:useBean id="bean" class="in.co.rays.project4.beans.TimetableBean"
		scope="request"></jsp:useBean>
	<%@include file="header.jsp"%>


	<form action="<%=ORSView.TIMETABLE_LIST_CTL%>" method="post">

		<center>

			<div align="center">
				<h1>TimeTable List</h1>

				<%
					List cList = (List) request.getAttribute("courseList");

					List sList = (List) request.getAttribute("subjectList");
				%>

				<h3>
					<font style="font: bold; color: red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font style="font: bold; color: green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
			</div>

			<table width="100%">
				<tr>
					<td align="center"><label><b>Course Name :</b></label> <%=HTMLUtility.getList("clist", String.valueOf(bean.getCourseId()), cList)%>

						<label><b>Subject Name :</b></label> <%=HTMLUtility.getList("slist", String.valueOf(bean.getSubId()), sList)%>

						<input type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int next=DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List list = ServletUtility.getList(request);
				Iterator<TimetableBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<table width="100%" border="1">
				<tr>

					<th width="5%"><input type="checkbox" id="select_all"
						name="Select">Select All.</th>
					<th>S.No.</th>
					<th>Course Name.</th>
					<th>Subject Name.</th>
					<th>Semester.</th>
					<th>ExamDate.</th>
					<th>ExamTime.</th>
					<th>Edit</th>

				</tr>
				<%
					while (it.hasNext()) {
							bean = it.next();
				%>
				<tr align="center">
					<td align="center" width="130px"><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td><%=bean.getCourseName()%></td>
					<td><%=bean.getSubName()%></td>
					<td><%=bean.getSem()%></td>
					<td><%=bean.getExamDate()%></td>
					<td><%=bean.getExamTime()%></td>
					<td><a href="TimetableCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>

			<table width="100%">
				<tr>
					<th></th>
					<td align="left"><input type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_PREVIOUS%>"
						<%=((pageNo == 1)) ? "disabled" : ""%>></td>
					<td><input type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_NEW%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_NEXT%>"
						<%=(next==0) ? "disabled" : ""%>></td>
				</tr>
			</table>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<%
				}
				if (list.size() == 0) {
			%>
			<input type="submit" name="operation"
				value="<%=TimeTableListCtl.OP_BACK%>">
			<%
				}
			%>
		</center>
	</form>
	<br>
	<br>


	<%@include file="footer.jsp"%>

</body>
</html>