package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.beans.BaseBean;
import in.co.rays.project4.beans.MarksheetBean;
import in.co.rays.project4.beans.StudentBean;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.models.MarksheetModel;
import in.co.rays.project4.models.StudentModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * marksheeet functionality controller.to perform add,delete and update operation
 * @author Gagan
 *
 */
@WebServlet(name="MarksheetCtl",urlPatterns={"/ctl/MarksheetCtl"})
public class MarksheetCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	@Override
	protected void preload(HttpServletRequest request) {
		try {
			List<StudentBean> l = StudentModel.list();
			request.setAttribute("Studentlist", l);
			System.out.println("pre list");
		} catch (Exception e) {
			// log.error(e);
		}
		System.out.println("pre out");
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll No"));
			pass = false;
		}else if(!DataValidator.isRollNo(request.getParameter("rollNo"))){
			   request.setAttribute("rollNo", "Please Enter Valid Roll No");
			   pass=false;	 
		}
		System.out.println(request.getParameter("StudentId"));
		if (DataValidator.isNull(request.getParameter("StudentId"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("phy"))) {
			request.setAttribute("phy", PropertyReader.getValue("error.require", "physics marks"));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("phy")) < 0) {
			request.setAttribute("phy", "Marks can not less than 0");
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("phy")) > 100) {
			request.setAttribute("phy", "Marks can not be more than 100");
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("phy"))
				&& !DataValidator.isInteger(request.getParameter("phy"))) {
			request.setAttribute("phy", PropertyReader.getValue("error.integer", "Physics marks"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("chem"))) {
			request.setAttribute("chem", PropertyReader.getValue("error.require", "Chemistry Mark"));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("chem")) > 100) {
			request.setAttribute("chem", "Marks can Not More then 100");
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("chem")) < 0) {
			request.setAttribute("chem", "Marks can Not less then 0 ");
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("chem"))
				&& !DataValidator.isInteger(request.getParameter("chem"))) {
			request.setAttribute("chem", PropertyReader.getValue("error.integer", "Chemistry Marks"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("math"))) {
			request.setAttribute("math", PropertyReader.getValue("error.require", "Maths Marks"));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("math")) > 100) {
			request.setAttribute("math", "Marks can Not More then 100");
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("math")) < 0) {
			request.setAttribute("math", "Marks can Not less then 0 ");
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("math"))
				&& !DataValidator.isInteger(request.getParameter("math"))) {
			request.setAttribute("math", PropertyReader.getValue("error.integer", "Chemistry Marks"));
			pass = false;
		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		MarksheetBean bean = new MarksheetBean();
		bean.setRollNo(request.getParameter("rollNo"));
		bean.setPhysics(DataUtility.getInt(request.getParameter("phy")));
		bean.setChemistry(DataUtility.getInt(request.getParameter("chem")));
		bean.setMaths(DataUtility.getInt(request.getParameter("math")));
		bean.setStudentId(DataUtility.getInt(request.getParameter("StudentId")));
		populateDTO(bean, request);
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			MarksheetBean bean;
			try {
				bean = MarksheetModel.findByPK(id);
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
			MarksheetBean bean = (MarksheetBean) populateBean(request);
			try {
				if (id > 0) {
					MarksheetModel.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Marksheet is Successfully Updated ", request);
				} else {
					MarksheetModel.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Marksheet is Successfully Added ", request);
				}
			} catch (DuplicateException e1) {
				ServletUtility.setErrorMessage(e1.getMessage(), request);
				ServletUtility.forward(getView(), request, response);
				return;
			} catch (Exception e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.MARKSHEET_VIEW;
	}

}
