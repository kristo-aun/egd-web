'use strict';

egdApp
    .controller('SettingsController', function ($state, $rootScope, $scope, $log, Principal, Auth, AccountResource, Language, $translate, $confirm) {

        $scope.success = null;
        $scope.error = null;
        Principal.identity(true).then(function(account) {
            $scope.settingsAccount = angular.copy(account);
        });

        $scope.save = function () {
            AccountResource.save($scope.settingsAccount).then(function() {
                $scope.error = null;
                $scope.success = true;

                Language.getCurrent().then(function(current) {
                    if ($scope.settingsAccount.langKey !== current) {
                        $translate.use($scope.settingsAccount.langKey);
                    }
                });
                $rootScope.$broadcast('accountChange');
            }, function(response) {
                $scope.success = null;
                if (response.status === 400 && response.data === 'e-mail address already in use') {
                    $scope.errorEmailExists = true;
                } else {
                    $scope.error = true;
                }
            });
        };

        $scope.deleteAccount = function() {
            Auth.deleteAccount().then(function() {
                $state.go("login");
            });
        };

        $scope.deleteAccountConfirm = function() {
            $confirm({}, {templateUrl: 'scripts/app/account/settings/account.confirm.delete.html' })
                .then(function() {
                    $scope.deleteAccount();
                });
        };
    });
