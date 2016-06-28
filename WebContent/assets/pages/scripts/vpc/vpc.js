var app = angular.module('VpcApp', ['ngRoute']);

app.config(["$routeProvider", "$locationProvider", function($routeProvider, $locationProvider){
	$routeProvider
	.when("/", {
		templateUrl: "newController.html",
		controller: "MainCtrl"
	})
	.when("/page2", {
		templateUrl: "secondController.html",
		controller: "createNewAcl"
	})
	// .otherwise({ redirectTo: '/'})
	;
}]);

//Controller1: VpcCtrl
app.controller('VpcCtrl', function($scope, srvShareData, $location,$http) {
  
  $scope.dataToShare = [];
  
 
  
  /*This method is used to share data within multiple controller*/
  $scope.shareVpcObject = function (vpcObject) {
    $scope.dataToShare = vpcObject;
    srvShareData.addData($scope.dataToShare);
    
    window.location.href = "edit-vpc-wizard.html";
  }
  /*End of shareMyData method*/
  
  
  	 /*  selectVpc to retrieve all vpc associated with tenant id  */
  	 $scope.selectVpc = function() {
     	var response = $http.get('/paas-gui/rest/vpcService/getAllVPC');
     	response.success(function(data){
     		$scope.fields = data;
     	});
     	response.error(function(data, status, headers, config) {
                 alert("Error in Fetching Data");
         });
     };
     /* End of selectVpc */
     
     $scope.deleteData = function(data) {
      	var response = $http.get('/paas-gui/rest/vpcService/deleteVPCById/'+data);
      	response.success(function(data){
      		 window.location.href = "vpc-interface.html";
      	});
      	response.error(function(data, status, headers, config) {
                  alert("Error in Fetching Data");
          });
      	
      };
      $scope.sort = function(keyname){
  		$scope.sortKey = keyname;   //set the sortKey to the param passed
  		$scope.reverse = !$scope.reverse; //if true make it false and vice versa
  	};
      
});
//End of MainCtrl controller




//Controller2: createNewAcl 
app.controller('createNewVpc', function($scope, srvShareData,$http) {
	$scope.vpc = {};

	
 /*================VPC REGISTRATION===================*/
    
    $scope.regVpc = function() {
      if ($scope.vpcWizardForm.$valid) {
  	  console.log($scope.vpc);
  	  
  	  var userData = JSON.stringify($scope.vpc);
  	  var res = $http.post('/paas-gui/rest/vpcService/addVPC', userData);
  	  console.log(userData);
  	  res.success(function(data, status, headers, config) {
  	  $scope.message = data;
  	  $scope.selectVpc();
  	  window.location.href = "vpc-interface.html"; 
  	  
  	    
  	  });
  	  res.error(function(data, status, headers, config) {
  	    alert("failure message: " + JSON.stringify({
  	      data : data
  	    }));
  	  });
    }
  	};/* End of regVpc */
  	
  	/*==================POPULATE DATA TO TABLE===================*/
	 $scope.selectAcl = function() {
		
   	var response = $http.get('/paas-gui/rest/aclService/getAllACL');
   	response.success(function(data){
   		$scope.aclObj = data;
   		console.log($scope.aclObj);
   		 
   		console.log("data given");
   	});
   	response.error(function(data, status, headers, config) {
               alert("Error in Fetching Data");
       });
   };           

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
  			 /*if(document.getElementById('ACL').value != ''){
  			document.getElementById("myvpcbtn").disabled =false;
  			 }*/
  		  }
  	    
  	  });
  	  res.error(function(data, status, headers, config) {
  		 
  	    alert("failure message: " + JSON.stringify({
  	      data : data
  	    }));
  	  });
  };
  /*===============END of VPC validation==============*/
   
  /*  selectVpc to retrieve all vpc associated with tenant id  */
	 $scope.selectVpc = function() {
  	var response = $http.get('/paas-gui/rest/vpcService/getAllVPC');
  	response.success(function(data){
  		$scope.fields = data;
  	});
  	response.error(function(data, status, headers, config) {
              alert("Error in Fetching Data");
      });
  };
  /* End of selectVpc */
  
});
//End of createNewAcl controller

 
//Controller5: editVpc 
app.controller('editVpc', function($scope, srvShareData,$http) {
	
	$scope.sharedData = srvShareData.getData();
	$scope.vpc = $scope.sharedData[0];
	
	$scope.updateVpc = function(vpc){
		
		var userData = JSON.stringify($scope.vpc);
		var res = $http.put('/paas-gui/rest/vpcService/updateVPC/', userData);
		  console.log(userData);
		  res.success(function(data, status, headers, config) {
			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
			  if(data!='failed'){
					console.log("login success");
					/*document.location.href = '/paas-gui/html/acl.html'; also working*/ 
					window.location.href = "vpc-interface.html";
				}else{
					console.log("Login Error Please Enter Proper Details");
					/*document.location.href = '/paas-gui/html/acl_wizard.html'; also working*/
					 window.location.href = "edit-vpc-wizard.html"; 
				}
			
		  });
		  res.error(function(data, status, headers, config) {
			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
		    alert("failure message: " + JSON.stringify({
		      data : data
		    }));
		  });
	}

	 /*===============Add vpc validation details==============*/
 	 $scope.vpcValidationForUpdate = function(vpc) {
 		 
    	
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
    			 /*if(document.getElementById('ACL').value != ''){
    			document.getElementById("myvpcbtn").disabled =false;
    			 }*/
    		  }
    	    
    	  });
    	  res.error(function(data, status, headers, config) {
    		 
    	    alert("failure message: " + JSON.stringify({
    	      data : data
    	    }));
    	  });
    };
    /*===============END of VPC validation==============*/
	
    /*==================POPULATE DATA TO TABLE===================*/
	 $scope.selectAcl = function() {
		
  	var response = $http.get('/paas-gui/rest/aclService/getAllACL');
  	response.success(function(data){
  		$scope.aclObj = data;
  		console.log($scope.aclObj);
  		 
  		console.log("data given");
  	});
  	response.error(function(data, status, headers, config) {
              alert("Error in Fetching Data");
      });
  };
    
    /*===============ACL validation CURRENTLY NOT USED ==============*/
    $scope.aclValidation = function(acl) {
    	
    
	  	  console.log("acl >>>>>>>>>>>>>>>>>>. "+acl);
	  	  var res = $http.get('/paas-gui/rest/aclService/checkAcl/'+acl);
	  	  res.success(function(data, status, headers, config) {
	  		  
	  		if(data == 'success'){
	   	    	document.getElementById('aclerr').innerHTML="data exist enter different name";
	   	    	document.getElementById("myvpcbtn").disabled = true;
	   	    	
	   		  }
	   		  else{
	   			 document.getElementById('aclerr').innerHTML="";
	   			document.getElementById("myvpcbtn").disabled =false;
	   		  }
 	  		 
	  	  });
	  	  res.error(function(data, status, headers, config) {
	  	    alert("failure message: " + JSON.stringify({
	  	      data : data
	  	    }));
	  	  });
	  	 
	  	};
	    /*===============END Add ACL validation==============*/
	
});
//End of createNewInOutBoundRule controller


app.service('srvShareData', function($window) {
        var KEY = 'App.SelectedValue';

        var addData = function(newObj) {
        	mydata = [];
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