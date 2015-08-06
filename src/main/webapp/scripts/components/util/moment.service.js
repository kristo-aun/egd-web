'use strict';

egdApp
    .factory('Moment', function ($translate, $log, FORMAT_LOCAL_TIME, FORMAT_DATE_TIME, FORMAT_LOCAL_DATE_TIME, FORMAT_LOCAL_DATE, DISPLAY_DATE, DISPLAY_DATE_TIME, DISPLAY_TIME) {
        return {
            now: function() {
                return moment().locale($translate.use());
            },
            valueOf: function(date) {
                return moment(date).locale($translate.use());
            },
            deserialize: function(string, format) {
                return moment(string, format);
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
            deserializeLocalTime: function (input) {
                return this.deserialize(input, FORMAT_LOCAL_TIME);
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
            serializeLocalTime: function (input) {
                return this.serialize(input, FORMAT_LOCAL_TIME);
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
            },
            displayLocalDate: function(input) {
                var mdate = this.deserializeLocalDate(input);
                return this.format(mdate, DISPLAY_DATE);
            },
            displayTime: function(input) {
                var mdate = this.deserializeLocalDateTime(input);
                return this.format(mdate, DISPLAY_TIME);
            },
            dateTimeToString: function(input) {
                return this.serialize(input, DISPLAY_DATE_TIME);
            },
            deserializeDisplayDate: function(input) {
                return this.deserialize(input, DISPLAY_DATE);
            },
            serializeDisplayDate: function(input) {
                var a = this.deserializeDisplayDate(input);
                var b = this.serializeLocalDate(a);
                return b;
            }
        };
    });
