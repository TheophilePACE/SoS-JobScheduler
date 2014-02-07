'use strict';

/**
 * JobListCtrl
 * @constructor
 */
var JobListCtrl = function($timeout, $scope, $resource, ngTableParams) {
		var Api = $resource('/jobs');
		$scope.tableParams = new ngTableParams({
			page: 1,
			count: 10,
			sorting: { name: 'asc' 
			} 
		}, {
			total: 0,
			getData : function($defer, params) {
				Api.get(params.url(), function(data) {
					$timeout(function() {
						params.total(data.total);
						$defer.resolve(data.result);
					}, 500);
					
				});
			}
		});
};


