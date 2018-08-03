package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.revature.dao.EmployeeDAO;
import com.revature.util.ConnectionManager;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")	
@WebServlet("/loader")
public class LoaderServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		System.out.println("Currently logged in user is: " + username);	

		EmployeeDAO E_DAO = new EmployeeDAO(new ConnectionManager());
		PrintWriter pw = resp.getWriter();
		if (!E_DAO.detectEmployee(username)) {
			session.invalidate();
		} else {
			pw.println("{\"username\":\""+username+"\"}");		
		}
		pw.close();
	}
}
