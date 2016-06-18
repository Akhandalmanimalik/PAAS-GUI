function loadpage(text){
	  
	if (text === 'getStartedApplication'){
		$("#content").attr("src", "html/getstarted_application.html");
	};
	
	if (text === 'dashboardsummary'){
		$("#content").attr("src", "html/dashboardsummary.html");
	};
	
	/* Application INSIDE Applications Tab */
	if (text === 'applicationsummary'){
		$("#content").attr("src", "html/applicantmain.html");
	};
	
	
	if (text === 'dashboardresources'){
		 
		(function() {
		    // Load the script
		    var script = document.createElement("SCRIPT");
		    script.src = 'https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js';
		    script.type = 'text/javascript';
		    document.getElementsByTagName("head")[0].appendChild(script);

		})();
	 
		$.ajax({
			url: "/paas-gui/rest/registerAndLoginService/test" 
			})
	    .success(function(data) { alert("success");
	     
	    
	    (function (global) {
	    	 global.localStorage.setItem("mySharedData", data);
	         
	    }(window));
	    
        url = 'http://localhost:8080/paas-gui/html/dashboardresources.html';
       // document.location.href = url;
        $("#content").attr("src", url);
	    })
	    .error(function() { alert("error"); })
	    .complete(function() { alert("complete"); });
		
	};
	

	if (text === 'activity'){
		$("#content").attr("src", "html/activity.html");
	};
	
	if (text === 'step1'){
			$("#content").attr("src", "step-1.html");
	};
	
	/*this file should remove it is for testig purpose*/
	/*if (text === 'applicationsummary'){
		$("#content").attr("src", "applicationsummary.html");
	};
	*/
	
	/*Get Started INSIDE Applications Tab*/
	if (text === 'applicationGetStarted'){
		$("#content").attr("src", "html/applicationWizardGetStarted.html");
	};
	
	if (text === 'applicationwebhooks'){
		$("#content").attr("src", "html/applicationwebhooks.html");
	};
	if (text === 'environments'){
		$("#content").attr("src", "html/container.html");
	};
	
	if (text === 'vpcinterface'){  
		$("#content").attr("src", "html/vpc-interface.html");
	};
	
	if (text === 'networkWizard'){  
		$("#content").attr("src", "html/netWorkWizard.html");
	};
	
	if (text === 'subnet'){
		$("#content").attr("src", "html/subnet-interface.html");
	};
	if (text === 'vpn'){
		$("#content").attr("src", "html/vpn.html");
	};
	if (text === 'acl'){
		
		$("#content").attr("src", "html/acl.html");
	};
	if (text === 'firewall'){
		$("#content").attr("src", "html/firewall.html");
	};
	if (text === 'certificates'){
		$("#content").attr("src", "html/certificates.html");
		
	};
	/*policies*/
	if (text === 'scalingandrecovery'){
		$("#content").attr("src", "html/scalingandrecovery.html");
	};
	if (text === 'hostscaling'){
		$("#content").attr("src", "html/hostscaling.html");
	};
	if (text === 'serviceaffinities'){
		$("#content").attr("src", "html/serviceaffinities.html");
	};
	if (text === 'resourceSelection'){
		$("#content").attr("src", "html/resourceselection.html");
	};
	if (text === 'environmentTypes'){
		$("#content").attr("src", "html/environmenttypes.html");
	};
	if (text === 'containertype'){
		$("#content").attr("src", "html/containertype-interface.html");
	};
	if (text === 'cloudprovider'){
		$("#content").attr("src", "html/cloudprovider.html");
	};
	if (text === 'storage'){
		$("#content").attr("src", "html/storage.html");
	};
	
	if (text === 'imageregistrywizard'){
		$("#content").attr("src", "html/imageregistry.html");
	};
	
	if (text === 'imageregistry'){
		$("#content").attr("src", "html/imageregistry.html");
	};
	if (text === 'logserver'){
		$("#content").attr("src", "html/logserver.html");
	};
	if (text === 'errordiagnosis'){
		$("#content").attr("src", "html/errordiagnosis.html");
	};
	if (text === 'allhost'){
		$("#content").attr("src", "html/allhost.html");
	};
	
}; 
