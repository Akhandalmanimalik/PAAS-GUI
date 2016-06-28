var app = angular.module('mysubnet', ['ngRoute']);

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

//Controller1: MainCtrl
app.controller('SubnetCtrl', function($scope, srvShareData, $location,$http) {
  
  $scope.dataToShare = [];
  

  
  /*This method is used to share data within multiple controller*/
  $scope.shareSubnetObject = function (subnetObject) {
    $scope.dataToShare = subnetObject;
    srvShareData.addData($scope.dataToShare);
    
    window.location.href = "edit-subnet.html";
  }
  /*End of shareMyData method*/
  
  
  /*======================= To get all Subnet details with current user  =========================*/
  $scope.selectSubnetnew = function() {
  
	//var response = $http.get('/PAAS-GUI/rest/fetchData/selectVpc');
	var response = $http.get('/paas-gui/rest/subnetService/getAllSubnet');
	response.success(function(data){
	
		$scope.subnet = data;
		console.log("data given>>>"+$scope.subnet);
	});
	response.error(function(data, status, headers, config) {
      alert("Error in Fetching subnet Data"+data);
    });
};  
/*======================= END OF selectSubnetnew =========================*/
  
      
/*======================== TO DELETE THE SUBNET BY SUBNET NAME ==============================*/
$scope.deleteSubnet = function(id) {
	console.log("subnet id : "+id);
 	var response = $http.get('/paas-gui/rest/subnetService/deleteSubnetById/'+id);
 	response.success(function(data){
		 $scope.selectSubnetnew();

 	});
 	response.error(function(data, status, headers, config) {
        alert("Error in deletinf subnet "+data);
     });
 };
 /*======================= END OF deleteSubnet =========================*/
 
 
});
//End of MainCtrl controller




