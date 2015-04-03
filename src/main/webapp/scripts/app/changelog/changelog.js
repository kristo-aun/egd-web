'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('changelog', {
                parent: 'site',
                url: '/changelog',
                data: {
                    roles: [],
                    pageTitle: 'changelog.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/changelog/changelog.html',
                        controller: 'ChangelogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('changelog');
                        return $translate.refresh();
                    }]
                }
            });
    });
