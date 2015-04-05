'use strict';

egdApp
    .directive('egdPagination', function () {
        return {
            replace: true,
            scope: {
                page:'=',
                links:'=',
                limit:'=',
                limits:'=?',
                loadPage: '=',
                wrapcount: '=?',
                hideIfLimitCount: '=?'
            },
            restrict: 'E',
            templateUrl: 'scripts/components/util/directive/egdPagination.html',
            link: function (scope, iElement, attr) {
                scope.hide = false;

                if (!angular.isDefined(scope.wrapcount) || scope.wrapcount < 1) scope.wrapcount = 3;

                if (scope.limits === undefined) {
                    scope.limits = [20, 50, 100];
                    if (scope.limits.indexOf(scope.limit) == -1) {
                        scope.limits.push(scope.limit);
                        scope.limits.sort(function sortNumber(a,b) {
                            return a - b;
                        });
                    }
                }

                scope.range = function(n) {
                    return new Array(n);
                };

                scope.getPageForLimit = function(currentpage, newlimit) {
                    var firstrowidx = (scope.limit * (currentpage - 1)) + 1;
                    var result = ((firstrowidx - 1) / newlimit) + 1;
                    return Math.floor(result);
                };

                var wrapcount = scope.wrapcount;

                scope.$watch('links', function(links) {

                    if (angular.isDefined(links)) {

                        scope.prevcount = Math.min(wrapcount, scope.page - 1);

                        scope.firstcount = Math.max(0, Math.min(wrapcount, scope.page - scope.prevcount - 1));
                        scope.showfirstpause = scope.page - wrapcount > scope.firstcount + 1;

                        var last = angular.isDefined(links['last']) ? links['last'] : scope.page;
                        scope.nextcount = Math.min(wrapcount, last - scope.page);

                        scope.lastcount = Math.max(0, Math.min(wrapcount, links['last'] - scope.page - wrapcount));
                        scope.showlastpause = scope.page + wrapcount < links['last'] - wrapcount;

                    }

                    if (scope.hideIfLimitCount) {
                        scope.hide = scope.hideIfLimitCount <= scope.limits[0];
                    } else {
                        scope.hide = false;
                    }
                });
            }
        };
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
            templateUrl: 'scripts/components/util/directive/egdAudio.html'
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
            templateUrl: 'scripts/components/util/directive/gridJa.html'
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
            templateUrl: 'scripts/components/util/directive/gridEt.html'
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
            templateUrl: 'scripts/components/util/directive/kanaKeys.html'
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
            templateUrl: 'scripts/components/util/directive/estonianKeys.html'
        }
    }).directive('ngRepeatN', function () {
        return {
            restrict: 'A',
            transclude: 'element',
            replace: true,
            link: function (scope, element, attrs, ctrl, $transclude) {

                // the element to insert after
                scope.last = element;

                // the parent element
                scope.parentElem = element.parent();

                // list of elements in the repeater
                scope.elems = [element];

                scope.$watch('repeat', function (newValue, oldValue) {

                    var newInt = parseInt(newValue)
                        , oldInt = parseInt(oldValue)
                        , bothValues = ! isNaN(newInt) && ! isNaN(oldInt)
                        , childScope
                        , i
                        , limit;

                    // decrease number of repeated elements
                    if (isNaN(newInt) || (bothValues && newInt < oldInt)) {
                        limit = bothValues ? newInt : 0;
                        scope.last = scope.elems[limit];
                        for (i = scope.elems.length - 1; i > limit; i -= 1) {
                            scope.elems[i].remove();
                            scope.elems.pop();
                        }
                    }

                    // increase number of repeated elements
                    else {
                        i = scope.elems.length - 1;

                        for (i; i < newInt; i += 1) {
                            childScope = scope.$new();
                            childScope.$index = i;
                            $transclude(childScope, function (clone) {
                                scope.last.after(clone);
                                scope.last = clone;
                                scope.elems.push(clone);
                            });
                        }
                    }
                });
            },
            scope: {
                repeat: '=ngRepeatN'
            }
        };
    });
