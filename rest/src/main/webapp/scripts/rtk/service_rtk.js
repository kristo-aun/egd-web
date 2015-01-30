'use strict';

egdApp.factory('RTKService', function ($http, $log) {
    return {
        search: function (book, query) {
            var context = "app/rest/rtk/search/" + book + "/" + query;
            return $http.get(context).then(function (response) {
                $log.debug("RTKService.search: response=", response);
                return response.data;
            });
        }
    }
});
