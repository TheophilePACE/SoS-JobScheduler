'use strict';

JobSchedulerApp
    .config(['$routeProvider', '$httpProvider', '$translateProvider',
        function ($routeProvider, $httpProvider, $translateProvider) {
            $routeProvider
                .when('/filters', {
                    templateUrl: 'app/filter/partials/filters.html',
                    controller: 'FilterController',
                    resolve:{
                        resolvedFilter: ['Filter', function (Filter) {
                            return Filter.query();
                        }]
                    }
                });
        }]);
