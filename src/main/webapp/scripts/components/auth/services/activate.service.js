'use strict';

egdApp
    .factory('Activate', function ($resource) {
        return $resource('api/pub/activate', {}, {
            'get': { method: 'GET', params: {}, isArray: false}
        });
    });


