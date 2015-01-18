'use strict';

egdApp.factory('Audio', function ($resource) {
        return $resource('app/rest/audios/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
