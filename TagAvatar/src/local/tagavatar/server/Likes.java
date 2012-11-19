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
}
