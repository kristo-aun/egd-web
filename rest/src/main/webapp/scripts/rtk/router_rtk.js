'use strict';

egdApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
        $routeProvider
            .when('/rtk', {
                templateUrl: 'views/rtk.html',
                controller: 'RTKController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/rtk/:book/:query', {
                templateUrl: 'views/rtk.html',
                controller: 'RTKController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
    });
