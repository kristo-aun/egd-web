'use strict';

egdApp
    .controller('ArticleController', function ($scope, $log, Article, ParseLinks, Principal) {
        $scope.articles = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Article.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $log.debug($scope.links);
                $scope.articles = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function() {
            $location.path("/article/-1");
        };

        $scope.open = function (id) {
            $location.path("/article/" + id);
        };

        $scope.delete = function (id) {
            Article.delete({id: id},
                function () {
                    $scope.loadAll();
                });
        };

        $scope.isArticleUpdateAllowed = function (article) {
            return Principal.isInRoleAdmin() || Principal.equals(article.createdBy);
        };
        $scope.isArticleDeleteAllowed = function (article) {
            return Principal.isInRoleAdmin() || Principal.equals(article.createdBy);
        };
        $scope.isAnonymous = function () {
            return Principal.isAuthenticated();
        };
    });
