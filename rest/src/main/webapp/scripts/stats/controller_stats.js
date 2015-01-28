'use strict';

egdApp.controller('StatsController', function ($scope, $log, StatsService) {
    $log.debug("StatsController");
    
    StatsService.counters().then(function(data) {
        $scope.counters = data;
    });

    StatsService.coreStats().then(function(data) {
        $scope.coreStats = data;
    });
});