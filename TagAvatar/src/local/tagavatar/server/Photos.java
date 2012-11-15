package local.tagavatar.server;

import java.sql.*;

import local.tagavatar.server.Settings;
import org.json.*;

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
	
	public String user_photos(String username){
		String sql="SELECT `title`,`desc`,`photo` FROM photos WHERE user_id='"+username+"'";
		try{
			Statement st=this.con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			JSONArray json2=new JSONArray();
			while(rs.next()){
				//json2
				//json2.put(rs.getString("title"));
				JSONObject json=new JSONObject();
				json.put("title", rs.getString("title"));
				json.put("desc", rs.getString("desc"));
				json.put("photo", rs.getString("photo"));
				json2.put(json);
			}
			return json2.toString();
		}catch(Exception e){
			return e.getMessage();
		}
	}
	
}
