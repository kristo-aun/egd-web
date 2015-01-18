'use strict';

egdApp.controller('DictController', function($scope, $route, $routeParams, $location, $translate, $log, DictService) {
    var phrase = $routeParams.phrase;
    if (phrase) $scope.phrase = phrase;
    var slng = $routeParams.slng;
    if (slng) $scope.slng = slng;
    else $scope.slng = "jp";

    $log.debug("DictController: phrase=" + phrase + ", slng=", slng);

    if (slng && phrase) {
        $scope.resultVisible = true;
        //kui kasutajaliidese keel on et, siis õpime jaapani keelt
        $scope.gridJpMode = "et" == 'et';
        $scope.gridEtMode = !$scope.gridJpMode;
        $log.debug("DictController: resultVisible=" + $scope.resultVisible + ", gridJpMode=", $scope.gridJpMode);
    } else {
        $scope.statisticsVisible = true;
    }

    $scope.showResult = function(q) {
        $log.debug("DictCtrl.showResult: q=" + q);
        var phrase = q != null ? q : $scope.phrase;
        var slng = $scope.slng;
        $log.debug("DictController.showResult: phrase=" + phrase + ", slng=", slng);
        if (phrase && slng) {
            $location.path('/dict/' + phrase + "/" + slng);
            $route.reload();
        }
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
