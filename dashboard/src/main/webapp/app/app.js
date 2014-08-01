
'use strict';

/* App Module */

var JobSchedulerApp = angular.module('JobSchedulerApp', ['http-auth-interceptor', 'tmh.dynamicLocale',
    'ngResource', 'ngRoute', 'ngCookies', 'pascalprecht.translate', 'ui.bootstrap',  
    'ngSanitize', 'ngTable', 'nvd3ChartDirectives', 'mgcrea.ngStrap', 'ngAnimate', 'ui.select2']);

JobSchedulerApp.config(['$routeProvider', '$httpProvider', '$translateProvider',  'tmhDynamicLocaleProvider',
        function ($routeProvider, $httpProvider, $translateProvider, tmhDynamicLocaleProvider) {
            $routeProvider
                .when('/login', {
                    templateUrl: 'app/home/partials/login.html',
                    controller: 'LoginController'
                })
                .when('/settings', {
                    templateUrl: 'app/home/partials/settings.html',
                    controller: 'SettingsController'
                })
                .when('/password', {
                    templateUrl: 'app/home/partials/password.html',
                    controller: 'PasswordController'
                })
                .when('/sessions', {
                    templateUrl: 'app/home/partials/sessions.html',
                    controller: 'SessionsController',
                    resolve:{
                        resolvedSessions:['Sessions', function (Sessions) {
                            return Sessions.get();
                        }]
                    }
                })
                .when('/tracker', {
                    templateUrl: 'app/home/partials/tracker.html',
                    controller: 'TrackerController'
                })
                .when('/metrics', {
                    templateUrl: 'app/home/partials/metrics.html',
                    controller: 'MetricsController'
                })
                .when('/logs', {
                    templateUrl: 'app/home/partials/logs.html',
                    controller: 'LogsController',
                    resolve:{
                        resolvedLogs:['LogsService', function (LogsService) {
                            return LogsService.findAll();
                        }]
                    }
                })
                .when('/audits', {
                    templateUrl: 'app/home/partials/audits.html',
                    controller: 'AuditsController'
                })
                .when('/logout', {
                    templateUrl: 'app/home/partials/main.html',
                    controller: 'LogoutController'
                })
                
                .otherwise({
                    templateUrl: 'app/home/partials/main.html',
                    controller: 'MainController'
                });
            

            $routeProvider.when('/stats-scheduler-job/spooler/:spoolerId', {
                templateUrl: 'app/jobs/partials/stats-scheduler-job.html',
                controller: SchedulerJobStatsController
            });
            

            $routeProvider.when('/stats-test', {
                templateUrl: 'app/jobs/partials/Stats-test.html',
                controller: StatsController
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
            
            $routeProvider.when('/newjob', {
                templateUrl: 'app/jobs/partials/init.html',
                controller: JobEditorController
            });
            
            
            
            
            $routeProvider.when('/newchain', {
                templateUrl: 'app/jobs/partials/New-Chain.html',
                controller: ChainEditorController
            });
            
            $routeProvider.when('/neworder', {
                templateUrl: 'app/jobs/partials/New-Order.html',
                controller: OrderEditorController
            });
            
            
            $routeProvider.when('/scheduler-history/:schedulerHistoryId', {
                templateUrl: 'app/jobs/partials/scheduler-history.html',
                controller: SchedulerHistoryController
            });
            
           

            // Initialize angular-translate
            $translateProvider.useStaticFilesLoader({
                prefix: 'app/i18n/',
                suffix: '.json'
            });

            $translateProvider.preferredLanguage('en');

            $translateProvider.useCookieStorage();

            tmhDynamicLocaleProvider.localeLocationPattern('components/angular-i18n/angular-locale_{{locale}}.js')
            tmhDynamicLocaleProvider.useCookieStorage('NG_TRANSLATE_LANG_KEY');
        }])
        .run(['$rootScope', '$location', 'AuthenticationSharedService', 'Account',
            function($rootScope, $location, AuthenticationSharedService, Account) {
            $rootScope.hasRole = function(role) {
                if ($rootScope.account === undefined) {
                    return false;
                }

                if ($rootScope.account.roles === undefined) {
                    return false;
                }

                if ($rootScope.account.roles[role] === undefined) {
                    return false;
                }

                return $rootScope.account.roles[role];
            };

            $rootScope.$on("$routeChangeStart", function(event, next, current) {
                // Check if the status of the user. Is it authenticated or not?
                AuthenticationSharedService.authenticate().then(function(response) {
                    if (response.data == '') {
                        $rootScope.$broadcast('event:auth-loginRequired');
                    } else {
                        $rootScope.authenticated = true;
                        $rootScope.login = response.data;
                        $rootScope.account = Account.get();

                        // If the login page has been requested and the user is already logged in
                        // the user is redirected to the home page
                        if ($location.path() === "/login") {
                            $location.path('/').replace();
                        }
                    }
                });
            });

            // Call when the 401 response is returned by the client
            $rootScope.$on('event:auth-loginRequired', function(rejection) {
                $rootScope.authenticated = false;
                if ($location.path() !== "/" && $location.path() !== "") {
                    $location.path('/login').replace();
                }
            });

            // Call when the user logs out
            $rootScope.$on('event:auth-loginCancelled', function() {
                $rootScope.login = null;
                $rootScope.authenticated = false;
                $location.path('');
            });
        }])
        .run(['$rootScope', '$route',
            function($rootScope, $route) {
                // This uses the Atmoshpere framework to do a Websocket connection with the server, in order to send
                // user activities each time a route changes.
                // The user activities can then be monitored by an administrator, see the views/tracker.html Angular view.

                $rootScope.websocketSocket = atmosphere;
                $rootScope.websocketSubSocket;
                $rootScope.websocketTransport = 'websocket';

                $rootScope.websocketRequest = { url: 'websocket/activity',
                    contentType : "application/json",
                    transport : $rootScope.websocketTransport ,
                    trackMessageLength : true,
                    reconnectInterval : 5000,
                    enableXDR: true,
                    timeout : 60000 };

                $rootScope.websocketRequest.onOpen = function(response) {
                    $rootScope.websocketTransport = response.transport;
                    $rootScope.websocketRequest.sendMessage();
                };

                $rootScope.websocketRequest.onClientTimeout = function(r) {
                    $rootScope.websocketRequest.sendMessage();
                    setTimeout(function (){
                        $rootScope.websocketSubSocket = $rootScope.websocketSocket.subscribe($rootScope.websocketRequest);
                    }, $rootScope.websocketRequest.reconnectInterval);
                };

                $rootScope.websocketRequest.onClose = function(response) {
                    if ($rootScope.websocketSubSocket) {
                        $rootScope.websocketRequest.sendMessage();
                    }
                };

                $rootScope.websocketRequest.sendMessage = function() {
                    if ($rootScope.websocketSubSocket.request.isOpen) {
                        $rootScope.websocketSubSocket.push(atmosphere.util.stringifyJSON({
                                userLogin: $rootScope.login,
                                page: $route.current.templateUrl}
                        ));
                    }
                };

                $rootScope.websocketSubSocket = $rootScope.websocketSocket.subscribe($rootScope.websocketRequest);

                $rootScope.$on("$routeChangeSuccess", function(event, next, current) {
                    $rootScope.websocketRequest.sendMessage();
                });
            }
        ]);


JobSchedulerApp.factory("SessionService", function() {
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


JobSchedulerApp.filter('newlines', function() {
	  return function(text) {
		  var result = "<p>" + text + "</p>";
		  result = result.replace(/\r\n\r\n/g, "</p><p>").replace(/\n\n/g, "</p><p>");
		  result = result.replace(/\r\n/g, "<br/>").replace(/\n/g, "<br/>");
		  result = result.replace(/info/g, "<b>info</b>");
		  result = result.replace(/ERROR/g, "<span class='btn btn-warning btn-xs'>ERROR</span>");
		  return result;
	  };
	});




