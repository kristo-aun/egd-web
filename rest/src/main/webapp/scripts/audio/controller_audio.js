'use strict';

egdApp.controller('AudioController', function ($scope, resolvedAudio, Audio) {

        $scope.audios = resolvedAudio;

        $scope.create = function () {
            Audio.save($scope.audio,
                function () {
                    $scope.audios = Audio.query();
                    $('#saveAudioModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.audio = Audio.get({id: id});
            $('#saveAudioModal').modal('show');
        };

        $scope.delete = function (id) {
            Audio.delete({id: id},
                function () {
                    $scope.audios = Audio.query();
                });
        };

        $scope.clear = function () {
            $scope.audio = {fileName: null, id: null};
        };
    });
