$(document).ready(function() {
	
	$("#attribute-address, #attribute-zipCode, #attribute-postalAddress").change(function() {
		
		if($("#attribute-address").val() != "" && $("#attribute-zipCode").val() != "" && $("#attribute-postalAddress").val() != "") {
			$("#attribute-contactByLetter").removeAttr("disabled").next("label").removeClass("disabled");
		} else {
			$("#attribute-contactByLetter").removeAttr("checked").attr("disabled", "disabled").next("label").addClass("disabled");
		}
		
	});
	
	
	$( "#attribute-mobilePhone").change(function() {
		
		if($(this).val() != "") {
			$("#attribute-contactBySMS").removeAttr("disabled").next("label").removeClass("disabled");
		} else {
			$("#attribute-contactBySMS").removeAttr("checked").attr("disabled", "disabled").next("label").addClass("disabled");
		}
		
	});
	
	$( "#email").change(function() {
		
		if($(this).val() != "") {
			$( "#attribute-contactByEmail").removeAttr("disabled").next("label").removeClass("disabled");
		} else {
			$( "#attribute-contactByEmail").removeAttr("checked").attr("disabled", "disabled").next("label").addClass("disabled");
		}
		
	});
	
	$( "#attribute-phone").change(function() {
		
		if($(this).val() != "") {
			$( "#attribute-contactByPhone").removeAttr("disabled").next("label").removeClass("disabled");
		} else {
			$( "#attribute-contactByPhone").removeAttr("checked").attr("disabled", "disabled").next("label").addClass("disabled");
		}
		
	});
	
	$("#attribute-address, #attribute-zipCode, #attribute-postalAddress, #attribute-mobilePhone, #email, #attribute-phone").trigger("change");
	
	if($(".user-updated-message").length > 0) {
		
		showNotificationDialog("success", 5000, $(".user-updated-message").text());
		
	}
	
});

function showNotificationDialog(type, delay, msg) {
	
	var modal = $("<div>").addClass("mini-modal " + type);
	var inner = $("<div>").addClass("inner");
	var span = $("<span>").text(msg);

	inner.append(span);
	modal.append(inner);
	modal.prependTo("body");

	modal.fadeIn("fast").delay(delay).fadeOut("fast", function() {
		modal.remove();
	});
	
}