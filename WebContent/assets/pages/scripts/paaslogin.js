/**
 * paaslogin.js
 */

//create angular app
			var validationApp = angular.module('validationApp', []);
		
			// create angular controller
			validationApp.controller('mainController', function($scope,$http) {
				$scope.fields = {};
				$scope.user="Test USer ";
				
				// function to submit the form after all validation has occurred            
				$scope.submitForm = function() {
					
				console.log("comming inside submit and validation");
					// check to make sure the form is completely valid
					if ($scope.userForm.$valid) {
						var userData = JSON.stringify($scope.fields);
						//var email = fields.email;
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
								console.log("login success");
							document.location.href = '/paas-gui/application-listing.html';				
							}else{
								console.log("Login Error Please Enter Proper Details");
								document.location.href = 'http://localhost:8080/paas-gui/';
								//document.location.href = '/paas-gui/login.html';				

							}
							
						});
						
						res.error(function(data, status, headers, config) {
							console.log("Inside error");
							alert("Error in Logining Please try agian : "+data );
						});
					}
		
				};
		
			});