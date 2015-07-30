'use strict';

egdApp
    .controller('ActivationController', function ($scope, $stateParams, AccountResource) {
        AccountResource.activate($stateParams.key).then(function () {
            $scope.error = null;
            $scope.success = 'OK';
        }, function () {
            $scope.success = null;
            $scope.error = 'ERROR';
        });
    });

