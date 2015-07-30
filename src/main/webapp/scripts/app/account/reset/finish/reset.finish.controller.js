'use strict';

egdApp
    .controller('ResetFinishController', function ($scope, $stateParams, $timeout, AccountResource) {

        $scope.keyMissing = $stateParams.key === undefined;
        $scope.doNotMatch = null;

        $scope.resetAccount = {};
        $timeout(function (){angular.element('[ng-model="resetAccount.password"]').focus();});

        $scope.finishReset = function() {
            if ($scope.resetAccount.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                AccountResource.resetPasswordFinish($stateParams.key, $scope.resetAccount.password).then(function () {
                    $scope.success = 'OK';
                }, function () {
                    $scope.success = null;
                    $scope.error = 'ERROR';
                });
            }
        };
    });
