var myimageregistry = angular.module('myimageregistry', []);

myimageregistry.controller('MainCtrl', function ($scope,$http,srvShareData) {
	
	$scope.field = {};
	
	 
	    
	    $scope.dataToShare = [];
		  
	    /** USED FOR ADD DATA TO SHARE SCOPE TO TRANSFER IN ANOTHER PAGE */
		  $scope.shareMyData = function (myValue) {

		    $scope.dataToShare = myValue;
		    srvShareData.addData($scope.dataToShare);
		    
		    window.location.href = "editImageRegistry12.html";
		  };
    
 
		  /** START OF ADD REGISTRY */
    $scope.regImageRegistry = function() {
    	
    	console.log($scope.field);
    	var userData = JSON.stringify($scope.field);
    	var response = $http.post('/paas-gui/rest/imageRegistry/addImageRegistry/', userData);
     
    	
    	response.success(function(data, status, headers, config) {
			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
				console.log("userData : "+userData);
			  $scope.message = data;
			  window.location.href="/html/imageregistry.html";
			  /* if(data!='failed'){
					console.log("login success");
					document.location.href = '/paas-gui/html/acl.html'; also working 
					window.location.href = "imageregistry.html";
				}else{
					console.log("Login Error Please Enter Proper Details");
					document.location.href = '/paas-gui/html/acl_wizard.html'; also working
					 window.location.href = "editImageRegistry12.html"; 
				}
			*/
		  });
    	 //test
    
   

    /*	response.success(function(data, status) {
    		console.log("data is coming "+data);
    		console.log("coming after succress");
    		alert("coming to success");
    		$scope.message = data;
    	
    		
    		window.location.href='http://localhost:8080/paas-gui/html/imageregistry.html';
    	});*/
    	/*response.error(function(data, status, headers, config) {
    		alert("failure message: " + JSON.stringify({
    			data : data
    		}));
    	});
    	*/
    	
    	response.error(function(data, status, headers, config) {
			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
		    alert("failure message: " + JSON.stringify({
		      data : data
		    }));
		  });
    	
    }; 
    /** END OF ADD REGISTRY */
    
    /**======== POPULATE DATA TO TABLE USING selectImageRegistry===============*/
    
 	 $scope.selectImageRegistry = function() {
    	var response = $http.get('/paas-gui/rest/imageRegistry/getAllImageRegistry');
    	response.success(function(data){
    		$scope.fields = data;
    		console.log("data given");
    	});
    	response.error(function(data, status, headers, config) {
                alert("Error in Fetching Data of selectImageRegistry");
        });
    };           
    /** END selectImageRegistry IMAGE REGISTRY */
    
   /**START DELETE IMAGE REGISTRY*/
    $scope.deleteImageRegistry = function(data,username) {
    	alert(username);
    	alert("coming delete"+username);
    	alert(data+" "+username);
     	var response = $http.get('/paas-gui/rest/imageRegistry/deleteImageRegistry/'+data+'/'+username);
     	response.success(function(data){
     		$scope.selectImageRegistry();
     	});
     	response.error(function(data, status, headers, config) {
                 alert("Error in Fetching Data"+data);
         });
     	
     };
     /**END DELETE IMAGE REGISTRY*/
     
   /*  $scope.updateImageRegistry=function(application){
    	 console.log("inside the application functon ");
    	 console.log("application --> "+application);
    	 $scope.dataToShare = application;
    	 console.log("application --> "+$scope.dataToShare);
 	    srvShareData.addData($scope.dataToShare);
 	     window.location.href = "editImageRegistry.html"; 
    	 
     };*/
    
     /**===============START Image registry validation==============*/
	    $scope.registryValidation = function(name) {
	    	
	    	console.log("coming");
		  	 console.log("<<<<<< acl validation >>>>>>>>>" +name);
		  	  var res = $http.get('/paas-gui/rest/imageRegistry/checkimageRegistry/'+name);
		  	  res.success(function(data, status, headers, config) {
		  		  
		  		if(data == 'success'){
		   	    	document.getElementById('registryerror').innerHTML="data exist enter different name";
		   	    	document.getElementById("registrybtn").disabled = true;
		   	    	
		   		  }
		   		  else{
		   			 document.getElementById('registryerror').innerHTML="";
		   			 
		   			/* if(document.getElementById('location').value !=''){
		   				 
		   			document.getElementById("registrybtn").disabled =false;
		   			 }*/
		   		  }
	 	  		 
		  	  });
		  	  res.error(function(data, status, headers, config) {
		  	    console.log("failure message: " + JSON.stringify({
		  	      data : data
		  	    }));
		  	  });
		  	 
		  	};
		    /**===============END Image registry validation==============*/
    
		  	/**===============START Image registry validation==============*/
		  	$scope.registUsernameValidation = function(userName) {
		    	
		    	
			  	 console.log("<<<<<< acl validation >>>>>>>>>" +name);
			  	  var res = $http.get('/paas-gui/rest/imageRegistry/checkingUserName/'+userName);
			  	  res.success(function(data, status, headers, config) {
			  		  
			  		if(data == 'success'){
			   	    	document.getElementById('userNameerror').innerHTML="data exist enter different name";
			   	    	document.getElementById("registrybtn").disabled = true;
			   	    	
			   		  }
			   		  else{
			   			 document.getElementById('userNameerror').innerHTML="";
			   			 if(document.getElementById('location').value !=''){
			   				 
			   			document.getElementById("registrybtn").disabled =false;
			   			 }
			   		  }
		 	  		 
			  	  });
			  	  res.error(function(data, status, headers, config) {
			  	    alert("failure message: " + JSON.stringify({
			  	      data : data
			  	    }));
			  	  });
			  	 
			  	};
			    /**===============END Image registry validation==============*/
		  	
    
  });   /**================end of controllers===================*/



