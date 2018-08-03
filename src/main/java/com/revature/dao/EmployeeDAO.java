package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Employee;
import com.revature.util.ConnectionManager;
import com.revature.util.ConnectionUtil;

public class EmployeeDAO {
	private ConnectionManager ConnM;
	
	public EmployeeDAO(ConnectionManager ConnM) {
		this.ConnM = ConnM;
	}

	public boolean detectEmployee(String username) {
		PreparedStatement ps = null;
		ArrayList<String> Usernames = new ArrayList<String>();
		
		try{
			Connection conn = ConnectionUtil.getConnection();//ConnM.makeConnection();
			String sql = "SELECT * FROM EMPLOYEES WHERE E_NAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username.trim());
			ResultSet rs = null;
			try {
				rs=ps.executeQuery();
				while (rs.next()) {
					String name = rs.getString("E_NAME");
					Usernames.add(name);
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
		return (Usernames.contains(username));
	}
	
	public String getPass(String username) {
		String pass = null;
		PreparedStatement ps = null;
		try{
			Connection conn = ConnM.makeConnection();
			String sql = "SELECT E_PASS FROM EMPLOYEES WHERE E_NAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				pass = rs.getString("E_PASS");
			}
			
			rs.close();
			ps.close();
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (pass);
	}
	
	public Integer getID(String username) {
		Integer UID = null;
		PreparedStatement ps = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "SELECT E_ID FROM EMPLOYEES WHERE E_NAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				UID = rs.getInt("E_ID");
			}
			
			rs.close();
			ps.close();
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (UID);
	}
	
	public boolean createEmployee(String username, String password) {
		Boolean result = false;
		CallableStatement cs = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "{CALL SP_CREATE_EMPLOYEE(?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, username);
			cs.setString(2, password);
			
			result = !(cs.execute());
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (result);
	}
	
	public boolean delEmployee(String username) {
		Boolean result = false;
		CallableStatement cs = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "{CALL SP_DEL_USER(?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, username);
			
			result = !(cs.execute());
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (result);
	}
	
	public Employee getEMP(String username) {
		String FNAME,LNAME,PW,PHON,ADDR,EMAIL;
		int E_ID,MNGR;
		Employee EMP = null;
		PreparedStatement ps = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "SELECT * FROM EMPLOYEES WHERE E_NAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				E_ID = rs.getInt("E_ID");
				FNAME = rs.getString("E_FNAME");
				LNAME = rs.getString("E_LNAME");
				PW = rs.getString("E_PASS");
				PHON = rs.getString("E_PHONE");
				ADDR = rs.getString("E_ADDRESS");
				EMAIL = rs.getString("E_EMAIL");
				MNGR = rs.getInt("E_MANAGER");
				EMP = new Employee(E_ID,MNGR,username,FNAME,LNAME,PW,PHON,ADDR,EMAIL);
			}
			
			rs.close();
			ps.close();
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return EMP;
	}
	
	public Employee getEMPbyID(int EID) {
		String USRNAME, FNAME,LNAME,PW,PHON,ADDR,EMAIL;
		int E_ID, MNGR;
		Employee EMP = null;
		PreparedStatement ps = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "SELECT * FROM EMPLOYEES WHERE E_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, EID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				E_ID = rs.getInt("E_ID");
				USRNAME = rs.getString("E_NAME");
				FNAME = rs.getString("E_FNAME");
				LNAME = rs.getString("E_LNAME");
				PW = rs.getString("E_PASS");
				PHON = rs.getString("E_PHONE");
				ADDR = rs.getString("E_ADDRESS");
				EMAIL = rs.getString("E_EMAIL");
				MNGR = rs.getInt("E_MANAGER");
				EMP = new Employee(E_ID,MNGR,USRNAME,FNAME,LNAME,PW,PHON,ADDR,EMAIL);
			}
			
			rs.close();
			ps.close();
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return EMP;
	}
	
	public List<Employee> getAllEmployees() {
		String USRNAME,FNAME,LNAME,PW,PHON,ADDR,EMAIL;
		int E_ID, MNGR;
		ArrayList<Employee> EMPLIST = new ArrayList<>();
		Employee EMP = null;
		PreparedStatement ps = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "SELECT * FROM EMPLOYEES";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				E_ID = rs.getInt("E_ID");
				USRNAME = rs.getString("E_NAME");
				FNAME = rs.getString("E_FNAME");
				LNAME = rs.getString("E_LNAME");
				PW = rs.getString("E_PASS");
				PHON = rs.getString("E_PHONE");
				ADDR = rs.getString("E_ADDRESS");
				EMAIL = rs.getString("E_EMAIL");
				MNGR = rs.getInt("E_MANAGER");
				EMP = new Employee(E_ID,MNGR,USRNAME,FNAME,LNAME,PW,PHON,ADDR,EMAIL);
				EMPLIST.add(EMP);
			}
			
			rs.close();
			ps.close();
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return EMPLIST;
	}
	
	public boolean updateEmployee(Employee EMP) {
		Boolean result = false;
		CallableStatement cs = null;
		String UN = EMP.getUsername();
		String FN = EMP.getFirstname();
		String LN = EMP.getLastname();
		String PW = EMP.getPassword();
		int MNG = EMP.getManager();
		String PHON = EMP.getPhone();
		String ADDR = EMP.getAddress();
		String EMAIL = EMP.getEmail();
		
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "{CALL SP_UPDATE_EMPLOYEE(?, ?, ?, ?, ?, ?, ?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, UN);
			cs.setString(2, FN);
			cs.setString(3, LN);
			cs.setString(4, PW);
			cs.setInt(5, MNG);
			cs.setString(6, PHON);
			cs.setString(7, ADDR);
			cs.setString(8, EMAIL);
			
			result = !(cs.execute());
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (result);
	}

}
