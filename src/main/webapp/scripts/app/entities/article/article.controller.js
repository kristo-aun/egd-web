'use strict';

egdApp
    .controller('ArticleController', function ($scope, Article, ParseLinks) {
        $scope.articles = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Article.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.articles = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Article.update($scope.article,
                function () {
                    $scope.loadAll();
                    $('#saveArticleModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Article.get({id: id}, function(result) {
                $scope.article = result;
                $('#saveArticleModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Article.get({id: id}, function(result) {
                $scope.article = result;
                $('#deleteArticleConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Article.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteArticleConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.article = {title: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
