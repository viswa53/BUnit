<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>BUNIT WEB</title>


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
        $(function() {
            $('.left .item').draggable({
                revert: true,
                proxy: 'clone'
            });
            $('.right td.drop').droppable({
                accept: '.item',
                onDragEnter: function() {
                    $(this).addClass('over');
                },
                onDragLeave: function() {
                    $(this).removeClass('over');
                },
                onDrop: function(e, source) {
                    $(this).removeClass('over');
                    if ($(source).hasClass('assigned')) {
                        $(this).append(source);
                    } else {
                        var c = $(source).clone().addClass('assigned');
                        $(this).empty().append(c);
                        c.draggable({
                            revert: true
                        });
                    }
                }
            });
            $('.left').droppable({
                accept: '.assigned',
                onDragEnter: function(e, source) {
                    $(source).addClass('trash');
                },
                onDragLeave: function(e, source) {
                    $(source).removeClass('trash');
                },
                onDrop: function(e, source) {
                    $(source).remove();
                }
            });
        });
    </script>

    <script type="text/javascript">
        $(function() {
            $('#dlg').dialog('close');
            $('#tdlg').dialog('close');
            $('#outputFlist').dialog('close');
            
            
            $.ajax({
                url: "testing.txt",
                context: document.body
            }).done(function(response) {
            	$('#logs').html(response);
            });
            
            
            
            $('#pg').datagrid({
                url: '/bunit/rest/bunit/get_actions',
                method: 'get',
                showGroup: true,
                scrollbarSize: 0,
                nowrap: false,

                columns: [
                    [{
                        field: 'actionId',
                        title: 'Action',
                        width: 250,
                        resizable: false
                    }, {
                        field: 'actionDesc',
                        title: 'Description',
                        width: 250,
                        sortable: true
                    }]
                ]
            });

            $('#dg').datagrid({
                url: '/bunit/rest/bunit/empty',
                method: 'get',
                rownumbers: true
            });


            $('#mm2').delegate("#openScenario", "click", function() {
                $.ajax({
                    url: "/bunit/rest/bunit/get_scenario",
                    context: document.body
                }).done(function(response) {
                    $('.modalContent').html("");
                    for (var i = 0; i < response.length; i++) {
                        $('.modalContent').append('<div style="margin: 10px;text-align: center;"><input  name="action" value=' + response[i] + ' type="radio">' + response[i] + '</div>');
                    }
                });
                $('#dlg').dialog('open');
            });

            //TODO newScenario
            $("#newScenario").click(function() {
                    $.ajax({
                        url: "/bunit/rest/bunit/new_scenario",
                        context: document.body
                    }).done(function(response) {

                        $('#dg').datagrid({
                            title: 'SCENARIO ID : <span class="title">' + response[0] + '</span> , Date : ' + response[1]
                        });
                    });
                    $('#dg').datagrid({
                        url: '/bunit/rest/bunit/empty',
                        method: 'get',
                        rownumbers: true
                    });
                }

            );

        });

        /* function saveItem(index) {
            var row = $('#dg').datagrid('getRows')[index];
            var url = row.isNewRecord ? 'save_user.php' : 'update_user.php?id=' + row.id;
            $('#dg').datagrid('getRowDetail', index).find('form').form('submit', {
                url: url,
                onSubmit: function() {
                    return $(this).form('validate');
                },
                success: function(data) {
                    data = eval('(' + data + ')');
                    data.isNewRecord = false;
                    $('#dg').datagrid('collapseRow', index);
                    $('#dg').datagrid('updateRow', {
                        index: index,
                        row: data
                    });
                }
            });
        } */

        /* function cancelItem(index) {
            var row = $('#dg').datagrid('getRows')[index];
            if (row.isNewRecord) {
                $('#dg').datagrid('deleteRow', index);
            } else {
                $('#dg').datagrid('collapseRow', index);
            }
        }
 */
        function inputListSelectedRow() {
            setTimeout(function() {
                var row = $('#dg').datagrid('getSelected');
                if (row) {

                    //TODO TREE
                    $('#test').treegrid({
                        url: '/bunit/rest/bunit/get_input_flist/' + row.actionID + '/' + row.scenarioID,
                        method: 'get',
                        title : '<div id="inputFlistId">' + row.actionID + '</div>',
                        treeField: 'name',
                        idField: 'name'
                    });


                    $('#tdlg').dialog('open');
                }
            }, 500);
        }

        function deleteSelectedRow() {
            setTimeout(function() {
                var row = $('#dg').datagrid('getSelected');
                if (row) {
                    //TODO TREE
                    $('#dg').datagrid({
                        url: '/bunit/rest/bunit/delete/' + row.actionID + '/' + row.scenarioID,
                        method: 'get'
                    });
                }
            }, 500);
        }

        function outputListSelectedRow() {
            setTimeout(function() {
                var row = $('#dg').datagrid('getSelected');
                if (row) {
                    //TODO TREE
                    $('#outputList').treegrid({
                        url: '/bunit/rest/bunit/get_output_flist/' + row.actionID + '/' + row.scenarioID,
                        method: 'get',
                        title : '<div id="outputFlistId">' + row.actionID + '</div>',
                        treeField: 'name',
                        idField: 'name'
                    });


                    $('#outputFlist').dialog('open');
                }
            }, 500);
        }


        function callingAction() {
            var selectedAction = $('input[name="action"]:checked').val();
            if (selectedAction) {
                $('#dg').datagrid({
                    url: '/bunit/rest/bunit/open_scenario/' + selectedAction,
                    method: 'get'
                });
                $.ajax({
                    url: "/bunit/rest/bunit/open_scenario_info/" + selectedAction,
                    context: document.body
                }).done(function(response) {
                    console.log(response);
                    // 			$('#dg').datagrid({title:'<div class="title">SCENARIO ID : '+response[0]+', 20/10/21016</div>'
                    $('#dg').datagrid({
                        title: 'SCENARIO ID : <span class="title">' + response[0] + '</span> , Date : ' + response[1]
                    });

                });
                $('#dlg').dialog('close');
            } else {
                alert("Please select radio button before cliking on ok button");
            }
        }

        function destroyItem() {
            var row = $('#dg').datagrid('getSelected');
            if (row) {
                $.messager.confirm('Confirm', 'Are you sure you want to remove this user?', function(r) {
                    if (r) {
                        var index = $('#dg').datagrid('getRowIndex', row);
                        $.post('destroy_user.php', {
                            id: row.id
                        }, function() {
                            $('#dg').datagrid('deleteRow', index);
                        });
                    }
                });
            }
        }

        var dragedId = null;
         //TODO On Drag		
         function OnDragStart(event) {
        	 dragedId = event.target.id;
        	  if(navigator.userAgent.indexOf("Firefox") != -1 ) {
        	           ragedId = event.explicitOriginalTarget.data;
        	            if (event.dataTransfer) {
        	                event.dataTransfer.setData("text", event.explicitOriginalTarget.data);
        	            }
        	    }
           
        };

         //TODO On Drop
        function OnDropTarget(event) {
            console.log("On Drop ;-)")
            console.log(event);
            if (event.dataTransfer) {
                var presentScenario = $(".title").text();
                console.log("presentScenario : " + presentScenario);
                $('#dg').datagrid({
                    url: '/bunit/rest/bunit/drag/' + dragedId + '/' + presentScenario,
                    method: 'get',
                    onLoadError: function() {
                    	if(presentScenario == null || presentScenario.length==0) {
                    		
						console.log("cccccccccccccc");
                    		 $('#dg').datagrid({
                                 url: '/bunit/rest/bunit/empty',
                                 method: 'get',
                                 rownumbers: true
                             });
                    		 window.alert('No scenatio available, create a new scenario or open existing.');
                    	} else {
	                    	 $('#dg').datagrid({
                             url: '/bunit/rest/bunit/open_scenario/' + presentScenario,
                             method: 'get'
                         });
                    		window.alert('Failed to drag action, same action draged consecutively.');                		
                    	}
                    }
                });

            } else {
                alert("Your browser does not support the dataTransfer object.");
            }

            if (event.stopPropagation) {
                event.stopPropagation();
            } else {
                event.cancelBubble = true;
            }
            return false;
        }
         
         //TODO editing output list
        function editOutputList() {

            console.log("Editing input flist");
            var input = $('<input class="afterOutputEdit"/>', {
                'type': 'text',
                'style': 'width:100px'
            });
            var parent = $(".output_edit").parent();
            parent.append(input);
            $(".edit").text('');
        }
         
        function saveOutputList() {
            console.log("Saving output flist ..........");
            console.log($('.afterOutputEdit').length);
                var map = new Object(); // or var map = {};
                var sendingObj = {};
            $('.afterOutputEdit').each(function(i, obj) {
                
                map[$(this).prev().attr("id")] = $(this).val();

            });
            sendingObj.values = map;
            
            JSON.stringify(sendingObj);
            console.log(sendingObj);
            var presentScenario = $(".title").text();
            $.ajax({
                url: "/bunit/rest/bunit/edit_scenario/output/" + $('#outputFlistId').text() + "/" + presentScenario,
                type: "POST",
                data: JSON.stringify(sendingObj),
                context: document.body,
                contentType: "application/json; charset=utf-8"
            }).done(function(response) {
            	$('#logs').html(response);
            });
            
            $('#outputFlist').dialog('close');
        }
        
        function editNode() {

            console.log("Editing input flist");
            var input = $('<input class="afterEdit"/>', {
                'type': 'text',
                'style': 'width:100px'
            });
            var parent = $(".edit").parent();
            parent.append(input);
            $(".edit").text('');
        }

        function saveNode() {
        	
            console.log("Saving input flist ..........");

            var map = new Object(); // or var map = {};
            var sendingObj = {};
            
            $('.afterEdit').each(function(i, obj) {
                console.log($(this).prev().attr("id"));
                map[$(this).prev().attr("id")] = $(this).val();

            });
            
            console.log($('.afterEdit').length);
               
            sendingObj.values = map;
            var presentScenario = $(".title").text();
            $.ajax({
                url: "/bunit/rest/bunit/edit_scenario/input/" + $('#inputFlistId').text() + "/" + presentScenario,
                type: "POST",
                data: JSON.stringify(sendingObj),
                context: document.body,
                contentType: "application/json; charset=utf-8"
            }).done(function(response) {
            	$('#logs').html(response);
            }); 
            $('#tdlg').dialog('close');
        }

         
    </script>
    <style type="text/css">
        form {
            margin: 0;
            padding: 0;
        }
        .dv-table td {
            border: 0;
        }
        .dv-table input {
            border: 1px solid #ccc;
        }
        .deleteIcon {
            cursor: pointer;
            height: 22px;
            width: 22px;
        }
        .deleteIconDiv {
            text-align: center;
            padding-top: 2px;
        }
    </style>

    <!-- -- END OF AUMAMAHESH -->

