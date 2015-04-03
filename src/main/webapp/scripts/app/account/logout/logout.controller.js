'use strict';

egdApp
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
