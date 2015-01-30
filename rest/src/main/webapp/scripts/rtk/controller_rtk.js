'use strict';

egdApp.controller('RTKController', function ($scope, $location, $routeParams, $log, RTKService) {
    $log.debug("RTKController");

    var book = $routeParams.book;
    var query = $routeParams.query;

    if (book && query) {
        RTKService.search(book, query).then(function(data) {
            $scope.frames = data;
        });
    }

    $scope.search = function(book) {
        var query = $scope.query;
        $log.debug("RTKController.search: book=", book + ", query=" + query);
        if (query) $location.path('/rtk/' + book + '/' + query);
    };

    $scope.imageLocation = function(strokeDiagramImageId) {
        return "app/rest/image/" + strokeDiagramImageId;
    }
});
