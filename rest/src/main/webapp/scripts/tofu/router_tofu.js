'use strict';

egdApp
    .config(function ($routeProvider, USER_ROLES) {
            $routeProvider
                .when('/tofus', {
                    templateUrl: 'views/tofus.html',
                    controller: 'TofusController',
                    access: {
                        authorizedRoles: [USER_ROLES.admin]
                    }
                })
        });
