'use strict';

egdApp
    .directive('globalAlert', function(AlertService, $rootScope) {
        return {
            restrict: 'E',
            templateUrl: 'scripts/components/alert/globalAlert.html',
            controller: ['$scope', function($scope) {
                $scope.alerts = AlertService.get();

                var cleanHttpErrorListener = $rootScope.$on('egdApp.httpError', function(event, httpResponse) {
                    var i;
                    event.stopPropagation();
                    switch (httpResponse.status) {
                        // connection refused, server not reachable
                        case 0:
                            addErrorAlert("Server not reachable", 'error.serverNotReachable');
                            break;

                        case 400:
                            if (httpResponse.data && httpResponse.data.fieldErrors) {
                                for (i = 0; i < httpResponse.data.fieldErrors.length; i++) {
                                    var fieldError = httpResponse.data.fieldErrors[i];
                                    // convert 'something[14].other[4].id' to 'something[].other[].id' so translations can be written to it
                                    var convertedField = fieldError.field.replace(/\[\d*\]/g, "[]");
                                    var fieldName = $translate.instant('egdApp.' + fieldError.objectName + '.' + convertedField);
                                    addErrorAlert('Field ' + fieldName + ' cannot be empty', 'error.' + fieldError.message, {fieldName: fieldName});
                                }
                            } else if (httpResponse.data && httpResponse.data.message) {
                                addErrorAlert(httpResponse.data.message, httpResponse.data.message, httpResponse.data);
                            } else {
                                addErrorAlert(httpResponse.data);
                            }
                            break;

                        default:
                            if (httpResponse.data && httpResponse.data.message) {
                                addErrorAlert(httpResponse.data.message);
                            } else {
                                addErrorAlert(JSON.stringify(httpResponse));
                            }
                    }
                });

                $scope.$on('$destroy', function() {
                    if (cleanHttpErrorListener !== undefined && cleanHttpErrorListener !== null) {
                        cleanHttpErrorListener();
                    }
                });

                var addErrorAlert = function(message, key, data) {
                    key = key && key != null ? key : message;
                    AlertService.error(key, data);
                }
            }]
        }
    });
