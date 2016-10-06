$('.spinner .btn:first-of-type').on('click', function() {
		    var spinner = $(this).parent().parent().find('input');
		    spinner.val(parseInt(spinner.val(), 10) + 1);
		});

		$('.spinner .btn:last-of-type').on('click', function() {
		    var spinner = $(this).parent().parent().find('input');
		    spinner.val(parseInt(spinner.val(), 10) - 1);
		});