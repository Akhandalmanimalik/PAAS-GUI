var myenvironmenttype = angular.module('myenvironmenttype', []);

myenvironmenttype.controller('MainCtrl', function ($scope,$http,srvShareData) {
	
	$scope.field = {};
	
	 
	$scope.dataToShare = [];
	  /**FOR SHARED THED DATA IN DIFFERERT PAGE */
	  $scope.shareMyData = function (myValue) {

	    $scope.dataToShare = myValue;
	    srvShareData.addData($scope.dataToShare);
	    
	    window.location.href = "edit-environmenttype.html";
	  };/**END OF SHARED SCOPE */
    
 /**============ ENVIRONMENT TYPES REG=============*/ // DONE
    
    $scope.regEnvironmentTypes = function() {
    	 
    	console.log($scope.field);
    	var userData = JSON.stringify($scope.field);
    	var res = $http.post('/paas-gui/rest/environmentTypeService/insertEnvironmentType', userData);
    	console.log(userData);

    	res.success(function(data, status, headers, config) {
    		if(data =='success'){
				console.log("login success");
				/*document.location.href = '/paas-gui/html/acl.html'; also working*/ 
				window.location.href = "environmenttypes.html";
			}else{
				console.log("Login Error Please Enter Proper Details");
				/*document.location.href = '/paas-gui/html/acl_wizard.html'; also working*/
				window.location.href = "create-new-environment.html";
			}
    	});
    	res.error(function(data, status, headers, config) {
    		alert("failure message: " + JSON.stringify({
    			data : data
    		}));
    	});
    }; /**END OF THE INSERT ENVIRONMENT TYPE */
    
    /**==================POPULATE DATA TO TABLE===================*/ // DONE
    
 	 $scope.selectEnvironmentTypes = function() {
 		 
 		 console.log("hiii");
    	var response = $http.get('/paas-gui/rest/environmentTypeService/getAllEnvironmentType');
    	response.success(function(data){
    		$scope.field = data;
    		console.log("data given");
    	});
    	response.error(function(data, status, headers, config) {
                alert("Error in Fetching Data");
        });
    };  /**END OF THE SELECT THE DATA FROM ENVIRONMENT TYPE */ 
    
/*    $scope.selectEnvironmentTypes = function() {
		 //console.log("hiii");
   	var response = $http.get('/paas-gui/rest/fetchData/displaygateway');
   	response.success(function(data){
   		$scope.display = data;
   		console.log("data given");
   	});
   	response.error(function(data, status, headers, config) {
               alert("Error in Fetching Data");
       });
   };   */                                          //Done
   /* $scope.selectEnvirnamentList = function() {
		 //console.log("hiii");
   	var response = $http.get('/pass-gui/rest/environmentTypeService/getAllEnvironamentList');
   	response.success(function(data){
   		$scope.envirnamentlist = data;
   		
   	});
   	response.error(function(data, status, headers, config) {
               alert("Error in Fetching Data");
       });
   }; 
*/
    /**=================== delete*====================// DONE */ 
   
    $scope.deleteEnvironmentTypes = function(data) {
     	var response = $http.post('/paas-gui/rest/environmentTypeService/deleteEnvironmentById/'+data);
     	response.success(function(data){
     		$scope.selectEnvironmentTypes();
     	});
     	response.error(function(data, status, headers, config) {
                 alert("Error in Fetching Data");
         });
     	
     };/**END OF THE DELETE ENVIRONMENT */
    
     //Done
    
    /* $scope.getApplicationName = function(data) {
      	var response = $http.get('/paas-gui/rest/environmentTypeService/getApplicationSummary');
      	response.success(function(data){
      		$scope.applicationlist=data;
      		
      		console.log("-------$scope.applicationlist----"+$scope.applicationlist);
      	});
      	response.error(function(data, status, headers, config) {
                  alert("Error in Fetching Data");
          });
      	
      };
    */
    /*  $scope.selectapp = function(reponame) {
 		// alert("selectSummary  function");
 		$scope.reponames;
 //JSON.stringify(data);
 		
 		$scope.isImg=true;
 	var response = $http.post('/paas-gui/rest/environmentTypeService/getImageRepositoryFromSummary',reponame);   //to do
 	
 	response.success(function(data){
 		$scope.isImg=false;
 		$scope.reponames = data;
 		console.log("selectRepoName >>>> "+$scope.reponames);
 	});
 	response.error(function(data, status, headers, config) {
             alert("Error in Fetching Data");
     });
 };
 
 $scope.storeEnvironments = function() {   //Done
    	console.log($scope.field);
    	var userData = JSON.stringify($scope.field);
    	var res = $http.post('/paas-gui/rest/environmentTypeService/insertEnvironmentsData', userData);
    	console.log(userData);

    	res.success(function(data, status, headers, config) {
    		$scope.message = data;
    		$scope.selectEnvirnamentList();
    		
    		//document.location.href = '/paas-gui/html/login.html';
    	});
    	res.error(function(data, status, headers, config) {
    		alert("failure message: " + JSON.stringify({
    			data : data
    		}));
    	});
    }; */
 /**SELECT SERVICE NAME IN TAG IN DROPDOWN OPTION */
 $scope.selectappfortag = function(reponame) {
	 $scope.field.tag;
	 $scope.env;
		// alert("selectSummary  function");
		//alert("reponame : "+reponame);

		 angular.forEach($scope.services,function(item){
			 
			// alert("item: "+item.serviceName)
			 
			   if(item.serviceName == reponame){
				  
				   
				   $scope.field.tag = item.tag;
				   console.log("tag"+$scope.field.tag);
				   $scope.env=item.env;
				  // alert("==="+$scope.env);
				   // alert("====="+angular.Json(item.env));
 }
});/**END OF TNE SELECT TAG FOR OPTION */
	
	/*response.success(function(data){
		alert("data "+data);
		$scope.tagdata=data;
		alert("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj "+$scope.tagdata);
	
		
	});*/
	/*response.error(function(data, status, headers, config) {
          alert("Error in Fetching Data");
  });*/
};
    /**GET ALL APPLICATION SERVICES IN THE TABLE */
 $scope.selectServices = function() {  
   	var response = $http.get('/pass-gui/rest/applicationService/getAllApplicationService');
   	response.success(function(data){
   		
   		$scope.services = data;
   		console.log("jjjjjjjj "+$scope.services);
   	});
   	response.error(function(data, status, headers, config) {
               alert("Error in Fetching Data");
       });
   };/**END OF THE APPLICATION SERVICE SCOPE*/
 
   
 /**===============Add Environments validation==============*/
   
   $scope.environmentTypesValidation = function(environment) {
   	
   	var res = $http.get('/paas-gui/rest/environmentTypeService/checknvironmentType/'+environment);

   	res.success(function(data, status, headers, config) {
   		
  		  if(data == 'success'){
  	    	document.getElementById('environmenterr').innerHTML="data exist enter different name";
  	    	document.getElementById("myenvbtn").disabled = true;
  		  }
  		  else{
  			 document.getElementById('environmenterr').innerHTML="";
  			document.getElementById("myenvbtn").disabled =false;
  		  }
   	});
   	res.error(function(data, status, headers, config) {
   		alert("failure message: " + JSON.stringify({
   			data : data
   		}));
   	});
   }; /**END OF THE VALIDATION FOR ENVIRONMENT TYPES NAME */
   

}); /** END OF THE CONTROLLER OF-- ('MainCtrl')---*/


