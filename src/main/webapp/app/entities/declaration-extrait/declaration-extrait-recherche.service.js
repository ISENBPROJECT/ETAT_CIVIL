(function() {
    'use strict';
    angular
        .module('etatCivilApp')
        .factory('DeclarationExtrait', DeclarationRecherche);

    DeclarationRecherche.$inject = ['$resource', 'DateUtils'];

    function DeclarationRecherche ($resource, DateUtils) {
        var resourceUrl =  'api/declaration-extraits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: false},
            'get': {
                method: 'GET',
                isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateDeclaration = DateUtils.convertLocalDateToServer(data.dateDeclaration);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateDeclaration = DateUtils.convertLocalDateToServer(data.dateDeclaration);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateNaissanceEnfant = DateUtils.convertLocalDateToServer(data.dateNaissanceEnfant);
                    return angular.toJson(data);
                }
            },
            'search': {
                method: 'POST',

                transformRequest: function (data) {
                    data.dateDeclaration = DateUtils.convertLocalDateToServer(data.dateDeclaration);
                    return angular.toJson(data);
                },
                transformResponse: function (data) {
                	 if (data) {
                         data = angular.fromJson(data);
                     }

                	 return data;
                },
                isArray: true
            }

        });


    }

})();
