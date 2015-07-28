'use strict';

egdApp
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
            'save': {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                transformRequest: function (data) {
                    return angular.toJson(data);
                }
            }
        });
    });


