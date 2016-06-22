var app = angular.module('application', []);

app.controller('appCtrl', function($scope,$http, srvShareData) {
	  
	  $scope.dataToShare = [];
	  
	  $scope.shareMyData = function (myValue) {

	    $scope.dataToShare = myValue;
	    srvShareData.addData($scope.dataToShare);
	    
	    window.location.href = "applicationWizard.html";
	  };
	  
	  
		 
		
		$scope.field = {};
		
	 
	      /*================Application REGISTRATION===================*/
	    
	    
	    
	    /*to store application deatils */
	    $scope.storeApplication = function() {
	    	
		  	  console.log("inside application data value ");

	    	  $scope.message=null;
	  	  var application = JSON.stringify($scope.field);
	  	  var res = $http.post('/paas-gui/rest/applicationService/storeApplication', application);
	  	  res.success(function(data, status, headers, config) {
		  	  console.log("inside application data value "+data);
		  	window.location.href = "applicantmain.html";
	  	  
	  	  });
	  	  res.error(function(data, status, headers, config) {
	  	    alert("failure message : " + JSON.stringify({
	  	      data : data
	  	    }));
	  	  });
	  	 /* console.log("application data value "+$scope.message);
	  	  if($scope.message!=null){
		  	  window.location.href = "applicantmain.html";

	  	  }*/
	  	 
	  	};//store application  
	  	
	  	   /*To Get Application Details */
	  	 $scope.getApplications = function() {
	     	var response = $http.get('/paas-gui/rest/applicationService/getApplications');
	     	response.success(function(data){
	     		$scope.fields = data;
	     		 
	     	});
	     	response.error(function(data, status, headers, config) {
	                 alert("Error in Fetching Data");
	         });
	     };//end of getApplications script
	     
	     //edit application
	     $scope.editApplication=function(application){
	    	 
	    	 $scope.dataToShare = application;
	 	    srvShareData.addData($scope.dataToShare);
	 	     window.location.href = "editApplication.html"; 
	    	 
	     };//end of edit application
	     
	     
	  	 /*delete of Application based on given Id*/
	     $scope.deleteApplication = function(apps_id) {
	     	var response = $http.get('/paas-gui/rest/applicationService/deleteApplication/'+apps_id);
	     	response.success(function(data){
	     		$scope.getApplications();
	     	});
	     	response.error(function(data, status, headers, config) {
	                 alert("Error in Fetching Data");
	         });
	     	
	     };//end of application script 
	     
	        
	
	     
	});

	app.controller('appDisplayCtrl', function($scope, srvShareData,$http) {
	  
	  $scope.sharedData = srvShareData.getData();
	  
		$scope.field = {};

	  
	   $scope.getAllServiceByAppsId = function(id) {
		   	 var response = $http.get('/paas-gui/rest/applicationService/getAllServiceByAppsId/'+id);
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
		     

	});
	app.controller('appUpdateCtrl', function($scope, srvShareData,$http) {
		  
		  $scope.sharedData = srvShareData.getData();
		  
			$scope.field = $scope.sharedData[0];

		  
		     /*Edit application script  */
		     $scope.updateApplication = function() {
		    var applictaionUpdatedData=	$scope.field;
		     	var response = $http.put('/paas-gui/rest/applicationService/updateApplication',	applictaionUpdatedData);
		     	response.success(function(data){
		     		
		     		window.location.href = "applicantmain.html";
		     	});
		     	response.error(function(data, status, headers, config) {
		                 alert("Error in Fetching Data");
		         });
		     };//end of edit script
		     
			     

		});

	app.service('srvShareData', function($window) {
        var KEY = 'App.SelectedValue';

        var addData = function(newObj) {
            var mydata = [];
           
            mydata.push(newObj);
            $window.sessionStorage.setItem(KEY, JSON.stringify(mydata));
        };

        var getData = function(){
            var mydata = $window.sessionStorage.getItem(KEY);
            if (mydata) {
                mydata = JSON.parse(mydata);
            }
            return mydata || [];
        };

        return {
            addData: addData,
            getData: getData
        };
    });


