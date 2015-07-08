'use strict';

egdApp
    .controller('RequestResetController', function ($rootScope, $scope, $state, $timeout, $log, Auth) {

        $scope.resetAccount = {};
        $timeout(function (){angular.element('[ng-model="resetAccount.email"]').focus();});

        $scope.requestReset = function () {

            delete $scope.success;
            delete $scope.error;
            delete $scope.errorEmailNotExists;

            Auth.resetPasswordInit($scope.resetAccount.email).then(function () {
                $log.debug("resetPasswordInit: Email sent");
                $scope.success = true;
            }).catch(function (response) {
                if (response.status === 400 && response.data === 'e-mail address not registered') {
                    $scope.errorEmailNotExists = true;
                } else {
                    $scope.error = true;
                }
            });
        }

    });
