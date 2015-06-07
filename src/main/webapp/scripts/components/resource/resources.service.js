'use strict';

egdApp
    .factory('RTKResource', function ($http) {//heisig
        return {
            findByBookAndQuery: function (book, query) {
                return $http.get('api/rtk/byBookAndQuery', {
                    params: {
                        book: book,
                        query: query
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            findAll: function () {
                return $http.get('api/rtk').then(function (response) {
                    return response.data;
                });
            },
            findById: function (frameId) {
                return $http.get('api/rtk/' + frameId).then(function (response) {
                    return response.data;
                });
            }
        };
    })
    .factory('StatResource', function ($http) {
        return {
            counters: function () {
                var context = "api/stats/counters";
                return $http.get(context).then(function (response) {
                    return response.data;
                });
            },
            coreStats: function () {
                var context = "api/stats/core_stats";
                return $http.get(context).then(function (response) {
                    return response.data;
                });
            },
            translatedEntrRatio: function () {
                var context = "api/stats/translated_entr_ratio";
                return $http.get(context).then(function (response) {
                    return response.data;
                });
            },
            countGlossToSumFreq: function () {
                var context = "api/stats/count_gloss_to_sum_freq";
                return $http.get(context).then(function (response) {
                    return response.data;
                });
            }
        };
    });