//Controller2: createSubnetCtrl 
app.controller('createSubnetCtrl', function($scope, srvShareData,$http) {
	$scope.subnet = {};
	 /*===================== To add subnet details into db =========================*/
    $scope.regSubnet = function() {
    	console.log($scope.subnet);
    	//var userData = JSON.stringify($scope.subnet);
    	var userData = angular.toJson($scope.subnet);
    	var res = $http.post('/paas-gui/rest/subnetService/addSubnet', userData);
    	console.log(userData);
    	
    	res.success(function(data, status, headers, config) {
    		 
    		 window.location.href = "subnet-interface.html";
    		
    	});
    	res.error(function(data, status, headers, config) {
    		alert("Error in registering subnet  " +data );
    	});
    }; 
    /*===================== END OF regSubnet =========================*/
	
    /*============ To get all vpc with current user ======================*/
    $scope.getAllVpc = function() {
    							  
    	var response = $http.get('/paas-gui/rest/vpcService/getAllVPC');
    	
    	response.success(function(data){
    	
    		$scope.vpcObj = data;
    		console.log(">>>>>>>  >>>  "+$scope.fields);
    	});
    	response.error(function(data, status, headers, config) {
                alert("Error in Fetching subnet Data"+data);
        });
    };
    /*======================= END OF getAllVpc =========================*/
    
    
    /*======================== TO GET ALL ENVIRONMENT NAME WITH THE CURRENT USER ====================*/
    $scope.selectEnvironment_name = function() {
   	var response = $http.get('/paas-gui/rest/environmentTypeService/getAllEnvironmentType');
   	//var response = $http.get('/PAAS-GUI/rest/fetchData/selectSubnet');
   	response.success(function(data){
   		$scope.environment = data;
   		console.log(">>>>>>>  >>>  "+$scope.environment);
   	});
   	response.error(function(data, status, headers, config) {
               alert("Error in Fetching environment Data"+data);
       });
   };   
   /*======================= END OF selectEnvironment_name =========================*/
	  	
   
   /*===================== TO GET ALL ACL NAME WITH CURRENT USER ============================*/
   $scope.selectACL_name = function() {
  	var response = $http.get('/paas-gui/rest/aclService/getAllACL');
  	
  	//var response = $http.get('/PAAS-GUI/rest/fetchData/selectSubnet');
  	
  	
  	response.success(function(data){
  	
  		$scope.acl = data;
  		console.log(">>>>>>>  >>>  "+$scope.acl);
  	});
  	response.error(function(data, status, headers, config) {
           alert("Error in Fetching environment Data"+data);
   });
  };   
  /*======================= END OF selectACL_name =========================*/

  /*=============== Add SUBNET validation==============*/
	$scope.subnetValidation = function(subnet) {
	  	  console.log("subnet "+subnet);
	  	  var res = $http.get('/paas-gui/rest/subnetService/checkSubnet/'+subnet);
	  	  res.success(function(data, status, headers, config) {
	  		  
	  		if(data == 'success'){
	   	    	document.getElementById('subneterror').innerHTML="data exist enter different name";
	   	    	document.getElementById("mysubnetbtn").disabled = true;
	   	    	
	   		  }
	   		  else{
	   			 document.getElementById('subneterror').innerHTML="";
	   			document.getElementById("mysubnetbtn").disabled =false;
	   			/* if(document.getElementById('cidr').value !=''){
	   			document.getElementById("mysubnetbtn").disabled =false;
	   		  }*/
	   		  }
	  		 
	  		  
	  		 
	  	  });
	  	  res.error(function(data, status, headers, config) {
	  	    alert("failure message: " + JSON.stringify({
	  	      data : data
	  	    }));
	  	  });
	  	 
	  	};
/*===============END Add SUBNET validation==============*/
});
//End of createNewAcl controller

 
//Controller5: createNewInOutBoundRule 
app.controller('editSubnet', function($scope, srvShareData,$http) {


	
	$scope.sharedData = srvShareData.getData();
	  
	$scope.subnet = $scope.sharedData[0];
	
	
	$scope.updateSubnet = function(subnet){
		var userData = JSON.stringify($scope.subnet);
		var res = $http.put('/paas-gui/rest/subnetService/updateSubnet/', userData);
		  console.log(userData);
		  res.success(function(data, status, headers, config) {
			  
			  console.log("data : " +" status : "+status+" headers : "+headers+"  config: "+config);
			  if(data =='Success'){
				  
					console.log("login success");
					/*document.location.href = '/paas-gui/html/acl.html'; also working*/ 
					window.location.href = "subnet-interface.html";
				}else{
					console.log("Login Error Please Enter Proper Details");
					/*document.location.href = '/paas-gui/html/acl_wizard.html'; also working*/
					 window.location.href = "edit-subnet.html"; 
				}
			
		  });
		  res.error(function(data, status, headers, config) {
		    alert("failure message: " + JSON.stringify({
		      data : data
		    }));
		  });
	}

	
	/*============ To get all vpc with current user ======================*/
    $scope.getAllVpc = function() {
    							  
    	var response = $http.get('/paas-gui/rest/vpcService/getAllVPC');
    	
    	response.success(function(data){
    	
    		$scope.vpcObj = data;
    		console.log(">>>>>>>  >>>  "+$scope.fields);
    	});
    	response.error(function(data, status, headers, config) {
                alert("Error in Fetching subnet Data"+data);
        });
    };
    /*======================= END OF getAllVpc =========================*/
    
    
    /*======================== TO GET ALL ENVIRONMENT NAME WITH THE CURRENT USER ====================*/
    $scope.selectEnvironment_name = function() {
   	var response = $http.get('/paas-gui/rest/environmentTypeService/getAllEnvironmentType');
   	//var response = $http.get('/PAAS-GUI/rest/fetchData/selectSubnet');
   	response.success(function(data){
   		$scope.environment = data;
   		console.log(">>>>>>>  >>>  "+$scope.environment);
   	});
   	response.error(function(data, status, headers, config) {
               alert("Error in Fetching environment Data"+data);
       });
   };   
   /*======================= END OF selectEnvironment_name =========================*/
	  	
   
   /*===================== TO GET ALL ACL NAME WITH CURRENT USER ============================*/
   $scope.selectACL_name = function() {
  	var response = $http.get('/paas-gui/rest/aclService/getAllACL');
  	
  	//var response = $http.get('/PAAS-GUI/rest/fetchData/selectSubnet');
  	
  	
  	response.success(function(data){
  	
  		$scope.acl = data;
  		console.log(">>>>>>>  >>>  "+$scope.acl);
  	});
  	response.error(function(data, status, headers, config) {
           alert("Error in Fetching environment Data"+data);
   });
  };   
  /*======================= END OF selectACL_name =========================*/

	
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