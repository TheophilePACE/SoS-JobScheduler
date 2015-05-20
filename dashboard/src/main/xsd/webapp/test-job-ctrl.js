angular.module('APP', ['ngTable', 'ngResource']).controller('jobListCtrl', ['$timeout', '$scope', '$resource', 'ngTableParams', function($timeout, $scope, $resource, ngTableParams) {
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
}]);




//function JobCtrl($scope, $http) {
//		$http.get('/jobs').success(function(data) {
//			$scope.jobs = data;
//		});
//};

