'use strict';

egdApp
    .factory('Language', function ($q, $http, $translate, LANGUAGES) {
        return {
            getCurrent: function () {
                var deferred = $q.defer();
                var language = $translate.storage().get('NG_TRANSLATE_LANG_KEY');

                if (angular.isUndefined(language)) {
                    language = 'et';
                }

                deferred.resolve(language);
                return deferred.promise;
            },
            getAll: function () {
                var deferred = $q.defer();
                deferred.resolve(LANGUAGES);
                return deferred.promise;
            }
        };
    })
    .constant('LANGUAGES', [
        'et', 'en', 'ja'
    ]
);
