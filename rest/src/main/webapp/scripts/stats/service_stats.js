'use strict';

egdApp.factory('StatsService', function ($http, $log) {
    return {
        counters: function () {
            var context = "app/rest/stats/counters";
            return $http.get(context).then(function (response) {
                $log.debug("StatsService.counters: response=", response);
                return response.data;
            });
        },
        coreStats: function () {
            var context = "app/rest/stats/core_stats";
            return $http.get(context).then(function (response) {
                $log.debug("StatsService.coreStats: response=", response);
                return response.data;
            });
        }
    }
});
