	var detail2 = false;
	var detail3 = false;
	var notNumber = /\D+/
	var splChars =/^.*?(?=[\^#%&$\*:;!\"�<>\?/\{\|\}@@]).*$/;
	

	$(document).ready(function(){
		for(var i = 2; i <= "@lecture.getLectureDetails().size()"; i++){
			$("#moreDetails").click();
		}	

		
		@for(index <- 0 to lecture.getLectureDetails().size()-1){
			document.getElementsByName("room_"+(@index+1).toString())[0].value = "@lecture.getLectureDetails().get(index).getRoom()";
			document.getElementsByName("day_"+(@index+1).toString())[0].selectedIndex = @lecture.getLectureDetails().get(index).getWeekday().getDateTimeConstant()-1;
			document.getElementsByName("startTime_"+(@index+1).toString())[0].value = "@lecture.getLectureDetails().get(index).getStartTime().toString("HH:mm")";
			document.getElementsByName("endTime_"+(@index+1).toString())[0].value = "@lecture.getLectureDetails().get(index).getEndTime().toString("HH:mm")";
			document.getElementsByName("startDate_"+(@index+1).toString())[0].value = "@lecture.getLectureDetails().get(index).getStartDate().toString("YYYY-MM-dd")";
			document.getElementsByName("endDate_"+(@index+1).toString())[0].value = "@lecture.getLectureDetails().get(index).getEndDate().toString("YYYY-MM-dd")";
								


			@if(lecture.getLectureDetails().get(index).getInterval().getDays() > 0){
				document.getElementsByName("period_"+(@index+1).toString())[0].value = @lecture.getLectureDetails().get(index).getInterval().getDays();
				document.getElementsByName("periodDD_"+(@index+1).toString())[0].selectedIndex = 0;
			}else{
				@if(lecture.getLectureDetails().get(index).getInterval().getWeeks() > 0){
				document.getElementsByName("period_"+(@index+1).toString())[0].value = @lecture.getLectureDetails().get(index).getInterval().getWeeks();
				document.getElementsByName("periodDD_"+(@index+1).toString())[0].selectedIndex = 1;
				}else{
					@if(lecture.getLectureDetails().get(index).getInterval().getMonths() > 0){
					document.getElementsByName("period_"+(@index+1).toString())[0].value = @lecture.getLectureDetails().get(index).getInterval().getMonths();
					document.getElementsByName("periodDD_"+(@index+1).toString())[0].selectedIndex = 2;
					}
				}
			}
		}
		
		
		$(document).on('change', '.Number', function(){	
			if(notNumber.test($(this).val())){
				$(this).val("0");
				alert('Bitte hier eine Nummer eingeben.'); 
			}
		});	
		
		$(document).on('blur', '.Date', function(){
			var year = parseInt($(this).val().slice(0, 4));
			if(year <= 1583 || year >= 9999){
				$(this).val("@util.TimeGroup.now("Y-MM-dd")");
			    alert("Die Gregorianer werden Böse!"); 
			    $(this).focus();
			}
		});
		
		$("#lectureDetail").on('change', '.Text', function(){	
			if(splChars.test($(this).val())){
				$(this).val('');
			    alert("Illegal characters detected!"); 
			    $(this).focus(); 
			}
		});
		
		$("#title").change(function(){
			if(splChars.test($(this).val())){
				$(this).val('');
			    alert ("Illegal characters detected!"); 
			    $(this).focus(); 
			}
		});
	});

	function checkLecture(inhalt)
	{
		$.get('/existLecture',
			      {'title': inhalt})
			      .success(function(data) {
		    		  	alert("Diese Veranstaltung ist schon vorhanden!");
		        	  	$("#title").val("");
			      })
	}
	