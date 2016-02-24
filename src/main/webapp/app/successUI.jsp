<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Basic Layout - jQuery EasyUI Demo</title>
    
    
   <!--  <link rel="stylesheet" type="text/css" href="icon.css">
    <link rel="stylesheet" type="text/css" href="demo.css"> -->
    <script type="text/javascript" src="js/lib/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/lib/jquery.easyui.min.js"></script>
    
    
       <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
   <!--   
     <script type="text/css" href="easyui.css"></script>
     <script type="text/css" href="icon.css"></script>
     <script type="text/css" href="demo.css"></script>
     -->
  
    <script type="text/javascript" src="js/lib/jquery.datagrid.js"></script>
    <script type="text/javascript" src="http://www.jeasyui.com/easyui/datagrid-detailview.js"></script>
      <script type="text/javascript" src="js/lib/jquery.etree.js"></script>
             
        <script>
        $(function(){
            $('.left .item').draggable({
                revert:true,
                proxy:'clone'
            });
            $('.right td.drop').droppable({
                accept: '.item',
                onDragEnter:function(){
                    $(this).addClass('over');
                },
                onDragLeave:function(){
                    $(this).removeClass('over');
                },
                onDrop:function(e,source){
                    $(this).removeClass('over');
                    if ($(source).hasClass('assigned')){
                        $(this).append(source);
                    } else {
                        var c = $(source).clone().addClass('assigned');
                        $(this).empty().append(c);
                        c.draggable({
                            revert:true
                        });
                    }
                }
            });
            $('.left').droppable({
                accept:'.assigned',
                onDragEnter:function(e,source){
                    $(source).addClass('trash');
                },
                onDragLeave:function(e,source){
                    $(source).removeClass('trash');
                },
                onDrop:function(e,source){
                    $(source).remove();
                }
            });
        });
    </script>
    
     <script type="text/javascript">
        $(function(){
        	$('#dlg').dialog('close');
        	$('#tdlg').dialog('close');
        	$('#pg').datagrid({url:'http://localhost:8080/bunit/rest/bunit/get_actions',
               method:'get',
               showGroup:true,
               scrollbarSize:0,
                columns:[[
                 		{field:'actionDesc',title:'Action Description',width:250,sortable:true},
              		    {field:'actionId',title:'Opcode',width:100,resizable:false}
                   ]]
        });
        	
        	$('#dg').datagrid({url:'http://localhost:8080/bunit/rest/bunit/open_scenario/BRMTestScenario1.xml',
                method:'get'
        	  });
       
             	
        	$('#mm2').delegate("#openScenario","click",function (){
        		$.ajax({
					url: "/bunit/rest/bunit/get_scenario",
					context: document.body
				}).done(function(response) {
					$('.modalContent').html("");
					for(var i =0; i<response.length;i++){
						$('.modalContent').append('<div style="margin: 10px;text-align: center;"><input  name="action" value='+response[i]+' type="radio">'+response[i]+'</div>');
					}
				});
        		$('#dlg').dialog('open');
        	 });
        	
        	
            $('#dg').datagrid({
                view: detailview,
                detailFormatter:function(index,row){
                    return '<div class="ddv"></div>';
                },
                onExpandRow: function(index,row){
                    var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
                    ddv.panel({
                        border:false,
                        cache:true,
                        href:'show_form.php?index='+index,
                        onLoad:function(){
                            $('#dg').datagrid('fixDetailRowHeight',index);
                            $('#dg').datagrid('selectRow',index);
                            $('#dg').datagrid('getRowDetail',index).find('form').form('load',row);
                        }
                    });
                    $('#dg').datagrid('fixDetailRowHeight',index);
                }
            });
        });
        function saveItem(index){
            var row = $('#dg').datagrid('getRows')[index];
            var url = row.isNewRecord ? 'save_user.php' : 'update_user.php?id='+row.id;
            $('#dg').datagrid('getRowDetail',index).find('form').form('submit',{
                url: url,
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(data){
                    data = eval('('+data+')');
                    data.isNewRecord = false;
                    $('#dg').datagrid('collapseRow',index);
                    $('#dg').datagrid('updateRow',{
                        index: index,
                        row: data
                    });
                }
            });
        }
        function cancelItem(index){
            var row = $('#dg').datagrid('getRows')[index];
            if (row.isNewRecord){
                $('#dg').datagrid('deleteRow',index);
            } else {
                $('#dg').datagrid('collapseRow',index);
            }
        }
        function inputListSelectedRow(){
        	setTimeout(function(){
        	var row = $('#dg').datagrid('getSelected');
        	if (row){
        		alert(row.actionId);
        		$('#tt').etree({
    				url: 'js/lib/tree_data.json'
    			});
        		$('#tdlg').dialog('open');
        	}
        	}, 500);
        	        	
   	   }
        function callingAction() {
        var selectedAction = $('input[name="action"]:checked').val();
        if(selectedAction){
   		 $('#dg').datagrid({url:'http://localhost:8080/bunit/rest/bunit/open_scenario/'+selectedAction,
                method:'get'
        	  });
   		 $('#dlg').dialog('close');
        } else {
        	alert("Please select radio button before cliking on ok button");
        }
   	 }
        function destroyItem(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.confirm('Confirm','Are you sure you want to remove this user?',function(r){
                    if (r){
                        var index = $('#dg').datagrid('getRowIndex',row);
                        $.post('destroy_user.php',{id:row.id},function(){
                            $('#dg').datagrid('deleteRow',index);
                        });
                    }
                });
            }
        }
        function newItem(){
            $('#dg').datagrid('appendRow',{isNewRecord:true});
            var index = $('#dg').datagrid('getRows').length - 1;
            $('#dg').datagrid('expandRow', index);
            $('#dg').datagrid('selectRow', index);
        }
    </script>
    <style type="text/css">
        form{
            margin:0;
            padding:0;
        }
        .dv-table td{
            border:0;
        }
        .dv-table input{
            border:1px solid #ccc;
        }
    </style>
    
    <!-- -- END OF AUMAMAHESH -->
    
