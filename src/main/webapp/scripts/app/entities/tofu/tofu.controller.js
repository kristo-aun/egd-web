'use strict';

egdApp
    .controller('TofuController', function ($scope, $log, TofuService, ParseLinks) {
        $scope.tofus = [];

        $scope.page = 1;
        $scope.limit = 100;

        $scope.loadAll = function() {
            TofuService.query({page: $scope.page, limit: $scope.limit}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $log.debug("TofuController: totalCount=", headers('X-Total-Count'));
                $scope.totalCount = headers('X-Total-Count');
                $scope.tofus = result;
            });
        };
        $scope.loadPage = function(page, limit) {
            $scope.page = page;
            $scope.limit = limit;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.update = function () {
            TofuService.update($scope.tofu,
                function () {
                    $scope.loadAll();
                    $('#saveTofuModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.show = function (id) {
            TofuService.get({id: id}, function(result) {
                $scope.tofu = result;
                $('#saveTofuModal').modal('show');
            });
        };

        $scope.clear = function () {
            $scope.tofu = {word: null, sentence: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
