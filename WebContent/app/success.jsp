<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>BUnit</title>
	<link rel="shortcut icon" href="img/title_logo.png">
	<link href="css/lib/bootstrap.css" type="text/css" rel="stylesheet">
	<link href="css/lib/jquery-ui.css" type="text/css" rel="stylesheet">
	<link href="css/lib/jquery.ui.all.css" type="text/css" rel="stylesheet">
	<link href="css/style.css" type="text/css" rel="stylesheet">
	<link href="css/responsive.css" type="text/css" rel="stylesheet">
	<link href="https://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css rel="stylesheet">
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
                   <div class="scenarioIdDiv">Scenario-ID: 45 Date:01/20/2016 10:10</div>
                   <div class="actionDiv"><div class="configScenario">Config Scenario</div><div>Run Scenarion</div>
                    </div>
                    <div>
                    <table id="example" class="display nowrap" cellspacing="0" width="100%">
        <thead>
            <tr>
                 <th>&nbsp;</th>
                 <th>Action</th>
			    <th>Opcode</th>
			    <th>Action-ID</th>
			    <th>Status</th>
			    <th>Opcode</th>
			    <th>Action-ID</th>
			  
            </tr>
        </thead>
         <tbody>
            <tr>
                 <td>&nbsp;</td>
                <td>Tiger Nixon</td>
                <td>System Architect</td>
                <td>Edinburgh</td>
                <td>61</td>
                <td>2011/04/25</td>
                <td>$320,800</td>
            </tr>
            <tr> 
                 <td>&nbsp;</td>
                <td>Garrett Winters</td>
                <td>Accountant</td>
                <td>Tokyo</td>
                <td>63</td>
                <td>2011/07/25</td>
                <td>$170,750</td>
            </tr>
            <tr>
                 <td>&nbsp;</td>
                <td>Ashton Cox</td>
                <td>Junior Technical Author</td>
                <td>San Francisco</td>
                <td>66</td>
                <td>2009/01/12</td>
                <td>$86,000</td>
            </tr>
         
            </table>
                       
        </div>
    </div> 
      <div class="rightContainer">
                  <div class="margin6"> ACTION WIDGETS</div>
                  <div class="actionRightDiv">
                  <div style="width:20px;float:left">&nbsp;</div>
                   <div style="float:left">Action Description</div>
                   <div style="float:right;padding-right: 100px;">Opcode</div>
                  </div>
                  <div class="SlectedItemsDiv" style="background-color:#E0ECFF">
                     <div id="id1" style="padding: 10px 0px; border: 1px solid;border-top:none;">
                        <div style="float: left; width: 20px;" class="openCloseTerms moduleClose">&nbsp</div>
                         <div>Accounxxxx t</div>
                     </div>
                      <div class="toggleDiv"></div>
                     <div id="id2" style="padding: 10px 0px; border: 1px solid;border-top:none;"> <div style="float: left; width: 20px;" class="openCloseTerms moduleClose">&nbsp</div>
                         <div>YYYYYYYYYYY</div></div>
                         <div class="toggleDiv"></div>
                    <div id="id3" style="padding: 10px 0px; border: 1px solid;border-top:none;"><div style="float: left; width: 20px; " class="openCloseTerms moduleClose">&nbsp</div>
                         <div>QQQQQQQQQ</div></div>
                         <div class="toggleDiv"></div>
                  </div>
       
    </div>
      </div>
   </div>
   <!-- <div class="parent">
    <div class="child"></div>
</div> -->

<script type="text/javascript">

</script>
<%@ include file="/app/inc/footer.jsp" %>
