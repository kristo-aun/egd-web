'use strict';

egdApp
    .controller('NavbarController', function ($rootScope, $scope, $location, $state, $log, Auth, Principal) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.isInRole = Principal.isInRole;
        $scope.$state = $state;

        $scope.identity = function() {
            $rootScope.$broadcast('accountChange');
        };
        $scope.identity();

        $scope.logout = function () {
            Auth.logout();
            $scope.identity();
            $state.go('home');
        };
    });
