  $(function() {
  	/*$('.rightContainer').resizable({
    handles: 'w',maxWidth : $('.rightContainer').width()
  	});*/
		$.ajax({
			url: "/bunit/rest/bunit/get_actions",
			context: document.body
		}).done(function(response) {
			for (var key in response) {
				console.log(key);
				  if (response.hasOwnProperty(key)) {
				     $('.selectedItemsDiv').append('<div class="openCloseImg" id="img_'+key+'" style="padding: 4px 0px; border: 1px solid;border-top:none;">'
                    + '<div style="float: left; width: 20px;" class="openCloseTerms moduleClose">&nbsp;</div>'
                    + ' <div class="title">'+key+'</div><div class="toggle hide"></div></div>');
				     var actionList =response[key];
				     for (var i=0;i<actionList.length;i++){
				    	 $('.selectedItemsDiv').find("#img_"+key+"").find('.toggle').append('<div class="clear"></div><div class="selectedItem " id="img_'+key+'"><div class="showActions"  style="width: 372px; padding: 4px;">'
									+ '<div id="actionID"  class="wordWrap" style="float: left; width: 230px;">'+actionList[i].id+'</div>'
									+ '<div id="description"  class="wordWrap">'+actionList[i].desc+'</div>'
									+ '</div><div class="clear"></div>');
				    	    }
				    }
				}

		});

		$('.selectedItemsDiv').delegate(".openCloseImg .openCloseTerms","click",function () {
			$(this).toggleClass('moduleClose');
			if($(this).hasClass('moduleClose')){
				$(this).next().next().addClass('hide');
			} else {
				$(this).next().next().removeClass('hide');
			}

		});




	/**
	 * Rest calls
	 */

	/*$.ajax({
		url: "/buint/rest/bunit/get_actions",
		context: document.body
	}).done(function(actions) {
		console.log(actions);
		var id = 'id';
		var count = 0;
		var action = actions.ACTION;

		$.each(action, function(index, value) {
			console.log(value);

			var data = '<div id='+  id + (++count) +' style="padding: 10px 0px; border: 1px solid;border-top:none;">' +
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



		});
	});
*/


	$("#newScenario").click(
			function() {
					$.ajax({
						url: "/bunit/rest/bunit/newScenario",
						context: document.body
					}).done(function(response) {
							console.log(response);

					});
					$(".tableData").hide();
					$("#titleScenarioId").text("");
					$("#titleScenarioDate").text("Date :");

			}
			);

	 $("#openScenario").click(

				function() {
					var filePath= encodeURI('file:///E:/tom/BUNIT/BRMTestScenario001');
					window.open(filePath, 'Buint Scenarios', 'left=700,top=700,width=200,height=200,toolbar=0,status=0,location=0,menubar=0,scrollbars=0,titlebar=0');
						$.ajax({
							url: "/bunit/rest/bunit/open_scenario",
							context: document.body
						}).done(function(response) {
							$("#titleScenarioId").text(response.SCENARIOID);
							$("#titleScenarioDate").append(response.DATE);
							for(var i=0;i<3;i++){
								var actionInfo = response.ACTIONLIST.ACTION[0];
								$('.search-table').append('<tr class="tableData"><td class="wordWarp">'+response.SCENARIOID+'</td><td  class="wordWarp">'+actionInfo.ID+'</td>'+
										+ '<td  class="wordWarp">'+actionInfo.DESCRIPTION+'</td>'
										+ '<td  class="wordWarp">sampleIFlist</td><td  class="wordWarp">sampleOFList</td><td  class="wordWarp">sampleOFList</td>'
										+ '<td  class="wordWarp">'+response.STATUS+'</td><td><button class="btn btn-primary">Run</button></tr>');
							}

						});
				}
				);

//	 $( ".leftContainer, .rightContainer, .resultContainer").resizable();

	 $( ".ui-resizable" ).resizable({
		 resize: function(event, ui) {
			 var mainDiv = $('.mainContainer').width();
			 if($(this).hasClass('leftContainer')){
				 var currentDiv = $(this).width();
				 var width = (mainDiv-currentDiv)-20;
				 $('.rightContainer').css('width',width);
			 }else if($(this).hasClass('rightContainer')){
				 var currentDiv = $(this).width();
				 var width = (mainDiv-currentDiv)-20;
				 $('.leftContainer').css('width',width);
			 }else if($(this).hasClass('resultContainer')){
				 /*var leftHeight = $('.leftContainer').height();
				 var rightHeight = $('.rightContainer').height();
				 var resultHeight = $('.resultContainer').height();
				 var containerHeight = $('#container').height();
				 var height = containerHeight-resultHeight;
				 var resultHeight1 = containerHeight-leftHeight;
				 $('.leftContainer,.rightContainer').css('height',height);
				 $('.resultContainer').css('height',resultHeight1);*/
			 }
		 }
	 });


  });

