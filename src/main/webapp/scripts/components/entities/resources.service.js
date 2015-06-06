'use strict';

egdApp
    .factory('AppointmentResource', function ($http, Moment) {//vastuvõtuajad
        return {
            findbyUnitAndDateRange: function (unitId, fromDate, toDate) {
                return $http.get('api/appointments/byUnitAndDateRange',
                    {params: {
                        unitId: unitId,
                        fromDate: Moment.serializeLocalDateTime(fromDate),
                        toDate: Moment.serializeLocalDateTime(toDate)}}).then(function (response) {
                    return response.data;
                });
            },
	        findById: function (appointmentId) {
	            return $http.get('api/appointments/' + appointmentId).then(function (response) {
	                return response.data;
	            });
	        },
	        findFirstFree: function (unitId) {
	            return $http.get('api/appointments/firstFree', {params: {unitId: unitId}}).then(function (response) {
	                return response.data;
	            });
	        },
	        createNew: function (datetime, unitId) {
        		return $http({
            		method: 'POST',
            		url: 'api/appointments/new',
            		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            		data: $.param({unitId: unitId,
            			    	 datetime: Moment.serializeLocalDateTime(datetime)})}).then(function (response) {
            	    return response.data;
        		});
            },
            cancelAppointment: function (appointmentId) {
        		return $http({
            		method: 'POST',
            		url: 'api/appointments/cancel',
            		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            		data: $.param({appointmentId: appointmentId})}).then(function (response) {
            	    return response.data;
        		});
            },
            unCancelAppointment: function (appointmentId) {
        		return $http({
            		method: 'POST',
            		url: 'api/appointments/unCancel',
            		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            		data: $.param({appointmentId: appointmentId})}).then(function (response) {
            	    return response.data;
        		});
            }
        };
    })
    .factory('SpecialtyResource', function ($http) {//eriala
        return {
            findByPrincipal: function () {
                return $http.get('api/specialties/byPrincipal').then(function (response) {
                    return response.data;
                });
            }
        };
    })
    .factory('UnitResource', function ($http) {//osakond
        return {
            findByPrincipal: function () {
                return $http.get('api/units/byPrincipal').then(function (response) {
                    return response.data;
                });
            },
            findAll: function (page, limit) {
                return $http.get('api/units',{params: {page: page, limit:limit}}).then(function (response) {
                    return response;
                });
            },
            findById: function (unitId) {
                return $http.get('api/units/' + unitId).then(function (response) {
                    return response.data;
                });
            }
        };
    })
    .factory('PersonResource', function ($http) {//isikud ehk patsiendid
        return {
            findByCodeAndName: function (idcode, fname, sname) {
                return $http.get('api/persons/byIdcodeAndName', {params: {idcode: idcode, fname: fname, sname: sname}}).then(function (response) {
                    return response.data;
                });
            },
            findById: function (id) {
                return $http.get('api/persons/' + id).then(function (response) {
                    return response.data;
                });
            }
        };
    })
    .factory('EmployeeResource', function ($http, $log) {//töötajad ehk arstid, õed ja teised
        return {
            findAll: function (page, limit) {
                return $http.get('api/employees', {params: {page: page, limit: limit}}).then(function (response) {
                    return response;
                });
            },
            findById: function (employeeId) {
                return $http.get('api/employees/' + employeeId).then(function (response) {
                    return response.data;
                });
            },
            findBySpecialty: function (specialtyId) {
                return $http.get('api/employees/bySpecialty/' + specialtyId).then(function (response) {
                    return response.data;
                });
            },
            findByUnit: function (osakondId) {
                return $http.get('api/employees/byUnit/' + osakondId).then(function (response) {
                    return response.data;
                });
            },
            findByLogin: function () {
                return $http.get('api/employees/byLogin').then(function (response) {
                    return response.data;
                });
            },
            changeActiveEmployee: function(employeeId) {
            	return $http({
            		method: 'POST',
            		url: 'api/changeActiveEmployee',
            		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            		data: $.param({employeeId: employeeId})}).then(function (response) {
            	    return response.data;
        		});
            }
        };
    })
    .factory('AuditResource', function ($http, Moment) {
        return {
            findAll: function () {
                return $http.get('api/audits').then(function (response) {
                    return response.data;
                });
            },
            findByPrincipal: function () {
                return $http.get('api/audits/byPrincipal').then(function (response) {
                    return response.data;
                });
            },
            findByDates: function (fromDate, toDate) {
                return $http.get('api/audits/byDates', {
                    params: {
                        fromDate: Moment.serializeDateTime(fromDate),
                        toDate: Moment.serializeDateTime(toDate)
                    }
                }).then(function (response) {
                    return response.data;
                });
            }
        };
    });
