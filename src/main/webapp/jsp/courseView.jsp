<%@page import="in.co.rays.project4.controller.CourseCtl"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course Registration Page</title>
</head>
<body>

	<form action="<%=ORSView.COURSE_CTL %>" method="post">
		<%@ include file="header.jsp"%>


		<jsp:useBean id="bean" class="in.co.rays.project4.beans.CourseBean" scope="request"></jsp:useBean>

		<center>

			<h1>
				<b> <%
 	if (bean.getId() > 0) {
 %> <%="Update Course"%> <%
 	} else {
 %> <%="Add Course"%> <%
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
				type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=bean.getModifiedDatetime()%>">


			<table>

				<tr>
					<td align="left">Course Name<font color="red">*</font></td>
					<td><input type="text" name="name" placeholder="Enter Course Name"
						value="<%=DataUtility.getStringData(bean.getCourseName())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%>
					</font></td>
				</tr>
				<tr>
					<td align="left">Description<font color="red">*</font></td>
					<td><input type="text" name="desc" placeholder="Enter Course Desc"
						value="<%=DataUtility.getStringData(bean.getDescription())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("desc", request)%>
					</font></td>
				</tr>
				<tr>
					<td align="left">Duration<font color="red">*</font></td>
					<td>
						<%
							LinkedHashMap map = new LinkedHashMap();
							//HashMap map = new HashMap();
							/* map.put("", "Select"); */
							map.put("1 Year", "1 Year");
							map.put("2 Year", "2 Year");
							map.put("3 Year", "3 Year");
							map.put("4 Year", "4 Year");
							map.put("5 Year", "5 Year");
							map.put("6 Year", "6 Year");

							String htmlList = HTMLUtility.getList("duration", bean.getDuration(), map);
						%> <%=htmlList%></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("duration", request)%>
					</font></td>
				</tr>
				<tr>
					<td></td>
					<%
						if (bean.getId() > 0) {
					%>
					<td><input type="submit" name="operation"
						value="<%=CourseCtl.OP_UPDATE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=CourseCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=CourseCtl.OP_SAVE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=CourseCtl.OP_RESET%>">
					</td>
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