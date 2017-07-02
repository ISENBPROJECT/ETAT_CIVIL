(function() {
    'use strict';
    angular
        .module('etatCivilApp')
        .factory('RegistreNaissance', RegistreNaissance);

    RegistreNaissance.$inject = ['$resource', 'DateUtils'];

    function RegistreNaissance ($resource, DateUtils) {
        var resourceUrl =  'api/registre-naissances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.anneeRegistre = DateUtils.convertLocalDateFromServer(data.anneeRegistre);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.anneeRegistre = DateUtils.convertLocalDateToServer(copy.anneeRegistre);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.anneeRegistre = DateUtils.convertLocalDateToServer(copy.anneeRegistre);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
