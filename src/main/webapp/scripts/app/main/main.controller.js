'use strict';

angular.module('egdApp')
    .controller('MainController', function ($scope, Principal, GitService) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        GitService.get(function (value) {
            $scope.buildTime = value.buildTime;
        });

        $scope.date = new Date();
    });
