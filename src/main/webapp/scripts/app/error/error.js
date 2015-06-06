'use strict';

egdApp
    .controller('ErrorController', function ($scope, $log, $stateParams, $translate) {

        var nomessage = 'error.nomessage';
        var tcode = $stateParams.code ? 'error.' + $stateParams.code : nomessage;

        $translate(tcode).then(function(value) {
            $scope.errorMessage = value;
        }, function(e) {
            $translate(nomessage).then(function(value) {
                $scope.errorMessage = value;
            });
        });
    });

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('error', {
                parent: 'site',
                url: '/error/:code',
                data: {
                    roles: [],
                    pageTitle: 'error.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/error/error.html',
                        controller: 'ErrorController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('error');
                        return $translate.refresh();
                    }]
                }
            });
    });
