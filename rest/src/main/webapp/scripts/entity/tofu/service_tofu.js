'use strict';

egdApp.factory('TofuService', function ($resource) {
        return $resource('app/rest/tofus', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
