package local.tagavatar.server;

import java.sql.*;
import org.json.JSONObject;
import local.tagavatar.server.Settings;

public class Likes {
	
	private Connection con;
	
	public Likes(){
		Settings s=new Settings();
		this.con=s.get_connection();
	}
	
	public String create(String username, int photo){
		String sql="INSERT INTO likes(user_id,photo) VALUES('"+username+"',"+photo+")";
		JSONObject json=new JSONObject();
		try{
			Statement st=this.con.createStatement();
			st.execute(sql);
			json.put("status", true);
			return json.toString();
		}catch(Exception e){
			return (json.put("status", false)).toString();
		}
	}
	
}
