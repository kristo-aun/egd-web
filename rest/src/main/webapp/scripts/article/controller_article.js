'use strict';

egdApp.controller('ArticleController', function ($rootScope, $scope, $log, resolvedArticle, Article, Session, $translate) {
    $log.debug("ArticleController");
    $scope.articles = resolvedArticle;

    $scope.transcriptLangs = [];

    $translate("language.et").then(function(translation) {
        $scope.transcriptLangs.push({id:"et", value: translation});
    });
    $translate("language.en").then(function(translation) {
        $scope.transcriptLangs.push({id:"en", value: translation});
    });

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
        $log.debug();
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

    $scope.isArticleUpdateAllowed = function (article) {
        return Session.hasRoleAdmin() || article.createdBy == Session.login;
    };
    $scope.isArticleDeleteAllowed = function (article) {
        return Session.hasRoleAdmin() || article.createdBy == Session.login;
    };


});
