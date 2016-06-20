var myimageregistry = angular.module('myimageregistry', []);

myimageregistry.controller('MainCtrl', function ($scope,$http) {
	
	$scope.field = {};
	
    $scope.showModal = false;
    $scope.toggleModal = function(){
        $scope.showModal = !$scope.showModal;
    };
    
    
 /*============ ImageRegistry REG=============*/
    
    $scope.regImageRegistry = function() {
    
    	console.log($scope.field);
    	var userData = JSON.stringify($scope.field);
    	var response = $http.post('/paas-gui/rest/imageRegistry/addImageRegistry', userData);
    	
    	JSON.stringify(response);
    	
    	 
    
    	console.log(userData);

    	response.success(function(data, status) {
    		alert("coming to success");
    		$scope.message = data;
    	
    		window.location.href="/html/imageregistry.html";
    		/*window.location.href='http://localhost:8080/paas-gui/html/imageregistry.html';*/
    	});
    	response.error(function(data, status, headers, config) {
    		alert("failure message: " + JSON.stringify({
    			data : data
    		}));
    	});
    }; 
    
    
    /*==================POPULATE DATA TO TABLE===================*/
    
 	 $scope.selectImageRegistry = function() {
    	var response = $http.get('/paas-gui/rest/imageRegistry/getAllImageRegistry');
    	response.success(function(data){
    		$scope.fields = data;
    		console.log("data given");
    	});
    	response.error(function(data, status, headers, config) {
                alert("Error in Fetching Data of selectImageRegistry");
        });
    };           

    /*=================== delete*====================*/
   
    $scope.deleteImageRegistry = function(data,username) {
    	alert(username);
    	alert("coming delete"+username);
    	alert(data+" "+username);
     	var response = $http.get('/paas-gui/rest/imageRegistry/deleteImageRegistry/'+data+'/'+username);
     	response.success(function(data){
     		$scope.selectImageRegistry();
     	});
     	response.error(function(data, status, headers, config) {
                 alert("Error in Fetching Data"+data);
         });
     	
     };
     
     
     /*===============Image registry validation==============*/
	    $scope.registryValidation = function(name) {
	    	
	    	
		  	 console.log("<<<<<< acl validation >>>>>>>>>" +name);
		  	  var res = $http.get('/paas-gui/rest/imageRegistry/checkimageRegistry/'+name);
		  	  res.success(function(data, status, headers, config) {
		  		  
		  		if(data == 'success'){
		   	    	document.getElementById('registryerror').innerHTML="data exist enter different name";
		   	    	document.getElementById("registrybtn").disabled = true;
		   	    	
		   		  }
		   		  else{
		   			 document.getElementById('registryerror').innerHTML="";
		   			 
		   			/* if(document.getElementById('location').value !=''){
		   				 
		   			document.getElementById("registrybtn").disabled =false;
		   			 }*/
		   		  }
	 	  		 
		  	  });
		  	  res.error(function(data, status, headers, config) {
		  	    console.log("failure message: " + JSON.stringify({
		  	      data : data
		  	    }));
		  	  });
		  	 
		  	};
		    /*===============END Image registry validation==============*/
     
     
		   
		  	
		    $scope.registUsernameValidation = function(userName) {
		    	
		    	
			  	 console.log("<<<<<< acl validation >>>>>>>>>" +name);
			  	  var res = $http.get('/paas-gui/rest/imageRegistry/checkingUserName/'+userName);
			  	  res.success(function(data, status, headers, config) {
			  		  
			  		if(data == 'success'){
			   	    	document.getElementById('userNameerror').innerHTML="data exist enter different name";
			   	    	document.getElementById("registrybtn").disabled = true;
			   	    	
			   		  }
			   		  else{
			   			 document.getElementById('userNameerror').innerHTML="";
			   			 if(document.getElementById('location').value !=''){
			   				 
			   			document.getElementById("registrybtn").disabled =false;
			   			 }
			   		  }
		 	  		 
			  	  });
			  	  res.error(function(data, status, headers, config) {
			  	    alert("failure message: " + JSON.stringify({
			  	      data : data
			  	    }));
			  	  });
			  	 
			  	};
			    /*===============END Image registry validation==============*/
     
     
     
     
     
     
     
    
    
    
    
    
  });   /*================end of controllers===================*/




/*================directive starts=================*/

myimageregistry.directive('modal', function () {
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