<%@page import="in.co.rays.project4.controller.MyProfileCtl"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile Page</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1980:2002",
		//maxDate:'0',
		// minDate:0
		//yearRange : "-40:-18"
		});
	});
</script>
</head>
<body>

	<form action="<%=ORSView.MY_PROFILE_CTL%>" method="post">
		<%@ include file="header.jsp"%>

		<center>

			<h1>
				<b>Update Profile</b>
			</h1>

			<h3>
				<font color="green"><b> <%=ServletUtility.getSuccessMessage(request)%>
				</b></font><font color="red"><b> <%=ServletUtility.getErrorMessage(request)%>
				</b></font>
			</h3>

			<jsp:useBean id="bean" class="in.co.rays.project4.beans.UserBean" scope="request"></jsp:useBean>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=bean.getModifiedDatetime()%>">

			<table>


				<tr>
					<th align="left">Login Id<span style="color: red">*</span></th>
					<td><input type="text" name="login" placeholder="Enter Login Id"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>

				</tr>
				<tr>
					<th align="left">First Name <span style="color: red">*</span></th>
					<td><input type="text" name="fName" placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("fName", request) %>
 			</font></td>
				</tr>
				<tr>
					<th align="left">Last Name<span style="color: red">*</span>
					</th>
					<td><input type="text" name="lName" placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("lName", request) %>
 				</font></td>
				</tr>
				<tr>
					<th align="left">Gender <span style="color: red">*</span>
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Male", "Male");
							map.put("Female", "Female");
							map.put("Other", "Other");

							String htmlList = HTMLUtility.getList("gen", String.valueOf(bean.getGender()), map);
						%><%=htmlList%></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("gen", request) %>
 			</font></td>
				</tr>
				<tr>
					<th align="left">Mobile No <span style="color: red">*</span>
					</th>
					<td><input type="text" name="phone" placeholder="Enter Mobile No"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("phone", request) %>
 			</font></td>
				</tr>
				<tr>
					<th align="left">Date Of Birth <span style="color: red">*</span>
					</th>
					<td><input type="text" name="dob" id="datepicker" readonly="readonly" placeholder="Enter dob"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request) %>
 					</font></td>
				</tr>

				<tr>
					<td></td>
					<td><input type="submit" name="operation"
						value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD%>"><input
						type="submit" name="operation" value="<%=MyProfileCtl.OP_SAVE%>"></td>
				</tr>
			</table>
		</center>
	</form>
	<%@ include file="footer.jsp"%>

</body>
</html>