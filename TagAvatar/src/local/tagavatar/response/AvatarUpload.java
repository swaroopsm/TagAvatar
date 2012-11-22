package local.tagavatar.response;

import java.awt.Image;
import org.json.JSONObject;
import java.awt.image.BufferedImage;
import java.io.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.Timestamp;
import java.util.*;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;
import org.imgscalr.Scalr;
import org.apache.commons.*;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import sun.org.mozilla.javascript.json.JsonParser;
import local.tagavatar.server.Users;

public class AvatarUpload extends HttpServlet {
   
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isMultipart;
   private String filePath;
   private String thumbPath;
   private int maxFileSize = 1000 * 1024;
   private int maxMemSize = 4 * 1024;
   private File file ;
   
   public AvatarUpload() {
       super();
       // TODO Auto-generated constructor stub
   }
   
   public void init( ){
      // Get the file location where it would be stored.
      filePath =  getServletContext().getInitParameter("avatar-upload"); 
      thumbPath = getServletContext().getInitParameter("avatar-upload-small");    
   }
   protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, java.io.IOException {
      // Check that we have a file upload request
	  java.io.PrintWriter out = response.getWriter( );
      isMultipart = ServletFileUpload.isMultipartContent(request);
      if( !isMultipart ){
         out.println("Not multipart!!");
         return;
      }
      DiskFileItemFactory factory = new DiskFileItemFactory();
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File("/tmp/"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );
      String fname=null;
      String ext=null;
      HttpSession se=request.getSession();
      String username=((String) se.getAttribute("username"));
      try{ 
      // Parse the request to get file items.
      List fileItems = upload.parseRequest(request);
	
      // Process the uploaded file items
      Iterator i = fileItems.iterator();
    
      while ( i.hasNext () ) 
      {
         FileItem fi = (FileItem)i.next();
         if ( !fi.isFormField () )	
         {
            // Get the uploaded file parameters
            String fieldName = fi.getFieldName();
            String fileName = fi.getName();
            String contentType = fi.getContentType();
            boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();
            // Write the file
            int mid= fileName.lastIndexOf(".");
            fname=fileName.substring(0,mid);
            ext=fileName.substring(mid+1,fileName.length());
            Date d=new Date();
            fname=fname+d.getTime()+username;
            fname=DigestUtils.md5Hex(fname);
            file=new File(filePath + fname + "." +ext);
            fi.write(file);
            BufferedImage image2=ImageIO.read(new File(filePath + fname+"."+ext));
            BufferedImage resized=Scalr.resize(image2, 350);
            File resized_avatar=new File(filePath + fname + "." + ext);
            ImageIO.write(resized, ext, resized_avatar);
            
            BufferedImage image=ImageIO.read(new File(filePath + fname+"."+ext));
            BufferedImage thumbnail=Scalr.resize(image, 35);
            File thumb=new File(thumbPath + fname + "." + ext);
            ImageIO.write(thumbnail, ext, thumb);
         }
         else{
        	 out.println("Error!");
         }
      }
      Users u=new Users();
      //response.setContentType("text/html");
      JSONObject json=new JSONObject();
      if(u.update_avatar(username, fname+"."+ext, filePath, thumbPath)){
    	  json.put("status", true);
          json.put("photo", fname+"."+ext);
          out.println(json.toString());
      }else{
    	  json.put("status", false);
    	  out.println(json.toString());
      }
   }catch(Exception ex) {
       JSONObject j=new JSONObject();
       out.println((j.put("status", false)).toString());
   }
      
   }
   
   protected void doGet(HttpServletRequest request, 
                       HttpServletResponse response)
        throws ServletException, java.io.IOException {
        
        throw new ServletException("GET method used with " +
                getClass( ).getName( )+": POST method required.");
   } 
}