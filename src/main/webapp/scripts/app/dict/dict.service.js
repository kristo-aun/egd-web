'use strict';

egdApp.factory('DictService', function ($http, $log) {
    return {
        autocomplete: function (phrasepart) {
            var context = "api/pub/dict/autocomplete/" + phrasepart;
            return $http.get(context).then(function (response) {
                $log.debug("DictService.autocomplete: response=", response);
                return response.data;
            });
        },
        jmtrans: function (phrase) {
            var context = "api/pub/dict/jmtrans/" + phrase;
            return $http.get(context).then(function (response) {
                $log.debug("DictService.jmtrans: response=", response);
                return response.data;
            });
        }
    }
});
