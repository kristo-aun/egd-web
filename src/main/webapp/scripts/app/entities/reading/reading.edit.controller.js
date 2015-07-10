'use strict';

egdApp
    .controller('ReadingEditController', function ($scope, $rootScope, $state, $stateParams, $translate, $confirm, ReadingResource) {
        $scope.languages = ["ja", "et", "en"];

        $scope.clear = function () {
            delete $scope.reading;
            delete $scope.success;
            delete $scope.error;
        };

        $scope.load = function (id) {
            $scope.clear();
            if (id > 0) {
                ReadingResource.get({id: id}, function (data) {
                    $scope.reading = data;
                });
            } else {
                $scope.reading = {"bodyLang": "ja", "transcriptLang": $translate.use()};
            }
        };
        $scope.load($stateParams.id);

        var setSuccess = function () {
            delete $scope.error;
            $scope.success = true;
        };

        var setError = function () {
            delete $scope.success;
            $scope.error = true;
        };

        var save = function (resourceFunction) {
            resourceFunction($scope.reading, function (data) {
                $scope.reading = data;
                setSuccess();
                $rootScope.setStateParams({id: data.id});
                $scope.emit("readingSaved", data);
            }, function () {
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

        $scope.deleteReading = function () {
            ReadingResource.delete({id: $scope.reading.id},
                function () {
                    $scope.clear();
                    $state.go("reading");
                });
        };

        $scope.deleteReadingConfirm = function () {
            $confirm({}, {templateUrl: 'scripts/app/entities/reading/reading.confirm.delete.html'})
                .then(function () {
                    $scope.deleteReading();
                });
        };
    });
