'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('stat', {
                parent: 'overview',
                url: '/stat',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'stat.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/overview/stat/stats.html',
                        controller: 'StatController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stat');
                        return $translate.refresh();
                    }]
                }
            });
    });
