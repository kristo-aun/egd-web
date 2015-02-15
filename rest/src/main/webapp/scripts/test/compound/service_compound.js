'use strict';

egdApp.factory('TestCompoundService', function ($http, $log) {
    return {
        params: function () {
            var context = "app/rest/test/compound/params";
            return $http.get(context).then(function (response) {
                $log.debug("TestCompoundService.params: response=", response);
                return response.data;
            });
        },
        formDefault: function (defaultId) {
            var context = "app/rest/test/compound/form-default/" + defaultId;
            return $http.get(context).then(function (response) {
                $log.debug("TestCompoundService.formDefault: response=", response);
                return response.data;
            });
        }
    }
});
