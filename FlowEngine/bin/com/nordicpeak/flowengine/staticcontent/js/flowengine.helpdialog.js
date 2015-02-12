$(document).ready(function() {

	$(document).on("click", ".help a.open-help", function(e) {
		e.preventDefault();
		var $this = $(this);
		if ($this.siblings("#currenthelp").length > 0) {
			$("#currenthelp").hide().attr("id", "");
			return;
		}
		$("#currenthelp").hide().attr("id", "");
		var text = $this.data("help");
		$this.parent().find(".help-box").attr("id", "currenthelp").toggle();		
	});
	
	$(document).on("click", "#currenthelp .close", function(e) {
		e.preventDefault();
		$("#currenthelp").hide().attr("id", "");
	});

});