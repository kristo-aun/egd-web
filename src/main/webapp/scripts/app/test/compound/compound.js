'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('compound', {
                parent: 'test',
                url: '/compound',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'compound.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/test/compound/compound.html',
                        controller: 'CompoundController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test');
                        return $translate.refresh();
                    }]
                }
            })
            .state('compound.first', {
                parent: "compound",
                templateUrl: 'scripts/app/test/compound/compound.first.html'
            })
            .state('compound.second', {
                parent: "compound",
                templateUrl: 'scripts/app/test/compound/compound.second.html'
            });
    });
