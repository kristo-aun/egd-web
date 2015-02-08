'use strict';

egdApp.controller('ArticlesController', function ($location, $scope, $log, resolvedArticle, ArticleService, Session) {
    $log.debug("ArticlesController");
    $scope.articles = resolvedArticle;

    $scope.create = function() {
        $location.path("/article/-1");
    };

    $scope.open = function (id) {
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
    $scope.isAnonymous = function () {
        return Session.login ? false : true;
    };
});

egdApp.controller('ArticleController', function ($rootScope, $routeParams, $location, $scope, $log, $timeout, $interval, ArticleService, ngAudio, Session) {
    $log.debug("ArticleController");

    $scope.isAnonymous = function () {
        return Session.login ? false : true;
    };

    //------------------------------ grid options ------------------------------

    var start = new Date();
    var sec = $interval(function () {
        var wait = parseInt(((new Date()) - start) / 1000, 10);
        $scope.wait = wait + 's';
    }, 1000);

    var rowTemplate = function() {
        return $timeout(function() {
            $scope.waiting = 'Done!';
            $interval.cancel(sec);
            $scope.wait = 'waiting';
            return '<div ng-repeat="col in colContainer.renderedColumns track by col.colDef.name" class="ui-grid-cell" ui-grid-cell></div>';
        }, 0);
    };

    $scope.waiting = 'Waiting for row template...';

    $scope.gridOptions = {
        rowTemplate: rowTemplate(),
        showHeader: false,
        enableHorizontalScrollbar: 0,
        enableVerticalScrollbar: 0,
        enableCellEditOnFocus: true
    };

    $scope.gridOptions.columnDefs = [
        { name: 'txt', displayName: 'Jaapani keeles', width: '50%', enableCellEdit: true },
        { name: 'transcript', displayName: 'Inglise keeles' , width: '50%' }
    ];

    //------------------------------ get article from xhr ------------------------------

    var id = $routeParams.id;
    if (id > 0) {
        ArticleService.get({id: id}, function(article) {
            $log.debug("ArticleController.get: article=", article);
            $rootScope.page.setTitle(article.title);
            $scope.article = article;
            $scope.gridOptions.data = $scope.article.articleParagraphs;

            $.each($scope.article.articleParagraphs , function(key, value) {
                if (value.audio) {
                    value.audio.ngAudio = ngAudio.load('app/rest/audio/' + value.audio.id);
                }
            });
        });

    } else {
        $scope.article = {
            author: null,
            copyright: null,
            title: null,
            transcriptLang: "en",
            id: null
        };
    }

    //------------------------------ scope helpers ------------------------------

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
