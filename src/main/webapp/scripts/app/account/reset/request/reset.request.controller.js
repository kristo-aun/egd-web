'use strict';

egdApp
    .controller('RequestResetController', function ($rootScope, $scope, $state, $timeout, $log, AccountResource) {

        $scope.resetAccount = {};
        $timeout(function (){angular.element('[ng-model="resetAccount.email"]').focus();});

        $scope.requestReset = function () {

            delete $scope.success;
            delete $scope.error;
            delete $scope.errorEmailNotExists;

            AccountResource.resetPasswordInit($scope.resetAccount.email).then(function () {
                $log.debug("resetPasswordInit: Email sent");
                $scope.success = true;
            }, function (response) {
                if (response.status === 400 && response.data === 'e-mail address not registered') {
                    $scope.errorEmailNotExists = true;
                } else {
                    $scope.error = true;
                }
            });
        }

    });
