package local.tagavatar.server;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItemIterator;

import com.mysql.jdbc.PreparedStatement;
import org.json.*;

public class Users {
	
	private String name;
	private String email;
	private String bio;
	private String url;
	private String location;
	private String avatar;
	private Connection con;
	
	public Users(){
		Settings s=new Settings();
		this.con=s.get_connection();
	}
	
	public void set_name(String arg){
		this.name = arg;
	}
	
	public void set_email(String arg){
		this.email = arg;
	}
	
	public void set_bio(String arg){
		this.bio = arg;
	}
	
	public void set_url(String arg){
		this.url = arg;
	}
	
	public void set_location(String arg){
		this.location = arg;
	}
	
	public void set_avatar(String arg){
		this.avatar = arg;
	}
	
	public String get_name(){
		return this.name;
	}
	
	public String get_email(){
		return this.email;
	}
	
	public String get_bio(){
		return this.bio;
	}
	
	public String get_url(){
		return this.url;
	}
	
	public String get_location(){
		return this.location;
	}
	
	public String get_avatar(){
		return this.avatar;
	}
	
	public String create(String name, String email, String username, String password){
		JSONObject json=new JSONObject();
		try{
			int c=0;
			String sql2="SELECT * FROM users WHERE username='"+username+"'";
			Statement st2=this.con.createStatement();
			ResultSet rs2=st2.executeQuery(sql2);
			while(rs2.next()){
				c++;
			}
			if(c>0){
				json.put("status", false);
				json.put("message", "Username already exists");
				return json.toString();
			}
			else{
				MessageDigest digest = MessageDigest.getInstance("MD5");
		        
		        //Update input string in message digest
		        digest.update(password.getBytes(), 0, password.length());
		 
		        //Converts message digest value in base 16 (hex) 
		        password = new BigInteger(1, digest.digest()).toString(16);
				String sql="INSERT INTO users(name,email,username,password,bio,url,location) VALUES('"+name+"','"+email+"','"+username+"','"+password+"','','','')";
				Statement st=this.con.createStatement();
				st.executeUpdate(sql);
				json.put("status", true);
				json.put("message", "User added successfully. Login to continue...");
				return "";
			}
		}catch(SQLException e){
			return e.getMessage();
		}catch(NoSuchAlgorithmException e2){
			return "No such algo!";
		}
	}
	
	public String view(String my_username, String username){
		try{
			String sql="SELECT users.name,users.email,users.bio,users.url,users.location,following.username,following.following FROM users,following WHERE (users.username=following.following AND following.following='"+username+"' AND following.username='"+my_username+"')";
			Statement st=this.con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			JSONObject json=new JSONObject();
			while(rs.next()){
				json.put("name", rs.getString("name"));
				json.put("email", rs.getString("email"));
				json.put("bio", rs.getString("bio"));
				json.put("my_username", rs.getString("username"));
				json.put("username", rs.getString("following"));
				json.put("url", rs.getString("url"));
				json.put("location", rs.getString("location"));
			}
			return json.toString();
		}catch(Exception e){
			return null;
		}
	}
	
	public void my_info(String username){
		try{
			String sql="SELECT * FROM users WHERE `username`='"+username+"'";
			Statement st=this.con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				set_name(rs.getString("name"));
				set_email(rs.getString("email"));
				set_bio(rs.getString("bio"));
				set_url(rs.getString("url"));
				set_location(rs.getString("location"));
				set_avatar(rs.getString("avatar"));
			}
		}catch(Exception e){
			
		}
	}
	
	public String update(String username, String name, String email, String bio, String url, String location){
		String sql="UPDATE users SET `name`=?, `email`=?, `bio`=?, `url`=?, `location`=? WHERE `username`=?";
		JSONObject json=new JSONObject();
		try{
			PreparedStatement ps=(PreparedStatement) this.con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, bio);
			ps.setString(4, url);
			ps.setString(5, location);
			ps.setString(6, username);
			ps.executeUpdate();
			json.put("status", true);
			json.put("message", "Profile updated successfully..");
			return json.toString();
		}catch(Exception e){
			json.put("status", false);
			json.put("message", e.getMessage());
			return json.toString();
		}
	}
	
	public Boolean update_avatar(String username, String avatar, String filepath, String thumbpath){
		String sql="UPDATE users SET `avatar`='"+avatar+"' WHERE username='"+username+"'";
		String sql2="SELECT `avatar` FROM users	WHERE username='"+username+"'";
		try{
			String old_pic;
			Statement st2=this.con.createStatement();
			ResultSet rs=st2.executeQuery(sql2);
			while(rs.next()){
				old_pic=rs.getString("avatar");
				File file1=new File(filepath+old_pic);
				File file2=new File(thumbpath+old_pic);
				file1.delete();
				file2.delete();
			}
			Statement st=this.con.createStatement();
			st.executeUpdate(sql);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public String update_account(String username, String opwd, String npwd){
		String sql="SELECT `password` FROM users WHERE username='"+username+"'";
		JSONObject json=new JSONObject();
		String p=null;
		try{
			Statement st=this.con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			int c=0;
			while(rs.next()){
				p=rs.getString("password");
				c++;
			}
			if(c>0){
				if(p.equals(opwd)){
					String sql2="UPDATE users SET password='"+npwd+"' WHERE username='"+username+"'";
					try{
						Statement st2=this.con.createStatement();
						st.executeUpdate(sql2);
						json.put("status", true);
						json.put("message", "Password updated successfully..");
						return json.toString();
					}catch(Exception e){
						json.put("status", false);
						json.put("message", e.getMessage());
						return json.toString();
					}
				}
				else{
					json.put("status", false);
					json.put("message", "Old password is incorrect!");
					return json.toString();
				}
			}else{
				json.put("status", false);
				json.put("message", "Old password is incorrect!");
				return json.toString();
			}
		}catch(Exception e){
			json.put("status", false);
			json.put("message", e.getMessage());
			return json.toString();
		}
	}
	
}
