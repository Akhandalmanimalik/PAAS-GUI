/**
 * 
 */

var app = angular.module("MyApp", []);

app.controller("MyController", function($scope, $http) {
	
	$scope.fields = {};
	$scope.user="Test USer ";
	
	$scope.login = function() {
		console.log("comming to login function of Angular js");
		var userData = JSON.stringify($scope.fields);
		var res = $http.post('/paas-gui/rest/registerAndLoginService/check',userData);
		res.success(function(data, status, headers, config) {
		console.log("login sucessfull with  USer EMail:");

			try{
				$scope.user=data;
				console.log("Login sucess"+	$scope.user);
			}catch (e) {
				console.log("Error In getting User Name : "+e);
			}
			
		});
		res.error(function(data, status, headers, config) {
			alert("Error in Logining Please try agian : "+data );
		});
	};
});
