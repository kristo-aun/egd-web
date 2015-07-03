'use strict';

egdApp
    .controller('SettingsController', function ($state, $rootScope, $scope, Principal, Auth, Language, $translate, $confirm) {
        $scope.success = null;
        $scope.error = null;
        Principal.identity(true).then(function(account) {
            $scope.settingsAccount = angular.copy(account);
        });

        $scope.save = function () {
            Auth.updateAccount($scope.settingsAccount).then(function() {
                $scope.error = null;
                $scope.success = 'OK';

                Language.getCurrent().then(function(current) {
                    if ($scope.settingsAccount.langKey !== current) {
                        $translate.use($scope.settingsAccount.langKey);
                    }
                });
                $rootScope.$broadcast('accountChange');
            }).catch(function() {
                $scope.success = null;
                $scope.error = 'ERROR';
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
