'use strict';

egdApp
    .controller('RegisterController', function ($state, $rootScope, $scope, $translate, $timeout, Auth) {
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        $timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});

        $scope.register = function () {
            if ($scope.registerAccount.password !== $scope.confirmPassword) {
                $scope.doNotMatch = true;
            } else {
                $scope.registerAccount.langKey = $translate.use();
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;

                Auth.createAccount($scope.registerAccount).then(function () {
                    Auth.login({
                        username: $scope.registerAccount.login,
                        password: $scope.registerAccount.password,
                        rememberMe: false
                    }).then(function () {
                        $rootScope.$broadcast("accountChange");
                        $state.go('home');
                    }).catch(function () {
                        $scope.error = true;
                    });
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = true;
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = true;
                    } else {
                        $scope.error = true;
                    }
                });
            }
        };
    }).controller('RegisterExternalController', function ($scope, $translate, $timeout, Auth, RegisterExternal) {
        $scope.success = null;
        $scope.error = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        $timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});

        RegisterExternal.get().$promise.then(
            function(data) {
                $scope.registerAccount = data;
            },
            function(httpResponse) {
                // a 404 means that there isn't an ongoing social registration.  this isn't really an error
                if (httpResponse.status != 404) {
                    $scope.error = 'ERROR';
                }
            }
        );

        $scope.register = function () {
            $scope.registerAccount.langKey = $translate.use();
            $scope.error = null;
            $scope.errorUserExists = null;
            $scope.errorEmailExists = null;

            Auth.createAccountFromSocial($scope.registerAccount).then(function () {
                $scope.success = 'OK';
            }).catch(function (response) {
                $scope.success = null;
                if (response.status === 400 && response.data === 'login already in use') {
                    $scope.errorUserExists = 'ERROR';
                } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                    $scope.errorEmailExists = 'ERROR';
                } else {
                    $scope.error = 'ERROR';
                }
            });
        };
    });
