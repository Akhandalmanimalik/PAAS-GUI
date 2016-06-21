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

/*================directive starts=================*/

applicationWizards.directive('modal', function () {
	return {
		template : '<div class="modal fade">'
				+ '<div class="modal-dialog">'
				+ '<div class="modal-content">'
				+ '<div class="modal-header">'
				+ '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
				+ '<h4 class="modal-title">{{ title }}</h4>'
				+ '</div>'
				+ '<div class="modal-body" ng-transclude></div>'
				+ '</div>' + '</div>' + '</div>',
		restrict : 'E',
		transclude : true,
		replace : true,
		scope : true,
		link : function postLink(scope, element, attrs) {
			scope.title = attrs.title;

			scope.$watch(attrs.visible, function(value) {
				if (value == true)
					$(element).modal('show');
				else
					$(element).modal('hide');
			});

			$(element).on('shown.bs.modal', function() {
				scope.$apply(function() {
					scope.$parent[attrs.visible] = true;
				});
			});

			$(element).on('hidden.bs.modal', function() {
				scope.$apply(function() {
					scope.$parent[attrs.visible] = false;
				});
			});
		}
	};
});