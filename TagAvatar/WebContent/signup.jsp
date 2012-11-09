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

  <body>

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
            <ul class="nav">
              <li class="active"><a href="#">Home</a></li>
              <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
            </ul>
            <div class="nav pull-right">
             	<a href="/login"><button class="btn btn-success" style="font-size: 13px;">Login</button></a> &nbsp; <a href="signup.jsp" style="font-size: 11px;color: #888;">New Account?</a>
             </div>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

<br>
		<center><div id="js-messages" class="span11"></div></center>
		<div class="well" style="height: 350px;">
			<center><form id="signup_form" class="form" method="POST">
				<legend>New to TagAvatar?</legend>
				<div class="control-group">
					<div class="controls">
						<input type="text" id="inputName" placeholder="Full Name" class="span5">
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<input type="text" id="inputEmail" placeholder="Email" class="span5">
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<input type="password" id="inputPassword" placeholder="Password" class="span5">
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<input type="text" id="inputUsername" placeholder="Username" class="span5">
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<button type="submit" class="btn btn-success">Start Cracking &raquo;</button>
						<div><img id="loader" style="margin-top: 10px;display: none;" src="img/loader.gif" /></div>
					</div>
				</div>
			</form></center>
		</div>
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jquery.js"></script>
	<script src="js/tagavatar.js"></script>
  </body>
</html>