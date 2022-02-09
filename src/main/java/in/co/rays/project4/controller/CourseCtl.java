package in.co.rays.project4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.beans.BaseBean;
import in.co.rays.project4.beans.CollegeBean;
import in.co.rays.project4.beans.CourseBean;
import in.co.rays.project4.models.CollegeModel;
import in.co.rays.project4.models.CourseModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * course functionality ctl.to perform add,delete ,update operation
 * @author Gagan
 *
 */
@WebServlet(name="CourseCtl",urlPatterns={"/ctl/CourseCtl"})
public class CourseCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
			if (DataValidator.isNull(request.getParameter("name"))) {
				request.setAttribute("name", PropertyReader.getValue("error.require", "Course Name"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("desc"))) {
				request.setAttribute("desc", PropertyReader.getValue("error.require", "Description"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("duration"))) {
				request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
				pass = false;
			}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CourseBean bean = new CourseBean();
		bean.setId(DataUtility.getLong((request.getParameter("id"))));
		bean.setCourseName(request.getParameter("name"));
		bean.setDescription(request.getParameter("desc"));
		bean.setDuration(request.getParameter("duration"));
		populateDTO(bean, request);
		return bean;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			CourseBean bean;
			try {
				bean = CourseModel.findByPK(id);
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

		String op = request.getParameter("operation");
		int id = DataUtility.getInt(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			
		} else if ( OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op) ) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		}
		try {
			CourseBean bean = (CourseBean) populateBean(request);
			if (id > 0) {
				CourseModel.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Course updated Successfully", request);
			} else {
				CourseModel.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Course Inserted Successfully", request);
			}

		} catch (Exception e) {
			ServletUtility.handleException(e, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.COURSE_VIEW;
	}

	
}
