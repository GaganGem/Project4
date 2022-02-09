package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.beans.BaseBean;
import in.co.rays.project4.beans.MarksheetBean;
import in.co.rays.project4.models.MarksheetModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * marksheet functionality ctl.to show list of marksheet
 * @author Gagan
 *
 */
@WebServlet(name="MarksheetListCtl",urlPatterns={"/ctl/MarksheetListCtl"})
public class MarksheetListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		MarksheetBean bean = new MarksheetBean();
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		 populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
                .getValue("page.size")) : pageSize;
		
		MarksheetBean bean = (MarksheetBean) populateBean(request);
		ArrayList<MarksheetBean> list = null;
		try {
			list = (ArrayList<MarksheetBean>) MarksheetModel.search(bean,pageNo,pageSize);
			List next = MarksheetModel.search(bean, pageNo+1, pageSize);
			
			if(list.size()==0){
				ServletUtility.setErrorMessage("No Record Found", request);
			}if(next==null||next.size()==0){
				request.setAttribute("nextListSize", 0);

			}else{
			request.setAttribute("nextListSize", next.size());
			}
		} catch (Exception e) {
			ServletUtility.handleException(e, request, response);
			return;
		}
		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
                .getValue("page.size")) : pageSize;

		MarksheetBean bean = (MarksheetBean) populateBean(request);
		ArrayList<MarksheetBean> list = null;
		
		String op = request.getParameter("operation");
		String[] ids = request.getParameterValues("ids");
		if(OP_SEARCH.equalsIgnoreCase(op)||OP_NEXT.equalsIgnoreCase(op)||OP_PREVIOUS.equalsIgnoreCase(op)){
			 if (OP_SEARCH.equalsIgnoreCase(op)) {
                 pageNo = 1;
             } else if (OP_NEXT.equalsIgnoreCase(op)) {
                 pageNo++;
             } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                 pageNo--;
             }
		}else if (OP_NEW.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.MARKSHEET_CTL, request,
                    response);
            return;
        }else if (OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			System.out.println("do post......."+op);
			return;
		}else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}else if (OP_DELETE.equalsIgnoreCase(op)) {
            pageNo = 1;
            if (ids != null && ids.length > 0) {
                MarksheetBean deletebean = new MarksheetBean();
                for (String id : ids) {
                    deletebean.setId(DataUtility.getInt(id));
                    try {
						MarksheetModel.delete(deletebean);
					} catch (Exception e) {
						ServletUtility.handleException(e, request, response);
						return;
					}
                }
                ServletUtility.setSuccessMessage("Data Delete Successfully", request);
				
            } else {
                ServletUtility.setErrorMessage(
                        "Select at least one record", request);
            }
        }
		
		try {
			list = (ArrayList<MarksheetBean>) MarksheetModel.search(bean,pageNo,pageSize);
			List next = MarksheetModel.search(bean, pageNo+1, pageSize);
			if(next==null||next.size()==0){
				request.setAttribute("nextListSize", 0);
				
			}else{
				request.setAttribute("nextListSize", next.size());
			}
		} catch (Exception e) {
			ServletUtility.handleException(e, request, response);
			return;
		}
		 if (list == null || list.size() == 0) {
             ServletUtility.setErrorMessage("No record found ", request);
     		ServletUtility.setBean(bean, request);
         }
		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.MARKSHEET_LIST_VIEW;
	}

}
