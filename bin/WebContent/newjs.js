var app = angular.module('plunker', ['ngRoute']);


app.controller('MainCtrl', function($scope, srvShareData, $location) {
  
  $scope.dataToShare = [];
  
  $scope.shareMyData = function (myValue) {

    $scope.dataToShare = myValue;
    srvShareData.addData($scope.dataToShare);
    
    window.location.href = "secondController.html";
  }
});

app.controller('SecondCtrl', function($scope, srvShareData) {
  
  $scope.sharedData = srvShareData.getData();

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