<%@page import="in.co.rays.project4.controller.StudentCtl"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="in.co.rays.project4.beans.CollegeBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Registration page</title>
 
  <script>
  $( function() {
    $( "#datepicker" ).datepicker({
      changeMonth: true,
      changeYear: true,
	  yearRange:'1980:2020',
	  dateFormat:'dd-mm-yy'
    });
  } );
  </script>
</head>
<body>
	<%@ include file="header.jsp"%>

	<%
		List<CollegeBean> clist = (List<CollegeBean>) request.getAttribute("collegeList");
	%>

	<form action="<%=ORSView.STUDENT_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.project4.beans.StudentBean"
			scope="request"></jsp:useBean>

		<center>

			<h1>
				<b> <%
 	if (bean.getId() > 0) {
 %> <%="Update Student"%> <%
 	} else {
 %> <%="Add Student"%> <%
 	}
 %>
				</b>
			</h1>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifieddatetime"
				value="<%=bean.getModifiedDatetime()%>">

			<h3>
				<font color="green"><b> <%=ServletUtility.getSuccessMessage(request)%>
				</b></font><font color="red"><b> <%=ServletUtility.getErrorMessage(request)%>
				</b></font>
			</h3>

			<table>

				<tr>
					<th align="Left">CollegeName<font color="red">*</font>
					</th>
					<td><%=HTMLUtility.getList("collegename", String.valueOf(bean.getCollegeId()), clist)%>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("collegename", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="Left">First Name<font color="red">*</font></th>
					<td><input type="text" name="fName" placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("fName", request)%>
 				</font></td>
				</tr>
				<tr>
					<th align="Left">Last Name<font color="red">*</font></th>
					<td><input type="text" name="lName" placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("lName", request)%>
 			</font></td>
				</tr>
				<tr>
					<th align="Left">Mobile<font color="red">*</font></th>
					<td><input type="text" name="phone" placeholder="Enter Mobile No"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("phone", request)%>
 				</font></td>
				</tr>
				<tr>
					<th align="Left">Email Id<font color="red">*</font></th>
					<td><input type="text" name="email" placeholder="Enter Login Id"
						value="<%=DataUtility.getStringData(bean.getEmail())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%>
 				</font></td>
				</tr>
				<tr>
					<th align="Left">Dob<font color="red">*</font></th>
					<td><input type="text" name="dob" id="datepicker" readonly="readonly"
						placeholder="Enter Your Date of Birth" value="<%=DataUtility.getStringData(bean.getDob())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%>
 				</font></td>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>

					<td><input type="submit" name="operation"
						value="<%=StudentCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=StudentCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td>&nbsp; &nbsp;<input type="submit" name="operation"
						value="<%=StudentCtl.OP_SAVE%>"> &nbsp; &nbsp;<input
						type="submit" name="operation" value="<%=StudentCtl.OP_RESET%>">
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