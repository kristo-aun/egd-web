'use strict';

egdApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
        $routeProvider
            .when('/dict', {
                templateUrl: 'main.html',
                controller: 'MainController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/dict/:phrase', {
                templateUrl: 'main.html',
                controller: 'MainController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
    });
