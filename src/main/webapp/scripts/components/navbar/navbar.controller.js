'use strict';

angular.module('egdApp')
    .controller('NavbarController', function ($scope, $location, $state, $log, Auth, Principal) {
        $log.debug("NavbarController");
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.isInRole = Principal.isInRole;
        $scope.$state = $state;

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
    });
