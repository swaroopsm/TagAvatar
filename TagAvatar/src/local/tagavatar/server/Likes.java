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
	
	public int get_likes(int photo){
		String sql="SELECT * FROM likes WHERE photo="+photo;
		int c=0;
		try{
			Statement st=this.con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				c++;
			}
			return c;
		}catch(Exception e){
			return 0;
		}
	}
	
	public int i_like(int photo, String username){
		String sql="SELECT * FROM likes WHERE photo="+photo+" AND user_id='"+username+"'";
		int c=0;
		try{
			Statement st=this.con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			if(rs.next()){
				c++;
			}
			return c;
		}catch(Exception e){
			return 0;
		}
	}
	
}
