'use strict';

egdApp
    .controller('RTKController', function ($scope, $location, $stateParams, $log, RTKService) {
        $log.debug("RTKController");

        var book = $stateParams.book;
        if ($stateParams.query) $scope.query = $stateParams.query;

        if (book && $scope.query) {
            RTKService.search(book, $scope.query).then(function(data) {
                $scope.frames = data;
            });
        }

        $scope.search = function(book) {
            var query = $scope.query;
            $log.debug("RTKController.search: book=", book + ", query=" + query);
            if (query) $location.path('/rtk/' + book + '/' + query);
        };

        $scope.imageLocation = function(strokeDiagramImageId) {
            return "api/images/" + strokeDiagramImageId;
        }
    });

egdApp
    .factory('RTKService', function ($http, $log) {
        return {
            search: function (book, query) {
                var context = "api/rtk/search/" + book + "/" + query;
                return $http.get(context).then(function (response) {
                    $log.debug("RTKService.search: response=", response);
                    return response.data;
                });
            }
        }
    });
