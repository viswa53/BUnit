<%@page import="java.io.File"%>
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
	 <script type="text/javascript" src="js/lib/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/lib/jquery.easyui.min.js"></script>
    
      <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
	<!--<link type="text/css" rel="stylesheet" href="css/lib/normalize.css">
    <link type="text/css" rel="stylesheet" href="css/lib/layout-default-latest.css"> -->
	<link href="https://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css rel="stylesheet">
	<link href="css/lib/font-awesome.min.css" type="text/css" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800|Sanchez" rel="stylesheet">
	 <script type="text/javascript" src="http://www.jeasyui.com/easyui/datagrid-detailview.js"></script>
	<script> window.history.forward(1);</script>

</head>
<script type="text/javascript">
<% String str = System.getProperty("catalina.base") + "\\BUNIT\\BRMTestScenario001"; %>
var filePath = "<%= str %>";
window.alert(filePath);
</script>
 <div style="margin:5px 0;"></div>
    <div class="easyui-layout" style="fit=true;width:100%;height:575px;">
  
                   <div data-options="region:'north'" style="height:100px; text-align:center"><h5>BUINT BRM TEST FRAMEWORK</h5> 
         
        <!--  this is menu code -->
   
    <div style="margin:20px 0;"></div>
    <div class="easyui-panel" style="padding:5px;background:#00acc8;"> <!-- #00acc8 -->
        
        <a href="#" class="easyui-menubutton" data-options="menu:'#mm1'">Home</a>
        <a href="#" class="easyui-menubutton" data-options="menu:'#mm2'">Test Scenario</a>
        <a href="#" class="easyui-menubutton" data-options="menu:'#mm3'">Config</a>
        <a href="#" class="easyui-menubutton" data-options="menu:'#mm4',iconCls:'icon-help'">Help</a>
        <a href="#" class="easyui-menubutton" data-options="menu:'#mm5'">About</a>
    </div>
    
        <div id="mm1" style="width:150px;">
        <div data-options="iconCls:'icon-undo'">Login</div>
        <div data-options="iconCls:'icon-redo'">Logout</div>
        </div>
        
        <div id="mm2" style="width:300px;">
        <div data-options="iconCls:'icon-undo'">New Test Scenario</div>
        <div data-options="iconCls:'icon-redo'">Open Test Scenario</div>
         </div> 
         
         <div id="mm3" style="width:300px;">
        <div data-options="iconCls:'icon-undo'">Open Infranet properties</div>
        <div data-options="iconCls:'icon-redo'">Test BRM Connectivity</div>
        <div data-options="iconCls:'icon-redo'">Refresh BRM DB Schema</div>
         </div> 
        
         <div id="mm4" style="width:300px;">
        <div data-options="iconCls:'icon-undo'">How to Create Test Scenario</div>
        <div data-options="iconCls:'icon-redo'">How to Config Action Parameters</div>
        </div>
         
        <div id="mm5" class="menu-content" style="background:#f0f0f0;padding:10px;text-align:left">
        <img src="http://www.jeasyui.com/images/logo1.png" style="width:150px;height:50px">
        <p style="font-size:14px;color:#444;">BRM TESTING </div>
                
         </div> 
           <div data-options="region:'south',split:true" style="height:100px; text-align:center"><h5>TEST RESULTS - LOGS</h5></div>
        <div data-options="region:'east',split:true" title="ACTION WIDGETS" style="width:300px;">
       
    </body>
</html>