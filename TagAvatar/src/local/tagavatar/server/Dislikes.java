package local.tagavatar.server;

import java.sql.*;
import local.tagavatar.server.Settings;
import local.tagavatar.server.Likes;

public class Dislikes {
	
	private Connection con;
	
	public Dislikes(){
		Settings s=new Settings();
		this.con=s.get_connection();
	}
	
	public int create(int photo, String username){
		String sql="INSERT INTO dislikes(`photo`,`user_id`) VALUES("+photo+",'"+username+"')";
		Likes l=new Likes();
		try{
			if(l.delete(photo, username)==1){
				Statement st=this.con.createStatement();
				st.execute(sql);
			}
			return 1;
		}catch(Exception e){
			return 0;
		}
	}
	
}
