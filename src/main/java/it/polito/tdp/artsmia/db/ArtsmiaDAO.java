package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artisti;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<String> getAllRuoli() {
		
		String sql = "SELECT DISTINCT a.role AS ruolo " + 
				"FROM authorship a "+
				"ORDER BY a.role ";
		ArrayList<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				String exObj = res.getString("ruolo");
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Artisti> getArtistiByRuolo(String s) {
		
		String sql = "SELECT DISTINCT(a2.artist_id) AS id, a2.name AS nome " + 
				"FROM authorship a1, artists a2 " + 
				"WHERE a1.artist_id=a2.artist_id AND a1.role=?";
		ArrayList<Artisti> result = new ArrayList<Artisti>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, s);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Artisti exObj = new Artisti(res.getInt("id"),res.getString("nome"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getArco(Artisti a1, Artisti a2, String s) {
		
		String sql = "SELECT COUNT( DISTINCT e1.exhibition_id ) AS numero " + 
				"FROM authorship a1, authorship a2, exhibition_objects e1, exhibition_objects e2 " + 
				"WHERE a1.artist_id=? AND a2.artist_id=? " + 
				"AND a1.role=? AND a2.role=? " + 
				"AND e1.object_id=a1.object_id " + 
				"AND e2.object_id=a2.object_id AND e1.exhibition_id=e2.exhibition_id";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a1.getId());
			st.setInt(2, a2.getId());
			st.setString(3, s);
			st.setString(4, s);
			ResultSet res = st.executeQuery();
			int numero=-1;
			if(res.next())
				numero=res.getInt("numero");
			conn.close();
			return numero;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
}
