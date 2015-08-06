'use strict';

egdApp
    .controller('ReadingEditController', function ($scope, $q, $rootScope, $state, $stateParams, $log, $translate, $confirm, ReadingResource, ReadingPageResource, Upload, Moment) {
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
                    ReadingPageResource.findByReading(data.id).then(function (data) {
                        $scope.readingPages = data;
                        $scope.readingPages[0].active = true;
                    });
                });
            } else {
                $scope.reading = {"bodyLang": "ja", "transcriptLang": $translate.use()};
                $scope.readingPages = [{active: true, page: 1, id: undefined}];
            }
        };
        $scope.load($stateParams.id);

        var commitRemovedPages = function() {
            var deferred = $q.defer();

            var i = $scope.removedPages.length;
            if (i < 1) {
                deferred.resolve();
            } else {
                $scope.removedPages.forEach(function(item) {
                    ReadingPageResource.deletePage(item.id).then(function() {
                        i--;
                        if (i == 0) {
                            //kõik on kustutatud
                            deferred.resolve();
                        }
                    }, function() {
                        deferred.reject();
                    });
                });
            }

            return deferred.promise;
        };

        $scope.submit = function () {

            commitRemovedPages().then(function() {
                ReadingResource.save($scope.reading, function (data) {
                    $scope.reading.id = data.id;
                    $scope.readingPages.forEach(function(item, index) {
                        item.readingId = data.id;
                        var copy = angular.copy(item);
                        delete copy.audioFile;

                        Upload.upload({
                            url: '/api/readingPages',
                            data: angular.toJson(copy),
                            file: item.audioFile
                        }).progress(function (evt) {
                            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                            $log.debug('upload.progress: ' + progressPercentage + '% ');
                        }).success(function (data, status, headers, config) {
                            item.id = data.id;
                            item.audioSha = data.audioSha;
                            $scope.removeFile(index);
                            setSuccess("global.messages.success.saved");
                        }).error(function () {
                            setError("global.messages.error.fail");
                        });
                    });
                });
            }, function() {
                setError("global.messages.error.fail");
            });
        };

        var deletePageAudio = function (index) {
            var readingPageId = $scope.readingPages[index].id;
            ReadingPageResource.deleteAudio(readingPageId).then(function (response) {
                $log.debug("removed", response.data);
                delete $scope.readingPages[index].audioSha;
                setSuccess("global.messages.success.removed");
            }, function() {
                setError("global.messages.error.fail");
            });
        };

        $scope.deletePageAudioConfirm = function (readingPageId) {
            $confirm({}, {templateUrl: 'scripts/app/entities/reading/reading.confirm.delete.html'})
                .then(function () {
                    deletePageAudio(readingPageId);
                });
        };

        var deleteReading = function () {
            ReadingResource.delete({id: $scope.reading.id},
                function () {
                    $scope.clear();
                    $state.go("reading");
                });
        };

        $scope.deleteReadingConfirm = function () {
            $confirm({}, {templateUrl: 'scripts/app/entities/reading/reading.confirm.delete.html'})
                .then(function () {
                    deleteReading();
                });
        };

        $scope.fileSelected = function(files, page) {
            if (files.length > 0) {
                page.audioFileName = files[0].name;
            }
        };

        //------------------------------ lehtede haldus ------------------------------

        var countPages = function() {
            return $scope.readingPages.length;
        };

        $scope.removedPages = [];

        $scope.removePage = function (index) {
            $scope.removedPages.push($scope.readingPages[index]);
            $scope.readingPages.splice(index, 1);
        };

        var clickLastPage = function() {
            try {
                var key = countPages() - 1;
                $('div#readingEditTabs').find('.nav-tabs li:nth-child(' + key + ')').find("a").click();
            } catch (ignored) {}
        };

        $scope.addPage = function(e) {
            e.preventDefault();
            e.stopPropagation();

            var len = countPages();

            var page = {
                body:undefined,
                transcript: undefined,
                readingId: $scope.reading.id,
                page: len + 1,
                audioFile: undefined,
                active: true
            };
            $scope.readingPages.push(page);
        };

        $scope.removeFile = function(index) {
            $('input#readingAudioFile_' + index).val("");
            delete $scope.readingPages[index].audioFile;
        };
    });
