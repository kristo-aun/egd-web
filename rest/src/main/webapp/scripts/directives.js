'use strict';

var directives = angular.module('egdApp');

directives.directive('activeMenu', function ($translate, $locale, tmhDynamicLocale) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs, controller) {
            var language = attrs.activeMenu;

            scope.$watch(function () {
                return $translate.use();
            }, function (selectedLanguage) {
                if (language === selectedLanguage) {
                    tmhDynamicLocale.set(language);
                    element.addClass('active');
                } else {
                    element.removeClass('active');
                }
            });
        }
    };
});

directives.directive('activeLink', function (location) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs, controller) {
            var clazz = attrs.activeLink;
            var path = attrs.href;
            path = path.substring(1); //hack because path does bot return including hashbang
            scope.location = location;
            scope.$watch('location.path()', function (newPath) {
                if (path === newPath) {
                    element.addClass(clazz);
                } else {
                    element.removeClass(clazz);
                }
            });
        }
    };
});

directives.directive('passwordStrengthBar', function () {
    return {
        replace: true,
        restrict: 'E',
        template: '<div id="strength">' +
        '<small translate="global.messages.validate.newpassword.strength">Password strength:</small>' +
        '<ul id="strengthBar">' +
        '<li class="point"></li><li class="point"></li><li class="point"></li><li class="point"></li><li class="point"></li>' +
        '</ul>' +
        '</div>',
        link: function (scope, iElement, attr) {
            var strength = {
                colors: ['#F00', '#F90', '#FF0', '#9F0', '#0F0'],
                mesureStrength: function (p) {

                    var _force = 0;
                    var _regex = /[$-/:-?{-~!"^_`\[\]]/g; // "

                    var _lowerLetters = /[a-z]+/.test(p);
                    var _upperLetters = /[A-Z]+/.test(p);
                    var _numbers = /[0-9]+/.test(p);
                    var _symbols = _regex.test(p);

                    var _flags = [_lowerLetters, _upperLetters, _numbers, _symbols];
                    var _passedMatches = $.grep(_flags, function (el) {
                        return el === true;
                    }).length;

                    _force += 2 * p.length + ((p.length >= 10) ? 1 : 0);
                    _force += _passedMatches * 10;

                    // penality (short password)
                    _force = (p.length <= 6) ? Math.min(_force, 10) : _force;

                    // penality (poor variety of characters)
                    _force = (_passedMatches == 1) ? Math.min(_force, 10) : _force;
                    _force = (_passedMatches == 2) ? Math.min(_force, 20) : _force;
                    _force = (_passedMatches == 3) ? Math.min(_force, 40) : _force;

                    return _force;

                },
                getColor: function (s) {

                    var idx = 0;
                    if (s <= 10) {
                        idx = 0;
                    }
                    else if (s <= 20) {
                        idx = 1;
                    }
                    else if (s <= 30) {
                        idx = 2;
                    }
                    else if (s <= 40) {
                        idx = 3;
                    }
                    else {
                        idx = 4;
                    }

                    return {idx: idx + 1, col: this.colors[idx]};
                }
            };
            scope.$watch(attr.passwordToCheck, function (password) {
                if (password) {
                    var c = strength.getColor(strength.mesureStrength(password));
                    iElement.removeClass('ng-hide');
                    iElement.find('ul').children('li')
                        .css({"background": "#DDD"})
                        .slice(0, c.idx)
                        .css({"background": c.col});
                }
            });
        }
    }
});

directives.directive('showValidation', function () {
    return {
        restrict: "A",
        require: 'form',
        link: function (scope, element, attrs, formCtrl) {
            element.find('.form-group').each(function () {
                var $formGroup = $(this);
                var $inputs = $formGroup.find('input[ng-model],textarea[ng-model],select[ng-model]');

                if ($inputs.length > 0) {
                    $inputs.each(function () {
                        var $input = $(this);
                        scope.$watch(function () {
                            return $input.hasClass('ng-invalid') && $input.hasClass('ng-dirty');
                        }, function (isInvalid) {
                            $formGroup.toggleClass('has-error', isInvalid);
                        });
                    });
                }
            });
        }
    };
});

directives.directive('gridJa', function () {
    return {
        restrict: 'E',
        scope: {
            rows: '@',
            phrase: '@'
        },
        templateUrl: 'views/directive/gridJa.html'
    }
});

directives.directive('gridEt', function (DictService, $log) {
    return {
        restrict: 'E',
        scope: {
            lang: '@',
            q: '@'
        },
        link: function (scope, element, attrs) {
            scope.gridVisible = false;
            scope.searchNotInProgress = false;

            DictService.estjap(scope.lang, scope.q).then(function (result) {
                $log.debug("directives.gridEt.link: result=", result);
                if (result.errors) throw result.errors;

                if (result.data.length > 0) {
                    scope.gridVisible = true;
                    scope.estjap = result.data;
                }
                scope.searchNotInProgress = true;
            });
        },
        templateUrl: 'views/directive/gridEt.html'
    }
});

directives.directive('phraseAutocomplete', function (DictService, $timeout, $log) {
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

                    DictService.autocomplete(scope.lang, request.term).then(function (result) {
                        $log.debug("phraseAutocomplete.autocomplete: result=", result);
                        if (result.errors) throw result.errors;
                        response(
                            $.map(result.data, function (item) {
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
});

directives.directive('kanaKeys', function ($log) {
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
});

directives.directive('estonianKeys', function ($log) {
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
