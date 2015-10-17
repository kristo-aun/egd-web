'use strict';

egdApp
    .factory('MonitoringService', function($rootScope, $http) {
        return {
            checkHealth: function() {
                return $http.get('health').then(function(response) {
                    return response.data;
                });
            }
        };
    });
