'use strict';

egdApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/entr', {
                    templateUrl: 'views/entrs.html',
                    controller: 'EntrController',
                    resolve:{
                        resolvedEntr: ['Entr', function (Entr) {
                            return Entr.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
