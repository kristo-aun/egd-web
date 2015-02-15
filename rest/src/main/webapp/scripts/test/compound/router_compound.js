'use strict';

egdApp
    .config(function ($routeProvider, USER_ROLES) {
            $routeProvider
                .when('/test/compound', {
                    templateUrl: 'views/compound.html',
                    controller: 'TestCompoundController',
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
