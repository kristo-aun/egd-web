'use strict';

egdApp.controller('TofusController', function ($location, $scope, $log, $http, uiGridConstants) {
    $log.debug("TofusController");

    var paginationOptions = {
        pageNumber: 1,
        pageSize: 25,
        sort: null
    };

    $scope.gridOptions = {
        showHeader: false,
        enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
        enableVerticalScrollbar: uiGridConstants.scrollbars.NEVER,
        paginationPageSizes: [25, 50, 75],
        paginationPageSize: 25,
        useExternalPagination: true,
        useExternalSorting: false,
        columnDefs: [
            { name: 'word', enableSorting: false },
            { name: 'sentence', enableSorting: false }
        ],
        onRegisterApi: function(gridApi) {
            $scope.gridApi = gridApi;
            gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
                paginationOptions.pageNumber = newPage;
                paginationOptions.pageSize = pageSize;
                getPage(newPage, pageSize);
            });
        }
    };

    var getPage = function(newPage, pageSize) {
        var url = "app/rest/tofus?page=" + newPage + "&size=" + pageSize;
        $http.get(url)
            .success(function (data) {
                $scope.gridOptions.totalItems = 4671;
                var firstRow = (paginationOptions.pageNumber - 1) * paginationOptions.pageSize;
                $scope.gridOptions.data = data.slice(firstRow, firstRow + paginationOptions.pageSize);
            });
    };

    getPage(paginationOptions.pageNumber, paginationOptions.pageSize);
});