</head>
<body>
    
    
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
        <div data-options="iconCls:'icon-redo'" id="openScenario">Open Test Scenario</div>
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
        
  
    <table id="pg" class="easyui-propertygrid" style="fit=true;width:100%;height:100%"  data-options="showGroup:true,scrollbarSize:0">
    </table>
        
         </div>
              <div id="uma" data-options="region:'center',title:'SCENARIO BUILD AREA',iconCls:'icon-ok'">
              

    <table id="dg" class="easyui-datagrid" style="fit=true;width:100%;height:100%"
            toolbar="#toolbar" 
            title="Scenario-ID:145  Date: 01/20/2016 10:10"
			fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="actionID" width="400">Action ID</th>
                <th field="scenarioID" width="300">Scenario ID</th>
                <th field="actionDescription" width="300">Action Description</th>
                <th field="inputFlist" width="400" >InputFlist</th>
                <th field="outputFlist" width="400">OutputFlist</th>
                <th field="status" width="400">Stauts</th>
                <th field="button" width="300" align="center">Button</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newItem()">Config Scenario</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyItem()">Run Scenario</a>
    </div>
 </div>
      <div id="dlg" class="easyui-dialog" title="Basic Dialog" data-options="
            iconCls:'icon-save',
            onResize:function(){
                $(this).dialog('center');
            } ,    buttons: [{
                    text:'Ok',
                    iconCls:'icon-ok',
                    handler:function(){
                       callingAction();
                    }
                },{
                    text:'Cancel',
                    handler:function(){
                       	$('#dlg').dialog('close');
                    }
                }]
            " style="width:400px;height:200px;padding:10px">
        <div class="modalContent"></div>
    </div>
    
     <div id="tdlg" class="easyui-dialog" title="Basic Dialog" data-options="
            iconCls:'icon-save',
            onResize:function(){
                $(this).dialog('center');
            }" style="width:600px;height:300px;padding:10px">
       <div style="margin-bottom:10px">
		<a href="#" onclick="javascript:$('#tt').etree('create')">Create</a>
		<a href="#" onclick="javascript:$('#tt').etree('edit')">Edit</a>
		<a href="#" onclick="javascript:$('#tt').etree('destroy')">Destroy</a>
	</div> <ul id="tt"></ul>
    </div>
    
  </div>      

</body>
</html>