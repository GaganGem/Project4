package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.beans.BaseBean;
import in.co.rays.project4.beans.CourseBean;
import in.co.rays.project4.beans.SubjectBean;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.models.CourseModel;
import in.co.rays.project4.models.SubModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * subject functionality controller.to perform add,delete and update operation.
 * @author Gagan
 *
 */
@WebServlet(name="SubjectCtl",urlPatterns={"/ctl/SubjectCtl"})
public class SubjectCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	protected void preload(HttpServletRequest request) {

		try {
			List<CourseBean> l = CourseModel.list();
			request.setAttribute("Courselist", l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("coursename"))) {
			request.setAttribute("coursename", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		SubjectBean bean = new SubjectBean();

		bean.setId(DataUtility.getInt(request.getParameter("id")));
		bean.setSubName(DataUtility.getString(request.getParameter("name")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setCourseId(DataUtility.getInt(request.getParameter("coursename")));
		// populateDTO(bean, request);
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		SubjectBean bean = null;

		int id = DataUtility.getInt(request.getParameter("id"));
		if (id > 0 || op != null) {
			try {
				bean = SubModel.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		int id = DataUtility.getInt(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			SubjectBean bean = (SubjectBean) populateBean(request);

			try {
				if (id > 0) {
					SubModel.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Subject is Succesfully updated", request);
				} else {
					SubModel.add(bean);
					ServletUtility.setSuccessMessage("Subject is Succesfully added", request);
				}
				ServletUtility.setBean(bean, request);
			} catch (DuplicateException e1) {
				ServletUtility.setErrorMessage(e1.getMessage(), request);
				ServletUtility.forward(getView(), request, response);
				return;
			} catch (Exception e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.SUBJECT_VIEW;
	}

}
