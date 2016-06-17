var mycloudprovider = angular.module('getstartedApp', []);

mycloudprovider.controller('MainCtrl', function ($scope,$http) {
	
	$scope.field = {};
	$scope.applications = {};
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
   
     
    var vpcName = $scope.applications.vpc.vpc_name;
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
	   	 $scope.selectSummary = function(reponame1) {
	  	 $scope.reponames;
	  	 
	  	
	  	 //alert("dsfsdfdsf "+$applications.imageRegi.imageRepository);
		//JSON.stringify(data);
	   		//$scope.isImg=true;
	     	var response = $http.post('/paas-gui/rest/imageRegistry/getDockerHubRegistryTags',reponame1);
	     	
	     	response.success(function(data){
	     		//$scope.isImg=false;
	     		$scope.reponames2 = data;
	     		console.log("selectRepo >>>> "+$scope.reponames2);
	     	});
	     	response.error(function(data, status, headers, config) {
	                 alert("Error in Fetching Application Summary"+data);
	         });
	     };
	     /*================== End of selectSummary ==================*/
    
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