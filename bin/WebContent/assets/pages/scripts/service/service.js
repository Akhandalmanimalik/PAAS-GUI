var mysubnet = angular.module('serviceApp', []);

mysubnet.controller('serviceCtrl', function ($scope,$http) {
	
	$scope.field = {};
	
    $scope.showModal = false;
    $scope.toggleModal = function(){
        $scope.showModal = !$scope.showModal;
    };
    
    
   
    
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
     
  
  /*======================== TO DELETE THE SUBNET BY SUBNET NAME ==============================*/
    $scope.deleteSubnet = function(data) {
     	var response = $http.get('/paas-gui/rest/networkservice/deleteSubnetByName/'+data);
     	response.success(function(data){
   		 $scope.selectSubnetnew();

     	});
     	response.error(function(data, status, headers, config) {
            alert("Error in deletinf subnet "+data);
         });
     	
     };
     /*======================= END OF deleteSubnet =========================*/     
    
    
    
    
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