'use strict';

angular.module('egdApp')
    .controller('AlertController', function ($scope, $translate, $log) {
        var pushAlert = function(type, translation) {
            $scope.alerts = $scope.alerts || [];
            $scope.alerts.push({type: type, msg: translation});
            $log.debug("AlertController.pushAlert: alerts=", $scope.alerts);
        };

        var translate = function(type, alertId, whenTranslated) {
            $translate(alertId)
                .then(function (translation) {
                    whenTranslated(type, translation);
                }, function(reason) {
                    $log.error("AlertController.pushAlert: i18n failed, reason=", reason);
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
