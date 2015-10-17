'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('rtk', {
                parent: 'site',
                url: '/rtk',
                data: {
                    authorities: [],
                    pageTitle: 'rtk.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/rtk/rtk.html',
                        controller: 'RTKController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rtk');
                        return $translate.refresh();
                    }]
                }
            }).state('rtk.detail', {
                parent: 'site',
                url: '/rtk/:book/:query',
                data: {
                    authorities: [],
                    pageTitle: 'rtk.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/rtk/rtk.html',
                        controller: 'RTKController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rtk');
                        return $translate.refresh();
                    }]
                }
            });
    });
