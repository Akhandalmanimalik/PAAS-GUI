var app = angular.module('myAcl', ['ngRoute']);

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
app.controller('MainCtrl', function($scope, srvShareData, $location,$http) {
  
  $scope.dataToShare = [];
  
  	// To get all the acl from the table associated with tenant id
	$scope.selectAcl = function() {
 	var response = $http.get('/paas-gui/rest/aclService/getAllACL');
 	response.success(function(data){
 		$scope.aclObj = data;
 		console.log($scope.fields);
 		 
 		console.log("data given");
 	});
 	response.error(function(data, status, headers, config) {
             alert("Error in Fetching Data");
     });
 };      //End of selectAcl method
 
 //deleteAcl is to delete the acl by id
 $scope.deleteAcl = function(data) {
 	 
  	var response = $http.get('/paas-gui/rest/aclService/deleteACLByaclId/'+data);
  	response.success(function(data){
  		$scope.selectAcl();
  	});
  	response.error(function(data, status, headers, config) {
              alert("Error in Fetching Data");
      });
  	
  };	//End of deleteAcl method
  
  //This method is used to share data within multiple controller
  $scope.shareMyData = function (myValue) {
    $scope.dataToShare = myValue;
    srvShareData.addData($scope.dataToShare);
    
    window.location.href = "inoutbound_rule_interface.html";
  }	//End of shareMyData method
  
  
  //editApplication to share data and render update page
  $scope.editAcl=function(acl){
 	 
 	 $scope.dataToShare = acl;
	    srvShareData.addData($scope.dataToShare);
	     window.location.href = "edit_acl.html"; 
 	 
  };//end of edit application
  
  
});	
/*===========================End of MainCtrl controller=======================================================*/


//Controller2: createNewAcl 
app.controller('createNewAcl', function($scope, srvShareData,$http) {
	$scope.acl = {};
	$scope.selectedAclId = srvShareData.getData();
	
	//regAcl method is to Register Acl into data base using tenant id
	 $scope.regAcl = function() {
	  	  console.log($scope.acl);
	  	  var userData = JSON.stringify($scope.acl);
	  	  var res = $http.post('/paas-gui/rest/aclService/addACLRule', userData);
	  	  console.log(userData);
	  	  res.success(function(data, status, headers, config) {
	  		  
	  		console.log("data "+data+" status "+status+" headers "+headers+" config "+config);
			if(data!='failed'){
				console.log("login success");
				/*document.location.href = '/paas-gui/html/acl.html'; also working*/ 
				window.location.href = "acl.html";
			}else{
				console.log("Login Error Please Enter Proper Details");
				/*document.location.href = '/paas-gui/html/acl_wizard.html'; also working*/
				window.location.href = "acl_wizard.html";
			}
	  	  });
	  	  res.error(function(data, status, headers, config) {
	  	    alert("failure message: " + JSON.stringify({
	  	      data : data
	  	    }));
	  	  });
	  	};	// End of regAcl method
	  	
	  	
	  	//ACL Server side validation
	    $scope.aclValidation = function(acl) {
		  	 console.log("<<<<<< acl validation >>>>>>>>>" +acl);
		  	  var res = $http.get('/paas-gui/rest/aclService/checkAcl/'+acl);
		  	  res.success(function(data, status, headers, config) {
		  		  
		  		if(data == 'success'){
		   	    	document.getElementById('aclerror').innerHTML="data exist enter different name";
		   	    	document.getElementById("myaclbtn").disabled = true;
		   	    	
		   		  }
		   		  else{
		   			 document.getElementById('aclerror').innerHTML="";
		   			document.getElementById("myaclbtn").disabled =false;
		   		  }
	 	  		 
		  	  });
		  	  res.error(function(data, status, headers, config) {
		  	    alert("failure message: " + JSON.stringify({
		  	      data : data
		  	    }));
		  	  });
		  	 
		  	}; //End of aclValidation method
		  	
		  	
});
/*=========================== End of createNewAcl controller =======================================================*/


