
<!DOCTYPE html>
<html>
<!--
   This is a jQuery Tools standalone demo. Feel free to copy/paste.
   http://flowplayer.org/tools/demos/

   Do *not* reference CSS files and images from flowplayer.org when in
   production Enjoy!
-->
<head>
  <title>TagAvatar</title>

    <!-- include the Tools -->
  <script src="js/jquery.tools.min.js"></script>
  
  <!-- standalone page styling (can be removed) -->
  <link rel="stylesheet" type="text/css" href="css/scrollable.css" />
<link rel="stylesheet" type="text/css" href="css/scroll-buttons.css" />

<style>
  /* styling for the image wrapper  */
#image_wrap {
    /* dimensions */
    opacity: 0.5;
    margin: 0px auto;
	//position: absolute;
    /* centered */
    text-align:center;
	//background-color: #222;
    /* some "skinning" */
    //background-image: -webkit-gradient(linear, 0% 0%, 0% 100%, from(black), to(#222));
    -moz-ouline-radius:4px;
    border-radius: 10px;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
    -o-border-radius: 10px;
}

#image_wrap img{
	max-width: 980px;
}

#img_title{
	display: block;
	color: white;
	text-align: center;
	padding-top: 10px;
	padding-bottom: 30px;
	font-family: 'Verdana';
	text-shadow: -1px 1px 21px #773277, 1px 0px 17px ghostWhite;
	font-size: 16px;
}

.home_link{
	font-family: "Verdana";
	color: #15BDFF;
	text-decoration: none;
	font-size: 13px;
}

a.home_link:hover{
	text-decoration: underline;
}

</style>
</head>
<body style="overflow-x: hidden;background: #000; //width: 980px; //margin: 0px auto;"><!-- wrapper element for the large image -->
<% try{
	if(session.getAttribute("loggedin").toString().equals("true")){	
		String param=request.getParameter("username");
%>
}
<div class="scrollable" id="scrollable" style="//width: 1000px;">

  <!-- root element for the items -->
  <div class="items">

    <!-- 1-5 -->
    

  </div>

</div>

<span class="home_link" style="margin-left: 30px;">&laquo; <a class="home_link" href="user.jsp">Home</a></span>

<!-- HTML structures -->
<div style="margin:0 auto; width: 980px; height:120px;">
<!-- "previous page" action -->

<!-- root element for scrollable -->
<div id="image_wrap" style="margin-top: 15px;//margin-right: 100px;">
  <!-- Initially the image is a simple 1x1 pixel transparent GIF -->
  <img src="img/loader.gif" width="" height="" />
</div>
<div id="img_title"></div>


<!-- "next page" action -->
<a class="next browse right"></a>
</div>
<%
}
else{
	response.sendRedirect("photos");
}
}catch(Exception e){
	response.sendRedirect("photos");
}
%>
<script>
$(document).ready(function(){
$(function() {
	var user=document.URL.split("?")[1].split("=")[1];
	var last_char=(user.substring(user.length-1,user.length));
	if(last_char=='#')
		user=user.substring(0,user.length-1);
	$.post("user_gallery", {username: user}, function(data){
		var obj=$.parseJSON(data);
		$(".items").html();
		var noItems=Math.ceil((obj.length)/8);
		for(var j=0;j<noItems;j++){
			$(".items").append("<div id='group"+j+"'></div>").show();
		}
		var t=8,z=0;
		for(var i=0;i<obj.length;i++){
			if(i<t){
				$("#group"+z).append("<a href='#'><img src='/images/thumbnails/"+obj[i].photo+"' data-large='/images/"+obj[i].photo+"' data-title='"+obj[i].title+"'></img></a>");
			}
			else{
				i--;
				t=t+8;
				z++;
			}
		}
		$(".items").hide().fadeIn(500);
		$("#img_title").html(obj[0].title);
		$("#image_wrap").html("<img src='/images/"+obj[0].photo+"'></img>").hide().fadeTo(500,"1.0");
	});
$(".scrollable").scrollable();

$(".items img").live("click", function() {
	// see if same thumb is being clicked
	if ($(this).hasClass("active")) { return; }

	// calclulate large image's URL based on the thumbnail URL (flickr specific)
	var url = $(this).attr("data-large");

	// get handle to element that wraps the image and make it semi-transparent
	var wrap = $("#image_wrap").fadeTo("medium", 0.5);

	// the large image from www.flickr.com
	var img = new Image();


	// call this function after it's loaded
	img.onload = function() {

		// make wrapper fully visible
		wrap.fadeTo("fast", 1);

		// change the image
		wrap.find("img").attr("src", url);
	
	};
	$("#img_title").html($(this).attr("data-title")).hide().fadeIn(500);
	// begin loading the image from www.flickr.com
	img.src = url;

	// activate item
	$(".items img").removeClass("active");
	$(this).addClass("active");

// when page loads simulate a "click" on the first image
}).filter(":first").click();
});
});
</script>
</html>