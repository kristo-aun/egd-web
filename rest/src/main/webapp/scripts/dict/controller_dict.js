'use strict';

egdApp.controller('DictController', function($scope, $route, $routeParams, $location, $translate, $log, DictService, $http) {

    if ($routeParams.phrase) $scope.phrase = $routeParams.phrase;
    $scope.lang = $translate.use();
    $scope.radioLang = "ja";
    $scope.phrases = [];

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
            $scope.getAutocomplete($scope.phrase).then(function(data) {
                $log.log('Dropdown is now: data=', data);
                $scope.phrases = data;
            });
        }

    };

    $scope.onPhraseClear = function() {
        $scope.phrase = undefined;
        $scope.phrases = [];
        $scope.toggleButtonDisabled = true;
    };

    $scope.getAutocomplete = function(phrasepart) {
        if (phrasepart && phrasepart.length > 1) {
            var context = "app/rest/dict/autocomplete/" + phrasepart;
            return $http.get(context).then(function (response) {
                $log.debug("DictService.autocomplete: response=", response);
                $scope.toggleButtonDisabled = false;
                return response.data;
            });
        } else {
            return [];
        }
    };

    $scope.onPhraseSelect = function($item, $model, $label) {
        $scope.phrase = angular.copy($item);
    };

    $scope.showResult = function() {
        $log.debug("DictController.showResult: phrase=" + $scope.phrase);
        $scope.resultVisible = true;
        $scope.gridJaVisible = $translate.use() === 'et' || $translate.use() === 'en';

        DictService.japest($scope.phrase).then(function (data) {
            if (data.length > 0) {
                $scope.rows = data;
            }
        });
    };
});
