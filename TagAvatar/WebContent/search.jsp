<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>TagAvatar</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Sony" >

    <!-- Le styles -->
    <link href="css/tagavatar.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="shortcut icon" href="../assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
  </head>


  <body class="body_background" style='overflow-y: scroll;'>
    
	<%
		try{
			if(session.getAttribute("loggedin").toString().equals("true")){
			
	%>
	<%@page import="local.tagavatar.server.Users" %>
	<%
		Users u = new Users();
		u.my_info((String) session.getAttribute("username"));
		String avatar=u.get_avatar();
		String full_pic;
		if(avatar == null){
			avatar="<i class='icon-user'></i>";
		}else{
			avatar="<img src='/images/avatars/small/"+avatar+"' style='max-height: 25px;'/>";
		}
	%>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">TagAvatar</a>
          <div class="nav-collapse collapse">
          </div><!--/.nav-collapse -->
          <ul class="nav pull-right">
          	 <li>
          	 	<form class="navbar-search pull-right" id="searchForm" action="search.jsp" style="//margin-left: 250px;" method="get">
              		<input type="text" class="search-query span2" placeholder="Search" id="q" name="q">
           		</form>
          	 </li>
          	 <li class="divider-vertical"></li>
          	  <li>
          	  	<a href="#photoModal" id="photoModalLink" data-toggle="modal" style="position: relative; padding-top: 12px;" title="Add a Photo!" >
	          	  	<div style="font-size: 20px;">
	  					<i class="icon-camera icon-white" style="background-position: 30px;"></i>
					</div>
				</a>
          	  </li>
             <li class="divider-vertical"></li>
             <li class="dropdown">
          	  <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="font-size: 13px;"><% out.println(avatar); %>&nbsp;<% out.println(session.getAttribute("username")); %><b class="caret"></b></a>
              <ul class="dropdown-menu" style="font-size: 13px;">
              	<li><a href="user.jsp">Home</a></li>
                <li><a href="profile">Profile</a></li>
                <li><a href="photos">My Photos</a></li>
                <li><a href="account.jsp">Account</a></li>
                <li class="divider"></li>
                <li><a href="logout">Log out!</a></li>
              </ul>
             </li>
          </ul>
        </div>
      </div>
    </div>

    <div class="container user_dashboard" id="container">

<br>
		<div id="photoContainer">
			
		</div> <!-- /photoContainer -->
		
		<!-- Photo Modal -->
			<div id="photoModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="photoModalLabel" aria-hidden="true">
			  <div class="modal-header">
			    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			    <h3 id="photoModalLabel">Add new photo</h3>
			  </div>
			   <div class="modal-body" id="afterUpload" style="display: none;"></div>
			   <form class="form-horizontal" id="photoUploadForm" method="POST" action="PhotoUpload" enctype="multipart/form-data">
			  <div class="modal-body">
				  <div class="control-group">
				    <div class="controls">
				      <input type="text" id="photoTitle" name="photoTitle" placeholder="Title">
				    </div>
				  </div>
				  <div class="control-group">
				    <div class="controls">
				      <textarea rows="4" class="span3" id="photoDesc" name="photoDesc" placeholder="Description"></textarea>
				    </div>
				  </div>
				  <div class="control-group">
				    <div class="controls">
				      <input type="file" id="file" name="file">
				    </div>
				  </div>
			  </div>
			  <div class="modal-footer">
			    <input type="submit" class="btn btn-success" id="uploadPhotoButton" value="Upload Photo &raquo;" />
			    <center><div id="loader" style="display: none;"><img src="img/loader.gif" style="position: absolute;"/></div></center>
			  </div>
			  </form>
			</div>
		<!-- End Photo Modal -->
		
		
		<!-- Large Photo Modal -->
			<div id="largePhotoModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="photoModalLabel" aria-hidden="true">
			  <div class="modal-header">
			    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>			    
			  </div>
			   <div class="modal-body">
				  <img src="/images/dfccdeb9eda2e2d8fe58a86f262681de.png" id="largePhotoImg" />
			  </div>
			  <div class="modal-footer">			    
			    <center><div id="loader" style="display: none;"><img src="img/loader.gif" style="position: absolute;"/></div></center>
			  </div>
			  
			</div>
		<!-- End Large Photo Modal -->
		
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jquery.js"></script>
	<script src="js/jquery.form.js"></script>
	<script src="js/tagavatar.js"></script>
	<script>

	function searchImg(){
		console.log("this it test");
	}
		$(document).ready(function(){
			var queryString=document.URL.split("?")[1].split("=")[1];
			var last_char=(queryString.substring(queryString.length-1,queryString.length));
			if(last_char=='#')
				queryString=queryString.substring(0,queryString.length-1);
			
			// get photos via ajax and display them
			$.post("searchPhotos", {title:queryString}, function(data){
				var obj = $.parseJSON(data);
				console.log(obj);
				$("#photoContainer").html("<legend> Search results for '"+queryString+"' </legend>");
				for(var i=0;i<obj.length;i++)					
					$("#photoContainer").append("<a href='#' class='searched_img'><img class = 'thumbnail' style='display:block;float:left;margin-right:20px;margin-bottom:10px;max-width: 180px;min-height: 140px;max-height: 140px;' id='searchImg' src='/images/thumbnails/"+obj[i].photo+"'/></a>").hide();
				$("#photoContainer").fadeIn(300);
			});
			
			$("#searchForm").submit(function(){
				var searchTitle = $("#titleString").val();
				$.post("searchPhotos", {title:searchTitle}, function(data){
					var obj = $.parseJSON(data);
					console.log(obj);					
				});
				
			});	
			
			$(".searched_img").live("mouseover", function(){
				var a=($(this).children()[0]);
				$(a).css({
					zoom: '120%'
				});
			});
			
			$(".searched_img").live("mouseout", function(){
				var a=($(this).children()[0]);
				$(a).css({
					zoom: '100%'
				});
			});
			
		});
	</script>
	<%
		}
			else{
				response.sendRedirect("login.jsp");
			}	
		}
		catch(Exception e){
			response.sendRedirect("login.jsp");
		}
	%>
  </body>
</html>