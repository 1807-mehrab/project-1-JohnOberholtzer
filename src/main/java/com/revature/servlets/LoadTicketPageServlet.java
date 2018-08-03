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
import com.revature.beans.Ticket;
import com.revature.dao.EmployeeDAO;
import com.revature.dao.TicketDAO;
import com.revature.util.ConnectionManager;

@SuppressWarnings("serial")
@WebServlet("/loadticketpage")
public class LoadTicketPageServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		System.out.println("User loading individual ticket: " + username);
		
		ObjectMapper mapper = new ObjectMapper();
		int TID = mapper.readValue(req.getInputStream(), Integer.class);
		System.out.println("Ticket ID: " + TID);

		EmployeeDAO E_DAO = new EmployeeDAO(new ConnectionManager());
		TicketDAO T_DAO = new TicketDAO(new ConnectionManager());
		Ticket TKT = T_DAO.getTKT(TID);
		PrintWriter pw = resp.getWriter();
		if (!E_DAO.detectEmployee(username)) {
			session.invalidate();
		} else {
			String jsonString = mapper.writeValueAsString(TKT);
			System.out.println(jsonString);
			pw.println(jsonString);
		}
		pw.close();
	}
}
