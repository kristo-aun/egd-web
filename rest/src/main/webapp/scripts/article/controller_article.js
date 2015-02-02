'use strict';

egdApp.controller('ArticlesController', function ($location, $scope, $log, resolvedArticle, ArticleService, Session) {
    $log.debug("ArticlesController");
    $scope.articles = resolvedArticle;

    $scope.update = function (id) {
        $location.path("/article/" + id);
    };

    $scope.delete = function (id) {
        ArticleService.delete({id: id},
            function () {
                $scope.articles = ArticleService.query();
            });
    };

    $scope.isArticleUpdateAllowed = function (article) {
        return Session.hasRoleAdmin() || article.createdBy == Session.login;
    };
    $scope.isArticleDeleteAllowed = function (article) {
        return Session.hasRoleAdmin() || article.createdBy == Session.login;
    };
});

egdApp.controller('ArticleController', function ($routeParams, $location, $scope, $log, ArticleService) {
    $log.debug("ArticleController");

    $scope.article = ArticleService.get({id: $routeParams.id});

    $scope.save = function () {
        ArticleService.save($scope.article,
            function () {
                $log.debug("ArticleController.save");
                $location.path("/article");
            });
    };

    $scope.clear = function () {
        $location.path("/article");
    };
});
