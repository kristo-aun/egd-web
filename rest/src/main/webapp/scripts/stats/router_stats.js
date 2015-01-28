'use strict';

egdApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
        $routeProvider
            .when('/stats', {
                templateUrl: 'views/stats.html',
                controller: 'StatsController',
                access: {
                    authorizedRoles: [USER_ROLES.user]
                }
            })
    });
