'use strict';

egdApp
    .controller('ReadingController', function ($scope, $state, $log, ReadingResource, ParseLinks, Principal) {
        $scope.readings = [];

        $scope.page = 1;
        $scope.limit = 20;

        $scope.loadAll = function() {
            ReadingResource.query({page: $scope.page, limit: $scope.limit}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalCount = headers('X-Total-Count');
                $log.debug($scope.links);
                $scope.readings = result;
            });
        };
        $scope.loadPage = function(page, limit) {
            $scope.page = page;
            $scope.limit = limit;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.create = function() {
            $state.go("reading.detail", {id: -1});
        };

        $scope.open = function (id) {
            $state.go("reading.detail", {id: id});
        };

        $scope.delete = function (id) {
            ReadingResource.delete({id: id},
                function () {
                    $scope.loadAll();
                });
        };

        $scope.isReadingUpdateAllowed = function (reading) {
            return Principal.isAuthenticated() && (Principal.isInRoleAdmin() || Principal.equals(reading.createdBy));
        };
        $scope.isReadingDeleteAllowed = function (reading) {
            return Principal.isAuthenticated() && (Principal.isInRoleAdmin() || Principal.equals(reading.createdBy));
        };
        $scope.isAnonymous = function () {
            return !Principal.isAuthenticated();
        };
    });
