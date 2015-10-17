'use strict';

egdApp
    .controller('FooterController', function($scope, GitService) {
        GitService.get(function(value) {
            $scope.buildTime = value.buildTime;
        });
    });
