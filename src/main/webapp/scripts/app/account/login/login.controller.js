'use strict';

egdApp
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout, $location, $window, Auth) {

        $scope.user = {};
        $scope.errors = {};

        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function (event) {
            event.preventDefault();
            Auth.login({
                username: $scope.username,
                password: $scope.password
            }).then(function () {
                $rootScope.$broadcast("accountChange");
                $scope.authenticationError = false;
                if ($rootScope.previousStateName === 'register' ||
                    $rootScope.previousStateName === 'registerExternal' ||
                    $rootScope.previousStateName === 'resetRequest' ||
                    $rootScope.previousStateName === 'resetFinish') {
                    $state.go('home');
                } else {
                    $rootScope.back();
                }
            }, function(e) {
                $scope.authenticationError = true;
            });
        };
    });
