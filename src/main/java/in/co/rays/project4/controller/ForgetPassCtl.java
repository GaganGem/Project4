package in.co.rays.project4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.beans.BaseBean;
import in.co.rays.project4.beans.UserBean;
import in.co.rays.project4.models.UserModel;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * forget password ctl.To perform password send in email
 * @author Gagan
 *
 */
@WebServlet(name = "ForgetPasswordCtl", urlPatterns = { "/ForgetPassCtl" })
public class ForgetPassCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("login"))) {
		//	request.setAttribute("login", "Please Enter Valid Email Id!");
			request.setAttribute("login", PropertyReader.getValue("error.require","Login"));
			pass = false;
		}else if(!DataValidator.isEmail(request.getParameter("login"))){
			request.setAttribute("login", PropertyReader.getValue("error.email","Login Id"));
			pass=false;
		}
		System.out.println("validate");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		UserBean bean = new UserBean();
		bean.setLogin(request.getParameter("login"));
		// populateDTO(bean, request);
		System.out.println("pop");

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("pst");
		UserBean bean = (UserBean) populateBean(request);
		try {
			System.out.println("try");
			bean = UserModel.findByLogin(bean.getLogin());
			if(bean.getFirstName()!=null){
			UserModel.reset(bean);
				ServletUtility.setSuccessMessage("Password has been sent to your email id!", request);
			}else{
				ServletUtility.setErrorMessage("Invalid Email Id!", request);
			}
			
		} catch (Exception e) {
			ServletUtility.handleException(e, request, response);
			return;
		}
		ServletUtility.setBean(bean, request);
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.FORGET_PASSWORD_VIEW;
	}

}
