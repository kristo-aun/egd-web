'use strict';

egdApp.factory('Entr', function ($resource) {
        return $resource('app/rest/entrs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
