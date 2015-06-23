'use strict';

egdApp
    .controller('TofuController', function ($q, $scope, $log, TofuResource, ParseLinks, TranslatorResource) {
        $scope.tofus = [];

        $scope.page = 1;
        $scope.limit = 100;

        $scope.loadAll = function() {
            TofuResource.query({page: $scope.page, limit: $scope.limit}, function(result, headers) {
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

        //------------------------------ tofu dialoog ------------------------------

        $scope.saveTofu = function () {
            var deferred = $q.defer();
            TofuResource.update($scope.tofu,
                function () {
                    deferred.resolve();
                });
            return deferred.promise;
        };

        $scope.loadTofu = function(id) {
            var deferred = $q.defer();
            TofuResource.get({id: id}, function(result) {
                $scope.tofu = result;
                deferred.resolve();
                if ($scope.tofu.translation == null) {
                    TranslatorResource.translate("ja", "en", $scope.tofu.sentence).then(function(result) {
                        $scope.tofu.sentenceHint = result;
                    });
                }
            });
            return deferred.promise;
        };

        $scope.show = function (id) {
            $scope.loadTofu(id).then(function() {
                $('#saveTofuModal').modal('show');
            });
        };

        $scope.nextTofu = function() {
            $scope.clearForm();
            $scope.loadTofu($scope.tofu.id + 1);
        };

        $scope.prevTofu = function() {
            $scope.clearForm();
            $scope.loadTofu($scope.tofu.id - 1);
        };

        $scope.submitTofu = function() {
            $scope.saveTofu().then(function() {
                $scope.nextTofu();
            });
        };

        $scope.clearForm = function () {
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.cancel = function () {
            $scope.clearForm();
            delete $scope.tofu;
            $scope.loadAll();
        };
    });