</head>

<body>


    <div style="margin:5px 0;"></div>

    <div class="easyui-layout" style="fit=true;width:100%;height:575px;">
        <div data-options="region:'north'" style="height:100px; text-align:center">
            <h5>BUINT BRM TEST FRAMEWORK</h5> 

            <!--  this is menu code -->

            <div style="margin:20px 0;"></div>
            <div class="easyui-panel" style="padding:5px;background:#00acc8;">
                <!-- #00acc8 -->

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
                <div data-options="iconCls:'icon-undo'" id="newScenario">New Test Scenario</div>
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
                <p style="font-size:14px;color:#444;">BRM TESTING</div>
        </div>


        <div data-options="region:'south',split:true" style="height:100px; text-align:center">
            <h5>TEST RESULTS - LOGS</h5>
            <div id='logs' style="font-size: 14px; text-align: justify;"></div>
        </div>
        <div data-options="region:'east',split:true" title="ACTION WIDGETS" style="width:300px;">


            <table id="pg" class="easyui-propertygrid" style="fit=true;width:100%;height:100%" data-options="showGroup:true,scrollbarSize:0">
            </table>

        </div>
        <div id="uma" data-options="region:'center',title:'SCENARIO BUILD AREA',iconCls:'icon-ok'" ondragenter="return false;" ondragover="return false;" ondrop="return OnDropTarget(event);">


            <table id="dg" class="easyui-datagrid" style="fit=true;width:100%;height:100%" toolbar="#toolbar" fitColumns="true" singleSelect="true">
                <thead>
                    <tr>
                        <th field="actionID" width="400">Action ID</th>
                        <th field="scenarioID" width="300">Scenario ID</th>
                        <th field="actionDescription" width="300">Action Description</th>
                        <th field="inputFlist" width="400">InputFlist</th>
                        <th field="outputFlist" width="400">OutputFlist</th>
                        <th field="status" width="400">Status</th>
                        <th field="runButton" width="300" align="center">Button</th>
                        <th field="deleteButton" width="300" align="center">Delete</th>
                    </tr>
                </thead>
            </table>
            <div id="toolbar">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">Config Scenario</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">Run Scenario</a>
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
            }" style="width:900px;height:500px;padding:10px">
            <div style="margin-bottom:10px">
                <a href="#" onclick="saveNode()">Save</a>
                <a href="#" onclick="editNode()">Edit</a>
            </div>

            <table id="test" title="Input FList" class="easyui-treegrid" style="width:857px;height:400px">
                <thead>
                    <tr>
                        <th title="Field" field="name" width="220">Field</th>
                        <th title="Default Value" field="defaultValue" width="220" align="right">Default Value</th>
                        <th title="Editable Field to overwrite the Default value" field="editField" width="220">Editable Field to overwrite the Default value</th>
                    </tr>
                </thead>
            </table>
        </div>

        <div id="outputFlist" class="easyui-dialog" title="Basic Dialog" data-options="
            iconCls:'icon-save',
            onResize:function(){
                $(this).dialog('center');
            }" style="width:900px;height:500px;padding:10px">
            <div style="margin-bottom:10px">
                <a href="#" onclick="saveOutputList()">Save</a>
                <a href="#" onclick="editOutputList()">Edit</a>
            </div>

            <table id="outputList" title="OutPut FList" class="easyui-treegrid" style="width:857px;height:400px">
                <thead>
                    <tr>
                        <th title="Field" field="name" width="220">Field</th>
                        <th title="Default Value" field="defaultValue" width="220" align="right">Default Value</th>
                        <th title="Define (global)variable" field="editField" width="220">Define (global)variable</th>
                    </tr>
                </thead>
            </table>
        </div>

    </div>

</body>

</html>