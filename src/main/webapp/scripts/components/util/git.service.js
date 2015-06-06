egdApp.factory('GitService', function ($resource) {
    return $resource('api/pub/git', {}, {
        'get': {method: 'GET', params: {}, isArray: false}
    });
});
