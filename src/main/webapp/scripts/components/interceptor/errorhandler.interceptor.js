'use strict';

egdApp
    .factory('errorHandlerInterceptor', function ($q, $rootScope) {
        return {
            'responseError': function (response) {
                if (response.status != 404 &&
                    !(response.status == 403 && response.data.path.indexOf("/api/account") == 0) &&
                    !(response.status == 401 && response.data.path.indexOf("/api/") == 0)) {
                    $rootScope.$emit('egdApp.httpError', response);
                }
                return $q.reject(response);
            }
        };
    });
