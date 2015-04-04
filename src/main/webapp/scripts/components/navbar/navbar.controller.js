'use strict';

egdApp
    .controller('NavbarController', function ($scope, $location, $state, $log, Auth, Principal) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.isInRole = Principal.isInRole;
        $scope.$state = $state;

        Principal.identity().then(function(account) {
            $scope.account = account;
        });

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
    });
