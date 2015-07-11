'use strict';

egdApp
    .factory('TestCompoundResource', function ($http, $log) {
        return {
            params: function () {
                var context = "api/test/compound/params";
                return $http.get(context).then(function (response) {
                    $log.debug("TestCompoundResource.params: response=", response);
                    return response.data;
                });
            },
            formDefault: function (defaultId) {
                var context = "api/test/compound/form-default/" + defaultId;
                return $http.get(context).then(function (response) {
                    $log.debug("TestCompoundResource.formDefault: response=", response);
                    return response.data;
                });
            },
            submit: function (submit) {
                var context = "api/test/compound/submit";
                return $http.post(context, submit).then(function (response) {
                   return response.data;
                });
            }
        }
    })
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
            },
            setDefaultHeisigWord: function (kanji, word, wordReading, wordTranslation) {
                return $http({
                    method: 'POST',
                    url: 'api/rtk/defaultWord',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    data: $.param({
                        kanji: kanji,
                        word: word,
                        wordReading: wordReading,
                        wordTranslation: wordTranslation
                    })
                }).then(function (response) {
                    return response.data;
                });
            }
        };
    })
    .factory('TranslatorResource', function ($http) {
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
    .factory('ReadingResource', function ($resource, $http, Moment) {
        var BASE_URL = 'api/readings';
        var result = $resource(BASE_URL + '/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    if (data.createdDate)
                        data.createdDate = Moment.deserializeDateTime(data.createdDate);
                    if (data.lastModifiedDate)
                        data.lastModifiedDate = Moment.deserializeDateTime(data.lastModifiedDate);
                    return data;
                }
            }
        });
        delete result.save;
        result.save = function (reading, file) {
            var fd = new FormData();
            fd.append('files', file);
            fd.append('json', angular.toJson(reading));
            return $http.post('api/readings', fd, {
                transformRequest: angular.identity,
                headers: {"Content-Type": undefined}
            });
        };
        return result;
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
