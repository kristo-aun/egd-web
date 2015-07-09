'use strict';

egdApp
    .controller('ReadingEditController', function ($scope, $rootScope, $state, $stateParams, $translate, $log, ReadingResource) {
        $scope.languages = ["ja", "et", "en"];

        $scope.load = function (id) {
            if (id > 0) {
                ReadingResource.get({id: id}, function(data) {
                    $scope.reading = data;
                });
            } else {
                $scope.reading = {"bodyLang": "ja", "transcriptLang": $translate.use()};
            }
        };
        $scope.load($stateParams.id);

        var setSuccess = function() {
            delete $scope.error;
            $scope.success = true;
        };

        var setError = function() {
            delete $scope.success;
            $scope.error = true;
        };

        var save = function(resourceFunction) {
            resourceFunction($scope.reading, function(data) {
                $scope.reading = data;
                setSuccess();
                $rootScope.setStateParams({id: data.id});
                $scope.emit("readingSaved", data);
            }, function() {
                setError();
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
