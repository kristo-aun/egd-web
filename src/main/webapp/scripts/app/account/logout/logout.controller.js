'use strict';

egdApp
    .controller('LogoutController', function ($state, Auth) {
        Auth.logout();
        $state.forceReload();
    });
