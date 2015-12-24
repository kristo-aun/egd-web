'use strict';

var egdApp = angular.module('egdApp', [
    'ngAnimate',
    'LocalStorageModule',
    'ngAria',
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
    'ui.grid.pagination',
    'angular-loading-bar',
    'angular-confirm',
    'blockUI',
    'ngTagsInput',
    'ngFileUpload',
    'monospaced.elastic'
]);

egdApp
    .run(function($rootScope, $location, $window, $http, $state, $translate, $log, Auth, Principal, Language, tmhDynamicLocale, ENV) {
        $rootScope.ENV = ENV;

        if (!$translate.use()) tmhDynamicLocale.set("et");

        $rootScope.forceSSL = function(event) {
            if ($location.protocol() !== 'https') {
                event.preventDefault();
                $window.location.href = $location.absUrl().replace('http', 'https');
            }
        };

        $rootScope.$on('$stateChangeStart', function(event, toState, toStateParams) {

            //in case someone manually tries to change the protocol
            if (Principal.isAuthenticated() || toState == 'login') {
                $rootScope.forceSSL(event);
            }

            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;

            if (Principal.isIdentityResolved()) {
                Auth.authorize();
            }

            // Update the language
            Language.getCurrent().then(function(language) {
                $translate.use(language);
            });

        });

        $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
            var titleKey = 'global.title';

            // Remember previous state unless we've been redirected to login or we've just
            // reset the state memory after logout. If we're redirected to login, our
            // previousState is already set in the authExpiredInterceptor. If we're going
            // to login directly, we don't want to be sent to some previous state anyway
            if (toState.name != 'login' && $rootScope.previousStateName) {
                $rootScope.previousStateName = fromState.name;
                $rootScope.previousStateParams = fromParams;
            }

            // Set the page title key to the one configured in state or use default one
            if (toState.data.pageTitle) {
                titleKey = toState.data.pageTitle;
            }

            $translate(titleKey).then(function(title) {
                // Change window title with translated one
                $window.document.title = title;
            });

            if (!$window.ga)
                return;
            $window.ga('send', 'pageview', { page: $location.url() });
        });

        $rootScope.$on('accountChange', function() {
            Principal.identity(true).then(function(account) {
                $rootScope.account = angular.copy(account);
            }, function() {
                delete $rootScope.account;
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

        $rootScope.emit = function(eventname, data) {
            $rootScope.$emit('egdApp:' + eventname, data);
        };
    })
    .config(function(blockUIConfig) {//loading spinner configuration
        blockUIConfig.cssClass = 'block-ui block-ui-custom-spinner';
        // Change the default delay to 100ms before the blocking is visible
        blockUIConfig.delay = 100;

        // Disable auto body block
        blockUIConfig.autoInjectBodyBlock = false;
    })
    .config(function(cfpLoadingBarProvider) {//blue ribbon shooting through header during http request
        cfpLoadingBarProvider.includeSpinner = false;
    })
    .config(function($stateProvider, $urlRouterProvider, $httpProvider,
                     $logProvider, $translateProvider, tmhDynamicLocaleProvider,
                     httpRequestInterceptorCacheBusterProvider, ENV) {

        $logProvider.debugEnabled(ENV != 'prod');

        //enable CSRF
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';

        //Cache everything except rest api requests
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        $urlRouterProvider.otherwise(function($injector, $location) {
            $injector.invoke(function($state) {
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
                    function(Auth) {
                        return Auth.authorize();
                    }
                ],
                translatePartialLoader: ['$translate', '$translatePartialLoader',
                    function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('navbar');
                        $translatePartialLoader.addPart('ontology');
                        $translatePartialLoader.addPart('language');
                        $translatePartialLoader.addPart('tip');
                    }]
            }
        });

        $httpProvider.interceptors.push('authExpiredInterceptor');
        $httpProvider.interceptors.push('notificationInterceptor');

        // Initialize angular-translate
        $translateProvider.useLoader('$translatePartialLoader', {
            urlTemplate: 'i18n/{lang}/{part}.json'
        });

        $translateProvider.preferredLanguage('et');
        $translateProvider.useCookieStorage();
        $translateProvider.useSanitizeValueStrategy('escaped');
        $translateProvider.addInterpolation('$translateMessageFormatInterpolation');

        tmhDynamicLocaleProvider.localeLocationPattern('i18n/angular-locale/angular-locale_{{locale}}.js');
        tmhDynamicLocaleProvider.useCookieStorage();
        tmhDynamicLocaleProvider.storageKey('NG_TRANSLATE_LANG_KEY');

    })
    .config(['$urlMatcherFactoryProvider', function($urlMatcherFactory) {
        $urlMatcherFactory.type('boolean', {
            name : 'boolean',
            decode: function(val) { return val == true ? true : val == "true" },
            encode: function(val) { return val ? 1 : 0; },
            equals: function(a, b) { return this.is(a) && a === b; },
            is: function(val) { return [true,false,0,1].indexOf(val) >= 0 },
            pattern: /bool|true|0|1/
        });
    }]);

