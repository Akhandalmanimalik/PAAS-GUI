var myvpc = angular.module('VpcApp', []);

myvpc.controller('VpcCtrl', function ($scope,$http) {
	
	$scope.field = {};
	
    $scope.showModal = false;
    $scope.toggleModal = function(){
        $scope.showModal = !$scope.showModal;
    };
    
      /*================VPC REGISTRATION===================*/
    
    $scope.regVpc = function() {
      if ($scope.vpcWizardForm.$valid) {
  	  console.log($scope.field);
  	  
  	  var userData = JSON.stringify($scope.field);
  	  var res = $http.post('/paas-gui/rest/vpcService/addVPC', userData);
  	  console.log(userData);
  	  res.success(function(data, status, headers, config) {
  	    $scope.message = data;
  	    
  	  $scope.selectVpc();
  	    
  	  });
  	  res.error(function(data, status, headers, config) {
  	    alert("failure message: " + JSON.stringify({
  	      data : data
  	    }));
  	  });
    }
  	};
  	
  	           /*POPULATE DATA TO TABLE*/
  
  	 $scope.selectVpc = function() {
     	var response = $http.get('/paas-gui/rest/vpcService/getAllVPC');
     	response.success(function(data){
     		$scope.fields = data;
     	});
     	response.error(function(data, status, headers, config) {
                 alert("Error in Fetching Data");
         });
     };
     
   
     //ACL
     $scope.getAcl = function() {
      	var response = $http.get('/paas-gui/rest/aclService/getAllACL');
      	response.success(function(data){
      		
      		$scope.aclist = data;
      		console.log("sddd"+$scope.aclist);
      		
      		
      
      	});
      	response.error(function(data, status, headers, config) {
                  alert("Error in Fetching Data");
          });
      };
     
  	         /*DELETE POPULATED DATA*/
  	

     $scope.deleteData = function(data) {
     	var response = $http.get('/paas-gui/rest/vpcService/deleteVPCByName/'+data);
     	response.success(function(data){
     		$scope.select();
     	});
     	response.error(function(data, status, headers, config) {
                 alert("Error in Fetching Data");
         });
     	
     };
     
              /*EDIT VPC DATA*/
     $scope.update = function(data) {
     	var response = $http.get('/paas-gui/rest/vpcService/updateVPC/'+data);
     	response.success(function(data){
     		$scope.select();
     	});
     	response.error(function(data, status, headers, config) {
                 alert("Error in Fetching Data");
         });
     };
     
     
     

	 /*===============Add vpc validation details==============*/
  	
  	 $scope.vpcValidation = function(vpc) {
  		 
     	
     	  console.log("vpc "+vpc);
     	  var res = $http.get('/paas-gui/rest/vpcService/checkVPC/'+vpc);
     	  res.success(function(data, status, headers, config) {
   
     	    	
     		  if(data == 'success'){
     	    	document.getElementById('errfn').innerHTML="data exist enter different name";
     	    	document.getElementById("myvpcbtn").disabled = true;
     	    	
     		  }
     		  else{
     			 document.getElementById('errfn').innerHTML="";
     			 if(document.getElementById('ACL').value != ''){
     			document.getElementById("myvpcbtn").disabled =false;
     			 }
     		  }
     	    
     	  });
     	  res.error(function(data, status, headers, config) {
     		 
     	    alert("failure message: " + JSON.stringify({
     	      data : data
     	    }));
     	  });
     };
     /*===============END of VPC validation==============*/

     
     
     /*===============ACL validation==============*/
	    $scope.aclValidation = function(acl) {
	    	
	    
		  	  console.log("acl >>>>>>>>>>>>>>>>>>. "+acl);
		  	  var res = $http.get('/paas-gui/rest/aclService/checkAcl/'+acl);
		  	  res.success(function(data, status, headers, config) {
		  		  
		  		if(data == 'success'){
		   	    	document.getElementById('aclerr').innerHTML="data exist enter different name";
		   	    	document.getElementById("myvpcbtn").disabled = true;
		   	    	
		   		  }
		   		  else{
		   			 document.getElementById('aclerr').innerHTML="";
		   			document.getElementById("myvpcbtn").disabled =false;
		   		  }
	 	  		 
		  	  });
		  	  res.error(function(data, status, headers, config) {
		  	    alert("failure message: " + JSON.stringify({
		  	      data : data
		  	    }));
		  	  });
		  	 
		  	};
		    /*===============END Add ACL validation==============*/
		  	
  	
  	

    
});  /*end of controller*/


myvpc.directive('modal', function () {
    return {
      template: '<div class="modal fade">' + 
          '<div class="modal-dialog">' + 
            '<div class="modal-content">' + 
              '<div class="modal-header">' + 
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' + 
                '<h4 class="modal-title">{{ title }}</h4>' + 
              '</div>' + 
              '<div class="modal-body" ng-transclude></div>' + 
            '</div>' + 
          '</div>' + 
        '</div>',
      restrict: 'E',
      transclude: true,
      replace:true,
      scope:true,
      link: function postLink(scope, element, attrs) {
        scope.title = attrs.title;

        scope.$watch(attrs.visible, function(value){
          if(value == true)
            $(element).modal('show');
          else
            $(element).modal('hide');
        });

        $(element).on('shown.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = true;
          });
        });

        $(element).on('hidden.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = false;
          });
        });
      }
    };
  });
