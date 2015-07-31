'use strict';

egdApp
    .controller('TofuController', function ($q, $scope, $log, $translate, TofuResource, ParseLinks, TranslatorResource) {
        $scope.tofus = [];

        $scope.page = 1;
        $scope.limit = 100;

        $scope.loadAll = function() {
            TofuResource.query({page: $scope.page, limit: $scope.limit}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
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

        $scope.saveTranslation = function () {
            var deferred = $q.defer();

            $scope.tofu.translation.lang = $translate.use();
            $scope.tofu.translation.tofuSentenceId = $scope.tofu.id;

            TofuResource.save($scope.tofu.translation,
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
                if (!$scope.tofu.translation) {
                    TranslatorResource.translate("ja", "en", $scope.tofu.sentence).then(function(response) {
                        $scope.tofu.sentenceHint = response.data;
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
            $scope.saveTranslation().then(function() {
                $scope.nextTofu();
            });
        };

        $scope.clearForm = function () {
            $scope.form.$setPristine();
            $scope.form.$setUntouched();
        };

        $scope.cancel = function () {
            $scope.clearForm();
            delete $scope.tofu;
            $scope.loadAll();
        };

        $scope.getExcelURL = function() {
            return "api/report/tofu_translations.csv";
        }
    });
