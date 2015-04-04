'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('logout', {
                parent: 'account',
                url: '/logout',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/dict/dict.html',
                        controller: 'LogoutController'
                    }
                }
            });
    });
