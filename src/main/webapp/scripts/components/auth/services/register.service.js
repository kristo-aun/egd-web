'use strict';

angular.module('egdApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


