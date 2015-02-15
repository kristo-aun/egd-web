'use strict';

egdApp.controller('TestCompoundController', function ($location, $scope, $log, TestCompoundService) {
    $log.debug("TestCompoundController");

    TestCompoundService.params().then(function (data) {
        $scope.params = data;
    });

    $scope.formVisible = true;

    function setFactsToDefault() {
        $scope.facts = {
            kanjiInterval: [1, 100]
        };
        $log.debug("setFactsToDefault", $scope.facts);
    }
    setFactsToDefault();

    var filterTypeChange = function() {
        var filterType = $scope.facts.filterType;
        var intervalType = $scope.facts.kanjiIntervalType;//level or index
        $log.debug("filterTypeChange: filterType=" + filterType + ", intervalType=" + intervalType);

        if (intervalType == 'level') {
            switch (parseInt(intervalType)) {
                case 1: {
                    $scope.kanjiLevelMap = $scope.params.gradeLevelMap;
                    break;
                }
                case 2: {
                    $scope.kanjiLevelMap = $scope.params.jlptLevelMap;
                    break;
                }
                case 3: {
                    $scope.kanjiLevelMap = $scope.params.jouyouLevelMap;
                    break;
                }
                case 4: {
                    $scope.kanjiLevelMap = $scope.params.heisig4LessonMap;
                    break;
                }
                case 5: {
                    $scope.kanjiLevelMap = $scope.params.heisig6LessonMap;
                    break;
                }
            }
        }
    };

    $scope.onDefaultFormChange = function(defaultForm) {
        $log.debug("onDefaultFormChange: defaultForm=", defaultForm);
        if (defaultForm) {
            $log.debug("onDefaultFormChange: will change form values");
            TestCompoundService.formDefault($scope.formDefaultId).then(function (data) {
                $scope.facts = data;
            });
        } else {
            setFactsToDefault();
        }
    };

    $scope.onFilterTypeChange = function() {
        filterTypeChange();
    };

    $scope.onKanjiIntervalTypeChange = function() {
        filterTypeChange();
    };

    $scope.doSubmit = function() {
        $log.debug("doSubmit: facts=", $scope.facts);

        var url = japest.persistence.url.testCompoundSubmit();
        cors.post(url, $scope.facts, function(result) {
            $scope.formVisible = false;
            $scope.compounds = result.data;
        });
    };

    function goAheadNumberDate(dateToIncrement, increment) {
        var a = increment ? increment : 1;
        return dateToIncrement.setDate(dateToIncrement.getDate() + a);
    }

    function goAheadNumber(model, increment) {
        var a = increment ? increment : 1;
        return model + increment;
    }


});
