  $(function() {
  	$('.child').resizable({
    handles: 'w,s,e',minWidth: 200, maxWidth : 500
});
  	
 
	$('#example').DataTable( {
        "scrollY": 200,
        "scrollX": true,
         paging: false,
         searching: false
    } );
	
	
	$('.openCloseTerms').click(function () {

		$(this).toggleClass('moduleClose');
		if($(this).hasClass('moduleClose')){
			$(this).parent().next().find('.selectedItem').remove();
		} else {
			$(this).parent().next().append('<div class="clear"></div><div class="selectedItem" ><div class="showActions"  style="width: 398px; padding: 4px; border: 1px dashed;">'
					+ '<div style="float: left; width: 258px;">create account</div><div>127362736</div>'
					+ '</div><div class="clear"></div>');
			
		}
	        
     });
  });

  