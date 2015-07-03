'use strict';

egdApp
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    })
    .factory('RegisterExternal', function ($resource) {
        return $resource('api/register/external', {}, {
        });
    });


