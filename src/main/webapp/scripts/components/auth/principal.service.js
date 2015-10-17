'use strict';

egdApp
    .factory('Principal', function Principal($q, AccountResource) {
        var _identity,
            _authenticated = false;

        return {
            equals: function(uuid) {
                if (!_authenticated || !_identity || !_identity.uuid) {
                    //$log.debug("Principal.equals", uuid, false);
                    return false;
                }
                return _identity.uuid === uuid;
            },
            isIdentityResolved: function() {
                return angular.isDefined(_identity);
            },
            isAuthenticated: function() {
                return _authenticated;
            },
            hasAuthority: function(authority) {
                if (!_authenticated) {
                    return $q.when(false);
                }

                return this.identity().then(function(_id) {
                    return _id.authorities && _id.authorities.indexOf(authority) !== -1;
                }, function(err) {
                    return false;
                });
            },
            hasAnyAuthority: function(authorities) {
                if (!_authenticated || !_identity || !_identity.authorities) {
                    return false;
                }

                for (var i = 0; i < authorities.length; i++) {
                    if (_identity.authorities.indexOf(authorities[i]) !== -1) {
                        return true;
                    }
                }

                return false;
            },
            authenticate: function(identity) {
                _identity = identity;
                _authenticated = identity !== null;
            },
            identity: function(force) {
                var deferred = $q.defer();

                if (force === true) {
                    _identity = undefined;
                }

                // check and see if we have retrieved the identity data from the server.
                // if we have, reuse it by immediately resolving
                if (angular.isDefined(_identity)) {
                    deferred.resolve(_identity);

                    return deferred.promise;
                }

                // retrieve the identity data from the server, update the identity object, and then resolve.
                AccountResource.get().$promise
                    .then(function(account) {
                        _identity = account;
                        _authenticated = true;
                        deferred.resolve(_identity);
                    })
                    .catch(function() {
                        _identity = null;
                        _authenticated = false;
                        deferred.resolve(_identity);
                    });
                return deferred.promise;
            }
        };
    });
