'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('overview', {
                abstract: true,
                parent: 'site'
            });
    });
