package local.tagavatar.server;

import java.sql.*;
import local.tagavatar.server.Settings;

public class Dislikes {
	
	private Connection con;
	
	public Dislikes(){
		Settings s=new Settings();
		this.con=s.get_connection();
	}
	
}
