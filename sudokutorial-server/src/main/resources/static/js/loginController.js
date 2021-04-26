var app = angular.module("loginApp", []);

app.controller("loginController", function($scope, $http) {
	$scope.loginUser = function() {
		let validMail = this.loginForm.email.$valid && this.loginForm.email.$dirty
		if (!validMail) {
			return;
		}
		
		let data = {};
		data.username = $scope.email;
		data.password = $scope.inputPassword;
	    let token = $('input#csrf-token').attr("value");
	    
		$http.post(
			"/login", 
			{}, 
			{ params: data,
			  headers: {
				  "X-CSRF-TOKEN": token
			  }
			}
		).then(function() {
			window.location.href = "/login";
		});
	}
});