//Controller3: InOutBoundRuleCtrl 
app.controller('InOutBoundRuleCtrl', function($scope, srvShareData,$http) {
	$scope.selectedAclId = srvShareData.getData()[0];
	$scope.inOutBndRule ={};
	
	 	//Get all in out bound rule
		$scope.getInOutBoundRuleByAclId = function(selectedAclId) {
			 
			var response = $http.get('/paas-gui/rest/aclService/getAllInOutBoundRuleByAclId/'+selectedAclId);
			response.success(function(data){
	 		$scope.inOutBndRule = data;
	 			console.log($scope.inOutBndRule);
	 			console.log("data given");
			});
			response.error(function(data, status, headers, config) {
				alert("Error in Fetching Data");
			});
	 };	//End of getInOutBoundRuleByAclId
	 
	 
	 //deleteInOutBoundById is to delete the  InOutBound  by id
	 $scope.deleteInOutBoundById = function(id) {
	 	 
	  	var response = $http.get('/paas-gui/rest/aclService/deleteInOutBoundRuleById/'+id);
	  	response.success(function(data){
	  		 window.location.href = "inoutbound_rule_interface.html"; 
	  	});
	  	response.error(function(data, status, headers, config) {
	              alert("Error in Fetching Data");
	      });
	  	
	  }; 	//End of deleteAcl method
	  
	  //shareInOutBoundId to share data and render update page
	  $scope.shareInOutBoundId=function(inOutBoundId){
	 	 
	 	 $scope.dataToShare = inOutBoundId;
		    srvShareData.addData($scope.dataToShare);
		     window.location.href = "edit-inoutbound-rule.html"; 
	  };//end of edit shareInOutBoundId
	 
	  
});
/*====================== End of InOutBoundRuleCtrl controller ==============================================*/

//Controller4: createNewInOutBoundRule 
app.controller('createNewInOutBoundRule', function($scope, srvShareData,$http) {
	
	$scope.rule = {};
	$scope.selectedAclId = srvShareData.getData()[0];
	$scope.regRule = function(selectedAclId){
		$scope.rule.aclId=selectedAclId;
		var userData = JSON.stringify($scope.rule);
		
		var res = $http.post('/paas-gui/rest/aclService/addInOutBoundRule', userData);
		  console.log(userData);
		  res.success(function(data, status, headers, config) {
			  console.log("data : " +" status : "+status+" headers : "+headers+"  config: "+config);
			  if(data == 'Success'){
					console.log("login success");
					/*document.location.href = '/paas-gui/html/acl.html'; also working*/ 
					window.location.href = "inoutbound_rule_interface.html";
				}else{
					console.log("Login Error Please Enter Proper Details");
					/*document.location.href = '/paas-gui/html/acl_wizard.html'; also working*/
					window.location.href = "create-new-in-out-bound-rule.html";
				}
		  });
		  res.error(function(data, status, headers, config) {
		    alert("failure message: " + JSON.stringify({
		      data : data
		    }));
		  });
	}

	
});
/*====================== End of createNewInOutBoundRule controller ==========================================*/



//Controller5: editAcl 
app.controller('editAcl', function($scope, srvShareData,$http) {
	
	$scope.sharedData = srvShareData.getData();
	$scope.acl = $scope.sharedData[0];
	
	$scope.updateAcl = function(acl){
		
		var userData = JSON.stringify($scope.acl);
		var res = $http.put('/paas-gui/rest/aclService/updateAclById/', userData);
		  console.log(userData);
		  res.success(function(data, status, headers, config) {
			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
			  if(data!='failed'){
					console.log("login success");
					/*document.location.href = '/paas-gui/html/acl.html'; also working*/ 
					window.location.href = "acl.html";
				}else{
					console.log("Login Error Please Enter Proper Details");
					/*document.location.href = '/paas-gui/html/acl_wizard.html'; also working*/
					 window.location.href = "edit_acl.html"; 
				}
			
		  });
		  res.error(function(data, status, headers, config) {
			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
		    alert("failure message: " + JSON.stringify({
		      data : data
		    }));
		  });
	}

	
});
/*====================== End of editAcl controller =========================================================*/


//Controller6: editInOutBoundController 
app.controller('editInOutBoundController', function($scope, srvShareData,$http) {
	$scope.sharedData = srvShareData.getData();
	$scope.rule = $scope.sharedData[0];
	$scope.updateInOutBoundRule = function(rule){
		var userData = JSON.stringify($scope.rule);
		var res = $http.put('/paas-gui/rest/aclService/updateInOutBoundRuleById/', userData);
		  console.log(userData);
		  res.success(function(data, status, headers, config) {
			  console.log("data : " +" status : "+status+" headers : "+headers+"  config: "+config);
			  var mydata = [];
	             mydata.push($scope.rule.aclId);
	             srvShareData.addData($scope.rule.aclId);
//	     		 $scope.sharedData=mydata;
	     		 
	     		
	     	    
	     		 
//	     		 window.location.href = "edit-inoutbound-rule.html";
	     		if(data =='Success'){
					console.log("login success");
					/*document.location.href = '/paas-gui/html/acl.html'; also working*/ 
					window.location.href = "inoutbound_rule_interface.html";
				}else{
					console.log("Login Error Please Enter Proper Details");
					/*document.location.href = '/paas-gui/html/acl_wizard.html'; also working*/
					window.location.href = "edit-inoutbound-rule.html";
				}
		  });
		  res.error(function(data, status, headers, config) {
		    alert("failure message: " + JSON.stringify({
		      data : data
		    }));
		  });
	}

	
});
/*====================== End of editInOutBoundController controller ========================================*/

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