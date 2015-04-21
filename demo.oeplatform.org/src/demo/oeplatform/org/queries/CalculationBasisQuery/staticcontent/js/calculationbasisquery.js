$(document).ready(function() {
	
	setQueryRequiredFunctions["CalculationBasisQueryInstance"] = makeCalculationBasisQueryRequired;
	
});

function initCalculationBasisQuery(queryID) {
	
	var shortQueryID = "#q" + queryID;
	
	$(shortQueryID + "_firstname, " + shortQueryID + "_lastname, " + shortQueryID + "_address, " + shortQueryID + "_zipcode, " + shortQueryID + "_postaladdress").change(function() {
		
		if(($(shortQueryID + "_firstname").length == 0 || $(shortQueryID + "_firstname").val() != "") 
				&& ($(shortQueryID + "_lastname").length == 0 || $(shortQueryID + "_lastname").val() != "") 
				&& $(shortQueryID + "_address").val() != "" 
				&& $(shortQueryID + "_zipcode").val() != "" 
				&& $(shortQueryID + "_postaladdress").val() != "") {
			$(shortQueryID + "_contactByLetter").removeAttr("disabled").next("label").removeClass("disabled");
		} else {
			$(shortQueryID + "_contactByLetter").removeAttr("checked").attr("disabled", "disabled").next("label").addClass("disabled");
		}
		
	});
	
	
	$(shortQueryID + "_mobilephone").change(function() {
		
		if($(this).val() != "") {
			$(shortQueryID + "_contactBySMS").removeAttr("disabled").next("label").removeClass("disabled");
		} else {
			$(shortQueryID + "_contactBySMS").removeAttr("checked").attr("disabled", "disabled").next("label").addClass("disabled");
		}
		
	});
	
	$(shortQueryID + "_email").change(function() {
		
		if($(this).val() != "") {
			$(shortQueryID + "_contactByEmail").removeAttr("disabled").next("label").removeClass("disabled");
		} else {
			$(shortQueryID + "_contactByEmail").removeAttr("checked").attr("disabled", "disabled").next("label").addClass("disabled");
		}
		
	});
	
	$(shortQueryID + "_phone").change(function() {
		
		if($(this).val() != "") {
			$(shortQueryID + "_contactByPhone").removeAttr("disabled").next("label").removeClass("disabled");
		} else {
			$(shortQueryID + "_contactByPhone").removeAttr("checked").attr("disabled", "disabled").next("label").addClass("disabled");
		}
		
	});
	
	$(shortQueryID + "_firstname, " + shortQueryID + "_lastname, " + shortQueryID + "_address, " + shortQueryID + "_zipcode, " + shortQueryID + "_postaladdress, " + shortQueryID + "_mobilephone, " + shortQueryID + "_email, " + shortQueryID + "_phone").trigger("change");
	
	$("#query_" + queryID).find("input.input-error").parent().addClass("input-error");
	
}

function makeCalculationBasisQueryRequired(queryID) {
	
	$("#query_" + queryID).find(".heading-wrapper h2").addClass("required");
	
}