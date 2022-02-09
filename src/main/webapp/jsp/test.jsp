<%@page import="in.co.rays.project4.controller.ORSView"%>
<%@page import="in.co.rays.project4.controller.UserCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<head>
<title>User Registration Page</title>
</head>
<html>
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
	<jsp:useBean id="bean" class="in.co.rays.project4.beans.UserBean"
		scope="request"></jsp:useBean>
	<%@ include file="header.jsp"%>
	<center>
		<form action="<%=ORSView.USER_CTL%>" method="post">



			<%
				List l = (List) request.getAttribute("roleList");
			%>
			<div align="center">

				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font>Update User</font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font>Add User</font></th>
					</tr>
					<%
						}
					%>
				</h1>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
				</H2>

				<H2>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H2>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=bean.getModifiedDatetime()%>">


			<table>
				<tr>
					<th align="left">First Name <span style="color: red">*</span>:
					</th>
					<td><input type="text" name="firstName"
						placeholder="Enter FirstName"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Last Name <span style="color: red">*</span>:
					</th>
					<td><input type="text" name="lastName"
						placeholder="Enter LastName"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">LoginId <span style="color: red">*</span>:
					</th>
					<td><input type="text" name="login"
						placeholder="Enter LoginId"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Password<span style="color: red">*</span>:
					</th>
					<td><input type="password" name="password"
						placeholder="Enter Password"
						value="<%=DataUtility.getStringData(bean.getPass())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>

				<!--                 <tr> -->
				<!--                     <th>Confirm Password*</th> -->
				<!--                     <td><input type="password" name="confirmPassword" -->
				<%--                         value="<%=DataUtility.getStringData(bean.getPassword())%>"><font --%>
				<%--                         color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", --%>
				<%--                     request)%></font></td> --%>
				<!--                 </tr> -->
				<tr>
					<th align="left">Gender <span style="color: red">*</span>:
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Male", "Male");
							map.put("Female", "Female");

							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%><%=htmlList%> &nbsp;<font style="position: fixed;" color="red">
							<%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">Mobile No<span style="color: red">*</span>:
					</th>
					<td><input type="text" name="MobileNo"
						placeholder="Enter MobileNno"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("MobileNo", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Role<span style="color: red">*</span>:
					</th>
					<td></td>
				</tr>





				<tr>
					<th align="left">Date Of Birth<font color="red">*</font></th>
					<td><input type="text" name="dob" id="datepicker"
						placeholder="Enter Date Of Birth" readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getDob())%>">
						&nbsp;<font style="position: fixed;" color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=UserCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=UserCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=UserCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=UserCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>
			</table>
		</form>
	</center>
	<%@ include file="footer.jsp"%>
</body>
</html>
