<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.controller.UserRegistrationCtl"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration Page</title>
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	function phoneno() {
		$('#phone').keypress(function(e) {
			var a = [];
			var k = e.which;

			for (i = 48; i < 58; i++)
				a.push(i);

			if (!(a.indexOf(k) >= 0))
				e.preventDefault();
		});
	}

	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1970:2030',
			dateFormat : 'dd/mm/yy',
			endDate : '-18y',
				maxDate:0
		});
	});
</script>
</head>
<body>

	<%@ include file="header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.project4.beans.UserBean"
		scope="request"></jsp:useBean>


	<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">

		<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
			type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy"
			value="<%=bean.getModifiedBy()%>"> <input type="hidden"
			name="createdDatetime" value="<%=bean.getCreatedDatetime()%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=bean.getModifiedDatetime()%>">

		<center>


			<h1>
				<b>User Registration</b>
			</h1>

			<h3>
				<font color="green"><b> <%=ServletUtility.getSuccessMessage(request)%>
				</b></font><font color="red"><b> <%=ServletUtility.getErrorMessage(request)%>
				</b></font>
			</h3>

			<table>

				<tr>
					<th align="left">First Name*</th>
					<td><input type="text" name="firstName" placeholder="Please Enter First Name" 
					value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%>
 				</font></td>
				</tr>
				<tr>
					<th align="left">Last Name*</th>
					<td><input type="text" name="lastName" placeholder="Please Enter Last Name"
					value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%>
 					</font></td>
				</tr>
				<tr>
					<th align="left">Login Id*</th>
					<td><input type="text" name="login" placeholder="Must Be an Email Id"
					value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%>
 				</font></td>
				</tr>
				 <tr>
					<th align="left">MobileNo<span style="color:red;">*</span></th>
					<td><input type="text" name="mobile"  maxlength="10" placeholder="Please Enter mobile no"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
						<td style="position: fixed;"><font
						color="red"> <%=ServletUtility.getErrorMessage("mobile", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Password*</th>
					<td><input type="password" name="password" placeholder="must be a password"
					value="<%=DataUtility.getStringData(bean.getPass())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%>
 			</font></td>
				</tr>
				<tr>
					<th align="left">Confirm Password*</th>
					<td><input type="password" name="confirmPassword" placeholder="Must Be a Password"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%>
 				</font></td>
				</tr>
				<tr>
					<th align="left">Gender*</th>
					<td>
						<%
							HashMap map = new HashMap();
							/* map.put("", "Select"); */
							map.put("Male", "Male");
							map.put("Female", "Female");
							map.put("Other", "Other");

							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%> <%=htmlList%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%>
 				</font></td>
				</tr>
				<tr>
					<th align="left">Dob*</th>
					<td><input type="text" id="datepicker" readonly="readonly" name="dob" placeholder="must be a valid dob"
					value="<%=DataUtility.getStringData(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%>
 			</font></td>
				</tr>

				<tr>
					<td></td>
					<td><input type="submit" name="operation"
						value="<%=UserRegistrationCtl.OP_SIGN_UP%>"> <input
						type="submit" name="operation"
						value="<%=UserRegistrationCtl.OP_RESET%>"></td>
				</tr>
			</table>
		</center>
	</form>

	<%@ include file="footer.jsp"%>


</body>
</html>