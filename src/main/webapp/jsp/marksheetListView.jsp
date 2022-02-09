<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.beans.StudentBean"%>
<%@page import="in.co.rays.project4.models.StudentModel"%>
<%@page import="in.co.rays.project4.controller.MarksheetListCtl"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="in.co.rays.project4.beans.MarksheetBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>

	<%@ include file="header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.project4.beans.MarksheetBean" scope="request"></jsp:useBean>
	<center>

		<h1>Marksheet List</h1>
		
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>

		<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="post">
			<div style="margin-bottom: 15px; margin-top: 20px">
				<label for="name"><b>Name: </b></label><input type="text" placeholder="Enter Name" 
				value="<%=DataUtility.getString(bean.getName())%>" name="name">
				<label for="rollNo" style="margin-left: 20px"><b>Roll No: </b></label><input
					type="text" value="<%=DataUtility.getString(bean.getRollNo())%>" placeholder="Enter Name" name="rollNo"> <input type="submit" 
					value="Search" style="margin-left: 40px">
			</div>
			
			
			<%
				ArrayList<MarksheetBean> list = (ArrayList<MarksheetBean>) request.getAttribute("list");
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
					<th>ROLL NO:</th>
					<th>NAME:</th>
					<th>PHYSICS:</th>
					<th>CHEMISTRY:</th>
					<th>MATHS:</th>
					<th>Total</th>
					<th>Percentage</th>
					<th>Result</th>
					<th>EDIT:</th>
				</tr>
				<%
					if (list != null) {
							for (MarksheetBean b : list) {
								StudentBean stuBean = StudentModel.findByPK(b.getStudentId());

								int phy = DataUtility.getInt(DataUtility.getStringData(b.getPhysics()));
								int chem = DataUtility.getInt(DataUtility.getStringData(b.getChemistry()));
								int math = DataUtility.getInt(DataUtility.getStringData(b.getMaths()));
								int total = (phy + chem + math);
								float perc = total / 3;
				%>
				<tr>
					<td align="center" width="130px"><input type="checkbox" class="checkbox" name="ids"
						value="<%=b.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td><%=b.getRollNo()%></td>
					<td><%=stuBean.getFirstName() + " " + stuBean.getLastName()%></td>
					<td><%=b.getPhysics()%></td>
					<td><%=b.getChemistry()%></td>
					<td><%=b.getMaths()%></td>
					<td><%=total%></td>
					<td><%=((perc) + "%")%></td>

					<td>
						<%
							if (phy >= 33 && chem >= 33 && math >= 33) {
						%> <span
						style="color: green"> Pass</span> <%
 	} else {
 %> <span
						style="color: red"> Fail</span> <%
 	}
 %>
					</td>
					<td><a href="MarksheetCtl?id=<%=b.getId()%>">Edit</a></td>
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
						value="<%=MarksheetListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>
					<td><input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_NEXT%>"
						<%=(nextPageSize == 0) ? "disabled" : ""%>></td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<%
				} else {
			%>
			<input type="submit" name="operation"
				value="<%=MarksheetListCtl.OP_BACK%>">
			<%
				}
			%>
		</form>


	</center>

	<%@ include file="footer.jsp"%>


</body>
</html>