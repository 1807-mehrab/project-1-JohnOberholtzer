package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.dao.EmployeeDAO;
import com.revature.util.ConnectionManager;

@SuppressWarnings("serial")
@WebServlet("/loadprofile")
public class LoadProfileServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		System.out.println("User checking profile: " + username);

		EmployeeDAO E_DAO = new EmployeeDAO(new ConnectionManager());
		Employee EMP = E_DAO.getEMP(username);
		PrintWriter pw = resp.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		

		if (!E_DAO.detectEmployee(username)) {
			session.invalidate();
		} else {
			String jsonString = mapper.writeValueAsString(EMP);
			pw.println(jsonString);
		}
		pw.close();
	}
}
