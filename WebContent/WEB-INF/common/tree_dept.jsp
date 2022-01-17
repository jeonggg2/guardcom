<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<div id="org_tree" style="height: 91%; overflow: auto;">
     <ul>
         <li class="jstree-open" data-jstree='{"type":"root"}'>Company
             <ul>
             </ul>
         </li>
     </ul>
 </div>

 <script>

 var getCheckedDept = function(){
	 var result = $('#org_tree').jstree(true).get_selected();
	 $.each(result, function(index, value){
		 result[index] = result[index].replace('_','');
	 })

	 var reValue = [];
	 $.each(result, function(i, el){
 		 if($.inArray(el, reValue) === -1) reValue.push(el);
	 })
	 console.log(reValue);
	 if(reValue == []){
		 return result;
	 }
	 
	 return reValue;
 }

 $(document).ready(function(){

	 var treeLoaded = false;
	 var notUseFirst = false;
	 
	 	
	 $('#org_tree').jstree({
	     'core' : {
	         'check_callback' : false,
	         'data' : JSON.parse('${deptJson}')
	     },
         'plugins' : [ 'types', 'dnd', 'checkbox'],
         'types' : {
             'default' : {
                 'icon' : 'fa fa-user-circle'
             },
             'root' : {
                 'icon' : 'fa fa-building'
             }
	     },
	 });
	 
 	    $("#org_tree").bind("changed.jstree",
	    	    function (e, data) {
	    	 	    if(treeLoaded == true)   {
	 	    			if(notUseFirst == false){
	 	    				notUseFirst = true;
	 	    				return;
	 	    			}
	    	 	    	console.log( data );	    	 	    	
	    	 	    	reloadTable();
	    	 	    }

	    	    });
	  
	 $('#org_tree').bind('loaded.jstree', function(e, data) {
	 		$('#org_tree').jstree(true).check_all();		
	 		treeLoaded = true;
		})
 });
 
 </script>