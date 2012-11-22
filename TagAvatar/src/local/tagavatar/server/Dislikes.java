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
	
	public int get_dislikes(int photo){
		String sql="SELECT * FROM dislikes WHERE photo="+photo;
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
	
	public int i_dislike(int photo, String username){
		String sql="SELECT * FROM dislikes WHERE photo="+photo+" AND user_id='"+username+"'";
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
	
	public int delete(int photo, String username){
		String sql="DELETE FROM dislikes WHERE photo="+photo+" AND user_id='"+username+"'";
		try{
			Statement st=this.con.createStatement();
			st.executeUpdate(sql);
			return 1;
		}catch(Exception e){
			return 0;
		}
	}
	
}
