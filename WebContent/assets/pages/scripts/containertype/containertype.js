var mycontainertype = angular.module('mycontainertype', []);

mycontainertype.controller('MainCtrl', function ($scope,$http,srvShareData) {
	
	$scope.field = {};
	
	$scope.dataToShare = [];
	  /**FOR THE SHARED DATE FROM ONE PAGE TO ANOTHER PAGE */
	  $scope.shareMyData = function (myValue) {

	    $scope.dataToShare = myValue;
	    srvShareData.addData($scope.dataToShare);
	    
	    window.location.href = "edit-containertype.html";
	  };/**END OF SHARED SCOPE */
    
      /**FOR INSERT CONTAINER TYPE============ Container Types REG=============*/
    
    $scope.regContainerTypes = function() {
    	console.log("inside the regContainerTypes");
    	console.log($scope.field);
    	var userData = JSON.stringify($scope.field);
    	var res = $http.post('/paas-gui/rest/containersService/insertContainerTypes/', userData);
    	

    	res.success(function(data, status, headers, config) {
    		console.log("the data is coming "+data);
    		$scope.message = data;
    		
    		//document.location.href = '/PAAS-GUI/html/containertype-interface.html';
    		if(data =='Success'){
				console.log("login success");
//				document.location.href = '/paas-gui/html/acl.html';  
				window.location.href = "containertype-interface.html";
			}else{
				console.log("Login Error Please Enter Proper Details");
//				document.location.href = '/paas-gui/html/acl_wizard.html'; also working
				 window.location.href = "create-new-image-registry.html"; 
			}
    	});
    	res.error(function(data, status, headers, config) {
    		console.log("data"+data);
    		alert("failure message: " + JSON.stringify({
    			data : data
    		}));
    	});
    }; /**END OF INSERT CONTAINER TYPES*/
    
    
    /**==================POPULATE DATA TO TABLE===================*/
    
 	 $scope.selectContainerTypes = function() {
 		 console.log("hiii");
    	var response = $http.get('/paas-gui/rest/containersService/getAllContainerTypesData');
    	response.success(function(data){
    		$scope.fields = data;
    		console.log("data given");
    	});
    	response.error(function(data, status, headers, config) {
                alert("Error in Fetching Data");
        });
    };/** END OF SELECT CONTAINER TYPES */           

    /**=================== delete*====================*/
   
    $scope.deleteContainerTypes = function(data) {
     	var response = $http.get('/paas-gui/rest/containersService/deleteContainerTypes/'+data);
     	response.success(function(data){
     		$scope.selectContainerTypes();
     	});
     	response.error(function(data, status, headers, config) {
     		console.log("error in data"+data);
                 alert("Error in Fetching Data");
         });
     	
     }; /** END OF DELETE SCOPE OF CONTAINER TYPES*/
      
    
    
  });/**END OF 'MainCtrl'   CONTROLLER */

/**START THE SERVICE CONTROLLER */
mycontainertype.service('srvShareData', function($window) {
    var KEY = 'App.SelectedValue';

    var addData = function(newObj) {
        var mydata = [];
       
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
});/**END OF THE 'srvShareData' SERVICE CONTROLLER */

/**START THE CONTAINER UPDATE CONTROLLER */
mycontainertype.controller('containertUpdateCtrl', function($scope, srvShareData,$http) {
	
	$scope.sharedData = srvShareData.getData();
	$scope.field = $scope.sharedData[0];
	
	$scope.updateContainerType = function(field){
		console.log("inside updateContainer Function calling ");
		var userData = JSON.stringify($scope.field);
		var res = $http.put('/paas-gui/rest/containersService/updateContainerType/', userData);
		  console.log(userData);
		  res.success(function(data, status, headers, config) {
			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
			  if(data == 'Success'){
					console.log("login success");
					/*document.location.href = '/paas-gui/html/acl.html'; also working*/ 
					window.location.href = "containertype-interface.html";
				}else{
					console.log("Login Error Please Enter Proper Details");
					/*document.location.href = '/paas-gui/html/acl_wizard.html'; also working*/
					 window.location.href = "edit-containertype.html"; 
				}
			
		  });
		  res.error(function(data, status, headers, config) {
			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
		    alert("failure message: " + JSON.stringify({
		      data : data
		    }));
		  });
	}

	
});   /** END OF THE 'containertUpdateCtrl' UPDATE CONTROLLER*/








        
