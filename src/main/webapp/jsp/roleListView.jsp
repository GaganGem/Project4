<%@page import="in.co.rays.project4.controller.RoleListCtl"%>
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
<title>Role List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>

	<form action="<%=ORSView.ROLE_LIST_CTL%>" method="post">
		<%@ include file="header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.project4.beans.RoleBean" scope="request"></jsp:useBean>

		<%
			List rlist = (List) request.getAttribute("roleList");
		
		%>
		<center>

			<h1>Role List</h1>
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>


			<div style="margin-bottom: 15px; margin-top: 20px">
				<label for="name"><b>Name: </b></label> <input type="text" name="name"
					placeholder="Enter Role"
					value="<%=ServletUtility.getParameter("name", request)%>">
				<label for="roleid"><b>Role: </b></label>
				<%=HTMLUtility.getList("roleid", String.valueOf(bean.getId()), rlist)%>

				<input type="submit" name="operation"
					value="<%=RoleListCtl.OP_SEARCH%>"> <input type="submit"
					name="operation" value="<%=RoleListCtl.OP_RESET%>">
			</div>
			<%
				ArrayList<RoleBean> list = (ArrayList<RoleBean>) request.getAttribute("list");
				 		int pageNo = (Integer) request.getAttribute("pageNo");
						int pageSize = (Integer) request.getAttribute("pageSize");
						int index = ((pageNo - 1) * pageSize) + 1;
						if (list.size() != 0) {
						
			%>

			<table width="100%" border="1">
				<tr>
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>
					<th>S No:</th>
					<th>NAME:</th>
					<th>DESCRIPTION:</th>
					<th>Edit:</th>
				</tr>
				<%
					if (list != null) {
						for (RoleBean b : list) {
				%>
				<tr>
					<td align="center" width="130px"><input type="checkbox" class ="checkbox" name="ids" value="<%=b.getId()%>"
					<%if (b.getId() == RoleBean.ADMIN) {%> 
					<%="disabled"%>
					<%}%>></td>
					<td align="center"><%=index++%></td>
					<td><%=b.getName()%></td>
					<td><%=b.getDescription()%></td>
					<td><a href="RoleCtl?id=<%=b.getId()%>"<%if (b.getId() == RoleBean.ADMIN) {%> onclick="return false;"<%}%>>Edit</a></td>
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
						value="<%=RoleListCtl.OP_PREVIOUS%>" <%=(pageNo==1)?"disabled":"" %>></td>
					<td><input type="submit" name="operation"
						value="<%=RoleListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=RoleListCtl.OP_NEW%>"></td>
						<%if(list!=null){ %>
					<td align="right"><input type="submit" name="operation"
						value="<%=RoleListCtl.OP_NEXT%>" <%=((list.size()<pageSize))?"disabled":"" %>></td>
				<%}else{ %>
				<td align="right"><input type="submit" name="operation"
						value="<%=RoleListCtl.OP_NEXT%>" disabled="disabled"></td>
				<%}%>
				</tr>
			</table>
			 	<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
 
 <%
				}
				if (list.size() == 0) {
			%>
			<input type="submit" name="operation"
				value="<%=RoleListCtl.OP_BACK%>">
			<%
				}
			%>
		</center>

	</form>

	<%@ include file="footer.jsp"%>

</body>
</html>