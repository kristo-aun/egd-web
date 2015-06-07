'use strict';

var egdApp = angular.module('egdApp', [
    'ngAnimate',
    'LocalStorageModule',
    'tmh.dynamicLocale',
    'ngResource',
    'ui.router',
    'ngCookies',
    'pascalprecht.translate',
    'ngCacheBuster',
    'infinite-scroll',
    'ui.bootstrap',
    'sticky',
    'ngAudio',
    'ui.chart',
    'ui.select',
    'ui.grid.pagination'
]);

egdApp
    .run(function ($rootScope, $location, $window, $http, $state, $translate, $log, Auth, Principal, Language, ENV) {
        $rootScope.ENV = ENV;
        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;

            if (Principal.isIdentityResolved()) {
                Auth.authorize();
            }

            // Update the language
            Language.getCurrent().then(function (language) {
                $translate.use(language);
            });
        });

        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            var titleKey = 'global.title';

            $rootScope.previousStateName = fromState.name;
            $rootScope.previousStateParams = fromParams;

            // Set the page title key to the one configured in state or use default one
            if (toState.data.pageTitle) {
                titleKey = toState.data.pageTitle;
            }
            $translate(titleKey).then(function (title) {
                // Change window title with translated one
                $window.document.title = title;
            });
        });

        $rootScope.$on('accountChange', function () {
            $log.debug("accountChange");
            Principal.identity(true).then(function(account) {
                $rootScope.account = angular.copy(account);
            }, function() {
                delete $rootScope.account;
            });
        });

        $rootScope.back = function () {
            // If previous state is 'activate' or do not exist go to 'home'
            if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
                $state.go('home');
            } else {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
            }
        };
    })
    .factory('authInterceptor', function ($rootScope, $q, $location, localStorageService) {
        return {
            // Add authorization token to headers
            request: function (config) {
                config.headers = config.headers || {};
                var token = localStorageService.get('token');

                if (token && token.expires_at && token.expires_at > new Date().getTime()) {
                    config.headers.Authorization = 'Bearer ' + token.access_token;
                }

                return config;
            }
        };
    })
    .factory('authExpiredInterceptor', function ($rootScope, $q, $injector, localStorageService) {
        return {
            responseError: function (response) {
                // token has expired
                if (response.status === 401 && (response.data.error == 'invalid_token' || response.data.error == 'Unauthorized')) {
                    localStorageService.remove('token');
                    var Principal = $injector.get('Principal');
                    if (Principal.isAuthenticated()) {
                        var Auth = $injector.get('Auth');
                        Auth.authorize(true);
                    }
                }
                return $q.reject(response);
            }
        };
    })
    .config(function ($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider,
                      $logProvider, $translateProvider, tmhDynamicLocaleProvider,
                      httpRequestInterceptorCacheBusterProvider, ENV) {

        $logProvider.debugEnabled(ENV != 'prod');
        //$httpProvider.interceptors.push('HttpErrorInterceptor');

        //Cache everything except rest api requests
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        $urlRouterProvider.otherwise(function($injector, $location){
            $injector.invoke(function($state, $rootScope, $translate, $log) {
                $log.debug("urlRouterProvider.otherwise", $location.path());
                if ($location.path()) {
                    $state.go('error', {code: '404'});
                } else {
                    $state.go('home');
                }
            });
        });

        $stateProvider.state('site', {
            'abstract': true,
            views: {
                'navbar@': {
                    templateUrl: 'scripts/components/navbar/navbar.html',
                    controller: 'NavbarController'
                },
                'footer@': {
                    templateUrl: 'scripts/components/footer/footer.html',
                    controller: 'FooterController'
                }
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ],
                translatePartialLoader: ['$translate', '$translatePartialLoader',
                    function ($translate, $translatePartialLoader) {

                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('navbar');
                    $translatePartialLoader.addPart('ontology');
                    $translatePartialLoader.addPart('tip');
                }]
            }
        });

        $httpProvider.interceptors.push('authInterceptor');
        $httpProvider.interceptors.push('authExpiredInterceptor');

        // Initialize angular-translate
        $translateProvider.useLoader('$translatePartialLoader', {
            urlTemplate: 'i18n/{lang}/{part}.json'
        });

        $translateProvider.preferredLanguage('et');
        $translateProvider.useCookieStorage();
        $translateProvider.useSanitizeValueStrategy('escaped');

        tmhDynamicLocaleProvider.localeLocationPattern('i18n/angular-locale/angular-locale_{{locale}}.js');
        tmhDynamicLocaleProvider.useCookieStorage();
        tmhDynamicLocaleProvider.storageKey('NG_TRANSLATE_LANG_KEY');
    });
