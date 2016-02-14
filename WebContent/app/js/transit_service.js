  $(function() {
  	/*$('.rightContainer').resizable({
    handles: 'w',maxWidth : $('.rightContainer').width()
  	});*/
  	
 
	$('#example').DataTable( {
        "scrollY": 200,
        "scrollX": true,
         paging: false,
         searching: false
    } );
	
	
	$('.openCloseTerms').click(function () {

		$.ajax({
			url: "/sample/rest/bunit/get_actions",
			context: document.body
		}).done(function(response) {
			console.log(response);
			$("#actionID").text(response.ActionId);
			$("#description").text(response.ActionDescription);
		});
		
		$(this).toggleClass('moduleClose');
		if($(this).hasClass('moduleClose')){
			$(this).parent().next().find('.selectedItem').remove();
		} else {
			$(this).parent().next().append('<div class="clear"></div><div class="selectedItem" ><div class="showActions"  style="width: 398px; padding: 4px; border: 1px dashed;">'
					+ '<div id="actionID" style="float: left; width: 258px;"></div>'
					+ '<div id="description"></div>'
					+ '</div><div class="clear"></div>');
			
		}
	        
     });
	
	
	
	
	/**
	 * Rest calls
	 */
	
	$("#newScenario").click(
			function() {
					$.ajax({
						url: "/sample/rest/bunit/newScenario",
						context: document.body
					}).done(function() {
						
					});
			}
			);
	 
	 $("#openScenario").click(
				function() {
						$.ajax({
							url: "/sample/rest/bunit/open_scenario",
							context: document.body
						}).done(function(response) {
							var actionInfo = response.ACTION;
							$("#actionId").text(response.SCENARIOID);
							$("#actionName").text(actionInfo[0].ID);
							$("#actionDesc").text(actionInfo[0].DESCRIPTION);
							$("#actionType").text(actionInfo[0].GROUP);
							$("#actionDate").text(response.DATE);
							$("#type").text(actionInfo[0].GROUP);
							$("#status").text(response.STATUS);
						});
				}
				);
  });

  