'use strict';

egdApp.controller('DictController', function($scope, $route, $routeParams, $location, $translate, $log, DictService, $http) {

    $scope.lang = $translate.use();
    $scope.radioLang = "ja";

    $scope.toggleButtonDisabled = true;

    $scope.getPhrase = function() {
        return $scope.phrase;
    };

    $scope.setPhrase = function(q) {
        $log.debug("DictController.setPhrase: q=" + q);
        $scope.phrase = q;
    };

    $scope.appendToPhrase = function(letter) {
        if ($scope.phrase == undefined)
            $scope.phrase = letter;
        else
            $scope.phrase += letter;

        $scope.toggleButtonDisabled = false;
    };

    $scope.toggled = function(open) {
        if (open) {
            $scope.phrases = $scope.phrases = $scope.getAutocomplete($scope.phrase);
        }
        $log.log('Dropdown is now: ', open);
    };

    $scope.onPhraseClear = function() {
        $scope.phrase = undefined;
        $scope.phrases = [];
        $scope.toggleButtonDisabled = true;
    };

    $scope.getAutocomplete = function(phrase) {
        if (phrase && phrase.length > 0) {
            var context = "app/rest/dict/autocomplete?lang=" + "et" + "&q=" + phrase;
            var promise = $http.get(context).then(function (response) {
                $log.debug("DictService.autocomplete: response=", response);
                $scope.toggleButtonDisabled = false;
                return response.data;
            });
            return promise;
        } else {
            return [];
        }
    };

    $scope.onPhraseSelect = function($item, $model, $label) {
        $scope.phrase = angular.copy($item);
    };

    $scope.showResult = function() {
        $log.debug("DictController.showResult: phrase=" + $scope.phrase, ", radioLang=", $scope.radioLang);
        $scope.resultVisible = true;
        $scope.gridJaVisible = $scope.lang === 'et' || $scope.lang === 'en';

        DictService.japest($scope.radioLang, $scope.phrase).then(function (data) {
            if (data.length > 0) {
                $scope.rows = data;
            }
        });
    };
});