/**
 * This imageUpdateCtrl is used in Image Update page for update the data.
 */

/**START THE IMAGE UPDATE CTRL */

myimageregistry.controller('imageUpdateCtrl', function($scope, srvShareData,$http) {
	
	$scope.sharedData = srvShareData.getData();
	$scope.field = $scope.sharedData[0];
	
	$scope.updateImageRegistry = function(field){
		console.log("inside updateImageRegistry Function calling ");
		var userData = JSON.stringify($scope.field);
		var res = $http.put('/paas-gui/rest/imageRegistry/updateImageRegistry/', userData);
		  console.log(userData);
		  res.success(function(data, status, headers, config) {
			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
			  if(data!='failed'){
					console.log("login success");
					/*document.location.href = '/paas-gui/html/acl.html'; also working*/ 
					window.location.href = "imageregistry.html";
				}else{
					console.log("Login Error Please Enter Proper Details");
					/*document.location.href = '/paas-gui/html/acl_wizard.html'; also working*/
					 window.location.href = "editImageRegistry12.html"; 
				}
			
		  });
		  res.error(function(data, status, headers, config) {
			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
		    alert("failure message: " + JSON.stringify({
		      data : data
		    }));
		  });
	}
	
	
	 $scope.registryValidation1 = function(name) {
	    	
	    	alert("comingREG2");
		  	 console.log("<<<<<< acl validation >>>>>>>>>" +name);
		  	  var res = $http.get('/paas-gui/rest/imageRegistry/checkimageRegistry/'+name);
		  	  res.success(function(data, status, headers, config) {
		  		  
		  		if(data == 'success'){
		   	    	document.getElementById('registryerror').innerHTML="data exist enter different name";
		   	    	document.getElementById("registrybtn").disabled = true;
		   	    	
		   		  }
		   		  else{
		   			 document.getElementById('registryerror').innerHTML="";
		   			 
		   			/* if(document.getElementById('location').value !=''){
		   				 
		   			document.getElementById("registrybtn").disabled =false;
		   			 }*/
		   		  }
	 	  		 
		  	  });
		  	  res.error(function(data, status, headers, config) {
		  	    console.log("failure message: " + JSON.stringify({
		  	      data : data
		  	    }));
		  	  });
		  	 
		  	};
		  	
		  	$scope.registUsernameValidation1 = function(userName) {
		    	
		    	
			  	 console.log("<<<<<< acl validation >>>>>>>>>" +name);
			  	  var res = $http.get('/paas-gui/rest/imageRegistry/checkingUserName/'+userName);
			  	  res.success(function(data, status, headers, config) {
			  		  
			  		if(data == 'success'){
			   	    	document.getElementById('userNameerror').innerHTML="data exist enter different name";
			   	    	document.getElementById("registrybtn").disabled = true;
			   	    	
			   		  }
			   		  else{
			   			 document.getElementById('userNameerror').innerHTML="";
			   			 if(document.getElementById('location').value !=''){
			   				 
			   			document.getElementById("registrybtn").disabled =false;
			   			 }
			   		  }
		 	  		 
			  	  });
			  	  res.error(function(data, status, headers, config) {
			  	    alert("failure message: " + JSON.stringify({
			  	      data : data
			  	    }));
			  	  });
			  	 
			  	};
});  /**END THE IMAGE UPDATE CTRL */

/***
 * The service Controller for data can be shared in different page ,
 * The srvShareData should be declared for get and set data,
 */


/**START THE IMAGE SERVICE SHARED CTRL */
myimageregistry.service('srvShareData', function($window) {
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
});
/**END THE IMAGE SERVICE SHARED CTRL */

