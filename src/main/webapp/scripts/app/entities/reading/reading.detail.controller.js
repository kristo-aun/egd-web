'use strict';

egdApp
    .controller('ReadingDetailController', function ($window, $rootScope, $scope, $stateParams, $interval, $timeout, $log, Principal, ReadingResource) {
        $scope.reading = {};
        $scope.load = function (id) {

            if (id > 0) {
                ReadingResource.get({id: id}, function(reading) {
                    $scope.reading = reading;
                    $window.document.title = reading.title;
                    $scope.gridOptions.data = reading.readingParagraphs;
                });
            } else {
                $scope.reading = {
                    author: null,
                    copyright: null,
                    title: null,
                    transcriptLang: "en",
                    id: null
                };
                $window.document.title = "New reading";
            }

        };
        $scope.load($stateParams.id);

        $scope.isAnonymous = function () {
            return !Principal.isAuthenticated();
        };

        //------------------------------ grid options ------------------------------

        var start = new Date();
        var sec = $interval(function () {
            var wait = parseInt(((new Date()) - start) / 1000, 10);
            $scope.wait = wait + 's';
        }, 1000);

        var rowTemplate = function() {
            return $timeout(function() {
                $scope.waiting = 'Done!';
                $interval.cancel(sec);
                $scope.wait = 'waiting';
                return '<div ng-repeat="col in colContainer.renderedColumns track by col.colDef.name" class="ui-grid-cell" ui-grid-cell></div>';
            }, 0);
        };

        $scope.waiting = 'Waiting for row template...';

        $scope.gridOptions = {
            rowTemplate: rowTemplate(),
            showHeader: false,
            enableHorizontalScrollbar: 0,
            enableVerticalScrollbar: 0,
            enableCellEditOnFocus: true
        };

        $scope.gridOptions.columnDefs = [
            { name: 'txt', displayName: 'Jaapani keeles', width: '50%', enableCellEdit: true },
            { name: 'transcript', displayName: 'Inglise keeles' , width: '50%' }
        ];

        //------------------------------ get reading from xhr ------------------------------

        $scope.getAudioResource = function(audioId) {
            return 'api/audio/' + audioId;
        };

        //------------------------------ scope helpers ------------------------------

        $scope.save = function () {
            ReadingService.save($scope.reading,
                function () {
                    $log.debug("ReadingController.save");
                    $location.path("/reading");
                });
        };

        $scope.clear = function () {
            $location.path("/reading");
        };
    });
