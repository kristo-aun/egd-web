'use strict';

egdApp
    .factory('AuditsService', function($http, Moment) {
        return {
            findAll: function() {
                return $http.get('api/audits/all').then(function(response) {
                    return response.data;
                });
            },
            findByDates: function(fromDate, toDate) {
                return $http.get('api/audits/byDates', {
                    params: {
                        fromDate: Moment.serializeDateTime(fromDate),
                        toDate: Moment.serializeDateTime(toDate)
                    }
                }).then(function(response) {
                    return response.data;
                });
            }
        };
    });
