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
					+ '<div id="actionID" style="float: left; width: 258px;">SampleId</div>'
					+ '<div id="description">Sample DEsc</div>'
					+ '</div><div class="clear"></div>');
			
		}
	        
     });
	
	
	
	
	/**
	 * Rest calls
	 */
	
	$.ajax({
		url: "/sample/rest/bunit/get_actions",
		context: document.body
	}).done(function(actions) {
		console.log(actions);
		var id = 'id';
		var count = 0;
		var action = actions.ACTION;
		
		$.each(action, function(index, value) { 
			console.log(value);
			
			/*var data = '<div id='+  id + (++count) +' style="padding: 10px 0px; border: 1px solid;border-top:none;">' +
			'<div style="float: left; width: 20px;" class="openCloseTerms moduleClose">&nbsp</div>' +
			'<div>' + value.DATA.GROUP + '</div>' +
			'</div>' +
			'<div class="toggleDiv"></div>';
			
			var toggleData = '<div class="clear"></div><div class="selectedItem" ><div class="showActions"  style="width: 398px; padding: 4px; border: 1px dashed;">'
				+ '<div id="actionID" style="float: left; width: 258px;">new</div>'
				+ '<div id="description">viswa</div>'
				+ '</div><div class="clear"></div>';
			
			$(".SlectedItemsDiv").append(data+toggleData);
			$('.openCloseTerms').toggleClass('moduleClose');
*/			

			
		});
	});
	
	
	
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
							var actionInfo = response.ACTIONLIST.ACTION[0];
							$("#titleScenarioId").text(response.SCENARIOID);
							$("#titleScenarioDate").append(response.DATE);
							$("#scenarioId").text(response.SCENARIOID);
							$("#actionId").text(actionInfo.ID);
							$("#actionDesc").text(actionInfo.DESCRIPTION);
							$("#inputFList").text("sampleIFlist");
							$("#outputFList").text("sampleOFList");
							$("#status").text(response.STATUS);
							$("#button").append("<button class='btn btn-primary'>Run </button>");
						});
				}
				);
  });

  