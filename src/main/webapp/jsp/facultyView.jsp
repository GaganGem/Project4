<%@page import="in.co.rays.project4.beans.SubjectBean"%>
<%@page import="in.co.rays.project4.beans.CourseBean"%>
<%@page import="in.co.rays.project4.beans.CollegeBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.rays.project4.controller.FacultyCtl"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty Registration Page</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2020',
			dateFormat : 'dd-mm-yy'
		});
	});
</script>
</head>
<body>

	<form action="<%=ORSView.FACULTY_CTL%>" method="post">
		<%@ include file="header.jsp"%>

		<%
			List<CollegeBean> colist = (List<CollegeBean>) request.getAttribute("CollegeList");
			List<CourseBean> clist = (List<CourseBean>) request.getAttribute("CourseList");
			List<SubjectBean> slist = (List<SubjectBean>) request.getAttribute("SubjectList");
		%>

		<jsp:useBean id="bean" class="in.co.rays.project4.beans.FacultyBean"
			scope="request"></jsp:useBean>
		<center>


			<h1>
				<b> <%
 	if (bean.getId() > 0) {
 %> <%="Update Faculty"%> <%
 	} else {
 %> <%="Add Faculty"%> <%
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
					<th align="left">First Name<font color="red">*</font></th>
					<td><input type="text" name="fName" placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("fName", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">Last Name<font color="red">*</font></th>
					<td><input type="text" name="lName" placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("lName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Gender<font color="red">*</font></th>
					<td>
						<%
							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
							/* map.put("", "Select"); */
							map.put("Male", "Male");
							map.put("Female", "Female");
							map.put("Other", "Other");
							String htmlList = HTMLUtility.getList("gen", bean.getGender(), map);
						%><%=htmlList%></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("gen", request)%>
					</font></td>
				</tr>
				
				<tr>
					<th align="left">Email<font color="red">*</font></th>
					<td><input type="text" name="email" placeholder="Enter Email Id"
						value="<%=DataUtility.getStringData(bean.getEmail())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%>
					</font></td>
				</tr>
				
				<tr>
					<th align="left">Mobile No<font color="red">*</font></th>
					<td><input type="text" name="phone" placeholder="Enter Mobile No"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("phone", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">College Name<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("collegeName", String.valueOf(bean.getCollegeId()), colist)%></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("collegeName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Course Name<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("coName", String.valueOf(bean.getCourseId()), clist)%></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("coName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Subject Name<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("subName", String.valueOf(bean.getSubId()), slist)%></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("subName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Date of Joining<font color="red">*</font></th>
					<td><input type="text" name="doj" id="datepicker"
						placeholder="Enter Date of Joining" readonly="readonly"
						value="<%=DataUtility.getStringData(bean.getDoj())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("doj", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Qualification<font color="red">*</font></th>
					<td><input type="text" name="qua" placeholder="Enter Qualification"
						value="<%=DataUtility.getStringData(bean.getQualification())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("qua", request)%>
					</font></td>
				</tr>

				<tr>
					<td></td>
					<%
						if (bean.getId() > 0) {
					%>

					<td>&nbsp; &emsp; <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_UPDATE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=FacultyCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td>&nbsp; &emsp; <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_SAVE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=FacultyCtl.OP_RESET%>"></td>
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