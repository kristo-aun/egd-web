'use strict';

egdApp
    .controller('ReadingEditController', function ($scope, $rootScope, $state, $stateParams, $translate, $confirm, ReadingResource) {
        $scope.languages = ["ja", "et", "en"];

        $scope.clear = function () {
            delete $scope.reading;
            delete $scope.success;
            delete $scope.error;
        };

        $scope.loadTags = function(tagstart) {
            return ReadingResource.autocompleteTag(tagstart);
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

        $scope.removeFile = function() {
            $('input#readingAudioFile').val("");
            delete $scope.audioFile;
        };

        $scope.submit = function () {
            ReadingResource.save($scope.reading, $scope.audioFile).then(function (result) {
                $scope.reading = result.data;
                setSuccess();
                $rootScope.setStateParams({id: $scope.reading.id});
                $scope.emit("readingSaved", $scope.reading);
            }, function () {
                setError();
            });
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
