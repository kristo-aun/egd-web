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
    .factory('TranslatorResource', function ($http, $log) {
        return {
            translate: function (from, to, string) {
                var context = "api/translator/" + from + "/" + to + "/" + string;
                return $http.get(context).then(function (response) {
                    return response.data;
                });
            }
        }
    })
    .factory('DictResource', function ($http, $log) {
        return {
            autocomplete: function (phrasepart) {
                var context = "api/dict/autocomplete/" + phrasepart;
                return $http.get(context).then(function (response) {
                    $log.debug("DictResource.autocomplete: response=", response);
                    return response.data;
                });
            },
            jmtrans: function (phrase) {
                var context = "api/dict/jmtrans/" + phrase;
                return $http.get(context).then(function (response) {
                    $log.debug("DictResource.jmtrans: response=", response);
                    return response.data;
                });
            }
        }
    })
    .factory('ArticleResource', function ($resource) {
        return $resource('api/articles/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    })
    .factory('TofuResource', function ($resource) {
        return $resource('api/tofus/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
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
