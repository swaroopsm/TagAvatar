package local.tagavatar.server;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.*;
import org.apache.commons.codec.digest.DigestUtils;

import com.mysql.jdbc.PreparedStatement;

public class Users {
	
	public String create(String name, String email, String username, String password){
		Settings s=new Settings();
		try{
			MessageDigest digest = MessageDigest.getInstance("MD5");
	        
	        //Update input string in message digest
	        digest.update(password.getBytes(), 0, password.length());
	 
	        //Converts message digest value in base 16 (hex) 
	        password = new BigInteger(1, digest.digest()).toString(16);
			String sql="INSERT INTO users(name,email,username,password,bio,url,location) VALUES('"+name+"','"+email+"','"+username+"','"+password+"','','','')";
			//PreparedStatement pt=(PreparedStatement) s.get_connection().prepareStatement(sql);
			//pt.setString(1, name);
			//pt.setString(2, email);
			//pt.setString(3, username);
			//pt.setString(4, password);
			Statement st=s.get_connection().createStatement();
			//Statement st=s.get_connection().createStatement(sql);
			st.executeUpdate(sql);
			return "User added successfully!";
		}catch(SQLException e){
			return e.getMessage();
		}catch(NoSuchAlgorithmException e2){
			return "No such algo!";
		}
	}
	
}
