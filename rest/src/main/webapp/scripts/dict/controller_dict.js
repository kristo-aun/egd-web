'use strict';

egdApp.controller('DictController', function($scope, $route, $routeParams, $location, $translate, $log, DictService) {

    $scope.lang = $translate.use();
    $scope.radioLang = "ja";

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
    };

    $scope.onPhraseClear = function() {
        $scope.phrase = undefined;
    }
});
