<%@page import="in.co.rays.project4.controller.CollegeCtl"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College Registration</title>
</head>
<body>
<%@ include file="header.jsp"%>

	<form action="<%=ORSView.COLLEGE_CTL%>" method="post">
		
		<jsp:useBean id="bean" class="in.co.rays.project4.beans.CollegeBean" scope="request"></jsp:useBean>

		<center>

			<h1>
				<b> <%
 	if (bean.getId() > 0) {
 %> <%="Update College"%> <%
 	} else {
 %> <%="Add College"%> <%
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
					<th align="left">Name<font color="red">*</font></th>
					<td><input type="text" name="name" placeholder="Enter College Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">Address<font color="red">*</font></th>
					<td><input type="text" name="add" placeholder="Enter College Address"
						value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("add", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">City<font color="red">*</font></th>
					<td><input type="text" name="city" placeholder="Enter College City"
						value="<%=DataUtility.getStringData(bean.getCity())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("city", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">State<font color="red">*</font></th>
					<td><input type="text" name="state" placeholder="Enter College State"
						value="<%=DataUtility.getStringData(bean.getState())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("state", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">Phone No<font color="red">*</font></th>
					<td><input type="text" name="phone" placeholder="Enter College Phone No"
						value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("phone", request)%>
					</font></td>
				</tr>

				<tr>
					<td></td>
					<td>
						<%
							if (bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=CollegeCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=CollegeCtl.OP_CANCEL%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=CollegeCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=CollegeCtl.OP_RESET%>"> <%
 	}
 %>
					</td>
				</tr>
			</table>
		</center>
	</form>

	<%@ include file="footer.jsp"%>

</body>
</html>