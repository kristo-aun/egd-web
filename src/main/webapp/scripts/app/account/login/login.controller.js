'use strict';

angular.module('egdApp')
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout, $translate, Auth) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = true;
        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function () {
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                if ($rootScope.previousStateName === 'register') {
                    $state.go('home');
                } else {
                    $rootScope.back();
                }
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };

        $scope.idlogin = function () {
            var isolang = "eng";
            if ($translate.use() === 'et') {
                isolang = "est";
            }

            loadSigningPlugin(isolang);
            var plugin =  new IdCardPluginHandler(isolang);
            plugin.getCertificate(function(certificate) {
                Auth.idlogin({
                    certificate: certificate,
                    rememberMe: $scope.rememberMe
                }).then(function () {
                    $scope.authenticationError = false;
                    if ($rootScope.previousStateName === 'register') {
                        $state.go('home');
                    } else {
                        $rootScope.back();
                    }
                }).catch(function () {
                    $scope.authenticationError = true;
                });
            }, function(e) {
                $log.error("LoginController.idlogin.error: e=", e);
                if (e instanceof IdCardException) {
                    var msg = '[Veakood: ' + e.returnCode + '; Viga: ' + e.message + ']';
                    $rootScope.$broadcast('error', msg);
                } else {
                    var msg = e.message != undefined ? e.message : e;
                    $rootScope.$broadcast('error', msg);
                }
            });
        };
    });
