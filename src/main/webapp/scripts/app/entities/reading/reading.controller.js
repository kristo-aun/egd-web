'use strict';

egdApp
    .controller('ReadingController', function ($scope, $state, $log, ReadingResource, ParseLinks, Principal) {
        $scope.isAuthenticated = Principal.isAuthenticated;

        $scope.readings = [];

        $scope.page = 1;
        $scope.limit = 20;

        $scope.loadAll = function() {
            ReadingResource.query({page: $scope.page, limit: $scope.limit}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalCount = headers('X-Total-Count');
                $scope.readings = result;
            });
        };
        $scope.loadPage = function(page, limit) {
            $scope.page = page;
            $scope.limit = limit;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.isReadingEditAllowed = function (reading) {
            return $scope.isAuthenticated() && (Principal.isInRole('ROLE_ADMIN') || Principal.equals(reading.createdBy));
        };
    });
