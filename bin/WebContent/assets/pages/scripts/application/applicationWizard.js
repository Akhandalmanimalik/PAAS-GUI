var applicationWizards = angular.module('applicationWizards', []);

applicationWizards.controller('MainCtrl', function ($scope,$http) {
	
	$scope.field = {};
    
    
    $scope.getAllApplicationByByService = function() {
   	 var response = $http.get('/paas-gui/rest/applicationService/getAllApplicationService');
	     	response.success(function(data){
	     		$scope.field = data;
	     		console.log("data given");
	     	});
	     	response.error(function(data, status, headers, config) {
	                 alert("Error in Fetching Data of selectImageRegistry");
	         });	
    };  
   
   
 
    /*=================== delete*====================*/
   
    $scope.deleteApplicationByApplName = function(serviceName) {
     	alert("coming applicationName>>>"+serviceName);
     	//25 is hardcode value for apps_id
     	$scope.appsid=25;
     	alert("coin");
     	var response = $http.get('/paas-gui/rest/applicationService/deleteServiceByName/'+serviceName);
     	response.success(function(data){
     		
     	});
     	response.error(function(data, status, headers, config) {
                 alert("Error in Fetching Data"+data);
         });
     	
     };
     
     
  });   /*================end of controllers===================*/


