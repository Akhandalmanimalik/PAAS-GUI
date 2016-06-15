var myvpc = angular.module('StorageApp', ['ui.bootstrap']);

myvpc.controller('StorageCtrl', function ($scope,$http) {
	$scope.scaling={};
	$scope.field = {};
	
	$scope.services={};

	
	
    $scope.showModal = false;
    $scope.toggleModal = function(){
        $scope.showModal = !$scope.showModal;
    };
    
    
    
    $scope.storage = function() {
    	console.log($scope.field);
    	var userData = JSON.stringify($scope.field);
    	var res = $http.post('/PAAS-GUI/rest/fetchData/storestorage', userData);
    	console.log(userData);

    	res.success(function(data, status, headers, config) {
    		$scope.message = data;
    		$scope.selectStorageList();
    		
    		//document.location.href = '/PAAS-GUI/html/login.html';
    	});
    	res.error(function(data, status, headers, config) {
    		alert("failure message: " + JSON.stringify({
    			data : data
    		}));
    	});
    };
    
    
    
   
  	
  	
  	 $scope.selectappfortag = function(reponame) {
  		 $scope.field.tag;
  		 $scope.env;
  			// alert("selectSummary  function");
  			//alert("reponame : "+reponame);

  			 angular.forEach($scope.services,function(item){
  				 
  				// alert("item: "+item.serviceName)
  				 
  				   if(item.serviceName == reponame){
  					  
  					   //alert("coming inside this ");
  					   $scope.field.tag = item.tag;
  					   console.log("tag"+$scope.field.tag);
  					   $scope.env=item.env;
  					  // alert("==="+$scope.env);
  					   // alert("====="+angular.Json(item.env));
  	 }
  	});
  		
  		/*response.success(function(data){
  			alert("data "+data);
  			$scope.tagdata=data;
  			alert("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj "+$scope.tagdata);
  		
  			
  		});*/
  		/*response.error(function(data, status, headers, config) {
  	          alert("Error in Fetching Data");
  	  });*/
  	};
  	 $scope.selectServices = function() {
  	   	var response = $http.get('/PAAS-GUI/rest/applicationService/getAllApplicationService');
  	   	response.success(function(data){
  	   		
  	   		$scope.services = data;
  	   		console.log("jjjjjjjj "+$scope.services);
  	   	});
  	   	response.error(function(data, status, headers, config) {
  	               alert("Error in Fetching Data");
  	       });
  	   };
  	
  	//marathons  call
  	 $scope.callMarathonsLaunch = function() {
  	  	  console.log($scope.field);
  	  
  	  	   userData = angular.toJson($scope.field);
  	  	   alert(userData);
  	  	  var res = $http.post('/PAAS-GUI/rest/fetchData/callMarathonsLaunch');
  	  	  
  	  	  res.success(function(data, status, headers, config) {
  	  	    $scope.message = data;
  	  	    
  	  	    
  	  	  });
  	  	  res.error(function(data, status, headers, config) {
  	  	    alert("failure message: " + JSON.stringify({
  	  	      data : data
  	  	    }));
  	  	  });
  	  	 
  	  	};
  	           /*POPULATE DATA TO TABLE*/
  
  	  $scope.selectStorageList = function() {
 		 //console.log("hiii");
    	var response = $http.get('/PAAS-GUI/rest/storageService/getAllStorage');
    	response.success(function(data){
    		$scope.storageList = data;
    		console.log("storage list "+$scope.storageList);
    		
    	});
    	response.error(function(data, status, headers, config) {
                alert("Error in Fetching Data");
        });
    };
    
     
     $scope.selectServices = function() {
      	var response = $http.get('/PAAS-GUI/rest/applicationService/getAllApplicationService');
      	response.success(function(data){
      		
      		$scope.services = data;
      		console.log("jjjjjjjj "+$scope.services);
      	});
      	response.error(function(data, status, headers, config) {
                  alert("Error in Fetching Data");
          });
      };
     
});  /*end of controller*/


myvpc.directive('modal', function () {
    return {
      template: '<div class="modal fade">' + 
          '<div class="modal-dialog modal-lg">' + 
            '<div class="modal-content" >' + 
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
    	$('.continue').click(function() {
			$('.nav-tabs > .active').next('li').find('a').trigger('click');
		});
		$('.back').click(function() {
			$('.nav-tabs > .active').prev('li').find('a').trigger('click');
		});
		$('.cancel').click(function() {
			$('.nav-tabs > .active').cancel('li').find('a').trigger('click');
		});
        
      }
    };
  });

myvpc.directive('scaling', function () {
    return {
      template: '<div class="modal fade">' + 
          '<div class="modal-dialog modal-lg">' + 
            '<div class="modal-content" >' + 
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
    	$('.continue').click(function() {
			$('.nav-tabs > .active').next('li').find('a').trigger('click');
		});
		$('.back').click(function() {
			$('.nav-tabs > .active').prev('li').find('a').trigger('click');
		});
		$('.cancel').click(function() {
			$('.nav-tabs > .active').cancel('li').find('a').trigger('click');
		});
        
      }
    };
  });
