'use strict';

egdApp
    .factory('AccountResource', function($resource, $http) {
        var BASE_URL = 'api/account';
        var result = $resource(BASE_URL);
        delete result.save;

        var post = function(url, data) {
            return $http.post(url, data, {
                headers: {'Content-Type': 'application/json'},
                transformRequest: function(data) {
                    return angular.toJson(data);
                },
                transformResponse: function(response) {
                    return response;
                }
            });
        };

        result.save = function(account) {
            return post("api/account", account);
        };

        result.register = function(account) {
            return post("api/register", account);
        };

        result.activate = function(key) {
            var context = "api/activate?key=" + key;
            return $http.get(context).then(function(response) {
                return response.data;
            });
        };

        result.changePassword = function(password) {
            return post("api/account/change_password", {password: password});
        };

        result.resetPasswordInit = function(mail) {
            return post("api/account/reset_password/init", {mail: mail});
        };

        result.resetPasswordFinish = function(key, password) {
            return post("api/account/reset_password/finish", {key: key, password: password});
        };

        return result;
    });
