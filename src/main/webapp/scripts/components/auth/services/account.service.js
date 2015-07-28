'use strict';

egdApp
    .factory('Account', function Account($resource) {
        return $resource('api/account', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            },
            'save': {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                transformRequest: function (data) {
                    return angular.toJson(data);
                }
            }
        });
    });
