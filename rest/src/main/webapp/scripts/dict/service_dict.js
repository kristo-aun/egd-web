'use strict';

egdApp.factory('DictService', function ($http, $log) {
    return {
        autocomplete: function (lang, q) {
            var context = "app/dict/autocomplete?lang=" + lang + "&q=" + q;
            return $http.get(context).then(function (response) {
                $log.debug("DictService.autocomplete: response=", response);
                return response;
            });
        },
        japest: function (lang, q) {
            var context = "app/dict/japest?lang=" + lang + "&q=" + q;
            return $http.get(context).then(function (response) {
                $log.debug("DictService.japest: response=", response);
                return response;
            });
        },
        estjap: function (lang, q) {
            var context = "app/dict/estjap?lang=" + lang + "&q=" + q;
            return $http.get(context).then(function (response) {
                $log.debug("DictService.estjap: response=", response);
                return response;
            });
        }
    }
});

