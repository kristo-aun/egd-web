'use strict';

/* App Module */

var egdApp = angular.module('egdApp', [
    'http-auth-interceptor',
    'tmh.dynamicLocale',
    'ngResource',
    'ngRoute',
    'ngCookies',
    'ngSanitize',
    'ngTouch',
    'ngAudio',
    'egdAppUtils',
    'pascalprecht.translate',
    'truncate',
    'ui.bootstrap',
    'ui.chart',
    'ui.select',
    'ui.grid',
    'ui.grid.pagination',
    'ngCacheBuster'
]);

egdApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, $logProvider, tmhDynamicLocaleProvider,
                      httpRequestInterceptorCacheBusterProvider, USER_ROLES) {

        //$logProvider.debugEnabled(false);
        $httpProvider.interceptors.push('HttpErrorInterceptor');

        //Cache everything except rest api requests
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*rest.*/], true);

        $routeProvider
            .when('/register', {
                templateUrl: 'views/register.html',
                controller: 'RegisterController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/activate', {
                templateUrl: 'views/activate.html',
                controller: 'ActivationController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/login', {
                templateUrl: 'views/login.html',
                controller: 'LoginController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/changelog', {
                templateUrl: 'views/changelog.html',
                controller: 'ChangelogController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/error', {
                templateUrl: 'views/error.html',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/settings', {
                templateUrl: 'views/settings.html',
                controller: 'SettingsController',
                access: {
                    authorizedRoles: [USER_ROLES.user]
                }
            })
            .when('/password', {
                templateUrl: 'views/password.html',
                controller: 'PasswordController',
                access: {
                    authorizedRoles: [USER_ROLES.user]
                }
            })
            .when('/sessions', {
                templateUrl: 'views/sessions.html',
                controller: 'SessionsController',
                resolve: {
                    resolvedSessions: ['Sessions', function (Sessions) {
                        return Sessions.get();
                    }]
                },
                access: {
                    authorizedRoles: [USER_ROLES.user]
                }
            })
            .when('/metrics', {
                templateUrl: 'views/metrics.html',
                controller: 'MetricsController',
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
            .when('/health', {
                templateUrl: 'views/health.html',
                controller: 'HealthController',
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
            .when('/configuration', {
                templateUrl: 'views/configuration.html',
                controller: 'ConfigurationController',
                resolve: {
                    resolvedConfiguration: ['ConfigurationService', function (ConfigurationService) {
                        return ConfigurationService.get();
                    }]
                },
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
            .when('/logs', {
                templateUrl: 'views/logs.html',
                controller: 'LogsController',
                resolve: {
                    resolvedLogs: ['LogsService', function (LogsService) {
                        return LogsService.findAll();
                    }]
                },
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
            .when('/audits', {
                templateUrl: 'views/audits.html',
                controller: 'AuditsController',
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
            .when('/logout', {
                templateUrl: 'views/main.html',
                controller: 'LogoutController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .otherwise({
                templateUrl: 'views/main.html',
                controller: 'MainController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            });

        // Initialize angular-translate
        $translateProvider.useStaticFilesLoader({
            prefix: 'i18n/',
            suffix: '.json'
        });

        var initI18n = function(preferredLanguage) {
            $translateProvider.preferredLanguage(preferredLanguage);
            $translateProvider.useCookieStorage();
            tmhDynamicLocaleProvider.localeLocationPattern('i18n/angular-locale/angular-locale_{{locale}}.js');
            tmhDynamicLocaleProvider.useCookieStorage('NG_TRANSLATE_LANG_KEY');
        };

        //lets try to determine user's language by browser and location
        try {
            var nav = window.navigator.languages || [window.navigator.language || window.navigator.userLanguage];
            var navlang = nav[0].substring(0,2);

            if (navlang === "et") {
                initI18n("et");
            } else if (navlang === 'ja' || navlang === 'jp') {
                initI18n("ja");
            } else {
                initI18n("et");
            }
        } catch(ignored) {
            initI18n("en");
        }
    })
    .run(function ($rootScope, $location, $http, $translate, $log, AuthenticationSharedService, Session, USER_ROLES) {
        $rootScope.authenticated = false;
        $rootScope.$on('$routeChangeStart', function (event, next) {
            $rootScope.isAuthorized = AuthenticationSharedService.isAuthorized;
            $rootScope.userRoles = USER_ROLES;
            AuthenticationSharedService.valid(next.access.authorizedRoles);
        });

        // Call when the the client is confirmed
        $rootScope.$on('event:auth-loginConfirmed', function (data) {
            $rootScope.authenticated = true;
            if ($location.path() === "/login") {
                var search = $location.search();
                if (search.redirect !== undefined) {
                    $location.path(search.redirect).search('redirect', null).replace();
                } else {
                    $location.path('/').replace();
                }
            }
        });

        // Call when the 401 response is returned by the server
        $rootScope.$on('event:auth-loginRequired', function (rejection) {
            Session.invalidate();
            $rootScope.authenticated = false;
            if ($location.path() !== "/" && $location.path() !== "" && $location.path() !== "/register" &&
                $location.path() !== "/activate" && $location.path() !== "/login") {
                var redirect = $location.path();
                $location.path('/login').search('redirect', redirect).replace();
            }
        });

        // Call when the 403 response is returned by the server
        $rootScope.$on('event:auth-notAuthorized', function (rejection) {
            $rootScope.errorMessage = 'errors.403';
            $location.path('/error').replace();
        });

        // Call when the user logs out
        $rootScope.$on('event:auth-loginCancelled', function () {
            $location.path('');
        });

        //page title i18n
        var titleSuffix = 'EsutoniaGoDesu';

        $rootScope.page = {
            setTitle: function(title) {
                $log.debug("rootScope.page.setTitle: title=", title);
                $rootScope.page.title = title + ' | ' + titleSuffix;
            },
            setI18nTitle: function(key) {
                $translate(key).then(function(translation) {
                    $rootScope.page.title = translation + ' | ' + titleSuffix;
                });
            }
        };

        $rootScope.$on('$routeChangeStart', function(scope, next, current) {
            $rootScope.page.title = titleSuffix;
        });
    }
);
