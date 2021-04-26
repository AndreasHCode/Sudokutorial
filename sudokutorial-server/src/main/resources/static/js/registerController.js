var app = angular.module("registerApp", []);

app.factory("passwordSafety", [function() {
	return {
		evaluatePassword: function() {
			if (!arguments[0]) {
				return 0;
			}
			let password = arguments[0];
			let score = 0;
			
			if (password.length == 0) {
				return 0;
			}
			if (password.length < 5) {
				return 1;
			}
			if (password.length > 7) {
				score += 2;
			}
			if (/[a-z]/.test(password)) {
				score += 2;
			}
			if (/[A-Z]/.test(password)) {
				score += 2;
			}
			if (/\d/.test(password)) {
				score += 1;
			}
			if (/[-!$%^§&*()_+|~=`{}[:;<>?,.@#\]]/.test(password)) {
				score += 1;
			}
			
			return score;
		}
	}
}]);

app.controller("registerController", ["$scope", "$http", "$location", "passwordSafety", function($scope, $http, $location, passwordSafety) {
	let score = 0;
	
	$scope.registerUser = function() {
		let validMail = this.registerForm.email.$valid && this.registerForm.email.$dirty
		if (!validMail || !$scope.valid || !$scope.validRepeat) {
			return;
		}
		
		let data = {};
		data.firstname = $scope.firstname;
		data.lastname = $scope.lastname;
		data.email = $scope.email;
		data.password = $scope.inputPassword;
	    let token = $('input#csrf-token').attr("value");
	    
		$http.put("/registerplayer", 
				data, 
				{ headers: {
					"X-CSRF-TOKEN": token
				} }
		).then(function(successResponse) {
			window.location.href = "/login";
		}, function(failureResponse) {
			window.location.href = "/register";
		});
	}
	
	$scope.checkValidity = function() {
		score = passwordSafety.evaluatePassword($scope.inputPassword);
		
		if (score > 4) {
			$scope.valid = true;
		} else {
			$scope.valid = false;
		}
		
		$scope.checkRepeat();
		$scope.adjustMeter();
	}
	
	$scope.checkRepeat = function() {
		let password = $scope.inputPassword;
		let repeatPassword = $scope.confirmPassword;
		
		if (password === repeatPassword) {
			$scope.validRepeat = true;
		} else {
			$scope.validRepeat = false;
		}
	}
	
	$scope.adjustMeter = function() {
		if (score == 0) {
			$scope.meterValue = "empty";
		} else if (score < 5) {
			$scope.meterValue = "red";
		} else if (score < 7) {
			$scope.meterValue = "orange";
		} else if (score == 7) {
			$scope.meterValue = "yellow";
		} else if (score == 8) {
			$scope.meterValue = "green";
		}
	}
}]);
