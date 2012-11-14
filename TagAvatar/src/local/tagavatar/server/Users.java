package local.tagavatar.server;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.*;
import org.apache.commons.codec.digest.DigestUtils;

import com.mysql.jdbc.PreparedStatement;

public class Users {
	
	private Connection con;
	
	public Users(){
		Settings s=new Settings();
		this.con=s.get_connection();
	}
	
	public String create(String name, String email, String username, String password){
		try{
			MessageDigest digest = MessageDigest.getInstance("MD5");
	        
	        //Update input string in message digest
	        digest.update(password.getBytes(), 0, password.length());
	 
	        //Converts message digest value in base 16 (hex) 
	        password = new BigInteger(1, digest.digest()).toString(16);
			String sql="INSERT INTO users(name,email,username,password,bio,url,location) VALUES('"+name+"','"+email+"','"+username+"','"+password+"','','','')";
			Statement st=this.con.createStatement();
			st.executeUpdate(sql);
			return "User added successfully!";
		}catch(SQLException e){
			return e.getMessage();
		}catch(NoSuchAlgorithmException e2){
			return "No such algo!";
		}
	}
	
	
	
}
