
      google.charts.load("current", {packages:["corechart"]});
      google.charts.setOnLoadCallback(drawChart);
      google.charts.setOnLoadCallback(drawChartForApplications);
      google.charts.setOnLoadCallback(drawChartForServices);

      
     
      function drawChart() {
    	
    	  try{
    	  var environments=document.getElementById("env").value;
    	    try{
    	    	environments=   JSON.parse(environments);
    	    	  }catch (e) {
    	    		  environments=[["Environemnts","Total Counts"]];
    				alert(e);
    			}
    	 
     
        var data = google.visualization.arrayToDataTable(environments);

        var options = {
          title: 'Enivronments',
          is3D: true,
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
        chart.draw(data, options);
    	  }catch (e) {
			alert(e)
		}
      }
      function drawChartForApplications() {
    	  
    	  var applications=document.getElementById("app").value;
  	    try{
  	    	applications=   JSON.parse(applications);
  	    	  }catch (e) {
  	    		applications=[["Applications","Application Counts"]];
  				
  			}
          var data = google.visualization.arrayToDataTable(applications);

          var options = {
            title: 'Applications',
          
            is3D: true,
          };

          var chart = new google.visualization.PieChart(document.getElementById('piechart_4d'));
          chart.draw(data, options);
        }
      function drawChartForServices() {
    	  
    	  var services=document.getElementById("services").value;
    	    try{
    	    	services=   JSON.parse(services);
    	    	  }catch (e) {
    	    		  services=[["Services","Services Counts"]];
    				
    			}
          var data = google.visualization.arrayToDataTable(services)
          var options = {
            title: 'Services',
          
            is3D: true,
          };

          var chart = new google.visualization.PieChart(document.getElementById('piechart_5d'));
          chart.draw(data, options);
        }
    