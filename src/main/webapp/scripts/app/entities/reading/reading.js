'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('reading', {
                parent: 'entity',
                url: '/reading',
                data: {
                    roles: [],
                    pageTitle: 'reading.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reading/readings.html',
                        controller: 'ReadingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reading');
                        return $translate.refresh();
                    }]
                }
            })
            .state('reading.detail', {
                parent: 'reading',
                url: '/reading/:id',
                data: {
                    roles: [],
                    pageTitle: 'reading.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reading/reading.detail.html',
                        controller: 'ReadingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reading');
                        return $translate.refresh();
                    }]
                }
            });
    });
