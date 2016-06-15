var mysubnet = angular.module('mysubnet', []);

mysubnet.controller('SubnetCtrl', function ($scope,$http) {
	
	$scope.field = {};
	
    $scope.showModal = false;
    $scope.toggleModal = function(){
        $scope.showModal = !$scope.showModal;
    };
    
    
    /*subnet reg*/
    
    $scope.regSubnet = function() {
    	console.log($scope.field);
    	//var userData = JSON.stringify($scope.field);
    	var userData = angular.toJson($scope.field);
    	var res = $http.post('/PAAS-GUI/rest/networkservice/addSubnet', userData);
    	console.log(userData);
    	
    	res.success(function(data, status, headers, config) {
    		 $scope.selectSubnetnew();
    		//$scope.selectSubnet();
    		
    	});
    	res.error(function(data, status, headers, config) {
    		alert("Error in registering subnet  " +data );
    	});
    }; 
    
    
    /*POPULATE DATA TO TABLE*/
    
 	 $scope.selectSubnet = function() {
 		 
    	var response = $http.get('/PAAS-GUI/rest/networkservice/getAllVPC');
    	//var response = $http.get('/PAAS-GUI/rest/fetchData/selectSubnet');
    	response.success(function(data){
    		$scope.fields = data;
    		console.log(">>>>>>>  >>>  "+$scope.fields);
    	});
    	response.error(function(data, status, headers, config) {
                alert("Error in Fetching subnet Data"+data);
        });
    };           
    $scope.selectSubnetnew = function() {
		 console.log("selectSubnetnew");
  	//var response = $http.get('/PAAS-GUI/rest/fetchData/selectVpc');
  	var response = $http.get('/PAAS-GUI/rest/networkservice/getAllSubnet');
  	response.success(function(data){
  		$scope.fieldss = data;
  		console.log("data given");
  	});
  	response.error(function(data, status, headers, config) {
        alert("Error in Fetching subnet Data"+data);
      });
  };  
    /*subnet delete*/
    
    $scope.deleteSubnet = function(data) {
     	var response = $http.get('/PAAS-GUI/rest/networkservice/deleteSubnetByName/'+data);
     	response.success(function(data){
   		 $scope.selectSubnetnew();

     	});
     	response.error(function(data, status, headers, config) {
            alert("Error in deletinf subnet "+data);
         });
     	
     };
     
    
    
    
    
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