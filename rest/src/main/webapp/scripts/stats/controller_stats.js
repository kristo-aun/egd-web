'use strict';

egdApp.controller('StatsController', function ($scope, $log, StatsService) {
    $log.debug("StatsController");
    StatsService.counters().then(function(data) {
        $scope.counters = data;
    });
    StatsService.coreStats().then(function(data) {
        $scope.coreStats = data;
    });
    StatsService.translatedEntrRatio().then(function(data) {
        $scope.translatedEntrRatio = data;
    });

    $scope.data = [[
        ['Heavy Industry', 12],['Retail', 9], ['Light Industry', 14],
        ['Out of home', 16],['Commuting', 7], ['Orientation', 9]
    ]];

    $scope.chartOptions = {
        seriesDefaults: {
            // Make this a pie chart.
            renderer: jQuery.jqplot.PieRenderer,
            rendererOptions: {
                // Put data labels on the pie slices.
                // By default, labels show the percentage of the slice.
                showDataLabels: true
            }
        },
        legend: { show:true, location: 'e' }
    };

    $scope.translatedEntrRatioChartOpts = {
        seriesDefaults: {
            // Make this a pie chart.
            renderer: jQuery.jqplot.PieRenderer,
                rendererOptions: {
                // Put data labels on the pie slices.
                // By default, labels show the percentage of the slice.
                showDataLabels: true
            }
        },
        legend: { show:false, location: 'e' }
    };

    StatsService.countGlossToSumFreq().then(function(data) {
        $scope.countGlossToSumFreq = data;
    });
});
