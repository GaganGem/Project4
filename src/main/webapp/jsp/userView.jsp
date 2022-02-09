<%@page import="in.co.rays.project4.controller.UserCtl"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Add</title>
</head>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "1980:2002",
		//maxDate:'0',
		// minDate:0
		//yearRange : "-40:-18"
		});
	});
</script>

<body>

	<%@ include file="header.jsp"%>

	<form action="<%=ORSView.USER_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.project4.beans.UserBean"
			scope="request"></jsp:useBean>

		<%
			List l = (List) request.getAttribute("roleList");
		%>


		<center>

			<h1>
				<b> <%
 	if (bean.getId() > 0) {
 %> <%="Update User"%> <%
 	} else {
 %> <%="Add User"%> <%
 	}
 %>
				</b>
			</h1>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=bean.getModifiedDatetime()%>">


			<h3>
				<font color="green"><b> <%=ServletUtility.getSuccessMessage(request)%>
				</b></font><font color="red"><b> <%=ServletUtility.getErrorMessage(request)%>
				</b></font>
			</h3>

			<table>

				<tr>
					<th align="left">First Name<span style="color: red">*</span></th>
					
					<td><input type="text" name="firstName" placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%>
 			</font></td>
				</tr>
				<tr>
					<th align="left">Last Name<span style="color: red">*</span></th>
					
					<td><input type="text" name="lastName" placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%>
 				</font></td>
				</tr>
				<tr>
					<th align="left">Login Id<span style="color: red">*</span></th>
					
					<td><input type="text" name="login" placeholder="Enter Login Id"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%>
 			</font></td>
				</tr>
				
					<%
					if (bean != null && bean.getId() > 0) {
				%>
				<%
					} else {
				%>
				<tr>
					<th align="left">Password<span style="color: red">*</span></th>
					
					<td><input type="password" name="password" placeholder="Enter Password"
						value="<%=DataUtility.getStringData(bean.getPass())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%>
 				</font></td>
				</tr>
				<tr>
					<th align="left">Confirm Password<span style="color: red">*</span></th>
					
					<td><input type="password" name="confirmPassword" placeholder="Confirm Password"
						value="<%=DataUtility.getStringData(bean.getPass())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%>
 				</font></td>
				</tr>
				<%
					}
				%>
				<tr>
					<th align="left">Gender<span style="color: red">*</span></th>
					
					<td>
						<%
							HashMap map = new HashMap();
							/* map.put("", "Select"); */
							map.put("Male", "Male");
							map.put("Female", "Female");

							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%><%=htmlList%></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%>
 				</font></td>
				</tr>
				<tr>
					<th align="left">Dob<span style="color: red">*</span></th>
					
					<td><input type="text" name="dob" id="datepicker" placeholder="Enter Your Date of Birth"
						value="<%=DataUtility.getDateString(bean.getDob())%>"
						readonly="readonly"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%>
 				</font></td>
				</tr>

				<tr>
					<th align="left">Mobile No<span style="color: red">*</span>
					</th>
					<td><input type="text" name="MobileNo"
						placeholder="Enter Mobile No"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("MobileNo", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Role<span style="color: red">*</span>:
					</th>
					<td><%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), l)%>

						&nbsp;<font style="position: fixed;" color="red"> <%=ServletUtility.getErrorMessage("roleId", request)%></font>
					</td>
				</tr>

				<tr>
					<td></td>
					<%
						if (bean.getId() > 0) {
					%>
					<td><input type="submit" name="operation"
						value="<%=UserCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=UserCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=UserCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=UserCtl.OP_RESET%>"></td>

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