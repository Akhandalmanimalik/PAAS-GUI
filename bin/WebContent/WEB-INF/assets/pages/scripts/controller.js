var app = angular.module("MyApp", []);

app.controller("MyController", function($scope, $http) {

	$scope.fields = {};
	$scope.user="Test USer ";
                     /*registration controller*/
	$scope.reg = function() {
		console.log($scope.fields);
		var userData = JSON.stringify($scope.fields);

		var res = $http.post('/PAAS-GUI/rest/registerAndLoginService/register', userData);
		console.log(userData);

		res.success(function(data, status, headers, config) {
			$scope.message = data;
			document.location.href = '/PAAS-GUI/html/login.html';
		});
		res.error(function(data, status, headers, config) {
			alert("ERROR in Registering User " + data);
		});
	};
	           /*reset function*/
	$scope.reset = function() {
		$scope.list = {};
	};

	/*$scope.login = function() {
		console.log("HelloWorld");
		var app = angular.module('MyApp', ['UserValidation']);
		angular.module('UserValidation', []).directive('validPasswordC', function () {
			return {
				require: 'ngModel',
				link: function (scope, elm, attrs, ctrl) {
					ctrl.$parsers.unshift(function (viewValue, $scope) {
						var noMatch = viewValue != scope.myForm.password.$viewValue;
						ctrl.$setValidity('noMatch', !noMatch)
					})
				}
			}
		})*/
	             /*login*/
	$scope.login = function() {
		var userData = JSON.stringify($scope.fields);
		var res = $http.post('/PAAS-GUI/rest/registerAndLoginService/login',userData);
		alert("after AngAjaxJqeryCall");
		res.success(function(data, status, headers, config) {
			
			alert("Inside Success");
			console.log("login sucessfull with  USer EMail:");

			try{
			$scope.user=data;

			console.log("Login sucess"+	$scope.user);
			}catch (e) {
				alert("Error In getting User Name : "+e);
				console.log("Error In getting User Name : "+e);
	
			}
			 
			if(data!='failed'){
				alert("failed");
			document.location.href = '/PAAS-GUI/application-listing.html';				
			}else{
				alert('Login Error Please Enter Proper Details');
				document.location.href = '/PAAS-GUI/login.html';				

			}
			
		});
		alert("before error");
		res.error(function(data, status, headers, config) {
			alert("Inside success");
			alert("Error in Logining Please try agian : "+data );
		});
		alert("after error");
	};
	
	
	$scope.logout = function() {
	
	console.log("Inside logout method --------");
		var res = $http.get('/PAAS-GUI/rest/registerAndLoginService/logout');
		res.success(function(data, status, headers, config) {
			
			document.location.href = '/PAAS-GUI/html/login.html';				
			
			
		});
		res.error(function(data, status, headers, config) {
			document.location.href = '/PAAS-GUI/html/login.html';				
		});
	};
	
	
	
             /*check email is exits or not*/
		$scope.checkemail = function() {
			console.log("HelloWorld");
			var email = $scope.fields.email;
			var res = $http.get('/PAAS-GUI/rest/registerAndLoginService/'+email);
			res.success(function(data, status, headers, config) {
				$scope.fields.email = data;
				if(data==="Email Already Exists"){
					console.log("Incorrect");
					/*logic */
					
				}
				else{
					console.log("correct");
					/*logic	*/
					var res = $http.post('/PAAS-GUI/rest/registerAndLoginService/register', userData);
					console.log(userData);
				}
			});
		};
		
		
		
                 /*vpc*/
/*
		$scope.regVpc = function() {

			window.alert("hiiiii");
			console.log($scope.vpc);
			var userData = JSON.stringify($scope.vpc);
			var res = $http.post('/PAAS-GUI/rest/fetchData/vpc', userData);
			console.log(userData);

			res.success(function(data, status, headers, config) {
				$scope.message = data;
				alert("success");
				//document.location.href = '/PAAS-GUI/html/login.html';
			});
			res.error(function(data, status, headers, config) {
				alert("failure message: " + JSON.stringify({
					data : data
				}));
			});
	
				};
*/









	/*$scope.collect = function() {
		console.log($scope.vpc);
		var userData = JSON.stringify($scope.vpc);
		var res = $http.post('/pass-gui/rest/fetchData/insertvpc', userData);
		console.log(userData);

		res.success(function(data, status, headers, config) {
			$scope.message = data;
		});
		res.error(function(data, status, headers, config) {
			alert("failure message: " + JSON.stringify({
				data : data
			}));
		});
	};

	$scope.loadactivity = function() {
		$('#contentdisplay').load('activity.html');
	};
	 */
});