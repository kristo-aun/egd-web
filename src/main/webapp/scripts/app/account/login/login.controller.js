'use strict';

egdApp
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout, $location, $window, Auth) {

        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = true;
        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function (event) {
            event.preventDefault();
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $rootScope.$broadcast("accountChange");
                $scope.authenticationError = false;
                if ($rootScope.previousStateName === 'register' ||
                    $rootScope.previousStateName === 'register.external' ||
                    $rootScope.previousStateName === 'resetRequest' ||
                    $rootScope.previousStateName === 'resetFinish') {
                    $state.go('home');
                } else {
                    $rootScope.back();
                }
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
    });
