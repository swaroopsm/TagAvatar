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
	
	public int[] get_likes(int photo, String username){
		String sql="SELECT * FROM likes WHERE photo="+photo;
		int[] a=new int[2];
		int c=0, u=0;
		try{
			Statement st=this.con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				c++;
				if(rs.getString("user_id").equals(username)){
					u++;
				}
			}
			a[0]=c;
			a[1]=u;
			return a;
		}catch(Exception e){
			a[0]=0;
			a[1]=0;
			return a;
		}
	}
}
