'use strict';


/**
 * SchedulerJob
 */
var SchedulerJobStatsController = function($http, $scope, $resource,
		$routeParams) {
	var spoolerId = $routeParams.spoolerId;
	var jobName = $routeParams.job;
	$http({
		url : '/jobStats',
		method : 'GET',
		params : {
			spoolerId : spoolerId,
			jobName : jobName
		}
	}).success(function(data) {
		$scope.job = data;
	});
};

var ListSchedulerJobController = function($timeout, $scope, $resource,
		ngTableParams) {
	var Api = $resource('/jobs');
	$scope.tableParams = new ngTableParams({
		page : 1,
		count : 10
	// sorting: { name: 'asc' }
	}, {
		total : 0,
		getData : function($defer, params) {
			Api.get(params.url(), function(data) {
				$timeout(function() {
					params.total(data.totalElements);
					$scope.totalPages = data.totalPages;
					$defer.resolve(data.result);
				}, 500);

			});
		}
	});
};

/**
 * SchedulerHistory
 */
var SchedulerHistoryController = function($scope, $http, $routeParams) {
	$scope.back = function() {
		window.history.back();
	};
	var schedulerHistoryId = $routeParams.schedulerHistoryId;
	$http({
		url : '/schedulerHistory',
		method : 'GET',
		params : {
			id : schedulerHistoryId
		}
	}).success(function(data) {
		$scope.job = data;
	});

};

var ListSchedulerHistoryController = function($timeout, $scope, $http,
		$resource, ngTableParams, SessionService) {
	var ApiSchedulerHistories = $resource('/schedulerHistories');
	// Load filters in select option
	$http({
		url : '/filters',
		method : 'GET'
	}).success(function(data) {
		$scope.filters = data;
	});

	$scope.tableParams = new ngTableParams({
		page : 1,
		count : 10
	// sorting: { jobName: 'asc' }
	}, {
		total : 0,
		getData : function($defer, params) {
			ApiSchedulerHistories.get(params.url(), function(data) {
				$timeout(function() {
					// set total for recalc pagination
					params.total(data.totalElements);
					$scope.totalPages = data.totalPages;
					$defer.resolve(data.result);
					SessionService.set("jobs", data.result);
				}, 500);

			});
		}
	});

	// Watch option
	$scope.$watch('filterSelect', function(newValue, oldValue) {
		if (newValue != oldValue) {
			$http({
				url : '/filter',
				method : 'GET',
				params : {
					name : newValue
				}
			}).success(function(data) {
				if (data) {
					$scope.tableParams.filter({});
					for (var i = 0; i < data.fields.length; i++) {
						var key = Object.keys(data.fields[i])[0].toString();
						var val = data.fields[i][key];
						$scope.tableParams.filter()[key] = val.toString();
					}
				}
			});
		}
		;
	});
};

var ChartSchedulerHistoryController = function($timeout, $scope, $resource,
		$http, $q, $log) {
	$scope.getNbJobsBetween2Date = function(startDate, endDate, jobInError) {
		$http({
			url : '/nbJobsBetween2Date',
			method : 'GET',
			params : {
				startDate : startDate,
				endDate : endDate,
				jobInError : jobInError
			}
		}).success(function(data) {
			$scope.nbJobsBetween2Date = data;
		});
	};

	$scope.getLongRunningJobsBetween2Date = function(startDate, endDate,
			jobInError) {
		$http({
			url : '/longRunningJobsBetween2Date',
			method : 'GET',
			params : {
				startDate : startDate,
				endDate : endDate
			}
		}).success(function(data) {
			$scope.longRunningJobsBetween2Date = data;
		});
	};

	$scope.xAxisTickFormatFunction = function() {
		return function(d) {
			return d3.time.format('%Y/%m/%d')(new Date(d));
		};
	};

	$scope.yAxisTickFormatFunction = function() {
		return function(d) {
			return d3.format('d')(d);
		};
	};

	$scope.xFunction = function() {
		return function(d) {
			return d[0];
		};
	};

	$scope.yFunction = function() {
		return function(d) {
			return d[1];
		};
	};

	$scope.yBarFunction = function() {
		return function(d) {
			return d[1] / 1000;
		};
	};
};
