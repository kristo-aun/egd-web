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
        japest: function (phrase) {
            var context = "app/rest/dict/japest/" + phrase;
            return $http.get(context).then(function (response) {
                $log.debug("DictService.japest: response=", response);
                return response.data;
            });
        },
        estjap: function (phrase) {
            var context = "app/rest/dict/estjap/" + phrase;
            return $http.get(context).then(function (response) {
                $log.debug("DictService.estjap: response=", response);
                return response.data;
            });
        }
    }
});
