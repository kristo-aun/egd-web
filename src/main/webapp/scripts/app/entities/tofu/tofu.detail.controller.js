'use strict';

egdApp
    .controller('TofuDetailController', function ($scope, $stateParams, TofuService) {
        $scope.tofu = {};
        $scope.load = function (id) {
            TofuService.get({id: id}, function(result) {
              $scope.tofu = result;
            });
        };
        $scope.load($stateParams.id);
    });
