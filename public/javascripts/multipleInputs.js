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
	'<div class="well">\
	<table style="width:100%" class="parentOfA">\
		<td style="width:50%">\
			<h4>Detail #IDNR</h4>\
		</td>\
		<td style="width:50%">\
			<div style="float:right">\
				<a href="#" style="color:red" class="removeDetails">\
					<font color="red"><div class="glyphicon glyphicon-remove"></div></font>\
				</a>\
			</div>\
		</td>\
	</table><br>\
	<table style="width:100%">\
		<td style="width:50%">\
				<input type="text" name="room_IDNR" class="Text form-control round-corners" placeholder="Raum">\
		</td>\
	</table><br>\
	<input type="hidden" name="day_IDNR" value="monday">\
	<div class="btn-group">\
	  <button type="button" class="btn btn-default">Montag</button>\
	  <button type="button" class="btn btn-default">Dienstag</button>\
	  <button type="button" class="btn btn-default">Mittwoch</button>\
	  <button type="button" class="btn btn-default">Donnerstag</button>\
	  <button type="button" class="btn btn-default">Freitag</button>\
	  <button type="button" class="btn btn-default">Samstag</button>\
	  <button type="button" class="btn btn-default">Sonntag</button>\
	</div><br><br>\
	<table style="width:100%">\
		<tr>\
			<td style="width:30%">\
				Startzeit:\
			</td>\
			<td style="width:20%"></td>\
			<td style="width:30%">\
				Endzeit:\
			</td>\
			<td style="width:20%"></td>\
		</tr>\
		<tr>\
			<td style="width:30%">\
				<input type="time" name="startTime_IDNR" value="00:00" class="Time form-control round-corners">\
			</td>\
			<td style="width:20%"></td>\
			<td style="width:30%">\
				<input type="time" name="endTime_IDNR" value="00:00" class="Time form-control round-corners">\
			</td>\
			<td style="width:20%"></td>\
		</tr>\
	</table><br>\
	<div class="input-group">\
	    <input type="text" class="form-control round-corners" placeholder="Periode" name="period_IDNR>\
	  <div class="input-group-btn">\
		  <button type="button" class="btn btn-default">Tag(e)</button>\
		  <button type="button" class="btn btn-default">Woche(n)</button>\
		  <button type="button" class="btn btn-default">Monat(e)</button>\
	  </div>\
	<br>\
	 <table style="width:100%">\
		<tr>\
			<td style="width:30%">\
				Startdatum:\
			</td>\
			<td style="width:20%"></td>\
			<td style="width:30%">\
				Enddatum:\
			</td>\
			<td style="width:20%"></td>\
		</tr>\
		<tr>\
			<td style="width:30%">\
				<input type="date" id="startDate_IDNR" name="startDate_IDNR" value="@util.TimeGroup.now("Y-MM-dd")" class="Date form-control round-corners" min="1584-01-01" max="9998-12-31">\
			</td>\
			<td style="width:20%"></td>\
			<td style="width:30%">\
				<input type="date" name="endDate_IDNR" value="@util.TimeGroup.now("Y-MM-dd")" class="Date form-control round-corners" min="1584-01-01" max="9998-12-31">\
			</td>\
			<td style="width:20%"></td>\
		</tr>\
	</table>\
	</div>\
	</div>');
	
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
		e.preventDefault();
		createLecture.remove('.parentOfA');
	});
});

