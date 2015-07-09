'use strict';

egdApp
    .controller('ReadingEditController', function ($scope, $rootScope, $stateParams, $log, entity, ReadingResource) {
        $scope.reading = entity;
        $scope.languages = ["ja", "et", "en"];

        $scope.load = function (id) {
            ReadingResource.get({id: id}, function(data) {
                $scope.reading = data;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('egdApp:readingSaved', result);
        };

        var save = function(resourceFunction) {
            delete $scope.success;
            delete $scope.error;

            resourceFunction($scope.reading, function(result) {
                $scope.success = true;
                onSaveFinished(result);
            }, function() {
                $scope.error = true;
            });
        };

        $scope.submit = function () {
            if ($scope.reading.id != null) {
                save(ReadingResource.update);
            } else {
                save(ReadingResource.save);
            }
        };


    });
