'use strict';

egdApp
    .factory('Auth', function Auth($log, $rootScope, $state, $q, $translate, Principal, AuthServerProvider, AccountResource) {
        return {
            deleteAccount: function() {
                var deferred = $q.defer();
                var that = this;
                AccountResource.delete({},
                    function() {
                        that.logout();
                        deferred.resolve();
                    });
                return deferred.promise;
            },
            login: function(credentials, callback) {
                var cb = callback || angular.noop;
                var deferred = $q.defer();

                AuthServerProvider.login(credentials).then(function(data) {
                    // retrieve the logged account information
                    Principal.identity(true).then(function(account) {

                        // After the login the language will be changed to
                        // the language selected by the user during his registration
                        $translate.use(account.langKey);
                        $translate.refresh();
                        deferred.resolve(data);
                    });
                    return cb();
                }).catch(function(err) {
                    this.logout();
                    deferred.reject(err);
                    return cb(err);
                }.bind(this));

                return deferred.promise;
            },

            logout: function() {
                AuthServerProvider.logout();
                Principal.authenticate(null);
                // Reset state memory
                $rootScope.previousStateName = undefined;
                $rootScope.previousStateNameParams = undefined;
            },

            authorize: function(force) {
                return Principal.identity(force)
                    .then(function() {
                        var isAuthenticated = Principal.isAuthenticated();

                        // an authenticated user can't access to login and register pages
                        if (isAuthenticated && $rootScope.toState.parent === 'account' && ($rootScope.toState.name === 'login' || $rootScope.toState.name === 'register')) {
                            $state.go('home');
                        }

                        if ($rootScope.toState.data.authorities && $rootScope.toState.data.authorities.length > 0 && !Principal.hasAnyAuthority($rootScope.toState.data.authorities)) {
                            if (isAuthenticated) {
                                // user is signed in but not authorized for desired state
                                $state.go("error", {code: '403'});
                            }
                            else {
                                // user is not authenticated. stow the state they wanted before you
                                // send them to the signin state, so you can return them when you're done
                                $rootScope.previousStateName = $rootScope.toState;
                                $rootScope.previousStateNameParams = $rootScope.toStateParams;

                                // now, send them to the signin state so they can log in
                                $state.go('login');
                            }
                        }
                    });
            }
        };
    });
