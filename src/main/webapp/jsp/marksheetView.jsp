<%@page import="in.co.rays.project4.controller.MarksheetCtl"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="in.co.rays.project4.beans.StudentBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet Page</title>
</head>
<body>
	<%@ include file="header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.project4.beans.MarksheetBean" scope="request"></jsp:useBean>

	<%
		List<StudentBean> l = (List<StudentBean>) request.getAttribute("Studentlist");
	%>
	<center>

		<h1>
			<b> <%
 	if (bean.getId() > 0) {
 %> <%="Update Marksheet"%> <%
 	} else {
 %> <%="Add Marksheet"%> <%
 	}
 %>
			</b>
		</h1>

		<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">
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
					<th align="left">Roll No<font color="red">*</font></th>
					<td><input type="text" name="rollNo" placeholder="Enter Roll No Here"
						value="<%=DataUtility.getStringData(bean.getRollNo())%>"
						<%=((bean.getId() > 0)) ? "readonly" : ""%>></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("rollNo", request) %>
					</font></td>
				</tr>
				<tr>
					<th align="left">Name<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("StudentId", String.valueOf(bean.getStudentId()), l)%></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("name", request) %>
 				</font></td>
				</tr>
				<tr>
					<th align="left">Physics<font color="red">*</font></th>
					<td><input type="text" name="phy" placeholder="Enter Physics No"
						value="<%=(DataUtility.getStringData(bean.getPhysics()).equals("0")) ? ""
					: DataUtility.getStringData(bean.getPhysics())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("phy", request) %>
 				</font></td>
				</tr>
				<tr>
					<th align="left">Chemistry<font color="red">*</font></th>
					<td><input type="text" name="chem" placeholder="Enter Chemistry No"
						value="<%=(DataUtility.getStringData(bean.getChemistry()).equals("0")) ? ""
					: DataUtility.getStringData(bean.getChemistry())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("chem", request) %>
					</font></td>
				</tr>
				<tr>
					<th align="left">Maths<font color="red">*</font></th>
					<td><input type="text" name="math" placeholder="Enter Math No"
						value="<%=(DataUtility.getStringData(bean.getMaths()).equals("0")) ? ""
					: DataUtility.getStringData(bean.getMaths())%>"></td>
					<td style="position:fixed"><font color="red"> <%=ServletUtility.getErrorMessage("math", request) %>
 			</font></td>
				</tr>
				<tr>
					<td></td>
					<%
						if (bean.getId() > 0 && bean != null) {
					%>
					<td>&nbsp; <input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_UPDATE%>"> &nbsp; <input
						type="submit" name="operation" value="<%=MarksheetCtl.OP_CANCEL%>"></td>


					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=MarksheetCtl.OP_SAVE%>">
						&nbsp;&nbsp; <input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_RESET%>"></td>

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