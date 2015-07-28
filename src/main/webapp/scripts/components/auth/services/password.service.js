'use strict';

egdApp
    .factory('Password', function ($resource) {
        return $resource('api/account/change_password', {}, {});
    });

egdApp
    .factory('PasswordResetInit', function ($resource) {
        return $resource('api/account/reset_password/init', {}, {})
    });

egdApp
    .factory('PasswordResetFinish', function ($resource) {
        return $resource('api/account/reset_password/finish', {}, {})
    });
