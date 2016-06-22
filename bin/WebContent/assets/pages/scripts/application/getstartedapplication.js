var mycloudprovider = angular.module('getstartedApp', []);

mycloudprovider.controller('MainCtrl', function ($scope,$http) {
	
	$scope.field = {};
	
	$scope.service = {env:[]};
	$scope.env = [{envkey:'',envvalue:''}];
	
    $scope.showModal = false;
    $scope.toggleModal = function(){
        $scope.showModal = !$scope.showModal;
    };
    
    $scope.selectVpc = function() {
     	var response = $http.get('/paas-gui/rest/vpcService/getAllVPC');
     	response.success(function(data){
     		$scope.vpcObject = data;
     	});
     	response.error(function(data, status, headers, config) {
                 alert("Error in Fetching Data");
         });
     };
     
     /*======================= To get all Subnet details with current user  =========================*/
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
	   var response = $http.get('/paas-gui/rest/policiesService/getContainerTypesByTenantId');
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
		   	
		     
		  // function to process the form
		     $scope.processForm = function() {
		          
		    	angular.forEach($scope.env,function(value){
		     		 $scope.service.env.push(value);            
		            })       
		          userData = angular.toJson($scope.service);
		          var res = $http.post('/paas-gui/rest/applicationService/addService', userData);
		          console.log(userData);
		      	  res.success(function(data, status, headers, config) {
		      	    $scope.message = data;
		      	    
		      	    
		      	  });
		      	  res.error(function(data, status, headers, config) {
//		      	  	    alert("Error in storing Application Summary "+data);
		      	  });
		            
		      };
		      
		      //check vpcname exist or not
		      $scope.vpcValidation = function(vpc) {

		    	  var res = $http.get('/paas-gui/rest/networkservice/checkVPC/'+vpc);
		    	  res.success(function(data, status, headers, config) {


		    	  if(data == 'success'){
		    	  document.getElementById('errfn').innerHTML="data exist enter different name";

		    	  document.getElementById("myvpcbtn").disabled = true;

		    	  }
		    	  else{
		    	  document.getElementById('errfn').innerHTML="";
		    	  /*
		    	  if(document.getElementById('aclerr').innerHTML =="")
		    	  document.getElementById("myvpcbtn").disabled = true;

		    	  else
		    	  document.getElementById("myvpcbtn").disabled =false;

		    	  if(document.getElementById('errfn').innerHtml && document.getElementById('aclerr').innerHtml =="")*/

		    	  document.getElementById("myvpcbtn").disabled =false;

		    	  }

		    	  });
		    	  res.error(function(data, status, headers, config) {

		    	  alert("failure message: " + JSON.stringify({
		    	  data : data
		    	  }));
		    	  });
		    	  };
		    	  /*===============END of VPC validation==============*/
    
		    	  
		    	  /*=============== Add SUBNET validation==============*/

		    	  $scope.subnetValidation = function(subnet) {
		    	  console.log("subnet "+subnet);
		    	  var res = $http.get('/paas-gui/rest/networkservice/checkSubnet/'+subnet);
		    	  res.success(function(data, status, headers, config) {

		    	  if(data == 'success'){
		    	  document.getElementById('suberr').innerHTML="data exist enter different name";
		    	  document.getElementById("mysubnetbtn").disabled = true;

		    	  }
		    	  else{
		    	  document.getElementById('suberr').innerHTML="";

		    	  if(document.getElementById('vpc').value && document.getElementById('environment_id').value !='?')
		    	  document.getElementById("mysubnetbtn").disabled =false;
		    	  }
 		    	  });
		    	  res.error(function(data, status, headers, config) {
		    	  alert("failure message: " + JSON.stringify({
		    	  data : data
		    	  }));
		    	  });

		    	  };
		    	  /*===============END Add SUBNET validation==============*/
 	  
		    	  
		    	  /*======================== TO GET ALL ENVIRONMENT NAME WITH THE CURRENT USER ====================*/

		    	  $scope.selectEnvironment_name = function() {
		    	  var response = $http.get('/paas-gui/rest/environmentTypeService/getAllEnvironmentType');

		    	  //var response = $http.get('/PAAS-GUI/rest/fetchData/selectSubnet');


		    	  response.success(function(data){

		    	  $scope.environment = data;
		    	  console.log(">>>>>>> >>> "+$scope.environment);
		    	  });
		    	  response.error(function(data, status, headers, config) {
		    	  alert("Error in Fetching environment Data"+data);
		    	  });
		    	  };
		    	  /*======================= END OF selectEnvironment_name =========================*/
		    	  
		    	  
		    	  /*===================== TO GET ALL ACL NAME WITH CURRENT USER ============================*/
		    	  $scope.selectACL_name = function() {
		    	  var response = $http.get('/paas-gui/rest/networkservice/getAllACL');

		    	  //var response = $http.get('/PAAS-GUI/rest/fetchData/selectSubnet');


		    	  response.success(function(data){

		    	  $scope.acl = data;
		    	  console.log(">>>>>>> >>> "+$scope.acl);
		    	  });
		    	  response.error(function(data, status, headers, config) {
		    	  alert("Error in Fetching environment Data"+data);
		    	  });
		    	  };
		    	  /*======================= END OF selectACL_name =========================*/	  
		    	  
		    	  /*===================== To add subnet details into db =========================*/
		    	    $scope.regSubnet = function() {
		    	    	console.log($scope.field);
		    	    	//var userData = JSON.stringify($scope.field);
		    	    	var userData = angular.toJson($scope.field);
		    	    	var res = $http.post('/paas-gui/rest/networkservice/addSubnet', userData);
		    	    	console.log(userData);
		    	    	
		    	    	res.success(function(data, status, headers, config) {
		    	    		 $scope.selectSubnetnew();
		    	    		window.location.href="/paas-gui/html/subnet-interface.html";
		    	    		
		    	    	});
		    	    	res.error(function(data, status, headers, config) {
		    	    		alert("Error in registering subnet  " +data );
		    	    	});
		    	    }; 
		    	    /*===================== END OF regSubnet =========================*/	  
		    	  


		    	    /* ===============================Image validation=====================*/
		    	    					  	
		    	    					    $scope.registryValidation = function(name) {
		    	    					    	
		    	    					    	
		    	    						  	 console.log("<<<<<< acl validation >>>>>>>>>" +name);
		    	    						  	  var res = $http.get('/paas-gui/rest/imageRegistry/checkimageRegistry/'+name);
		    	    						  	  res.success(function(data, status, headers, config) {
		    	    						  		  
		    	    						  		if(data == 'success'){
		    	    						   	    	document.getElementById('registryerror').innerHTML="data exist enter different name";
		    	    						   	    	document.getElementById("registrybtn").disabled = true;
		    	    						   	    	
		    	    						   		  }
		    	    						   		  else{
		    	    						   			 document.getElementById('registryerror').innerHTML="";
		    	    						   			 
		    	    						   			 if(document.getElementById('location').value && document.getElementById('userNameerror').value !=''){
		    	    						   				
		    	    						   				 
		    	    						   			document.getElementById("registrybtn").disabled =false;
		    	    						   			 }
		    	    						   		  }
		    	    					 	  		 
		    	    						  	  });
		    	    						  	  res.error(function(data, status, headers, config) {
		    	    						  	    console.log("failure message: " + JSON.stringify({
		    	    						  	      data : data
		    	    						  	    }));
		    	    						  	  });
		    	    						  	 
		    	    						  	};
		    	    						    /*===============END Image registry validation==============*/
		    	  
		    	    /* ================User name validation====================*/
		    	    						  	
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
		    	    							   			 if(document.getElementById('registryerror').value ==''){
		    	    							   				 
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
		    	    							    /*===============END of user validation==============*/
		    	    							  	
		    	    							  	
		    	    							  	/*============ ImageRegistry REG=============*/

		    	    							  	$scope.regImageRegistry = function() {

		    	    							  		console.log($scope.field);
		    	    							  		var userData = JSON.stringify($scope.field);
		    	    							  		var response = $http.post('/paas-gui/rest/imageRegistry/addImageRegistry', userData);
		    	    							  		
		    	    							  		JSON.stringify(response);
		    	    							  		
		    	    							  		 

		    	    							  		console.log(userData);

		    	    							  		response.success(function(data, status) {
		    	    							  			alert("coming to success");
		    	    							  			$scope.message = data;
		    	    							  		
		    	    							  			window.location.href="/html/imageregistry.html";
		    	    							  			/*window.location.href='http://localhost:8080/paas-gui/html/imageregistry.html';*/
		    	    							  		});
		    	    							  		response.error(function(data, status, headers, config) {
		    	    							  			alert("failure message: " + JSON.stringify({
		    	    							  				data : data
		    	    							  			}));
		    	    							  		});
		    	    							  	}; 
		   
		    	    							  	
		    	    							  	
		    	    							  	$scope.applicationValidation = function(userName) {

		    	    							  		var res = $http.get('/paas-gui/rest/applicationService/checkingApplication/'+userName);



		    	    							  		res.success(function(data, status, headers, config) {


		    	    							  		if(data == 'success'){
		    	    							  		document.getElementById('apperror').innerHTML="data exist enter different name";
		    	    							  		document.getElementById("appbtn").disabled = true;

		    	    							  		}
		    	    							  		else{
		    	    							  		document.getElementById('apperror').innerHTML="";


		    	    							  		document.getElementById("appbtn").disabled =false;

		    	    							  		}

		    	    							  		});
		    	    							  		res.error(function(data, status, headers, config) {
		    	    							  		alert("failure message: " + JSON.stringify({
		    	    							  		data : data
		    	    							  		}));
		    	    							  		});

		    	    							  		};							  	
		    	    							  	
		    	    
  });  /*================end of controller======================*/





/*==================POPULATE DATA TO TABLE===================*/


/*=============directive starts============*/

mycloudprovider.directive('modal', function () {
	return {
		template : '<div class="modal fade">'
				+ '<div class="modal-dialog">'
				+ '<div class="modal-content">'
				+ '<div class="modal-header">'
				+ '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
				+ '<h4 class="modal-title">{{ title }}</h4>'
				+ '</div>'
				+ '<div class="modal-body" ng-transclude></div>'
				+ '</div>' + '</div>' + '</div>',
		restrict : 'E',
		transclude : true,
		replace : true,
		scope : true,
		link : function postLink(scope, element, attrs) {
			scope.title = attrs.title;

			scope.$watch(attrs.visible, function(value) {
				if (value == true)
					$(element).modal('show');
				else
					$(element).modal('hide');
			});

			$(element).on('shown.bs.modal', function() {
				scope.$apply(function() {
					scope.$parent[attrs.visible] = true;
				});
			});

			$(element).on('hidden.bs.modal', function() {
				scope.$apply(function() {
					scope.$parent[attrs.visible] = false;
				});
			});
			
			$('.continue').click(function() {
				$('.nav-tabs > .active').next('li').find('a').trigger('click');
			});
			$('.back').click(function() {
				$('.nav-tabs > .active').prev('li').find('a').trigger('click');
			});
			$('.cancel').click(function() {
				$('.nav-tabs > .active').cancel('li').find('a').trigger('click');
			});
		}
	};
});