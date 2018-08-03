package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Ticket;
import com.revature.util.ConnectionManager;
import com.revature.util.ConnectionUtil;

public class TicketDAO {
	private ConnectionManager ConnM;
	
	public TicketDAO(ConnectionManager ConnM) {
		this.ConnM = ConnM;
	}

	public boolean detectTicket(int TID) {
		PreparedStatement ps = null;
		ArrayList<Integer> tickets = new ArrayList<Integer>();
		
		try{
			Connection conn = ConnectionUtil.getConnection();//ConnM.makeConnection();
			String sql = "SELECT * FROM TICKETS WHERE T_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, TID);
			ResultSet rs = null;
			try {
				rs=ps.executeQuery();
				while (rs.next()) {
					int ticket = rs.getInt("T_ID");
					tickets.add(ticket);
				}
			} catch (SQLSyntaxErrorException ex) {
				System.out.println("[! CONNECTION FAILURE !");
			}
			try {
				rs.close();
				ps.close();
			} catch (NullPointerException ex) {
				ex.getMessage();
			}
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (tickets.contains(TID));
	}
	
	public int getSolved(int TID) {
		int val = 0;
		PreparedStatement ps = null;
		try{
			Connection conn = ConnM.makeConnection();
			String sql = "SELECT R_BOOLEAN FROM TICKETS WHERE T_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, TID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				val = rs.getInt("R_BOOLEAN");
			}
			
			rs.close();
			ps.close();
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (val);
	}
	
	public boolean createBlankTicket(int EID) {
		Boolean result = false;
		CallableStatement cs = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "{CALL SP_CREATE_TICKET(?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, EID);
			
			result = !(cs.execute());
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (result);
	}
	
	public boolean createTicket(Ticket TKT) {
		Boolean result = false;
		CallableStatement cs = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "{CALL SP_CREATE_TICKET(?,?,?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, TKT.getE_ID());
			cs.setString(2, TKT.getT_TITLE());
			cs.setString(3, TKT.getT_DESC());
			
			result = !(cs.execute());
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (result);
	}
	
	public boolean delTicket(int TID) {
		Boolean result = false;
		CallableStatement cs = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "{CALL SP_DEL_TICKET(?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, TID);
			
			result = !(cs.execute());
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (result);
	}
	
	public Ticket getTKT(int TID) {
		Integer EID = null;
		String T_TITLE = null;
		String T_DESC = null;
		Integer R_BOOLEAN = null;
		Integer RM_ID = null;
		Ticket TKT = null;
		PreparedStatement ps = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "SELECT * FROM TICKETS WHERE T_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, TID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				EID = rs.getInt("E_ID");
				T_TITLE = rs.getString("T_TITLE");
				T_DESC = rs.getString("T_DESC");
				R_BOOLEAN = rs.getInt("R_BOOLEAN");
				RM_ID = rs.getInt("RM_ID");
				TKT = new Ticket(TID,EID,T_TITLE,T_DESC,R_BOOLEAN,RM_ID);
			}
			
			rs.close();
			ps.close();
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TKT;
	}
	
	public List<Ticket> getTicketsByUser(int EID) {
		ArrayList<Ticket> TICKETS = new ArrayList<>();
		Integer TID = null;
		String T_TITLE = null;
		String T_DESC = null;
		Integer R_BOOLEAN = null;
		Integer RM_ID = null;
		Ticket TKT = null;
		PreparedStatement ps = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "SELECT * FROM TICKETS WHERE E_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, EID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				TID = rs.getInt("T_ID");
				T_TITLE = rs.getString("T_TITLE");
				T_DESC = rs.getString("T_DESC");
				R_BOOLEAN = rs.getInt("R_BOOLEAN");
				RM_ID = rs.getInt("RM_ID");
				TKT = new Ticket(TID,EID,T_TITLE,T_DESC,R_BOOLEAN,RM_ID);
				TICKETS.add(TKT);
			}
			
			rs.close();
			ps.close();
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TICKETS;
	}
	
	public List<Ticket> getAllTickets() {
		ArrayList<Ticket> TICKETS = new ArrayList<>();
		Integer TID = null;
		Integer EID = null;
		String T_TITLE = null;
		String T_DESC = null;
		Integer R_BOOLEAN = null;
		Integer RM_ID = null;
		Ticket TKT = null;
		PreparedStatement ps = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "SELECT * FROM TICKETS";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				TID = rs.getInt("T_ID");
				EID = rs.getInt("E_ID");
				T_TITLE = rs.getString("T_TITLE");
				T_DESC = rs.getString("T_DESC");
				R_BOOLEAN = rs.getInt("R_BOOLEAN");
				RM_ID = rs.getInt("RM_ID");
				TKT = new Ticket(TID,EID,T_TITLE,T_DESC,R_BOOLEAN,RM_ID);
				TICKETS.add(TKT);
			}
			
			rs.close();
			ps.close();
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TICKETS;
	}
	
	public boolean updateTicket(Ticket TKT) {
		Boolean result = false;
		CallableStatement cs = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "{CALL SP_UPDATE_TICKET(?, ?, ?, ?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, TKT.getT_ID());
			cs.setString(2, TKT.getT_TITLE());
			cs.setString(3, TKT.getT_DESC());
			cs.setInt(4, TKT.getR_BOOLEAN());
			cs.setInt(5, TKT.getRM_ID());
			
			result = !(cs.execute());
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (result);
	}

}
