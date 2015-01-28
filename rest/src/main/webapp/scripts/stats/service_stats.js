'use strict';

egdApp.factory('StatsService', function ($http, $log) {
    return {
        counters: function () {
            var context = "app/rest/stats/counters";
            return $http.get(context).then(function (response) {
                return response.data;
            });
        },
        coreStats: function () {
            var context = "app/rest/stats/core_stats";
            return $http.get(context).then(function (response) {
                return response.data;
            });
        },
        translatedEntrRatio: function () {
            var context = "app/rest/stats/translated_entr_ratio";
            return $http.get(context).then(function (response) {
                return response.data;
            });
        },
        countGlossToSumFreq: function () {
            var context = "app/rest/stats/count_gloss_to_sum_freq";
            return $http.get(context).then(function (response) {
                return response.data;
            });
        }
    }
});
