'use strict';

egdApp
    .controller('TofuDetailController', function ($scope, $stateParams, Tofu) {
        $scope.tofu = {};
        $scope.load = function (id) {
            Tofu.get({id: id}, function(result) {
              $scope.tofu = result;
            });
        };
        $scope.load($stateParams.id);
    });
