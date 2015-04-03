'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('tofus', {
                parent: 'entities',
                url: '/tofus',
                data: {
                    roles: [],
                    pageTitle: 'tofus.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tofus/tofus.html',
                        controller: 'TofusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tofus');
                        return $translate.refresh();
                    }]
                }
            });
    });
