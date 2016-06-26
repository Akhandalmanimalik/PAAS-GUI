/**
 * 
 */

// create our angular app and inject ngAnimate and ui-router 
// =============================================================================
var app=angular.module('application', ['ngAnimate', 'ui.router']);

// configuring our routes 
// =============================================================================
app.config(function($stateProvider, $urlRouterProvider) {
    
    $stateProvider
    
        // route to show our basic form (/form)
        .state('form', {
            url: '/form',
            templateUrl: 'form.html',
            controller: 'formController'
        })
        
        // nested states 
        // each of these sections will have their own view
        // url will be nested (/form/profile)
        .state('form.service', {
            url: '/service',
           
            templateUrl: 'service.html'
        })
        
        // url will be /form/interests
        .state('form.image', {
            url: '/image',
            templateUrl: 'imageRegistory.html'
        })
        
        // url will be /form/payment
        .state('form.run', {
            url: '/run',
            templateUrl: 'run.html'
        })
    // url will be /form/payment
    .state('form.network', {
        url: '/network',
        templateUrl: 'network.html'
    })
    // url will be /form/payment
    .state('form.health', {
        url: '/health',
        templateUrl: 'healthcheckup.html'
    })
    
  /*  .state('form.routes', {
        url: '/routes',
        templateUrl: 'routes.html'
    })*/
    
    .state('form.volume', {
        url: '/volume',
        templateUrl: 'volumes.html'
    })
    
    .state('form.subnet', {
        url: '/subnet',
        templateUrl: 'subnetSelection.html'
    });
    // catch all route
    // send users to the form page 
    $urlRouterProvider.otherwise('/form/service');
})
;
// our controller for the form
// =============================================================================
app.controller('formController', function($scope,$http) {
	
    // we will store all of our form data in this object

	$scope.field={env:[]};
	$scope.env = [{envkey:'',envvalue:''}];
	$scope.image = {};
	$scope.service = {};
    
    
    // function to process the form
   $scope.processForm = function() { 
	   var apps_id=document.getElementById("appsid").value;
	  
        angular.forEach($scope.env,function(value){
   		 $scope.field.env.push(value);            
          })      
          
          $scope.field.appsId=apps_id;
        userData = angular.toJson($scope.field);
        var res = $http.post('/paas-gui/rest/applicationService/addService', userData);
        console.log(userData);
    	  res.success(function(data, status, headers, config) {
    	    $scope.message = data;
    	    
    	    
    	  });
    	  res.error(function(data, status, headers, config) {
//    	  	    alert("Error in storing Application Summary "+data);
    	  });
          
    };
    /* TO ADD DYNAMICA TABLE ROW FOR ENVIRONMENT VARIABLE */
    $scope.addNewEnvirnament = function() {
  	    $scope.env.push({envkey:'',envvalue:''});
    };
    /*TO REMOVE THE SPECIFIC NEWLY ADDED ENVIRONMENT VARIABLE */
    $scope.removeEnvirnament = function(index) {
 		  $scope.env.splice(index,1);
    };
    
    /*================== TO GET THE CONTAINER TYPE ==================*/
    //NEED TO SHOW IN DROP-DOWN LIST OF CONTAINER_TYPE FIELD IN THE SERVICE.HTML PAGE
    $scope.getAllRelatedContainerTypes = function() {
    	console.log("getAllRelatedContainerTypes ");
	    	var response = $http.get('/paas-gui/rest/containersService/getContainerTypesByTenantId');
	    	response.success(function(data){
	    		$scope.image = data;
	    		console.log("return data from db: "+$scope.image);
	    	});
	    	response.error(function(data, status, headers, config) {
	                alert("Error in Fetching Data");
	        });
	 };
	 /*================== END OF getAllRelatedContainerTypes ==================*/
	 
	    
	 /*================== TO GET THE IMAGE REGISTRY NAME ==================*/
	    //NEED TO SHOW IN DROP-DOWN LIST OF IMAGE_REGISTRY_NAME FIELD IN THE IMAGERESISTRY.HTML PAGE 
	 	 $scope.selectImageRegistry = function() {
	 		 
	    	var response = $http.get('/paas-gui/rest/imageRegistry/getAllImageRegistry');
	    	response.success(function(data){
	    		$scope.imageregistry = data;
	    		console.log("data given");
	    	});
	    	response.error(function(data, status, headers, config) {
	                alert("Error in Fetching Data of selectImageRegistry");
	        });
	    };
	    /*================== END OF selectImageRegistry ==================*/
	    
	    
	    /*================== To get the Dockerhub data ==================*/
	    //NEED TO SHOW IN DROP-DOWN LIST OF TAGS FIELD IN THE IMAGERESISTRY.HTML PAGE
	   	 $scope.selectSummary = function(reponame) {
	  	 $scope.reponames;
		//JSON.stringify(data);
	   		$scope.isImg=true;
	     	var response = $http.post('/paas-gui/rest/imageRegistry/getDockerHubRegistryTags',reponame);
	     	
	     	response.success(function(data){
	     		$scope.isImg=false;
	     		$scope.reponames = data;
	     		console.log("selectRepo >>>> "+$scope.reponames);
	     	});
	     	response.error(function(data, status, headers, config) {
	                 alert("Error in Fetching Application Summary"+data);
	         });
	     };
	     /*================== End of selectSummary ==================*/
	     
	     
	     /*================== get all service  details ==================*/
	     $scope.selectImageRegistry1 = function() {
	 		 alert("cccc");
		    	var response = $http.get('/paas-gui/rest/imageRegistry/getAllImageRegistry');
		    	response.success(function(data){
		    		 
		    	});
		    	response.error(function(data, status, headers, config) {
		                alert("Error in Fetching Data of selectImageRegistry");
		        });
		    };
		 /*================== End of getAllServiceDetails() ==================*/
		    
		    /*delete this method*/
		    $scope.selectImageRegistry2 = function() {
		    	 alert("comming to dddd"); 	
		    	 }; 
		    	 
		    	   /*======================= To get all Subnet details with current user  =========================*/
			     $scope.selectAllSubnet = function() {
			     
			   	//var response = $http.get('/PAAS-GUI/rest/fetchData/selectVpc');
			   	var response = $http.get('/paas-gui/rest/subnetService/getAllSubnet');
			   	response.success(function(data){
			   	
			   		$scope.subnetObj = data;
			   		console.log("data given>>>"+$scope.subnet);
			   	});
			   	response.error(function(data, status, headers, config) {
			         alert("Error in Fetching subnet Data"+data);
			       });
			   };  
		    
			   
			   $scope.selectCidrforSubent = function(subnet_name) {
				   	 $scope.field.cidr;
				  		 angular.forEach($scope.subnetObj,function(item){
				   			   if(item.subnetName == subnet_name){
				   				  
				   				   $scope.field.cidr = item.cidr;
				   				   console.log("tag"+$scope.field.cidr);
				   }
				   });
				    }
    
});

