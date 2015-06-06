'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('stats', {
                abstract: true,
                parent: 'site'
            });
    });
