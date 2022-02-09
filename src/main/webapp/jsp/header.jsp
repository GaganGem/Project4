<%@page import="in.co.rays.project4.models.RoleModel"%>
<%@page import="in.co.rays.project4.beans.RoleBean"%>
<%@page import="in.co.rays.project4.controller.LoginCtl"%>
<%@page import="in.co.rays.project4.controller.ORSView"%>
<%@page import="in.co.rays.project4.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
<div>
		<img style="float: right" src="<%=ORSView.APP_CONTEXT%>/img/logo.png" alt="Rays Logo"> <br>
		</div>


	<%
		UserBean user = (UserBean) session.getAttribute("user");
		boolean userLoggedIn = user != null;
		if (user == null || user.getId() <= 0) {
	%>
	<div style="margin-bottom: 100px">

		<div>
			<div style="float: left;">
				<a href="<%=ORSView.WELCOME_CTL%>"><b>Welcome</b></a> | <a
					href="<%=ORSView.LOGIN_CTL%>"><b>LogIn</b></a> <br> <span
					style="margin-top: 25px">Hi, Guest</span>
			</div>
			<%-- <div>
				<img style="float: right"src="<%=ORSView.APP_CONTEXT%>/img/logo.png"> <br>
			</div> --%>
		</div>
	</div>
	<%
		} else {

			long role = user.getRoleId();
			RoleBean roleBean = RoleModel.findByPK(role);
			String wlcmMsg = user.getFirstName() + "(" + roleBean.getName() + ")";
	%>
	<div style="margin-bottom: 40px">

		<div style="float: left;">
			<a href="<%=ORSView.WELCOME_CTL%>"><b>Welcome</b></a> | <a
				href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><b>LogOut</b></a>
			<br> Hi,
			<%=wlcmMsg%>

		</div>
		
		<br> <br>


		<%
			if (userLoggedIn) {
		%>
		<div style="margin-top: 30px;">
			<a href="<%=ORSView.MY_PROFILE_CTL%>"><b>MyProfile</b></a> | <a
				href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><b>Change Password</b></a> | <a
				href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>Get Marksheet</b></a> | <a
				href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><b>Marksheet MeritList</b>
			</a> |
			<%
				if (user.getRoleId() == 1) {
			%>
			<a href="<%=ORSView.MARKSHEET_CTL%>"><b>Add Marksheet</b></a> | <a
				href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>Marksheet List</b></a> | <a
				href="<%=ORSView.USER_CTL%>"><b>Add User</b></a> | <a
				href="<%=ORSView.USER_LIST_CTL%>"><b>User List</b></a> | <a
				href="<%=ORSView.COLLEGE_CTL%>"><b>Add College</b></a> | <a
				href="<%=ORSView.COLLEGE_LIST_CTL%>"><b>College List</b></a> | <a
				href="<%=ORSView.ROLE_CTL%>"><b>Add Role</b></a> | <a
				href="<%=ORSView.ROLE_LIST_CTL%>"><b>Role List</b></a> | <br> <a
				href="<%=ORSView.STUDENT_CTL%>"><b>Add Student</b></a> | <a
				href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student List</b></a> | <a
				href="<%=ORSView.COURSE_CTL%>"><b>Add Course</b></a> | <a
				href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course List</b></a> | <a
				href="<%=ORSView.SUBJECT_CTL%>"><b>Add Subject</b></a> | <a
				href="<%=ORSView.SUBJECT_LIST_CTL%>"><b>Subject List</b></a> | <a
				href="<%=ORSView.FACULTY_CTL%>"><b>Add Faculty</b></a> | <a
				href="<%=ORSView.FACULTY_LIST_CTL%>"><b>Faculty List</b></a> | <a
				href="<%=ORSView.TIMETABLE_CTL%>"><b>Add TimeTable</b></a> | <a
				href="<%=ORSView.TIMETABLE_LIST_CTL%>"><b>TimeTable List</b></a> | <a
				target="blank" href="<%=ORSView.JAVA_DOC_VIEW%>"><b>Java Doc</b></a>

			<%
				} else if (user.getRoleId() == RoleBean.STUDENT) {
			%>
			<a href="<%=ORSView.COLLEGE_LIST_CTL%>"><b>College List</b></a> | <a
				href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student List</b></a> | <a
				href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course List</b></a> | <a
				href="<%=ORSView.SUBJECT_LIST_CTL%>"><b>Subject List</b></a> | <a
				href="<%=ORSView.FACULTY_LIST_CTL%>"><b>Faculty List</b></a> | <a
				href="<%=ORSView.TIMETABLE_LIST_CTL%>"><b>TimeTable List</b></a> |
			<%
				} else if (user.getRoleId() == RoleBean.COLLEGE_SCHOOL) {
			%>

			<a href="<%=ORSView.MARKSHEET_CTL%>"><b>Add Marksheet</b></a> | <a
				href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>Marksheet List</b></a> | <a
				href="<%=ORSView.COLLEGE_LIST_CTL%>"><b>College List</b></a> | <a
				href="<%=ORSView.STUDENT_CTL%>"><b>Add Student</b></a> | <a
				href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student List</b></a> | <a
				href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course List</b></a> | <a
				href="<%=ORSView.SUBJECT_CTL%>"><b>Add Subject</b></a> | <br> <a
				href="<%=ORSView.SUBJECT_LIST_CTL%>"><b>Subject List</b></a> | <a
				href="<%=ORSView.TIMETABLE_CTL%>"><b>Add TimeTable</b></a> | <a
				href="<%=ORSView.TIMETABLE_LIST_CTL%>"><b>TimeTable List</b></a> |
			<%
 	} else if (user.getRoleId() == RoleBean.KIOSK) {
 %>

			<a href="<%=ORSView.MARKSHEET_CTL%>"><b>Add Marksheet</b></a> | <a
				href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>Marksheet List</b></a> | <a
				href="<%=ORSView.STUDENT_CTL%>"><b>Add Student</b></a> | <a
				href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student List</b></a> | <a
				href="<%=ORSView.FACULTY_LIST_CTL%>"><b>Faculty List</b></a> | <a
				href="<%=ORSView.TIMETABLE_LIST_CTL%>"><b>TimeTable List</b></a> | <a
				href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course List</b></a> |
			<%
				}
			%>
		</div>
		<%
			}
			}
		%>


		<hr>
</body>
</html>