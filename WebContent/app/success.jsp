<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Transit</title>
	<link rel="shortcut icon" href="img/title_logo.png">
	<link href="css/lib/bootstrap.css" type="text/css" rel="stylesheet">
	<link href="css/style.css" type="text/css" rel="stylesheet">
	<link href="css/responsive.css" type="text/css" rel="stylesheet">
	<link href="css/lib/font-awesome.min.css" type="text/css" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800|Sanchez" rel="stylesheet">
	<script> window.history.forward(1);</script>
</head>
<body>

<%@ include file="/app/inc/header.jsp" %>

<div id="container">
	<div class="marginTop25 header">
	 <div style="text_align:center;"><h4>BUNIT BRM TEST FRAMEWORK</h4></div>
	 </div>
   <div class="navBarMenu">
    <nav class="navbar navbar-inverse">
  <div class="container-fluid">
      <ul class="nav navbar-nav">
     
       <li class="dropdown homeDropDown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Home
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Page 1</a></li>
          <li><a href="#">Page 2</a></li>
        </ul>
      </li>
      
      <li class="dropdown testdropDown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Test Scenario 1
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Page 1</a></li>
          <li><a href="#">Page 2</a></li>
        </ul>
      </li>
      <li class="dropdown configDropDown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Config
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Page 1</a></li>
          <li><a href="#">Page 2</a></li>
        </ul>
      </li>
       <li class="dropdown helpDropDown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Help
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Page 1</a></li>
          <li><a href="#">Page 2</a></li>
         
        </ul>
      </li>
      <li class="dropdown aboutDropDown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">About
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Page 1</a></li>
          <li><a href="#">Page 2</a></li>
          </ul>
      </li>
     </ul>
  </div>
</nav>
   </div>
   <div class="bottomBorder">&nbsp;</div>
   <div style="width:1100px;">
      <div class="leftContainer">
                   <div class="margin6">SCENARIO BUILD AREA</div>
      </div>
      <div class="rightContainer">
                  <div class="margin6"> ACTION WIDGETS</div>
      </div>
   </div>
</div>


<%@ include file="/app/inc/footer.jsp" %>
