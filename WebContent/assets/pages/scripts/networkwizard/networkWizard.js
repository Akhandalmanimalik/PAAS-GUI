var mycloudprovider = angular.module('networkwizardApp', []);

mycloudprovider.controller('MainCtrl', function ($scope,$http) {
	
	$scope.field = {};
	$scope.network = {};
	
    $scope.showModal = false;
    $scope.toggleModal = function(){
        $scope.showModal = !$scope.showModal;
    };
    
    
/*============ ServiceAffinities REG=============*/
    
    $scope.addNetworkDet = function() {
    	 
    	var userData = JSON.stringify($scope.network);
    	$scope.regVpc($scope.network.vpc);
    	$scope.regEnvironmentTypes($scope.network.environment);
    	$scope.regAcl($scope.network.acl);
    	$scope.regSubnet($scope.network.subnet);
    }; 
    
    
    /*===============Add Environments details==============*/
    $scope.regVpc = function(vpc) {
    	  console.log("vpc "+vpc);
    	  var res = $http.post('/paas-gui/rest/networkservice/addVPC', vpc);
    	  res.success(function(data, status, headers, config) {
    	    $scope.message = data;
    	  });
    	  res.error(function(data, status, headers, config) {
    	    alert("failure message: " + JSON.stringify({
    	      data : data
    	    }));
    	  });
    };
    /*===============END VPC details==============*/
    
    
    /*===============Add Environments details==============*/
    $scope.regEnvironmentTypes = function(environment) {
    	 
    	var res = $http.post('/paas-gui/rest/environmentTypeService/insertEnvironmentType', environment);

    	res.success(function(data, status, headers, config) {
    		$scope.message = data;
    		
    		window.location.href ="/paas-gui/html/environmenttypes.html";
    	});
    	res.error(function(data, status, headers, config) {
    		alert("failure message: " + JSON.stringify({
    			data : data
    		}));
    	});
    }; 
    /*===============End Add Environments details==============*/

    /*===============Add ACL details==============*/
    $scope.regAcl = function(acl) {
	  	  console.log("acl "+acl);
	  	  var res = $http.post('/paas-gui/rest/networkservice/addACLRule', acl);
	  	  res.success(function(data, status, headers, config) {
 	  		 
	  	  });
	  	  res.error(function(data, status, headers, config) {
	  	    alert("failure message: " + JSON.stringify({
	  	      data : data
	  	    }));
	  	  });
	  	 
	  	};
	    /*===============END Add ACL details==============*/
	  	
	  	/*=============== Add SUBNET details==============*/
	  	$scope.regSubnet = function(subnet) {
		  	  console.log("subnet "+subnet);
		  	  var res = $http.post('/paas-gui/rest/networkservice/addSubnet', subnet);
		  	  res.success(function(data, status, headers, config) {
	 	  		 
		  	  });
		  	  res.error(function(data, status, headers, config) {
		  	    alert("failure message: " + JSON.stringify({
		  	      data : data
		  	    }));
		  	  });
		  	 
		  	};
	/*===============END Add SUBNET details==============*/
    
		  	
		  	
		  	
		  	
		  	
  });  /*================end of controller======================*/











/*=============directive starts============*/

mycloudprovider.directive('modal', function () {
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