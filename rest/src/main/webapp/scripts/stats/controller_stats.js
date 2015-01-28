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
        seriesDefaults: {renderer: $.jqplot.BarRenderer},
        axes: {
            xaxis: {
                renderer:$.jqplot.CategoryAxisRenderer
            },
            yaxis: {padMax: 1}
        }
    };

    StatsService.countGlossToSumFreq().then(function (data) {
        $scope.countGlossToSumFreq = data;
    });
});
