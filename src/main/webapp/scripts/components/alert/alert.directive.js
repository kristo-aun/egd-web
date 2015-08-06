'use strict';

egdApp
    .directive('globalAlert', function (AlertService, $rootScope) {
        return {
            restrict: 'E',
            templateUrl: 'scripts/components/alert/globalAlert.html',
            controller: ['$scope', function ($scope) {
                $scope.alerts = AlertService.get();

                $rootScope.$on('egdApp.httpError', function (event, httpResponse) {
                    switch (httpResponse.status) {
                        case 0: {
                            // connection refused, server not reachable
                            addErrorAlert("Server ei ole kättesaadav! Proovige uuesti sisse logida.");
                            break;
                        }
                        default: {
                            if (httpResponse.data && httpResponse.data.payload) {
                                addErrorAlert(httpResponse.data.payload);
                            } else if (httpResponse.data && httpResponse.data.exception) {
                                addErrorAlert(JSON.stringify(httpResponse.data.exception));
                            } else {
                                addErrorAlert(JSON.stringify(httpResponse));
                            }
                        }
                    }
                });

                $scope.$on('$destroy', function () {
                    cleanupCleanErrorsListener();
                });

                var addErrorAlert = function (message) {
                    AlertService.error(message);
                }
            }]
        }
    });
