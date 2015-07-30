'use strict';

egdApp
    .controller('PasswordController', function ($scope, AccountResource, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
        });

        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.changePassword = function () {
            if ($scope.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.doNotMatch = null;
                AccountResource.changePassword($scope.password).then(function () {
                    $scope.error = null;
                    $scope.success = 'OK';
                }, function () {
                    $scope.success = null;
                    $scope.error = 'ERROR';
                });
            }
        };
    });
