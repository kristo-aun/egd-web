'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('reading', {
                parent: 'entity',
                url: '/reading',
                data: {
                    authorities: [],
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
            .state('reading.view', {
                parent: 'reading',
                url: '/{id}',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reading/reading.view.html',
                        controller: 'ReadingViewController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reading');
                        return $translate.refresh();
                    }]
                }
            })
            .state('reading.edit', {
                parent: 'reading',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reading/reading.edit.html',
                        controller: 'ReadingEditController'
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
