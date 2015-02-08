'use strict';

egdApp.factory('DictService', function ($http, $log) {
    return {
        autocomplete: function (phrasepart) {
            var context = "app/rest/dict/autocomplete/" + phrasepart;
            return $http.get(context).then(function (response) {
                $log.debug("DictService.autocomplete: response=", response);
                return response.data;
            });
        },
        jmtrans: function (phrase) {
            var context = "app/rest/dict/jmtrans/" + phrase;
            return $http.get(context).then(function (response) {
                $log.debug("DictService.jmtrans: response=", response);
                return response.data;
            });
        }
    }
});
