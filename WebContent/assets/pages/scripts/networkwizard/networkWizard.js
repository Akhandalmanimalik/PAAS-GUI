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
    	  var res = $http.post('/paas-gui/rest/vpcService/addVPC', vpc);
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
	  	  var res = $http.post('/paas-gui/rest/aclService/addACLRule', acl);
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
		  	  var res = $http.post('/paas-gui/rest/subnetService/addSubnet', subnet);
		  	  res.success(function(data, status, headers, config) {
	 	  		 
		  	  });
		  	  res.error(function(data, status, headers, config) {
		  	    alert("failure message: " + JSON.stringify({
		  	      data : data
		  	    }));
		  	  });
		  	 
		  	};
	/*===============END Add SUBNET details==============*/
    
		  	
		  	 /*===============Add vpc validation details==============*/
		  	
		  	 $scope.vpcValidation = function(vpc) {
		     	
		     	  console.log("vpc "+vpc);
		     	  var res = $http.get('/paas-gui/rest/vpcService/checkVPC/'+vpc);
		     	  res.success(function(data, status, headers, config) {
		   
		     	    	
		     		  if(data == 'success'){
		     	    	document.getElementById('errfn').innerHTML="data exist enter different name";
		     	    	document.getElementById("myvpcbtn").disabled = true;
		     	    	
		     		  }
		     		  else{
		     			 document.getElementById('errfn').innerHTML="";
		     			document.getElementById("myvpcbtn").disabled =false;
		     		  }
		     	    
		     	  });
		     	  res.error(function(data, status, headers, config) {
		     		 
		     	    alert("failure message: " + JSON.stringify({
		     	      data : data
		     	    }));
		     	  });
		     };
		     /*===============END of VPC validation==============*/	  	
		  	
		     /*===============Add Environments validation==============*/
			    $scope.environmentTypesValidation = function(environment) {
			    	var res = $http.get('/paas-gui/rest/environmentTypeService/checknvironmentType/'+environment);
			    	res.success(function(data, status, headers, config) {
			   		  if(data == 'success'){
			   	    	document.getElementById('environmenterr').innerHTML="data exist enter different name";
			   	    	document.getElementById("myenvbtn").disabled = true;
			   		  }
			   		else{
			   			document.getElementById("myenvbtn").disabled =false;
			   			document.getElementById('environmenterr').innerHTML="";
			   			}
			    	});
			    	res.error(function(data, status, headers, config) {
			    		alert("failure message: " + JSON.stringify({
			    			data : data
			    		}));
			    	});
			    }; //end of validation
			    
			    /*===============ACL validation==============*/
			    $scope.aclValidation = function(acl) {
			    	
			    	 
				  	  console.log("acl "+acl);
				  	  var res = $http.get('/paas-gui/rest/aclService/checkAcl/'+acl);
				  	  res.success(function(data, status, headers, config) {
				  		  
				  		if(data == 'success'){
				   	    	document.getElementById('aclerr').innerHTML="data exist enter different name";
				   	    	document.getElementById("myaclbtn").disabled = true;
				   	    	
				   		  }
				   		  else{
				   			 document.getElementById('aclerr').innerHTML="";
				   			 document.getElementById("myaclbtn").disabled =false;
				   		  }
			 	  		 
				  	  });
				  	  res.error(function(data, status, headers, config) {
				  	    alert("failure message: " + JSON.stringify({
				  	      data : data
				  	    }));
				  	  });
				  	 
				  	};
				    /*===============END Add ACL validation==============*/
				  
				  	
				  	/*=============== Add SUBNET validation==============*/
				  	$scope.subnetValidation = function(subnet) {
					  	  console.log("subnet "+subnet);
					  	  var res = $http.get('/paas-gui/rest/subnetService/checkSubnet/'+subnet);
					  	  res.success(function(data, status, headers, config) {
					  		  
					  		if(data == 'success'){
					   	    	document.getElementById('suberr').innerHTML="data exist enter different name";
					   	    	document.getElementById("mysubnetbtn").disabled = true;
					   	    	
					   		  }
					  		else{
					  			document.getElementById('suberr').innerHTML="";
					  			document.getElementById("mysubnetbtn").disabled =false;
					  			}
					  			
					  	  });
					  	  res.error(function(data, status, headers, config) {
					  	    alert("failure message: " + JSON.stringify({
					  	      data : data
					  	    }));
					  	  });
					  	 
					  	};
				/*===============END Add SUBNET validation==============*/
					  	
					  	
		  	
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