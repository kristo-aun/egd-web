'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('test', {
                abstract: true,
                parent: 'site'
            });
    });
