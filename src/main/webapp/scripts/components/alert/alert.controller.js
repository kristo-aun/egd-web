'use strict';

egdApp
    .controller('AlertController', function ($scope, $translate, $log) {
        var pushAlert = function(type, content) {
            $scope.alerts = $scope.alerts || [];

            try {
                $.each($scope.alerts, function(key, value) {
                    if (value.msg == content) {
                        value.count++;
                        throw "alert exists";
                    }
                });
                $scope.alerts.push({type: type, msg: content, count: 1});
            } catch (ignored) {}
        };

        var translate = function(type, alertId, whenTranslated) {
            $translate(alertId)
                .then(function (translation) {
                    whenTranslated(type, translation);
                }, function(e) {
                    whenTranslated(type, alertId);
                }
            );
        };

        // Picks up events to display a server success or error message.
        $scope.$on('success', function (event, alertId) {
            $log.debug("AlertController.success: alertId=", alertId);
            translate("success", alertId, pushAlert);
        });

        $scope.$on('error', function (event, alertId) {
            $log.debug("AlertController.error: alertId=", alertId);
            translate("danger", alertId, pushAlert);
        });

        $scope.closeAlert = function (index) {
            $scope.alerts.splice(index, 1);
        };

        /*
         $timeout(function(){
         $scope.alerts.splice($scope.alerts.indexOf(alert), 1);
         }, 3000);
         //*/
    });
