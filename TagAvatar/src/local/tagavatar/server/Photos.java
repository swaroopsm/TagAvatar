package local.tagavatar.server;

import java.sql.*;

import local.tagavatar.server.Settings;

public class Photos {
	
	private Connection con;
	
	public Photos(){
		Settings s=new Settings();
		this.con=s.get_connection();
	}
	
	
}
