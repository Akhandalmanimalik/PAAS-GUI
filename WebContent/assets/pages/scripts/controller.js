var app = angular.module("MyApp", []);

app.controller("MyController", function($scope, $http) {

	$scope.fields = {};
	$scope.user="Test USer ";
                     /*registration controller*/
	$scope.reg = function() {
		alert("inside reg method of controller");
		console.log($scope.fields);
		var userData = JSON.stringify($scope.fields);

		var res = $http.post('/paas-gui/rest/registerAndLoginService/register', userData);
		console.log(userData);

		res.success(function(data, status, headers, config) {
			$scope.message = data;
			document.location.href = '/paas-gui/login.html';
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
	$scope.submitForm = function() {
		alert("submitForm (.) ");
        // check to make sure the form is completely valid
        if ($scope.userForm.$valid) {
            alert('our form is amazing');
        }

    };
	             /*login*/
	$scope.login = function() {
		
		
		var userData = JSON.stringify($scope.fields);
		var email = fields.email;
		/*var staus = validatemail(email);
		if(status false){
			
		}*/
		
		var res = $http.post('/paas-gui/rest/registerAndLoginService/login',userData);
		res.success(function(data, status, headers, config) {
 
		console.log("login sucessfull with  USer EMail:");
		try{
		$scope.user=data;
		console.log("Login sucess"+	$scope.user);
		}catch (e) {
			 
			console.log("Error In getting User Name : "+e);
		}
			 
			if(data!='failed'){
				alert("login success");
			document.location.href = '/paas-gui/application-listing.html';				
			}else{
				alert('Login Error Please Enter Proper Details');
				document.location.href = '/paas-gui/login.html';				

			}
			
			
			
			
		});
		
		res.error(function(data, status, headers, config) {
			alert("Inside error");
			alert("Error in Logining Please try agian : "+data );
		});
		
	};
	
	
	$scope.logout = function() {
	
	console.log("Inside logout method --------");
		var res = $http.get('/paas-gui/rest/registerAndLoginService/logout');
		res.success(function(data, status, headers, config) {
			
			document.location.href = '/paas-gui/login.html';				
			
			
		});
		res.error(function(data, status, headers, config) {
			document.location.href = '/paas-gui/login.html';				
		});
	};
	
	
	
        /*check email is exits or not*/
		$scope.checkemail = function() {
			console.log("HelloWorld");
			var email = $scope.fields.email;
			var res = $http.get('/paas-gui/rest/registerAndLoginService/'+email);
			res.success(function(data, status, headers, config) {
				$scope.fields.email = data;
				if(data==="Email Already Exists"){
					console.log("Incorrect");
					/*logic */
					
				}
				else{
					console.log("correct");
					/*logic	*/
					var res = $http.post('/paas-gui/rest/registerAndLoginService/register', userData);
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