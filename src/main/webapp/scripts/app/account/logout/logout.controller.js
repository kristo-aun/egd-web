'use strict';

angular.module('egdApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
