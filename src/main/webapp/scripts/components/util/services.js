'use strict';

egdApp
    .factory('GitService', function ($resource) {
        return $resource('api/git', {}, {
            'get': {method: 'GET', params: {}, isArray: false}
        });
    })
    .factory('TranslatorService', function ($http, $log) {
        return {
            translate: function (from, to, string) {
                var context = "api/translator/" + from + "/" + to + "/" + string;
                return $http.get(context).then(function (response) {
                    $log.debug("TranslatorService.translate: response=", response);
                    return response.data;
                });
            }
        }
    });
