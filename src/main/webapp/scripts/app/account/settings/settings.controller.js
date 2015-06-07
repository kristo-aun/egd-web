'use strict';

egdApp
    .controller('SettingsController', function ($rootScope, $scope, Principal, Auth, Language, $translate) {
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
    });
