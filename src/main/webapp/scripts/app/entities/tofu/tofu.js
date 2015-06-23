'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('tofu', {
                parent: 'entity',
                url: '/tofu',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'tofu.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tofu/tofus.html',
                        controller: 'TofuController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tofu');
                        return $translate.refresh();
                    }]
                }
            });
    });
