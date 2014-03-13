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
			'<div><textarea name="test_IDNR" style="width: 100%" rows="8" ></textarea><a href="#" class="REMOVE_ID">&times; entfernen</a></div>');

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
			Startzeit:<input type="time" name="startTime_IDNR"><br><br>\
			Endzeit: <input type="time" name="endTime_IDNR"><br><br>\
			\
			Periode:<input type="number" name="period_IDNR" value="1" class="Number"/>\
		    <select name="periodDD_IDNR" size="1">\
		      <option value="days">Tag(e)</option>\
		      <option value="weeks" selected>Woche(n)</option>\
		      <option value="months">Monat(e)</option>\
		    </select><br><br>\
			Startdatum:<input type="date" name="startDate_IDNR"><br><br>\
			Enddatum:<input type="date" name="endDate_IDNR"><br><br>\
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

