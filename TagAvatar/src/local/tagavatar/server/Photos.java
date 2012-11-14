package local.tagavatar.server;

import java.sql.*;

import local.tagavatar.server.Settings;

public class Photos {
	
	private Connection con;
	
	public Photos(){
		Settings s=new Settings();
		this.con=s.get_connection();
	}
	
	public Boolean create(String title, String desc, String photo, String username){
		String sql="INSERT INTO photos(`title`,`desc`,`photo`,`user_id`,`datetime`) VALUES(?,?,?,?,?)";
		try{
			PreparedStatement ps=this.con.prepareStatement(sql);
			ps.setString(1, title);
			ps.setString(2, desc);
			ps.setString(3, photo);
			ps.setString(4, username);
			ps.setString(5, "");
			ps.executeUpdate();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
}
