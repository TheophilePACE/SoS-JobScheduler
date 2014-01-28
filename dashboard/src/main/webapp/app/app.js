function JobCtrl($scope, $http) {
		$http.get('/jobs').success(function(data) {
			$scope.jobs = data;
		});
};

