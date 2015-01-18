'use strict';

egdApp.controller('EntrController', function ($scope, resolvedEntr, Entr) {

        $scope.entrs = resolvedEntr;

        $scope.create = function () {
            Entr.save($scope.entr,
                function () {
                    $scope.entrs = Entr.query();
                    $('#saveEntrModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.entr = Entr.get({id: id});
            $('#saveEntrModal').modal('show');
        };

        $scope.delete = function (id) {
            Entr.delete({id: id},
                function () {
                    $scope.entrs = Entr.query();
                });
        };

        $scope.clear = function () {
            $scope.entr = {src: null, id: null};
        };
    });
