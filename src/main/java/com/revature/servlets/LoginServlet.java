package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.beans.Employee;
import com.revature.beans.Login;
import com.revature.dao.EmployeeDAO;
import com.revature.util.ConnectionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")	
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		ObjectMapper mapper = new ObjectMapper();
		Login LG = mapper.readValue(req.getInputStream(), Login.class);
		System.out.println(LG.getUsername());
		

		String username = LG.getUsername();
		
		EmployeeDAO E_DAO = new EmployeeDAO(new ConnectionManager());
		PrintWriter pw = resp.getWriter();
		if (!E_DAO.detectEmployee(username)) {
			pw.println("{\"result\":3}");
		} else {
			String password = LG.getPassword();
			String truepass = E_DAO.getPass(username);
			if(truepass.equals(password)){
				Employee EMP = E_DAO.getEMP(username);
				if(EMP.getManager()==1) {
					pw.println("{\"result\":1}");
				} else {
					pw.println("{\"result\":0}");
				}
				session.setAttribute("username", username);
			} else {
				pw.println("{\"result\":2}");
			}
			
		}
		pw.close();
	}
}
