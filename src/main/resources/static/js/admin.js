$( document ).ready(function() {

	var sse = $.SSE('/biz/user-management/kafka-messages', {
	    onMessage: function(e){ 
	        console.log(e); 
	        $('#kafka-messages tr:last').after('<tr><td>'+e.data+'</td></tr>');
	    },
	    onError: function(e){ 
	    	sse.stop();
	        console.log("Could not connect..Stopping SSE"); 
	    },
	    onEnd: function(e){ 
	        console.log("End"); 
	    }
	});
	sse.start();
	
});

function submitForProcessing() {
       var userId = $('#userId').val();
       var userName = $('#userName').val();
       
       var currentDate = new Date();
       var currentTime = currentDate.getTime();
       
        var inputData = {};
        inputData["id"] = userId;
        inputData["name"] = userName;
        console.log("inputData :" + inputData);

       $.ajax({
         url: "http://localhost:8181/biz/user-management/v1/user-credit",
         data: JSON.stringify(inputData),
         type: "POST",
         beforeSend: function(xhr){
         xhr.setRequestHeader('Content-Type', 'application/json');
         xhr.setRequestHeader('MWMD-requestTimestamp', currentTime);
          xhr.setRequestHeader('MWMD-activityName', 'credit-check');
           xhr.setRequestHeader('MWMD-requestID', Math.floor((Math.random() * 100000000) + 1));
         },
         success: function() { 
 			console.log("Success");
			$('#successBanner').show();
			$('#successBanner').hide(5000);
			
		 },
         error: function() { 
         	console.log("Error");
			$('#errorBanner').show();
			$('#errorBanner').hide(5000);
			
         
          },
         
      });
      
    };
