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
  /*==================POPULATE DATA TO TABLE===================*/
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
 };      
 
 /*=================== delete*====================*/
 $scope.deleteAcl = function(data) {
 	 
  	var response = $http.get('/paas-gui/rest/aclService/deleteACLByNameUsingTenantId/'+data);
  	response.success(function(data){
  		$scope.selectAcl();
  	});
  	response.error(function(data, status, headers, config) {
              alert("Error in Fetching Data");
      });
  	
  };
  $scope.shareMyData = function (myValue) {
    $scope.dataToShare = myValue;
    srvShareData.addData($scope.dataToShare);
    
    window.location.href = "inoutbound_rule_interface.html";
  }
});

//Controller2: createNewAcl 
app.controller('createNewAcl', function($scope, srvShareData,$http) {
	$scope.acl = {};
	$scope.selectedAclId = srvShareData.getData();
	
	/*============ REGISTER ACL =============*/
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
	  	 
	  	};
	  	

});

//Controller2: createNewAcl 
app.controller('InOutBoundRuleCtrl', function($scope, srvShareData) {
	$scope.test = "ram";
	$scope.selectedAclId = srvShareData.getData();
	

});

//Controller3: createNewInOutBoundRule 
app.controller('createNewInOutBoundRule', function($scope, srvShareData) {
	
	$scope.rule = {};
	$scope.test = "ram";
	$scope.selectedAclId = srvShareData.getData()[0];
	$scope.regRule = function(selectedAclId){
		$scope.rule.aclId=selectedAclId;
		var userData = JSON.stringify($scope.rule);
		
		var res = $http.post('/paas-gui/rest/aclService/addInOutBoundRule', userData);
		  console.log(userData);
		  res.success(function(data, status, headers, config) {
			  console.log("data : " +" status : "+status+" headers : "+headers+"  config: "+config);
			// $location.path('/paas-gui/html/Acl.html');
		    //document.location.href = '/paas-gui/html/Acl.html';
		  });
		  res.error(function(data, status, headers, config) {
		    alert("failure message: " + JSON.stringify({
		      data : data
		    }));
		  });
	}

});


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