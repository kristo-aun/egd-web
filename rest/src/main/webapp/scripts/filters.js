'use strict';

var filters = angular.module('egdApp');

filters.filter('checkmark', function () {
    return function (input) {
        return input ? '\u2713' : '\u2718';
    };
});

filters.filter('ie', function() {
    return function(v, yes, no){
        return v ? yes : no;
    };
});

filters.filter('interpolate', ['version', function(version) {
    return function(text) {
        return String(text).replace(/\%VERSION\%/mg, version);
    };
}]);
