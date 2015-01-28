'use strict';

egdApp.controller('StatsController', function ($scope, $log, StatsService) {
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

        title: 'Bar Chart with Point Labels',
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
                label: "X Axis",
                // Turn off "padding".  This will allow data point to lie on the
                // edges of the grid.  Default padding is 1.2 and will keep all
                // points inside the bounds of the grid.
                pad: 0
            },
            yaxis: {
                label: "Y Axis",
                min:1, max:8000
            }
        }
    };

    StatsService.countGlossToSumFreq().then(function (data) {
        $scope.countGlossToSumFreq = data;
    });
});
