'use strict';

egdApp
    .controller('ReadingController', function ($scope, $state, $log, ReadingResource, ParseLinks, Principal) {

        $scope.readingRows = [];

        $scope.page = 1;
        $scope.limit = 20;

        var modReadings = function(readings) {
            var result = [];
            var row = [];
            angular.forEach(readings, function(value, key) {
                if (key % 3 == 0) {
                    this.push(row);
                    row = [value];
                } else {
                    row.push(value);
                }
            }, result);
            if (row.length > 0) result.push(row);
            return result;
        };

        $scope.loadAll = function() {
            ReadingResource.query({}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalCount = headers('X-Total-Count');
                $scope.readingRows = modReadings(result);
            });
        };
        $scope.loadPage = function(page, limit) {
            $scope.page = page;
            $scope.limit = limit;
            $scope.loadAll();
        };
        $scope.loadAll();

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
