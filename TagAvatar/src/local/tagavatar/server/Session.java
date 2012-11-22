package local.tagavatar.server;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import local.tagavatar.server.Settings;

public class Session {
	
	public String login(String username,String pwd){
		try{
			MessageDigest digest = MessageDigest.getInstance("MD5");
	        
	        //Update input string in message digest
	        digest.update(pwd.getBytes(), 0, pwd.length());
	 
	        //Converts message digest value in base 16 (hex) 
	        pwd = new BigInteger(1, digest.digest()).toString(16);
	        Settings s=new Settings();
	        String sql="SELECT username, password FROM users WHERE username='"+username+"' AND password='"+pwd+"'";
	        Statement st=s.get_connection().createStatement();
	        ResultSet rs=st.executeQuery(sql);
	        int c=0;
	        while(rs.next()){
	        	c++;
	        }
	        if(c>=1)
	        	return "1";
	        else
	        	return "0";
		}catch(Exception e){
			return e.getMessage();
		}
	}
	
}
