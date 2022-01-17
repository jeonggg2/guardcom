<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<div id="org_tree" style="overflow: hidden;">
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
	 return resultl;
}

 $(document).ready(function(){
	 $('#org_tree').jstree({
	     'core' : {
	         'check_callback' : true,
	         'data' : JSON.parse('${deptJson}')
	     },
         'plugins' : [ 'types', 'dnd'],
         'types' : {
             'default' : {
                 'icon' : 'fa fa-user-circle'
             },
             'root' : {
                 'icon' : 'fa fa-building'
             }
	     }
	 })
 	 .on("check_node.jstree uncheck_node.jstree", function(e, data) {
	  alert(data.node.id + ' ' + data.node.text +
	        (data.node.state.checked ? ' CHECKED': ' NOT CHECKED'))
	});
	 
	 $('#org_tree').bind('loaded.jstree', function(e, data) {
	 		$('#org_tree').jstree(true).open_all();		
		})
 });
 
 </script>