'use strict';

egdApp.controller('ArticleController', function ($rootScope, $scope, $log, resolvedArticle, Article, Session) {
    $log.debug("ArticleController");
    $scope.articles = resolvedArticle;

    $scope.create = function () {
        Article.save($scope.article,
            function () {
                $scope.articles = Article.query();
                $('#saveArticleModal').modal('hide');
                $scope.clear();
            });
    };

    $scope.update = function (id) {
        $scope.article = Article.get({id: id});
        $('#saveArticleModal').modal('show');
    };

    $scope.delete = function (id) {
        Article.delete({id: id},
            function () {
                $scope.articles = Article.query();
            });
    };

    $scope.clear = function () {
        $scope.article = {
            author: null,
            copyright: null,
            title: null,
            transcriptLang: null,
            id: null
        };
    };

    $scope.isArticleUpdateAllowed = function(article) {
        return Session.hasRoleAdmin() || article.createdBy == Session.login;
    };
    $scope.isArticleDeleteAllowed = function(article) {
        return Session.hasRoleAdmin() || article.createdBy == Session.login;
    };
});
