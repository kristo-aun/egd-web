'use strict';

egdApp
    .directive('egdAudio', function (ngAudio) {
        return {
            restrict: 'E',
            scope: {
                src: '='
            },
            link: function (scope, element, attrs) {
                scope.audio = ngAudio.load(scope.src);
            },
            templateUrl: 'scripts/components/directive/egdAudio.html'
        }
    })
    .directive('gridJa', function () {
        return {
            restrict: 'E',
            scope: {
                rows: '='
            },
            link: function (scope, element, attrs) {
                scope.getAudioResource = function(audioId) {
                    return  '/api/audio/' + audioId;
                };
            },
            templateUrl: 'scripts/components/directive/gridJa.html'
        }
    })
    .directive('gridEt', function () {
        return {
            restrict: 'E',
            scope: {
                rows: '='
            },
            link: function (scope, element, attrs) {
                scope.getAudioResource = function(audioId) {
                    return  '/api/audio/' + audioId;
                };
            },
            templateUrl: 'scripts/components/directive/gridEt.html'
        }
    })
    .directive('phraseAutocomplete', function (DictResource, $timeout, $log) {
        return {
            restrict: 'A',
            scope: {
                onSelect: '&',
                minLength: '@',
                lang: '@'
            },
            link: function (scope, iElement) {
                iElement.autocomplete({//jquery ui ac
                    source: function (request, response) {
                        $log.debug("phraseAutocomplete.term=", request.term);

                        DictResource.autocomplete(scope.lang, request.term).then(function (data) {
                            $log.debug("phraseAutocomplete.autocomplete: data=", data);
                            response(
                                $.map(data, function (item) {
                                    return {
                                        label: item.title,
                                        value: item.title
                                    }
                                })
                            );
                        });
                    },
                    select: function (event, element) {
                        var val = element.item.value;
                        $log.debug("phraseAutocomplete: val=", val);
                        scope.onSelect({q: val})
                    },
                    minLength: scope.minLength
                });
            }
        }
    })
    .directive('kanaKeys', function ($log) {
        return {
            restrict: 'A',
            scope: {
                onClick: '&'
            },
            link: function (scope, element, attrs) {
                scope.onLetterClick = function (letter) {
                    $log.debug("onLetterClick: letter=", letter);
                    scope.onClick({letter: letter});
                }
            },
            templateUrl: 'scripts/components/directive/kanaKeys.html'
        }
    })
    .directive('estonianKeys', function ($log) {
        return {
            restrict: 'A',
            scope: {
                onClick: '&'
            },
            link: function (scope, element, attrs) {
                scope.onLetterClick = function (letter) {
                    $log.debug("onLetterClick: letter=", letter);
                    scope.onClick({letter: letter});
                }
            },
            templateUrl: 'scripts/components/directive/estonianKeys.html'
        }
    })
    .directive('audios', function($sce) {
        return {
            restrict: 'A',
            scope: { code:'=' },
            replace: true,
            template: '<audio ng-src="{{url}}" controls></audio>',
            link: function (scope) {
                scope.$watch('code', function (newVal, oldVal) {
                    if (newVal !== undefined) {
                        scope.url = $sce.trustAsResourceUrl("/data/media/" + newVal);
                    }
                });
            }
        };
    });
