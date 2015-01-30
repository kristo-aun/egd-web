'use strict';

/* Controllers */

//Binds with xhr feedback messages section. Shows messages in a modal popup at the page header
egdApp.controller('AlertController', function ($scope, $translate, $log) {

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
                $log.error("alertCtrl.pushAlert: i18n failed, reason=", reason);
                whenTranslated(type, alertId);
            }
        );
    };

    // Picks up events to display a server success or error message.
    $scope.$on('success', function (event, alertId) {
        $log.debug("alertCtrl.success: alertId=", alertId);
        translate("success", alertId, pushAlert);
    });

    $scope.$on('error', function (event, alertId) {
        $log.debug("alertCtrl.error: alertId=", alertId);
        translate("danger", alertId, pushAlert);
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

egdApp.controller('MainController', function ($scope, GitService) {
    $scope.date = new Date();
    GitService.get(function (value) {
        $scope.buildTime = value.buildTime;
    });
});

egdApp.controller('AdminController', function ($scope) {
});

egdApp.controller('LanguageController', function ($scope, $translate, LanguageService) {
    $scope.changeLanguage = function (languageKey) {
        $translate.use(languageKey);

        LanguageService.getBy(languageKey).then(function (languages) {
            $scope.languages = languages;
        });
    };

    LanguageService.getBy().then(function (languages) {
        $scope.languages = languages;
    });
});

egdApp.controller('MenuController', function ($scope) {
});

egdApp.controller('LoginController', function ($scope, $location, AuthenticationSharedService) {
    $scope.rememberMe = true;
    $scope.login = function () {
        AuthenticationSharedService.login({
            username: $scope.username,
            password: $scope.password,
            rememberMe: $scope.rememberMe
        });
    }
});

egdApp.controller('ChangelogController', function ($scope) {
});

egdApp.controller('LogoutController', function ($location, AuthenticationSharedService) {
    AuthenticationSharedService.logout();
});

egdApp.controller('SettingsController', function ($scope, Account) {
    $scope.success = null;
    $scope.error = null;
    $scope.settingsAccount = Account.get();

    $scope.save = function () {
        $scope.success = null;
        $scope.error = null;
        $scope.errorEmailExists = null;
        Account.save($scope.settingsAccount,
            function (value, responseHeaders) {
                $scope.error = null;
                $scope.success = 'OK';
                $scope.settingsAccount = Account.get();
            },
            function (httpResponse) {
                if (httpResponse.status === 400 && httpResponse.data === "e-mail address already in use") {
                    $scope.errorEmailExists = "ERROR";
                } else {
                    $scope.error = "ERROR";
                }
            });
    };
});

egdApp.controller('RegisterController', function ($scope, $translate, Register) {
    $scope.success = null;
    $scope.error = null;
    $scope.doNotMatch = null;
    $scope.errorUserExists = null;
    $scope.register = function () {
        if ($scope.registerAccount.password != $scope.confirmPassword) {
            $scope.doNotMatch = "ERROR";
        } else {
            $scope.registerAccount.langKey = $translate.use();
            $scope.doNotMatch = null;
            $scope.success = null;
            $scope.error = null;
            $scope.errorUserExists = null;
            $scope.errorEmailExists = null;
            Register.save($scope.registerAccount,
                function (value, responseHeaders) {
                    $scope.success = 'OK';
                },
                function (httpResponse) {
                    if (httpResponse.status === 400 && httpResponse.data === "login already in use") {
                        $scope.error = null;
                        $scope.errorUserExists = "ERROR";
                    } else if (httpResponse.status === 400 && httpResponse.data === "e-mail address already in use") {
                        $scope.error = null;
                        $scope.errorEmailExists = "ERROR";
                    } else {
                        $scope.error = "ERROR";
                    }
                });
        }
    }
});

egdApp.controller('ActivationController', function ($scope, $routeParams, Activate) {
    Activate.get({key: $routeParams.key},
        function (value, responseHeaders) {
            $scope.error = null;
            $scope.success = 'OK';
        },
        function (httpResponse) {
            $scope.success = null;
            $scope.error = "ERROR";
        });
});

egdApp.controller('PasswordController', function ($scope, Password) {
    $scope.success = null;
    $scope.error = null;
    $scope.doNotMatch = null;
    $scope.changePassword = function () {
        if ($scope.password != $scope.confirmPassword) {
            $scope.doNotMatch = "ERROR";
        } else {
            $scope.doNotMatch = null;
            Password.save($scope.password,
                function (value, responseHeaders) {
                    $scope.error = null;
                    $scope.success = 'OK';
                },
                function (httpResponse) {
                    $scope.success = null;
                    $scope.error = "ERROR";
                });
        }
    };
});

egdApp.controller('SessionsController', function ($scope, resolvedSessions, Sessions) {
    $scope.success = null;
    $scope.error = null;
    $scope.sessions = resolvedSessions;
    $scope.invalidate = function (series) {
        Sessions.delete({series: encodeURIComponent(series)},
            function (value, responseHeaders) {
                $scope.error = null;
                $scope.success = "OK";
                $scope.sessions = Sessions.get();
            },
            function (httpResponse) {
                $scope.success = null;
                $scope.error = "ERROR";
            });
    };
});

egdApp.controller('HealthController', function ($scope, HealthCheckService) {
    $scope.updatingHealth = true;

    $scope.refresh = function () {
        $scope.updatingHealth = true;
        HealthCheckService.check().then(function (promise) {
            $scope.healthCheck = promise;
            $scope.updatingHealth = false;
        }, function (promise) {
            $scope.healthCheck = promise.data;
            $scope.updatingHealth = false;
        });
    };

    $scope.refresh();

    $scope.getLabelClass = function (statusState) {
        if (statusState == 'UP') {
            return "label-success";
        } else {
            return "label-danger";
        }
    };
});

egdApp.controller('ConfigurationController', function ($scope, resolvedConfiguration) {
    $scope.configuration = resolvedConfiguration;
});

egdApp.controller('LogsController', function ($scope, resolvedLogs, LogsService) {
    $scope.loggers = resolvedLogs;

    $scope.changeLevel = function (name, level) {
        LogsService.changeLevel({name: name, level: level}, function () {
            $scope.loggers = LogsService.findAll();
        });
    }
});

egdApp.controller('AuditsController', function ($scope, $translate, $filter, AuditsService) {
    $scope.onChangeDate = function () {
        AuditsService.findByDates($scope.fromDate, $scope.toDate).then(function (data) {
            $scope.audits = data;
        });
    };

    // Date picker configuration
    $scope.today = function () {
        // Today + 1 day - needed if the current day must be included
        var today = new Date();
        var tomorrow = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1); // create new increased date

        $scope.toDate = $filter('date')(tomorrow, "yyyy-MM-dd");
    };

    $scope.previousMonth = function () {
        var fromDate = new Date();
        if (fromDate.getMonth() == 0) {
            fromDate = new Date(fromDate.getFullYear() - 1, 0, fromDate.getDate());
        } else {
            fromDate = new Date(fromDate.getFullYear(), fromDate.getMonth() - 1, fromDate.getDate());
        }

        $scope.fromDate = $filter('date')(fromDate, "yyyy-MM-dd");
    };

    $scope.today();
    $scope.previousMonth();

    AuditsService.findByDates($scope.fromDate, $scope.toDate).then(function (data) {
        $scope.audits = data;
    });
});
