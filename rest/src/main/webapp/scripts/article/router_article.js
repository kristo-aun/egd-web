'use strict';

egdApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/article', {
                    templateUrl: 'views/articles.html',
                    controller: 'ArticleController',
                    resolve:{
                        resolvedArticle: ['Article', function (Article) {
                            return Article.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
