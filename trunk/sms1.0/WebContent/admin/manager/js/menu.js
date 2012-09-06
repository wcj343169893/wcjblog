$(document).ready(function(){
	/* This code is executed after the DOM has been completely loaded */

	/* Changing the default easing effect - will affect the slideUp/slideDown methods: */
	$.easing.def = "jswing";

	/* Binding a click event handler to the links: */
	$('li.button').click(function(e){
		/* Finding the drop down list that corresponds to the current section: */
		var dropDown = $(this).next();
		
		/* Closing all other drop down sections, except the current one */
		$('.dropdown').not(dropDown).slideUp('slow');
		dropDown.slideToggle('slow');
		
		/* Preventing the default event (which would be to navigate the browser to the link's address) */
		e.preventDefault();
	});
	
});