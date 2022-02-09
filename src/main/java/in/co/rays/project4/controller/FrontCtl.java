package in.co.rays.project4.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.project4.beans.UserBean;
import in.co.rays.project4.util.ServletUtility;

/**
 * Front Functionality ctl. to perform session management operation
 * @author Gagan
 *
 */
@WebFilter(filterName = "FrontCtl", urlPatterns = { "/ctl/*", "/doc/*" })
public class FrontCtl implements Filter{
	
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession(true);
		UserBean bean = (UserBean) session.getAttribute("user");
		
		String uri = req.getRequestURI();
		request.setAttribute("uri",uri);
		
		if(bean==null){
			req.setAttribute("message", "Your Session Has Expired, Please Login!");
			ServletUtility.forward("/LoginCtl", req, res);
		}else{
			chain.doFilter(req, res);
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	

	
}
