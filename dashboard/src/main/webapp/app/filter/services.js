'use strict';

JobSchedulerApp.factory('Filter', ['$resource',
    function ($resource) {
        return $resource('rest/filters/:id', {}, {
            'query': { 
            	method: 'GET', 
            	isArray: true
            },
            'get': { method: 'GET'}
        });
    }]);
