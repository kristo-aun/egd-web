'use strict';

egdApp
    .controller('RTKController', function ($scope, $state, $stateParams, $log, RTKResource, Principal) {

        $scope.isAdmin = function() {
            return Principal.isInRole('ROLE_ADMIN');
        };

        $scope.clear = function () {
            delete $scope.book;
            delete $scope.query;
            delete $scope.frames;
            delete $scope.error;
        };

        $scope.load = function (book, query) {
            $scope.clear();
            RTKResource.findByBookAndQuery(book, query).then(function(data) {
                $scope.frames = data;
            }, function(e) {
                $scope.error = "global.messages.warn.notfound";
            });
        };

        if ($stateParams.book && $stateParams.query) {
            $scope.load($stateParams.book, $stateParams.query);
            $scope.book = $stateParams.book;
            $scope.query = $stateParams.query;
        }

        $scope.search = function(book) {
            var query = $scope.query;
            if (query) {
                $state.go("rtk.detail", { "book": book, query: query});
            }
        };

        $scope.imageLocation = function(sha) {
            return "/api/media/" + sha;
        };
    });
