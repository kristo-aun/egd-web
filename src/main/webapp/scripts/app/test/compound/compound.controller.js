'use strict';

egdApp
    .controller('CompoundController', function ($state, $scope, $translate, $log, TestCompoundResource) {

        //------------------------------ first ------------------------------

        $scope.clear = function() {
            delete $scope.params;
        };

        $scope.clearError = function() {
            delete $scope.errorI18n;
            delete $scope.error;
        };

        $scope.setFactsToDefault = function() {
            $scope.first = {
                kanjiInterval: [1, 100]
            };
            $log.debug("setFactsToDefault", $scope.first);
        };

        $scope.load = function() {
            $scope.clear();
            TestCompoundResource.params().then(function (data) {
                $scope.params = data;
            });
            $scope.setFactsToDefault();
        };
        $scope.load();

        $scope.filterTypeChange = function() {
            var filterType = $scope.first.filterType;
            var intervalType = $scope.first.kanjiIntervalType;//level or index
            $log.debug("filterTypeChange: filterType=" + filterType + ", intervalType=" + intervalType);

            if (intervalType == 'level') {
                switch (parseInt(filterType)) {
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
            $scope.formDefaultId = defaultForm;
            if (defaultForm) {
                $log.debug("onDefaultFormChange: will change form values");
                TestCompoundResource.formDefault(defaultForm).then(function (data) {
                    $scope.first = data;
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
            $scope.clearError();
            TestCompoundResource.submit($scope.first).then(function (data) {
                $scope.compounds = data;
                $state.go("compound.second");
            }, function() {
                $scope.error = true;
                $scope.errorI18n = "global.messages.error.fail";
            });
        };
        $state.go(".first");

        //------------------------------ second ------------------------------

        $scope.showAnswer = false;
        $scope.showHints = false;
    });
