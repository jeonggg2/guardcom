function getDashboardFlotChartData(obj){
	var data = new Object();
	
	// 기준일, 
	data.setValue;	// 기준일
	data.setRagne;  // 범위
	data.setType;	//DAY, MONTH
	
	
	
	
	$.ajax({      
        type:"POST",  
        url:context + '/ax/admin/statistic/chart',
        async: false,
        data:input,
        success:function(args){   
        	//console.log(args)
        	data = args;

        },   
        //beforeSend:showRequest,  
        error:function(e){  
            console.log(e.responseText);  
        }  
    }); 
	return data;
}


function getDashboardSimpleAuditForm(obj){
	//var data;
	$.ajax({      
        type:"GET",  
        url:context + '/ax/admin/statistic/audit',
        async: true,
        data:{
        	
        	_:$.now()
        },
        success:function(args){   
        	//console.log($.now())
        	//data = args;
        	$('#simple_audit_list').html(args);

        },   
        //beforeSend:showRequest,  
        error:function(e){  
            console.log(e.responseText);  
        }  
    }); 
	//return data;
}