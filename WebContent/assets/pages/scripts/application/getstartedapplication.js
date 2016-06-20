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
     	var response = $http.get('/paas-gui/rest/networkservice/getAllVPC');
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
    
  });  /*================end of controller======================*/





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