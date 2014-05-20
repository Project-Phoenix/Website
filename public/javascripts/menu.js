if (typeof jQuery === 'undefined') { throw new Error('Bootstrap\'s JavaScript requires jQuery') }

$(document).ready(function(){
	var toggleGroupNav = 0;
	var toggleTaskSheetNav = 0;
	$('#everythingElse').click(function(e){
		e.preventDefault();
		
		$('.navbar-brand').removeClass('myActive');
		
        $('.toggled').fadeToggle();
        $('.toggled').removeClass('toggled');
        $('.toggled2').removeClass('toggled2');
       	$('.groupToggled').removeClass('groupToggled');
       	$('.taskSheetToggled').removeClass('taskSheetToggled');
	}); 
	$('#lecturesLink').click(function(e){
		e.preventDefault();


		if(!$(this).hasClass('myActive')){
			$('.navbar-brand').removeClass('myActive');
			$(this).addClass('myActive');
		}else{
			$('.navbar-brand').removeClass('myActive');
		}
			
		if(!$('#lectures').hasClass('toggled')){
			$('#lectures').addClass('toggled');	
		}
        $('.toggled').fadeToggle();
        $('.toggled').not('#lectures').removeClass('toggled');
        $('.toggled2').removeClass('toggled2');
       	$('.groupToggled').removeClass('groupToggled');
       	$('.taskSheetToggled').removeClass('taskSheetToggled');
	});   

	$('.lectureButtons').click(function(e){
	   e.preventDefault();
		var name = $(this).attr('name').replace(/\s/g, 'popel1215');
		
		$('.navbar-brand').not('.navbarButtons').removeClass('myActive');
		$(this).addClass('myActive');
		
		if(toggleGroupNav === 0){
			toggleGroupNav = 1;
			$("#"+name).addClass('toggled');
		}else{
			if($("#"+name).hasClass('toggled')){
				toggleGroupNav = 0;
				$("#"+name).removeClass('toggled');
				$('.taskSheetNavs').removeClass('toggled');
			}else{
				$('.groupNavs').removeClass('toggled');
				$('.taskSheetNavs').removeClass('toggled');
				$("#"+name).addClass('toggled');
			}
		}		

		$("#"+name).addClass('groupToggled');
       	$('.groupToggled').fadeToggle();	
       	$('.groupToggled').not("#"+name).removeClass('groupToggled');
        $('.toggled2').fadeToggle();
        $('.toggled2').removeClass('toggled2');
       	$('.taskSheetToggled').removeClass('taskSheetToggled');
   	})
   	
  	.dblclick(function(e){
		e.preventDefault();
		$('#hiddenLecture').val($(this).attr('name'));
		$('#menuForm').submit();
   	});
   	
	$('.groupButtons').click(function(e){
		var name = $(this).attr('name').replace(/\s/g, 'popel1215');

		$('.groupButtons').removeClass('myActive');
		$(this).addClass('myActive');
		
		if(toggleTaskSheetNav === 0){
			toggleTaskSheetNav = 1;
			$("#"+name).addClass('toggled');
			$("#"+name).addClass('toggled2');
		}else{
			if($("#"+name).hasClass('toggled')){
				toggleTaskSheetNav = 0;
				$("#"+name).removeClass('toggled');
			}else{
				$('.taskSheetNavs').removeClass('toggled');
				$("#"+name).addClass('toggled');
			}
			if($("#"+name).hasClass('toggled2')){
				toggleTaskSheetNav = 0;
				$("#"+name).removeClass('toggled2');
			}else{
				$('.taskSheetNavs').removeClass('toggled2');
				$("#"+name).addClass('toggled2');
			}
		}
		e.preventDefault();

		$("#"+name).addClass('taskSheetToggled');
       	$('.taskSheetToggled').fadeToggle();	
       	$('.taskSheetToggled').not("#"+name).removeClass('taskSheetToggled');
       	
   	})

  	.dblclick(function(e){
		e.preventDefault();
		$('#hiddenLecture').val($(this).attr('name'));
		$('#menuForm').submit();
   	});
});