'use strict';

egdApp
    .controller('ArticleDetailController', function ($scope, $stateParams, Article) {
        $scope.article = {};
        $scope.load = function (id) {
            Article.get({id: id}, function(result) {
              $scope.article = result;
            });
        };
        $scope.load($stateParams.id);
    });
