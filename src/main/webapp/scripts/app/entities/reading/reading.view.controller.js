'use strict';

egdApp
    .controller('ReadingViewController', function ($scope, $rootScope, $stateParams, $log, ReadingResource, Principal, ReadingPageResource) {

        var cleanup = function(text) {
            if (text) {
                while(text.indexOf("\n\n\n") > -1) {
                    text = text.split("\n\n\n").join("\n\n");
                }
                return text;
            }
            return "";
        };

        var buildRows = function(body, transcript) {
            var bodyRows = cleanup(body).split("\n\n");
            var transcriptRows = cleanup(transcript).split("\n\n");
            var rows = [];
            for (var i = 0; i < Math.max(bodyRows.length, transcriptRows.length); i++) {
                rows.push([bodyRows[i], transcriptRows[i]]);
            }
            return rows;
        };

        $scope.setReading = function(reading) {
            angular.forEach(reading.pages, function(item) {
                item.rows = buildRows(item.body, item.transcript);
                delete item.body;
                delete item.transcript
            });
            $scope.reading = reading;
        };

        $scope.setPage = function(page) {
            $scope.currentPage = page;
            $scope.readingPage = $scope.reading.pages[$scope.currentPage-1];
        };

        $scope.load = function (id) {
            ReadingResource.get({id: id}, function (data) {
                ReadingPageResource.findByReading(data.id).then(function (pages) {
                    data.pages = pages;
                    $scope.setReading(data);
                    $scope.setPage(1);
                });
            });
        };
        $scope.load($stateParams.id);

        $scope.isReadingEditAllowed = function (reading) {
            if (!reading) return false;
            if (angular.isDefined(reading.isReadingEditAllowed)) {
                return reading.isReadingEditAllowed;
            } else {
                reading.isReadingEditAllowed = Principal.isAuthenticated() && (Principal.hasAuthority('ROLE_ADMIN') || (reading && Principal.equals(reading.createdBy)));
                return reading.isReadingEditAllowed;
            }
        };
    });
