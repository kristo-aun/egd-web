'use strict';

egdApp
    .controller('NavbarController', function ($scope, $location, $state, $log, Auth, Principal) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.isInRole = Principal.isInRole;
        $scope.$state = $state;

        $scope.identity = function() {
            Principal.identity().then(function(account) {
                $scope.account = account;
            });
        };

        $scope.identity();

        $scope.$on('login', function (event) {
            $scope.identity();
        });

        $scope.logout = function () {
            Auth.logout();
            $scope.account = null;
            $state.go('home');
        };
    });
