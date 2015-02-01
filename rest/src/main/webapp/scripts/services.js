'use strict';

/* Services */

egdApp.factory('GitService', function ($resource) {
    return $resource('app/rest/git', {}, {
        'get': {method: 'GET', params: {}, isArray: false}
    });
});

egdApp.factory('HttpErrorInterceptor', function ($q, $rootScope, $log, $translate) {
    return {
        request: function (config) {
            return config || $q.when(config);
        },
        requestError: function (request) {
            return $q.reject(request);
        },
        response: function (response) {
            return response || $q.when(response);
        },
        responseError: function (response) {
            var msg = "";

            if (response && response.status === 401) {
                return $q.reject(response);
            } else if (response && response.status === 404) {
                msg += "Puudub ühendus";
            } else if (response && response.status >= 500) {
                if (response.data.message) {
                    msg += response.data.message;
                } else {
                    msg += "Viga: serveri vastus=" + response.data;
                }
            } else {
                msg += "response=" + response;
            }

            $log.error("HttpErrorInterceptor: msg=", msg);
            $rootScope.$broadcast('error', msg);
            return $q.reject(response);
        }
    };
});

egdApp.factory('LanguageService', function ($http, $translate, LANGUAGES) {
    return {
        getBy: function (language) {
            if (language == undefined) {
                language = $translate.storage().get('NG_TRANSLATE_LANG_KEY');
            }
            if (language == undefined) {
                language = 'en';
            }

            var promise = $http.get('i18n/' + language + '.json').then(function (response) {
                return LANGUAGES;
            });
            return promise;
        }
    };
});

egdApp.factory('Register', function ($resource) {
    return $resource('app/rest/register', {}, {});
});

egdApp.factory('Activate', function ($resource) {
    return $resource('app/rest/activate', {}, {
        'get': {method: 'GET', params: {}, isArray: false}
    });
});

egdApp.factory('Account', function ($resource) {
    return $resource('app/rest/account', {}, {});
});

egdApp.factory('Password', function ($resource) {
    return $resource('app/rest/account/change_password', {}, {});
});

egdApp.factory('Sessions', function ($resource) {
    return $resource('app/rest/account/sessions/:series', {}, {
        'get': {method: 'GET', isArray: true}
    });
});

egdApp.factory('MetricsService', function ($http) {
    return {
        get: function () {
            var promise = $http.get('metrics/metrics').then(function (response) {
                return response.data;
            });
            return promise;
        }
    };
});

egdApp.factory('ThreadDumpService', function ($http) {
    return {
        dump: function () {
            var promise = $http.get('dump').then(function (response) {
                return response.data;
            });
            return promise;
        }
    };
});

egdApp.factory('HealthCheckService', function ($rootScope, $http) {
    return {
        check: function () {
            var promise = $http.get('health').then(function (response) {
                return response.data;
            });
            return promise;
        }
    };
});

egdApp.factory('ConfigurationService', function ($rootScope, $filter, $http) {
    return {
        get: function () {
            var promise = $http.get('configprops').then(function (response) {
                var properties = [];
                angular.forEach(response.data, function (data) {
                    properties.push(data);
                });
                var orderBy = $filter('orderBy');
                return orderBy(properties, 'prefix');
            });
            return promise;
        }
    };
});

egdApp.factory('LogsService', function ($resource) {
    return $resource('app/rest/logs', {}, {
        'findAll': {method: 'GET', isArray: true},
        'changeLevel': {method: 'PUT'}
    });
});

egdApp.factory('AuditsService', function ($http) {
    return {
        findAll: function () {
            var promise = $http.get('app/rest/audits/all').then(function (response) {
                return response.data;
            });
            return promise;
        },
        findByDates: function (fromDate, toDate) {
            var promise = $http.get('app/rest/audits/byDates', {
                params: {
                    fromDate: fromDate,
                    toDate: toDate
                }
            }).then(function (response) {
                return response.data;
            });
            return promise;
        }
    }
});

egdApp.factory('Session', function (USER_ROLES) {
    this.create = function (login, firstName, lastName, email, userRoles) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRoles = userRoles;
    };
    this.invalidate = function () {
        this.login = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.userRoles = null;
    };
    this.hasRole = function(authorizedRole) {
        return this.userRoles && this.userRoles.indexOf(authorizedRole) !== -1;
    };
    this.hasRoleAdmin = function() {
        return this.hasRole(USER_ROLES.admin);
    };
    this.hasRoleSensei = function() {
        return this.hasRole(USER_ROLES.sensei);
    };
    this.hasRoleUser = function() {
        return this.hasRole(USER_ROLES.user);
    };
    return this;
});

egdApp.factory('AuthenticationSharedService', function ($rootScope, $http, authService, Session, Account) {
    return {
        login: function (param) {
            var data = "j_username=" + encodeURIComponent(param.username) + "&j_password=" +
                encodeURIComponent(param.password) +
                "&_spring_security_remember_me=" +
                param.rememberMe + "&submit=Login";

            $http.post('app/authentication', data, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                ignoreAuthModule: 'ignoreAuthModule'
            }).success(function (data, status, headers, config) {
                Account.get(function (data) {
                    Session.create(data.login, data.firstName, data.lastName, data.email, data.roles);
                    $rootScope.account = Session;
                    authService.loginConfirmed(data);
                });
            }).error(function (data, status, headers, config) {
                $rootScope.authenticationError = true;
                Session.invalidate();
            });
        },
        valid: function (authorizedRoles) {

            $http.get('protected/authentication_check.gif', {
                ignoreAuthModule: 'ignoreAuthModule'
            }).success(function (data, status, headers, config) {
                if (!Session.login) {
                    Account.get(function (data) {
                        Session.create(data.login, data.firstName, data.lastName, data.email, data.roles);
                        $rootScope.account = Session;
                        if (!$rootScope.isAuthorized(authorizedRoles)) {
                            // user is not allowed
                            $rootScope.$broadcast("event:auth-notAuthorized");
                        } else {
                            $rootScope.$broadcast("event:auth-loginConfirmed");
                        }
                    });
                } else {
                    if (!$rootScope.isAuthorized(authorizedRoles)) {
                        // user is not allowed
                        $rootScope.$broadcast("event:auth-notAuthorized");
                    } else {
                        $rootScope.$broadcast("event:auth-loginConfirmed");
                    }
                }
            }).error(function (data, status, headers, config) {
                if (!$rootScope.isAuthorized(authorizedRoles)) {
                    $rootScope.$broadcast('event:auth-loginRequired', data);
                }
            });
        },
        isAuthorized: function (authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                if (authorizedRoles == '*') {
                    return true;
                }

                authorizedRoles = [authorizedRoles];
            }

            var isAuthorized = false;
            angular.forEach(authorizedRoles, function (authorizedRole) {
                var authorized = (!!Session.login &&
                Session.userRoles.indexOf(authorizedRole) !== -1);

                if (authorized || authorizedRole == '*') {
                    isAuthorized = true;
                }
            });

            return isAuthorized;
        },
        logout: function () {
            $rootScope.authenticationError = false;
            $rootScope.authenticated = false;
            $rootScope.account = null;

            $http.get('app/logout');
            Session.invalidate();
            authService.loginCancelled();
        }
    };
});

egdApp.factory('Image', function ($resource) {
    return $resource('app/rest/images/:id', {}, {
        'query': { method: 'GET', isArray: true},
        'get': { method: 'GET'}
    });
});
