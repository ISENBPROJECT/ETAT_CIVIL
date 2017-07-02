(function() {
    'use strict';
    angular
        .module('etatCivilApp')
        .factory('Extrait', Extrait);

    Extrait.$inject = ['$resource', 'DateUtils'];

    function Extrait ($resource, DateUtils) {
        var resourceUrl =  'api/extraits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateDeclaration = DateUtils.convertLocalDateFromServer(data.dateDeclaration);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateDeclaration = DateUtils.convertLocalDateToServer(copy.dateDeclaration);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateDeclaration = DateUtils.convertLocalDateToServer(copy.dateDeclaration);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
