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
	<!--<link type="text/css" rel="stylesheet" href="css/lib/normalize.css">
    <link type="text/css" rel="stylesheet" href="css/lib/layout-default-latest.css"> -->   
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
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Test Scenario
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a id="newScenario" href="#">New Scenario</a></li>
          <li><a id="openScenario" href="#">Open Scenario</a></li>
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
   <div style="width:1100px;" class="mainContainer">
      <div class="leftContainer">
                   <div class="margin6"><div class="checkImg">&nbsp;</div>SCENARIO BUILD AREA</div>
                   <div class="scenarioIdDiv">Scenario-ID: <span id="titleScenarioId"></span> 
                    <span id="titleScenarioDate">Date : </span>
                     </div>
                   <div class="actionDiv"><div class="plusImg">&nbsp;</div><div class="configScenario">Config Scenario</div><div class="minusImg">&nbsp;</div><div>Run Scenarion</div>
                    </div>
                    <div>
                    
                    <div class="search-table-outter wrapper">
	<table class="search-table inner">
		<tr>
			   <th>ScenarioID</th>
                <th>ActionId</th>
                <th>ActionId</th>
                <th>Input FList</th>
			    <th>Output FList</th>
			    <th>Status</th>
			    <th>Button</th>			        
            </tr>
		 
               
               </table>     
         <!--          <table id="example" class="display nowrap" cellspacing="0" width="100%">
        <thead>
            <tr>
            	<th>ScenarioID</th>
                <th>ActionId</th>
                <th>ActionId</th>
                <th>Input FList</th>
			    <th>Output FList</th>
			    <th>Status</th>
			    <th>Button</th>			        
            </tr>
        </thead>
         <tbody>
            <tr>
                <td id="scenarioId"></td>
                <td id="actionId"></td>
                <td id="actionDesc"></td>
                <td id="inputFList"></td>
                <td id="outputFList"></td>
                <td id="status"></td>
                <td id="button"></td>
            </tr>
         </tbody>
            </table> -->  
                       
        </div>
    </div> 
    </div>
      <div class="rightContainer">
          <div class="margin6"> ACTION WIDGETS</div>
             <div class="actionRightDiv">
                  <div style="width:20px;float:left">&nbsp;</div>
                   <div style="float:left">Action Description</div>
                   <div style="float:right;padding-right: 100px;">Opcode</div>
              </div>
              <div class="selectedItemsDiv" style="background-color:#E0ECFF">
               </div>
       
    </div>
    
   </div>
   </div>
   <div>
   <div class="clear"></div>
    <div id="bottomContainer">
      <div style=" margin-top: 10px;text-align: center;font-weight: bold;">TEST RESULTS LOGS</div>
    </div>
    </div>
   
   <!-- <div class="parent">
    <div class="child"></div>
</div> -->

<script type="text/javascript">

</script>
<%@ include file="/app/inc/footer.jsp" %>
