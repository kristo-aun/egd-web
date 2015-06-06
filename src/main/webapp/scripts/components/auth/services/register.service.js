'use strict';

egdApp
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


