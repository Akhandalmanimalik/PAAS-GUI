/**
 * 
 */

// create our angular app and inject ngAnimate and ui-router 
// =============================================================================
angular.module('formApp', ['ngAnimate', 'ui.router'])

// configuring our routes 
// =============================================================================
.config(function($stateProvider, $urlRouterProvider) {
    
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

// our controller for the form
// =============================================================================
.controller('formController', function($scope,$http) {
    
	
    // we will store all of our form data in this object
	
	$scope.field={env:[]};
	$scope.env = [{envkey:'',envvalue:''}];
	$scope.image = {};
	$scope.service = {};
    
    
    // function to process the form
   $scope.processForm = function() {
        alert("awesome!");
        
        angular.forEach($scope.env,function(value){
   		 $scope.field.env.push(value);            
          })
       alert("BEFORE STRINGFY");   	
        userData = angular.toJson($scope.field);
       alert("after strinfy"+userData);
        var res = $http.post('/paas-gui/rest/applicationService/addService', userData);
        console.log(userData);
    	  res.success(function(data, status, headers, config) {
    	    $scope.message = data;
    	    
    	    
    	  });
    	  res.error(function(data, status, headers, config) {
//    	  	    alert("Error in storing Application Summary "+data);
    	  });
          
    };
    
    $scope.addNewEnvirnament = function() {
  	    $scope.env.push({envkey:'',envvalue:''});
    };

    $scope.removeEnvirnament = function(index) {
 		  $scope.env.splice(index,1);
    };
    
    
    //NEED TO SHOW IN DROP-DOWN LIST OF CONTAINER_TYPE FIELD IN THE SERVICE.HTML PAGE
    $scope.getAllRelatedContainerTypes = function() {
    	console.log("getAllRelatedContainerTypes ");
	    	var response = $http.get('/paas-gui/rest/policiesService/getContainerTypesByTenantId');
	    	response.success(function(data){
	    		$scope.image = data;
	    		console.log("return data from db: "+$scope.image);
	    	});
	    	response.error(function(data, status, headers, config) {
	                alert("Error in Fetching Data");
	        });
	    };
	    
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
	     
	     /*for table*/
		  //Envirnament  add
		
    
    
});

