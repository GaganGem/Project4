package in.co.rays.project4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.beans.BaseBean;
import in.co.rays.project4.beans.RoleBean;
import in.co.rays.project4.models.RoleModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * role functionality controller.to perform add,delete ,update operation
 * @author Gagan
 *
 */
@WebServlet(name="RoleCtl",urlPatterns={"/ctl/RoleCtl"})
public class RoleCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("desc"))) {
			request.setAttribute("desc", PropertyReader.getValue("error.require", "Desciption"));
			pass = false;
		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		RoleBean bean = new RoleBean();
		bean.setId(DataUtility.getInt(request.getParameter("id")));

		bean.setName(request.getParameter("name"));
		bean.setDescription(request.getParameter("desc"));
		 populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			RoleBean bean = null;
			try {
				bean = RoleModel.findByPK(id);
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

		if (OP_SAVE.equalsIgnoreCase(op)|| OP_UPDATE.equalsIgnoreCase(op)) {

			RoleBean bean = (RoleBean) populateBean(request);
			long id = DataUtility.getLong(request.getParameter("id"));

			if (id > 0) {
				try {
					RoleModel.update(bean);
					ServletUtility.setSuccessMessage("You Have Successfully Updated", request);
					ServletUtility.setBean(bean, request);
				} catch (Exception e) {
					ServletUtility.handleException(e, request, response);
					return;
				}
			} else {
				try {
					RoleModel.add(bean);
					ServletUtility.setSuccessMessage("You Have Successfully Added", request);
				} catch (Exception e) {
					ServletUtility.handleException(e, request, response);
					return;
				}
			}
			ServletUtility.forward(getView(), request, response);
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
            return;
        } else if (OP_RESET.equals(op)){
        	ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
        	return;
        }

	}

	@Override
	protected String getView() {
		return ORSView.ROLE_VIEW;
	}

}
