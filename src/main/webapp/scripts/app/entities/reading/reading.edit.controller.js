'use strict';

egdApp
    .controller('ReadingEditController', function ($scope, $rootScope, $state, $stateParams, $translate, $confirm, ReadingResource, ReadingPageResource, Upload, Moment) {
        $scope.languages = ["ja", "et", "en"];

        $scope.clear = function () {
            delete $scope.reading;
            delete $scope.success;
            delete $scope.error;
        };

        $scope.loadTags = function(tagstart) {
            console.log($scope.reading.tags);
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

            var toJson = function (data) {
                data.createdDate = Moment.serializeDateTime(data.createdDate);
                data.lastModifiedDate = Moment.serializeDateTime(data.lastModifiedDate);

                var tags = [];
                angular.forEach(data.tags, function(value) {
                    this.push(value.text);
                }, tags);
                data.tags = tags;

                return angular.toJson(data);
            };

            Upload.upload({
                url: '/api/readingPages',
                data: toJson($scope.reading), // additional data to send
                file: $scope.audioFile
            }).progress(function (evt) {
                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                console.log('progress: ' + progressPercentage + '% ');

            }).success(function (data, status, headers, config) {
                $scope.reading.audioSha = data.audioSha;
                setSuccess();
            }).error(function (data, status, headers, config) {
                console.log('error status: ' + status);
                setError();
            });
        };

        $scope.deleteAudio = function () {
            ReadingResource.deleteAudio($scope.reading.id).then(function () {
                setSuccess();
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
