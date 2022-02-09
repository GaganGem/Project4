<%@page import="in.co.rays.project4.controller.TimeTableCtl"%>
<%@page import="in.co.rays.project4.util.DataUtility"%>
<%@page import="in.co.rays.project4.util.HTMLUtility"%>
<%@page import="in.co.rays.project4.util.ServletUtility"%>
<%@page import="in.co.rays.project4.beans.TimetableBean"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Timetable Registration Page</title>
 <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

  <script>
  
  function disableSunday(d){
	  var day = d.getDay();
	  if(day==0)
	  {
	   return [false];
	  }else
	  {
		  return [true];
	  }
  }
  
  $( function() {
	  $( "#datepiker" ).datepicker({
		  changeMonth :true,
		  changeYear :true,
		  yearRange :'0:+2',
		  dateFormat : 'dd-mm-yy',

// Disable for Sunday
		  beforeShowDay : disableSunday,		  
// Disable for back date
		  minDate : 0   
	  });
  } );
  </script>


</head>
<body>

	<jsp:useBean id="bean" class="in.co.rays.project4.beans.TimetableBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.TIMETABLE_CTL%>" method="post">

		<%@include file="header.jsp"%>

		<center>
			<%
				List<TimetableBean> courseList = (List<TimetableBean>) request.getAttribute("CourseList");
				List<TimetableBean> subjectList = (List<TimetableBean>) request.getAttribute("SubjectList");
			%>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModifiedDatetime()%>">

			<div align="center">
				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>


					Update TimeTable


					<%
						} else {
					%>


					Add TimeTable
					<%
						}
					%>
				</h1>

				<h3 align="center">
					<font style="color: red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>
				<h3 align="center">
					<font style="color: green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
			</div>

			<table>
				<tr>
					<th align="left">Course <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), courseList)%></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Subject <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubId()), subjectList)%></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subjectId", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Semester<span style="color: red">*</span> :
					</th>
					<td>
						<%
							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
							map.put("1st", "1st");
							map.put("2nd", "2nd");
							map.put("3rd", "3rd");
							map.put("4th", "4th");
							map.put("5th", "5th");
							map.put("6th", "6th");
							map.put("7th", "7th");
							map.put("8th", "8th");

							String htmlList = HTMLUtility.getList("semester", bean.getSem(), map);
						%> <%=htmlList%>
					</td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("semester", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Exam Date <span style="color: red">*</span> :
					</th>
					<td><input type="text" readonly="readonly" id="datepiker"
						size="20" placeholder="Select Date" name="ExDate"
						value="<%=DataUtility.getDateString(bean.getExamDate())%>">
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("ExDate", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Exam Time <span style="color: red">*</span> :
					</th>
					<td>
						<%
							LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
							map1.put("08:00 AM to 11:00 AM", "08:00 AM to 11:00 AM");
							map1.put("12:00 PM to 03:00 PM", "12:00 PM to 03:00 PM");
							map1.put("04:00 PM to 07:00 PM", "04:00 PM to 07:00 PM");

							String htmlList1 = HTMLUtility.getList("Extime", bean.getExamTime(), map1);
						%> <%=htmlList1%>
					</td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("Extime", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
					<td></td>
				</tr>

				<tr>
					<th></th>
					<td></td>
				</tr>

				<tr align="center">
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td><input type="submit" name="operation"
						value="<%=TimeTableCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=TimeTableCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td></td>
					<td><input type="submit" name="operation"
						value="<%=TimeTableCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=TimeTableCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>
			</table>
		</center>

	</form>

</body>
</html>