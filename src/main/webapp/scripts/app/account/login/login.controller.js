'use strict';

egdApp
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout, $location, $window, Auth) {

        $scope.forceSSL = function () {
            if ($location.protocol() !== 'https') {
                $window.location.href = $location.absUrl().replace('http', 'https');
            }
        };
        $scope.forceSSL();

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
                $scope.authenticationError = false;
                $rootScope.$broadcast('accountChange');
                if ($rootScope.previousStateName === 'register') {
                    $state.go('home');
                } else {
                    $rootScope.back();
                }
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
    });
