package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.beans.BaseBean;
import in.co.rays.project4.beans.CollegeBean;
import in.co.rays.project4.beans.CourseBean;
import in.co.rays.project4.beans.FacultyBean;
import in.co.rays.project4.beans.SubjectBean;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.models.CollegeModel;
import in.co.rays.project4.models.CourseModel;
import in.co.rays.project4.models.FacultyModel;
import in.co.rays.project4.models.SubModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * faculty functionality ctl.To perform add,delete and update operation
 * @author Gagan
 *
 */
@WebServlet(name = "FacultyCtl", urlPatterns = { "/ctl/FacultyCtl" })
public class FacultyCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	@Override
	protected void preload(HttpServletRequest request) {
		try {
			List<CollegeBean> college = CollegeModel.list();
			List<CourseBean> course = CourseModel.list();
			List<SubjectBean> sub = SubModel.list();
			request.setAttribute("CollegeList", college);
			request.setAttribute("CourseList", course);
			request.setAttribute("SubjectList", sub);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		String op = request.getParameter("operation");
		if (op.equalsIgnoreCase(OP_UPDATE) || op.equalsIgnoreCase(OP_SAVE)) {
			if (DataValidator.isNull(request.getParameter("fName"))) {
				request.setAttribute("fName", PropertyReader.getValue("error.require", "First Name"));
				pass = false;
			}else if (!DataValidator.isName(request.getParameter("fName"))) {
				request.setAttribute("fName", "please enter correct Name");
				pass = false;

			}
			if (DataValidator.isNull(request.getParameter("lName"))) {
				request.setAttribute("lName", PropertyReader.getValue("error.require", "Last Name"));
				pass = false;
			}else if (!DataValidator.isName(request.getParameter("lName"))) {
				request.setAttribute("lName", "please enter correct Name");
				pass = false;

			}
			if (DataValidator.isNull(request.getParameter("gen"))) {
				request.setAttribute("gen", PropertyReader.getValue("error.require", "Gender"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("email"))) {
				request.setAttribute("email", PropertyReader.getValue("error.require", "Login Id"));
				pass = false;
			} else if (!DataValidator.isEmail(request.getParameter("email"))) {
				request.setAttribute("email", PropertyReader.getValue("error.email", "Login "));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("collegeName"))) {
				request.setAttribute("collegeName", PropertyReader.getValue("error.require", "College Name"));
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("doj"))) {
				request.setAttribute("doj", PropertyReader.getValue("error.require", "Date Of Joining"));
				pass = false;
			}else if (!DataValidator.isDate(request.getParameter("doj"))) {
				request.setAttribute("doj", PropertyReader.getValue("error.date", "Joining Date"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("phone"))) {
				request.setAttribute("phone", PropertyReader.getValue("error.require", "Phone No"));
				pass = false;
			}else if(!DataValidator.isPhoneNo(request.getParameter("phone")))
			{
				  request.setAttribute("phone", "Please Enter Valid Mobile Number");
				  pass=false;	
			    }
			if (DataValidator.isNull(request.getParameter("coName"))) {
				request.setAttribute("coName", PropertyReader.getValue("error.require", "Course Name"));
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("subName"))) {
				request.setAttribute("subName", PropertyReader.getValue("error.require", "Subject Name"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("qua"))) {
				request.setAttribute("qua", PropertyReader.getValue("error.require", "Qualification"));
				pass = false;
			}
		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		FacultyBean bean = new FacultyBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("fName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lName")));
		bean.setGender(DataUtility.getString(request.getParameter("gen")));
		bean.setDoj(DataUtility.getDate(request.getParameter("doj")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("phone")));
		bean.setQualification(DataUtility.getString(request.getParameter("qua")));
		bean.setCollegeId(DataUtility.getInt(request.getParameter("collegeName")));
		bean.setCourseId(DataUtility.getInt(request.getParameter("coName")));
		bean.setSubId(DataUtility.getInt(request.getParameter("subName")));
		populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			FacultyBean bean;
			try {
				bean = FacultyModel.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		String op = request.getParameter("operation");
		System.out.println("post");
		if (op.equalsIgnoreCase(OP_SAVE) || op.equalsIgnoreCase(OP_UPDATE)) {
			FacultyBean bean = (FacultyBean) populateBean(request);
			
			try {
				if (id > 0) {
					System.out.println("update fac");
					FacultyModel.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Faculty Successfully Upadated", request);
				} else {
					System.out.println("add fac");
					FacultyModel.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Faculty Successfully Inserted", request);
				}
			} catch (DuplicateException e1) {
				ServletUtility.setErrorMessage(e1.getMessage(), request);
				ServletUtility.forward(getView(), request, response);
				return;
			} catch (Exception e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		if (op.equalsIgnoreCase(OP_RESET)) {
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		}
		if (op.equalsIgnoreCase(OP_CANCEL)) {
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.FACULTY_VIEW;
	}

}
