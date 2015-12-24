'use strict';

egdApp
    .factory('errorHandlerInterceptor', function($q, $rootScope) {
        return {
            'responseError': function(response) {
                if (!(response.status == 401 &&
                    response.data.path.indexOf("/api/account") < 1 &&
                    response.data.path.indexOf("/api/authentication")  < 1)) {

                    $rootScope.$emit('egdApp.httpError', response);
                }
                return $q.reject(response);
            }
        };
    });
