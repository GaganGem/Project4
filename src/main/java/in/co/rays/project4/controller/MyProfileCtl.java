package in.co.rays.project4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.project4.beans.BaseBean;
import in.co.rays.project4.beans.UserBean;
import in.co.rays.project4.models.UserModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * Myprofile functionality controller.to perform update profile operation and
 * show profile
 * @author Gagan
 *
 */
@WebServlet(name="MyProfileCtl",urlPatterns={"/ctl/MyProfileCtl"})
public class MyProfileCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	public static final String OP_CHANGE_MY_PASSWORD = "Change Password";

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		String op = request.getParameter("operation");
		if (op.equalsIgnoreCase(OP_CHANGE_MY_PASSWORD)) {
			return pass;
		}
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
		if (DataValidator.isNull(request.getParameter("phone"))) {
			request.setAttribute("phone", PropertyReader.getValue("error.require", "Phone No"));
			pass = false;
		}else if (!DataValidator.isPhoneNo(request.getParameter("phone"))) {
			request.setAttribute("phone", "Please Enter Valid Mobile Number");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gen"))) {
			request.setAttribute("gen", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Dob"));
			pass = false;
		}else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		UserBean bean = new UserBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(request.getParameter("fName"));
		bean.setLastName(request.getParameter("lName"));
		bean.setGender(request.getParameter("gen"));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(request.getParameter("phone"));
		System.out.println("date chej " + DataUtility.getDateString(bean.getDob()));

		 populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean bean = (UserBean) session.getAttribute("user");
System.out.println("date at" +bean.getDob());
		ServletUtility.setBean(bean, request);
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserBean bean = (UserBean) populateBean(request);
		String op = request.getParameter("operation");
		if (OP_SAVE.equals(op)) {
			try {
				UserModel.update(bean);
				ServletUtility.setSuccessMessage("You Have Successfully Updated Your Profile", request);
			} catch (Exception e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CHANGE_MY_PASSWORD.equals(op)) {
			ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.MY_PROFILE_VIEW;
	}

}
