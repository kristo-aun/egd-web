'use strict';

egdApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/article', {
                    templateUrl: 'views/articles.html',
                    controller: 'ArticlesController',
                    resolve:{
                        resolvedArticle: ['ArticleService', function (ArticleService) {
                            return ArticleService.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
                .when('/article/:id', {
                    templateUrl: 'views/article.html',
                    controller: 'ArticleController',
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
