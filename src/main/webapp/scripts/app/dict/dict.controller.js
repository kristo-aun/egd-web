'use strict';

egdApp
    .controller('DictController', function ($window, $rootScope, $scope, $http, $location, $stateParams, $translate, $log, Principal, DictService) {
        Principal.identity().then(function() {
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        if ($stateParams.phrase) {
            $scope.phrase = $stateParams.phrase;
            $window.document.title = $scope.phrase;

            $scope.resultVisible = true;
            $scope.gridJaVisible = $translate.use() === 'et' || $translate.use() === 'en' || false;
            $log.debug("DictController: gridJaVisible=", $scope.gridJaVisible);

            $scope.rows = {};

            DictService.jmtrans($scope.phrase).then(function (data) {
                $scope.rows = data;
            });
        }
        $scope.lang = $translate.use();

        $scope.radioLang = $stateParams.radioLang ? $stateParams.radioLang : "ja";
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
                var context = "api/dict/autocomplete/" + phrasepart;
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
            if ($scope.phrase != undefined)
                $location.url("/dict/" + $scope.phrase + "?radioLang=" + $scope.radioLang);
        };

    });
