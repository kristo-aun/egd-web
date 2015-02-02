'use strict';

egdApp.controller('ArticlesController', function ($location, $scope, $log, resolvedArticle, ArticleService, Session) {
    $log.debug("ArticlesController");
    $scope.articles = resolvedArticle;

    $scope.create = function() {
        $location.path("/article/-1");
    };

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

    var id = $routeParams.id;
    if (id > 0) {
        $scope.article = ArticleService.get({id: id});
    } else {
        $scope.article = {
            author: null,
            copyright: null,
            title: null,
            transcriptLang: "en",
            id: null
        };
    }

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
