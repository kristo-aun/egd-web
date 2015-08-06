'use strict';

egdApp
    .controller('StatController', function ($scope, $translate, $log, StatResource) {
        $log.debug("StatsController");
        StatResource.counters().then(function (data) {
            $scope.counters = data;
        });
        StatResource.coreStats().then(function (data) {
            $scope.coreStats = data;
        });
        StatResource.translatedEntrRatio().then(function (data) {
            $log.debug("StatsController.translatedEntrRatio: data=", data);
            $scope.translatedEntrRatio = [
                $.map(data, function (item) {
                    return [item.countGloss, item.sumCount]
                })
            ];
        });

        $scope.translatedEntrRatioChartOpts = {
            seriesDefaults: {
                showMarker:true,
                lineWidth:1,
                pointLabels: {
                    show: true,
                    edgeTolerance: 0
                }},
            axes:{
                xaxis: {
                    min:1, max:16,
                    // Turn off "padding".  This will allow data point to lie on the
                    // edges of the grid.  Default padding is 1.2 and will keep all
                    // points inside the bounds of the grid.
                    pad: 0
                },
                yaxis: {
                    min:1, max:8000
                }
            }
        };
        $translate("stats.jmdict.chart.xaxis").then(function(translation) {
            $scope.translatedEntrRatioChartOpts.axes.xaxis.label = translation;
        });
        $translate("stats.jmdict.chart.yaxis").then(function(translation) {
            $scope.translatedEntrRatioChartOpts.axes.yaxis.label = translation;
        });

        StatResource.countGlossToSumFreq().then(function (data) {
            $log.debug("StatsController.countGlossToSumFreq: data=", data);
            $scope.countGlossToSumFreq = [
                $.map(data, function (item) {
                    return [item.countGloss, item.sumFreq]
                })
            ];
        });

        $scope.countGlossToSumFreqChartOpts = {
            seriesDefaults: {
                showMarker:true,
                lineWidth:1,
                pointLabels: {
                    show: true,
                    edgeTolerance: 0
                }},
            axes:{
                xaxis: {
                    min:0, max:40,
                    pad: 0
                },
                yaxis: {
                    min:0, max:12000000
                }
            }
        };
        $translate("stats.freq.chart.xaxis").then(function(translation) {
            $scope.countGlossToSumFreqChartOpts.axes.xaxis.label = translation;
        });
        $translate("stats.freq.chart.yaxis").then(function(translation) {
            $scope.countGlossToSumFreqChartOpts.axes.yaxis.label = translation;
        });
    });
