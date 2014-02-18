'use strict';

var AngularSpringApp = {};

var App = angular.module('DashBoard-JobScheduler', ['ui.bootstrap', 'ngRoute', 
             'ngSanitize', 'ngTable', 'ngResource', 'nvd3ChartDirectives', 'mgcrea.ngStrap', 'ngAnimate']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {

    $routeProvider.when('/login', {
        templateUrl: 'resources/templates/login.html',
        controller: 'LoginController'
    });

    $routeProvider.when('/logout', {
        templateUrl: 'resources/templates/login.html',
        controller: 'LoginController'
    });

    $routeProvider.when('/home', {
        templateUrl: 'app/home/partials/home.html',
        controller: HomeController
    });
    
    $routeProvider.when('/list-scheduler-job', {
        templateUrl: 'app/jobs/partials/list-scheduler-job.html',
        controller: ListSchedulerJobController
    });

    $routeProvider.when('/chart-scheduler-history', {
        templateUrl: 'app/jobs/partials/chart-scheduler-history.html',
        controller: ChartSchedulerHistoryController
    });

    $routeProvider.when('/list-scheduler-history', {
        templateUrl: 'app/jobs/partials/list-scheduler-history.html',
        controller: ListSchedulerHistoryController
    });
    
    $routeProvider.otherwise({redirectTo: '/home'});
}]);

App.config(function($httpProvider) {

    var logsOutUserOn401 = function($location, $q, SessionService, FlashService) {
        var success = function(response) {
            return response;
        };

        var error = function(response) {
            if(response.status === 401) {
                SessionService.unset('authenticated');
                $location.path('/login');
                FlashService.show("Please log in to continue.");
            }
            return $q.reject(response);
        };

        return function(promise) {
            return promise.then(success, error);
        };
    };

    $httpProvider.responseInterceptors.push(logsOutUserOn401);

});

App.run(function($rootScope, $location, AuthenticationService, FlashService) {
    var routesThatRequireAuth = ['/todoes'];

    $rootScope.$on('$routeChangeStart', function(event, next, current) {
//        if(_(routesThatRequireAuth).contains($location.path()) && !AuthenticationService.isLoggedIn()) {
//            $location.path('/login');
//            FlashService.show("Please log in to continue.");
//        }
    });
});

App.controller("LoginController", function($scope, $location, AuthenticationService) {
    $scope.credentials = { j_username: "", j_password: "" };

    $scope.login = function(credentials) {
        AuthenticationService.login(credentials).success(function() {
            $location.path('/todoes');
        });
    };
});


App.factory("SessionService", function() {
    return {
        get: function(key) {
            return sessionStorage.getItem(key);
        },
        set: function(key, val) {
            return sessionStorage.setItem(key, val);
        },
        unset: function(key) {
            return sessionStorage.removeItem(key);
        }
    };
});


App.factory("FlashService", function($rootScope) {
    return {
        show: function(message) {
            $rootScope.flash = message;
        },
        clear: function() {
            $rootScope.flash = "";
        }
    };
});


App.factory("AuthenticationService", function($http, $sanitize, SessionService, FlashService) {

    var cacheSession   = function() {
        SessionService.set('authenticated', true);
    };

    var uncacheSession = function() {
        SessionService.unset('authenticated');
    };

    var loginError = function(response) {
        //console.log("Error during login"+response);
        FlashService.show("Invalid username or password");
    };

/*
    var sanitizeCredentials = function(credentials) {
        return {
            email: $sanitize(credentials.email),
            password: $sanitize(credentials.password),
            csrf_token: CSRF_TOKEN
        };
    };
*/

    return {
        login: function(credentials) {
            var payload = $.param({j_username: credentials.j_username, j_password: credentials.j_password});
            var config = {
                headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
            };

            var login = $http.post("j_spring_security_check", payload,config );
            login.success(cacheSession);
            login.success(FlashService.clear);
            login.error(loginError);
            return login;
        },
        logout: function() {
            var logout = $http.get("j_spring_security_logout");
            logout.success(uncacheSession);
            return logout;
        },
        isLoggedIn: function() {
            return SessionService.get('authenticated');
        }
    };
});





