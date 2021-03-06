package com.revature.servlets;

import java.io.IOException;

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
@WebServlet("/updateticketfinal")
public class UpdateTicketServletFinal extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String UN = (String) session.getAttribute("username");
		System.out.println("User updating ticket: " + UN);
		
		EmployeeDAO E_DAO = new EmployeeDAO(new ConnectionManager());
		int RM_ID = E_DAO.getID(UN);
		TicketDAO T_DAO = new TicketDAO(new ConnectionManager());
		ObjectMapper mapper = new ObjectMapper();
		Ticket TKT = mapper.readValue(req.getInputStream(), Ticket.class);
		Ticket newTKT = T_DAO.getTKT(TKT.getT_ID());
		newTKT.setR_BOOLEAN(TKT.getR_BOOLEAN());
		newTKT.setRM_ID(RM_ID);
		T_DAO.updateTicket(newTKT);

	}
}
