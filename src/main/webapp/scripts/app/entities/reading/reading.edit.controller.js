'use strict';

egdApp
    .controller('ReadingEditController', function ($scope, $rootScope, $state, $stateParams, $log, $translate, $confirm, ReadingResource, ReadingPageResource, Upload, Moment) {
        $scope.languages = ["ja", "et", "en"];

        //------------------------------ success & error ------------------------------

        var clearSuccess = function () {
            delete $scope.success;
            delete $scope.successI18n;
        };

        var setSuccess = function (i18n) {
            $log.debug("setSuccess", i18n);
            clearError();
            $scope.successI18n = i18n;
            $scope.success = true;
        };

        var clearError = function () {
            delete $scope.error;
            delete $scope.errorI18n;
        };

        var setError = function (i18n) {
            $log.debug("setError", i18n);
            clearSuccess();
            $scope.errorI18n = i18n;
            $scope.error = true;
        };

        var clearNotice = function () {
            clearError();
            clearSuccess();
        };

        //------------------------------ init ------------------------------

        $scope.clear = function () {
            delete $scope.reading;
            clearNotice();
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
                $log.debug('upload.progress: ' + progressPercentage + '% ');
            }).success(function (data, status, headers, config) {
                $scope.reading.audioSha = data.audioSha;
                setSuccess("global.messages.success.saved");
            }).error(function () {
                setError("global.messages.error.failed");
            });
        };

        $scope.deleteAudio = function () {
            ReadingResource.deleteAudio($scope.reading.id).then(function () {
                setSuccess("global.messages.success.removed");
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
