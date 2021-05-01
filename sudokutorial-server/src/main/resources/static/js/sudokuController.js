var app = angular.module("sudokuApp", []);

app.controller("sudokuController", function($scope, $http, $timeout, $location, $window) {
	let token = $('input#csrf-token').attr("value");
	$scope.sudokuGrid = {};
	$scope.arrayGrid = {};
	$scope.selectedCell = {};
	$scope.entryPromise;
	$scope.ruleStepsMap = {};
	$scope.solve = true;
	$scope.difficutly;
	let defaultDifficulty = 3;
	
	$scope.helpEnabled = false;
	$scope.stepColorCells = [];
	
	$scope.rules = ["removeOptions", "findUnique", "findUniqueRowColumn", "entryCombination", 
		"findXWing", "findSwordfish", "findJellyfish", "findRemotePairs"];
	
	$scope.rulesText = ["Remove Options", "Find Unique", "Find Unique in Row or Column", "Find Entry Combinations", 
		"Find X-Wing", "Find Swordfish", "Find Jellyfish", "Find Remote Pairs"];
	
	$scope.ruleDifficulty = {
		"removeOptions": 1,
		"findUnique": 2,
		"findUniqueRowColumn": 3,
		"entryCombination": 4,
		"findXWing": 5,
		"findSwordfish": 5,
		"findJellyfish": 5,
		"findRemotePairs": 5,
	}
	
	$scope.getUrlParameter = function(urlParam) {
		let urlString = $window.location.search;
		let paramIndex = urlString.indexOf(urlParam + "=");
		if (paramIndex == -1) {
			return -1;
		}
		
		urlString = urlString.substring(paramIndex, urlString.length);
		let paramLength = urlString.indexOf("=");
		
		let endIndex = urlString.indexOf("&");
		if (endIndex == -1) {
			endIndex = urlString.length; 
		}
		
		urlString = urlString.substring(paramLength + 1, endIndex);
		return urlString;
	}
	
	$scope.showTooltip = function(rule, $event) {
		let ruleDescription = $scope.rulesText[$scope.rules.indexOf(rule)];
		$("#tooltip").text(ruleDescription);
		$("#tooltip").css("left", $event.pageX - ($("#tooltip").width() / 1.75));
		$("#tooltip").css("top", $event.pageY - $("#tooltip").height());
		$("#tooltip").show();
	}
	
	$scope.hideTooltip = function() {
		$("#tooltip").hide();
	}
	
	$scope.difficulty = parseInt($scope.getUrlParameter("d"));
	if (isNaN($scope.difficulty) || $scope.difficulty < 1) {
		$scope.difficulty = defaultDifficulty;
	}
	
	if ($scope.getUrlParameter("t") == "true") {
		$scope.helpEnabled = true;
		$scope.difficulty = 5;
	};
	
	$scope.getGrid = function() {
		let url = "/grid" + "?d=" + $scope.difficulty;
		
		$http.get(url).then(function(response) {
			$scope.sudokuGrid = response.data;
			$scope.arrayGrid = response.data.arrayGrid;
			$scope.getSolutionStepsMap();
		});
	}
	
	if ($scope.getUrlParameter("l") == "true") {
		$http.get("/loadGrid").then(function(response) {
			$scope.sudokuGrid = response.data;
			$scope.arrayGrid = response.data.arrayGrid;
			$scope.getSolutionStepsMap();
		});
	} else {
		$scope.getGrid();
	}
	
	$scope.showRuleButton = function(rule) {
		let optionsRemoved = $scope.ruleStepsMap["removeOptions"] && $scope.ruleStepsMap["removeOptions"].length == 0;
		return (rule == "removeOptions" || ($scope.shownDifficulty(rule) && optionsRemoved));
	 }
	
	$scope.shownDifficulty = function(rule) {
		if ($scope.ruleDifficulty[rule] <= $scope.difficulty) {
			return true;
		} else {
			return false;
		}
	}
	
	$scope.getSolutionStepsMap = function() {
		$http.post("/solutionStepsMap", $scope.sudokuGrid, {
			headers : {
				"X-CSRF-TOKEN" : token
			}
		}).then(function(response) {
			$scope.ruleStepsMap = {};

			for (let i = 0; i < $scope.rules.length; i++) {
				let rule = $scope.rules[i];
				$scope.ruleStepsMap[rule] = (response.data[rule]);
			}
		});
	}
	
	$scope.makeEntries = function() {
		$http.post("/makeEntries", $scope.sudokuGrid, {
			headers : {
				"X-CSRF-TOKEN" : token
			}
		}).then(function(response) {
			$scope.sudokuGrid = response.data;
			$scope.arrayGrid = response.data.arrayGrid;
			$scope.getSolutionStepsMap();
		});
	}
	
	$scope.applyRule = function(rule, $event) {
		let solve = $scope.solve;
		if ($event.button == 2) {
			solve = !solve;
		}
		
		if (solve) {
			$scope.solveRule(rule);
		} else {
			$scope.showStep(rule);
		}
	}
	
	$scope.solveRule = function(rule) {
		$http.post("/applyRule/"+ rule, $scope.sudokuGrid, {
			headers : {
				"X-CSRF-TOKEN" : token
			}
		}).then(function(response) {
			$scope.sudokuGrid = response.data;
			$scope.arrayGrid = response.data.arrayGrid;
			$scope.getSolutionStepsMap();
		});
	}
	
	$scope.showStep = function(rule) {
		$scope.removeStepColorings();
		let stepCell = $scope.ruleStepsMap[rule][0];
		let reasonCell;
		console.log(reasonCell);
		
		if (stepCell == null) {
			return;
		}
		
		stepCell.reason.forEach(cell => {
			reasonCell = $("#cell-" + cell.column + "-" + cell.row);
			reasonCell.addClass("reason-cell");
			$scope.stepColorCells.push(reasonCell);
		});
		
		reasonCell = $("#cell-entry-" + stepCell.cell.column + "-" + stepCell.cell.row + "-" + stepCell.entry);
		reasonCell.addClass("entry-removal");
		$scope.stepColorCells.push(reasonCell);
		$("#solutionStepDesc").text(reasonCell);
	}
	
	$scope.removeStepColorings = function() {
		$scope.stepColorCells.forEach(domCell => {
			domCell.removeClass("reason-cell");
			domCell.removeClass("entry-removal");
		})
	}
	
	$scope.getSolution = function() {
		$http.post("/solveGrid", $scope.sudokuGrid, {
			headers : {
				"X-CSRF-TOKEN" : token
			}
		}).then(function(response) {
			$scope.sudokuGrid = response.data;
			$scope.arrayGrid = response.data.arrayGrid;
			if ($scope.helpEnabled) {
				$scope.removeStepColorings();
				$scope.getSolutionStepsMap();
			}
		});
	}
	
	$scope.saveGrid = function() {
		$http.post("/saveGrid", $scope.sudokuGrid, {
			headers : {
				"X-CSRF-TOKEN" : token
			}
		});
	}

	$scope.selectCell = function($event) {
		$("#" + $scope.selectedCell.id).removeClass("selected");
		$scope.selectedCell = $event.currentTarget;
		$scope.selectedCell.row = $scope.selectedCell.id.charAt(7);
		$scope.selectedCell.column = $scope.selectedCell.id.charAt(5);
		let cell = $scope.arrayGrid[$scope.selectedCell.row][$scope.selectedCell.column];
		if (cell.startingCell) {
			return;
		}
		
		$("#" + $scope.selectedCell.id).addClass("selected");
		
		for (let i = 0; i < 9; i++) {
			if (cell.entries.includes(i + 1)) {
				$("#option-" + (i + 1)).removeClass("invalid-option");
				$("#option-" + (i + 1)).addClass("valid-option");
			} else {
				$("#option-" + (i + 1)).removeClass("valid-option");
				$("#option-" + (i + 1)).addClass("invalid-option");
			}
		}
		$("#fader").addClass("darkened");
		$("#options-menu").css("left", $event.pageX - ($("#options-menu").width() / 2));
		$("#options-menu").css("top", $event.pageY);
		$("#options-menu").show();
	}
	
	$scope.removeFade = function() {
		$("#fader").removeClass("darkened");
		$("#options-menu").hide();
		$("#" + $scope.selectedCell.id).removeClass("selected");
	}
	
	$scope.optionMouseDown = function($event) {
		$scope.entryPromise = $timeout(function() {
			$scope.makeEntry($event);
		}, 200);
	}
	
	$scope.optionMouseUp = function($event) {
		$timeout.cancel($scope.entryPromise);
	}
	
	$scope.selectOption = function($event) {
		if (!$scope.selectedCell.row) {
			return;
		}
		
		let selectedOption = $event.target.innerText;
		let cell = $scope.arrayGrid[$scope.selectedCell.row][$scope.selectedCell.column];

		if (cell.entries.indexOf(parseInt(selectedOption)) == -1) {
			cell.entries.push(parseInt(selectedOption));
			$("#option-" + parseInt(selectedOption)).removeClass("invalid-option");
			$("#option-" + parseInt(selectedOption)).addClass("valid-option");
		} else {
			cell.entries.splice(cell.entries.indexOf(parseInt(selectedOption)), 1);
			$("#option-" + parseInt(selectedOption)).removeClass("valid-option");
			$("#option-" + parseInt(selectedOption)).addClass("invalid-option");
		}

		if ($scope.helpEnabled) {
			$scope.removeStepColorings();
			$scope.getSolutionStepsMap();
		}
	}
	
	$scope.makeEntry = function($event) {
		let cell = $scope.arrayGrid[$scope.selectedCell.row][$scope.selectedCell.column];
		let selectedOption = parseInt($event.target.innerText);
		
		if (cell.number == selectedOption) {
			for (let i = 1; i <= 9; i++) {
				cell.entries.push(i);
			}
			cell.number = 0;
		} else {
			cell.entries = [];
			cell.number = parseInt(selectedOption);
		}
		
		$scope.removeFade();
		if ($scope.helpEnabled) {
			$scope.removeStepColorings();
			$scope.getSolutionStepsMap();
		}
	}
});

app.directive('ngRightClick', function($parse) {
    return function(scope, element, attrs) {
        var fn = $parse(attrs.ngRightClick);
        element.bind('contextmenu', function(event) {
            scope.$apply(function() {
                event.preventDefault();
                fn(scope, {$event:event});
            });	
        });
    };
});