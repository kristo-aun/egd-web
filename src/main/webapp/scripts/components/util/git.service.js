angular.module('egdApp').factory('GitService', function ($resource) {
    return $resource('api/git', {}, {
        'get': {method: 'GET', params: {}, isArray: false}
    });
});
