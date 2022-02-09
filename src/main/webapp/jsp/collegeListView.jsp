<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.controller.CollegeListCtl"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="in.co.rays.project4.beans.CollegeBean"%>
<%@page import="in.co.rays.project4.controller.ORSView"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>
	<%@ include file="header.jsp"%>

	<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="post">

<jsp:useBean id="bean" class="in.co.rays.project4.beans.CollegeBean" scope="request"></jsp:useBean>
		<center>

			<h1>College List</h1>
			
					<b><font color="red" size="5px"> <%=ServletUtility.getErrorMessage(request)%></font>
			<font color="red" size="5px"> <%=ServletUtility.getSuccessMessage(request)%>
			</font></b>
			
			<div style="margin-bottom: 15px; margin-top: 20px">
				<label for="name"><b>Name: </b></label><input type="text" placeholder="Enter College Name"
				value="<%=DataUtility.getString(bean.getName())%>" name="name">
				<label for="city" style="margin-left: 20px"><b>City: </b></label><input
					type="text" value="<%=DataUtility.getString(bean.getCity())%>" name="city" placeholder="Enter College City"> <input type="submit"
					value="<%=CollegeListCtl.OP_SEARCH %>" style="margin-left: 40px"><input type="submit"
					value="<%=CollegeListCtl.OP_RESET%>" style="margin-left: 40px">
			</div>
		
			<%
				ArrayList<CollegeBean> list = (ArrayList<CollegeBean>) request.getAttribute("list");
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				
				if (list.size() != 0) {
			%>

		

			<table width="100%" border="1">
				<tr>
					<th><input type="checkbox" id="select_all" name="select">Select
						All.</th>
					<th>S No:</th>
					<th>NAME:</th>
					<th>ADDRESS:</th>
					<th>STATE:</th>
					<th>CITY:</th>
					<th>PHONE NO:</th>
					<th>EDIT:</th>
				</tr>
				<%
					for (CollegeBean b : list) {
				%>
				<tr>
					<td align="center" width="130px"><input type="checkbox" class ="checkbox" name="ids" value="<%=b.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td><%=b.getName()%></td>
					<td><%=b.getAddress()%></td>
					<td><%=b.getState()%></td>
					<td><%=b.getCity()%></td>
					<td><%=b.getPhoneNo()%></td>
					<td><a href="CollegeCtl?id=<%=b.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>

			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=CollegeListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 5px;" value="<%=CollegeListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 5px;" value="<%=CollegeListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						style="padding: 5px;" value="<%=CollegeListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>

			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<br>
			<input type="submit" name="operation"
				value="<%=CollegeListCtl.OP_BACK%>">
			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

		</center>

	</form>


	<%@ include file="footer.jsp"%>

</body>
</html>