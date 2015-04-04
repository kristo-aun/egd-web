'use strict';

egdApp
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
    })
    .directive('ngReallyClick', [function() {
        /**
         * A generic confirmation for risky actions.
         * Usage: Add attributes: ng-really-message="Are you sure"? ng-really-click="takeAction()" function
         */
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                element.bind('click', function() {
                    var message = attrs.ngReallyMessage;
                    if (message && confirm(message)) {
                        scope.$apply(attrs.ngReallyClick);
                    }
                });
            }
        }
    }])
    .directive('autoGrow', function() {
        return function(scope, element, attr) {
            var minHeight, paddingLeft, paddingRight, $shadow = null;

            function createShadow(){

                minHeight = element[0].offsetHeight;
                if (minHeight === 0)
                    return ;
                paddingLeft = element.css('paddingLeft');
                paddingRight = element.css('paddingRight');

                $shadow = angular.element('<div></div>').css({
                    position: 'absolute',
                    top: -10000,
                    left: -10000,
                    width: element[0].offsetWidth - parseInt(paddingLeft ? paddingLeft : 0, 10) - parseInt(paddingRight ? paddingRight : 0, 10),
                    fontSize: element.css('fontSize'),
                    fontFamily: element.css('fontFamily'),
                    lineHeight: element.css('lineHeight'),
                    resize: 'none'
                });
                angular.element(document.body).append($shadow);

            }

            var update = function() {
                if ($shadow === null)
                    createShadow();
                if ($shadow === null)
                    return ;
                var times = function(string, number) {
                    for (var i = 0, r = ''; i < number; i++) {
                        r += string;
                    }
                    return r;
                };

                var val = element.val().replace(/</g, '&lt;')
                    .replace(/>/g, '&gt;')
                    .replace(/&/g, '&amp;')
                    .replace(/\n$/, '<br/>&nbsp;')
                    .replace(/\n/g, '<br/>')
                    .replace(/\s{2,}/g, function(space) { return times('&nbsp;', space.length - 1) + ' '; });
                $shadow.html(val);

                element.css('height', Math.max($shadow[0].offsetHeight + 30, minHeight) + 'px');
            };

            element.bind('keyup keydown keypress change focus', update);
            scope.$watch(attr.ngModel, update);
            scope.$watch(function () { return element.is(':visible') ; }, update);
        };
    })
    .directive('egdAudio', function (ngAudio) {
        return {
            restrict: 'E',
            scope: {
                src: '='
            },
            link: function (scope, element, attrs) {
                scope.audio = ngAudio.load(scope.src);
            },
            templateUrl: 'views/directive/egdAudio.html'
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
                    return  '/app/rest/audio/' + audioId;
                };
            },
            templateUrl: 'views/directive/gridJa.html'
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
                    return  '/app/rest/audio/' + audioId;
                };
            },
            templateUrl: 'views/directive/gridEt.html'
        }
    })
    .directive('phraseAutocomplete', function (DictService, $timeout, $log) {
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

                        DictService.autocomplete(scope.lang, request.term).then(function (data) {
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
            templateUrl: 'views/directive/kanaKeys.html'
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
            templateUrl: 'views/directive/estonianKeys.html'
        }
    });
