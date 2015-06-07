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
    });
