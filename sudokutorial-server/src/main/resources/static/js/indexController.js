var app = angular.module("indexApp", []);

app.controller("indexController", function($scope, $window) {
	$scope.startNewGrid = function(difficulty) {
		$window.location.href = "/sudoku?d=" + difficulty;
	}

	$scope.startTutorial = function() {
		$window.location.href = "/sudoku?t=true";
	}
	
	$scope.continueSavedGrid = function() {
		$window.location.href = "/sudoku?l=true";
	}
});
