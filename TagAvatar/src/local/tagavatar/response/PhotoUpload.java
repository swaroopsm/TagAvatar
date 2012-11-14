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
import local.tagavatar.server.Photos;

public class PhotoUpload extends HttpServlet {
   
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
   
   public PhotoUpload() {
       super();
       // TODO Auto-generated constructor stub
   }
   
   public void init( ){
      // Get the file location where it would be stored.
      filePath =  getServletContext().getInitParameter("img-upload"); 
      thumbPath = getServletContext().getInitParameter("img-upload-thumb");    
   }
   protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, java.io.IOException {
      // Check that we have a file upload request
	  java.io.PrintWriter out = response.getWriter( );
      isMultipart = ServletFileUpload.isMultipartContent(request);
      if( !isMultipart ){
         
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
      String val[]=new String[2];
      int c=0;
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
            fname=fname+d.getTime();
            fname=DigestUtils.md5Hex(fname);
            file=new File(filePath + fname + "." +ext);
            fi.write(file);
            BufferedImage image=ImageIO.read(new File(filePath + fname+"."+ext));
            BufferedImage thumbnail=Scalr.resize(image, 180);
            File thumb=new File(thumbPath + fname + "." + ext);
            ImageIO.write(thumbnail, ext, thumb);
         }
         else{
        	 if(c<2){
        		 val[c]=fi.getString().toString();
            	 c++;
        	 }
         }
      }
      HttpSession se=request.getSession();
      Photos p=new Photos();
      response.setContentType("text/html");
      JSONObject json=new JSONObject();
      if(p.create(val[0], val[1], fname+"."+ext, (String) se.getAttribute("username"))){
    	  json.put("stat", true);
    	  json.put("pic", fname+"."+ext);
    	  out.println(json.toString());
      }
      else{
    	  json.put("stat", false);
    	  out.println(json.toString());
      }
   }catch(Exception ex) {
       System.out.println(ex);
   }
      
   }
   
   protected void doGet(HttpServletRequest request, 
                       HttpServletResponse response)
        throws ServletException, java.io.IOException {
        
        throw new ServletException("GET method used with " +
                getClass( ).getName( )+": POST method required.");
   } 
}