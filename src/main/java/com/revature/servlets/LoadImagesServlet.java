package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Image;
import com.revature.dao.ImageDAO;
import com.revature.util.ConnectionManager;

@SuppressWarnings("serial")
@WebServlet("loadimagemenu")
public class LoadImagesServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		int TID = mapper.readValue(req.getInputStream(), Integer.class);
		PrintWriter pw = resp.getWriter();
		ImageDAO IM_DAO = new ImageDAO(new ConnectionManager());
		List<Image> ImageList = IM_DAO.getImagesByTicket(TID);
		
		String jsonString = mapper.writeValueAsString(ImageList);
		System.out.println(jsonString);
		pw.println(jsonString);
		pw.close();
		
	}
}
