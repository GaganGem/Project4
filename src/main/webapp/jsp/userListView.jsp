<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.models.RoleModel"%>
<%@page import="in.co.rays.project4.controller.UserListCtl"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>
	<%@ include file="header.jsp"%>
	<jsp:useBean id="bean" class="in.co.rays.project4.beans.UserBean"
		scope="request"></jsp:useBean>


	<%
		List rl = (List) request.getAttribute("Rolem");
	%>

	<form action="<%=ORSView.USER_LIST_CTL%>" method="post">
		<div align="center">
			<h1>User List</h1>
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>

		</div>
		<center>

			<table>
				<tr>
					<th></th>
					<td align="center"><label><b>FirstName : </b></label> <input
						type="text" name="firstName" placeholder="Enter First Name"
						value="<%=DataUtility.getString(bean.getFirstName())%>">

						<label><b> Role : </b></label> <%=HTMLUtility.getList("roleid", String.valueOf(bean.getRoleId()), rl)%>

						<label><b>LoginId : </b></label> <input type="text" name="login"
						placeholder="Enter Login-Id"
						value="<%=DataUtility.getString(bean.getLogin())%>">

						<input type="submit" name="operation"
						value="<%=UserListCtl.OP_SEARCH%>"><input type="submit"
						name="operation" value="<%=UserListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<%
				ArrayList<UserBean> list = (ArrayList<UserBean>) request.getAttribute("list");
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				if (list.size() != 0) {
			%>

			<table width="100%" border="1">
				<tr>
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>Role</th>
					<th>LoginId</th>
					<th>Gender</th>
					<th>Date Of Birth</th>
					<th>Mobile No</th>
					<th>Edit</th>
				</tr>
				<%
					if (list != null) {
							for (UserBean b : list) {
								RoleBean role = RoleModel.findByPK(b.getRoleId());
				%>
				<tr>
					<td align="center" width="130px"><input type="checkbox" class="checkbox" name="ids"
						value="<%=b.getId()%>"
						<%if (b.getRoleId() == RoleBean.ADMIN) {%> <%="disabled"%>
						<%}%>></td>
					<td align="center"><%=index++%></td>
					<td><%=b.getFirstName()%></td>
					<td><%=b.getLastName()%></td>
					<td><%=role.getName()%></td>
					<td><%=b.getLogin()%></td>
					<td><%=b.getGender()%></td>
					<td><%=b.getDob()%></td>
					<td><%=b.getMobileNo()%></td>
					<td><a href="UserCtl?id=<%=b.getId()%>"
						<%if (b.getRoleId() == RoleBean.ADMIN) {%>
						onclick="return false;" <%}%>>Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>
			<%
				}
			%>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_PREVIOUS%>"
						<%=((pageNo == 1)) ? "disabled" : ""%>></td>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEXT%>"
						<%=(nextPageSize == 0) ? "disabled" : ""%>></td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<%
				}
				if (list.size() == 0) {
			%>
			<input type="submit" name="operation"
				value="<%=UserListCtl.OP_BACK%>">
			<%
				}
			%>

		</center>

	</form>


	<%@ include file="footer.jsp"%>


</body>
</html>