var app = angular.module('application', []);




app.controller('appCtrl', function($scope,$http, srvShareData) {
	  
	  $scope.dataToShare = [];
	  
	  $scope.shareMyData = function (myValue) {

	    $scope.dataToShare = myValue;
	    srvShareData.addData($scope.dataToShare);
	    
	    window.location.href = "applicationWizard.html";
	  };
	  
	  
		 
		
		$scope.field = {};
		
	 
	      /*================Application REGISTRATION===================*/
	    
		
	    
	    
	    /*to store application deatils */
	    $scope.storeApplication = function() {
	    	
		  	  console.log("inside application data value ");

	    	  $scope.message=null;
	  	  var application = JSON.stringify($scope.field);
	  	  var res = $http.post('/paas-gui/rest/applicationService/storeApplication', application);
	  	  res.success(function(data, status, headers, config) {
		  	  console.log("inside application data value "+data);
		  	window.location.href = "applicantmain.html";
	  	  
	  	  });
	  	  res.error(function(data, status, headers, config) {
	  	    alert("failure message : " + JSON.stringify({
	  	      data : data
	  	    }));
	  	  });
	  	 /* console.log("application data value "+$scope.message);
	  	  if($scope.message!=null){
		  	  window.location.href = "applicantmain.html";

	  	  }*/
	  	 
	  	};//store application  
	  	
	  	
	  	
	  	
	  	
	    /*To Check Application name Exist or Not   */
	  	 $scope.checkAppsName = function(applicationName) {
	  		 $scope.sucess=false;
	     	var response = $http.get('/paas-gui/rest/applicationService/checkApplication/'+applicationName);
	     	response.success(function(data){
	     		if(data=='sucess')
	     			$scope.sucess=true;
	     		 
	     	});
	     	response.error(function(data, status, headers, config) {
	                 alert("Error in Fetching Data");
	         });
	     };//end of CheckApplications script
	  	
	  	
	  	   /*To Get Application Details */
	  	 $scope.getApplications = function() {
	     	var response = $http.get('/paas-gui/rest/applicationService/getApplications');
	     	response.success(function(data){
	     		$scope.fields = data;
	     		 
	     	});
	     	response.error(function(data, status, headers, config) {
	                 alert("Error in Fetching Data");
	         });
	     };//end of getApplications script
	     
	     //edit application
	     $scope.editApplication=function(application){
	    	 
	    	 $scope.dataToShare = application;
	 	    srvShareData.addData($scope.dataToShare);
	 	     window.location.href = "editApplication.html"; 
	    	 
	     };//end of edit application
	     
	     
	  	 /*delete of Application based on given Id*/
	     $scope.deleteApplication = function(apps_id) {
	     	var response = $http.get('/paas-gui/rest/applicationService/deleteApplication/'+apps_id);
	     	response.success(function(data){
	     		$scope.getApplications();
	     	});
	     	response.error(function(data, status, headers, config) {
	                 alert("Error in Fetching Data");
	         });
	     	
	     };//end of application script 
	     
	        
	
	     
	});

	app.controller('appDisplayCtrl', function($scope, srvShareData,$http) {
	  
	  $scope.sharedData = srvShareData.getData();
	  
		$scope.field = {};

	  
	   $scope.getAllServiceByAppsId = function(id) {
		
		    (function (global) {
		    	 global.localStorage.setItem("applicationId", id);
		         
		    }(window));
		    
	      
			
	
		   
		   
		   	 var response = $http.get('/paas-gui/rest/applicationService/getAllServiceByAppsId/'+id);
			     	response.success(function(data){
			     		$scope.field = data;
			     		console.log("data given");
			     	});
			     	response.error(function(data, status, headers, config) {
			                 alert("Error in Fetching Data of selectImageRegistry");
			         });	
		    };  
		   
		   
		    //edit application
		     $scope.editService=function(service){
		    	 
		    	 $scope.dataToShare = service;
		 	    srvShareData.addData($scope.dataToShare);
		 	     window.location.href = "editService.html"; 
		    	 
		     };//end of edit application
		     
		    
		    
		   
		 
		    /*=================== delete*====================*/
		   
		    $scope.deleteServiceById = function(ServiceId,appid) {
		     	var response = $http.get('/paas-gui/rest/applicationService/deleteServiceById/'+ServiceId);
		     	response.success(function(data){
			    	 $scope.getAllServiceByAppsId(appid); 

		     	});
		     	response.error(function(data, status, headers, config) {
		                 alert("Error in Fetching Data"+data);
		         });
		     	
		     };
		     
		     
		 
		

	});
	
	//Controller to update Application
	app.controller('appUpdateCtrl', function($scope, srvShareData,$http) {
		  
		  $scope.sharedData = srvShareData.getData();
		  
			$scope.field = $scope.sharedData[0];

		  
		  /*   Edit application script  
		     $scope.updateApplication = function() {
		    var applictaionUpdatedData=	$scope.field;
		     	var response = $http.put('/paas-gui/rest/applicationService/updateApplication/',	applictaionUpdatedData);
	     		console.log("Response:  "+JSON.stringify(response));

		     	response.success(function(data, status, headers, config){
		     		console.log("sucessData:  "+data);
		     	window.location.href = "applicantmain.html";
		     	});
		     	response.error(function(data, status, headers, config) {
		                 alert("Error in Fetching Data");
		         });
		     };//end of to update Application
		     */

		 	$scope.updateApplication = function(){
		 		
		 		  var applictaionUpdatedData=	$scope.field;
		 		var res = $http.put('/paas-gui/rest/applicationService/updateApplication/', applictaionUpdatedData);
		 		  res.success(function(data, status, headers, config) {
		 			  console.log("data : "+data +" status : "+status+" headers : "+headers+"  config: "+config);
		 			  if(data!='failed'){
		 					/*document.location.href = '/paas-gui/html/acl.html'; also working*/ 
		 					window.location.href = "applicantmain.html";
		 				}else{
		 					console.log("Login Error Please Enter Proper Details");
		 					/*document.location.href = '/paas-gui/html/acl_wizard.html'; also working*/
		 					 window.location.href = "editApplication.html"; 
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
	
	//Controller to update Service
	app.controller('serviceUpdateCtrl', function($scope, srvShareData,$http) {
		$scope.service = {env:[]};
		$scope.env = [{envkey:'',envvalue:''}];
		
		 $scope.sharedData = srvShareData.getData();
			$scope.service = $scope.sharedData[0];
			
		
		
	     
		 
			
			
			/* TO ADD DYNAMICA TABLE ROW FOR ENVIRONMENT VARIABLE */
		     $scope.addNewEnvirnament = function() {
		   	    $scope.env.push({envkey:'',envvalue:''});
		     };
		     /*TO REMOVE THE SPECIFIC NEWLY ADDED ENVIRONMENT VARIABLE */
		     $scope.removeEnvirnament = function(index) {
		  		  $scope.env.splice(index,1);
		     };
			 
			 
		  
		     /*Edit Service script  */
		     $scope.updateService = function(servie) {
		    	 
		    	 angular.forEach($scope.env,function(value){
		     		 $scope.service.env.push(value);            
		            });    
		    	 
		         userData = angular.toJson($scope.service);
		         var res = $http.put('/paas-gui/rest/applicationService/updateService', userData);
		         console.log(userData);
		     	  res.success(function(data, status, headers, config) {
		     		 if(data!='failed'){
		 					/*document.location.href = '/paas-gui/html/acl.html'; also working*/ 
		     			 var mydata = [];
			             mydata.push($scope.service.appsId);
			             srvShareData.addData($scope.service.appsId);
		 					window.location.href = "applicationWizard.html";
		 				}else{
		 					console.log(" update sucessfull for service ");
		 					/*document.location.href = '/paas-gui/html/acl_wizard.html'; also working*/
		 					 window.location.href = "editService.html"; 
		 				}
		 			
		     	    
		     	    
		     	  });
		     	  res.error(function(data, status, headers, config) {
//		     	  	    alert("Error in storing Application Summary "+data);
		     	  });
		     };//end of edit script
		     
			     
		     $scope.cancelServiceApp=function(serviceId ){
		    	 $scope.dataToShare =serviceId;
			 	    srvShareData.addData($scope.dataToShare);
 					window.location.href = "applicationWizard.html";

		     };
		     
		     

		     /*======================= To get all Subnet details with current user  =========================*/
		     $scope.selectAllSubnet = function() {
		     
		   	//var response = $http.get('/PAAS-GUI/rest/fetchData/selectVpc');
		   	var response = $http.get('/paas-gui/rest/subnetService/getAllSubnet');
		   	response.success(function(data){
		   	
		   		$scope.subnetObj = data;
		   		console.log("data given>>>"+$scope.subnet);
		   	});
		   	response.error(function(data, status, headers, config) {
		         alert("Error in Fetching subnet Data"+data);
		       });
		   };  
		   /*======================= END OF selectSubnetnew =========================*/
		     
		     $scope.selectSubnetnew = function() {
		    	    var vpcName = $scope.service.vpc_name;
		    	    console.log("vpcName "+vpcName);
		    	    var response = $http.post('/paas-gui/rest/subnetService/getSubnetNameByVpc',vpcName); 
		    	   	response.success(function(data){
		    	   		$scope.subnetObject = data;
		    	   		console.log("data given>>>");
		    	   	});
		    	   	response.error(function(data, status, headers, config) {
		    	        // alert("Error in Fetching subnet Data"+data);
		    	       });
		    	   };  
		    	   /*======================= END OF selectSubnetnew =========================*/
		    	    
		    	   
		    	   /*==================POPULATE DATA TO TABLE===================*/
		    	   
		    		 $scope.selectImageRegistry = function() {
		    	  	var response = $http.get('/paas-gui/rest/imageRegistry/getAllImageRegistry');
		    	  	response.success(function(data){
		    	  		$scope.imageRegObject = data;
		    	  		console.log("data given");
		    	  	});
		    	  	response.error(function(data, status, headers, config) {
		    	              alert("Error in Fetching Data of selectImageRegistry");
		    	      });
		    	  };           

		    	  /*=================== delete*====================*/
		    	  
		    	  
		    	  /*================== TO GET THE CONTAINER TYPE ==================*/
		    	  //NEED TO SHOW IN DROP-DOWN LIST OF CONTAINER_TYPE FIELD IN THE SERVICE.HTML PAGE
		    	  $scope.getAllRelatedContainerTypes = function() {
		    	  	console.log("getAllRelatedContainerTypes ");
		    		   var response = $http.get('/paas-gui/rest/containersService/getContainerTypesByTenantId');
		    		   response.success(function(data){
		    			$scope.containerObject = data;
		    		    	console.log("return data from db: "+$scope.image);
		    		    });
		    		    response.error(function(data, status, headers, config) {
		    		        alert("Error in Fetching Data");
		    		    });
		    		 };
		    		 /*================== END OF getAllRelatedContainerTypes ==================*/
		    	  
		    		 
		    		 /*================== To get the Dockerhub data ==================*/
		    			    //NEED TO SHOW IN DROP-DOWN LIST OF TAGS FIELD IN THE IMAGERESISTRY.HTML PAGE
		    			   	 $scope.selectSummary = function(reponame) {
		    			  	 $scope.reponames;
		    				//JSON.stringify(data);
		    			   		$scope.isImg=true;
		    			     	var response = $http.post('/paas-gui/rest/imageRegistry/getDockerHubRegistryTags',reponame);
		    			     	
		    			     	response.success(function(data){
		    			     		$scope.isImg=false;
		    			     		$scope.reponames = data;
		    			     		console.log("selectRepo >>>> "+$scope.reponames);
		    			     	});
		    			     	response.error(function(data, status, headers, config) {
		    			                 alert("Error in Fetching Application Summary"+data);
		    			         });
		    			     };
		    			     /*================== End of selectSummary ==================*/
		    			     
		    			     
		    			     /* TO ADD DYNAMICA TABLE ROW FOR ENVIRONMENT VARIABLE */
		    			     $scope.addNewEnvirnament = function() {
		    			   	    $scope.env.push({envkey:'',envvalue:''});
		    			     };
		    			     /*TO REMOVE THE SPECIFIC NEWLY ADDED ENVIRONMENT VARIABLE */
		    			     $scope.removeEnvirnament = function(index) {
		    			  		  $scope.env.splice(index,1);
		    			     };
		    		      
		    			     $scope.createApplicationByName = function() {
		    			     /*if(angular.isUndefined($scope.service.applicantionName && $scope.service.applicantionName == '')){
		    			    	 alert("Please Enter valid Application name.");
		    			     }else{*/

		    				   	  var res = $http.post('/paas-gui/rest/applicationService/createApplicationByName', $scope.service.applicantionName);
		    				   	  console.log(userData);
		    				   	  res.success(function(data, status, headers, config) {
		    				   	    $scope.message = data;		   	  
		    				   	    window.location = "html/applicantmain.html";
		    				   	 
		    				   	  });
		    				   	  res.error(function(data, status, headers, config) {
		    				   	    alert("failure message : " + JSON.stringify({
		    				   	      data : data
		    				   	    }));
		    				   	  });
		    				   	  
		    			     /*}*///END OF ELSE
		    			   	};

		});

	app.service('srvShareData', function($window) {
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


