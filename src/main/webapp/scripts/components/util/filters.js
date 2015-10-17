'use strict';

egdApp
/**
 * https://gist.github.com/cmmartin/341b017194bac09ffa1a
 * {{ anyDateObjectOrString | moment: 'format': 'MMM DD, YYYY' }} = Nov 11, 2014
 * {{ anyDateObjectOrString | moment: 'fromNow' }} = 10 minutes ago
 * {{ someDate | moment: 'utc' | moment: 'format': 'MMM DD, YYYY' }}
 */
    .filter('moment', function($translate) {
        return function(input, momentFn) {
            var args = Array.prototype.slice.call(arguments, 2),
                momentObj = moment(input).locale($translate.use());
            return momentObj[momentFn].apply(momentObj, args);
        };
    })
    .filter('momentDateTime', function(Moment, DISPLAY_DATE_TIME) {
        return function(input) {
            return Moment.serialize(input, DISPLAY_DATE_TIME);
        };
    })
    .filter('momentDate', function(Moment, DISPLAY_DATE) {
        return function(input) {
            return Moment.serialize(input, DISPLAY_DATE);
        };
    })
    .filter('momentTime', function(Moment, DISPLAY_TIME) {
        return function(input) {
            return Moment.serialize(input, DISPLAY_TIME);
        };
    })
    .filter('capitalize', function() {
        return function(input, all) {
            return (!!input) ? input.replace(/([^\W_]+[^\s-]*) */g, function(txt) {
                return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
            }) : '';
        }
    })
    .filter('arrayToString', function() {
        return function(input) {
            var result = "";
            angular.forEach(input, function(item) {
                if (result.length > 0)
                    result += ", ";
                result += item;
            });
            return result;
        };
    })
    .filter('characters', function() {
        return function(input, chars, breakOnWord) {
            if (isNaN(chars)) {
                return input;
            }
            if (chars <= 0) {
                return '';
            }
            if (input && input.length > chars) {
                input = input.substring(0, chars);

                if (!breakOnWord) {
                    var lastspace = input.lastIndexOf(' ');
                    // Get last space
                    if (lastspace !== -1) {
                        input = input.substr(0, lastspace);
                    }
                } else {
                    while (input.charAt(input.length - 1) === ' ') {
                        input = input.substr(0, input.length - 1);
                    }
                }
                return input + '...';
            }
            return input;
        };
    })
    .filter('words', function() {
        return function(input, words) {
            if (isNaN(words)) {
                return input;
            }
            if (words <= 0) {
                return '';
            }
            if (input) {
                var inputWords = input.split(/\s+/);
                if (inputWords.length > words) {
                    input = inputWords.slice(0, words).join(' ') + '...';
                }
            }
            return input;
        };
    })
    .filter('unsafe', function($sce) {
        return function(value) {
            if (!value) {
                return '';
            }
            return $sce.trustAsHtml(value);
        };
    })
    .filter('checkmark', function() {
        return function(input) {
            return input ? '\u2713' : '\u2718';
        };
    })
    .filter('securehash', function() {
        return function(input) {
            var port = window.location.port;
            if (port == 8443 || port == 443) {
                return input;
            }
            var portstring = port == 8080 ? ":8443" : "";
            return "https://" + window.location.hostname + portstring + input;
        };
    })
    .filter('ie', function() {
        return function(v, yes, no) {
            return v ? yes : no;
        };
    });
