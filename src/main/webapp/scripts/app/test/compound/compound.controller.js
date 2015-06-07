'use strict';

egdApp
    .controller('CompoundController', function ($scope, $translate, $log, TestCompoundResource) {

        $scope.clear = function() {
            delete $scope.params;
        };

        $scope.setFactsToDefault = function() {
            $scope.facts = {
                kanjiInterval: [1, 100]
            };
            $log.debug("setFactsToDefault", $scope.facts);
        };

        $scope.load = function() {
            $scope.clear();
            TestCompoundResource.params().then(function (data) {
                $scope.params = data;
            });
            $scope.formVisible = true;
            $scope.setFactsToDefault();
        };
        $scope.load();

        $scope.filterTypeChange = function() {
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
                TestCompoundResource.formDefault($scope.formDefaultId).then(function (data) {
                    $scope.facts = data;
                });
            } else {
                $scope.setFactsToDefault();
            }
        };

        $scope.onFilterTypeChange = function() {
            $scope.filterTypeChange();
        };

        $scope.onKanjiIntervalTypeChange = function() {
            $scope.filterTypeChange();
        };

        $scope.doSubmit = function() {
            $log.debug("doSubmit: facts=", $scope.facts);

            TestCompoundResource.submit($scope.facts).then(function (data) {
                $scope.formVisible = false;
                $scope.compounds = data;
            });
        };
    });
