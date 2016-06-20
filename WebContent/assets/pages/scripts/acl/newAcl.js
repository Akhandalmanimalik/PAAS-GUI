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

app.controller('MainCtrl', function($scope, srvShareData, $location,$http) {
  
  $scope.dataToShare = [];
  /*==================POPULATE DATA TO TABLE===================*/
	$scope.selectAcl = function() {
	alert("comming");
 	var response = $http.get('/paas-gui/rest/networkservice/getAllACL');
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
 
  $scope.shareMyData = function (myValue) {
    $scope.dataToShare = myValue;
    srvShareData.addData($scope.dataToShare);
    
    window.location.href = "secondController.html";
  }
});

app.controller('createNewAcl', function($scope, srvShareData) {
	$scope.field = {};
	$scope.test = "ram";
  $scope.sharedData = srvShareData.getData();

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