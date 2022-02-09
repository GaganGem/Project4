<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.controller.SubjectCtl"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="in.co.rays.project4.beans.CourseBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Subject Registration Page</title>
</head>
<body>

	<jsp:useBean id="bean" class="in.co.rays.project4.beans.SubjectBean"
		scope="request"></jsp:useBean>
	<form action="<%=ORSView.SUBJECT_CTL%>" method="post">


		<%@include file="header.jsp"%>


		<%
			ArrayList<CourseBean> l = (ArrayList<CourseBean>) request.getAttribute("Courselist");
		%>
		<center>
			<h1>
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				<tr>
					<th>Update Subject</th>
				</tr>
				<%
					} else {
				%>
				<tr>
					<th>Add Subject</th>
				</tr>
				<%
					}
				%>
			</h1>
			<div>
				<h3>
					<font style="color: green"><%=ServletUtility.getSuccessMessage(request)%>
					</font> <font style="color: red"><%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifieddatetime"
				value="<%=bean.getModifiedDatetime()%>">

			<table>

				<tr>
					<th align="left">Course Name <font color="red">*</font>:
					</th>
					<td><%=HTMLUtility.getList("coursename", String.valueOf(bean.getCourseId()), l)%>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("coursename", request)%>
					</font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Subject Name<font color="red">*</font> :
					</th>
					<td><input type="text" name="name" 
						placeholder="Enter Subject Name" size="20"
						value="<%=DataUtility.getStringData(bean.getSubName())%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%>
					</font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Description<font color="red">*</font> :
					</th>
					<td><input type="text" name="description"
						placeholder="Enter Description" size="20"
						value="<%=DataUtility.getStringData(bean.getDescription())%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("description", request)%>
					</font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td>&nbsp; &emsp; <input type="submit" name="operation"
						value="<%=SubjectCtl.OP_UPDATE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=SubjectCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>

					<td>&nbsp; &emsp; <input type="submit" name="operation"
						value="<%=SubjectCtl.OP_SAVE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=SubjectCtl.OP_RESET%>">
					</td>
					<%
						}
					%>

				</tr>
			</table>
	</form>
	</center>

	<%@include file="footer.jsp"%>

</body>
</html>