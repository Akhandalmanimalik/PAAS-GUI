var mysubnet = angular.module('mysubnet', []);

mysubnet.controller('SubnetCtrl', function ($scope,$http) {
	
	$scope.field = {};
	
    $scope.showModal = false;
    $scope.toggleModal = function(){
        $scope.showModal = !$scope.showModal;
    };
    
    
    /*===================== To add subnet details into db =========================*/
    $scope.regSubnet = function() {
    	console.log($scope.field);
    	//var userData = JSON.stringify($scope.field);
    	var userData = angular.toJson($scope.field);
    	var res = $http.post('/paas-gui/rest/subnetService/addSubnet', userData);
    	console.log(userData);
    	
    	res.success(function(data, status, headers, config) {
    		 $scope.selectSubnetnew();
    		window.location.href="/paas-gui/html/subnet-interface.html";
    		
    	});
    	res.error(function(data, status, headers, config) {
    		alert("Error in registering subnet  " +data );
    	});
    }; 
    /*===================== END OF regSubnet =========================*/
    
    /*============ To get all vpc with current user ======================*/
    $scope.getAllVpc = function() {
    							  
    	var response = $http.get('/paas-gui/rest/vpcService/getAllVPC');
    	
    	response.success(function(data){
    	
    		$scope.fields = data;
    		console.log(">>>>>>>  >>>  "+$scope.fields);
    	});
    	response.error(function(data, status, headers, config) {
                alert("Error in Fetching subnet Data"+data);
        });
    };
    /*======================= END OF getAllVpc =========================*/
    
    
    /*======================= To get all Subnet details with current user  =========================*/
    $scope.selectSubnetnew = function() {
    
  	//var response = $http.get('/PAAS-GUI/rest/fetchData/selectVpc');
  	var response = $http.get('/paas-gui/rest/subnetService/getAllSubnet');
  	response.success(function(data){
  	
  		$scope.subnet = data;
  		console.log("data given>>>"+$scope.subnet);
  	});
  	response.error(function(data, status, headers, config) {
        alert("Error in Fetching subnet Data"+data);
      });
  };  
  /*======================= END OF selectSubnetnew =========================*/
  
    
  /*======================== TO GET ALL ENVIRONMENT NAME WITH THE CURRENT USER ====================*/
  $scope.selectEnvironment_name = function() {
 	var response = $http.get('/paas-gui/rest/environmentTypeService/getAllEnvironmentType');
 	
 	//var response = $http.get('/PAAS-GUI/rest/fetchData/selectSubnet');
 	
 	
 	response.success(function(data){
 	
 		$scope.environment = data;
 		console.log(">>>>>>>  >>>  "+$scope.environment);
 	});
 	response.error(function(data, status, headers, config) {
             alert("Error in Fetching environment Data"+data);
     });
 };   
 /*======================= END OF selectEnvironment_name =========================*/
 
/*===================== TO GET ALL ACL NAME WITH CURRENT USER ============================*/
 $scope.selectACL_name = function() {
	var response = $http.get('/paas-gui/rest/aclService/getAllACL');
	
	//var response = $http.get('/PAAS-GUI/rest/fetchData/selectSubnet');
	
	
	response.success(function(data){
	
		$scope.acl = data;
		console.log(">>>>>>>  >>>  "+$scope.acl);
	});
	response.error(function(data, status, headers, config) {
         alert("Error in Fetching environment Data"+data);
 });
};   
/*======================= END OF selectACL_name =========================*/
  
  
  /*======================== TO DELETE THE SUBNET BY SUBNET NAME ==============================*/
    $scope.deleteSubnet = function(data) {
     	var response = $http.get('/paas-gui/rest/subnetService/deleteSubnetByName/'+data);
     	response.success(function(data){
   		 $scope.selectSubnetnew();

     	});
     	response.error(function(data, status, headers, config) {
            alert("Error in deletinf subnet "+data);
         });
     	
     };
     /*======================= END OF deleteSubnet =========================*/     
    
    
     

	  	/*=============== Add SUBNET validation==============*/
	  	$scope.subnetValidation = function(subnet) {
		  	  console.log("subnet "+subnet);
		  	  var res = $http.get('/paas-gui/rest/subnetService/checkSubnet/'+subnet);
		  	  res.success(function(data, status, headers, config) {
		  		  
		  		if(data == 'success'){
		   	    	document.getElementById('subneterror').innerHTML="data exist enter different name";
		   	    	document.getElementById("mysubnetbtn").disabled = true;
		   	    	
		   		  }
		   		  else{
		   			 document.getElementById('subneterror').innerHTML="";
		   			 if(document.getElementById('cidr').value !=''){
		   			document.getElementById("mysubnetbtn").disabled =false;
		   		  }
		   		  }
	 	  		 
		  		  
	 	  		 
		  	  });
		  	  res.error(function(data, status, headers, config) {
		  	    alert("failure message: " + JSON.stringify({
		  	      data : data
		  	    }));
		  	  });
		  	 
		  	};
	/*===============END Add SUBNET validation==============*/
		  	
		  	
		  	
     
     
     
     
     
    
    
  });
      /*=============CONTROLLER ENDS===================*/


     /*===============DIRECTIVES STARTS=========================*/ 

mysubnet.directive('modal', function () {
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