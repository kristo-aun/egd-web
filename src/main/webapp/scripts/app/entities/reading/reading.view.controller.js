'use strict';

egdApp
    .controller('ReadingViewController', function ($scope, $rootScope, $stateParams, $log, entity, ReadingResource) {
        $scope.reading = entity;
        $scope.load = function (id) {
            ReadingResource.get({id: id}, function(data) {
                $scope.reading = data;
            });
        };
        $rootScope.$on('egdApp:readingSaved', function(event, data) {
            $scope.reading = data;
        });
    });
