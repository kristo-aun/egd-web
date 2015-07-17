'use strict';

egdApp
    .controller('ReadingViewController', function ($scope, $rootScope, $stateParams, $log, ReadingResource, Principal) {
        $scope.isAuthenticated = Principal.isAuthenticated;

        var cleanup = function(text) {
            if (text)
                while(text.indexOf("\n\n\n") > -1) {
                    text = text.split("\n\n\n").join("\n\n");
                }
            return text;
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

        $scope.setReading = function(data) {
            data.rows = buildRows(data.body, data.transcript);
            $scope.reading = data;
        };

        $scope.load = function (id) {
            ReadingResource.get({id: id}, function (data) {
                $scope.setReading(data);
            });
        };
        $scope.load($stateParams.id);

        $scope.isReadingEditAllowed = function (reading) {
            return $scope.isAuthenticated() && (Principal.isInRole('ROLE_ADMIN') || (reading && Principal.equals(reading.createdBy)));
        };
    });
