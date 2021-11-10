$().ready(function() {
	$('#show-more-content').hide();
	$('#show-less').hide(); $('#show-more').click(function() {
		$('#show-more').hide(); $('#mydefault').hide(); $('#show-less').show();
		$('#show-more-content').show();
	}); $('#show-less').click(function() {
		$('#show-more').show(); $('#mydefault').show(); $('#show-less').hide();
		$('#show-more-content').hide();
	});
})
