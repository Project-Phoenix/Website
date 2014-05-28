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
			'<div>\
			Raum: <input type="text" name="room_IDNR" value="" class="Text"><br><br>\
			\
			<select name="day_IDNR" size="1">\
				<option value="monday" selected>Montag</option>\
				<option value="tuesday">Dienstag</option>\
				<option value="wednesday">Mittwoch</option>\
				<option value="thursday">Donnerstag</option>\
				<option value="friday">Freitag</option>\
				<option value="saturday">Samstag</option>\
				<option value="sunday">Sonntag</option>\
			</select><br><br>\
			\
			Startzeit:<input type="time" name="startTime_IDNR" value="00:00"><br><br>\
			Endzeit: <input type="time" name="endTime_IDNR" value="00:00"><br><br>\
			\
			Periode:<input type="text" name="period_IDNR" class="Number" value="0"/>\
		    <select name="periodDD_IDNR" size="1">\
		      <option value="days">Tag(e)</option>\
		      <option value="weeks" selected>Woche(n)</option>\
		      <option value="months">Monat(e)</option>\
		    </select><br><br>\
			Startdatum:<input type="date" name="startDate_IDNR" value="@util.TimeGroup.now("Y-MM-dd")" class="Date" min="1584-01-01" max="9998-12-31""><br><br>\
			Enddatum:<input type="date" name="endDate_IDNR" value="@util.TimeGroup.now("Y-MM-dd")" class="Date" min="1584-01-01" max="9998-12-31""><br><br>\
			<input type="button" class="removeDetails" value="Detail löschen"/></div>');
	
	$("#AddMoreTestBox").click(function (e) {
		tests.add();
	});

	$("body").on("click",".removeclassTest", function(e) { 
		tests.remove(this);
	});
	// Test Boxen ende
	
	//Create Lecture functions
	$("#moreDetails").click(function (e) {
		createLecture.add();
	});

	$("body").on("click",".removeDetails", function(e) { 
		createLecture.remove(this);
	});
});

