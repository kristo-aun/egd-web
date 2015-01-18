'use strict';

egdApp.factory('Article', function ($resource) {
        return $resource('app/rest/articles/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
