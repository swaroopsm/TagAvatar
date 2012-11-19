<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>TagAvatar</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

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

  <body class="body_background">
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
		if(avatar.equals("")){
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
          	 	<form class="navbar-search pull-right" action="" style="//margin-left: 250px;">
              		<input type="text" class="search-query span2" placeholder="Search" >
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
                <li class="divider"></li>
                <li><a href="logout">Log out!</a></li>
              </ul>
             </li>
          </ul>
        </div>
      </div>
    </div>

    <div class="container user_dashboard">

<br>
		<div class="rows">
			<div id="latest_pic_div">
				<div class="span6">
				<div id="random_pic" style="max-height: 400px; max-width: 400px;">
					<center><img src="img/loader.gif" style="margin-left: 200px;position: absolute;"/></center>
				</div>
			</div>
			<div class="span5 well" style="display: none;" id="pic_info">
				<h4 id="pic_title">Photo Title</h4>
				<hr class="adjust">
				<p id="pic_desc">Photo Description...</p>
			</div>
			</div>
			<div class="span12">
				<hr style="width: 900px;">
			</div>
		</div>
		
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
		
		
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jquery.js"></script>
	<script src="js/jquery.form.js"></script>
	<script src="js/tagavatar.js"></script>
	<script>
		$(document).ready(function(){
			$.post("random_pic", function(data){
				var obj=$.parseJSON(data);
				var like_link_full="<a href='#' id='like_btn' class='like_btn' title='Like'><i class='icon-thumbs-up icon-white'> &nbsp;</i></a> ";
				console.log(obj);
				if(obj.ilike>0){
					like_link_full="<a href='#' id='' class='like_btn' title='You like this!'><i class='icon-thumbs-up icon-white'> &nbsp;</i></a> ";
				}
				$("#random_pic").html("<img id='my_img' src='/images/"+obj.photo+"' class='thumbnail' style='max-width: 400px;'></img>");
				$("#pic_title").html(obj.title+"<p style='font-size: 11px;' id='photo_id' data-photo='"+obj.photo_id+"'> by <a href='#'>"+obj.username+"</a></p><p>"+like_link_full+"<span class='like_dislike_count' id='likes_count'>"+obj.likes+"</span>&nbsp;&nbsp;<a href='#' title='Dislike' id='dislike_btn' class='dislike_btn'><i class='icon-thumbs-down icon-white'></i></a></p>");
				$("#pic_desc").html(obj.desc);
				$("#random_pic").hide().fadeIn(300);
				$("#my_img").ready(function(){
					$("div#pic_info").fadeIn(300);
				})
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