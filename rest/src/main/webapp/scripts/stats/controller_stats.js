'use strict';

egdApp.controller('StatsController', function ($scope, $translate, $log, StatsService) {
    $log.debug("StatsController");
    StatsService.counters().then(function (data) {
        $scope.counters = data;
    });
    StatsService.coreStats().then(function (data) {
        $scope.coreStats = data;
    });
    StatsService.translatedEntrRatio().then(function (data) {
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

    StatsService.countGlossToSumFreq().then(function (data) {
        $log.debug("StatsController.translatedEntrRatio: data=", data);
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
