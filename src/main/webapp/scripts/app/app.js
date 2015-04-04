'use strict';

var egdApp = angular.module('egdApp', [
    'LocalStorageModule',
    'tmh.dynamicLocale',
    'ngResource',
    'ui.router',
    'ngCookies',
    'pascalprecht.translate',
    'ngCacheBuster',
    'infinite-scroll',
    'ngAudio',
    'ui.chart',
    'ui.select',
    'ui.grid',
    'ui.grid.pagination'
]);

egdApp
    .run(function ($rootScope, $location, $window, $http, $state, $translate, Auth, Principal, Language) {
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

        $rootScope.$on('$stateChangeSuccess',  function(event, toState, toParams, fromState, fromParams) {
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

        $rootScope.back = function() {
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

                if (token && token.expires && token.expires > new Date().getTime()) {
                    config.headers['x-auth-token'] = token.token;
                }

                return config;
            }
        };
    })

    .config(function ($stateProvider, $httpProvider, $locationProvider, $translateProvider, $urlRouterProvider, $logProvider, tmhDynamicLocaleProvider, httpRequestInterceptorCacheBusterProvider, ENV) {

        $logProvider.debugEnabled(ENV == 'dev');
        $httpProvider.interceptors.push('HttpErrorInterceptor');

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
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('language');
                    return $translate.refresh();
                }]
            }
        });

        $httpProvider.interceptors.push('authInterceptor');

        // Initialize angular-translate
        $translateProvider.useLoader('$translatePartialLoader', {
            urlTemplate: 'i18n/{lang}/{part}.json'
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
    });
