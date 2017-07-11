(function() {
    'use strict';
    angular
        .module('etatCivilApp')
        .factory('DeclarationExtrait', DeclarationExtrait);

    DeclarationExtrait.$inject = ['$resource', 'DateUtils'];

    function DeclarationExtrait ($resource, DateUtils) {
        var resourceUrl =  'api/declaration-extraits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateNaissanceEnfant = DateUtils.convertLocalDateFromServer(data.dateNaissanceEnfant);
                        data.dateNaissanceMere = DateUtils.convertLocalDateFromServer(data.dateNaissanceMere);
                        data.dateNaissancePere = DateUtils.convertLocalDateFromServer(data.dateNaissancePere);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateNaissanceEnfant = DateUtils.convertLocalDateToServer(copy.dateNaissanceEnfant);
                    copy.dateNaissanceMere = DateUtils.convertLocalDateToServer(copy.dateNaissanceMere);
                    copy.dateNaissancePere = DateUtils.convertLocalDateToServer(copy.dateNaissancePere);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateNaissanceEnfant = DateUtils.convertLocalDateToServer(copy.dateNaissanceEnfant);
                    copy.dateNaissanceMere = DateUtils.convertLocalDateToServer(copy.dateNaissanceMere);
                    copy.dateNaissancePere = DateUtils.convertLocalDateToServer(copy.dateNaissancePere);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
