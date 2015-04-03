egdApp.factory('HttpErrorInterceptor', function ($q, $rootScope, $log) {
    return {
        request: function (config) {
            return config || $q.when(config);
        },
        requestError: function (request) {
            return $q.reject(request);
        },
        response: function (response) {
            return response || $q.when(response);
        },
        responseError: function (response) {
            var msg = "";

            if (response && response.status === 401) {
                return $q.reject(response);
            } else if (response && response.status === 404) {
                msg += "Not found";
            } else if (response && response.status >= 500) {
                if (response.data.message) {
                    msg += response.data.message;
                } else {
                    msg += "Viga: serveri vastus=" + response.data;
                }
            } else {
                msg += "response=" + response;
            }

            $log.error("HttpErrorInterceptor: msg=", msg);
            $rootScope.$broadcast('error', msg);
            return $q.reject(response);
        }
    };
});
