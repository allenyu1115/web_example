$().ready(
	function() {
		(function() {
			checkfunc = { true: function(x) { x.show(); }, false: function(x) { x.hide(); } }
			$('#selectGrp > input[type="radio"] ').click(function(myevent) {
				$('#bindGrp > div').each(function(_index, value) {
					checkfunc[value.id == (myevent.target.id + 'Bind')]($('#' + value.id))
				});
			})
		})()
	}
)