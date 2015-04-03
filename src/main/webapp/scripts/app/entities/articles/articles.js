'use strict';

egdApp
    .config(function ($stateProvider) {
        $stateProvider
            .state('articles', {
                parent: 'entities',
                url: '/article',
                data: {
                    roles: [],
                    pageTitle: 'articles.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/articles/articles.html',
                        controller: 'ArticlesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('articles');
                        return $translate.refresh();
                    }]
                }
            }).state('article', {
                parent: 'entities',
                url: '/article/:id',
                data: {
                    roles: [],
                    pageTitle: 'articles.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/articles/article.html',
                        controller: 'ArticleController'
                    }
                },
                resolve:{
                    resolvedArticle: ['ArticleService', function (ArticleService) {
                        return ArticleService.query().$promise;
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('articles');
                        return $translate.refresh();
                    }]
                }
            });
    });
