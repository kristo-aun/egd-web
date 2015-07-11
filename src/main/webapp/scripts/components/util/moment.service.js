'use strict';

egdApp
    .factory('Moment', function ($translate, $log, FORMAT_DATE_TIME, FORMAT_LOCAL_DATE_TIME, FORMAT_LOCAL_DATE) {
        return {
            deserialize: function(string, format) {
                return string ? moment(string, format) : undefined;
            },
            deserializeDateTime: function (input) {
                return this.deserialize(input, FORMAT_DATE_TIME);
            },
            deserializeLocalDateTime: function (input) {
                return this.deserialize(input, FORMAT_LOCAL_DATE_TIME);
            },
            deserializeLocalDate: function (input) {
                return this.deserialize(input, FORMAT_LOCAL_DATE);
            },
            serialize: function (input, format) {
                if (input == undefined || input == null) return input;
                if (angular.isString(input) || angular.isDate(input)) {
                    input = moment(input);
                }
                return input.format(format);
            },
            serializeDateTime: function (input) {
                return this.serialize(input, FORMAT_DATE_TIME);
            },
            serializeLocalDateTime: function (input) {
                return this.serialize(input, FORMAT_LOCAL_DATE_TIME);
            },
            serializeLocalDate: function (input) {
                return this.serialize(input, FORMAT_LOCAL_DATE);
            },
            isBetween: function(from, to, date, compareDates) {
                if (compareDates) {
                    var from2 = moment(from).startOf('day').add(-1, 'minutes');
                    var to2 = moment(to).startOf('day').add(1, 'minutes');
                    var date2 = moment(date).startOf('day');
                    return date2.isBetween(from2, to2);
                }
                return date.isBetween(from, to);
            },
            weekFirstDayFromDate: function (date) {
            	return moment(date).locale($translate.use()).startOf('week').hours(0).minutes(0).seconds(0);
            },
            weekLastDayFromDate: function(date) {
            	return moment(date).locale($translate.use()).endOf('week').hours(23).minutes(59).seconds(59);
            },
            format: function(date, format) {
            	return date.locale($translate.use()).format(format);
            }
        };
    });
