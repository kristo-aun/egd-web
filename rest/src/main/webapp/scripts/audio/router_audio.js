'use strict';

egdApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/audio', {
                    templateUrl: 'views/audios.html',
                    controller: 'AudioController',
                    resolve:{
                        resolvedAudio: ['Audio', function (Audio) {
                            return Audio.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