/** FOR UPDATE PAGE ,2ND CONTROLLER START */
myenvironmenttype.controller('environmentUpdateCtrl', function($scope, srvShareData,$http) {
	
	$scope.sharedData = srvShareData.getData();
	$scope.field = $scope.sharedData[0];
	/** UPDATE FUNCTION USED IN UPDATE PAGE  ON SUBMIT */
	$scope.updateEnvironmentType = function(field){
		console.log("inside updateEnvironmentType Function calling ");
		var userData = JSON.stringify($scope.field);
		var res = $http.put('/paas-gui/rest/environmentTypeService/updateEnvironmentType/', userData);
		  console.log(userData);
		  res.success(function(data, status, headers, config) {
			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
			  if(data!='failed'){
					console.log("login success");
					/*document.location.href = '/paas-gui/html/acl.html'; also working*/ 
					window.location.href = "environmenttypes.html";
				}else{
					console.log("Login Error Please Enter Proper Details");
					/*document.location.href = '/paas-gui/html/acl_wizard.html'; also working*/
					 window.location.href = "edit-environmenttype.html"; 
				}
			
		  });
		  res.error(function(data, status, headers, config) {
			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
		    alert("failure message: " + JSON.stringify({
		      data : data
		    }));
		  });
	}
/**ENVIRONMENT VALIDATION IN UPDAGTE PAGE */
	 $scope.environmentTypesValidation1 = function(environment) {
		   	
		   	var res = $http.get('/paas-gui/rest/environmentTypeService/checknvironmentType/'+environment);

		   	res.success(function(data, status, headers, config) {
		   		console.log("data"+data);
		   		
		  		  if(data == 'success'){
		  	    	document.getElementById('environmenterr').innerHTML="data exist enter different name";
		  	    	document.getElementById("myenvbtn").disabled = true;
		  		  }
		  		  else{
		  			 document.getElementById('environmenterr').innerHTML="";
		  			document.getElementById("myenvbtn").disabled =false;
		  		  }
		   	});
		   	res.error(function(data, status, headers, config) {
		   		alert("failure message: " + JSON.stringify({
		   			data : data
		   		}));
		   	});
		   };
});/**END OF THE UPDATE CONTROLLER --('environmentUpdateCtrl')--*/


/**THE 3RD CONTROLLER FOR SERVICE TO SHARE THE DATA IN UPDATE PAGE */
myenvironmenttype.service('srvShareData', function($window) {
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
});/**END OF THE SERVICE CONTROLLER--('srvShareData')-- */



