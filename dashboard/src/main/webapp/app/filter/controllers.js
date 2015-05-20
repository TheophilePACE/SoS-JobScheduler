'use strict';

JobSchedulerApp.controller('FilterController', ['$scope', 'resolvedFilter', 'Filter',
    function ($scope, resolvedFilter, Filter) {

        $scope.filters = resolvedFilter;

        $scope.create = function () {
            Filter.save($scope.filter,
                function () {
                    $scope.filters = Filter.query();
                    $('#saveFilterModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.filter = Filter.get({id: id});
            $('#saveFilterModal').modal('show');
        };

        $scope.delete = function (id) {
            Filter.delete({id: id},
                function () {
                    $scope.filters = Filter.query();
                });
        };

        $scope.clear = function () {
            $scope.filter = {id: "", name: "", fields: ""};
        };
        

        
    }]);
