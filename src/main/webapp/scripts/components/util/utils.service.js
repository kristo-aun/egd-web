egdApp
    .factory('ArrayUtils', function() {
        return {
            arrayOf: function(value, length) {
                var arr = [], i = length;
                while (i--) {
                    arr[i] = value;
                }
                return arr;
            },
            fill: function(array, value) {
                angular.forEach(array, function(item, key) {
                    array[key] = value;
                });
            },
            containsValue: function(array, value) {
                if (array == undefined || array.length < 1) return false;
                var result = false;
                angular.forEach(array, function(item) {
                    if (item === value) {
                        result = true;
                    }
                });
                return result;
            },
            countValue: function(array, value) {
                return array.reduce(function(count, item) {
                    return count + (item == value ? 1 : 0);
                }, 0);
            },
            uniques: function(arr) {
                var a = [];
                for (var i = 0, l = arr.length; i < l; i++)
                    if (a.indexOf(arr[i]) === -1 && arr[i] !== '')
                        a.push(arr[i]);
                return a;
            }
        };
    });
