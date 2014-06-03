function Adding(wrapperID, removeID, inputHtml) {
	this.addedNr = 1;
	this.wrapperID = wrapperID;
	this.removeID = removeID;
	this.inputHtml = inputHtml;
	this.add = function() {
		this.addedNr++;
	    $(this.wrapperID).append(this.inputHtml.replace(new RegExp("IDNR","g"),this.addedNr).replace(new RegExp("REMOVE_ID","g"), this.removeID));
	}
	this.remove = function(tag) {
		if (this.addedNr > 1) {
			$(tag).parent('div').remove();
			this.addedNr--;
		}
	}
}

$(document).ready(function() {
	
	// Test Boxen
	var tests = new Adding("#InputsWrapperTest","removeclassTest",
			'<div><button class="REMOVE_ID" type="button" class="small" style="margin-bottom: 4px; float: right;"><font color="red"><div class="glyphicon glyphicon-trash small"></div><b>entfernen</b></font></button>\
			<textarea name="test_IDNR" style="max-width: 100%; min-width: 100%" rows="9" ></textarea></div>');

	var createLecture = new Adding("#lectureDetail", "removeDetail",
	'<div class="well">\
			<table style="width:100%" class="parentTable">\
	<td style="width:50%">\
			<h4>Termin #IDNR</h4>\
	</td>\
	<td style="width:50%">\
			<a href="#" style="color:red; float:right" class="removeDetails">\
				<font color="red"><div class="glyphicon glyphicon-remove"></div></font>\
			</a>\
	</td>\
</table>\
<table style="width:100%">\
	<tr>\
		<td style="width:50%">Raum:</td>\
		<td style="width:50%"></td>\
	</tr>\
	<tr>\
		<td style="width:50%">\
					<input type="text" name="room_IDNR" class="Text form-control round-corners" placeholder="Tragen Sie hier den Raum ein">\
		</td>\
		<td style="width:50%"></td>\
	</tr>\
</table><br>\
<div class="buttonGroupParent">\
    <input type="hidden" name="day_IDNR" value="monday" class="hiddenInput">\
	<div class="btn-group" data-toggle="buttons">\
	  <label class="btn btn-default">\
	    <input type="radio" name="weekDay" value="monday"> Montag\
	  </label>\
	  <label class="btn btn-default">\
	    <input type="radio" name="weekDay" value="tuesday"> Dienstag\
	  </label>\
	  <label class="btn btn-default">\
	    <input type="radio" name="weekDay" value="wednesday"> Mittwoch\
	  </label>\
		  <label class="btn btn-default">\
	    <input type="radio" name="weekDay" value="thursday"> Donnerstag\
	  </label>\
		  <label class="btn btn-default">\
	    <input type="radio" name="weekDay" value="friday"> Freitag\
	  </label>\
		  <label class="btn btn-default">\
	    <input type="radio" name="weekDay" value="saturday"> Samstag\
	  </label>\
		  <label class="btn btn-default">\
	    <input type="radio" name="weekDay" value="sunday"> Sonntag\
	  </label>\
	</div>\
</div><br>\
<table style="width:100%">\
<tr>\
	<td style="width:20%">\
		Startzeit:\
	</td>\
	<td style="width:15%"></td>	\
	<td style="width:20%">\
		Endzeit:\
	</td>\
	<td style="width:45%"></td>\
</tr>\
<tr>\
	<td style="width:20%">\
		<input type="time" name="startTime_IDNR" value="00:00" class="Time form-control round-corners">\
	</td>\
	<td style="width:15%; text-align:center">bis</td>\
	<td style="width:20%">\
		<input type="time" name="endTime_IDNR" value="00:00" class="Time form-control round-corners">\
	</td>\
	<td style="width:45%"></td>\
	</tr>\
</table><br>\
<table style="width:100%">\
	<tr>\
		<td style="width:20%">\
                <input type="text" class="form-control round-corners Number" placeholder="Periode" name="period_IDNR">\
		</td>\
		<td style="width:60%">\
			<div class="buttonPeriodParent">\
		        <input type="hidden" name="periodDD_IDNR" value="weeks" class="hiddenPeriod">\
				<div class="btn-group" data-toggle="buttons">\
				  <label class="btn btn-default">\
				    <input type="radio" name="period" value="days"> Tag(e)\
				  </label>\
					  <label class="btn btn-default">\
				    <input type="radio" name="period" value="weeks"> Woche(n)\
				  </label>\
					  <label class="btn btn-default">\
				    <input type="radio" name="period" value="months"> Monat(e)\
				  </label>\
				 </div>\
			</div>\
			</td>\
			<td style="width:20%"></td>\
		</tr>\
	</table>\
	<br>\
    <table style="width:100%">\
	<tr>\
		<td style="width:20%">\
			Startdatum:\
		</td>\
		<td style="width:15%"></td>\
		<td style="width:20%">\
			Enddatum:\
		</td>\
		<td style="width:45%"></td>\
	</tr>\
	<tr>\
		<td style="width:20%">\
			<input type="date" name="startDate_IDNR" value="2014-06-05" class="Date form-control round-corners" min="1584-01-01" max="9998-12-31">\
		</td>\
		<td style="width:15%; text-align:center">bis</td>\
		<td style="width:20%">\
			<input type="date" name="endDate_IDNR" value="2014-06-05" class="Date form-control round-corners" min="1584-01-01" max="9998-12-31">\
		</td>\
		<td style="width:45%"></td>\
	</tr>\
</table>\
</div>');
	
	$("#AddMoreTestBox").click(function (e) {
		tests.add();
	});

	$("body").on("click",".removeclassTest", function(e) { 
		tests.remove(this);
	});
	// Test Boxen ende
	
	//Create Lecture functions
	$("#moreDetails").click(function () {
		createLecture.add();
	});

	$("body").on("click",".removeDetails", function(e) { 
		e.preventDefault();
		createLecture.remove($(this).parents(".parentTable"));
	});
});

