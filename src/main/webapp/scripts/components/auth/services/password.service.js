'use strict';

egdApp
    .factory('Password', function ($resource) {
        return $resource('api/account/change_password', {}, {
        });
    });
