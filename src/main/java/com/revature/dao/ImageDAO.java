package com.revature.dao;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Image;
import com.revature.util.ConnectionManager;
import com.revature.util.ConnectionUtil;

public class ImageDAO {
	private ConnectionManager ConnM;
	
	public ImageDAO(ConnectionManager ConnM) {
		this.ConnM = ConnM;
	}

	public boolean detectImages(int IMG_ID) {
		PreparedStatement ps = null;
		ArrayList<Integer> IDCOUNT = new ArrayList<Integer>();
		
		try{
			Connection conn = ConnectionUtil.getConnection();//ConnM.makeConnection();
			String sql = "SELECT * FROM IMAGES WHERE I_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, IMG_ID);
			ResultSet rs = null;
			try {
				rs=ps.executeQuery();
				while (rs.next()) {
					Integer imgid = rs.getInt("I_ID");
					IDCOUNT.add(imgid);
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
		if(IDCOUNT.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public List<Image> getImagesByTicket(int TID) {
		int I_ID;
		Blob IMAGEBLOB;
		ArrayList<Image> IMGLIST = new ArrayList<>();
		Image IMG = null;
		PreparedStatement ps = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "SELECT * FROM IMAGES WHERE T_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, TID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				I_ID = rs.getInt("I_ID");
				IMAGEBLOB = rs.getBlob("IMAGE");
				
				IMG = new Image(I_ID,TID,IMAGEBLOB);
				IMGLIST.add(IMG);
			}
			
			rs.close();
			ps.close();
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return IMGLIST;
	}
	
	public Image getImage(int IID) {
		int T_ID;
		Blob IMAGEBLOB;
		Image IMG = null;
		PreparedStatement ps = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "SELECT * FROM IMAGES WHERE I_ID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, IID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				T_ID = rs.getInt("T_ID");
				IMAGEBLOB = rs.getBlob("IMAGE");
				
				IMG = new Image(IID,T_ID,IMAGEBLOB);
			}
			
			rs.close();
			ps.close();
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return IMG;
	}
	
	public boolean createImage(int TID, Blob IMGBLOB) {
		Boolean result = false;
		CallableStatement cs = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "{CALL SP_CREATE_IMAGE(?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, TID);
			cs.setBlob(2, IMGBLOB);
			
			result = !(cs.execute());
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (result);
	}
	
	public boolean delImage(int IID) {
		Boolean result = false;
		CallableStatement cs = null;
		try(Connection conn = ConnM.makeConnection()) {
			String sql = "{CALL SP_CREATE_IMAGE(?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, IID);
			
			result = !(cs.execute());
			ConnM.Kill(conn);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (result);
	}
	
	
}
